package io.github.webcoder49.dolphinsofthedeep.item;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class DolphinSaddle extends Item {
    public DolphinSaddle(Settings settings) {
        super(settings);
    }
    // Action processed in DolphinEntity

    public static Identifier getEntityTexture() {
        return new Identifier(DolphinsOfTheDeep.MOD_ID, "textures/entity/dolphin/armour/saddle.png");
    }
}