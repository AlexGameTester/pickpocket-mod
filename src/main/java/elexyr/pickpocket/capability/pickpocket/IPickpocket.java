package elexyr.pickpocket.capability.pickpocket;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface IPickpocket {
    float getSkill();
    void setStealingSkill(float value);
    void addSkill(float value);
}
