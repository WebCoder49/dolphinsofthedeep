package io.github.webcoder49.dolphinsofthedeep.entity.interfacecomponent.tieredgift;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Tiers of gifts, with their names, display colours, and probability statistics. Use {@code GiftTierSelector} to choose a random tier from a list.
 */
 public enum GiftTier {
    COMMON("common", Formatting.LIGHT_PURPLE),
    RARE("rare", Formatting.RED, 0.5, 0.75),
    EPIC("epic", Formatting.AQUA, 0.3, 0.95),
    LEGENDARY("legendary", Formatting.GOLD, 0.05, 0.98);
    // See .notes/gifts.xlsx

    private String name;
    public String getName() {
        return name;
    }

    private Formatting formatting;
    public Formatting getFormatting() {
        return formatting;
    }

    /**
     * Maximum probability
     */
    private double max;
    /**
     * max*(1-(base^num days))) = probability
     */
    private double base;

    private GiftTier(String name, Formatting formatting, double max, double base) {
      this.name = name;
      this.formatting = formatting;
      this.max = max;
      this.base = base;
    }
    private GiftTier(String name, Formatting formatting) {
       // No probability counter - remainder
       this(name, formatting, 1, 0);
    }

    /**
     * Return the probability of getting this gift tier for a certain XP.
     * @param experience the XP (works best as number of days; higher XP means higher chance of everything but common)
     */
    public double getProbability(double experience) {
        // Get probability of this tier based on experience
        return max * (
                1 - Math.pow(this.base, (double) experience)
                );
    }

    /**
     * Get a gift from the loot table for this tier.
     * @param after The Consumer to send the gift stacks to
     * @param world The world this is run in
     * @param player The player the gift is for
     * @param pos The position of the entity (used for randomness)
     */
    public void getGift(Consumer<ItemStack> after, World world, Entity player, Vec3d pos) {
        // TODO: Add loot table

        Identifier lootTableId = new Identifier(DolphinsOfTheDeep.MOD_ID, "dolphingift/" + this.name);
        LootTable lootTable = Objects.requireNonNull(world.getServer()).getLootManager().getTable(lootTableId);

        // Add parameters
        LootContext.Builder builder = new LootContext.Builder((ServerWorld) world)
                .parameter(LootContextParameters.ORIGIN, pos)
                .parameter(LootContextParameters.THIS_ENTITY, player);

        if(player instanceof PlayerEntity) {
            builder.luck(((PlayerEntity) player).getLuck());
        }

        // Build
        lootTable.generateLoot(builder.build(LootContextTypes.GIFT), after);
    }
}
