package com.jerotes.jerotesvillage.init;

import com.jerotes.jerotes.entity.Interface.ArmorEntity;
import com.jerotes.jerotes.init.JerotesItemsAdd;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.item.ItemGiantBeastArmor;
import com.jerotes.jerotes.item.ItemWarBeastArmor;
import com.jerotes.jerotes.item.Tool.ItemToolBaseShield;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.block.BrightGrassland.BrightLandDrum;
import com.jerotes.jerotesvillage.block.DamagedRuins.MerorProjectionTable;
import com.jerotes.jerotesvillage.block.DamagedRuins.MerorProjectionTableEntity;
import com.jerotes.jerotesvillage.block.EndlessOcean.GreyscalePrizeMachine;
import com.jerotes.jerotesvillage.block.ResurrectSediment.ResurrecterEmptyCoffin;
import com.jerotes.jerotesvillage.block.ResurrectSediment.SedimentLordEmptyCoffin;
import com.jerotes.jerotesvillage.entity.Boss.Biome.SerponCombatExecuterEntity;
import com.jerotes.jerotesvillage.entity.Other.Machine.SerponChariotEntity;
import com.jerotes.jerotesvillage.entity.Neutral.CleaningRobotEntity;
import com.jerotes.jerotesvillage.entity.Other.Boat.ABaseBoatEntity;
import com.jerotes.jerotesvillage.entity.Other.Boat.ABaseBoatType;
import com.jerotes.jerotesvillage.entity.Other.Boat.ABaseChestBoatEntity;
import com.jerotes.jerotesvillage.entity.Other.Other.PrimedSerponClassicalBombEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.*;
import com.jerotes.jerotesvillage.entity.Shoot.Other.OminousBombEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Other.PurpleSandAlchemyBombEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Other.PurpleSandExplosiveAlchemyBombEntity;
import com.jerotes.jerotesvillage.entity.Shoot.ThrowingBall.*;
import com.jerotes.jerotesvillage.item.*;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class JerotesVillageItems implements JerotesItemsAdd {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, JerotesVillage.MODID);

	public static final RegistryObject<Item> JEROTESVILLAGE = REGISTRY.register("jerotesvillage", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> SECOND_ROUND_WORLD_EXPLORATION_GUIDE = REGISTRY.register("second_round_world_exploration_guide", () -> new SecondRoundWorldExplorationGuide());
	public static final RegistryObject<Item> THE_VILLAGE_CHRONICLE = REGISTRY.register("the_village_chronicle", () -> new TheVillageChronicle());

	public static final RegistryObject<Item> AASTRONG_GRASS_BLOCK = fireResistanceEpicBlock(JerotesVillageBlocks.AASTRONG_GRASS_BLOCK);
	public static final RegistryObject<Item> AASTRONG_DIRT = fireResistanceEpicBlock(JerotesVillageBlocks.AASTRONG_DIRT);

	public static final RegistryObject<Item> SMART_SPAWN_EGG = REGISTRY.register("smart_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SMART, 0xeeeeee, 0xeeeeee, new Item.Properties()));
	public static final RegistryObject<Item> SMART_BULGE_SPAWN_EGG = REGISTRY.register("smart_bulge_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SMART_BULGE, -9384961, -15907170, new Item.Properties()));

	public static final RegistryObject<Item> VILLAGER_METAL_BLOCK = block(JerotesVillageBlocks.VILLAGER_METAL_BLOCK);
	public static final RegistryObject<Item> VILLAGER_METAL_BASE_BLOCK = block(JerotesVillageBlocks.VILLAGER_METAL_BASE_BLOCK);
	public static final RegistryObject<Item> VILLAGER_METAL_DOOR = highBlock(JerotesVillageBlocks.VILLAGER_METAL_DOOR);
	public static final RegistryObject<Item> VILLAGER_METAL_TRAPDOOR = block(JerotesVillageBlocks.VILLAGER_METAL_TRAPDOOR);
	public static final RegistryObject<Item> VILLAGER_METAL_PRESSURE_PLATE = block(JerotesVillageBlocks.VILLAGER_METAL_PRESSURE_PLATE);
	public static final RegistryObject<Item> CARVED_BRICKS = block(JerotesVillageBlocks.CARVED_BRICKS);
	public static final RegistryObject<Item> CARVED_BRICK_STAIRS = block(JerotesVillageBlocks.CARVED_BRICK_STAIRS);
	public static final RegistryObject<Item> CARVED_BRICK_SLAB = block(JerotesVillageBlocks.CARVED_BRICK_SLAB);
	public static final RegistryObject<Item> CARVED_BRICK_WALL = block(JerotesVillageBlocks.CARVED_BRICK_WALL);
	public static final RegistryObject<Item> CARVED_VILLAGER_CONCERTINA_WIRE = block(JerotesVillageBlocks.CARVED_VILLAGER_CONCERTINA_WIRE);
	public static final RegistryObject<Item> CARVED_LODESTONE = block(JerotesVillageBlocks.CARVED_LODESTONE);
	public static final RegistryObject<Item> SECOND_ROUND_WORLD_BLOCK = block(JerotesVillageBlocks.SECOND_ROUND_WORLD_BLOCK);
	public static final RegistryObject<Item> SECOND_ROUND_WORLD_PORTAL_BLOCK = block(JerotesVillageBlocks.SECOND_ROUND_WORLD_PORTAL_BLOCK);
	public static final RegistryObject<Item> VILLAGER_METAL_INGOT = REGISTRY.register("villager_metal_ingot", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> VILLAGER_METAL_BASE = REGISTRY.register("villager_metal_base", () -> new VillagerMetalBase());
	public static final RegistryObject<Item> SCRAP_VILLAGER_METAL = REGISTRY.register("scrap_villager_metal", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> SECOND_ROUND_WORLD_KEY = REGISTRY.register("second_round_world_key", () -> new SecondRoundWorldKey());
	public static final RegistryObject<Item> VILLAGER_METAL_MORTAR_AND_PESTLE = REGISTRY.register("villager_metal_mortar_and_pestle", () -> new VillagerMetalMortarAndPestle());
	public static final RegistryObject<Item> VILLAGER_METAL_SYRINGE = REGISTRY.register("villager_metal_syringe", () -> new VillagerMetalSyringe());
	public static final RegistryObject<Item> VILLAGER_METAL_JAVELIN = REGISTRY.register("villager_metal_javelin", () -> new VillagerMetalJavelin());
	public static final RegistryObject<Item> VILLAGER_METAL_WAR_BEAST_ARMOR = REGISTRY.register("villager_metal_war_beast_armor", () -> new ItemWarBeastArmor(12, 2, 0, 0x9a2a00, JerotesVillage.MODID, "villager_metal", new Item.Properties().stacksTo(1).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> VILLAGER_METAL_GIANT_BEAST_ARMOR = REGISTRY.register("villager_metal_giant_beast_armor", () -> new ItemGiantBeastArmor(14, 3, 0, 0x9a2a00, JerotesVillage.MODID, "villager_metal", new Item.Properties().stacksTo(1).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> VILLAGER_METAL_SWORD = REGISTRY.register("villager_metal_sword", () -> new VillagerMetalSword());
	public static final RegistryObject<Item> VILLAGER_METAL_AXE = REGISTRY.register("villager_metal_axe", () -> new VillagerMetalAxe());
	public static final RegistryObject<Item> VILLAGER_METAL_PICKAXE = REGISTRY.register("villager_metal_pickaxe", () -> new VillagerMetalPickaxe());
	public static final RegistryObject<Item> VILLAGER_METAL_SHOVEL = REGISTRY.register("villager_metal_shovel", () -> new VillagerMetalShovel());
	public static final RegistryObject<Item> VILLAGER_METAL_HOE = REGISTRY.register("villager_metal_hoe", () -> new VillagerMetalHoe());
	public static final RegistryObject<Item> VILLAGER_METAL_DAGGER = REGISTRY.register("villager_metal_dagger", () -> new VillagerMetalDagger());
	public static final RegistryObject<Item> VILLAGER_METAL_HAMMER = REGISTRY.register("villager_metal_hammer", () -> new VillagerMetalHammer());
	public static final RegistryObject<Item> VILLAGER_METAL_GREATSWORD = REGISTRY.register("villager_metal_greatsword", () -> new VillagerMetalGreatsword());
	public static final RegistryObject<Item> VILLAGER_METAL_SPEAR = REGISTRY.register("villager_metal_spear", () -> new VillagerMetalSpear());
	public static final RegistryObject<Item> VILLAGER_METAL_PIKE = REGISTRY.register("villager_metal_pike", () -> new VillagerMetalPike());
	public static final RegistryObject<Item> VILLAGER_METAL_HORSEWHIP = REGISTRY.register("villager_metal_horsewhip", () -> new VillagerMetalHorseWhip());
	public static final RegistryObject<Item> VILLAGER_METAL_BOW = REGISTRY.register("villager_metal_bow", () -> new VillagerMetalBow());
	public static final RegistryObject<Item> VILLAGER_METAL_CROSSBOW = REGISTRY.register("villager_metal_crossbow", () -> new VillagerMetalCrossbow());
	public static final RegistryObject<Item> CARVED_VILLAGER_SPECTRAL_ARROW = REGISTRY.register("carved_villager_spectral_arrow", () -> new CarvedVillagerSpectralArrow());
	public static final RegistryObject<Item> VILLAGER_METAL_SHILED = REGISTRY.register("villager_metal_shield", () -> new ItemToolBaseShield(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON).durability(450 * 3), 3f, 1.2f, 17,  Ingredient.of(JerotesVillageItems.VILLAGER_METAL_INGOT.get())));
	public static final RegistryObject<Item> VILLAGER_METAL_HELMET = REGISTRY.register("villager_metal_helmet", () -> new VillagerMetalArmor.Helmet());
	public static final RegistryObject<Item> VILLAGER_METAL_CHESTPLATE = REGISTRY.register("villager_metal_chestplate", () -> new VillagerMetalArmor.Chestplate());
	public static final RegistryObject<Item> VILLAGER_METAL_LEGGINGS = REGISTRY.register("villager_metal_leggings", () -> new VillagerMetalArmor.Leggings());
	public static final RegistryObject<Item> VILLAGER_METAL_BOOTS = REGISTRY.register("villager_metal_boots", () -> new VillagerMetalArmor.Boots());
	public static final RegistryObject<Item> CARVED_LEADER_UPGRADE_SMITHING_TEMPLATE = REGISTRY.register("carved_leader_upgrade_smithing_template", () -> new CarvedLeaderUpgradeSmithingTemplate());
	public static final RegistryObject<Item> CARVED_LEADER_HELMET = REGISTRY.register("carved_leader_helmet", () -> new CarvedLeaderArmor.Helmet());
	public static final RegistryObject<Item> CARVED_LEADER_CHESTPLATE = REGISTRY.register("carved_leader_chestplate", () -> new CarvedLeaderArmor.Chestplate());
	public static final RegistryObject<Item> CARVED_LEADER_LEGGINGS = REGISTRY.register("carved_leader_leggings", () -> new CarvedLeaderArmor.Leggings());
	public static final RegistryObject<Item> CARVED_LEADER_BOOTS = REGISTRY.register("carved_leader_boots", () -> new CarvedLeaderArmor.Boots());
	public static final RegistryObject<Item> SHRAPNEL_LAUNCHER = REGISTRY.register("shrapnel_launcher", () -> new ShrapnelLauncher());
	public static final RegistryObject<Item> THERAPEUTIC_TABLET = REGISTRY.register("therapeutic_tablet", () -> new ItemTabletBase(new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON), MobEffects.REGENERATION, 1600, 1, MobEffects.ABSORPTION, 1600, 1, false, 16711720, 16777215));
	public static final RegistryObject<Item> FIRM_TABLET = REGISTRY.register("firm_tablet", () -> new ItemTabletBase(new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON), MobEffects.DAMAGE_RESISTANCE, 1600, 1, MobEffects.HEALTH_BOOST, 1600, 1, false, 0x4d66d7, 16777215));
	public static final RegistryObject<Item> VIOLENT_TABLET = REGISTRY.register("violent_tablet", () -> new ItemTabletBase(new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON), MobEffects.DAMAGE_BOOST, 1600, 1, MobEffects.DIG_SPEED, 1600, 1, false, 16750080, 16777215));
	public static final RegistryObject<Item> DEXTEROUS_TABLET = REGISTRY.register("dexterous_tablet", () -> new ItemTabletBase(new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON), MobEffects.MOVEMENT_SPEED, 1600, 1, MobEffects.JUMP, 1600, 1, false, 6946560, 16777215));
	public static final RegistryObject<Item> DEBILITATED_TABLET = REGISTRY.register("debilitated_tablet", () -> new ItemTabletBase(new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON), MobEffects.WEAKNESS, 1600, 1, MobEffects.DIG_SLOWDOWN, 1600, 1, true, 16121600, 5658198));
	public static final RegistryObject<Item> ABLEPSIA_TABLET = REGISTRY.register("ablepsia_tablet", () -> new ItemTabletBase(new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON), MobEffects.BLINDNESS, 1600, 0, MobEffects.DARKNESS, 1600, 0, true, 56319, 5658198));
	public static final RegistryObject<Item> CERTIFICATE_OF_HONOR_FOR_HERO_OF_THE_VILLAGE = REGISTRY.register("certificate_of_honor_for_hero_of_the_village", () -> new CertificateofHonorforHeroofTheVillage());
	public static final RegistryObject<Item> DOLPHIN_COPPER_DECORATION = REGISTRY.register("dolphin_copper_decoration", () -> new ItemBaseGiveEffect(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), MobEffects.DOLPHINS_GRACE, 365, 0, 1));
	public static final RegistryObject<Item> ILLAGER_SIGNAL_LIGHT = REGISTRY.register("illager_signal_light", () -> new IllagerSignalLight());
	public static final RegistryObject<Item> ILLAGER_SIGNAL_LIGHT_USE = REGISTRY.register("illager_signal_light_use", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> CARVED_FLAG = REGISTRY.register("carved_flag", () -> new CarvedFlag());
	public static final RegistryObject<Item> CARVED_CONTACTER = REGISTRY.register("carved_contacter", () -> new CarvedContacter());
	public static final RegistryObject<Item> CARVED_HORSE_SLAB = REGISTRY.register("carved_horse_slab", () -> new CarvedHorseSlab());
	public static final RegistryObject<Item> CARVED_HORN = REGISTRY.register("carved_horn", () -> new CarvedHorn());
	public static final RegistryObject<Item> ARMOR_STAND_ARM_SET = REGISTRY.register("armor_stand_arm_set", () -> new ArmorStandArmSet());
	public static final RegistryObject<Item> SPEAR_MACHINE_ITEM = REGISTRY.register("spear_machine_item", () -> new SpearMachineItem());
	public static final RegistryObject<Item> UNICYCLE_ITEM = REGISTRY.register("unicycle_item", () -> new UnicycleItem());
	public static final RegistryObject<Item> VILLAGER_INVITATION = REGISTRY.register("villager_invitation", () -> new VillagerInvitation());
	public static final RegistryObject<Item> CARVED_VILLAGER_ITEM = REGISTRY.register("carved_villager_item", () -> new CarvedVillagerItem());
	public static final RegistryObject<Item> CARVED_DOG_WHISTLE = REGISTRY.register("carved_dog_whistle", () -> new CarvedDogWhistle());
	public static final RegistryObject<Item> CARVED_IRON_GOLEM_CORE = REGISTRY.register("carved_iron_golem_core", () -> new CarvedIronGolemCore());
	public static final RegistryObject<Item> CARVED_AMETHYST_FLUTE = REGISTRY.register("carved_amethyst_flute", () -> new CarvedAmethystFlute());
	public static final RegistryObject<Item> CARVED_LEAD = REGISTRY.register("carved_lead", () -> new CarvedLead());
	public static final RegistryObject<Item> SPEAR_MACHINE_SPAWN_EGG = REGISTRY.register("spear_machine_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SPEAR_MACHINE, -7039080, -5946606, new Item.Properties()));
	public static final RegistryObject<Item> UNICYCLE_SPAWN_EGG = REGISTRY.register("unicycle_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.UNICYCLE, -4821220, -4022942, new Item.Properties()));
	public static final RegistryObject<Item> CARVED_VILLAGER_SPAWN_EGG = REGISTRY.register("carved_villager_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.CARVED_VILLAGER, -6724096, -10053376, new Item.Properties()));
	public static final RegistryObject<Item> CARVED_HOUND_SPAWN_EGG = REGISTRY.register("carved_hound_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.CARVED_HOUND, -6338292, -10053376, new Item.Properties()));
	public static final RegistryObject<Item> CARVED_IRON_GOLEM_SPAWN_EGG = REGISTRY.register("carved_iron_golem_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.CARVED_IRON_GOLEM, -8037091, -10053376, new Item.Properties()));
	public static final RegistryObject<Item> CARVED_ALLAY_SPAWN_EGG = REGISTRY.register("carved_allay_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.CARVED_ALLAY, -32222, -10053376, new Item.Properties()));
	public static final RegistryObject<Item> CARVED_LLAMA_SPAWN_EGG = REGISTRY.register("carved_llama_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.CARVED_LLAMA, 13663305, -10053376, new Item.Properties()));

	public static final RegistryObject<Item> VILLAGER_BLOCK = block(JerotesVillageBlocks.VILLAGER_BLOCK);
	public static final RegistryObject<Item> VILLAGER_BLOCK_WITCH = block(JerotesVillageBlocks.VILLAGER_BLOCK_WITCH);
	public static final RegistryObject<Item> VILLAGER_BLOCK_OBSERVER = block(JerotesVillageBlocks.VILLAGER_BLOCK_OBSERVER);
	public static final RegistryObject<Item> VILLAGER_BLOCK_SHOPKEEPER = block(JerotesVillageBlocks.VILLAGER_BLOCK_SHOPKEEPER);
	public static final RegistryObject<Item> VILLAGER_BLOCK_VILLAGEHEAD = block(JerotesVillageBlocks.VILLAGER_BLOCK_VILLAGEHEAD);
	public static final RegistryObject<Item> VILLAGER_BLOCK_FORGER = block(JerotesVillageBlocks.VILLAGER_BLOCK_FORGER);
	public static final RegistryObject<Item> VILLAGER_BLOCK_CHEF = block(JerotesVillageBlocks.VILLAGER_BLOCK_CHEF);
	public static final RegistryObject<Item> VILLAGER_BLOCK_HUNTER = block(JerotesVillageBlocks.VILLAGER_BLOCK_HUNTER);
	public static final RegistryObject<Item> VILLAGER_BLOCK_PHYSICIAN = block(JerotesVillageBlocks.VILLAGER_BLOCK_PHYSICIAN);
	public static final RegistryObject<Item> VILLAGER_BLOCK_CONSCRIPTION = block(JerotesVillageBlocks.VILLAGER_BLOCK_CONSCRIPTION);
	public static final RegistryObject<Item> VILLAGER_BLOCK_BARTENDER = block(JerotesVillageBlocks.VILLAGER_BLOCK_BARTENDER);
	public static final RegistryObject<Item> VILLAGER_BLOCK_LAMPLIGHTER = block(JerotesVillageBlocks.VILLAGER_BLOCK_LAMPLIGHTER);
	public static final RegistryObject<Item> VILLAGER_BLOCK_HORSEMAN = block(JerotesVillageBlocks.VILLAGER_BLOCK_HORSEMAN);
	public static final RegistryObject<Item> VILLAGER_BLOCK_COACHMAN = block(JerotesVillageBlocks.VILLAGER_BLOCK_COACHMAN);
	public static final RegistryObject<Item> VILLAGER_BLOCK_BUILDER = block(JerotesVillageBlocks.VILLAGER_BLOCK_BUILDER);
	public static final RegistryObject<Item> VILLAGER_BLOCK_BREEDER = block(JerotesVillageBlocks.VILLAGER_BLOCK_BREEDER);
	public static final RegistryObject<Item> VILLAGER_BLOCK_HAWKER = block(JerotesVillageBlocks.VILLAGER_BLOCK_HAWKER);
	public static final RegistryObject<Item> VILLAGER_BLOCK_JAILER = block(JerotesVillageBlocks.VILLAGER_BLOCK_JAILER);
	public static final RegistryObject<Item> VILLAGER_BLOCK_PISCATOR = block(JerotesVillageBlocks.VILLAGER_BLOCK_PISCATOR);
	public static final RegistryObject<Item> VILLAGER_BLOCK_MAGICIAN = block(JerotesVillageBlocks.VILLAGER_BLOCK_MAGICIAN);
	public static final RegistryObject<Item> VILLAGER_BLOCK_CAPTAIN = block(JerotesVillageBlocks.VILLAGER_BLOCK_CAPTAIN);
	public static final RegistryObject<Item> VILLAGER_BLOCK_MESSENGER = block(JerotesVillageBlocks.VILLAGER_BLOCK_MESSENGER);
	public static final RegistryObject<Item> VILLAGER_BLOCK_DYE_MERCHANT = block(JerotesVillageBlocks.VILLAGER_BLOCK_DYE_MERCHANT);
	public static final RegistryObject<Item> VILLAGER_BLOCK_ILLAGER = block(JerotesVillageBlocks.VILLAGER_BLOCK_ILLAGER);
	public static final RegistryObject<Item> VILLAGER_BLOCK_PIGLIN = block(JerotesVillageBlocks.VILLAGER_BLOCK_PIGLIN);
	public static final RegistryObject<Item> VILLAGER_BLOCK_BEEKEEPER = block(JerotesVillageBlocks.VILLAGER_BLOCK_BEEKEEPER);
	//冒险
	public static final RegistryObject<Item> SECOND_ROUND_ANCHOR_CORE = uncommonBlock(JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE);
	public static final RegistryObject<Item> SECOND_ROUNDER_ZOMBIE_HEAD = REGISTRY.register("second_rounder_zombie_head", () -> new StandingAndWallBlockItem(JerotesVillageBlocks.SECOND_ROUNDER_ZOMBIE_HEAD.get(), JerotesVillageBlocks.SECOND_ROUNDER_ZOMBIE_WALL_HEAD.get(), new Item.Properties().rarity(Rarity.UNCOMMON), Direction.DOWN));
	public static final RegistryObject<Item> SECOND_ROUNDER_SKELETON_SKULL = REGISTRY.register("second_rounder_skeleton_skull", () -> new StandingAndWallBlockItem(JerotesVillageBlocks.SECOND_ROUNDER_SKELETON_SKULL.get(), JerotesVillageBlocks.SECOND_ROUNDER_SKELETON_WALL_SKULL.get(), new Item.Properties().rarity(Rarity.UNCOMMON), Direction.DOWN));
	public static final RegistryObject<Item> VIEWER_SPAWN_EGG = REGISTRY.register("viewer_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.VIEWER, 1111111, 1111111, new Item.Properties()));
	public static final RegistryObject<Item> SECOND_ROUNDER_SPAWN_EGG = REGISTRY.register("second_rounder_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SECOND_ROUNDER, -14409158, -15305728, new Item.Properties()));
	public static final RegistryObject<Item> MEROR_SPAWN_EGG = REGISTRY.register("meror_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.MEROR, -7918531, -6911841, new Item.Properties()));
	public static final RegistryObject<Item> WILDERNESS_GIANT_SPAWN_EGG = REGISTRY.register("wilderness_giant_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.WILDERNESS_GIANT, -8819625, -11781851, new Item.Properties()));
	public static final RegistryObject<Item> WILDERNESS_GIANT_SKELETON_SPAWN_EGG = REGISTRY.register("wilderness_giant_skeleton_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.WILDERNESS_GIANT_SKELETON, 0x80756f, 0Xb0a8a0, new Item.Properties()));
	public static final RegistryObject<Item> CHIBAO_SPAWN_EGG = REGISTRY.register("chibao_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.CHIBAO, -2902672, -9280958, new Item.Properties()));
	public static final RegistryObject<Item> SERPON_SPAWN_EGG = REGISTRY.register("serpon_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SERPON, -13261, -1464246, new Item.Properties()));
	public static final RegistryObject<Item> QOAIKU_SPAWN_EGG = REGISTRY.register("qoaiku_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.QOAIKU, -16408594, -15994912, new Item.Properties()));
	public static final RegistryObject<Item> SECOND_ROUNDER_ZOMBIE_SPAWN_EGG = REGISTRY.register("second_rounder_zombie_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SECOND_ROUNDER_ZOMBIE, -5066858, -9473962, new Item.Properties()));
	public static final RegistryObject<Item> SECOND_ROUNDER_SKELETON_SPAWN_EGG = REGISTRY.register("second_rounder_skeleton_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SECOND_ROUNDER_SKELETON, -3753310, -9543093, new Item.Properties()));
	public static final RegistryObject<Item> SECOND_ROUNDER_SPIDER_SPAWN_EGG = REGISTRY.register("second_rounder_spider_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SECOND_ROUNDER_SPIDER, 4933462, 11013646, new Item.Properties()));
	public static final RegistryObject<Item> ENDER_STOWAWAY_SPAWN_EGG = REGISTRY.register("ender_stowaway_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.ENDER_STOWAWAY, 1447446, -9894932, new Item.Properties()));
	//沉降
	public static final RegistryObject<Item> RESURRECTER_COFFIN = block(JerotesVillageBlocks.RESURRECTER_COFFIN);
	public static final RegistryObject<Item> RESURRECTER_EMPTY_COFFIN = block(JerotesVillageBlocks.RESURRECTER_EMPTY_COFFIN);
	public static final RegistryObject<Item> SEDIMENT_LORD_COFFIN = block(JerotesVillageBlocks.SEDIMENT_LORD_COFFIN);
	public static final RegistryObject<Item> SEDIMENT_LORD_EMPTY_COFFIN = block(JerotesVillageBlocks.SEDIMENT_LORD_EMPTY_COFFIN);
	public static final RegistryObject<Item> SEDIMENT_COFFIN = block(JerotesVillageBlocks.SEDIMENT_COFFIN);
	public static final RegistryObject<Item> SEDIMENT_MUD_BRICKS = block(JerotesVillageBlocks.SEDIMENT_MUD_BRICKS);
	public static final RegistryObject<Item> SEDIMENT_MUD_BRICK_STAIRS = block(JerotesVillageBlocks.SEDIMENT_MUD_BRICK_STAIRS);
	public static final RegistryObject<Item> SEDIMENT_MUD_BRICK_SLAB = block(JerotesVillageBlocks.SEDIMENT_MUD_BRICK_SLAB);
	public static final RegistryObject<Item> SEDIMENT_MUD_BRICK_WALL = block(JerotesVillageBlocks.SEDIMENT_MUD_BRICK_WALL);
	public static final RegistryObject<Item> SEDIMENT_MOISTURE_PROOF_PASTE = block(JerotesVillageBlocks.SEDIMENT_MOISTURE_PROOF_PASTE);
	public static final RegistryObject<Item> SEDIMENT_URN = block(JerotesVillageBlocks.SEDIMENT_URN);
	public static final RegistryObject<Item> RESURRECTER_HEAD = REGISTRY.register("resurrecter_head", () -> new StandingAndWallBlockItem(JerotesVillageBlocks.RESURRECTER_HEAD.get(), JerotesVillageBlocks.RESURRECTER_WALL_HEAD.get(), new Item.Properties().rarity(Rarity.UNCOMMON), Direction.DOWN));
	public static final RegistryObject<Item> SEDIMENT_CATACOMBS_MAP = REGISTRY.register("sediment_catacombs_map", () -> new ItemMagicMap(new Item.Properties().stacksTo(1).rarity(Rarity.RARE), "sediment_catacombs"));
	public static final RegistryObject<Item> ANCIENT_SECOND_ROUNDER_DAGGER = REGISTRY.register("ancient_second_rounder_dagger", () -> new AncientSecondRounderDagger());
	public static final RegistryObject<Item> ANCIENT_SECOND_ROUNDER_SWORD = REGISTRY.register("ancient_second_rounder_sword", () -> new AncientSecondRounderSword());
	public static final RegistryObject<Item> ANCIENT_SECOND_ROUNDER_WAR_AXE = REGISTRY.register("ancient_second_rounder_war_axe", () -> new AncientSecondRounderWarAxe());
	public static final RegistryObject<Item> ANCIENT_SECOND_ROUNDER_MACE = REGISTRY.register("ancient_second_rounder_mace", () -> new AncientSecondRounderMace());
	public static final RegistryObject<Item> ANCIENT_SECOND_ROUNDER_GREATSWORD = REGISTRY.register("ancient_second_rounder_greatsword", () -> new AncientSecondRounderGreatsword());
	public static final RegistryObject<Item> ANCIENT_SECOND_ROUNDER_BATTLEAXE = REGISTRY.register("ancient_second_rounder_battleaxe", () -> new AncientSecondRounderBattleaxe());
	public static final RegistryObject<Item> ANCIENT_SECOND_ROUNDER_WARHAMMER = REGISTRY.register("ancient_second_rounder_warhammer", () -> new AncientSecondRounderWarhammer());
	public static final RegistryObject<Item> ANCIENT_SECOND_ROUNDER_WARSPEAR = REGISTRY.register("ancient_second_rounder_warspear", () -> new AncientSecondRounderWarspear());
	public static final RegistryObject<Item> ANCIENT_SECOND_ROUNDER_PIKE = REGISTRY.register("ancient_second_rounder_pike", () -> new AncientSecondRounderPike());
	public static final RegistryObject<Item> ANCIENT_SECOND_ROUNDER_PINCER = REGISTRY.register("ancient_second_rounder_pincer", () -> new AncientSecondRounderPincer());
	public static final RegistryObject<Item> ANCIENT_SECOND_ROUNDER_BOW = REGISTRY.register("ancient_second_rounder_bow", () -> new AncientSecondRounderBow());
	public static final RegistryObject<Item> ANCIENT_SECOND_ROUNDER_ARROW = REGISTRY.register("ancient_second_rounder_arrow", () -> new AncientSecondRounderArrow());
	public static final RegistryObject<Item> ANCIENT_SECOND_ROUNDER_SHIELD = REGISTRY.register("ancient_second_rounder_shield", () -> new ItemToolBaseShield(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON).durability(300 * 3), 2f, 1.2f, 15, Ingredient.of(Items.IRON_INGOT)));
	public static final RegistryObject<Item> ANCIENT_SECOND_ROUNDER_HELMET = REGISTRY.register("ancient_second_rounder_helmet", () -> new AncientSecondRounderArmor.Helmet());
	public static final RegistryObject<Item> ANCIENT_SECOND_ROUNDER_CHESTPLATE = REGISTRY.register("ancient_second_rounder_chestplate", () -> new AncientSecondRounderArmor.Chestplate());
	public static final RegistryObject<Item> ANCIENT_SECOND_ROUNDER_LEGGINGS = REGISTRY.register("ancient_second_rounder_leggings", () -> new AncientSecondRounderArmor.Leggings());
	public static final RegistryObject<Item> ANCIENT_SECOND_ROUNDER_BOOTS = REGISTRY.register("ancient_second_rounder_boots", () -> new AncientSecondRounderArmor.Boots());
	public static final RegistryObject<Item> ANCIENT_SECOND_ROUNDER_HEAVY_HELMET = REGISTRY.register("ancient_second_rounder_heavy_helmet", () -> new AncientSecondRounderHeavyArmor.Helmet());
	public static final RegistryObject<Item> ANCIENT_SECOND_ROUNDER_HEAVY_CHESTPLATE = REGISTRY.register("ancient_second_rounder_heavy_chestplate", () -> new AncientSecondRounderHeavyArmor.Chestplate());
	public static final RegistryObject<Item> ANCIENT_SECOND_ROUNDER_HEAVY_LEGGINGS = REGISTRY.register("ancient_second_rounder_heavy_leggings", () -> new AncientSecondRounderHeavyArmor.Leggings());
	public static final RegistryObject<Item> ANCIENT_SECOND_ROUNDER_HEAVY_BOOTS = REGISTRY.register("ancient_second_rounder_heavy_boots", () -> new AncientSecondRounderHeavyArmor.Boots());
	public static final RegistryObject<Item> FUNERARY_SHEET = REGISTRY.register("funerary_sheet", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.RARE)));
	public static final RegistryObject<Item> ANCIENT_SECOND_ROUNDER_NOBLE_UPGRADE_SMITHING_TEMPLATE = REGISTRY.register("ancient_second_rounder_noble_upgrade_smithing_template", () -> new AncientSecondRounderNobleUpgradeSmithingTemplate());
	public static final RegistryObject<Item> ANCIENT_SECOND_ROUNDER_NOBLE_HELMET = REGISTRY.register("ancient_second_rounder_noble_helmet", () -> new AncientSecondRounderNobleArmor.Helmet());
	public static final RegistryObject<Item> ANCIENT_SECOND_ROUNDER_NOBLE_CHESTPLATE = REGISTRY.register("ancient_second_rounder_noble_chestplate", () -> new AncientSecondRounderNobleArmor.Chestplate());
	public static final RegistryObject<Item> ANCIENT_SECOND_ROUNDER_NOBLE_LEGGINGS = REGISTRY.register("ancient_second_rounder_noble_leggings", () -> new AncientSecondRounderNobleArmor.Leggings());
	public static final RegistryObject<Item> ANCIENT_SECOND_ROUNDER_NOBLE_BOOTS = REGISTRY.register("ancient_second_rounder_noble_boots", () -> new AncientSecondRounderNobleArmor.Boots());
	public static final RegistryObject<Item> SEDIMENT_LORD_ROBE_PIECES = REGISTRY.register("sediment_lord_robe_pieces", () -> new ItemBossDrop(JerotesVillageEntityType.BOSS_SHOW_ENTITY_SEDIMENT_LORD, () -> JerotesVillageSoundEvents.SEDIMENT_LORD_AMBIENT));
	public static final RegistryObject<Item> MUSIC_DISC_SEDIMENT_LORD = REGISTRY.register("music_disc_sediment_lord", () -> new RecordItem(1, JerotesVillageSoundEvents.BOSS_MUSIC_1, new Item.Properties().fireResistant().stacksTo(1).rarity(Rarity.EPIC), 471));
	public static final RegistryObject<Item> RESURRECTER_SPAWN_EGG = REGISTRY.register("resurrecter_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.RESURRECTER, -12958622, -15984845, new Item.Properties()));
	public static final RegistryObject<Item> SEDIMENT_GUARD_SPAWN_EGG = REGISTRY.register("sediment_guard_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SEDIMENT_GUARD, -12958622, -13150605, new Item.Properties()));
	public static final RegistryObject<Item> SEDIMENT_SOUL_STABILIZER_SPAWN_EGG = REGISTRY.register("sediment_soul_stabilizer_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SEDIMENT_SOUL_STABILIZER, -12958622, -12162202, new Item.Properties()));
	public static final RegistryObject<Item> HEAVY_ARMORED_SEDIMENT_GUARD_SPAWN_EGG = REGISTRY.register("heavy_armored_sediment_guard_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.HEAVY_ARMORED_SEDIMENT_GUARD, -12958622, -9798528, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> SEDIMENT_LORD_SPAWN_EGG = REGISTRY.register("sediment_lord_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SEDIMENT_LORD, -12958622, -11817777, new Item.Properties().rarity(Rarity.EPIC)));
	//繁兴
	public static final RegistryObject<Item> PROSPEROUS_PERFUME = REGISTRY.register("prosperous_perfume", () -> new ProsperousPerfume());
	public static final RegistryObject<Item> PROSPEROUS_POUCH = REGISTRY.register("prosperous_pouch", () -> new ProsperousPouch());
	public static final RegistryObject<Item> ENVOY_FAN = REGISTRY.register("envoy_fan", () -> new EnvoyFan());
	public static final RegistryObject<Item> PROSPEROUS_ENVOY_FLOWER = REGISTRY.register("prosperous_envoy_flower", () -> new ItemBossDrop(JerotesVillageEntityType.BOSS_SHOW_ENTITY_PROSPEROUS_ENVOY, () -> JerotesVillageSoundEvents.PROSPEROUS_ENVOY_AMBIENT));
	public static final RegistryObject<Item> MUSIC_DISC_PROSPEROUS_ENVOY = REGISTRY.register("music_disc_prosperous_envoy", () -> new RecordItem(1, JerotesVillageSoundEvents.PROSPEROUS_ENVOY_MUSIC, new Item.Properties().fireResistant().stacksTo(1).rarity(Rarity.EPIC), 650));
	public static final RegistryObject<Item> PROSPEROUS_ENVOY_SPAWN_EGG = REGISTRY.register("prosperous_envoy_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.PROSPEROUS_ENVOY, -264080, -999575, new Item.Properties().rarity(Rarity.EPIC)));
	//灾厄
	public static final RegistryObject<Item> OMINOUS_TORCH = REGISTRY.register("ominous_torch", () -> new OminousTorch());
	public static final RegistryObject<Item> explorer_tracking_record = REGISTRY.register("explorer_tracking_record", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> SECOND_ROUNDER_MAP = REGISTRY.register("second_rounder_map", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> GUIDELINES_FOR_THE_CRAFT_OF_THE_SECONDER_ROUND_GEM = REGISTRY.register("guidelines_for_the_craft_of_the_second_rounder_gem", () -> new ItemDesc(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), 1));
	public static final RegistryObject<Item> SECOND_ROUNDER_GEM = REGISTRY.register("second_rounder_gem", () -> new ItemDesc(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), 1));
	public static final RegistryObject<Item> SECOND_ROUND_WORLD_TELEPORT_STONE = REGISTRY.register("second_round_world_teleport_stone", () -> new SecondRoundWorldTeleportStone());
	public static final RegistryObject<Item> OMINOUS_BANNER_RAID_TAG_DEPLOYER = REGISTRY.register("ominous_banner_raid_tag_deployer", () -> new OminousBannerRaidTagDeployer());
	public static final RegistryObject<Item> EXPLORER_CAMP_MAP = REGISTRY.register("explorer_camp_map", () -> new ItemMagicMap(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), "explorer_camp"));
	public static final RegistryObject<Item> EXECUTIONERS_AXE = REGISTRY.register("executioners_axe", () -> new ExecutionersAxe());
	public static final RegistryObject<Item> EXECUTIONERS_HAMMER = REGISTRY.register("executioners_hammer", () -> new ExecutionersHammer());
	public static final RegistryObject<Item> EXECUTIONERS_HALBERD = REGISTRY.register("executioners_halberd", () -> new ExecutionersHalberd());
	public static final RegistryObject<Item> OMINOUS_BOMB = REGISTRY.register("ominous_bomb", () -> new OminousBomb());
	public static final RegistryObject<Item> OMINOUS_CUTLASS = REGISTRY.register("ominous_cutlass", () -> new OminousCutlass());
	public static final RegistryObject<Item> OMINOUS_DAGGER = REGISTRY.register("ominous_dagger", () -> new OminousDagger());
	public static final RegistryObject<Item> OMINOUS_MACE = REGISTRY.register("ominous_mace", () -> new OminousMace());
	public static final RegistryObject<Item> OMINOUS_BOW = REGISTRY.register("ominous_bow", () -> new OminousBow());
	public static final RegistryObject<Item> OMINOUS_JAVELIN = REGISTRY.register("ominous_javelin", () -> new OminousJavelin());
	public static final RegistryObject<Item> SLAVERY_SUPERVISOR_HAMMER_WHIP = REGISTRY.register("slavery_supervisor_hammer_whip", () -> new SlaverySupervisorHammerWhip());
	public static final RegistryObject<Item> FIREPOWER_POURER_CHAINSAW = REGISTRY.register("firepower_pourer_chainsaw", () -> new FirepowerPourerChainsaw());
	public static final RegistryObject<Item> FIREPOWER_POURER_CROSSBOW = REGISTRY.register("firepower_pourer_crossbow", () -> new FirepowerPourerCrossbow());
	public static final RegistryObject<Item> CRAZY_AXE = REGISTRY.register("crazy_axe", () -> new CrazyAxe());
	public static final RegistryObject<Item> OMINOUS_ADVENTURER_RAPIER = REGISTRY.register("ominous_adventurer_rapier", () -> new OminousAdventurerRapier());
	public static final RegistryObject<Item> OMINOUS_WAR_BEAST_ARMOR = REGISTRY.register("ominous_war_beast_armor", () -> new ItemWarBeastArmor(11, 0x668785, JerotesVillage.MODID,"ominous", new Item.Properties().stacksTo(1).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> OMINOUS_GIANT_BEAST_ARMOR = REGISTRY.register("ominous_giant_beast_armor", () -> new ItemGiantBeastArmor(13, 0x668785, JerotesVillage.MODID, "ominous", new Item.Properties().stacksTo(1).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> EXPLORER_IRON_UPGRADE_SMITHING_TEMPLATE = REGISTRY.register("explorer_iron_upgrade_smithing_template", () -> new ExplorerIronUpgradeSmithingTemplate());
	public static final RegistryObject<Item> EXPLORER_IRON_HELMET = REGISTRY.register("explorer_iron_helmet", () -> new ExplorerIronArmor.Helmet());
	public static final RegistryObject<Item> EXPLORER_IRON_CHESTPLATE = REGISTRY.register("explorer_iron_chestplate", () -> new ExplorerIronArmor.Chestplate());
	public static final RegistryObject<Item> EXPLORER_IRON_LEGGINGS = REGISTRY.register("explorer_iron_leggings", () -> new ExplorerIronArmor.Leggings());
	public static final RegistryObject<Item> EXPLORER_IRON_BOOTS = REGISTRY.register("explorer_iron_boots", () -> new ExplorerIronArmor.Boots());
	public static final RegistryObject<Item> EXPLORER_UPGRADE_SMITHING_TEMPLATE = REGISTRY.register("explorer_upgrade_smithing_template", () -> new ExplorerUpgradeSmithingTemplate());
	public static final RegistryObject<Item> EXPLORER_HELMET = REGISTRY.register("explorer_helmet", () -> new ExplorerArmor.Helmet());
	public static final RegistryObject<Item> EXPLORER_CHESTPLATE = REGISTRY.register("explorer_chestplate", () -> new ExplorerArmor.Chestplate());
	public static final RegistryObject<Item> EXPLORER_LEGGINGS = REGISTRY.register("explorer_leggings", () -> new ExplorerArmor.Leggings());
	public static final RegistryObject<Item> EXPLORER_BOOTS = REGISTRY.register("explorer_boots", () -> new ExplorerArmor.Boots());
	public static final RegistryObject<Item> ADVENTURER_HELMET = REGISTRY.register("adventurer_helmet", () -> new AdventurerArmor.Helmet());
	public static final RegistryObject<Item> ADVENTURER_CHESTPLATE = REGISTRY.register("adventurer_chestplate", () -> new AdventurerArmor.Chestplate());
	public static final RegistryObject<Item> ADVENTURER_LEGGINGS = REGISTRY.register("adventurer_leggings", () -> new AdventurerArmor.Leggings());
	public static final RegistryObject<Item> ADVENTURER_BOOTS = REGISTRY.register("adventurer_boots", () -> new AdventurerArmor.Boots());
	public static final RegistryObject<Item> HORNED_HELMET = REGISTRY.register("horned_helmet", () -> new HornedArmor.Helmet());
	public static final RegistryObject<Item> SLAVERY_SUPERVISOR_UPGRADE_SMITHING_TEMPLATE = REGISTRY.register("slavery_supervisor_upgrade_smithing_template", () -> new SlaverySupervisorUpgradeSmithingTemplate());
	public static final RegistryObject<Item> SLAVERY_SUPERVISOR_HELMET = REGISTRY.register("slavery_supervisor_helmet", () -> new SlaverySupervisorArmor.Helmet());
	public static final RegistryObject<Item> SLAVERY_SUPERVISOR_CHESTPLATE = REGISTRY.register("slavery_supervisor_chestplate", () -> new SlaverySupervisorArmor.Chestplate());
	public static final RegistryObject<Item> SLAVERY_SUPERVISOR_LEGGINGS = REGISTRY.register("slavery_supervisor_leggings", () -> new SlaverySupervisorArmor.Leggings());
	public static final RegistryObject<Item> SLAVERY_SUPERVISOR_BOOTS = REGISTRY.register("slavery_supervisor_boots", () -> new SlaverySupervisorArmor.Boots());
	public static final RegistryObject<Item> OMINOUS_PROBE = REGISTRY.register("ominous_probe", () -> new OminousProbe());
	public static final RegistryObject<Item> OMINOUS_BANNER_PROJECTION_EMERALD_FRAGMENT = REGISTRY.register("ominous_banner_projection_emerald_fragment", () -> new ItemBossDrop(JerotesVillageEntityType.BOSS_SHOW_ENTITY_OMINOUS_BANNER_PROJECTION, () -> JerotesVillageSoundEvents.OMINOUS_BANNER_PROJECTION_AMBIENT));
	public static final RegistryObject<Item> MUSIC_DISC_OMINOUS_BANNER_PROJECTION = REGISTRY.register("music_disc_ominous_banner_projection", () -> new RecordItem(1, JerotesVillageSoundEvents.OMINOUS_BANNER_PROJECTION_MUSIC, new Item.Properties().fireResistant().stacksTo(1).rarity(Rarity.EPIC), 363));
	public static final RegistryObject<Item> BOUND_ZOMBIE_VILLAGER_SPAWN_EGG = REGISTRY.register("bound_zombie_villager_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.BOUND_ZOMBIE_VILLAGER, 7039851, 7969893, new Item.Properties()));
	public static final RegistryObject<Item> EXPLORER_SPAWN_EGG = REGISTRY.register("explorer_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.EXPLORER, -5993875, 9804699, new Item.Properties()));
	public static final RegistryObject<Item> EXECUTIONER_SPAWN_EGG = REGISTRY.register("executioner_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.EXECUTIONER, -12379622, 9804699, new Item.Properties()));
	public static final RegistryObject<Item> MAPMAKER_SPAWN_EGG = REGISTRY.register("mapmaker_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.MAPMAKER, -13333275, 9804699, new Item.Properties()));
	public static final RegistryObject<Item> DEFECTOR_SPAWN_EGG = REGISTRY.register("defector_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.DEFECTOR, -9674948, 9804699, new Item.Properties()));
	public static final RegistryObject<Item> BANNER_BEARER_SPAWN_EGG = REGISTRY.register("banner_bearer_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.BANNER_BEARER, -5718612, 9804699, new Item.Properties()));
	public static final RegistryObject<Item> BLASTER_SPAWN_EGG = REGISTRY.register("blaster_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.BLASTER, 4131328, 9804699, new Item.Properties()));
	public static final RegistryObject<Item> TRUMPETER_SPAWN_EGG = REGISTRY.register("trumpeter_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.TRUMPETER, 7041921, 9804699, new Item.Properties()));
	public static final RegistryObject<Item> TELEPORTER_SPAWN_EGG = REGISTRY.register("teleporter_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.TELEPORTER, -3883084, 9804699, new Item.Properties()));
	public static final RegistryObject<Item> CYCLONER_SPAWN_EGG = REGISTRY.register("cycloner_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.CYCLONER, 8546922, 9804699, new Item.Properties()));
	public static final RegistryObject<Item> JAVELIN_THROWER_SPAWN_EGG = REGISTRY.register("javelin_thrower_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.JAVELIN_THROWER, -9358848, 9804699, new Item.Properties()));
	public static final RegistryObject<Item> ZOMBIE_KEEPER_SPAWN_EGG = REGISTRY.register("zombie_keeper_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.ZOMBIE_KEEPER, 5580544, 9804699, new Item.Properties()));
	public static final RegistryObject<Item> BITTER_COLD_SORCERER_SPAWN_EGG = REGISTRY.register("bitter_cold_sorcerer_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.BITTER_COLD_SORCERER, 6460119, 9804699, new Item.Properties()));
	public static final RegistryObject<Item> FIRE_SPITTER_SPAWN_EGG = REGISTRY.register("fire_spitter_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.FIRE_SPITTER, 9633792, 9804699, new Item.Properties()));
	public static final RegistryObject<Item> WILD_FINDER_SPAWN_EGG = REGISTRY.register("wild_finder_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.WILD_FINDER, 6241585, 9804699, new Item.Properties()));
	public static final RegistryObject<Item> SUBMARINER_SPAWN_EGG = REGISTRY.register("submariner_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SUBMARINER, 3501011, 9804699, new Item.Properties()));
	public static final RegistryObject<Item> LAMP_WIZARD_SPAWN_EGG = REGISTRY.register("lamp_wizard_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.LAMP_WIZARD, 54027, 9804699, new Item.Properties()));
	public static final RegistryObject<Item> SLAVERY_SUPERVISOR_SPAWN_EGG = REGISTRY.register("slavery_supervisor_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SLAVERY_SUPERVISOR, 2367268, 9804699, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> FIREPOWER_POURER_SPAWN_EGG = REGISTRY.register("firepower_pourer_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.FIREPOWER_POURER, 4802889, 2631720, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> NECROMANCY_WARLOCK_SPAWN_EGG = REGISTRY.register("necromancy_warlock_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.NECROMANCY_WARLOCK, 8595535, 9804699, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> GAVILER_SPAWN_EGG = REGISTRY.register("gaviler_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.GAVILER, 3629670, 9804699, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> AX_CRAZY_SPAWN_EGG = REGISTRY.register("ax_crazy_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.AX_CRAZY, -43239, 9804699, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> ADVENTURER_SPAWN_EGG = REGISTRY.register("adventurer_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.ADVENTURER, 0x694f4a, 0x58777a, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> OMINOUS_BANNER_PROJECTION_SPAWN_EGG = REGISTRY.register("ominous_banner_projection_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.OMINOUS_BANNER_PROJECTION, 5190168, 9678509, new Item.Properties().rarity(Rarity.EPIC)));
	//苦寒
	public static final RegistryObject<Item> SNOW_FROZEN_SOIL = block(JerotesVillageBlocks.SNOW_FROZEN_SOIL);
	public static final RegistryObject<Item> ICE_ROCK = block(JerotesVillageBlocks.ICE_ROCK);
	public static final RegistryObject<Item> TORCH_OF_FIRM_ICE = highfireResistanceEpicBlock(JerotesVillageBlocks.TORCH_OF_FIRM_ICE);
	public static final RegistryObject<Item> FRAGRANT_LAWN = block(JerotesVillageBlocks.FRAGRANT_LAWN);
	public static final RegistryObject<Item> CYAN_ROOT = block(JerotesVillageBlocks.CYAN_ROOT);
	public static final RegistryObject<Item> BITTER_COLD_SNOW_FLOWER = block(JerotesVillageBlocks.BITTER_COLD_SNOW_FLOWER);
	public static final RegistryObject<Item> BITTER_COLD_LOG = block(JerotesVillageBlocks.BITTER_COLD_LOG);
	public static final RegistryObject<Item> BITTER_COLD_WOOD = block(JerotesVillageBlocks.BITTER_COLD_WOOD);
	public static final RegistryObject<Item> STRIPPED_BITTER_COLD_LOG = block(JerotesVillageBlocks.STRIPPED_BITTER_COLD_LOG);
	public static final RegistryObject<Item> STRIPPED_BITTER_COLD_WOOD = block(JerotesVillageBlocks.STRIPPED_BITTER_COLD_WOOD);
	public static final RegistryObject<Item> BITTER_COLD_PLANKS = block(JerotesVillageBlocks.BITTER_COLD_PLANKS);
	public static final RegistryObject<Item> BITTER_COLD_STAIRS = block(JerotesVillageBlocks.BITTER_COLD_STAIRS);
	public static final RegistryObject<Item> BITTER_COLD_SLAB = block(JerotesVillageBlocks.BITTER_COLD_SLAB);
	public static final RegistryObject<Item> BITTER_COLD_FENCE = block(JerotesVillageBlocks.BITTER_COLD_FENCE);
	public static final RegistryObject<Item> BITTER_COLD_FENCE_GATE = block(JerotesVillageBlocks.BITTER_COLD_FENCE_GATE);
	public static final RegistryObject<Item> BITTER_COLD_DOOR = highBlock(JerotesVillageBlocks.BITTER_COLD_DOOR);
	public static final RegistryObject<Item> BITTER_COLD_TRAPDOOR = block(JerotesVillageBlocks.BITTER_COLD_TRAPDOOR);
	public static final RegistryObject<Item> BITTER_COLD_PRESSURE_PLATE = block(JerotesVillageBlocks.BITTER_COLD_PRESSURE_PLATE);
	public static final RegistryObject<Item> BITTER_COLD_BUTTON = block(JerotesVillageBlocks.BITTER_COLD_BUTTON);
	public static final RegistryObject<Item> BITTER_COLD_SIGN = REGISTRY.register("bitter_cold_sign", () -> new SignItem(new Item.Properties().stacksTo(16), JerotesVillageBlocks.BITTER_COLD_SIGN.get(), JerotesVillageBlocks.BITTER_COLD_WALL_SIGN.get()));
	public static final RegistryObject<Item> BITTER_COLD_HANGING_SIGN = REGISTRY.register("bitter_cold_hanging_sign", () -> new HangingSignItem(JerotesVillageBlocks.BITTER_COLD_HANGING_SIGN.get(), JerotesVillageBlocks.BITTER_COLD_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> BITTER_COLD_LEAVES = block(JerotesVillageBlocks.BITTER_COLD_LEAVES);
	public static final RegistryObject<Item> BITTER_COLD_SAPLING = block(JerotesVillageBlocks.BITTER_COLD_SAPLING);
	public static final RegistryObject<Item> BITTER_COLD_BOAT = REGISTRY.register("bitter_cold_boat", () -> new BitterColdBoat());
	public static final RegistryObject<Item> BITTER_COLD_CHEST_BOAT = REGISTRY.register("bitter_cold_chest_boat", () -> new BitterColdChestBoat());
	public static final RegistryObject<Item> BITTER_COD = REGISTRY.register("bitter_cod", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.BITTER_COD)));
	public static final RegistryObject<Item> COOKED_BITTER_COD = REGISTRY.register("cooked_bitter_cod", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.COOKED_BITTER_COD)));
	public static final RegistryObject<Item> BITTER_COD_BUCKET = REGISTRY.register("bitter_cod_bucket", () -> new ItemBucketBase(JerotesVillageEntityType.BITTER_COD, Fluids.WATER, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> GIANT_MONSTER_HAIR = REGISTRY.register("giant_monster_hair", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> GIANT_MONSTER_HORN = REGISTRY.register("giant_monster_horn", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> GIANT_MONSTER_HELMET = REGISTRY.register("giant_monster_helmet", () -> new GiantMonsterArmor.Helmet());
	public static final RegistryObject<Item> GIANT_MONSTER_HORNED_HELMET = REGISTRY.register("giant_monster_horned_helmet", () -> new GiantMonsterArmor.HornedHelmet());
	public static final RegistryObject<Item> GIANT_MONSTER_CHESTPLATE = REGISTRY.register("giant_monster_chestplate", () -> new GiantMonsterArmor.Chestplate());
	public static final RegistryObject<Item> GIANT_MONSTER_LEGGINGS = REGISTRY.register("giant_monster_leggings", () -> new GiantMonsterArmor.Leggings());
	public static final RegistryObject<Item> GIANT_MONSTER_BOOTS = REGISTRY.register("giant_monster_boots", () -> new GiantMonsterArmor.Boots());
	public static final RegistryObject<Item> MEGANOSE_SNOW_LIZARD_MEAT = REGISTRY.register("meganose_snow_lizard_meat", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.MEGANOSE_SNOW_LIZARD_MEAT)));
	public static final RegistryObject<Item> COOKED_MEGANOSE_SNOW_LIZARD_MEAT = REGISTRY.register("cooked_meganose_snow_lizard_meat", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.COOKED_MEGANOSE_SNOW_LIZARD_MEAT)));
	public static final RegistryObject<Item> BITTER_COLD_FAT = REGISTRY.register("bitter_cold_fat", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.BITTER_COLD_FAT)));
	public static final RegistryObject<Item> FROST_YETI_HAIR = REGISTRY.register("frost_yeti_hair", () -> new ItemDesc(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON), 1));
	public static final RegistryObject<Item> BITTER_COLD_BELL = REGISTRY.register("bitter_cold_bell", () -> new BitterColdBell());
	public static final RegistryObject<Item> BITTER_COLD_IGLOO_MAP = REGISTRY.register("bitter_cold_igloo_map", () -> new ItemMagicMap(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), "bitter_cold_igloo"));
	public static final RegistryObject<Item> ICE_ROCK_THROWING_BALL = REGISTRY.register("ice_rock_throwing_ball", () -> new IceRockThrowingBall());
	public static final RegistryObject<Item> MYOPIA_GLASSES = REGISTRY.register("myopia_glasses", () -> new GlassesArmor.Myopia());
	public static final RegistryObject<Item> PRESBYOPIC_GLASSES = REGISTRY.register("presbyopic_glasses", () -> new GlassesArmor.Presbyopic());
	public static final RegistryObject<Item> SUNGLASSES = REGISTRY.register("sunglasses", () -> new GlassesArmor.Sun());
	public static final RegistryObject<Item> CORROSIVER_BUGLE = REGISTRY.register("corrosiver_bugle", () -> new CorrosiverBugle());
	public static final RegistryObject<Item> CORROSIVER_CLAW = REGISTRY.register("corrosiver_claw", () -> new CorrosiverClaw());
	public static final RegistryObject<Item> BITTER_COLD_FIRM_ICE = REGISTRY.register("bitter_cold_firm_ice", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.EPIC).fireResistant()));
	public static final RegistryObject<Item> BITTER_COLD_HAMMER = REGISTRY.register("bitter_cold_hammer", () -> new BitterColdHammer());
	public static final RegistryObject<Item> CORROSIVER_BROKEN_HORN = REGISTRY.register("corrosiver_broken_horn", () -> new ItemBossDrop(JerotesVillageEntityType.BOSS_SHOW_ENTITY_CORROSIVER, () -> JerotesVillageSoundEvents.CORROSIVER_AMBIENT));
	public static final RegistryObject<Item> MUSIC_DISC_CORROSIVER = REGISTRY.register("music_disc_corrosiver", () -> new RecordItem(1, JerotesVillageSoundEvents.CORROSIVER_MUSIC, new Item.Properties().fireResistant().stacksTo(1).rarity(Rarity.EPIC), 548));
	public static final RegistryObject<Item> BITTER_COD_SPAWN_EGG = REGISTRY.register("bitter_cod_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.BITTER_COD, 0x6b8398, 0xd4def1, new Item.Properties()));
	public static final RegistryObject<Item> GIANT_MONSTER_SPAWN_EGG = REGISTRY.register("giant_monster_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.GIANT_MONSTER, -2302756, -6973968, new Item.Properties()));
	public static final RegistryObject<Item> BIG_WOOL_GEMSBOK_SPAWN_EGG = REGISTRY.register("big_wool_gemsbok_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.BIG_WOOL_GEMSBOK, -1385263, -4088447, new Item.Properties()));
	public static final RegistryObject<Item> FLUFFMOUND_SPAWN_EGG = REGISTRY.register("fluffmound_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.FLUFFMOUND, 0x95a9c3, 0xdbf2f2, new Item.Properties()));
	public static final RegistryObject<Item> LITTLE_VELVET_SPAWN_EGG = REGISTRY.register("little_velvet_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.LITTLE_VELVET, -4796195, -1839115, new Item.Properties()));
	public static final RegistryObject<Item> MEGANOSE_SNOW_LIZARD_SPAWN_EGG = REGISTRY.register("meganose_snow_lizard_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.MEGANOSE_SNOW_LIZARD, -1905166, -6969412, new Item.Properties()));
	public static final RegistryObject<Item> SNOWY_APE_SPAWN_EGG = REGISTRY.register("snowy_ape_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SNOWY_APE, -657931, -2901571, new Item.Properties()));
	public static final RegistryObject<Item> DRACOGNATHUS_SPAWN_EGG = REGISTRY.register("dracognathus_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.DRACOGNATHUS, 15395562, 6721956, new Item.Properties()));
	public static final RegistryObject<Item> BITTER_COLD_WIND_SPAWN_EGG = REGISTRY.register("bitter_cold_wind_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.BITTER_COLD_WIND, -4195333, -9906689, new Item.Properties()));
	public static final RegistryObject<Item> FROST_YETI_SPAWN_EGG = REGISTRY.register("frost_yeti_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.FROST_YETI, -10314296, -2101265, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> PRIMORDIER_SPAWN_EGG = REGISTRY.register("primordier_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.PRIMORDIER, 0xb6cce7, 0x36819e, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> CORROSIVER_SPAWN_EGG = REGISTRY.register("corrosiver_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.CORROSIVER, 15658718, 44988, new Item.Properties().rarity(Rarity.EPIC)));
	//紫沙
	public static final RegistryObject<Item> PURPLE_SAND = block(JerotesVillageBlocks.PURPLE_SAND);
	public static final RegistryObject<Item> PURPLE_SANDSTONE = block(JerotesVillageBlocks.PURPLE_SANDSTONE);
	public static final RegistryObject<Item> PURPLE_SANDSTONE_STAIRS = block(JerotesVillageBlocks.PURPLE_SANDSTONE_STAIRS);
	public static final RegistryObject<Item> PURPLE_SANDSTONE_SLAB = block(JerotesVillageBlocks.PURPLE_SANDSTONE_SLAB);
	public static final RegistryObject<Item> PURPLE_SANDSTONE_WALL = block(JerotesVillageBlocks.PURPLE_SANDSTONE_WALL);
	public static final RegistryObject<Item> CHISELED_PURPLE_SANDSTONE = block(JerotesVillageBlocks.CHISELED_PURPLE_SANDSTONE);
	public static final RegistryObject<Item> SMOOTH_PURPLE_SANDSTONE = block(JerotesVillageBlocks.SMOOTH_PURPLE_SANDSTONE);
	public static final RegistryObject<Item> SMOOTH_PURPLE_SANDSTONE_STAIRS = block(JerotesVillageBlocks.SMOOTH_PURPLE_SANDSTONE_STAIRS);
	public static final RegistryObject<Item> SMOOTH_PURPLE_SANDSTONE_SLAB = block(JerotesVillageBlocks.SMOOTH_PURPLE_SANDSTONE_SLAB);
	public static final RegistryObject<Item> CUT_PURPLE_SANDSTONE = block(JerotesVillageBlocks.CUT_PURPLE_SANDSTONE);
	public static final RegistryObject<Item> CUT_PURPLE_SANDSTONE_SLAB = block(JerotesVillageBlocks.CUT_PURPLE_SANDSTONE_SLAB);
	public static final RegistryObject<Item> PURPLE_SAND_SHORT_DRY_GRASS = block(JerotesVillageBlocks.PURPLE_SAND_SHORT_DRY_GRASS);
	public static final RegistryObject<Item> PURPLE_SAND_TALL_DRY_GRASS = block(JerotesVillageBlocks.PURPLE_SAND_TALL_DRY_GRASS);
	public static final RegistryObject<Item> PURPLE_BLACK_DEAD_BUSH = block(JerotesVillageBlocks.PURPLE_BLACK_DEAD_BUSH);
	public static final RegistryObject<Item> PINK_CACTUS = block(JerotesVillageBlocks.PINK_CACTUS);
	public static final RegistryObject<Item> PINK_CACTUS_FLOWER = block(JerotesVillageBlocks.PINK_CACTUS_FLOWER);
	public static final RegistryObject<Item> ENCHANTED_STONE = block(JerotesVillageBlocks.ENCHANTED_STONE);
	public static final RegistryObject<Item> PURPLE_SAND_CREEPER_HEAD = REGISTRY.register("purple_sand_creeper_head", () -> new StandingAndWallBlockItem(JerotesVillageBlocks.PURPLE_SAND_CREEPER_HEAD.get(), JerotesVillageBlocks.PURPLE_SAND_CREEPER_WALL_HEAD.get(), new Item.Properties().rarity(Rarity.UNCOMMON), Direction.DOWN));
	public static final RegistryObject<Item> SPIRVE_HEAD = REGISTRY.register("spirve_head", () -> new StandingAndWallBlockItem(JerotesVillageBlocks.SPIRVE_HEAD.get(), JerotesVillageBlocks.SPIRVE_WALL_HEAD.get(), new Item.Properties().rarity(Rarity.UNCOMMON), Direction.DOWN));
	public static final RegistryObject<Item> HAGS_CAULDRON = fireResistanceRareBlock(JerotesVillageBlocks.HAGS_CAULDRON);
	public static final RegistryObject<Item> UNSTABLE_HAGS_CAULDRON = fireResistanceRareBlock(JerotesVillageBlocks.UNSTABLE_HAGS_CAULDRON);
	public static final RegistryObject<Item> DIRTY_HAGS_CAULDRON = fireResistanceRareBlock(JerotesVillageBlocks.DIRTY_HAGS_CAULDRON);
	public static final RegistryObject<Item> NEW_HAGS_CAULDRON = fireResistanceEpicBlock(JerotesVillageBlocks.NEW_HAGS_CAULDRON);
	public static final RegistryObject<Item> PURPLE_SAND_GECKO_TAIL = REGISTRY.register("purple_sand_gecko_tail", () -> new ItemDesc(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.PURPLE_SAND_GECKO_TAIL), 1));
	public static final RegistryObject<Item> PURPLE_SANDWICH = REGISTRY.register("purple_sandwich", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.PURPLE_SANDWICH)));
	public static final RegistryObject<Item> WITCH_COVEN_MAP = REGISTRY.register("witch_coven_map", () -> new ItemMagicMap(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), "witch_coven"));
	public static final RegistryObject<Item> WITCH_RESIDENCE_MAP = REGISTRY.register("witch_residence_map", () -> new ItemMagicMap(new Item.Properties().stacksTo(1).rarity(Rarity.RARE), "witch_residence"));
	public static final RegistryObject<Item> SPIRVE_HELMET = REGISTRY.register("spirve_helmet", () -> new SpirveArmor.Helmet());
	public static final RegistryObject<Item> SPIRVE_CHESTPLATE = REGISTRY.register("spirve_chestplate", () -> new SpirveArmor.Chestplate());
	public static final RegistryObject<Item> SPIRVE_LEGGINGS = REGISTRY.register("spirve_leggings", () -> new SpirveArmor.Leggings());
	public static final RegistryObject<Item> SPIRVE_BOOTS = REGISTRY.register("spirve_boots", () -> new SpirveArmor.Boots());
	public static final RegistryObject<Item> SPIRVE_BROOM = REGISTRY.register("spirve_broom", () -> new SpirveBroom());
	public static final RegistryObject<Item> SPIRVE_DUSTPAN = REGISTRY.register("spirve_dustpan", () -> new SpirveWeaponIronShovel());
	public static final RegistryObject<Item> SPIRVE_INSECT_NET = REGISTRY.register("spirve_insect_net", () -> new SpirveInsectNet());
	public static final RegistryObject<Item> SPIRVE_MOP = REGISTRY.register("spirve_mop", () -> new SpirveMop());
	public static final RegistryObject<Item> SPIRVE_KITCHEN_KNIFE = REGISTRY.register("spirve_kitchen_knife", () -> new SpirveKitchenKnife());
	public static final RegistryObject<Item> SPIRVE_SPATULA = REGISTRY.register("spirve_spatula", () -> new SpirveWeaponIronShovel());
	public static final RegistryObject<Item> SPIRVE_PAN = REGISTRY.register("spirve_pan", () -> new SpirveWeaponIron());
	public static final RegistryObject<Item> SPIRVE_GOURD_LADLE = REGISTRY.register("spirve_gourd_ladle", () -> new SpirveWeaponIron());
	public static final RegistryObject<Item> SPIRVE_SAW = REGISTRY.register("spirve_saw", () -> new SpirveSaw());
	public static final RegistryObject<Item> SPIRVE_FRAME_SAW = REGISTRY.register("spirve_frame_saw", () -> new SpirveSaw());
	public static final RegistryObject<Item> SPIRVE_PINCER = REGISTRY.register("spirve_pincer", () -> new SpirvePincer());
	public static final RegistryObject<Item> SPIRVE_SCISSORS = REGISTRY.register("spirve_scissors", () -> new SpirveScissors());
	public static final RegistryObject<Item> SPIRVE_RAZOR = REGISTRY.register("spirve_razor", () -> new SpirveScissors());
	public static final RegistryObject<Item> SPIRVE_DUSTER = REGISTRY.register("spirve_duster", () -> new SpirveDuster());
	public static final RegistryObject<Item> SPIRVE_BROKEN_BOOTLE = REGISTRY.register("spirve_broken_bottle", () -> new SpirveBrokenBottle());
	public static final RegistryObject<Item> SPIRVE_FORK = REGISTRY.register("spirve_fork", () -> new SpirveFork());
	public static final RegistryObject<Item> SPIRVE_STUB = REGISTRY.register("spirve_stub", () -> new SpirveStub());
	public static final RegistryObject<Item> SPIRVE_CHOPPER = REGISTRY.register("spirve_chopper", () -> new SpirveWeaponIronAxe());
	public static final RegistryObject<Item> SPIRVE_MORNING_STAR = REGISTRY.register("spirve_morning_star", () -> new SpirveHammer());
	public static final RegistryObject<Item> SPIRVE_HAMMER = REGISTRY.register("spirve_hammer", () -> new SpirveHammer());
	public static final RegistryObject<Item> SPIRVE_FLAIL = REGISTRY.register("spirve_flail", () -> new SpirveFlail());
	public static final RegistryObject<Item> SPIRVE_HORSEWHIP = REGISTRY.register("spirve_horsewhip", () -> new SpirveHorseWhip());
	public static final RegistryObject<Item> SPIRVE_HOOK = REGISTRY.register("spirve_hook", () -> new SpirveWeaponIron());
	public static final RegistryObject<Item> SPIRVE_RULER = REGISTRY.register("spirve_ruler", () -> new SpirveWeaponWood());
	public static final RegistryObject<Item> SPIRVE_WOODEN_BACK_SCRATCHER = REGISTRY.register("spirve_wooden_back_scratcher", () -> new SpirveWoodenBackScratcher());
	public static final RegistryObject<Item> SPIRVE_LADLE = REGISTRY.register("spirve_ladle", () -> new SpirveWeaponIronShovel());
	public static final RegistryObject<Item> SPIRVE_FIRESTICK = REGISTRY.register("spirve_firestick", () -> new SpirveWeaponIron());
	public static final RegistryObject<Item> SPIRVE_SICKLE = REGISTRY.register("spirve_sickle", () -> new SpirveSickle());
	public static final RegistryObject<Item> SPIRVE_WRENCH = REGISTRY.register("spirve_wrench", () -> new SpirveWeaponIron());
	public static final RegistryObject<Item> SPIRVE_LID = REGISTRY.register("spirve_lid", () -> new ItemToolBaseShield(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON).durability(32 * 3), 2f, 1.2f, 15, Ingredient.of(ItemTags.PLANKS)));
	public static final RegistryObject<Item> LIGHTNING_WORM_CHITIN = REGISTRY.register("lightning_worm_chitin", () -> new ItemDesc(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON), 1));
	public static final RegistryObject<Item> EVIL_POTION = REGISTRY.register("evil_potion", () -> new EvilPotion());
	public static final RegistryObject<Item> BIG_WITCHS_HAT = REGISTRY.register("big_witchs_hat", () -> new BigWitchArmor.Helmet());
	public static final RegistryObject<Item> HAG_EYE = REGISTRY.register("hag_eye", () -> new HagEye());
	public static final RegistryObject<Item> HAGS_BRUSH = REGISTRY.register("hags_brush", () -> new HagsBrush());
	public static final RegistryObject<Item> PURPLE_SAND_HAG_EYE = REGISTRY.register("purple_sand_hag_eye", () -> new PurpleSandHagEye());
	public static final RegistryObject<Item> PURPLE_SAND_HAG_HAIR = REGISTRY.register("purple_sand_hag_hair", () -> new ItemBossDrop(JerotesVillageEntityType.BOSS_SHOW_ENTITY_PURPLE_SAND_HAG, () -> JerotesVillageSoundEvents.PURPLE_SAND_HAG_AMBIENT));
	public static final RegistryObject<Item> MUSIC_DISC_PURPLE_SAND_HAG = REGISTRY.register("music_disc_purple_sand_hag", () -> new RecordItem(1, JerotesVillageSoundEvents.PURPLE_SAND_HAG_MUSIC, new Item.Properties().fireResistant().stacksTo(1).rarity(Rarity.EPIC), 258));
	public static final RegistryObject<Item> PURPLE_SAND_ALCHEMY_BOMB = REGISTRY.register("purple_sand_alchemy_bomb", () -> new PurpleSandAlchemyBomb());
	public static final RegistryObject<Item> PURPLE_SAND_EXPLOSIVE_ALCHEMY_BOMB = REGISTRY.register("purple_sand_explosive_alchemy_bomb", () -> new PurpleSandExplosiveAlchemyBomb());
	public static final RegistryObject<Item> PURPLE_SAND_GECKO_SPAWN_EGG = REGISTRY.register("purple_sand_gecko_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.PURPLE_SAND_GECKO, -4888065, -4470273, new Item.Properties()));
	public static final RegistryObject<Item> SANDSTONE_BEAST_SPAWN_EGG = REGISTRY.register("sandstone_beast_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SANDSTONE_BEAST, -3646301, -6724171, new Item.Properties()));
	public static final RegistryObject<Item> PURPLE_SAND_RABBIT_SPAWN_EGG = REGISTRY.register("purple_sand_rabbit_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.PURPLE_SAND_RABBIT, 5388620, 10382974, new Item.Properties()));
	public static final RegistryObject<Item> PURPLE_SAND_CAMEL_SPAWN_EGG = REGISTRY.register("purple_sand_camel_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.PURPLE_SAND_CAMEL, 8660854, 14320250, new Item.Properties()));
	public static final RegistryObject<Item> PURPLE_SAND_SCORPION_SPAWN_EGG = REGISTRY.register("purple_sand_scorpion_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.PURPLE_SAND_SCORPION, -10926498, -14741977, new Item.Properties()));
	public static final RegistryObject<Item> PURPLE_SAND_CREEPER_SPAWN_EGG = REGISTRY.register("purple_sand_creeper_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.PURPLE_SAND_CREEPER, -5876018, -13760454, new Item.Properties()));
	public static final RegistryObject<Item> PURPLE_SAND_WITCH_SPAWN_EGG = REGISTRY.register("purple_sand_witch_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.PURPLE_SAND_WITCH, -4373881, -10874267, new Item.Properties()));
	public static final RegistryObject<Item> WITCH_SCHOLAR_SPAWN_EGG = REGISTRY.register("witch_scholar_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.WITCH_SCHOLAR, -16761724, -16316620, new Item.Properties()));
	public static final RegistryObject<Item> SPIRVE_SPAWN_EGG = REGISTRY.register("spirve_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SPIRVE, -5991582, -12115950, new Item.Properties()));
	public static final RegistryObject<Item> ROTTEN_DOG_SPAWN_EGG = REGISTRY.register("rotten_dog_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.ROTTEN_DOG, -15987700, -12494466, new Item.Properties()));
	public static final RegistryObject<Item> BIG_WITCH_SPAWN_EGG = REGISTRY.register("big_witch_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.BIG_WITCH, -13434778, -16318452, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> LIGHTNING_WORM_SPAWN_EGG = REGISTRY.register("lightning_worm_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.LIGHTNING_WORM, -8903004, -11720776, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> DUNESHOCKLER_SPAWN_EGG = REGISTRY.register("duneshockler_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.DUNESHOCKLER, 0x312094, 0xac33c0, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> COHORT_HAG_SPAWN_EGG = REGISTRY.register("cohort_hag_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.COHORT_HAG, -13684945, -9800415, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> COVEN_HAG_ONE_SPAWN_EGG = REGISTRY.register("coven_hag_one_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.COVEN_HAG_ONE, -12965873, -9800415, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> COVEN_HAG_TWO_SPAWN_EGG = REGISTRY.register("coven_hag_two_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.COVEN_HAG_TWO, -3682874, -9800415, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> PURPLE_SAND_HAG_SPAWN_EGG = REGISTRY.register("purple_sand_hag_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.PURPLE_SAND_HAG, -13626548, -9800415, new Item.Properties().rarity(Rarity.EPIC)));
	//海洋
	public static final RegistryObject<Item> GEMSTONE_GRAVEL = block(JerotesVillageBlocks.GEMSTONE_GRAVEL);
	public static final RegistryObject<Item> GLITTER_SAND = block(JerotesVillageBlocks.GLITTER_SAND);
	public static final RegistryObject<Item> GLITTER_SANDSTONE = block(JerotesVillageBlocks.GLITTER_SANDSTONE);
	public static final RegistryObject<Item> GLITTER_SANDSTONE_STAIRS = block(JerotesVillageBlocks.GLITTER_SANDSTONE_STAIRS);
	public static final RegistryObject<Item> GLITTER_SANDSTONE_SLAB = block(JerotesVillageBlocks.GLITTER_SANDSTONE_SLAB);
	public static final RegistryObject<Item> GLITTER_SANDSTONE_WALL = block(JerotesVillageBlocks.GLITTER_SANDSTONE_WALL);
	public static final RegistryObject<Item> CHISELED_GLITTER_SANDSTONE = block(JerotesVillageBlocks.CHISELED_GLITTER_SANDSTONE);
	public static final RegistryObject<Item> SMOOTH_GLITTER_SANDSTONE = block(JerotesVillageBlocks.SMOOTH_GLITTER_SANDSTONE);
	public static final RegistryObject<Item> SMOOTH_GLITTER_SANDSTONE_STAIRS = block(JerotesVillageBlocks.SMOOTH_GLITTER_SANDSTONE_STAIRS);
	public static final RegistryObject<Item> SMOOTH_GLITTER_SANDSTONE_SLAB = block(JerotesVillageBlocks.SMOOTH_GLITTER_SANDSTONE_SLAB);
	public static final RegistryObject<Item> CUT_GLITTER_SANDSTONE = block(JerotesVillageBlocks.CUT_GLITTER_SANDSTONE);
	public static final RegistryObject<Item> CUT_GLITTER_SANDSTONE_SLAB = block(JerotesVillageBlocks.CUT_GLITTER_SANDSTONE_SLAB);
	public static final RegistryObject<Item> GEMSTONE_ANEMONE = block(JerotesVillageBlocks.GEMSTONE_ANEMONE);
	public static final RegistryObject<Item> OCEAN_GEMSTONE_BLOCK = block(JerotesVillageBlocks.OCEAN_GEMSTONE_BLOCK);
	public static final RegistryObject<Item> QOAIKU_METAL_BLOCK = block(JerotesVillageBlocks.QOAIKU_METAL_BLOCK);
	public static final RegistryObject<Item> GREYSCALE_PRIZE_MACHINE = block(JerotesVillageBlocks.GREYSCALE_PRIZE_MACHINE);
	public static final RegistryObject<Item> OCEAN_GEMSTONE = REGISTRY.register("ocean_gemstone", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> OCEAN_GEMSTONE_FRAGMENTS = REGISTRY.register("ocean_gemstone_fragments", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> QOAIKU_METAL_NUGGET = REGISTRY.register("qoaiku_metal_nugget", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> QOAIKU_METAL_INGOT = REGISTRY.register("qoaiku_metal_ingot", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> GEMSTONE_THROWING_KNIVES = REGISTRY.register("gemstone_throwing_knives", () -> new GemstoneThrowingKnives());
	public static final RegistryObject<Item> QOAIKU_KNIFE = REGISTRY.register("qoaiku_knife", () -> new QoaikuKnife());
	public static final RegistryObject<Item> QOAIKU_AXE = REGISTRY.register("qoaiku_axe", () -> new QoaikuAxe());
	public static final RegistryObject<Item> QOAIKU_PICKAXE = REGISTRY.register("qoaiku_pickaxe", () -> new QoaikuPickaxe());
	public static final RegistryObject<Item> QOAIKU_SHOVEL = REGISTRY.register("qoaiku_shovel", () -> new QoaikuShovel());
	public static final RegistryObject<Item> QOAIKU_HOE = REGISTRY.register("qoaiku_hoe", () -> new QoaikuHoe());
	public static final RegistryObject<Item> QOAIKU_DAGGER = REGISTRY.register("qoaiku_dagger", () -> new QoaikuDagger());
	public static final RegistryObject<Item> QOAIKU_GAVEL = REGISTRY.register("qoaiku_gavel", () -> new QoaikuGavel());
	public static final RegistryObject<Item> QOAIKU_TRIDENT = REGISTRY.register("qoaiku_trident", () -> new QoaikuTrident());
	public static final RegistryObject<Item> QOAIKU_SPEAR = REGISTRY.register("qoaiku_spear", () -> new QoaikuSpear());
	public static final RegistryObject<Item> QOAIKU_PIKE = REGISTRY.register("qoaiku_pike", () -> new QoaikuPike());
	public static final RegistryObject<Item> QOAIKU_ROUND_SHIELD = REGISTRY.register("qoaiku_round_shield", () -> new ItemToolBaseShield(new Item.Properties().stacksTo(1).durability(580 * 3).rarity(Rarity.COMMON), 4f, 1.2f, 23, Ingredient.of(JerotesVillageItems.QOAIKU_METAL_INGOT.get())));
	public static final RegistryObject<Item> QOAIKU_SPIKE = REGISTRY.register("qoaiku_spike", () -> new QoaikuSpike());
	public static final RegistryObject<Item> QOAIKU_FISHING_ROD = REGISTRY.register("qoaiku_fishing_rod", () -> new QoaikuFishingRod());
	public static final RegistryObject<Item> QOAIKU_WAR_BEAST_ARMOR = REGISTRY.register("qoaiku_war_beast_armor", () -> new ItemWarBeastArmor(11, 2, 0, 0x008767, JerotesVillage.MODID, "qoaiku_metal", new Item.Properties().stacksTo(1).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> QOAIKU_GIANT_BEAST_ARMOR = REGISTRY.register("qoaiku_giant_beast_armor", () -> new ItemGiantBeastArmor(13, 3, 0, 0x008767, JerotesVillage.MODID, "qoaiku_metal", new Item.Properties().stacksTo(1).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> QOAIKU_HELMET = REGISTRY.register("qoaiku_helmet", () -> new QoaikuArmor.Helmet());
	public static final RegistryObject<Item> QOAIKU_CHESTPLATE = REGISTRY.register("qoaiku_chestplate", () -> new QoaikuArmor.Chestplate());
	public static final RegistryObject<Item> QOAIKU_LEGGINGS = REGISTRY.register("qoaiku_leggings", () -> new QoaikuArmor.Leggings());
	public static final RegistryObject<Item> QOAIKU_BOOTS = REGISTRY.register("qoaiku_boots", () -> new QoaikuArmor.Boots());
	public static final RegistryObject<Item> GREYSCALE = REGISTRY.register("greyscale", () -> new Greyscale());
	public static final RegistryObject<Item> COOKED_GREYSCALE = REGISTRY.register("cooked_greyscale", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.COOKED_GREYSCALE)));
	public static final RegistryObject<Item> GREYSCALE_BUCKET = REGISTRY.register("greyscale_bucket", () -> new ItemBucketBase(JerotesVillageEntityType.GREYSCALE, Fluids.WATER, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> JEWELROAMER = REGISTRY.register("jewelroamer", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.JEWELROAMER)));
	public static final RegistryObject<Item> COOKED_JEWELROAMER = REGISTRY.register("cooked_jewelroamer", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.COOKED_JEWELROAMER)));
	public static final RegistryObject<Item> JEWELROAMER_BUCKET = REGISTRY.register("jewelroamer_bucket", () -> new ItemBucketBase(JerotesVillageEntityType.JEWELROAMER, Fluids.WATER, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> STARBRITE = REGISTRY.register("starbrite", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.STARBRITE)));
	public static final RegistryObject<Item> COOKED_STARBRITE = REGISTRY.register("cooked_starbrite", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.COOKED_STARBRITE)));
	public static final RegistryObject<Item> STARBRITE_BUCKET = REGISTRY.register("starbrite_bucket", () -> new ItemBucketBase(JerotesVillageEntityType.STARBRITE, Fluids.WATER, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> ABYSSALER = REGISTRY.register("abyssaler", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.ABYSSALER)));
	public static final RegistryObject<Item> COOKED_ABYSSALER = REGISTRY.register("cooked_abyssaler", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.COOKED_ABYSSALER)));
	public static final RegistryObject<Item> ABYSSALER_BUCKET = REGISTRY.register("abyssaler_bucket", () -> new ItemBucketBase(JerotesVillageEntityType.ABYSSALER, Fluids.WATER, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> SOLISH_MEAT = REGISTRY.register("solish_meat", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.SOLISH_MEAT)));
	public static final RegistryObject<Item> COOKED_SOLISH_MEAT = REGISTRY.register("cooked_solish_meat", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.COOKED_SOLISH_MEAT)));
	public static final RegistryObject<Item> BENTHICT_BUCKET = REGISTRY.register("benthict_bucket", () -> new ItemBucketBase(JerotesVillageEntityType.BENTHICT, Fluids.WATER, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> BULGE_ROD = REGISTRY.register("bulge_rod", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> BULGE_POWDER = REGISTRY.register("bulge_powder", () -> new ItemDesc(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON), 1));
	public static final RegistryObject<Item> MALIGNASAUR_TEETH = REGISTRY.register("malignasaur_teeth", () -> new ItemDesc(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON), 1));
	public static final RegistryObject<Item> MALIGNASAUR_ARROW = REGISTRY.register("malignasaur_arrow", () -> new MalignasaurArrow());
	public static final RegistryObject<Item> MALIGNASAUR_FISHGIG = REGISTRY.register("malignasaur_fishgig", () -> new MalignasaurFishgig());
	public static final RegistryObject<Item> MALIGNASAUR_BAIT_FISHGIG = REGISTRY.register("malignasaur_bait_fishgig", () -> new MalignasaurBaitFishgig());
	public static final RegistryObject<Item> SEAWAVE_TRIDENT = REGISTRY.register("seawave_trident", () -> new SeawaveTrident());
	public static final RegistryObject<Item> MARINE_REPTILES_FINE_SCALE = REGISTRY.register("marine_reptiles_fine_scale", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.RARE)));
	public static final RegistryObject<Item> SEA_MONSTER_POTION = REGISTRY.register("sea_monster_potion", () -> new ItemDesc(new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 1));
	public static final RegistryObject<Item> GEMSTONE_MALIGNASAUR_SCALE = REGISTRY.register("gemstone_malignasaur_scale", () -> new ItemBossDrop(JerotesVillageEntityType.BOSS_SHOW_ENTITY_GEMSTONE_MALIGNASAUR, () -> JerotesVillageSoundEvents.GEMSTONE_MALIGNASAUR_AMBIENT));
	public static final RegistryObject<Item> MUSIC_DISC_GEMSTONE_MALIGNASAUR = REGISTRY.register("music_disc_gemstone_malignasaur", () -> new RecordItem(1, JerotesVillageSoundEvents.GEMSTONE_MALIGNASAUR_MUSIC, new Item.Properties().fireResistant().stacksTo(1).rarity(Rarity.EPIC), 629));
	public static final RegistryObject<Item> GREYSCALE_SPAWN_EGG = REGISTRY.register("greyscale_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.GREYSCALE, 0x717171, 0xe0e4d8, new Item.Properties()));
	public static final RegistryObject<Item> JEWELROAMER_SPAWN_EGG = REGISTRY.register("jewelroamer_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.JEWELROAMER, 0x2758c5, 0x58c3ca, new Item.Properties()));
	public static final RegistryObject<Item> STARBRITE_SPAWN_EGG = REGISTRY.register("starbrite_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.STARBRITE, 0x758e7e, 0xa2c6bb, new Item.Properties()));
	public static final RegistryObject<Item> ABYSSALER_SPAWN_EGG = REGISTRY.register("abyssaler_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.ABYSSALER, -10066177, -10027009, new Item.Properties()));
	public static final RegistryObject<Item> SOLISH_SPAWN_EGG = REGISTRY.register("solish_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SOLISH, -11820857, -15440192, new Item.Properties()));
	public static final RegistryObject<Item> BENTHICT_SPAWN_EGG = REGISTRY.register("benthict_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.BENTHICT, -16766872, -16756523, new Item.Properties()));
	public static final RegistryObject<Item> PRANCED_DOLPHIN_SPAWN_EGG = REGISTRY.register("pranced_dolphin_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.PRANCED_DOLPHIN, 7239857, 11254747, new Item.Properties()));
	public static final RegistryObject<Item> MALIALOSAUR_SPAWN_EGG = REGISTRY.register("malialosaur_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.MALIALOSAUR, 6734073, 1523361, new Item.Properties()));
	public static final RegistryObject<Item> MALIGNASAUR_SPAWN_EGG = REGISTRY.register("malignasaur_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.MALIGNASAUR, -13865258, 0x2852e1, new Item.Properties()));
	public static final RegistryObject<Item> BULGE_SPAWN_EGG = REGISTRY.register("bulge_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.BULGE, 0x70c9fe, -15907170, new Item.Properties()));
	public static final RegistryObject<Item> FURY_BULGE_SPAWN_EGG = REGISTRY.register("fury_bulge_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.FURY_BULGE, 0x70abfe, -15907170, new Item.Properties()));
	public static final RegistryObject<Item> QOAIKU_SEEKER_SPAWN_EGG = REGISTRY.register("qoaiku_seeker_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.QOAIKU_SEEKER, -16408594, -16747451, new Item.Properties()));
	public static final RegistryObject<Item> SEAWAVE_SPAWN_EGG = REGISTRY.register("seawave_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SEAWAVE, 0x00af9b, -15907170, new Item.Properties()));
	public static final RegistryObject<Item> FURY_SEAWAVE_SPAWN_EGG = REGISTRY.register("fury_seawave_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.FURY_SEAWAVE, 0x0097af, -15907170, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> GEMSTONE_MALIGNASAUR_SPAWN_EGG = REGISTRY.register("gemstone_malignasaur_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.GEMSTONE_MALIGNASAUR, -13865258, -5576705, new Item.Properties().rarity(Rarity.EPIC)));
	//明草
	public static final RegistryObject<Item> BRIGHT_COBBLESTONE = block(JerotesVillageBlocks.BRIGHT_COBBLESTONE);
	public static final RegistryObject<Item> BRIGHT_COBBLESTONE_STAIRS = block(JerotesVillageBlocks.BRIGHT_COBBLESTONE_STAIRS);
	public static final RegistryObject<Item> BRIGHT_COBBLESTONE_SLAB = block(JerotesVillageBlocks.BRIGHT_COBBLESTONE_SLAB);
	public static final RegistryObject<Item> BRIGHT_COBBLESTONE_WALL = block(JerotesVillageBlocks.BRIGHT_COBBLESTONE_WALL);
	public static final RegistryObject<Item> BRIGHT_GRASS = block(JerotesVillageBlocks.BRIGHT_GRASS);
	public static final RegistryObject<Item> TALL_BRIGHT_GRASS = highBlock(JerotesVillageBlocks.TALL_BRIGHT_GRASS);
	public static final RegistryObject<Item> FLASHING_GRASS = block(JerotesVillageBlocks.FLASHING_GRASS);
	public static final RegistryObject<Item> PURPLE_PONUO = block(JerotesVillageBlocks.PURPLE_PONUO);
	public static final RegistryObject<Item> SECOND_ROUND_DANDELION = block(JerotesVillageBlocks.SECOND_ROUND_DANDELION);
	public static final RegistryObject<Item> WHITE_BREEZE_VIOLET = block(JerotesVillageBlocks.WHITE_BREEZE_VIOLET);
	public static final RegistryObject<Item> SECOND_ROUND_ALOE = block(JerotesVillageBlocks.SECOND_ROUND_ALOE);
	public static final RegistryObject<Item> SECOND_ROUND_ALOE_BUNDLE = block(JerotesVillageBlocks.SECOND_ROUND_ALOE_BUNDLE);
	public static final RegistryObject<Item> GLOW_SHINE = block(JerotesVillageBlocks.GLOW_SHINE);
	public static final RegistryObject<Item> TANGLER_HEAD = REGISTRY.register("tangler_head", () -> new StandingAndWallBlockItem(JerotesVillageBlocks.TANGLER_HEAD.get(), JerotesVillageBlocks.TANGLER_WALL_HEAD.get(), new Item.Properties().rarity(Rarity.UNCOMMON), Direction.DOWN));
	///明光木 Bright Glow Wood
	public static final RegistryObject<Item> BRIGHT_GLOW_LOG = block(JerotesVillageBlocks.BRIGHT_GLOW_LOG);
	public static final RegistryObject<Item> BRIGHT_GLOW_WOOD = block(JerotesVillageBlocks.BRIGHT_GLOW_WOOD);
	public static final RegistryObject<Item> STRIPPED_BRIGHT_GLOW_LOG = block(JerotesVillageBlocks.STRIPPED_BRIGHT_GLOW_LOG);
	public static final RegistryObject<Item> STRIPPED_BRIGHT_GLOW_WOOD = block(JerotesVillageBlocks.STRIPPED_BRIGHT_GLOW_WOOD);
	public static final RegistryObject<Item> BRIGHT_GLOW_PLANKS = block(JerotesVillageBlocks.BRIGHT_GLOW_PLANKS);
	public static final RegistryObject<Item> BRIGHT_GLOW_STAIRS = block(JerotesVillageBlocks.BRIGHT_GLOW_STAIRS);
	public static final RegistryObject<Item> BRIGHT_GLOW_SLAB = block(JerotesVillageBlocks.BRIGHT_GLOW_SLAB);
	public static final RegistryObject<Item> BRIGHT_GLOW_FENCE = block(JerotesVillageBlocks.BRIGHT_GLOW_FENCE);
	public static final RegistryObject<Item> BRIGHT_GLOW_FENCE_GATE = block(JerotesVillageBlocks.BRIGHT_GLOW_FENCE_GATE);
	public static final RegistryObject<Item> BRIGHT_GLOW_DOOR = highBlock(JerotesVillageBlocks.BRIGHT_GLOW_DOOR);
	public static final RegistryObject<Item> BRIGHT_GLOW_TRAPDOOR = block(JerotesVillageBlocks.BRIGHT_GLOW_TRAPDOOR);
	public static final RegistryObject<Item> BRIGHT_GLOW_PRESSURE_PLATE = block(JerotesVillageBlocks.BRIGHT_GLOW_PRESSURE_PLATE);
	public static final RegistryObject<Item> BRIGHT_GLOW_BUTTON = block(JerotesVillageBlocks.BRIGHT_GLOW_BUTTON);
	public static final RegistryObject<Item> BRIGHT_GLOW_SIGN = REGISTRY.register("bright_glow_sign", () -> new SignItem(new Item.Properties().stacksTo(16), JerotesVillageBlocks.BRIGHT_GLOW_SIGN.get(), JerotesVillageBlocks.BRIGHT_GLOW_WALL_SIGN.get()));
	public static final RegistryObject<Item> BRIGHT_GLOW_HANGING_SIGN = REGISTRY.register("bright_glow_hanging_sign", () -> new HangingSignItem(JerotesVillageBlocks.BRIGHT_GLOW_HANGING_SIGN.get(), JerotesVillageBlocks.BRIGHT_GLOW_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> BRIGHT_GLOW_LEAVES = block(JerotesVillageBlocks.BRIGHT_GLOW_LEAVES);
	public static final RegistryObject<Item> BRIGHT_GLOW_SAPLING = block(JerotesVillageBlocks.BRIGHT_GLOW_SAPLING);
	public static final RegistryObject<Item> BRIGHT_GLOW_BOAT = REGISTRY.register("bright_glow_boat", () -> new BrightGlowBoat());
	public static final RegistryObject<Item> BRIGHT_GLOW_CHEST_BOAT = REGISTRY.register("bright_glow_chest_boat", () -> new BrightGlowChestBoat());
	public static final RegistryObject<Item> BRIGHT_LAND_DRUM = block(JerotesVillageBlocks.BRIGHT_LAND_DRUM);
	public static final RegistryObject<Item> SECOND_ROUND_NIGRUM_CRATE = block(JerotesVillageBlocks.SECOND_ROUND_NIGRUM_CRATE);
	public static final RegistryObject<Item> BRIGHT_MELON_CRATE = block(JerotesVillageBlocks.BRIGHT_MELON_CRATE);
	public static final RegistryObject<Item> GOLDEN_BRIGHT_MELON_CRATE = rareBlock(JerotesVillageBlocks.GOLDEN_BRIGHT_MELON_CRATE);
	public static final RegistryObject<Item> SECOND_ROUND_NIGRUM = REGISTRY.register("second_round_nigrum", () -> new ItemNameBlockItem(JerotesVillageBlocks.SECOND_ROUND_NIGRUMS.get(), new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.SECOND_ROUND_NIGRUM)));
	public static final RegistryObject<Item> COOKED_SECOND_ROUND_NIGRUM = REGISTRY.register("cooked_second_round_nigrum", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.COOKED_SECOND_ROUND_NIGRUM)));
	public static final RegistryObject<Item> SECOND_ROUND_NIGRUM_SOUP = REGISTRY.register("second_round_nigrum_soup", () -> new BowlFoodItem(new Item.Properties().stacksTo(16).rarity(Rarity.COMMON).food(JerotesVillageFoods.SECOND_ROUND_NIGRUM_SOUP)));
	public static final RegistryObject<Item> BRIGHT_MELON = REGISTRY.register("bright_melon", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.BRIGHT_MELON)));
	public static final RegistryObject<Item> BRIGHT_MELON_SEEDS = REGISTRY.register("bright_melon_seeds", () -> new ItemNameBlockItem(JerotesVillageBlocks.BRIGHT_MELONS.get(), new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> ROTTEN_BRIGHT_MELON = REGISTRY.register("rotten_bright_melon", () -> new RottenBrightMelon());
	public static final RegistryObject<Item> GOLDEN_BRIGHT_MELON = REGISTRY.register("golden_bright_melon", () ->  new ItemDesc(new Item.Properties().stacksTo(64).rarity(Rarity.RARE).food(JerotesVillageFoods.GOLDEN_BRIGHT_MELON), 1));
	public static final RegistryObject<Item> BRIGHT_GOLDEN_MELON_HAMMER = REGISTRY.register("bright_golden_melon_hammer", () -> new BrightGoldenMelonHammer());
	public static final RegistryObject<Item> SECOND_ROUND_ALOE_LEAF = REGISTRY.register("second_round_aloe_leaf", () -> new ItemNameBlockItem(JerotesVillageBlocks.SECOND_ROUND_ALOE_CROP.get(), new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.SECOND_ROUND_ALOE_LEAF)));
	public static final RegistryObject<Item> ALOE_VERA_PULP = REGISTRY.register("aloe_vera_pulp", () -> new AloeVeraPulp());
	public static final RegistryObject<Item> HERO_SOUP = REGISTRY.register("hero_soup", () -> new HeroSoup());
	public static final RegistryObject<Item> BRIGHT_LAND_DRUMSTICK = REGISTRY.register("bright_land_drumstick", () -> new BrightLandDrumstick());
	public static final RegistryObject<Item> BRIGHT_GRASS_UMBRELLA = REGISTRY.register("bright_grass_umbrella", () -> new BrightGrassUmbrella());
	public static final RegistryObject<Item> THROWING_STUBBORN_STONE = REGISTRY.register("throwing_stubborn_stone", () -> new ThrowingStubbornStone());
	public static final RegistryObject<Item> VILLAGER_METAL_THROWING_BALL = REGISTRY.register("villager_metal_throwing_ball", () -> new VillagerMetalThrowingBall());
	public static final RegistryObject<Item> GLASS_THROWING_BALL = REGISTRY.register("glass_throwing_ball", () -> new GlassThrowingBall());
	public static final RegistryObject<Item> POTION_THROWING_BALL = REGISTRY.register("potion_throwing_ball", () -> new PotionThrowingBall());
	public static final RegistryObject<Item> STUBBORN_STONE_CANE = REGISTRY.register("stubborn_stone_cane", () -> new StubbornStoneCane());
	public static final RegistryObject<Item> LEATHER_SLING = REGISTRY.register("leather_sling", () -> new LeatherSling());
	public static final RegistryObject<Item> WOODEN_SLINGSHOT = REGISTRY.register("wooden_slingshot", () -> new WoodenSlingshot());
	public static final RegistryObject<Item> BRIGHT_BOLA = REGISTRY.register("bright_bola", () -> new BrightBola());
	public static final RegistryObject<Item> BRIGHT_GRASS_CARP = REGISTRY.register("bright_grass_carp", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.BRIGHT_GRASS_CARP)));
	public static final RegistryObject<Item> COOKED_BRIGHT_GRASS_CARP = REGISTRY.register("cooked_bright_grass_carp", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.COOKED_BRIGHT_GRASS_CARP)));
	public static final RegistryObject<Item> BRIGHT_GRASS_CARP_BUCKET = REGISTRY.register("bright_grass_carp_bucket", () -> new ItemBucketBase(JerotesVillageEntityType.BRIGHT_GRASS_CARP, Fluids.WATER, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> CHROMASNAP_BASS = REGISTRY.register("chromasnap_bass", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.CHROMASNAP_BASS)));
	public static final RegistryObject<Item> COOKED_CHROMASNAP_BASS = REGISTRY.register("cooked_chromasnap_bass", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.COOKED_CHROMASNAP_BASS)));
	public static final RegistryObject<Item> CHROMASNAP_BASS_BUCKET = REGISTRY.register("chromasnap_bass_bucket", () -> new ItemBucketBase(JerotesVillageEntityType.CHROMASNAP_BASS, Fluids.WATER, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> LAKE_LOACH = REGISTRY.register("lake_loach", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.LAKE_LOACH)));
	public static final RegistryObject<Item> COOKED_LAKE_LOACH = REGISTRY.register("cooked_lake_loach", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.COOKED_LAKE_LOACH)));
	public static final RegistryObject<Item> LAKE_LOACH_BUCKET = REGISTRY.register("lake_loach_bucket", () -> new ItemBucketBase(JerotesVillageEntityType.LAKE_LOACH, Fluids.WATER, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> IRISHTAIL_CHICKEN_EGG = REGISTRY.register("irishtail_chicken_egg", () -> new EggItem(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> SCALY_BEAR_HAIR = REGISTRY.register("scaly_bear_hair", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> SCALY_BEAR_SCALE = REGISTRY.register("scaly_bear_scale", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> SCALY_BEAR_UPGRADE_SMITHING_TEMPLATE = REGISTRY.register("scaly_bear_upgrade_smithing_template", () -> new ScalyBearUpgradeSmithingTemplate());
	public static final RegistryObject<Item> SCALY_BEAR_HELMET = REGISTRY.register("scaly_bear_helmet", () -> new ScalyBearArmor.Helmet());
	public static final RegistryObject<Item> SCALY_BEAR_CHESTPLATE = REGISTRY.register("scaly_bear_chestplate", () -> new ScalyBearArmor.Chestplate());
	public static final RegistryObject<Item> SCALY_BEAR_LEGGINGS = REGISTRY.register("scaly_bear_leggings", () -> new ScalyBearArmor.Leggings());
	public static final RegistryObject<Item> SCALY_BEAR_BOOTS = REGISTRY.register("scaly_bear_boots", () -> new ScalyBearArmor.Boots());
	public static final RegistryObject<Item> MAGICKA_SCALE_UPGRADE_SMITHING_TEMPLATE = REGISTRY.register("magicka_scale_upgrade_smithing_template", () -> new MagickaScaleUpgradeSmithingTemplate());
	public static final RegistryObject<Item> MAGICKA_SCALE_HELMET = REGISTRY.register("magicka_scale_helmet", () -> new MagickaScaleArmor.Helmet());
	public static final RegistryObject<Item> MAGICKA_SCALE_CHESTPLATE = REGISTRY.register("magicka_scale_chestplate", () -> new MagickaScaleArmor.Chestplate());
	public static final RegistryObject<Item> MAGICKA_SCALE_LEGGINGS = REGISTRY.register("magicka_scale_leggings", () -> new MagickaScaleArmor.Leggings());
	public static final RegistryObject<Item> MAGICKA_SCALE_BOOTS = REGISTRY.register("magicka_scale_boots", () -> new MagickaScaleArmor.Boots());
	public static final RegistryObject<Item> BRIGHT_LAND_BEAST_HORN = REGISTRY.register("bright_land_beast_horn", () -> new BrightLandBeastHorn());
	public static final RegistryObject<Item> BRIGHT_LAND_HEART = REGISTRY.register("bright_land_heart", () -> new ItemBaseGiveEffect(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), JerotesVillageMobEffects.BRIGHT_LAND_HEART.get(), 255, 0, 2));
	public static final RegistryObject<Item> BRIGHT_LAND_BEAST_FLUFF = REGISTRY.register("bright_land_beast_fluff", () -> new ItemBossDrop(JerotesVillageEntityType.BOSS_SHOW_ENTITY_BRIGHT_LAND_BEAST, () -> JerotesVillageSoundEvents.BRIGHT_LAND_BEAST_AMBIENT));
	public static final RegistryObject<Item> MUSIC_DISC_BRIGHT_LAND_BEAST = REGISTRY.register("music_disc_bright_land_beast", () -> new RecordItem(1, JerotesVillageSoundEvents.BRIGHT_LAND_BEAST_MUSIC, new Item.Properties().fireResistant().stacksTo(1).rarity(Rarity.EPIC), 733));
	public static final RegistryObject<Item> BRIGHT_GRASS_CARP_SPAWN_EGG = REGISTRY.register("bright_grass_carp_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.BRIGHT_GRASS_CARP, 0x665d46, 0xdadbc2, new Item.Properties()));
	public static final RegistryObject<Item> CHROMASNAP_BASS_SPAWN_EGG = REGISTRY.register("chromasnap_bass_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.CHROMASNAP_BASS, 0x7c776a, 0xec71d9, new Item.Properties()));
	public static final RegistryObject<Item> LAKE_LOACH_SPAWN_EGG = REGISTRY.register("lake_loach_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.LAKE_LOACH, 0x8b682a, 0xcd904b, new Item.Properties()));
	public static final RegistryObject<Item> BRIGHT_MOUSE_SPAWN_EGG = REGISTRY.register("bright_mouse_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.BRIGHT_MOUSE, -2442879, -3300993, new Item.Properties()));
	public static final RegistryObject<Item> IRISHTAIL_CHICKEN_SPAWN_EGG = REGISTRY.register("irishtail_chicken_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.IRISHTAIL_CHICKEN, 0xffc38b, 0xef337a, new Item.Properties()));
	public static final RegistryObject<Item> SCALY_BEAR_SPAWN_EGG = REGISTRY.register("scaly_bear_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SCALY_BEAR, 16167718, -3155329, new Item.Properties()));
	public static final RegistryObject<Item> RUSHER_SPAWN_EGG = REGISTRY.register("rusher_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.RUSHER, 12167718, -6555329, new Item.Properties()));
	public static final RegistryObject<Item> GLOW_SPARROW_SPAWN_EGG = REGISTRY.register("glow_sparrow_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.GLOW_SPARROW, -14147965, -4423968, new Item.Properties()));
	public static final RegistryObject<Item> SOFT_SPIRAL_SHEEP_SPAWN_EGG = REGISTRY.register("soft_spiral_sheep_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SOFT_SPIRAL_SHEEP, 0xd8c79e, 0xfcfaed, new Item.Properties()));
	public static final RegistryObject<Item> ARCHNOSED_HORNBEAST_SPAWN_EGG = REGISTRY.register("archnosed_hornbeast_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.ARCHNOSED_HORNBEAST, -12180196, -9348780, new Item.Properties()));
	public static final RegistryObject<Item> TANGLER_SPAWN_EGG = REGISTRY.register("tangler_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.TANGLER, -11241639, -12278666, new Item.Properties()));
	public static final RegistryObject<Item> BRIGHT_LAND_BEAST_SPAWN_EGG = REGISTRY.register("bright_land_beast_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.BRIGHT_LAND_BEAST, -12180196, -16737536, new Item.Properties().rarity(Rarity.EPIC)));
	//旱火
	public static final RegistryObject<Item> DROUGHT_FIRE_STONE = fireResistanceBlock(JerotesVillageBlocks.DROUGHT_FIRE_STONE);
	public static final RegistryObject<Item> DROUGHT_FIRE_BRICKS = fireResistanceBlock(JerotesVillageBlocks.DROUGHT_FIRE_BRICKS);
	public static final RegistryObject<Item> DROUGHT_FIRE_BRICK_STAIRS = fireResistanceBlock(JerotesVillageBlocks.DROUGHT_FIRE_BRICK_STAIRS);
	public static final RegistryObject<Item> DROUGHT_FIRE_BRICK_SLAB = fireResistanceBlock(JerotesVillageBlocks.DROUGHT_FIRE_BRICK_SLAB);
	public static final RegistryObject<Item> DROUGHT_FIRE_BRICK_WALL = fireResistanceBlock(JerotesVillageBlocks.DROUGHT_FIRE_BRICK_WALL);
	public static final RegistryObject<Item> DROUGHT_FIRE_MUD = fireResistanceBlock(JerotesVillageBlocks.DROUGHT_FIRE_MUD);
	public static final RegistryObject<Item> PIGLIN_SECOND_ROUND_WORLD_PORTAL_BLOCK = block(JerotesVillageBlocks.PIGLIN_SECOND_ROUND_WORLD_PORTAL_BLOCK);
	public static final RegistryObject<Item> FIRELIGHT_STONE = fireResistanceBlock(JerotesVillageBlocks.FIRELIGHT_STONE);
	public static final RegistryObject<Item> SERPON_TORCH = REGISTRY.register("serpon_torch", () -> new StandingAndWallBlockItem(JerotesVillageBlocks.SERPON_TORCH.get(), JerotesVillageBlocks.SERPON_WALL_TORCH.get(), new Item.Properties().fireResistant(), Direction.DOWN));
	public static final RegistryObject<Item> SERPON_CLASSICAL_BOMB = fireResistanceRareBlock(JerotesVillageBlocks.SERPON_CLASSICAL_BOMB);
	public static final RegistryObject<Item> SERPON_CONNECTION_POINT = fireResistanceBlock(JerotesVillageBlocks.SERPON_CONNECTION_POINT);
	public static final RegistryObject<Item> SERPON_ALLOY_BLOCK = fireResistanceBlock(JerotesVillageBlocks.SERPON_ALLOY_BLOCK);
	public static final RegistryObject<Item> SERPON_ALLOY_NUGGET = REGISTRY.register("serpon_alloy_nugget", () -> new Item(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> SERPON_ALLOY_INGOT = REGISTRY.register("serpon_alloy_ingot", () -> new Item(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> PIGLIN_GOLDEN_FIRE = REGISTRY.register("piglin_golden_fire", () -> new PiglinGoldenFire());
	public static final RegistryObject<Item> PIGLIN_BASE_MAP = REGISTRY.register("piglin_base_map", () -> new ItemMagicMap(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), "piglin_base"));
	public static final RegistryObject<Item> HOGLIN_FARM_MAP = REGISTRY.register("hoglin_farm_map", () -> new ItemMagicMap(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), "hoglin_farm"));
	public static final RegistryObject<Item> SERPON_WATCHTOWER_MAP = REGISTRY.register("serpon_watchtower_map", () -> new ItemMagicMap(new Item.Properties().stacksTo(1).rarity(Rarity.RARE), "serpon_watchtower"));
	public static final RegistryObject<Item> SERPON_TRAINING_GROUND_MAP = REGISTRY.register("serpon_training_ground_map", () -> new ItemMagicMap(new Item.Properties().stacksTo(1).rarity(Rarity.RARE), "serpon_training_ground"));
	public static final RegistryObject<Item> SERPON_SWORD = REGISTRY.register("serpon_sword", () -> new SerponSword());
	public static final RegistryObject<Item> SERPON_AXE = REGISTRY.register("serpon_axe", () -> new SerponAxe());
	public static final RegistryObject<Item> SERPON_PICKAXE = REGISTRY.register("serpon_pickaxe", () -> new SerponPickaxe());
	public static final RegistryObject<Item> SERPON_SHOVEL = REGISTRY.register("serpon_shovel", () -> new SerponShovel());
	public static final RegistryObject<Item> SERPON_HOE = REGISTRY.register("serpon_hoe", () -> new SerponHoe());
	public static final RegistryObject<Item> SERPON_DAGGER = REGISTRY.register("serpon_dagger", () -> new SerponDagger());
	public static final RegistryObject<Item> SERPON_MACE = REGISTRY.register("serpon_mace", () -> new SerponMace());
	public static final RegistryObject<Item> SERPON_BLOWPIPE = REGISTRY.register("serpon_blowpipe", () -> new SerponBlowpipe());
	public static final RegistryObject<Item> SERPON_DART = REGISTRY.register("serpon_dart", () -> new SerponDart());
	public static final RegistryObject<Item> SERPON_JAVELIN = REGISTRY.register("serpon_javelin", () -> new SerponJavelin());
	public static final RegistryObject<Item> SERPON_SPEAR = REGISTRY.register("serpon_spear", () -> new SerponSpear());
	public static final RegistryObject<Item> SERPON_PIKE = REGISTRY.register("serpon_pike", () -> new SerponPike());
	public static final RegistryObject<Item> SERPON_GLAIVE = REGISTRY.register("serpon_glaive", () -> new SerponGlaive());
	public static final RegistryObject<Item> SERPON_SPIKED_SHIELD = REGISTRY.register("serpon_spiked_shield", () -> new SerponPikedShield());
	public static final RegistryObject<Item> SERPON_WAR_BEAST_ARMOR = REGISTRY.register("serpon_war_beast_armor", () -> new ItemWarBeastArmor(11, 2, 0, 0xc85e00, JerotesVillage.MODID, "serpon_alloy", new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> SERPON_GIANT_BEAST_ARMOR = REGISTRY.register("serpon_giant_beast_armor", () -> new ItemGiantBeastArmor(13, 3, 0, 0xc85e00, JerotesVillage.MODID, "serpon_alloy", new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> FIRE_APOSTLE_SWORD = REGISTRY.register("fire_apostle_sword", () -> new FireApostleSword());
	public static final RegistryObject<Item> SERPON_CHARIOT_PART = REGISTRY.register("serpon_chariot_part", () -> new Item(new Item.Properties().stacksTo(64).fireResistant()));
	public static final RegistryObject<Item> SERPON_CHARIOT_ITEM = REGISTRY.register("serpon_chariot_item", () -> new SerponChariotItem());
	public static final RegistryObject<Item> SERPON_CROSSBOW_TURRET_PART = REGISTRY.register("serpon_crossbow_turret_part", () -> new Item(new Item.Properties().stacksTo(64).fireResistant()));
	public static final RegistryObject<Item> SERPON_CROSSBOW_TURRET_ITEM = REGISTRY.register("serpon_crossbow_turret_item", () -> new SerponCrossbowTurretItem());
	public static final RegistryObject<Item> FLAMOAR_SCIMITAR = REGISTRY.register("flamoar_scimitar", () -> new FlamoarScimitar());
	public static final RegistryObject<Item> SERPON_HELMET = REGISTRY.register("serpon_helmet", () -> new SerponArmor.Helmet());
	public static final RegistryObject<Item> SERPON_CHESTPLATE = REGISTRY.register("serpon_chestplate", () -> new SerponArmor.Chestplate());
	public static final RegistryObject<Item> SERPON_LEGGINGS = REGISTRY.register("serpon_leggings", () -> new SerponArmor.Leggings());
	public static final RegistryObject<Item> SERPON_BOOTS = REGISTRY.register("serpon_boots", () -> new SerponArmor.Boots());
	public static final RegistryObject<Item> SERPON_POWERHOUSE_UPGRADE_SMITHING_TEMPLATE = REGISTRY.register("serpon_powerhouse_upgrade_smithing_template", () -> new SerponPowerhouseUpgradeSmithingTemplate());
	public static final RegistryObject<Item> SERPON_POWERHOUSE_HELMET = REGISTRY.register("serpon_powerhouse_helmet", () -> new SerponPowerhouseArmor.Helmet());
	public static final RegistryObject<Item> SERPON_POWERHOUSE_CHESTPLATE = REGISTRY.register("serpon_powerhouse_chestplate", () -> new SerponPowerhouseArmor.Chestplate());
	public static final RegistryObject<Item> SERPON_POWERHOUSE_LEGGINGS = REGISTRY.register("serpon_powerhouse_leggings", () -> new SerponPowerhouseArmor.Leggings());
	public static final RegistryObject<Item> SERPON_POWERHOUSE_BOOTS = REGISTRY.register("serpon_powerhouse_boots", () -> new SerponPowerhouseArmor.Boots());
	public static final RegistryObject<Item> SERPON_WIZARD_HELMET = REGISTRY.register("serpon_wizard_helmet", () -> new SerponWizardArmor.Helmet());
	public static final RegistryObject<Item> SERPON_WIZARD_CHESTPLATE = REGISTRY.register("serpon_wizard_chestplate", () -> new SerponWizardArmor.Chestplate());
	public static final RegistryObject<Item> SERPON_WIZARD_LEGGINGS = REGISTRY.register("serpon_wizard_leggings", () -> new SerponWizardArmor.Leggings());
	public static final RegistryObject<Item> SERPON_WIZARD_BOOTS = REGISTRY.register("serpon_wizard_boots", () -> new SerponWizardArmor.Boots());
	public static final RegistryObject<Item> FIRE_APOSTLE_HELMET = REGISTRY.register("fire_apostle_helmet", () -> new FireApostleArmor.Helmet());
	public static final RegistryObject<Item> FIRE_APOSTLE_CHESTPLATE = REGISTRY.register("fire_apostle_chestplate", () -> new FireApostleArmor.Chestplate());
	public static final RegistryObject<Item> FIRE_APOSTLE_LEGGINGS = REGISTRY.register("fire_apostle_leggings", () -> new FireApostleArmor.Leggings());
	public static final RegistryObject<Item> FIRE_APOSTLE_BOOTS = REGISTRY.register("fire_apostle_boots", () -> new FireApostleArmor.Boots());
	public static final RegistryObject<Item> SERPON_SIGNAL_BULLET = REGISTRY.register("serpon_signal_bullet", () -> new SerponSignalBullet());
	public static final RegistryObject<Item> FIRE_SECRETOR_ADHESIVE = REGISTRY.register("fire_secretor_adhesive", () -> new ItemDesc(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), 1));
	public static final RegistryObject<Item> DROUGHT_WILDFIRE_SHIELD = REGISTRY.register("drought_wildfire_shield", () -> new DroughtWildfireShield());
	public static final RegistryObject<Item> SERPENT_DOMINATER_ARMOR_TRIM_SMITHING_TEMPLATE = REGISTRY.register("serpent_dominater_armor_trim_smithing_template", () -> new IremArmorTrimSmithingTemplate(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> SERPON_CREDENTIAL = REGISTRY.register("serpon_credential", () -> new SerponCredential());
	public static final RegistryObject<Item> THE_GRACE_OF_SERPENPERTH = REGISTRY.register("the_grace_of_serpenperth", () -> new TheGraceofSerpenperth());
	public static final RegistryObject<Item> GLAIVE_OF_SERPENT_DOMINATER = REGISTRY.register("glaive_of_serpent_dominater", () -> new GlaiveofSerpentDominater());
	public static final RegistryObject<Item> SERPON_COMBAT_EXECUTER_MEDAL = REGISTRY.register("serpon_combat_executer_medal", () -> new ItemBossDrop(JerotesVillageEntityType.BOSS_SHOW_ENTITY_SERPON_COMBAT_EXECUTER, () -> JerotesVillageSoundEvents.SERPON_COMBAT_EXECUTER_AMBIENT));
	public static final RegistryObject<Item> MUSIC_DISC_SERPON_COMBAT_EXECUTER = REGISTRY.register("music_disc_serpon_combat_executer", () -> new RecordItem(1, JerotesVillageSoundEvents.SERPON_COMBAT_EXECUTER_MUSIC, new Item.Properties().fireResistant().stacksTo(1).rarity(Rarity.EPIC), 439));
	public static final RegistryObject<Item> SERPON_COMBAT_PORTAL = REGISTRY.register("serpon_combat_portal", () -> new SerponCombatPortal());
	public static final RegistryObject<Item> SERPON_CROSSBOW_TURRET_ARROW = REGISTRY.register("serpon_crossbow_turret_arrow", () -> new SerponCrossbowTurretArrow());
	public static final RegistryObject<Item> PIGLIN_RESIDENT_SPAWN_EGG = REGISTRY.register("piglin_resident_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.PIGLIN_RESIDENT, -1531788, -665046, new Item.Properties()));
	public static final RegistryObject<Item> SERPON_COLONIAL_SOLDIER_SPAWN_EGG = REGISTRY.register("serpon_colonial_soldier_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SERPON_COLONIAL_SOLDIER, -13261, -1464286, new Item.Properties()));
	public static final RegistryObject<Item> SERPON_PIONEER_SPAWN_EGG = REGISTRY.register("serpon_pioneer_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SERPON_PIONEER, -13261, -20941, new Item.Properties()));
	public static final RegistryObject<Item> SERPON_RIOTER_SPAWN_EGG = REGISTRY.register("serpon_rioter_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SERPON_RIOTER, -13261, -2192363, new Item.Properties()));
	public static final RegistryObject<Item> SERPON_FLAME_WIZARD_SPAWN_EGG = REGISTRY.register("serpon_flame_wizard_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SERPON_FLAME_WIZARD, -13261, -6342889, new Item.Properties()));
	public static final RegistryObject<Item> SERPON_INTRUDER_SPAWN_EGG = REGISTRY.register("serpon_intruder_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SERPON_INTRUDER, -13261, -1608960, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> SERPON_FIRE_APOSTLE_SPAWN_EGG = REGISTRY.register("serpon_fire_apostle_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SERPON_FIRE_APOSTLE, -13261, -1429248, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> WAR_PYTHON_SPAWN_EGG = REGISTRY.register("war_python_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.WAR_PYTHON, -1470390, -1870558, new Item.Properties()));
	public static final RegistryObject<Item> ROYAL_WAR_PYTHON_SPAWN_EGG = REGISTRY.register("royal_war_python_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.ROYAL_WAR_PYTHON, -1470390, -734893, new Item.Properties()));
	public static final RegistryObject<Item> FLAMOAR_SPAWN_EGG = REGISTRY.register("flamoar_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.FLAMOAR, -4975360, -2400512, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> SERPON_CHARIOT_SPAWN_EGG = REGISTRY.register("serpon_chariot_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SERPON_CHARIOT, -1994496, -10682368, new Item.Properties()));
	public static final RegistryObject<Item> SERPON_CROSSBOW_TURRET_SPAWN_EGG = REGISTRY.register("serpon_crossbow_turret_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SERPON_CROSSBOW_TURRET, 0x5d0000, 0xec9800, new Item.Properties()));
	public static final RegistryObject<Item> SERPON_COMBAT_PLATFORM_SPAWN_EGG = REGISTRY.register("serpon_combat_platform_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SERPON_COMBAT_PLATFORM, -34304, -6476288, new Item.Properties()));
	public static final RegistryObject<Item> FLYING_FIREBALL_SPAWN_EGG = REGISTRY.register("flying_fireball_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.FLYING_FIREBALL, -1930752, -1154560, new Item.Properties()));
	public static final RegistryObject<Item> FURY_FLYING_FIREBALL_SPAWN_EGG = REGISTRY.register("fury_flying_fireball_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.FURY_FLYING_FIREBALL, -1930752, 4456448, new Item.Properties()));
	public static final RegistryObject<Item> DROUGHT_BLAZE_SPAWN_EGG = REGISTRY.register("drought_blaze_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.DROUGHT_BLAZE, 8719360, 16762468, new Item.Properties()));
	public static final RegistryObject<Item> FURY_DROUGHT_BLAZE_SPAWN_EGG = REGISTRY.register("fury_drought_blaze_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.FURY_DROUGHT_BLAZE, 0xff7000, 16762468, new Item.Properties()));
	public static final RegistryObject<Item> FIRE_SECRETOR_SPAWN_EGG = REGISTRY.register("fire_secretor_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.FIRE_SECRETOR, -812288, -14803426, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> DROUGHT_WILDFIRE_SPAWN_EGG = REGISTRY.register("drought_wildfire_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.DROUGHT_WILDFIRE, 0x75002a, 0x7f0000, new Item.Properties()));
	public static final RegistryObject<Item> FURY_DROUGHT_WILDFIRE_SPAWN_EGG = REGISTRY.register("fury_drought_wildfire_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.FURY_DROUGHT_WILDFIRE, 0x850c00, 0x7f0000, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> SERPON_COMBAT_EXECUTER_SPAWN_EGG = REGISTRY.register("serpon_combat_executer_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SERPON_COMBAT_EXECUTER, -13261, -13096857, new Item.Properties().rarity(Rarity.EPIC)));
	//仙境
	public static final RegistryObject<Item> PINK_SLIME_BLOCK = block(JerotesVillageBlocks.PINK_SLIME_BLOCK);
	public static final RegistryObject<Item> PINK_SLIME_BLOCK_ON = block(JerotesVillageBlocks.PINK_SLIME_BLOCK_ON);
	public static final RegistryObject<Item> BOTANIZE_ALTAR = uncommonBlock(JerotesVillageBlocks.BOTANIZE_ALTAR);
	public static final RegistryObject<Item> MAGENTA_MUMS = block(JerotesVillageBlocks.MAGENTA_MUMS);
	public static final RegistryObject<Item> RED_LUSH = block(JerotesVillageBlocks.RED_LUSH);
	public static final RegistryObject<Item> WONDERLAND_FLOWER = block(JerotesVillageBlocks.WONDERLAND_FLOWER);
	public static final RegistryObject<Item> HEARSAY_GRASS = block(JerotesVillageBlocks.HEARSAY_GRASS);
	public static final RegistryObject<Item> AWN_HERB = block(JerotesVillageBlocks.AWN_HERB);
	public static final RegistryObject<Item> WONDERLAND_FRUIT_CRATE = block(JerotesVillageBlocks.WONDERLAND_FRUIT_CRATE);
	public static final RegistryObject<Item> HARMONIOUS_DREAM_BELL = block(JerotesVillageBlocks.HARMONIOUS_DREAM_BELL);
	public static final RegistryObject<Item> WONDERLAND_GIANT_FROG_FROGSPAWN = REGISTRY.register("wonderland_giant_frog_frogspawn", () -> new PlaceOnWaterBlockItem(JerotesVillageBlocks.WONDERLAND_GIANT_FROG_FROGSPAWN.get(), new Item.Properties()));
	public static final RegistryObject<Item> SPRING_FROGLIGHT = block(JerotesVillageBlocks.SPRING_FROGLIGHT);
	public static final RegistryObject<Item> SUMMER_FROGLIGHT = block(JerotesVillageBlocks.SUMMER_FROGLIGHT);
	public static final RegistryObject<Item> AUTUMN_FROGLIGHT = block(JerotesVillageBlocks.AUTUMN_FROGLIGHT);
	public static final RegistryObject<Item> WINTER_FROGLIGHT = block(JerotesVillageBlocks.WINTER_FROGLIGHT);
	public static final RegistryObject<Item> TYRANT_OF_THE_MOUNTAIN_REALM_STATUE = rareBlock(JerotesVillageBlocks.TYRANT_OF_THE_MOUNTAIN_REALM_STATUE);
	public static final RegistryObject<Item> CELESTIAL_COILVINE_CARPET = uncommonBlock(JerotesVillageBlocks.CELESTIAL_COILVINE_CARPET);
	public static final RegistryObject<Item> WONDERLAND_LOG = block(JerotesVillageBlocks.WONDERLAND_LOG);
	public static final RegistryObject<Item> WONDERLAND_WOOD = block(JerotesVillageBlocks.WONDERLAND_WOOD);
	public static final RegistryObject<Item> STRIPPED_WONDERLAND_LOG = block(JerotesVillageBlocks.STRIPPED_WONDERLAND_LOG);
	public static final RegistryObject<Item> STRIPPED_WONDERLAND_WOOD = block(JerotesVillageBlocks.STRIPPED_WONDERLAND_WOOD);
	public static final RegistryObject<Item> WONDERLAND_PLANKS = block(JerotesVillageBlocks.WONDERLAND_PLANKS);
	public static final RegistryObject<Item> WONDERLAND_STAIRS = block(JerotesVillageBlocks.WONDERLAND_STAIRS);
	public static final RegistryObject<Item> WONDERLAND_SLAB = block(JerotesVillageBlocks.WONDERLAND_SLAB);
	public static final RegistryObject<Item> WONDERLAND_FENCE = block(JerotesVillageBlocks.WONDERLAND_FENCE);
	public static final RegistryObject<Item> WONDERLAND_FENCE_GATE = block(JerotesVillageBlocks.WONDERLAND_FENCE_GATE);
	public static final RegistryObject<Item> WONDERLAND_DOOR = highBlock(JerotesVillageBlocks.WONDERLAND_DOOR);
	public static final RegistryObject<Item> WONDERLAND_TRAPDOOR = block(JerotesVillageBlocks.WONDERLAND_TRAPDOOR);
	public static final RegistryObject<Item> WONDERLAND_PRESSURE_PLATE = block(JerotesVillageBlocks.WONDERLAND_PRESSURE_PLATE);
	public static final RegistryObject<Item> WONDERLAND_BUTTON = block(JerotesVillageBlocks.WONDERLAND_BUTTON);
	public static final RegistryObject<Item> WONDERLAND_SIGN = REGISTRY.register("wonderland_sign", () -> new SignItem(new Item.Properties().stacksTo(16), JerotesVillageBlocks.WONDERLAND_SIGN.get(), JerotesVillageBlocks.WONDERLAND_WALL_SIGN.get()));
	public static final RegistryObject<Item> WONDERLAND_HANGING_SIGN = REGISTRY.register("wonderland_hanging_sign", () -> new HangingSignItem(JerotesVillageBlocks.WONDERLAND_HANGING_SIGN.get(), JerotesVillageBlocks.WONDERLAND_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> WONDERLAND_LEAVES = block(JerotesVillageBlocks.WONDERLAND_LEAVES);
	public static final RegistryObject<Item> WONDERLAND_SAPLING = block(JerotesVillageBlocks.WONDERLAND_SAPLING);
	public static final RegistryObject<Item> WONDERLAND_BOAT = REGISTRY.register("wonderland_boat", () -> new WonderlandBoat());
	public static final RegistryObject<Item> WONDERLAND_CHEST_BOAT = REGISTRY.register("wonderland_chest_boat", () -> new WonderlandChestBoat());
	public static final RegistryObject<Item> WONDERLAND_FRUIT = REGISTRY.register("wonderland_fruit", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.WONDERLAND_FRUIT)));
	public static final RegistryObject<Item> IMMORTAL_BREW = REGISTRY.register("immortal_brew", () -> new ImmortalBrew());
	public static final RegistryObject<Item> AWN_HERB_BANDAGE = REGISTRY.register("awn_herb_bandage", () -> new AwnHerbBandage());
	public static final RegistryObject<Item> WONDERLIN_PAINTING = REGISTRY.register("wonderlin_painting", () -> new WonderlinPainting());
	public static final RegistryObject<Item> WONDERLIN_HANG_GLIDER = REGISTRY.register("wonderlin_hang_glider", () -> new WonderlinHangGlider());
	public static final RegistryObject<Item> WONDERLIN_SMALL_BAG = REGISTRY.register("wonderlin_small_bag", () -> new WonderlinSmallBag(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> WONDERLIN_FAKE_EARS = REGISTRY.register("wonderlin_fake_ears", () -> new WonderlinFakeEars());
	public static final RegistryObject<Item> COLLECTING_ARROWS_BALL = REGISTRY.register("collecting_arrows_ball", () -> new CollectingArrowsBall());
	public static final RegistryObject<Item> SUSPICIOUS_BRANCH = REGISTRY.register("suspicious_branch", () -> new SuspiciousBranch());
	public static final RegistryObject<Item> RAMBLER_SKIN_WAND = REGISTRY.register("rambler_skin_wand", () -> new RamblerSkinWand());
	public static final RegistryObject<Item> WONDERLAND_DAGGER_AXE = REGISTRY.register("wonderland_dagger_axe", () -> new WonderlandDaggerAxe());
	public static final RegistryObject<Item> WONDERLAND_HALBERD = REGISTRY.register("wonderland_halberd", () -> new WonderlandHalberd());
	public static final RegistryObject<Item> WONDERLAND_HOOK_SHIELD = REGISTRY.register("wonderland_hook_shield", () -> new WonderlandHookShield());
	public static final RegistryObject<Item> BOTANIZE_VILLAGE_MAP = REGISTRY.register("botanize_village_map", () -> new ItemMagicMap(new Item.Properties().stacksTo(1).rarity(Rarity.RARE), "botanize_village"));
	public static final RegistryObject<Item> WONDERLIN_CAVE_MAP = REGISTRY.register("wonderlin_cave_map", () -> new ItemMagicMap(new Item.Properties().stacksTo(1).rarity(Rarity.RARE), "wonderlin_cave"));
	public static final RegistryObject<Item> PINK_SLIME_BALL = REGISTRY.register("pink_slime_ball", () -> new ItemDesc(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.PINK_SLIME_BALL), 1));
	public static final RegistryObject<Item> GLUTINOUS_CRUST_MEAT_PIE = REGISTRY.register("glutinous_crust_meat_pie", () -> new ItemDesc(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.GLUTINOUS_CRUST_MEAT_PIE), 1));
	public static final RegistryObject<Item> BOTANIZE_ROD = REGISTRY.register("botanize_rod", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> BOTANIZE_POWDER = REGISTRY.register("botanize_powder", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> VINE_THROWING_BALL = REGISTRY.register("vine_throwing_ball", () -> new VineThrowingBall());
	public static final RegistryObject<Item> FLOWMINNOW = REGISTRY.register("flowminnow", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.FLOWMINNOW)));
	public static final RegistryObject<Item> COOKED_FLOWMINNOW = REGISTRY.register("cooked_flowminnow", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.COOKED_FLOWMINNOW)));
	public static final RegistryObject<Item> FLOWMINNOW_BUCKET = REGISTRY.register("flowminnow_bucket", () -> new ItemBucketBase(JerotesVillageEntityType.FLOWMINNOW, Fluids.WATER, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> WONDERLAND_GIANT_FROG_TADPOLE_BUCKET = REGISTRY.register("wonderland_giant_frog_tadpole_bucket", () -> new ItemBucketBase(JerotesVillageEntityType.WONDERLAND_GIANT_FROG_TADPOLE, Fluids.WATER, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> HARMONIOUS_SLIME_BUCKET = REGISTRY.register("harmonious_slime_bucket", () -> new ItemBucketBase(JerotesVillageEntityType.HARMONIOUS_SLIME, Fluids.EMPTY, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> LUCKY_CLUB = REGISTRY.register("lucky_club", () -> new LuckyClub());
	public static final RegistryObject<Item> WONDERLAND_CHALLENGE = REGISTRY.register("wonderland_challenge", () -> new WonderlandChallenge());
	public static final RegistryObject<Item> MOUNTAIN_REALM_NINE_RINGED_DAO = REGISTRY.register("mountain_realm_nine_ringed_dao", () -> new MountainRealmNineRingedDao());
	public static final RegistryObject<Item> MOUNTAIN_REALM_FLAG_SPEAR_OF_SPEAR = REGISTRY.register("mountain_realm_flag_spear_of_spear", () -> new MountainRealmFlagSpear.MountainRealmFlagSpearOfSpear());
	public static final RegistryObject<Item> MOUNTAIN_REALM_FLAG_SPEAR_OF_JAVELIN = REGISTRY.register("mountain_realm_flag_spear_of_javelin", () -> new MountainRealmFlagSpear.MountainRealmFlagSpearOfJavelin());
	public static final RegistryObject<Item> MOUNTAIN_REALM_HELMET = REGISTRY.register("mountain_realm_helmet", () -> new MountainRealmArmor.Helmet());
	public static final RegistryObject<Item> MOUNTAIN_REALM_CHESTPLATE = REGISTRY.register("mountain_realm_chestplate", () -> new MountainRealmArmor.Chestplate());
	public static final RegistryObject<Item> MOUNTAIN_REALM_LEGGINGS = REGISTRY.register("mountain_realm_leggings", () -> new MountainRealmArmor.Leggings());
	public static final RegistryObject<Item> MOUNTAIN_REALM_BOOTS = REGISTRY.register("mountain_realm_boots", () -> new MountainRealmArmor.Boots());
	public static final RegistryObject<Item> TYRANT_OF_THE_MOUNTAIN_REALM_HELMET_PLUME = REGISTRY.register("tyrant_of_the_mountain_realm_helmet_plume", () -> new ItemBossDrop(JerotesVillageEntityType.BOSS_SHOW_ENTITY_TYRANT_OF_THE_MOUNTAIN_REALM, () -> JerotesVillageSoundEvents.TYRANT_OF_THE_MOUNTAIN_REALM_AMBIENT));
	public static final RegistryObject<Item> MUSIC_DISC_TYRANT_OF_THE_MOUNTAIN_REALM = REGISTRY.register("music_disc_tyrant_of_the_mountain_realm", () -> new RecordItem(1, JerotesVillageSoundEvents.TYRANT_OF_THE_MOUNTAIN_REALM_MUSIC, new Item.Properties().fireResistant().stacksTo(1).rarity(Rarity.EPIC), 431));
	public static final RegistryObject<Item> CONTINUOUS_CUT_LEAF = REGISTRY.register("continuous_cut_leaf", () -> new ContinuousCutLeaf());
	public static final RegistryObject<Item> CELESTIAL_COILVINE_BROKEN_VINE = REGISTRY.register("celestial_coilvine_broken_vine", () -> new ItemBossDrop(JerotesVillageEntityType.BOSS_SHOW_ENTITY_CELESTIAL_COILVINE, () -> JerotesVillageSoundEvents.CELESTIAL_COILVINE_AMBIENT));
	public static final RegistryObject<Item> MUSIC_DISC_CELESTIAL_COILVINE = REGISTRY.register("music_disc_celestial_coilvine", () -> new RecordItem(1, JerotesVillageSoundEvents.CELESTIAL_COILVINE_MUSIC, new Item.Properties().fireResistant().stacksTo(1).rarity(Rarity.EPIC), 431));
	public static final RegistryObject<Item> FLOWMINNOW_SPAWN_EGG = REGISTRY.register("flowminnow_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.FLOWMINNOW, 0x969abc, 0xc6a2c3, new Item.Properties()));
	public static final RegistryObject<Item> HARMONIOUS_SLIME_SPAWN_EGG = REGISTRY.register("harmonious_slime_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.HARMONIOUS_SLIME, -26113, -13057, new Item.Properties()));
	public static final RegistryObject<Item> WONDERLAND_YELLOW_DOG_SPAWN_EGG = REGISTRY.register("wonderland_yellow_dog_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.WONDERLAND_YELLOW_DOG, -3756702, -12040120, new Item.Properties()));
	public static final RegistryObject<Item> WONDERLAND_LI_CAT_SPAWN_EGG = REGISTRY.register("wonderland_li_cat_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.WONDERLAND_LI_CAT, -11780050, -9608118, new Item.Properties()));
	public static final RegistryObject<Item> WONDERLAND_RAMBLER_SPAWN_EGG = REGISTRY.register("wonderland_rambler_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.WONDERLAND_RAMBLER, -1071155, -999575, new Item.Properties()));
	public static final RegistryObject<Item> WONDERLAND_GIANT_FROG_SPAWN_EGG = REGISTRY.register("wonderland_giant_frog_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.WONDERLAND_GIANT_FROG, -8217054, -27017, new Item.Properties()));
	public static final RegistryObject<Item> WONDERLAND_GIANT_FROG_TADPOLE_SPAWN_EGG = REGISTRY.register("wonderland_giant_frog_tadpole_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.WONDERLAND_GIANT_FROG_TADPOLE, -10990560, -12898557, new Item.Properties()));
	public static final RegistryObject<Item> WONDERLAND_BRONCO_SPAWN_EGG = REGISTRY.register("wonderland_bronco_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.WONDERLAND_BRONCO, 7428393, 11177817, new Item.Properties()));
	public static final RegistryObject<Item> BOTANIZE_SPAWN_EGG = REGISTRY.register("botanize_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.BOTANIZE, 6594373, 14328218, new Item.Properties()));
	public static final RegistryObject<Item> COILVINE_BOTANIZE_SPAWN_EGG = REGISTRY.register("coilvine_botanize_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.COILVINE_BOTANIZE, 6594373, 14387606, new Item.Properties()));
	public static final RegistryObject<Item> VINE_POD_BOMB_SPAWN_EGG = REGISTRY.register("vine_pod_bomb_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.VINE_POD_BOMB, 0x3c6a19, 0x304a13, new Item.Properties()));
	public static final RegistryObject<Item> WONDERLIN_SPAWN_EGG = REGISTRY.register("wonderlin_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.WONDERLIN, 12749690, 11167319, new Item.Properties()));
	public static final RegistryObject<Item> WONDERLIN_MERCHANT_SPAWN_EGG = REGISTRY.register("wonderlin_merchant_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.WONDERLIN_MERCHANT, 12749690, 3036949, new Item.Properties()));
	public static final RegistryObject<Item> WONDERLIN_WARRIOR_SPAWN_EGG = REGISTRY.register("wonderlin_warrior_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.WONDERLIN_WARRIOR, 12749690, 8148506, new Item.Properties()));
	public static final RegistryObject<Item> TYRANT_OF_THE_MOUNTAIN_REALM_SPAWN_EGG = REGISTRY.register("tyrant_of_the_mountain_realm_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.TYRANT_OF_THE_MOUNTAIN_REALM, 9446183, 14127404, new Item.Properties().rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> CELESTIAL_COILVINE_SPAWN_EGG = REGISTRY.register("celestial_coilvine_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.CELESTIAL_COILVINE, 6663718, 16765672, new Item.Properties().rarity(Rarity.EPIC)));
	//沙屿
	public static final RegistryObject<Item> SUN_SAND = block(JerotesVillageBlocks.SUN_SAND);
	public static final RegistryObject<Item> SUN_SANDSTONE = block(JerotesVillageBlocks.SUN_SANDSTONE);
	public static final RegistryObject<Item> SUN_SANDSTONE_STAIRS = block(JerotesVillageBlocks.SUN_SANDSTONE_STAIRS);
	public static final RegistryObject<Item> SUN_SANDSTONE_SLAB = block(JerotesVillageBlocks.SUN_SANDSTONE_SLAB);
	public static final RegistryObject<Item> SUN_SANDSTONE_WALL = block(JerotesVillageBlocks.SUN_SANDSTONE_WALL);
	public static final RegistryObject<Item> CHISELED_SUN_SANDSTONE = block(JerotesVillageBlocks.CHISELED_SUN_SANDSTONE);
	public static final RegistryObject<Item> SMOOTH_SUN_SANDSTONE = block(JerotesVillageBlocks.SMOOTH_SUN_SANDSTONE);
	public static final RegistryObject<Item> SMOOTH_SUN_SANDSTONE_STAIRS = block(JerotesVillageBlocks.SMOOTH_SUN_SANDSTONE_STAIRS);
	public static final RegistryObject<Item> SMOOTH_SUN_SANDSTONE_SLAB = block(JerotesVillageBlocks.SMOOTH_SUN_SANDSTONE_SLAB);
	public static final RegistryObject<Item> CUT_SUN_SANDSTONE = block(JerotesVillageBlocks.CUT_SUN_SANDSTONE);
	public static final RegistryObject<Item> CUT_SUN_SANDSTONE_SLAB = block(JerotesVillageBlocks.CUT_SUN_SANDSTONE_SLAB);
	public static final RegistryObject<Item> SUSPICIOUS_SUN_SAND = block(JerotesVillageBlocks.SUSPICIOUS_SUN_SAND);
	public static final RegistryObject<Item> THORNY_TORTOISE_EGG = block(JerotesVillageBlocks.THORNY_TORTOISE_EGG);
	public static final RegistryObject<Item> LIGHTNING_POTTERY_SHERD = REGISTRY.register("lightning_pottery_sherd", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RAMBLER_MEAT = REGISTRY.register("rambler_meat", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.RAMBLER_MEAT)));
	public static final RegistryObject<Item> COOKED_RAMBLER_MEAT = REGISTRY.register("cooked_rambler_meat", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.COOKED_RAMBLER_MEAT)));
	public static final RegistryObject<Item> RAMBLER_SKIN = REGISTRY.register("rambler_skin", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> RAMBLER_LEATHER = REGISTRY.register("rambler_leather", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> RAMBLER_LEATHER_STRIP = REGISTRY.register("rambler_leather_strip", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> RAMBLER_LEATHER_STICK = REGISTRY.register("rambler_leather_stick", () -> new RamblerLeatherStick());
	public static final RegistryObject<Item> RAMBLER_LEATHER_WAR_BEAST_ARMOR = REGISTRY.register("rambler_leather_war_beast_armor", () -> new ItemWarBeastArmor(12, 6, 4, 0x974a00, JerotesVillage.MODID, "rambler_leather", new Item.Properties().stacksTo(1).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> RAMBLER_LEATHER_GIANT_BEAST_ARMOR = REGISTRY.register("rambler_leather_giant_beast_armor", () -> new ItemGiantBeastArmor(14, 9, 6, 0x974a00, JerotesVillage.MODID, "rambler_leather", new Item.Properties().stacksTo(1).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> RAMBLER_LEATHER_HELMET = REGISTRY.register("rambler_leather_helmet", () -> new RamblerLeatherArmor.Helmet());
	public static final RegistryObject<Item> RAMBLER_LEATHER_CHESTPLATE = REGISTRY.register("rambler_leather_chestplate", () -> new RamblerLeatherArmor.Chestplate());
	public static final RegistryObject<Item> RAMBLER_LEATHER_LEGGINGS = REGISTRY.register("rambler_leather_leggings", () -> new RamblerLeatherArmor.Leggings());
	public static final RegistryObject<Item> RAMBLER_LEATHER_BOOTS = REGISTRY.register("rambler_leather_boots", () -> new RamblerLeatherArmor.Boots());
	public static final RegistryObject<Item> RAMBLER_LEATHER_SHIELD = REGISTRY.register("rambler_leather_shield", () -> new ItemToolBaseShield(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON).durability(250 * 3), 3f, 1.2f, 15, Ingredient.of(JerotesVillageItems.RAMBLER_LEATHER.get())));
	public static final RegistryObject<Item> RAMBLER_LEATHER_GLOVE = REGISTRY.register("rambler_leather_glove", () -> new RamblerLeatherGlove());
	public static final RegistryObject<Item> SUN_CRAB = REGISTRY.register("sun_crab", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.SUN_CRAB)));
	public static final RegistryObject<Item> COOKED_SUN_CRAB = REGISTRY.register("cooked_sun_crab", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.COOKED_SUN_CRAB)));
	public static final RegistryObject<Item> SUN_CRAB_BUCKET = REGISTRY.register("sun_crab_bucket", () -> new ItemBucketBase(JerotesVillageEntityType.SUN_CRAB, Fluids.WATER, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> ARC_FANG = REGISTRY.register("arc_fang", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> ARC_ARROW = REGISTRY.register("arc_arrow", () -> new ArcArrow());
	public static final RegistryObject<Item> RAMBLER_SPAWN_EGG = REGISTRY.register("rambler_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.RAMBLER, -2776764, -999575, new Item.Properties()));
	public static final RegistryObject<Item> THORNY_TORTOISE_SPAWN_EGG = REGISTRY.register("thorny_tortoise_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.THORNY_TORTOISE, -3355648, -10053376, new Item.Properties()));
	public static final RegistryObject<Item> SUN_CRAB_SPAWN_EGG = REGISTRY.register("sun_crab_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SUN_CRAB, -335206, -11703, new Item.Properties()));
	public static final RegistryObject<Item> BEACH_TERROR_BIRD_SPAWN_EGG = REGISTRY.register("beach_terror_bird_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.BEACH_TERROR_BIRD, 0xae8849, 0xe2cea8, new Item.Properties()));
	//遗迹
	public static final RegistryObject<Item> RUIN_STONE_BRICKS = fireResistanceBlock(JerotesVillageBlocks.RUIN_STONE_BRICKS);
	public static final RegistryObject<Item> RUIN_STONE_BRICK_STAIRS = fireResistanceBlock(JerotesVillageBlocks.RUIN_STONE_BRICK_STAIRS);
	public static final RegistryObject<Item> RUIN_STONE_BRICK_SLAB = fireResistanceBlock(JerotesVillageBlocks.RUIN_STONE_BRICK_SLAB);
	public static final RegistryObject<Item> RUIN_STONE_BRICK_WALL = fireResistanceBlock(JerotesVillageBlocks.RUIN_STONE_BRICK_WALL);
	public static final RegistryObject<Item> INDUSTRIAL_RESIDUAL_SOIL = fireResistanceBlock(JerotesVillageBlocks.INDUSTRIAL_RESIDUAL_SOIL);
	public static final RegistryObject<Item> RAW_MEROR_METAL_BLOCK = fireResistanceRareBlock(JerotesVillageBlocks.RAW_MEROR_METAL_BLOCK);
	public static final RegistryObject<Item> MEROR_METAL_ORE = fireResistanceRareBlock(JerotesVillageBlocks.MEROR_METAL_ORE);
	public static final RegistryObject<Item> DEEPSLATE_MEROR_METAL_ORE = fireResistanceRareBlock(JerotesVillageBlocks.DEEPSLATE_MEROR_METAL_ORE);
	public static final RegistryObject<Item> MEROR_METAL_BLOCK = fireResistanceRareBlock(JerotesVillageBlocks.MEROR_METAL_BLOCK);
	public static final RegistryObject<Item> REFINE_MEROR_METAL_BLOCK = fireResistanceEpicBlock(JerotesVillageBlocks.REFINE_MEROR_METAL_BLOCK);
	public static final RegistryObject<Item> MEROR_PROJECTION_TABLE = fireResistanceEpicBlock(JerotesVillageBlocks.MEROR_PROJECTION_TABLE);
	public static final RegistryObject<Item> RAW_MEROR_METAL = REGISTRY.register("raw_meror_metal", () -> new ItemRareFire());
	public static final RegistryObject<Item> MEROR_METAL_NUGGET = REGISTRY.register("meror_metal_nugget", () -> new ItemRareFire());
	public static final RegistryObject<Item> MEROR_METAL_STICK = REGISTRY.register("meror_metal_stick", () -> new ItemRareFire());
	public static final RegistryObject<Item> MEROR_METAL_INGOT = REGISTRY.register("meror_metal_ingot", () -> new ItemRareFire());
	public static final RegistryObject<Item> MEROR_METAL_MIXTURE = REGISTRY.register("meror_metal_mixture", () -> new ItemRareFire());
	public static final RegistryObject<Item> REFINE_MEROR_METAL_INGOT = REGISTRY.register("refine_meror_metal_ingot", () -> new Item(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> MEROR_METAL_SWORD = REGISTRY.register("meror_metal_sword", () -> new MerorMetalSword());
	public static final RegistryObject<Item> MEROR_METAL_AXE = REGISTRY.register("meror_metal_axe", () -> new MerorMetalAxe());
	public static final RegistryObject<Item> MEROR_METAL_PICKAXE = REGISTRY.register("meror_metal_pickaxe", () -> new MerorMetalPickaxe());
	public static final RegistryObject<Item> MEROR_METAL_SHOVEL = REGISTRY.register("meror_metal_shovel", () -> new MerorMetalShovel());
	public static final RegistryObject<Item> MEROR_METAL_HOE = REGISTRY.register("meror_metal_hoe", () -> new MerorMetalHoe());
	public static final RegistryObject<Item> MEROR_METAL_DAGGER = REGISTRY.register("meror_metal_dagger", () -> new MerorMetalDagger());
	public static final RegistryObject<Item> MEROR_METAL_MACE = REGISTRY.register("meror_metal_mace", () -> new MerorMetalMace());
	public static final RegistryObject<Item> MEROR_METAL_GREATSWORD = REGISTRY.register("meror_metal_greatsword", () -> new MerorMetalGreatsword());
	public static final RegistryObject<Item> MEROR_METAL_SPEAR = REGISTRY.register("meror_metal_spear", () -> new MerorMetalSpear());
	public static final RegistryObject<Item> MEROR_METAL_PIKE = REGISTRY.register("meror_metal_pike", () -> new MerorMetalPike());
	public static final RegistryObject<Item> MEROR_METAL_BOW = REGISTRY.register("meror_metal_bow", () -> new MerorMetalBow());
	public static final RegistryObject<Item> MEROR_METAL_CROSSBOW = REGISTRY.register("meror_metal_crossbow", () -> new MerorMetalCrossbow());
	public static final RegistryObject<Item> MEROR_METAL_ARROW = REGISTRY.register("meror_metal_arrow", () -> new MerorMetalArrow());
	public static final RegistryObject<Item> MEROR_METAL_SHIELD = REGISTRY.register("meror_metal_shield", () -> new ItemToolBaseShield(new Item.Properties().stacksTo(1).durability(5000 * 3).fireResistant().rarity(Rarity.RARE), 5f, 1.2f, 24, Ingredient.of(JerotesVillageItems.MEROR_METAL_INGOT.get())));
	public static final RegistryObject<Item> MEROR_METAL_JAVELIN = REGISTRY.register("meror_metal_javelin", () -> new MerorMetalJavelin());
	public static final RegistryObject<Item> MEROR_METAL_WAR_BEAST_ARMOR = REGISTRY.register("meror_metal_war_beast_armor", () -> new MerorMetalWarBeastArmor());
	public static final RegistryObject<Item> MEROR_METAL_GIANT_BEAST_ARMOR = REGISTRY.register("meror_metal_giant_beast_armor", () -> new MerorMetalGiantBeastArmor());
	public static final RegistryObject<Item> MEROR_METAL_HELMET = REGISTRY.register("meror_metal_helmet", () -> new MerorMetalArmor.Helmet());
	public static final RegistryObject<Item> MEROR_METAL_CHESTPLATE = REGISTRY.register("meror_metal_chestplate", () -> new MerorMetalArmor.Chestplate());
	public static final RegistryObject<Item> MEROR_METAL_LEGGINGS = REGISTRY.register("meror_metal_leggings", () -> new MerorMetalArmor.Leggings());
	public static final RegistryObject<Item> MEROR_METAL_BOOTS = REGISTRY.register("meror_metal_boots", () -> new MerorMetalArmor.Boots());
	public static final RegistryObject<Item> MEROR_BOUNTY_HUNTER_HELMET = REGISTRY.register("meror_bounty_hunter_helmet", () -> new MerorBountyHunterArmor.Helmet());
	public static final RegistryObject<Item> MEROR_BOUNTY_HUNTER_CHESTPLATE = REGISTRY.register("meror_bounty_hunter_chestplate", () -> new MerorBountyHunterArmor.Chestplate());
	public static final RegistryObject<Item> MEROR_BOUNTY_HUNTER_LEGGINGS = REGISTRY.register("meror_bounty_hunter_leggings", () -> new MerorBountyHunterArmor.Leggings());
	public static final RegistryObject<Item> MEROR_BOUNTY_HUNTER_BOOTS = REGISTRY.register("meror_bounty_hunter_boots", () -> new MerorBountyHunterArmor.Boots());
	public static final RegistryObject<Item> MEROR_ENERGY_REGIME = REGISTRY.register("meror_energy_regime", () -> new ItemDesc(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.EPIC), 2));
	public static final RegistryObject<Item> MEROR_RAPIER = REGISTRY.register("meror_rapier", () -> new MerorRapier());
	public static final RegistryObject<Item> MEROR_CHAINSAW = REGISTRY.register("meror_chainsaw", () -> new MerorChainsaw());
	public static final RegistryObject<Item> MEROR_DRILL = REGISTRY.register("meror_drill", () -> new MerorDrill());
	public static final RegistryObject<Item> MEROR_ROLLER = REGISTRY.register("meror_roller", () -> new MerorRoller());
	public static final RegistryObject<Item> MEROR_SCYTHE = REGISTRY.register("meror_scythe", () -> new MerorScythe());
	public static final RegistryObject<Item> MEROR_CHARGING_BOW = REGISTRY.register("meror_charging_bow", () -> new MerorChargingBow());
	public static final RegistryObject<Item> MEROR_SHOTGUN = REGISTRY.register("meror_shotgun", () -> new MerorShotgun());
	public static final RegistryObject<Item> MEROR_BULLET = REGISTRY.register("meror_bullet", () -> new Item(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> MEROR_RIOT_SHIELD = REGISTRY.register("meror_riot_shield", () -> new MerorRiotShield());
	public static final RegistryObject<Item> MEROR_FIRE_SPEAR = REGISTRY.register("meror_fire_spear", () -> new MerorFireSpear());
	public static final RegistryObject<Item> MEROR_PILUM = REGISTRY.register("meror_pilum", () -> new MerorPilum());
	public static final RegistryObject<Item> MEROR_LONG_KNIFE = REGISTRY.register("meror_long_knife", () -> new MerorLongKnife());
	public static final RegistryObject<Item> MEROR_DAGGER = REGISTRY.register("meror_dagger", () -> new MerorDagger());
	public static final RegistryObject<Item> MEROR_IMPACT_PISTOL = REGISTRY.register("meror_impact_pistol", () -> new MerorImpactPistol());
	public static final RegistryObject<Item> MEROR_STANDARD_UPGRADE_SMITHING_TEMPLATE = REGISTRY.register("meror_standard_upgrade_smithing_template", () -> new MerorStandardUpgradeSmithingTemplate());
	public static final RegistryObject<Item> MEROR_STANDARD_HELMET = REGISTRY.register("meror_standard_helmet", () -> new MerorStandardArmor.Helmet());
	public static final RegistryObject<Item> MEROR_STANDARD_CHESTPLATE = REGISTRY.register("meror_standard_chestplate", () -> new MerorStandardArmor.Chestplate());
	public static final RegistryObject<Item> MEROR_STANDARD_LEGGINGS = REGISTRY.register("meror_standard_leggings", () -> new MerorStandardArmor.Leggings());
	public static final RegistryObject<Item> MEROR_STANDARD_BOOTS = REGISTRY.register("meror_standard_boots", () -> new MerorStandardArmor.Boots());
	public static final RegistryObject<Item> MEROR_TOKEN_M = REGISTRY.register("meror_token_m", () -> new ItemDesc(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.RARE), 2));
	public static final RegistryObject<Item> MEROR_TOKEN_E = REGISTRY.register("meror_token_e", () -> new ItemDesc(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.RARE), 2));
	public static final RegistryObject<Item> MEROR_TOKEN_R = REGISTRY.register("meror_token_r", () -> new ItemDesc(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.RARE), 2));
	public static final RegistryObject<Item> ILLAGER_TOWER_MAP = REGISTRY.register("illager_tower_map", () -> new ItemMagicMap(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), "illager_tower"));
	public static final RegistryObject<Item> MEROR_STIMULIN = REGISTRY.register("meror_stimulin", () -> new MerorStimulin());
	public static final RegistryObject<Item> CAPTURED_DUST_MOTH = REGISTRY.register("captured_dust_moth", () -> new ItemCatchBaseAdd(JerotesVillageEntityType.DUST_MOTH, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> CLEANING_ROBOT_ITEM = REGISTRY.register("cleaning_robot_item", () -> new CleaningRobotItem());
	public static final RegistryObject<Item> MEROR_OATHGUARD_MECH_SWORD = REGISTRY.register("meror_oathguard_mech_sword", () -> new MerorOathguardMechSword());
	public static final RegistryObject<Item> MEROR_OATHGUARD_MECH_SWORD_CLOSE = REGISTRY.register("meror_oathguard_mech_sword_close", () -> new MerorOathguardMechSwordClose());
	public static final RegistryObject<Item> MEROR_RETE = REGISTRY.register("meror_rete", () -> new MerorRete());
	public static final RegistryObject<Item> SECOND_ROUNDER_GOLEM_DEBRIS = REGISTRY.register("second_rounder_golem_debris", () -> new ItemBossDrop(JerotesVillageEntityType.BOSS_SHOW_ENTITY_SECOND_ROUNDER_GOLEM, () -> JerotesVillageSoundEvents.SECOND_ROUNDER_GOLEM_AMBIENT));
	public static final RegistryObject<Item> MUSIC_DISC_SECOND_ROUNDER_GOLEM = REGISTRY.register("music_disc_second_rounder_golem", () -> new RecordItem(1, JerotesVillageSoundEvents.SECOND_ROUNDER_GOLEM_MUSIC, new Item.Properties().fireResistant().stacksTo(1).rarity(Rarity.EPIC), 471));
	public static final RegistryObject<Item> RUINS_MOUSE_SPAWN_EGG = REGISTRY.register("ruins_mouse_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.RUINS_MOUSE, 10525338, 14931927, new Item.Properties()));
	public static final RegistryObject<Item> RUSTLE_FOX_SPAWN_EGG = REGISTRY.register("rustle_fox_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.RUSTLE_FOX, 14578975, 16357796, new Item.Properties()));
	public static final RegistryObject<Item> DUST_MOTH_SPAWN_EGG = REGISTRY.register("dust_moth_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.DUST_MOTH, 10132122, 16185078, new Item.Properties()));
	public static final RegistryObject<Item> CLEANING_ROBOT_SPAWN_EGG = REGISTRY.register("cleaning_robot_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.CLEANING_ROBOT, -5144959, -490915, new Item.Properties()));
	public static final RegistryObject<Item> RUINS_BREEZE_SPAWN_EGG = REGISTRY.register("ruins_breeze_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.RUINS_BREEZE, 12488147, 8339079, new Item.Properties()));
	public static final RegistryObject<Item> MEROR_ADVENTURER_SPAWN_EGG = REGISTRY.register("meror_adventurer_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.MEROR_ADVENTURER, -7918531, -10335177, new Item.Properties()));
	public static final RegistryObject<Item> MEROR_MERCHANT_SPAWN_EGG = REGISTRY.register("meror_merchant_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.MEROR_MERCHANT, -7918531, -4436439, new Item.Properties()));
	public static final RegistryObject<Item> MEROR_BOUNTY_HUNTER_SPAWN_EGG = REGISTRY.register("meror_bounty_hunter_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.MEROR_BOUNTY_HUNTER, -7918531, -12237499, new Item.Properties()));
	public static final RegistryObject<Item> MEROR_LOCATOR_SPAWN_EGG = REGISTRY.register("meror_locator_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.MEROR_LOCATOR, 14519957, 14078161, new Item.Properties()));
	public static final RegistryObject<Item> MEROR_CUBE_SPAWN_EGG = REGISTRY.register("meror_cube_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.MEROR_CUBE, 12009287, 13075857, new Item.Properties()));
	public static final RegistryObject<Item> MEROR_MINISTROSITY_SPAWN_EGG = REGISTRY.register("meror_ministrosity_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.MEROR_MINISTROSITY, 12009287, -2001014, new Item.Properties()));
	public static final RegistryObject<Item> MEROR_OATHGUARD_MECH_SPAWN_EGG = REGISTRY.register("meror_oathguard_mech_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.MEROR_OATHGUARD_MECH, 12009287, 0xffe6e6, new Item.Properties()));
	public static final RegistryObject<Item> MEROR_GOLEM_SPAWN_EGG = REGISTRY.register("meror_golem_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.MEROR_GOLEM, 12009287, 11679842, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> MEROR_MONSTROSITY_SPAWN_EGG = REGISTRY.register("meror_monstrosity_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.MEROR_MONSTROSITY, 12009287, 15045814, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> MEROR_CHAMPION_SPAWN_EGG = REGISTRY.register("meror_champion_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.MEROR_CHAMPION, 12009287, 15440035, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> SECOND_ROUNDER_GOLEM_SPAWN_EGG = REGISTRY.register("second_rounder_golem_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SECOND_ROUNDER_GOLEM, 12009287, 16756138, new Item.Properties().rarity(Rarity.EPIC)));
	//深坑
	public static final RegistryObject<Item> DEEP_DUST_BLOCK = block(JerotesVillageBlocks.DEEP_DUST_BLOCK);
	public static final RegistryObject<Item> BLAMER_HEAD = REGISTRY.register("blamer_head", () -> new StandingAndWallBlockItem(JerotesVillageBlocks.BLAMER_HEAD.get(), JerotesVillageBlocks.BLAMER_WALL_HEAD.get(), new Item.Properties().rarity(Rarity.UNCOMMON), Direction.DOWN));
	public static final RegistryObject<Item> DEEP_DUST = REGISTRY.register("deep_dust", () -> new ItemDesc(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON), 1));
	public static final RegistryObject<Item> RUST_BEETLE_MANDIBLE = REGISTRY.register("rust_beetle_mandible", () -> new ItemDesc(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON), 1));
	public static final RegistryObject<Item> BLAMER_SOUL = REGISTRY.register("blamer_soul", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> BLAMER_ROBE = REGISTRY.register("blamer_robe", () -> new BlamerRobeArmor.Chestplate());
	public static final RegistryObject<Item> ANIMATED_IRON_HELMET = REGISTRY.register("animated_iron_helmet", () -> new AnimatedIronArmor.Helmet());
	public static final RegistryObject<Item> ANIMATED_IRON_CHESTPLATE = REGISTRY.register("animated_iron_chestplate", () -> new AnimatedIronArmor.Chestplate());
	public static final RegistryObject<Item> ANIMATED_IRON_LEGGINGS = REGISTRY.register("animated_iron_leggings", () -> new AnimatedIronArmor.Leggings());
	public static final RegistryObject<Item> ANIMATED_IRON_BOOTS = REGISTRY.register("animated_iron_boots", () -> new AnimatedIronArmor.Boots());
	public static final RegistryObject<Item> DEEP_BAT_SPAWN_EGG = REGISTRY.register("deep_bat_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.DEEP_BAT, 6500400, 8220784, new Item.Properties()));
	public static final RegistryObject<Item> RUST_BEETLE_SPAWN_EGG = REGISTRY.register("rust_beetle_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.RUST_BEETLE, -3111879, -13489364, new Item.Properties()));
	public static final RegistryObject<Item> FLOWING_DUST_SPAWN_EGG = REGISTRY.register("flowing_dust_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.FLOWING_DUST, -10724260, -11645362, new Item.Properties()));
	public static final RegistryObject<Item> BLAMER_SPAWN_EGG = REGISTRY.register("blamer_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.BLAMER, -3407770, -13434880, new Item.Properties()));
	public static final RegistryObject<Item> IRON_TEETH_SPAWN_EGG = REGISTRY.register("iron_teeth_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.IRON_TEETH, -9671572, -6645094, new Item.Properties()));
	public static final RegistryObject<Item> FURY_IRON_TEETH_SPAWN_EGG = REGISTRY.register("fury_iron_teeth_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.FURY_IRON_TEETH, -9671572, 4456448, new Item.Properties()));
	public static final RegistryObject<Item> SECOND_ROUNDER_ANIMATED_ARMOR_SPAWN_EGG = REGISTRY.register("second_rounder_animated_armor_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SECOND_ROUNDER_ANIMATED_ARMOR, -4013875, -10528144, new Item.Properties()));
	public static final RegistryObject<Item> FURY_SECOND_ROUNDER_ANIMATED_ARMOR_SPAWN_EGG = REGISTRY.register("fury_second_rounder_animated_armor_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.FURY_SECOND_ROUNDER_ANIMATED_ARMOR, -4013875, 4456448, new Item.Properties()));
	public static final RegistryObject<Item> EYE_DISC_SPAWN_EGG = REGISTRY.register("eye_disc_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.EYE_DISC, -11403264, -12189696, new Item.Properties()));
	public static final RegistryObject<Item> DARISORDER_SPAWN_EGG = REGISTRY.register("darisorder_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.DARISORDER, 0, 0x252423, new Item.Properties()));
	public static final RegistryObject<Item> FURY_DARISORDER_SPAWN_EGG = REGISTRY.register("fury_darisorder_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.FURY_DARISORDER, 0, 0x444342, new Item.Properties().rarity(Rarity.RARE)));
	//虚洞
	public static final RegistryObject<Item> VIRTUAL_CAVE_EVIL_BLOCK = block(JerotesVillageBlocks.VIRTUAL_CAVE_EVIL_BLOCK);
	public static final RegistryObject<Item> SLIDER_BLOCK = block(JerotesVillageBlocks.SLIDER_BLOCK);
	public static final RegistryObject<Item> BLACK_PRETTY_BUSH = block(JerotesVillageBlocks.BLACK_PRETTY_BUSH);
	public static final RegistryObject<Item> FIBRE_DUST_FLOWER = block(JerotesVillageBlocks.FIBRE_DUST_FLOWER);
	public static final RegistryObject<Item> PREDATION_TOOTHED_FLOWER_BLOCK = block(JerotesVillageBlocks.PREDATION_TOOTHED_FLOWER_BLOCK);
	public static final RegistryObject<Item> VIRTUAL_CAVE_FISHING_NET_BLOCK = REGISTRY.register("virtual_cave_fishing_net_block", () -> new VirtualCaveFishingNetBlockItem());
	public static final RegistryObject<Item> SLIDER_MARK = block(JerotesVillageBlocks.SLIDER_MARK);
	public static final RegistryObject<Item> VIRTUAL_CAVE_EVIL_CRYSTAL = REGISTRY.register("virtual_cave_evil_crystal", () -> new VirtualCaveEvilCrystal());
	public static final RegistryObject<Item> VIRTUAL_CAVE_CRYSTAL_ARROW = REGISTRY.register("virtual_cave_crystal_arrow", () -> new VirtualCaveCrystalArrow());
	public static final RegistryObject<Item> VIRTUAL_CAVE_CRYSTAL_THROWING_BALL = REGISTRY.register("virtual_cave_crystal_throwing_ball", () -> new VirtualCaveCrystalThrowingBall());
	public static final RegistryObject<Item> VIRTUAL_CAVE_SPIDER_SETAE = REGISTRY.register("virtual_cave_spider_setae", () -> new VirtualCaveSpiderSetae());
	public static final RegistryObject<Item> VIRTUAL_CAVE_FISHING_NET = REGISTRY.register("virtual_cave_fishing_net", () -> new VirtualCaveFishingNet());
	public static final RegistryObject<Item> VIRTUAL_CAVE_INSECT_NET = REGISTRY.register("virtual_cave_insect_net", () -> new VirtualCaveInsectNet());
	public static final RegistryObject<Item> CAPTURED_DOWNY_SPIDER = REGISTRY.register("captured_downy_spider", () -> new ItemCatchBaseAdd(JerotesVillageEntityType.DOWNY_SPIDER, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> CAPTURED_VIRTUAL_CAVE_COCKROACH = REGISTRY.register("captured_virtual_cave_cockroach", () -> new ItemCatchBaseAdd(JerotesVillageEntityType.VIRTUAL_CAVE_COCKROACH, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> BLUE_SHIMMER_SQUID_BUCKET = REGISTRY.register("blue_shimmer_squid_bucket", () -> new ItemBucketBase(JerotesVillageEntityType.BLUE_SHIMMER_SQUID, Fluids.WATER, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> SLIDER_MUCUS = REGISTRY.register("slider_mucus", () ->  new ItemDesc(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.SLIDER_MUCUS), 1));
	public static final RegistryObject<Item> DOWNY_SPIDER_SPAWN_EGG = REGISTRY.register("downy_spider_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.DOWNY_SPIDER, -8890624, -8290304, new Item.Properties()));
	public static final RegistryObject<Item> VIRTUAL_CAVE_COCKROACH_SPAWN_EGG = REGISTRY.register("virtual_cave_cockroach_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.VIRTUAL_CAVE_COCKROACH, 3610129, 8203029, new Item.Properties()));
	public static final RegistryObject<Item> BLUE_SHIMMER_SQUID_SPAWN_EGG = REGISTRY.register("blue_shimmer_squid_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.BLUE_SHIMMER_SQUID, 50159, 720889, new Item.Properties()));
	public static final RegistryObject<Item> VIRTUAL_CAVE_SPIDER_SPAWN_EGG = REGISTRY.register("virtual_cave_spider_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.VIRTUAL_CAVE_SPIDER, -6724096, -10010880, new Item.Properties()));
	public static final RegistryObject<Item> SLIDER_SPAWN_EGG = REGISTRY.register("slider_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SLIDER, -14680143, -16716676, new Item.Properties()));
	public static final RegistryObject<Item> PREDATION_TOOTHED_FLOWER_SPAWN_EGG = REGISTRY.register("predation_toothed_flower_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.PREDATION_TOOTHED_FLOWER, -11993048, -4452244, new Item.Properties()));
	//荒原
	public static final RegistryObject<Item> WILDERNESS_DIRT = block(JerotesVillageBlocks.WILDERNESS_DIRT);
	public static final RegistryObject<Item> STONE_LEAVES_FERN = block(JerotesVillageBlocks.STONE_LEAVES_FERN);
	public static final RegistryObject<Item> LARGE_STONE_LEAVES_FERN = block(JerotesVillageBlocks.LARGE_STONE_LEAVES_FERN);
	public static final RegistryObject<Item> TINBUSH = block(JerotesVillageBlocks.TINBUSH);
	public static final RegistryObject<Item> SUN_ORANGE_FLOWER = block(JerotesVillageBlocks.SUN_ORANGE_FLOWER);
	public static final RegistryObject<Item> GREEN_HUMANOID_CANE = block(JerotesVillageBlocks.GREEN_HUMANOID_CANE);
	public static final RegistryObject<Item> BROWN_MUD_THORNS = block(JerotesVillageBlocks.BROWN_MUD_THORNS);
	public static final RegistryObject<Item> MYSTEREST_FLOWER = block(JerotesVillageBlocks.MYSTEREST_FLOWER);
	public static final RegistryObject<Item> WILDERNESS_THORNS = block(JerotesVillageBlocks.WILDERNESS_THORNS);
	public static final RegistryObject<Item> FLAVORLESS_MELON = block(JerotesVillageBlocks.FLAVORLESS_MELON);
	public static final RegistryObject<Item> GREEN_HUMANOID_CANE_STEMS_BUNDLE = block(JerotesVillageBlocks.GREEN_HUMANOID_CANE_STEMS_BUNDLE);
	public static final RegistryObject<Item> RAMBLER_LEATHER_PLANKS = block(JerotesVillageBlocks.RAMBLER_LEATHER_PLANKS);
	public static final RegistryObject<Item> RAMBLER_LEATHER_BLOCK = block(JerotesVillageBlocks.RAMBLER_LEATHER_BLOCK);
	public static final RegistryObject<Item> BLACK_YELLOW_LEAF_LITTER = block(JerotesVillageBlocks.BLACK_YELLOW_LEAF_LITTER);
	public static final RegistryObject<Item> BLACK_YELLOW_LOG = block(JerotesVillageBlocks.BLACK_YELLOW_LOG);
	public static final RegistryObject<Item> BLACK_YELLOW_WOOD = block(JerotesVillageBlocks.BLACK_YELLOW_WOOD);
	public static final RegistryObject<Item> STRIPPED_BLACK_YELLOW_LOG = block(JerotesVillageBlocks.STRIPPED_BLACK_YELLOW_LOG);
	public static final RegistryObject<Item> STRIPPED_BLACK_YELLOW_WOOD = block(JerotesVillageBlocks.STRIPPED_BLACK_YELLOW_WOOD);
	public static final RegistryObject<Item> BLACK_YELLOW_PLANKS = block(JerotesVillageBlocks.BLACK_YELLOW_PLANKS);
	public static final RegistryObject<Item> BLACK_YELLOW_STAIRS = block(JerotesVillageBlocks.BLACK_YELLOW_STAIRS);
	public static final RegistryObject<Item> BLACK_YELLOW_SLAB = block(JerotesVillageBlocks.BLACK_YELLOW_SLAB);
	public static final RegistryObject<Item> BLACK_YELLOW_FENCE = block(JerotesVillageBlocks.BLACK_YELLOW_FENCE);
	public static final RegistryObject<Item> BLACK_YELLOW_FENCE_GATE = block(JerotesVillageBlocks.BLACK_YELLOW_FENCE_GATE);
	public static final RegistryObject<Item> BLACK_YELLOW_DOOR = highBlock(JerotesVillageBlocks.BLACK_YELLOW_DOOR);
	public static final RegistryObject<Item> BLACK_YELLOW_TRAPDOOR = block(JerotesVillageBlocks.BLACK_YELLOW_TRAPDOOR);
	public static final RegistryObject<Item> BLACK_YELLOW_PRESSURE_PLATE = block(JerotesVillageBlocks.BLACK_YELLOW_PRESSURE_PLATE);
	public static final RegistryObject<Item> BLACK_YELLOW_BUTTON = block(JerotesVillageBlocks.BLACK_YELLOW_BUTTON);
	public static final RegistryObject<Item> BLACK_YELLOW_SIGN = REGISTRY.register("black_yellow_sign", () -> new SignItem(new Item.Properties().stacksTo(16), JerotesVillageBlocks.BLACK_YELLOW_SIGN.get(), JerotesVillageBlocks.BLACK_YELLOW_WALL_SIGN.get()));
	public static final RegistryObject<Item> BLACK_YELLOW_HANGING_SIGN = REGISTRY.register("black_yellow_hanging_sign", () -> new HangingSignItem(JerotesVillageBlocks.BLACK_YELLOW_HANGING_SIGN.get(), JerotesVillageBlocks.BLACK_YELLOW_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> BLACK_YELLOW_LEAVES = block(JerotesVillageBlocks.BLACK_YELLOW_LEAVES);
	public static final RegistryObject<Item> BLACK_YELLOW_SAPLING = block(JerotesVillageBlocks.BLACK_YELLOW_SAPLING);
	public static final RegistryObject<Item> BLACK_YELLOW_BOAT = REGISTRY.register("black_yellow_boat", () -> new BlackYellowBoat());
	public static final RegistryObject<Item> BLACK_YELLOW_CHEST_BOAT = REGISTRY.register("black_yellow_chest_boat", () -> new BlackYellowChestBoat());
	public static final RegistryObject<Item> FLAVORLESS_MELON_SEEDS = REGISTRY.register("flavorless_melon_seeds", () -> new ItemNameBlockItem(JerotesVillageBlocks.FLAVORLESS_MELON_STEM.get(), new Item.Properties()));
	public static final RegistryObject<Item> FLAVORLESS_MELON_SLICE = REGISTRY.register("flavorless_melon_slice", () -> new Item(new Item.Properties().food(JerotesVillageFoods.FLAVORLESS_MELON_SLICE)));
	public static final RegistryObject<Item> GLISTERING_FLAVORLESS_MELON_SLICE = REGISTRY.register("glistering_flavorless_melon_slice", () -> new Item(new Item.Properties().food(JerotesVillageFoods.GLISTERING_FLAVORLESS_MELON_SLICE)));
	public static final RegistryObject<Item> GREEN_HUMANOID_CANE_STEMS = REGISTRY.register("green_humanoid_cane_stems", () -> new ItemNameBlockItem(JerotesVillageBlocks.GREEN_HUMANOID_CANE_CROP.get(), new Item.Properties()));
	public static final RegistryObject<Item> BAGGED_MYSTEREST_PETALS = REGISTRY.register("bagged_mysterest_petals", () -> new BaggedMysterestPetals());
	public static final RegistryObject<Item> MYSTEREST_PERFUME = REGISTRY.register("mysterest_perfume", () -> new MysterestPerfume());
	public static final RegistryObject<Item> ANESTHETIC_TABLET = REGISTRY.register("anesthetic_tablet", () -> new ItemTabletBase(new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON), JerotesMobEffects.ANESTHETIZED_HOLD.get(), 20, 0, MobEffects.MOVEMENT_SLOWDOWN, 1600, 0, true, 0x53a36a, 5658198));
	public static final RegistryObject<Item> ANESTHETIC_THROWING_BALL = REGISTRY.register("anesthetic_throwing_ball", () -> new AnestheticThrowingBall());
	public static final RegistryObject<Item> WILDERNESS_GIANT_CAMP_MAP = REGISTRY.register("wilderness_giant_camp_map", () -> new ItemMagicMap(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), "wilderness_giant_camp"));
	public static final RegistryObject<Item> WILDERNESS_GIANT_HUNTER_CAMP_MAP = REGISTRY.register("wilderness_giant_hunter_camp_map", () -> new ItemMagicMap(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), "wilderness_giant_hunter_camp"));
	public static final RegistryObject<Item> WILDERNESS_WOOD_CORE = REGISTRY.register("wilderness_wood_core", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> WILDERNESS_SWORD = REGISTRY.register("wilderness_sword", () -> new WildernessSword());
	public static final RegistryObject<Item> WILDERNESS_THROWING_SPEAR_OF_SPEAR = REGISTRY.register("wilderness_throwing_spear_of_spear", () -> new WildernessThrowingSpear.WildernessThrowingSpearOfSpear());
	public static final RegistryObject<Item> WILDERNESS_THROWING_SPEAR_OF_JAVELIN = REGISTRY.register("wilderness_throwing_spear_of_javelin", () -> new WildernessThrowingSpear.WildernessThrowingSpearOfJavelin());
	public static final RegistryObject<Item> WILDERNESS_MACHETE = REGISTRY.register("wilderness_machete", () -> new WildernessMachete());
	public static final RegistryObject<Item> WILDERNESS_STICK = REGISTRY.register("wilderness_stick", () -> new WildernessStick());
	public static final RegistryObject<Item> WILDERNESS_STAFF = REGISTRY.register("wilderness_staff", () -> new WildernessStaff());
	public static final RegistryObject<Item> WILDERNESS_BOOMERANG = REGISTRY.register("wilderness_boomerang", () -> new WildernessBoomerang());
	public static final RegistryObject<Item> WILDERNESS_ARROW_THROWER = REGISTRY.register("wilderness_arrow_thrower", () -> new WildernessArrowThrower());
	public static final RegistryObject<Item> WILDERNESS_PIKE = REGISTRY.register("wilderness_pike", () -> new WildernessPike());
	public static final RegistryObject<Item> WILDERNESS_BOW = REGISTRY.register("wilderness_bow", () -> new WildernessBow());
	public static final RegistryObject<Item> WILDERNESS_GIANT_HELMET = REGISTRY.register("wilderness_giant_helmet", () -> new WildernessGiantArmor.Helmet());
	public static final RegistryObject<Item> WILDERNESS_GIANT_CHESTPLATE = REGISTRY.register("wilderness_giant_chestplate", () -> new WildernessGiantArmor.Chestplate());
	public static final RegistryObject<Item> WILDERNESS_GIANT_LEGGINGS = REGISTRY.register("wilderness_giant_leggings", () -> new WildernessGiantArmor.Leggings());
	public static final RegistryObject<Item> WILDERNESS_GIANT_BOOTS = REGISTRY.register("wilderness_giant_boots", () -> new WildernessGiantArmor.Boots());
	public static final RegistryObject<Item> WILDERNESS_HUNTER_HELMET = REGISTRY.register("wilderness_hunter_helmet", () -> new WildernessHunterArmor.Helmet());
	public static final RegistryObject<Item> WILDERNESS_HUNTER_CHESTPLATE = REGISTRY.register("wilderness_hunter_chestplate", () -> new WildernessHunterArmor.Chestplate());
	public static final RegistryObject<Item> WILDERNESS_HUNTER_LEGGINGS = REGISTRY.register("wilderness_hunter_leggings", () -> new WildernessHunterArmor.Leggings());
	public static final RegistryObject<Item> WILDERNESS_HUNTER_BOOTS = REGISTRY.register("wilderness_hunter_boots", () -> new WildernessHunterArmor.Boots());
	public static final RegistryObject<Item> WOODLAND_HEART_GUARDIAN_MASK = REGISTRY.register("woodland_heart_guardian_mask", () -> new WoodlandHeartGuardianMask.Helmet());
	public static final RegistryObject<Item> CAPTURED_BROWN_VENOMOUS_SLUG = REGISTRY.register("captured_brown_venomous_slug", () -> new ItemCatchBaseAdd(JerotesVillageEntityType.BROWN_VENOMOUS_SLUG, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> CLOUD_FUR_VENISON = REGISTRY.register("cloud_fur_venison", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.CLOUD_FUR_VENISON)));
	public static final RegistryObject<Item> COOKED_CLOUD_FUR_VENISON = REGISTRY.register("cooked_cloud_fur_venison", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.COOKED_CLOUD_FUR_VENISON)));
	public static final RegistryObject<Item> BROWN_VENOMOUS_SLUG = REGISTRY.register("brown_venomous_slug", () -> new ItemDesc(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.BROWN_VENOMOUS_SLUG), 1));
	public static final RegistryObject<Item> COOKED_BROWN_VENOMOUS_SLUG = REGISTRY.register("cooked_brown_venomous_slug", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food(JerotesVillageFoods.COOKED_BROWN_VENOMOUS_SLUG)));
	public static final RegistryObject<Item> WILDERNESS_WOLF_SPAWN_EGG = REGISTRY.register("wilderness_wolf_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.WILDERNESS_WOLF, -10924481, -3094863, new Item.Properties()));
	public static final RegistryObject<Item> WILDERNESS_RAMBLER_SPAWN_EGG = REGISTRY.register("wilderness_rambler_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.WILDERNESS_RAMBLER, -2189757, -999575, new Item.Properties()));
	public static final RegistryObject<Item> WILDERNESS_BULL_SPAWN_EGG = REGISTRY.register("wilderness_bull_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.WILDERNESS_BULL, -7306128, -13162215, new Item.Properties()));
	public static final RegistryObject<Item> CLOUD_FUR_DEER_SPAWN_EGG = REGISTRY.register("cloud_fur_deer_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.CLOUD_FUR_DEER, 0xf0ede9, 0x9c6043, new Item.Properties()));
	public static final RegistryObject<Item> BROWN_VENOMOUS_SLUG_SPAWN_EGG = REGISTRY.register("brown_venomous_slug_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.BROWN_VENOMOUS_SLUG, -10469337, -2700864, new Item.Properties()));
	public static final RegistryObject<Item> WILDERNESS_GIANT_TRAMP_SPAWN_EGG = REGISTRY.register("wilderness_giant_tramp_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.WILDERNESS_GIANT_TRAMP, -8819625, -12300241, new Item.Properties()));
	public static final RegistryObject<Item> WILDERNESS_GIANT_HUNTER_SPAWN_EGG = REGISTRY.register("wilderness_giant_hunter_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.WILDERNESS_GIANT_HUNTER, -8819625, -10805248, new Item.Properties()));
	public static final RegistryObject<Item> WOODLAND_HEART_GUARDIAN_SPAWN_EGG = REGISTRY.register("woodland_heart_guardian_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.WOODLAND_HEART_GUARDIAN, 0x80756f, 0x2d5c03, new Item.Properties().rarity(Rarity.RARE)));
	//瘟疫
	public static final RegistryObject<Item> MUSIC_DISC_VARIANT_ZSIEIN = REGISTRY.register("music_disc_variant_zsiein", () -> new RecordItem(1, JerotesVillageSoundEvents.BOSS_MUSIC_1, new Item.Properties().fireResistant().stacksTo(1).rarity(Rarity.EPIC), 471));
	public static final RegistryObject<Item> SCAVENGER_SPAWN_EGG = REGISTRY.register("scavenger_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.SCAVENGER, 12169711, 11511290, new Item.Properties()));
	public static final RegistryObject<Item> BLOODSUCKER_SPAWN_EGG = REGISTRY.register("bloodsucker_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.BLOODSUCKER, 12169711, 10984436, new Item.Properties()));
	public static final RegistryObject<Item> RIGIDER_SPAWN_EGG = REGISTRY.register("rigider_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.RIGIDER, 12169711, 10063347, new Item.Properties()));
	public static final RegistryObject<Item> VARIANT_ZSIEIN_SPAWN_EGG = REGISTRY.register("variant_zsiein_spawn_egg", () -> new ForgeSpawnEggItem(JerotesVillageEntityType.VARIANT_ZSIEIN, 12169711, 3353959, new Item.Properties().rarity(Rarity.EPIC)));
	//发射物
	public static final RegistryObject<Item> CARVED_ALLAY_AMETHYST = REGISTRY.register("carved_allay_amethyst", () -> new Shoot());
	public static final RegistryObject<Item> SEDIMENT_MISSILE = REGISTRY.register("sediment_missile", () -> new Shoot());
	public static final RegistryObject<Item> BITTER_COLD_FROSTBITE = REGISTRY.register("bitter_cold_frostbite", () -> new Shoot());
	public static final RegistryObject<Item> OMINOUS_FLAMES = REGISTRY.register("ominous_flames", () -> new Shoot());
	public static final RegistryObject<Item> ARCANE_LIGHT_SPOT = REGISTRY.register("arcane_light_spot", () -> new Shoot());
	public static final RegistryObject<Item> RADIANT_BOMB = REGISTRY.register("radiant_bomb", () -> new Shoot());
	public static final RegistryObject<Item> SLAVERY_SUPERVISOR_CHAIN = REGISTRY.register("slavery_supervisor_chain", () -> new Shoot());
	public static final RegistryObject<Item> BLOODY_SCREAM = REGISTRY.register("bloody_scream", () -> new Shoot());
	public static final RegistryObject<Item> OMINOUS_FORCE = REGISTRY.register("ominous_force", () -> new Shoot());
	public static final RegistryObject<Item> INDOMITABLE_WILL = REGISTRY.register("indomitable_will", () -> new Shoot());
	public static final RegistryObject<Item> CORROSIVER_BREATH = REGISTRY.register("corrosiver_breath", () -> new Shoot());
	public static final RegistryObject<Item> LIGHTNING_SPIT = REGISTRY.register("lightning_spit", () -> new Shoot());
	public static final RegistryObject<Item> TOXIC_MIST_VOLLEY = REGISTRY.register("toxic_mist_volley", () -> new Shoot());
	public static final RegistryObject<Item> WORM_THUNDERSTORM = REGISTRY.register("worm_thunderstorm", () -> new Shoot());
	public static final RegistryObject<Item> BULGE_BULLET = REGISTRY.register("bulge_bullet", () -> new Shoot());
	public static final RegistryObject<Item> OCEAN_CURRENT_BREATH = REGISTRY.register("ocean_current_breath", () -> new Shoot());
	public static final RegistryObject<Item> WHIRLPOOL = REGISTRY.register("whirlpool", () -> new Shoot());
	public static final RegistryObject<Item> MALIGNASAUR_BOMB = REGISTRY.register("malignasaur_bomb", () -> new Shoot());
	public static final RegistryObject<Item> ABUNDANT_COURAGE = REGISTRY.register("abundant_courage", () -> new Shoot());
	public static final RegistryObject<Item> DROUGHT_FIREBALL = REGISTRY.register("drought_fireball", () -> new Shoot());
	public static final RegistryObject<Item> SERPON_FIRE_CHARGE = REGISTRY.register("serpon_fire_charge", () -> new Shoot());
	public static final RegistryObject<Item> SERPON_FIREBALL = REGISTRY.register("serpon_fireball", () -> new Shoot());
	public static final RegistryObject<Item> SERPON_FLAMES = REGISTRY.register("serpon_flames", () -> new Shoot());
	public static final RegistryObject<Item> SECRETE_FIRE_OIL_BOMB = REGISTRY.register("secrete_fire_oil_bomb", () -> new Shoot());
	public static final RegistryObject<Item> SMOKE_MISSILE = REGISTRY.register("smoke_missile", () -> new Shoot());
	public static final RegistryObject<Item> SERPON_SIROCCO = REGISTRY.register("serpon_sirocco", () -> new Shoot());
	public static final RegistryObject<Item> BOTANIZE_BULLET = REGISTRY.register("botanize_bullet", () -> new Shoot());
	public static final RegistryObject<Item> WILT_MISSILE = REGISTRY.register("wilt_missile", () -> new Shoot());
	public static final RegistryObject<Item> MEROR_FIRE = REGISTRY.register("meror_fire", () -> new Shoot());
	public static final RegistryObject<Item> MEROR_BOMB = REGISTRY.register("meror_bomb", () -> new Shoot());
	public static final RegistryObject<Item> MEROR_SHOCK_WAVE = REGISTRY.register("meror_shock_wave", () -> new Shoot());
	public static final RegistryObject<Item> INTENSE_BOMBING = REGISTRY.register("intense_bombing", () -> new Shoot());
	public static final RegistryObject<Item> EYE_DISC_SHOOT = REGISTRY.register("eye_disc_shoot", () -> new Shoot());
	public static final RegistryObject<Item> PREDATION_TOOTHED_FLOWER_SHOOT = REGISTRY.register("predation_toothed_flower_shoot", () -> new Shoot());
	public static final RegistryObject<Item> ZSIEIN_SNOIC = REGISTRY.register("zsiein_snoic", () -> new Shoot());

	private static RegistryObject<Item> block(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}
	private static RegistryObject<Item> fireResistanceBlock(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().fireResistant()));
	}
	private static RegistryObject<Item> fireResistanceRareBlock(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().fireResistant().rarity(Rarity.RARE)));
	}
	private static RegistryObject<Item> uncommonBlock(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));
	}
	private static RegistryObject<Item> rareBlock(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().fireResistant().rarity(Rarity.RARE)));
	}
	private static RegistryObject<Item> fireResistanceEpicBlock(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().fireResistant().rarity(Rarity.EPIC)));
	}
	private static RegistryObject<Item> highBlock(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new DoubleHighBlockItem(block.get(), new Item.Properties()));
	}
	private static RegistryObject<Item> highfireResistanceEpicBlock(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new DoubleHighBlockItem(block.get(), new Item.Properties().fireResistant().rarity(Rarity.EPIC)));
	}

	public static void setup() {
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.WILDERNESS_DIRT.get().asItem(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.BITTER_COLD_LEAVES.get().asItem(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.BITTER_COLD_SAPLING.get().asItem(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.BRIGHT_GLOW_LEAVES.get().asItem(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.BRIGHT_GLOW_SAPLING.get().asItem(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.WONDERLAND_LEAVES.get().asItem(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.WONDERLAND_SAPLING.get().asItem(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.BLACK_YELLOW_LEAVES.get().asItem(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.BLACK_YELLOW_SAPLING.get().asItem(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.BRIGHT_GRASS.get().asItem(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.TALL_BRIGHT_GRASS.get().asItem(), 0.5F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.PINK_CACTUS.get().asItem(), 0.5F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.STONE_LEAVES_FERN.get().asItem(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.LARGE_STONE_LEAVES_FERN.get().asItem(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.PURPLE_SAND_SHORT_DRY_GRASS.get().asItem(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.PURPLE_SAND_TALL_DRY_GRASS.get().asItem(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.PURPLE_BLACK_DEAD_BUSH.get().asItem(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.BLACK_YELLOW_LEAF_LITTER.get().asItem(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.PINK_CACTUS_FLOWER.get().asItem(), 0.3F);
		//花朵
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.FRAGRANT_LAWN.get().asItem(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.CYAN_ROOT.get().asItem(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.BITTER_COLD_SNOW_FLOWER.get().asItem(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.GEMSTONE_ANEMONE.get().asItem(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.FLASHING_GRASS.get().asItem(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.PURPLE_PONUO.get().asItem(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.SECOND_ROUND_DANDELION.get().asItem(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.WHITE_BREEZE_VIOLET.get().asItem(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.MAGENTA_MUMS.get().asItem(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.RED_LUSH.get().asItem(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.WONDERLAND_FLOWER.get().asItem(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.HEARSAY_GRASS.get().asItem(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.AWN_HERB.get().asItem(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.HARMONIOUS_DREAM_BELL.get().asItem(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.TINBUSH.get().asItem(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.SUN_ORANGE_FLOWER.get().asItem(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.GREEN_HUMANOID_CANE.get().asItem(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.BROWN_MUD_THORNS.get().asItem(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.MYSTEREST_FLOWER.get().asItem(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.BLACK_PRETTY_BUSH.get().asItem(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.FIBRE_DUST_FLOWER.get().asItem(), 0.65F);
		//作物
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.SECOND_ROUND_ALOE.get().asItem(), 0.85F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.SECOND_ROUND_ALOE_LEAF.get().asItem(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.PREDATION_TOOTHED_FLOWER_BLOCK.get().asItem(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.SECOND_ROUND_NIGRUM.get().asItem(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.COOKED_SECOND_ROUND_NIGRUM.get().asItem(), 0.85F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.WILDERNESS_THORNS.get().asItem(), 0.30F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.BRIGHT_MELON.get().asItem(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.GOLDEN_BRIGHT_MELON.get().asItem(), 1.0F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.BRIGHT_MELON_SEEDS.get().asItem(), 0.30F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.ROTTEN_BRIGHT_MELON.get().asItem(), 1.0F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.VINE_THROWING_BALL.get().asItem(), 1.0F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.WONDERLAND_FRUIT.get().asItem(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.FLAVORLESS_MELON.get().asItem(), 0.65f);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.FLAVORLESS_MELON_SEEDS.get().asItem(), 0.3f);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.FLAVORLESS_MELON_SLICE.get().asItem(), 0.5f);
		ComposterBlock.COMPOSTABLES.put(JerotesVillageItems.GLISTERING_FLAVORLESS_MELON_SLICE.get().asItem(), 1.0F);


		CauldronInteraction.WATER.put(JerotesVillageItems.SERPON_WIZARD_HELMET.get(), CauldronInteraction.DYED_ITEM);
		CauldronInteraction.WATER.put(JerotesVillageItems.SERPON_WIZARD_CHESTPLATE.get(), CauldronInteraction.DYED_ITEM);
		CauldronInteraction.WATER.put(JerotesVillageItems.SERPON_WIZARD_LEGGINGS.get(), CauldronInteraction.DYED_ITEM);
		CauldronInteraction.WATER.put(JerotesVillageItems.SERPON_WIZARD_BOOTS.get(), CauldronInteraction.DYED_ITEM);

		//村民金属坯
		OptionalDispenseItemBehavior optionalDispenseItemBehaviorVillagerMetalBase = new OptionalDispenseItemBehavior(){
			@Override
			protected ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
				BlockPos blockPos = JerotesItemsAdd.getPos(blockSource).relative(JerotesItemsAdd.getBlockState(blockSource).getValue(DispenserBlock.FACING));
				List<Mob> list = JerotesItemsAdd.getLevel(blockSource).getEntitiesOfClass(Mob.class, new AABB(blockPos), abstractHorse -> abstractHorse.isAlive());
				for (Mob livingEntity : list) {
					if (livingEntity instanceof AbstractVillager abstractVillager &&
							livingEntity instanceof VillagerDataHolder villager &&
							abstractVillager.isAlive()) {
						VillagerProfession armorer = VillagerProfession.ARMORER;
						VillagerProfession toolSmith = VillagerProfession.TOOLSMITH;
						VillagerProfession weaponSmith = VillagerProfession.WEAPONSMITH;
						VillagerProfession forger = JerotesVillageVillagerProfessions.VILLAGER_FORGER.get();
						boolean profession = villager.getVillagerData().getProfession() == armorer ||
								villager.getVillagerData().getProfession() == toolSmith ||
								villager.getVillagerData().getProfession() == weaponSmith || villager.getVillagerData().getProfession() == forger;

						if (!abstractVillager.isBaby() && profession) {
							if (!abstractVillager.level().isClientSide()) {
								abstractVillager.playSound(SoundEvents.SMITHING_TABLE_USE, 1.0f, abstractVillager.getVoicePitch());
							}
							boolean ingot = false;
							float baseDropProfession = 0.5f;
							float baseDropLevel = 0f + villager.getVillagerData().getLevel() * 0.1f;
							if (villager.getVillagerData().getProfession() == forger) {
								baseDropProfession = 0.75f;
							}
							if (abstractVillager instanceof Villager villagers) {
								villagers.setVillagerXp(villagers.getVillagerXp() + 3);
							}
							if (abstractVillager.getRandom().nextFloat() < baseDropProfession + baseDropLevel) {
								ingot = true;
							}
							ItemStack itemDrop;
							if (!ingot) {
								itemDrop = new ItemStack(JerotesVillageItems.SCRAP_VILLAGER_METAL.get(), 1);
							} else {
								itemDrop = new ItemStack(JerotesVillageItems.VILLAGER_METAL_INGOT.get(), 1);
								if (!abstractVillager.level().isClientSide()) {
									abstractVillager.playSound(SoundEvents.VILLAGER_TRADE, 1.0f, abstractVillager.getVoicePitch());
								}
								for (int i = 0; i < 5; ++i) {
									double d = abstractVillager.getRandom().nextGaussian() * 0.02;
									double d2 = abstractVillager.getRandom().nextGaussian() * 0.02;
									double d3 = abstractVillager.getRandom().nextGaussian() * 0.02;
									abstractVillager.level().addParticle(ParticleTypes.HAPPY_VILLAGER, abstractVillager.getRandomX(1.0), abstractVillager.getRandomY() + 1.0, abstractVillager.getRandomZ(1.0), d, d2, d3);
								}
							}
							itemStack.shrink(1);
							ItemEntity itemEntity = abstractVillager.spawnAtLocation(itemDrop, 1);
							if (itemEntity != null) {
								itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add((abstractVillager.getRandom().nextFloat() - abstractVillager.getRandom().nextFloat()) * 0.1f, abstractVillager.getRandom().nextFloat() * 0.2f, (abstractVillager.getRandom().nextFloat() - abstractVillager.getRandom().nextFloat()) * 0.1f));
							}
							return itemStack;
						}
					}
				}
				return super.execute(blockSource, itemStack);
			}
		};
		DispenserBlock.registerBehavior(JerotesVillageItems.VILLAGER_METAL_BASE.get(), optionalDispenseItemBehaviorVillagerMetalBase);
		//船
		DispenserBlock.registerBehavior(JerotesVillageItems.BITTER_COLD_BOAT.get(), new ABaseBoatDispenseItemBehavior(ABaseBoatType.BITTER_COLD));
		DispenserBlock.registerBehavior(JerotesVillageItems.BITTER_COLD_CHEST_BOAT.get(), new ABaseBoatDispenseItemBehavior(ABaseBoatType.BITTER_COLD, true));
		DispenserBlock.registerBehavior(JerotesVillageItems.BRIGHT_GLOW_BOAT.get(), new ABaseBoatDispenseItemBehavior(ABaseBoatType.BRIGHT_GLOW));
		DispenserBlock.registerBehavior(JerotesVillageItems.BRIGHT_GLOW_CHEST_BOAT.get(), new ABaseBoatDispenseItemBehavior(ABaseBoatType.BRIGHT_GLOW, true));
		DispenserBlock.registerBehavior(JerotesVillageItems.BLACK_YELLOW_BOAT.get(), new ABaseBoatDispenseItemBehavior(ABaseBoatType.BLACK_YELLOW));
		DispenserBlock.registerBehavior(JerotesVillageItems.BLACK_YELLOW_CHEST_BOAT.get(), new ABaseBoatDispenseItemBehavior(ABaseBoatType.BLACK_YELLOW, true));
		DispenserBlock.registerBehavior(JerotesVillageItems.WONDERLAND_BOAT.get(), new ABaseBoatDispenseItemBehavior(ABaseBoatType.WONDERLAND));
		DispenserBlock.registerBehavior(JerotesVillageItems.WONDERLAND_CHEST_BOAT.get(), new ABaseBoatDispenseItemBehavior(ABaseBoatType.WONDERLAND, true));
		//长矛机器
		DispenserBlock.registerBehavior(JerotesVillageItems.SPEAR_MACHINE_ITEM.get(), new SpawnMobDispenseItemBehavior(JerotesVillageEntityType.SPEAR_MACHINE.get()));
		//独轮车
		DispenserBlock.registerBehavior(JerotesVillageItems.UNICYCLE_ITEM.get(), new SpawnMobDispenseItemBehavior(JerotesVillageEntityType.UNICYCLE.get()));
		//蛇龙战车
		DispenserBlock.registerBehavior(JerotesVillageItems.SERPON_CHARIOT_ITEM.get(), new SpawnMobDispenseItemBehavior(JerotesVillageEntityType.SERPON_CHARIOT.get()));
		//蛇龙传送门
		DispenserBlock.registerBehavior(JerotesVillageItems.SERPON_COMBAT_PORTAL.get(), new SpawnMobDispenseItemBehavior(JerotesVillageEntityType.SERPON_COMBAT_EXECUTER.get()));
		//清洁机器
		DispenserBlock.registerBehavior(JerotesVillageItems.CLEANING_ROBOT_ITEM.get(), new SpawnMobDispenseItemBehavior(JerotesVillageEntityType.CLEANING_ROBOT.get()));
		//蛇龙弩台
		DispenserBlock.registerBehavior(JerotesVillageItems.SERPON_CROSSBOW_TURRET_ITEM.get(), new SpawnMobDispenseItemBehavior(JerotesVillageEntityType.SERPON_CROSSBOW_TURRET.get()));
		//铜刻村民箭
		DispenserBlock.registerBehavior(JerotesVillageItems.CARVED_VILLAGER_SPECTRAL_ARROW.get(), new AbstractProjectileDispenseBehavior(){
			@Override
			protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
				CarvedVillagerSpectralArrowEntity arrow = new CarvedVillagerSpectralArrowEntity(level, position.x(), position.y(), position.z(), itemStack.copyWithCount(1));
				arrow.pickup = AbstractArrow.Pickup.ALLOWED;
				return arrow;
			}
		});
		//古二轮箭
		DispenserBlock.registerBehavior(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_ARROW.get(), new AbstractProjectileDispenseBehavior(){
			@Override
			protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
				AncientSecondRounderArrowEntity arrow = new AncientSecondRounderArrowEntity(level, position.x(), position.y(), position.z(), itemStack.copyWithCount(1));
				arrow.pickup = AbstractArrow.Pickup.ALLOWED;
				return arrow;
			}
		});
		//蛇龙吹镖
		DispenserBlock.registerBehavior(JerotesVillageItems.SERPON_DART.get(), new AbstractProjectileDispenseBehavior(){
			@Override
			protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
				SerponDartEntity arrow = new SerponDartEntity(level, position.x(), position.y(), position.z(), itemStack.copyWithCount(1));
				arrow.pickup = AbstractArrow.Pickup.ALLOWED;
				arrow.setSecondsOnFire(100);
				return arrow;
			}
		});
		//电弧箭
		DispenserBlock.registerBehavior(JerotesVillageItems.ARC_ARROW.get(), new AbstractProjectileDispenseBehavior(){
			@Override
			protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
				ArcArrowEntity arrow = new ArcArrowEntity(level, position.x(), position.y(), position.z(), itemStack.copyWithCount(1));
				arrow.pickup = AbstractArrow.Pickup.ALLOWED;
				return arrow;
			}
		});
		//莫厄金属箭
		DispenserBlock.registerBehavior(JerotesVillageItems.MEROR_METAL_ARROW.get(), new AbstractProjectileDispenseBehavior(){
			@Override
			protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
				MerorMetalArrowEntity arrow = new MerorMetalArrowEntity(level, position.x(), position.y(), position.z(), itemStack.copyWithCount(1));
				arrow.pickup = AbstractArrow.Pickup.ALLOWED;
				return arrow;
			}
		});
		//虚洞水晶箭
		DispenserBlock.registerBehavior(JerotesVillageItems.VIRTUAL_CAVE_CRYSTAL_ARROW.get(), new AbstractProjectileDispenseBehavior(){
			@Override
			protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
				VirtualCaveCrystalArrowEntity arrow = new VirtualCaveCrystalArrowEntity(level, position.x(), position.y(), position.z(), itemStack.copyWithCount(1));
				arrow.pickup = AbstractArrow.Pickup.ALLOWED;
				return arrow;
			}
		});
		//虚洞蜘蛛蛰毛
		DispenserBlock.registerBehavior(JerotesVillageItems.VIRTUAL_CAVE_SPIDER_SETAE.get(), new AbstractProjectileDispenseBehavior(){
			@Override
			protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
				VirtualCaveSpiderSetaeEntity arrow = new VirtualCaveSpiderSetaeEntity(level, position.x(), position.y(), position.z(), itemStack.copyWithCount(1));
				arrow.pickup = AbstractArrow.Pickup.ALLOWED;
				return arrow;
			}
		});
		//灾厄炸弹
		DispenserBlock.registerBehavior(JerotesVillageItems.OMINOUS_BOMB.get(), new AbstractProjectileDispenseBehavior(){
			@Override
			protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
				return Util.make(new OminousBombEntity(level, position.x(), position.y(), position.z()), thrown -> thrown.setItem(itemStack));
			}
		});
		//紫沙炼金炸弹
		DispenserBlock.registerBehavior(JerotesVillageItems.PURPLE_SAND_ALCHEMY_BOMB.get(), new AbstractProjectileDispenseBehavior(){
			@Override
			protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
				return Util.make(new PurpleSandAlchemyBombEntity(level, position.x(), position.y(), position.z()), thrown -> thrown.setItem(itemStack));
			}
		});
		//易爆形紫沙炼金炸弹
		DispenserBlock.registerBehavior(JerotesVillageItems.PURPLE_SAND_EXPLOSIVE_ALCHEMY_BOMB.get(), new AbstractProjectileDispenseBehavior(){
			@Override
			protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
				return Util.make(new PurpleSandExplosiveAlchemyBombEntity(level, position.x(), position.y(), position.z()), thrown -> thrown.setItem(itemStack));
			}
		});
		//投掷顽石
		DispenserBlock.registerBehavior(JerotesVillageItems.THROWING_STUBBORN_STONE.get(), new AbstractProjectileDispenseBehavior(){
			@Override
			protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
				return Util.make(new ThrowingStubbornStoneEntity(level, position.x(), position.y(), position.z()), thrown -> thrown.setItem(itemStack));
			}
		});
		//村民金属投团
		DispenserBlock.registerBehavior(JerotesVillageItems.VILLAGER_METAL_THROWING_BALL.get(), new AbstractProjectileDispenseBehavior(){
			@Override
			protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
				return Util.make(new VillagerMetalThrowingBallEntity(level, position.x(), position.y(), position.z()), thrown -> thrown.setItem(itemStack));
			}
		});
		//玻璃投团
		DispenserBlock.registerBehavior(JerotesVillageItems.GLASS_THROWING_BALL.get(), new AbstractProjectileDispenseBehavior(){
			@Override
			protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
				return Util.make(new GlassThrowingBallEntity(level, position.x(), position.y(), position.z()), thrown -> thrown.setItem(itemStack));
			}
		});
		//药水投团
		DispenserBlock.registerBehavior(JerotesVillageItems.POTION_THROWING_BALL.get(), new AbstractProjectileDispenseBehavior(){
			@Override
			protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
				return Util.make(new PotionThrowingBallEntity(level, position.x(), position.y(), position.z()), thrown -> thrown.setItem(itemStack));
			}
		});
		//烂明路脆瓜
		DispenserBlock.registerBehavior(JerotesVillageItems.ROTTEN_BRIGHT_MELON.get(), new AbstractProjectileDispenseBehavior(){
			@Override
			protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
				return Util.make(new RottenBrightMelonEntity(level, position.x(), position.y(), position.z()), thrown -> thrown.setItem(itemStack));
			}
		});
		//藤蔓投团
		DispenserBlock.registerBehavior(JerotesVillageItems.VINE_THROWING_BALL.get(), new AbstractProjectileDispenseBehavior(){
			@Override
			protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
				return Util.make(new VineThrowingBallEntity(level, position.x(), position.y(), position.z()), thrown -> thrown.setItem(itemStack));
			}
		});
		//麻醉投团
		DispenserBlock.registerBehavior(JerotesVillageItems.ANESTHETIC_THROWING_BALL.get(), new AbstractProjectileDispenseBehavior(){
			@Override
			protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
				return Util.make(new AnestheticThrowingBallEntity(level, position.x(), position.y(), position.z()), thrown -> thrown.setItem(itemStack));
			}
		});
		//虚洞水晶投团
		DispenserBlock.registerBehavior(JerotesVillageItems.VIRTUAL_CAVE_CRYSTAL_THROWING_BALL.get(), new AbstractProjectileDispenseBehavior(){
			@Override
			protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
				return Util.make(new VirtualCaveCrystalThrowingBallEntity(level, position.x(), position.y(), position.z()), thrown -> thrown.setItem(itemStack));
			}
		});
		//冰岩投团
		DispenserBlock.registerBehavior(JerotesVillageItems.ICE_ROCK_THROWING_BALL.get(), new AbstractProjectileDispenseBehavior(){
			@Override
			protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
				return Util.make(new IceRockThrowingBallEntity(level, position.x(), position.y(), position.z()), thrown -> thrown.setItem(itemStack));
			}
		});
		//肮脏的鬼婆之锅
		DispenserBlock.registerBehavior(JerotesVillageItems.HAGS_BRUSH.get(), new OptionalDispenseItemBehavior(){
			@Override
			public ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
				Direction direction = JerotesItemsAdd.getBlockState(blockSource).getValue(DispenserBlock.FACING);
				BlockPos blockPos = JerotesItemsAdd.getPos(blockSource).relative(direction);
				ServerLevel serverLevel = JerotesItemsAdd.getLevel(blockSource);
				BlockState blockState = serverLevel.getBlockState(blockPos);
				this.setSuccess(true);
				if (blockState.is(JerotesVillageBlocks.DIRTY_HAGS_CAULDRON.get())) {
					serverLevel.setBlock(blockPos, JerotesVillageBlocks.NEW_HAGS_CAULDRON.get().defaultBlockState(), 2);
					serverLevel.playSound(null, blockPos, SoundEvents.BRUSH_GENERIC, SoundSource.NEUTRAL, 1.0f, 1.0f / (serverLevel.getRandom().nextFloat() * 0.4f + 1.2f) + 1 * 0.5f);
					return itemStack;
				}
				return super.execute(blockSource, itemStack);
			}
		});
		//明路大鼓
		DispenserBlock.registerBehavior(JerotesVillageItems.BRIGHT_LAND_DRUMSTICK.get(), new OptionalDispenseItemBehavior(){
			@Override
			public ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
				Direction direction = JerotesItemsAdd.getBlockState(blockSource).getValue(DispenserBlock.FACING);
				BlockPos blockPos = JerotesItemsAdd.getPos(blockSource).relative(direction);
				ServerLevel serverLevel = JerotesItemsAdd.getLevel(blockSource);
				BlockState blockState = serverLevel.getBlockState(blockPos);
				this.setSuccess(true);
				if (blockState.is(JerotesVillageBlocks.BRIGHT_LAND_DRUM.get())) {
					BrightLandDrum.playNote(null, blockState, serverLevel, blockPos, blockState.getBlock());
					serverLevel.gameEvent(null, GameEvent.BLOCK_CHANGE, blockPos);
					return itemStack;
				}
				return super.execute(blockSource, itemStack);
			}
		});
		//复魂尸空棺
		DispenserBlock.registerBehavior(Items.TOTEM_OF_UNDYING, new OptionalDispenseItemBehavior(){
			@Override
			public ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
				Direction direction = JerotesItemsAdd.getBlockState(blockSource).getValue(DispenserBlock.FACING);
				BlockPos blockPos = JerotesItemsAdd.getPos(blockSource).relative(direction);
				ServerLevel serverLevel = JerotesItemsAdd.getLevel(blockSource);
				BlockState blockState = serverLevel.getBlockState(blockPos);
				this.setSuccess(true);
				if (blockState.is(JerotesVillageBlocks.RESURRECTER_EMPTY_COFFIN.get())) {
					ResurrecterEmptyCoffin.resurrects(serverLevel, blockState, blockPos);
					itemStack.shrink(1);
					return itemStack;
				}
				return super.execute(blockSource, itemStack);
			}
		});
		//沉降领主空棺
		DispenserBlock.registerBehavior(Items.TOTEM_OF_UNDYING, new OptionalDispenseItemBehavior(){
			@Override
			public ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
				Direction direction = JerotesItemsAdd.getBlockState(blockSource).getValue(DispenserBlock.FACING);
				BlockPos blockPos = JerotesItemsAdd.getPos(blockSource).relative(direction);
				ServerLevel serverLevel = JerotesItemsAdd.getLevel(blockSource);
				BlockState blockState = serverLevel.getBlockState(blockPos);
				this.setSuccess(true);
				if (blockState.is(JerotesVillageBlocks.SEDIMENT_LORD_EMPTY_COFFIN.get())) {
					SedimentLordEmptyCoffin.resurrect(serverLevel, blockPos);
					itemStack.shrink(1);
					return itemStack;
				}
				return super.execute(blockSource, itemStack);
			}
		});
		//蛇龙古式炸弹
		DispenserBlock.registerBehavior(JerotesVillageBlocks.SERPON_CLASSICAL_BOMB.get(), new DefaultDispenseItemBehavior(){
			@Override
			protected ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
				ServerLevel serverLevel = JerotesItemsAdd.getLevel(blockSource);
				BlockPos blockPos = JerotesItemsAdd.getPos(blockSource).relative(JerotesItemsAdd.getBlockState(blockSource).getValue(DispenserBlock.FACING));
				PrimedSerponClassicalBombEntity primedSerponClassicalBomb = new PrimedSerponClassicalBombEntity(serverLevel, (double)blockPos.getX() + 0.5, blockPos.getY(), (double)blockPos.getZ() + 0.5, null);
				serverLevel.addFreshEntity(primedSerponClassicalBomb);
				serverLevel.playSound(null, primedSerponClassicalBomb.getX(), primedSerponClassicalBomb.getY(), primedSerponClassicalBomb.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0f, 1.0f);
				serverLevel.gameEvent(null, GameEvent.ENTITY_PLACE, blockPos);
				itemStack.shrink(1);
				return itemStack;
			}
		});
		//战兽铠
		OptionalDispenseItemBehavior optionalDispenseItemBehaviorHorseArmor = new OptionalDispenseItemBehavior(){
			@Override
			protected ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
				BlockPos blockPos = JerotesItemsAdd.getPos(blockSource).relative(JerotesItemsAdd.getBlockState(blockSource).getValue(DispenserBlock.FACING));
				List<AbstractHorse> listHorse = JerotesItemsAdd.getLevel(blockSource).getEntitiesOfClass(AbstractHorse.class, new AABB(blockPos), abstractHorse -> abstractHorse.isAlive() && abstractHorse.canWearArmor());
				for (AbstractHorse abstractHorse2 : listHorse) {
					if (!abstractHorse2.isArmor(itemStack) || abstractHorse2.isWearingArmor() || !abstractHorse2.isTamed()) continue;
					abstractHorse2.getSlot(401).set(itemStack.split(1));
					this.setSuccess(true);
					return itemStack;
				}
				List<Mob> list = JerotesItemsAdd.getLevel(blockSource).getEntitiesOfClass(Mob.class, new AABB(blockPos), abstractHorse -> abstractHorse.isAlive());
				for (Mob abstractHorse : list) {
					if (!(abstractHorse instanceof ArmorEntity abstractHorse2)) continue;
					if (!abstractHorse2.isWarBeastArmor() || !abstractHorse2.canWearArmor() || !abstractHorse2.isArmor(itemStack) || abstractHorse2.isWearingArmor() || (abstractHorse2 instanceof OwnableEntity ownable && ownable.getOwner() == null)) continue;
					Mob mob = (Mob) abstractHorse2;
					mob.getSlot(abstractHorse2.getAddNumber() + 1).set(itemStack.split(1));
					this.setSuccess(true);
					return itemStack;
				}
				return super.execute(blockSource, itemStack);
			}
		};
		DispenserBlock.registerBehavior(JerotesVillageItems.VILLAGER_METAL_WAR_BEAST_ARMOR.get(), optionalDispenseItemBehaviorHorseArmor);
		DispenserBlock.registerBehavior(JerotesVillageItems.RAMBLER_LEATHER_WAR_BEAST_ARMOR.get(), optionalDispenseItemBehaviorHorseArmor);
		DispenserBlock.registerBehavior(JerotesVillageItems.QOAIKU_WAR_BEAST_ARMOR.get(), optionalDispenseItemBehaviorHorseArmor);
		DispenserBlock.registerBehavior(JerotesVillageItems.SERPON_WAR_BEAST_ARMOR.get(), optionalDispenseItemBehaviorHorseArmor);
		DispenserBlock.registerBehavior(JerotesVillageItems.MEROR_METAL_WAR_BEAST_ARMOR.get(), optionalDispenseItemBehaviorHorseArmor);
		DispenserBlock.registerBehavior(JerotesVillageItems.OMINOUS_WAR_BEAST_ARMOR.get(), optionalDispenseItemBehaviorHorseArmor);
		//巨兽铠
		OptionalDispenseItemBehavior optionalDispenseItemBehaviorGiantBeastArmor = new OptionalDispenseItemBehavior(){
			@Override
			protected ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
				BlockPos blockPos = JerotesItemsAdd.getPos(blockSource).relative(JerotesItemsAdd.getBlockState(blockSource).getValue(DispenserBlock.FACING));
				List<Mob> list = JerotesItemsAdd.getLevel(blockSource).getEntitiesOfClass(Mob.class, new AABB(blockPos), abstractHorse -> abstractHorse.isAlive());
				for (Mob abstractHorse : list) {
					if (!(abstractHorse instanceof ArmorEntity abstractHorse2)) continue;
					if (!abstractHorse2.isGiantBeastArmor() || !abstractHorse2.canWearArmor() || !abstractHorse2.isArmor(itemStack) || abstractHorse2.isWearingArmor() || (abstractHorse2 instanceof OwnableEntity ownable && ownable.getOwner() == null)) continue;
					Mob mob = (Mob) abstractHorse2;
					mob.getSlot(abstractHorse2.getAddNumber() + 1).set(itemStack.split(1));
					this.setSuccess(true);
					return itemStack;
				}
				return super.execute(blockSource, itemStack);
			}
		};
		DispenserBlock.registerBehavior(JerotesVillageItems.VILLAGER_METAL_GIANT_BEAST_ARMOR.get(), optionalDispenseItemBehaviorGiantBeastArmor);
		DispenserBlock.registerBehavior(JerotesVillageItems.RAMBLER_LEATHER_GIANT_BEAST_ARMOR.get(), optionalDispenseItemBehaviorGiantBeastArmor);
		DispenserBlock.registerBehavior(JerotesVillageItems.QOAIKU_GIANT_BEAST_ARMOR.get(), optionalDispenseItemBehaviorGiantBeastArmor);
		DispenserBlock.registerBehavior(JerotesVillageItems.SERPON_GIANT_BEAST_ARMOR.get(), optionalDispenseItemBehaviorGiantBeastArmor);
		DispenserBlock.registerBehavior(JerotesVillageItems.MEROR_METAL_GIANT_BEAST_ARMOR.get(), optionalDispenseItemBehaviorGiantBeastArmor);
		DispenserBlock.registerBehavior(JerotesVillageItems.OMINOUS_GIANT_BEAST_ARMOR.get(), optionalDispenseItemBehaviorGiantBeastArmor);
		//桶
		DefaultDispenseItemBehavior defaultDispenseItemBehaviorBucket = new DefaultDispenseItemBehavior(){
			private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

			@Override
			public ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
				DispensibleContainerItem dispensibleContainerItem = (DispensibleContainerItem)((Object)itemStack.getItem());
				BlockPos blockPos = JerotesItemsAdd.getPos(blockSource).relative(JerotesItemsAdd.getBlockState(blockSource).getValue(DispenserBlock.FACING));
				ServerLevel serverLevel = JerotesItemsAdd.getLevel(blockSource);
				if (dispensibleContainerItem.emptyContents(null, serverLevel, blockPos, null)) {
					dispensibleContainerItem.checkExtraContent(null, serverLevel, itemStack, blockPos);
					return new ItemStack(Items.BUCKET);
				}
				return this.defaultDispenseItemBehavior.dispense(blockSource, itemStack);
			}
		};
		DispenserBlock.registerBehavior(JerotesVillageItems.BENTHICT_BUCKET.get(), defaultDispenseItemBehaviorBucket);
		DispenserBlock.registerBehavior(JerotesVillageItems.ABYSSALER_BUCKET.get(), defaultDispenseItemBehaviorBucket);
		DispenserBlock.registerBehavior(JerotesVillageItems.SUN_CRAB_BUCKET.get(), defaultDispenseItemBehaviorBucket);
		DispenserBlock.registerBehavior(JerotesVillageItems.BLUE_SHIMMER_SQUID_BUCKET.get(), defaultDispenseItemBehaviorBucket);
		DispenserBlock.registerBehavior(JerotesVillageItems.WONDERLAND_GIANT_FROG_TADPOLE_BUCKET.get(), defaultDispenseItemBehaviorBucket);
		DispenserBlock.registerBehavior(JerotesVillageItems.BRIGHT_GRASS_CARP_BUCKET.get(), defaultDispenseItemBehaviorBucket);
		DispenserBlock.registerBehavior(JerotesVillageItems.CHROMASNAP_BASS_BUCKET.get(), defaultDispenseItemBehaviorBucket);
		DispenserBlock.registerBehavior(JerotesVillageItems.LAKE_LOACH_BUCKET.get(), defaultDispenseItemBehaviorBucket);
		DispenserBlock.registerBehavior(JerotesVillageItems.FLOWMINNOW_BUCKET.get(), defaultDispenseItemBehaviorBucket);
		DispenserBlock.registerBehavior(JerotesVillageItems.HARMONIOUS_SLIME_BUCKET.get(), defaultDispenseItemBehaviorBucket);
		DispenserBlock.registerBehavior(JerotesVillageItems.BITTER_COD_BUCKET.get(), defaultDispenseItemBehaviorBucket);
		DispenserBlock.registerBehavior(JerotesVillageItems.GREYSCALE_BUCKET.get(), defaultDispenseItemBehaviorBucket);
		DispenserBlock.registerBehavior(JerotesVillageItems.JEWELROAMER_BUCKET.get(), defaultDispenseItemBehaviorBucket);
		DispenserBlock.registerBehavior(JerotesVillageItems.STARBRITE_BUCKET.get(), defaultDispenseItemBehaviorBucket);
		//繁兴荷包
		DispenserBlock.registerBehavior(JerotesVillageItems.PROSPEROUS_POUCH.get(), new OptionalDispenseItemBehavior(){
			@Override
			protected ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
				this.setSuccess(true);
				ServerLevel serverLevel = JerotesItemsAdd.getLevel(blockSource);
				BlockPos blockPos = JerotesItemsAdd.getPos(blockSource).relative(JerotesItemsAdd.getBlockState(blockSource).getValue(DispenserBlock.FACING));
				if (ProsperousPouch.growCrop(itemStack, serverLevel, blockPos, null) ||
						ProsperousPouch.growWaterPlant(itemStack, serverLevel, blockPos, null, null)) {
					if (!serverLevel.isClientSide) {
						serverLevel.levelEvent(1505, blockPos, 0);
					}
				}
				else if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
					this.setSuccess(false);
				}
				else {
					this.setSuccess(false);
				}
				return itemStack;
			}
		});
		//捕捉物
		DefaultDispenseItemBehavior defaultDispenseItemBehaviorCatch = new DefaultDispenseItemBehavior(){
			private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

			@Override
			public ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
				DispensibleContainerItem dispensibleContainerItem = (DispensibleContainerItem)((Object)itemStack.getItem());
				BlockPos blockPos = JerotesItemsAdd.getPos(blockSource).relative(JerotesItemsAdd.getBlockState(blockSource).getValue(DispenserBlock.FACING));
				ServerLevel serverLevel = JerotesItemsAdd.getLevel(blockSource);
				if (dispensibleContainerItem.emptyContents(null, serverLevel, blockPos, null)) {
					dispensibleContainerItem.checkExtraContent(null, serverLevel, itemStack, blockPos);
					return new ItemStack(Items.AIR);
				}
				return this.defaultDispenseItemBehavior.dispense(blockSource, itemStack);
			}
		};
		DispenserBlock.registerBehavior(JerotesVillageItems.CAPTURED_DUST_MOTH.get(), defaultDispenseItemBehaviorCatch);
		DispenserBlock.registerBehavior(JerotesVillageItems.CAPTURED_DOWNY_SPIDER.get(), defaultDispenseItemBehaviorCatch);
		DispenserBlock.registerBehavior(JerotesVillageItems.CAPTURED_VIRTUAL_CAVE_COCKROACH.get(), defaultDispenseItemBehaviorCatch);
		DispenserBlock.registerBehavior(JerotesVillageItems.CAPTURED_BROWN_VENOMOUS_SLUG.get(), defaultDispenseItemBehaviorCatch);
		//头颅
		OptionalDispenseItemBehavior optionalDispenseItemBehaviorHead = new OptionalDispenseItemBehavior(){

			@Override
			protected ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
				this.setSuccess(ArmorItem.dispenseArmor(blockSource, itemStack));
				return itemStack;
			}
		};
		DispenserBlock.registerBehavior(JerotesVillageItems.SECOND_ROUNDER_ZOMBIE_HEAD.get(), optionalDispenseItemBehaviorHead);
		DispenserBlock.registerBehavior(JerotesVillageItems.SECOND_ROUNDER_SKELETON_SKULL.get(), optionalDispenseItemBehaviorHead);
		DispenserBlock.registerBehavior(JerotesVillageItems.RESURRECTER_HEAD.get(), optionalDispenseItemBehaviorHead);
		DispenserBlock.registerBehavior(JerotesVillageItems.PURPLE_SAND_CREEPER_HEAD.get(), optionalDispenseItemBehaviorHead);
		DispenserBlock.registerBehavior(JerotesVillageItems.SPIRVE_HEAD.get(), optionalDispenseItemBehaviorHead);
		DispenserBlock.registerBehavior(JerotesVillageItems.TANGLER_HEAD.get(), optionalDispenseItemBehaviorHead);
		DispenserBlock.registerBehavior(JerotesVillageItems.BLAMER_HEAD.get(), optionalDispenseItemBehaviorHead);
		//类装备
		DispenserBlock.registerBehavior(JerotesVillageItems.WONDERLIN_FAKE_EARS.get(), optionalDispenseItemBehaviorHead);
		//boss掉落物
		OptionalDispenseItemBehavior optionalDispenseItemBehaviorBossDrop = new OptionalDispenseItemBehavior(){
			@Override
			public ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
				Direction direction = JerotesItemsAdd.getBlockState(blockSource).getValue(DispenserBlock.FACING);
				BlockPos blockPos = JerotesItemsAdd.getPos(blockSource).relative(direction);
				ServerLevel serverLevel = JerotesItemsAdd.getLevel(blockSource);
				BlockState blockState = serverLevel.getBlockState(blockPos);
				this.setSuccess(true);
				if (blockState.is(JerotesVillageBlocks.MEROR_PROJECTION_TABLE.get())) {
					float facing = 0f;
					if (blockState.getValue(MerorProjectionTable.FACING) == Direction.EAST) {
						facing = 270f;
					} else if (blockState.getValue(MerorProjectionTable.FACING) == Direction.NORTH) {
						facing = 180f;
					} else if (blockState.getValue(MerorProjectionTable.FACING) == Direction.WEST) {
						facing = 90f;
					} else if (blockState.getValue(MerorProjectionTable.FACING) == Direction.SOUTH) {
						facing = 0f;
					}
					if (serverLevel.getBlockEntity(blockPos) instanceof MerorProjectionTableEntity merorProjectionTable) {
						if (itemStack.getItem() instanceof ItemBossDrop itemBossDrop && merorProjectionTable.getItem(0).isEmpty()) {
							merorProjectionTable.setItem(0, itemStack);
							MerorProjectionTableEntity.summonBoss(blockPos, merorProjectionTable, itemStack, facing, serverLevel);
						}
					}
					return itemStack;
				}
				return super.execute(blockSource, itemStack);
			}
		};
		DispenserBlock.registerBehavior(JerotesVillageItems.MEROR_PROJECTION_TABLE.get(), optionalDispenseItemBehaviorBossDrop);
		DispenserBlock.registerBehavior(JerotesVillageItems.CORROSIVER_BROKEN_HORN.get(), optionalDispenseItemBehaviorBossDrop);
		DispenserBlock.registerBehavior(JerotesVillageItems.PURPLE_SAND_HAG_HAIR.get(), optionalDispenseItemBehaviorBossDrop);
		DispenserBlock.registerBehavior(JerotesVillageItems.GEMSTONE_MALIGNASAUR_SCALE.get(), optionalDispenseItemBehaviorBossDrop);
		DispenserBlock.registerBehavior(JerotesVillageItems.BRIGHT_LAND_BEAST_FLUFF.get(), optionalDispenseItemBehaviorBossDrop);
		DispenserBlock.registerBehavior(JerotesVillageItems.SERPON_COMBAT_EXECUTER_MEDAL.get(), optionalDispenseItemBehaviorBossDrop);
		DispenserBlock.registerBehavior(JerotesVillageItems.CELESTIAL_COILVINE_BROKEN_VINE.get(), optionalDispenseItemBehaviorBossDrop);
		DispenserBlock.registerBehavior(JerotesVillageItems.SECOND_ROUNDER_GOLEM_DEBRIS.get(), optionalDispenseItemBehaviorBossDrop);
		DispenserBlock.registerBehavior(JerotesVillageItems.SEDIMENT_LORD_ROBE_PIECES.get(), optionalDispenseItemBehaviorBossDrop);
		DispenserBlock.registerBehavior(JerotesVillageItems.PROSPEROUS_ENVOY_FLOWER.get(), optionalDispenseItemBehaviorBossDrop);
		DispenserBlock.registerBehavior(JerotesVillageItems.OMINOUS_BANNER_PROJECTION_EMERALD_FRAGMENT.get(), optionalDispenseItemBehaviorBossDrop);
		DispenserBlock.registerBehavior(JerotesVillageItems.TYRANT_OF_THE_MOUNTAIN_REALM_HELMET_PLUME.get(), optionalDispenseItemBehaviorBossDrop);
		//灰鳞鱼抽奖机
		OptionalDispenseItemBehavior optionalDispenseItemBehaviorGreyscale = new OptionalDispenseItemBehavior(){
			@Override
			public ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
				Direction direction = JerotesItemsAdd.getBlockState(blockSource).getValue(DispenserBlock.FACING);
				BlockPos blockPos = JerotesItemsAdd.getPos(blockSource).relative(direction);
				ServerLevel serverLevel = JerotesItemsAdd.getLevel(blockSource);
				BlockState blockState = serverLevel.getBlockState(blockPos);
				this.setSuccess(true);
				if (blockState.is(JerotesVillageBlocks.GREYSCALE_PRIZE_MACHINE.get()) && blockState.getBlock() instanceof GreyscalePrizeMachine greyscalePrizeMachine) {
					greyscalePrizeMachine.useDispense(blockState, serverLevel, blockPos, itemStack);
					return itemStack;
				}
				return super.execute(blockSource, itemStack);
			}
		};
		DispenserBlock.registerBehavior(JerotesVillageItems.GREYSCALE.get(), optionalDispenseItemBehaviorGreyscale);
	}

	public static class ABaseBoatDispenseItemBehavior extends DefaultDispenseItemBehavior {
	   private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
	   private final ABaseBoatType type;
	   private final boolean isChestBoat;

	   public ABaseBoatDispenseItemBehavior(ABaseBoatType type) {
		  this(type, false);
	   }

	   public ABaseBoatDispenseItemBehavior(ABaseBoatType type, boolean bl) {
		  this.type = type;
		  this.isChestBoat = bl;
	   }

	   public ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
		  Direction direction = JerotesItemsAdd.getBlockState(blockSource).getValue(DispenserBlock.FACING);
		  ServerLevel serverlevel = JerotesItemsAdd.getLevel(blockSource);
		  Vec3 vec3 = JerotesItemsAdd.getCenter(blockSource);
		  double d0 = 0.5625D + (double)EntityType.BOAT.getWidth() / 2.0D;
		  double d1 = vec3.x() + (double)direction.getStepX() * d0;
		  double d2 = vec3.y() + (double)((float)direction.getStepY() * 1.125F);
		  double d3 = vec3.z() + (double)direction.getStepZ() * d0;
		  BlockPos blockpos = JerotesItemsAdd.getPos(blockSource).relative(direction);
		  Boat boat;
		  if (this.isChestBoat) {
			 boat = new ABaseChestBoatEntity(serverlevel, d0, d1, d2);
			 ((ABaseChestBoatEntity)boat).setModBoatType(this.type);
		  }
		  else {
			 boat = new ABaseBoatEntity(serverlevel, d0, d1, d2);
			 ((ABaseBoatEntity)boat).setModBoatType(this.type);
		  }
		  boat.setYRot(direction.toYRot());
		  double d4;
		  if (boat.canBoatInFluid(serverlevel.getFluidState(blockpos))) {
			 d4 = 1.0D;
		  } else {
			 if (!serverlevel.getBlockState(blockpos).isAir() || !boat.canBoatInFluid(serverlevel.getFluidState(blockpos.below()))) {
				return this.defaultDispenseItemBehavior.dispense(blockSource, itemStack);
			 }
			 d4 = 0.0D;
		  }
		  EntityType.<Boat>createDefaultStackConfig(serverlevel, itemStack, (Player)null).accept(boat);
		  boat.setPos(d1, d2 + d4, d3);
		  serverlevel.addFreshEntity(boat);
		  itemStack.shrink(1);
		  return itemStack;
	   }
	   protected void playSound(BlockSource blockSource) {
		   JerotesItemsAdd.getLevel(blockSource).levelEvent(1000, JerotesItemsAdd.getPos(blockSource), 0);
	   }
	}

	public static class SpawnMobDispenseItemBehavior extends DefaultDispenseItemBehavior {
	   private final EntityType type;

	   public SpawnMobDispenseItemBehavior(EntityType type) {
		  this.type = type;
	   }

	   protected ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
		  ServerLevel serverLevel = JerotesItemsAdd.getLevel(blockSource);
		  BlockPos blockPos = JerotesItemsAdd.getPos(blockSource).relative(JerotesItemsAdd.getBlockState(blockSource).getValue(DispenserBlock.FACING));
		  Entity entity = this.type.spawn(serverLevel, blockPos, MobSpawnType.EVENT);
		  if (entity != null) {
			 if (entity instanceof SerponChariotEntity serponChariot && itemStack.getItem() == SERPON_CHARIOT_ITEM.get()) {
				serponChariot.setNotSerpon(true);
			 }
			  if (entity instanceof SerponCombatExecuterEntity serponCombatExecuter && itemStack.getItem() == SERPON_COMBAT_PORTAL.get()) {
				  serponCombatExecuter.setTrueCome(false);
			  }
			  if (entity instanceof CleaningRobotEntity cleaningRobotEntity && itemStack.getItem() == CLEANING_ROBOT_ITEM.get()) {
				  cleaningRobotEntity.loadFromCatchTag(itemStack.getOrCreateTag());
				  cleaningRobotEntity.setOn(true);
				  cleaningRobotEntity.setIsItem(true);
			 }
			 serverLevel.addFreshEntity(entity);
			 serverLevel.gameEvent(null, GameEvent.ENTITY_PLACE, blockPos);
			 itemStack.shrink(1);
		  }
		  return itemStack;
	   }
	}
}
