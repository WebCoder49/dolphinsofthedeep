package io.github.webcoder49.dolphinsofthedeep.material.tools;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class ToolsGoldenDelphinium implements ToolMaterial {

    public static final ToolsGoldenDelphinium INSTANCE = new ToolsGoldenDelphinium();

    @Override
    public int getDurability() {
        return 42;
    }
    @Override
    public float getMiningSpeedMultiplier() {
        return 12.0F;
    }
    @Override
    public float getAttackDamage() {
        return 0.0F;
    }
    @Override
    public int getMiningLevel() {
        return 1;
    }
    @Override
    public int getEnchantability() {
        return 25;
    }
    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(DolphinsOfTheDeep.GOLDEN_DELPHINIUM_INGOT);
    }
}