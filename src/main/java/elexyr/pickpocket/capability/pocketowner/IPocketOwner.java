package elexyr.pickpocket.capability.pocketowner;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface IPocketOwner {
    int getRobberiesCount();
    void setRobberiesCount(int count);
    void addRobbery();
}
