package com.ilia_ip.mintindustry.blocks.entities;

import com.mrcrayfish.furniture.refurbished.blockentity.ElectricityModuleBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class DroneStationEntity extends ElectricityModuleBlockEntity {
    public Player owner;

    public DroneStationEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.DRONE_STATION_ENTITY.get(), blockPos, blockState);
        //owner = Owner.getOwner(this.getLevel(), blockPos);
    }   

    @Override
    public boolean isNodePowered() {
        BlockState state = this.getBlockState();
        return state.hasProperty(BlockStateProperties.POWERED) && state.getValue(BlockStateProperties.POWERED);
        
    }

    @Override
    public void setNodePowered(boolean powered) {
        BlockState state = this.getBlockState();
        if (state.hasProperty(BlockStateProperties.POWERED)) {
            this.level.setBlock(this.worldPosition, state.setValue(BlockStateProperties.POWERED, powered), Block.UPDATE_ALL);
        }
    }
}
