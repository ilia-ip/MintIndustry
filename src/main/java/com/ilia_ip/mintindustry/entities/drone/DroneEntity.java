package com.ilia_ip.mintindustry.entities.drone;

import java.util.List;

import com.ilia_ip.mintindustry.core.ModSounds;
import com.ilia_ip.mintindustry.entities.drone.tasks.FollowOwnerTask;
import com.ilia_ip.mintindustry.entities.drone.tasks.RechargeTask;
import com.ilia_ip.mintindustry.util.DroneOwner;
import com.ilia_ip.mintindustry.util.DroneUtils;
import com.mojang.logging.LogUtils;

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
    private DroneOwner owner;

    // Some data
    private float propellerSpeed;
    private float powerLevel;

    // Task system
    private DroneTask currentTask;
    private DroneTasks currentTaskType;
    private List<DroneTask> tasks;

    public DroneEntity(EntityType<DroneEntity> type, Level level) {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this, 65, true);
        this.currentTaskType = DroneTasks.FOLLOWING_PLAYER;
        this.propellerSpeed = 0;
        this.powerLevel = 0;
        this.tasks = List.of(new FollowOwnerTask(this), new RechargeTask(this));
        this.currentTask = this.tasks.get(0);
    }

    // New method
    public void setTask(DroneTasks task) {
        this.currentTaskType = task;
    }

    // New method
    public DroneOwner getOwner() {
        return this.owner;
    }

    // New method
    public float getPropellerSpeed() {
        return this.propellerSpeed;
    }

    // New method
    public float getPowerLevel() {
        return this.powerLevel;
    }

    // New method
    public void setPowerLevel(float powerLevel) {
        this.powerLevel = powerLevel;
    }

    // New method 
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor server, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnData, CompoundTag compound) {
        this.owner = DroneOwner.getOwner(this.level(), this);
        return spawnData;
    }

    // New method
    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, level);
        flyingpathnavigation.setCanOpenDoors(true);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    // New method
    @Override
    public void tick() {
        LogUtils.getLogger().atWarn().log("DRONE ENTITY LOG [ prop speed: " + this.propellerSpeed + " power: " + this.powerLevel + " task: " + this.currentTaskType + " ]");
        if (this.propellerSpeed < DroneUtils.MAX_PROPELLER_SPEED && this.powerLevel > 5) {
            this.propellerSpeed += 0.05;
        } else if (this.powerLevel < 5 && this.propellerSpeed > 0) {
            this.propellerSpeed -= 0.05;
        }

        if (this.powerLevel > 0)this.powerLevel -= this.propellerSpeed/1000;

        // Plays sound with volume based on propeller speed (from 0.01 to 0.10)
        this.playSound(ModSounds.DRONE_ENGINE_LOOP.get(),
                    (this.propellerSpeed/2)*0.01f, 1.0f);


        if (!this.currentTask.canContinueToUse(currentTaskType)) {
            LogUtils.getLogger().atWarn().log("sadsadsadasdsd ssd sa dasd asd ds dsd sd ");
            int maxPriority = 0;
            currentTaskType = DroneTasks.IDLE;
            for (DroneTask task : tasks) {
                if (task.canUse(currentTaskType) && task.PRIORITY > maxPriority) {
                    currentTask = task;
                    maxPriority = task.PRIORITY;
                    currentTaskType = task.TYPE;
                    LogUtils.getLogger().atWarn().log("SDSDADASDSD FAFFSFSF");
                }
            }
        }        
        currentTask.tick();
        
        super.tick();
    }

    public boolean canFly() {
        return this.propellerSpeed > (DroneUtils.MAX_PROPELLER_SPEED-5) && this.powerLevel > 5;
    }

    @Override
    protected void registerGoals() {
    }

    // New method
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
        currentTaskType = DroneTasks.values()[tag.getInt("drone_task")];
        super.readAdditionalSaveData(tag);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putUUID("owner_uuid", owner.getUUID());
        tag.putFloat("propeller_speed", propellerSpeed);
        tag.putInt("drone_task", currentTaskType.value);
        super.addAdditionalSaveData(tag);
    }

    @Override
    public boolean isFlying() {
        return !this.onGround();
    }

    // New method
    @Override
    protected void checkFallDamage(double p_20990_, boolean p_20991_, BlockState p_20992_, BlockPos p_20993_) {
    }

    // New method
    @Override
    public boolean canBeLeashed(Player p_21418_) {
        return false;
    } 
}