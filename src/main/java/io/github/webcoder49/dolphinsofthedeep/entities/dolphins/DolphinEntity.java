package io.github.webcoder49.dolphinsofthedeep.entities.dolphins;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

/**
 * Extends vanilla dolphin to add vanilla functionality; extra modded functionality added here
 */
public class DolphinEntity extends net.minecraft.entity.passive.DolphinEntity {
    public DolphinEntity(EntityType<? extends net.minecraft.entity.passive.DolphinEntity> entityType, World world) {
        super(entityType, world);
    }
}
