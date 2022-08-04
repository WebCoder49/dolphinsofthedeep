package io.github.webcoder49.dolphinsofthedeep.mixin;

import net.minecraft.entity.ai.goal.DolphinJumpGoal;
import net.minecraft.entity.passive.DolphinEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DolphinJumpGoal.class)
public class RiddenDolphinJumpGoal {
    @Final
    @Shadow // Placeholder
    private DolphinEntity dolphin;

    @Inject(at=@At("TAIL"), method="stop()V")
    public void addToStop(CallbackInfo info) {
        if(this.dolphin instanceof io.github.webcoder49.dolphinsofthedeep.entity.dolphin.DolphinEntity) {
            // Stop ridden jump
            ((io.github.webcoder49.dolphinsofthedeep.entity.dolphin.DolphinEntity) this.dolphin).stopRiddenJump(); // Casted
        }
    }
}
