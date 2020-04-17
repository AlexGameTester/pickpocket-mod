package elexyr.pickpocket.util;

import elexyr.pickpocket.Pickpocket;
import elexyr.pickpocket.capability.pickpocket.IPickpocket;
import elexyr.pickpocket.capability.pocketowner.IPocketOwner;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class SkillUtils {
    /**
     * Represents how much of attentiveness villager gains per robbery
     */
    private static final float ATTENTIVENESS_PER_ROBBERY = 1F;
    /**
     * Represents how much of attentiveness villager gains per it's leveL(novice, master etc.)
     */
    private static final float ATTENTIVENESS_PER_LEVEL = 3f;
    private static final float REPUTATION_MODIFIER = 61F;

    /**
     * Represents the minimal portion of items you can gain with very high skill
     */
    private static final float RANDOM_AMOUNT_MODIFIER = 0.7f;

    /**
     * Represents the minimal portion of skill increase you can get
     */
    private static final float BASE_SKILL_ADDITION_PERCENT = 0.8F;
    /**
     * Represents how fast you gain skill from robberies
     */
    private static final float SKILL_MULTIPLIER = 1F;
    private static final Random RANDOM = new Random();

    public static void updateSkillFromStack(LazyOptional<IPickpocket> pickpocket, ItemStack stack) {
        if (!pickpocket.isPresent()) {
            throw new IllegalArgumentException("Pickpocket must exist but wasn't provided");
        }

        pickpocket.ifPresent(iPickpocket -> {
            float skill = iPickpocket.getSkill();
            iPickpocket.addSkill(
                    getSkillMultiplier(stack.getRarity(), skill) * stack.getCount() * getRarityMultiplier(stack.getRarity()) * (BASE_SKILL_ADDITION_PERCENT + RANDOM.nextFloat())
            );
        });
    }

    public static ItemStack getStolenStack(PlayerEntity playerEntity, LazyOptional<IPocketOwner> targetCap, ItemStack stack, VillagerEntity villagerEntity) {
        if (stack.isEmpty()) {
            return ItemStack.EMPTY.copy();
        }

        float count = (float) ((float) stack.getCount() *
                                (1 - Math.exp(1 /
                                        getRarityMultiplier(stack.getRarity()) * probabilityModifier(playerEntity, targetCap, villagerEntity) +
                                        RANDOM_AMOUNT_MODIFIER * RANDOM.nextFloat()
                                        )));
        int intCount = (int) Math.floor(count);

        if (intCount == 0) {
            return ItemStack.EMPTY.copy();
        }

        ItemStack copy = stack.copy();
        copy.setCount((intCount));
        return copy;
    }

    private static float probabilityModifier(PlayerEntity playerEntity, LazyOptional<IPocketOwner> targetCap, @Nullable VillagerEntity villagerEntity) {

        /* int size = playerEntity.inventory.getSizeInventory(); */
        //TODO: write skill upgrade from rings
        float skill = 1;

        return Math.max(0,
                (-1f) * calculateVillagerAttentiveness(playerEntity, villagerEntity, targetCap) + skill);
    }

    private static float calculateVillagerAttentiveness(@Nonnull PlayerEntity player, @Nullable VillagerEntity villager, @Nonnull LazyOptional<IPocketOwner> capability) {
        float base = calculateAttentiveness(capability);
        if (villager == null) {
            return base;
        }
        VillagerData data = villager.getVillagerData();
        //Reputation can be from -30 to 30, so if reputation = 0 it's modifier is 1
        return data.getLevel() * ATTENTIVENESS_PER_LEVEL * (1f / ( (float)villager.getPlayerReputation(player) + 61f)) * REPUTATION_MODIFIER * base;
    }

    private static float calculateAttentiveness(LazyOptional<IPocketOwner> capability) {
        //1 is set in case if capability is empty or there were no robberies
        final float[] attentiveness = {1};
        if (!capability.isPresent()) {
            Pickpocket.LOGGER.warn("Can't find PocketOwner capability of villager.");
        }
        capability.ifPresent(cap -> {
            attentiveness[0] += cap.getRobberiesCount() * ATTENTIVENESS_PER_ROBBERY;
        }
        );

        return attentiveness[0];
    }

    private static float getSkillMultiplier(Rarity rarity, float skill) {
        //If your skill is high you gain less experience from stealing common items. 0.1f is used to prevent zero addition
        return SKILL_MULTIPLIER * (float) (Math.log(skill + 0.1f) / Math.pow(skill, SkillUtils.getRarityMultiplier(rarity) / 2f));
    }


    public static float getRarityMultiplier(Rarity rarity) {
        switch (rarity) {
            case COMMON:
                return 1f;
            case UNCOMMON:
                return 3f;
            case RARE:
                return 5f;
            case EPIC:
                return 10f;
            default:
                return 2f;
        }
    }

}
