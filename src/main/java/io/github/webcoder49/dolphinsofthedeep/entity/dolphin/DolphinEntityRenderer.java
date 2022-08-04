package io.github.webcoder49.dolphinsofthedeep.entity.dolphin;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeepClient;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class DolphinEntityRenderer extends MobEntityRenderer<DolphinEntity, DolphinEntityModel> {
    public DolphinEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new DolphinEntityModel(context.getPart(DolphinsOfTheDeepClient.MODEL_DOLPHIN_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(DolphinEntity entity) {
        return new Identifier(DolphinsOfTheDeep.MOD_ID, "textures/entity/dolphin/bottlenose.png");
    }
}
