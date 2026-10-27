package com.jerotes.jerotesvillage.init;

import com.jerotes.jerotes.entity.Part.BasePartLivingEntity;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Animal.*;
import com.jerotes.jerotesvillage.entity.Animal.Fish.AbyssalerEntity;
import com.jerotes.jerotesvillage.entity.Animal.Fish.BenthicFish.*;
import com.jerotes.jerotesvillage.entity.Animal.Fish.SolishEntity;
import com.jerotes.jerotesvillage.entity.Boss.*;
import com.jerotes.jerotesvillage.entity.Boss.Biome.*;
import com.jerotes.jerotesvillage.entity.Humanoid.CarvedVillagerEntity;
import com.jerotes.jerotesvillage.entity.Humanoid.ChibaoEntity;
import com.jerotes.jerotesvillage.entity.Humanoid.Meror.MerorAdventurerEntity;
import com.jerotes.jerotesvillage.entity.Humanoid.Meror.MerorBountyHunterEntity;
import com.jerotes.jerotesvillage.entity.Humanoid.Meror.MerorEntity;
import com.jerotes.jerotesvillage.entity.Humanoid.Meror.MerorMerchantEntity;
import com.jerotes.jerotesvillage.entity.Humanoid.Qoaiku.QoaikuEntity;
import com.jerotes.jerotesvillage.entity.Humanoid.Qoaiku.QoaikuSeekerEntity;
import com.jerotes.jerotesvillage.entity.Humanoid.SecondRounderEntity;
import com.jerotes.jerotesvillage.entity.Humanoid.Serpon.*;
import com.jerotes.jerotesvillage.entity.Humanoid.WildernessGiant.WildernessGiantEntity;
import com.jerotes.jerotesvillage.entity.Humanoid.WildernessGiant.WildernessGiantHunterEntity;
import com.jerotes.jerotesvillage.entity.Humanoid.WildernessGiant.WildernessGiantTrampEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Botanize.BotanizeEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Botanize.CoilvineBotanizeEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Bulge.BulgeEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Bulge.FuryBulgeEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Bulge.FurySeawaveEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Bulge.SeawaveEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Darisorder.DarisorderEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Darisorder.FuryDarisorderEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.DroughtBlaze.DroughtBlazeEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.DroughtBlaze.DroughtWildfireEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.DroughtBlaze.FuryDroughtBlazeEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.DroughtBlaze.FuryDroughtWildfireEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.FlyingFireball.FlyingFireballEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.FlyingFireball.FuryFlyingFireballEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.IronTeeth.FuryIronTeethEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.IronTeeth.IronTeethEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.SecondRounderAnimatedArmor.FurySecondRounderAnimatedArmorEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.SecondRounderAnimatedArmor.SecondRounderAnimatedArmorEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.VinePodBomb.VinePodBombEntity;
import com.jerotes.jerotesvillage.entity.Monster.*;
import com.jerotes.jerotesvillage.entity.Monster.Elite.*;
import com.jerotes.jerotesvillage.entity.Monster.Ender.EnderStowawayEntity;
import com.jerotes.jerotesvillage.entity.Monster.Hag.CohortHagEntity;
import com.jerotes.jerotesvillage.entity.Monster.Hag.CovenHagEntity;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.*;
import com.jerotes.jerotesvillage.entity.Monster.Piglin.PiglinResidentEntity;
import com.jerotes.jerotesvillage.entity.Monster.Resurrecter.HeavyArmoredSedimentGuardEntity;
import com.jerotes.jerotesvillage.entity.Monster.Resurrecter.ResurrecterEntity;
import com.jerotes.jerotesvillage.entity.Monster.Resurrecter.SedimentGuardEntity;
import com.jerotes.jerotesvillage.entity.Monster.Resurrecter.SedimentSoulStabilizerEntity;
import com.jerotes.jerotesvillage.entity.Monster.WickedRidiculeCalls.SecondRounderSkeletonEntity;
import com.jerotes.jerotesvillage.entity.Monster.WickedRidiculeCalls.SecondRounderSpiderEntity;
import com.jerotes.jerotesvillage.entity.Monster.WickedRidiculeCalls.SecondRounderZombieEntity;
import com.jerotes.jerotesvillage.entity.Neutral.*;
import com.jerotes.jerotesvillage.entity.Neutral.MerorMachine.*;
import com.jerotes.jerotesvillage.entity.Neutral.WildernessGiantSkeleton.WildernessGiantSkeletonEntity;
import com.jerotes.jerotesvillage.entity.Neutral.WildernessGiantSkeleton.WoodlandHeartGuardianEntity;
import com.jerotes.jerotesvillage.entity.Neutral.Wonderlin.WonderlinEntity;
import com.jerotes.jerotesvillage.entity.Neutral.Wonderlin.WonderlinMerchantEntity;
import com.jerotes.jerotesvillage.entity.Neutral.Wonderlin.WonderlinWarriorEntity;
import com.jerotes.jerotesvillage.entity.Neutral.Zsiein.BloodsuckerEntity;
import com.jerotes.jerotesvillage.entity.Neutral.Zsiein.RigiderEntity;
import com.jerotes.jerotesvillage.entity.Neutral.Zsiein.ScavengerEntity;
import com.jerotes.jerotesvillage.entity.Other.Boat.ABaseBoatEntity;
import com.jerotes.jerotesvillage.entity.Other.Boat.ABaseChestBoatEntity;
import com.jerotes.jerotesvillage.entity.Other.BreathAreaSpell.BloodyScreamAreaEntity;
import com.jerotes.jerotesvillage.entity.Other.BreathAreaSpell.CorrosiverBreathAreaEntity;
import com.jerotes.jerotesvillage.entity.Other.BreathAreaSpell.OceanCurrentBreathAreaEntity;
import com.jerotes.jerotesvillage.entity.Other.BreathAreaSpell.VirosaansWindOfSlaughterEntity;
import com.jerotes.jerotesvillage.entity.Other.Machine.SerponChariotEntity;
import com.jerotes.jerotesvillage.entity.Other.Machine.SerponCombatPlatformEntity;
import com.jerotes.jerotesvillage.entity.Other.Machine.SerponCrossbowTurretEntity;
import com.jerotes.jerotesvillage.entity.Other.MagicAboutMob.*;
import com.jerotes.jerotesvillage.entity.Other.Other.BossShowEntity;
import com.jerotes.jerotesvillage.entity.Other.Other.PrimedSerponClassicalBombEntity;
import com.jerotes.jerotesvillage.entity.Other.Other.PurpleSandGeckoTailEntity;
import com.jerotes.jerotesvillage.entity.Other.Other.ViewerEntity;
import com.jerotes.jerotesvillage.entity.Other.OtherSpell.*;
import com.jerotes.jerotesvillage.entity.Other.ShootTargetSpell.SerpenperthMeteorEntity;
import com.jerotes.jerotesvillage.entity.Other.ShootTargetSpell.TakeRootEntity;
import com.jerotes.jerotesvillage.entity.Other.ShootTargetSpell.VirosaansForceBurstBallEntity;
import com.jerotes.jerotesvillage.entity.Other.WonderlinPainting.WonderlinPaintingEntity;
import com.jerotes.jerotesvillage.entity.Part.BitterColdAltarPart;
import com.jerotes.jerotesvillage.entity.Part.DuneshocklerPart;
import com.jerotes.jerotesvillage.entity.Part.ImprisonVinePart;
import com.jerotes.jerotesvillage.entity.Part.WarPythonBasePartLivingEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.*;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicThrow.VineboltEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.Breath.*;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.Cloud.CorrosiveSnowCloudEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.Cloud.RainEffectCloudEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.Cloud.UncleanBloodRainEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicBeam.ElectroflashEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicBeam.SkylanceDragonBeamEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicMissile.ArcaneLightSpotEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicMissile.SedimentMissileEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicMissile.VertigoDelugeEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicMissile.WiltMissileEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicShoot.*;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicSpray.BitterColdFrostbiteEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicSpray.OminousFlamesEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicSpray.SerponFlamesEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicSpray.VirtualCaveSurgeSiphonEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicThrow.*;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.Ray.*;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.Target.DeadCenterEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.Target.FloatingForceEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.Target.GravityForceEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.Target.IndomitableWillEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Other.*;
import com.jerotes.jerotesvillage.entity.Shoot.ThrowingBall.*;
import com.jerotes.jerotesvillage.entity.Smart.SmartBulgeEntity;
import com.jerotes.jerotesvillage.entity.Smart.SmartEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class JerotesVillageEntityType {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, JerotesVillage.MODID);
	public static final RegistryObject<EntityType<SmartEntity>> SMART = register("smart",
			EntityType.Builder.of(SmartEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<SmartBulgeEntity>> SMART_BULGE = register("smart_bulge",
			EntityType.Builder.of(SmartBulgeEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.6f, 1.5f));
	public static final RegistryObject<EntityType<SpearMachineEntity>> SPEAR_MACHINE = register("spear_machine",
			EntityType.Builder.of(SpearMachineEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).immuneTo(Blocks.POWDER_SNOW)
					.sized(0.6f, 1.1f));
	public static final RegistryObject<EntityType<UnicycleEntity>> UNICYCLE = register("unicycle",
			EntityType.Builder.of(UnicycleEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).immuneTo(Blocks.POWDER_SNOW)
					.sized(0.6f, 1.7f));
	public static final RegistryObject<EntityType<CarvedVillagerEntity>> CARVED_VILLAGER = register("carved_villager",
			EntityType.Builder.of(CarvedVillagerEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<CarvedHoundEntity>> CARVED_HOUND = register("carved_hound",
			EntityType.Builder.of(CarvedHoundEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.6f, 0.85f));
	public static final RegistryObject<EntityType<CarvedIronGolemEntity>> CARVED_IRON_GOLEM = register("carved_iron_golem",
			EntityType.Builder.of(CarvedIronGolemEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(1.4f, 2.7f));
	public static final RegistryObject<EntityType<CarvedAllayEntity>> CARVED_ALLAY = register("carved_allay",
			EntityType.Builder.of(CarvedAllayEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.35f, 0.6f));
	public static final RegistryObject<EntityType<CarvedLlamaEntity>> CARVED_LLAMA = register("carved_llama",
			EntityType.Builder.of(CarvedLlamaEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.9f, 1.87f));
	public static final RegistryObject<EntityType<ViewerEntity>> VIEWER = register("viewer",
			EntityType.Builder.of(ViewerEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<SecondRounderEntity>> SECOND_ROUNDER = register("second_rounder",
			EntityType.Builder.of(SecondRounderEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.65f, 2.15f));
	public static final RegistryObject<EntityType<MerorEntity>> MEROR = register("meror",
			EntityType.Builder.of(MerorEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.895f));
	public static final RegistryObject<EntityType<WildernessGiantEntity>> WILDERNESS_GIANT = register("wilderness_giant",
			EntityType.Builder.of(WildernessGiantEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(1.05f, 3.4f));
	public static final RegistryObject<EntityType<WildernessGiantSkeletonEntity>> WILDERNESS_GIANT_SKELETON = register("wilderness_giant_skeleton",
			EntityType.Builder.of(WildernessGiantSkeletonEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(1.05f, 3.4f));
	public static final RegistryObject<EntityType<ChibaoEntity>> CHIBAO = register("chibao",
			EntityType.Builder.of(ChibaoEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.9f, 2f));
	public static final RegistryObject<EntityType<SerponEntity>> SERPON = register("serpon",
			EntityType.Builder.of(SerponEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune()
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<QoaikuEntity>> QOAIKU = register("qoaiku",
			EntityType.Builder.of(QoaikuEntity::new, MobCategory.WATER_CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<SecondRounderZombieEntity>> SECOND_ROUNDER_ZOMBIE = register("second_rounder_zombie",
			EntityType.Builder.of(SecondRounderZombieEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.65f, 1.95f));
	public static final RegistryObject<EntityType<SecondRounderSkeletonEntity>> SECOND_ROUNDER_SKELETON = register("second_rounder_skeleton",
			EntityType.Builder.of(SecondRounderSkeletonEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.65f, 1.95f));
	public static final RegistryObject<EntityType<SecondRounderSpiderEntity>> SECOND_ROUNDER_SPIDER = register("second_rounder_spider",
			EntityType.Builder.of(SecondRounderSpiderEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(1.68f, 0.9f));
	public static final RegistryObject<EntityType<EnderStowawayEntity>> ENDER_STOWAWAY = register("ender_stowaway",
			EntityType.Builder.of(EnderStowawayEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.6f, 2.9f));
	public static final RegistryObject<EntityType<ProsperousEnvoyEntity>> PROSPEROUS_ENVOY = register("prosperous_envoy",
			EntityType.Builder.of(ProsperousEnvoyEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(2.25f, 2.2f));
	public static final RegistryObject<EntityType<ResurrecterEntity>> RESURRECTER = register("resurrecter",
			EntityType.Builder.of(ResurrecterEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.65f, 1.95f));
	public static final RegistryObject<EntityType<SedimentGuardEntity>> SEDIMENT_GUARD = register("sediment_guard",
			EntityType.Builder.of(SedimentGuardEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.65f, 1.95f));
	public static final RegistryObject<EntityType<SedimentSoulStabilizerEntity>> SEDIMENT_SOUL_STABILIZER = register("sediment_soul_stabilizer",
			EntityType.Builder.of(SedimentSoulStabilizerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.65f, 1.95f));
	public static final RegistryObject<EntityType<HeavyArmoredSedimentGuardEntity>> HEAVY_ARMORED_SEDIMENT_GUARD = register("heavy_armored_sediment_guard",
			EntityType.Builder.of(HeavyArmoredSedimentGuardEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.65f, 1.95f));
	public static final RegistryObject<EntityType<SedimentLordEntity>> SEDIMENT_LORD = register("sediment_lord",
			EntityType.Builder.of(SedimentLordEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.65f, 1.95f));
	public static final RegistryObject<EntityType<BoundZombieVillagerEntity>> BOUND_ZOMBIE_VILLAGER = register("bound_zombie_villager",
			EntityType.Builder.of(BoundZombieVillagerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<ExplorerEntity>> EXPLORER = register("explorer",
			EntityType.Builder.of(ExplorerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<ExecutionerEntity>> EXECUTIONER = register("executioner",
			EntityType.Builder.of(ExecutionerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<MapmakerEntity>> MAPMAKER = register("mapmaker",
			EntityType.Builder.of(MapmakerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<DefectorEntity>> DEFECTOR = register("defector",
			EntityType.Builder.of(DefectorEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<BannerBearerEntity>> BANNER_BEARER = register("banner_bearer",
			EntityType.Builder.of(BannerBearerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<BlasterEntity>> BLASTER = register("blaster",
			EntityType.Builder.of(BlasterEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<TrumpeterEntity>> TRUMPETER = register("trumpeter",
			EntityType.Builder.of(TrumpeterEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<TeleporterEntity>> TELEPORTER = register("teleporter",
			EntityType.Builder.of(TeleporterEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<CyclonerEntity>> CYCLONER = register("cycloner",
			EntityType.Builder.of(CyclonerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<JavelinThrowerEntity>> JAVELIN_THROWER = register("javelin_thrower",
			EntityType.Builder.of(JavelinThrowerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<ZombieKeeperEntity>> ZOMBIE_KEEPER = register("zombie_keeper",
			EntityType.Builder.of(ZombieKeeperEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<BitterColdSorcererEntity>> BITTER_COLD_SORCERER = register("bitter_cold_sorcerer",
			EntityType.Builder.of(BitterColdSorcererEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).immuneTo(Blocks.POWDER_SNOW)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<BitterColdAltarEntity>> BITTER_COLD_ALTAR = register("bitter_cold_altar",
			EntityType.Builder.of(BitterColdAltarEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(10).immuneTo(Blocks.POWDER_SNOW)
					.sized(6, 0.5f));
	public static final RegistryObject<EntityType<BitterColdAltarPart>> BITTER_COLD_ALTAR_PART = register("bitter_cold_altar_part",
			EntityType.Builder.<BitterColdAltarPart>of(BitterColdAltarPart::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(1f, 3.5f));
	public static final RegistryObject<EntityType<FireSpitterEntity>> FIRE_SPITTER = register("fire_spitter",
			EntityType.Builder.of(FireSpitterEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<WildFinderEntity>> WILD_FINDER = register("wild_finder",
			EntityType.Builder.of(WildFinderEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<SubmarinerEntity>> SUBMARINER = register("submariner",
			EntityType.Builder.of(SubmarinerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<LampWizardEntity>> LAMP_WIZARD = register("lamp_wizard",
			EntityType.Builder.of(LampWizardEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<SlaverySupervisorEntity>> SLAVERY_SUPERVISOR = register("slavery_supervisor",
			EntityType.Builder.of(SlaverySupervisorEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<AnimatedChainEntity>> ANIMATED_CHAIN = register("animated_chain",
			EntityType.Builder.of(AnimatedChainEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(10).immuneTo(Blocks.POWDER_SNOW)
					.sized(6, 0.5f));
	public static final RegistryObject<EntityType<FirepowerPourerEntity>> FIREPOWER_POURER = register("firepower_pourer",
			EntityType.Builder.of(FirepowerPourerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).immuneTo(Blocks.POWDER_SNOW)
					.sized(2f, 3.8f));
	public static final RegistryObject<EntityType<NecromancyWarlockEntity>> NECROMANCY_WARLOCK = register("necromancy_warlock",
			EntityType.Builder.of(NecromancyWarlockEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<UncleanTentacleEntity>> UNCLEAN_TENTACLE = register("unclean_tentacle",
			EntityType.Builder.of(UncleanTentacleEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.5f, 3f));
	public static final RegistryObject<EntityType<GavilerEntity>> GAVILER = register("gaviler",
			EntityType.Builder.of(GavilerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<AxCrazyEntity>> AX_CRAZY = register("ax_crazy",
			EntityType.Builder.of(AxCrazyEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<AdventurerEntity>> ADVENTURER = register("adventurer",
			EntityType.Builder.of(AdventurerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<OminousBannerProjectionEntity>> OMINOUS_BANNER_PROJECTION = register("ominous_banner_projection",
			EntityType.Builder.of(OminousBannerProjectionEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).immuneTo(Blocks.POWDER_SNOW)
					.sized(0.75f, 5.2f));
	public static final RegistryObject<EntityType<BitterCodEntity>> BITTER_COD = register("bitter_cod",
			EntityType.Builder.of(BitterCodEntity::new, MobCategory.WATER_AMBIENT).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.7f, 0.35f));
	public static final RegistryObject<EntityType<GiantMonsterEntity>> GIANT_MONSTER = register("giant_monster",
			EntityType.Builder.of(GiantMonsterEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).immuneTo(Blocks.POWDER_SNOW)
					.sized(2.95f, 3.1f));
	public static final RegistryObject<EntityType<BigWoolGemsbokEntity>> BIG_WOOL_GEMSBOK = register("big_wool_gemsbok",
			EntityType.Builder.of(BigWoolGemsbokEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10).immuneTo(Blocks.POWDER_SNOW)
					.sized(1.6f, 2.05f));
	public static final RegistryObject<EntityType<FluffmoundEntity>> FLUFFMOUND = register("fluffmound",
			EntityType.Builder.of(FluffmoundEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).immuneTo(Blocks.POWDER_SNOW)
					.sized(3.5f, 3.75f));
	public static final RegistryObject<EntityType<LittleVelvetEntity>> LITTLE_VELVET = register("little_velvet",
			EntityType.Builder.of(LittleVelvetEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10).immuneTo(Blocks.POWDER_SNOW)
					.sized(0.6f, 0.5f));
	public static final RegistryObject<EntityType<MeganoseSnowLizardEntity>> MEGANOSE_SNOW_LIZARD = register("meganose_snow_lizard",
			EntityType.Builder.of(MeganoseSnowLizardEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).immuneTo(Blocks.POWDER_SNOW)
					.sized(1.8f, 1.5f));
	public static final RegistryObject<EntityType<SnowyApeEntity>> SNOWY_APE = register("snowy_ape",
			EntityType.Builder.of(SnowyApeEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).immuneTo(Blocks.POWDER_SNOW)
					.sized(1.2f, 2.6f));
	public static final RegistryObject<EntityType<DracognathusEntity>> DRACOGNATHUS = register("dracognathus",
			EntityType.Builder.of(DracognathusEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).immuneTo(Blocks.POWDER_SNOW)
					.sized(1.7f, 1.6f));
	public static final RegistryObject<EntityType<BitterColdWindEntity>> BITTER_COLD_WIND = register("bitter_cold_wind",
			EntityType.Builder.of(BitterColdWindEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).immuneTo(Blocks.POWDER_SNOW)
					.sized(1.8f, 2.1f));
	public static final RegistryObject<EntityType<FrostYetiEntity>> FROST_YETI = register("frost_yeti",
			EntityType.Builder.of(FrostYetiEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).immuneTo(Blocks.POWDER_SNOW)
					.sized(1.5f, 3.375f));
	public static final RegistryObject<EntityType<PrimordierEntity>> PRIMORDIER = register("primordier",
			EntityType.Builder.of(PrimordierEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).immuneTo(Blocks.POWDER_SNOW)
					.sized(3.5f, 5.5f));
	public static final RegistryObject<EntityType<CorrosiverEntity>> CORROSIVER = register("corrosiver",
			EntityType.Builder.of(CorrosiverEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).immuneTo(Blocks.POWDER_SNOW)
					.sized(2.2f, 3.5f));
	public static final RegistryObject<EntityType<PurpleSandGeckoEntity>> PURPLE_SAND_GECKO = register("purple_sand_gecko",
			EntityType.Builder.of(PurpleSandGeckoEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.4f, 0.4f));
	public static final RegistryObject<EntityType<PurpleSandGeckoTailEntity>> PURPLE_SAND_GECKO_TAIL = register("purple_sand_gecko_tail",
			EntityType.Builder.of(PurpleSandGeckoTailEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.3f, 0.3f));
	public static final RegistryObject<EntityType<SandstoneBeastEntity>> SANDSTONE_BEAST = register("sandstone_beast",
			EntityType.Builder.of(SandstoneBeastEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(1.8f, 2.0f));
	public static final RegistryObject<EntityType<PurpleSandRabbitEntity>> PURPLE_SAND_RABBIT = register("purple_sand_rabbit",
			EntityType.Builder.of(PurpleSandRabbitEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(8)
					.sized(0.6f, 0.75f));
	public static final RegistryObject<EntityType<PurpleSandCamelEntity>> PURPLE_SAND_CAMEL = register("purple_sand_camel",
			EntityType.Builder.of(PurpleSandCamelEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(1.95f, 2.85f));
	public static final RegistryObject<EntityType<PurpleSandScorpionEntity>> PURPLE_SAND_SCORPION = register("purple_sand_scorpion",
			EntityType.Builder.of(PurpleSandScorpionEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(1.5f, 0.8f));
	public static final RegistryObject<EntityType<PurpleSandCreeperEntity>> PURPLE_SAND_CREEPER = register("purple_sand_creeper",
			EntityType.Builder.of(PurpleSandCreeperEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.6f, 1.7f));
	public static final RegistryObject<EntityType<PurpleSandWitchEntity>> PURPLE_SAND_WITCH = register("purple_sand_witch",
			EntityType.Builder.of(PurpleSandWitchEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<WitchScholarEntity>> WITCH_SCHOLAR = register("witch_scholar",
			EntityType.Builder.of(WitchScholarEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<SpirveEntity>> SPIRVE = register("spirve",
			EntityType.Builder.of(SpirveEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<RottenDogEntity>> ROTTEN_DOG = register("rotten_dog",
			EntityType.Builder.of(RottenDogEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(1.1f, 1.2f));
	public static final RegistryObject<EntityType<BigWitchEntity>> BIG_WITCH = register("big_witch",
			EntityType.Builder.of(BigWitchEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16).immuneTo(Blocks.POWDER_SNOW)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<LightningWormEntity>> LIGHTNING_WORM = register("lightning_worm",
			EntityType.Builder.of(LightningWormEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(1.5f, 1.5f));
	public static final RegistryObject<EntityType<BasePartLivingEntity>> LIGHTNING_WORM_PART = register("lightning_worm_part",
			EntityType.Builder.<BasePartLivingEntity>of(BasePartLivingEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(1.5f, 1.5f));
	public static final RegistryObject<EntityType<DuneshocklerEntity>> DUNESHOCKLER = register("duneshockler",
			EntityType.Builder.of(DuneshocklerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(4f, 8f));
	public static final RegistryObject<EntityType<DuneshocklerPart>> DUNESHOCKLER_PART = register("duneshockler_part",
			EntityType.Builder.<DuneshocklerPart>of(DuneshocklerPart::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(2f, 2.5f));
	public static final RegistryObject<EntityType<CohortHagEntity>> COHORT_HAG = register("cohort_hag",
			EntityType.Builder.of(CohortHagEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<CovenHagEntity>> COVEN_HAG_ONE = register("coven_hag_one",
			EntityType.Builder.of(CovenHagEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<CovenHagEntity>> COVEN_HAG_TWO = register("coven_hag_two",
			EntityType.Builder.of(CovenHagEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<PurpleSandHagEntity>> PURPLE_SAND_HAG = register("purple_sand_hag",
			EntityType.Builder.of(PurpleSandHagEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune()
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<PurpleSandPhantomEntity>> PURPLE_SAND_PHANTOM = register("purple_sand_phantom",
			EntityType.Builder.of(PurpleSandPhantomEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.6f, 1.895f));
	public static final RegistryObject<EntityType<GreyscaleEntity>> GREYSCALE = register("greyscale",
			EntityType.Builder.of(GreyscaleEntity::new, MobCategory.WATER_AMBIENT).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.7f, 0.35f));
	public static final RegistryObject<EntityType<JewelroamerEntity>> JEWELROAMER = register("jewelroamer",
			EntityType.Builder.of(JewelroamerEntity::new, MobCategory.WATER_AMBIENT).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.6f, 0.3f));
	public static final RegistryObject<EntityType<StarbriteEntity>> STARBRITE = register("starbrite",
			EntityType.Builder.of(StarbriteEntity::new, MobCategory.WATER_AMBIENT).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.5f, 0.3f));
	public static final RegistryObject<EntityType<AbyssalerEntity>> ABYSSALER = register("abyssaler",
			EntityType.Builder.of(AbyssalerEntity::new, MobCategory.WATER_AMBIENT).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(1.0f, 0.6f));
	public static final RegistryObject<EntityType<SolishEntity>> SOLISH = register("solish",
			EntityType.Builder.of(SolishEntity::new, MobCategory.WATER_CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(2.0f, 1.05f));
	public static final RegistryObject<EntityType<BenthictEntity>> BENTHICT = register("benthict",
			EntityType.Builder.of(BenthictEntity::new, MobCategory.WATER_CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.5f, 0.25f));
	public static final RegistryObject<EntityType<PrancedDolphinEntity>> PRANCED_DOLPHIN = register("pranced_dolphin",
			EntityType.Builder.of(PrancedDolphinEntity::new, MobCategory.WATER_CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(1.8f, 1.2f));
	public static final RegistryObject<EntityType<MalialosaurEntity>> MALIALOSAUR = register("malialosaur",
			EntityType.Builder.of(MalialosaurEntity::new, MobCategory.WATER_CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(2.0f, 1.4f));
	public static final RegistryObject<EntityType<MalignasaurEntity>> MALIGNASAUR = register("malignasaur",
			EntityType.Builder.of(MalignasaurEntity::new, MobCategory.WATER_CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(3.375f, 3.0f));
	public static final RegistryObject<EntityType<QoaikuSeekerEntity>> QOAIKU_SEEKER = register("qoaiku_seeker",
			EntityType.Builder.of(QoaikuSeekerEntity::new, MobCategory.WATER_CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<BulgeEntity>> BULGE = register("bulge",
			EntityType.Builder.of(BulgeEntity::new, MobCategory.WATER_CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.6f, 1.5f));
	public static final RegistryObject<EntityType<FuryBulgeEntity>> FURY_BULGE = register("fury_bulge",
			EntityType.Builder.of(FuryBulgeEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.6f, 1.5f));
	public static final RegistryObject<EntityType<SeawaveEntity>> SEAWAVE = register("seawave",
			EntityType.Builder.of(SeawaveEntity::new, MobCategory.WATER_CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.9f, 2.25f));
	public static final RegistryObject<EntityType<FurySeawaveEntity>> FURY_SEAWAVE = register("fury_seawave",
			EntityType.Builder.of(FurySeawaveEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.9f, 2.25f));
	public static final RegistryObject<EntityType<GemstoneMalignasaurEntity>> GEMSTONE_MALIGNASAUR = register("gemstone_malignasaur",
			EntityType.Builder.of(GemstoneMalignasaurEntity::new, MobCategory.WATER_CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(4.5f, 4.0f));
	public static final RegistryObject<EntityType<BrightGrassCarpEntity>> BRIGHT_GRASS_CARP = register("bright_grass_carp",
			EntityType.Builder.of(BrightGrassCarpEntity::new, MobCategory.WATER_AMBIENT).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.7f, 0.35f));
	public static final RegistryObject<EntityType<ChromasnapBassEntity>> CHROMASNAP_BASS = register("chromasnap_bass",
			EntityType.Builder.of(ChromasnapBassEntity::new, MobCategory.WATER_AMBIENT).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.7f, 0.35f));
	public static final RegistryObject<EntityType<LakeLoachEntity>> LAKE_LOACH = register("lake_loach",
			EntityType.Builder.of(LakeLoachEntity::new, MobCategory.WATER_AMBIENT).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.9f, 0.35f));
	public static final RegistryObject<EntityType<BrightMouseEntity>> BRIGHT_MOUSE = register("bright_mouse",
			EntityType.Builder.of(BrightMouseEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.6f, 0.5f));
	public static final RegistryObject<EntityType<IrishtailChickenEntity>> IRISHTAIL_CHICKEN = register("irishtail_chicken",
			EntityType.Builder.of(IrishtailChickenEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.4f, 0.7f));
	public static final RegistryObject<EntityType<ScalyBearEntity>> SCALY_BEAR = register("scaly_bear",
			EntityType.Builder.of(ScalyBearEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(16).immuneTo(Blocks.POWDER_SNOW)
					.sized(1.7f, 1.7f));
	public static final RegistryObject<EntityType<RusherEntity>> RUSHER = register("rusher",
			EntityType.Builder.of(RusherEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(1.5f, 1.8f));
	public static final RegistryObject<EntityType<GlowSparrowEntity>> GLOW_SPARROW = register("glow_sparrow",
			EntityType.Builder.of(GlowSparrowEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<SoftSpiralSheepEntity>> SOFT_SPIRAL_SHEEP = register("soft_spiral_sheep",
			EntityType.Builder.of(SoftSpiralSheepEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10).immuneTo(Blocks.POWDER_SNOW)
					.sized(0.8f, 1.2f));
	public static final RegistryObject<EntityType<ArchnosedHornbeastEntity>> ARCHNOSED_HORNBEAST = register("archnosed_hornbeast",
			EntityType.Builder.of(ArchnosedHornbeastEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(5.8f, 7.2f));
	public static final RegistryObject<EntityType<PiglinResidentEntity>> PIGLIN_RESIDENT = register("piglin_resident",
			EntityType.Builder.of(PiglinResidentEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<TanglerEntity>> TANGLER = register("tangler",
			EntityType.Builder.of(TanglerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<BrightLandBeastEntity>> BRIGHT_LAND_BEAST = register("bright_land_beast",
			EntityType.Builder.of(BrightLandBeastEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(7.25f, 9f));
	public static final RegistryObject<EntityType<PrimedSerponClassicalBombEntity>> SERPON_CLASSICAL_BOMB = register("serpon_classical_bomb",
			EntityType.Builder.<PrimedSerponClassicalBombEntity>of(PrimedSerponClassicalBombEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.98f, 0.98f));
	public static final RegistryObject<EntityType<SerponColonialSoldierEntity>> SERPON_COLONIAL_SOLDIER = register("serpon_colonial_soldier",
			EntityType.Builder.of(SerponColonialSoldierEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune()
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<SerponPioneerEntity>> SERPON_PIONEER = register("serpon_pioneer",
			EntityType.Builder.of(SerponPioneerEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune()
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<SerponRioterEntity>> SERPON_RIOTER = register("serpon_rioter",
			EntityType.Builder.of(SerponRioterEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune()
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<SerponFlameWizardEntity>> SERPON_FLAME_WIZARD = register("serpon_flame_wizard",
			EntityType.Builder.of(SerponFlameWizardEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune()
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<SerponIntruderEntity>> SERPON_INTRUDER = register("serpon_intruder",
			EntityType.Builder.of(SerponIntruderEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune()
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<SerponFireApostleEntity>> SERPON_FIRE_APOSTLE = register("serpon_fire_apostle",
			EntityType.Builder.of(SerponFireApostleEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune()
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<WarPythonEntity>> WAR_PYTHON = register("war_python",
			EntityType.Builder.of(WarPythonEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(16).fireImmune()
					.sized(0.6f, 0.6f));
	public static final RegistryObject<EntityType<WarPythonBasePartLivingEntity>> WAR_PYTHON_PART = register("war_python_part",
			EntityType.Builder.<WarPythonBasePartLivingEntity>of(WarPythonBasePartLivingEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<RoyalWarPythonEntity>> ROYAL_WAR_PYTHON = register("royal_war_python",
			EntityType.Builder.of(RoyalWarPythonEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(16).fireImmune()
					.sized(0.85f, 0.85f));
	public static final RegistryObject<EntityType<WarPythonBasePartLivingEntity>> ROYAL_WAR_PYTHON_PART = register("royal_war_python_part",
			EntityType.Builder.<WarPythonBasePartLivingEntity>of(WarPythonBasePartLivingEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.7f, 0.7f));
	public static final RegistryObject<EntityType<FlamoarEntity>> FLAMOAR = register("flamoar",
			EntityType.Builder.of(FlamoarEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune()
					.sized(1.5f, 3.25f));
	public static final RegistryObject<EntityType<SerponChariotEntity>> SERPON_CHARIOT = register("serpon_chariot",
			EntityType.Builder.of(SerponChariotEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune()
					.sized(2.4f, 1.5f));
	public static final RegistryObject<EntityType<SerponCrossbowTurretEntity>> SERPON_CROSSBOW_TURRET = register("serpon_crossbow_turret",
			EntityType.Builder.of(SerponCrossbowTurretEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune()
					.sized(1.99f, 1.99f));
	public static final RegistryObject<EntityType<SerponCombatPlatformEntity>> SERPON_COMBAT_PLATFORM = register("serpon_combat_platform",
			EntityType.Builder.of(SerponCombatPlatformEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune()
					.sized(9.0f, 0.875f));
	public static final RegistryObject<EntityType<SerponCombatExecuterEntity>> SERPON_COMBAT_EXECUTER = register("serpon_combat_executer",
			EntityType.Builder.of(SerponCombatExecuterEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune()
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<FlyingFireballEntity>> FLYING_FIREBALL = register("flying_fireball",
			EntityType.Builder.of(FlyingFireballEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(16).fireImmune()
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<FuryFlyingFireballEntity>> FURY_FLYING_FIREBALL = register("fury_flying_fireball",
			EntityType.Builder.of(FuryFlyingFireballEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16).fireImmune()
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<DroughtBlazeEntity>> DROUGHT_BLAZE = register("drought_blaze",
			EntityType.Builder.of(DroughtBlazeEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).fireImmune()
					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<FuryDroughtBlazeEntity>> FURY_DROUGHT_BLAZE = register("fury_drought_blaze",
			EntityType.Builder.of(FuryDroughtBlazeEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).fireImmune()
					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<FireSecretorEntity>> FIRE_SECRETOR = register("fire_secretor",
			EntityType.Builder.of(FireSecretorEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune()
					.sized(0.95f, 2.85f));
	public static final RegistryObject<EntityType<DroughtWildfireEntity>> DROUGHT_WILDFIRE = register("drought_wildfire",
			EntityType.Builder.of(DroughtWildfireEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.9f, 2.25f));
	public static final RegistryObject<EntityType<FuryDroughtWildfireEntity>> FURY_DROUGHT_WILDFIRE = register("fury_drought_wildfire",
			EntityType.Builder.of(FuryDroughtWildfireEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.9f, 2.25f));
	public static final RegistryObject<EntityType<HarmoniousSlimeEntity>> HARMONIOUS_SLIME = register("harmonious_slime",
			EntityType.Builder.of(HarmoniousSlimeEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(2.04f, 2.04f));
	public static final RegistryObject<EntityType<FlowminnowEntity>> FLOWMINNOW = register("flowminnow",
			EntityType.Builder.of(FlowminnowEntity::new, MobCategory.WATER_AMBIENT).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.5f, 0.3f));
	public static final RegistryObject<EntityType<WonderlandYellowDogEntity>> WONDERLAND_YELLOW_DOG = register("wonderland_yellow_dog",
			EntityType.Builder.of(WonderlandYellowDogEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.6f, 0.85f));
	public static final RegistryObject<EntityType<WonderlandLiCatEntity>> WONDERLAND_LI_CAT = register("wonderland_li_cat",
			EntityType.Builder.of(WonderlandLiCatEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(8)
					.sized(0.6f, 0.7f));
	public static final RegistryObject<EntityType<WonderlandRamblerEntity>> WONDERLAND_RAMBLER = register("wonderland_rambler",
			EntityType.Builder.of(WonderlandRamblerEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(1.1f, 1.0f));
	public static final RegistryObject<EntityType<WonderlandGiantFrogEntity>> WONDERLAND_GIANT_FROG = register("wonderland_giant_frog",
			EntityType.Builder.of(WonderlandGiantFrogEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(1.5f, 1.1f));
	public static final RegistryObject<EntityType<WonderlandGiantFrogTadpoleEntity>> WONDERLAND_GIANT_FROG_TADPOLE = register("wonderland_giant_frog_tadpole",
			EntityType.Builder.of(WonderlandGiantFrogTadpoleEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(WonderlandGiantFrogTadpoleEntity.HITBOX_WIDTH, WonderlandGiantFrogTadpoleEntity.HITBOX_HEIGHT));
	public static final RegistryObject<EntityType<WonderlandBroncoEntity>> WONDERLAND_BRONCO = register("wonderland_bronco",
			EntityType.Builder.of(WonderlandBroncoEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(2.0f, 2.7f));
	public static final RegistryObject<EntityType<BotanizeEntity>> BOTANIZE = register("botanize",
			EntityType.Builder.of(BotanizeEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.5f, 0.9f));
	public static final RegistryObject<EntityType<CoilvineBotanizeEntity>> COILVINE_BOTANIZE = register("coilvine_botanize",
			EntityType.Builder.of(CoilvineBotanizeEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.75f, 1.35f));
	public static final RegistryObject<EntityType<WonderlinEntity>> WONDERLIN = register("wonderlin",
			EntityType.Builder.of(WonderlinEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.5f, 1.1f));
	public static final RegistryObject<EntityType<WonderlinMerchantEntity>> WONDERLIN_MERCHANT = register("wonderlin_merchant",
			EntityType.Builder.of(WonderlinMerchantEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.5f, 1.1f));
	public static final RegistryObject<EntityType<WonderlinWarriorEntity>> WONDERLIN_WARRIOR = register("wonderlin_warrior",
			EntityType.Builder.of(WonderlinWarriorEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.5f, 1.1f));
	public static final RegistryObject<EntityType<ThornSpikeEntity>> THORN_SPIKE = register("thorn_spike",
			EntityType.Builder.<ThornSpikeEntity>of(ThornSpikeEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.5f, 3f));
	public static final RegistryObject<EntityType<TyrantOfTheMountainRealmEntity>> TYRANT_OF_THE_MOUNTAIN_REALM = register("tyrant_of_the_mountain_realm",
			EntityType.Builder.of(TyrantOfTheMountainRealmEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(2.25f, 4.6f));
	public static final RegistryObject<EntityType<CogonSwordEntity>> COGON_SWORD = register("cogon_sword",
			EntityType.Builder.<CogonSwordEntity>of(CogonSwordEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(2.5f, 1.5f));
	public static final RegistryObject<EntityType<ImprisonVineEntity>> IMPRISON_VINE = register("imprison_vine",
			EntityType.Builder.<ImprisonVineEntity>of(ImprisonVineEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(2f, 2f));
	public static final RegistryObject<EntityType<ImprisonVinePart>> IMPRISON_VINE_PART = register("imprison_vine_part",
			EntityType.Builder.<ImprisonVinePart>of(ImprisonVinePart::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(1f, 1f));
	public static final RegistryObject<EntityType<CelestialCoilvineFlowerEntity>> CELESTIAL_COILVINE_FLOWER = register("celestial_coilvine_flower",
			EntityType.Builder.<CelestialCoilvineFlowerEntity>of(CelestialCoilvineFlowerEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.75f, 0.75f));
	public static final RegistryObject<EntityType<WiltMissileEntity>> WILT_MISSILE = register("wilt_missile",
			EntityType.Builder.<WiltMissileEntity>of(WiltMissileEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(1.0f, 1.0f));
	public static final RegistryObject<EntityType<VinePodBombEntity>> VINE_POD_BOMB = register("vine_pod_bomb",
			EntityType.Builder.of(VinePodBombEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(1.0f, 1.0f));
	public static final RegistryObject<EntityType<VineheartBombEntity>> VINEHEART_BOMB = register("vineheart_bomb",
			EntityType.Builder.<VineheartBombEntity>of(VineheartBombEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.75f, 2.125f));
	public static final RegistryObject<EntityType<CelestialCoilvineEntity>> CELESTIAL_COILVINE = register("celestial_coilvine",
			EntityType.Builder.of(CelestialCoilvineEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(2.5f, 5.0f));
	public static final RegistryObject<EntityType<RamblerEntity>> RAMBLER = register("rambler",
			EntityType.Builder.of(RamblerEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(1.1f, 1.0f));
	public static final RegistryObject<EntityType<ThornyTortoiseEntity>> THORNY_TORTOISE = register("thorny_tortoise",
			EntityType.Builder.of(ThornyTortoiseEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(2f, 2.2f));
	public static final RegistryObject<EntityType<SunCrabEntity>> SUN_CRAB = register("sun_crab",
			EntityType.Builder.of(SunCrabEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.6f, 0.6f));
	public static final RegistryObject<EntityType<BeachTerrorBirdEntity>> BEACH_TERROR_BIRD = register("beach_terror_bird",
			EntityType.Builder.of(BeachTerrorBirdEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.8f, 2.4f));
	public static final RegistryObject<EntityType<RuinsMouseEntity>> RUINS_MOUSE = register("ruins_mouse",
			EntityType.Builder.of(RuinsMouseEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.6f, 0.5f));
	public static final RegistryObject<EntityType<RustleFoxEntity>> RUSTLE_FOX = register("rustle_fox",
			EntityType.Builder.of(RustleFoxEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).immuneTo(Blocks.SWEET_BERRY_BUSH)
					.sized(0.7f, 0.8f));
	public static final RegistryObject<EntityType<DustMothEntity>> DUST_MOTH = register("dust_moth",
			EntityType.Builder.of(DustMothEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.4f, 0.2f));
	public static final RegistryObject<EntityType<CleaningRobotEntity>> CLEANING_ROBOT = register("cleaning_robot",
			EntityType.Builder.of(CleaningRobotEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).immuneTo(Blocks.POWDER_SNOW)
					.sized(1.0f, 1.3f));
	public static final RegistryObject<EntityType<RuinsBreeze>> RUINS_BREEZE = register("ruins_breeze",
			EntityType.Builder.of(RuinsBreeze::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.6f, 1.7f));
	public static final RegistryObject<EntityType<MerorAdventurerEntity>> MEROR_ADVENTURER = register("meror_adventurer",
			EntityType.Builder.of(MerorAdventurerEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.895f));
	public static final RegistryObject<EntityType<MerorMerchantEntity>> MEROR_MERCHANT = register("meror_merchant",
			EntityType.Builder.of(MerorMerchantEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.895f));
	public static final RegistryObject<EntityType<MerorBountyHunterEntity>> MEROR_BOUNTY_HUNTER = register("meror_bounty_hunter",
			EntityType.Builder.of(MerorBountyHunterEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.895f));
	public static final RegistryObject<EntityType<MerorLocatorEntity>> MEROR_LOCATOR = register("meror_locator",
			EntityType.Builder.of(MerorLocatorEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune()
					.sized(0.4f, 0.125f));
	public static final RegistryObject<EntityType<MerorCubeEntity>> MEROR_CUBE = register("meror_cube",
			EntityType.Builder.of(MerorCubeEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune()
					.sized(1.0f, 1.0f));
	public static final RegistryObject<EntityType<MerorMinistrosityEntity>> MEROR_MINISTROSITY = register("meror_ministrosity",
			EntityType.Builder.of(MerorMinistrosityEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune()
					.sized(0.45f, 0.875f));
	public static final RegistryObject<EntityType<MerorOathguardMechEntity>> MEROR_OATHGUARD_MECH = register("meror_oathguard_mech",
			EntityType.Builder.of(MerorOathguardMechEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune()
					.sized(0.6f, 2.2f));
	public static final RegistryObject<EntityType<MerorGolemEntity>> MEROR_GOLEM = register("meror_golem",
			EntityType.Builder.of(MerorGolemEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune()
					.sized(2.75f, 3.875f));
	public static final RegistryObject<EntityType<MerorMonstrosityEntity>> MEROR_MONSTROSITY = register("meror_monstrosity",
			EntityType.Builder.of(MerorMonstrosityEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune()
					.sized(4.0f, 5.4f));
	public static final RegistryObject<EntityType<MerorChampionEntity>> MEROR_CHAMPION = register("meror_champion",
			EntityType.Builder.of(MerorChampionEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune()
					.sized(1.9f, 4f));
	public static final RegistryObject<EntityType<MerorOathguardMechEntity>> SECOND_ROUNDER_GOLEM = register("second_rounder_golem",
			EntityType.Builder.of(MerorOathguardMechEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune()
					.sized(0.6f, 2.2f));
//	public static final RegistryObject<EntityType<OldSecondRounderGolemEntity>> SECOND_ROUNDER_GOLEM = register("second_rounder_golem",
//			EntityType.Builder.of(OldSecondRounderGolemEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune()
//					.sized(2.5f, 5f));
	public static final RegistryObject<EntityType<SecondRounderGolemSwordEntity>> SECOND_ROUNDER_GOLEM_SWORD = register("second_rounder_golem_sword",
			EntityType.Builder.of(SecondRounderGolemSwordEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune()
					.sized(2.5f, 5f));
	public static final RegistryObject<EntityType<DeepBatEntity>> DEEP_BAT = register("deep_bat",
			EntityType.Builder.of(DeepBatEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(5)
					.sized(0.5f, 0.9f));
	public static final RegistryObject<EntityType<RustBeetleEntity>> RUST_BEETLE = register("rust_beetle",
			EntityType.Builder.of(RustBeetleEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(1.6f, 1.1f));
	public static final RegistryObject<EntityType<FlowingDustEntity>> FLOWING_DUST = register("flowing_dust",
			EntityType.Builder.of(FlowingDustEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.625f, 0.625f));
	public static final RegistryObject<EntityType<BlamerEntity>> BLAMER = register("blamer",
			EntityType.Builder.of(BlamerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<IronTeethEntity>> IRON_TEETH = register("iron_teeth",
			EntityType.Builder.of(IronTeethEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(16).immuneTo(Blocks.POWDER_SNOW)
					.sized(0.6f, 1.5f));
	public static final RegistryObject<EntityType<FuryIronTeethEntity>> FURY_IRON_TEETH = register("fury_iron_teeth",
			EntityType.Builder.of(FuryIronTeethEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16).immuneTo(Blocks.POWDER_SNOW)
					.sized(0.6f, 1.5f));
	public static final RegistryObject<EntityType<SecondRounderAnimatedArmorEntity>> SECOND_ROUNDER_ANIMATED_ARMOR = register("second_rounder_animated_armor",
			EntityType.Builder.of(SecondRounderAnimatedArmorEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<FurySecondRounderAnimatedArmorEntity>> FURY_SECOND_ROUNDER_ANIMATED_ARMOR = register("fury_second_rounder_animated_armor",
			EntityType.Builder.of(FurySecondRounderAnimatedArmorEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<EyeDiscEntity>> EYE_DISC = register("eye_disc",
			EntityType.Builder.of(EyeDiscEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(1.4f, 2.25f));
	public static final RegistryObject<EntityType<DarisorderEntity>> DARISORDER = register("darisorder",
			EntityType.Builder.of(DarisorderEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune().immuneTo(Blocks.POWDER_SNOW)
					.sized(2f, 4.5f));
	public static final RegistryObject<EntityType<FuryDarisorderEntity>> FURY_DARISORDER = register("fury_darisorder",
			EntityType.Builder.of(FuryDarisorderEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune().immuneTo(Blocks.POWDER_SNOW)
					.sized(2f, 4.5f));
	public static final RegistryObject<EntityType<DownySpiderEntity>> DOWNY_SPIDER = register("downy_spider",
			EntityType.Builder.of(DownySpiderEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.5f, 0.25f));
	public static final RegistryObject<EntityType<VirtualCaveCockroachEntity>> VIRTUAL_CAVE_COCKROACH = register("virtual_cave_cockroach",
			EntityType.Builder.of(VirtualCaveCockroachEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(1.15f, 0.8f));
	public static final RegistryObject<EntityType<BlueShimmerSquidEntity>> BLUE_SHIMMER_SQUID = register("blue_shimmer_squid",
			EntityType.Builder.of(BlueShimmerSquidEntity::new, MobCategory.UNDERGROUND_WATER_CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.6f, 0.6f));
	public static final RegistryObject<EntityType<VirtualCaveSpiderEntity>> VIRTUAL_CAVE_SPIDER = register("virtual_cave_spider",
			EntityType.Builder.of(VirtualCaveSpiderEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(2.0f, 1.0f));
	public static final RegistryObject<EntityType<SliderEntity>> SLIDER = register("slider",
			EntityType.Builder.of(SliderEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(1.0f, 1.0f));
	public static final RegistryObject<EntityType<PredationToothedFlowerEntity>> PREDATION_TOOTHED_FLOWER = register("predation_toothed_flower",
			EntityType.Builder.of(PredationToothedFlowerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(1.0f, 2.0f));
	public static final RegistryObject<EntityType<WildernessWolfEntity>> WILDERNESS_WOLF = register("wilderness_wolf",
			EntityType.Builder.of(WildernessWolfEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(16).immuneTo(Blocks.POWDER_SNOW)
					.sized(0.85f, 1.2f));
	public static final RegistryObject<EntityType<WildernessRamblerEntity>> WILDERNESS_RAMBLER = register("wilderness_rambler",
			EntityType.Builder.of(WildernessRamblerEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(1.6f, 1.5f));
	public static final RegistryObject<EntityType<WildernessBullEntity>> WILDERNESS_BULL = register("wilderness_bull",
			EntityType.Builder.of(WildernessBullEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(1.9f, 2.55f));
	public static final RegistryObject<EntityType<CloudFurDeerEntity>> CLOUD_FUR_DEER = register("cloud_fur_deer",
			EntityType.Builder.of(CloudFurDeerEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.9f, 1.87f));
	public static final RegistryObject<EntityType<BrownVenomousSlugEntity>> BROWN_VENOMOUS_SLUG = register("brown_venomous_slug",
			EntityType.Builder.of(BrownVenomousSlugEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.4f, 0.2f));
	public static final RegistryObject<EntityType<WildernessGiantTrampEntity>> WILDERNESS_GIANT_TRAMP = register("wilderness_giant_tramp",
			EntityType.Builder.of(WildernessGiantTrampEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(1.05f, 3.4f));
	public static final RegistryObject<EntityType<WildernessGiantHunterEntity>> WILDERNESS_GIANT_HUNTER = register("wilderness_giant_hunter",
			EntityType.Builder.of(WildernessGiantHunterEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(1.05f, 3.4f));
	public static final RegistryObject<EntityType<WoodlandHeartGuardianEntity>> WOODLAND_HEART_GUARDIAN = register("woodland_heart_guardian",
			EntityType.Builder.of(WoodlandHeartGuardianEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(1.05f, 3.4f));
	public static final RegistryObject<EntityType<EagleBowEntity>> EAGLE_BOW = register("eagle_bow",
			EntityType.Builder.of(EagleBowEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(1.05f, 3.4f));
	public static final RegistryObject<EntityType<ScavengerEntity>> SCAVENGER = register("scavenger",
			EntityType.Builder.of(ScavengerEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).immuneTo(Blocks.POWDER_SNOW)
					.sized(2.2f, 1.8f));
	public static final RegistryObject<EntityType<BloodsuckerEntity>> BLOODSUCKER = register("bloodsucker",
			EntityType.Builder.of(BloodsuckerEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).immuneTo(Blocks.POWDER_SNOW)
					.sized(2.0f, 1.75f));
	public static final RegistryObject<EntityType<RigiderEntity>> RIGIDER = register("rigider",
			EntityType.Builder.of(RigiderEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).immuneTo(Blocks.POWDER_SNOW)
					.sized(2.5f, 1.85f));
	public static final RegistryObject<EntityType<ScavengerEntity>> VARIANT_ZSIEIN = register("variant_zsiein",
			EntityType.Builder.of(ScavengerEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).immuneTo(Blocks.POWDER_SNOW)
					.sized(2.2f, 1.8f));
//	public static final RegistryObject<EntityType<OldVariantZsieinEntity>> VARIANT_ZSIEIN = register("variant_zsiein",
//			EntityType.Builder.<OldVariantZsieinEntity>of(OldVariantZsieinEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).immuneTo(Blocks.POWDER_SNOW)
//					.sized(4f, 8f));
	public static final RegistryObject<EntityType<ImprisonVinePart>> VARIANT_ZSIEIN_PART = register("variant_zsiein_part",
			EntityType.Builder.<ImprisonVinePart>of(ImprisonVinePart::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(4.5f, 8.5f));

	//发射物
	public static final RegistryObject<EntityType<ThrownJavelinVillagerMetalEntity>> THROWN_VILLAGER_METAL_JAVELIN = register("projectile_throw_villager_metal_javelin",
			EntityType.Builder.<ThrownJavelinVillagerMetalEntity>of(ThrownJavelinVillagerMetalEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<CarvedVillagerSpectralArrowEntity>> CARVED_VILLAGER_SPECTRAL_ARROW = register("projectile_carved_villager_spectral_arrow",
			EntityType.Builder.<CarvedVillagerSpectralArrowEntity>of(CarvedVillagerSpectralArrowEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<AncientSecondRounderArrowEntity>> ANCIENT_SECOND_ROUNDER_ARROW = register("projectile_ancient_second_rounder_arrow",
			EntityType.Builder.<AncientSecondRounderArrowEntity>of(AncientSecondRounderArrowEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<CarvedAllayAmethystEntity>> CARVED_ALLAY_AMETHYST = register("carved_allay_amethyst",
			EntityType.Builder.<CarvedAllayAmethystEntity>of(CarvedAllayAmethystEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<SedimentMissileEntity>> SEDIMENT_MISSILE = register("sediment_missile",
			EntityType.Builder.<SedimentMissileEntity>of(SedimentMissileEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.3125F, 0.3125F));
	public static final RegistryObject<EntityType<VertigoDelugeEntity>> VERTIGO_DELUGE = register("vertigo_deluge",
			EntityType.Builder.<VertigoDelugeEntity>of(VertigoDelugeEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.3125F, 0.3125F));
	public static final RegistryObject<EntityType<OminousBombEntity>> OMINOUS_BOMB = register("ominous_bomb",
			EntityType.Builder.<OminousBombEntity>of(OminousBombEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.4f, 0.4f));
	public static final RegistryObject<EntityType<OminousBombFragmentEntity>> OMINOUS_BOMB_FRAGMENT = register("ominous_bomb_fragment",
			EntityType.Builder.<OminousBombFragmentEntity>of(OminousBombFragmentEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<ThrownOminousJavalinEntity>> OMINOUS_JAVALIN = register("projectile_ominous_javelin",
			EntityType.Builder.<ThrownOminousJavalinEntity>of(ThrownOminousJavalinEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<BitterColdIceSpikeEntity>> BITTER_COLD_ICE_SPIKE = register("bitter_cold_ice_spike",
			EntityType.Builder.<BitterColdIceSpikeEntity>of(BitterColdIceSpikeEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<CorrosiveIceSpikeEntity>> CORROSIVE_ICE_SPIKE = register("corrosive_ice_spike",
			EntityType.Builder.<CorrosiveIceSpikeEntity>of(CorrosiveIceSpikeEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<BitterColdFrostbiteEntity>> BITTER_COLD_FROSTBITE = register("bitter_cold_frostbite",
			EntityType.Builder.<BitterColdFrostbiteEntity>of(BitterColdFrostbiteEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.3125F, 0.3125F));
	public static final RegistryObject<EntityType<OminousFlamesEntity>> OMINOUS_FLAMES = register("ominous_flames",
			EntityType.Builder.<OminousFlamesEntity>of(OminousFlamesEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.3125F, 0.3125F));
	public static final RegistryObject<EntityType<ArcaneLightSpotEntity>> ARCANE_LIGHT_SPOT = register("arcane_light_spot",
			EntityType.Builder.<ArcaneLightSpotEntity>of(ArcaneLightSpotEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.3125F, 0.3125F));
	public static final RegistryObject<EntityType<ElasticLightBallEntity>> ELASTIC_LIGHT_BALL = register("elastic_light_ball",
			EntityType.Builder.<ElasticLightBallEntity>of(ElasticLightBallEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<RadiantBombEntity>> RADIANT_BOMB = register("radiant_bomb",
			EntityType.Builder.<RadiantBombEntity>of(RadiantBombEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.3125F, 0.3125F));
	public static final RegistryObject<EntityType<SnapBoltEntity>> SNAP_BOLT = register("snap_bolt",
			EntityType.Builder.<SnapBoltEntity>of(SnapBoltEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.3125F, 0.3125F));
	public static final RegistryObject<EntityType<VirosaansMagicMissileEntity>> VIROSAANS_MAGIC_MISSILE = register("virosaans_magic_missile",
			EntityType.Builder.<VirosaansMagicMissileEntity>of(VirosaansMagicMissileEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.3125F, 0.3125F));
	public static final RegistryObject<EntityType<StarburstMarkerEntity>> STARBURST_MARKER = register("starburst_marker",
			EntityType.Builder.<StarburstMarkerEntity>of(StarburstMarkerEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.3125F, 0.3125F));
	public static final RegistryObject<EntityType<BloodyScreamEntity>> BLOODY_SCREAM = register("bloody_scream",
			EntityType.Builder.<BloodyScreamEntity>of(BloodyScreamEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(1.6f, 1.6f));
	public static final RegistryObject<EntityType<PushForceEntity>> PUSH_FORCE = register("push_force",
			EntityType.Builder.<PushForceEntity>of(PushForceEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.3125F, 0.3125F));
	public static final RegistryObject<EntityType<UneasyUncertaintyEntity>> UNEASY_UNCERTAINTY = register("uneasy_uncertainty",
			EntityType.Builder.<UneasyUncertaintyEntity>of(UneasyUncertaintyEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.3125F, 0.3125F));
	public static final RegistryObject<EntityType<FloatingForceEntity>> FLOATING_FORCE = register("floating_force",
			EntityType.Builder.<FloatingForceEntity>of(FloatingForceEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(1.5f, 1.5f));
	public static final RegistryObject<EntityType<GravityForceEntity>> GRAVITY_FORCE = register("gravity_force",
			EntityType.Builder.<GravityForceEntity>of(GravityForceEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(1.5f, 1.5f));
	public static final RegistryObject<EntityType<ThrownMountainRealmFlagSpearEntity>> PROJECTILE_THROW_MOUNTAIN_REALM_FLAG_SPEAR = register("projectile_throw_mountain_realm_flag_spear",
			EntityType.Builder.<ThrownMountainRealmFlagSpearEntity>of(ThrownMountainRealmFlagSpearEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.75f, 0.75f));
	public static final RegistryObject<EntityType<IndomitableWillEntity>> INDOMITABLE_WILL = register("indomitable_will",
			EntityType.Builder.<IndomitableWillEntity>of(IndomitableWillEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(1.5f, 1.5f));
	public static final RegistryObject<EntityType<IceRockThrowingBallEntity>> ICE_ROCK_THROWING_BALL = register("ice_rock_throwing_ball",
			EntityType.Builder.<IceRockThrowingBallEntity>of(IceRockThrowingBallEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.3f, 0.3f));
	public static final RegistryObject<EntityType<IceRockThrowEntity>> ICE_ROCK_THROW = register("ice_rock_throw",
			EntityType.Builder.<IceRockThrowEntity>of(IceRockThrowEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.setUpdateInterval(Integer.MAX_VALUE)
					.sized(1.0f, 1.0f));
	public static final RegistryObject<EntityType<AstralJavelinEntity>> ASTRAL_JAVELIN = register("astral_javelin",
			EntityType.Builder.<AstralJavelinEntity>of(AstralJavelinEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.setUpdateInterval(Integer.MAX_VALUE)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<WhipTheTideEntity>> WHIP_THE_TIDE = register("whip_the_tide",
			EntityType.Builder.<WhipTheTideEntity>of(WhipTheTideEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.setUpdateInterval(Integer.MAX_VALUE)
					.sized(1.0f, 1.0f));
	public static final RegistryObject<EntityType<WhipTheTideVineEntity>> WHIP_THE_TIDE_VINE = register("whip_the_tide_vine",
			EntityType.Builder.<WhipTheTideVineEntity>of(WhipTheTideVineEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.5f, 1.5f));
	public static final RegistryObject<EntityType<CorrosiverBreathEntity>> CORROSIVER_BREATH = register("corrosiver_breath",
			EntityType.Builder.<CorrosiverBreathEntity>of(CorrosiverBreathEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(1.6f, 1.6f));
	public static final RegistryObject<EntityType<PurpleSandAlchemyBombEntity>> PURPLE_SAND_ALCHEMY_BOMB = register("purple_sand_alchemy_bomb",
			EntityType.Builder.<PurpleSandAlchemyBombEntity>of(PurpleSandAlchemyBombEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.25f, 0.25f));
	public static final RegistryObject<EntityType<PurpleSandExplosiveAlchemyBombEntity>> PURPLE_SAND_EXPLOSIVE_ALCHEMY_BOMB = register("purple_sand_explosive_alchemy_bomb",
			EntityType.Builder.<PurpleSandExplosiveAlchemyBombEntity>of(PurpleSandExplosiveAlchemyBombEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.25f, 0.25f));
	public static final RegistryObject<EntityType<WormThunderstormEntity>> WORM_THUNDERSTORM = register("worm_thunderstorm",
			EntityType.Builder.<WormThunderstormEntity>of(WormThunderstormEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.3125F, 0.3125F));
	public static final RegistryObject<EntityType<WormThunderstormChainLightningEntity>> WORM_THUNDERSTORM_CHAIN_LIGHTNING = register("worm_thunderstorm_chain_lightning",
			EntityType.Builder.<WormThunderstormChainLightningEntity>of(WormThunderstormChainLightningEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.3125F, 0.3125F));
	public static final RegistryObject<EntityType<LightningSpitEntity>> LIGHTNING_SPIT = register("lightning_spit",
			EntityType.Builder.<LightningSpitEntity>of(LightningSpitEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(1.6f, 1.6f));
	public static final RegistryObject<EntityType<ToxicMistVolleyEntity>> TOXIC_MIST_VOLLEY = register("toxic_mist_volley",
			EntityType.Builder.<ToxicMistVolleyEntity>of(ToxicMistVolleyEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(1.6f, 1.6f));
	public static final RegistryObject<EntityType<GemstoneThrowingKnivesShootEntity>> GEMSTONE_THROWING_KNIVES_SHOOT = register("gemstone_throwing_knives_shoot",
			EntityType.Builder.<GemstoneThrowingKnivesShootEntity>of(GemstoneThrowingKnivesShootEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.4f, 0.4f));
	public static final RegistryObject<EntityType<MalignasaurArrowEntity>> MALIGNASAUR_ARROW = register("projectile_malignasaur_arrow",
			EntityType.Builder.<MalignasaurArrowEntity>of(MalignasaurArrowEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<ThrownQoaikuTridentEntity>> THROWN_QOAIKU_TRIDENT = register("projectile_throw_qoaiku_trident",
			EntityType.Builder.<ThrownQoaikuTridentEntity>of(ThrownQoaikuTridentEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<ThrownMalignasaurFishgigEntity>> THROWN_MALIGNASAUR_FISHGIG = register("projectile_throw_malignasaur_fishgig",
			EntityType.Builder.<ThrownMalignasaurFishgigEntity>of(ThrownMalignasaurFishgigEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<ThrownMalignasaurBaitFishgigEntity>> THROWN_MALIGNASAUR_BAIT_FISHGIG = register("projectile_throw_malignasaur_bait_fishgig",
			EntityType.Builder.<ThrownMalignasaurBaitFishgigEntity>of(ThrownMalignasaurBaitFishgigEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<BulgeBulletEntity>> BULGE_BULLET = register("bulge_bullet",
			EntityType.Builder.<BulgeBulletEntity>of(BulgeBulletEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<OceanCurrentBreathEntity>> OCEAN_CURRENT_BREATH = register("ocean_current_breath",
			EntityType.Builder.<OceanCurrentBreathEntity>of(OceanCurrentBreathEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(1.6f, 1.6f));
	public static final RegistryObject<EntityType<WhirlpoolEntity>> WHIRLPOOL = register("whirlpool",
			EntityType.Builder.<WhirlpoolEntity>of(WhirlpoolEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(2.5f, 2.5f));
	public static final RegistryObject<EntityType<MalignasaurBombEntity>> MALIGNASAUR_BOMB = register("malignasaur_bomb",
			EntityType.Builder.<MalignasaurBombEntity>of(MalignasaurBombEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<ThrowingStubbornStoneEntity>> THROWING_STUBBORN_STONE = register("throwing_stubborn_stone",
			EntityType.Builder.<ThrowingStubbornStoneEntity>of(ThrowingStubbornStoneEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.3f, 0.3f));
	public static final RegistryObject<EntityType<RottenBrightMelonEntity>> ROTTEN_BRIGHT_MELON = register("rotten_bright_melon",
			EntityType.Builder.<RottenBrightMelonEntity>of(RottenBrightMelonEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.3f, 0.3f));
	public static final RegistryObject<EntityType<VillagerMetalThrowingBallEntity>> VILLAGER_METAL_THROWING_BALL = register("villager_metal_throwing_ball",
			EntityType.Builder.<VillagerMetalThrowingBallEntity>of(VillagerMetalThrowingBallEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.3f, 0.3f));
	public static final RegistryObject<EntityType<GlassThrowingBallEntity>> GLASS_THROWING_BALL = register("glass_throwing_ball",
			EntityType.Builder.<GlassThrowingBallEntity>of(GlassThrowingBallEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.3f, 0.3f));
	public static final RegistryObject<EntityType<PotionThrowingBallEntity>> POTION_THROWING_BALL = register("potion_throwing_ball",
			EntityType.Builder.<PotionThrowingBallEntity>of(PotionThrowingBallEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.3f, 0.3f));
	public static final RegistryObject<EntityType<BrightBolaEntity>> BRIGHT_BOLA = register("bright_bola",
			EntityType.Builder.<BrightBolaEntity>of(BrightBolaEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<SerponDartEntity>> SERPON_DART = register("serpon_dart",
			EntityType.Builder.<SerponDartEntity>of(SerponDartEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<SerponSignalBulletEntity>> SERPON_SIGNAL_BULLET = register("serpon_signal_bullet",
			EntityType.Builder.<SerponSignalBulletEntity>of(SerponSignalBulletEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.25f, 0.25f));
	public static final RegistryObject<EntityType<SerponFireChargeEntity>> SERPON_FIRE_CHARGE = register("serpon_fire_charge",
			EntityType.Builder.<SerponFireChargeEntity>of(SerponFireChargeEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<ThrownSerponJavelinEntity>> THROWN_SERPON_JAVELIN = register("projectile_throw_serpon_javelin",
			EntityType.Builder.<ThrownSerponJavelinEntity>of(ThrownSerponJavelinEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<DroughtFireballEntity>> DROUGHT_FIREBALL = register("drought_fireball",
			EntityType.Builder.<DroughtFireballEntity>of(DroughtFireballEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.3125F, 0.3125F));
	public static final RegistryObject<EntityType<SerponFireballEntity>> SERPON_FIREBALL = register("serpon_fireball",
			EntityType.Builder.<SerponFireballEntity>of(SerponFireballEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.3125F, 0.3125F));
	public static final RegistryObject<EntityType<SerponFlamesEntity>> SERPON_FLAMES = register("serpon_flames",
			EntityType.Builder.<SerponFlamesEntity>of(SerponFlamesEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.3125F, 0.3125F));
	public static final RegistryObject<EntityType<VirtualCaveSurgeSiphonEntity>> VIRTUAL_CAVE_SURGE_SIPHON = register("virtual_cave_surge_siphon",
			EntityType.Builder.<VirtualCaveSurgeSiphonEntity>of(VirtualCaveSurgeSiphonEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.3125F, 0.3125F));
	public static final RegistryObject<EntityType<SecreteFireOilBombEntity>> SECRETE_FIRE_OIL_BOMB = register("secrete_fire_oil_bomb",
			EntityType.Builder.<SecreteFireOilBombEntity>of(SecreteFireOilBombEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(0.3125F, 0.3125F));
	public static final RegistryObject<EntityType<SmokeMissileEntity>> SMOKE_MISSILE = register("smoke_missile",
			EntityType.Builder.<SmokeMissileEntity>of(SmokeMissileEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(16)
					.sized(2.0f, 2.0f));
	public static final RegistryObject<EntityType<SerponCrossbowTurretArrowEntity>> PROJECTILE_SERPON_CROSSBOW_TURRET_ARROW = register("projectile_serpon_crossbow_turret_arrow",
			EntityType.Builder.<SerponCrossbowTurretArrowEntity>of(SerponCrossbowTurretArrowEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<SerponSiroccoEntity>> SERPON_SIROCCO = register("serpon_sirocco",
			EntityType.Builder.<SerponSiroccoEntity>of(SerponSiroccoEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(2.0f, 3.0f));
	public static final RegistryObject<EntityType<SerpenperthMeteorEntity>> SERPENPERTH_METEOR = register("serpenperth_meteor",
			EntityType.Builder.<SerpenperthMeteorEntity>of(SerpenperthMeteorEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(1.0f, 1.0f));
	public static final RegistryObject<EntityType<OldSerpenperthMeteorEntity>> OLD_SERPENPERTH_METEOR = register("old_serpenperth_meteor",
			EntityType.Builder.<OldSerpenperthMeteorEntity>of(OldSerpenperthMeteorEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(1.0f, 1.0f));
	public static final RegistryObject<EntityType<VineThrowingBallEntity>> VINE_THROWING_BALL = register("vine_throwing_ball",
			EntityType.Builder.<VineThrowingBallEntity>of(VineThrowingBallEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.3f, 0.3f));
	public static final RegistryObject<EntityType<BotanizeBulletEntity>> BOTANIZE_BULLET = register("botanize_bullet",
			EntityType.Builder.<BotanizeBulletEntity>of(BotanizeBulletEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<VineboltEntity>> PROJECTILE_VINEBOLT = register("projectile_vinebolt",
			EntityType.Builder.<VineboltEntity>of(VineboltEntity::new, MobCategory.MISC)
					.setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64)
					.setUpdateInterval(Integer.MAX_VALUE)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<ArcArrowEntity>> ARC_ARROW = register("projectile_arc_arrow",
			EntityType.Builder.<ArcArrowEntity>of(ArcArrowEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<MerorMetalArrowEntity>> MEROR_METAL_ARROW = register("projectile_meror_metal_arrow",
			EntityType.Builder.<MerorMetalArrowEntity>of(MerorMetalArrowEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<ThrownMerorMetalJavelinEntity>> THROWN_MEROR_METAL_JAVELIN = register("projectile_throw_meror_metal_javelin",
			EntityType.Builder.<ThrownMerorMetalJavelinEntity>of(ThrownMerorMetalJavelinEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<ThrownMerorPilumEntity>> THROWN_MEROR_PILUM = register("projectile_thrown_meror_pilum",
			EntityType.Builder.<ThrownMerorPilumEntity>of(ThrownMerorPilumEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<MerorRapierShootEntity>> MEROR_RAPIER_SHOOT = register("meror_rapier_shoot",
			EntityType.Builder.<MerorRapierShootEntity>of(MerorRapierShootEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.6f, 0.6f));
	public static final RegistryObject<EntityType<MerorScytheShootEntity>> MEROR_SCYTHE_SHOOT = register("meror_scythe_shoot",
			EntityType.Builder.<MerorScytheShootEntity>of(MerorScytheShootEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.6f, 0.6f));
	public static final RegistryObject<EntityType<MerorChargingBowArrowIEntity>> MEROR_CHARGING_BOW_ARROW_I = register("projectile_meror_charging_arrow_1",
			EntityType.Builder.<MerorChargingBowArrowIEntity>of(MerorChargingBowArrowIEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<MerorChargingBowArrowIIEntity>> MEROR_CHARGING_BOW_ARROW_II = register("projectile_meror_charging_arrow_2",
			EntityType.Builder.<MerorChargingBowArrowIIEntity>of(MerorChargingBowArrowIIEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<MerorChargingBowArrowIIIEntity>> MEROR_CHARGING_BOW_ARROW_III = register("projectile_meror_charging_arrow_3",
			EntityType.Builder.<MerorChargingBowArrowIIIEntity>of(MerorChargingBowArrowIIIEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<MerorChargingBowArrowIVEntity>> MEROR_CHARGING_BOW_ARROW_IV = register("projectile_meror_charging_arrow_4",
			EntityType.Builder.<MerorChargingBowArrowIVEntity>of(MerorChargingBowArrowIVEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<MerorChargingBowArrowVEntity>> MEROR_CHARGING_BOW_ARROW_V = register("projectile_meror_charging_arrow_5",
			EntityType.Builder.<MerorChargingBowArrowVEntity>of(MerorChargingBowArrowVEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<RuinsWindChargeEntity>> RUINS_WIND_CHARGE = register("ruins_wind_charge",
			EntityType.Builder.<RuinsWindChargeEntity>of(RuinsWindChargeEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4).clientTrackingRange(4).updateInterval(10)
					.sized(0.3125f, 0.3125f));
	public static final RegistryObject<EntityType<MerorBulletEntity>> MEROR_BULLET = register("meror_bullet",
			EntityType.Builder.<MerorBulletEntity>of(MerorBulletEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<MerorShockWaveEntity>> MEROR_SHOCK_WAVE = register("meror_shock_wave",
			EntityType.Builder.<MerorShockWaveEntity>of(MerorShockWaveEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.75f, 0.75f));
	public static final RegistryObject<EntityType<MerorFireEntity>> MEROR_FIRE = register("meror_fire",
			EntityType.Builder.<MerorFireEntity>of(MerorFireEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.3125F, 0.3125F));
	public static final RegistryObject<EntityType<MerorBombEntity>> MEROR_BOMB = register("meror_bomb",
			EntityType.Builder.<MerorBombEntity>of(MerorBombEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.85f, 0.85f));
	public static final RegistryObject<EntityType<MerorReteEntity>> MEROR_RETE = register("meror_rete",
			EntityType.Builder.<MerorReteEntity>of(MerorReteEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(1.0f, 1.0f));
	public static final RegistryObject<EntityType<IntenseBombingEntity>> INTENSE_BOMBING = register("intense_bombing",
			EntityType.Builder.<IntenseBombingEntity>of(IntenseBombingEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.7f, 0.7f));
	public static final RegistryObject<EntityType<EyeDiscShootEntity>> EYE_DISC_SHOOT = register("eye_disc_shoot",
			EntityType.Builder.<EyeDiscShootEntity>of(EyeDiscShootEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.2f, 0.2f));
	public static final RegistryObject<EntityType<VirtualCaveCrystalArrowEntity>> VIRTUAL_CAVE_CRYSTAL_ARROW = register("projectile_virtual_cave_crystal_arrow",
			EntityType.Builder.<VirtualCaveCrystalArrowEntity>of(VirtualCaveCrystalArrowEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<VirtualCaveCrystalThrowingBallEntity>> VIRTUAL_CAVE_CRYSTAL_THROWING_BALL = register("virtual_cave_crystal_throwing_ball",
			EntityType.Builder.<VirtualCaveCrystalThrowingBallEntity>of(VirtualCaveCrystalThrowingBallEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.3f, 0.3f));
	public static final RegistryObject<EntityType<VirtualCaveSpiderSetaeEntity>> VIRTUAL_CAVE_SPIDER_SETAE = register("projectile_virtual_cave_spider_setae",
			EntityType.Builder.<VirtualCaveSpiderSetaeEntity>of(VirtualCaveSpiderSetaeEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<VirtualCaveFishingNetEntity>> VIRTUAL_CAVE_FISHING_NET = register("virtual_cave_fishing_net",
			EntityType.Builder.<VirtualCaveFishingNetEntity>of(VirtualCaveFishingNetEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(1.0f, 1.0f));
	public static final RegistryObject<EntityType<PredationToothedFlowerShootEntity>> PREDATION_TOOTHED_FLOWER_SHOOT = register("predation_toothed_flower_shoot",
			EntityType.Builder.<PredationToothedFlowerShootEntity>of(PredationToothedFlowerShootEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.4f, 0.4f));
	public static final RegistryObject<EntityType<VirosaansManaSurgeEntity>> VIROSAANS_MANA_SURGE = register("virosaans_mana_surge",
			EntityType.Builder.<VirosaansManaSurgeEntity>of(VirosaansManaSurgeEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.3125F, 0.3125F));
	public static final RegistryObject<EntityType<AnestheticThrowingBallEntity>> ANESTHETIC_THROWING_BALL = register("anesthetic_throwing_ball",
			EntityType.Builder.<AnestheticThrowingBallEntity>of(AnestheticThrowingBallEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.3f, 0.3f));
	public static final RegistryObject<EntityType<ThrownWildernessThrowingSpearEntity>> THROWN_WILDERNESS_THROWING_SPEAR = register("projectile_throw_wilderness_throwing_spear",
			EntityType.Builder.<ThrownWildernessThrowingSpearEntity>of(ThrownWildernessThrowingSpearEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<ThrownWildernessBoomerangEntity>> THROWN_WILDERNESS_BOOMERANG = register("projectile_throw_wilderness_boomerang",
			EntityType.Builder.<ThrownWildernessBoomerangEntity>of(ThrownWildernessBoomerangEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<ZsieinSonicEntity>> ZSIEIN_SONIC = register("zsiein_sonic",
			EntityType.Builder.<ZsieinSonicEntity>of(ZsieinSonicEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(7f, 7f));
	//船
	public static final RegistryObject<EntityType<ABaseBoatEntity>> ABASE_BOAT = register("abase_boat",
			EntityType.Builder.<ABaseBoatEntity>of(ABaseBoatEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(1.375f, 0.5625f));
	public static final RegistryObject<EntityType<ABaseChestBoatEntity>> ABASE_CHEST_BOAT = register("abase_chest_boat",
			EntityType.Builder.<ABaseChestBoatEntity>of(ABaseChestBoatEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(1.375f, 0.5625f));
	//技术性
	public static final RegistryObject<EntityType<RainEffectCloudEntity>> RAIN_EFFECT_CLOUD = register("rain_effect_cloud",
			EntityType.Builder.<RainEffectCloudEntity>of(RainEffectCloudEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(6.0f, 0.5f));
	public static final RegistryObject<EntityType<CorrosiverBreathAreaEntity>> CORROSIVER_BREATH_AREA = register("corrosiver_breath_area",
			EntityType.Builder.<CorrosiverBreathAreaEntity>of(CorrosiverBreathAreaEntity::new, MobCategory.MISC).fireImmune().clientTrackingRange(10).updateInterval(Integer.MAX_VALUE)
					.sized(0.5F, 0.5F));
	public static final RegistryObject<EntityType<OceanCurrentBreathAreaEntity>> OCEAN_CURRENT_BREATH_AREA = register("ocean_current_breath_area",
			EntityType.Builder.<OceanCurrentBreathAreaEntity>of(OceanCurrentBreathAreaEntity::new, MobCategory.MISC).fireImmune().clientTrackingRange(10).updateInterval(Integer.MAX_VALUE)
					.sized(0.5F, 0.5F));
	public static final RegistryObject<EntityType<BloodyScreamAreaEntity>> BLOODY_SCREAM_AREA = register("bloody_scream_area",
			EntityType.Builder.<BloodyScreamAreaEntity>of(BloodyScreamAreaEntity::new, MobCategory.MISC).fireImmune().clientTrackingRange(10).updateInterval(Integer.MAX_VALUE)
					.sized(0.5F, 0.5F));
	public static final RegistryObject<EntityType<VirosaansWindOfSlaughterEntity>> VIROSAANS_WIND_OF_SLAUGHTER = register("virosaans_wind_of_slaughter",
			EntityType.Builder.<VirosaansWindOfSlaughterEntity>of(VirosaansWindOfSlaughterEntity::new, MobCategory.MISC).fireImmune().clientTrackingRange(10).updateInterval(Integer.MAX_VALUE)
					.sized(0.5F, 0.5F));
	public static final RegistryObject<EntityType<ChaoticDischargeEntity>> CHAOTIC_DISCHARGE = register("chaotic_discharge",
			EntityType.Builder.<ChaoticDischargeEntity>of(ChaoticDischargeEntity::new, MobCategory.MISC).fireImmune().clientTrackingRange(16).updateInterval(Integer.MAX_VALUE)
					.sized(0.5F, 0.5F));
	public static final RegistryObject<EntityType<TorchGazeEntity>> TORCH_GAZE = register("torch_gaze",
			EntityType.Builder.<TorchGazeEntity>of(TorchGazeEntity::new, MobCategory.MISC).fireImmune().clientTrackingRange(16).updateInterval(Integer.MAX_VALUE)
					.sized(0.5F, 1.0f));
	public static final RegistryObject<EntityType<CollapseZoneEntity>> COLLAPSE_ZONE = register("collapse_zone",
			EntityType.Builder.<CollapseZoneEntity>of(CollapseZoneEntity::new, MobCategory.MISC).fireImmune().clientTrackingRange(16).updateInterval(Integer.MAX_VALUE)
					.sized(0.5F, 1.0f));
	public static final RegistryObject<EntityType<DeadCenterEntity>> DEAD_CENTER = register("dead_center",
			EntityType.Builder.<DeadCenterEntity>of(DeadCenterEntity::new, MobCategory.MISC).fireImmune().clientTrackingRange(16).updateInterval(Integer.MAX_VALUE)
					.sized(0.5F, 0.5F));
	public static final RegistryObject<EntityType<SlaverySupervisorChainEntity>> SLAVERY_SUPERVISOR_CHAIN = register("slavery_supervisor_chain",
			EntityType.Builder.<SlaverySupervisorChainEntity>of(SlaverySupervisorChainEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.3125F, 0.3125F));
	public static final RegistryObject<EntityType<OminousGearEntity>> OMINOUS_GEAR = register("ominous_gear",
			EntityType.Builder.<OminousGearEntity>of(OminousGearEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(1.0f, 0.5f));
	public static final RegistryObject<EntityType<UncleanBloodRainEntity>> UNCLEAN_BLOOD_RAIN = register("unclean_blood_rain",
			EntityType.Builder.<UncleanBloodRainEntity>of(UncleanBloodRainEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(6.0f, 0.5f));
	public static final RegistryObject<EntityType<WonderlinPaintingEntity>> WONDERLIN_PAINTING = register("wonderlin_painting",
			EntityType.Builder.<WonderlinPaintingEntity>of(WonderlinPaintingEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(10).updateInterval(Integer.MAX_VALUE)
					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<BiteVinesEntity>> BITE_VINES = register("bite_vines",
			EntityType.Builder.of(BiteVinesEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.8f, 1.0f));
	public static final RegistryObject<EntityType<CorrosiveSnowCloudEntity>> CORROSIVE_SNOW_CLOUD = register("corrosive_snow_cloud",
			EntityType.Builder.<CorrosiveSnowCloudEntity>of(CorrosiveSnowCloudEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(6.0f, 0.5f));
	public static final RegistryObject<EntityType<MerorTrapEntity>> MEROR_TRAP = register("meror_trap",
			EntityType.Builder.<MerorTrapEntity>of(MerorTrapEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(0.5f, 1.5f));
	public static final RegistryObject<EntityType<ElectroflashEntity>> ELECTROFLASH = register("electroflash",
			EntityType.Builder.<ElectroflashEntity>of(ElectroflashEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).updateInterval(1)
					.sized(0.6f, 0.6f));
	public static final RegistryObject<EntityType<SkylanceDragonBeamEntity>> SKYLANCE_DRAGON_BEAM = register("skylance_dragon_beam",
			EntityType.Builder.<SkylanceDragonBeamEntity>of(SkylanceDragonBeamEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).updateInterval(1)
					.sized(1.5f, 1.5f));
	public static final RegistryObject<EntityType<VirosaansWindOfSlaughterEffectEntity>> VIROSAANS_WIND_OF_SLAUGHTER_EFFECT = register("virosaans_wind_of_slaughter_effect",
			EntityType.Builder.<VirosaansWindOfSlaughterEffectEntity>of(VirosaansWindOfSlaughterEffectEntity::new, MobCategory.MISC).fireImmune().clientTrackingRange(10).updateInterval(Integer.MAX_VALUE)
					.sized(0.15f, 0.15f));
	public static final RegistryObject<EntityType<VirosaansForceBurstBallEntity>> VIROSAANS_FORCE_BURST_BALL = register("virosaans_force_burst_ball",
			EntityType.Builder.<VirosaansForceBurstBallEntity>of(VirosaansForceBurstBallEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(1.0f, 1.0f));
	public static final RegistryObject<EntityType<TakeRootEntity>> TAKE_ROOT = register("take_root",
			EntityType.Builder.<TakeRootEntity>of(TakeRootEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(10)
					.sized(1.0f, 1.0f));
	public static final RegistryObject<EntityType<AstralJavelinPointEntity>> ASTRAL_JAVELIN_POINT = register("astral_javelin_point",
			EntityType.Builder.<AstralJavelinPointEntity>of(AstralJavelinPointEntity::new, MobCategory.MISC).fireImmune().clientTrackingRange(10)
					.sized(0.5F, 0.5F));
	//投影
	public static final RegistryObject<EntityType<BossShowEntity>> BOSS_SHOW_ENTITY_CORROSIVER = register("boss_show_entity_corrosiver",
			EntityType.Builder.of(BossShowEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.75f, 1.9375f));
	public static final RegistryObject<EntityType<BossShowEntity>> BOSS_SHOW_ENTITY_PURPLE_SAND_HAG = register("boss_show_entity_purple_sand_hag",
			EntityType.Builder.of(BossShowEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.75f, 1.9375f));
	public static final RegistryObject<EntityType<BossShowEntity>> BOSS_SHOW_ENTITY_GEMSTONE_MALIGNASAUR = register("boss_show_entity_gemstone_malignasaur",
			EntityType.Builder.of(BossShowEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.75f, 1.9375f));
	public static final RegistryObject<EntityType<BossShowEntity>> BOSS_SHOW_ENTITY_BRIGHT_LAND_BEAST = register("boss_show_entity_bright_land_beast",
			EntityType.Builder.of(BossShowEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.75f, 1.9375f));
	public static final RegistryObject<EntityType<BossShowEntity>> BOSS_SHOW_ENTITY_SERPON_COMBAT_EXECUTER = register("boss_show_entity_serpon_combat_executer",
			EntityType.Builder.of(BossShowEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.75f, 1.9375f));
	public static final RegistryObject<EntityType<BossShowEntity>> BOSS_SHOW_ENTITY_CELESTIAL_COILVINE = register("boss_show_entity_celestial_coilvine",
			EntityType.Builder.of(BossShowEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.75f, 1.9375f));
	public static final RegistryObject<EntityType<BossShowEntity>> BOSS_SHOW_ENTITY_SECOND_ROUNDER_GOLEM = register("boss_show_entity_second_rounder_golem",
			EntityType.Builder.of(BossShowEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.75f, 1.9375f));
	public static final RegistryObject<EntityType<BossShowEntity>> BOSS_SHOW_ENTITY_SEDIMENT_LORD = register("boss_show_entity_sediment_lord",
			EntityType.Builder.of(BossShowEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.75f, 1.9375f));
	public static final RegistryObject<EntityType<BossShowEntity>> BOSS_SHOW_ENTITY_PROSPEROUS_ENVOY = register("boss_show_entity_prosperous_envoy",
			EntityType.Builder.of(BossShowEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.75f, 1.9375f));
	public static final RegistryObject<EntityType<BossShowEntity>> BOSS_SHOW_ENTITY_OMINOUS_BANNER_PROJECTION = register("boss_show_entity_ominous_banner_projection",
			EntityType.Builder.of(BossShowEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.75f, 1.9375f));
	public static final RegistryObject<EntityType<BossShowEntity>> BOSS_SHOW_ENTITY_TYRANT_OF_THE_MOUNTAIN_REALM = register("boss_show_entity_tyrant_of_the_mountain_realm",
			EntityType.Builder.of(BossShowEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.75f, 1.9375f));


	private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> entityTypeBuilder.build(registryname));
	}
	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(SMART.get(), SmartEntity.createAttributes().build());
		event.put(SMART_BULGE.get(), SmartBulgeEntity.createAttributes().build());
		event.put(SPEAR_MACHINE.get(), SpearMachineEntity.createAttributes().build());
		event.put(UNICYCLE.get(), UnicycleEntity.createAttributes().build());
		event.put(CARVED_VILLAGER.get(), CarvedVillagerEntity.createAttributes().build());
		event.put(CARVED_HOUND.get(), CarvedHoundEntity.createAttributes().build());
		event.put(CARVED_IRON_GOLEM.get(), CarvedIronGolemEntity.createAttributes().build());
		event.put(CARVED_ALLAY.get(), CarvedAllayEntity.createAttributes().build());
		event.put(CARVED_LLAMA.get(), CarvedLlamaEntity.createAttributes().build());
		event.put(CORROSIVER.get(), CorrosiverEntity.createAttributes().build());
		event.put(VIEWER.get(), ViewerEntity.createAttributes().build());
		event.put(SECOND_ROUNDER.get(), SecondRounderEntity.createAttributes().build());
		event.put(MEROR.get(), MerorEntity.createAttributes().build());
		event.put(WILDERNESS_GIANT.get(), WildernessGiantEntity.createAttributes().build());
		event.put(WILDERNESS_GIANT_SKELETON.get(), WildernessGiantSkeletonEntity.createAttributes().build());
		event.put(CHIBAO.get(), ChibaoEntity.createAttributes().build());
		event.put(SERPON.get(), SerponEntity.createAttributes().build());
		event.put(QOAIKU.get(), QoaikuEntity.createAttributes().build());
		event.put(SECOND_ROUNDER_ZOMBIE.get(), SecondRounderZombieEntity.createAttributes().build());
		event.put(SECOND_ROUNDER_SKELETON.get(), SecondRounderSkeletonEntity.createAttributes().build());
		event.put(SECOND_ROUNDER_SPIDER.get(), SecondRounderSpiderEntity.createAttributes().build());
		event.put(ENDER_STOWAWAY.get(), EnderStowawayEntity.createAttributes().build());
		event.put(RESURRECTER.get(), ResurrecterEntity.createAttributes().build());
		event.put(SEDIMENT_GUARD.get(), SedimentGuardEntity.createAttributes().build());
		event.put(SEDIMENT_SOUL_STABILIZER.get(), SedimentSoulStabilizerEntity.createAttributes().build());
		event.put(HEAVY_ARMORED_SEDIMENT_GUARD.get(), HeavyArmoredSedimentGuardEntity.createAttributes().build());
		event.put(SEDIMENT_LORD.get(), SedimentLordEntity.createAttributes().build());
		event.put(PROSPEROUS_ENVOY.get(), ProsperousEnvoyEntity.createAttributes().build());
		event.put(BOUND_ZOMBIE_VILLAGER.get(), BoundZombieVillagerEntity.createAttributes().build());
		event.put(EXPLORER.get(), ExplorerEntity.createAttributes().build());
		event.put(EXECUTIONER.get(), ExecutionerEntity.createAttributes().build());
		event.put(MAPMAKER.get(), MapmakerEntity.createAttributes().build());
		event.put(DEFECTOR.get(), DefectorEntity.createAttributes().build());
		event.put(BANNER_BEARER.get(), BannerBearerEntity.createAttributes().build());
		event.put(BLASTER.get(), BlasterEntity.createAttributes().build());
		event.put(TRUMPETER.get(), TrumpeterEntity.createAttributes().build());
		event.put(TELEPORTER.get(), TeleporterEntity.createAttributes().build());
		event.put(CYCLONER.get(), CyclonerEntity.createAttributes().build());
		event.put(JAVELIN_THROWER.get(), JavelinThrowerEntity.createAttributes().build());
		event.put(ZOMBIE_KEEPER.get(), ZombieKeeperEntity.createAttributes().build());
		event.put(BITTER_COLD_SORCERER.get(), BitterColdSorcererEntity.createAttributes().build());
		event.put(BITTER_COLD_ALTAR.get(), BitterColdAltarEntity.createAttributes().build());
		event.put(FIRE_SPITTER.get(), FireSpitterEntity.createAttributes().build());
		event.put(WILD_FINDER.get(), WildFinderEntity.createAttributes().build());
		event.put(SUBMARINER.get(), SubmarinerEntity.createAttributes().build());
		event.put(LAMP_WIZARD.get(), LampWizardEntity.createAttributes().build());
		event.put(SLAVERY_SUPERVISOR.get(), SlaverySupervisorEntity.createAttributes().build());
		event.put(ANIMATED_CHAIN.get(), AnimatedChainEntity.createAttributes().build());
		event.put(FIREPOWER_POURER.get(), FirepowerPourerEntity.createAttributes().build());
		event.put(NECROMANCY_WARLOCK.get(), NecromancyWarlockEntity.createAttributes().build());
		event.put(UNCLEAN_TENTACLE.get(), UncleanTentacleEntity.createAttributes().build());
		event.put(GAVILER.get(), GavilerEntity.createAttributes().build());
		event.put(AX_CRAZY.get(), AxCrazyEntity.createAttributes().build());
		event.put(ADVENTURER.get(), AdventurerEntity.createAttributes().build());
		event.put(OMINOUS_BANNER_PROJECTION.get(), OminousBannerProjectionEntity.createAttributes().build());
		event.put(BITTER_COD.get(), BitterCodEntity.createAttributes().build());
		event.put(GIANT_MONSTER.get(), GiantMonsterEntity.createAttributes().build());
		event.put(BIG_WOOL_GEMSBOK.get(), BigWoolGemsbokEntity.createAttributes().build());
		event.put(FLUFFMOUND.get(), FluffmoundEntity.createAttributes().build());
		event.put(LITTLE_VELVET.get(), LittleVelvetEntity.createAttributes().build());
		event.put(MEGANOSE_SNOW_LIZARD.get(), MeganoseSnowLizardEntity.createAttributes().build());
		event.put(SNOWY_APE.get(), SnowyApeEntity.createAttributes().build());
		event.put(DRACOGNATHUS.get(), DracognathusEntity.createAttributes().build());
		event.put(BITTER_COLD_WIND.get(), BitterColdWindEntity.createAttributes().build());
		event.put(FROST_YETI.get(), FrostYetiEntity.createAttributes().build());
		event.put(PRIMORDIER.get(), PrimordierEntity.createAttributes().build());
		event.put(PURPLE_SAND_GECKO.get(), PurpleSandGeckoEntity.createAttributes().build());
		event.put(PURPLE_SAND_GECKO_TAIL.get(), PurpleSandGeckoTailEntity.createAttributes().build());
		event.put(SANDSTONE_BEAST.get(), SandstoneBeastEntity.createAttributes().build());
		event.put(PURPLE_SAND_RABBIT.get(), PurpleSandRabbitEntity.createAttributes().build());
		event.put(PURPLE_SAND_CAMEL.get(), PurpleSandCamelEntity.createAttributes().build());
		event.put(PURPLE_SAND_SCORPION.get(), PurpleSandScorpionEntity.createAttributes().build());
		event.put(PURPLE_SAND_CREEPER.get(), PurpleSandCreeperEntity.createAttributes().build());
		event.put(PURPLE_SAND_WITCH.get(), PurpleSandWitchEntity.createAttributes().build());
		event.put(WITCH_SCHOLAR.get(), WitchScholarEntity.createAttributes().build());
		event.put(BIG_WITCH.get(), BigWitchEntity.createAttributes().build());
		event.put(SPIRVE.get(), SpirveEntity.createAttributes().build());
		event.put(ROTTEN_DOG.get(), RottenDogEntity.createAttributes().build());
		event.put(LIGHTNING_WORM.get(), LightningWormEntity.createAttributes().build());
		event.put(LIGHTNING_WORM_PART.get(), BasePartLivingEntity.createAttributes().build());
		event.put(DUNESHOCKLER.get(), DuneshocklerEntity.createAttributes().build());
		event.put(DUNESHOCKLER_PART.get(), BasePartLivingEntity.createAttributes().build());
		event.put(COHORT_HAG.get(), CohortHagEntity.createAttributes().build());
		event.put(COVEN_HAG_ONE.get(), CovenHagEntity.createAttributes().build());
		event.put(COVEN_HAG_TWO.get(), CovenHagEntity.createAttributes().build());
		event.put(PURPLE_SAND_HAG.get(), PurpleSandHagEntity.createAttributes().build());
		event.put(PURPLE_SAND_PHANTOM.get(), PurpleSandPhantomEntity.createAttributes().build());
		event.put(GREYSCALE.get(), GreyscaleEntity.createAttributes().build());
		event.put(JEWELROAMER.get(), JewelroamerEntity.createAttributes().build());
		event.put(STARBRITE.get(), StarbriteEntity.createAttributes().build());
		event.put(ABYSSALER.get(), AbyssalerEntity.createAttributes().build());
		event.put(SOLISH.get(), SolishEntity.createAttributes().build());
		event.put(PRANCED_DOLPHIN.get(), PrancedDolphinEntity.createAttributes().build());
		event.put(BENTHICT.get(), BenthictEntity.createAttributes().build());
		event.put(MALIALOSAUR.get(), MalialosaurEntity.createAttributes().build());
		event.put(MALIGNASAUR.get(), MalignasaurEntity.createAttributes().build());
		event.put(QOAIKU_SEEKER.get(), QoaikuSeekerEntity.createAttributes().build());
		event.put(BULGE.get(), BulgeEntity.createAttributes().build());
		event.put(FURY_BULGE.get(), FuryBulgeEntity.createAttributes().build());
		event.put(SEAWAVE.get(), SeawaveEntity.createAttributes().build());
		event.put(FURY_SEAWAVE.get(), FurySeawaveEntity.createAttributes().build());
		event.put(GEMSTONE_MALIGNASAUR.get(), GemstoneMalignasaurEntity.createAttributes().build());
		event.put(BRIGHT_GRASS_CARP.get(), BrightGrassCarpEntity.createAttributes().build());
		event.put(CHROMASNAP_BASS.get(), ChromasnapBassEntity.createAttributes().build());
		event.put(LAKE_LOACH.get(), LakeLoachEntity.createAttributes().build());
		event.put(BRIGHT_MOUSE.get(), BrightMouseEntity.createAttributes().build());
		event.put(IRISHTAIL_CHICKEN.get(), IrishtailChickenEntity.createAttributes().build());
		event.put(SCALY_BEAR.get(), ScalyBearEntity.createAttributes().build());
		event.put(RUSHER.get(), RusherEntity.createAttributes().build());
		event.put(GLOW_SPARROW.get(), GlowSparrowEntity.createAttributes().build());
		event.put(SOFT_SPIRAL_SHEEP.get(), SoftSpiralSheepEntity.createAttributes().build());
		event.put(ARCHNOSED_HORNBEAST.get(), ArchnosedHornbeastEntity.createAttributes().build());
		event.put(PIGLIN_RESIDENT.get(), PiglinResidentEntity.createAttributes().build());
		event.put(TANGLER.get(), TanglerEntity.createAttributes().build());
		event.put(BRIGHT_LAND_BEAST.get(), BrightLandBeastEntity.createAttributes().build());
		event.put(SERPON_COLONIAL_SOLDIER.get(), SerponColonialSoldierEntity.createAttributes().build());
		event.put(SERPON_PIONEER.get(), SerponPioneerEntity.createAttributes().build());
		event.put(SERPON_RIOTER.get(), SerponRioterEntity.createAttributes().build());
		event.put(SERPON_INTRUDER.get(), SerponIntruderEntity.createAttributes().build());
		event.put(SERPON_FLAME_WIZARD.get(), SerponFlameWizardEntity.createAttributes().build());
		event.put(SERPON_FIRE_APOSTLE.get(), SerponFireApostleEntity.createAttributes().build());
		event.put(WAR_PYTHON.get(), WarPythonEntity.createAttributes().build());
		event.put(WAR_PYTHON_PART.get(), WarPythonBasePartLivingEntity.createAttributes().build());
		event.put(ROYAL_WAR_PYTHON.get(), RoyalWarPythonEntity.createAttributes().build());
		event.put(ROYAL_WAR_PYTHON_PART.get(), WarPythonBasePartLivingEntity.createAttributes().build());
		event.put(FLAMOAR.get(), FlamoarEntity.createAttributes().build());
		event.put(SERPON_CHARIOT.get(), SerponChariotEntity.createAttributes().build());
		event.put(SERPON_CROSSBOW_TURRET.get(), SerponCrossbowTurretEntity.createAttributes().build());
		event.put(SERPON_COMBAT_PLATFORM.get(), SerponCombatPlatformEntity.createAttributes().build());
		event.put(FLYING_FIREBALL.get(), FlyingFireballEntity.createAttributes().build());
		event.put(FURY_FLYING_FIREBALL.get(), FuryFlyingFireballEntity.createAttributes().build());
		event.put(DROUGHT_BLAZE.get(), DroughtBlazeEntity.createAttributes().build());
		event.put(FURY_DROUGHT_BLAZE.get(), FuryDroughtBlazeEntity.createAttributes().build());
		event.put(FIRE_SECRETOR.get(), FireSecretorEntity.createAttributes().build());
		event.put(DROUGHT_WILDFIRE.get(), DroughtWildfireEntity.createAttributes().build());
		event.put(FURY_DROUGHT_WILDFIRE.get(), FuryDroughtWildfireEntity.createAttributes().build());
		event.put(SERPON_COMBAT_EXECUTER.get(), SerponCombatExecuterEntity.createAttributes().build());
		event.put(HARMONIOUS_SLIME.get(), HarmoniousSlimeEntity.createAttributes().build());
		event.put(FLOWMINNOW.get(), FlowminnowEntity.createAttributes().build());
		event.put(WONDERLAND_YELLOW_DOG.get(), WonderlandYellowDogEntity.createAttributes().build());
		event.put(WONDERLAND_LI_CAT.get(), WonderlandLiCatEntity.createAttributes().build());
		event.put(WONDERLAND_RAMBLER.get(), WonderlandRamblerEntity.createAttributes().build());
		event.put(WONDERLAND_GIANT_FROG.get(), WonderlandGiantFrogEntity.createAttributes().build());
		event.put(WONDERLAND_GIANT_FROG_TADPOLE.get(), WonderlandGiantFrogTadpoleEntity.createAttributes().build());
		event.put(WONDERLAND_BRONCO.get(), WonderlandBroncoEntity.createAttributes().build());
		event.put(BOTANIZE.get(), BotanizeEntity.createAttributes().build());
		event.put(COILVINE_BOTANIZE.get(), CoilvineBotanizeEntity.createAttributes().build());
		event.put(WONDERLIN.get(), WonderlinEntity.createAttributes().build());
		event.put(WONDERLIN_MERCHANT.get(), WonderlinMerchantEntity.createAttributes().build());
		event.put(WONDERLIN_WARRIOR.get(), WonderlinWarriorEntity.createAttributes().build());
		event.put(THORN_SPIKE.get(), ThornSpikeEntity.createAttributes().build());
		event.put(TYRANT_OF_THE_MOUNTAIN_REALM.get(), TyrantOfTheMountainRealmEntity.createAttributes().build());
		event.put(COGON_SWORD.get(), CogonSwordEntity.createAttributes().build());
		event.put(IMPRISON_VINE.get(), ImprisonVineEntity.createAttributes().build());
		event.put(CELESTIAL_COILVINE_FLOWER.get(), CelestialCoilvineFlowerEntity.createAttributes().build());
		event.put(VINE_POD_BOMB.get(), VinePodBombEntity.createAttributes().build());
		event.put(VINEHEART_BOMB.get(), VineheartBombEntity.createAttributes().build());
		event.put(CELESTIAL_COILVINE.get(), CelestialCoilvineEntity.createAttributes().build());
		event.put(RAMBLER.get(), RamblerEntity.createAttributes().build());
		event.put(THORNY_TORTOISE.get(), ThornyTortoiseEntity.createAttributes().build());
		event.put(SUN_CRAB.get(), SunCrabEntity.createAttributes().build());
		event.put(BEACH_TERROR_BIRD.get(), BeachTerrorBirdEntity.createAttributes().build());
		event.put(RUINS_MOUSE.get(), RuinsMouseEntity.createAttributes().build());
		event.put(RUSTLE_FOX.get(), RustleFoxEntity.createAttributes().build());
		event.put(DUST_MOTH.get(), DustMothEntity.createAttributes().build());
		event.put(CLEANING_ROBOT.get(), CleaningRobotEntity.createAttributes().build());
		event.put(RUINS_BREEZE.get(), RuinsBreeze.createAttributes().build());
		event.put(MEROR_ADVENTURER.get(), MerorAdventurerEntity.createAttributes().build());
		event.put(MEROR_MERCHANT.get(), MerorMerchantEntity.createAttributes().build());
		event.put(MEROR_BOUNTY_HUNTER.get(), MerorBountyHunterEntity.createAttributes().build());
		event.put(MEROR_LOCATOR.get(), MerorLocatorEntity.createAttributes().build());
		event.put(MEROR_CUBE.get(), MerorCubeEntity.createAttributes().build());
		event.put(MEROR_MINISTROSITY.get(), MerorMinistrosityEntity.createAttributes().build());
		event.put(MEROR_OATHGUARD_MECH.get(), MerorOathguardMechEntity.createAttributes().build());
		event.put(MEROR_GOLEM.get(), MerorGolemEntity.createAttributes().build());
		event.put(MEROR_MONSTROSITY.get(), MerorMonstrosityEntity.createAttributes().build());
		event.put(MEROR_CHAMPION.get(), MerorChampionEntity.createAttributes().build());
		event.put(SECOND_ROUNDER_GOLEM.get(), OldSecondRounderGolemEntity.createAttributes().build());
		event.put(SECOND_ROUNDER_GOLEM_SWORD.get(), OldSecondRounderGolemEntity.createAttributes().build());
		event.put(DEEP_BAT.get(), DeepBatEntity.createAttributes().build());
		event.put(RUST_BEETLE.get(), RustBeetleEntity.createAttributes().build());
		event.put(FLOWING_DUST.get(), FlowingDustEntity.createAttributes().build());
		event.put(BLAMER.get(), BlamerEntity.createAttributes().build());
		event.put(IRON_TEETH.get(), IronTeethEntity.createAttributes().build());
		event.put(FURY_IRON_TEETH.get(), FuryIronTeethEntity.createAttributes().build());
		event.put(SECOND_ROUNDER_ANIMATED_ARMOR.get(), SecondRounderAnimatedArmorEntity.createAttributes().build());
		event.put(FURY_SECOND_ROUNDER_ANIMATED_ARMOR.get(), FurySecondRounderAnimatedArmorEntity.createAttributes().build());
		event.put(EYE_DISC.get(), EyeDiscEntity.createAttributes().build());
		event.put(DARISORDER.get(), DarisorderEntity.createAttributes().build());
		event.put(FURY_DARISORDER.get(), FuryDarisorderEntity.createAttributes().build());
		event.put(DOWNY_SPIDER.get(), DownySpiderEntity.createAttributes().build());
		event.put(VIRTUAL_CAVE_COCKROACH.get(), VirtualCaveCockroachEntity.createAttributes().build());
		event.put(BLUE_SHIMMER_SQUID.get(), BlueShimmerSquidEntity.createAttributes().build());
		event.put(VIRTUAL_CAVE_SPIDER.get(), VirtualCaveSpiderEntity.createAttributes().build());
		event.put(SLIDER.get(), SliderEntity.createAttributes().build());
		event.put(PREDATION_TOOTHED_FLOWER.get(), PredationToothedFlowerEntity.createAttributes().build());
		event.put(WILDERNESS_WOLF.get(), WildernessWolfEntity.createAttributes().build());
		event.put(WILDERNESS_RAMBLER.get(), WildernessRamblerEntity.createAttributes().build());
		event.put(WILDERNESS_BULL.get(), WildernessBullEntity.createAttributes().build());
		event.put(CLOUD_FUR_DEER.get(), CloudFurDeerEntity.createAttributes().build());
		event.put(BROWN_VENOMOUS_SLUG.get(), BrownVenomousSlugEntity.createAttributes().build());
		event.put(WILDERNESS_GIANT_TRAMP.get(), WildernessGiantTrampEntity.createAttributes().build());
		event.put(WILDERNESS_GIANT_HUNTER.get(), WildernessGiantHunterEntity.createAttributes().build());
		event.put(WOODLAND_HEART_GUARDIAN.get(), WoodlandHeartGuardianEntity.createAttributes().build());
		event.put(EAGLE_BOW.get(), EagleBowEntity.createAttributes().build());
		event.put(SCAVENGER.get(), ScavengerEntity.createAttributes().build());
		event.put(BLOODSUCKER.get(), BloodsuckerEntity.createAttributes().build());
		event.put(RIGIDER.get(), RigiderEntity.createAttributes().build());
		event.put(VARIANT_ZSIEIN.get(), OldVariantZsieinEntity.createAttributes().build());

		event.put(BOSS_SHOW_ENTITY_CORROSIVER.get(), BossShowEntity.createAttributes().build());
		event.put(BOSS_SHOW_ENTITY_PURPLE_SAND_HAG.get(), BossShowEntity.createAttributes().build());
		event.put(BOSS_SHOW_ENTITY_GEMSTONE_MALIGNASAUR.get(), BossShowEntity.createAttributes().build());
		event.put(BOSS_SHOW_ENTITY_BRIGHT_LAND_BEAST.get(), BossShowEntity.createAttributes().build());
		event.put(BOSS_SHOW_ENTITY_SERPON_COMBAT_EXECUTER.get(), BossShowEntity.createAttributes().build());
		event.put(BOSS_SHOW_ENTITY_CELESTIAL_COILVINE.get(), BossShowEntity.createAttributes().build());
		event.put(BOSS_SHOW_ENTITY_SECOND_ROUNDER_GOLEM.get(), BossShowEntity.createAttributes().build());
		event.put(BOSS_SHOW_ENTITY_SEDIMENT_LORD.get(), BossShowEntity.createAttributes().build());
		event.put(BOSS_SHOW_ENTITY_PROSPEROUS_ENVOY.get(), BossShowEntity.createAttributes().build());
		event.put(BOSS_SHOW_ENTITY_OMINOUS_BANNER_PROJECTION.get(), BossShowEntity.createAttributes().build());
		event.put(BOSS_SHOW_ENTITY_TYRANT_OF_THE_MOUNTAIN_REALM.get(), BossShowEntity.createAttributes().build());
	}
}