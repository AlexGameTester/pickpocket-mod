package elexyr.pickpocket.util;

import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.List;

public class VillagerUtils {
    public static List<ItemStack> getSellingStacks(VillagerEntity villagerEntity) {
        ArrayList<ItemStack> sellingStacks = new ArrayList<>(18);
        villagerEntity.getOffers().forEach(
                merchantOffer -> {
                    ItemStack stack = merchantOffer.getCopyOfSellingStack();
                    if (stack.getItem() != Items.EMERALD) {
                        sellingStacks.add(stack);
                    }
                }
        );

        return sellingStacks;
    }
}
