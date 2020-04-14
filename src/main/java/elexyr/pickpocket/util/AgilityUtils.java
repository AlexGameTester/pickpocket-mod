package elexyr.pickpocket.util;

import elexyr.pickpocket.Pickpocket;
import elexyr.pickpocket.capability.pickpocket.IPickpocket;
import elexyr.pickpocket.capability.pocketowner.CapabilityPocketOwner;
import elexyr.pickpocket.capability.pocketowner.IPocketOwner;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraftforge.common.util.LazyOptional;

public class AgilityUtils {
    private static final float ATTENTIVENESS_PER_ROBBERY = 1F;
    private static final float ATTENTIVENESS_PER_LEVEL = 1f;

//    public static float getProbabilityForStack(Inventory playerInventory, LazyOptional<IPickpocket> userCap, LazyOptional<IPocketOwner> targetCap, ItemStack stack, AbstractVillagerEntity) {}

    private static float calculateVillagerAttentiveness(PlayerEntity player, VillagerEntity villager, LazyOptional<IPocketOwner> capability) {
        VillagerData data = villager.getVillagerData();
        //TODO: What if reputation is zero - zero division
        return data.getLevel() * ATTENTIVENESS_PER_LEVEL * (1f / (float)villager.getPlayerReputation(player)) * calculateAttentiveness(villager.getCapability(CapabilityPocketOwner.POCKET_OWNER_CAPABILITY));
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
