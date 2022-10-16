package io.github.webcoder49.dolphinsofthedeep.entity.interfacecomponent.conversation;

import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

/**
 * A message (or action) with a delay after it, used in `Conversation`s
 */
public class DelayedMessage { // TODO
    private @Nullable Text msg;
    private int delayTicks;
    private @Nullable Runnable action;
    private boolean sent; // Has this been sent yet?

    public DelayedMessage(@Nullable Text msg, int delayTicks) {
        this(
                msg,
                delayTicks,
                null
        );
    }

    /**
     * A message (optionally with a Runnable action) with a delay after it, used in `Conversation`s
     * @param msg The Text of the message to be shown in the chat bar.
     * @param delayTicks The delay of the message before the next in ticks
     */
    public DelayedMessage(@Nullable Text msg, int delayTicks, @Nullable Runnable action) {
        this.msg = msg;
        this.action = action;
        this.delayTicks = delayTicks;
        this.sent = false;
    }

    /**
     * Is this a new message?
     * @return true if the message hasn't been sent yet and should be now.
     */
    public boolean toBeSent() {
        if(!this.sent) {
            this.sent = true;
            return true;
        }
        return false;
    }

    /**
     * Tick in the delay after the message
     * @return true if the message's delay is complete and the next action should be run on this tick.
     */
    public boolean tickComplete() {
        if(this.delayTicks <= 0) {
            return true;
        }
        this.delayTicks--;
        return false;
    }

    /**
     * After running the action, get the message to display in the chat bar (so it can be further edited)
     * @return message or null if only action
     */
    @Nullable
    public Text getMessage() {
        if(this.action != null) {
            this.action.run();
        }
        return this.msg;
    }
}
