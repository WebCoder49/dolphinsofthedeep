package io.github.webcoder49.dolphinsofthedeep.mixin;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


/**
 * Remove automatic spawning of vanilla dolphin and add custom dolphin spawning
 */
@Mixin(DefaultBiomeFeatures.class)
public class CustomDefaultFeatures {
    @Redirect(method = "addWarmOceanMobs(Lnet/minecraft/world/biome/SpawnSettings$Builder;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/SpawnSettings$Builder;spawn(Lnet/minecraft/entity/SpawnGroup;Lnet/minecraft/world/biome/SpawnSettings$SpawnEntry;)Lnet/minecraft/world/biome/SpawnSettings$Builder;"))
    private static SpawnSettings.Builder tryRemoveVanillaDolphin(SpawnSettings.Builder builder, SpawnGroup spawnGroup, SpawnSettings.SpawnEntry spawnEntry) {
        if(spawnEntry.type == EntityType.DOLPHIN) {
            DolphinsOfTheDeep.log(Level.WARN, "Removed vanilla dolphin.");
            // Cancel vanilla dolphin spawning
            return null;
        }
        // Run other entity spawning
        return builder.spawn(spawnGroup, spawnEntry);
    }

    // Params = 1 SpawnSettings Builder; 3 ints
    @Inject(at = @At("HEAD"), method = "addOceanMobs(Lnet/minecraft/world/biome/SpawnSettings$Builder;III)V")
    private static void addCustomDolphinsOcean(SpawnSettings.Builder builder, int squidWeight, int squidMaxGroupSize, int codWeight, CallbackInfo info) {
        DolphinsOfTheDeep.log(Level.WARN, "Custom Biome Features Initialised!");
        builder.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(DolphinsOfTheDeep.COMMON, 100, 1, 20)); // Weight 10
    }

    @Inject(at = @At("HEAD"), method = "addWarmOceanMobs(Lnet/minecraft/world/biome/SpawnSettings$Builder;II)V")
    private static void addCustomDolphinsWarmOcean(SpawnSettings.Builder builder, int squidWeight, int squidMinGroupSize, CallbackInfo info) {
        builder.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(DolphinsOfTheDeep.BOTTLENOSE, 70, 1, 12)); // Weight 5
    }
}
