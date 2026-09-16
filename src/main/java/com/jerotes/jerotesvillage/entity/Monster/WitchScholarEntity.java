package com.jerotes.jerotesvillage.entity.Monster;

import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Interface.PurpleSandSisterhoodEntity;
import com.jerotes.jerotes.goal.JerotesHelpAlliesGoal;
import com.jerotes.jerotes.goal.JerotesHelpSameFactionGoal;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
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

import java.util.ArrayList;
import java.util.List;

public class WitchScholarEntity extends Witch implements PurpleSandSisterhoodEntity, JerotesEntity, FactionEntity {
	public WitchScholarEntity(EntityType<? extends WitchScholarEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 15;
		this.applyOpenDoorsAbility();
		this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
	}

	@Override
	public int PurpleSandSisterhoodLevel() {
		return 3;
	}

	private void applyOpenDoorsAbility() {
		if (GoalUtils.hasGroundPathNavigation(this)) {
			((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
		}
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
	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 30);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
		this.goalSelector.addGoal(0, new OpenDoorGoal(this, true));
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
	public void performRangedAttack(LivingEntity livingEntity, float f) {
		if (this.isDrinkingPotion()) {
			return;
		}
		Vec3 vec3 = livingEntity.getDeltaMovement();
		double d = livingEntity.getX() + vec3.x - this.getX();
		double d2 = livingEntity.getEyeY() - 1.100000023841858 - this.getY();
		double d3 = livingEntity.getZ() + vec3.z - this.getZ();
		double d4 = Math.sqrt(d * d + d3 * d3);
		Potion potion = Potions.HARMING;
		if (livingEntity instanceof Raider raider && !(raider.getTarget() == this || (this.getTeam() != null && raider.getTeam() != this.getTeam() && !this.hasActiveRaid()))) {
			potion = livingEntity.getHealth() <= 4.0f ? Potions.HEALING : Potions.REGENERATION;
			this.setTarget(null);
		} else if (d4 >= 8.0 && !livingEntity.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
			potion = Potions.SLOWNESS;
		} else if (livingEntity.getHealth() >= 8.0f && !livingEntity.hasEffect(MobEffects.POISON)) {
			potion = Potions.POISON;
		} else if (d4 <= 3.0 && !livingEntity.hasEffect(MobEffects.WEAKNESS) && this.random.nextFloat() < 0.25f) {
			potion = Potions.WEAKNESS;
		}
		ThrownPotion thrownPotion = new ThrownPotion(this.level(), this);
		thrownPotion.setItem(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), potion));
		thrownPotion.setXRot(thrownPotion.getXRot() - -20.0f);
		thrownPotion.shoot(d, d2 + d4 * 0.2, d3, 0.75f, 8.0f);
		if (!this.isSilent()) {
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.WITCH_THROW, this.getSoundSource(), 1.0f, 0.8f + this.random.nextFloat() * 0.4f);
		}
		this.level().addFreshEntity(thrownPotion);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
			this.heal(3.0f);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//
		//摧毁骑乘物
		Main.destroyRides(this);
	}
}
