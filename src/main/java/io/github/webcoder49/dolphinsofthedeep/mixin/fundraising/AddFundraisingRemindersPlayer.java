package io.github.webcoder49.dolphinsofthedeep.mixin.fundraising;

import io.github.webcoder49.dolphinsofthedeep.fundraising.FundraisingReminder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


/**
 * Add fundraising reminders triggered by the player entity
 */
@Mixin(PlayerEntity.class)
public abstract class AddFundraisingRemindersPlayer extends LivingEntity {
    @Shadow public abstract boolean isPlayer();

    protected AddFundraisingRemindersPlayer(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method="wakeUp(ZZ)V", at=@At("TAIL"))
    public void wakeUpReminder(CallbackInfo ci) {
        if(this.getWorld().isClient()) {
            FundraisingReminder.sendReminder();
        }
    }
}
