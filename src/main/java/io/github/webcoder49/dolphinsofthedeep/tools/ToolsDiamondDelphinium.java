package io.github.webcoder49.dolphinsofthedeep.tools;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class ToolsDiamondDelphinium implements ToolMaterial {

    public static final ToolsDiamondDelphinium INSTANCE = new ToolsDiamondDelphinium();

    @Override
    public int getDurability() {
        return 1581;
    }
    @Override
    public float getMiningSpeedMultiplier() {
        return 8.0F;
    }
    @Override
    public float getAttackDamage() {
        return 3.0F;
    }
    @Override
    public int getMiningLevel() {
        return 3;
    }
    @Override
    public int getEnchantability() {
        return 15;
    }
    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(DolphinsOfTheDeep.DIAMOND_DELPHINIUM_INGOT);
    }
}