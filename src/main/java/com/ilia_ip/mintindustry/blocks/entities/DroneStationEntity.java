package com.ilia_ip.mintindustry.blocks.entities;

import java.util.Set;

import com.mrcrayfish.furniture.refurbished.electricity.Connection;
import com.mrcrayfish.furniture.refurbished.electricity.IModuleNode;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DroneStationEntity extends BlockEntity implements IModuleNode {
    private boolean powered = false;
    private boolean receivingPower = false;
    
    public DroneStationEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.DRONE_STATION_ENTITY.get(), blockPos, blockState);
    }

    @Override
    public BlockPos getNodePosition() {
        return this.worldPosition;
    }

    @Override
    public boolean isNodePowered() { 
        return powered;
    } 

    @Override
    public void setNodePowered(boolean powered) {
        this.powered = powered;
    }

    @Override
    public Level getNodeLevel() {
        return this.level;
    }

    @Override
    public Set<Connection> getNodeConnections() {
        return null;
    }

    @Override
    public Set<BlockPos> getPowerSources() {
        return null;
    }

    @Override
    public void setNodeReceivingPower(boolean receivingPower) {
        this.receivingPower = receivingPower;
    } 

    @Override
    public BlockEntity getNodeOwner() {
        return this;
    }

    @Override
    public boolean isNodeReceivingPower() {
        return this.receivingPower;
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    
    @Override
    protected void saveAdditional(CompoundTag tag) {
        this.writeNodeNbt(tag);
        tag.putBoolean("is_powered", remove);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        this.readNodeNbt(tag);
        this.powered = tag.getBoolean("is_powered");
        super.load(tag);
    }
}
