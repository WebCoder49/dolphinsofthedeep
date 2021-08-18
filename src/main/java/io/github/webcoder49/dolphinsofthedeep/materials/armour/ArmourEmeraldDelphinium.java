package io.github.webcoder49.dolphinsofthedeep.materials.armour;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;

public class ArmourEmeraldDelphinium implements ArmorMaterial {
    private static final int[] BASE_DURABILITY = new int[] {68, 61, 66, 100};
    private static final int[] PROTECTION_VALUES = new int[] {2, 3, 4, 2};

    @Override
    public int getDurability(EquipmentSlot slot) {
        return BASE_DURABILITY[slot.getEntitySlotId()] * 110;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return PROTECTION_VALUES[slot.getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return 14;
    }

    @Override
    public SoundEvent getEquipSound() {
        return DolphinsOfTheDeep.EQUIP_EMERALD_DELPHINIUM;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(DolphinsOfTheDeep.EMERALD_DELPHINIUM_INGOT);
    }

    @Override
    public String getName() {
        return "emerald_delphinium";
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
