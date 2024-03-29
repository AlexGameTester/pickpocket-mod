package elexyr.pickpocket.capability.pocketowner;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class VillagerPocketOwner implements IPocketOwner {

    private int robberiesCount;

    public VillagerPocketOwner() {
        this.robberiesCount = 0;
    }

    @Override
    public int getRobberiesCount() {
        return robberiesCount;
    }

    @Override
    public void setRobberiesCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Robberies count can't be less than 0. Provided value " + count);
        }

        robberiesCount = count;
    }

    @Override
    public void addRobbery() {
        robberiesCount++;
    }
}
