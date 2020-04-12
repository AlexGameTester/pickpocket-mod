package elexyr.pickpocket.capability.pickpocket;

import elexyr.pickpocket.util.AgilityUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;

import java.util.List;

public interface IPickpocket {
    float getStealingSkills();
    void setStealingSkill(float value);
    void updateSkillsFromStolenStacks(List<ItemStack> stolenStacks);
    void addStealingSkill(float value);

    public static float getSkillMultiplier(Rarity rarity, float skill) {
        //If your skill is high you gain less experience from stealing common items
        return (float) (Math.log(skill + 0.05) / Math.pow(skill, AgilityUtils.getRarityMultiplier(rarity) / 2));
    }
}
