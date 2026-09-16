package com.jerotes.jerotesvillage.entity.MagicSummoned.SecondRounderAnimatedArmor;

import com.jerotes.jerotes.entity.MagicSummoned.MagicSummonedEntity;
import com.jerotes.jerotes.goal.JerotesTameMobNonTameRandomTargetGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.item.AnimatedIronArmor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.function.Predicate;

public class FurySecondRounderAnimatedArmorEntity extends SecondRounderAnimatedArmorEntity implements Enemy {
	public static final Predicate<LivingEntity> PREY_SELECTOR = livingEntity -> true;

	public FurySecondRounderAnimatedArmorEntity(EntityType<? extends FurySecondRounderAnimatedArmorEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 20;
		setMaxUpStep(0.6f);
		this.setCanPickUpLoot(true);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(5, new JerotesTameMobNonTameRandomTargetGoal<>(this, Player.class, false, PREY_SELECTOR));
		this.targetSelector.addGoal(5, new JerotesTameMobNonTameRandomTargetGoal<>(this, IronGolem.class, false, PREY_SELECTOR));
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		if (this.isDamageSourceBlocked(damageSource)) {
			return SoundEvents.SHIELD_BLOCK;
		}
		Item armor = this.getItemBySlot(EquipmentSlot.HEAD).getItem();
		if (armor instanceof ArmorItem && !(armor instanceof AnimatedIronArmor)) {
			return ((ArmorItem) armor).getEquipSound();
		}
		return JerotesVillageSoundEvents.FURY_SECOND_ROUNDER_ANIMATED_ARMOR_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		Item armor = this.getItemBySlot(EquipmentSlot.CHEST).getItem();
		if (armor instanceof ArmorItem && !(armor instanceof AnimatedIronArmor)) {
			return ((ArmorItem) armor).getEquipSound();
		}
		return JerotesVillageSoundEvents.FURY_SECOND_ROUNDER_ANIMATED_ARMOR_DEATH;
	}

	protected MagicSummonedEntity getTameEntity() {
		SecondRounderAnimatedArmorEntity animatedArmorEntity = this.convertTo(JerotesVillageEntityType.SECOND_ROUNDER_ANIMATED_ARMOR.get(), true);
		ForgeEventFactory.onLivingConvert(this, animatedArmorEntity);
		return animatedArmorEntity;
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
}

