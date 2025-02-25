package com.ilia_ip.mintindustry.entities.drone;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public enum DroneTasks {
    IDLE(0),
    FOLLOWING_PLAYER(1),
    RECHARGING(2);

    public int value;

    private DroneTasks(int value) {
        this.value = value;
    }
}

class RechargeGoal extends Goal {
    protected final DroneEntity mob;
    protected BlockPos stationPos;

    public RechargeGoal(DroneEntity mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.MOVE));
    }
    
    
    @Override
    public boolean canUse() {
        if (!mob.canFly()) {
            return false;
        }
        return mob.currentTask == DroneTasks.RECHARGING;
    }

    @Override
    public void tick() {
         
    }
}

class FollowGoal extends Goal {
    protected final DroneEntity mob;
    protected Player player;

    public FollowGoal(DroneEntity mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean canUse() {
        this.player = mob.owner;
        if (player == null || !mob.canFly()) {
            return false;
        }
        return mob.currentTask == DroneTasks.FOLLOWING_PLAYER;
    }


    public double distanceToSqr2D(double x, double z) {
        double d0 = this.mob.getX() - x;
        double d2 = this.mob.getZ() - z;
        return d0 * d0 + d2 * d2;
    }

    public void tick() {
        this.mob.getLookControl().setLookAt(this.player, (float) (this.mob.getMaxHeadYRot() + 20),
                (float) this.mob.getMaxHeadXRot());

        // Actual movement
        Vec3 playerPos = new Vec3(this.player.getX(), this.player.getY() + 2.0D, this.player.getZ());
        Vec3 mobPos = this.mob.position();

        boolean isNearPlayer = distanceToSqr2D(playerPos.x, playerPos.z) < 1.5D;
        boolean isUnderPlayer = mobPos.y - playerPos.y < 0.3D;
        boolean isHittingPlayer = distanceToSqr2D(playerPos.x, playerPos.z) < 1D;

        // spagetti
        boolean isBehind = mobPos.x < playerPos.x;
        boolean isToTheLeft = mobPos.z > playerPos.z;
        int signX = isBehind ? -1 : 1;
        int signZ = isToTheLeft ? -1 : 1;

        if (isHittingPlayer) {
            this.mob.getNavigation().moveTo(mobPos.x + (1.5D * signX), playerPos.y + 0.5D, mobPos.z + (1.0 * signZ),
                    1.5D);
        } else if (isNearPlayer && !isUnderPlayer) {
            this.mob.getNavigation().stop();

        } else if (isNearPlayer && isUnderPlayer) {
            this.mob.getNavigation().moveTo(mobPos.x, playerPos.y + 0.5D, mobPos.z, 1.0D);
        } else {
            this.mob.getNavigation().moveTo(playerPos.x + (1.5D * signX), playerPos.y + 0.5D,
                    playerPos.z + (1.5D * signZ), 1.0D);
        }

    }
}