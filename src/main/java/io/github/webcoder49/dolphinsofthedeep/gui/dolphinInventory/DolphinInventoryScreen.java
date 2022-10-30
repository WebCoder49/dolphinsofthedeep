package io.github.webcoder49.dolphinsofthedeep.gui.dolphinInventory;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import io.github.webcoder49.dolphinsofthedeep.entity.dolphin.DolphinEntity;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class DolphinInventoryScreen extends HandledScreen<DolphinInventoryScreenHandler> {
    // GUI Texture path
    private static final Identifier TEXTURE = new Identifier("dolphinsofthedeep", "textures/gui/container/dolphin_inventory.png");
    private DolphinEntity dolphin;

    private TextFieldWidget nameBox;

    public DolphinInventoryScreen(DolphinInventoryScreenHandler handler, PlayerInventory playerInventory, DolphinEntity dolphin) {
        super(handler, playerInventory, dolphin.getDisplayName());
        this.dolphin = dolphin;
    }

    public DolphinInventoryScreen(DolphinInventoryScreenHandler handler, PlayerInventory playerInventory, Text title) {
        super(handler, playerInventory, title);
        this.dolphin = null;
    }

    /** Set up naming box
     *
     */
    protected void init() {
        super.init();
        this.nameBox = new TextFieldWidget(this.textRenderer, ((this.width - this.backgroundWidth) / 2)+80+4, ((this.height - this.backgroundHeight) / 2)+18+4+23+1, 82, 9, Text.of("Name me!"));
        this.nameBox.setMaxLength(50);
        this.nameBox.setDrawsBackground(false);
        this.nameBox.setVisible(true);
        this.nameBox.setEditableColor(16777215);

        if(this.dolphin.getCustomName() != null) {
            this.nameBox.setText(this.dolphin.getCustomName().getString());
        }
        this.addSelectableChild(this.nameBox);
    }

    /**
     * Refresh search box
     * @param client
     */
    public void resize(MinecraftClient client) {
        this.init();
    }

    /**
     * Rename dolphin if in search box
     * @param keyCode
     * @param scanCode
     * @param modifiers
     * @return
     */
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        this.nameBox.keyPressed(keyCode, scanCode, modifiers);
        // Success at entering in box
        if(!Objects.equals(this.dolphin.getName().getString(), this.nameBox.getText())) {
            // Name on client
            this.dolphin.setCustomName(Text.of(this.nameBox.getText()));

            // Name on server
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeInt(this.dolphin.getId());
            buf.writeString(this.nameBox.getText());
            ClientPlayNetworking.send(DolphinsOfTheDeep.RENAME_ENTITY_PACKET_ID, buf);
        }

        // Already completed if box focused; else normal action
        return this.nameBox.isFocused() && keyCode != 256 || super.keyPressed(keyCode, scanCode, modifiers);
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

        this.nameBox.render(matrices, mouseX, mouseY, delta);
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

        String editedName = this.nameBox.getText();

        // Write statistics
        this.textRenderer.draw(matrices, Text.translatable(DolphinsOfTheDeep.MOD_ID+".stats.giftXp").append(Text.of(": ")).append(Text.of(String.valueOf(dolphin.giftXp)).getWithStyle(Style.EMPTY.withColor(Formatting.AQUA)).get(0)), (float)topLeftX+80+4, (float)topLeftY+18+4, 15658734);
        this.textRenderer.draw(matrices, Text.translatable(DolphinsOfTheDeep.MOD_ID+".stats.armour").append(Text.of(": ")).append(Text.of(String.valueOf(dolphin.getAttributeValue(EntityAttributes.GENERIC_ARMOR))).getWithStyle(Style.EMPTY.withColor(Formatting.RED)).get(0)), (float)topLeftX+80+4, (float)topLeftY+18+4+9+1, 15658734);
        // Name placeholder
        if(editedName == "") {
            this.textRenderer.draw(matrices, Text.of("Click to name"), (float)topLeftX+80+4, (float)topLeftY+18+4+23+1, 2171169);
        }
        this.textRenderer.draw(matrices, Text.translatable(dolphin.getType().getTranslationKey()+".latin").getWithStyle(Style.EMPTY.withColor(Formatting.GRAY).withItalic(true)).get(0).copy().append(" ").append(Text.translatable(dolphin.getType().getTranslationKey() + ".status").getWithStyle(Style.EMPTY.withColor(Formatting.DARK_RED)).get(0)), (float)topLeftX+80+4, (float)topLeftY+18+4+36+1, 15658734);
    }
}
