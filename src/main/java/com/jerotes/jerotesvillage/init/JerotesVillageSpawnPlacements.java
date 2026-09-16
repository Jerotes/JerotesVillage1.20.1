package com.jerotes.jerotesvillage.init;

import com.jerotes.jerotesvillage.util.SpawnRules;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class JerotesVillageSpawnPlacements {
	public static void init() {
		SpawnPlacements.register(JerotesVillageEntityType.SMART.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SMART_BULGE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.WaterMobSpawn(4, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SPEAR_MACHINE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.UNICYCLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.CARVED_VILLAGER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.CarvedSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.CARVED_HOUND.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.CarvedSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.CARVED_IRON_GOLEM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.CarvedSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.CARVED_ALLAY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.CarvedSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.CARVED_LLAMA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.CarvedSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.VIEWER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SECOND_ROUNDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NPCSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.MEROR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NPCSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.WILDERNESS_GIANT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NPCSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.WILDERNESS_GIANT_SKELETON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NPCSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.CHIBAO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NPCSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SERPON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NPCSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.QOAIKU.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.WaterNPCSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SECOND_ROUNDER_ZOMBIE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.WickedRidculeCallsSpawn(32, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SECOND_ROUNDER_SKELETON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.WickedRidculeCallsSpawn(32, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SECOND_ROUNDER_SPIDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.WickedRidculeCallsSpawn(32, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.ENDER_STOWAWAY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.DarkMonsterSpawn(4, 32, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.PROSPEROUS_ENVOY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.BossSpawn(1, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.RESURRECTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.DarkMonsterSpawn(64, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SEDIMENT_GUARD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.DarkMonsterSpawn(32, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SEDIMENT_SOUL_STABILIZER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.DarkMonsterSpawn(16, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.HEAVY_ARMORED_SEDIMENT_GUARD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.DarkMonsterSpawn(16, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SEDIMENT_LORD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.BossSpawn(1, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.BOUND_ZOMBIE_VILLAGER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.DarkMonsterSpawn(32, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.EXPLORER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.LightMonsterSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.EXECUTIONER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.LightMonsterSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.MAPMAKER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.LightMonsterSpawn(4, 64, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.DEFECTOR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.LightMonsterSpawn(64, 32, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.BANNER_BEARER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.LightMonsterSpawn(4, 64, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.BLASTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.LightMonsterSpawn(64, 32, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.TRUMPETER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.LightMonsterSpawn(64, 32, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.TELEPORTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.LightMonsterSpawn(4, 64, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.CYCLONER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.LightMonsterSpawn(64, 32, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.JAVELIN_THROWER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.LightMonsterSpawn(64, 32, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.ZOMBIE_KEEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.LightMonsterSpawn(64, 32, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.BITTER_COLD_SORCERER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.LightMonsterSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.BITTER_COLD_ALTAR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.FIRE_SPITTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.LightMonsterSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.WILD_FINDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.LightMonsterSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SUBMARINER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.LightMonsterSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.LAMP_WIZARD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.LightMonsterSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SLAVERY_SUPERVISOR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.LightMonsterSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.ADVENTURER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.LightMonsterSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.ANIMATED_CHAIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.FIREPOWER_POURER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.LightMonsterSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.NECROMANCY_WARLOCK.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.LightMonsterSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.UNCLEAN_TENTACLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.GAVILER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.LightMonsterSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.AX_CRAZY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.LightMonsterSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.OMINOUS_BANNER_PROJECTION.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.BossSpawn(1, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.BITTER_COD.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.WaterFishSpawn(32, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.GIANT_MONSTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.BIG_WOOL_GEMSBOK.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(32, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.FLUFFMOUND.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.LITTLE_VELVET.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(32, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.MEGANOSE_SNOW_LIZARD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(32, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SNOWY_APE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.DRACOGNATHUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.BITTER_COLD_WIND.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.LightMonsterSpawn(64, 32, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.FROST_YETI.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.EliteSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.PRIMORDIER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.EliteSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.CORROSIVER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.BossSpawn(1, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.PURPLE_SAND_GECKO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.PURPLE_SAND_GECKO_TAIL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SANDSTONE_BEAST.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.PURPLE_SAND_RABBIT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.PURPLE_SAND_CAMEL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.PURPLE_SAND_SCORPION.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.PURPLE_SAND_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.DarkMonsterSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.PURPLE_SAND_WITCH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.DarkMonsterSpawn(4, 128, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.WITCH_SCHOLAR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.DarkMonsterSpawn(16, 128, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.BIG_WITCH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.EliteSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SPIRVE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.DarkMonsterSpawn(32, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.ROTTEN_DOG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(12, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.LIGHTNING_WORM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.EliteSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.DUNESHOCKLER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.EliteSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.COHORT_HAG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.EliteSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.COVEN_HAG_ONE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.EliteSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.COVEN_HAG_TWO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.EliteSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.PURPLE_SAND_HAG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.BossSpawn(1, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.PURPLE_SAND_PHANTOM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.GREYSCALE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.WaterFishSpawn(32, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.JEWELROAMER.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.WaterFishSpawn(32, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.STARBRITE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.WaterFishSpawn(32, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.ABYSSALER.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.WaterAnimalSpawn(4, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SOLISH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.WaterAnimalSpawn(4, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.BENTHICT.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.WaterMobSpawn(8, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.PRANCED_DOLPHIN.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.WaterMobSpawn(8, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.MALIALOSAUR.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.WaterMobSpawn(8, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.MALIGNASAUR.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.WaterMobSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.QOAIKU_SEEKER.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.WaterNPCSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.BULGE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.WaterMobSpawn(4, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.FURY_BULGE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.WaterMobSpawn(4, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SEAWAVE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.EliteSpawn(1, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.FURY_SEAWAVE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.EliteSpawn(1, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.GEMSTONE_MALIGNASAUR.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.BossSpawn(1, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.BRIGHT_GRASS_CARP.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.WaterFishSpawn(32, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.CHROMASNAP_BASS.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.WaterFishSpawn(32, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.LAKE_LOACH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.WaterFishSpawn(32, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.BRIGHT_MOUSE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.AnimalSpawn(32, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.IRISHTAIL_CHICKEN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.AnimalSpawn(32, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SCALY_BEAR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.AnimalSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.RUSHER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.AnimalSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.GLOW_SPARROW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.AnimalSpawn(32, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SOFT_SPIRAL_SHEEP.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.AnimalSpawn(32, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.ARCHNOSED_HORNBEAST.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.ArchnosedHornbeastSpawn(4, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.PIGLIN_RESIDENT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.LightMonsterSpawn(6, 64, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.TANGLER.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.DarkMonsterSpawn(4, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.BRIGHT_LAND_BEAST.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.BossSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SERPON_COLONIAL_SOLDIER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.SerponSpawn(8, 128, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SERPON_PIONEER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NPCSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SERPON_RIOTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NPCSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SERPON_INTRUDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NPCSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SERPON_FLAME_WIZARD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NPCSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SERPON_FIRE_APOSTLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NPCSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.WAR_PYTHON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.ROYAL_WAR_PYTHON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.FLAMOAR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SERPON_CHARIOT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SERPON_CROSSBOW_TURRET.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SERPON_COMBAT_PLATFORM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(4, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.FLYING_FIREBALL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.FURY_FLYING_FIREBALL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.DROUGHT_BLAZE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.FURY_DROUGHT_BLAZE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.FIRE_SECRETOR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.EliteSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.DROUGHT_WILDFIRE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.EliteSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.FURY_DROUGHT_WILDFIRE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.EliteSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SERPON_COMBAT_EXECUTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.BossSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.HARMONIOUS_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.AnimalSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.FLOWMINNOW.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.WaterFishSpawn(32, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.WONDERLAND_YELLOW_DOG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.AnimalSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.WONDERLAND_LI_CAT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.AnimalSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.WONDERLAND_RAMBLER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.AnimalSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.WONDERLAND_GIANT_FROG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.AnimalSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.WONDERLAND_GIANT_FROG_TADPOLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.WaterMobSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.WONDERLAND_BRONCO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.AnimalSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.BOTANIZE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NPCSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.BOTANIZE_ALTAR.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.COILVINE_BOTANIZE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NPCSpawn(2, 64, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.BOTANIZE_ALTAR.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.WONDERLIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NPCSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.WONDERLIN_MERCHANT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NPCSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.WONDERLIN_WARRIOR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NPCSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.THORN_SPIKE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.TYRANT_OF_THE_MOUNTAIN_REALM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.BossSpawn(1, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.COGON_SWORD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.IMPRISON_VINE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.CELESTIAL_COILVINE_FLOWER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.VINE_POD_BOMB.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.VINEHEART_BOMB.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.CELESTIAL_COILVINE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.BossSpawn(1, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.RAMBLER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(32, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.THORNY_TORTOISE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SUN_CRAB.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(32, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.BEACH_TERROR_BIRD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.RUINS_MOUSE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.AnimalSpawn(32, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.RUSTLE_FOX.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.AnimalSpawn(32, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.DUST_MOTH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.AnimalSpawn(32, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.CLEANING_ROBOT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.RUINS_BREEZE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.LightMonsterSpawn(4, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.MEROR_ADVENTURER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NPCSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.MEROR_MERCHANT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NPCSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.MEROR_BOUNTY_HUNTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NPCSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.MEROR_LOCATOR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(1, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.MEROR_CUBE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(64, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.MEROR_MINISTROSITY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(64, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.MEROR_OATHGUARD_MECH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(64, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.MEROR_GOLEM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.EliteSpawn(4, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.MEROR_MONSTROSITY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.EliteSpawn(2, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.MEROR_CHAMPION.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.EliteSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SECOND_ROUNDER_GOLEM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.BossSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SECOND_ROUNDER_GOLEM_SWORD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.BossSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.DEEP_BAT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.BatSpawn(8, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.RUST_BEETLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.FLOWING_DUST.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.DarkMonsterSpawn(32, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.BLAMER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.DarkMonsterSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.IRON_TEETH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.FURY_IRON_TEETH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SECOND_ROUNDER_ANIMATED_ARMOR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(4, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.FURY_SECOND_ROUNDER_ANIMATED_ARMOR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(4, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.EYE_DISC.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.DarkMonsterSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.DARISORDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.DarisorderSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.FURY_DARISORDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.DarisorderSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.DOWNY_SPIDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(32, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.VIRTUAL_CAVE_COCKROACH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(32, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.BLUE_SHIMMER_SQUID.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.GlowSquidSpawn(2, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.VIRTUAL_CAVE_SPIDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.DarkMonsterSpawn(4, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SLIDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.LightMonsterSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.PREDATION_TOOTHED_FLOWER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.LightMonsterSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.WILDERNESS_WOLF.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.AnimalSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.WILDERNESS_RAMBLER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.WILDERNESS_BULL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.CLOUD_FUR_DEER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.BROWN_VENOMOUS_SLUG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(16, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.WILDERNESS_GIANT_TRAMP.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NPCSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.WILDERNESS_GIANT_HUNTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NPCSpawn(4, 64, arg_0, arg_1, arg_2, arg_3, arg_4) &&
						SpawnRules.BlockSpawn(64, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get(), arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.WOODLAND_HEART_GUARDIAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NPCSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.SCAVENGER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(64, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.BLOODSUCKER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(64, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.RIGIDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.NeutralSpawn(64, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		SpawnPlacements.register(JerotesVillageEntityType.VARIANT_ZSIEIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
				SpawnRules.BossSpawn(1, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
	}
}