package io.github.webcoder49.dolphinsofthedeep.gui.dolphinInventory;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.webcoder49.dolphinsofthedeep.entity.dolphin.DolphinEntity;
import io.github.webcoder49.dolphinsofthedeep.item.DolphinSaddle;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class DolphinInventoryScreen extends HandledScreen<DolphinInventoryScreenHandler> {
    // GUI Texture path
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/generic_54.png");

    public DolphinInventoryScreen(DolphinInventoryScreenHandler handler, PlayerInventory playerInventory, Text title) {
        super(handler, playerInventory, title);
        this.backgroundHeight = 114 + 6 * 18;
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

        this.textRenderer.draw(matrices, this.title, 8.0F, 6.0F, 4210752);
        this.textRenderer.draw(matrices, this.playerInventoryTitle, 8.0F, (float)(this.backgroundHeight - 96 + 2), 4210752);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        // Prepare rendering
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        this.client.getTextureManager().bindTexture(TEXTURE);
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, 6 * 18 + 17);
        this.drawTexture(matrices, i, j + 6 * 18 + 17, 0, 126, this.backgroundWidth, 96);
    }
}
