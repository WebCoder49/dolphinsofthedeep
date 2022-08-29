package io.github.webcoder49.dolphinsofthedeep;

import io.github.webcoder49.dolphinsofthedeep.entity.dolphin.appearance.DolphinEntityModel;
import io.github.webcoder49.dolphinsofthedeep.entity.dolphin.appearance.DolphinEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

@Environment(EnvType.CLIENT)
public class DolphinsOfTheDeepClient implements ClientModInitializer {
    public static final EntityModelLayer MODEL_DOLPHIN_LAYER = new EntityModelLayer(new Identifier(DolphinsOfTheDeep.MOD_ID, "dolphin"), "main");
    public static final EntityModelLayer MODEL_LONG_NOSE_DOLPHIN_LAYER = new EntityModelLayer(new Identifier(DolphinsOfTheDeep.MOD_ID, "long_nose_dolphin"), "main");
    @Override
    public void onInitializeClient() {
        /* Register EntityRenderers */
        // Dolphins
        EntityRendererRegistry.register(DolphinsOfTheDeep.BOTTLENOSE, (context) -> {
            return new DolphinEntityRenderer(context, "bottlenose", MODEL_DOLPHIN_LAYER);
        });
        EntityRendererRegistry.register(DolphinsOfTheDeep.COMMON, (context) -> {
            return new DolphinEntityRenderer(context, "common", MODEL_DOLPHIN_LAYER);
        });
        EntityRendererRegistry.register(DolphinsOfTheDeep.PINKRIVER, (context) -> {
            return new DolphinEntityRenderer(context, "pinkriver", MODEL_LONG_NOSE_DOLPHIN_LAYER);
        });

        // Register different models
        EntityModelLayerRegistry.registerModelLayer(MODEL_DOLPHIN_LAYER, DolphinEntityModel::getTexturedModelDataDefault);
        EntityModelLayerRegistry.registerModelLayer(MODEL_LONG_NOSE_DOLPHIN_LAYER, DolphinEntityModel::getTexturedModelDataLongNose);
    }
}
