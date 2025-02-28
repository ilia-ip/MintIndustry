package com.ilia_ip.mintindustry.entities.drone.tasks;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ilia_ip.mintindustry.blockentities.DroneStationEntity;
import com.ilia_ip.mintindustry.blocks.DroneStation;
import com.ilia_ip.mintindustry.core.MintIndustry;
import com.ilia_ip.mintindustry.entities.drone.DroneEntity;
import com.ilia_ip.mintindustry.entities.drone.DroneTask;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.level.block.entity.BlockEntity;

public class RechargeTask extends Goal {
    protected final DroneEntity mob;
    protected BlockPos stationPos;

    public RechargeTask(DroneEntity mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.MOVE));
    }
    
    
    @Override
    public boolean canUse() {
        return mob.currentTask == DroneTask.RECHARGING;
    }

    private DroneStationEntity findStation() {
        List<DroneStationEntity> stations = List.of();

        BlockPos blockpos = this.mob.blockPosition();
        PoiManager poimanager = ((ServerLevel)this.mob.level()).getPoiManager();
        Stream<PoiRecord> stream = poimanager.getInRange((p_218130_) -> {
           return p_218130_.is(TagKey.create(Registries.POINT_OF_INTEREST_TYPE, ResourceLocation.tryBuild("drone_station", MintIndustry.MODID)));
        }, blockpos, 20, PoiManager.Occupancy.ANY);
        List<BlockPos> positions = stream.map(PoiRecord::getPos).sorted(Comparator.comparingDouble((p_148811_) -> {
           return p_148811_.distSqr(blockpos);
        })).collect(Collectors.toList());

        for (BlockPos position : positions) {
            BlockEntity entity = this.mob.level().getBlockEntity(position);
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
        DroneStationEntity station = this.findStation();

        if (this.mob.distanceToSqr(station.getBlockPos().getCenter()) <= 0.1) {
            this.mob.propellerSpeed = 0;
            return;
        }
        this.mob.getLookControl().setLookAt(station.getBlockPos().getCenter());
        this.mob.getNavigation().moveTo(station.getBlockPos().getX(), station.getBlockPos().getY(), station.getBlockPos().getZ(), 1.0);
    }
}
