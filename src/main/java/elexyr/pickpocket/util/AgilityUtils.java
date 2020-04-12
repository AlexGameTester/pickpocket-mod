package elexyr.pickpocket.util;

import elexyr.pickpocket.capability.pickpocket.IPickpocket;
import elexyr.pickpocket.capability.pocketowner.IPocketOwner;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraftforge.common.util.LazyOptional;

public class AgilityUtils {
    public static float getProbabilityForStack(Inventory playerInventory, LazyOptional<IPickpocket> userCap, LazyOptional<IPocketOwner> targetCap, ItemStack stack) {}

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
