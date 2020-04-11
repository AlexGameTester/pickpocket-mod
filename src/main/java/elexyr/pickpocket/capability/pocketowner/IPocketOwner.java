package elexyr.pickpocket.capability.pocketowner;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface IPocketOwner {
    int getRobberiesCount();
    List<ItemStack> getStolenItems();
    void setRobberiesCount(int count);
    default void addRobbery() {
        setRobberiesCount(getRobberiesCount() + 1);
    }
    void addStolenItems(List<ItemStack> newStolen);
}
