package io.github.webcoder49.dolphinsofthedeep.mixin.gui;

import com.mojang.serialization.Decoder;
import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import io.github.webcoder49.dolphinsofthedeep.entity.dolphin.DolphinEntity;
import io.github.webcoder49.dolphinsofthedeep.gui.dolphinInventory.DolphinInventoryScreen;
import io.github.webcoder49.dolphinsofthedeep.gui.dolphinInventory.DolphinInventoryScreenHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.message.SentMessage;
import net.minecraft.network.packet.s2c.play.OpenHorseScreenS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Add a method to the ClientPlayNetworkHandler to display a dolphin inventory - works with a horse packet but adds an extra if statement to the end
 */
@Mixin(ClientPlayNetworkHandler.class)
public class ClientOpenDolphinInventory {
    @Shadow @Final private MinecraftClient client;

    @Inject(method="onOpenHorseScreen(Lnet/minecraft/network/packet/s2c/play/OpenHorseScreenS2CPacket;)V", at=@At("TAIL"), locals= LocalCapture.CAPTURE_FAILHARD)
    public void addDolphinInventoryHandler(OpenHorseScreenS2CPacket packet, CallbackInfo ci, Entity entity) {
        if(entity instanceof DolphinEntity) {
            ClientPlayerEntity player = this.client.player;
            DolphinEntity dolphin = (DolphinEntity)entity;
            DolphinInventoryScreenHandler screenHandler = new DolphinInventoryScreenHandler(packet.getSyncId(), player.getInventory(), player, dolphin);
            player.currentScreenHandler = screenHandler;
            this.client.setScreen(new DolphinInventoryScreen(screenHandler, player.getInventory(), dolphin));
        }
    }
}
