package io.github.webcoder49.dolphinsofthedeep.entities.dolphins;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

/**
 * Extends vanilla dolphin to add vanilla functionality; extra modded functionality added here
 */
public class DolphinEntity extends net.minecraft.entity.passive.DolphinEntity implements Tameable {
    /**
     * Constructor for a dolphin
     */
    public DolphinEntity(EntityType<? extends net.minecraft.entity.passive.DolphinEntity> entityType, World world) {
        super(entityType, world);
    }

    /**
     * TEST
     */
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        // TODO: Fix run/crash-reports/crash-2022-07-25_08.32.49-client.txt
        LivingEntity owner = this.getOwner();
        if(owner == null) {
            DolphinsOfTheDeep.log(Level.INFO, "No owner");
        } else {
            if(owner instanceof PlayerEntity) {
                DolphinsOfTheDeep.log(Level.INFO, owner.getName().getString());
            } else {
                DolphinsOfTheDeep.log(Level.INFO, "Non-player owner");
            }
        }
        this.setOwner(player);
        return ActionResult.success(true);
    }

    /* Tamable */

    // Getting and setting owner

    // Attach owner data to DataTracker
    /** Each bit is 1 flag:
     * 128=
     * 64=
     * 32=
     * 16=
     * 8=
     * 4=Tamed?
     * 2=
     * 1=
     */
    protected static final TrackedData<Byte> TAMEABLE_FLAGS;
    protected static final TrackedData<Optional<UUID>> OWNER_UUID;
    static {
        TAMEABLE_FLAGS = DataTracker.registerData(DolphinEntity.class, TrackedDataHandlerRegistry.BYTE);
        OWNER_UUID = DataTracker.registerData(DolphinEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    }

    /**
     * Initialise tracked data
     */
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TAMEABLE_FLAGS, (byte)0);
        this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
    }

    /**
     * Get the UUID of the owner, or null if not tamed
     * @return Owner UUID
     */
    @Nullable
    public UUID getOwnerUuid() {
        // Use DataTracker
        return (UUID)((Optional)this.dataTracker.get(OWNER_UUID)).orElse((Object)null);
    }

    /**
     * Set the UUID of the owner, or null if not tamed
     * @param uuid UUID of the owner
     */
    public void setOwnerUuid(@Nullable UUID uuid) {
        this.dataTracker.set(OWNER_UUID, Optional.ofNullable(uuid));
    }

    /**
     * Set the username of the owner, or null if not tamed
     * @param player username of the owner
     */
    public void setOwner(PlayerEntity player) {
        this.setTamed(true);
        this.setOwnerUuid(player.getUuid());
        // TODO: Add Advancement Criteria
    }

    @Nullable
    public LivingEntity getOwner() {
        try {
            UUID uUID = this.getOwnerUuid();
            return uUID == null ? null : this.world.getPlayerByUuid(uUID);
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    /**
     * Set bit with significance 4 of the tameable flags to show whether tamed
     * @param tamed Is this animal tamed? = flag value
     */
    public void setTamed(boolean tamed) {
        // Set bit4 tameable flags
        byte b = (Byte)this.dataTracker.get(TAMEABLE_FLAGS);
        if (tamed) {
            this.dataTracker.set(TAMEABLE_FLAGS, (byte)(b | 4)); // Set bit4 to 1
        } else {
            this.dataTracker.set(TAMEABLE_FLAGS, (byte)(b & -5)); // Set bit4 to 0
        }
    }
}
