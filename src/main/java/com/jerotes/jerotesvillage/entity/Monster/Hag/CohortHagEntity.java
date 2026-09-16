package com.jerotes.jerotesvillage.entity.Monster.Hag;

import com.jerotes.jerotes.entity.Interface.EliteEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.spell.SpellType;
import com.jerotes.jerotes.spell.SpellTypeInterface;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.init.JerotesVillageGameRules;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CohortHagEntity extends BaseHagEntity implements EliteEntity {
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.GREEN, BossEvent.BossBarOverlay.NOTCHED_6, false);

	public CohortHagEntity(EntityType<? extends CohortHagEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 50;
	}

	@Override
	public int PurpleSandSisterhoodLevel() {
		return 4;
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
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.33);
		builder = builder.add(Attributes.MAX_HEALTH, 121);
		builder = builder.add(Attributes.ARMOR, 12);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 12);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		return builder;
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
		//技能列表-隐形通道
		spellList.add(SpellType.JEROTES_INVISIBLE_PASSAGE);
		return spellList;
	}
	@Override
	protected float getSoundVolume() {
		return 2.0f;
	}
	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
		this.bossEvent.setName(this.getDisplayName());
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		this.bossEvent.setId(this.getUUID());
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

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
		if (mobSpawnType != MobSpawnType.CONVERSION) {
			RandomSource randomSource = serverLevelAccessor.getRandom();
			if (randomSource.nextFloat() > 0.99f) {
				this.setMelee(true);
			}
		}
		else {
			this.setCanPickUpLoot(false);
		}
		return spawnGroupData;
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
