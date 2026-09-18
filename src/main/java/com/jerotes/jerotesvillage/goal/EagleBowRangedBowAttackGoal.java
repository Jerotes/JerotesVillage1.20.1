package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotes.entity.Interface.InventoryEntity;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Boss.EagleBowEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;

import java.util.EnumSet;

public class EagleBowRangedBowAttackGoal<T extends EagleBowEntity> extends Goal {
	private final T mob;
	private final double speedModifier;
	private int attackIntervalMin;
	private final float attackRadiusSqr;
	private int attackTime = -1;
	private int meleeAttackTime;
	private int seeTime;
	private boolean strafingClockwise;
	private boolean strafingBackwards;
	private int strafingTime = -1;

	public EagleBowRangedBowAttackGoal(T t, double d, int n, float f) {
		this.mob = t;
		this.speedModifier = d;
		this.attackIntervalMin = n;
		this.attackRadiusSqr = f * f;
		this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
	}

	public void setMinAttackInterval(int p_25798_) {
		this.attackIntervalMin = p_25798_;
	}

	public boolean canUse() {
		return this.mob.getTarget() != null && isHoldingBow(this.mob);
	}

	public static boolean isHoldingBow(Mob mob) {
		Item main = mob.getMainHandItem().getItem();
		Item off = mob.getOffhandItem().getItem();
		float reach = 8.0F;
		if (mob instanceof InventoryEntity inventoryEntity) {
			reach = inventoryEntity.meleeOrRangeDistance();
		}
		if (InventoryEntity.isMeleeWeapon(mob.getMainHandItem()) && mob.getTarget() != null && mob.distanceTo(mob.getTarget()) <= reach) {
			return false;
		} else {
			return main instanceof BowItem || off instanceof BowItem && (mob.getMainHandItem().isEmpty() || InventoryEntity.isMeleeWeapon(mob.getMainHandItem()));
		}
	}

	public boolean canContinueToUse() {
		return (this.canUse() || !this.mob.getNavigation().isDone()) && isHoldingBow(this.mob);
	}

	public void start() {
		super.start();
		this.mob.setAggressive(true);
		this.meleeAttackTime = 0;
	}

	public void stop() {
		label14: {
			super.stop();
			LivingEntity livingEntity = this.mob.getTarget();
			if (livingEntity != null) {
				Mob var3 = this.mob;
				if (var3 instanceof InventoryEntity) {
					InventoryEntity inventoryEntity = (InventoryEntity)var3;
					if (inventoryEntity.isCanChangeMeleeOrRange()) {
						this.mob.getNavigation().stop();
						break label14;
					}
				}
			}

			this.mob.setTarget((LivingEntity)null);
			this.mob.setAggressive(false);
		}

		this.seeTime = 0;
		this.attackTime = -1;
		this.mob.stopUsingItem();
	}

	public boolean requiresUpdateEveryTick() {
		return true;
	}

	public void tick() {
		LivingEntity livingentity = this.mob.getTarget();
		if (livingentity != null) {
			if (!(this.mob.getUseItem().getItem() instanceof BowItem)) {
				this.mob.stopUsingItem();
			}

			double d0 = this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
			boolean flag = this.mob.getSensing().hasLineOfSight(livingentity);
			boolean flag1 = this.seeTime > 0;
			if (flag != flag1) {
				this.seeTime = 0;
			}

			if (flag) {
				++this.seeTime;
			} else {
				--this.seeTime;
			}

			if (!(d0 > (double)this.attackRadiusSqr) && this.seeTime >= 20) {
				this.mob.getNavigation().stop();
				++this.strafingTime;

				if (flag && this.mob.getRandom().nextFloat() < 0.3f) {
					BlockPos coverPos = findSimpleCover(livingentity);
					if (coverPos != null) {
						this.mob.getNavigation().moveTo(coverPos.getX(), coverPos.getY(), coverPos.getZ(), this.speedModifier);
						this.strafingTime = -1;
					}
				}
			} else {
				if (!flag && this.mob.getRandom().nextFloat() < 0.7f) {
					BlockPos coverPos = findSimpleCover(livingentity);
					if (coverPos != null) {
						this.mob.getNavigation().moveTo(coverPos.getX(), coverPos.getY(), coverPos.getZ(), this.speedModifier);
					} else {
						this.mob.getNavigation().moveTo(livingentity, this.speedModifier);
					}
				} else {
					this.mob.getNavigation().moveTo(livingentity, this.speedModifier);
				}
				this.strafingTime = -1;
			}

			if (!(d0 > (double)this.attackRadiusSqr) && this.seeTime >= 20) {
				this.mob.getNavigation().stop();
				++this.strafingTime;
			} else {
				this.mob.getNavigation().moveTo(livingentity, this.speedModifier);
				this.strafingTime = -1;
			}

			if (this.strafingTime >= 20) {
				if ((double)this.mob.getRandom().nextFloat() < 0.3) {
					this.strafingClockwise = !this.strafingClockwise;
				}

				if ((double)this.mob.getRandom().nextFloat() < 0.3) {
					this.strafingBackwards = !this.strafingBackwards;
				}

				this.strafingTime = 0;
			}

			if (this.strafingTime > -1) {
				if (d0 > (double)(this.attackRadiusSqr * 0.75F)) {
					this.strafingBackwards = false;
				} else if (d0 < (double)(this.attackRadiusSqr * 0.25F)) {
					this.strafingBackwards = true;
				}

				this.mob.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
				Entity entity = this.mob.getControlledVehicle();
				if (entity instanceof Mob mob) {
					mob.getLookControl().setLookAt(livingentity, 360f, 360f);
				}
			} else {
				this.mob.getLookControl().setLookAt(livingentity, 360.0F, 360.0F);
			}

			if (this.mob.isUsingItem() && this.mob.getUseItem().getItem() instanceof BowItem) {
				float bodyYaw = (float)(Mth.atan2(livingentity.getZ() - this.mob.getZ(), livingentity.getX() - this.mob.getX()) * (180.0D / Math.PI)) - 90.0F;
				this.mob.setYRot(bodyYaw);
				this.mob.setYBodyRot(bodyYaw);
				this.mob.setYHeadRot(bodyYaw);
			}

			if (this.mob.isUsingItem()) {
				if (!flag && this.seeTime < -60) {
					this.mob.stopUsingItem();
				} else if (flag) {
					int i = this.mob.getTicksUsingItem();
					if (i >= 20) {
						this.mob.stopUsingItem();
						((RangedAttackMob)this.mob).performRangedAttack(livingentity, BowItem.getPowerForTime(i));
						int time = Math.max(15, (int) this.mob.distanceTo(livingentity));
						this.attackTime = Math.min(time, this.attackIntervalMin);
					}
				}
			}

			if ((!this.mob.isUsingItem() || !(this.mob.getUseItem().getItem() instanceof BowItem)) && --this.attackTime <= 0 && this.seeTime >= -60 && !this.mob.specialAction() && this.mob.getAttackTick() <= 0) {
				if (!(this.mob.getUseItem().getItem() instanceof BowItem)) {
					this.mob.stopUsingItem();
				}
				 this.mob.startUsingItem(ProjectileUtil.getWeaponHoldingHand(this.mob, (item) -> {
					return item instanceof BowItem;
				}));
				if (!this.mob.level().isClientSide()) {
					this.mob.setShootTick(30);
					this.mob.setAnimTick(30);
					this.mob.level().broadcastEntityEvent(this.mob, (byte) 101);
				}
				if ((this.mob.onGround() || Main.isInFluid(this.mob))) {
					if (this.mob.distanceToSqr(livingentity) < 12) {
						float f1 = this.mob.getYRot();
						float f2 = this.mob.getXRot();
						float f3 = -Mth.sin(f1 * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
						float f4 = -Mth.sin(f2 * 0.017453292f);
						float f5 = Mth.cos(f1 * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
						float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
						float f7 = -0.55f * this.mob.getRandom().nextFloat();
						this.mob.setOnGround(false);
						this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(f3 *= f7 / f6 * 2, 0.25, f5 *= f7 / f6 * 2));
					}
				}
			}

			if (!this.mob.isUsingItem() && --this.meleeAttackTime <= 0
					&& (this.mob.isWithinMeleeAttackRange(livingentity) || this.mob.distanceToSqr(livingentity) <= 9.0D)
					&& this.mob.getSensing().hasLineOfSight(livingentity)) {
				this.meleeAttackTime = 20;
				this.mob.swing(InteractionHand.MAIN_HAND);
				this.mob.doHurtTarget(livingentity);
			}
		}

	}

	private BlockPos findSimpleCover(LivingEntity target) {
		if (target == null) return null;

		BlockPos mobPos = this.mob.blockPosition();
		BlockPos targetPos = target.blockPosition();

		for (int i = 0; i < 8; i++) {
			double angle = i * Math.PI * 0.25;
			int x = mobPos.getX() + (int)(3 * Math.cos(angle));
			int z = mobPos.getZ() + (int)(3 * Math.sin(angle));
			BlockPos checkPos = new BlockPos(x, mobPos.getY(), z);

			if (this.mob.level().getBlockState(checkPos).isSolid() &&
					this.mob.level().isEmptyBlock(checkPos.above()) &&
					this.mob.getNavigation().createPath(checkPos, 1) != null) {
				return checkPos;
			}
		}
		return null;
	}
}