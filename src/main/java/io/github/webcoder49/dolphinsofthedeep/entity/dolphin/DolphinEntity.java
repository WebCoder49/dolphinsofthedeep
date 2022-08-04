package io.github.webcoder49.dolphinsofthedeep.entity.dolphin;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import io.github.webcoder49.dolphinsofthedeep.entity.component.TamableComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.DolphinJumpGoal;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

/**
 * Extends vanilla dolphin to add vanilla functionality; extra modded functionality added here
 */
public class DolphinEntity extends net.minecraft.entity.passive.DolphinEntity implements Tameable, Saddleable {
    // Components
    private final SaddledComponent saddledComponent;
    private final TamableComponent tamableComponent;

    private static Ingredient TAMING_INGREDIENT;
    private static Ingredient ARMOUR_INGREDIENT;
    protected SimpleInventory items;
    private static final int INV_SIZE = 1;

    protected static final TrackedData<Boolean> IS_TAMED;
    protected static final TrackedData<Optional<UUID>> OWNER_UUID;
    private static final TrackedData<Boolean> SADDLED;
    private static final TrackedData<Integer> BOOST_TIME;

    /**
     * Constructor for a dolphin
     */
    public DolphinEntity(EntityType<? extends net.minecraft.entity.passive.DolphinEntity> entityType, World world) {
        super(entityType, world);
        this.saddledComponent = new SaddledComponent(this.dataTracker, BOOST_TIME, SADDLED);
        this.tamableComponent = new TamableComponent(this.dataTracker, IS_TAMED, OWNER_UUID);

        this.items = new SimpleInventory(INV_SIZE);
    }

    /**
     * Initialise tracked data
     */
    protected void initDataTracker() {
        super.initDataTracker();
        // Taming
        this.dataTracker.startTracking(IS_TAMED, false);
        this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
        // Saddling
        this.dataTracker.startTracking(SADDLED, false);
        this.dataTracker.startTracking(BOOST_TIME, 0);
    }

    // NBTs
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        this.tamableComponent.writeNbt(nbt);
        this.saddledComponent.writeNbt(nbt);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.tamableComponent.readNbt(nbt, this.getServer());
        this.saddledComponent.readNbt(nbt);
    }

    // Events
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        DolphinsOfTheDeep.log(Level.INFO, "Interacted.");
        // Get item in hand
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if (this.world.isClient) {
            // Just display to player; not whole server - return SUCCESS to avoid duplicate where any action will be taken
            if((this.getOwner() == null && this.isTamingItem(itemStack)) || (this.getOwner() == player)) {
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.FAIL;
            }
        } else {
            // TODO: Remove duplicates
            if(this.getOwner() == null) { // Untamed - try to tame
                if (item.isFood() && this.isTamingItem(itemStack)) {
                    DolphinsOfTheDeep.log(Level.INFO, "Taming.");
                    this.setOwner(player);
                    this.tellOwner(this.getTranslatedText("tamed"));

                    this.playSound(SoundEvents.ENTITY_DOLPHIN_EAT, 1.0F, 1.0F);
                    this.eat(itemStack, player, hand);

                    return ActionResult.CONSUME;
                } else {
                    DolphinsOfTheDeep.log(Level.WARN, "Not taming item.");
                    return ActionResult.FAIL;
                }
            } else if(this.getOwner() == player) { // Tamed by player.
                if(itemStack.isOf(Items.SADDLE)) { // Saddle
                    itemStack.useOnEntity(player, this, hand);
                    this.saddle(SoundCategory.NEUTRAL);
                    player.startRiding(this);
                }
                if(this.isArmourItem(itemStack)) { // TODO: Add ItemStack decrement + armour returns
                    this.setArmour(itemStack);
                }
                this.tellOwner(Text.of("Hello!"));
                return ActionResult.SUCCESS;
            } else { // Tamed by someone else
                return ActionResult.FAIL;
            }
        }
    }

    /* Tamable */
    // Attach owner data to DataTracker
    static {
        IS_TAMED = DataTracker.registerData(DolphinEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        OWNER_UUID = DataTracker.registerData(DolphinEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);


        /**
         * Items used to tame a dolphin = Tropical Fish, Salmon, Cod
         */
        TAMING_INGREDIENT = Ingredient.ofItems(Items.TROPICAL_FISH, Items.SALMON, Items.COD);
    }

    // Getting and setting owner - lowest-level>highest-level

    public void setTamed(boolean tamed) {
        this.tamableComponent.setTamed(tamed);
    }

    public boolean getTamed() {
        return this.tamableComponent.getTamed();
    }

    /**
     * Get the UUID of the owner, or null if not tamed
     * @return Owner UUID
     */
    @Nullable
    public UUID getOwnerUuid() {
        // Use DataTracker
        return this.tamableComponent.getOwnerUuid();
    }

    /**
     * Set the UUID of the owner, or null if not tamed
     * @param uuid UUID of the owner
     */
    public void setOwnerUuid(@Nullable UUID uuid) {
        this.tamableComponent.setOwnerUuid(uuid);
    }

    /**
     * Set the username of the owner, or null if not tamed
     * @param player username of the owner
     */
    public void setOwner(PlayerEntity player) {
        this.tamableComponent.setOwner(player);
    }

    /**
     * Get the username of the owner, or null if not tamed
     */
    @Nullable
    public LivingEntity getOwner() {
        return this.tamableComponent.getOwner(this.world);
    }

    /**
     * Get the translated text by path key under this mob.
     * @param path "."-separated path of key after entity.modid.entityid
     * @return Translated MutableText
     */
    public MutableText getTranslatedText(String path, Object... args) {
        return Text.translatable(
                String.format("%1$s.%2$s", this.getType().getTranslationKey(), path),
                args
        );
    }

    /**
     * Return true if the item can be used to tame a dolphin.
     */
    public boolean isTamingItem(ItemStack stack) {
        return TAMING_INGREDIENT.test(stack);
    }

    /**
     * Eat an item from the player and use it up.
     * @param itemStack The ItemStack of food.
     * @param player The player who is holding the food.
     * @param hand The player's hand which the food is in.
     */
    public void eat(ItemStack itemStack, PlayerEntity player, Hand hand) {
        this.playSound(SoundEvents.ENTITY_DOLPHIN_EAT, 1F, 1F);
        if(!player.getAbilities().creativeMode) { // Not creative - use up
            itemStack.decrement(1); // Use up 1 item
        }
    }

    /* Conversations */
    public boolean tellOwner(Text message) {
        DolphinsOfTheDeep.log(Level.INFO, message.getString());
        LivingEntity owner = this.getOwner();
        if(owner instanceof PlayerEntity) {
            MutableText chatMessage = Text.empty().copy();
            chatMessage.append(this.getTranslatedText("chatPrefix", this.getName())
                    .setStyle(Style.EMPTY.withColor(TextColor.parse("aqua"))));
            chatMessage.append(message);

            owner.sendMessage(chatMessage);
            return true;
        }
        return false; // Not player owner
    }

    /* Riding */
    // Saddleable
    static {
        SADDLED = DataTracker.registerData(DolphinEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        BOOST_TIME = DataTracker.registerData(DolphinEntity.class, TrackedDataHandlerRegistry.INTEGER);

        ARMOUR_INGREDIENT = Ingredient.ofItems(DolphinsOfTheDeep.LEATHER_DOLPHIN_ARMOUR, DolphinsOfTheDeep.IRON_DOLPHIN_ARMOUR, DolphinsOfTheDeep.GOLD_DOLPHIN_ARMOUR, DolphinsOfTheDeep.DIAMOND_DOLPHIN_ARMOUR, DolphinsOfTheDeep.NETHERITE_DOLPHIN_ARMOUR);
    }
    public void onTrackedDataSet(TrackedData<?> data) {
        if (BOOST_TIME.equals(data) && this.world.isClient) {
            // Looking at boost time - want to boost now
            this.saddledComponent.boost();
        }
        super.onTrackedDataSet(data);
    }
    public boolean isSaddled() {
        return this.saddledComponent.isSaddled();
    }
    public void saddle(@Nullable SoundCategory soundCategory) {

        this.saddledComponent.setSaddled(true);
        if(soundCategory != null) { // Play sound
            this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_DOLPHIN_SPLASH, soundCategory, 1.0F, 1.0F);
        }
    }
    public boolean canBeSaddled() {
        return this.getTamed();
    }

    // Inventory + Armour
    public void setArmour(ItemStack playerArmourStack) {
        if(!this.items.getStack(0).isEmpty()) {
            // TODO: Give player armour
        }
        Item armour = playerArmourStack.getItem();
        ItemStack myArmourStack = new ItemStack(armour);
        this.items.setStack(0, myArmourStack);
        this.tellOwner(Text.of(":) I have ").copy().append(this.items.getStack(0).getName()));
    }
    /**
     * Return true if the item is dolphin armour.
     */
    public boolean isArmourItem(ItemStack stack) {
        return ARMOUR_INGREDIENT.test(stack);
    }

    // Rideable
    /**
     * Dolphins can be ridden in water
     * @return true
     */
    public boolean canBeRiddenInWater() {
        return true;
    }

    /**
     * Return the PrimaryPassenger with movement control as a LivingEntity
     * @return nullable LivingEntity passenger
     */
    @Nullable
    public LivingEntity getPrimaryPassenger() {
        Entity passenger = super.getFirstPassenger();
        if(this.isSaddled()) { // Therefore, passenger has movement control
            if (passenger instanceof LivingEntity) { // Casting to LivingEntity
                return (LivingEntity) passenger;
            }
        }
        return null;
    }


    public boolean riddenJumpInProgress = false;
    public void stopRiddenJump() {
        this.riddenJumpInProgress = false;
    }
    public void startRiddenJump() {
        // Jump - now autonomous
        if(!this.riddenJumpInProgress) {
            this.riddenJumpInProgress = true;
            DolphinJumpGoal jumpGoal = new DolphinJumpGoal(this, 100);
            jumpGoal.start();
        }
    }
    public void travel(Vec3d movementInput) {
        LivingEntity passenger = this.getPrimaryPassenger();
        if(this.hasPassengers() && passenger != null) {
            if(!this.isSubmergedInWater() || this.riddenJumpInProgress) { // Stopped by jumpGoal
                // TODO: Don't begin riddenjump if in progress
                this.startRiddenJump();
                super.travel(movementInput);
            } else {
                // Allow riding
                // Rotate the same as passenger
                this.setYaw(passenger.getYaw());
                this.setPitch(passenger.getPitch());
                this.setRotation(this.getYaw(), this.getPitch());

                // Travel, using player's jump/float y velocity
                Vec3d passengerVelocity = passenger.getVelocity();
                double yVel = passengerVelocity.y;

                if (passenger.getPitch() > 30 && yVel < 0) {
                    // Looking down; sinking
                    yVel *= 10;
                }
                this.setVelocity(this.getVelocity().x, yVel, this.getVelocity().z);
                // Use player's riding speed to travel
                double SPEED_BOOST = 1.5;
                this.setMovementSpeed(passenger.getMovementSpeed());
                super.travel(new Vec3d(passenger.sidewaysSpeed * SPEED_BOOST, passenger.upwardSpeed, passenger.forwardSpeed * SPEED_BOOST));
                // Transfer jumping as well with yVel

                this.tryCheckBlockCollision();
            }
        } else {
            super.travel(movementInput);
        }
    }
}
