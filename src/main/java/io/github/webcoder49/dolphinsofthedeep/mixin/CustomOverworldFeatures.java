package io.github.webcoder49.dolphinsofthedeep.mixin;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraft.world.biome.OverworldBiomeCreator;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


/**
 * Remove automatic spawning of vanilla dolphin and add custom dolphin spawning
 */
@Mixin(OverworldBiomeCreator.class)
public class CustomOverworldFeatures {
    @ModifyVariable(method = "createRiver(Z)Lnet/minecraft/world/biome/Biome;", at = @At("STORE"), ordinal=0)
    private static SpawnSettings.Builder addCustomDolphinsRiver(SpawnSettings.Builder builder) {
        // Capture first spawn settings builder
        builder.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(DolphinsOfTheDeep.PINKRIVER, 100, 1, 8)); // Weight 2
        return builder;
    }
}
