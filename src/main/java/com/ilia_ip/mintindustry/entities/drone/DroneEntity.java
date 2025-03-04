package com.ilia_ip.mintindustry.entities.drone;


import com.ilia_ip.mintindustry.core.ModSounds;
import com.ilia_ip.mintindustry.entities.drone.tasks.FollowOwnerTask;
import com.ilia_ip.mintindustry.util.DroneOwner;
import com.ilia_ip.mintindustry.util.DroneUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class DroneEntity extends PathfinderMob implements FlyingAnimal {
    public DroneOwner owner;
    public float propellerSpeed;
    public DroneTask currentTask;

    

    public DroneEntity(EntityType<DroneEntity> type, Level level) {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this, 65, true);
        this.currentTask = DroneTask.IDLE;
        this.propellerSpeed = 0;
    }

    public void setTask(DroneTask task) {
        this.currentTask = task;
    }

    /* 
     * Getting DroneOwner
     */    
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor server, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnData, CompoundTag compound) {
        this.owner = DroneOwner.getOwner(this.level(), this);
        return spawnData;
    }
 
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, level);
        flyingpathnavigation.setCanOpenDoors(true);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    @Override
    public void tick() {
        if (this.propellerSpeed < 20) {
            this.propellerSpeed += 0.2;
        }

        // Playes sound with volume based on propeller speed (from 0.01 to 0.10)
        this.playSound(ModSounds.DRONE_ENGINE_LOOP.get(),
                    (this.propellerSpeed/2)*0.01f, 1.0f);
        super.tick();
    }

    public boolean canFly() {
        return this.propellerSpeed > 18;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FollowOwnerTask(this));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0f).add(Attributes.FLYING_SPEED, 3.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f);
    }

    /*
     * Immidiatly drop DRONE_ITEM if hit by owner, or apply decreased damage
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        Vec3 pos = this.getPosition(0);

        if (this.level().isClientSide) return true;
        if (source.is(DamageTypes.PLAYER_ATTACK) && this.owner.equals(source.getEntity())) {
            this.spawnAtLocation(DroneUtils.DRONE_ITEM_STACK);
            DroneUtils.spawnParticles(pos, this.level(), ParticleTypes.SMOKE, 10, 20);
            this.discard();
        } else {
            DroneUtils.spawnParticles(pos, this.level(), ParticleTypes.EXPLOSION, 1, 3);
            super.actuallyHurt(source, amount / 100);
        }

        return true;
    }

    /* 
     * Hurt Entities when pushed
     */
    @Override
    public void push(Entity entity) {
        if (entity instanceof DroneEntity) return;
        entity.hurt(DroneUtils.DRONE_BLADES_DAMAGE_SOURCE, 1.0f);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        owner = DroneOwner.byUUID(this.level(), tag.getUUID("owner_uuid"));
        propellerSpeed = tag.getFloat("propeller_speed");
        currentTask = DroneTask.values()[tag.getInt("drone_task")];
        super.readAdditionalSaveData(tag);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putUUID("owner_uuid", owner.getUUID());
        tag.putFloat("propeller_speed", propellerSpeed);
        tag.putInt("drone_task", currentTask.value);
        super.addAdditionalSaveData(tag);
    }

    @Override
    public boolean isFlying() {
        return !this.onGround();
    }

    /* 
     * Drone is flying entity, so no fall damage should be applied
     */
    @Override
    protected void checkFallDamage(double p_20990_, boolean p_20991_, BlockState p_20992_, BlockPos p_20993_) {
    }

    @Override
    public boolean canBeLeashed(Player p_21418_) {
        return false;
    } 
}