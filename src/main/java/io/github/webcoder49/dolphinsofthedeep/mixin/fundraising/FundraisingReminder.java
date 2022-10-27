package io.github.webcoder49.dolphinsofthedeep.mixin.fundraising;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class FundraisingReminder extends LivingEntity {
    protected FundraisingReminder(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method="wakeUp()V", at=@At("TAIL"))
    public void wakeUpReminder(CallbackInfo ci) { // TODO: FIX
        if(this.getWorld().isClient()) {
            MinecraftClient.getInstance().setScreen(new ConfirmScreen((wantsToDonate) -> { // Ask if wants to donate

                if(wantsToDonate) {
                    Util.getOperatingSystem().open("https://justgiving.com/fundraising/dolphinsofthedeep");
                    MinecraftClient.getInstance().setScreen(new ConfirmScreen((wantsToLearnMore) -> { // Thank player
                        if(wantsToLearnMore) {
                            Util.getOperatingSystem().open("https://uk.whales.org");
                        } else {
                            MinecraftClient.getInstance().setScreen(null);
                        }
                    }, Text.of("Thank you so much for your donation."), Text.of("We're opening the page now; if it doesn't work, please go to JustGiving.com/DolphinsOfTheDeep.\n\n To see how your support will be used, please see WDC's website below, or continue playing the game!"), Text.of("Learn more about WDC"), Text.of("Keep Playing")));
                } else {
                    MinecraftClient.getInstance().setScreen(null);
                }
            }, Text.of("We need your help.").getWithStyle(Style.EMPTY
                    .withBold(true)
                    .withFormatting(Formatting.AQUA)
            ).get(0), Text.of("Not all dolphins live in the sea: there are 4 species of river dolphins, but unfortunately all of them are endangered.\n\nPlease donate to the WDC to help keep them safe."), Text.of("Donate").getWithStyle(Style.EMPTY
                    .withUnderline(true)
                    .withFormatting(Formatting.AQUA)
            ).get(0), Text.of("Maybe Later").getWithStyle(Style.EMPTY
                    .withFormatting(Formatting.DARK_GRAY)
            ).get(0)));
        }
    }
}
