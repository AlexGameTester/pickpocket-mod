package elexyr.pickpocket.item;

import elexyr.pickpocket.Pickpocket;
import elexyr.pickpocket.capability.pickpocket.IPickpocket;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraftforge.common.util.LazyOptional;

public class BasicRingItem extends Item {

    public BasicRingItem() {
        super(new Item.Properties().maxStackSize(1).rarity(Rarity.UNCOMMON).group(Pickpocket.ITEM_GROUP));
        Pickpocket.LOGGER.debug("Basic ring constructor is fired");
    }


    float getSkillIncrease(LazyOptional<IPickpocket> owner) {
        return 0;
    }
}
