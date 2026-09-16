package com.jerotes.jerotesvillage.entity.Humanoid.Meror;

import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class MerorBountyHunterEntity extends MerorEntity {

	public MerorBountyHunterEntity(EntityType type, Level world) {
		super(type, world);
		xpReward = 20;
	}

	@Override
	public boolean canHoldItem(ItemStack itemStack) {
		return itemStack.getItem() == JerotesVillageItems.MEROR_TOKEN_M.get()
				|| itemStack.getItem() == JerotesVillageItems.MEROR_TOKEN_E.get()
				|| itemStack.getItem() == JerotesVillageItems.MEROR_TOKEN_R.get();
	}

	@Override
	protected boolean canReplaceCurrentItem(ItemStack itemStack, ItemStack itemStack2) {
		return false;
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
		this.maybeWearArmor(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.MEROR_BOUNTY_HUNTER_HELMET.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.MEROR_BOUNTY_HUNTER_CHESTPLATE.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.MEROR_BOUNTY_HUNTER_LEGGINGS.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.MEROR_BOUNTY_HUNTER_BOOTS.get()), randomSource);
	}

	@Override
	protected void pickUpItem(ItemEntity itemEntity) {
		InventoryCarrier.pickUpItem(this, this, itemEntity);
	}

	private void maybeWearArmor(EquipmentSlot equipmentSlot, ItemStack itemStack, RandomSource randomSource) {
		this.setItemSlot(equipmentSlot, itemStack);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.37);
		builder = builder.add(Attributes.MAX_HEALTH, 40);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 4);
		builder = builder.add(Attributes.FOLLOW_RANGE, 128);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0);
		return builder;
	}

	private int hasMerorSword = 1;
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("HasMerorSword", this.hasMerorSword);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.hasMerorSword = compoundTag.getInt("HasMerorSword");
	}

	@Override
	public void aiStep() {
		super.aiStep();

		if (this.getTarget() != null && hasMerorSword == 1) {
			hasMerorSword = 0;
			if (!this.level().isClientSide) {
				this.addEffect(new MobEffectInstance(JerotesVillageMobEffects.MEROR_STIMULIN.get(), 600, 0));
			}
		}

		if (this.getTarget() == null && hasMerorSword == 0) {
			hasMerorSword = 1;
		}

		if (this.getTarget() instanceof Player player && player.hasEffect(JerotesVillageMobEffects.MEROR_WANTED_ORDER.get()) && !player.isAlive()) {
			this.transport(this);
		}
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (AttackFind.isWaterDamage(damageSource) && !this.hasEffect(MobEffects.WATER_BREATHING)) {
			if (!this.level().isClientSide) {
				this.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 12000, 0));
			}
		}
		if (AttackFind.isFireDamage(damageSource) && !this.hasEffect(MobEffects.FIRE_RESISTANCE)) {
			if (!this.level().isClientSide) {
				this.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 12000, 0));
			}
		}
		return super.hurt(damageSource, amount);
	}

	@Override
	public void awardKillScore(Entity entity, int score, DamageSource damageSource) {
		if (entity instanceof Player player && player.hasEffect(JerotesVillageMobEffects.MEROR_WANTED_ORDER.get())) {
			this.transport(this);
		}
		super.awardKillScore(entity, score, damageSource);
	}

	@Override
	public ItemStack createSpawnWeapon(float weaponRandom) {
		if (weaponRandom > 0.75) {
			return new ItemStack(JerotesVillageItems.MEROR_LONG_KNIFE.get());
		}
		if (weaponRandom > 0.35) {
			return new ItemStack(JerotesVillageItems.MEROR_IMPACT_PISTOL.get());
		}
		return new ItemStack(JerotesVillageItems.MEROR_DAGGER.get());
	}

	@Override
	public ItemStack createSpawnOffhand(float offhandRandom, float weaponRandom) {
		if (weaponRandom > 0.35) {
			return new ItemStack(Items.AIR);
		}
		return new ItemStack(JerotesVillageItems.MEROR_DAGGER.get());
	}
}
