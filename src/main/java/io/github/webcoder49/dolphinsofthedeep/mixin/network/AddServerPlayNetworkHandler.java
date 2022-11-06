package io.github.webcoder49.dolphinsofthedeep.mixin.network;

import io.github.webcoder49.dolphinsofthedeep.mixinInterfaces.network.AddServerPlayNetworkHandlerInterface;
import io.github.webcoder49.dolphinsofthedeep.network.packet.c2s.RenameEntityC2SPacket;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerPlayNetworkHandler.class)
/**
 * Add new listeners for C2S packets to the ServerPlayNetworkHandler
 * Ducktyped
 */
public class AddServerPlayNetworkHandler implements AddServerPlayNetworkHandlerInterface {
    @Shadow public ServerPlayerEntity player;

    public void onRenameEntity(RenameEntityC2SPacket packet) {
        Entity entity = this.player.getWorld().getEntityById(packet.getEntityId());
        entity.setCustomName(Text.of(packet.getName()));
    }
}
