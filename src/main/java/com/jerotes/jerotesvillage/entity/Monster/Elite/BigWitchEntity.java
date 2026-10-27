package com.jerotes.jerotesvillage.entity.Monster.Elite;

import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.goal.JerotesHelpAlliesGoal;
import com.jerotes.jerotes.goal.JerotesHelpSameFactionGoal;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesPotions;
import com.jerotes.jerotes.spell.SpellList;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.goal.BigWitchAttackGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageGameRules;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillagePotions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BigWitchEntity extends Witch implements PurpleSandSisterhoodEntity, JerotesEntity, FactionEntity, SpellUseEntity, EliteEntity {
	private static final UUID SPEED_MODIFIER_DRINKING_UUID = UUID.fromString("18D74E90-0454-5B4F-047D-58DC8811BC43");
	private static final AttributeModifier SPEED_MODIFIER_DRINKING = new AttributeModifier(SPEED_MODIFIER_DRINKING_UUID, "Drinking speed penalty", 0.20, AttributeModifier.Operation.ADDITION);
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.NOTCHED_6, false);

	public BigWitchEntity(EntityType<? extends BigWitchEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 50;
		this.applyOpenDoorsAbility();
		this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
	}

	@Override
	public int PurpleSandSisterhoodLevel() {
		return 4;
	}

	@Override
	public String getFirstFactionTypeName() {
		return "purple_sand_sisterhood";
	}
	@Override
	public List<String> getFactionTypeUntilTame() {
		List<String> list = new ArrayList<>();
		list.add(getFirstFactionTypeName());
		list.add("radier");
		return list;
	}

	@Override
	public void startSeenByPlayer(ServerPlayer serverPlayer) {
		super.startSeenByPlayer(serverPlayer);
		if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId()))
			this.bossEvent.addPlayer(serverPlayer);
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer serverPlayer) {
		super.stopSeenByPlayer(serverPlayer);
		if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId()))
			this.bossEvent.removePlayer(serverPlayer);
	}

	@Override
	public void customServerAiStep() {
		super.customServerAiStep();
		if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId()))
			this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 70);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.3);
		return builder;
	}

	private void applyOpenDoorsAbility() {
		if (GoalUtils.hasGroundPathNavigation(this)) {
			((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
		}
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
		this.goalSelector.addGoal(0, new OpenDoorGoal(this, true));
		this.goalSelector.removeGoal(new RangedAttackGoal(this, 1.0D, 60, 10.0F));
		this.goalSelector.addGoal(1, new BigWitchAttackGoal(this, 1.0, 30, 10.0f));
		this.targetSelector.addGoal(1, new JerotesHelpSameFactionGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 5, false, false, livingEntity -> EntityFactionFind.isHateFaction(this, livingEntity)));
		this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
	}

	@Override
	public boolean removeWhenFarAway(double d) {
		return false;
	}
	@Override
	protected boolean shouldDespawnInPeaceful() {
		return false;
	}
	@Override
	protected float getSoundVolume() {
		return 2.0f;
	}

	public int spellLevel = 5;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
		this.bossEvent.setName(this.getDisplayName());
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("SpellLevel", this.spellLevel);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.bossEvent.setId(this.getUUID());
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == MobEffects.POISON) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.DEADLY_POISON.get()) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
	}

	@Override
	public void performRangedAttack(LivingEntity livingEntity, float f) {
		if (this.isDrinkingPotion() && this.level().getDifficulty() != Difficulty.HARD) {
			return;
		}
		Vec3 vec3 = livingEntity.getDeltaMovement();
		double d = livingEntity.getX() + vec3.x - this.getX();
		double d2 = livingEntity.getEyeY() - 1.100000023841858 - this.getY();
		double d3 = livingEntity.getZ() + vec3.z - this.getZ();
		double d4 = Math.sqrt(d * d + d3 * d3);
		//大巫婆伤害药水
		Potion potion = JerotesVillagePotions.BIG_WITCH_HURT.get();
		if (livingEntity instanceof Raider raider && raider.getMobType() != MobType.UNDEAD && !(raider.getTarget() == this || (this.getTeam() != null && raider.getTeam() != this.getTeam() && !this.hasActiveRaid()))) {
			potion = livingEntity.getHealth() <= 4.0f ? Potions.STRONG_HEALING : JerotesVillagePotions.BIG_WITCH_HEAL.get();
			this.setTarget(null);
		}
		//缓慢药水
		else if (d4 >= 8.0 && !livingEntity.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)
				&& livingEntity.canBeAffected(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN))) {
			potion = Potions.STRONG_SLOWNESS;
		}
		//腐蚀药水
		else if ((livingEntity.getAttribute(Attributes.ARMOR) != null
				&& livingEntity.getAttributeValue(Attributes.ARMOR) > 10
				|| EntityFactionFind.isMachine(livingEntity)
				|| EntityFactionFind.isConstruct(livingEntity))
				&& livingEntity.canBeAffected(new MobEffectInstance(JerotesMobEffects.CORROSIVE.get()))
				&& !livingEntity.hasEffect(JerotesMobEffects.CORROSIVE.get())) {
			potion = JerotesPotions.STRONG_CORROSIVE.get();
		}
		else if (this.level().getDifficulty() == Difficulty.HARD && this.random.nextFloat() < 0.25f) {
			//巫妖诅咒
			if (livingEntity.getMobType() == MobType.UNDEAD) {
				potion = JerotesVillagePotions.LICH_CURSE.get();
			}
			//凋零安眠
			else {
				potion = JerotesVillagePotions.WITHER_SLEEP.get();
			}
		}
		//海怪崇拜
		else if (this.level().getDifficulty() == Difficulty.HARD
				&& this.random.nextFloat() < 0.5f
				&& (livingEntity.isInWaterOrRain() || livingEntity.isInWaterOrBubble()) && !livingEntity.hasEffect(MobEffects.WATER_BREATHING)
				&& (livingEntity.canBeAffected(new MobEffectInstance(JerotesVillageMobEffects.MALIGNASAUR_ASPHYXIA.get()))
				|| livingEntity.canBeAffected(new MobEffectInstance(JerotesVillageMobEffects.OCEAN_DRAGON_DETERRENCE.get())))) {
			potion = JerotesVillagePotions.SEA_MONSTER_WORSHIP.get();
		}
		//催吐剂
		else if (this.level().getDifficulty() == Difficulty.HARD
				&& this.random.nextFloat() < 0.25f && livingEntity instanceof Player
				&& !livingEntity.hasEffect(MobEffects.CONFUSION)
				&& livingEntity.canBeAffected(new MobEffectInstance(MobEffects.CONFUSION))) {
			potion = JerotesVillagePotions.VOMIT_INDUCER.get();
		}
		//暗黑之心
		else if (this.level().getDifficulty() == Difficulty.HARD
				&& this.random.nextFloat() < 0.25f
				&& !livingEntity.hasEffect(JerotesMobEffects.ABACK.get())
				&& livingEntity.canBeAffected(new MobEffectInstance(JerotesMobEffects.ABACK.get()))) {
			potion = JerotesVillagePotions.DARK_HEART.get();
		}
		//相爱之耻
		else if (this.level().getDifficulty() == Difficulty.HARD
				&& this.random.nextFloat() < 0.25f
				&& livingEntity.getMobType() == MobType.UNDEAD) {
			potion = JerotesVillagePotions.LOVES_SHAME.get();
		}
		//大巫婆伤害药水
		else if (livingEntity.getHealth() >= 8.0f && !livingEntity.hasEffect(MobEffects.POISON) && livingEntity.getMobType() != MobType.UNDEAD) {
			potion = JerotesVillagePotions.BIG_WITCH_HURT.get();
		}
		//虚弱药水
		else if (d4 <= 3.0
				&& !livingEntity.hasEffect(MobEffects.WEAKNESS)
				&& this.random.nextFloat() < 0.25f
				&& livingEntity.canBeAffected(new MobEffectInstance(MobEffects.WEAKNESS))) {
			potion = Potions.LONG_WEAKNESS;
		}
		//治疗药水
		else if (livingEntity.getMobType() == MobType.UNDEAD) {
			potion = Potions.STRONG_HEALING;
		}
		ThrownPotion thrownPotion = new ThrownPotion(this.level(), this);
		if ((double)this.random.nextFloat() < 0.4) {
			thrownPotion.setItem(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), potion));
		}
		else {
			thrownPotion.setItem(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), potion));
		}
		if (livingEntity.distanceTo(this) > 6) {
			thrownPotion.setXRot(thrownPotion.getXRot() - -20.0f);
		}
		thrownPotion.shoot(d, d2 + d4 * 0.2, d3, 0.80f, 2.0f);
		if (!this.isSilent()) {
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.WITCH_THROW, this.getSoundSource(), 1.0f, 0.8f + this.random.nextFloat() * 0.4f);
		}
		this.level().addFreshEntity(thrownPotion);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId())) {
			this.bossEvent.update();
			if (OtherMainConfig.EliteBossBarOnlyCombat) {
				this.bossEvent.setVisible(this.getTarget() != null);
			}
		}
		if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
			this.heal(3.0f);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))){
			this.setTarget(null);
		}
		//
		//摧毁骑乘物
		Main.destroyRides(this);
		if (!this.level().isClientSide && this.isAlive()) {
			if (!this.isDrinkingPotion()) {
				Potion potion = null;
				if (this.random.nextFloat() < 0.15f && this.isEyeInFluid(FluidTags.WATER) && !this.hasEffect(MobEffects.WATER_BREATHING)) {
					potion = Potions.LONG_WATER_BREATHING;
				} else if (this.random.nextFloat() < 0.15f && (this.isOnFire() || this.getLastDamageSource() != null && this.getLastDamageSource().is(DamageTypeTags.IS_FIRE)) && !this.hasEffect(MobEffects.FIRE_RESISTANCE)) {
					potion = Potions.LONG_FIRE_RESISTANCE;
				} else if (this.random.nextFloat() < 0.05f && this.getHealth() < this.getAttributeBaseValue(Attributes.MAX_HEALTH)) {
					potion = JerotesVillagePotions.BIG_WITCH_HEAL.get();
				} else if (this.random.nextFloat() < 0.5f && this.getTarget() != null && !this.hasEffect(MobEffects.MOVEMENT_SPEED) && this.getTarget().distanceToSqr(this) > 121.0) {
					potion = JerotesVillagePotions.BIG_WITCH_HEAL.get();
				} else if (this.random.nextFloat() < 0.5f && !this.hasEffect(MobEffects.REGENERATION)) {
					potion = JerotesVillagePotions.BIG_WITCH_HEAL.get();
				}
				if (potion != null) {
					this.setItemSlot(EquipmentSlot.MAINHAND, PotionUtils.setPotion(new ItemStack(Items.POTION), potion));
					this.setUsingItem(true);
					if (!this.isSilent()) {
						this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.WITCH_DRINK, this.getSoundSource(), 1.0f, 0.8f + this.random.nextFloat() * 0.4f);
					}
					AttributeInstance attributeInstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
                    if (attributeInstance != null) {
                        attributeInstance.removeModifier(SPEED_MODIFIER_DRINKING.getId());
						attributeInstance.addTransientModifier(SPEED_MODIFIER_DRINKING);
                    }
                }
			}
			if (this.random.nextFloat() < 7.5E-4f) {
				this.level().broadcastEntityEvent(this, (byte)15);
			}
		}
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| damageSource.is(DamageTypeTags.IS_FALL)
		)
			return true;
		if (damageSource.getDirectEntity() instanceof ThrownPotion || damageSource.getDirectEntity() instanceof AreaEffectCloud)
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		//魔法抗性
		if (AttackFind.MagicResistance(damageSource)) {
			//技能-魔法吸收
			SpellList.MagicAbsorption(this.getSpellLevel(), this, this).spellUse();
			if (amount <= this.getSpellLevel() * 5 && !damageSource.is(JerotesDamageTypes.MAGIC_EFFECT)) {
				return false;
			}
			amount *= 0.8f;
		}
		return super.hurt(damageSource, amount);
	}

	@Override
	public void tickDeath() {
		++this.deathTime;
		if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
			this.level().broadcastEntityEvent(this, (byte)60);
			this.remove(RemovalReason.KILLED);
		}
	}
}
