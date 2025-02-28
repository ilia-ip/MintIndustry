package com.ilia_ip.mintindustry.util;

import java.util.concurrent.ThreadLocalRandom;

import com.ilia_ip.mintindustry.core.ModItems;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DeathMessageType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class DroneUtils {
    public static final ItemStack DRONE_ITEM_STACK = new ItemStack(ModItems.DRONE_ITEM.get(), 1);
   
    public static final DamageSource DRONE_BLADES_DAMAGE_SOURCE = new DamageSource(
            Holder.direct(
                    new DamageType("thorns", DamageScaling.NEVER, 3, DamageEffects.THORNS, DeathMessageType.INTENTIONAL_GAME_DESIGN)));

    public static void spawnParticles(Vec3 pos, Level level, ParticleOptions type, int minAmount, int maxAmount) {
        if (level.isClientSide) {
            for (int i = ThreadLocalRandom.current().nextInt(minAmount, maxAmount); i > 0; i--) {
                level.addParticle(type, pos.x, pos.y, pos.z, 0, 0, 0);
            }
        } else {
            int amount = ThreadLocalRandom.current().nextInt(minAmount, maxAmount);
            ((ServerLevel)level).sendParticles(type, pos.x, pos.y, pos.z, amount, 0, 0, 0, 0);
        }
    }
}
