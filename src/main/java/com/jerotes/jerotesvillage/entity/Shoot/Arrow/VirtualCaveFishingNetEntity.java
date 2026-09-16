package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotes.config.MainConfig;
import com.jerotes.jerotes.entity.Shoot.Arrow.BaseJavelinEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.HitResult;

public class VirtualCaveFishingNetEntity extends BaseJavelinEntity {
	private static final ItemStack item = new ItemStack(JerotesVillageItems.VIRTUAL_CAVE_FISHING_NET.get());
	private static final EntityType<VirtualCaveFishingNetEntity> type = JerotesVillageEntityType.VIRTUAL_CAVE_FISHING_NET.get();
	public VirtualCaveFishingNetEntity(EntityType<? extends VirtualCaveFishingNetEntity> entityType, Level level) {
		super(entityType, level, item, 1.0f);
	}
	public VirtualCaveFishingNetEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
		super(level, livingEntity, itemStack, type, 1.0f);
	}

	@Override
	protected void doPostHurtEffects(LivingEntity livingEntity) {
		super.doPostHurtEffects(livingEntity);
		if (!this.level().isClientSide()) {
			livingEntity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.FISHING_NET_CONFINEMET.get(), 50, 2, false, false));
		}
	}

	@Override
	public boolean canLoyalty() {
		return false;
	}
	@Override
	public boolean canHurtEnderman() {
		return true;
	}
	@Override
	public void hitUse(Entity entity) {
	}
	@Override
	public DamageSource getDamageSource(Entity entity) {
		return this.damageSources().thrown(this, this.getOwner() == null ? this : this.getOwner());
	}
	@Override
	protected SoundEvent getDefaultHitSoundEvent() {
		return SoundEvents.PLAYER_ATTACK_CRIT;
	}
	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return SoundEvents.PLAYER_ATTACK_CRIT;
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (!this.level().isClientSide && !this.isRemoved()) {
			if ((this.getOwner() instanceof Player || MainConfig.MobUseThrowShrinkItem)) {
				if ((this.level().getBlockState(BlockPos.containing(hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z))).getBlock() == Blocks.WATER) {
					this.level().setBlock(BlockPos.containing(hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z), JerotesVillageBlocks.VIRTUAL_CAVE_FISHING_NET_BLOCK.get().defaultBlockState(), 3);
				} else if ((this.level().getBlockState(BlockPos.containing(hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z))).is(Blocks.AIR)) {
					this.level().setBlock(BlockPos.containing(hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z), JerotesVillageBlocks.VIRTUAL_CAVE_FISHING_NET_BLOCK.get().defaultBlockState(), 3);
				} else {
					if (this.level() instanceof ServerLevel _level) {
						ItemEntity entityToSpawn = new ItemEntity(_level, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z, new ItemStack(JerotesVillageItems.VIRTUAL_CAVE_FISHING_NET.get()));
						entityToSpawn.setPickUpDelay(10);
						_level.addFreshEntity(entityToSpawn);
					}

				}
		}
			this.discard();
		}
	}

	@Override
	protected float getWaterInertia() {
		return 0.99f;
	}
	@Override
	public float specialDamage(Entity entity) {
		if (entity instanceof LivingEntity livingEntity && livingEntity.getMobType() == MobType.WATER) {
			return 7f;
		}
		return 0f;
	}
}
