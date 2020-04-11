package elexyr.pickpocket.capabilitypickpocket;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;

import java.util.List;

public interface IPickpocket {
    double getStealingSkills();
    void setStealingSkill(double value);
    void updateSkillsFromStolenStacks(List<ItemStack> stolenStacks);

    public static double getSkillMultiplier(Rarity rarity, double skill) {
        //If your skill is high you gain less experience from stealing common items
        switch (rarity) {
            case COMMON:
                return Math.exp(- skill);
            case UNCOMMON:
                return Math.exp(- skill) * 3;
            case RARE:
                return Math.exp(- skill) * 5;
            case EPIC:
                return Math.exp(- skill) * 10;
            default:
                return Math.exp(- skill) * 2;
        }
    }
}
