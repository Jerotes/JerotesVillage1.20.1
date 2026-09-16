package com.jerotes.jerotesvillage.entity.Monster.Ender;

import com.jerotes.jerotes.control.GiantBodyRotationControl;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.goal.BloodsuckerMeleeAttackGoal;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EnderStowawayEntity extends EnderMan implements JerotesEntity, FactionEntity {
	public EnderStowawayEntity(EntityType<? extends EnderStowawayEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 20;
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.32);
		builder = builder.add(Attributes.MAX_HEALTH, 45);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 7);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		return builder;
	}

	@Override
	public String getFirstFactionTypeName() {
		return "ender_stowaway_collective";
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new BloodsuckerMeleeAttackGoal(this, 1.2, true) {
			@Override
			public boolean canUse() {
				String string = ChatFormatting.stripFormatting(EnderStowawayEntity.this.getName().getString());
				if (!"Elite".equals(string)) {
					return false;
				}
				return super.canUse();
			}  @Override
			public boolean canContinueToUse() {
				String string = ChatFormatting.stripFormatting(EnderStowawayEntity.this.getName().getString());
				if (!"Elite".equals(string)) {
					return false;
				}
				return super.canContinueToUse();
			}
		});
	}

	private int hurtsTick;
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("HurtsTick", this.hurtsTick);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.hurtsTick = compoundTag.getInt("HurtsTick");
	}

	@Override
	public AABB getAttackBoundingBox() {
		Entity entity = this.getVehicle();
		AABB aabb;
		if (entity != null) {
			AABB aabb1 = entity.getBoundingBox();
			AABB aabb2 = this.getBoundingBox();
			aabb = new AABB(Math.min(aabb2.minX, aabb1.minX), aabb2.minY, Math.min(aabb2.minZ, aabb1.minZ), Math.max(aabb2.maxX, aabb1.maxX), aabb2.maxY, Math.max(aabb2.maxZ, aabb1.maxZ));
		} else {
			aabb = this.getBoundingBox();
		}
		AABB aabb1 = aabb.inflate(Math.sqrt((double)2.04F) - (double)0.6F, 0.0D, Math.sqrt((double)2.04F) - (double)0.6F);
		return aabb1.inflate(0.25d, 0.25d, 0.25d);
	}
	@Override
	protected @NotNull BodyRotationControl createBodyControl() {
		return new GiantBodyRotationControl(this);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}

	public void aiStep() {
		super.aiStep();
		String string = ChatFormatting.stripFormatting(this.getName().getString());
		if ("Elite".equals(string)) {
			if (this.hurtsTick > 0) {
				this.hurtsTick -= 10;
			}
		}
		if (this.hurtsTick > 0) {
			--this.hurtsTick;
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//
	}
	@Override
	public boolean doHurtTarget(Entity entity) {
		boolean bl = super.doHurtTarget(entity);
		String string = ChatFormatting.stripFormatting(this.getName().getString());
		if ("Elite".equals(string)) {
			if (this.getRandom().nextFloat() > 0.15f){
				this.teleport();
			}
		}
		else {
			if (this.getRandom().nextFloat() > 0.7f) {
				this.teleport();
			}
		}
		return bl;
	}

	@Override
	public boolean hurt(DamageSource damageSource, float f) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, f);
		}
		String string = ChatFormatting.stripFormatting(this.getName().getString());
		if (!this.isNoAi() && (damageSource.is(DamageTypeTags.IS_FALL) ||
				AttackFind.isWaterDamage(damageSource) && "Elite".equals(string) ||
				damageSource.is(DamageTypes.CACTUS) ||
				damageSource.is(DamageTypes.SWEET_BERRY_BUSH))) {
			boolean bl = this.teleport();
			if (bl) {
				return false;
			}
		}
		if ("Elite".equals(string)) {
			f *= 0.5f;
		}
		if (!damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && this.getTarget() != null && !this.isNoAi()) {
			if ("Elite".equals(string)) {
				if (f < this.getHealth() * 10 &&
						this.getRandom().nextFloat() > 0.25f){
					boolean bl = this.teleport();
					if (bl) {
						return false;
					}
				}
			} else {
				if (f < this.getHealth() * 5 &&
						this.getRandom().nextFloat() > 0.7f) {
					boolean bl = this.teleport();
					if (bl) {
						return false;
					}
				}
			}
		}
		boolean bl = super.hurt(damageSource, f);
		if (bl && f > this.getHealth() / 3) {
			this.hurtsTick = 120;
		}
		return bl;
	}

		private boolean hurtWithCleanWater(DamageSource p_186273_, ThrownPotion p_186274_, float p_186275_) {
		ItemStack itemstack = p_186274_.getItem();
		Potion potion = PotionUtils.getPotion(itemstack);
		List<MobEffectInstance> list = PotionUtils.getMobEffects(itemstack);
		boolean flag = potion == Potions.WATER && list.isEmpty();
		return flag ? super.hurt(p_186273_, p_186275_) : false;
	}

	protected boolean teleport() {
		if (!this.level().isClientSide() && this.isAlive()) {
			double d0 = this.getX() + (this.random.nextDouble() - 0.5) * 64.0;
			double d1 = this.getY() + (double)(this.random.nextInt(64) - 32);
			double d2 = this.getZ() + (this.random.nextDouble() - 0.5) * 64.0;
			return this.teleport(d0, d1, d2);
		} else {
			return false;
		}
	}

	private boolean teleport(double p_32544_, double p_32545_, double p_32546_) {
		float find = 0.25f;
		String string = ChatFormatting.stripFormatting(this.getName().getString());
		if ("Elite".equals(string)) {
			find = 0.025f;
		}
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(p_32544_, p_32545_, p_32546_);

		while(blockpos$mutableblockpos.getY() > this.level().getMinBuildHeight() && !this.level().getBlockState(blockpos$mutableblockpos).blocksMotion()) {
			blockpos$mutableblockpos.move(Direction.DOWN);
		}

		BlockState blockstate = this.level().getBlockState(blockpos$mutableblockpos);
		boolean flag = blockstate.blocksMotion();
		boolean flag1 = blockstate.getFluidState().is(FluidTags.WATER);
		if (flag && !flag1) {
			EntityTeleportEvent.EnderEntity event = ForgeEventFactory.onEnderTeleport(this, p_32544_, p_32545_, p_32546_);
			if (event.isCanceled()) {
				return false;
			} else {
				Vec3 vec3 = this.position();
				boolean flag2;
				if (this.getRandom().nextFloat() > find && this.getTarget() != null && this.hurtsTick <= 0) {
					flag2 = this.findTeleport(this.getTarget());
				}
				else {
					flag2 = this.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
				}
				if (flag2) {
					this.level().gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(this));
					if (!this.isSilent()) {
						this.level().playSound(null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0F, 1.0F);
						this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
					}
				}

				return flag2;
			}
		} else {
			return false;
		}
	}

	public boolean findTeleport(LivingEntity entity) {
		int maxFind = 16;
		BlockPos summonPos;
		summonPos = Main.findSpawnPositionNearFillOnBlock(entity, 4);
		for (int find = 0; find < maxFind; ++find) {
			if (Main.canSee(this, entity)) {
				summonPos = Main.findSpawnPositionNearFillOnBlock(entity, 4);
			}
			else {
				break;
			}
		}
		this.teleportTo(summonPos.getX(), summonPos.getY(), summonPos.getZ());
		return true;
	}
}