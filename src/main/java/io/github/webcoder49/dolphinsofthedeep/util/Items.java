package io.github.webcoder49.dolphinsofthedeep.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class Items {
    /**
     * Use up an item from a player if appropriate (not creative)
     * @param stack The stack from which to use up an item
     * @param player The player who owns the stack
     */
    public static void useUpItem(ItemStack stack, PlayerEntity player) {
        if(!player.getAbilities().creativeMode) { // Don't use up in creative
            stack.decrement(1);
        }
    }
}