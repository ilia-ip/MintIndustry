package com.ilia_ip.mintindustry.entities.drone.tasks;

import com.ilia_ip.mintindustry.entities.drone.DroneEntity;
import com.ilia_ip.mintindustry.entities.drone.DroneTask;
import com.ilia_ip.mintindustry.entities.drone.DroneTasks;
import com.ilia_ip.mintindustry.util.DroneUtils;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/*
 * Default drone task
 * Drone just follows its owner 
 */
public class FollowOwnerTask extends DroneTask {

    public FollowOwnerTask(DroneEntity drone) {
        super(drone, 1);
        this.TYPE = DroneTasks.FOLLOWING_PLAYER;
    }

    @Override
    public boolean canUse(DroneTasks task) {
        return this.drone.getOwner() != null && this.drone.getOwner().getPlayer() != null && super.canUse(task);
    } 

    public void tick() {
        if (!this.canUse(TYPE)) return;
        Player player = this.drone.getOwner().getPlayer();
        this.drone.getLookControl().setLookAt(player);

        // Actual movement
        Vec3 playerPos = new Vec3(player.getX(), player.getY() + 2.0D, player.getZ());
        Vec3 mobPos = this.drone.position();

        boolean isNearPlayer = DroneUtils.distanceToSqr2D(this.drone, playerPos.x, playerPos.z) < 1.5D;
        boolean isUnderPlayer = mobPos.y - playerPos.y < 0.3D;
        boolean isHittingPlayer = DroneUtils.distanceToSqr2D(this.drone, playerPos.x, playerPos.z) < 1D;

        // spagetti
        boolean isBehind = mobPos.x < playerPos.x;
        boolean isToTheLeft = mobPos.z > playerPos.z;
        int signX = isBehind ? -1 : 1;
        int signZ = isToTheLeft ? -1 : 1;

        if (isHittingPlayer) {
            this.drone.getNavigation().moveTo(mobPos.x + (1.5D * signX), playerPos.y + 0.5D, mobPos.z + (1.0 * signZ),
                    1.0D);
        } else if (isNearPlayer && !isUnderPlayer) {
            this.drone.getNavigation().stop();

        } else if (isNearPlayer && isUnderPlayer) {
            this.drone.getNavigation().moveTo(mobPos.x, playerPos.y + 0.5D, mobPos.z, 1.0D);
        } else {
            this.drone.getNavigation().moveTo(playerPos.x + (1.5D * signX), playerPos.y + 0.5D,
                    playerPos.z + (1.5D * signZ), 1.0D);
        }
    }
}
