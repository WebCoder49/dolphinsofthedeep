package io.github.webcoder49.dolphinsofthedeep.entity.dolphin.species;

import io.github.webcoder49.dolphinsofthedeep.entity.dolphin.DolphinAttributes;
import io.github.webcoder49.dolphinsofthedeep.entity.dolphin.DolphinEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public class PinkRiverDolphinEntity extends DolphinEntity {
    public PinkRiverDolphinEntity(EntityType<? extends net.minecraft.entity.passive.DolphinEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createPinkRiverAttributes() {
        // Harder to tame; better gifts
        return DolphinEntity.createDolphinAttributes()
                .add(DolphinAttributes.DOLPHIN_TAMING_DIFFICULTY, 2)
                .add(DolphinAttributes.DOLPHIN_GIFT_MIN_QUALITY, 0.2D)
                .add(DolphinAttributes.DOLPHIN_CHAT_CHANCE, 0.003D)
                .add(DolphinAttributes.DOLPHIN_FRIENDLY_CHANCE, 0.2D);
    }

    @Override
    public boolean canSpawn(WorldView world) {
        Chunk chunk = world.getChunk(this.getBlockPos());
        // Only spawn near jungles.
        // Jungle at top; caves below. Is jungle in one of the top corners?
        int[] cornerPos = {0, 16};

        for(int x : cornerPos) {
            for(int z : cornerPos) {
                RegistryEntry<Biome> biome = chunk.getBiomeForNoiseGen(x, 16, z);
                if(biome.matchesId(new Identifier("minecraft", "jungle"))
                || biome.matchesId(new Identifier("minecraft", "bamboo_jungle"))
                || biome.matchesId(new Identifier("minecraft", "sparse_jungle"))) {
                    return super.canSpawn(world); // TODO: Test
                }
            }
        }
        return false; // Block spawning if no jungle
    }
}
