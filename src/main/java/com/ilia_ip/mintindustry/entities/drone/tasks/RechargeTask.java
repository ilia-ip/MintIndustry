package com.ilia_ip.mintindustry.entities.drone.tasks;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ilia_ip.mintindustry.blockentities.DroneStationEntity;
import com.ilia_ip.mintindustry.core.MintIndustry;
import com.ilia_ip.mintindustry.entities.drone.DroneEntity;
import com.ilia_ip.mintindustry.entities.drone.DroneTask;
import com.ilia_ip.mintindustry.entities.drone.DroneTasks;
import com.mojang.logging.LogUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.level.block.entity.BlockEntity;

public class RechargeTask extends DroneTask {
    private DroneStationEntity station;

    public RechargeTask(DroneEntity drone) {
        super(drone, 10);
    }
   
    @Override
    public boolean canContinueToUse(DroneTasks task) {
        return this.TYPE == task && this.drone.getPowerLevel() < 500;
    }
    
    @Override
    public boolean canUse(DroneTasks task) {
        return this.TYPE == task && this.drone.getPowerLevel() < 5;
    }

    private DroneStationEntity findStation() {
        List<DroneStationEntity> stations = List.of();

        BlockPos blockpos = this.drone.blockPosition();
        PoiManager poimanager = ((ServerLevel)this.drone.level()).getPoiManager();
        Stream<PoiRecord> stream = poimanager.getInRange((p_218130_) -> {
           return p_218130_.is(TagKey.create(Registries.POINT_OF_INTEREST_TYPE, ResourceLocation.tryBuild("drone_station", MintIndustry.MODID)));
        }, blockpos, 20, PoiManager.Occupancy.ANY);
        List<BlockPos> positions = stream.map(PoiRecord::getPos).sorted(Comparator.comparingDouble((p_148811_) -> {
           return p_148811_.distSqr(blockpos);
        })).collect(Collectors.toList());

        for (BlockPos position : positions) {
            BlockEntity entity = this.drone.level().getBlockEntity(position);
            if (entity instanceof DroneStationEntity) {
                DroneStationEntity droneStationEntity = (DroneStationEntity)entity;
                if (!droneStationEntity.isFull())
                    stations.add(droneStationEntity);
            }
        }
        return stations.get(0);
    }

    @Override
    public void tick() {
        LogUtils.getLogger().atWarn().log("WORKING");
        if(this.station == null || this.station.isFull()) this.station = this.findStation();

        if (this.drone.distanceToSqr(station.getBlockPos().getCenter()) <= 1) {
            this.drone.setPowerLevel(this.drone.getPowerLevel()+2);
        } else {
            this.drone.getLookControl().setLookAt(station.getBlockPos().getCenter());
            this.drone.getNavigation().moveTo(station.getBlockPos().getX(), station.getBlockPos().getY(), station.getBlockPos().getZ(), 1.0);
        }
    }
}
