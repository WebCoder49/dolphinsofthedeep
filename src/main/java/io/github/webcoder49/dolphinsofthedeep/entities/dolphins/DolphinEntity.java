package io.github.webcoder49.dolphinsofthedeep.entities.dolphins;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
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

    private static Ingredient TAMING_INGREDIENT;

    /* Tamable */
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

    // Getting and setting owner - lowest-level>highest-level

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

    /**
     * Get the username of the owner, or null if not tamed
     */
    @Nullable
    public LivingEntity getOwner() {
        try {
            UUID uUID = this.getOwnerUuid();
            return uUID == null ? null : this.world.getPlayerByUuid(uUID);
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.getOwnerUuid() != null) { // Tamed
            // Add Owner UUID
            nbt.putUuid("Owner", this.getOwnerUuid());
        }
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        // Get UUID of owner
        UUID uUID;
        if (nbt.containsUuid("Owner")) { // UUID
            uUID = nbt.getUuid("Owner");
        } else { // Username
            String string = nbt.getString("Owner");
            uUID = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string);
        }

        if (uUID != null) { // Tamed
            try {
                this.setOwnerUuid(uUID);
                this.setTamed(true);
            } catch (Throwable var4) { // UUID Doesn't exist
                this.setTamed(false);
            }
        }
    }
    // Tame with fish
    /**
     * TEST
     */
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        DolphinsOfTheDeep.log(Level.INFO, "Interacted.");
        // Get item in hand
        ItemStack itemStack = player.getStackInHand(hand);
        if (this.isTamingItem(itemStack)) {
            DolphinsOfTheDeep.log(Level.WARN, "Not taming item.");
            return ActionResult.FAIL;
        } else {
            DolphinsOfTheDeep.log(Level.INFO, "Taming.");
            this.setOwner(player);
            player.sendMessage(Text.translatable(String.format("entity.%1$s.dolphin.tamed", DolphinsOfTheDeep.MOD_ID)));
            return ActionResult.CONSUME;
        }
    }

    /**
     * Return true if the item can be used to tame a dolphin.
     */
    public boolean isTamingItem(ItemStack stack) {
        return TAMING_INGREDIENT.test(stack);
    }
    static {
        /**
         * Items used to tame a dolphin = Tropical Fish, Salmon, Cod
          */
        TAMING_INGREDIENT = Ingredient.ofItems(Items.TROPICAL_FISH, Items.SALMON, Items.COD);
    }

    /* Conversations */
//    public boolean tellOwner(Text message) {
//        LivingEntity owner = this.getOwner();
//        if(owner instanceof PlayerEntity) {
//            owner.sendMessage(message);
//        }
//        return false; // Not player owner
//    }
}
