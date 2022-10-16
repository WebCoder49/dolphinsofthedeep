package io.github.webcoder49.dolphinsofthedeep.entity.interfacecomponent.conversation;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Consumer;

/**
 * A list of messages with delays to send to the owner via a `ConversationInterface`
 */
public class Conversation {
    private Deque<DelayedMessage> messages;

    /**
     * Create a conversation; use `tick` to run and `add` to add messages.
     */
    public Conversation() {
        // Create a conversation (use add
        this.messages = new LinkedList<>();
    }

    /**
     * Run one tick and complete any actions
     * @param sendMessage direct any message to this callback
     */
    public void tick(Consumer<Text> sendMessage) {
        while(!this.messages.isEmpty()) {
            // Tick this message
            if(this.messages.peekFirst().toBeSent()) {
                // Send the message
                DelayedMessage delayedMessage = this.messages.peekFirst();
                if(delayedMessage != null) {
                    // Run next message
                    Text message = delayedMessage.getMessage();
                    if(message != null) {
                        sendMessage.accept(message);
                    }
                }
            } else if(this.messages.peekFirst().tickComplete()) {
                this.messages.removeFirst();
            } else {
                // Still in delay
                return;
            }
        }
    }

    /***
     * Add messages to the conversation queue
     */
    public void add(DelayedMessage... messages) {
        for (DelayedMessage message : messages) {
            this.messages.offer(message);
        }
    }

    /***
     * Add messages to the conversation queue from a conversation key of saved messages
     * How to create a conversation key:
     * - Set the `lang` file value for "dolphinsofthedeep.conversation."+[the key] to "[num ticks]:Message 1;[num ticks]:Message 2"... as a single string, e.g. "20:Hello;10:World!"
     */
    public void addConversation(String key) {
        String encodedMessages = Text.translatable("dolphinsofthedeep.conversation." + key).getString();
        String[] encodedMessagePairs = encodedMessages.split(";");
        for (String encodedMessagePair : encodedMessagePairs) {
            String[] messagePair = encodedMessagePair.split(":", 2);
            this.messages.offer(new DelayedMessage(Text.of(messagePair[1]), Integer.parseInt(messagePair[0])));
        }
    }

    /***
     * Return true if there are no messages left in the message stack right now.
     */
    public boolean isFree() {
        return this.messages.isEmpty();
    }
}
