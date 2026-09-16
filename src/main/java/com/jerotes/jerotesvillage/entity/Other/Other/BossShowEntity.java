package com.jerotes.jerotesvillage.entity.Other.Other;

import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotesvillage.block.DamagedRuins.MerorProjectionTableEntity;
import com.jerotes.jerotesvillage.item.ItemBossDrop;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class BossShowEntity extends LivingEntity implements JerotesEntity, FactionEntity {
	private static final EntityDataAccessor<Integer> BLOCK_X = SynchedEntityData.defineId(BossShowEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BLOCK_Y = SynchedEntityData.defineId(BossShowEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BLOCK_Z = SynchedEntityData.defineId(BossShowEntity.class, EntityDataSerializers.INT);
	private final NonNullList<ItemStack> handItems = NonNullList.withSize(2, ItemStack.EMPTY);
	private final NonNullList<ItemStack> armorItems = NonNullList.withSize(4, ItemStack.EMPTY);
	public BossShowEntity(EntityType<? extends BossShowEntity> entityType, Level level) {
		super(entityType, level);
		this.setMaxUpStep(0.0f);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.tickCount % 40 == 0) {
			BlockPos base = new BlockPos(getBlockAboutX(), getBlockAboutY(), getBlockAboutZ());
			if (!(this.level().getBlockEntity(base) instanceof MerorProjectionTableEntity merorProjectionTable)) {
				this.discard();
			}
			else if (!(merorProjectionTable.getItem(0).getItem() instanceof ItemBossDrop itemBossDrop)) {
				this.discard();
			}
			else if (itemBossDrop.getBossEntityType() != this.getType()) {
				this.discard();
			}
		}
	}

	@Override
	public InteractionResult interactAt(Player player, Vec3 vec3, InteractionHand interactionHand) {
		BlockPos base = new BlockPos(getBlockAboutX(), getBlockAboutY(), getBlockAboutZ());
		if ((this.level().getBlockEntity(base) instanceof MerorProjectionTableEntity merorProjectionTable) && (merorProjectionTable.getItem(0).getItem() instanceof ItemBossDrop itemBossDrop)) {
			SoundEvent soundEvent = itemBossDrop.getBossSoundEvent();
			this.playSound(soundEvent, 2.0f, 1.0f);
			return InteractionResult.SUCCESS;
		}
		return super.interactAt(player, vec3, interactionHand);
	}


	@Override
	public HumanoidArm getMainArm() {
		return HumanoidArm.RIGHT;
	}

	@Override
	public Iterable<ItemStack> getHandSlots() {
		return this.handItems;
	}

	@Override
	public Iterable<ItemStack> getArmorSlots() {
		return this.armorItems;
	}

	@Override
	public ItemStack getItemBySlot(EquipmentSlot equipmentSlot) {
		switch (equipmentSlot.getType()) {
			case HAND: {
				return this.handItems.get(equipmentSlot.getIndex());
			}
			case ARMOR: {
				return this.armorItems.get(equipmentSlot.getIndex());
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {
		this.verifyEquippedItem(itemStack);
		switch (equipmentSlot.getType()) {
			case HAND: {
				this.onEquipItem(equipmentSlot, this.handItems.set(equipmentSlot.getIndex(), itemStack), itemStack);
				break;
			}
			case ARMOR: {
				this.onEquipItem(equipmentSlot, this.armorItems.set(equipmentSlot.getIndex(), itemStack), itemStack);
			}
		}
	}

	private int start;
	public float facing;

	public void setBlockAboutX(int n){
		this.getEntityData().set(BLOCK_X, n);
	}
	public int getBlockAboutX() {return this.getEntityData().get(BLOCK_X);}
	public void setBlockAboutY(int n){
		this.getEntityData().set(BLOCK_Y, n);
	}
	public int getBlockAboutY() {
		return this.getEntityData().get(BLOCK_Y);
	}
	public void setBlockAboutZ(int n){
		this.getEntityData().set(BLOCK_Z, n);
	}
	public int getBlockAboutZ() {
		return this.getEntityData().get(BLOCK_Z);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("Start", this.start);
		compoundTag.putInt("BlockAboutX", this.getBlockAboutX());
		compoundTag.putInt("BlockAboutY", this.getBlockAboutY());
		compoundTag.putInt("BlockAboutZ", this.getBlockAboutZ());
		compoundTag.putFloat("Facing", this.facing);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.start = compoundTag.getInt("Start");
		this.setBlockAboutX(compoundTag.getInt("BlockAboutX"));
		this.setBlockAboutY(compoundTag.getInt("BlockAboutY"));
		this.setBlockAboutZ(compoundTag.getInt("BlockAboutZ"));
		this.facing = compoundTag.getFloat("Facing");
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(BLOCK_X, 0);
		this.getEntityData().define(BLOCK_Y, 0);
		this.getEntityData().define(BLOCK_Z, 0);
	}
	@Override
	public boolean isPushable() {
		return false;
	}
	@Override
	protected void doPush(Entity entity) {
	}

	@Override
	protected void pushEntities() {
	}

	@Override
	public boolean hurt(DamageSource damageSource, float f) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, f);
		}
		return false;
	}

	@Override
	public boolean isBaby() {
		return false;
	}

	@Override
	public void setDeltaMovement(double d, double d2, double d3) {
		super.setDeltaMovement(0, 0, 0);
	}
	@Override
	public boolean canFreeze() {
		return false;
	}
	@Override
	public boolean isFreezing() {
		return false;
	}

	@Override
	public boolean isOnFire() {
		return false;
	}


	@Override
	public void setYBodyRot(float f) {
		this.yBodyRotO = this.yRotO = f;
		this.yHeadRotO = this.yHeadRot = f;
	}

	@Override
	public void setYHeadRot(float f) {
		this.yBodyRotO = this.yRotO = f;
		this.yHeadRotO = this.yHeadRot = f;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.clearFire();
		//清除冻结
		if (this.getTicksFrozen() > 0) {
			this.setTicksFrozen(0);
		}
	}

	@Override
	public void kill() {
		this.remove(Entity.RemovalReason.KILLED);
		this.gameEvent(GameEvent.ENTITY_DIE);
	}

	@Override
	public boolean canBeSeenByAnyone() {
		return true;
	}
	@Override
	public boolean canBeSeenAsEnemy() {
		return false;
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0);
		builder = builder.add(Attributes.MAX_HEALTH, 10);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
		return builder;
	}
}