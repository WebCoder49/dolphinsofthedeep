package io.github.webcoder49.dolphinsofthedeep;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
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
        super(settings);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        CustomDamageSource damageSource = new CustomDamageSource("sea_laser");
        entity.damage(damageSource, 7F);
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
