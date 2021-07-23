package io.github.webcoder49.dolphinsofthedeep.materials.armour;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;

public class ArmourGoldenDelphinium implements ArmorMaterial {
    private static final int[] BASE_DURABILITY = new int[] {13, 15, 16, 11};
    private static final int[] PROTECTION_VALUES = new int[] {2, 4, 6, 2};

    @Override
    public int getDurability(EquipmentSlot slot) {
        return BASE_DURABILITY[slot.getEntitySlotId()] * 18;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return PROTECTION_VALUES[slot.getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return 28;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ENTITY_DOLPHIN_SPLASH;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(DolphinsOfTheDeep.GOLDEN_DELPHINIUM_INGOT);
    }

    @Override
    public String getName() {
        return "golden_delphinium";
    }

    @Override
    public float getToughness() {
        return 0.0F;
    }

    @Override
    public float getKnockbackResistance() {
        return 0.0F;
    }
}
