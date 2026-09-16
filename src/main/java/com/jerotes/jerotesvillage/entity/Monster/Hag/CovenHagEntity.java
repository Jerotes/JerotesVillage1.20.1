package com.jerotes.jerotesvillage.entity.Monster.Hag;

import com.jerotes.jerotes.entity.Interface.EliteEntity;
import com.jerotes.jerotes.entity.Interface.LoreUndyingEntity;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.spell.SpellType;
import com.jerotes.jerotes.spell.SpellTypeInterface;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotesvillage.goal.FollowPurpleSandHagGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageGameRules;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CovenHagEntity extends BaseHagEntity implements EliteEntity, LoreUndyingEntity {
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.NOTCHED_6, false);
	public CovenHagEntity(EntityType<? extends CovenHagEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 50;
	}

	@Override
	public int PurpleSandSisterhoodLevel() {
		return 5;
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
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
		builder = builder.add(Attributes.MAX_HEALTH, 121);
		builder = builder.add(Attributes.ARMOR, 12);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 12);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(3, new FollowPurpleSandHagGoal(this, 1.2f));
	}

	@Override
	public List<SpellTypeInterface> SelfMainSpellList() {
		List<SpellTypeInterface> spellList = new ArrayList<>();
		if (this.isCovens()) {
			if (this.getTarget() != null && EntityFactionFind.isHumanoid(this.getTarget()) && this.distanceTo(this.getTarget()) <= 12) {
				//技能列表-人类定身术
				spellList.add(SpellType.JEROTES_HOLD_PERSON);
			}
			else {
				//技能列表-致病射线
				spellList.add(SpellType.JEROTES_RAY_OF_SICKNESS);
			}
			//技能列表-魅影杀手
			spellList.add(SpellType.JEROTES_PHANTASMAL_KILLER);
			//技能列表-摄心目光
			spellList.add(SpellType.JEROTES_EYEBITE);
			//技能列表-闪电束
			spellList.add(SpellType.JEROTES_LIGHTNING_BOLT);
		}
		else {
			//技能列表-恶毒嘲笑
			spellList.add(SpellType.JEROTES_VICIOUS_MOCKERY);
			//技能列表-致病射线
			spellList.add(SpellType.JEROTES_RAY_OF_SICKNESS);
			//技能列表-降咒
			spellList.add(SpellType.JEROTES_BESTOW_CURSE);
			//技能列表-致病射线
			spellList.add(SpellType.JEROTES_RAY_OF_SICKNESS);
		}
		//技能列表-恶毒嘲笑
		spellList.add(SpellType.JEROTES_VICIOUS_MOCKERY);
		//技能列表-致病射线
		spellList.add(SpellType.JEROTES_RAY_OF_SICKNESS);
		//技能列表-降咒
		spellList.add(SpellType.JEROTES_BESTOW_CURSE);
		//技能列表-疗伤术
		spellList.add(SpellType.JEROTES_CURE_WOUNDS);
		return spellList;
	}
	@Override
	public List<SpellTypeInterface> SelfAddSpellList() {
		List<SpellTypeInterface> spellList = new ArrayList<>();
		if (this.isCovens() && this.getHealth() <= getMaxHealth() && this.getTarget() != null && this.distanceTo(this.getTarget()) > 6) {
			//技能列表-法术反制
			spellList.add(SpellType.JEROTES_COUNTERSPELL);
		}
		else {
			//技能列表-隐形通道
			spellList.add(SpellType.JEROTES_INVISIBLE_PASSAGE);
		}
		//技能列表-镜影术
		spellList.add(SpellType.JEROTES_MIRROR_IMAGE);
		//技能列表-隐形通道
		spellList.add(SpellType.JEROTES_INVISIBLE_PASSAGE);
		return spellList;
	}
	@Override
	protected float getSoundVolume() {
		return 2.0f;
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
	}

	public int spellLevel = 3;
	public int covenSpellLevel = 4;
	@Override
	public int getSpellLevel() {
		if (this.covenSpellLevel > spellLevel && this.isCoven()) {
			return covenSpellLevel;
		}
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
		compoundTag.putInt("CovenSpellLevel", this.covenSpellLevel);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.covenSpellLevel = compoundTag.getInt("CovenSpellLevel");
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
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.IN_FIRE)
				|| damageSource.is(DamageTypes.ON_FIRE)
				|| AttackFind.isWaterDamage(damageSource))
			return true;
		if (damageSource.getDirectEntity() instanceof ThrownPotion || damageSource.getDirectEntity() instanceof AreaEffectCloud)
			return true;
		return super.isInvulnerableTo(damageSource);
	}

	@Override
	protected float getDamageAfterMagicAbsorb(@NotNull DamageSource damageSource, float f) {
		f = super.getDamageAfterMagicAbsorb(damageSource, f);
		if (damageSource.getEntity() == this) {
			f = 0.0f;
		}
		if (damageSource.is(DamageTypeTags.WITCH_RESISTANT_TO)) {
			f *= 0.15f;
		}
		return f;
	}

	@Override
	public void tickDeath() {
		if(deathTime <= 0){
			if (!this.level().isClientSide()) {
				this.setAnimTick(20);
				this.setAnimationState("dead");
			}
		}
		++this.deathTime;
		if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
			this.level().broadcastEntityEvent(this, (byte)60);
			this.remove(RemovalReason.KILLED);
		}
	}
}

