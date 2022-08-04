package io.github.webcoder49.dolphinsofthedeep;

import io.github.webcoder49.dolphinsofthedeep.entity.dolphin.DolphinEntityModel;
import io.github.webcoder49.dolphinsofthedeep.entity.dolphin.DolphinEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class DolphinsOfTheDeepClient implements ClientModInitializer {
    public static final EntityModelLayer MODEL_DOLPHIN_LAYER = new EntityModelLayer(new Identifier(DolphinsOfTheDeep.MOD_ID, "dolphin"), "main");
    @Override
    public void onInitializeClient() {
        /* Register EntityRenderers */
        // Dolphin
        EntityRendererRegistry.register(DolphinsOfTheDeep.DOLPHIN, (context) -> {
            return new DolphinEntityRenderer(context);
        });

        EntityModelLayerRegistry.registerModelLayer(MODEL_DOLPHIN_LAYER, DolphinEntityModel::getTexturedModelData);
    }
}
