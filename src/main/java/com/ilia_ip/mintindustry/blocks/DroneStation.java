package com.ilia_ip.mintindustry.blocks;

import com.ilia_ip.mintindustry.blocks.entities.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DroneStation extends Block implements EntityBlock {
    public DroneStation() {
        super(BlockBehaviour.Properties.of().instabreak());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos position,
            CollisionContext context) {
        return Block.box(1.0d, 0.0d, 1.0d, 15.0d, 1.0d, 15.0d);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos position) {
        return !level.isEmptyBlock(position.below());
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos position, BlockState state) {
        return ModBlockEntities.DRONE_STATION_ENTITY.get().create(position, state);
    } 
}
