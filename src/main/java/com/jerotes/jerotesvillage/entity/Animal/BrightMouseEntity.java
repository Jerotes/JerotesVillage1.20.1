package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesBreedGoal;
import com.jerotes.jerotes.goal.JerotesFollowParentGoal;
import com.jerotes.jerotesvillage.entity.Humanoid.SecondRounderEntity;
import com.jerotes.jerotesvillage.goal.BrightMouseTemptGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class BrightMouseEntity extends Animal implements JerotesEntity {

	public BrightMouseEntity(EntityType<? extends BrightMouseEntity> type, Level world) {
		super(type, world);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(0, new ClimbOnTopOfPowderSnowGoal(this, this.level()));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.4));
		this.goalSelector.addGoal(2, new JerotesBreedGoal(this, 1.0));
		this.goalSelector.addGoal(2, new BrightMouseTemptGoal(this, 1.1, false));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<Fox>(this, Fox.class, 4.0f, 1.6, 1.6));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<Ocelot>(this, Ocelot.class, 6.0f, 1.6, 1.6));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<Cat>(this, Cat.class, 6.0f, 1.6, 1.6));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<Monster>(this, Monster.class, 6.0f, 1.6, 1.6));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<Player>(this, Player.class, 6.0f, 1.2, 1.2));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<AbstractVillager>(this, AbstractVillager.class, 6.0f, 1.2, 1.2));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<HumanEntity>(this, HumanEntity.class, 6.0f, 1.2, 1.2));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<SecondRounderEntity>(this, SecondRounderEntity.class, 6.0f, 1.2, 1.2));
		this.goalSelector.addGoal(4, new JerotesFollowParentGoal(this, 1.1));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, HumanEntity.class, 6.0f));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
	}
	
	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.4);
		builder = builder.add(Attributes.MAX_HEALTH, 4);
		builder = builder.add(Attributes.FOLLOW_RANGE, 20);
		return builder;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.BRIGHT_MOUSE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.BRIGHT_MOUSE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.BRIGHT_MOUSE_DEATH;
	}

	@Override
	protected float getSoundVolume() {
		return 0.1f;
	}

	@Override
	public boolean isFood(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item.isEdible();
	}

	@Nullable
	@Override
	public BrightMouseEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		return JerotesVillageEntityType.BRIGHT_MOUSE.get().create(serverLevel);
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		float height = 0.4f;
		if (this.isBaby()) {
			return height / 2;
		}
		return height;
	}
}




