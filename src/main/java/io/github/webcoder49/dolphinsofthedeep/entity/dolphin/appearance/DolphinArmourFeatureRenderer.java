package io.github.webcoder49.dolphinsofthedeep.entity.dolphin.appearance;

import io.github.webcoder49.dolphinsofthedeep.entity.dolphin.DolphinEntity;
import io.github.webcoder49.dolphinsofthedeep.item.DolphinArmour;
import io.github.webcoder49.dolphinsofthedeep.item.DolphinSaddle;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

public class DolphinArmourFeatureRenderer extends FeatureRenderer<DolphinEntity, DolphinEntityModel> {
    public DolphinArmourFeatureRenderer(FeatureRendererContext<DolphinEntity, DolphinEntityModel> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, DolphinEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        DolphinArmour armour = entity.getArmour();

        Identifier texture;
        if(armour != null) {
            texture = armour.getEntityTexture();
        } else if(entity.isSaddled()) {
            texture = DolphinSaddle.getEntityTexture();
        } else {
            return; // No armour
        }
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(texture)); // TODO: Change to armour-like + TEST
        this.getContextModel().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
    }
}
