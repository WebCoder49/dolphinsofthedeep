package io.github.webcoder49.dolphinsofthedeep.entities.dolphins;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;


/** Extends minecraft's DolphinEntity
 * but adds different skins and functionality */
public class DolphinEntity extends PathAwareEntity {
    public DolphinEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }
}
