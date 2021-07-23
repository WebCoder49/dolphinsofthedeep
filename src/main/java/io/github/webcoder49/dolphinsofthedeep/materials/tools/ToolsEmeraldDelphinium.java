package io.github.webcoder49.dolphinsofthedeep.materials.tools;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class ToolsEmeraldDelphinium implements ToolMaterial {

    public static final ToolsEmeraldDelphinium INSTANCE = new ToolsEmeraldDelphinium();

    @Override
    public int getDurability() {
        return 150;
    }
    @Override
    public float getMiningSpeedMultiplier() {
        return 3.0F;
    }
    @Override
    public float getAttackDamage() {
        return 0.0F;
    }
    @Override
    public int getMiningLevel() {
        return 0;
    }
    @Override
    public int getEnchantability() {
        return 20;
    }
    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(DolphinsOfTheDeep.EMERALD_DELPHINIUM_INGOT);
    }
}