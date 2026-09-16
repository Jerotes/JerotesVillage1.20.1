package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotes.entity.Shoot.Arrow.BaseJavelinEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageBuiltInLootTable;
import com.jerotes.jerotesvillage.init.JerotesVillageDimensions;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class ThrownMalignasaurFishgigEntity extends BaseJavelinEntity {
	private static final ItemStack item = new ItemStack(JerotesVillageItems.MALIGNASAUR_FISHGIG.get());
	private static final EntityType<ThrownMalignasaurFishgigEntity> type = JerotesVillageEntityType.THROWN_MALIGNASAUR_FISHGIG.get();
	public ThrownMalignasaurFishgigEntity(EntityType<? extends ThrownMalignasaurFishgigEntity> entityType, Level level) {
		super(entityType, level, item, 8.0f);
	}
	public ThrownMalignasaurFishgigEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
		super(level, livingEntity, itemStack, type, 8.0f);
	}

	private int fishgig = 0;

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
		BlockPos blockPos = this.blockPosition();
		FluidState fluidState = this.level().getFluidState(blockPos);
		double sx;
		double sy;
		double sz;

		int waterCount = 0;

		sx = -2;
		for (int index0 = 0; index0 < 5; index0++) {
			sy = 0;
			for (int index1 = 0; index1 < 2; index1++) {
				sz = -2;
				for (int index2 = 0; index2 < 5; index2++) {
					if ((this.level().getFluidState(BlockPos.containing(blockPos.getX() + sx, blockPos.getY() + sy, blockPos.getZ() + sz))).is(FluidTags.WATER)) {
						waterCount += 1;
					}
					sz = sz + 1;
				}
				sy = sy + 1;
			}
			sx = sx + 1;
		}
		if (waterCount >= 18 && fluidState.is(FluidTags.WATER) && this.fishgig == 0) {
			if (this.getOwner() != null && this.getOwner() instanceof Player player) {
				if (this.level() instanceof ServerLevel serverLevel) {
					float chance = 0.25f;
					if (serverLevel.isRaining()) {
						chance = 0.4f;
					}
					if (player.getRandom().nextFloat() < chance) {
						int x = this.getBlockX();
						int y = this.getBlockY();
						int z = this.getBlockZ();

						BlockPos blockPos1 = BlockPos.containing(x, y, z);
						if (serverLevel.dimension() == JerotesVillageDimensions.SECOND_ROUND_WORLD) {
							for (ItemStack itemStack : player.level().getServer().getLootData().getLootTable(JerotesVillageBuiltInLootTable.FISHING).getRandomItems(new LootParams.Builder((ServerLevel) player.level()).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockPos1)).withParameter(LootContextParams.BLOCK_STATE, player.level().getBlockState(blockPos1)).withOptionalParameter(LootContextParams.BLOCK_ENTITY, player.level().getBlockEntity(blockPos1)).create(LootContextParamSets.EMPTY))) {
								if (player.level() instanceof ServerLevel _level) {
									ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemStack);
									entityToSpawn.setPickUpDelay(10);
									entityToSpawn.setUnlimitedLifetime();
									_level.addFreshEntity(entityToSpawn);
								}
								player.playSound(SoundEvents.FISH_SWIM, 1.0F, 1.0F);
							}
						}
						else {
							for (ItemStack itemStack : player.level().getServer().getLootData().getLootTable(BuiltInLootTables.FISHING).getRandomItems(new LootParams.Builder((ServerLevel) player.level()).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockPos1)).withParameter(LootContextParams.BLOCK_STATE, player.level().getBlockState(blockPos1)).withOptionalParameter(LootContextParams.BLOCK_ENTITY, player.level().getBlockEntity(blockPos1)).create(LootContextParamSets.EMPTY))) {
								if (player.level() instanceof ServerLevel _level) {
									ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemStack);
									entityToSpawn.setPickUpDelay(10);
									entityToSpawn.setUnlimitedLifetime();
									_level.addFreshEntity(entityToSpawn);
								}
								player.playSound(SoundEvents.FISH_SWIM, 1.0F, 1.0F);
							}
						}
					}
				}
				this.fishgig = 1;
			}
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		compoundTag.putInt("Fishgig", this.fishgig);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("Fishgig", this.fishgig);
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
