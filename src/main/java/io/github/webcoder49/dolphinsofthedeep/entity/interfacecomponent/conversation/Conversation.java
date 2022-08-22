package io.github.webcoder49.dolphinsofthedeep.entity.interfacecomponent.conversation;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * A list of messages with delays to send to the owner via a `ConversationInterface`
 */
public class Conversation {
    private Deque<DelayedMessage> messages;

    /**
     * Create a conversation; use `tick` to run
     * @param messages Each param is a `DelayedMessage`.
     */
    public Conversation(DelayedMessage... messages) {
        // Create a conversation out of `DelayedMessage`s
        this.messages = new LinkedList<>();
        this.messages.offer(new DelayedMessage(null, 0)); // Remove this to send first message
        for (DelayedMessage message : messages) {
            this.messages.offer(message);
        }
    }

    /**
     * Run one tick and complete any actions
     * @param sendMessage direct any message to this callback
     */
    public void tick(Consumer<Text> sendMessage) {
        while(!this.messages.isEmpty()) {
            // Tick this message
            if(this.messages.peekFirst().tickComplete()) {
                this.messages.removeFirst();

                DelayedMessage delayedMessage = this.messages.peekFirst();
                if(delayedMessage != null) {
                    // Run next message
                    Text message = delayedMessage.getMessage();
                    if(message != null) {
                        sendMessage.accept(message);
                    }
                }
            } else {
                // Still in delay
                return;
            }
        }
    }
}
