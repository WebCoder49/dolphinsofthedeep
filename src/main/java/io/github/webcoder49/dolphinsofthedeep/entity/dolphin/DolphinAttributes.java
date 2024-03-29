package io.github.webcoder49.dolphinsofthedeep.entity.dolphin;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DolphinAttributes {
    /** 1 in difficulty chance to tame when given fish. */
    public static final EntityAttribute DOLPHIN_TAMING_DIFFICULTY = register("dolphin.taming_difficulty", new ClampedEntityAttribute("attribute.name.dolphin.taming_difficulty", 2, 1, 5));
    /** Minimum number when choosing gift - higher is better and cuts off some of lowest */
    public static final EntityAttribute DOLPHIN_GIFT_MIN_QUALITY = register("dolphin.gift_min_quality", new ClampedEntityAttribute("attribute.name.dolphin.gift_min_quality", 0.0D, 0.0D, 1.0D));

    private static EntityAttribute register(String path, EntityAttribute attribute) {
        return (EntityAttribute)Registry.register(Registry.ATTRIBUTE, Identifier.of(DolphinsOfTheDeep.MOD_ID, path), attribute);
    }
}
