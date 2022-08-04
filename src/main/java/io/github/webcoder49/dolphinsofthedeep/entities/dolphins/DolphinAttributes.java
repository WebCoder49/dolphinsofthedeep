package io.github.webcoder49.dolphinsofthedeep.entities.dolphins;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DolphinAttributes {
    // Speed boost when riding
    public static final EntityAttribute DOLPHIN_SPEED_BOOST = register(new Identifier(DolphinsOfTheDeep.MOD_ID, "dolphin.speed_boost"), (new ClampedEntityAttribute("attribute.name.dolphin.speed_boost", 1.0D, 0.0D, 10.0D)).setTracked(true));

    /**
     * Register and return an entity attribute
     * @param id The string identifier
     * @param attr
     * @return
     */
    private static EntityAttribute register(Identifier id, EntityAttribute attr) {
        return Registry.register(Registry.ATTRIBUTE, id, attr);
    }
}
