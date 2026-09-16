package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotes.entity.Shoot.Arrow.BaseJavelinEntity;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Boss.Biome.GemstoneMalignasaurEntity;
import com.jerotes.jerotesvillage.util.BossSpawn;
import com.jerotes.jerotesvillage.init.JerotesVillageBiomes;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.util.SpawnRules;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

public class ThrownMalignasaurBaitFishgigEntity extends BaseJavelinEntity {
	private static final ItemStack item = new ItemStack(JerotesVillageItems.MALIGNASAUR_BAIT_FISHGIG.get());
	private static final EntityType<ThrownMalignasaurBaitFishgigEntity> type = JerotesVillageEntityType.THROWN_MALIGNASAUR_BAIT_FISHGIG.get();
	public ThrownMalignasaurBaitFishgigEntity(EntityType<? extends ThrownMalignasaurBaitFishgigEntity> entityType, Level level) {
		super(entityType, level, item, 8.0f);
	}
	public ThrownMalignasaurBaitFishgigEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
		super(level, livingEntity, itemStack, type, 8.0f);
	}

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
		BlockPos blockPos = this.blockPosition();
		FluidState fluidState = this.level().getFluidState(blockPos);
		int waterCount = 0;

		AABB aabb = new AABB(this.getX() + 18, this.getY() - 36, this.getZ() + 18, this.getX() - 18, this.getY() + 36, this.getZ() - 18);
		if (this.level() instanceof ServerLevel serverLevel) {
			for (BlockPos blockPosFind : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
				if (serverLevel.getFluidState(blockPosFind).is(FluidTags.WATER)) {
					waterCount += 1;
				}
				if (SpawnRules.BossSpawnFind(JerotesVillageEntityType.GEMSTONE_MALIGNASAUR.get(), this)) {
					if (waterCount > 10000 && fluidState.is(FluidTags.WATER)) {
						if  (serverLevel.getBiome(blockPos).is(JerotesVillageBiomes.ENDLESS_OCEAN)){
							if (this.getOwner() != null && this.getOwner() instanceof Player player) {
								if (!player.level().isClientSide) {
									player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 200, 0));
								}
								BlockPos blockPos1 = Main.findSpawnPositionNearFill(player, 24);
								GemstoneMalignasaurEntity boss = null;
								if (blockPos1 != null) {
									boss = JerotesVillageEntityType.GEMSTONE_MALIGNASAUR.get().spawn(serverLevel, blockPos1, MobSpawnType.EVENT);
									if (boss != null) {
										boss.setDeltaMovement(0, 0, 0);
										if (!boss.level().isClientSide) {
											boss.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 200, 1));
										}
										boss.start = 260;
										boss.setHungerOf(boss.getHungerOf() + 480);
									}
									serverLevel.playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.GEMSTONE_MALIGNASAUR_AMBIENT, SoundSource.NEUTRAL, 20.0f, 1.0f / (player.getRandom().nextFloat() * 0.4f + 1.2f) + 1 * 0.5f);
									player.getCooldowns().addCooldown(JerotesVillageItems.MALIGNASAUR_BAIT_FISHGIG.get(), 1800);
									BossSpawn.GemstoneMalignasaur(player);
									this.discard();
								}
								return;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public float specialDamage(Entity entity) {
		if (entity instanceof LivingEntity livingEntity && livingEntity.getMobType() == MobType.WATER) {
			return 8f;
		}
		return 0f;
	}
	@Override
	protected float getWaterInertia() {
		return 1.2f;
	}
}
