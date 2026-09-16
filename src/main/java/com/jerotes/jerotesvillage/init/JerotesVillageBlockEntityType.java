package com.jerotes.jerotesvillage.init;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.block.BaseBlock.ABaseBlock;
import com.jerotes.jerotesvillage.block.BitterCold.TorchOfFirmIceEntity;
import com.jerotes.jerotesvillage.block.DamagedRuins.MerorProjectionTableEntity;
import com.jerotes.jerotesvillage.block.EndlessOcean.GreyscalePrizeMachineEntity;
import com.jerotes.jerotesvillage.block.HarmoniousWonderland.BotanizeAltarEntity;
import com.jerotes.jerotesvillage.block.HarmoniousWonderland.TyrantOfTheMountainRealmStatueBlockEntity;
import com.jerotes.jerotesvillage.block.Other.TabletPressMachineEntity;
import com.jerotes.jerotesvillage.block.Other.VillagerLetterBoxEntity;
import com.jerotes.jerotesvillage.block.ResurrectSediment.SedimentUrnEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class JerotesVillageBlockEntityType {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, JerotesVillage.MODID);

	public static final RegistryObject<BlockEntityType<ABaseBlock.ABaseBrushableBlockEntity>> ABASE_BRUSHABLE_BLOCK = REGISTRY.register("abase_brushable_block",
			() -> BlockEntityType.Builder.of(ABaseBlock.ABaseBrushableBlockEntity::new, JerotesVillageBlocks.SUSPICIOUS_SUN_SAND.get()).build(null));

	public static final RegistryObject<BlockEntityType<ABaseBlock.ABaseSkullBlockEntity>> ABASE_SKULL_BLOCK = REGISTRY.register("abase_skull_block",
			() -> BlockEntityType.Builder.of(ABaseBlock.ABaseSkullBlockEntity::new,
					JerotesVillageBlocks.SECOND_ROUNDER_ZOMBIE_HEAD.get(), JerotesVillageBlocks.SECOND_ROUNDER_ZOMBIE_WALL_HEAD.get(),
					JerotesVillageBlocks.SECOND_ROUNDER_SKELETON_SKULL.get(), JerotesVillageBlocks.SECOND_ROUNDER_SKELETON_WALL_SKULL.get(),
					JerotesVillageBlocks.RESURRECTER_HEAD.get(), JerotesVillageBlocks.RESURRECTER_WALL_HEAD.get(),
					JerotesVillageBlocks.SPIRVE_HEAD.get(), JerotesVillageBlocks.SPIRVE_WALL_HEAD.get(),
					JerotesVillageBlocks.PURPLE_SAND_CREEPER_HEAD.get(), JerotesVillageBlocks.PURPLE_SAND_CREEPER_WALL_HEAD.get(),
					JerotesVillageBlocks.TANGLER_HEAD.get(), JerotesVillageBlocks.TANGLER_WALL_HEAD.get(),
					JerotesVillageBlocks.BLAMER_HEAD.get(), JerotesVillageBlocks.BLAMER_WALL_HEAD.get()
			).build(null));


	public static final RegistryObject<BlockEntityType<VillagerLetterBoxEntity>> VILLAGER_LETTER_BOX = REGISTRY.register("villager_letter_box",
			() -> BlockEntityType.Builder.of(VillagerLetterBoxEntity::new, JerotesVillageBlocks.VILLAGER_BLOCK_MESSENGER.get()).build(null));

	public static final RegistryObject<BlockEntityType<TabletPressMachineEntity>> TABLET_PRESS_MACHINE = REGISTRY.register("tablet_press_machine",
			() -> BlockEntityType.Builder.of(TabletPressMachineEntity::new, JerotesVillageBlocks.VILLAGER_BLOCK_PHYSICIAN.get()).build(null));

	public static final RegistryObject<BlockEntityType<BotanizeAltarEntity>> BOTANIZE_ALTAR = REGISTRY.register("botanize_altar",
			() -> BlockEntityType.Builder.of(BotanizeAltarEntity::new, JerotesVillageBlocks.BOTANIZE_ALTAR.get()).build(null));

	public static final RegistryObject<BlockEntityType<SedimentUrnEntity>> SEDIMENT_URN = REGISTRY.register("sediment_urn",
			() -> BlockEntityType.Builder.of(SedimentUrnEntity::new, JerotesVillageBlocks.SEDIMENT_URN.get()).build(null));

	public static final RegistryObject<BlockEntityType<MerorProjectionTableEntity>> MEROR_PROJECTION_TABLE = REGISTRY.register("meror_projection_table",
			() -> BlockEntityType.Builder.of(MerorProjectionTableEntity::new, JerotesVillageBlocks.MEROR_PROJECTION_TABLE.get()).build(null));

	public static final RegistryObject<BlockEntityType<TorchOfFirmIceEntity>> TORCH_OF_FIRM_ICE = REGISTRY.register("torch_of_firm_ice",
			() -> BlockEntityType.Builder.of(TorchOfFirmIceEntity::new, JerotesVillageBlocks.TORCH_OF_FIRM_ICE.get()).build(null));

	public static final RegistryObject<BlockEntityType<GreyscalePrizeMachineEntity>> GREYSCALE_PRIZE_MACHINE = REGISTRY.register("greyscale_prize_machine",
			() -> BlockEntityType.Builder.of(GreyscalePrizeMachineEntity::new, JerotesVillageBlocks.GREYSCALE_PRIZE_MACHINE.get()).build(null));

	public static final RegistryObject<BlockEntityType<TyrantOfTheMountainRealmStatueBlockEntity>> TYRANT_OF_THE_MOUNTAIN_REALM_STATUE = REGISTRY.register("tyrant_of_the_mountain_realm_statue",
			() -> BlockEntityType.Builder.of(TyrantOfTheMountainRealmStatueBlockEntity::new, JerotesVillageBlocks.TYRANT_OF_THE_MOUNTAIN_REALM_STATUE.get()).build(null));

	public static final RegistryObject<BlockEntityType<ABaseBlock.ABaseSignEntity>> ABASE_SIGN = REGISTRY.register("abase_sign",
			() -> BlockEntityType.Builder.of(ABaseBlock.ABaseSignEntity::new,
					JerotesVillageBlocks.BITTER_COLD_SIGN.get(), JerotesVillageBlocks.BITTER_COLD_WALL_SIGN.get(),
					JerotesVillageBlocks.BRIGHT_GLOW_SIGN.get(), JerotesVillageBlocks.BRIGHT_GLOW_WALL_SIGN.get(),
					JerotesVillageBlocks.WONDERLAND_SIGN.get(), JerotesVillageBlocks.WONDERLAND_WALL_SIGN.get(),
					JerotesVillageBlocks.BLACK_YELLOW_SIGN.get(), JerotesVillageBlocks.BLACK_YELLOW_WALL_SIGN.get()
			).build(null));

	public static final RegistryObject<BlockEntityType<ABaseBlock.ABaseHangingSignEntity>> ABASE_HANGING_SIGN = REGISTRY.register("abase_hanging_sign",
			() -> BlockEntityType.Builder.of(ABaseBlock.ABaseHangingSignEntity::new,
					JerotesVillageBlocks.BITTER_COLD_HANGING_SIGN.get(), JerotesVillageBlocks.BITTER_COLD_WALL_HANGING_SIGN.get(),
					JerotesVillageBlocks.BRIGHT_GLOW_HANGING_SIGN.get(), JerotesVillageBlocks.BRIGHT_GLOW_WALL_HANGING_SIGN.get(),
					JerotesVillageBlocks.WONDERLAND_HANGING_SIGN.get(), JerotesVillageBlocks.WONDERLAND_WALL_HANGING_SIGN.get(),
					JerotesVillageBlocks.BLACK_YELLOW_HANGING_SIGN.get(), JerotesVillageBlocks.BLACK_YELLOW_WALL_HANGING_SIGN.get()
			).build(null));


}
