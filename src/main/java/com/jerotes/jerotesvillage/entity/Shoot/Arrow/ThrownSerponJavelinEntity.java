package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotes.entity.Shoot.Arrow.BaseJavelinEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.entity.Boss.Biome.SerponCombatExecuterEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThrownSerponJavelinEntity extends BaseJavelinEntity {
	private static final ItemStack item = new ItemStack(JerotesVillageItems.SERPON_JAVELIN.get());
	private static final EntityType<ThrownSerponJavelinEntity> type = JerotesVillageEntityType.THROWN_SERPON_JAVELIN.get();
	public ThrownSerponJavelinEntity(EntityType<? extends ThrownSerponJavelinEntity> entityType, Level level) {
		super(entityType, level, item, 8.0f);
	}
	public ThrownSerponJavelinEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
		super(level, livingEntity, itemStack, type, 8.0f);
	}

	public boolean isSummon;
    @Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.isSummon = compoundTag.getBoolean("IsSummon");
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean("IsSummon", this.isSummon);
	}

	@Override
	public void tick() {
		if (this.isSummon) {
			if (this.level() instanceof ServerLevel serverLevel && !this.inGround) {
				serverLevel.sendParticles(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		super.tick();
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (this.isSummon) {
			this.discard();
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		if (this.isSummon && entityHitResult.getEntity() instanceof SerponCombatExecuterEntity) {
			return;
		}
		super.onHitEntity(entityHitResult);
	}
	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
		if (this.level().isClientSide) {
			return;
		}
		if (this.isSummon) {
			Entity entity = this.getOwner();
			if (!(entity instanceof Mob) || this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
				BlockPos blockPos = blockHitResult.getBlockPos().relative(blockHitResult.getDirection());
				if (this.level().isEmptyBlock(blockPos)) {
					this.level().setBlockAndUpdate(blockPos, BaseFireBlock.getState(this.level(), blockPos));
				}
			}
		}
	}

	@Override
	public DamageSource getDamageSource(Entity entity) {
		if (this.isSummon) {
			return AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_SHOOT, this, this.getOwner() == null ? this : this.getOwner());
		}
		return super.getDamageSource(entity);
	}
}
