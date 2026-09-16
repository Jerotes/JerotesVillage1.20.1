package com.jerotes.jerotesvillage.entity.MagicSummoned.Darisorder;

import com.jerotes.jerotes.entity.Interface.EliteEntity;
import com.jerotes.jerotes.entity.MagicSummoned.MagicSummonedEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Humanoid.SecondRounderEntity;
import com.jerotes.jerotesvillage.goal.DarkNearestAttackableTargetGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageGameRules;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class FuryDarisorderEntity extends DarisorderEntity implements Enemy, EliteEntity {
	public static final Predicate<LivingEntity> PREY_SELECTOR = livingEntity -> true;
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.WHITE, BossEvent.BossBarOverlay.NOTCHED_12, false);

	public FuryDarisorderEntity(EntityType<? extends FuryDarisorderEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 100;
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

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(2, new DarkNearestAttackableTargetGoal<Player>(this, Player.class, true));
		this.targetSelector.addGoal(2, new DarkNearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, true));
		this.targetSelector.addGoal(2, new DarkNearestAttackableTargetGoal<AbstractIllager>(this, AbstractIllager.class, true));
		this.targetSelector.addGoal(2, new DarkNearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, true));
		this.targetSelector.addGoal(2, new DarkNearestAttackableTargetGoal<SecondRounderEntity>(this, SecondRounderEntity.class, false));
		this.targetSelector.addGoal(2, new DarkNearestAttackableTargetGoal<HumanEntity>(this, HumanEntity.class, false));
	}


	protected MagicSummonedEntity getTameEntity() {
		DarisorderEntity darisorderEntity = this.convertTo(JerotesVillageEntityType.DARISORDER.get(), true);
		ForgeEventFactory.onLivingConvert(this, darisorderEntity);
		return darisorderEntity;
	}
	@Override
	public SoundSource getSoundSource() {
		return SoundSource.HOSTILE;
	}
	@Override
	public boolean shouldDropExperience() {
		return true;
	}
	@Override
	protected boolean shouldDropLoot() {
		return true;
	}
	@Override
	public boolean removeWhenFarAway(double d) {
		return !this.isTame();
	}
	@Override
	protected boolean shouldDespawnInPeaceful() {
		return !this.isTame();
	}

	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
		this.bossEvent.setName(this.getDisplayName());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		this.bossEvent.setId(this.getUUID());
	}

	public void aiStep() {
		super.aiStep();
		if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId())) {
			this.bossEvent.update();
			if (OtherMainConfig.EliteBossBarOnlyCombat) {
				this.bossEvent.setVisible(this.getTarget() != null);
			}
		}
	}
}

