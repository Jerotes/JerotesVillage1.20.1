package com.jerotes.jerotesvillage.util;

import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Animal.CarvedHoundEntity;
import com.jerotes.jerotesvillage.entity.Animal.CarvedLlamaEntity;
import com.jerotes.jerotesvillage.entity.Humanoid.CarvedVillagerEntity;
import com.jerotes.jerotesvillage.entity.Neutral.CarvedAllayEntity;
import com.jerotes.jerotesvillage.entity.Neutral.CarvedIronGolemEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class CopperCarvedCompany {
	//铜刻佣兵团
	public static void CopperCarvedCompanySpawn(Level level, BlockPos blockPos, int n) {
		//1-初始村民
		SummonCarvedVillager(level, blockPos, n);
		//2-初始小队
		if (n >= 2) {
			for (int i = 0; i < 2; ++i) {
				SummonCarvedVillager(level, blockPos, n);
			}
		}
		//3-铜刻吹号兵+铜刻猎犬
		if (n >= 3) {
			SummonCarvedTrumpeter(level, blockPos, n);
			SummonCarvedHound(level, blockPos, n);
		}
		//4-铜刻铁傀儡+铜刻村民*2
		if (n >= 4) {
			SummonCarvedIronGolem(level, blockPos, n);
			for (int i = 0; i < 2; ++i) {
				SummonCarvedVillager(level, blockPos, n);
			}
		}
		//5-铜刻悦灵+铜刻战马骑兵+铜刻猎犬*2
		if (n >= 5) {
			SummonCarvedRider(level, blockPos, n);
			SummonCarvedAllay(level, blockPos, n);
			for (int i = 0; i < 2; ++i) {
				SummonCarvedHound(level, blockPos, n);
			}
		}
		//6-铜刻小队长+铜刻羊驼*2
		if (n >= 6) {
			SummonCarvedCaptain(level, blockPos, n);
			for (int i = 0; i < 2; ++i) {
				SummonCarvedLlama(level, blockPos, n);
			}
		}
		//7-铜刻铁傀儡*2+铜刻悦灵
		if (n >= 7) {
			SummonCarvedAllay(level, blockPos, n);
		}
		//8-新增部队
		if (n >= 8) {
			for (int i = 0; i < 3; ++i) {
				SummonCarvedVillager(level, blockPos, n);
			}
		}
		//9-新增部队
		if (n >= 9) {
			for (int i = 0; i < 3; ++i) {
				SummonCarvedVillager(level, blockPos, n);
			}
		}
		//10-新增部队+下界合金铜刻铁傀儡
		if (n >= 10) {
			SummonNetheriteCarvedIronGolem(level, blockPos, n);
			for (int i = 0; i < 3; ++i) {
				SummonCarvedVillager(level, blockPos, n);
			}
		}
		//剩余
		if (n > 10) {
			int other = n - 10;
			//铜刻村民
			if (other < 5) {
				SummonCarvedVillager(level, blockPos, n);
			}
			else {
				if (other % 3 == 0) {
					SummonCarvedTrumpeter(level, blockPos, n);
					for (int i = 0; i < 2; ++i) {
						SummonCarvedVillager(level, blockPos, n);
					}
				}
				else if (other % 5 == 0) {
					SummonCarvedRider(level, blockPos, n);
					for (int i = 0; i < 2; ++i) {
						SummonCarvedVillager(level, blockPos, n);
					}
				}
				else if (other % 7 == 0) {
					SummonCarvedCaptain(level, blockPos, n);
					for (int i = 0; i < 2; ++i) {
						SummonCarvedVillager(level, blockPos, n);
					}
				}
				else {
					for (int i = 0; i < 2; ++i) {
						SummonCarvedVillager(level, blockPos, n);
					}
				}
			}
			//铜刻铁傀儡
            SummonCarvedIronGolem(level, blockPos, n);
            if (other >= 5 || other % 2 != 0) {
                if (other % 10 == 0) {
                    SummonNetheriteCarvedIronGolem(level, blockPos, n);
                }
            }
			//铜刻猎犬 铜刻羊驼
			SummonCarvedHound(level, blockPos, n);
			if (other >= 5) {
				SummonCarvedHound(level, blockPos, n);
				if (other % 3 == 0) {
					SummonCarvedLlama(level, blockPos, n);
				}
			}
			//铜刻悦灵
			if (other < 5) {
				if (other % 2 == 0) {
					SummonCarvedAllay(level, blockPos, n);
				}
			}
			else {
				SummonCarvedAllay(level, blockPos, n);
			}
		}
	}

	//铜刻村民
	public static void SummonCarvedVillager(Level level, BlockPos blockPos, int n) {
		if (level instanceof ServerLevel serverLevel) {
			BlockPos spawnBlockPos = Main.findSpawnPositionNearFillOnBlock(serverLevel, blockPos, Math.min(12, n/8+1));
			CarvedVillagerEntity carvedVillager = JerotesVillageEntityType.CARVED_VILLAGER.get().spawn(serverLevel, BlockPos.containing(spawnBlockPos.getX(), spawnBlockPos.getY(), spawnBlockPos.getZ()), MobSpawnType.EVENT);
			if (carvedVillager != null) {
				carvedVillager.setItemSlot(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.VILLAGER_METAL_HELMET.get()));
				carvedVillager.setItemSlot(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.VILLAGER_METAL_CHESTPLATE.get()));
				carvedVillager.setItemSlot(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.VILLAGER_METAL_LEGGINGS.get()));
				carvedVillager.setItemSlot(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.VILLAGER_METAL_BOOTS.get()));
				for (int i = 0; i < 10; ++i) {
					serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, carvedVillager.getRandomX(0.5f), carvedVillager.getRandomY(), carvedVillager.getRandomZ(0.5f), 0, 0, 0, 0, 0);
				}
			}
		}
	}
	//铜刻吹号兵
	public static void SummonCarvedTrumpeter(Level level, BlockPos blockPos, int n) {
		if (level instanceof ServerLevel serverLevel) {
			BlockPos spawnBlockPos = Main.findSpawnPositionNearFillOnBlock(serverLevel, blockPos, n/8+1);
			CarvedVillagerEntity carvedVillager = JerotesVillageEntityType.CARVED_VILLAGER.get().spawn(serverLevel, BlockPos.containing(spawnBlockPos.getX(), spawnBlockPos.getY(), spawnBlockPos.getZ()), MobSpawnType.EVENT);
			if (carvedVillager != null) {
				carvedVillager.setItemSlot(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.VILLAGER_METAL_HELMET.get()));
				carvedVillager.setItemSlot(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.VILLAGER_METAL_CHESTPLATE.get()));
				carvedVillager.setItemSlot(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.VILLAGER_METAL_LEGGINGS.get()));
				carvedVillager.setItemSlot(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.VILLAGER_METAL_BOOTS.get()));
				carvedVillager.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(JerotesVillageItems.VILLAGER_METAL_SWORD.get()));
				carvedVillager.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(JerotesVillageItems.CARVED_HORN.get()));
				for (int i = 0; i < 10; ++i) {
					serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, carvedVillager.getRandomX(0.5f), carvedVillager.getRandomY(), carvedVillager.getRandomZ(0.5f), 0, 0, 0, 0, 0);
				}
			}
		}
	}
	//铜刻小队长
	public static void SummonCarvedCaptain(Level level, BlockPos blockPos, int n) {
		if (level instanceof ServerLevel serverLevel) {
			BlockPos spawnBlockPos = Main.findSpawnPositionNearFillOnBlock(serverLevel, blockPos, n/8+1);
			CarvedVillagerEntity carvedVillager = JerotesVillageEntityType.CARVED_VILLAGER.get().spawn(serverLevel, BlockPos.containing(spawnBlockPos.getX(), spawnBlockPos.getY(), spawnBlockPos.getZ()), MobSpawnType.EVENT);
			if (carvedVillager != null) {
				carvedVillager.setItemSlot(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.CARVED_LEADER_HELMET.get()));
				carvedVillager.setItemSlot(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.CARVED_LEADER_CHESTPLATE.get()));
				carvedVillager.setItemSlot(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.CARVED_LEADER_LEGGINGS.get()));
				carvedVillager.setItemSlot(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.CARVED_LEADER_BOOTS.get()));
				carvedVillager.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.NETHERITE_SWORD));
				carvedVillager.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(JerotesVillageItems.CARVED_FLAG.get()));
				Objects.requireNonNull(carvedVillager.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(32);
				carvedVillager.setHealth(carvedVillager.getMaxHealth());
				Objects.requireNonNull(carvedVillager.getAttribute(Attributes.FOLLOW_RANGE)).setBaseValue(96);
				carvedVillager.setCanChangeInventory(true);
				carvedVillager.setCanChangeMeleeOrRange(true);
				carvedVillager.setChangeInventoryCooldownTick(15);
				carvedVillager.setBowLevel(18);
				carvedVillager.mobInventory().addItem(new ItemStack(JerotesVillageItems.VILLAGER_METAL_BOW.get()));
				for (int i = 0; i < 10; ++i) {
					serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, carvedVillager.getRandomX(0.5f), carvedVillager.getRandomY(), carvedVillager.getRandomZ(0.5f), 0, 0, 0, 0, 0);
				}
			}
		}
	}
	//铜刻骑兵
	public static void SummonCarvedRider(Level level, BlockPos blockPos, int n) {
		if (level instanceof ServerLevel serverLevel) {
			BlockPos spawnBlockPos = Main.findSpawnPositionNearFillOnBlock(serverLevel, blockPos, n/8+1);
			CarvedVillagerEntity carvedVillager = JerotesVillageEntityType.CARVED_VILLAGER.get().spawn(serverLevel, BlockPos.containing(spawnBlockPos.getX(), spawnBlockPos.getY(), spawnBlockPos.getZ()), MobSpawnType.EVENT);
			if (carvedVillager != null) {
				carvedVillager.setItemSlot(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.VILLAGER_METAL_HELMET.get()));
				carvedVillager.setItemSlot(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.VILLAGER_METAL_CHESTPLATE.get()));
				carvedVillager.setItemSlot(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.VILLAGER_METAL_LEGGINGS.get()));
				carvedVillager.setItemSlot(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.VILLAGER_METAL_BOOTS.get()));
				carvedVillager.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(JerotesVillageItems.VILLAGER_METAL_SWORD.get()));
				carvedVillager.setCanChangeInventory(true);
				carvedVillager.setCanChangeMeleeOrRange(true);
				carvedVillager.mobInventory().addItem(new ItemStack(JerotesVillageItems.VILLAGER_METAL_BOW.get()));
				for (int i = 0; i < 10; ++i) {
					serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, carvedVillager.getRandomX(0.5f), carvedVillager.getRandomY(), carvedVillager.getRandomZ(0.5f), 0, 0, 0, 0, 0);
				}
			}
			Horse horse = EntityType.HORSE.spawn(serverLevel, BlockPos.containing(spawnBlockPos.getX(), spawnBlockPos.getY(), spawnBlockPos.getZ()), MobSpawnType.EVENT);
			if (horse != null) {
				horse.setTamed(true);
				horse.equipSaddle(SoundSource.NEUTRAL);
				CompoundTag compoundTag = new CompoundTag();
				horse.addAdditionalSaveData(compoundTag);
				compoundTag.put("ArmorItem", new ItemStack(JerotesVillageItems.VILLAGER_METAL_WAR_BEAST_ARMOR.get()).save(new CompoundTag()));
				horse.readAdditionalSaveData(compoundTag);
				if (carvedVillager != null) {
					carvedVillager.startRiding(horse);
				}
				for (int i = 0; i < 10; ++i) {
					serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, horse.getRandomX(0.5f), horse.getRandomY(), horse.getRandomZ(0.5f), 0, 0, 0, 0, 0);
				}
			}
		}
	}
	//铜刻铁傀儡
	public static void SummonCarvedIronGolem(Level level, BlockPos blockPos, int n) {
		if (level instanceof ServerLevel serverLevel) {
			BlockPos spawnBlockPos = Main.findSpawnPositionNearFillOnBlock(serverLevel, blockPos, n/8+1);
			CarvedIronGolemEntity carvedIronGolem = JerotesVillageEntityType.CARVED_IRON_GOLEM.get().spawn(serverLevel, BlockPos.containing(spawnBlockPos.getX(), spawnBlockPos.getY(), spawnBlockPos.getZ()), MobSpawnType.EVENT);
			if (carvedIronGolem != null) {
				for (int i = 0; i < 10; ++i) {
					serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, carvedIronGolem.getRandomX(0.5f), carvedIronGolem.getRandomY(), carvedIronGolem.getRandomZ(0.5f), 0, 0, 0, 0, 0);
				}
			}
		}
	}
	//下界合金铜刻铁傀儡
	public static void SummonNetheriteCarvedIronGolem(Level level, BlockPos blockPos, int n) {
		if (level instanceof ServerLevel serverLevel) {
			BlockPos spawnBlockPos = Main.findSpawnPositionNearFillOnBlock(serverLevel, blockPos, n/8+1);
			CarvedIronGolemEntity carvedIronGolem = JerotesVillageEntityType.CARVED_IRON_GOLEM.get().spawn(serverLevel, BlockPos.containing(spawnBlockPos.getX(), spawnBlockPos.getY(), spawnBlockPos.getZ()), MobSpawnType.EVENT);
			if (carvedIronGolem != null) {
				carvedIronGolem.setNetheriteTick(12000);
				for (int i = 0; i < 10; ++i) {
					serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, carvedIronGolem.getRandomX(0.5f), carvedIronGolem.getRandomY(), carvedIronGolem.getRandomZ(0.5f), 0, 0, 0, 0, 0);
				}
			}
		}
	}
	//铜刻猎犬
	public static void SummonCarvedHound(Level level, BlockPos blockPos, int n) {
		if (level instanceof ServerLevel serverLevel) {
			BlockPos spawnBlockPos = Main.findSpawnPositionNearFillOnBlock(serverLevel, blockPos, n/8+1);
			CarvedHoundEntity carvedHound = JerotesVillageEntityType.CARVED_HOUND.get().spawn(serverLevel, BlockPos.containing(spawnBlockPos.getX(), spawnBlockPos.getY(), spawnBlockPos.getZ()), MobSpawnType.EVENT);
			if (carvedHound != null) {
				for (int i = 0; i < 10; ++i) {
					serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, carvedHound.getRandomX(0.5f), carvedHound.getRandomY(), carvedHound.getRandomZ(0.5f), 0, 0, 0, 0, 0);
				}
			}
		}
	}
	//铜刻羊驼
	public static void SummonCarvedLlama(Level level, BlockPos blockPos, int n) {
		if (level instanceof ServerLevel serverLevel) {
			BlockPos spawnBlockPos = Main.findSpawnPositionNearFillOnBlock(serverLevel, blockPos, n/8+1);
			CarvedLlamaEntity carvedLlama = JerotesVillageEntityType.CARVED_LLAMA.get().spawn(serverLevel, BlockPos.containing(spawnBlockPos.getX(), spawnBlockPos.getY(), spawnBlockPos.getZ()), MobSpawnType.EVENT);
			if (carvedLlama != null) {
				for (int i = 0; i < 10; ++i) {
					serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, carvedLlama.getRandomX(0.5f), carvedLlama.getRandomY(), carvedLlama.getRandomZ(0.5f), 0, 0, 0, 0, 0);
				}
			}
		}
	}
	//铜刻悦灵
	public static void SummonCarvedAllay(Level level, BlockPos blockPos, int n) {
		if (level instanceof ServerLevel serverLevel) {
			BlockPos spawnBlockPos = Main.findSpawnPositionNearFillOnBlock(serverLevel, blockPos, n/8+1);
			CarvedAllayEntity carvedAllay = JerotesVillageEntityType.CARVED_ALLAY.get().spawn(serverLevel, BlockPos.containing(spawnBlockPos.getX(), spawnBlockPos.getY(), spawnBlockPos.getZ()), MobSpawnType.EVENT);
			if (carvedAllay != null) {
				for (int i = 0; i < 10; ++i) {
					serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, carvedAllay.getRandomX(0.5f), carvedAllay.getRandomY(), carvedAllay.getRandomZ(0.5f), 0, 0, 0, 0, 0);
				}
			}
		}
	}
}