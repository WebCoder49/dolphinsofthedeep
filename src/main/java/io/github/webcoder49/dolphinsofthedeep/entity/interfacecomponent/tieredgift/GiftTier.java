package io.github.webcoder49.dolphinsofthedeep.entity.interfacecomponent.tieredgift;

import net.minecraft.util.Formatting;

/**
 * Tiers of gifts, with its name, display colour, and probability statistics. Use {@code GiftTierSelector} to choose a random tier from a list.
 */
 public enum GiftTier {
    COMMON("common", Formatting.LIGHT_PURPLE),
    RARE("rare", Formatting.RED),
    EPIC("epic", Formatting.AQUA),
    LEGENDARY("epic", Formatting.GOLD);

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

    public double getProbability(double experience) {
        // Get probability of this tier based on experience
        return max * (
                1 - Math.pow(this.base, (double) experience)
                );
    }
}
