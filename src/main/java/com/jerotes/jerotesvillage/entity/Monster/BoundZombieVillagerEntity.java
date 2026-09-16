package com.jerotes.jerotesvillage.entity.Monster;

import com.jerotes.jerotes.entity.Interface.CanBeIllagerFactionEntity;
import com.jerotes.jerotes.entity.Interface.InventoryEntity;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesHelpAlliesGoal;
import com.jerotes.jerotes.goal.JerotesHelpSameFactionGoal;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotesvillage.entity.Humanoid.SecondRounderEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.Team;

import javax.annotation.Nullable;
import java.util.UUID;

public class BoundZombieVillagerEntity extends ZombieVillager implements JerotesEntity, CanBeIllagerFactionEntity, OwnableEntity {
	public BoundZombieVillagerEntity(EntityType<? extends BoundZombieVillagerEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 10;
	}

	@Override
	protected boolean canReplaceCurrentItem(ItemStack newItem, ItemStack oldItem) {
		return InventoryEntity.canReplaceCurrentItem(newItem, oldItem, true, false, false, false, false, false, true, true, true, true);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.26);
		builder = builder.add(Attributes.MAX_HEALTH, 35);
		builder = builder.add(Attributes.ARMOR, 2);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.FOLLOW_RANGE, 35);
		builder = builder.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, -1);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, new JerotesHelpSameFactionGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, 5, false, false, livingEntity -> EntityFactionFind.isHateFaction(this, livingEntity)));
		this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<SecondRounderEntity>(this, SecondRounderEntity.class, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<HumanEntity>(this, HumanEntity.class, false));
	}

	@Override
	public boolean isIllagerFaction() {
		return this.getOwner() != null && (EntityFactionFind.isRaider(this.getOwner()));
	}

	@Override
	public void setIllagerFaction(boolean b) {

	}

	@Override
	public boolean removeWhenFarAway(double d) {
		return false;
	}
	@Override
	protected boolean isSunSensitive() {
		return false;
	}
	@Override
	protected boolean isSunBurnTick() {
		return false;
	}
	@Override
	protected boolean shouldDespawnInPeaceful() {
		return false;
	}

	public double meleeTick;
	@Nullable
	private LivingEntity owner;
	@Nullable
	private UUID ownerUUID;

	@Nullable
	@Override
	public UUID getOwnerUUID() {
		return ownerUUID;
	}
	@Nullable
	public LivingEntity getOwner() {
		Entity entity;
		if (this.owner == null && this.ownerUUID != null && this.level() instanceof ServerLevel && (entity = ((ServerLevel)this.level()).getEntity(this.ownerUUID)) instanceof LivingEntity) {
			this.owner = (LivingEntity)entity;
		}
		return this.owner;
	}
	public void setOwner(@Nullable LivingEntity livingEntity) {
		this.owner = livingEntity;
		this.ownerUUID = livingEntity == null ? null : livingEntity.getUUID();
	}

	@Override
	public boolean isAlliedTo(Entity entity) {
		if (this.getOwner() != null) {
			LivingEntity livingEntity = this.getOwner();
			if (entity == livingEntity) {
				return true;
			}
			if (livingEntity != null) {
				return livingEntity.isAlliedTo(entity);
			}
		}
		return super.isAlliedTo(entity);
	}
	@Override
	//1.20.4↑//
	//public PlayerTeam getTeam() {
	//1.20.1//
	public Team getTeam() {
		LivingEntity livingEntity;
		if (this.getOwner() != null && (livingEntity = this.getOwner()) != null) {
			return livingEntity.getTeam();
		}
		return super.getTeam();
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		if (this.ownerUUID != null) {
			compoundTag.putUUID("Owner", this.ownerUUID);
		}
		compoundTag.putDouble("MeleeTick", this.meleeTick);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (compoundTag.hasUUID("Owner")) {
			this.ownerUUID = compoundTag.getUUID("Owner");
		}
		this.meleeTick = compoundTag.getDouble("MeleeTick");
	}

	public void aiStep() {
		super.aiStep();
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		Item item = itemStack.getItem();
		if (itemStack.is(TagKey.create(Registries.ITEM, new ResourceLocation("forge:tools/pincers"))) ||
				itemStack.is(TagKey.create(Registries.ITEM, new ResourceLocation("forge:tools/shears"))) ||
				itemStack.is(TagKey.create(Registries.ITEM, new ResourceLocation("forge:shears")))) {
			ZombieVillager zombieVillager = this.convertTo(EntityType.ZOMBIE_VILLAGER, true);
			zombieVillager.setVillagerData(this.getVillagerData());
			this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, player.getSoundSource(), 1.0f, 1.0f);
			net.minecraftforge.event.ForgeEventFactory.onLivingConvert(this, zombieVillager);
		}
		return super.mobInteract(player, interactionHand);
	}

	@Override
	public boolean canAttack(LivingEntity livingEntity) {
		if (this.isIllagerFaction() && EntityFactionFind.isRaider(livingEntity) && ((this.getTeam() == null && livingEntity.getTeam() == null) || this.getTeam() == livingEntity.getTeam())) {
			return false;
		}
		if (livingEntity == this.owner)
			return false;
		return super.canAttack(livingEntity);
	}
}