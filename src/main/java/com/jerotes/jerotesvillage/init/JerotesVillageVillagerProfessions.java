package com.jerotes.jerotesvillage.init;

import com.google.common.collect.ImmutableSet;
import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class JerotesVillageVillagerProfessions {
	private static final Map<String, ProfessionPoiType> POI_TYPES = new HashMap<>();
	public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, JerotesVillage.MODID);
	public static final RegistryObject<VillagerProfession> VILLAGER_WITCH = registerProfession("villager_witch", JerotesVillageBlocks.VILLAGER_BLOCK_WITCH,
			() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_cleric")));
	public static final RegistryObject<VillagerProfession> VILLAGER_OBSERVER = registerProfession("villager_observer", JerotesVillageBlocks.VILLAGER_BLOCK_OBSERVER,
			() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_librarian")));
	public static final RegistryObject<VillagerProfession> VILLAGER_SHOPKEEPER = registerProfession("villager_shopkeeper", JerotesVillageBlocks.VILLAGER_BLOCK_SHOPKEEPER,
			() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_cartographer")));
	public static final RegistryObject<VillagerProfession> VILLAGER_VILLAGEHEAD = registerProfession("villager_villagehead", JerotesVillageBlocks.VILLAGER_BLOCK_VILLAGEHEAD,
			() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_cartographer")));
	public static final RegistryObject<VillagerProfession> VILLAGER_FORGER = registerProfession("villager_forger", JerotesVillageBlocks.VILLAGER_BLOCK_FORGER,
			() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_weaponsmith")));
	public static final RegistryObject<VillagerProfession> VILLAGER_CHEF = registerProfession("villager_chef", JerotesVillageBlocks.VILLAGER_BLOCK_CHEF,
			() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_butcher")));
	public static final RegistryObject<VillagerProfession> VILLAGER_HUNTER = registerProfession("villager_hunter", JerotesVillageBlocks.VILLAGER_BLOCK_HUNTER,
			() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_butcher")));
	public static final RegistryObject<VillagerProfession> VILLAGER_PHYSICIAN = registerProfession("villager_physician", JerotesVillageBlocks.VILLAGER_BLOCK_PHYSICIAN,
			() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_cleric")));
	public static final RegistryObject<VillagerProfession> VILLAGER_CONSCRIPTION = registerProfession("villager_conscription", JerotesVillageBlocks.VILLAGER_BLOCK_CONSCRIPTION,
			() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_cleric")));
	public static final RegistryObject<VillagerProfession> VILLAGER_BARTENDER = registerProfession("villager_bartender", JerotesVillageBlocks.VILLAGER_BLOCK_BARTENDER,
			() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_fisherman")));
	public static final RegistryObject<VillagerProfession> VILLAGER_LAMPLIGHTER = registerProfession("villager_lamplighter", JerotesVillageBlocks.VILLAGER_BLOCK_LAMPLIGHTER,
			() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_toolsmith")));
	public static final RegistryObject<VillagerProfession> VILLAGER_HORSEMAN = registerProfession("villager_horseman", JerotesVillageBlocks.VILLAGER_BLOCK_HORSEMAN,
			() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_farmer")));
	public static final RegistryObject<VillagerProfession> VILLAGER_COACHMAN = registerProfession("villager_coachman", JerotesVillageBlocks.VILLAGER_BLOCK_COACHMAN,
			() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_leatherworker")));
	public static final RegistryObject<VillagerProfession> VILLAGER_BUILDER = registerProfession("villager_builder", JerotesVillageBlocks.VILLAGER_BLOCK_BUILDER,
			() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_toolsmith")));
	public static final RegistryObject<VillagerProfession> VILLAGER_BREEDER = registerProfession("villager_breeder", JerotesVillageBlocks.VILLAGER_BLOCK_BREEDER,
			() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_farmer")));
	public static final RegistryObject<VillagerProfession> VILLAGER_HAWKER = registerProfession("villager_hawker", JerotesVillageBlocks.VILLAGER_BLOCK_HAWKER,
			() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_farmer")));
	public static final RegistryObject<VillagerProfession> VILLAGER_JAILER = registerProfession("villager_jailer", JerotesVillageBlocks.VILLAGER_BLOCK_JAILER,
			() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_weaponsmith")));
	public static final RegistryObject<VillagerProfession> VILLAGER_PISCATOR = registerProfession("villager_piscator", JerotesVillageBlocks.VILLAGER_BLOCK_PISCATOR,
			() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_fisherman")));
	public static final RegistryObject<VillagerProfession> VILLAGER_MAGICIAN = registerProfession("villager_magician", JerotesVillageBlocks.VILLAGER_BLOCK_MAGICIAN,
			() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_cleric")));
	public static final RegistryObject<VillagerProfession> VILLAGER_CAPTAIN = registerProfession("villager_captain", JerotesVillageBlocks.VILLAGER_BLOCK_CAPTAIN,
			() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_fisherman")));
	public static final RegistryObject<VillagerProfession> VILLAGER_MESSENGER = registerProfession("villager_messenger", JerotesVillageBlocks.VILLAGER_BLOCK_MESSENGER,
			() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_cartographer")));
	public static final RegistryObject<VillagerProfession> VILLAGER_DYE_MERCHANT = registerProfession("villager_dye_merchant", JerotesVillageBlocks.VILLAGER_BLOCK_DYE_MERCHANT,
			() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_leatherworker")));
	public static final RegistryObject<VillagerProfession> VILLAGER_ILLAGER = registerProfession("villager_illager", JerotesVillageBlocks.VILLAGER_BLOCK_ILLAGER,
			() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_leatherworker")));
	public static final RegistryObject<VillagerProfession> VILLAGER_PIGLIN = registerProfession("villager_piglin", JerotesVillageBlocks.VILLAGER_BLOCK_PIGLIN,
			() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_leatherworker")));
	public static final RegistryObject<VillagerProfession> VILLAGER_BEEKEEPER = registerProfession("villager_beekeeper", JerotesVillageBlocks.VILLAGER_BLOCK_BEEKEEPER,
			() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_farmer")));

	private static RegistryObject<VillagerProfession> registerProfession(String name, Supplier<Block> block, Supplier<SoundEvent> soundEvent) {
		POI_TYPES.put(name, new ProfessionPoiType(block, null));
		return PROFESSIONS.register(name, () -> {
			Predicate<Holder<PoiType>> poiPredicate = poiTypeHolder -> (POI_TYPES.get(name).poiType != null) && (poiTypeHolder.get() == POI_TYPES.get(name).poiType.get());
			return new VillagerProfession(JerotesVillage.MODID + ":" + name, poiPredicate, poiPredicate, ImmutableSet.of(), ImmutableSet.of(), soundEvent.get());
		});
	}

	@SubscribeEvent
	public static void registerProfessionPointsOfInterest(RegisterEvent event) {
		event.register(ForgeRegistries.Keys.POI_TYPES, registerHelper -> {
			for (Map.Entry<String, ProfessionPoiType> entry : POI_TYPES.entrySet()) {
				String name = entry.getKey();
				String blockName = "villager_block_" + name.substring("villager_".length());
				Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JerotesVillage.MODID, blockName));
				if (block == null) {
					throw new IllegalStateException("Block not found: " + blockName);
				}
				Optional<Holder<PoiType>> existingCheck = PoiTypes.forState(block.defaultBlockState());
				if (existingCheck.isPresent()) {
					continue;
				}
				PoiType poiType = new PoiType(ImmutableSet.copyOf(block.getStateDefinition().getPossibleStates()), 1, 1);
				registerHelper.register(name, poiType);
				entry.getValue().poiType = ForgeRegistries.POI_TYPES.getHolder(poiType).get();
			}
		});
	}

	private static class ProfessionPoiType {
		final Supplier<Block> block;
		Holder<PoiType> poiType;

		ProfessionPoiType(Supplier<Block> block, Holder<PoiType> poiType) {
			this.block = block;
			this.poiType = poiType;
		}
	}
}
