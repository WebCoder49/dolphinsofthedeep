package io.github.webcoder49.dolphinsofthedeep.entity.dolphin.species;

import io.github.webcoder49.dolphinsofthedeep.entity.dolphin.DolphinAttributes;
import io.github.webcoder49.dolphinsofthedeep.entity.dolphin.DolphinEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.world.World;

public class CommonDolphinEntity extends DolphinEntity {
    public CommonDolphinEntity(EntityType<? extends net.minecraft.entity.passive.DolphinEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createCommonAttributes() {
        // Defaults
        return DolphinEntity.createDolphinAttributes()
                .add(DolphinAttributes.DOLPHIN_TAMING_DIFFICULTY, 2)
                .add(DolphinAttributes.DOLPHIN_GIFT_MIN_QUALITY, 0.0D)
                .add(DolphinAttributes.DOLPHIN_CHAT_CHANCE, 0.005D)
                .add(DolphinAttributes.DOLPHIN_FRIENDLY_CHANCE, 0.3D);
    }
}
