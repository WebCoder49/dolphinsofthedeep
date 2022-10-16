package io.github.webcoder49.dolphinsofthedeep.entity.dolphin.appearance;

import com.google.common.collect.ImmutableList;
import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import io.github.webcoder49.dolphinsofthedeep.entity.dolphin.DolphinEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import org.apache.logging.log4j.Level;

public class DolphinEntityModel extends EntityModel<DolphinEntity> {

    // Declare parts
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart leftFin;
    private final ModelPart rightFin;
    private final ModelPart dorsalFin;
    private final ModelPart tailFin;
    private final ModelPart nose;

    public DolphinEntityModel(ModelPart modelPart) {
        // Initialise parts
        this.head = modelPart.getChild(EntityModelPartNames.HEAD);
        this.body = modelPart.getChild(EntityModelPartNames.BODY);
        this.tail = modelPart.getChild(EntityModelPartNames.TAIL);
        // Fins
        this.leftFin = modelPart.getChild(EntityModelPartNames.LEFT_FIN);
        this.rightFin = modelPart.getChild(EntityModelPartNames.RIGHT_FIN);
        this.dorsalFin = modelPart.getChild(EntityModelPartNames.BACK_FIN);
        this.tailFin = modelPart.getChild(EntityModelPartNames.TAIL_FIN);
        this.nose = modelPart.getChild(EntityModelPartNames.NOSE);
    }

    public static TexturedModelData getTexturedModelData(boolean long_nose) {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        // Main body (front > back)
        modelPartData.addChild(
                EntityModelPartNames.HEAD,
                ModelPartBuilder.create()
                        .uv(0, 0) // Texture location
                        .cuboid(-6F, 12F, -6F, 12F, 12F, 28F) // Offset + Size
                , ModelTransform.pivot(0F, 0F, -8F)
        );
        modelPartData.addChild(
                EntityModelPartNames.BODY,
                ModelPartBuilder.create()
                        .uv(0, 41) // Texture location
                        .cuboid(-4F, 16F, 22F, 8F, 8F, 4F) // Offset + Size
                , ModelTransform.pivot(0F, 0F, -8F)
        );
        modelPartData.addChild(
                EntityModelPartNames.TAIL,
                ModelPartBuilder.create()
                        .uv(0, 54) // Texture location
                        .cuboid(-4F, 20F, 26F, 8F, 4F, 4F) // Offset + Size
                , ModelTransform.pivot(0F, 0F, -8F)
        );
        // Extra parts
        if(long_nose) {
            modelPartData.addChild(
                    EntityModelPartNames.NOSE,
                    ModelPartBuilder.create()
                            .uv(57, 11) // Texture location
                            .cuboid(-2F, 21F, -14F, 4F, 2F, 8F) // Offset + Size
                    , ModelTransform.pivot(0F, 0F, -8F)
            );
        } else {
            modelPartData.addChild(
                    EntityModelPartNames.NOSE,
                    ModelPartBuilder.create()
                            .uv(0, 0) // Texture location
                            .cuboid(-2F, 21F, -10F, 4F, 2F, 4F) // Offset + Size
                    , ModelTransform.pivot(0F, 0F, -8F)
            );
        }
        modelPartData.addChild(
                EntityModelPartNames.RIGHT_FIN,
                ModelPartBuilder.create()
                        .uv(0, 7) // Texture location
                        .cuboid(-6F, -3F, 4F, 6F, 2F, 6F) // Offset + Size
                , ModelTransform.pivot(-6F, 25F, -15F)
        );
        modelPartData.addChild(
                EntityModelPartNames.LEFT_FIN,
                ModelPartBuilder.create()
                        .uv(0, 16) // Texture location
                        .cuboid(0F, -3F, 0F, 6F, 2F, 6F) // Offset + Size
                , ModelTransform.pivot(6F, 25F, -11F)
        );

        modelPartData.addChild(
                EntityModelPartNames.BACK_FIN,
                ModelPartBuilder.create()
                        .uv(64, 0) // Texture location
                        .cuboid(0, 0, -6F, 2F, 4F, 6F) // Offset + Size
                , ModelTransform.of(-1F, 8F, 0F, // Pivot - Rotate about back of fin
                        45F, 0F, 0F)  // Rotation - Rotate so diagonal
        );

        modelPartData.addChild(
                EntityModelPartNames.TAIL_FIN,
                ModelPartBuilder.create()
                        .uv(25, 41) // Texture location
                        .cuboid(-8F, 21F, 28F, 16F, 1F, 10F) // Offset + Size
                , ModelTransform.pivot(0, 0, -8F)
        );

        return TexturedModelData.of(modelData, 80, 62); // 80x62px texture file
    }


    public static TexturedModelData getTexturedModelDataDefault() {
        return DolphinEntityModel.getTexturedModelData(false);
    }
    public static TexturedModelData getTexturedModelDataLongNose() {
        return DolphinEntityModel.getTexturedModelData(true);
    }

    @Override
    public void setAngles(DolphinEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        // Animate transforms
        DolphinsOfTheDeep.log(Level.WARN, "Limb angle="+limbAngle);//+"; Limb angle="+limbAngle+" distance="+limbDistance+"; Head yaw="+headYaw+" pitch="+headPitch);

        /* Fin angle */ // TODO
        float finAngle = limbAngle % 40;
        if(finAngle > 20) {
            finAngle = 40F-finAngle; // Move back
        }
        DolphinsOfTheDeep.log(Level.WARN, "Fin angle="+finAngle);
        this.leftFin.setAngles(0F, finAngle, 0F);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        // body
        ImmutableList.of(this.head, this.body, this.tail, this.nose, this.leftFin, this.rightFin, this.tailFin, this.dorsalFin).forEach((modelRenderer) -> { // Render each part
            modelRenderer.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        });
    }
}
