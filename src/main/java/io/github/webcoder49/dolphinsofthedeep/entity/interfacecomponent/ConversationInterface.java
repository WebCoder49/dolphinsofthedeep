package io.github.webcoder49.dolphinsofthedeep.entity.interfacecomponent;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Pair;
import org.apache.logging.log4j.Level;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Allow an entity to converse privately with its owner using the chat.
 */
public interface ConversationInterface {

    LivingEntity getOwner();
    Text getName();
    EntityType<?> getType();

    /**
     * Send a message to the owner in the chat and return a boolean success flag.
     * @param message The styled Text message to send
     * @return boolean success flag
     */
    default boolean tellOwner(Text message) {
        DolphinsOfTheDeep.log(Level.INFO, message.getString());
        LivingEntity owner = this.getOwner();
        if(owner instanceof PlayerEntity) {
            MutableText chatMessage = Text.empty().copy();
            chatMessage.append(this.getTranslatedText("chatPrefix", this.getName())
                    .setStyle(Style.EMPTY.withColor(TextColor.parse("aqua"))));
            chatMessage.append(message);

            owner.sendMessage(chatMessage);
            return true;
        }
        return false; // Not player owner
    }

    /**
     * Send a conversation to the player, with delays
     * @param messages A List of Pairs of (message (MutableText), delay (int, s))
     * @param then A closure to run after the messages
     */
    default void tellOwnerMany(List<Pair<MutableText, Integer>> messages, @Nullable Runnable then) { // TODO: add `then` parameter (what to do afterwards)
        new Thread(() -> {
            for(int i = 0; i < messages.size(); i++) {
                Pair<MutableText, Integer> messageAndDelay = messages.get(i);
                this.tellOwner(messageAndDelay.getLeft()); // Message
                try {
                    Thread.sleep(messageAndDelay.getRight()); // Delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(then != null) {
                then.run();
            }
        }).start(); // Run without pausing game
    }

    /**
     * Get the translated text by path key under this mob.
     * @param path "."-separated path of key after entity.modid.entityid
     * @return Translated MutableText
     */
    default MutableText getTranslatedText(String path, Object... args) {
        return Text.translatable(
                String.format("%1$s.%2$s", this.getType().getTranslationKey(), path),
                args
        );
    }
}
