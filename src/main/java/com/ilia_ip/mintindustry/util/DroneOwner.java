package com.ilia_ip.mintindustry.util;


import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class DroneOwner {
    private static final double range = 10.0d;
    private Player player; 

    private DroneOwner(Player player) {
        this.player = player;
    }

    public static <T extends Entity> DroneOwner getOwner(Level level, T entity) {

        return getOwner(level, entity.getX(), entity.getY(), entity.getZ());
    }

    public static DroneOwner getOwner(Level level, BlockPos position) {
        return getOwner(level, position.getX(), position.getY(), position.getZ());
    }

    public static DroneOwner getOwner(Level level, double x, double y, double z) {
        return new DroneOwner(level.getNearestPlayer(x, y, z, range, false));
    }


    public static DroneOwner byUUID(Level level, UUID uuid) {
        return new DroneOwner(level.getPlayerByUUID(uuid));
    }

    public UUID getUUID() {
        return this.player.getUUID();
    }

    public Player getPlayer() {
        return this.player;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            return ((Player)obj).getUUID().equals(this.player.getUUID());
        } else {
            return false;
        }
    }
}
