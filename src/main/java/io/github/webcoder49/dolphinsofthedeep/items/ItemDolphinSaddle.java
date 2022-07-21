package io.github.webcoder49.dolphinsofthedeep.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ItemDolphinSaddle extends Item {
    public ItemDolphinSaddle(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {

        // TODO: Action for Dolphin Saddle

        return TypedActionResult.success(playerEntity.getStackInHand(hand));
    }
}