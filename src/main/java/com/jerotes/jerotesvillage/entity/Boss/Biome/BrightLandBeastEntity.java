package com.jerotes.jerotesvillage.entity.Boss.Biome;

import com.jerotes.jerotes.client.sound.BossMusicPlayer;
import com.jerotes.jerotes.config.MainConfig;
import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesParticleTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Animal.ArchnosedHornbeastEntity;
import com.jerotes.jerotesvillage.entity.Part.BrightLandBeastPart;
import com.jerotes.jerotesvillage.event.BossBarEvent;
import com.jerotes.jerotesvillage.init.*;
import com.jerotes.jerotesvillage.spell.OtherSpellFind;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class BrightLandBeastEntity extends ArchnosedHornbeastEntity implements BeastToughEntity, BossEntity, LoreUndyingEntity, ShockEntity, SpellUseEntity {
	private static final EntityDataAccessor<Integer> BEAST_TOUGH_TICK = SynchedEntityData.defineId(BrightLandBeastEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Float> BEAST_TOUGH_DAMAGE = SynchedEntityData.defineId(BrightLandBeastEntity.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Boolean> IS_BEAST_TOUGH = SynchedEntityData.defineId(BrightLandBeastEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> BREAK_BEAST_TOUGH_TICK = SynchedEntityData.defineId(BrightLandBeastEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BREAK_BEAST_TOUGH_COOLDOWN_TICK = SynchedEntityData.defineId(BrightLandBeastEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IS_BREAK_BEAST_TOUGH = SynchedEntityData.defineId(BrightLandBeastEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_LEGEND = SynchedEntityData.defineId(BrightLandBeastEntity.class, EntityDataSerializers.BOOLEAN);
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.RED, false);
	public final BrightLandBeastPart back;
	public final BrightLandBeastPart[] allParts;

	public BrightLandBeastEntity(EntityType<? extends BrightLandBeastEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(3.1f);
		this.xpReward = 400;
		this.setPathfindingMalus(BlockPathTypes.LEAVES, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.FENCE, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.BLOCKED, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_IRON_CLOSED, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0f);
		if (OtherMainConfig.BossBaseAttributeCanUseConfig) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(OtherMainConfig.BrightLandBeastMaxHealth);
			this.setHealth(this.getMaxHealth());
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(OtherMainConfig.BrightLandBeastMeleeDamage);
			this.getAttribute(Attributes.ARMOR).setBaseValue(OtherMainConfig.BrightLandBeastArmor);
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(OtherMainConfig.BrightLandBeastMovementSpeed);
			this.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(OtherMainConfig.BrightLandBeastAttackKnockback);
			this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(OtherMainConfig.BrightLandBeastKnockbackResistance);
		}
		if (level.isClientSide) {
			BossBarEvent.BOSSES.add(this);
		}
		back = new BrightLandBeastPart(this, "back", 7.25f, 3f);
		allParts = new BrightLandBeastPart[]{back};
	}
	//百分比
	public boolean hasPercentageDamage() {
		return MainConfig.HasPercentageDamage.contains(this.getEncodeId())
				|| OtherMainConfig.BossHasPercentageDamage.contains(this.getEncodeId()) || this.isLegend();
	}
	public float PercentageDamage(DamageSource damageSource) {
		if (AttackFind.MagicResistance(damageSource)) {
			return(float) OtherMainConfig.BrightLandBeastMagicAttackPercentage;
		}
		return (float) OtherMainConfig.BrightLandBeastAttackPercentage;
	}
	//限伤
	public boolean hasDamageCap() {
		return MainConfig.HasDamageCap.contains(this.getEncodeId()) || OtherMainConfig.BossHasDamageCap.contains(this.getEncodeId()) || this.isLegend();
	}
	public float DamageCap(DamageSource damageSource, Entity entity) {
		return (float) OtherMainConfig.BrightLandBeastDamageCap;
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
		return (float)OtherMainConfig.BrightLandBeastDamageCooldownTick * base;
	}

	@Override
	public int getExperienceReward() {
		return 400;
	}

	@Override
	public void remove(@NotNull RemovalReason reason) {
		super.remove(reason);
		if (allParts != null) {
			for (BrightLandBeastPart part : allParts) {
				part.remove(RemovalReason.KILLED);
			}
		}
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
		if (this.start > 0) {
			this.bossEvent.setProgress(health1 / this.getMaxHealth());
			health1 += (this.getHealth() - health1) / 60;
		}
		else{
			this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MAX_HEALTH, 650);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 40);
		builder = builder.add(Attributes.ARMOR, 4);
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.32);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 4.0);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);

		builder = builder.add(Attributes.FOLLOW_RANGE, 128);
		return builder;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.BRIGHT_LAND_BEAST_AMBIENT;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.BRIGHT_LAND_BEAST_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.BRIGHT_LAND_BEAST_DEATH;
	}
	@Override
	public void playRushSound() {
		this.playSound(JerotesVillageSoundEvents.BRIGHT_LAND_BEAST_RUSH, 1.0f, 1.0f);
	}
	@Override
	public void playWalkSound() {
		this.playSound(JerotesVillageSoundEvents.BRIGHT_LAND_BEAST_WALK, 1.0f, 1.0f);
	}
	@Override
	public void trampUse() {
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2.5, 2.5, 2.5));
		list.removeIf(livingEntity -> livingEntity == this || livingEntity.getY() >= this.getEyeY() || AttackFind.FindCanNotAttack(this, livingEntity));
		if (list.isEmpty()) return;
		for (LivingEntity shock : list) {
			if (shock == null) continue;
			if ((this.distanceToSqr(shock)) > Double.MAX_VALUE) continue;
			if (shock.getY() >= this.getEyeY()) continue;
			if (AttackFind.FindCanNotAttack(this, shock)) continue;
			if (this.getOwner() != null && AttackFind.FindCanNotAttack(this.getOwner(), shock)) continue;
			if (shock instanceof ArchnosedHornbeastEntity archnosedHornbeast && archnosedHornbeast.getTarget() != this && this.getTarget() != archnosedHornbeast) continue;
			if (!shock.onGround()) continue;
			if (!(Main.mobSizeSmall(shock) || Main.mobSizeMedium(shock))) continue;
			if (shock.getHealth() <= this.getHealth()/10) {
				shock.hurt(this.damageSources().noAggroMobAttack(this), 8f);
			}
			else{
				AttackFind.attackBegin(this, shock);
				AttackFind.attackAfter(this, shock, 0, 0.2f, true, 8f);
			}
		}
	}
	@Override
	protected float getSoundVolume() {
		return 5.0f;
	}
	@Override
	public int getMaxAirSupply() {
		return 6000;
	}
	@Override
	public int getMaxFallDistance() {
		if (this.getHealth() > 160) {
			return super.getMaxFallDistance() * 64;
		}
		return super.getMaxFallDistance();
	}
	@Override
	public void makeStuckInBlock(BlockState blockState, Vec3 vec3) {
		this.resetFallDistance();
	}
	@Override
	public boolean canCollideWith(Entity entity) {
		if (entity instanceof BrightLandBeastPart) {
			return false;
		}
		return entity.canBeCollidedWith() && !this.isPassengerOfSameVehicle(entity) && this.isAlive();
	}
	@Override
	public boolean useShock() {
		return this.getHealth() > this.getMaxHealth()/2;
	}
	@Nullable
	@Override
	public BrightLandBeastEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		UUID uUID;
		BrightLandBeastEntity archnosedHornbeast = JerotesVillageEntityType.BRIGHT_LAND_BEAST.get().create(serverLevel);
		if (archnosedHornbeast != null && (uUID = this.getOwnerUUID()) != null) {
			archnosedHornbeast.setOwnerUUID(uUID);
			archnosedHornbeast.setTame(true);
		}
		return archnosedHornbeast;
	}
	@Override
	public boolean isFood(ItemStack itemStack) {
		return itemStack.is(TagKey.create(Registries.ITEM, new ResourceLocation("jerotes", "aa_creative_claw")));
	}
	@Override
	public boolean isBaby() {
		return false;
	}
	@Override
	public boolean isOnFire() {
		if (this.isBeastTough()) {
			return false;
		}
		return super.isOnFire();
	}
	public SoundEvent getBossMusic() {
		return JerotesVillageSoundEvents.BRIGHT_LAND_BEAST_MUSIC;
	}

	public int start = -101;
	private float health1 = 1;
	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
		this.bossEvent.setName(this.getDisplayName());
	}
	public void setBeastToughDamage(float f){
		this.getEntityData().set(BEAST_TOUGH_DAMAGE, f);
	}
	public float getBeastToughDamage(){
		return this.getEntityData().get(BEAST_TOUGH_DAMAGE);
	}
	@Override
	public boolean isBeastTough() {
		return this.getEntityData().get(IS_BEAST_TOUGH) && this.isAlive() && !this.isBreakBeastTough();
	}
	public void setBeastTough(boolean bl) {
		this.getEntityData().set(IS_BEAST_TOUGH, bl);
	}
	public void setBeastToughTick(int n){
		this.getEntityData().set(BEAST_TOUGH_TICK, n);
	}
	public int getBeastToughTick(){
		return this.getEntityData().get(BEAST_TOUGH_TICK);
	}
	@Override
	public boolean isBreakBeastTough() {
		return this.getEntityData().get(IS_BREAK_BEAST_TOUGH);
	}
	public void setBreakBeastTough(boolean bl) {
		this.getEntityData().set(IS_BREAK_BEAST_TOUGH, bl);
	}
	public void setBreakBeastToughTick(int n){
		this.getEntityData().set(BREAK_BEAST_TOUGH_TICK, n);
	}
	public int getBreakBeastToughTick(){
		return this.getEntityData().get(BREAK_BEAST_TOUGH_TICK);
	}
	public void setBreakBeastToughCooldownTick(int n){
		this.getEntityData().set(BREAK_BEAST_TOUGH_COOLDOWN_TICK, n);
	}
	public int getBreakBeastToughCooldownTick(){
		return this.getEntityData().get(BREAK_BEAST_TOUGH_COOLDOWN_TICK);
	}
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
	public float getBeastToughBreakHurtCooldownMultiple() {
		return (float) OtherMainConfig.BrightLandBeastBreakToughHurtCooldownMultiple;
	}
	public int getBaseBeastToughTick() {
		return (int) (OtherMainConfig.BrightLandBeastToughHurtCooldown * 20);
	}
	public float getBreakBeastToughNeedBaseDamage() {
		return (int) (OtherMainConfig.BrightLandBeastBreakToughNeedBaseDamage);
	}
	public int spellLevel = 1;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean("IsLegend", this.isLegend());
		compoundTag.putInt("SpellLevel", this.spellLevel);

		compoundTag.putInt("BeastToughTick", this.getBeastToughTick());
		compoundTag.putFloat("BeastToughDamage", this.getBeastToughDamage());
		compoundTag.putBoolean("IsBeastTough", this.isBeastTough());
		compoundTag.putInt("BreakBeastToughTick", this.getBreakBeastToughTick());
		compoundTag.putInt("BreakBeastToughCooldownTick", this.getBreakBeastToughCooldownTick());
		compoundTag.putBoolean("IsBreakBeastTough", this.isBreakBeastTough());
		
		compoundTag.putInt("Start", this.start);
		compoundTag.putFloat("Health1", this.health1);
		this.addPersistentAngerSaveData(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		this.setLegend(compoundTag.getBoolean("IsLegend"));
		this.spellLevel = compoundTag.getInt("SpellLevel");

		this.setBeastToughTick(compoundTag.getInt("BeastToughTick"));
		this.setBeastToughDamage(compoundTag.getFloat("BeastToughDamage"));
		this.setBeastTough(compoundTag.getBoolean("IsBeastTough"));
		this.setBreakBeastToughTick(compoundTag.getInt("BreakBeastToughTick"));
		this.setBreakBeastToughCooldownTick(compoundTag.getInt("BreakBeastToughCooldownTick"));
		this.setBreakBeastTough(compoundTag.getBoolean("IsBreakBeastTough"));
		
		this.start = compoundTag.getInt("Start");
		this.health1 = compoundTag.getFloat("Health1");
		this.readPersistentAngerSaveData(this.level(), compoundTag);
		this.bossEvent.setId(this.getUUID());
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();

		this.getEntityData().define(BEAST_TOUGH_TICK, 0);
		this.getEntityData().define(BEAST_TOUGH_DAMAGE, 0f);
		this.getEntityData().define(IS_BEAST_TOUGH, false);
		this.getEntityData().define(BREAK_BEAST_TOUGH_TICK, 0);
		this.getEntityData().define(BREAK_BEAST_TOUGH_COOLDOWN_TICK, 0);
		this.getEntityData().define(IS_BREAK_BEAST_TOUGH, false);
		
		this.getEntityData().define(IS_LEGEND, false);
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (itemStack.is(TagKey.create(Registries.ITEM, new ResourceLocation("jerotes", "aa_creative_claw")))) {
			if (!this.isTame()) {
				this.tame(player);
				this.setOrderedToSit(true);
				this.jumping = false;
				this.navigation.stop();
				this.setTarget(null);
				this.level().broadcastEntityEvent(this, (byte) 7);
				return InteractionResult.SUCCESS;
			} else {
				this.level().broadcastEntityEvent(this, (byte) 6);
			}
			this.setPersistenceRequired();
			return InteractionResult.SUCCESS;
		}
		return super.mobInteract(player, interactionHand);
	}

	@Override
	public boolean isMultipartEntity() {
		return true;
	}
	@Override
	public PartEntity<?>[] getParts() {
		return allParts;
	}
	public BrightLandBeastPart[] getSubEntities() {
		return this.allParts;
	}
	@Override
	public void recreateFromPacket(ClientboundAddEntityPacket clientboundAddEntityPacket) {
		super.recreateFromPacket(clientboundAddEntityPacket);
		BrightLandBeastPart[] brightLandBeastParts = this.getSubEntities();
		for (int i = 0; i < brightLandBeastParts.length; ++i) {
			brightLandBeastParts[i].setId(i + 1 + clientboundAddEntityPacket.getId());
		}
	}
	private void tickMultipart() {
		Vec3[] avector3d = new Vec3[this.allParts.length];
		for (int j = 0; j < this.allParts.length; ++j) {
			avector3d[j] = new Vec3(this.allParts[j].getX(), this.allParts[j].getY(), this.allParts[j].getZ());
		}
		Vec3 center = this.position().add(0, this.getBbHeight() * 0.5F, 0);
		this.back.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 3f, 0), 0, this.yBodyRot).add(center));
		for (int l = 0; l < this.allParts.length; ++l) {
			this.allParts[l].xo = avector3d[l].x;
			this.allParts[l].yo = avector3d[l].y;
			this.allParts[l].zo = avector3d[l].z;
			this.allParts[l].xOld = avector3d[l].x;
			this.allParts[l].yOld = avector3d[l].y;
			this.allParts[l].zOld = avector3d[l].z;
		}
	}
	private Vec3 rotateOffsetVec(Vec3 offset, float xRot, float yRot) {
		return offset.xRot(-xRot * ((float) Math.PI / 180F)).yRot(-yRot * ((float) Math.PI / 180F));
	}

	@Override
	public void tick() {
		tickMultipart();
		super.tick();
		if (!level().isClientSide && getBossMusic() != null) {
			if (!isSilent() && getTarget() instanceof Player player && player.isAlive()) {
				this.level().broadcastEntityEvent(this, (byte)67);
			}
			else {
				this.level().broadcastEntityEvent(this, (byte)68);
			}
		}
		//技能-强韧
		if (!level().isClientSide()) {
			this.setBeastToughTick(Math.max(0, this.getBeastToughTick() - 1));
			this.setBreakBeastToughTick(Math.max(0, this.getBreakBeastToughTick() - 1));
			this.setBreakBeastToughCooldownTick(Math.max(0, this.getBreakBeastToughCooldownTick() - 1));
			this.setBeastTough(this.getBeastToughTick() > 0);
			this.setBreakBeastTough(this.getBreakBeastToughTick() > 0);
		}
		if (this.isBeastTough() && this.getRemainingFireTicks() > 0) {
			this.clearFire();
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.bossEvent.update();
		//出现召唤
		if (this.start > 0) {
			this.start -= 1;
		}
		else if (this.start == 0) {
			if (!this.isSilent()) {
				this.playSound(JerotesVillageSoundEvents.BRIGHT_LAND_BEAST_AMBIENT, 10.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
			}
			if (!this.level().isClientSide) {
				this.addEffect(new MobEffectInstance(MobEffects.GLOWING, 660, 1));
			}
			this.start = -1;
		}
		else if (this.start > -100) {
			this.start -= 1;
		}
		if (this.start <= -100) {
			this.start = -101;
		}

		//触发地震
		if (this.getTarget() != null && this.getOwner() == null && !this.isNoAi()) {
			if (this.distanceTo(this.getTarget()) < 24 && getQuakeUseTick() <= 0 && this.getQuakeTick() <= 0 && this.getAttackTick() <= -(int)( OtherMainConfig.BrightLandBeastMeleeAttackCooldown * 20)) {
				if (!this.level().isClientSide()) {
					this.setQuakeUseTick(110);
					this.setQuakeTick((int) (OtherMainConfig.BrightLandBeastQuakeCooldown * 20));
					this.setAnimTick(20);
					this.setAnimationState("quakeStart");
					this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 3), this);
				}
			}
		}
		//巨兽震撼
		if (this.isAlive() && this.getHealth() > this.getMaxHealth() / 2 && !this.isNoAi() && this.tickCount % 120 == 0) {
			//给予buff
			this.addAback();
		}
	}

	public boolean addAback() {
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(32.0, 32.0, 32.0));
		double d = Double.MAX_VALUE;
		for (LivingEntity shock : list) {
			if (shock == null) continue;
			if (shock == this) continue;
			if ((this.distanceToSqr(shock)) > d) continue;
			if (AttackFind.FindCanNotAttack(this, shock)) continue;
			if (this.getOwner() != null && AttackFind.FindCanNotAttack(this.getOwner(), shock)) continue;
			//震撼免疫
			if (EntityAndItemFind.isAbackAwayImmune(shock.getType())) continue;
			if (shock.getHealth() > this.getHealth() / 1.5f) continue;
			if (shock.hasEffect(JerotesVillageMobEffects.ABUNDANT_COURAGE.get())
					|| shock.hasEffect(JerotesVillageMobEffects.RAMPANT.get())) continue;
			if (shock instanceof ArchnosedHornbeastEntity archnosedHornbeast && archnosedHornbeast.getTarget() != this && this.getTarget() != archnosedHornbeast)
				continue;
			if (!shock.level().isClientSide()) {
				shock.addEffect(new MobEffectInstance(JerotesMobEffects.ABACK.get(), 240, 0), this);
				shock.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 240, 0), this);
			}
		}
		if (this.level().isClientSide()) {
			for (int i = 0; i < 24; ++i) {
				this.level().addParticle(JerotesVillageParticleTypes.ABACK.get(), this.getRandomX(0.5), this.getRandomY() + this.getBbHeight()/4, this.getRandomZ(0.5), 0.0, 0.02, 0.0);
			}
		}
		return true;
	}

	public boolean trueHurt() {
		if (!this.isSilent()) {
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.BRIGHT_LAND_BEAST_ATTACK, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
		}
		float damageMulti = 1.0f;
		float knockbackMulti = 1.0f;
		if (this.getAttackUse() == 1) {
			knockbackMulti = 1.25f;
		}
		if (this.getAttackUse() == 2) {
			damageMulti = 1.25f;
		}
		if (this.getAttackUse() == 3) {
			damageMulti = 1.5f;
			knockbackMulti = 1.25f;
		}
		if (this.getAttackUse() == 4) {
			damageMulti = 1.5f;
			knockbackMulti = 1.05f;
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(5.0, 4.0, 5.0));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			if (!Main.canSee(hurt, this) && !this.getAttackBoundingBox().intersects(hurt.getBoundingBox()) && this.getAttackUse() != 2 && this.getAttackUse() != 4) continue;
			if (hurt instanceof ArchnosedHornbeastEntity archnosedHornbeast && archnosedHornbeast.getTarget() != this && this.getTarget() != archnosedHornbeast)
				continue;
			//计算伤害
			float hurtBrightLandBeast = 0;
			if (JerotesVillageGameRules.JEROTES_VILLAGE_BRIGHT_LAND_BEAST_TO_GIANT_ENTITY != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_BRIGHT_LAND_BEAST_TO_GIANT_ENTITY)) {
				hurtBrightLandBeast = (Main.mobWidth(hurt) * 4 * Main.mobHeight(hurt));
				if (hurtBrightLandBeast > this.getHealth() / 4) {
					hurtBrightLandBeast = this.getHealth() / 4;
				}
			}
			float damages = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) * damageMulti + hurtBrightLandBeast;
			AttackFind.attackBegin(this, hurt);
			boolean bl2 = AttackFind.attackAfter(this, hurt, 0f, knockbackMulti, true, damages);
			if (bl2) {
				//击飞
				if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt) || this.getBoundingBox().getSize() > hurt.getBoundingBox().getSize())
						&& !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
					double d = 0.0;
					if (hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
						d = Math.max(hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0);
					}
					double d3 = Math.max(0, 1 - d) + 0.5;
					hurt.setOnGround(false);
					hurt.setDeltaMovement(hurt.getDeltaMovement().add(
							-(this.getX() - hurt.getX()) * 1.4 * d3 * (0.7 + knockbackMulti / 3),
							-(this.getY() - hurt.getY()) * 1.2 * d3 * (0.7 + knockbackMulti / 3) + 0.2f * knockbackMulti,
							-(this.getZ() - hurt.getZ()) * 1.4 * d3 * (0.7 + knockbackMulti / 3)
							));
				}
				if (this.getAttackUse() == 2 && this.getAttackUse() == 4) {
					if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt)) && !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
						hurt.setOnGround(false);
						hurt.setDeltaMovement(hurt.getDeltaMovement().add((7 - this.distanceTo(hurt)) / 4, (7 - this.distanceTo(hurt)) / 4, (7 - this.distanceTo(hurt)) / 4));
					}
				}
				if (hurt.level() instanceof ServerLevel serverLevel) {
					serverLevel.sendParticles(JerotesVillageParticleTypes.BRIGHT_LAND_BEAST_HURT.get(), hurt.getX(), hurt.getY() + 0.1, hurt.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
				}
			}
		}
		//震动
		if (this.getAttackUse() == 2 || this.getAttackUse() == 4) {
			List<LivingEntity> listShake = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(24.0, 24.0, 24.0));
			for (LivingEntity shake : listShake) {
				if (shake == null || shake == this || (this.distanceToSqr(shake)) > Double.MAX_VALUE)
					continue;
				if (!shake.level().isClientSide()) {
					shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 40, 0, false, false), this);
				}
			}
		}
		//横扫效果
		Main.sweepAttack(this);
		//下落方块
		if (this.level() instanceof ServerLevel serverLevel) {
			AABB aabb = AABB.ofSize(new Vec3(this.getX(), this.getY() - 1, this.getZ()), Main.mobWidth(this)/2 + 5, 0, Main.mobWidth(this)/2 + 5);
			for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
				if (!Main.canSee(blockPos.getCenter(), this) && !(this.getAttackUse() == 2 || this.getAttackUse() == 4)) continue;
				if (!Main.canSeeAngle(this, blockPos.getCenter(), 40) && !(this.getAttackUse() == 2 || this.getAttackUse() == 4 || this.getAttackUse() == 3)) continue;
				Main.spawnFallingBlockByPos(serverLevel, blockPos);
			}
		}
		ParticleOptions particleOptions = ParticleTypes.CAMPFIRE_COSY_SMOKE;
		if (this.getAttackUse() == 4) {
			//范围粒子
			if (this.level() instanceof ServerLevel serverLevel) {
				double d = -Mth.sin(this.getYRot() * 0.017453292f);
				double d2 = Mth.cos(this.getYRot() * 0.017453292f);
				for (int i = 0; i < 160; i++) {
					double angle = (Math.PI * 2 * i) / 160;
					double radius = 8;
					double offsetX = Math.cos(angle) * radius;
					double offsetZ = Math.sin(angle) * radius;
					// 生成烟雾粒子
					serverLevel.sendParticles(particleOptions, this.getX() + offsetX + d, this.getY() + 0.2f, this.getZ() + offsetZ + d2, 0, offsetX * 0.02, -0.04, offsetZ * 0.02, 1.0);
				}
				for (int i = 0; i < 100; i++) {
					double angle = (Math.PI * 2 * i) / 100;
					double radius = 5;
					double offsetX = Math.cos(angle) * radius;
					double offsetZ = Math.sin(angle) * radius;
					// 生成烟雾粒子
					serverLevel.sendParticles(particleOptions, this.getX() + offsetX + d, this.getY() + 0.2f, this.getZ() + offsetZ + d2, 0, offsetX * 0.05, -0.02, offsetZ * 0.05, 1.0);
				}
			}
		}
		return true;
	}

	public boolean earthQuake() {
		float damage = (float) (OtherMainConfig.BrightLandBeastQuakeBaseDamage * 0.5f + this.getHealth() * OtherMainConfig.BrightLandBeastQuakeHealthMultipleDamage * 0.5f);
		if (this.level().getDifficulty() == Difficulty.NORMAL) {
			damage = (float) (OtherMainConfig.BrightLandBeastQuakeBaseDamage + this.getHealth() * OtherMainConfig.BrightLandBeastQuakeHealthMultipleDamage);
		} else if (this.level().getDifficulty() == Difficulty.HARD) {
			damage = (float) (OtherMainConfig.BrightLandBeastQuakeBaseDamage * 1.5f + this.getHealth() * OtherMainConfig.BrightLandBeastQuakeHealthMultipleDamage * 1.5f);
		}
		OtherSpellFind.EarthQuake(this, (int) damage, 18);
		if (!this.isSilent() && this.isAlive()) {
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.BRIGHT_LAND_BEAST_QUAKE, this.getSoundSource(), 100.0f, 0.8f + this.getRandom().nextFloat() * 0.4f);
		}
		if (!this.level().isClientSide()) {
			this.setQuakeTick((int) (OtherMainConfig.BrightLandBeastQuakeCooldown * 20));
		}
		return true;
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (this.start > -100){
			return true;
		}
		if (damageSource.is(DamageTypes.THORNS))
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (amount <= 3) {
			return false;
		}

		//强韧期间
		if (this.isBeastTough() && !this.isBreakBeastTough()) {
			//破除强韧
			if (this.getBeastToughDamage() > this.getBreakBeastToughNeedBaseDamage() && this.getBreakBeastToughCooldownTick() <= 0 && amount > this.getBeastToughDamage() * this.getBeastToughBreakHurtCooldownMultiple() && damageSource.getEntity() != null && EntityAndItemFind.isIgnoreSpecialHurtCooldown(damageSource.getEntity().getType())) {
				if (!this.level().isClientSide()) {
					this.setBreakBeastToughTick(this.getBaseBreakBeastToughTick());
					this.setBreakBeastToughCooldownTick(this.getBaseBreakBeastToughCooldownTick());
					if (!this.level().isClientSide()) {
						this.addEffect(new MobEffectInstance(JerotesMobEffects.PARALYZED.get(), this.getBaseBreakBeastToughHoldTick(), 0, false, false));
					}
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesParticleTypes.BEAST_TOUGH_BREAK.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
				}
			}
			//强韧抵挡
			else if (!damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && amount <= this.getBeastToughDamage() * this.getBeastToughBreakHurtCooldownMultiple()) {
				if (this.level() instanceof ServerLevel serverLevel) {
					if (!this.isInvisible()) {
						serverLevel.sendParticles(JerotesParticleTypes.BEAST_TOUGH_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
					}
				}
				return false;
			}
			//其他情况无视强韧
		}
		//其他情况设置强韧上限
		if (!this.level().isClientSide()) {
			this.setBeastToughDamage(amount);
		}
		//设置强韧间隔
		double toughBase = this.getBaseBeastToughTick();
		float toughMultiple = 0.5f;
		if (this.level().getDifficulty() == Difficulty.NORMAL) {
			toughMultiple = 1f;
		} else if (this.level().getDifficulty() == Difficulty.HARD) {
			toughMultiple = 1.5f;
		}
		if (!this.level().isClientSide()) {
			this.setBeastToughTick((int) (toughBase * toughMultiple));
		}

		if (damageSource.is(DamageTypeTags.IS_FALL)) {
			float fallDamage = amount / 2;
			if (fallDamage > 80) {
				fallDamage = 80;
			}
			return super.hurt(damageSource, fallDamage);
		}
		if (damageSource.getDirectEntity() instanceof AbstractArrow || damageSource.getDirectEntity() instanceof ThrowableProjectile) {
			return super.hurt(damageSource, amount/2);
		}

		return super.hurt(damageSource, amount);
	}
	public boolean hurtByPart(BrightLandBeastPart brightLandBeastPart, DamageSource damageSource, float f) {
		return this.hurt(damageSource, f);
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == JerotesMobEffects.ABACK.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.WEAKNESS) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.MOVEMENT_SLOWDOWN) {
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
	public void tickDeath() {
		if(deathTime <= 0){
			if (!this.level().isClientSide()) {
				this.setAnimTick(40);
				this.setAnimationState("dead");
			}
		}
		++this.deathTime;
		if (!this.level().isClientSide()) {
			this.setQuakeUseTick(0);
		}
		if (this.deathTime == 32) {
			this.earthQuake();
			if (!this.isSilent()) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.BRIGHT_LAND_BEAST_QUAKE, SoundSource.AMBIENT, 100.0f, 0.8f + this.getRandom().nextFloat() * 0.4f);
			}
		}
		if (this.deathTime >= 40 && !this.level().isClientSide() && !this.isRemoved()) {
			this.remove(RemovalReason.CHANGED_DIMENSION);
		}
	}
}




