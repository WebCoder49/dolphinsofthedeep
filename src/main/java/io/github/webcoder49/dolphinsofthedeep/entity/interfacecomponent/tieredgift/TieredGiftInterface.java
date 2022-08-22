package io.github.webcoder49.dolphinsofthedeep.entity.interfacecomponent.tieredgift;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import io.github.webcoder49.dolphinsofthedeep.entity.interfacecomponent.conversation.Conversation;
import io.github.webcoder49.dolphinsofthedeep.entity.interfacecomponent.conversation.ConversationInterface;
import io.github.webcoder49.dolphinsofthedeep.entity.interfacecomponent.conversation.DelayedMessage;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;

import java.util.List;
import java.util.function.Consumer;

public interface TieredGiftInterface extends ConversationInterface {
    // TODO: CONVERSATION_NUMPOSS_GIFTS_ANNOUNCE = ...
    // TODO: CONVERSATION_NUMPOSS_GIFTS_DELIVER = ...

    /* Give gifts */
    default void giveGiftFromTier(GiftTier tier) {
        // TODO: Test next line; add to lang; add loot tables
        // Global
        Text styledTier = Text.translatable(DolphinsOfTheDeep.MOD_ID + ".gifts.tier." + tier.getName()).getWithStyle(Style.EMPTY.withFormatting(tier.getFormatting())).get(0);
        this.setConversation(
                new Conversation(
                        new DelayedMessage(
                                this.getTranslatedText("gifts.announce." + (int) (Math.random() * 3))
                                , 60 // delay in ticks
                        ),
                        new DelayedMessage(
                                this.getTranslatedText("gifts.deliver.beforeTier." + (int) (Math.random() * 1))
                                        .append(styledTier)
                                        .append(this.getTranslatedText("gifts.deliver.afterTier." + (int) (Math.random() * 1)))
                                , 20 // delay in ticks
                                , () -> {
                                    // Give gift
                                    if(this instanceof Entity) {
                                        Entity thisEntity = ((Entity) this);
                                        Consumer<ItemStack> after = (gift) -> {
                                            // Drop a gift stack
                                            ((Entity) this).dropStack(gift, 2);
                                        };
                                        tier.getGift(after, thisEntity.world, this.getOwner(), thisEntity.getPos());
                                    }
                                }
                        )
                )
        ); // TODO: this.CONVERSATION_NUMPOSS_GIFTS_DELIVER
    }

    default void giveGift(double xp) {
        this.giveGiftFromTier(this.getGiftTier(xp));
    }

    /**
     * Get the tier of a gift using probabilities based on the gift xp. See the {@code GiftTier} enum for more.
     * @param xp The gift XP (e.g. days experience)
     */
    default GiftTier getGiftTier(double xp) {
        double randomLeft = Math.random();
        for (GiftTier tier : GiftTier.values()) {
            if(tier != GiftTier.COMMON) { // TODO: TEST; Add list of non-default tiers + default in GiftTier
                double prob = tier.getProbability(xp);
//                this.tellOwner(Text.of(tier.getName() + " " + prob + " (" + randomLeft + " left)"));
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
