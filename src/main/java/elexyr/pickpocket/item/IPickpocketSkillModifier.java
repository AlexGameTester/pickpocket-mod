package elexyr.pickpocket.item;

import elexyr.pickpocket.capability.pickpocket.IPickpocket;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

/**
 * This interface should be implemented on item which can increase/decrease pickpocket skill of entity that holds this item in it's inventory
 */
public interface IPickpocketSkillModifier {

    /**
     * @param target capability of pickpocket
     * @return value of pickpocket skill this item adds
     */
    float getSkillIncrease(LazyOptional<IPickpocket> target);

    /**
     * Fired when this item is used to affect pickpocket's skill
     * @param stack item stack that contains this item
     * @param target capability of pickpocket
     */
    void onUsed(ItemStack stack, LazyOptional<IPickpocket> target);
}
