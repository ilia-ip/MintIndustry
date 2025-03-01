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

    /*
     * Wrappers for getting owner from level and coordinates
     */
    public static DroneOwner getOwner(Level level, Entity entity) {

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

    public boolean equals(Entity obj) {
        if (obj instanceof Player) {
            return ((Player)obj).getUUID().equals(this.player.getUUID());
        } else {
            return false;
        }
    }
}
