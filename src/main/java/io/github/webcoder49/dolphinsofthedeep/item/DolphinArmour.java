package io.github.webcoder49.dolphinsofthedeep.item;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class DolphinArmour extends Item {
    int armourBonus;
    private Identifier entityTexture;

    /**
     * Create a dolphin armour item
     * @param armourBonus The protection value of the armour on a dolphin
     * @param settings The item's settings
     */
    public DolphinArmour(String name, int armourBonus, Settings settings) {
        super(settings);
        this.armourBonus = armourBonus;
        this.entityTexture = new Identifier(DolphinsOfTheDeep.MOD_ID, "textures/entity/dolphin/armour/" + name + ".png");
    }

    public int getArmourBonus() {
        return armourBonus;
    }

    public Identifier getEntityTexture() {
        return this.entityTexture;
    }
}