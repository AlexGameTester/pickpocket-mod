package elexyr.pickpocket.capability.pickpocket;

import net.minecraft.item.ItemStack;

import java.util.List;

public class PickpocketPlayer implements IPickpocket {

    private float skill;

    public PickpocketPlayer() {
        //TODO: Debug only. Change to 1
        this.skill = 100000f;
    }

    @Override
    public float getSkill() {
        return skill;
    }

    @Override
    public void setStealingSkill(float value) {
        if (value < 0) {
            throw new IllegalArgumentException("Skill must be bigger than 0. Provided value " + value);
        }

        skill = value;
    }

    @Override
    public void addSkill(float value) {
        if (skill + value < 0) {
            throw new IllegalArgumentException("Skill must be bigger than 0. You can't add value of " + value + " to current skill of " + skill);
        }

        skill += value;
    }

}
