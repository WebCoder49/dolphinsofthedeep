package io.github.webcoder49.dolphinsofthedeep.entities.dolphins;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;

public class DolphinEntity extends PathAwareEntity {
    public DolphinEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }
}
