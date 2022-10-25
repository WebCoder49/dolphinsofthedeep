package io.github.webcoder49.dolphinsofthedeep.mixinInterfaces.gui;

import io.github.webcoder49.dolphinsofthedeep.entity.dolphin.DolphinEntity;
import net.minecraft.inventory.Inventory;

public interface ServerOpenDolphinInventoryInterface {
    void openDolphinInventory(DolphinEntity dolphin, Inventory inventory);
}
