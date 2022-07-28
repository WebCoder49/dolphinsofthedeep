package io.github.webcoder49.dolphinsofthedeep.entities.components;

import io.github.webcoder49.dolphinsofthedeep.entities.dolphins.DolphinEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

/**
 * A bit like the vanilla TameableEntity for land animals, but does not include
 * sitting to allow it to be applied to other entities than `Animal`s. This is a component
 * to have an instance used as a property of an entity on initialisation, rather than
 * a class to be extended from.
 */
public class TamableComponent {
    // TrackedData
    DataTracker dataTracker;
    TrackedData<Boolean> isTamed;
    TrackedData<Optional<UUID>> ownerUuid;
    public TamableComponent(DataTracker dataTracker, TrackedData<Boolean> isTamed, TrackedData<Optional<UUID>> ownerUuid) {
        // Save TrackedData fields
        this.dataTracker = dataTracker;
        this.isTamed = isTamed;
        this.ownerUuid = ownerUuid;
    }

    // Getting and setting owner - lowest-level>highest-level

    /**
     * Set the tracked data show whether tamed
     * @param isTamed Is this entity tamed? = flag value
     */
    public void setTamed(boolean isTamed) {
        this.dataTracker.set(this.isTamed, isTamed);
    }

    /**
     * Get whether this entity is tamed
     * @return Is this entity tamed?
     */
    public boolean getTamed() {
        return this.dataTracker.get(this.isTamed);
    }

    /**
     * Get the UUID of the owner, or null if not tamed
     * @return Owner UUID
     */
    @Nullable
    public UUID getOwnerUuid() {
        // Use DataTracker
        return (UUID)(
                (Optional)this.dataTracker.get(
                        this.ownerUuid
                )).orElse(
                        (Object)null
                );
    }

    /**
     * Set the UUID of the owner, or null if not tamed
     * @param uuid UUID of the owner
     */
    public void setOwnerUuid(@Nullable UUID uuid) {
        this.dataTracker.set(this.ownerUuid, Optional.ofNullable(uuid)); // As optional so nullable
    }

    /**
     * Set the PlayerEntity owner, or null if not tamed
     * @param player username of the owner
     */
    public void setOwner(PlayerEntity player) {
        this.setTamed(true);
        this.setOwnerUuid(player.getUuid());
        // TODO: Add Advancement Criteria
    }

    /**
     * Get the PlayerEntity owner, or null if not tamed
     */
    @Nullable
    public LivingEntity getOwner(World world) {
        try { // getting the owner's UUID
            UUID uUID = this.getOwnerUuid();
            return uUID == null ? null : world.getPlayerByUuid(uUID);
        } catch (IllegalArgumentException wrongUuid) { // because there is no player with this UUID
            return null;
        }
    }

    // NBTs
    public void writeNbt(NbtCompound nbt) {
        // Taming
        if (this.getOwnerUuid() != null) { // Tamed
            // Add Owner UUID
            nbt.putUuid("Owner", this.getOwnerUuid());
        }
    }

    public void readNbt(NbtCompound nbt, MinecraftServer server) {
        // Get UUID of owner
        UUID uUID;
        if (nbt.containsUuid("Owner")) { // UUID
            uUID = nbt.getUuid("Owner");
        } else { // Username
            String string = nbt.getString("Owner");
            uUID = ServerConfigHandler.getPlayerUuidByName(server, string);
        }

        if (uUID != null) { // Tamed
            try { // setting the owner
                this.setOwnerUuid(uUID);
                this.setTamed(true);
            } catch (Throwable wrongUuid) { // UUID Doesn't exist, so can't be tamed
                this.setTamed(false);
            }
        }
    }
}
