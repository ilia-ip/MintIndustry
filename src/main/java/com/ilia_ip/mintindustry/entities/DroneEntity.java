package com.ilia_ip.mintindustry.entities;

import java.util.concurrent.ThreadLocalRandom;

import com.ilia_ip.mintindustry.items.ItemInit;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class DroneEntity extends PathfinderMob {

    public DroneEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    @Override
    protected void playHurtSound(DamageSource p_21493_) {
        return;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        ItemStack items = new ItemStack(ItemInit.DRONE_ITEM.get(), 2);
        this.spawnAtLocation(items);
        Vec3 pos = this.getPosition(0);
        for (int i = ThreadLocalRandom.current().nextInt(10, 20); i > 0; i--) {
            this.level().addParticle(ParticleTypes.EXPLOSION, pos.x, pos.y, pos.z, 0, 0, 0);
        }
        this.discard();
        return true;
    }

    @Override
    public boolean canBeLeashed(Player p_21418_) {
        return false;
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 1.0f);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }
}
