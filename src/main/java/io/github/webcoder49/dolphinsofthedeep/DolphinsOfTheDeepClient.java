package io.github.webcoder49.dolphinsofthedeep;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class DolphinsOfTheDeepClient implements ClientModInitializer {
    /*public static final EntityModelLayer MODEL_DOLPHIN_LAYER = new EntityModelLayer(new Identifier("entitytesting", "cube"), "main");*/
    @Override
    public void onInitializeClient() {

        // Register DolphinEntity Renderer
        /*EntityRendererRegistry.INSTANCE.register(DolphinsOfTheDeep.DOLPHIN, (context) -> {
            return new CubeEntityRenderer(context);
        });
        EntityModelLayerRegistry.registerModelLayer(MODEL_CUBE_LAYER, CubeEntityModel::getTexturedModelData);*/
    }
}
