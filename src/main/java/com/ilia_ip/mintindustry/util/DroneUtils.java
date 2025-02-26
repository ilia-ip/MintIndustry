package com.ilia_ip.mintindustry.util;

import java.util.concurrent.ThreadLocalRandom;


import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DeathMessageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class DroneUtils {
    public static final DamageSource DRONE_DAMAGE_SOURCE = new DamageSource(
            Holder.direct(
                    new DamageType("thorns", DamageScaling.NEVER, 3, DamageEffects.THORNS, DeathMessageType.INTENTIONAL_GAME_DESIGN)));

    /*
     * Spawns configurable amount of particles using only net.minecraft.world.Level
     */
    public static void spawnParticles(Vec3 pos, Entity entity, ParticleOptions type, int minAmount, int maxAmount) {
        for (int i = ThreadLocalRandom.current().nextInt(minAmount, maxAmount); i > 0; i--) {
            entity.level().addParticle(type, pos.x, pos.y, pos.z, 0, 0, 0);
        }
    }
}
