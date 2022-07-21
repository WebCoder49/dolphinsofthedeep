package io.github.webcoder49.dolphinsofthedeep.entities.dolphins;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.WolfEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class DolphinEntityModel extends EntityModel<DolphinEntity> {

    // Declare parts
    private final ModelPart body;
    private final ModelPart leftFin;
    private final ModelPart rightFin;
//    private final ModelPart dorsalFin;
    private final ModelPart nose;

    public DolphinEntityModel(ModelPart modelPart) {
        // Initialise parts
        this.body = modelPart.getChild(EntityModelPartNames.BODY);
        this.leftFin = modelPart.getChild(EntityModelPartNames.LEFT_FIN);
        this.rightFin = modelPart.getChild(EntityModelPartNames.RIGHT_FIN);
//        this.dorsalFin = modelPart.getChild(EntityModelPartNames.BACK_FIN);
        this.nose = modelPart.getChild(EntityModelPartNames.NOSE);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(
                EntityModelPartNames.BODY,
                ModelPartBuilder.create()
                        .uv(0, 0) // Texture location
                        .cuboid(-6F, 12F, -6F, 12F, 12F, 28F) // Offset + Size
                , ModelTransform.pivot(0F, 0F, 0F)
        );
        modelPartData.addChild(
                EntityModelPartNames.NOSE,
                ModelPartBuilder.create()
                        .uv(0, 0) // Texture location
                        .cuboid(-2F, 21F, -10F, 4F, 2F, 4F) // Offset + Size
                , ModelTransform.pivot(0F, 0F, 0F)
        );
        modelPartData.addChild(
                EntityModelPartNames.LEFT_FIN,
                ModelPartBuilder.create()
                        .uv(0, 7) // Texture location
                        .cuboid(-12F, 21F, -4F, 6F, 2F, 6F) // Offset + Size
                , ModelTransform.pivot(-6F, -4F, -7F)
        );
        modelPartData.addChild(
                EntityModelPartNames.RIGHT_FIN,
                ModelPartBuilder.create()
                        .uv(0, 16) // Texture location
                        .cuboid(6F, 21F, -4F, 6F, 2F, 6F) // Offset + Size
                , ModelTransform.pivot(6F, -4F, -7F)
        );

        return TexturedModelData.of(modelData, 80, 40); // 80x40px texture file
    }

    @Override
    public void setAngles(DolphinEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        // Animate transforms
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        // body
        ImmutableList.of(this.body).forEach((modelRenderer) -> { // Render each part
            modelRenderer.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        });
        // nose
        ImmutableList.of(this.nose).forEach((modelRenderer) -> { // Render each part
            modelRenderer.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        });
        // TODO: Add fins
    }
}
