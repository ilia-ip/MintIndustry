package com.ilia_ip.mintindustry.entities.drone;

import java.util.concurrent.ThreadLocalRandom;

import com.ilia_ip.mintindustry.core.ModItems;
import com.ilia_ip.mintindustry.core.ModSounds;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DeathMessageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class DroneEntity extends PathfinderMob implements FlyingAnimal {
    public Player owner = null;
    public float propellerSpeed = 0;
    public DroneTasks currentTask;

    public static final DamageSource damageSource = new DamageSource(
            Holder.direct(
                    new DamageType("thorns", DamageScaling.NEVER, 3, DamageEffects.THORNS, DeathMessageType.DEFAULT)));

    // ========================== CONSTRUCTOR =============================
    public DroneEntity(EntityType<DroneEntity> type, Level level) {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this, 30, true);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor server, DifficultyInstance difficulty,
            MobSpawnType spawnType, SpawnGroupData spawnData, CompoundTag compound) {

        owner = server.getLevel().getNearestPlayer(this, 10.0d);
        currentTask = DroneTasks.FOLLOWING_PLAYER;
        return spawnData;
    }

    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, level);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    @Override
    public void tick() {
        if (this.propellerSpeed < 20) {
            this.propellerSpeed += 0.2;
        } else {
            this.playSound(ModSounds.DRONE_ENGINE_LOOP.get(),
                    0.10f, 1.0f);
        }
        super.tick();
    }

    public boolean canFly() {
        return this.propellerSpeed > 18;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FollowGoal(this));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0f).add(Attributes.FLYING_SPEED, 3.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        Vec3 pos = this.getPosition(0);
        if (source.is(DamageTypes.PLAYER_ATTACK)) {
            ItemStack items = new ItemStack(ModItems.DRONE_ITEM.get(), 1);
            this.spawnAtLocation(items);
            for (int i = ThreadLocalRandom.current().nextInt(10, 20); i > 0; i--) {
                this.level().addParticle(ParticleTypes.SOUL, pos.x, pos.y, pos.z, 0, 0, 0);
            }
            this.discard();
        } else if (source.is(DamageTypes.EXPLOSION) || source.is(DamageTypes.GENERIC_KILL)
                || source.is(DamageTypes.GENERIC)) {
            for (int i = ThreadLocalRandom.current().nextInt(1, 3); i > 0; i--) {
                this.level().addParticle(ParticleTypes.EXPLOSION, pos.x, pos.y, pos.z, 0, 0, 0);
            }
            super.actuallyHurt(source, amount / 100);
        }
        return true;
    }

    @Override
    public void push(Entity entity) {
        entity.hurt(damageSource, 1.0f);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        owner = this.level().getPlayerByUUID(tag.getUUID("owner_uuid"));
        propellerSpeed = tag.getFloat("propeller_speed");
        currentTask = DroneTasks.values()[tag.getInt("drone_task")];
        super.readAdditionalSaveData(tag);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putUUID("owner_uuid", owner.getUUID());
        tag.putFloat("propeller_speed", propellerSpeed);
        tag.putInt("drone_task", currentTask.value);
        super.addAdditionalSaveData(tag);
    }

    // ========================= UNUSED ============================
    @Override
    protected void doPush(Entity p_20971_) {
    }

    @Override
    protected void playHurtSound(DamageSource p_21493_) {
    }

    @Override
    public boolean isFlying() {
        return !this.onGround();
    }

    @Override
    protected void checkFallDamage(double p_20990_, boolean p_20991_, BlockState p_20992_, BlockPos p_20993_) {
    }

    @Override
    public boolean canBeLeashed(Player p_21418_) {
        return false;
    }

    @Override
    public void push(double p_20286_, double p_20287_, double p_20288_) {
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }
}