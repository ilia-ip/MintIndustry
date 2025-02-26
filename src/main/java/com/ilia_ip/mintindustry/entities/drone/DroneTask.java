package com.ilia_ip.mintindustry.entities.drone;

public enum DroneTask {
    IDLE(0),
    FOLLOWING_PLAYER(1),
    RECHARGING(2);

    public int value;

    private DroneTask(int value) {
        this.value = value;
    }
}