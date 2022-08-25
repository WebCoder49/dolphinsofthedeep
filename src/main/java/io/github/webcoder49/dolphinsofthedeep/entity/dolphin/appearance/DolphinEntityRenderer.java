package io.github.webcoder49.dolphinsofthedeep.entity.dolphin.appearance;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeepClient;
import io.github.webcoder49.dolphinsofthedeep.entity.dolphin.DolphinEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class DolphinEntityRenderer extends MobEntityRenderer<DolphinEntity, DolphinEntityModel> {
    private final String name;

    public DolphinEntityRenderer(EntityRendererFactory.Context context, String name, EntityModelLayer modelLayer) {
        super(context, new DolphinEntityModel(context.getPart(modelLayer)), 0.5f);
        this.addFeature(new DolphinArmourFeatureRenderer(this));

        this.name = name;
    }

    @Override
    public Identifier getTexture(DolphinEntity entity) {
        return new Identifier(DolphinsOfTheDeep.MOD_ID, "textures/entity/dolphin/" + this.name + ".png");
    }
}
