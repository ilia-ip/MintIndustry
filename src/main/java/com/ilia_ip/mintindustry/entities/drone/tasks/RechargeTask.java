package com.ilia_ip.mintindustry.entities.drone.tasks;

import java.util.EnumSet;

import com.ilia_ip.mintindustry.entities.drone.DroneEntity;
import com.ilia_ip.mintindustry.entities.drone.DroneTask;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

public class RechargeTask extends Goal {
    protected final DroneEntity mob;
    protected BlockPos stationPos;

    public RechargeTask(DroneEntity mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.MOVE));
    }
    
    
    @Override
    public boolean canUse() {
        if (!mob.canFly()) {
            return false;
        }
        return mob.currentTask == DroneTask.RECHARGING;
    }

    @Override
    public void tick() {
         
    }
}
