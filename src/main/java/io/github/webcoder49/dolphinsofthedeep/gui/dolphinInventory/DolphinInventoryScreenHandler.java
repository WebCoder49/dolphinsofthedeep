package io.github.webcoder49.dolphinsofthedeep.gui.dolphinInventory;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import io.github.webcoder49.dolphinsofthedeep.entity.dolphin.DolphinEntity;
import net.minecraft.client.gui.screen.ingame.Generic3x3ContainerScreen;
import net.minecraft.client.gui.screen.ingame.HorseScreen;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.HorseScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

public class DolphinInventoryScreenHandler extends ScreenHandler {
    public final DolphinEntity dolphin;
    public static final int INVENTORY_SIZE = 2;

    //TODO: Add needed constructors - https://github.com/FabricMC/fabric/blob/1.19.1/fabric-screen-handler-api-v1/src/testmod/java/net/fabricmc/fabric/test/screenhandler/screen/BagScreenHandler.java
    public DolphinInventoryScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, null, null);
    }

    public DolphinInventoryScreenHandler(int syncId, PlayerInventory playerInventory, PlayerEntity player, DolphinEntity dolphin) {
        super(DolphinsOfTheDeep.DOLPHIN_INVENTORY_SCREEN_HANDLER, syncId);

        this.dolphin = dolphin;
        checkSize(dolphin.inventory, INVENTORY_SIZE);
        dolphin.inventory.onOpen(player);

        // GUI slots
        // Dolphin inventory
        // Saddle
        this.addSlot(new Slot(dolphin.inventory, 0, 8, 18) {
            public boolean canInsert(ItemStack stack) {
                return DolphinEntity.SADDLE_INGREDIENT.test(stack) && !this.hasStack() && dolphin.canBeSaddled();
            }

            public boolean isEnabled() {
                return dolphin.canBeSaddled();
            }
        });
        // Armour
        this.addSlot(new Slot(dolphin.inventory, 1, 8, 36) {
            public boolean canInsert(ItemStack stack) {
                return DolphinEntity.isDolphinArmour(stack);
            }

            public boolean isEnabled() {
                return dolphin.canBeSaddled();
            }

            public int getMaxItemCount() {
                return 1;
            }
        });

        // Player Inventory (27 storage + 9 hotbar)
        int i;
        int j;
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, i * 9 + j + 9, 8 + j * 18, 18 + i * 18 + 103 + 18));
            }
        }


        for (j = 0; j < 9; j++) {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 18 + 161 + 18));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.dolphin.getOwner() == player;
    }
    // Shift + Player Inv Slot - https://fabricmc.net/wiki/tutorial:containers
    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < INVENTORY_SIZE) {
                // Start and end indexes
                if (!this.insertItem(originalStack, INVENTORY_SIZE, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, INVENTORY_SIZE, false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack; // Copy items from slot after transfer
    }
}
