package io.github.webcoder49.dolphinsofthedeep.structures;

import com.mojang.serialization.Codec;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class DolphinPalaceFeature extends StructureFeature<DefaultFeatureConfig> {
    
    public DolphinPalaceFeature(Codec<StructurePoolFeatureConfig> codec) {
        super(codec);
    }
    
    @Override
    protected boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long l, ChunkRandom chunkRandom, int i, int j, Biome biome, ChunkPos ChunkPos, StructurePoolFeatureConfig featureConfig) {
        return chunkRandom.nextInt(150) == 0; // 1 in 150 chance
    }

    @Override
    
}