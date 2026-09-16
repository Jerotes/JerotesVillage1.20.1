package com.jerotes.jerotesvillage.util;

import com.jerotes.jerotes.entity.Interface.BossEntity;
import com.jerotes.jerotes.entity.Interface.EliteEntity;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Darisorder.DarisorderEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.network.JerotesVillageLevelData;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Objects;

public class SpawnRules {
	//生成
	//方块
	public static boolean BlockSpawn(int num, Block block, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
		AABB aabb = AABB.ofSize(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()), num, num, num);
		for (BlockPos blockPoss : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
			if (!levelAccessor.hasChunkAt(blockPoss.getX(), blockPoss.getZ())) continue;
			if (!levelAccessor.getBlockState(blockPoss).is(block)) continue;
			return true;
		}
		return false;
	}
	//Base
	public static boolean checkSurfaceWaterSpawnRules(EntityType<? extends Mob> mob, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
		int i = levelAccessor.getSeaLevel();
		int j = i - 13;
		return blockPos.getY() >= j && blockPos.getY() <= i && levelAccessor.getFluidState(blockPos.below()).is(FluidTags.WATER) && levelAccessor.getBlockState(blockPos.above()).is(Blocks.WATER);
	}
	//正常
	public static boolean NeutralSpawn(int num, double area, EntityType<? extends Mob> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
		List<Mob> list = levelAccessor.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()), area, area, area));
		list.removeIf(entity -> entity.getType() != entityType);
		return list.size() < num && Mob.checkMobSpawnRules(entityType, levelAccessor, mobSpawnType, blockPos, randomSource);
	}
	//蝙蝠
	public static boolean BatSpawn(int num, double area, EntityType<? extends Mob> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
		List<Mob> list = levelAccessor.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()), area, area, area));
		list.removeIf(entity -> entity.getType() != entityType);
		return list.size() < num && checkBatSpawnRules(entityType, levelAccessor, mobSpawnType, blockPos, randomSource);
	}
	public static boolean checkBatSpawnRules(EntityType<? extends Mob> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
		if (blockPos.getY() >= levelAccessor.getSeaLevel()) {
			return false;
		}
		int n = levelAccessor.getMaxLocalRawBrightness(blockPos);
		int n2 = 4;
		if (isHalloween() ) {
			n2 = 7;
		} else if (randomSource.nextBoolean()) {
			return false;
		}
		if (n > randomSource.nextInt(n2)) {
			return false;
		}
		return Bat.checkMobSpawnRules(entityType, levelAccessor, mobSpawnType, blockPos, randomSource);
	}
	public static boolean isHalloween() {
		LocalDate $$0 = LocalDate.now();
		int $$1 = $$0.get(ChronoField.DAY_OF_MONTH);
		int $$2 = $$0.get(ChronoField.MONTH_OF_YEAR);
		return $$2 == 10 && $$1 >= 20 || $$2 == 11 && $$1 <= 3;
	}
	//铜刻类型生物
	public static boolean CarvedSpawn(int num, double area, EntityType<? extends Mob> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
		List<Mob> list = levelAccessor.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()), area, area, area));
		list.removeIf(entity -> entity.getType() != entityType);
		return list.size() < num && Mob.checkMobSpawnRules(entityType, levelAccessor, mobSpawnType, blockPos, randomSource);
	}
	//动物
	public static boolean AnimalSpawn(int num, double area, EntityType<? extends Animal> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
		List<Mob> list = levelAccessor.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()), area, area, area));
		list.removeIf(entity -> entity.getType() != entityType);
		return list.size() < num && Animal.checkAnimalSpawnRules(entityType, levelAccessor, mobSpawnType, blockPos, randomSource);
	}
	//拱鼻角兽
	public static boolean ArchnosedHornbeastSpawn(int num, double area, EntityType<? extends Animal> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
		List<Mob> list = levelAccessor.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()), area, area, area));
		list.removeIf(entity -> entity.getType() != entityType);
		boolean bl = false;
		List<? extends Player> playerList = levelAccessor.players();
		playerList.removeIf(player -> player.level() != levelAccessor);
		for (Player player : playerList) {
			if (Main.getJerotesPersistentData(player).getDouble("jerotesvillage_bright_land_heart") > 0 ||
					player.hasEffect(JerotesVillageMobEffects.BRIGHT_LAND_HEART.get())) {
				bl = true;
				break;
			}
		}
		return bl && list.size() < num && Animal.checkAnimalSpawnRules(entityType, levelAccessor, mobSpawnType, blockPos, randomSource);
	}
	//特殊鱼类
	public static boolean WaterFishSpawn(int num, double area, EntityType<? extends WaterAnimal> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
		List<Mob> list = levelAccessor.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()), area, area, area));
		list.removeIf(entity -> entity.getType() != entityType);
		return list.size() < num && WaterAnimal.checkSurfaceWaterAnimalSpawnRules(entityType, levelAccessor, mobSpawnType, blockPos, randomSource);
	}
	//水生动物
	public static boolean WaterAnimalSpawn(int num, double area, EntityType<? extends WaterAnimal> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
		List<Mob> list = levelAccessor.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()), area, area, area));
		list.removeIf(entity -> entity.getType() != entityType);
		return list.size() < num && WaterAnimal.checkSurfaceWaterAnimalSpawnRules(entityType, levelAccessor, mobSpawnType, blockPos, randomSource);
	}
	//水生生物
	public static boolean WaterMobSpawn(int num, double area, EntityType<? extends Mob> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
		List<Mob> list = levelAccessor.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()), area, area, area));
		list.removeIf(entity -> entity.getType() != entityType);
		return list.size() < num && checkSurfaceWaterSpawnRules(entityType, levelAccessor, mobSpawnType, blockPos, randomSource);
	}
	//黑暗怪物
	public static boolean DarkMonsterSpawn(int num, double area, EntityType<? extends Monster> entityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
		List<LivingEntity> list = serverLevelAccessor.getEntitiesOfClass(LivingEntity.class, AABB.ofSize(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()), area, area, area));
		list.removeIf(entity -> entity.getType() != entityType);
		return list.size() < num && Monster.checkMonsterSpawnRules(entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource);
	}
	//暗乱
	public static boolean DarisorderSpawn(int num, double area, EntityType<? extends DarisorderEntity> entityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
		List<LivingEntity> list = serverLevelAccessor.getEntitiesOfClass(LivingEntity.class, AABB.ofSize(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()), area, area, area));
		list.removeIf(entity -> entity.getType() != entityType);
		return list.size() < num && DarisorderEntity.checkMonsterSpawnRules(entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource);
	}
	//发光鱿鱼
	public static boolean GlowSquidSpawn(int num, double area, EntityType<? extends Mob> entityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
		List<LivingEntity> list = serverLevelAccessor.getEntitiesOfClass(LivingEntity.class, AABB.ofSize(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()), area, area, area));
		list.removeIf(entity -> entity.getType() != entityType);
		return list.size() < num && blockPos.getY() <= serverLevelAccessor.getSeaLevel() - 33 && serverLevelAccessor.getRawBrightness(blockPos, 0) == 0 && serverLevelAccessor.getBlockState(blockPos).is(Blocks.WATER);
	}
	//邪笑呼唤
	public static boolean WickedRidculeCallsSpawn(int num, double area, EntityType<? extends Monster> entityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
		if (JerotesVillageLevelData.get(serverLevelAccessor) != null && !JerotesVillageLevelData.get(serverLevelAccessor).WickedRidiculeNight) {
			List<LivingEntity> list = serverLevelAccessor.getEntitiesOfClass(LivingEntity.class, AABB.ofSize(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()), area, area, area));
			list.removeIf(entity -> entity.getType() != entityType);
			return list.size() < num && Monster.checkMonsterSpawnRules(entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource);
		}
		return Monster.checkMonsterSpawnRules(entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource);
	}
	//阳光怪物
	public static boolean LightMonsterSpawn(int num, double area, EntityType<? extends Monster> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
		List<Mob> list = levelAccessor.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()), area, area, area));
		list.removeIf(entity -> entity.getType() != entityType);
		return list.size() < num && Monster.checkAnyLightMonsterSpawnRules(entityType, levelAccessor, mobSpawnType, blockPos, randomSource);
	}
	//NPC
	public static boolean NPCSpawn(int num, double area, EntityType<? extends Mob> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
		List<Mob> list = levelAccessor.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()), area, area, area));
		list.removeIf(entity -> entity.getType() != entityType);
		return list.size() < num && Mob.checkMobSpawnRules(entityType, levelAccessor, mobSpawnType, blockPos, randomSource);
	}
	//蛇龙人
	public static boolean SerponSpawn(int num, double area, EntityType<? extends Mob> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
		List<Mob> list = levelAccessor.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()), area, area, area));
		list.removeIf(entity -> entity.getType() != entityType);
		double areas = OtherMainConfig.BossSpawnRestrictionRange;
		List<Mob> listBoss = levelAccessor.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()), areas, areas, areas));
		listBoss.removeIf(entity -> entity.getType() != JerotesVillageEntityType.SERPON_COMBAT_EXECUTER.get());
		return list.size() < num && JerotesVillageLevelData.get(levelAccessor) != null && !JerotesVillageLevelData.get(levelAccessor).SerponAway && listBoss.isEmpty() && Mob.checkMobSpawnRules(entityType, levelAccessor, mobSpawnType, blockPos, randomSource);
	}
	//水生生物
	public static boolean WaterNPCSpawn(int num, double area, EntityType<? extends Mob> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
		List<Mob> list = levelAccessor.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()), area, area, area));
		list.removeIf(entity -> entity.getType() != entityType);
		return list.size() < num && checkSurfaceWaterSpawnRules(entityType, levelAccessor, mobSpawnType, blockPos, randomSource);
	}
	//精英
	public static boolean EliteSpawn(int num, double area, EntityType<? extends Mob> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
		if (area != OtherMainConfig.EliteSpawnRestrictionRange) {
			area = OtherMainConfig.EliteSpawnRestrictionRange;
		}
		List<Mob> list = levelAccessor.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()), area, area, area));
		if (!(OtherMainConfig.EliteOnlyOne || OtherMainConfig.EliteAndBossOnlyOne || OtherMainConfig.SameTypeEliteOnlyOne)) {
			num = num * 8;
		}
		//不影响的情况
		list.removeIf(entity -> entity.getType() != entityType
				&& !(OtherMainConfig.EliteOnlyOne && entity instanceof EliteEntity && !isSameMod(entityType, entity.getType()))
				&& !(OtherMainConfig.EliteAndBossOnlyOne && (entity instanceof BossEntity || entity instanceof EliteEntity) && !isSameMod(entityType, entity.getType()))
		);
		return list.size() < num && Mob.checkMobSpawnRules(entityType, levelAccessor, mobSpawnType, blockPos, randomSource);
	}
	//Boss
	public static boolean BossSpawn(int num, double area, EntityType<? extends Mob> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
		if (area != OtherMainConfig.BossSpawnRestrictionRange) {
			area = OtherMainConfig.BossSpawnRestrictionRange;
		}
		List<Mob> list = levelAccessor.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()), area, area, area));
		if (!(OtherMainConfig.BossOnlyOne || OtherMainConfig.EliteAndBossOnlyOne || OtherMainConfig.SameTypeBossOnlyOne)) {
			num = num * 4;
		}
		//不影响的情况
		list.removeIf(entity -> entity.getType() != entityType
				&& !(OtherMainConfig.BossOnlyOne && entity instanceof BossEntity && !isSameMod(entityType, entity.getType()))
				&& !(OtherMainConfig.EliteAndBossOnlyOne && (entity instanceof BossEntity || entity instanceof EliteEntity) && !isSameMod(entityType, entity.getType()))
		);
		return list.size() < num && Mob.checkMobSpawnRules(entityType, levelAccessor, mobSpawnType, blockPos, randomSource);
	}

	//Boss生成时的检测
	public static boolean BossSpawnFind(EntityType<? extends BossEntity> entityType, Entity posEntity) {
		double area = OtherMainConfig.BossSpawnRestrictionRange;
		List<Mob> list = posEntity.level().getEntitiesOfClass(Mob.class, posEntity.getBoundingBox().inflate(area, area, area));
		//不影响的情况
		list.removeIf(entity ->
				!(OtherMainConfig.SameTypeBossOnlyOne && entity.getType() == entityType)
						&& !(OtherMainConfig.BossOnlyOne && entity instanceof BossEntity && !isSameMod(entityType, entity.getType()))
						&& !(OtherMainConfig.EliteAndBossOnlyOne && (entity instanceof BossEntity || entity instanceof EliteEntity) && !isSameMod(entityType, entity.getType()))
		);
		if (!list.isEmpty() && posEntity.level() instanceof ServerLevel) {
			posEntity.sendSystemMessage(Component.translatable("boss.jerotes.can_not_spawn").withStyle(ChatFormatting.BLUE));
		}
		return list.isEmpty();
	}

	public static boolean BossSpawnFind(EntityType<? extends BossEntity> entityType, Level level, BlockPos blockPos) {
		double area = OtherMainConfig.BossSpawnRestrictionRange;
		List<Mob> list = level.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()), area, area, area));
		//不影响的情况
		list.removeIf(entity ->
				!(OtherMainConfig.SameTypeBossOnlyOne && entity.getType() == entityType)
						&& !(OtherMainConfig.BossOnlyOne && entity instanceof BossEntity && !isSameMod(entityType, entity.getType()))
						&& !(OtherMainConfig.EliteAndBossOnlyOne && (entity instanceof BossEntity || entity instanceof EliteEntity) && !isSameMod(entityType, entity.getType()))
		);
		return list.isEmpty();
	}

	public static boolean isSameMod(EntityType<?> entityType, EntityType<?> entityType2) {
		return Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(entityType)).getNamespace().equals(Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(entityType2)).getNamespace());
	}
}