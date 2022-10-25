package io.github.webcoder49.dolphinsofthedeep.mixin.gui;


import com.mojang.authlib.GameProfile;
import io.github.webcoder49.dolphinsofthedeep.entity.dolphin.DolphinEntity;
import io.github.webcoder49.dolphinsofthedeep.mixinInterfaces.gui.ServerOpenDolphinInventoryInterface;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.network.packet.s2c.play.OpenHorseScreenS2CPacket;
import net.minecraft.screen.HorseScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Add a method to a ServerPlayerEntity to open a dolphin inventory
 */
@Mixin(ServerPlayerEntity.class)
// ServerOpenDolphinInventoryInterface is a "duck interface" - allows openDolphinInventory to be accessed
// Thanks to https://www.reddit.com/r/fabricmc/comments/mzg99m/
public abstract class ServerOpenDolphinInventory extends PlayerEntity
                    implements ServerOpenDolphinInventoryInterface {
    @Shadow protected abstract void onScreenHandlerOpened(ScreenHandler screenHandler);

    @Shadow private int screenHandlerSyncId;
    @Shadow public ServerPlayNetworkHandler networkHandler;

    @Shadow protected abstract void incrementScreenHandlerSyncId();

    @Shadow public abstract void closeHandledScreen();


    public ServerOpenDolphinInventory(World world, BlockPos pos, float yaw, GameProfile gameProfile, @Nullable PlayerPublicKey publicKey) {
        super(world, pos, yaw, gameProfile, publicKey);
    }

    public void openDolphinInventory(DolphinEntity dolphin,
                                     Inventory inventory) {
        dolphin.tellOwner(Text.of("DEBUG - Opening inv"));

        if(this.currentScreenHandler != this.playerScreenHandler) {
            // Close screen handler
            this.closeHandledScreen();
        }

        this.incrementScreenHandlerSyncId();


        // Spawn a random horse to hijack its inventory - TODO: Change to dolphin
        HorseEntity debuggingHorse = new HorseEntity(EntityType.HORSE, this.world);
        this.world.spawnEntity(debuggingHorse);

        this.networkHandler.sendPacket(new OpenHorseScreenS2CPacket(this.screenHandlerSyncId, inventory.size(), debuggingHorse.getId()));
        // ^ Tell the client to open the inventory
        this.currentScreenHandler = new HorseScreenHandler(this.screenHandlerSyncId, this.getInventory(), inventory, debuggingHorse);
        // ^ Set the screen handler on the server
        this.onScreenHandlerOpened(this.currentScreenHandler);
    }

    @Shadow
    @Override
    public boolean isSpectator() {
        return false;
    }

    @Shadow
    @Override
    public boolean isCreative() {
        return false;
    }
}
