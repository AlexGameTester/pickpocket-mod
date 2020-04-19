package elexyr.pickpocket.item;

import elexyr.pickpocket.Pickpocket;
import elexyr.pickpocket.capability.pickpocket.IPickpocket;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraftforge.common.util.LazyOptional;

public class BasicRingItem extends RingItem {

    public BasicRingItem() {
        super(new Item.Properties().maxStackSize(1).rarity(Rarity.UNCOMMON).group(Pickpocket.ITEM_GROUP).defaultMaxDamage(100));
    }

    @Override
    public float getSkillIncrease(LazyOptional<IPickpocket> target) {
        return 0;
    }

    @Override
    public void onUsed(ItemStack stack, LazyOptional<IPickpocket> target) {

    }

}
