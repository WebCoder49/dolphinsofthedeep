package io.github.webcoder49.dolphinsofthedeep.entity.interfacecomponent.tieredgift;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import io.github.webcoder49.dolphinsofthedeep.entity.interfacecomponent.conversation.Conversation;
import io.github.webcoder49.dolphinsofthedeep.entity.interfacecomponent.conversation.ConversationInterface;
import io.github.webcoder49.dolphinsofthedeep.entity.interfacecomponent.conversation.DelayedMessage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.map.MapIcon;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.apache.logging.log4j.Level;

import java.util.function.Consumer;

public interface TieredGiftInterface extends ConversationInterface {

    /* Give gifts */
    default void giveGiftFromTier(GiftTier tier, Conversation conversation) {
        // Global
        Text styledTier = Text.translatable(DolphinsOfTheDeep.MOD_ID + ".gifts.tier." + tier.getName()).getWithStyle(Style.EMPTY.withFormatting(tier.getFormatting())).get(0);
        conversation.add(
            new DelayedMessage(
                    this.getTranslatedText("gifts.announce." + (int) (Math.random() * 3)) // 3 different messages
                    , 60 // delay in ticks
            ),
            new DelayedMessage(
                    this.getTranslatedText("gifts.deliver.beforeTier")
                            .append(styledTier)
                            .append(this.getTranslatedText("gifts.deliver.afterTier"))
                    , 20 // delay in ticks
                    , () -> {
                        // Give gift
                        if(this instanceof Entity) {
                            Entity thisEntity = ((Entity) this);
                            Consumer<ItemStack> after = (gift) -> {
                                // Drop a gift stack
                                if(this.getOwner() instanceof  PlayerEntity) {
                                    ((PlayerEntity)this.getOwner()).giveItemStack(gift);
                                }
                            };
                            tier.getGift(after, thisEntity.world, this.getOwner(), thisEntity.getPos());
                        }
                    }
            )
        ); // TODO: this.CONVERSATION_NUMPOSS_GIFTS_DELIVER
    }

    default void giveGift(double minQuality, double xp, Conversation conversation) {
        this.giveGiftFromTier(this.getGiftTier(minQuality, xp), conversation);
    }

    /**
     * Get the tier of a gift using probabilities based on the gift xp. See the {@code GiftTier} enum for more.
     * @param xp The gift XP (e.g. days experience)
     */
    default GiftTier getGiftTier(double minQuality, double xp) {
        DolphinsOfTheDeep.log(Level.DEBUG, String.valueOf(minQuality));
        double randomLeft = 1 - (Math.random()*minQuality);
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
