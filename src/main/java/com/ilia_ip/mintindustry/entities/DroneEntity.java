package com.ilia_ip.mintindustry.entities;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;
import com.ilia_ip.mintindustry.items.ItemInit;
import com.mojang.logging.LogUtils;

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
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class DroneEntity extends PathfinderMob implements FlyingAnimal {
    public Player owner = null;
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
        return super.finalizeSpawn(server, difficulty, spawnType, spawnData, compound);
    }

    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, level);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FollowGoal(this));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        Vec3 pos = this.getPosition(0);
        if (source.is(DamageTypes.PLAYER_ATTACK)) {
            ItemStack items = new ItemStack(ItemInit.DRONE_ITEM.get(), 2);
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

    // ========================= ETC ============================

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

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0f).add(Attributes.FLYING_SPEED, 0.4f);
    }

    @Override
    public void push(double p_20286_, double p_20287_, double p_20288_) {
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }
}

class FollowGoal extends Goal {
    protected final PathfinderMob mob;
    protected Player player;
    private boolean isRunning;
    private final Ingredient items;

    public FollowGoal(PathfinderMob mob) {
        this.mob = mob;
        this.items = Ingredient.of(Items.ACACIA_BOAT);
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean canUse() {
        if (player == null) {
            player = this.mob.level().getNearestPlayer(mob, 10.0f);
            return false;
        }
        return this.player.isHolding(this.items);
    }

    // public boolean canContinueToUse() {
    // return this.canUse();
    // }

    public void start() {
        this.isRunning = true;
    }

    public void stop() {
        this.mob.getNavigation().stop();
        this.isRunning = false;
    }

    public void tick() {
        this.mob.getLookControl().setLookAt(this.player, (float) (this.mob.getMaxHeadYRot() + 20),
                (float) this.mob.getMaxHeadXRot());
        if (this.mob.distanceToSqr(this.player) < 3.0D && this.mob.position().y - this.player.position().y == 1) {
            this.mob.getNavigation().stop();
        } else {
            Vec3 pos = this.player.position();
            this.mob.getNavigation().moveTo(pos.x, pos.y + 2, pos.z, 1.0D);
        }

    }

    public boolean isRunning() {
        return this.isRunning;
    }
}