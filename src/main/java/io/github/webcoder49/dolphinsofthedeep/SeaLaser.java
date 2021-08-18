package io.github.webcoder49.dolphinsofthedeep;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.TorchBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class SeaLaser extends Block implements Waterloggable {
    public static final BooleanProperty WATERLOGGED;

    public SeaLaser(Settings settings) {
        super(settings.luminance((state) -> (int)(15)).noCollision());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if(entity.getType() == EntityType.PLAYER) {
            CustomDamageSource damageSource = new CustomDamageSource("sea_laser");
            entity.damage(damageSource, 7F);
            /*if (!world.isClient) {
                world.playSound(
                        null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                        pos, // The position of where the sound will come from
                        DolphinsOfTheDeep.SEA_LASER_ZAP, // The sound that will play
                        SoundCategory.PLAYERS, // This determines which of the volume sliders affect this sound
                        1f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                        1f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
                );
            }*/
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.4375f, 0f, 0.4375f, 0.5625f, 1.0f, 0.5625f);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
		return !(Boolean)state.get(WATERLOGGED);
	}

    @Override
    public FluidState getFluidState(BlockState state) {
		return (Boolean)state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

    static {
		WATERLOGGED = Properties.WATERLOGGED;
	}
}
