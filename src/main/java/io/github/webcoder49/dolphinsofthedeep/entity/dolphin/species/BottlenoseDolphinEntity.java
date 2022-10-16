package io.github.webcoder49.dolphinsofthedeep.entity.dolphin.species;

import io.github.webcoder49.dolphinsofthedeep.entity.dolphin.DolphinAttributes;
import io.github.webcoder49.dolphinsofthedeep.entity.dolphin.DolphinEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.world.World;

public class BottlenoseDolphinEntity extends DolphinEntity {
    public BottlenoseDolphinEntity(EntityType<? extends net.minecraft.entity.passive.DolphinEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createBottlenoseAttributes() {
        // Slightly better gifts than common
        return DolphinEntity.createDolphinAttributes()
                .add(DolphinAttributes.DOLPHIN_TAMING_DIFFICULTY, 3)
                .add(DolphinAttributes.DOLPHIN_GIFT_MIN_QUALITY, 0.1D)
                .add(DolphinAttributes.DOLPHIN_CHAT_CHANCE, 0.01D);
    }
}
