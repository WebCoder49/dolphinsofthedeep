package io.github.webcoder49.dolphinsofthedeep.mixin.fundraising;

import com.mojang.authlib.GameProfile;
import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
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
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class FundraisingReminder extends LivingEntity {
    @Shadow public abstract boolean isPlayer();

    protected FundraisingReminder(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method="wakeUp(ZZ)V", at=@At("TAIL"))
    public void wakeUpReminder(CallbackInfo ci) { // TODO: Split into separate class
        if(this.isPlayer() && this.getWorld().isClient()) {
            MinecraftClient.getInstance().setScreen(new ConfirmScreen((wantsToDonate) -> { // Ask if wants to donate

                if(wantsToDonate) {
                    Util.getOperatingSystem().open("https://justgiving.com/fundraising/dolphinsofthedeep");
                    MinecraftClient.getInstance().setScreen(new ConfirmScreen((wantsToLearnMore) -> { // Thank player
                        if(wantsToLearnMore) {
                            Util.getOperatingSystem().open("https://uk.whales.org");
                        } else {
                            MinecraftClient.getInstance().setScreen(null);
                        }
                    }, Text.translatable(DolphinsOfTheDeep.MOD_ID+".fundraising.thanks.title"), Text.translatable(DolphinsOfTheDeep.MOD_ID+".fundraising.thanks.body"), Text.translatable(DolphinsOfTheDeep.MOD_ID+".fundraising.thanks.learnMore"), Text.of("Keep Playing")));
                } else {
                    MinecraftClient.getInstance().setScreen(null);
                }
            }, Text.translatable(DolphinsOfTheDeep.MOD_ID+".fundraising.reminder.title").getWithStyle(Style.EMPTY
                    .withBold(true)
                    .withFormatting(Formatting.AQUA)

                    // Choose random from language file - TODO: Remove dec. point (look at other usage)
            ).get(0), Text.translatable(DolphinsOfTheDeep.MOD_ID+".fundraising.reminder.body" + (int)Math.floor(Math.random()*Integer.parseInt(Text.translatable(DolphinsOfTheDeep.MOD_ID+".fundraising.reminder.body.num").getString())))

                    , Text.translatable(DolphinsOfTheDeep.MOD_ID+".fundraising.reminder.donate").getWithStyle(Style.EMPTY
                    .withUnderline(true)
                    .withFormatting(Formatting.AQUA)
            ).get(0), Text.translatable(DolphinsOfTheDeep.MOD_ID+".fundraising.reminder.maybeLater").getWithStyle(Style.EMPTY
                    .withFormatting(Formatting.DARK_GRAY)
            ).get(0)));
        }
    }
}
