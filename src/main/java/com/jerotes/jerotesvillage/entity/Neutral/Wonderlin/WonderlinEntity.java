package com.jerotes.jerotesvillage.entity.Neutral.Wonderlin;

import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotesvillage.entity.Humanoid.ChibaoEntity;
import com.jerotes.jerotesvillage.goal.WonderlinKickAttackGoal;
import com.jerotes.jerotesvillage.goal.WonderlinKickTargetGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class WonderlinEntity extends BaseWonderlinEntity {
	public WonderlinEntity(EntityType<? extends WonderlinEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 5;
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.32);
		builder = builder.add(Attributes.MAX_HEALTH, 14);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 2);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new JerotesShiftKeyDownGoal(this));
		this.goalSelector.addGoal(1, new JerotesMainSpellAttackGoal(this, this.getSpellLevel(), 60, 240, 0.5f));
		this.goalSelector.addGoal(1, new JerotesAddSpellAttackGoal(this, this.getSpellLevel(), 180, 240, 0.5f));
		this.goalSelector.addGoal(1, new JerotesCombatIMagicAttackGoal<>(this, 0.5, 15.0f, this.meleeOrRangeDistance()));
		this.goalSelector.addGoal(1, new JerotesCombatIIMagicAttackGoal<>(this, 0.2, true, this.meleeOrRangeDistance()));
		this.goalSelector.addGoal(1, new JerotesCombatIIIMagicAttackGoal<>(this, 0.5, 15.0f, this.meleeOrRangeDistance()));
		this.goalSelector.addGoal(1, new JerotesCombatIVMagicAttackGoal<>(this, 0.5, 15.0f, this.meleeOrRangeDistance()));
		this.goalSelector.addGoal(1, new JerotesRangedBowAttackGoal<WonderlinEntity>(this, 0.5, 20, 15.0f));
		this.goalSelector.addGoal(1, new JerotesRangedCrossbowAttackGoal<WonderlinEntity>(this, 1.0, 15.0f));
		this.goalSelector.addGoal(1, new JerotesRangedThrowAttackGoal<>(this, 0.4, 40, 12.0F));
		this.goalSelector.addGoal(1, new JerotesRangedJavelinAttackGoal<>(this, 1.0, 60, 12.0F, this.meleeOrRangeDistance()));
		this.goalSelector.addGoal(1, new JerotesSpearUseGoal<>(this,  1.0, 1.0, 10.0f, 2.0f));
		this.goalSelector.addGoal(1, new JerotesPikeUseGoal(this,  1.2, true));


		this.goalSelector.addGoal(2, new WonderlinKickAttackGoal(WonderlinEntity.this));
		this.goalSelector.addGoal(2, new WonderlinMeleeAttackGoal(WonderlinEntity.this));
		this.goalSelector.addGoal(2, new WonderlinAvoidGoal(this));
		this.targetSelector.addGoal(3, new WonderlinKickTargetGoal<LivingEntity>(this, LivingEntity.class, false));
	}

	public boolean canRush() {
		return true;
	}
}

