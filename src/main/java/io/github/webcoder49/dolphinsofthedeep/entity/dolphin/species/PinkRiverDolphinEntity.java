package io.github.webcoder49.dolphinsofthedeep.entity.dolphin.species;

import io.github.webcoder49.dolphinsofthedeep.entity.dolphin.DolphinEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class PinkRiverDolphinEntity extends DolphinEntity {
    public PinkRiverDolphinEntity(EntityType<? extends net.minecraft.entity.passive.DolphinEntity> entityType, World world) {
        super(entityType, world);
    }

    // Species-specific
    private int TAMING_CHANCE = 4; // 1 in 4 chance - harder for rarer
}
