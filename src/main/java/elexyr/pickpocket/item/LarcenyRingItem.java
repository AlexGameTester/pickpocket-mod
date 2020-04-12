package elexyr.pickpocket.item;

import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class LarcenyRingItem extends Item {
    //TODO: get this from config
    private static float skillIncrease = 1f;

    public LarcenyRingItem() {
        this(new Item.Properties().maxStackSize(1).rarity(Rarity.EPIC));
    }

    public LarcenyRingItem(Properties properties) {
        super(properties);
    }
}
