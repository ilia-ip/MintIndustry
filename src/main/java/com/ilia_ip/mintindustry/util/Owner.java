package com.ilia_ip.mintindustry.util;


import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class Owner {
    private static final double range = 10.0d;


    public static <T extends Entity> Player getOwner(Level level, T entity) {

        return level.getNearestPlayer(entity, range);
    }

    public static Player getOwner(Level level, BlockPos position) {
        return level.getNearestPlayer(position.getX(), position.getY(), position.getZ(), range, null);
    }
}
