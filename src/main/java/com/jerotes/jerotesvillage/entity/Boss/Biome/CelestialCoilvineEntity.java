package com.jerotes.jerotesvillage.entity.Boss.Biome;

import com.jerotes.jerotes.client.sound.BossMusicPlayer;
import com.jerotes.jerotes.config.MainConfig;
import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.goal.JerotesHelpAlliesGoal;
import com.jerotes.jerotes.goal.JerotesHelpSameFactionGoal;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.control.CelestialCoilvineLookControl;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Botanize.BotanizeEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Botanize.CoilvineBotanizeEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.VinePodBomb.VinePodBombEntity;
import com.jerotes.jerotesvillage.entity.Neutral.Wonderlin.BaseWonderlinEntity;
import com.jerotes.jerotesvillage.event.BossBarEvent;
import com.jerotes.jerotesvillage.goal.CelestialCoilvineAttackGoal;
import com.jerotes.jerotesvillage.goal.CelestialCoilvineNearestAttackableTargetGoal;
import com.jerotes.jerotesvillage.init.*;
import com.jerotes.jerotesvillage.spell.OtherSpellFind;
import com.jerotes.jerotesvillage.spell.OtherSpellList;
import com.jerotes.jerotesvillage.util.Other;
import com.jerotes.jerotesvillage.util.OtherParticlesUse;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CelestialCoilvineEntity extends PathfinderMob implements NeutralMob, BossEntity, LoreUndyingEntity, SpellUseEntity, JerotesEntity, FactionEntity, StopLook {
	public AnimationState idleBaseAnimationState = new AnimationState();
	public AnimationState rotAnimationState = new AnimationState();
	public AnimationState attackAnimationState = new AnimationState();
	public AnimationState startAnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();
	public AnimationState hideAnimationState = new AnimationState();
	public AnimationState spell1AnimationState = new AnimationState();
	public AnimationState spell2AnimationState = new AnimationState();
	public AnimationState spell3AnimationState = new AnimationState();
	public AnimationState spell4AnimationState = new AnimationState();
	public AnimationState spell5AnimationState = new AnimationState();
	public AnimationState spell6AnimationState = new AnimationState();
	public AnimationState spell7AnimationState = new AnimationState();
	public AnimationState change1AnimationState = new AnimationState();
	public AnimationState change2AnimationState = new AnimationState();
	public AnimationState change3AnimationState = new AnimationState();
	public AnimationState change4AnimationState = new AnimationState();
	public AnimationState claw1AnimationState = new AnimationState();
	public AnimationState claw2AnimationState = new AnimationState();
	public AnimationState claw3AnimationState = new AnimationState();
	public AnimationState claw4AnimationState = new AnimationState();
	public AnimationState claw5AnimationState = new AnimationState();
	public AnimationState claw6AnimationState = new AnimationState();
	public AnimationState claw7AnimationState = new AnimationState();
	public AnimationState eye1AnimationState = new AnimationState();
	public AnimationState eye2AnimationState = new AnimationState();
	public AnimationState eye3AnimationState = new AnimationState();
	public AnimationState eye4AnimationState = new AnimationState();
	public AnimationState eye5AnimationState = new AnimationState();
	public AnimationState eye6AnimationState = new AnimationState();
	public AnimationState eye7AnimationState = new AnimationState();
	public AnimationState mouth1AnimationState = new AnimationState();
	public AnimationState mouth2AnimationState = new AnimationState();
	public AnimationState mouth3AnimationState = new AnimationState();
	public AnimationState mouth4AnimationState = new AnimationState();
	public AnimationState mouth5AnimationState = new AnimationState();
	public AnimationState mouth6AnimationState = new AnimationState();
	public AnimationState mouth7AnimationState = new AnimationState();
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.GREEN, false);
	private static final EntityDataAccessor<Integer> START_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> MELEE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IS_LOCK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_ARMOR = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> SPELL_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ARMOR_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Float> ARMOR_DAMAGE = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Boolean> IS_HIDE = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> HIDE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> HIDE_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SWORD_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SWORD_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> IMPRISON_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> IMPRISON_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BOTANIZE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BOTANIZE_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> FOG_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> FOG_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> UNDEAD_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> UNDEAD_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> HEAL_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> HEAL_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> TIME_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> TIME_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	//巨爪
	private static final EntityDataAccessor<Integer> SLAM_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SLAM_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> GRASP_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> GRASP_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ROOT_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ROOT_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> CENTER_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> CENTER_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SNAP_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SNAP_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> THROW_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> THROW_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> COLLAPSE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> COLLAPSE_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	//巨眼
	private static final EntityDataAccessor<Integer> BEAM_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BEAM_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ARROW_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ARROW_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DISARM_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DISARM_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> EIGHT_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> EIGHT_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> CLOUD_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> CLOUD_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> VERTIGO_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> VERTIGO_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> TORCH_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> TORCH_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	//巨嘴
	private static final EntityDataAccessor<Integer> FOUR_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> FOUR_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BITE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BITE_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> EAT_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> EAT_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> WILT_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> WILT_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BLOW_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BLOW_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SUCK_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SUCK_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BOMB_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BOMB_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	//转换
	private static final EntityDataAccessor<Integer> BODY_TYPE = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> LAST_BODY_TYPE = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BODY_TYPE_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> FIRST_HEALTH = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> CHANGE_BODY_TYPE_TO_FIRST_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> CHANGE_BODY_TYPE_TO_SECOND_USE_TICK = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);
	//
	private static final EntityDataAccessor<Boolean> IS_LEGEND = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> HEAD_X_ROT_ANIM = SynchedEntityData.defineId(CelestialCoilvineEntity.class, EntityDataSerializers.INT);

	public CelestialCoilvineEntity(EntityType<? extends CelestialCoilvineEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(0f);
		xpReward = 700;
		this.setJumping(false);
		if (OtherMainConfig.BossBaseAttributeCanUseConfig) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(OtherMainConfig.CelestialCoilvineMaxHealth);
			this.setHealth(this.getMaxHealth());
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(OtherMainConfig.CelestialCoilvineMeleeDamage);
			this.getAttribute(Attributes.ARMOR).setBaseValue(OtherMainConfig.CelestialCoilvineArmor);
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(OtherMainConfig.CelestialCoilvineMovementSpeed);
			this.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(OtherMainConfig.CelestialCoilvineAttackKnockback);
			this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(OtherMainConfig.CelestialCoilvineKnockbackResistance);
		}
		if (level.isClientSide()) {
			BossBarEvent.BOSSES.add(this);
		}
		this.lookControl = new CelestialCoilvineLookControl(this, 1);
		this.fixupDimensions();
	}
	//百分比
	public boolean hasPercentageDamage() {
		return MainConfig.HasPercentageDamage.contains(this.getEncodeId())
				|| OtherMainConfig.BossHasPercentageDamage.contains(this.getEncodeId()) || this.isLegend();
	}
	public float PercentageDamage(DamageSource damageSource) {
		if (AttackFind.MagicResistance(damageSource)) {
			return(float) OtherMainConfig.CelestialCoilvineMagicAttackPercentage;
		}
		return (float) OtherMainConfig.CelestialCoilvineAttackPercentage;
	}
	//限伤
	public boolean hasDamageCap() {
		return MainConfig.HasDamageCap.contains(this.getEncodeId()) || OtherMainConfig.BossHasDamageCap.contains(this.getEncodeId()) || this.isLegend();
	}
	public float DamageCap(DamageSource damageSource, Entity entity) {
		return (float) OtherMainConfig.CelestialCoilvineDamageCap;
	}
	//间隔
	public boolean hasDamageCooldownTick() {
		return MainConfig.HasDamageCooldownTick.contains(this.getEncodeId()) || OtherMainConfig.BossHasDamageCooldownTick.contains(this.getEncodeId()) || this.isLegend();
	}
	public float DamageCooldownTick(DamageSource damageSource, Entity entity) {
		float base = 1.0F;
		if (damageSource.is(DamageTypeTags.BYPASSES_COOLDOWN)) {
			base *= 0.5F;
		}
		return (float)OtherMainConfig.CelestialCoilvineDamageCooldownTick * base;
	}

	@Override
	public String getFirstFactionTypeName() {
		return "botanize_village";
	}

	@Override
	public void startSeenByPlayer(ServerPlayer serverPlayer) {
		super.startSeenByPlayer(serverPlayer);
		this.bossEvent.addPlayer(serverPlayer);
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer serverPlayer) {
		super.stopSeenByPlayer(serverPlayer);
		this.bossEvent.removePlayer(serverPlayer);
	}

	@Override
	public void customServerAiStep() {
		super.customServerAiStep();
		this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MAX_HEALTH, 420);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 18);
		builder = builder.add(Attributes.ARMOR, 15);
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 2.0);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0);

		builder = builder.add(Attributes.FOLLOW_RANGE, 128);
		return builder;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(2, new CelestialCoilvineAttackGoal(this));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, BotanizeEntity.class, 18.0f) {
			@Override
			public boolean canUse() {
				return super.canUse() && !CelestialCoilvineEntity.this.stopLookTime() && !CelestialCoilvineEntity.this.isAggressive();
			}
			@Override
			public boolean canContinueToUse() {
				return super.canContinueToUse() && !CelestialCoilvineEntity.this.stopLookTime() && !CelestialCoilvineEntity.this.isAggressive();
			}
		});
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, CelestialCoilvineEntity.class, 18.0f) {
			@Override
			public boolean canUse() {
				return super.canUse() && !CelestialCoilvineEntity.this.stopLookTime() && !CelestialCoilvineEntity.this.isAggressive();
			}
			@Override
			public boolean canContinueToUse() {
				return super.canContinueToUse() && !CelestialCoilvineEntity.this.stopLookTime() && !CelestialCoilvineEntity.this.isAggressive();
			}
		});
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, BaseWonderlinEntity.class, 18.0f) {
			@Override
			public boolean canUse() {
				return super.canUse() && !CelestialCoilvineEntity.this.stopLookTime() && !CelestialCoilvineEntity.this.isAggressive();
			}
			@Override
			public boolean canContinueToUse() {
				return super.canContinueToUse() && !CelestialCoilvineEntity.this.stopLookTime() && !CelestialCoilvineEntity.this.isAggressive();
			}
		});
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, VinePodBombEntity.class, 18.0f) {
			@Override
			public boolean canUse() {
				return super.canUse() && !CelestialCoilvineEntity.this.stopLookTime() && !CelestialCoilvineEntity.this.isAggressive();
			}
			@Override
			public boolean canContinueToUse() {
				return super.canContinueToUse() && !CelestialCoilvineEntity.this.stopLookTime() && !CelestialCoilvineEntity.this.isAggressive();
			}
		});
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 18.0f) {
			@Override
			public boolean canUse() {
				return super.canUse() && !CelestialCoilvineEntity.this.stopLookTime() && !CelestialCoilvineEntity.this.isAggressive();
			}
			@Override
			public boolean canContinueToUse() {
				return super.canContinueToUse() && !CelestialCoilvineEntity.this.stopLookTime() && !CelestialCoilvineEntity.this.isAggressive();
			}
		});
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, HumanEntity.class, 18.0f) {
			@Override
			public boolean canUse() {
				return super.canUse() && !CelestialCoilvineEntity.this.stopLookTime() && !CelestialCoilvineEntity.this.isAggressive();
			}
			@Override
			public boolean canContinueToUse() {
				return super.canContinueToUse() && !CelestialCoilvineEntity.this.stopLookTime() && !CelestialCoilvineEntity.this.isAggressive();
			}
		});
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, LivingEntity.class, 18.0f) {
			@Override
			public boolean canUse() {
				return super.canUse() && !CelestialCoilvineEntity.this.stopLookTime() && !CelestialCoilvineEntity.this.isAggressive();
			}
			@Override
			public boolean canContinueToUse() {
				return super.canContinueToUse() && !CelestialCoilvineEntity.this.stopLookTime() && !CelestialCoilvineEntity.this.isAggressive();
			}
		});
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				return super.canUse() && !CelestialCoilvineEntity.this.stopLookTime() && !CelestialCoilvineEntity.this.isAggressive();
			}
			@Override
			public boolean canContinueToUse() {
				return super.canContinueToUse() && !CelestialCoilvineEntity.this.stopLookTime() && !CelestialCoilvineEntity.this.isAggressive();
			}
		});
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
		this.targetSelector.addGoal(1, new JerotesHelpSameFactionGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, 5, false, false, livingEntity -> EntityFactionFind.isHateFaction(this, livingEntity)));
		this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(3, new CelestialCoilvineNearestAttackableTargetGoal<>(this, LivingEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<CelestialCoilvineEntity>(this, false));
	}

	public static boolean isCelestialCoilvineTarget(LivingEntity self, LivingEntity livingEntity) {
		return !AttackFind.FindCanNotAttack(self, livingEntity) &&
				livingEntity instanceof Enemy &&
				livingEntity.level().getBiome(BlockPos.containing(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ())).is(JerotesVillageBiomes.HARMONIOUS_WONDERLAND);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.CELESTIAL_COILVINE_AMBIENT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.CELESTIAL_COILVINE_DEATH;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.CELESTIAL_COILVINE_HURT;
	}
	public float getYRot() {
		return this.getYHeadRot();
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		if (this.isHide()) {
			return 0.1f;
		}
		else if (this.getBodyType() == 2) {
			return 6.75f;
		}
		else if (this.getBodyType() == 3) {
			return 4.5f;
		}
		else if (this.getBodyType() == 4) {
			return 6.75f;
		}
		return 4f;
	}
	public AABB getAttackBoundingBox() {
		Entity entity = this.getVehicle();
		AABB aabb;
		if (entity != null) {
			AABB aabb1 = entity.getBoundingBox();
			AABB aabb2 = this.getBoundingBox();
			aabb = new AABB(Math.min(aabb2.minX, aabb1.minX), aabb2.minY, Math.min(aabb2.minZ, aabb1.minZ), Math.max(aabb2.maxX, aabb1.maxX), aabb2.maxY, Math.max(aabb2.maxZ, aabb1.maxZ));
		} else {
			aabb = this.getBoundingBox();
		}
		AABB aabb1 = aabb.inflate(Math.sqrt((double)2.04F) - (double)0.6F, 0.0D, Math.sqrt((double)2.04F) - (double)0.6F);
		return aabb1.inflate(0.75d, 0.125d, 0.75d).deflate(0, 2f, 0).move(0, -2f, 0);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}
	@Override
	protected float getSoundVolume() {
		return 5.0f;
	}
	@Override
	public void travel(Vec3 vec3) {
		super.travel(vec3);
		if (this.isLock()) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(0d, 0d, 0d));
		}
	}
	@Override
	protected boolean canRide(Entity entity) {
		return super.canRide(entity) && !this.isLock();
	}
	@Override
	public boolean canChangeDimensions() {
		return super.canChangeDimensions() && !this.isLock();
	}
	@Override
	public void setDeltaMovement(Vec3 vec3) {
		if (this.isLock()) {
			super.setDeltaMovement(new Vec3(0, 0, 0));
		}
		else {
			super.setDeltaMovement(new Vec3(0, vec3.y, 0));
		}
	}
	//
	@Override
	public boolean isPushedByFluid() {
		return false;
	}
	@Override
	public void makeStuckInBlock(BlockState blockState, Vec3 vec3) {
		this.resetFallDistance();
	}
	@Override
	public boolean isInvisible() {
		return super.isInvisible() || (this.getHideUseTick() <= 80 && this.getHideUseTick() > 18);
	}
	@Override
	public boolean isPushable() {
		return false;
	}
	@Override
	public int getMaxHeadYRot() {
		return 360;
	}
	@Override
	public int getMaxHeadXRot() {
		return 360;
	}
	@Override
	public void stopRiding() {
		super.stopRiding();
		this.yBodyRotO = 0.0f;
		this.yBodyRot = 0.0f;
	}
	@Override
	public void recreateFromPacket(ClientboundAddEntityPacket clientboundAddEntityPacket) {
		super.recreateFromPacket(clientboundAddEntityPacket);
		this.yBodyRot = 0.0f;
		this.yBodyRotO = 0.0f;
	}
	@Override
	public void knockback(double d, double d2, double d3) {
		super.knockback(0, 0, 0);
	}
	@Override
	public void push(double d, double d2, double d3) {
		super.push(0, 0, 0);
	}
	@Override
	public SoundEvent getBossMusic() {
		return JerotesVillageSoundEvents.CELESTIAL_COILVINE_MUSIC;
	}
	@Override
	protected BodyRotationControl createBodyControl() {
		return new CelestialCoilvineBodyRotationControl(this);
	}
	@Override
	public boolean removeWhenFarAway(double d) {
		return false;
	}
	@Override
	protected boolean shouldDespawnInPeaceful() {
		return false;
	}
	//体型相关
	@Override
	public EntityDimensions getDimensions(Pose pose) {
		if (this.getHideUseTick() <= 80 && this.getHideUseTick() > 20) {
			return super.getDimensions(pose).scale(0.1f);
		}
		else if (this.isHide()) {
			return super.getDimensions(pose).scale(0.05f);
		}
		else if (this.getBodyType() == 2) {
			return super.getDimensions(pose).scale(1f, 1.75f);
		}
		else if (this.getBodyType() == 3) {
			return super.getDimensions(pose).scale(1f, 1.2f);
		}
		else if (this.getBodyType() == 4) {
			return super.getDimensions(pose).scale(1f, 1.75f);
		}
		return super.getDimensions(pose);
	}

	public float thisTickRenderTime = 0;
	public float lastTickRenderTime = 6;
	public float headXRotAnimProgress = 0.0f;
	public void setStartTick(int n){
		this.getEntityData().set(START_TICK, n);
	}
	public int getStartTick(){
		return this.getEntityData().get(START_TICK);
	}
	public int spellLevel = OtherMainConfig.CelestialCoilvineSpellNormalLevel;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	public void setMeleeTick(int n){
		this.getEntityData().set(MELEE_TICK, n);
	}
	public int getMeleeTick(){
		return this.getEntityData().get(MELEE_TICK);
	}
	public void setLock(boolean bl){
		this.getEntityData().set(IS_LOCK, bl);
	}
	public boolean isLock(){
		return this.getEntityData().get(IS_LOCK);
	}
	public List<LivingEntity> needMelee(){
		List<LivingEntity> needMelee = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox());
		needMelee.removeIf(livingEntity -> livingEntity instanceof Mob mob && !isCelestialCoilvineTarget(this, mob) && this.getTarget() != mob && mob.getTarget() != this && !(mob.getTarget() != null && mob.getTarget().isAlliedTo(this)) && !(this.getTarget() != null && mob.isAlliedTo(this.getTarget())));
		needMelee.removeIf(livingEntity -> livingEntity instanceof Player player && this.getTarget() != player && !(this.getTarget() != null && player.isAlliedTo(this.getTarget())));
		needMelee.removeIf(livingEntity -> !(livingEntity instanceof Player || livingEntity instanceof Mob) || !this.hasLineOfSight(livingEntity));
		return needMelee;
	}
	public void setSpellTick(int n){
		this.getEntityData().set(SPELL_TICK, n);
	}
	public int getSpellTick(){
		return this.getEntityData().get(SPELL_TICK);
	}
	public float attackFindXRot;
	public float attackFindYRot;
	//技能
	public void setArmor(boolean bl){
		this.getEntityData().set(IS_ARMOR, bl);
	}
	public boolean isArmor(){
		return this.getEntityData().get(IS_ARMOR);
	}
	public void setArmorTick(int n){
		this.getEntityData().set(ARMOR_TICK, n);
	}
	public int getArmorTick(){
		return this.getEntityData().get(ARMOR_TICK);
	}
	public void setArmorDamage(float f){
		this.getEntityData().set(ARMOR_DAMAGE, f);
	}
	public float getArmorDamage(){
		return this.getEntityData().get(ARMOR_DAMAGE);
	}
	public void setHide(boolean bl){
		this.getEntityData().set(IS_HIDE, bl);
		this.reapplyPosition();
		this.refreshDimensions();
	}
	@Override
	public void refreshDimensions() {
		double d = this.getX();
		double d2 = this.getY();
		double d3 = this.getZ();
		super.refreshDimensions();
		this.setPos(d, d2, d3);
	}
	public boolean isHide(){
		return this.getEntityData().get(IS_HIDE);
	}
	public void setHideTick(int n){
		this.getEntityData().set(HIDE_TICK, n);
	}
	public int getHideTick(){
		return this.getEntityData().get(HIDE_TICK);
	}
	public void setHideUseTick(int n){
		this.getEntityData().set(HIDE_USE_TICK, n);
	}
	public int getHideUseTick(){
		return this.getEntityData().get(HIDE_USE_TICK);
	}
	public void setSwordTick(int n){
		this.getEntityData().set(SWORD_TICK, n);
	}
	public int getSwordTick(){
		return this.getEntityData().get(SWORD_TICK);
	}
	public void setSwordUseTick(int n){
		this.getEntityData().set(SWORD_USE_TICK, n);
	}
	public int getSwordUseTick(){
		return this.getEntityData().get(SWORD_USE_TICK);
	}
	public void setImprisonTick(int n){
		this.getEntityData().set(IMPRISON_TICK, n);
	}
	public int getImprisonTick(){
		return this.getEntityData().get(IMPRISON_TICK);
	}
	public void setImprisonUseTick(int n){
		this.getEntityData().set(IMPRISON_USE_TICK, n);
	}
	public int getImprisonUseTick(){
		return this.getEntityData().get(IMPRISON_USE_TICK);
	}
	public void setBotanizeTick(int n){
		this.getEntityData().set(BOTANIZE_TICK, n);
	}
	public int getBotanizeTick(){
		return this.getEntityData().get(BOTANIZE_TICK);
	}
	public void setBotanizeUseTick(int n){
		this.getEntityData().set(BOTANIZE_USE_TICK, n);
	}
	public int getBotanizeUseTick(){
		return this.getEntityData().get(BOTANIZE_USE_TICK);
	}
	public void setFogTick(int n){
		this.getEntityData().set(FOG_TICK, n);
	}
	public int getFogTick(){
		return this.getEntityData().get(FOG_TICK);
	}
	public void setFogUseTick(int n){
		this.getEntityData().set(FOG_USE_TICK, n);
	}
	public int getFogUseTick(){
		return this.getEntityData().get(FOG_USE_TICK);
	}
	public void setUndeadTick(int n){
		this.getEntityData().set(UNDEAD_TICK, n);
	}
	public int getUndeadTick(){
		return this.getEntityData().get(UNDEAD_TICK);
	}
	public void setUndeadUseTick(int n){
		this.getEntityData().set(UNDEAD_USE_TICK, n);
	}
	public int getUndeadUseTick(){
		return this.getEntityData().get(UNDEAD_USE_TICK);
	}
	public void setHealTick(int n){
		this.getEntityData().set(HEAL_TICK, n);
	}
	public int getHealTick(){
		return this.getEntityData().get(HEAL_TICK);
	}
	public void setHealUseTick(int n){
		this.getEntityData().set(HEAL_USE_TICK, n);
	}
	public int getHealUseTick(){
		return this.getEntityData().get(HEAL_USE_TICK);
	}
	public void setTimeTick(int n){
		this.getEntityData().set(TIME_TICK, n);
	}
	public int getTimeTick(){
		return this.getEntityData().get(TIME_TICK);
	}
	public void setTimeUseTick(int n){
		this.getEntityData().set(TIME_USE_TICK, n);
	}
	public int getTimeUseTick(){
		return this.getEntityData().get(TIME_USE_TICK);
	}

	public void setSlamTick(int n){
		this.getEntityData().set(SLAM_TICK, n);
	}
	public int getSlamTick(){
		return this.getEntityData().get(SLAM_TICK);
	}
	public void setSlamUseTick(int n){
		this.getEntityData().set(SLAM_USE_TICK, n);
	}
	public int getSlamUseTick(){
		return this.getEntityData().get(SLAM_USE_TICK);
	}
	public void setGraspTick(int n){
		this.getEntityData().set(GRASP_TICK, n);
	}
	public int getGraspTick(){
		return this.getEntityData().get(GRASP_TICK);
	}
	public void setGraspUseTick(int n){
		this.getEntityData().set(GRASP_USE_TICK, n);
	}
	public int getGraspUseTick(){
		return this.getEntityData().get(GRASP_USE_TICK);
	}
	public void setRootTick(int n){
		this.getEntityData().set(ROOT_TICK, n);
	}
	public int getRootTick(){
		return this.getEntityData().get(ROOT_TICK);
	}
	public void setRootUseTick(int n){
		this.getEntityData().set(ROOT_USE_TICK, n);
	}
	public int getRootUseTick(){
		return this.getEntityData().get(ROOT_USE_TICK);
	}
	public void setCenterTick(int n){
		this.getEntityData().set(CENTER_TICK, n);
	}
	public int getCenterTick(){
		return this.getEntityData().get(CENTER_TICK);
	}
	public void setCenterUseTick(int n){
		this.getEntityData().set(CENTER_USE_TICK, n);
	}
	public int getCenterUseTick(){
		return this.getEntityData().get(CENTER_USE_TICK);
	}
	public void setSnapTick(int n){
		this.getEntityData().set(SNAP_TICK, n);
	}
	public int getSnapTick(){
		return this.getEntityData().get(SNAP_TICK);
	}
	public void setSnapUseTick(int n){
		this.getEntityData().set(SNAP_USE_TICK, n);
	}
	public int getSnapUseTick(){
		return this.getEntityData().get(SNAP_USE_TICK);
	}
	public void setThrowTick(int n){
		this.getEntityData().set(THROW_TICK, n);
	}
	public int getThrowTick(){
		return this.getEntityData().get(THROW_TICK);
	}
	public void setThrowUseTick(int n){
		this.getEntityData().set(THROW_USE_TICK, n);
	}
	public int getThrowUseTick(){
		return this.getEntityData().get(THROW_USE_TICK);
	}
	public void setCollapseTick(int n){
		this.getEntityData().set(COLLAPSE_TICK, n);
	}
	public int getCollapseTick(){
		return this.getEntityData().get(COLLAPSE_TICK);
	}
	public void setCollapseUseTick(int n){
		this.getEntityData().set(COLLAPSE_USE_TICK, n);
	}
	public int getCollapseUseTick(){
		return this.getEntityData().get(COLLAPSE_USE_TICK);
	}

	public void setBeamTick(int n){
		this.getEntityData().set(BEAM_TICK, n);
	}
	public int getBeamTick(){
		return this.getEntityData().get(BEAM_TICK);
	}
	public void setBeamUseTick(int n){
		this.getEntityData().set(BEAM_USE_TICK, n);
	}
	public int getBeamUseTick(){
		return this.getEntityData().get(BEAM_USE_TICK);
	}
	public void setArrowTick(int n){
		this.getEntityData().set(ARROW_TICK, n);
	}
	public int getArrowTick(){
		return this.getEntityData().get(ARROW_TICK);
	}
	public void setArrowUseTick(int n){
		this.getEntityData().set(ARROW_USE_TICK, n);
	}
	public int getArrowUseTick(){
		return this.getEntityData().get(ARROW_USE_TICK);
	}
	public void setDisarmTick(int n){
		this.getEntityData().set(DISARM_TICK, n);
	}
	public int getDisarmTick(){
		return this.getEntityData().get(DISARM_TICK);
	}
	public void setDisarmUseTick(int n){
		this.getEntityData().set(DISARM_USE_TICK, n);
	}
	public int getDisarmUseTick(){
		return this.getEntityData().get(DISARM_USE_TICK);
	}
	public void setEightTick(int n){
		this.getEntityData().set(EIGHT_TICK, n);
	}
	public int getEightTick(){
		return this.getEntityData().get(EIGHT_TICK);
	}
	public void setEightUseTick(int n){
		this.getEntityData().set(EIGHT_USE_TICK, n);
	}
	public int getEightUseTick(){
		return this.getEntityData().get(EIGHT_USE_TICK);
	}
	public void setCloudTick(int n){
		this.getEntityData().set(CLOUD_TICK, n);
	}
	public int getCloudTick(){
		return this.getEntityData().get(CLOUD_TICK);
	}
	public void setCloudUseTick(int n){
		this.getEntityData().set(CLOUD_USE_TICK, n);
	}
	public int getCloudUseTick(){
		return this.getEntityData().get(CLOUD_USE_TICK);
	}
	public void setVertigoTick(int n){
		this.getEntityData().set(VERTIGO_TICK, n);
	}
	public int getVertigoTick(){
		return this.getEntityData().get(VERTIGO_TICK);
	}
	public void setVertigoUseTick(int n){
		this.getEntityData().set(VERTIGO_USE_TICK, n);
	}
	public int getVertigoUseTick(){
		return this.getEntityData().get(VERTIGO_USE_TICK);
	}
	public void setTorchTick(int n){
		this.getEntityData().set(TORCH_TICK, n);
	}
	public int getTorchTick(){
		return this.getEntityData().get(TORCH_TICK);
	}
	public void setTorchUseTick(int n){
		this.getEntityData().set(TORCH_USE_TICK, n);
	}
	public int getTorchUseTick(){
		return this.getEntityData().get(TORCH_USE_TICK);
	}

	public void setFourTick(int n){
		this.getEntityData().set(FOUR_TICK, n);
	}
	public int getFourTick(){
		return this.getEntityData().get(FOUR_TICK);
	}
	public void setFourUseTick(int n){
		this.getEntityData().set(FOUR_USE_TICK, n);
	}
	public int getFourUseTick(){
		return this.getEntityData().get(FOUR_USE_TICK);
	}
	public void setBiteTick(int n){
		this.getEntityData().set(BITE_TICK, n);
	}
	public int getBiteTick(){
		return this.getEntityData().get(BITE_TICK);
	}
	public void setBiteUseTick(int n){
		this.getEntityData().set(BITE_USE_TICK, n);
	}
	public int getBiteUseTick(){
		return this.getEntityData().get(BITE_USE_TICK);
	}
	public void setEatTick(int n){
		this.getEntityData().set(EAT_TICK, n);
	}
	public int getEatTick(){
		return this.getEntityData().get(EAT_TICK);
	}
	public void setEatUseTick(int n){
		this.getEntityData().set(EAT_USE_TICK, n);
	}
	public int getEatUseTick(){
		return this.getEntityData().get(EAT_USE_TICK);
	}
	public void setWiltTick(int n){
		this.getEntityData().set(WILT_TICK, n);
	}
	public int getWiltTick(){
		return this.getEntityData().get(WILT_TICK);
	}
	public void setWiltUseTick(int n){
		this.getEntityData().set(WILT_USE_TICK, n);
	}
	public int getWiltUseTick(){
		return this.getEntityData().get(WILT_USE_TICK);
	}
	public void setBlowTick(int n){
		this.getEntityData().set(BLOW_TICK, n);
	}
	public int getBlowTick(){
		return this.getEntityData().get(BLOW_TICK);
	}
	public void setBlowUseTick(int n){
		this.getEntityData().set(BLOW_USE_TICK, n);
	}
	public int getBlowUseTick(){
		return this.getEntityData().get(BLOW_USE_TICK);
	}
	public void setSuckTick(int n){
		this.getEntityData().set(SUCK_TICK, n);
	}
	public int getSuckTick(){
		return this.getEntityData().get(SUCK_TICK);
	}
	public void setSuckUseTick(int n){
		this.getEntityData().set(SUCK_USE_TICK, n);
	}
	public int getSuckUseTick(){
		return this.getEntityData().get(SUCK_USE_TICK);
	}
	public void setBombTick(int n){
		this.getEntityData().set(BOMB_TICK, n);
	}
	public int getBombTick(){
		return this.getEntityData().get(BOMB_TICK);
	}
	public void setBombUseTick(int n){
		this.getEntityData().set(BOMB_USE_TICK, n);
	}
	public int getBombUseTick(){
		return this.getEntityData().get(BOMB_USE_TICK);
	}
	//
	public int getHeadXRotAnim() {
		return this.getEntityData().get(HEAD_X_ROT_ANIM);
	}
	public void setHeadXRotAnim(int n) {
		this.getEntityData().set(HEAD_X_ROT_ANIM, n);
	}
	//阶段
	public void setBodyType(int n){
		this.getEntityData().set(BODY_TYPE, n);
		this.deathTime = 0;
		this.hurtTime = 0;
	}
	public int getBodyType(){
		return this.getEntityData().get(BODY_TYPE);
	}
	public void setLastBodyType(int n){
		this.getEntityData().set(LAST_BODY_TYPE, n);
	}
	public int getLastBodyType(){
		return this.getEntityData().get(LAST_BODY_TYPE);
	}
	public void setBodyTypeUseTick(int n){
		this.getEntityData().set(BODY_TYPE_USE_TICK, n);
	}
	public int getBodyTypeUseTick(){
		return this.getEntityData().get(BODY_TYPE_USE_TICK);
	}
	public boolean isSecondType(){
		return this.getEntityData().get(BODY_TYPE) != 1;
	}
	public void setFirstHeal(boolean bl){
		this.getEntityData().set(FIRST_HEALTH, bl);
	}
	public boolean isFirstHeal(){
		return this.getEntityData().get(FIRST_HEALTH);
	}
	public void setChangeBodyTypeToFirstUseTick(int n){
		this.getEntityData().set(CHANGE_BODY_TYPE_TO_FIRST_USE_TICK, n);
	}
	public int getChangeBodyTypeToFirstUseTick(){
		return this.getEntityData().get(CHANGE_BODY_TYPE_TO_FIRST_USE_TICK);
	}
	public void setChangeBodyTypeToSecondUseTick(int n){
		this.getEntityData().set(CHANGE_BODY_TYPE_TO_SECOND_USE_TICK, n);
	}
	public int getChangeBodyTypeToSecondUseTick(){
		return this.getEntityData().get(CHANGE_BODY_TYPE_TO_SECOND_USE_TICK);
	}
	//
	public boolean specialAction() {
		return this.isHide()
				|| this.getHideUseTick() > 0
				|| this.getSwordUseTick() > 0
				|| this.getImprisonUseTick() > 0
				|| this.getBotanizeUseTick() > 0
				|| this.getFogUseTick() > 0
				|| this.getUndeadUseTick() > 0
				|| this.getHealUseTick() > 0
				|| this.getTimeUseTick() > 0

				|| this.getSlamUseTick() > 0
				|| this.getGraspUseTick() > 0
				|| this.getRootUseTick() > 0
				|| this.getCenterUseTick() > 0
				|| this.getSnapUseTick() > 0
				|| this.getThrowUseTick() > 0
				|| this.getCollapseUseTick() > 0

				|| this.getBeamUseTick() > 0
				|| this.getArrowUseTick() > 0
				|| this.getDisarmUseTick() > 0
				|| this.getEightUseTick() > 0
				|| this.getCloudUseTick() > 0
				|| this.getVertigoUseTick() > 0
				|| this.getTorchUseTick() > 0

				|| this.getFourUseTick() > 0
				|| this.getBiteUseTick() > 0
				|| this.getEatUseTick() > 0
				|| this.getWiltUseTick() > 0
				|| this.getBlowUseTick() > 0
				|| this.getSuckUseTick() > 0
				|| this.getBombUseTick() > 0

				|| this.getBodyTypeUseTick() > 0;
	}
	public boolean stopLookTime() {
		return this.isHide()
				|| this.getHideUseTick() > 0

				|| this.getFourUseTick() > 0
				|| this.getSlamUseTick() > 0
				|| this.getGraspUseTick() > 0
				|| this.getCollapseUseTick() > 0
				|| (this.getBeamUseTick() <= 260 && this.getBeamUseTick() > 240)

				|| this.getBodyTypeUseTick() > 0;
	}
	public boolean wantMelee() {
		if (this.getBodyType() == 3)
			return false;
		if (this.getBodyType() == 2 || this.getBodyType() == 4)
			return true;
		return !this.isHide() && !(this.specialAction() && this.getHealUseTick() <= 0);
	}
	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
		this.bossEvent.setName(this.getDisplayName());
	}
	//动画
	public void setAnimTick(int n){
		this.getEntityData().set(ANIM_TICK, n);
	}
	public int getAnimTick(){
		return this.getEntityData().get(ANIM_TICK);
	}
	public void setAnimationState(String input) {
		this.setAnimationState(this.getAnimationState(input));
	}
	public void setAnimationState(int id) {
		this.entityData.set(ANIM_STATE, id);
	}
	public int getAnimationState(String animation) {
		if (Objects.equals(animation, "attack")){
			return 1;
		}
		else if (Objects.equals(animation, "start")){
			return 2;
		}
		else if (Objects.equals(animation, "dead")){
			return 3;
		}
		else if (Objects.equals(animation, "hide")){
			return 4;
		}
		else if (Objects.equals(animation, "spell1")){
			return 5;
		}
		else if (Objects.equals(animation, "spell2")){
			return 6;
		}
		else if (Objects.equals(animation, "spell3")){
			return 7;
		}
		else if (Objects.equals(animation, "spell4")){
			return 8;
		}
		else if (Objects.equals(animation, "spell5")){
			return 9;
		}
		else if (Objects.equals(animation, "spell6")){
			return 10;
		}
		else if (Objects.equals(animation, "spell7")){
			return 11;
		}
		else if (Objects.equals(animation, "change1")){
			return 12;
		}
		else if (Objects.equals(animation, "change2")){
			return 13;
		}
		else if (Objects.equals(animation, "change3")){
			return 14;
		}
		else if (Objects.equals(animation, "change4")){
			return 15;
		}
		else if (Objects.equals(animation, "claw1")){
			return 16;
		}
		else if (Objects.equals(animation, "claw2")){
			return 17;
		}
		else if (Objects.equals(animation, "claw3")){
			return 18;
		}
		else if (Objects.equals(animation, "claw4")){
			return 19;
		}
		else if (Objects.equals(animation, "claw5")){
			return 20;
		}
		else if (Objects.equals(animation, "claw6")){
			return 21;
		}
		else if (Objects.equals(animation, "claw7")){
			return 22;
		}
		else if (Objects.equals(animation, "eye1")){
			return 23;
		}
		else if (Objects.equals(animation, "eye2")){
			return 24;
		}
		else if (Objects.equals(animation, "eye3")){
			return 25;
		}
		else if (Objects.equals(animation, "eye4")){
			return 26;
		}
		else if (Objects.equals(animation, "eye5")){
			return 27;
		}
		else if (Objects.equals(animation, "eye6")){
			return 28;
		}
		else if (Objects.equals(animation, "eye7")){
			return 29;
		}
		else if (Objects.equals(animation, "mouth1")){
			return 30;
		}
		else if (Objects.equals(animation, "mouth2")){
			return 31;
		}
		else if (Objects.equals(animation, "mouth3")){
			return 32;
		}
		else if (Objects.equals(animation, "mouth4")){
			return 33;
		}
		else if (Objects.equals(animation, "mouth5")){
			return 34;
		}
		else if (Objects.equals(animation, "mouth6")){
			return 35;
		}
		else if (Objects.equals(animation, "mouth7")){
			return 36;
		}
		else if (Objects.equals(animation, "rot")){
			return 37;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.attackAnimationState);
		list.add(this.startAnimationState);
		list.add(this.deadAnimationState);
		list.add(this.hideAnimationState);
		list.add(this.spell1AnimationState);
		list.add(this.spell2AnimationState);
		list.add(this.spell3AnimationState);
		list.add(this.spell4AnimationState);
		list.add(this.spell5AnimationState);
		list.add(this.spell6AnimationState);
		list.add(this.spell7AnimationState);
		list.add(this.change1AnimationState);
		list.add(this.change2AnimationState);
		list.add(this.change3AnimationState);
		list.add(this.change4AnimationState);
		list.add(this.claw1AnimationState);
		list.add(this.claw2AnimationState);
		list.add(this.claw3AnimationState);
		list.add(this.claw4AnimationState);
		list.add(this.claw5AnimationState);
		list.add(this.claw6AnimationState);
		list.add(this.claw7AnimationState);
		list.add(this.eye1AnimationState);
		list.add(this.eye2AnimationState);
		list.add(this.eye3AnimationState);
		list.add(this.eye4AnimationState);
		list.add(this.eye5AnimationState);
		list.add(this.eye6AnimationState);
		list.add(this.eye7AnimationState);
		list.add(this.mouth1AnimationState);
		list.add(this.mouth2AnimationState);
		list.add(this.mouth3AnimationState);
		list.add(this.mouth4AnimationState);
		list.add(this.mouth5AnimationState);
		list.add(this.mouth6AnimationState);
		list.add(this.mouth7AnimationState);
		list.add(this.rotAnimationState);
		return list;
	}
	public void stopMostAnimation(AnimationState exception){
		for (AnimationState state : this.getAllAnimations()){
			if (state != exception && state != startAnimationState && state != rotAnimationState) {
				state.stop();
			}
		}
	}
	public void stopAllAnimation(){
		for (AnimationState state : this.getAllAnimations()){
			state.stop();
		}
	}
	//
	//是否传奇
	public boolean isLegend() {
		return this.getEntityData().get(IS_LEGEND);
	}
	public void setLegend(boolean bl) {
		this.getEntityData().set(IS_LEGEND, bl);
	}
	public boolean isLegendary() {
		return this.isLegend() || hasPercentageDamage() && hasDamageCap() && hasDamageCooldownTick();
	}
	//
	@Nullable
	private LivingEntity eatGet;
	@Nullable
	private UUID eatGetUUID;
	@Nullable
	public UUID getEatGetUUID() {
		return eatGetUUID;
	}
	@Nullable
	public LivingEntity getEatGet() {
		Entity entity;
		if (this.eatGet == null && this.eatGetUUID != null && this.level() instanceof ServerLevel && (entity = ((ServerLevel)this.level()).getEntity(this.eatGetUUID)) instanceof LivingEntity) {
			this.eatGet = (LivingEntity)entity;
		}
		return this.eatGet;
	}
	public void setEatGet(@Nullable LivingEntity livingEntity) {
		this.eatGet = livingEntity;
		this.eatGetUUID = livingEntity == null ? null : livingEntity.getUUID();
	}
	//
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		if (this.eatGetUUID != null) {
			compoundTag.putUUID("EatGet", this.eatGetUUID);
		}
		compoundTag.putBoolean("IsLegend", this.isLegend());
		compoundTag.putInt("StartTick", this.getStartTick());
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("SpellLevel", this.spellLevel);
		compoundTag.putInt("MeleeTick", this.getMeleeTick());
		compoundTag.putBoolean("IsLock", this.isLock());
		compoundTag.putInt("SpellTick", this.getSpellTick());
		compoundTag.putInt("BodyType", this.getBodyType());
		compoundTag.putInt("LastBodyType", this.getLastBodyType());
		compoundTag.putBoolean("IsFirstHeal", this.isFirstHeal());
		compoundTag.putInt("ChangeBodyTypeToFirstUseTick", this.getChangeBodyTypeToFirstUseTick());
		compoundTag.putInt("ChangeBodyTypeToSecondUseTick", this.getChangeBodyTypeToSecondUseTick());
		compoundTag.putInt("BodyTypeUseTick", this.getBodyTypeUseTick());
		compoundTag.putDouble("AttackFindXRot", this.attackFindXRot);
		compoundTag.putDouble("AttackFindYRot", this.attackFindYRot);
		compoundTag.putInt("HeadXRotAnim", this.getHeadXRotAnim());
		//技能
		compoundTag.putBoolean("IsArmor", this.isArmor());
		compoundTag.putInt("ArmorTick", this.getArmorTick());
		compoundTag.putFloat("ArmorDamage", this.getArmorDamage());
		compoundTag.putBoolean("IsHide", this.isHide());
		compoundTag.putInt("HideTick", this.getHideTick());
		compoundTag.putInt("HideUseTick", this.getHideUseTick());
		compoundTag.putInt("SwordTick", this.getSwordTick());
		compoundTag.putInt("SwordUseTick", this.getSwordUseTick());
		compoundTag.putInt("ImprisonTick", this.getImprisonTick());
		compoundTag.putInt("ImprisonUseTick", this.getImprisonUseTick());
		compoundTag.putInt("BotanizeTick", this.getBotanizeTick());
		compoundTag.putInt("BotanizeUseTick", this.getBotanizeUseTick());
		compoundTag.putInt("FogTick", this.getFogTick());
		compoundTag.putInt("FogUseTick", this.getFogUseTick());
		compoundTag.putInt("UndeadTick", this.getUndeadTick());
		compoundTag.putInt("UndeadUseTick", this.getUndeadUseTick());
		compoundTag.putInt("HealTick", this.getHealTick());
		compoundTag.putInt("HealUseTick", this.getHealUseTick());
		compoundTag.putInt("TimeTick", this.getTimeTick());
		compoundTag.putInt("TimeUseTick", this.getTimeUseTick());
		compoundTag.putInt("SlamTick", this.getSlamTick());
		compoundTag.putInt("SlamUseTick", this.getSlamUseTick());
		compoundTag.putInt("GraspTick", this.getGraspTick());
		compoundTag.putInt("GraspUseTick", this.getGraspUseTick());
		compoundTag.putInt("RootTick", this.getRootTick());
		compoundTag.putInt("RootUseTick", this.getRootUseTick());
		compoundTag.putInt("CenterTick", this.getCenterTick());
		compoundTag.putInt("CenterUseTick", this.getCenterUseTick());
		compoundTag.putInt("SnapTick", this.getSnapTick());
		compoundTag.putInt("SnapUseTick", this.getSnapUseTick());
		compoundTag.putInt("ThrowTick", this.getThrowTick());
		compoundTag.putInt("ThrowUseTick", this.getThrowUseTick());
		compoundTag.putInt("CollapseTick", this.getCollapseTick());
		compoundTag.putInt("CollapseUseTick", this.getCollapseUseTick());
		compoundTag.putInt("BeamTick", this.getBeamTick());
		compoundTag.putInt("BeamUseTick", this.getBeamUseTick());
		compoundTag.putInt("ArrowTick", this.getArrowTick());
		compoundTag.putInt("ArrowUseTick", this.getArrowUseTick());
		compoundTag.putInt("DisarmTick", this.getDisarmTick());
		compoundTag.putInt("DisarmUseTick", this.getDisarmUseTick());
		compoundTag.putInt("EightTick", this.getEightTick());
		compoundTag.putInt("EightUseTick", this.getEightUseTick());
		compoundTag.putInt("CloudTick", this.getCloudTick());
		compoundTag.putInt("CloudUseTick", this.getCloudUseTick());
		compoundTag.putInt("VertigoTick", this.getVertigoTick());
		compoundTag.putInt("VertigoUseTick", this.getVertigoUseTick());
		compoundTag.putInt("TorchTick", this.getTorchTick());
		compoundTag.putInt("TorchUseTick", this.getTorchUseTick());
		compoundTag.putInt("FourTick", this.getFourTick());
		compoundTag.putInt("FourUseTick", this.getFourUseTick());
		compoundTag.putInt("BiteTick", this.getBiteTick());
		compoundTag.putInt("BiteUseTick", this.getBiteUseTick());
		compoundTag.putInt("EatTick", this.getEatTick());
		compoundTag.putInt("EatUseTick", this.getEatUseTick());
		compoundTag.putInt("WiltTick", this.getWiltTick());
		compoundTag.putInt("WiltUseTick", this.getWiltUseTick());
		compoundTag.putInt("BlowTick", this.getBlowTick());
		compoundTag.putInt("BlowUseTick", this.getBlowUseTick());
		compoundTag.putInt("SuckTick", this.getSuckTick());
		compoundTag.putInt("SuckUseTick", this.getSuckUseTick());
		compoundTag.putInt("BombTick", this.getBombTick());
		compoundTag.putInt("BombUseTick", this.getBombUseTick());
		//
		this.addPersistentAngerSaveData(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		if (compoundTag.hasUUID("EatGet")) {
			this.eatGetUUID = compoundTag.getUUID("EatGet");
		}
		this.setLegend(compoundTag.getBoolean("IsLegend"));
		this.setStartTick(compoundTag.getInt("StartTick"));
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.setMeleeTick(compoundTag.getInt("MeleeTick"));
		this.setLock(compoundTag.getBoolean("IsLock"));
		this.setSpellTick(compoundTag.getInt("SpellTick"));
		this.setBodyType(compoundTag.getInt("BodyType"));
		this.setLastBodyType(compoundTag.getInt("LastBodyType"));
		this.setFirstHeal(compoundTag.getBoolean("IsFirstHeal"));
		this.setChangeBodyTypeToFirstUseTick(compoundTag.getInt("ChangeBodyTypeToFirstUseTick"));
		this.setChangeBodyTypeToSecondUseTick(compoundTag.getInt("ChangeBodyTypeToSecondUseTick"));
		this.setBodyTypeUseTick(compoundTag.getInt("BodyTypeUseTick"));
		this.attackFindXRot = compoundTag.getFloat("AttackFindXRot");
		this.attackFindYRot = compoundTag.getFloat("AttackFindYRot");
		this.setHeadXRotAnim(compoundTag.getInt("HeadXRotAnim"));
		//技能
		this.setArmor(compoundTag.getBoolean("IsArmor"));
		this.setArmorTick(compoundTag.getInt("ArmorTick"));
		this.setArmorDamage(compoundTag.getFloat("ArmorDamage"));
		this.setHide(compoundTag.getBoolean("IsHide"));
		this.setHideTick(compoundTag.getInt("HideTick"));
		this.setHideUseTick(compoundTag.getInt("HideUseTick"));
		this.setSwordTick(compoundTag.getInt("SwordTick"));
		this.setSwordUseTick(compoundTag.getInt("SwordUseTick"));
		this.setImprisonTick(compoundTag.getInt("ImprisonTick"));
		this.setImprisonUseTick(compoundTag.getInt("ImprisonUseTick"));
		this.setBotanizeTick(compoundTag.getInt("BotanizeTick"));
		this.setBotanizeUseTick(compoundTag.getInt("BotanizeUseTick"));
		this.setFogTick(compoundTag.getInt("FogTick"));
		this.setFogUseTick(compoundTag.getInt("FogUseTick"));
		this.setUndeadTick(compoundTag.getInt("UndeadTick"));
		this.setUndeadUseTick(compoundTag.getInt("UndeadUseTick"));
		this.setHealTick(compoundTag.getInt("HealTick"));
		this.setHealUseTick(compoundTag.getInt("HealUseTick"));
		this.setTimeTick(compoundTag.getInt("TimeTick"));
		this.setTimeUseTick(compoundTag.getInt("TimeUseTick"));
		this.setBeamTick(compoundTag.getInt("BeamTick"));
		this.setBeamUseTick(compoundTag.getInt("BeamUseTick"));
		this.setSlamTick(compoundTag.getInt("SlamTick"));
		this.setSlamUseTick(compoundTag.getInt("SlamUseTick"));
		this.setGraspTick(compoundTag.getInt("GraspTick"));
		this.setGraspUseTick(compoundTag.getInt("GraspUseTick"));
		this.setRootTick(compoundTag.getInt("RootTick"));
		this.setRootUseTick(compoundTag.getInt("RootUseTick"));
		this.setCenterTick(compoundTag.getInt("CenterTick"));
		this.setCenterUseTick(compoundTag.getInt("CenterUseTick"));
		this.setSnapTick(compoundTag.getInt("SnapTick"));
		this.setSnapUseTick(compoundTag.getInt("SnapUseTick"));
		this.setThrowTick(compoundTag.getInt("ThrowTick"));
		this.setThrowUseTick(compoundTag.getInt("ThrowUseTick"));
		this.setCollapseTick(compoundTag.getInt("CollapseTick"));
		this.setCollapseUseTick(compoundTag.getInt("CollapseUseTick"));
		this.setArrowTick(compoundTag.getInt("ArrowTick"));
		this.setArrowUseTick(compoundTag.getInt("ArrowUseTick"));
		this.setDisarmTick(compoundTag.getInt("DisarmTick"));
		this.setDisarmUseTick(compoundTag.getInt("DisarmUseTick"));
		this.setEightTick(compoundTag.getInt("EightTick"));
		this.setEightUseTick(compoundTag.getInt("EightUseTick"));
		this.setCloudTick(compoundTag.getInt("CloudTick"));
		this.setCloudUseTick(compoundTag.getInt("CloudUseTick"));
		this.setVertigoTick(compoundTag.getInt("VertigoTick"));
		this.setVertigoUseTick(compoundTag.getInt("VertigoUseTick"));
		this.setTorchTick(compoundTag.getInt("TorchTick"));
		this.setTorchUseTick(compoundTag.getInt("TorchUseTick"));
		this.setFourTick(compoundTag.getInt("FourTick"));
		this.setFourUseTick(compoundTag.getInt("FourUseTick"));
		this.setBiteTick(compoundTag.getInt("BiteTick"));
		this.setBiteUseTick(compoundTag.getInt("BiteUseTick"));
		this.setEatTick(compoundTag.getInt("EatTick"));
		this.setEatUseTick(compoundTag.getInt("EatUseTick"));
		this.setWiltTick(compoundTag.getInt("WiltTick"));
		this.setWiltUseTick(compoundTag.getInt("WiltUseTick"));
		this.setBlowTick(compoundTag.getInt("BlowTick"));
		this.setBlowUseTick(compoundTag.getInt("BlowUseTick"));
		this.setSuckTick(compoundTag.getInt("SuckTick"));
		this.setSuckUseTick(compoundTag.getInt("SuckUseTick"));
		this.setBombTick(compoundTag.getInt("BombTick"));
		this.setBombUseTick(compoundTag.getInt("BombUseTick"));
		//
		this.readPersistentAngerSaveData(this.level(), compoundTag);
		this.bossEvent.setId(this.getUUID());
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(START_TICK, 0);
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(MELEE_TICK, 0);
		this.getEntityData().define(IS_LOCK, false);
		this.getEntityData().define(SPELL_TICK, 0);
		this.getEntityData().define(BODY_TYPE, 1);
		this.getEntityData().define(LAST_BODY_TYPE, 1);
		this.getEntityData().define(BODY_TYPE_USE_TICK, 0);
		this.getEntityData().define(FIRST_HEALTH, true);
		this.getEntityData().define(CHANGE_BODY_TYPE_TO_FIRST_USE_TICK, 0);
		this.getEntityData().define(CHANGE_BODY_TYPE_TO_SECOND_USE_TICK, 0);
		//技能
		this.getEntityData().define(IS_ARMOR, false);
		this.getEntityData().define(ARMOR_DAMAGE, 0f);
		this.getEntityData().define(ARMOR_TICK, 0);
		this.getEntityData().define(IS_HIDE, false);
		this.getEntityData().define(HIDE_TICK, 600);
		this.getEntityData().define(HIDE_USE_TICK, 0);
		this.getEntityData().define(SWORD_TICK, 250);
		this.getEntityData().define(SWORD_USE_TICK, 0);
		this.getEntityData().define(IMPRISON_TICK, 240);
		this.getEntityData().define(IMPRISON_USE_TICK, 0);
		this.getEntityData().define(BOTANIZE_TICK, 450);
		this.getEntityData().define(BOTANIZE_USE_TICK, 0);
		this.getEntityData().define(FOG_TICK, 300);
		this.getEntityData().define(FOG_USE_TICK, 0);
		this.getEntityData().define(UNDEAD_TICK, 240);
		this.getEntityData().define(UNDEAD_USE_TICK, 0);
		this.getEntityData().define(HEAL_TICK, 400);
		this.getEntityData().define(HEAL_USE_TICK, 0);
		this.getEntityData().define(TIME_TICK, 400);
		this.getEntityData().define(TIME_USE_TICK, 0);

		this.getEntityData().define(SLAM_TICK, 200);
		this.getEntityData().define(SLAM_USE_TICK, 0);
		this.getEntityData().define(GRASP_TICK, 300);
		this.getEntityData().define(GRASP_USE_TICK, 0);
		this.getEntityData().define(ROOT_TICK, 300);
		this.getEntityData().define(ROOT_USE_TICK, 0);
		this.getEntityData().define(CENTER_TICK, 200);
		this.getEntityData().define(CENTER_USE_TICK, 0);
		this.getEntityData().define(SNAP_TICK, 300);
		this.getEntityData().define(SNAP_USE_TICK, 0);
		this.getEntityData().define(THROW_TICK, 200);
		this.getEntityData().define(THROW_USE_TICK, 0);
		this.getEntityData().define(COLLAPSE_TICK, 300);
		this.getEntityData().define(COLLAPSE_USE_TICK, 0);

		this.getEntityData().define(BEAM_TICK, 400);
		this.getEntityData().define(BEAM_USE_TICK, 0);
		this.getEntityData().define(ARROW_TICK, 200);
		this.getEntityData().define(ARROW_USE_TICK, 0);
		this.getEntityData().define(DISARM_TICK, 200);
		this.getEntityData().define(DISARM_USE_TICK, 0);
		this.getEntityData().define(EIGHT_TICK, 300);
		this.getEntityData().define(EIGHT_USE_TICK, 0);
		this.getEntityData().define(CLOUD_TICK, 300);
		this.getEntityData().define(CLOUD_USE_TICK, 0);
		this.getEntityData().define(VERTIGO_TICK, 360);
		this.getEntityData().define(VERTIGO_USE_TICK, 0);
		this.getEntityData().define(TORCH_TICK, 360);
		this.getEntityData().define(TORCH_USE_TICK, 0);

		this.getEntityData().define(FOUR_TICK, 150);
		this.getEntityData().define(FOUR_USE_TICK, 0);
		this.getEntityData().define(BITE_TICK, 150);
		this.getEntityData().define(BITE_USE_TICK, 0);
		this.getEntityData().define(EAT_TICK, 240);
		this.getEntityData().define(EAT_USE_TICK, 0);
		this.getEntityData().define(WILT_TICK, 240);
		this.getEntityData().define(WILT_USE_TICK, 0);
		this.getEntityData().define(BLOW_TICK, 300);
		this.getEntityData().define(BLOW_USE_TICK, 0);
		this.getEntityData().define(SUCK_TICK, 150);
		this.getEntityData().define(SUCK_USE_TICK, 0);
		this.getEntityData().define(BOMB_TICK, 400);
		this.getEntityData().define(BOMB_USE_TICK, 0);
		this.getEntityData().define(HEAD_X_ROT_ANIM, 20);
		this.getEntityData().define(IS_LEGEND, false);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (BODY_TYPE.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		if (IS_HIDE.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		if (ANIM_STATE.equals(entityDataAccessor)) {
			if (this.level().isClientSide()) {
				switch (this.entityData.get(ANIM_STATE)){
					case 0:
						this.stopAllAnimation();
						break;
					case 1:
						this.attackAnimationState.startIfStopped(this.tickCount);
						break;
					case 2:
						this.startAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.startAnimationState);
						break;
					case 3:
						this.deadAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.deadAnimationState);
						break;
					case 4:
						this.hideAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.hideAnimationState);
						break;
					case 5:
						this.spell1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.spell1AnimationState);
						break;
					case 6:
						this.spell2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.spell2AnimationState);
						break;
					case 7:
						this.spell3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.spell3AnimationState);
						break;
					case 8:
						this.spell4AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.spell4AnimationState);
						break;
					case 9:
						this.spell5AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.spell5AnimationState);
						break;
					case 10:
						this.spell6AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.spell6AnimationState);
						break;
					case 11:
						this.spell7AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.spell7AnimationState);
						break;
					case 12:
						this.change1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.change1AnimationState);
						break;
					case 13:
						this.change2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.change2AnimationState);
						break;
					case 14:
						this.change3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.change3AnimationState);
						break;
					case 15:
						this.change4AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.change4AnimationState);
						break;
					case 16:
						this.claw1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.claw1AnimationState);
						break;
					case 17:
						this.claw2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.claw2AnimationState);
						break;
					case 18:
						this.claw3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.claw3AnimationState);
						break;
					case 19:
						this.claw4AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.claw4AnimationState);
						break;
					case 20:
						this.claw5AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.claw5AnimationState);
						break;
					case 21:
						this.claw6AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.claw6AnimationState);
						break;
					case 22:
						this.claw7AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.claw7AnimationState);
						break;
					case 23:
						this.eye1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.eye1AnimationState);
						break;
					case 24:
						this.eye2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.eye2AnimationState);
						break;
					case 25:
						this.eye3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.eye3AnimationState);
						break;
					case 26:
						this.eye4AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.eye4AnimationState);
						break;
					case 27:
						this.eye5AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.eye5AnimationState);
						break;
					case 28:
						this.eye6AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.eye6AnimationState);
						break;
					case 29:
						this.eye7AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.eye7AnimationState);
						break;
					case 30:
						this.mouth1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.mouth1AnimationState);
						break;
					case 31:
						this.mouth2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.mouth2AnimationState);
						break;
					case 32:
						this.mouth3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.mouth3AnimationState);
						break;
					case 33:
						this.mouth4AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.mouth4AnimationState);
						break;
					case 34:
						this.mouth5AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.mouth5AnimationState);
						break;
					case 35:
						this.mouth6AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.mouth6AnimationState);
						break;
					case 36:
						this.mouth7AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.mouth7AnimationState);
						break;
					case 37:
						this.rotAnimationState.startIfStopped(this.tickCount);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	public void tick() {
		super.tick();
		this.setJumping(false);
		if (!this.level().isClientSide()) {
			this.goalSelector.setControlFlag(Goal.Flag.JUMP, false);
			this.goalSelector.setControlFlag(Goal.Flag.MOVE, false);
		}
		if (this.isHide()) {
			if (!this.level().isClientSide()) {
				this.goalSelector.setControlFlag(Goal.Flag.LOOK, false);
			}
		}
		if (!level().isClientSide() && getBossMusic() != null) {
			if (!isSilent() && getTarget() instanceof Player player && player.isAlive()) {
				this.level().broadcastEntityEvent(this, (byte)67);
			}
			else {
				this.level().broadcastEntityEvent(this, (byte)68);
			}
		}
		if (level().isClientSide()) {
			lastTickRenderTime = thisTickRenderTime;
			thisTickRenderTime = 0;
		}
	}
	@Override
	public void aiStep() {
		super.aiStep();
		if (!this.isDeadOrDying() && this.deathTime > 0) {
			this.deathTime = 0;
		}
		this.bossEvent.update();
		if (this.getRandom().nextInt(900) == 1 && this.isAlive()) {
			this.heal(10.0f);
		}
		if (!this.level().isClientSide()) {
			this.updatePersistentAnger((ServerLevel) this.level(), true);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//
		if (!this.level().isClientSide()) {
			if (this.getStartTick() < 30) {
				this.setStartTick(this.getStartTick() + 1);
			}
		}
		//锁定
		if (this.isLock() && this.level().getBlockState(this.getOnPos()).isAir()) {
			OtherParticlesUse.CelestialCoilvineLock(this);
		}
		//目标粒子
		if (this.tickCount % 5 ==0) {
			if (this.level() instanceof ServerLevel serverLevel) {
				if (this.isAlive() && this.getTarget() != null) {
					serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_TARGET.get(), this.getTarget().getX(), this.getTarget().getY(1.2f), this.getTarget().getZ(), 0, this.getRandom().nextFloat() / 5 - 0.1f, 0.15 + this.getRandom().nextFloat() / 5, this.getRandom().nextFloat() / 5 - 0.1f, 0.05);
				}
			}
		}
		//清除动画
		if (!this.level().isClientSide()) {
			this.setAnimTick(Math.max(-1, this.getAnimTick() - 1));
		}
		if (this.getAnimTick() == 0) {
			if (!this.level().isClientSide()) {
				this.setAnimationState(0);
			}
		}
		if (this.tickCount == 20) {
			this.startAnimationState.stop();
		}
		//站立动画
		this.idleBaseAnimationState.startIfStopped(this.tickCount);
		if (!this.stopLookTime() && this.getBlowUseTick() <= 0 && this.getBombUseTick() <= 0) {
			if (!this.level().isClientSide()) {
				this.setHeadXRotAnim(Math.min(this.getHeadXRotAnim() + 1, 20));
			}
		}
		else {
			if (!this.level().isClientSide()) {
				this.setHeadXRotAnim(Math.max(this.getHeadXRotAnim() - 1, 0));
			}
		}
		//视角
		if (this.isAlive() && this.getBeamUseTick() > 0) {
			if (this.isAlive() && this.getTarget() != null){
				this.getLookControl().setLookAt(this.getTarget(), 2.0f, 2.0f);
			}
			this.yHeadRotO = this.getYHeadRot();
			this.lerpSteps = 0;
			if (!this.level().isClientSide) {
				this.goalSelector.setControlFlag(Goal.Flag.LOOK, false);
			}
		}
		else if (this.isAlive() && this.getTarget() != null && !this.isHide() && !stopLookTime()) {
			this.getLookControl().setLookAt(this.getTarget(), 10.0f, 10.0f);
		}
		//锁定
		{
			if (this.isAlive() && !this.isHide()) {
				if (!this.isLock()) {
					this.setLock(true);
				}
			} else if (this.isLock()) {
				if (!this.level().isClientSide()) {
					this.setLock(false);
				}
			}
		}


		//切换型态
		if (this.isAlive()) { // BodyType LastBodyType SecondType // BodyTypeUseTick FirstHeal ChangeBodyTypeToFirstUseTick
			//一阶段至二阶段
			if (!this.isSecondType() && this.isFirstHeal()) {
				boolean special = !this.specialAction();
				boolean lowHealth = this.getHealth() <= this.getMaxHealth() / 10;
				if (lowHealth && special) {
					this.changeBodyType();
				}
			}
			//一阶段至二阶段的血量恢复
			if (this.getLastBodyType() == 1 && this.getBodyTypeUseTick() > 0) {
				this.heal(this.getMaxHealth() / 40);
			}
			//二阶段至一阶段
			boolean nonCombat = this.getTarget() == null;
			boolean maxHealth = this.getHealth() >= this.getMaxHealth();
			boolean tick = this.getChangeBodyTypeToFirstUseTick() >= 3600;
			boolean special = !this.specialAction();
			if (this.isSecondType()) {
				if (nonCombat && maxHealth && tick && special) {
					if (!this.level().isClientSide()) {
						this.setFirstHeal(true);
						this.setLastBodyType(this.getBodyType());
						this.setAnimTick(40);
						this.setAnimationState("change1");
						this.setBodyType(1);
						this.setBodyTypeUseTick(40);
						this.setSpellTick(60);
						this.setChangeBodyTypeToFirstUseTick(0);
						this.setChangeBodyTypeToSecondUseTick(0);
					}
				}
			}
			//二阶段相互转换
			if (this.isSecondType()) {
				if (!nonCombat && special && this.getBodyTypeUseTick() <= 0
						&& this.getChangeBodyTypeToSecondUseTick() >= 1200
						&& !this.level().isClientSide()) {
					int currentBodyType = this.getBodyType();
					int nextBodyType;
					do {
						nextBodyType = this.getRandom().nextInt(3) + 2;
					} while (nextBodyType == currentBodyType);
					this.setLastBodyType(currentBodyType);
					this.setAnimTick(40);
					this.setAnimationState("change" + nextBodyType);
					this.setBodyType(nextBodyType);
					this.setBodyTypeUseTick(40);
					this.setSpellTick(60);
					this.setChangeBodyTypeToSecondUseTick(0);
				}
			}

			if (this.getBodyTypeUseTick() > 0 && this.isAlive() && this.getBodyTypeUseTick() % 5 == 0) {
				if (this.level() instanceof ServerLevel serverLevel) {
					for (int i = 0; i < 24; i++) {
						double angle = (Math.PI * 2 * i) / 24;
						double radius = 5;
						double offsetX = Math.cos(angle) * radius;
						double offsetZ = Math.sin(angle) * radius;
						serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_CHANGE.get(),
								this.getX() + offsetX, this.getY() + 0.05f,
								this.getZ() + offsetZ, 0, offsetX * -0.02f, 0.12, offsetZ * -0.02f, 1);
					}
				}
			}
			//计时
			if (!this.level().isClientSide()) {
				//转换过程计时
				this.setBodyTypeUseTick(Math.max(0, this.getBodyTypeUseTick() - 1));
				//恢复一阶段计时
				if (!this.isSecondType()) {
					this.setChangeBodyTypeToFirstUseTick(0);
				}
				else if (nonCombat && maxHealth) {
					this.setChangeBodyTypeToFirstUseTick(Math.min(3600, this.getChangeBodyTypeToFirstUseTick() + 1));
				}
				else {
					this.setChangeBodyTypeToFirstUseTick(Math.max(0, this.getChangeBodyTypeToFirstUseTick() - 1));
				}
				//二阶段形态计时
				if (this.isSecondType() && !nonCombat) {
					this.setChangeBodyTypeToSecondUseTick(Math.min(1200, this.getChangeBodyTypeToSecondUseTick() + 1));
				}
				else {
					this.setChangeBodyTypeToSecondUseTick(Math.max(0, this.getChangeBodyTypeToSecondUseTick() - 1));
				}
			}
		}

		//技能-法术反制
		if (this.tickCount % 600 == 0 || this.tickCount == 1) {
			if (!this.level().isClientSide()) {
				int spellLevel = this.getSpellLevel() - 1;
				if (this.level().getDifficulty() == Difficulty.NORMAL) {
					spellLevel = this.getSpellLevel();
				} else if (this.level().getDifficulty() == Difficulty.HARD) {
					spellLevel = this.getSpellLevel() + 1;
				}
				this.addEffect(new MobEffectInstance(JerotesMobEffects.COUNTERSPELL.get(), 600, spellLevel, false, false), this);
			}
		}
		//近战攻击
		{
			if (this.getMeleeTick() <= 0 && this.isAlive() && !needMelee().isEmpty() && !this.isHide() && this.getHideUseTick() <= 0) {
				this.swing(InteractionHand.MAIN_HAND);
				this.trueHurt();
				if (this.level() instanceof ServerLevel serverLevel) {
					serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_ATTACK.get(), this.getX(), this.getY() + 0.1f, this.getZ(), 0, 0, 0, 0, 0);
				}
			}
			if (!this.level().isClientSide()) {
				this.setMeleeTick(Math.max(0, this.getMeleeTick() - 1));
			}
		}
		//技能
		{
			int spellLevel = this.getSpellLevel() - 1;
			if (this.level().getDifficulty() == Difficulty.NORMAL) {
				spellLevel = this.getSpellLevel();
			} else if (this.level().getDifficulty() == Difficulty.HARD) {
				spellLevel = this.getSpellLevel() + 1;
			}
			List<CelestialCoilvineEntity> listVine = this.level().getEntitiesOfClass(CelestialCoilvineEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
			listVine.removeIf(entity -> this.getTarget() == entity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
			List<CoilvineBotanizeEntity> listBotanize = this.level().getEntitiesOfClass(CoilvineBotanizeEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
			listBotanize.removeIf(entity -> this.getTarget() == entity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
			List<VinePodBombEntity> listVinePodBomb = this.level().getEntitiesOfClass(VinePodBombEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
			listVinePodBomb.removeIf(entity -> this.getTarget() == entity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
			{
				//堂堂正正
				{
					if (this.getTarget() != null &&
							!(this.getTarget() instanceof Mob mob && mob.getTarget() != null && AttackFind.FindCanNotAttack(this, mob.getTarget())) &&
							(this.getTarget() != null && (this.getTarget().getLastHurtMob() == null || this.getTarget().getLastHurtMob() != null && AttackFind.FindCanNotAttack(this, this.getTarget().getLastHurtMob())))) {
						if (!this.level().isClientSide()) {
							this.getTarget().addEffect(new MobEffectInstance(JerotesVillageMobEffects.FAIRNESS.get(), 10, (int) (this.getHealth() / 100)), this);
						}
					}
				}
				//固若金汤
				{
					if (this.getArmorTick() > 0 && !this.isArmor()) {
						if (!this.level().isClientSide()) {
							this.setArmor(true);
						}
					}
					if (this.getArmorTick() <= 0 && this.isArmor()) {
						if (!this.level().isClientSide()) {
							this.setArmor(false);
						}
					}
				}
				//缩地成寸
				{
					//开始
					if (this.getHideTick() == 1200 && this.spellNeed(0, 512, 2)) {
						if (!this.level().isClientSide()) {
							this.setHideUseTick(120);
							this.setSpellTick(125);
						}
					}
					if (this.getHideUseTick() == 120 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setHideTick(0);
							this.setAnimTick(40);
							this.setAnimationState("hide");
						}
						if (this.level() instanceof ServerLevel serverLevel) {
							if (!this.isInvisible()) {
								serverLevel.sendParticles(JerotesVillageParticleTypes.VINE_TELEPORT_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
							}
						}
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_TELEPORT, 5.0f, 1.0f);
						}
					}
					if ((this.getHideUseTick() <= 120 && this.getHideUseTick() > 80 || this.getHideUseTick() <= 20 && this.getHideUseTick() > 0) && this.getHideUseTick() % 5 == 0 && this.isAlive()) {
						//震动
						List<LivingEntity> listShake = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(16.0, 16.0, 16.0));
						for (LivingEntity shake : listShake) {
							if (shake == null || shake == this || (this.distanceToSqr(shake)) > Double.MAX_VALUE)
								continue;
							if (!shake.level().isClientSide()) {
								shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 20, 0, false, false), this);
							}
						}
						//粒子
						if (this.level() instanceof ServerLevel serverLevel) {
							for (int j = 0; j < 5; j++) {
								for (int i = 0; i < 24; i++) {
									for (int n = 0; n < 5; n++) {
										double angle = (Math.PI * 2 * i) / 24;
										double radius = Main.mobWidth(this) * 0.25f * (n + 1);
										double offsetX = Math.cos(angle) * radius;
										double offsetZ = Math.sin(angle) * radius;
										BlockPos blockPos = new BlockPos(
												(int) (this.getX() + offsetX),
												(int) (this.getY(0) + j * 3),
												(int) (this.getZ() + offsetZ));
										BlockState blockState = serverLevel.getBlockState(blockPos.below());
										if (!blockState.isAir())
											serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockState), this.getX() + offsetX, this.getY(0) + j * 0.25, this.getZ() + offsetZ, 0, 0, 0.12, 0, 1);
									}
								}
							}
						}
					}
					if (this.getHideUseTick() == 80 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setHideTick(0);
							this.setHide(true);
						}
					}
					//距离过近过远或近战远程伤害增加缩地计时
					if (this.getTarget() != null && this.getHideUseTick() <= 0 && (this.getTarget().distanceTo(this) > 16 && this.wantMelee() || this.getTarget().distanceTo(this) < 8 && !this.wantMelee())) {
						if (!this.level().isClientSide()) {
							this.setHideTick(Math.min(1200, this.getHideTick() + 2));
						}
					}
					else if (this.getLastHurtMob() != null && (this.getLastHurtMob().distanceTo(this) > 16 && this.wantMelee() || this.getLastHurtMob().distanceTo(this) < 8 && !this.wantMelee())) {
						if (!this.level().isClientSide()) {
							this.setHideTick(Math.min(1200, this.getHideTick() + 2));
						}
					}
					//切换
					if (this.getHideUseTick() == 25) {
						//传送
						LivingEntity targetEntity = this.getTarget();
						if (targetEntity != null) {
							boolean near = this.wantMelee();
							float distances = this.wantMelee() ? 8 : 24;
							OtherSpellFind.VineTeleport(this, this, targetEntity, targetEntity, distances, near);
						} else {
							OtherSpellFind.VineTeleport(this, this, this, this, 16, true);
						}
					}
					if (this.getHideUseTick() == 20) {
						//出现
						if (!this.level().isClientSide()) {
							this.setHideTick(0);
							this.setHide(false);
							this.setAnimTick(20);
							this.setAnimationState("start");
						}
					}
					if (this.getHideUseTick() > 0 && !this.isInvisible()) {
						//粒子
						if (this.level() instanceof ServerLevel serverLevel) {
							for (int i = 0; i < 5; ++i) {
								double angle = (Math.PI * 2 * Main.randomReach(this.getRandom(), 1, 30)) / 30;
								for (int i2 = 0; i2 < 10; ++i2) {
									double radius = 1 + i2 / 5f;
									double offsetX = Math.cos(angle) * radius;
									double offsetZ = Math.sin(angle) * radius;
									if (i == 3) {
										serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_LEAF.get(), this.getX() + offsetX, this.getY(i2 / 10f), this.getZ() + offsetZ, 0, 0, 0, 0, 0);
									} else {
										serverLevel.sendParticles(ParticleTypes.CHERRY_LEAVES, this.getX() + offsetX, this.getY(i2 / 10f), this.getZ() + offsetZ, 0, 0, 0, 0, 0);
									}
								}
							}
						}
					}
					if (this.getHideUseTick() <= 0 && this.isHide()) {
						if (!this.level().isClientSide()) {
							this.setHide(false);
						}
					}
				}
				//枯木逢春
				{
					//开始
					if (this.getHealTick() == 800 && this.spellNeedHeal(1) && this.getHealth() < this.getMaxHealth()) {
						if (!this.level().isClientSide()) {
							this.setHealUseTick(60);
							this.setSpellTick(115);
						}
					}
					if (this.getHealUseTick() == 60 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setHealTick(0);
							this.setAnimTick(60);
							this.setAnimationState("spell6");
						}
					}
					if (this.getHealUseTick() <= 60 && this.getHealUseTick() > 10 && this.isAlive()) {
						if (this.level() instanceof ServerLevel serverLevel) {
							for (int i = 0; i < 24; i++) {
								double angle = (Math.PI * 2 * i) / 24;
								double radius = 5;
								double offsetX = Math.cos(angle) * radius;
								double offsetZ = Math.sin(angle) * radius;
								serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_LEAF.get(),
										this.getX() + offsetX, this.getY() + 0.05f,
										this.getZ() + offsetZ, 0, 0, 0.12, 0, 1);
							}
						}
					}
					//使用
					if (getHealUseTick() == 10 && this.isAlive()) {
						//法术列表-枯木逢春
						OtherSpellList.SpringRejuvenation(spellLevel, this, this.getTarget()).spellUse();
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_HEAL, 5.0f, 1.0f);
						}
					}
				}
			}
			//一阶段
			{
				//喝茅成剑
				{
					//开始
					if (this.getSwordTick() >= 320 && this.spellNeed(0, 24, 1) && !this.isSecondType()) {
						if (!this.level().isClientSide()) {
							this.setSwordUseTick(20);
							this.setSpellTick(25);
						}
					}
					if (this.getSwordUseTick() == 20 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setSwordTick(0);
							this.setAnimTick(20);
							this.setAnimationState("spell1");
						}
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_SWORD, 5.0f, 1.0f);
						}
					}
					//使用
					if (getSwordUseTick() == 5 && this.isAlive()) {
						if (this.getTarget() != null) {
							//法术列表-喝茅成剑
							OtherSpellList.CogonSword(spellLevel, this, this.getTarget()).spellUse();
						}
						else {
							//法术列表-喝茅成剑
							OtherSpellList.CogonSword(spellLevel, this, this).spellUse();
						}
					}
				}
				//盘根错节
				{
					//开始
					if (this.getImprisonTick() >= 480 && this.spellNeed(0, 12, 2) &&
							this.getTarget() != null && (this.getY() - this.getTarget().getY() <= 3 &&  this.getY() - this.getTarget().getY() >= -3) &&
							!this.isSecondType()) {
						if (!this.level().isClientSide()) {
							this.setImprisonUseTick(60);
							this.setSpellTick(70);
						}
					}
					if (this.getImprisonUseTick() == 60 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setImprisonTick(0);
							this.setAnimTick(60);
							this.setAnimationState("spell2");
						}
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_IMPRISON, 5.0f, 1.0f);
						}
					}
					//使用
					if (getImprisonUseTick() == 25 && this.isAlive()) {
						if (this.getTarget() != null) {
							//法术列表-盘根错节
							OtherSpellList.ImprisonVine(spellLevel, this, this.getTarget()).spellUse();
						}
					}
				}
				//撒豆成兵
				{
					//开始
					if ((this.getBotanizeTick() >= 900 || this.getBotanizeTick() >= 600 && !(this.getTarget() != null && this.getTarget() instanceof Player player)) && listBotanize.size() < listVine.size() * 8 && this.spellNeed(2, 24, 4) && !this.isSecondType() && OtherSpellList.ConjureCoilvineBotanizes(spellLevel, this, this.getTarget()).canUse()) {
						if (!this.level().isClientSide()) {
							this.setBotanizeUseTick(60);
							this.setSpellTick(65);
						}
					}
					if (this.getBotanizeUseTick() == 60 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setBotanizeTick(0);
							this.setAnimTick(60);
							this.setAnimationState("spell3");
						}
					}
					if (this.getBotanizeUseTick() <= 60 && this.getBotanizeUseTick() > 10 && this.isAlive()) {
						if (this.level() instanceof ServerLevel serverLevel) {
							for (int i = 0; i < 20; i++) {
								serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_TARGET.get(),
										this.getRandomX(0.5f),
										this.getRandomY(),
										this.getRandomZ(0.5f),
										0,
										this.getRandom().nextFloat() / 5 - 0.1f,
										0.15 + this.getRandom().nextFloat() / 5,
										this.getRandom().nextFloat() / 5 - 0.1f,
										0.25);
							}
						}
					}
					if (getBotanizeUseTick() == 18 && this.isAlive()) {
						if (this.level() instanceof ServerLevel serverLevel) {
							for (int i = 0; i < 70; i++) {
								double angle = (Math.PI * 2 * i) / 70;
								double offsetXAdd = Math.cos(angle);
								double offsetZAdd = Math.sin(angle);
								// 生成烟雾粒子
								serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, this.getX(), this.getEyeY(), this.getZ(), 0, offsetXAdd * 4f, -0.8, offsetZAdd * 4f, 1.0);
								serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, this.getX(), this.getEyeY(), this.getZ(), 0, offsetXAdd * 3f, -0.6, offsetZAdd * 3f, 1.0);
							}
						}
					}
					//使用
					if (this.getBotanizeUseTick() == 10 && this.isAlive()) {
						//法术列表-撒豆成兵
						OtherSpellList.ConjureCoilvineBotanizes(spellLevel, this, this.getTarget()).spellUse();
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_BOTANIZE, 5.0f, 1.0f);
						}
					}
				}
				//目眩神驰
				{
					//开始
					if (this.getFogTick() >= 600 && this.spellNeed(2, 24, 4) && OtherSpellList.DazzlingLightFog(spellLevel, this, this.getTarget()).canUse() && !this.isSecondType() && this.getTarget() != null && !this.getTarget().onGround()) {
						if (!this.level().isClientSide()) {
							this.setFogUseTick(60);
							this.setSpellTick(65);
						}
					}
					if (this.getFogUseTick() == 60 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setFogTick(0);
							this.setAnimTick(60);
							this.setAnimationState("spell4");
						}
					}
					if (this.getFogUseTick() <= 60 && this.getFogUseTick() >= 20 && (this.getFogUseTick() & 5) == 0 && this.isAlive()) {
						//球形粒子
						OtherParticlesUse.sendBallParticles(this, JerotesVillageParticleTypes.DAZZLING_LIGHT_FOG.get(), true, 8.0f, 0.35f);
						//震动
						List<LivingEntity> listShake = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(16.0, 16.0, 16.0));
						for (LivingEntity shake : listShake) {
							if (shake == null || shake == this || (this.distanceToSqr(shake)) > Double.MAX_VALUE)
								continue;
							if (!shake.level().isClientSide()) {
								shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 20, 0, false, false), this);
							}
						}
					}
					//使用
					if (getFogUseTick() == 10 && this.isAlive()) {
						//法术列表-目眩神驰
						OtherSpellList.DazzlingLightFog(spellLevel, this, this.getTarget()).spellUse();
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_FOG, 5.0f, 1.0f);
						}
					}
					//目眩神驰生物强制坠落
					if (this.getTarget() != null && this.getTarget().hasEffect(JerotesVillageMobEffects.DAZZLING_LIGHT.get())) {
						if (!this.getTarget().onGround() && !this.getTarget().isInWater() && this.getTarget().getDeltaMovement().y > -1d) {
							if (this.getTarget().getY() > this.getY()) {
								this.getTarget().setDeltaMovement(this.getTarget().getDeltaMovement().x, -1d, this.getTarget().getDeltaMovement().z);
							}
						}
					}
				}
				//驱鬼禳灾
				{
					//开始
					if (this.getUndeadTick() >= 240 && this.spellNeed(0, 32, 1) && this.getTarget() != null && this.getTarget().getMobType() == MobType.UNDEAD && !this.isSecondType()) {
						if (!this.level().isClientSide()) {
							this.setUndeadUseTick(60);
							this.setSpellTick(65);
						}
					}
					if (this.getUndeadUseTick() == 60 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setUndeadTick(0);
							this.setAnimTick(60);
							this.setAnimationState("spell5");
						}
					}

					if (this.getUndeadUseTick() <= 60 && this.getUndeadUseTick() >= 20 && (this.getUndeadUseTick() & 5) == 0 && this.isAlive()) {
						//震动
						List<LivingEntity> listShake = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(16.0, 16.0, 16.0));
						for (LivingEntity shake : listShake) {
							if (shake == null || shake == this || (this.distanceToSqr(shake)) > Double.MAX_VALUE)
								continue;
							if (!shake.level().isClientSide()) {
								shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 20, 0, false, false), this);
							}
						}
					}
					//使用
					if (getUndeadUseTick() == 10 && this.isAlive()) {
						//法术列表-驱鬼禳灾
						OtherSpellList.UndeadBanishing(spellLevel, this, this.getTarget()).spellUse();
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_UNDEAD, 5.0f, 1.0f);
						}
					}
				}
				//偷天换日
				{
					//开始
					if (this.getTimeTick() >= 800 && this.spellNeed(0, 24, 1) && !this.isSecondType()) {
						if (!this.level().isClientSide()) {
							this.setTimeUseTick(100);
							this.setSpellTick(115);
						}
					}
					if (this.getTimeUseTick() == 100 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setTimeTick(0);
							this.setAnimTick(100);
							this.setAnimationState("spell7");
						}
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_TIME, 5.0f, 1.0f);
						}
						if (this.level() instanceof ServerLevel serverLevel) {
							if (!this.isInvisible()) {
								serverLevel.sendParticles(JerotesVillageParticleTypes.TIME_STEALING_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
							}
						}
					}
					if (this.getTimeUseTick() == 90 && this.isAlive()) {
						this.bossEvent.setCreateWorldFog(true);
					}
					//球形粒子
					if (this.getTimeUseTick() <= 100 && this.getTimeUseTick() >= 60 && (this.getTimeUseTick() & 2) == 0 && this.isAlive()) {
						OtherParticlesUse.sendBallParticles(this, JerotesVillageParticleTypes.CELESTIAL_COILVINE_LEAF.get(), false, 6.0f, 0.15f);
						OtherParticlesUse.sendBallParticles(this, JerotesVillageParticleTypes.CELESTIAL_COILVINE_TARGET.get(), false, 4.0f, 0.25f);
					}
					//时钟
					if (getTimeUseTick() == 60 && this.isAlive()) {
						if (this.level() instanceof ServerLevel serverLevel) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.TIME_STEALING.get(), this.getX(), this.getEyeY(), this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
					if (getTimeUseTick() == 31 && this.isAlive()) {
						this.bossEvent.setCreateWorldFog(false);
					}
					//使用
					if (getTimeUseTick() == 30 && this.isAlive()) {
						//震动
						List<LivingEntity> listShake = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(16.0, 16.0, 16.0));
						for (LivingEntity shake : listShake) {
							if (shake == null || shake == this || (this.distanceToSqr(shake)) > Double.MAX_VALUE)
								continue;
							if (!shake.level().isClientSide()) {
								shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 20, 0, false, false), this);
							}
						}
						if (this.level() instanceof ServerLevel serverLevel) {
							for (int i = 0; i < 8; i++) {
								serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_ATTACK.get(), this.getX(), this.getY() + 0.5f + i * 0.45f, this.getZ(), 0, 0, 0, 0, 0);
							}
						}
						//缩地成寸 盘根错节 枯木逢春 喝茅为剑
						int spellRandom = this.getRandom().nextInt(40);
						//缩地成寸
						if (spellRandom >= 32) {
							if (this.level() instanceof ServerLevel serverLevel) {
								if (!this.isInvisible()) {
									serverLevel.sendParticles(JerotesVillageParticleTypes.VINE_TELEPORT_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
								}
							}
							if (!this.isSilent()) {
								this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_TELEPORT, 5.0f, 1.0f);
							}
							if (!this.level().isClientSide()) {
								this.setHideTick(0);
								this.setHide(true);
								this.setHideUseTick(40);
							}
						}
						//盘根错节
						else if (spellRandom >= 20) {
							if (!this.isSilent()) {
								this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_IMPRISON, 5.0f, 1.0f);
							}
							if (this.getTarget() != null) {
								//法术列表-盘根错节
								OtherSpellList.ImprisonVine(spellLevel, this, this.getTarget()).spellUse();
							}
						}
						//枯木逢春
						else if (spellRandom >= 13) {
							if (!this.isSilent()) {
								this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_HEAL, 5.0f, 1.0f);
							}
							//法术列表-枯木逢春
							OtherSpellList.SpringRejuvenation(spellLevel, this, this.getTarget()).spellUse();
						}
						//喝茅为剑
						else {
							if (!this.isSilent()) {
								this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_SWORD, 5.0f, 1.0f);
							}
							if (this.getTarget() != null) {
								//法术列表-喝茅成剑
								OtherSpellList.CogonSword(spellLevel, this, this.getTarget()).spellUse();
							} else {
								//法术列表-喝茅成剑
								OtherSpellList.CogonSword(spellLevel, this, this).spellUse();
							}
						}
					}
				}
			}
			//二阶段·巨爪
			if (this.getBodyType() == 2) {
				//排山倒海
				{
					//开始
					if (this.getSlamTick() >= 300 && this.spellNeed(0, 8, 1) && this.getBodyType() == 2) {
						if (!this.level().isClientSide()) {
							this.setSlamUseTick(40);
							this.setSpellTick(45);
						}
					}
					if (this.getSlamUseTick() == 40 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setSlamTick(0);
							this.setAnimTick(40);
							this.setAnimationState("claw1");
						}
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_SLAM, 5.0f, 1.0f);
						}
						if (this.level() instanceof ServerLevel serverLevel) {
							if (!this.isInvisible()) {
								serverLevel.sendParticles(JerotesVillageParticleTypes.MOUNTAIN_SLAM_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
							}
						}
					}
					if (getSlamUseTick() > 30) {
						this.attackFindXRot = this.getXRot();
						this.attackFindYRot = this.getYRot();
						if (this.getTarget() != null) {
							this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
						}
					}
					//使用
					if (this.isAlive()) {
						if (getSlamUseTick() > 20) {
							this.slamParticles();
						}
						if (getSlamUseTick() == 20) {
							this.trueHurt();
							this.slamTrueHurt(2.0f, 1.0f);
						}

						if (getSlamUseTick() <= 20 && getSlamUseTick() > 10) {
							float radius = 6f;
							float angleYaw = (90.0F) * ((float) Math.PI / 180.0F);
							double x = radius * Mth.cos((float) (this.getYHeadRot() * (Math.PI / 180.0F) + angleYaw));
							double z = radius * Mth.sin((float) (this.getYHeadRot() * (Math.PI / 180.0F) + angleYaw));
							Vec3 start = new Vec3(getX() + x, this.getY(), getZ() +z);
							AABB aabb = AABB.ofSize(new Vec3(this.getX() + x, this.getY() - 1, this.getZ() + z),
									24, 0, 24);
							int n = 4 + (-getSlamUseTick() + 20) / 2;
							if (this.level() instanceof ServerLevel serverLevel) {
								for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
									Vec3 vec3 = blockPos.getCenter();
									if (start.distanceToSqr(vec3) < n * n || start.distanceToSqr(vec3) >= (n + 2) * (n + 2)) continue;
									if (this.getRandom().nextBoolean()) continue;
									Other.spawnEarthquakeFallingBlockByPos(serverLevel, blockPos, this);
								}
							}
						}
					}
				}
				//擒龙伏虎
				{
					//开始
					if (this.getGraspTick() >= 300 && this.spellNeed(0, 8, 1) && this.getBodyType() == 2) {
						if (!this.level().isClientSide()) {
							this.setGraspUseTick(40);
							this.setSpellTick(45);
						}
					}
					if (this.getGraspUseTick() == 40 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setGraspTick(0);
							this.setAnimTick(40);
							this.setAnimationState("claw2");
						}
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_GRASP, 5.0f, 1.0f);
						}
						if (this.level() instanceof ServerLevel serverLevel) {
							if (!this.isInvisible()) {
								serverLevel.sendParticles(JerotesVillageParticleTypes.GRASP_FIEND_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
							}
						}
					}
					if (getGraspUseTick() > 30) {
						this.attackFindXRot = this.getXRot();
						this.attackFindYRot = this.getYRot();
						if (this.getTarget() != null) {
							this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
						}
					}
					//使用
					if (this.isAlive()) {
						if (getGraspUseTick() > 20) {
							this.frontParticles(12);
						}
						if (getGraspUseTick() == 20) {
							this.trueHurt();
							this.graspTrueHurt(2.0f, 1.0f);
						}
					}
				}
				//落地生根
				{
					//开始
					if (this.getRootTick() >= 600 && this.spellNeed(2, 32, 2) && this.getBodyType() == 2 && OtherSpellList.TakeRoot(spellLevel, this, this.getTarget()).canUse()) {
						if (!this.level().isClientSide()) {
							this.setRootUseTick(40);
							this.setSpellTick(50);
						}
					}
					if (this.getRootUseTick() == 40 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setRootTick(0);
							this.setAnimTick(40);
							this.setAnimationState("claw3");
						}
					}
					//效果
					if (this.getRootUseTick() == 20 && this.isAlive()) {
						//法术列表-落地生根
						OtherSpellList.TakeRoot(spellLevel, this, this.getTarget()).spellUse();
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_ROOT, 5.0f, 1.0f);
						}
					}
				}
				//一发破的
				{
					//开始
					if (this.getCenterTick() >= 400 && this.spellNeed(0, 40, 2) && this.getBodyType() == 2 && OtherSpellList.DeadCenter(spellLevel, this, this.getTarget()).canUse()) {
						if (!this.level().isClientSide()) {
							this.setCenterUseTick(40);
							this.setSpellTick(50);
						}
					}
					if (this.getCenterUseTick() == 40 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setCenterTick(0);
							this.setAnimTick(40);
							this.setAnimationState("claw4");
						}
					}
					//效果
					if (this.getCenterUseTick() == 30 && this.isAlive()) {
						//法术列表-一发破的
						OtherSpellList.DeadCenter(spellLevel, this, this.getTarget()).spellUse();
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_CENTER, 5.0f, 1.0f);
						}
					}
				}
				//一弹指顷
				{
					//开始
					if (this.getSnapTick() >= 600 && this.spellNeed(0, 80, 2) && this.getBodyType() == 2 && OtherSpellList.SnapBolt(spellLevel, this, this.getTarget()).canUse()) {
						if (!this.level().isClientSide()) {
							this.setSnapUseTick(40);
							this.setSpellTick(50);
						}
					}
					if (this.getSnapUseTick() == 40 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setSnapTick(0);
							this.setAnimTick(40);
							this.setAnimationState("claw5");
						}
					}
					//效果
					if (this.getSnapUseTick() == 20 && this.isAlive()) {
						//法术列表-一弹指顷
						OtherSpellList.SnapBolt(spellLevel, this, this.getTarget()).spellUse();
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_SNAP, 5.0f, 1.0f);
						}
					}
				}
				//投鞭断流
				{
					//开始
					if (this.getThrowTick() >= 400 && this.spellNeed(0, 20, 2) && this.getBodyType() == 2 && OtherSpellList.WhipTheTide(spellLevel, this, this.getTarget()).canUse()) {
						if (!this.level().isClientSide()) {
							this.setThrowUseTick(40);
							this.setSpellTick(50);
						}
					}
					if (this.getThrowUseTick() == 40 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setThrowTick(0);
							this.setAnimTick(40);
							this.setAnimationState("claw6");
						}
					}
					//效果
					if (this.getThrowUseTick() == 20 && this.isAlive()) {
						//法术列表-投鞭断流
						OtherSpellList.WhipTheTide(spellLevel, this, this.getTarget()).spellUse();
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_THROW, 5.0f, 1.0f);
						}
					}
				}
				//土崩瓦解
				{
					//开始
					if (this.getCollapseTick() >= 600 && this.spellNeed(0, 18, 2) && this.getBodyType() == 2 && OtherSpellList.CollapseZone(spellLevel, this, this.getTarget()).canUse()) {
						if (!this.level().isClientSide()) {
							this.setCollapseUseTick(40);
							this.setSpellTick(50);
						}
					}
					if (this.getCollapseUseTick() == 40 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setCollapseTick(0);
							this.setAnimTick(40);
							this.setAnimationState("claw7");
						}
					}
					//效果
					if (this.getCollapseUseTick() == 20 && this.isAlive()) {
						//法术列表-土崩瓦解
						OtherSpellList.CollapseZone(spellLevel, this, this.getTarget()).spellUse();
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_COLLAPSE, 5.0f, 1.0f);
						}
					}

					if (getCollapseUseTick() > 30) {
						this.attackFindXRot = this.getXRot();
						this.attackFindYRot = this.getYRot();
						if (this.getTarget() != null) {
							this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
						}
					}
					//使用
					if (this.isAlive()) {
						if (getCollapseUseTick() > 20) {
							this.slamParticles();
						}
						if (getCollapseUseTick() == 20) {
							this.trueHurt();
							this.slamTrueHurt(2.0f, 1.0f, false);
						}
						if (getCollapseUseTick() <= 20 && getCollapseUseTick() > 18) {
							float radius = 6f;
							float angleYaw = (90.0F) * ((float) Math.PI / 180.0F);
							double x = radius * Mth.cos((float) (this.getYHeadRot() * (Math.PI / 180.0F) + angleYaw));
							double z = radius * Mth.sin((float) (this.getYHeadRot() * (Math.PI / 180.0F) + angleYaw));
							Vec3 start = new Vec3(getX() + x, this.getY(), getZ() +z);
							AABB aabb = AABB.ofSize(new Vec3(this.getX() + x, this.getY() - 1, this.getZ() + z),
									24, 0, 24);
							int n = 4 + (-getCollapseUseTick() + 20) / 2;
							if (this.level() instanceof ServerLevel serverLevel) {
								for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
									Vec3 vec3 = blockPos.getCenter();
									if (start.distanceToSqr(vec3) < n * n || start.distanceToSqr(vec3) >= (n + 2) * (n + 2)) continue;
									if (this.getRandom().nextBoolean()) continue;
									Other.spawnEarthquakeFallingBlockByPos(serverLevel, blockPos, this);
								}
							}
						}
					}
				}
			}
			//二阶段·巨眼
			if (this.getBodyType() == 3) {
				//迅电流光
				{
					//开始
					if (this.getBeamTick() >= 800 && this.spellNeed(4, 32, 4) && this.getBodyType() == 3 && OtherSpellList.Electroflash(spellLevel, this, this.getTarget()).canUse()) {
						if (!this.level().isClientSide()) {
							this.setBeamUseTick(260);
							this.setSpellTick(270);
						}
					}
					if (this.getBeamUseTick() == 260 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setBeamTick(0);
							this.setAnimTick(260);
							this.setAnimationState("eye1");
						}
					}
					//效果
					if (this.getBeamUseTick() == 240 && this.isAlive()) {
						//法术列表-迅电流光
						OtherSpellList.Electroflash(spellLevel, this, this.getTarget()).spellUse();
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_BEAM, 5.0f, 1.0f);
						}
					}
					if (this.getBeamUseTick() > 240) {
						this.setXRot(Math.max(-45f, this.getXRot() - 0.8f - this.getRandom().nextFloat() * 0.8f));
						this.setYHeadRot(this.getYHeadRot() + (this.getRandom().nextFloat() - 0.5f) * 1.5f);
					}
				}
				//离弦之箭
				{
					//开始
					if (this.getArrowTick() >= 400 && this.spellNeed(4, 24, 4) && this.getBodyType() == 3 && OtherSpellList.Vinebolt(spellLevel, this, this.getTarget()).canUse()) {
						if (!this.level().isClientSide()) {
							this.setArrowUseTick(70);
							this.setSpellTick(80);
						}
					}
					if (this.getArrowUseTick() == 70 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setArrowTick(0);
							this.setAnimTick(70);
							this.setAnimationState("eye2");
						}
					}
					//效果
					if (this.getArrowUseTick() == 30 && this.isAlive()) {
						//法术列表-离弦之箭
						OtherSpellList.Vinebolt(spellLevel, this, this.getTarget()).spellUse();
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_ARROW, 5.0f, 1.0f);
						}
					}
				}
				//忐忑不定
				{
					//开始
					if (this.getDisarmTick() >= 400 && this.spellNeed(2, 36, 2) && this.getBodyType() == 3 && OtherSpellList.Vinebolt(spellLevel, this, this.getTarget()).canUse()) {
						if (!this.level().isClientSide()) {
							this.setDisarmUseTick(50);
							this.setSpellTick(70);
						}
					}
					if (this.getDisarmUseTick() == 50 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setDisarmTick(0);
							this.setAnimTick(50);
							this.setAnimationState("eye3");
						}
					}
					//效果
					if (this.getDisarmUseTick() == 20 && this.isAlive()) {
						//法术列表-忐忑不定
						OtherSpellList.UneasyUncertainty(spellLevel, this, this.getTarget()).spellUse();
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_DISARM, 5.0f, 1.0f);
						}
					}
				}
				//八面威风
				{
					//开始
					if (this.getEightTick() >= 600 && this.spellNeed(2, 24, 3) && this.getBodyType() == 3 && OtherSpellList.VineheartBomb(spellLevel, this, this.getTarget()).canUse()) {
						if (!this.level().isClientSide()) {
							this.setEightUseTick(50);
							this.setSpellTick(70);
						}
					}
					if (this.getEightUseTick() == 50 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setEightTick(0);
							this.setAnimTick(50);
							this.setAnimationState("eye4");
						}
					}
					//效果
					if (this.getEightUseTick() == 20 && this.isAlive()) {
						//法术列表-八面威风
						OtherSpellList.VineheartBomb(spellLevel, this, this.getTarget()).spellUse();
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_EIGHT, 5.0f, 1.0f);
						}
					}
				}
				//云雾蒸集
				{
					//开始
					if (this.getCloudTick() >= 600 && this.spellNeed(0, 36, 2) && this.getBodyType() == 3 && OtherSpellList.ToxicMistVolley(spellLevel, this, this.getTarget()).canUse()) {
						if (!this.level().isClientSide()) {
							this.setCloudUseTick(50);
							this.setSpellTick(70);
						}
					}
					if (this.getCloudUseTick() == 50 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setCloudTick(0);
							this.setAnimTick(50);
							this.setAnimationState("eye5");
						}
					}
					//效果
					if (this.getCloudUseTick() == 20 && this.isAlive()) {
						//法术列表-云雾蒸集
						OtherSpellList.ToxicMistVolley(spellLevel, this, this.getTarget()).spellUse();
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_CLOUD, 5.0f, 1.0f);
						}
					}
				}
				//眼花缭乱
				{
					//开始
					if (this.getVertigoTick() >= 720 && this.spellNeed(0, 80, 2) && this.getBodyType() == 3 && OtherSpellList.VertigoDeluge(spellLevel, this, this.getTarget()).canUse()) {
						if (!this.level().isClientSide()) {
							this.setVertigoUseTick(80);
							this.setSpellTick(100);
						}
					}
					if (this.getVertigoUseTick() == 80 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setVertigoTick(0);
							this.setAnimTick(80);
							this.setAnimationState("eye6");
						}
					}
					//效果
					if (this.getVertigoUseTick() == 60 && this.isAlive()) {
						//法术列表-眼花缭乱
						OtherSpellList.VertigoDeluge(spellLevel, this, this.getTarget()).spellUse();
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_VERTIGO, 5.0f, 1.0f);
						}
					}
				}
				//目光如炬
				{
					//开始
					if (this.getTorchTick() >= 720 && this.spellNeed(0, 36, 2) && this.getBodyType() == 3 && OtherSpellList.TorchGaze(spellLevel, this, this.getTarget()).canUse()) {
						if (!this.level().isClientSide()) {
							this.setTorchUseTick(50);
							this.setSpellTick(80);
						}
					}
					if (this.getTorchUseTick() == 50 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setTorchTick(0);
							this.setAnimTick(50);
							this.setAnimationState("eye7");
						}
					}
					//效果
					if (this.getTorchUseTick() == 20 && this.isAlive()) {
						//法术列表-目光如炬
						OtherSpellList.TorchGaze(spellLevel, this, this.getTarget()).spellUse();
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_TORCH, 5.0f, 1.0f);
						}
					}
				}
			}
			//二阶段·巨嘴
			if (this.getBodyType() == 4) {
				//除恶务尽
				{
					//开始
					if (this.getFourTick() >= 300 && this.spellNeed(0, 8, 1) && this.getBodyType() == 4) {
						if (!this.level().isClientSide()) {
							this.setFourUseTick(120);
							this.setSpellTick(125);
						}
					}
					if (this.getFourUseTick() == 120 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setFourTick(0);
							this.setAnimTick(120);
							this.setAnimationState("mouth1");
						}
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_FOUR, 5.0f, 1.0f);
						}
						if (this.level() instanceof ServerLevel serverLevel) {
							if (!this.isInvisible()) {
								serverLevel.sendParticles(JerotesVillageParticleTypes.PURGE_ALL_WICKED_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
							}
						}
					}
					if (getFourUseTick() > 100) {
						this.attackFindXRot = this.getXRot();
						this.attackFindYRot = this.getYRot();
						if (this.getTarget() != null) {
							this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
						}
					}
					if (this.getFourUseTick() == 70 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setAnimationState("rot");
						}
					}
					//使用
					if (this.isAlive()) {
						if (getFourUseTick() > 20 && getFourUseTick() <= 100) {
							int n = 0;
							this.setXRot(this.attackFindXRot);
							if (getFourUseTick() > 80) {
								n = 1;
							}
							else if (getFourUseTick() > 60) {
								n = 2;
							}
							else if (getFourUseTick() > 40) {
								n = 3;
							}
							else if (getFourUseTick() > 20) {
								n = 4;
							}
							this.fourTrueHurtParticles(n);
						}
						if (getFourUseTick() == 80) {
							this.trueHurt();
							this.fourTrueHurt(1);
						}
						if (getFourUseTick() == 60) {
							this.trueHurt();
							this.fourTrueHurt(2);
						}
						if (getFourUseTick() == 40) {
							this.trueHurt();
							this.fourTrueHurt(3);
						}
						if (getFourUseTick() == 20) {
							this.trueHurt();
							this.fourTrueHurt(4);
						}
					}
				}
				//碎尸万段
				{
					//开始
					if (this.getBiteTick() >= 300 && this.spellNeed(0, 8, 1) && this.getBodyType() == 4) {
						if (!this.level().isClientSide()) {
							this.setBiteUseTick(100);
							this.setSpellTick(105);
						}
					}
					if (this.getBiteUseTick() == 100 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setBiteTick(0);
							this.setAnimTick(100);
							this.setAnimationState("mouth2");
						}
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_BITE, 5.0f, 1.0f);
						}
						if (this.level() instanceof ServerLevel serverLevel) {
							if (!this.isInvisible()) {
								serverLevel.sendParticles(JerotesVillageParticleTypes.LIMB_FROM_LIMB_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
							}
						}
					}
					//使用
					if (this.isAlive()) {
						if (getBiteUseTick() <= 60 && getBiteUseTick() >= 20 && getBiteUseTick() % 10 == 0) {
							this.trueHurt();
							this.fourTrueHurt(1, 3.0f, getBiteUseTick() == 0 ? 1.0f : 0f);
							this.fourTrueHurtParticles(1);
						}
					}
				}
				//狼餐虎咽
				{
					//开始
					if (this.getEatTick() >= 480 && this.spellNeed(0, 12, 1) && this.getBodyType() == 4) {
						if (!this.level().isClientSide()) {
							this.setEatUseTick(100);
							this.setSpellTick(105);
						}
					}
					if (this.getEatUseTick() == 100 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setEatTick(0);
							this.setAnimTick(100);
							this.setAnimationState("mouth3");
						}
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_EAT, 5.0f, 1.0f);
						}
						if (this.level() instanceof ServerLevel serverLevel) {
							if (!this.isInvisible()) {
								serverLevel.sendParticles(JerotesVillageParticleTypes.DEVOURING_GULPING_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
							}
						}
					}
					if (this.getTarget() != null && this.getEatUseTick() > 80 && this.isAlive()) {
						this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
					}
					//抓住
					if (getEatUseTick() == 80 && this.isAlive()) {
						this.trueHurt();
						this.fourTrueHurt(1, 2.0f, 0f);
						this.fourTrueHurtParticles(1);
						//锁定
						if (this.getTarget() != null && this.isWithinEatAttackRange(this.getTarget(), 8f) && this.getSensing().hasLineOfSight(this.getTarget()) && Main.canSee(this.getTarget(), this) &&
								(Main.mobSizeSmall(this.getTarget()) || Main.mobSizeMedium(this.getTarget()) || Main.mobSizeLarge(this.getTarget())) &&
								!EntityAndItemFind.isNoSpecialKnockback(this.getTarget().getType())) {
							if (!this.level().isClientSide()) {
								this.setEatGet(this.getTarget());
							}
							if (!this.getTarget().level().isClientSide() && !(this.getTarget() instanceof Player) && this.getTarget() != null) {
								this.getTarget().addEffect(new MobEffectInstance(JerotesMobEffects.ENSLAVEMENT.get(), 10, 0, false, false), this);
							}
						}
					}
					//调整位置
					if (this.getEatGet() != null && this.isAlive()) {
						//float radius, float rot, float offsetY
						LivingEntity livingEntity = this.getEatGet();
						if (this.getEatUseTick() >= 64 && this.getEatUseTick() <= 80)
							this.eatMove(livingEntity, 4f + (this.getEatUseTick() - 64) / 16f * 2f, 0f, 3);
						if (this.getEatUseTick() == 63)
							this.eatMove(livingEntity, 3f, 0f, 4.65f);
						if (this.getEatUseTick() == 62)
							this.eatMove(livingEntity, 1.25f, 0f, 7f);
						if (this.getEatUseTick() == 61)
							this.eatMove(livingEntity, -2f, 0f, 7f);
						if (this.getEatUseTick() < 61 && this.getEatUseTick() >= 60)
							this.eatMove(livingEntity, -3.65f, 0f, 7f);
						if (this.getEatUseTick() < 60 && this.getEatUseTick() >= 43)
							this.eatMove(livingEntity, -3.65f + (this.getEatUseTick() - 45) / 17f * 4.9f, 0f, 7f);
						if (this.getEatUseTick() == 42)
							this.eatMove(livingEntity, -1.5f, 0f, 7f);
						if (this.getEatUseTick() < 30 && this.getEatUseTick() >= 41)
							this.eatMove(livingEntity, -3.0f, 0f, 7f);
						if (this.getEatUseTick() < 10 && this.getEatUseTick() >= 30)
							this.eatMove(livingEntity, -3.0f + (this.getEatUseTick() - 12) / 20f * 4.9f, 0f, 7f);
					}
					//放弃
					if (!this.isAlive() || (this.getEatUseTick() <= 0 && this.getEatGet() != null) || (this.getEatGet() != null && this.getEatGet() instanceof Player player && player.isShiftKeyDown())) {
						if (!this.level().isClientSide()) {
							this.setEatGet(null);
						}
					}
				}
				//摧枯拉朽
				{
					//开始
					if (this.getWiltTick() >= 480 && this.spellNeed(8, 32, 2) && this.getBodyType() == 4) {
						if (!this.level().isClientSide()) {
							this.setWiltUseTick(70);
							this.setSpellTick(75);
						}
					}
					if (this.getWiltUseTick() == 70 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setWiltTick(0);
							this.setAnimTick(100);
							this.setAnimationState("mouth4");
						}
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_WILT, 5.0f, 1.0f);
						}
					}
					if (this.getWiltUseTick() == 25 && this.isAlive()) {
						if (this.getTarget() != null) {
							//法术列表-摧枯拉朽
							OtherSpellList.WiltMissile(spellLevel, this, this.getTarget()).spellUse();
						} else {
							//法术列表-摧枯拉朽
							OtherSpellList.WiltMissile(spellLevel, this, this).spellUse();
						}
					}
				}
				//风卷残云
				{
					//开始
					if (this.getBlowTick() >= 300 && this.spellNeed(5, 16, 2) && this.getBodyType() == 4 && !(this.getTarget() != null && this.getTarget() instanceof CelestialCoilvineEntity) && !(this.getTarget() != null && !(Main.mobSizeSmall(this.getTarget()) || Main.mobSizeMedium(this.getTarget()) || Main.mobSizeLarge(this.getTarget())))) {
						if (!this.level().isClientSide()) {
							this.setBlowUseTick(120);
							this.setSpellTick(125);
						}
					}
					if (this.getBlowUseTick() == 120 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setBlowTick(0);
							this.setAnimTick(120);
							this.setAnimationState("mouth5");
						}
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_BLOW, 5.0f, 1.0f);
						}
						if (this.level() instanceof ServerLevel serverLevel) {
							if (!this.isInvisible()) {
								serverLevel.sendParticles(JerotesVillageParticleTypes.DEVOURING_TEMPEST_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
							}
						}
					}
					//围绕粒子
					if (this.getBlowUseTick() <= 100 && this.getBlowUseTick() > 20 && this.isAlive()) {
						//粒子
						if (this.level() instanceof ServerLevel serverLevel) {
							for (int i = 0; i < 30; i++) {
								for (int i2 = 0; i2 < 3; i2++) {
									double angle = (Math.PI * 2 * i) / 30;
									double radius = 3;
									double offsetX = Math.cos(angle) * radius;
									double offsetZ = Math.sin(angle) * radius;
									serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_TARGET.get(),
											this.getX() + offsetX,
											this.getY() + 0.1f,
											this.getZ() + offsetZ, 0, 0, 0.3f, 0, 1);
								}
							}
						}
					}
					//音效
					if (this.getBlowUseTick() == 100 && this.isAlive()) {
						if (!this.isSilent()) {
							this.playSound(JerotesSoundEvents.WIND_SHORT, 5.0f, 1.0f);
						}
					}
					//效果
					if (this.getBlowUseTick() <= 60 && this.getBlowUseTick() > 30 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setHideTick(Math.min(1200, this.getHideTick() + 5));
						}
						//震动
						List<LivingEntity> listShake = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(32.0, 32.0, 32.0));
						for (LivingEntity shake : listShake) {
							if (shake == null || shake == this || (this.distanceToSqr(shake)) > Double.MAX_VALUE)
								continue;
							if (!shake.level().isClientSide()) {
								shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 60, 0, false, false), this);
							}
						}
						//粒子
						if (this.level() instanceof ServerLevel serverLevel) {
							float yaw = this.yHeadRot * ((float) Math.PI / 180F);
							float pitch = this.getXRot() * ((float) Math.PI / 180F);
							Vec3 direction = new Vec3(
									-Math.sin(yaw) * Math.cos(pitch),
									-Math.sin(pitch),
									Math.cos(yaw) * Math.cos(pitch)
							);
							for (int i = 0; i < 60; i++) {
								Vec3 origin = this.position()
										.add((this.getRandom().nextFloat() - 0.5f),
												6f + (this.getRandom().nextFloat() - 0.5f), (this.getRandom().nextFloat() - 0.5f))
										//2~6
										.add(direction.scale(7 - (this.getBlowUseTick() - 30) / 30f * 4f));


								double angle = (Math.PI * 2 * this.getRandom().nextInt(70)) / 70;
								double offsetXAdd = Math.cos(angle) + (this.getRandom().nextFloat() - 0.5);
								double offsetYAdd = (this.getRandom().nextFloat() - 0.5);
								double offsetZAdd = Math.sin(angle) + (this.getRandom().nextFloat() - 0.5);
								for (int i2 = 0; i2 < 5; i2++) {
									if (Main.canSeeAngle(this, new Vec3(this.getX() + offsetXAdd, 0, this.getZ() + offsetZAdd), 80)) {
										break;
									} else {
										angle = (Math.PI * 2 * this.getRandom().nextInt(70)) / 70;
										offsetXAdd = Math.cos(angle) + (this.getRandom().nextFloat() - 0.5) * 0.5f;
										offsetYAdd = (this.getRandom().nextFloat() - 0.5) * 0.5f;
										offsetZAdd = Math.sin(angle) + (this.getRandom().nextFloat() - 0.5) * 0.5f;
									}
								}
								if (Main.canSeeAngle(this, new Vec3(this.getX() + offsetXAdd, 0, this.getZ() + offsetZAdd), 80)) {
									if (i % 3 == 0)
										serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_TARGET.get(), origin.x, origin.y, origin.z, 0, offsetXAdd, offsetYAdd, offsetZAdd, 1);
									serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_LEAF.get(), origin.x, origin.y, origin.z, 0, offsetXAdd, offsetYAdd, offsetZAdd, 1);
								}
							}
						}
						//下落方块
						if (this.getBlowUseTick() % 3 == 0) {
							AABB aabb = AABB.ofSize(new Vec3(this.getX(), this.getY() - 1, this.getZ()), Main.mobWidth(this) / 2 + 16, 0, Main.mobWidth(this) / 2 + 16);
							if (this.level() instanceof ServerLevel serverLevel) {
								for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
									Vec3 vec3 = blockPos.getCenter();
									if (!canSeeAngleFour(this, vec3, 0, 120) || vec3.distanceTo(this.getPosition(0)) <= 6) continue;
									Other.spawnEarthquakeFallingBlockByPos(serverLevel, blockPos, this);
								}
							}
						}
						//击飞
						List<Entity> list = this.level().getEntitiesOfClass(Entity.class, this.getAttackBoundingBox().inflate(32.0, 32.0, 32.0));
						for (Entity hurt : list) {
							if (hurt == null) continue;
							if ((this.distanceTo(hurt)) > 128) continue;
							if (AttackFind.FindCanNotAttack(this, hurt) && (hurt instanceof LivingEntity || hurt instanceof TraceableEntity)) continue;
							if (!Main.canSee(hurt, this)) continue;
							if (!this.hasLineOfSight(hurt)) continue;
							if (Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt)) {
								double d = 0.0;
								if (hurt instanceof LivingEntity livingEntity && livingEntity.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
									d = Math.max(livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0);
								}
								double d3 = Math.max(0, 1 - d) + 0.2f;
								if (Main.canSeeAngle(this, hurt.getEyePosition(), 120)) {
									d3 += 0.2f;
								}
								if (Main.canSeeAngle(this, hurt.getEyePosition(), 80)) {
									d3 += 0.2f;
								}
								if (Main.canSeeAngle(this, hurt.getEyePosition(), 30)) {
									d3 += 0.2f;
								}
								if (hurt instanceof Player player && player.isShiftKeyDown()) {
									d3 /= 24f;
								}
								float base = (0.15f + 1 / this.distanceTo(hurt) / 3f) * 0.25f;
								hurt.setOnGround(false);
								hurt.setDeltaMovement(hurt.getDeltaMovement().add(
										-(this.getX() - hurt.getX()) * d3 * base,
										-(this.getY() - hurt.getY()) * d3 * base + 0.125f * d3 * base,
										-(this.getZ() - hurt.getZ()) * d3 * base
								));
							}
						}
					}
				}
				//气吞山河
				{
					//开始
					if (this.getSuckTick() >= 300 && this.spellNeed(8, 32, 2) && this.getBodyType() == 4 && !(this.getTarget() != null && this.getTarget() instanceof CelestialCoilvineEntity) && !(this.getTarget() != null && !(Main.mobSizeSmall(this.getTarget()) || Main.mobSizeMedium(this.getTarget()) || Main.mobSizeLarge(this.getTarget())))) {
						if (!this.level().isClientSide()) {
							this.setSuckUseTick(100);
							this.setSpellTick(105);
						}
					}
					if (this.getSuckUseTick() == 100 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setSuckTick(0);
							this.setAnimTick(100);
							this.setAnimationState("mouth6");
						}
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_SUCK, 5.0f, 1.0f);
						}
						if (this.level() instanceof ServerLevel serverLevel) {
							if (!this.isInvisible()) {
								serverLevel.sendParticles(JerotesVillageParticleTypes.WORLD_DEVOURING_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
							}
						}
					}
					//音效
					if (this.getSuckUseTick() == 80 && this.isAlive()) {
						if (!this.isSilent()) {
							this.playSound(JerotesSoundEvents.WIND_SHORT, 5.0f, 1.0f);
						}
					}
					//效果
					if (this.getSuckUseTick() <= 60 && this.getSuckUseTick() > 20 && this.isAlive()) {
						if (this.getTarget() != null) {
							this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
						}
						//震动
						List<LivingEntity> listShake = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(32.0, 32.0, 32.0));
						for (LivingEntity shake : listShake) {
							if (shake == null || shake == this || (this.distanceToSqr(shake)) > Double.MAX_VALUE)
								continue;
							if (!shake.level().isClientSide()) {
								shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 60, 0, false, false), this);
							}
						}
						//粒子
						if (this.level() instanceof ServerLevel serverLevel) {
							float yaw = this.yHeadRot * ((float) Math.PI / 180F);
							float pitch = this.getXRot() * ((float) Math.PI / 180F);
							Vec3 direction = new Vec3(
									-Math.sin(yaw) * Math.cos(pitch),
									-Math.sin(pitch),
									Math.cos(yaw) * Math.cos(pitch)
							).normalize();
							Vec3 center = this.position()
									.add(0, 5.35f, 0)
									.add(direction.scale(3.5));
							float radius = 3.5f;
							int particleCount = 80;
							Vec3 up = new Vec3(0, 1, 0);
							Vec3 right = direction.cross(up).normalize();
							for (int i = 0; i < particleCount; i++) {
								float angle = (float) (2 * Math.PI * i / particleCount);
								Vec3 offset = right.scale(radius * Math.cos(angle))
										.add(up.scale(radius * Math.sin(angle)));
								Vec3 particlePos = center.add(offset);
								serverLevel.sendParticles(
										ParticleTypes.CLOUD,
										particlePos.x, particlePos.y, particlePos.z,
										1,
										0, 0, 0,
										1
								);
							}
						}
						//击飞
						List<Entity> list = this.level().getEntitiesOfClass(Entity.class, this.getAttackBoundingBox().inflate(32.0, 32.0, 32.0));
						for (Entity hurt : list) {
							if (hurt == null) continue;
							if ((this.distanceTo(hurt)) > 128) continue;
							if (AttackFind.FindCanNotAttack(this, hurt) && (hurt instanceof LivingEntity || hurt instanceof TraceableEntity)) continue;
							if (!Main.canSee(hurt, this)) continue;
							if (!this.hasLineOfSight(hurt)) continue;
							if (Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt)) {
								double d = 0.0;
								if (hurt instanceof LivingEntity livingEntity && livingEntity.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
									d = Math.max(livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0);
								}
								double d3 = Math.max(0, 1 - d) + 0.2f;
								if (Main.canSeeAngle(this, hurt.getEyePosition(), 120)) {
									d3 += 0.2f;
								}
								if (Main.canSeeAngle(this, hurt.getEyePosition(), 80)) {
									d3 += 0.2f;
								}
								if (Main.canSeeAngle(this, hurt.getEyePosition(), 30)) {
									d3 += 0.2f;
								}
								if (hurt instanceof Player player && player.isShiftKeyDown()) {
									d3 /= 24f;
								}
								float base = (0.15f + this.distanceTo(hurt) / 128f) * 0.25f;
								hurt.setOnGround(false);
								hurt.setDeltaMovement(hurt.getDeltaMovement().add(
										(this.getX() - hurt.getX()) * d3 * base,
										(this.getY() - hurt.getY()) * d3 * base + 0.125f * d3 * base,
										(this.getZ() - hurt.getZ()) * d3 * base
								));
							}
						}
					}
				}
				//天女散花
				{
					//开始
					if (this.getBombTick() >= 800 && this.spellNeed(6, 16, 4) && listVinePodBomb.size() < listVine.size() * 8 && this.getBodyType() == 4 && OtherSpellList.VinePodBomb(spellLevel, this, this.getTarget()).canUse()) {
						if (!this.level().isClientSide()) {
							this.setBombUseTick(80);
							this.setSpellTick(85);
						}
					}
					if (this.getBombUseTick() == 80 && this.isAlive()) {
						if (!this.level().isClientSide()) {
							this.setBombTick(0);
							this.setAnimTick(80);
							this.setAnimationState("mouth7");
						}
					}
					//效果
					if (this.getBombUseTick() == 40 && this.isAlive()) {
						//法术列表-天女散花
						OtherSpellList.VinePodBomb(spellLevel, this, this.getTarget()).spellUse();
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.CELESTIAL_COILVINE_BOMB, 5.0f, 1.0f);
						}
					}
				}
			}
			if (!this.level().isClientSide()) {
				this.setSpellTick(Math.max(0, this.getSpellTick() - 1));
				this.setArmorTick(Math.max(0, this.getArmorTick() - 1));
				this.setHideTick(Math.min(1200, this.getHideTick() + 1));
				if (this.getBodyType() == 4 || (this.getBodyType() == 4 && this.tickCount % 2 == 0)) {
					this.setHideTick(Math.min(1200, this.getHideTick() + 1));
				}
				this.setHideUseTick(Math.max(0, this.getHideUseTick() - 1));
				this.setSwordTick(Math.min(320, this.getSwordTick() + 1));
				this.setSwordUseTick(Math.max(0, this.getSwordUseTick() - 1));
				this.setImprisonTick(Math.min(480, this.getImprisonTick() + 1));
				this.setImprisonUseTick(Math.max(0, this.getImprisonUseTick() - 1));
				this.setBotanizeTick(Math.min(900, this.getBotanizeTick() + 1));
				this.setBotanizeUseTick(Math.max(0, this.getBotanizeUseTick() - 1));
				this.setFogTick(Math.min(600, this.getFogTick() + 1));
				this.setFogUseTick(Math.max(0, this.getFogUseTick() - 1));
				this.setUndeadTick(Math.min(240, this.getUndeadTick() + 1));
				this.setUndeadUseTick(Math.max(0, this.getUndeadUseTick() - 1));
				this.setHealTick(Math.min(800, this.getHealTick() + 1));
				this.setHealUseTick(Math.max(0, this.getHealUseTick() - 1));
				this.setTimeTick(Math.min(800, this.getTimeTick() + 1));
				this.setTimeUseTick(Math.max(0, this.getTimeUseTick() - 1));
				if (this.getBodyType() == 2) {
					this.setSlamTick(Math.min(400, this.getSlamTick() + 1));
					this.setSlamUseTick(Math.max(0, this.getSlamUseTick() - 1));
					this.setGraspTick(Math.min(400, this.getGraspTick() + 1));
					this.setGraspUseTick(Math.max(0, this.getGraspUseTick() - 1));
					this.setRootTick(Math.min(600, this.getRootTick() + 1));
					this.setRootUseTick(Math.max(0, this.getRootUseTick() - 1));
					this.setCenterTick(Math.min(400, this.getCenterTick() + 1));
					this.setCenterUseTick(Math.max(0, this.getCenterUseTick() - 1));
					this.setSnapTick(Math.min(600, this.getSnapTick() + 1));
					this.setSnapUseTick(Math.max(0, this.getSnapUseTick() - 1));
					this.setThrowTick(Math.min(400, this.getThrowTick() + 1));
					this.setThrowUseTick(Math.max(0, this.getThrowUseTick() - 1));
					this.setCollapseTick(Math.min(600, this.getCollapseTick() + 1));
					this.setCollapseUseTick(Math.max(0, this.getCollapseUseTick() - 1));
				}
				if (this.getBodyType() == 3) {
					this.setBeamTick(Math.min(800, this.getBeamTick() + 1));
					this.setBeamUseTick(Math.max(0, this.getBeamUseTick() - 1));
					this.setArrowTick(Math.min(400, this.getArrowTick() + 1));
					this.setArrowUseTick(Math.max(0, this.getArrowUseTick() - 1));
					this.setDisarmTick(Math.min(400, this.getDisarmTick() + 1));
					this.setDisarmUseTick(Math.max(0, this.getDisarmUseTick() - 1));
					this.setEightTick(Math.min(600, this.getEightTick() + 1));
					this.setEightUseTick(Math.max(0, this.getEightUseTick() - 1));
					this.setCloudTick(Math.min(600, this.getCloudTick() + 1));
					this.setCloudUseTick(Math.max(0, this.getCloudUseTick() - 1));
					this.setVertigoTick(Math.min(720, this.getVertigoTick() + 1));
					this.setVertigoUseTick(Math.max(0, this.getVertigoUseTick() - 1));
					this.setTorchTick(Math.min(720, this.getTorchTick() + 1));
					this.setTorchUseTick(Math.max(0, this.getTorchUseTick() - 1));
				}
				if (this.getBodyType() == 4) {
					this.setFourTick(Math.min(300, this.getFourTick() + 1));
					this.setFourUseTick(Math.max(0, this.getFourUseTick() - 1));
					this.setBiteTick(Math.min(300, this.getBiteTick() + 1));
					this.setBiteUseTick(Math.max(0, this.getBiteUseTick() - 1));
					this.setEatTick(Math.min(480, this.getEatTick() + 1));
					this.setEatUseTick(Math.max(0, this.getEatUseTick() - 1));
					this.setWiltTick(Math.min(480, this.getWiltTick() + 1));
					this.setWiltUseTick(Math.max(0, this.getWiltUseTick() - 1));
					this.setBlowTick(Math.min(300, this.getBlowTick() + 1));
					this.setBlowUseTick(Math.max(0, this.getBlowUseTick() - 1));
					this.setSuckTick(Math.min(300, this.getSuckTick() + 1));
					this.setSuckUseTick(Math.max(0, this.getSuckUseTick() - 1));
					this.setBombTick(Math.min(800, this.getBombTick() + 1));
					this.setBombUseTick(Math.max(0, this.getBombUseTick() - 1));
				}
			}
		}
	}
	//法术前提
	public boolean spellNeed(float min, float max, int time) {
		return this.getSpellTick() <= 0 && !this.isHide() && !this.specialAction()
				&& this.isAlive() && !this.isNoAi() && this.getTarget() != null && this.getTarget().isAlive()
				&& this.getTarget().distanceTo(this) > min && this.getTarget().distanceTo(this) < max
				&& this.getRandom().nextInt(time * 20) == 1;
	}
	public boolean spellNeedHeal(int time) {
		return this.getSpellTick() <= 0 && !this.isHide() && !this.specialAction()
				&& this.isAlive() && !this.isNoAi()
				&& this.getRandom().nextInt(time * 20) == 1;
	}
	public boolean isWithinEatAttackRange(LivingEntity entity, float f) {
		return this.getAttackBoundingBox().inflate(f).intersects(entity.getBoundingBox());
	}
	//调整位置
	public void eatMove(LivingEntity entity, float radius, float rot, float offsetY) {
		float angleYaw = (90.0F - rot) * ((float) Math.PI / 180.0F);
		double x = this.getX() + radius * Mth.cos((float) (this.getYHeadRot() * (Math.PI / 180.0F) + angleYaw));
		double y = this.getY() + offsetY;
		double z = this.getZ() + radius * Mth.sin((float) (this.getYHeadRot() * (Math.PI / 180.0F) + angleYaw));
		entity.teleportTo(x, y, z);
		entity.setDeltaMovement(0, 0, 0);
	}

	public boolean trueHurt() {
		if (!this.level().isClientSide()) {
			this.setMeleeTick(10);
			this.setAnimationState("attack");
		}
		if (!this.isSilent()) {
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.CELESTIAL_COILVINE_ATTACK, this.getSoundSource(), 10.0f, 1.0f);
		}
		for (LivingEntity hurt : needMelee()) {
			if (hurt == null) continue;
			if ((this.distanceTo(hurt)) > Double.MAX_VALUE) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			AttackFind.attackBegin(this, hurt);
			double healthOld = hurt.getHealth();
			DamageSource damageSource = AttackFind.findDamageType(this, JerotesVillageDamageTypes.CELESTIAL_COILVINE_ATTACK, this);
			boolean bl = AttackFind.attackAfterCustomDamage(this, hurt, damageSource, 1.0f, 0f, false, 0);
			if (bl) {
				double healthNew = hurt.getHealth();
				if ((float) (healthOld - healthNew)/8 > 0) {
					this.heal((float) (healthOld - healthNew) / 8);
				}
				if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt))
						&& !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
					double d = 0.0;
					if (hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
						d = Math.max(hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0);
					}
					double d3 = Math.max(0, 1 - d) + 0.2;
					hurt.setOnGround(false);
					hurt.setDeltaMovement(hurt.getDeltaMovement().add(-(this.getX() - hurt.getX()) * 1 * d3, -(this.getY() - hurt.getY()) * 1 * d3, -(this.getZ() - hurt.getZ()) * 1 * d3));
				}
			}
		}
		//范围粒子
		if (this.level() instanceof ServerLevel serverLevel) {
			for (int i = 0; i < 70; i++) {
				double angle = (Math.PI * 2 * i) / 70;
				double offsetXAdd = Math.cos(angle);
				double offsetZAdd = Math.sin(angle);
				// 生成烟雾粒子
				serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, this.getX(), this.getY() + 0.85f, this.getZ(), 0, offsetXAdd * 1.4f, -0.04, offsetZAdd * 1.4f, 1.0);
				serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, this.getX(), this.getY() + 0.85f, this.getZ(), 0, offsetXAdd * 1.0f, -0.04, offsetZAdd * 1.0f, 1.0);
				serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, this.getX(), this.getY() + 0.85f, this.getZ(), 0, offsetXAdd * 0.7f, -0.04, offsetZAdd * 0.7f, 1.0);
			}
		}
		return true;
	}
	public boolean fourTrueHurt(int n) {
		return fourTrueHurt(n, 2.0f, 1.0f);
	}
	public boolean fourTrueHurt(int n, float f, float f2) {
		if (!this.isSilent()) {
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.CELESTIAL_COILVINE_ATTACK, this.getSoundSource(), 10.0f, 1.0f);
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, this.getSoundSource(), 10.0f, 1.0f);
		}
		List<LivingEntity> listFind = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(16, 4, 16));
		listFind.removeIf(livingEntity -> livingEntity instanceof Mob mob && !isCelestialCoilvineTarget(this, mob) && this.getTarget() != mob && mob.getTarget() != this && !(mob.getTarget() != null && mob.getTarget().isAlliedTo(this)) && !(this.getTarget() != null && mob.isAlliedTo(this.getTarget())));
		listFind.removeIf(livingEntity -> livingEntity instanceof Player player && this.getTarget() != player && !(this.getTarget() != null && player.isAlliedTo(this.getTarget())));
		listFind.removeIf(livingEntity -> !(livingEntity instanceof Player || livingEntity instanceof Mob) || !this.hasLineOfSight(livingEntity));
		for (LivingEntity hurt : listFind) {
			if (hurt == null) continue;
			if ((this.distanceTo(hurt)) > Double.MAX_VALUE) continue;
			if ((this.distanceToSqr(hurt)) > 64) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			Vec3 vec3 = hurt.getPosition(0);
			if (n == 1 && !canSeeAngleFour(this, vec3, 0, 75)) continue;
			if (n == 2 && !canSeeAngleFour(this, vec3, 90, 75)) continue;
			if (n == 3 && !canSeeAngleFour(this, vec3, -90, 75)) continue;
			if (n == 4 && !canSeeAngleFour(this, vec3, 180, 75)) continue;
			AttackFind.attackBegin(this, hurt);
			float damageMulti = f;
			if (hurt instanceof Enemy)
				damageMulti *= 2.0f;
			if (hurt.getMobType() == MobType.UNDEAD)
				damageMulti *= 1.5f;
			if (EntityFactionFind.isFiend(hurt))
				damageMulti *= 1.5f;
			double healthOld = hurt.getHealth();
			DamageSource damageSource = AttackFind.findDamageType(this, JerotesVillageDamageTypes.CELESTIAL_COILVINE_ATTACK, this);
			boolean bl = AttackFind.attackAfterCustomDamage(this, hurt, damageSource, damageMulti, f2, false, 0);
			if (bl) {
				double healthNew = hurt.getHealth();
				if ((float) (healthOld - healthNew)/2 > 0) {
					this.heal((float) (healthOld - healthNew) / 2);
				}
				if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt))
						&& !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
					double d = 0.0;
					if (hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
						d = Math.max(hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0);
					}
					double d3 = Math.max(0, 1 - d) + 0.2;
					hurt.setOnGround(false);
					hurt.setDeltaMovement(hurt.getDeltaMovement().add(
							-(this.getX() - hurt.getX()) * 1.4 * d3 * (0.7 + damageMulti / 3) * f2,
							-(this.getY() - hurt.getY()) * 2 * d3 * (0.7 + damageMulti / 3) * f2,
							-(this.getZ() - hurt.getZ()) * 1.4 * d3 * (0.7 + damageMulti / 3) * f2
					));
				}
			}
		}
		//范围粒子
		if (this.level() instanceof ServerLevel serverLevel) {
			ParticleOptions particleOptions = JerotesVillageParticleTypes.UNDEAD_BANISHING.get();
			float rot = 0;
			if (n == 1)
				rot = 0;
			if (n == 2)
				rot = -90;
			if (n == 3)
				rot = 90;
			if (n == 4)
				rot = 180;
			float angleYaw = (rot + 90.0F) * ((float) Math.PI / 180.0F);
			float renderYawOffset = this.getYHeadRot();
			serverLevel.sendParticles(particleOptions,
					this.xo + 5.5f * Mth.cos((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)),
					this.yo + 0.1f,
					this.zo + 5.5f * Mth.sin((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)),
					0, 0, 0, 0, 0);
		}
		//震动
		List<LivingEntity> listShake = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(16.0, 16.0, 16.0));
		for (LivingEntity shake : listShake) {
			if (shake == null || shake == this || (this.distanceToSqr(shake)) > Double.MAX_VALUE)
				continue;
			if (!shake.level().isClientSide()) {
				shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 20, 0, false, false), this);
			}
		}
		//下落方块
		AABB aabb = AABB.ofSize(new Vec3(this.getX(), this.getY() - 1, this.getZ()), Main.mobWidth(this)/2 + 8, 0, Main.mobWidth(this)/2 + 8);
		if (this.level() instanceof ServerLevel serverLevel) {
			for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
				Vec3 vec3 = blockPos.getCenter();
				if (n == 1 && !canSeeAngleFour(this, vec3, 0, 75)) continue;
				if (n == 2 && !canSeeAngleFour(this, vec3, 90, 75)) continue;
				if (n == 3 && !canSeeAngleFour(this, vec3, -90, 75)) continue;
				if (n == 4 && !canSeeAngleFour(this, vec3, 180, 75)) continue;
				Main.spawnFallingBlockByPos(serverLevel, blockPos);
			}
		}
		return true;
	}
	public boolean graspTrueHurt(float f, float f2) {
		if (!this.isSilent()) {
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.CELESTIAL_COILVINE_ATTACK, this.getSoundSource(), 10.0f, 1.0f);
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, this.getSoundSource(), 10.0f, 1.0f);
		}
		List<LivingEntity> listFind = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(24, 4, 24));
		listFind.removeIf(livingEntity -> livingEntity instanceof Mob mob && !isCelestialCoilvineTarget(this, mob) && this.getTarget() != mob && mob.getTarget() != this && !(mob.getTarget() != null && mob.getTarget().isAlliedTo(this)) && !(this.getTarget() != null && mob.isAlliedTo(this.getTarget())));
		listFind.removeIf(livingEntity -> livingEntity instanceof Player player && this.getTarget() != player && !(this.getTarget() != null && player.isAlliedTo(this.getTarget())));
		listFind.removeIf(livingEntity -> !(livingEntity instanceof Player || livingEntity instanceof Mob) || !this.hasLineOfSight(livingEntity));
		for (LivingEntity hurt : listFind) {
			if (hurt == null) continue;
			if ((this.distanceTo(hurt)) > Double.MAX_VALUE) continue;
			if ((this.distanceToSqr(hurt)) > 144) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			Vec3 vec3 = hurt.getPosition(0);
			if (!canSeeAngleFour(this, vec3, 0, 180)) continue;
			AttackFind.attackBegin(this, hurt);
			float damageMulti = f / 2;
			if (hurt instanceof Enemy)
				damageMulti *= 2.0f;
			if (hurt.getMobType() == MobType.UNDEAD)
				damageMulti *= 1.5f;
			if (EntityFactionFind.isFiend(hurt))
				damageMulti *= 1.5f;
			double healthOld = hurt.getHealth();
			DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.RADIANT, this);
			boolean bl = AttackFind.attackAfterCustomDamage(this, hurt, damageSource, damageMulti, f2, false, 0);
			damageSource = AttackFind.findDamageType(this, JerotesVillageDamageTypes.CELESTIAL_COILVINE_ATTACK, this);
			boolean bl2 = AttackFind.attackAfterCustomDamage(this, hurt, damageSource, damageMulti, f2, false, 0);
			if (bl || bl2) {
				if (!this.level().isClientSide()) {
					hurt.addEffect(new MobEffectInstance(JerotesMobEffects.PARALYZED.get(), 120, 0), this);
					hurt.addEffect(new MobEffectInstance(MobEffects.GLOWING, 120, 0), this);
				}
				double healthNew = hurt.getHealth();
				if ((float) (healthOld - healthNew)/2 > 0) {
					this.heal((float) (healthOld - healthNew) / 2);
				}
				if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt))
						&& !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
					double d = 0.0;
					if (hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
						d = Math.max(hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0);
					}
					double d3 = Math.max(0, 1 - d) + 0.2;
					hurt.setOnGround(false);
					hurt.setDeltaMovement(hurt.getDeltaMovement().add(
							-(this.getX() - hurt.getX()) * 1.4 * d3 * (0.7 + damageMulti / 3) * f2,
							-(this.getY() - hurt.getY()) * 2 * d3 * (0.7 + damageMulti / 3) * f2,
							-(this.getZ() - hurt.getZ()) * 1.4 * d3 * (0.7 + damageMulti / 3) * f2
					));
				}
			}
		}
		//范围粒子
		if (this.level() instanceof ServerLevel serverLevel) {
			ParticleOptions particleOptions = JerotesVillageParticleTypes.UNDEAD_BANISHING.get();
			float rot = 0;
			float angleYaw = (rot + 90.0F) * ((float) Math.PI / 180.0F);
			float renderYawOffset = this.getYHeadRot();
			serverLevel.sendParticles(particleOptions,
					this.xo + 5.5f * Mth.cos((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)),
					this.yo + 0.1f,
					this.zo + 5.5f * Mth.sin((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)),
					0, 0, 0, 0, 0);
		}
		//震动
		List<LivingEntity> listShake = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(16.0, 16.0, 16.0));
		for (LivingEntity shake : listShake) {
			if (shake == null || shake == this || (this.distanceToSqr(shake)) > Double.MAX_VALUE)
				continue;
			if (!shake.level().isClientSide()) {
				shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 20, 0, false, false), this);
			}
		}
		//下落方块
		AABB aabb = AABB.ofSize(new Vec3(this.getX(), this.getY() - 1, this.getZ()), Main.mobWidth(this)/2 + 8, 0, Main.mobWidth(this)/2 + 8);
		if (this.level() instanceof ServerLevel serverLevel) {
			for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
				Vec3 vec3 = blockPos.getCenter();
				if (!canSeeAngleFour(this, vec3, 0, 180)) continue;
				Main.spawnFallingBlockByPos(serverLevel, blockPos);
			}
		}
		return true;
	}
	public boolean slamTrueHurt(float f, float f2) {
		return slamTrueHurt(f, f2, true);
	}
	public boolean slamTrueHurt(float f, float f2, boolean bls) {
		if (!this.isSilent()) {
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.CELESTIAL_COILVINE_ATTACK, this.getSoundSource(), 10.0f, 1.0f);
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, this.getSoundSource(), 10.0f, 1.0f);
		}
		float ballReach = 4f;
		float radius = 6f;
		float angleYaw = (90.0F) * ((float) Math.PI / 180.0F);
		double x = radius * Mth.cos((float) (this.getYHeadRot() * (Math.PI / 180.0F) + angleYaw));
		double z = radius * Mth.sin((float) (this.getYHeadRot() * (Math.PI / 180.0F) + angleYaw));
		Vec3 start = new Vec3(getX() + x, this.getY(), getZ() +z);

		List<LivingEntity> listFind = this.level().getEntitiesOfClass(LivingEntity.class,
				this.getAttackBoundingBox().inflate(ballReach * 2, 4, ballReach * 2).move(x,0,z));
		listFind.removeIf(livingEntity -> livingEntity instanceof Mob mob && !isCelestialCoilvineTarget(this, mob) && this.getTarget() != mob && mob.getTarget() != this && !(mob.getTarget() != null && mob.getTarget().isAlliedTo(this)) && !(this.getTarget() != null && mob.isAlliedTo(this.getTarget())));
		listFind.removeIf(livingEntity -> livingEntity instanceof Player player && this.getTarget() != player && !(this.getTarget() != null && player.isAlliedTo(this.getTarget())));
		listFind.removeIf(livingEntity -> !(livingEntity instanceof Player || livingEntity instanceof Mob) || !this.hasLineOfSight(livingEntity));
		for (LivingEntity hurt : listFind) {
			if (hurt == null) continue;
			if ((this.distanceTo(hurt)) > Double.MAX_VALUE) continue;
			if ((start.distanceToSqr(hurt.position())) > ballReach * ballReach) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			Vec3 vec3 = hurt.getPosition(0);
			if (!canSeeAngleFour(this, vec3, 0, 180)) continue;
			AttackFind.attackBegin(this, hurt);
			float damageMulti = f;
			if (hurt instanceof Enemy)
				damageMulti *= 2.0f;
			if (hurt.getMobType() == MobType.UNDEAD)
				damageMulti *= 1.5f;
			if (EntityFactionFind.isFiend(hurt))
				damageMulti *= 1.5f;
			double healthOld = hurt.getHealth();
			DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.RADIANT, this);
			boolean bl = AttackFind.attackAfterCustomDamage(this, hurt, damageSource, damageMulti, f2, false, 0);
			if (bl) {
				if (bls) {
					if (!this.level().isClientSide()) {
						hurt.addEffect(new MobEffectInstance(JerotesMobEffects.PRONE.get(), 120, 0, false, false), this);
					}
				}
				double healthNew = hurt.getHealth();
				if ((float) (healthOld - healthNew)/2 > 0) {
					this.heal((float) (healthOld - healthNew) / 2);
				}
				if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt))
						&& !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
					double d = 0.0;
					if (hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
						d = Math.max(hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0);
					}
					double d3 = Math.max(0, 1 - d) + 0.2;
					hurt.setOnGround(false);
					hurt.setDeltaMovement(hurt.getDeltaMovement().add(
							-(this.getX() - hurt.getX()) * 0.2 * d3 * (0.7 + damageMulti / 3) * f2,
							-(this.getY() - hurt.getY()) * 0.4 * d3 * (0.7 + damageMulti / 3) * f2,
							-(this.getZ() - hurt.getZ()) * 0.2 * d3 * (0.7 + damageMulti / 3) * f2
					));
				}
			}
		}
		//范围粒子
		if (this.level() instanceof ServerLevel serverLevel) {
			ParticleOptions particleOptions = JerotesVillageParticleTypes.UNDEAD_BANISHING.get();
			float renderYawOffset = this.getYHeadRot();
			serverLevel.sendParticles(particleOptions,
					this.xo + radius * Mth.cos((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)),
					this.yo + 0.1f,
					this.zo + radius * Mth.sin((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)),
					0, 0, 0, 0, 0);
		}
		//震动
		List<LivingEntity> listShake = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(16.0, 16.0, 16.0));
		for (LivingEntity shake : listShake) {
			if (shake == null || shake == this || (this.distanceToSqr(shake)) > Double.MAX_VALUE)
				continue;
			if (!shake.level().isClientSide()) {
				shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 20, 0, false, false), this);
			}
		}

		if (this.level() instanceof ServerLevel serverLevel) {
			BlockPos blockPos = new BlockPos(
					Mth.floor(start.x),
					Mth.floor(start.y),
					Mth.floor(start.z)
			);
			Main.spawnUnevenBlockByPos(serverLevel, blockPos, 5);
		}
		//下落方块
		AABB aabb = AABB.ofSize(new Vec3(this.getX() + x, this.getY() - 1, this.getZ() + z), 8, 0, 8);
		if (this.level() instanceof ServerLevel serverLevel) {
			for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
				Vec3 vec3 = blockPos.getCenter();
				if (start.distanceToSqr(vec3) > ballReach * ballReach) continue;
				Main.spawnFallingBlockByPos(serverLevel, blockPos);
			}
		}
		return true;
	}
	//攻击范围粒子
	public void fourTrueHurtParticles(int n) {
		float normal = 16f;
		if (this.level() instanceof ServerLevel serverLevel) {
			for (int i = 0; i < 64; ++i) {
				double findX = this.getRandomX(1f) + ((this.getRandom().nextFloat() * normal) * 2 - normal);
				double findY = this.getY() + 1;
				double findZ = this.getRandomZ(1f) + ((this.getRandom().nextFloat() * normal) * 2 - normal);
				Vec3 vec3 = new Vec3(findX, findY, findZ);
				if (n == 1 && !canSeeAngleFour(this, vec3, 0, 75)) continue;
				if (n == 2 && !canSeeAngleFour(this, vec3, 90, 75)) continue;
				if (n == 3 && !canSeeAngleFour(this, vec3, -90, 75)) continue;
				if (n == 4 && !canSeeAngleFour(this, vec3, 180, 75)) continue;
				if (this.distanceToSqr(vec3) > 64) continue;
				serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_LEAF.get(),
						findX,
						findY,
						findZ,
						0, 0, 0.0, 0, 0.0);
			}
		}
	}
	public void frontParticles(int reach) {
		float normal = reach * 2;
		if (this.level() instanceof ServerLevel serverLevel) {
			for (int i = 0; i < 128; ++i) {
				double findX = this.getRandomX(1f) + ((this.getRandom().nextFloat() * normal) * 2 - normal);
				double findY = this.getY() + 1;
				double findZ = this.getRandomZ(1f) + ((this.getRandom().nextFloat() * normal) * 2 - normal);
				Vec3 vec3 = new Vec3(findX, findY, findZ);
				if (!canSeeAngleFour(this, vec3, 0, 180)) continue;
				if (this.distanceToSqr(vec3) > reach * reach) continue;
				serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_LEAF.get(),
						findX,
						findY,
						findZ,
						0, 0, 0.0, 0, 0.0);
			}
		}
	}
	public void slamParticles() {
		float ballReach = 4f;
		float radius = 6f;
		if (this.level() instanceof ServerLevel serverLevel) {
			for (int i = 0; i < 64; ++i) {
				float angleYaw = (90.0F) * ((float) Math.PI / 180.0F);
				double x = this.getX() + radius * Mth.cos((float) (this.getYHeadRot() * (Math.PI / 180.0F) + angleYaw));
				double z = this.getZ() + radius * Mth.sin((float) (this.getYHeadRot() * (Math.PI / 180.0F) + angleYaw));

				double findX = this.getRandomX(1f) + ((this.getRandom().nextFloat() * ballReach) * 2 - ballReach) + x - this.getX();
				double findY = this.getY() + 1;
				double findZ = this.getRandomZ(1f) + ((this.getRandom().nextFloat() * ballReach) * 2 - ballReach) + z - this.getZ();
				Vec3 start = new Vec3(x, findY, z);
				Vec3 vec3 = new Vec3(findX, findY, findZ);
				if (start.distanceToSqr(vec3) > ballReach * ballReach) continue;
				serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_LEAF.get(),
						findX,
						findY,
						findZ,
						0, 0, 0.0, 0, 0.0);
			}
		}
	}
	//
	public static boolean canSeeAngleFour(Entity see, Vec3 beSeen, float addRot, double angleThreshold) {
		float f = 0.0F;
		float f1 = (-see.getYHeadRot() + addRot) * 0.017453292F;
		float f2 = Mth.cos(f1);
		float f3 = Mth.sin(f1);
		float f4 = Mth.cos(f);
		float f5 = Mth.sin(f);
		Vec3 vec3 = new Vec3((double)(f3 * f4), (double)(-f5), (double)(f2 * f4));
		Vec3 vec32 = beSeen.vectorTo(see.position());
		vec32 = (new Vec3(vec32.x, 0.0, vec32.z)).normalize();
		return vec32.dot(vec3) < -1.0 + angleThreshold / 180.0;
	}
	@Override
	public void awardKillScore(@NotNull Entity entity, int score, @NotNull DamageSource damageSource) {
		super.awardKillScore(entity, score, damageSource);
		if (entity instanceof LivingEntity livingEntity) {
			this.heal(Math.min(livingEntity.getMaxHealth() / 5, this.getMaxHealth() / 50));
		}
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypeTags.IS_FALL)
				|| AttackFind.isWaterDamage(damageSource)
				|| damageSource.is(DamageTypes.IN_WALL)
				|| damageSource.is(DamageTypes.FLY_INTO_WALL)
				|| damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| damageSource.is(DamageTypes.DRY_OUT)
				|| damageSource.is(DamageTypes.CRAMMING)
				|| damageSource.is(DamageTypes.THORNS)
		)
			return true;
		return super.isInvulnerableTo(damageSource);
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (amount >= this.getHealth() && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && amount < this.getMaxHealth() * 1000 && !this.isSecondType() && this.isFirstHeal()) {
			this.setHealth(Math.max(1, this.getHealth()));
			this.changeBodyType();
			return false;
		}

		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (this.getHideUseTick() > 0)
			amount *= 0.5f;
		if (this.getBodyTypeUseTick() > 0)
			amount *= 0.25f;
		//邪不干正
		{
			if (damageSource.getEntity() instanceof Enemy)
				amount *= 0.5f;
			if (damageSource.getEntity() instanceof LivingEntity livingEntity && livingEntity.getMobType() == MobType.UNDEAD)
				amount *= 0.5f;
			else if (damageSource.getEntity() instanceof LivingEntity livingEntity && EntityFactionFind.isFiend(livingEntity))
				amount *= 0.5f;
		}
		float trueDamage = amount;
		//堂堂正正
		if (this.getTarget() != null && amount < this.getMaxHealth() * 10 &&
				damageSource.getEntity() != this.getTarget() &&
				!(damageSource.getEntity() instanceof OwnableEntity ownable && ownable.getOwner() == this.getTarget()))
			return false;
		if (this.getTarget() == null && amount < this.getMaxHealth() * 10 && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) &&
				!(damageSource.getEntity() instanceof LivingEntity))
			return false;
		//缩地成寸
		if (amount < this.getMaxHealth() * 20 && this.getHideUseTick() <= 80 && this.getHideUseTick() > 20)
			return false;

		if (damageSource.getEntity() != null && AttackFind.isMeleeDamage(damageSource) && amount > this.getMaxHealth() * 0.15 && !this.level().isClientSide() && !this.specialAction() && this.getHideUseTick() <= 0 && this.getRandom().nextFloat() < 0.3f) {
			this.setHideUseTick(120);
			this.setSpellTick(125);
		}
		if (this.isSecondType()) {
			//严阵以待
			if (amount < this.getMaxHealth() * 3 && amount > this.getMaxHealth() / 15)
				amount = this.getMaxHealth() / 15;
			if (damageSource.getEntity() != null && damageSource.getEntity().distanceTo(this) >= 64)
				amount /= 8;
			else if (damageSource.getDirectEntity() instanceof AbstractArrow)
				amount /= 3;
			else if (damageSource.getEntity() != null && damageSource.getEntity().distanceTo(this) >= 16)
				amount /= 3;
			else if (damageSource.getEntity() != null && damageSource.getEntity() instanceof LivingEntity livingEntity && this.isWithinMeleeAttackRange(livingEntity))
				amount /= 2;
		}
		trueDamage = amount;
		//固若金汤
		float armorChance = 0.25f;
		if (this.isSecondType()) {
			//严阵以待
			armorChance = 0f;
		}
		if (this.isArmor()) {
			if (!(damageSource.getEntity() != null && damageSource.is(DamageTypeTags.BYPASSES_COOLDOWN) && EntityAndItemFind.isIgnoreSpecialHurtCooldown(damageSource.getEntity().getType()))) {
				trueDamage = trueDamage/5f;
				boolean bl = super.hurt(damageSource, trueDamage);
				if (this.isDeadOrDying() && amount < this.getMaxHealth() * 1000 && !this.isSecondType() && this.isFirstHeal()) {
					this.setHealth(Math.max(1, this.getHealth()));
					this.changeBodyType();
					return false;
				}
				return bl;
			}
			else if (amount / 3 > this.getArmorDamage()) {
				trueDamage = trueDamage/80f;
				if (!this.level().isClientSide()) {
					this.setArmorTick(0);
					this.setArmorDamage(0);
				}
				boolean bl = super.hurt(damageSource, trueDamage);
				if (this.isDeadOrDying() && amount < this.getMaxHealth() * 1000 && !this.isSecondType() && this.isFirstHeal()) {
					this.setHealth(Math.max(1, this.getHealth()));
					this.changeBodyType();
					return false;
				}
				return bl;
			}
			else {
				return false;
			}
		}
		else if (this.getRandom().nextFloat() > armorChance && this.isLock() && !this.isInvulnerableTo(damageSource) && (!this.specialAction() || this.isSecondType()) && !(damageSource.getEntity() instanceof LivingEntity livingEntity && AttackFind.FindCanNotAttack(livingEntity, this))) {
			if (!this.level().isClientSide()) {
				this.setArmorTick(this.isSecondType() ? 60 : 40);
				this.setArmorDamage(amount);
			}
			this.clearFire();
			if (!this.isSilent()) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.CELESTIAL_COILVINE_BLOCK, this.getSoundSource(), 10.0f, 1.0f);
			}
			if (this.level() instanceof ServerLevel serverLevel) {
				//技能-固若金汤
				if (!this.isInvisible()) {
					serverLevel.sendParticles(JerotesVillageParticleTypes.IMPREGNABLE_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
				}
				serverLevel.sendParticles(JerotesVillageParticleTypes.IMPREGNABLE.get(), this.getX(), this.getY(0.6f), this.getZ(), 0, 0.0, 0.0, 0.0, 0);
			}
		}
		boolean bl = super.hurt(damageSource, trueDamage);
		if (this.isDeadOrDying() && amount < this.getMaxHealth() * 1000 && !this.isSecondType() && this.isFirstHeal()) {
			this.setHealth(Math.max(1, this.getHealth()));
			this.changeBodyType();
			return false;
		}
		return bl;
	}
	@Override
	public void heal(float amount) {
		if (!(amount <= 0.0F)) {
			float f = this.getHealth();
			if (f > 0.0F) {
				this.setHealth(f + amount * 3f);
			}
		}
	}

	public void changeBodyType() {
		if (!this.level().isClientSide()) {
			this.setFirstHeal(false);
			this.setLastBodyType(this.getBodyType());
			this.setAnimTick(40);
			int spellRandom = this.getRandom().nextInt(30);
			if (spellRandom >= 20) {
				this.setAnimationState("change2");
				this.setBodyType(2);
			}
			else if (spellRandom >= 10) {
				this.setAnimationState("change3");
				this.setBodyType(3);
			}
			else {
				this.setAnimationState("change4");
				this.setBodyType(4);
			}
			this.setBodyTypeUseTick(40);
			this.setSpellTick(60);
			this.setChangeBodyTypeToSecondUseTick(0);
			this.setChangeBodyTypeToFirstUseTick(0);
			this.setHideTick(1200);
		}
		else {
			this.deathTime = 0;
			this.hurtTime = 0;
		}
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == JerotesMobEffects.ABACK.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.LEVITATION) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.POISON) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.DEADLY_POISON.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.HARM) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.WITHER) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.BLEEDING.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesVillageMobEffects.DAZZLING_LIGHT.get()) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
	}

	@Override
	public void handleEntityEvent(byte by) {
		if (by == 67) {
			BossMusicPlayer.playBossMusic(this);
		}
		else if (by == 68) {
			BossMusicPlayer.stopBossMusic(this);
		}
		else {
			super.handleEntityEvent(by);
		}
	}
	@Override
	public void setYRot(float yRot) {
		super.setYRot(0);
		this.yBodyRotO = 0;
		this.yBodyRot = 0;
	}
	@Override
	public void setYBodyRot(float yBodyRot) {
		this.yBodyRotO = 0;
		this.yBodyRot = 0;
	}
	@Override
	public void setYHeadRot(float yHeadRot) {
		this.yHeadRotO = this.yHeadRot;
		this.yHeadRot = yHeadRot;
	}
	@Override
	public boolean canAttack(LivingEntity livingEntity) {
		if (((this.getTeam() == null && livingEntity.getTeam() == null) || this.getTeam() == livingEntity.getTeam()) &&
				EntityFactionFind.isFaction(this, livingEntity)
		) {
			return false;
		}
		return super.canAttack(livingEntity);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		this.yBodyRot = 0.0f;
		this.yBodyRotO = 0.0f;
		if (!this.level().isClientSide()) {
			this.setAnimTick(20);
			this.setSpellTick(20);
			this.setBodyType(1);
			this.setFirstHeal(true);
			this.setAnimationState("start");
		}
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}

	@Override
	public void tickDeath() {
		if(deathTime <= 0) {
			if (!this.level().isClientSide()) {
				this.setAnimTick(40);
				this.setAnimationState("dead");
			}
		}
		++this.deathTime;
		//粒子
		if (this.level() instanceof ServerLevel serverLevel) {
			for (int i = 0; i < 5; ++i) {
				double angle = (Math.PI * 2 * Main.randomReach(this.getRandom(), 1, 30)) / 30;
				for (int i2 = 0; i2 < 10; ++i2) {
					double radius = 1 + i2 / 5f;
					double offsetX = Math.cos(angle) * radius;
					double offsetZ = Math.sin(angle) * radius;
					serverLevel.sendParticles(ParticleTypes.CHERRY_LEAVES, this.getX() + offsetX, this.getY(i2/10f), this.getZ() + offsetZ, 0, 0, 0, 0, 0);
				//	serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_LEAF.get(), this.getX() + offsetX, this.getY(i2/10f), this.getZ() + offsetZ, 0, this.getRandom().nextFloat() * 0.25f + 0.01f, 0.05f, this.getRandom().nextFloat() * 0.25f + 0.01f, 1f);
				}
			}
		}
		//治愈
		List<LivingEntity> needHelp = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox());
		for (LivingEntity heal : needHelp) {
			if (heal == null) continue;
			if (heal == this) continue;
			if ((this.distanceTo(heal)) > Double.MAX_VALUE) continue;
			if (!AttackFind.SameFactionAvoidDamage(this, heal, false)) continue;
			heal.heal(5f);
		}
		this.idleBaseAnimationState.stop();
		if (this.deathTime >= 40 && !this.level().isClientSide() && !this.isRemoved()) {
			this.remove(RemovalReason.CHANGED_DIMENSION);
		}
	}

	static class CelestialCoilvineBodyRotationControl extends BodyRotationControl {
		public CelestialCoilvineBodyRotationControl(Mob mob) {
			super(mob);
		}

		@Override
		public void clientTick() {
		}
	}

	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	private int remainingPersistentAngerTime;
	private UUID persistentAngerTarget;

	@Override
	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	}

	@Override
	public void setRemainingPersistentAngerTime(int n) {
		this.remainingPersistentAngerTime = n;
	}

	@Override
	public int getRemainingPersistentAngerTime() {
		return this.remainingPersistentAngerTime;
	}

	@Override
	public void setPersistentAngerTarget(UUID uUID) {
		this.persistentAngerTarget = uUID;
	}

	@Override
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}
}
