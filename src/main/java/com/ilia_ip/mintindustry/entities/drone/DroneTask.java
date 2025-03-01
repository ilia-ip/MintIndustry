package com.ilia_ip.mintindustry.entities.drone;

public abstract class DroneTask {
    protected DroneTasks TYPE;
    protected final DroneEntity drone;
    public final int PRIORITY;

    public DroneTask(DroneEntity drone, int priority) {
        this.drone = drone;
        this.PRIORITY = priority;
    }

    public boolean canContinueToUse(DroneTasks task) {
        return this.canUse(task);
    }

    public boolean canUse(DroneTasks task) {
        return task == this.TYPE && this.drone.canFly();
    }

    public abstract void tick();
}