package io.github.webcoder49.dolphinsofthedeep.entity.interfacecomponent.conversation;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Tameable;
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
public interface ConversationInterface extends Tameable {
    // Linked to mc classes so obfuscation does not make method unreachable
    default Text getName() {
        // Must be entity for this
        if(this instanceof Entity) {
            return ((Entity)this).getName();
        }
        return Text.of("");
    }

    default EntityType<?> getType() {
        // Must be entity for this
        if(this instanceof Entity) {
            return ((Entity)this).getType();
        }
        return EntityType.DOLPHIN; // Default
    }

    default void conversationTick(Conversation conversation) {
        if (conversation != null) {
            conversation.tick(this::tellOwner);
        }
    }

    /**
     * Send a message to the owner in the chat and return a boolean success flag.
     * @param message The styled Text message to send
     * @return boolean success flag
     */
    default boolean tellOwner(Text message) {
        DolphinsOfTheDeep.log(Level.INFO, message.getString());
        Entity owner = this.getOwner();
        if(owner instanceof PlayerEntity) {
            MutableText chatMessage = Text.empty().copy();
            chatMessage.append(this.getEntityTranslatedText("chatPrefix", this.getName())
                    .setStyle(Style.EMPTY.withColor(TextColor.parse("aqua"))));
            chatMessage.append(message);

            owner.sendMessage(chatMessage);
            return true;
        }
        return false; // Not player owner
    }

    /**
     * Get the translated text by path key under general conversation.
     * @param path "."-separated path of key after modid."conversation"
     * @return Translated MutableText
     */
    default MutableText getTranslatedText(String path, Object... args) {
        return Text.translatable(
                String.format("%1$s.%2$s", DolphinsOfTheDeep.MOD_ID + ".conversation", path),
                args
        );
    }

    /**
     * Get the translated text by path key under this mob.
     * @param path "."-separated path of key after entity.modid.entityid
     * @return Translated MutableText
     */
    default MutableText getEntityTranslatedText(String path, Object... args) {
        return Text.translatable(
                String.format("%1$s.%2$s", this.getType().getTranslationKey(), path),
                args
        );
    }
}
