package io.github.webcoder49.dolphinsofthedeep.entity.interfacecomponent.tieredgift;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import io.github.webcoder49.dolphinsofthedeep.entity.interfacecomponent.ConversationInterface;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Pair;

import java.util.List;

public interface TieredGiftInterface extends ConversationInterface {
    // TODO: CONVERSATION_NUMPOSS_GIFTS_ANNOUNCE = ...
    // TODO: CONVERSATION_NUMPOSS_GIFTS_DELIVER = ...
    public double giftXp = 0;

    /* Give gifts */
    default void giveGiftFromTier(GiftTier tier) {
        // TODO: Test next line; add to lang; add loot tables
        Text styledTier = this.getTranslatedText("gifts.tier." + tier.getName()).getWithStyle(Style.EMPTY.withFormatting(tier.getFormatting())).get(0);
        this.tellOwnerMany(
                List.of(
                        new Pair<>(
                                this.getTranslatedText("gifts.announce." + (int) (Math.random() * 3))
                                , 3000 // delay in milliseconds
                        ),
                        new Pair<>(
                                this.getTranslatedText("gifts.deliver.beforeTier." + (int) (Math.random() * 1))
                                        .append(styledTier)
                                        .append(this.getTranslatedText("gifts.deliver.afterTier." + (int) (Math.random() * 1)))
                                , 1000 // delay in milliseconds
                        )
                ),
                () -> {
                    // Give gift
                    if(this instanceof Entity) {
                        ((Entity) this).dropStack(tier.getGift(((Entity) this).getServer()), 2);
                    }
                }
        ); // TODO: this.CONVERSATION_NUMPOSS_GIFTS_DELIVER
    }

    default void giveGift() {
        this.giveGiftFromTier(this.getGiftTier());
    }

    /**
     * Get the tier of a gift using probabilities based on the gift xp. See the {@code GiftTier} enum for more.
     */
    default GiftTier getGiftTier() {
        int days = 20;
        double randomLeft = Math.random();
        for (GiftTier tier : GiftTier.values()) {
            if(tier != GiftTier.COMMON) { // TODO: TEST; Add list of non-default tiers + default in GiftTier
                double prob = tier.getProbability(days);
                this.tellOwner(Text.of(tier.getName() + " " + prob + " (" + randomLeft + " left)"));
                if (randomLeft < prob) {
                    return tier;
                }
                // Remove this prob - on next tier
                randomLeft -= prob;
            }
        }
        return GiftTier.COMMON; // Default
    }
}
