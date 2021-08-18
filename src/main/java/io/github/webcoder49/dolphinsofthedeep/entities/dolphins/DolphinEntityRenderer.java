package io.github.webcoder49.dolphinsofthedeep.entities.dolphins;

import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class DolphinEntityRenderer extends MobEntityRenderer<DolphinEntity, DolphinEntityModel> {
    public DolphinEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new DolphinEntityModel(context.getPart(DolphinsOfTheDeepClient.MODEL_DOLPHIN_LAYER)), 0.5f); // Shadow size
    }

    @Override
    public Identifier getTexture(DolphinEntity entity) {
        return new Identifier("dolphinsofthedeep", "textures/entity/dolphin/dolphin.png");
    }
}
