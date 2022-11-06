package io.github.webcoder49.dolphinsofthedeep.entity.component.tamable;

import net.minecraft.entity.player.PlayerEntity;

public interface ClientTameListener {
    public void clientSetOwner(PlayerEntity player);
}
