package elexyr.pickpocket.item;

import elexyr.pickpocket.Pickpocket;
import elexyr.pickpocket.capability.pickpocket.IPickpocket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraftforge.common.util.LazyOptional;

public abstract class RingItem extends Item {

    protected RingItem(Properties properties) {
        super(properties);
    }

    abstract float getSkillIncrease(LazyOptional<IPickpocket> owner);
}
