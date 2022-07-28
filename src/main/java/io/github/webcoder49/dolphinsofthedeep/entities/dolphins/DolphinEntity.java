package io.github.webcoder49.dolphinsofthedeep.entities.dolphins;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import io.github.webcoder49.dolphinsofthedeep.entities.components.TamableComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
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
            // Just display to player; not whole server
            if(this.getOwner() == null && this.isTamingItem(itemStack)) {
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

    // Rideable
    /**
     * Dolphins can be ridden in water
     * @return true
     */
    public boolean canBeRiddenInWater() {
        return true;
    }
}
