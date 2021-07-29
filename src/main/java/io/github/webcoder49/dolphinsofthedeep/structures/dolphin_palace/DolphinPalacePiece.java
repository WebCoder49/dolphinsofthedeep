package io.github.webcoder49.dolphinsofthedeep.structures.dolphin_palace;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.pool.LegacySinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;

public class DolphinPalacePiece extends PoolStructurePiece {

    public static void init() { }
 
    static {
        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        DolphinsOfTheDeep.DOLPHIN_PALACE_MAIN_ROOM_POOL,
                        new Identifier("dolphinsofthedeep:dolphin_palace_main_room"),
                        ImmutableList.of(
                                Pair.of(new LegacySinglePoolElement("dolphinsofthedeep:dolphin_palace/main_room"), 1)
                        ),
                        StructurePool.Projection.RIGID
                )
        );
    
        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        DolphinsOfTheDeep.DOLPHIN_PALACE_SMALL_ROOM_POOL,
                        new Identifier("dolphinsofthedeep:dolphin_palace_small_room"),
                        ImmutableList.of(
                                Pair.of(new LegacySinglePoolElement("dolphinsofthedeep:dolphin_palace/small_room_1"), 1),
                                Pair.of(new LegacySinglePoolElement("dolphinsofthedeep:dolphin_palace/small_room_2"), 1),
                                Pair.of(new LegacySinglePoolElement("dolphinsofthedeep:dolphin_palace/small_room_3"), 1),
                                Pair.of(new LegacySinglePoolElement("dolphinsofthedeep:dolphin_palace/small_room_4"), 1),
                                Pair.of(new LegacySinglePoolElement("dolphinsofthedeep:dolphin_palace/small_room_5"), 1),
                                Pair.of(new LegacySinglePoolElement("dolphinsofthedeep:dolphin_palace/small_room_6"), 1),
                                Pair.of(new LegacySinglePoolElement("dolphinsofthedeep:dolphin_palace/small_room_7"), 1),
                                Pair.of(new LegacySinglePoolElement("dolphinsofthedeep:dolphin_palace/small_room_8"), 1),
                                Pair.of(new LegacySinglePoolElement("dolphinsofthedeep:dolphin_palace/small_room_9"), 1),
                                Pair.of(new LegacySinglePoolElement("dolphinsofthedeep:dolphin_palace/small_room_10"), 1),
                                Pair.of(new LegacySinglePoolElement("dolphinsofthedeep:dolphin_palace/small_room_11"), 1),
                                Pair.of(new LegacySinglePoolElement("dolphinsofthedeep:dolphin_palace/small_room_12"), 1),
                                Pair.of(new LegacySinglePoolElement("dolphinsofthedeep:dolphin_palace/small_room_13"), 1),
                                Pair.of(new LegacySinglePoolElement("dolphinsofthedeep:dolphin_palace/small_room_14"), 1),
                                Pair.of(new LegacySinglePoolElement("dolphinsofthedeep:dolphin_palace/small_room_15"), 1),
                                Pair.of(new LegacySinglePoolElement("dolphinsofthedeep:dolphin_palace/small_room_16"), 1),
                                Pair.of(new LegacySinglePoolElement("dolphinsofthedeep:dolphin_palace/small_room_17"), 1),
                                Pair.of(new LegacySinglePoolElement("dolphinsofthedeep:dolphin_palace/small_room_18"), 1)
                        ),
                        StructurePool.Projection.RIGID
                )
        );
    }

    DolphinPalacePiece(StructureManager structureManager_1, StructurePoolElement structurePoolElement_1, BlockPos blockPos_1, int int_1, BlockRotation blockRotation_1, BlockBox mutableIntBoundingBox_1) {
        super(DolphinsOfTheDeep.DOLPHIN_PALACE_PIECE, structureManager_1, structurePoolElement_1, blockPos_1, int_1, blockRotation_1, mutableIntBoundingBox_1);
    }

    public DolphinPalacePiece(StructureManager manager, NbtCompound tag) {
        super(manager, tag, DolphinsOfTheDeep.DOLPHIN_PALACE_PIECE);
    }
}
