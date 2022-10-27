package io.github.webcoder49.dolphinsofthedeep.gui.dolphinInventory;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import io.github.webcoder49.dolphinsofthedeep.entity.dolphin.DolphinAttributes;
import io.github.webcoder49.dolphinsofthedeep.entity.dolphin.DolphinEntity;
import io.github.webcoder49.dolphinsofthedeep.item.DolphinSaddle;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HorseScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.realms.gui.screen.RealmsSettingsScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.sound.AmbientSoundLoops;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.HorseScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.core.jmx.Server;

import java.awt.*;

public class DolphinInventoryScreen extends HandledScreen<DolphinInventoryScreenHandler> {
    // GUI Texture path
    private static final Identifier TEXTURE = new Identifier("dolphinsofthedeep", "textures/gui/container/dolphin_inventory.png");
    private DolphinEntity dolphin;

    public DolphinInventoryScreen(DolphinInventoryScreenHandler handler, PlayerInventory playerInventory, DolphinEntity dolphin) {
        super(handler, playerInventory, dolphin.getDisplayName());
        this.dolphin = dolphin;
    }

    public DolphinInventoryScreen(DolphinInventoryScreenHandler handler, PlayerInventory playerInventory, Text title) {
        super(handler, playerInventory, title);
        this.dolphin = null;
    }

    /**
     * Render the foreground of the inventory - text titles
     * @param matrices
     * @param mouseX
     * @param mouseY
     */
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);

//        this.textRenderer.draw(matrices, this.title, 8.0F, 6.0F, 4210752);
//        this.textRenderer.draw(matrices, this.playerInventoryTitle, 8.0F, (float)(this.backgroundHeight - 96 + 2), 4210752);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        // Prepare rendering
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        this.client.getTextureManager().bindTexture(TEXTURE);
        int topLeftX = (this.width - this.backgroundWidth) / 2;
        int topLeftY = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrices, topLeftX, topLeftY, 0, 0, this.backgroundWidth, this.backgroundHeight);
        this.drawTexture(matrices, topLeftX + 7, topLeftY + 35 - 18, 18, this.backgroundHeight, 18, 18); // Saddle - immediately under "background height" in texture file
        this.drawTexture(matrices, topLeftX + 7, topLeftY + 35, 0, this.backgroundHeight, 18, 18); // Armour

        // Draw dolphin preview
        InventoryScreen.drawEntity(topLeftX + 51, topLeftY + 60, 17, ((topLeftX+51)-mouseX)*2, (topLeftY+60)-mouseY, this.dolphin); // x*2 so moves horizontally a lot

        // Write statistics
        this.textRenderer.draw(matrices, Text.translatable(DolphinsOfTheDeep.MOD_ID+".stats.giftXp").append(Text.of(": ")).append(Text.of(String.valueOf(dolphin.giftXp)).getWithStyle(Style.EMPTY.withColor(Formatting.AQUA)).get(0)), (float)topLeftX+80+4, (float)topLeftY+18+4, 15658734);
        this.textRenderer.draw(matrices, Text.translatable(DolphinsOfTheDeep.MOD_ID+".stats.armour").append(Text.of(": ")).append(Text.of(String.valueOf(dolphin.getAttributeValue(EntityAttributes.GENERIC_ARMOR))).getWithStyle(Style.EMPTY.withColor(Formatting.RED)).get(0)), (float)topLeftX+80+4, (float)topLeftY+18+4+9+1, 15658734);

        this.textRenderer.draw(matrices, Text.translatable(DolphinsOfTheDeep.MOD_ID+".fundraising.inventory.0"), (float)topLeftX-96, (float)1, 16776960);
        this.textRenderer.draw(matrices, Text.translatable(DolphinsOfTheDeep.MOD_ID+".fundraising.inventory.1", this.title), (float)topLeftX-96, (float)10, 16776960);
        this.textRenderer.draw(matrices, Text.translatable(DolphinsOfTheDeep.MOD_ID+".fundraising.inventory.2", this.title), (float)topLeftX-96, (float)19, 16776960);
    }
}
