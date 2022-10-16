package io.github.webcoder49.dolphinsofthedeep.entity.dolphin;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import io.github.webcoder49.dolphinsofthedeep.entity.component.tamable.FollowOwnerGoal;
import io.github.webcoder49.dolphinsofthedeep.entity.component.tamable.TameableComponent;
import io.github.webcoder49.dolphinsofthedeep.entity.interfacecomponent.conversation.Conversation;
import io.github.webcoder49.dolphinsofthedeep.entity.interfacecomponent.conversation.ConversationInterface;
import io.github.webcoder49.dolphinsofthedeep.entity.interfacecomponent.conversation.DelayedMessage;
import io.github.webcoder49.dolphinsofthedeep.entity.interfacecomponent.tieredgift.TieredGiftInterface;
import io.github.webcoder49.dolphinsofthedeep.item.DolphinArmour;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtInt;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

import static io.github.webcoder49.dolphinsofthedeep.util.Items.useUpItem;

/**
 * Extends vanilla dolphin to add vanilla functionality; extra modded functionality added here
 */
public class DolphinEntity extends net.minecraft.entity.passive.DolphinEntity implements Tameable, Saddleable, ConversationInterface, TieredGiftInterface {

    // Components
    private final SaddledComponent saddledComponent;
    private final TameableComponent tameableComponent;

    private final static Ingredient TAMING_INGREDIENT;
    private final static Ingredient ARMOUR_INGREDIENT;

    @Nullable
    protected EntityAttributeModifier dolphinArmourBonus;

    protected static final TrackedData<Boolean> IS_TAMED;
    protected static final TrackedData<Optional<UUID>> OWNER_UUID;
    protected @Nullable FollowMobGoal followOwnerGoal = null;

    private static final TrackedData<Boolean> SADDLED;
    private static final TrackedData<Integer> BOOST_TIME;

    public int giftXp = 0;
    public double lastGiftDay = -1;

    /**
     * Constructor for a dolphin
     */
    public DolphinEntity(EntityType<? extends net.minecraft.entity.passive.DolphinEntity> entityType, World world) {
        super(entityType, world);
        this.saddledComponent = new SaddledComponent(this.dataTracker, BOOST_TIME, SADDLED);
        this.tameableComponent = new TameableComponent(this.dataTracker, IS_TAMED, OWNER_UUID);
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
        this.tameableComponent.writeNbt(nbt);
        this.saddledComponent.writeNbt(nbt);
        // Dolphin armour
        DolphinArmour armour = this.getArmour();
        if(armour != null) {
            nbt.put("ArmorItem", armour.getDefaultStack().writeNbt(new NbtCompound())); // For consistency
        }
        nbt.put("GiftXP", NbtInt.of(this.giftXp)); // For consistency

        // Conversation
        if(this.getOwner() != null) {
            this.conversation = new Conversation();
        }
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.tameableComponent.readNbt(nbt, this.getServer());
        this.saddledComponent.readNbt(nbt);
        // Dolphin armour
        if(nbt.contains("ArmorItem", 10)) { // TYPE==COMPOUND
            this.setArmour(ItemStack.fromNbt(nbt.getCompound("ArmorItem")));
        }
    }

    // AI
    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(10, new FollowOwnerGoal(this, 1.0, 1.0, 1024.0));
    }

    // Attributes
    public static DefaultAttributeContainer.Builder createDolphinAttributes() {
        return net.minecraft.entity.passive.DolphinEntity.createDolphinAttributes().add(DolphinAttributes.DOLPHIN_TAMING_DIFFICULTY, 2).add(DolphinAttributes.DOLPHIN_GIFT_MIN_QUALITY, 0.0D).add(DolphinAttributes.DOLPHIN_CHAT_CHANCE, 0.01D);
    }

    // Events
    @Override
    public void tick() {
        // Components
        /* Chat */
        if(!this.world.isClient) {
//            if(world.getGameRules().getBoolean(DolphinsOfTheDeep.DOLPHIN_CHAT)) { - TODO: Add
                if(this.conversation != null) {
                    if(this.getPrimaryPassenger() == this.getOwner()) {
                        if(Math.random() < (double)this.getAttributeValue(DolphinAttributes.DOLPHIN_CHAT_CHANCE) && this.conversation.isFree()) {
                            this.conversation.addConversation("hello");
                            // TODO: Check+Fix
                        }
                    }
                    this.conversationTick(this.conversation);
                }
//            }
        }
        super.tick();
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        DolphinsOfTheDeep.log(Level.INFO, "Interacted.");
        // Get item in hand
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();

        if (this.world.isClient) {
            // Just display to player; not whole server
            if(
                    (this.getOwner() == null && this.isTamingItem(itemStack))
                            || (this.getOwner() == player && this.isDolphinArmour(itemStack))
            ) {
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.FAIL;
            }
        } else {
            if(this.getOwner() == null && world.getGameRules().getBoolean(DolphinsOfTheDeep.BEFRIEND_DOLPHINS)) { // Untamed - try to tame
                if (item.isFood() && this.isTamingItem(itemStack)) {
                    DolphinsOfTheDeep.log(Level.INFO, "Taming.");
                    if(this.random.nextInt((int)this.getAttributeValue(DolphinAttributes.DOLPHIN_TAMING_DIFFICULTY)) == 0) { // TODO: TEST
                        this.setOwner(player);
                        this.tellOwner(this.getTranslatedText("tamed", player.getName()));
                        DolphinsOfTheDeep.log(Level.INFO, "Tamed.");
                        this.world.addParticle(ParticleTypes.BUBBLE, this.getParticleX(1.0), this.getY()+1.0, this.getParticleZ(1.0), 0.0, 1.0, 0.0);
                    }

                    this.playSound(SoundEvents.ENTITY_DOLPHIN_EAT, 1.0F, 1.0F);
                    useUpItem(itemStack, player);

                    return ActionResult.CONSUME;
                } else {
                    DolphinsOfTheDeep.log(Level.WARN, "Not taming item.");
                    return ActionResult.FAIL;
                }
            } else if(this.getOwner() == player && world.getGameRules().getBoolean(DolphinsOfTheDeep.BEFRIEND_DOLPHINS)) { // Tamed by player.
                if(itemStack.isOf(DolphinsOfTheDeep.DOLPHIN_SADDLE) && world.getGameRules().getBoolean(DolphinsOfTheDeep.RIDE_DOLPHINS)) { // Saddle
                    if(player instanceof ServerPlayerEntity) {
                        Criteria.USING_ITEM.trigger((ServerPlayerEntity) player, itemStack);
                    }
                    itemStack.useOnEntity(player, this, hand);
                    useUpItem(itemStack, player);

                    if(this.shouldGiveGift()) {
                        this.giveGift(this.getAttributeBaseValue(DolphinAttributes.DOLPHIN_GIFT_MIN_QUALITY), this.giftXp, this.conversation);
                        giftXp++;
                    }

                    this.saddle(SoundCategory.NEUTRAL);
                    player.startRiding(this);

                } else if(this.isDolphinArmour(itemStack)) { // Armour
                    itemStack.useOnEntity(player, this, hand);
                    this.setArmour(itemStack);
                    useUpItem(itemStack, player);
                }
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
        this.tameableComponent.setTamed(tamed);
    }

    public boolean getTamed() {
        return this.tameableComponent.getTamed();
    }

    /**
     * Get the UUID of the owner, or null if not tamed
     * @return Owner UUID
     */
    @Nullable
    public UUID getOwnerUuid() {
        // Use DataTracker
        return this.tameableComponent.getOwnerUuid();
    }

    /**
     * Set the UUID of the owner, or null if not tamed
     * @param uuid UUID of the owner
     */
    public void setOwnerUuid(@Nullable UUID uuid) {
        this.tameableComponent.setOwnerUuid(uuid);
    }

    /**
     * Set the username of the owner, or null if not tamed
     * @param player username of the owner
     */
    public void setOwner(PlayerEntity player) {
        this.conversation = new Conversation();
        this.tameableComponent.setOwner(player);
    }

    /**
     * Get the username of the owner, or null if not tamed
     */
    @Nullable
    public LivingEntity getOwner() {
        return this.tameableComponent.getOwner(this.world);
    }

    /**
     * Return true if the item can be used to tame a dolphin.
     */
    public boolean isTamingItem(ItemStack stack) {
        return TAMING_INGREDIENT.test(stack);
    }

    /* Riding */
    // Saddleable
    static {
        SADDLED = DataTracker.registerData(DolphinEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        BOOST_TIME = DataTracker.registerData(DolphinEntity.class, TrackedDataHandlerRegistry.INTEGER);
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
    static {
        ARMOUR_INGREDIENT = Ingredient.ofItems(DolphinsOfTheDeep.LEATHER_DOLPHIN_ARMOUR, DolphinsOfTheDeep.IRON_DOLPHIN_ARMOUR, DolphinsOfTheDeep.GOLD_DOLPHIN_ARMOUR, DolphinsOfTheDeep.DIAMOND_DOLPHIN_ARMOUR, DolphinsOfTheDeep.NETHERITE_DOLPHIN_ARMOUR);
    }

    /**
     * Equip a piece of dolphin armour from the player.
     * @param armourStack The player's stack of dolphin armour
     */
    public void setArmour(ItemStack armourStack) {
        // Drop old armour
        ItemStack oldArmourStack = this.getArmourStack();
        this.dropStack(oldArmourStack);
        this.equipArmour(armourStack);

        if(!this.world.isClient()) {
            // Update attribute based on armour
            if(this.dolphinArmourBonus != null) { // Remove past bonus
                this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).removeModifier(this.dolphinArmourBonus);
            }
            int bonus = ((DolphinArmour)armourStack.getItem()).getArmourBonus();
            // Add new bonus
            this.dolphinArmourBonus = new EntityAttributeModifier("Dolphin armour bonus", (double)bonus, EntityAttributeModifier.Operation.ADDITION);
            this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).addTemporaryModifier(this.dolphinArmourBonus);
        }
    }

    /**
     * LOW-LEVEL: Equip a piece of dolphin armour visually using the CHEST EquipmentSlot. Use {@code setArmour} for complete equipping.
     * @param armourStack ItemStack of dolphin armour
     */
    protected void equipArmour(ItemStack armourStack) {
        // Add armour to CHEST slot
        this.equipStack(EquipmentSlot.CHEST, armourStack);
        // Don't drop on death
        this.setEquipmentDropChance(EquipmentSlot.CHEST, 0.0F);
    }

    /**
     * Get the dolphin armour this dolphin is wearing as a stack.
     */
    public ItemStack getArmourStack() {
        return this.getEquippedStack(EquipmentSlot.CHEST);
    }

    /**
     * Get the dolphin armour this dolphin is wearing as a DolphinArmour item.
     */
    @Nullable
    public DolphinArmour getArmour() {
        Item armourItem = this.getArmourStack().getItem();
        if(armourItem instanceof DolphinArmour) {
            return (DolphinArmour) armourItem;
        }
        return null;
    }

    /**
     * Return true if the item is dolphin armour.
     */
    public boolean isDolphinArmour(ItemStack stack) {
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
     * Return the first passenger with movement control as a LivingEntity
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

    public void travel(Vec3d movementInput) {
        LivingEntity passenger = this.getPrimaryPassenger();
        if(this.hasPassengers() && passenger != null && this.isTouchingWater()) {
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

            // Transfer jumping as well with yVel
            this.setVelocity(this.getVelocity().x, yVel, this.getVelocity().z);
            // Use player's riding speed to travel
            this.setMovementSpeed(passenger.getMovementSpeed());
            super.travel(new Vec3d(passenger.sidewaysSpeed, passenger.upwardSpeed, passenger.forwardSpeed));

            this.tryCheckBlockCollision();
        } else {
            super.travel(movementInput);
        }
    }

    /* Gift giving - see TieredGiftInterface */
    public boolean shouldGiveGift() {
        if(!world.getGameRules().getBoolean(DolphinsOfTheDeep.DOLPHIN_GIFTS)) {
            return false;
        }
        // TODO: Change to add only once a day
        long dayNo = this.getWorld().getLunarTime() / 24000;
        // Already given today
        if(dayNo == lastGiftDay) {
            return false;
        }
        this.lastGiftDay = dayNo;
        return true;
    }

    /* Conversation - see ConversationInterface */

    @Nullable Conversation conversation = null;

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
        if(!this.world.isClient && this.getOwner() != null) {
            // Send death message to owner, formatted
            this.tellOwner(damageSource.getDeathMessage(this));
        }
    }
}
