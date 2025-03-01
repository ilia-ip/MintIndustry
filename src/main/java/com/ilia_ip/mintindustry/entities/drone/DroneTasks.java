package com.ilia_ip.mintindustry.entities.drone;

public enum DroneTasks {
    IDLE(0),
    FOLLOWING_PLAYER(1),
    RECHARGING(2);

    public int value;

    private DroneTasks(int value) {
        this.value = value;
    }
}