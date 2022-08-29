package io.github.webcoder49.dolphinsofthedeep.entity.component.tamable;

import io.github.webcoder49.dolphinsofthedeep.entity.dolphin.DolphinEntity;
import io.github.webcoder49.dolphinsofthedeep.entity.interfacecomponent.conversation.ConversationInterface;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.text.Text;

import java.util.EnumSet;

public class FollowOwnerGoal extends Goal {
    private final Tameable entity;
    private final double maxDistSquared;
    private final double minDistSquared;
    private final double speed;

    private final EntityNavigation navigation;

    // Set up when goal started
    private Entity owner;

    /**
     * Create a goal to travel towards the owner within a certain radius.
     * @param entity {@Code Tameable} mob this is run on
     * @param speed Speed to follow owner at
     * @param minDist Don't follow owner inside this radius.
     * @param maxDist Don't follow owner outside this radius.
     */
    public FollowOwnerGoal(Tameable entity, double speed, double minDist, double maxDist) {
        // Follow the owner without teleportation.

        // Parameters
        if(entity instanceof MobEntity) {
            this.entity = entity;
        } else {
            throw new IllegalArgumentException("Tameable entity must be a mob.");
        }
        this.maxDistSquared = maxDist*maxDist;
        this.minDistSquared = minDist*minDist;
        this.speed = speed;

        // Derivatives
        this.navigation = ((MobEntity) entity).getNavigation();

        // Set controls used by this.
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        Entity owner = this.entity.getOwner();

        if(owner == null
            || owner.isSpectator()
            || ((MobEntity)this.entity).hasPassenger(owner)) {
            return false;
        }

        this.owner = owner; // Set owner so doesn't need to be accessed by getter
        return true;
    }

    @Override
    public boolean shouldContinue() {
        if(this.navigation.isIdle()) return false;

        // Check not outside of follow area - Pythagoras
        // x^2 + y^2 + z^2 <= r^2
        return ((MobEntity)this.entity).squaredDistanceTo(this.owner) <= this.maxDistSquared;
    }

    @Override
    public void stop() {
        // Remove owner
        this.owner = null;
        this.navigation.stop();
    }

    @Override
    public void tick() {
        if(!((MobEntity)this.entity).hasVehicle()) {
            double distSquared = ((MobEntity) this.entity).squaredDistanceTo(this.owner);
            if (distSquared < maxDistSquared && distSquared > minDistSquared) {
                this.navigation.startMovingTo(this.owner, this.speed);
            }
        }
    }
}
