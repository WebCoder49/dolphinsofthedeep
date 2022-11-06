package io.github.webcoder49.dolphinsofthedeep.fundraising;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import io.github.webcoder49.dolphinsofthedeep.mixinInterfaces.fundraising.HasDonatedGameOptionInterface;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;

/**
 * A class which controls fundraising reminders for the player
 */
public class FundraisingReminder {
    /**
     * Send a reminder on the client
     */
    public static void sendReminder() {
        if (!((HasDonatedGameOptionInterface) MinecraftClient.getInstance().options).getDotdHasDonated()) {
            MinecraftClient.getInstance().setScreen(new ConfirmScreen((wantsToDonate) -> { // Ask if wants to donate

                if (wantsToDonate) {
                    Util.getOperatingSystem().open("https://justgiving.com/fundraising/dolphinsofthedeep");

                    // Save has donated
                    ((HasDonatedGameOptionInterface) MinecraftClient.getInstance().options).setDotdHasDonated(true);
                    MinecraftClient.getInstance().options.write();

                    MinecraftClient.getInstance().setScreen(new ConfirmScreen((wantsToLearnMore) -> { // Thank player
                        if (wantsToLearnMore) {
                            Util.getOperatingSystem().open("https://uk.whales.org");
                        } else {
                            MinecraftClient.getInstance().setScreen(null);
                        }
                    }, Text.translatable(DolphinsOfTheDeep.MOD_ID + ".fundraising.thanks.title"), Text.translatable(DolphinsOfTheDeep.MOD_ID + ".fundraising.thanks.body"), Text.translatable(DolphinsOfTheDeep.MOD_ID + ".fundraising.thanks.learnMore"), Text.translatable(DolphinsOfTheDeep.MOD_ID + ".fundraising.thanks.keepPlaying")));
                } else {
                    MinecraftClient.getInstance().setScreen(null);
                }
            }, Text.translatable(DolphinsOfTheDeep.MOD_ID + ".fundraising.reminder.title").getWithStyle(Style.EMPTY
                            .withBold(true)
                            .withFormatting(Formatting.AQUA)

                    // Choose random from language file - TODO: Remove dec. point (look at other usage)
            ).get(0), Text.translatable(DolphinsOfTheDeep.MOD_ID + ".fundraising.reminder.body." + (int) Math.floor(Math.random() * Integer.parseInt(Text.translatable(DolphinsOfTheDeep.MOD_ID + ".fundraising.reminder.body.num").getString()))),

                    Text.translatable(DolphinsOfTheDeep.MOD_ID + ".fundraising.reminder.donate").getWithStyle(Style.EMPTY
                            .withUnderline(true)
                            .withFormatting(Formatting.AQUA)
                    ).get(0), Text.translatable(DolphinsOfTheDeep.MOD_ID + ".fundraising.reminder.maybeLater").getWithStyle(Style.EMPTY
                    .withFormatting(Formatting.DARK_GRAY)
            ).get(0)));
        }
    }
}
