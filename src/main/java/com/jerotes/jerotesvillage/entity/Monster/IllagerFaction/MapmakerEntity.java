package com.jerotes.jerotesvillage.entity.Monster.IllagerFaction;

import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesAttackAvoidEntityGoal;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellFind;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;

import java.util.List;

public class MapmakerEntity extends SpellIllagerEntity implements SpellUseEntity {
    private static final EntityDataAccessor<Integer> SWEAT_TICK = SynchedEntityData.defineId(MapmakerEntity.class, EntityDataSerializers.INT);

    public MapmakerEntity(EntityType<? extends MapmakerEntity> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 20;
        this.setCanPickUpLoot(false);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
        builder = builder.add(Attributes.MAX_HEALTH, 24);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 2);
        builder = builder.add(Attributes.FOLLOW_RANGE, 32);
        return builder;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.2));
        this.goalSelector.addGoal(2, new JerotesAttackAvoidEntityGoal<LivingEntity>(this, LivingEntity.class, 8.0f, 0.6, 1.0));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, HumanEntity.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Mob.class, 8.0f));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, false){
            @Override
            public boolean canUse() {
                if (MapmakerEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
                    return false;
                }
                return super.canUse();
            }
        });
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<HumanEntity>(this, HumanEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, false));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return JerotesVillageSoundEvents.MAPMAKER_AMBIENT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return JerotesVillageSoundEvents.MAPMAKER_DEATH;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return JerotesVillageSoundEvents.MAPMAKER_HURT;
    }
    @Override
    public SoundEvent getCelebrateSound() {
        return JerotesVillageSoundEvents.MAPMAKER_CHEER;
    }
    @Override
    protected SoundEvent getCastingSoundEvent() {
        return JerotesVillageSoundEvents.MAPMAKER_SPELL;
    }
    @Override
    public IllagerSpell customSpell() {
        return IllagerSpell.SUMMON_VEX;
    }

    public int spellLevel = 1;
    @Override
    public int getSpellLevel() {
        return this.spellLevel;
    }
    public void setSweatTick(int n){
        this.getEntityData().set(SWEAT_TICK, n);
    }
    public int getSweatTick(){
        return this.getEntityData().get(SWEAT_TICK);
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("SweatTick", this.getSweatTick());
        compoundTag.putInt("SpellLevel", this.spellLevel);
        this.writeInventoryToTag(compoundTag);
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setSweatTick(compoundTag.getInt("SweatTick"));
        this.spellLevel = compoundTag.getInt("SpellLevel");
        this.readInventoryFromTag(compoundTag);
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(SWEAT_TICK, 0);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide()) {
            this.setSweatTick(Math.max(0, this.getSweatTick() - 1));
        }
        if (this.getRandom().nextInt(12) == 1 && (this.getTarget() != null || this.isAggressive() || this.getSweatTick() > 0) && this.deathTime == 0) {
            this.addParticlesAroundSelf(ParticleTypes.SPLASH);
        }
        //队伍
        List<LivingEntity> listRaider = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(32.0, 32.0, 32.0));
        listRaider.removeIf(entity -> this.getTarget() == entity || entity == this || entity instanceof Mob mob && mob.getTarget() == this || !(EntityFactionFind.isRaider(entity) && this.getTeam() == null && entity.getTeam() == null || (entity.isAlliedTo(this))));

        if ((this.getTarget() != null || this.isAggressive() || (this.level() instanceof ServerLevel serverLevel && serverLevel.isRaided(this.getOnPos()))) && !listRaider.isEmpty() && this.random.nextInt(180) == 1 && !this.isCastingSpell()) {
            if (!this.level().isClientSide()) {
                this.setSpellTick(20);
            }
        }
        if (this.isCastingSpell()){
            if (this.level() instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 6; ++i) {
                    serverLevel.sendParticles(ParticleTypes.ENCHANT, this.getRandomX(1f), this.getRandomY(), this.getRandomZ(1f), 0, 0, 0.0, 0, 0.0);
                }
            }
        }
        if (this.getSpellTick() == 5) {
            OtherSpellFind.MapInference(this, this.getSpellLevel() * 12, this.getSpellLevel(), this.getSpellLevel() * 30, this.getSpellLevel(), 32);
            if (!this.isSilent()) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.MAPMAKER_SPELL, this.getSoundSource(), 5.0f, 0.8f + this.random.nextFloat() * 0.4f);
            }
        }
    }

    public void addParticlesAroundSelf(ParticleOptions particleOptions) {
        for (int i = 0; i < 5; ++i) {
            double d = this.random.nextGaussian() * 0.02;
            double d2 = this.random.nextGaussian() * 0.02;
            double d3 = this.random.nextGaussian() * 0.02;
            this.level().addParticle(particleOptions, this.getRandomX(1.0), this.getRandomY() + 1.0, this.getRandomZ(1.0), d, d2, d3);
        }
    }

    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if (isInvulnerableTo(damageSource)) {
            return super.hurt(damageSource, amount);
        }
        if (damageSource.getEntity() instanceof LivingEntity) {
            if (!this.level().isClientSide()) {
                this.setSweatTick(300);
            }
        }
        return super.hurt(damageSource, amount);
    }
}

