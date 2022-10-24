package io.github.webcoder49.dolphinsofthedeep.gui.dolphinInventory;

import io.github.webcoder49.dolphinsofthedeep.entity.dolphin.DolphinEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class DolphinInventoryScreenHandlerFactory implements NamedScreenHandlerFactory {

    private final DolphinEntity dolphin;

    /**
     * Create a factory for a player viewing a dolphin's inventory
     * @param dolphin the dolphin whose inventory this is
     */
    public DolphinInventoryScreenHandlerFactory(DolphinEntity dolphin) {
        this.dolphin = dolphin;
    }

    /**
     * Get display name from dolphin
     * @return
     */
    @Override
    public Text getDisplayName() {
        return dolphin.getDisplayName();
    }


    /**
     * Create a screenHandler
     * @param syncId
     * @param inv
     * @param player
     * @return
     */
    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new DolphinInventoryScreenHandler(syncId, inv, player, dolphin);
    }
}
