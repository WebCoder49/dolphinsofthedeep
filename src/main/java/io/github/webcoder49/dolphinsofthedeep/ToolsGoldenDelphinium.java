package io.github.webcoder49.dolphinsofthedeep;

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
        return 2.5F;
    }
    @Override
    public int getMiningLevel() {
        return 2;
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