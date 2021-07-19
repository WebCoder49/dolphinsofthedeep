package io.github.webcoder49.dolphinsofthedeep;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ItemLeatherDolphinArmour extends Item {
    public ItemLeatherDolphinArmour(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {

        // TODO: Action for Dolphin Armour

        return TypedActionResult.success(playerEntity.getStackInHand(hand));
    }
}