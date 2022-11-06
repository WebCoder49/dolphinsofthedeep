package io.github.webcoder49.dolphinsofthedeep.mixin.fundraising;

import io.github.webcoder49.dolphinsofthedeep.fundraising.FundraisingReminder;
import net.fabricmc.fabric.mixin.client.keybinding.GameOptionsMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.world.EditGameRulesScreen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.biome.SpawnSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Add fundraising reminders triggered by the client network handler
 */
@Mixin(ClientPlayNetworkHandler.class)
public abstract class AddFundraisingRemindersClient {
    @Shadow public abstract ClientWorld getWorld();

    @Shadow @Final private MinecraftClient client;

    @Inject(method="onGameJoin", at=@At("TAIL"))
    public void joinGameReminder(CallbackInfo ci) { // TODO: DEBUG
        if(this.getWorld().isClient()) {
            FundraisingReminder.sendReminder();
        }
    }
}
