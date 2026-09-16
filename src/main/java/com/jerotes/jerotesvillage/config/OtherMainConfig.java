package com.jerotes.jerotesvillage.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.google.common.collect.Lists;
import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = JerotesVillage.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class OtherMainConfig {
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final CommonConfig COMMON;
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final ClientConfig CLIENT;

    public static boolean SpecialDay;

    public static List<String> CarvedVillagerCustomNameWide;
    public static List<String> CarvedVillagerCustomNameSlim;
    public static boolean SpawnEggCarvedVillagerHasArmor;
    public static boolean CarvedObligation;
    public static boolean CarvedActiveAttackCreeper;
    public static List<String> CarvedGonnaActiveAttackList;
    public static List<String> CarvedNeverGonnaActiveAttack;
    public static List<String> CarvedCanNotAttackList;
    public static boolean CarvedHoundRetire;
    public static boolean CarvedVillagerHelmetHeightAsVillager;


    public static boolean NewProfessionVillagerGiveCustomGift;

    //莫厄通缉
    public static boolean MerorHighEnergySpaceTeleport;
    public static List<String> MerorHighEnergySpaceTeleportAboutDimension;
    public static double MerorWantedChancePerSecond;
    public static boolean AllCreaturesCanBeWantedByMeror;
    //邪笑之夜
    public static int WickedRidiculeNightBossBarColor;
    public static int WickedRidiculeNightBossBarNameColor;
    public static List<String> WickedRidiculeNightAboutDimension;
    public static boolean WickedRidiculeNightAboutAllDimension;
    public static double WickedRidiculeNightChance;
    public static boolean WickedRidiculeNightBossBar;
    public static boolean WickedRidiculeNightScreenOverlay;
    public static double WickedRidiculeNightScreenOverlayLevel;
    public static int WickedRidiculeNightScreenOverlayColorChoose;
    public static boolean WickedRidiculeNightFog;
    public static int WickedRidiculeNightFogColorChoose;
    public static boolean WickedRidiculeNightBlockColor;
    public static int WickedRidiculeNightBlockColorChoose;
    public static boolean WickedRidiculeNightLiquidColor;
    public static int WickedRidiculeNightLiquidColorChoose;
    //泽林亲和
    public static List<String> ZsieinAffinityAboutDimension;
    public static boolean ZsieinAffinityAboutAllDimension;
    public static double ZsieinAffinityMaxLevel;
    public static double ZsieinMildInfectedLevel;
    public static double ZsieinModerateInfectedLevel;
    public static double ZsieinSevereInfectedLevel;
    public static double ZsieinExtremelyInfectedLevel;

    public static boolean SecondRoundWorldSpecialScreen;
    public static boolean RandomSkinMobHasUnderTexture;
    public static double PurpleSandWeatherLightningChance;
    public static boolean PurpleSandEyeProtectionMode;
    public static boolean PurpleSandCustomColor;
    public static int PurpleSandCustomColorUse;
    public static boolean SomeEliteHasBossBarBaseInGameRule;
    public static boolean EliteBossBarOnlyCombat;
    public static List<String> EliteCanHasBossBar;
    public static boolean MerorMonstrositySpecialGlow;
    public static boolean DefectorMustAttack;
    public static boolean SerponWeaknessInNonHotBiomes;
    public static boolean SerponCanWearAllArmor;
    public static boolean WonderlinCanWearAllArmor;
    public static double ResurrectCoffinSedimentGuardWeight;
    public static double ResurrectCoffinSedimentSoulStabilizerWeight;
    public static double ResurrectCoffinHeavyArmoredSedimentGuardWeight;
    public static double ResurrectCoffinSedimentGuardWeightAfterTenResurrections;
    public static double ResurrectCoffinSedimentSoulStabilizerWeightAfterTenResurrections;
    public static double ResurrectCoffinHeavyArmoredSedimentGuardWeightAfterTenResurrections;
    public static boolean RustBeetleFindIronAboutIsItemCraftedWithIron;
    public static double MerorMachineMerorToughness;
    public static double VirtualCaveCockroachMaxSize;
    public static int WonderlandLiCatMaxWild;
    public static boolean VirtualCaveCockroachCute;

    public static boolean SameTypeBossOnlyOne;
    public static boolean BossOnlyOne;
    public static double BossSpawnRestrictionRange;
    public static boolean SameTypeEliteOnlyOne;
    public static boolean EliteOnlyOne;
    public static double EliteSpawnRestrictionRange;
    public static boolean EliteAndBossOnlyOne;


    //探险者
    public static boolean RaidSpawnExplorer;
    public static List<? extends Integer> RaidSpawnExplorerCount;
    //行刑者
    public static boolean RaidSpawnExecutioner;
    public static List<? extends Integer> RaidSpawnExecutionerCount;
    //绘图者
    public static boolean RaidSpawnMapmaker;
    public static List<? extends Integer> RaidSpawnMapmakerCount;
    //背叛者
    public static boolean RaidSpawnDefector;
    public static List<? extends Integer> RaidSpawnDefectorCount;
    //执旗者
    public static boolean RaidSpawnBannerBearer;
    public static List<? extends Integer> RaidSpawnBannerBearerCount;
    //爆破者
    public static boolean RaidSpawnBlaster;
    public static List<? extends Integer> RaidSpawnBlasterCount;
    //吹号者
    public static boolean RaidSpawnTrumpeter;
    public static List<? extends Integer> RaidSpawnTrumpeterCount;
    //传送者
    public static boolean RaidSpawnTeleporter;
    public static List<? extends Integer> RaidSpawnTeleporterCount;
    //旋风者
    public static boolean RaidSpawnCycloner;
    public static List<? extends Integer> RaidSpawnCyclonerCount;
    //掷枪者
    public static boolean RaidSpawnJavelinThrower;
    public static List<? extends Integer> RaidSpawnJavelinThrowerCount;
    //养尸人
    public static boolean RaidSpawnZombieKeeper;
    public static List<? extends Integer> RaidSpawnZombieKeeperCount;
    //苦寒术士
    public static boolean RaidSpawnBitterColdSorcerer;
    public static List<? extends Integer> RaidSpawnBitterColdSorcererCount;
    //吐火者
    public static boolean RaidSpawnFireSpitter;
    public static List<? extends Integer> RaidSpawnFireSpitterCount;
    //野探客
    public static boolean RaidSpawnWildFinder;
    public static List<? extends Integer> RaidSpawnWildFinderCount;
    //潜海者
    public static boolean RaidSpawnSubmariner;
    public static List<? extends Integer> RaidSpawnSubmarinerCount;
    //灯术师
    public static boolean RaidSpawnLampWizard;
    public static List<? extends Integer> RaidSpawnLampWizardCount;
    //奴制监督者
    public static boolean RaidSpawnSlaverySupervisor;
    public static List<? extends Integer> RaidSpawnSlaverySupervisorCount;
    //火力倾泻者
    public static boolean RaidSpawnFirepowerPourer;
    public static List<? extends Integer> RaidSpawnFirepowerPourerCount;
    //巫神汉
    public static boolean RaidSpawnNecromancyWarlock;
    public static List<? extends Integer> RaidSpawnNecromancyWarlockCount;
    //引力师
    public static boolean RaidSpawnGaviler;
    public static List<? extends Integer> RaidSpawnGavilerCount;
    //斧头杀人狂
    public static boolean RaidSpawnAxCrazy;
    public static List<? extends Integer> RaidSpawnAxCrazyCount;
    //涉险者
    public static boolean RaidSpawnAdventurer;
    public static List<? extends Integer> RaidSpawnAdventurerCount;
    //鬼婆
    public static boolean RaidSpawnHag;
    public static List<? extends Integer> RaidSpawnHagCount;
    //女巫学者
    public static boolean RaidSpawnWitchScholar;
    public static List<? extends Integer> RaidSpawnWitchScholarCount;
    //紫沙女巫
    public static boolean RaidSpawnPurpleSandWitch;
    public static List<? extends Integer> RaidSpawnPurpleSandWitchCount;
    //灵奴
    public static boolean RaidSpawnSpirve;
    public static List<? extends Integer> RaidSpawnSpirveCount;
    //大巫婆
    public static boolean RaidSpawnBigWitch;
    public static List<? extends Integer> RaidSpawnBigWitchCount;
    //灾厄旗帜投影
    public static boolean RaidSpawnOminousBannerProjection;
    public static List<? extends Integer> RaidSpawnOminousBannerProjectionCount;
    //紫沙鬼婆
    public static boolean RaidSpawnPurpleSandHag;
    public static List<? extends Integer> RaidSpawnPurpleSandHagCount;

    //Boss
    public static boolean BossBaseAttributeCanUseConfig;
    public static boolean RaidBossCanIncreaseSummonCountBasedOnPlayerCount;
    public static double RaidBossCanIncreaseSummonCountBasedOnPlayerCountFindReach;
    public static int RaidBossCanIncreaseSummonCountBasedOnPlayerCountMaxCount;
    public static boolean RaidBossCanIncreaseSummonCountBasedOnPlayerCountContainAllBoss;
    public static List<String> RaidBossCanIncreaseSummonCountBasedOnPlayerCountContainBoss;
    public static List<String> BossHasPercentageDamage;
    public static List<String> BossHasDamageCap;
    public static List<String> BossHasDamageCooldownTick;
    //沉降领主
    public static int SedimentLordBossBarColor;
    public static int SedimentLordBossBarNameColor;
    public static double SedimentLordMaxHealth;
    public static double SedimentLordMeleeDamage;
    public static double SedimentLordArmor;
    public static double SedimentLordMovementSpeed;
    public static double SedimentLordAttackKnockback;
    public static double SedimentLordKnockbackResistance;
    public static double SedimentLordLeadCooldown;
    public static double SedimentLordDropEquipmentChance;
    public static double SedimentLordAttackPercentage;
    public static double SedimentLordMagicAttackPercentage;
    public static double SedimentLordDamageCap;
    public static double SedimentLordDamageCooldownTick;
    //繁兴使者
    public static int ProsperousEnvoyBossBarColor;
    public static int ProsperousEnvoyBossBarNameColor;
    public static double ProsperousEnvoyMaxHealth;
    public static double ProsperousEnvoyMeleeDamage;
    public static double ProsperousEnvoyArmor;
    public static double ProsperousEnvoyMovementSpeed;
    public static double ProsperousEnvoyAttackKnockback;
    public static double ProsperousEnvoyKnockbackResistance;
    public static double ProsperousEnvoyToughHurtCooldown;
    public static double ProsperousEnvoyBreakToughHurtCooldownMultiple;
    public static double ProsperousEnvoyBreakToughNeedBaseDamage;
    public static double ProsperousEnvoyMeleeAttackCooldown;
    public static double ProsperousEnvoyProsperousTrampleChance;
    public static double ProsperousEnvoyProsperousRushTime;
    public static double ProsperousEnvoyProsperousRushCooldown;
    public static double ProsperousEnvoyBreakBlockCooldown;
    public static double ProsperousEnvoyAttackPercentage;
    public static double ProsperousEnvoyMagicAttackPercentage;
    public static double ProsperousEnvoyDamageCap;
    public static double ProsperousEnvoyDamageCooldownTick;
    //灾厄旗帜投影
    public static int OminousBannerProjectionBossBarColor;
    public static int OminousBannerProjectionBossBarNameColor;
    public static double OminousBannerProjectionMaxHealth;
    public static double OminousBannerProjectionMeleeDamage;
    public static double OminousBannerProjectionArmor;
    public static double OminousBannerProjectionMovementSpeed;
    public static double OminousBannerProjectionAttackKnockback;
    public static double OminousBannerProjectionKnockbackResistance;
    public static double OminousBannerProjectionRoundTimeMin;
    public static double OminousBannerProjectionRoundTimeMax;
    public static int OminousBannerProjectionMaxRound;
    public static double OminousBannerProjectionPointsBase;
    public static double OminousBannerProjectionPointsRoundGrowthMultiple;
    public static boolean OminousBannerProjectionAXCrazyCanAppear;
    public static boolean OminousBannerProjectionBiomeIllagerCanAppearInOtherBiome;
    public static int OminousBannerProjectionEliteCount;
    public static double OminousBannerProjectionAttackPercentage;
    public static double OminousBannerProjectionMagicAttackPercentage;
    public static double OminousBannerProjectionDamageCap;
    public static double OminousBannerProjectionDamageCooldownTick;
    //镇山大王
    public static int TyrantOfTheMountainRealmBossBarColor;
    public static int TyrantOfTheMountainRealmBossBarNameColor;
    public static double TyrantOfTheMountainRealmMaxHealth;
    public static double TyrantOfTheMountainRealmMeleeDamage;
    public static double TyrantOfTheMountainRealmArmor;
    public static double TyrantOfTheMountainRealmMovementSpeed;
    public static double TyrantOfTheMountainRealmAttackKnockback;
    public static double TyrantOfTheMountainRealmKnockbackResistance;
    public static double TyrantOfTheMountainRealmMeleeAttackCooldown;
    public static int TyrantOfTheMountainRealmSpellNormalLevel;
    public static int TyrantOfTheMountainRealmFlagSpearCount;
    public static double TyrantOfTheMountainRealmBreakBlockCooldown;
    public static double TyrantOfTheMountainRealmAttackPercentage;
    public static double TyrantOfTheMountainRealmMagicAttackPercentage;
    public static double TyrantOfTheMountainRealmDamageCap;
    public static double TyrantOfTheMountainRealmDamageCooldownTick;
    //蚀龙兽
    public static int CorrosiverBossBarColor;
    public static int CorrosiverBossBarNameColor;
    public static double CorrosiverMaxHealth;
    public static double CorrosiverMeleeDamage;
    public static double CorrosiverArmor;
    public static double CorrosiverMovementSpeed;
    public static double CorrosiverAttackKnockback;
    public static double CorrosiverKnockbackResistance;
    public static double CorrosiverToughHurtCooldown;
    public static double CorrosiverBreakToughHurtCooldownMultiple;
    public static double CorrosiverBreakToughNeedBaseDamage;
    public static double CorrosiverMeleeAttackCooldown;
    public static double CorrosiverSpellCooldown;
    public static int CorrosiverSpellNormalLevel;
    public static double CorrosiverColdAbsorptionPercentage;
    public static double CorrosiverBreakBlockCooldown;
    public static double CorrosiverAttackPercentage;
    public static double CorrosiverMagicAttackPercentage;
    public static double CorrosiverDamageCap;
    public static double CorrosiverDamageCooldownTick;
    //紫沙鬼婆
    public static int PurpleSandHagBossBarColor;
    public static int PurpleSandHagBossBarNameColor;
    public static double PurpleSandHagMaxHealth;
    public static double PurpleSandHagMeleeDamage;
    public static double PurpleSandHagArmor;
    public static double PurpleSandHagMovementSpeed;
    public static double PurpleSandHagAttackKnockback;
    public static double PurpleSandHagKnockbackResistance;
    public static double PurpleSandHagMainSpellCooldown;
    public static double PurpleSandHagOtherSpellCooldown;
    public static int PurpleSandHagSpellNormalLevel;
    public static double PurpleSandHagAttackPercentage;
    public static double PurpleSandHagMagicAttackPercentage;
    public static double PurpleSandHagDamageCap;
    public static double PurpleSandHagDamageCooldownTick;
    //宝石恶鳞
    public static int GemstoneMalignasaurBossBarColor;
    public static int GemstoneMalignasaurBossBarNameColor;
    public static double GemstoneMalignasaurMaxHealth;
    public static double GemstoneMalignasaurMeleeDamage;
    public static double GemstoneMalignasaurArmor;
    public static double GemstoneMalignasaurArmorGemstoneArmor;
    public static double GemstoneMalignasaurMovementSpeed;
    public static double GemstoneMalignasaurMovementSpeedGemstoneArmor;
    public static double GemstoneMalignasaurAttackKnockback;
    public static double GemstoneMalignasaurKnockbackResistance;
    public static double GemstoneMalignasaurToughHurtCooldown;
    public static double GemstoneMalignasaurBreakToughHurtCooldownMultiple;
    public static double GemstoneMalignasaurBreakToughNeedBaseDamage;
    public static double GemstoneMalignasaurMeleeAttackCooldown;
    public static int GemstoneMalignasaurJumpAttackLevel;
    public static double GemstoneMalignasaurJumpAttackCooldown;
    public static int GemstoneMalignasaurSpellNormalLevel;
    public static double GemstoneMalignasaurBreathCooldown;
    public static double GemstoneMalignasaurControlCooldown;
    public static double GemstoneMalignasaurAngrySpellCooldown;
    public static double GemstoneMalignasaurFakeDamageCap;
    public static double GemstoneMalignasaurBreakFakeDamageCap;
    public static double GemstoneMalignasaurBreakBlockCooldown;
    public static double GemstoneMalignasaurAttackPercentage;
    public static double GemstoneMalignasaurMagicAttackPercentage;
    public static double GemstoneMalignasaurDamageCap;
    public static double GemstoneMalignasaurDamageCooldownTick;
    //明路巨兽
    public static int BrightLandBeastBossBarColor;
    public static int BrightLandBeastBossBarNameColor;
    public static double BrightLandBeastMaxHealth;
    public static double BrightLandBeastMeleeDamage;
    public static double BrightLandBeastArmor;
    public static double BrightLandBeastMovementSpeed;
    public static double BrightLandBeastAttackKnockback;
    public static double BrightLandBeastKnockbackResistance;
    public static double BrightLandBeastToughHurtCooldown;
    public static double BrightLandBeastBreakToughHurtCooldownMultiple;
    public static double BrightLandBeastBreakToughNeedBaseDamage;
    public static double BrightLandBeastMeleeAttackCooldown;
    public static double BrightLandBeastQuakeCooldown;
    public static double BrightLandBeastQuakeBaseDamage;
    public static double BrightLandBeastQuakeHealthMultipleDamage;
    public static double BrightLandBeastBreakBlockCooldown;
    public static double BrightLandBeastAttackPercentage;
    public static double BrightLandBeastMagicAttackPercentage;
    public static double BrightLandBeastDamageCap;
    public static double BrightLandBeastDamageCooldownTick;
    //蛇龙执战者
    public static int SerponCombatExecuterBossBarColor;
    public static int SerponCombatExecuterBossBarNameColor;
    public static double SerponCombatExecuterMaxHealth;
    public static double SerponCombatExecuterMeleeDamage;
    public static double SerponCombatExecuterMeleeDamageFury;
    public static double SerponCombatExecuterArmor;
    public static double SerponCombatExecuterArmorFury;
    public static double SerponCombatExecuterMovementSpeed;
    public static double SerponCombatExecuterFlyingSpeed;
    public static double SerponCombatExecuterAttackKnockback;
    public static double SerponCombatExecuterKnockbackResistance;
    public static double SerponCombatExecuterKnockbackResistanceFury;
    public static double SerponCombatExecuterDropEquipmentChance;
    public static double SerponCombatExecuterRoundTimeMin;
    public static double SerponCombatExecuterRoundTimeMax;
    public static double SerponCombatExecuterFlameSlashChance;
    public static double SerponCombatExecuterWhirlwindSlashChance;
    public static double SerponCombatExecuterFireAbsorptionPercentage;
    public static int SerponCombatExecuterSpellNormalLevel;
    public static double SerponCombatExecuterSpellCooldown;
    public static double SerponCombatExecuterAttackPercentage;
    public static double SerponCombatExecuterMagicAttackPercentage;
    public static double SerponCombatExecuterDamageCap;
    public static double SerponCombatExecuterDamageCooldownTick;
    //盘罗仙蔓
    public static int CelestialCoilvineBossBarColor;
    public static int CelestialCoilvineBossBarNameColor;
    public static double CelestialCoilvineMaxHealth;
    public static double CelestialCoilvineMeleeDamage;
    public static double CelestialCoilvineArmor;
    public static double CelestialCoilvineMovementSpeed;
    public static double CelestialCoilvineAttackKnockback;
    public static double CelestialCoilvineKnockbackResistance;
    public static int CelestialCoilvineSpellNormalLevel;
    public static double CelestialCoilvineAttackPercentage;
    public static double CelestialCoilvineMagicAttackPercentage;
    public static double CelestialCoilvineDamageCap;
    public static double CelestialCoilvineDamageCooldownTick;
    //二轮傀儡
    public static int SecondRounderGolemBossBarColor;
    public static int SecondRounderGolemBossBarNameColor;
    public static double SecondRounderGolemMaxHealth;
    public static double SecondRounderGolemMeleeDamage;
    public static double SecondRounderGolemMeleeDamageUseSword;
    public static double SecondRounderGolemArmor;
    public static double SecondRounderGolemMovementSpeed;
    public static double SecondRounderGolemMovementSpeedUseSword;
    public static double SecondRounderGolemAttackKnockback;
    public static double SecondRounderGolemKnockbackResistance;
    public static double SecondRounderGolemArmorUseSword;
    public static int SecondRounderGolemSpellNormalLevel;
    public static double SecondRounderGolemBreathCooldown;
    public static double SecondRounderGolemIntenseBombingCooldown;
    public static double SecondRounderGolemFireAbsorptionPercentage;
    public static double SecondRounderGolemNotAttackedStartHealCooldown;
    public static double SecondRounderGolemNormalMerorToughnessNonMagical;
    public static double SecondRounderGolemNormalMerorToughnessMagical;
    public static double SecondRounderGolemPoisonFireCloakTime;
    public static int SecondRounderGolemPoisonFireCloakFireTime;
    public static int SecondRounderGolemPoisonFireCloakPotionLevel;
    public static double SecondRounderGolemSagesCooldown;
    public static double SecondRounderGolemBreakBlockCooldown;
    public static double SecondRounderGolemAttackPercentage;
    public static double SecondRounderGolemSwordAttackPercentage;
    public static double SecondRounderGolemMagicAttackPercentage;
    public static double SecondRounderGolemDamageCap;
    public static double SecondRounderGolemDamageCooldownTick;

    static {
        final Pair<CommonConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON = pair.getLeft();
        COMMON_SPEC = pair.getRight();
        final Pair<ClientConfig, ForgeConfigSpec> pair1 = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        CLIENT = pair1.getLeft();
        CLIENT_SPEC = pair1.getRight();
    }

    public static void loadConfig(ForgeConfigSpec config, String path) {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave()
                .writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }

    public static void bakeCommonConfig() {
        SpecialDay = COMMON.SpecialDay.get();

        CarvedVillagerCustomNameWide = COMMON.CarvedVillagerCustomNameWide.get();
        CarvedVillagerCustomNameSlim = COMMON.CarvedVillagerCustomNameSlim.get();
        SpawnEggCarvedVillagerHasArmor = COMMON.SpawnEggCarvedVillagerHasArmor.get();
        CarvedObligation = COMMON.CarvedObligation.get();
        CarvedActiveAttackCreeper = COMMON.CarvedActiveAttackCreeper.get();
        CarvedGonnaActiveAttackList = COMMON.CarvedGonnaActiveAttackList.get();
        CarvedNeverGonnaActiveAttack = COMMON.CarvedNeverGonnaActiveAttackList.get();
        CarvedCanNotAttackList = COMMON.CarvedCanNotAttackList.get();
        CarvedHoundRetire = COMMON.CarvedHoundRetire.get();
        CarvedVillagerHelmetHeightAsVillager = COMMON.CarvedVillagerHelmetHeightAsVillager.get();


        NewProfessionVillagerGiveCustomGift = COMMON.NewProfessionVillagerGiveCustomGift.get();

        //莫厄通缉
        MerorHighEnergySpaceTeleport = COMMON.MerorHighEnergySpaceTeleport.get();
        MerorHighEnergySpaceTeleportAboutDimension = COMMON.MerorHighEnergySpaceTeleportAboutDimension.get();
        MerorWantedChancePerSecond = COMMON.MerorWantedChancePerSecond.get();
        AllCreaturesCanBeWantedByMeror = COMMON.AllCreaturesCanBeWantedByMeror.get();

        //邪笑之夜
        WickedRidiculeNightBossBarColor = COMMON.WickedRidiculeNightBossBarColor.get();
        WickedRidiculeNightBossBarNameColor = COMMON.WickedRidiculeNightBossBarNameColor.get();
        WickedRidiculeNightAboutDimension = COMMON.WickedRidiculeNightAboutDimension.get();
        WickedRidiculeNightAboutAllDimension = COMMON.WickedRidiculeNightAboutAllDimension.get();
        WickedRidiculeNightChance = COMMON.WickedRidiculeNightChance.get();
        WickedRidiculeNightBossBar = COMMON.WickedRidiculeNightBossBar.get();
        WickedRidiculeNightScreenOverlay = COMMON.WickedRidiculeNightScreenOverlay.get();
        WickedRidiculeNightScreenOverlayLevel = COMMON.WickedRidiculeNightScreenOverlayLevel.get();
        WickedRidiculeNightScreenOverlayColorChoose = COMMON.WickedRidiculeNightScreenOverlayColorChoose.get();
        WickedRidiculeNightFog = COMMON.WickedRidiculeNightFog.get();
        WickedRidiculeNightFogColorChoose = COMMON.WickedRidiculeNightFogColorChoose.get();
        WickedRidiculeNightBlockColor = COMMON.WickedRidiculeNightBlockColor.get();
        WickedRidiculeNightBlockColorChoose = COMMON.WickedRidiculeNightBlockColorChoose.get();
        WickedRidiculeNightLiquidColor = COMMON.WickedRidiculeNightLiquidColor.get();
        WickedRidiculeNightLiquidColorChoose = COMMON.WickedRidiculeNightLiquidColorChoose.get();
        //泽林亲和
        ZsieinAffinityAboutDimension = COMMON.ZsieinAffinityAboutDimension.get();
        ZsieinAffinityAboutAllDimension = COMMON.ZsieinAffinityAboutAllDimension.get();
        ZsieinAffinityMaxLevel = COMMON.ZsieinAffinityMaxLevel.get();
        ZsieinMildInfectedLevel = COMMON.ZsieinMildInfectedLevel.get();
        ZsieinModerateInfectedLevel = COMMON.ZsieinModerateInfectedLevel.get();
        ZsieinSevereInfectedLevel = COMMON.ZsieinSevereInfectedLevel.get();
        ZsieinExtremelyInfectedLevel = COMMON.ZsieinExtremelyInfectedLevel.get();

        SecondRoundWorldSpecialScreen = COMMON.SecondRoundWorldSpecialScreen.get();
        RandomSkinMobHasUnderTexture = COMMON.RandomSkinMobHasUnderTexture.get();
        PurpleSandWeatherLightningChance = COMMON.PurpleSandWeatherLightningChance.get();
        PurpleSandEyeProtectionMode = COMMON.PurpleSandEyeProtectionMode.get();
        PurpleSandCustomColor = COMMON.PurpleSandCustomColor.get();
        PurpleSandCustomColorUse = COMMON.PurpleSandCustomColorUse.get();
        SomeEliteHasBossBarBaseInGameRule = COMMON.SomeEliteHasBossBarBaseInGameRule.get();
        EliteBossBarOnlyCombat = COMMON.EliteBossBarOnlyCombat.get();
        EliteCanHasBossBar = COMMON.EliteCanHasBossBar.get();
        MerorMonstrositySpecialGlow = COMMON.MerorMonstrositySpecialGlow.get();
        DefectorMustAttack = COMMON.DefectorMustAttack.get();
        SerponWeaknessInNonHotBiomes = COMMON.SerponWeaknessInNonHotBiomes.get();
        SerponCanWearAllArmor = COMMON.SerponCanWearAllArmor.get();
        WonderlinCanWearAllArmor = COMMON.WonderlinCanWearAllArmor.get();
        ResurrectCoffinSedimentGuardWeight = COMMON.ResurrectCoffinSedimentGuardWeight.get();
        ResurrectCoffinSedimentSoulStabilizerWeight = COMMON.ResurrectCoffinSedimentSoulStabilizerWeight.get();
        ResurrectCoffinHeavyArmoredSedimentGuardWeight = COMMON.ResurrectCoffinHeavyArmoredSedimentGuardWeight.get();
        ResurrectCoffinSedimentGuardWeightAfterTenResurrections = COMMON.ResurrectCoffinSedimentGuardWeightAfterTenResurrections.get();
        ResurrectCoffinSedimentSoulStabilizerWeightAfterTenResurrections = COMMON.ResurrectCoffinSedimentSoulStabilizerWeightAfterTenResurrections.get();
        ResurrectCoffinHeavyArmoredSedimentGuardWeightAfterTenResurrections = COMMON.ResurrectCoffinHeavyArmoredSedimentGuardWeightAfterTenResurrections.get();
        RustBeetleFindIronAboutIsItemCraftedWithIron = COMMON.RustBeetleFindIronAboutIsItemCraftedWithIron.get();
        MerorMachineMerorToughness = COMMON.MerorMachineMerorToughness.get();
        VirtualCaveCockroachMaxSize = COMMON.VirtualCaveCockroachMaxSize.get();
        WonderlandLiCatMaxWild = COMMON.WonderlandLiCatMaxWild.get();
        VirtualCaveCockroachCute = COMMON.VirtualCaveCockroachCute.get();

        SameTypeBossOnlyOne = COMMON.SameTypeBossOnlyOne.get();
        BossOnlyOne = COMMON.BossOnlyOne.get();
        BossSpawnRestrictionRange = COMMON.BossSpawnRestrictionRange.get();
        SameTypeEliteOnlyOne = COMMON.SameTypeEliteOnlyOne.get();
        EliteOnlyOne = COMMON.EliteOnlyOne.get();
        EliteSpawnRestrictionRange = COMMON.EliteSpawnRestrictionRange.get();
        EliteAndBossOnlyOne = COMMON.EliteAndBossOnlyOne.get();


        //探险者
        RaidSpawnExplorer = COMMON.RaidSpawnExplorer.get();
        RaidSpawnExplorerCount = COMMON.RaidSpawnExplorerCount.get();
        //行刑者
        RaidSpawnExecutioner = COMMON.RaidSpawnExecutioner.get();
        RaidSpawnExecutionerCount = COMMON.RaidSpawnExecutionerCount.get();
        //绘图者
        RaidSpawnMapmaker = COMMON.RaidSpawnMapmaker.get();
        RaidSpawnMapmakerCount = COMMON.RaidSpawnMapmakerCount.get();
        //背叛者
        RaidSpawnDefector = COMMON.RaidSpawnDefector.get();
        RaidSpawnDefectorCount = COMMON.RaidSpawnDefectorCount.get();
        //执旗者
        RaidSpawnBannerBearer = COMMON.RaidSpawnBannerBearer.get();
        RaidSpawnBannerBearerCount = COMMON.RaidSpawnBannerBearerCount.get();
        //爆破者
        RaidSpawnBlaster = COMMON.RaidSpawnBlaster.get();
        RaidSpawnBlasterCount = COMMON.RaidSpawnBlasterCount.get();
        //吹号者
        RaidSpawnTrumpeter = COMMON.RaidSpawnTrumpeter.get();
        RaidSpawnTrumpeterCount = COMMON.RaidSpawnTrumpeterCount.get();
        //传送者
        RaidSpawnTeleporter = COMMON.RaidSpawnTeleporter.get();
        RaidSpawnTeleporterCount = COMMON.RaidSpawnTeleporterCount.get();
        //旋风者
        RaidSpawnCycloner = COMMON.RaidSpawnCycloner.get();
        RaidSpawnCyclonerCount = COMMON.RaidSpawnCyclonerCount.get();
        //掷枪者
        RaidSpawnJavelinThrower = COMMON.RaidSpawnJavelinThrower.get();
        RaidSpawnJavelinThrowerCount = COMMON.RaidSpawnJavelinThrowerCount.get();
        //养尸人
        RaidSpawnZombieKeeper = COMMON.RaidSpawnZombieKeeper.get();
        RaidSpawnZombieKeeperCount = COMMON.RaidSpawnZombieKeeperCount.get();
        //苦寒术士
        RaidSpawnBitterColdSorcerer = COMMON.RaidSpawnBitterColdSorcerer.get();
        RaidSpawnBitterColdSorcererCount = COMMON.RaidSpawnBitterColdSorcererCount.get();
        //吐火者
        RaidSpawnFireSpitter = COMMON.RaidSpawnFireSpitter.get();
        RaidSpawnFireSpitterCount = COMMON.RaidSpawnFireSpitterCount.get();
        //野探客
        RaidSpawnWildFinder = COMMON.RaidSpawnWildFinder.get();
        RaidSpawnWildFinderCount = COMMON.RaidSpawnWildFinderCount.get();
        //潜海者
        RaidSpawnSubmariner = COMMON.RaidSpawnSubmariner.get();
        RaidSpawnSubmarinerCount = COMMON.RaidSpawnSubmarinerCount.get();
        //灯术师
        RaidSpawnLampWizard = COMMON.RaidSpawnLampWizard.get();
        RaidSpawnLampWizardCount = COMMON.RaidSpawnLampWizardCount.get();
        //奴制监督者
        RaidSpawnSlaverySupervisor = COMMON.RaidSpawnSlaverySupervisor.get();
        RaidSpawnSlaverySupervisorCount = COMMON.RaidSpawnSlaverySupervisorCount.get();
        //火力倾泻者
        RaidSpawnFirepowerPourer = COMMON.RaidSpawnFirepowerPourer.get();
        RaidSpawnFirepowerPourerCount = COMMON.RaidSpawnFirepowerPourerCount.get();
        //巫神汉
        RaidSpawnNecromancyWarlock = COMMON.RaidSpawnNecromancyWarlock.get();
        RaidSpawnNecromancyWarlockCount = COMMON.RaidSpawnNecromancyWarlockCount.get();
        //引力师
        RaidSpawnGaviler = COMMON.RaidSpawnGaviler.get();
        RaidSpawnGavilerCount = COMMON.RaidSpawnGavilerCount.get();
        //斧头杀人狂
        RaidSpawnAxCrazy = COMMON.RaidSpawnAxCrazy.get();
        RaidSpawnAxCrazyCount = COMMON.RaidSpawnAxCrazyCount.get();
        //涉险者
        RaidSpawnAdventurer = COMMON.RaidSpawnAdventurer.get();
        RaidSpawnAdventurerCount = COMMON.RaidSpawnAdventurerCount.get();
        //鬼婆
        RaidSpawnHag = COMMON.RaidSpawnHag.get();
        RaidSpawnHagCount = COMMON.RaidSpawnHagCount.get();
        //女巫学者
        RaidSpawnWitchScholar = COMMON.RaidSpawnWitchScholar.get();
        RaidSpawnWitchScholarCount = COMMON.RaidSpawnWitchScholarCount.get();
        //紫沙女巫
        RaidSpawnPurpleSandWitch = COMMON.RaidSpawnPurpleSandWitch.get();
        RaidSpawnPurpleSandWitchCount = COMMON.RaidSpawnPurpleSandWitchCount.get();
        //灵奴
        RaidSpawnSpirve = COMMON.RaidSpawnSpirve.get();
        RaidSpawnSpirveCount = COMMON.RaidSpawnSpirveCount.get();
        //大巫婆
        RaidSpawnBigWitch = COMMON.RaidSpawnBigWitch.get();
        RaidSpawnBigWitchCount = COMMON.RaidSpawnBigWitchCount.get();
        //灾厄旗帜投影
        RaidSpawnOminousBannerProjection = COMMON.RaidSpawnOminousBannerProjection.get();
        RaidSpawnOminousBannerProjectionCount = COMMON.RaidSpawnOminousBannerProjectionCount.get();
        //紫沙鬼婆
        RaidSpawnPurpleSandHag = COMMON.RaidSpawnPurpleSandHag.get();
        RaidSpawnPurpleSandHagCount = COMMON.RaidSpawnPurpleSandHagCount.get();

        BossBaseAttributeCanUseConfig = COMMON.BossBaseAttributeCanUseConfig.get();
        RaidBossCanIncreaseSummonCountBasedOnPlayerCount = COMMON.RaidBossCanIncreaseSummonCountBasedOnPlayerCount.get();
        RaidBossCanIncreaseSummonCountBasedOnPlayerCountFindReach = COMMON.RaidBossCanIncreaseSummonCountBasedOnPlayerCountFindReach.get();
        RaidBossCanIncreaseSummonCountBasedOnPlayerCountMaxCount = COMMON.RaidBossCanIncreaseSummonCountBasedOnPlayerCountMaxCount.get();
        RaidBossCanIncreaseSummonCountBasedOnPlayerCountContainAllBoss = COMMON.RaidBossCanIncreaseSummonCountBasedOnPlayerCountContainAllBoss.get();
        RaidBossCanIncreaseSummonCountBasedOnPlayerCountContainBoss = COMMON.RaidBossCanIncreaseSummonCountBasedOnPlayerCountContainBoss.get();
        BossHasPercentageDamage = COMMON.BossHasPercentageDamage.get();
        BossHasDamageCap = COMMON.BossHasDamageCap.get();
        BossHasDamageCooldownTick = COMMON.BossHasDamageCooldownTick.get();
        //沉降领主
        SedimentLordBossBarColor = COMMON.SedimentLordBossBarColor.get();
        SedimentLordBossBarNameColor = COMMON.SedimentLordBossBarNameColor.get();
        SedimentLordMaxHealth = COMMON.SedimentLordMaxHealth.get();
        SedimentLordMeleeDamage = COMMON.SedimentLordMeleeDamage.get();
        SedimentLordArmor = COMMON.SedimentLordArmor.get();
        SedimentLordMovementSpeed = COMMON.SedimentLordMovementSpeed.get();
        SedimentLordAttackKnockback = COMMON.SedimentLordAttackKnockback.get();
        SedimentLordKnockbackResistance = COMMON.SedimentLordKnockbackResistance.get();
        SedimentLordLeadCooldown = COMMON.SedimentLordLeadCooldown.get();
        SedimentLordDropEquipmentChance = COMMON.SedimentLordDropEquipmentChance.get();
        SedimentLordAttackPercentage = COMMON.SedimentLordAttackPercentage.get();
        SedimentLordMagicAttackPercentage = COMMON.SedimentLordMagicAttackPercentage.get();
        SedimentLordDamageCap = COMMON.SedimentLordDamageCap.get();
        SedimentLordDamageCooldownTick = COMMON.SedimentLordDamageCooldownTick.get();
        //繁兴使者
        ProsperousEnvoyBossBarColor = COMMON.ProsperousEnvoyBossBarColor.get();
        ProsperousEnvoyBossBarNameColor = COMMON.ProsperousEnvoyBossBarNameColor.get();
        ProsperousEnvoyMaxHealth = COMMON.ProsperousEnvoyMaxHealth.get();
        ProsperousEnvoyMeleeDamage = COMMON.ProsperousEnvoyMeleeDamage.get();
        ProsperousEnvoyArmor = COMMON.ProsperousEnvoyArmor.get();
        ProsperousEnvoyMovementSpeed = COMMON.ProsperousEnvoyMovementSpeed.get();
        ProsperousEnvoyAttackKnockback = COMMON.ProsperousEnvoyAttackKnockback.get();
        ProsperousEnvoyKnockbackResistance = COMMON.ProsperousEnvoyKnockbackResistance.get();
        ProsperousEnvoyToughHurtCooldown = COMMON.ProsperousEnvoyToughHurtCooldown.get();
        ProsperousEnvoyBreakToughHurtCooldownMultiple = COMMON.ProsperousEnvoyBreakToughHurtCooldownMultiple.get();
        ProsperousEnvoyBreakToughNeedBaseDamage = COMMON.ProsperousEnvoyBreakToughNeedBaseDamage.get();
        ProsperousEnvoyMeleeAttackCooldown = COMMON.ProsperousEnvoyMeleeAttackCooldown.get();
        ProsperousEnvoyProsperousTrampleChance = COMMON.ProsperousEnvoyProsperousTrampleChance.get();
        ProsperousEnvoyProsperousRushTime = COMMON.ProsperousEnvoyProsperousRushTime.get();
        ProsperousEnvoyProsperousRushCooldown = COMMON.ProsperousEnvoyProsperousRushCooldown.get();
        ProsperousEnvoyBreakBlockCooldown = COMMON.ProsperousEnvoyBreakBlockCooldown.get();
        ProsperousEnvoyAttackPercentage = COMMON.ProsperousEnvoyAttackPercentage.get();
        ProsperousEnvoyMagicAttackPercentage = COMMON.ProsperousEnvoyMagicAttackPercentage.get();
        ProsperousEnvoyDamageCap = COMMON.ProsperousEnvoyDamageCap.get();
        ProsperousEnvoyDamageCooldownTick = COMMON.ProsperousEnvoyDamageCooldownTick.get();
        //灾厄旗帜投影
        OminousBannerProjectionBossBarColor = COMMON.OminousBannerProjectionBossBarColor.get();
        OminousBannerProjectionBossBarNameColor = COMMON.OminousBannerProjectionBossBarNameColor.get();
        OminousBannerProjectionMaxHealth = COMMON.OminousBannerProjectionMaxHealth.get();
        OminousBannerProjectionMeleeDamage = COMMON.OminousBannerProjectionMeleeDamage.get();
        OminousBannerProjectionArmor = COMMON.OminousBannerProjectionArmor.get();
        OminousBannerProjectionMovementSpeed = COMMON.OminousBannerProjectionMovementSpeed.get();
        OminousBannerProjectionAttackKnockback = COMMON.OminousBannerProjectionAttackKnockback.get();
        OminousBannerProjectionKnockbackResistance = COMMON.OminousBannerProjectionKnockbackResistance.get();
        OminousBannerProjectionRoundTimeMin = COMMON.OminousBannerProjectionRoundTimeMin.get();
        OminousBannerProjectionRoundTimeMax = COMMON.OminousBannerProjectionRoundTimeMax.get();
        OminousBannerProjectionMaxRound = COMMON.OminousBannerProjectionMaxRound.get();
        OminousBannerProjectionPointsBase = COMMON.OminousBannerProjectionPointsBase.get();
        OminousBannerProjectionPointsRoundGrowthMultiple = COMMON.OminousBannerProjectionPointsRoundGrowthMultiple.get();
        OminousBannerProjectionAXCrazyCanAppear = COMMON.OminousBannerProjectionAXCrazyCanAppear.get();
        OminousBannerProjectionBiomeIllagerCanAppearInOtherBiome = COMMON.OminousBannerProjectionBiomeIllagerCanAppearInOtherBiome.get();
        OminousBannerProjectionEliteCount = COMMON.OminousBannerProjectionEliteCount.get();
        OminousBannerProjectionAttackPercentage = COMMON.OminousBannerProjectionAttackPercentage.get();
        OminousBannerProjectionMagicAttackPercentage = COMMON.OminousBannerProjectionMagicAttackPercentage.get();
        OminousBannerProjectionDamageCap = COMMON.OminousBannerProjectionDamageCap.get();
        OminousBannerProjectionDamageCooldownTick = COMMON.OminousBannerProjectionDamageCooldownTick.get();
        //镇山大王
        TyrantOfTheMountainRealmBossBarColor = COMMON.TyrantOfTheMountainRealmBossBarColor.get();
        TyrantOfTheMountainRealmBossBarNameColor = COMMON.TyrantOfTheMountainRealmBossBarNameColor.get();
        TyrantOfTheMountainRealmMaxHealth = COMMON.TyrantOfTheMountainRealmMaxHealth.get();
        TyrantOfTheMountainRealmMeleeDamage = COMMON.TyrantOfTheMountainRealmMeleeDamage.get();
        TyrantOfTheMountainRealmArmor = COMMON.TyrantOfTheMountainRealmArmor.get();
        TyrantOfTheMountainRealmMovementSpeed = COMMON.TyrantOfTheMountainRealmMovementSpeed.get();
        TyrantOfTheMountainRealmAttackKnockback = COMMON.TyrantOfTheMountainRealmAttackKnockback.get();
        TyrantOfTheMountainRealmKnockbackResistance = COMMON.TyrantOfTheMountainRealmKnockbackResistance.get();
        TyrantOfTheMountainRealmMeleeAttackCooldown = COMMON.TyrantOfTheMountainRealmMeleeAttackCooldown.get();
        TyrantOfTheMountainRealmSpellNormalLevel = COMMON.TyrantOfTheMountainRealmSpellNormalLevel.get();
        TyrantOfTheMountainRealmFlagSpearCount = COMMON.TyrantOfTheMountainRealmFlagSpearCount.get();
        TyrantOfTheMountainRealmBreakBlockCooldown = COMMON.TyrantOfTheMountainRealmBreakBlockCooldown.get();
        TyrantOfTheMountainRealmAttackPercentage = COMMON.TyrantOfTheMountainRealmAttackPercentage.get();
        TyrantOfTheMountainRealmMagicAttackPercentage = COMMON.TyrantOfTheMountainRealmMagicAttackPercentage.get();
        TyrantOfTheMountainRealmDamageCap = COMMON.TyrantOfTheMountainRealmDamageCap.get();
        TyrantOfTheMountainRealmDamageCooldownTick = COMMON.TyrantOfTheMountainRealmDamageCooldownTick.get();
        //蚀龙兽
        CorrosiverBossBarColor = COMMON.CorrosiverBossBarColor.get();
        CorrosiverBossBarNameColor = COMMON.CorrosiverBossBarNameColor.get();
        CorrosiverMaxHealth = COMMON.CorrosiverMaxHealth.get();
        CorrosiverMeleeDamage = COMMON.CorrosiverMeleeDamage.get();
        CorrosiverArmor = COMMON.CorrosiverArmor.get();
        CorrosiverMovementSpeed = COMMON.CorrosiverMovementSpeed.get();
        CorrosiverAttackKnockback = COMMON.CorrosiverAttackKnockback.get();
        CorrosiverKnockbackResistance = COMMON.CorrosiverKnockbackResistance.get();
        CorrosiverToughHurtCooldown = COMMON.CorrosiverToughHurtCooldown.get();
        CorrosiverBreakToughHurtCooldownMultiple = COMMON.CorrosiverBreakToughHurtCooldownMultiple.get();
        CorrosiverBreakToughNeedBaseDamage = COMMON.CorrosiverBreakToughNeedBaseDamage.get();
        CorrosiverMeleeAttackCooldown = COMMON.CorrosiverMeleeAttackCooldown.get();
        CorrosiverSpellCooldown = COMMON.CorrosiverSpellCooldown.get();
        CorrosiverSpellNormalLevel = COMMON.CorrosiverSpellNormalLevel.get();
        CorrosiverColdAbsorptionPercentage = COMMON.CorrosiverColdAbsorptionPercentage.get();
        CorrosiverBreakBlockCooldown = COMMON.CorrosiverBreakBlockCooldown.get();
        CorrosiverAttackPercentage = COMMON.CorrosiverAttackPercentage.get();
        CorrosiverMagicAttackPercentage = COMMON.CorrosiverMagicAttackPercentage.get();
        CorrosiverDamageCap = COMMON.CorrosiverDamageCap.get();
        CorrosiverDamageCooldownTick = COMMON.CorrosiverDamageCooldownTick.get();
        //紫沙鬼婆
        PurpleSandHagBossBarColor = COMMON.PurpleSandHagBossBarColor.get();
        PurpleSandHagBossBarNameColor = COMMON.PurpleSandHagBossBarNameColor.get();
        PurpleSandHagMaxHealth = COMMON.PurpleSandHagMaxHealth.get();
        PurpleSandHagMeleeDamage = COMMON.PurpleSandHagMeleeDamage.get();
        PurpleSandHagArmor = COMMON.PurpleSandHagArmor.get();
        PurpleSandHagMovementSpeed = COMMON.PurpleSandHagMovementSpeed.get();
        PurpleSandHagAttackKnockback = COMMON.PurpleSandHagAttackKnockback.get();
        PurpleSandHagKnockbackResistance = COMMON.PurpleSandHagKnockbackResistance.get();
        PurpleSandHagMainSpellCooldown = COMMON.PurpleSandHagMainSpellCooldown.get();
        PurpleSandHagOtherSpellCooldown = COMMON.PurpleSandHagOtherSpellCooldown.get();
        PurpleSandHagSpellNormalLevel = COMMON.PurpleSandHagSpellNormalLevel.get();
        PurpleSandHagAttackPercentage = COMMON.PurpleSandHagAttackPercentage.get();
        PurpleSandHagMagicAttackPercentage = COMMON.PurpleSandHagMagicAttackPercentage.get();
        PurpleSandHagDamageCap = COMMON.PurpleSandHagDamageCap.get();
        PurpleSandHagDamageCooldownTick = COMMON.PurpleSandHagDamageCooldownTick.get();
        //宝石恶鳞
        GemstoneMalignasaurBossBarColor = COMMON.GemstoneMalignasaurBossBarColor.get();
        GemstoneMalignasaurBossBarNameColor = COMMON.GemstoneMalignasaurBossBarNameColor.get();
        GemstoneMalignasaurMaxHealth = COMMON.GemstoneMalignasaurMaxHealth.get();
        GemstoneMalignasaurMeleeDamage = COMMON.GemstoneMalignasaurMeleeDamage.get();
        GemstoneMalignasaurArmor = COMMON.GemstoneMalignasaurArmor.get();
        GemstoneMalignasaurArmorGemstoneArmor = COMMON.GemstoneMalignasaurArmorGemstoneArmor.get();
        GemstoneMalignasaurMovementSpeed = COMMON.GemstoneMalignasaurMovementSpeed.get();
        GemstoneMalignasaurMovementSpeedGemstoneArmor = COMMON.GemstoneMalignasaurMovementSpeedGemstoneArmor.get();
        GemstoneMalignasaurAttackKnockback = COMMON.GemstoneMalignasaurAttackKnockback.get();
        GemstoneMalignasaurKnockbackResistance = COMMON.GemstoneMalignasaurKnockbackResistance.get();
        GemstoneMalignasaurToughHurtCooldown = COMMON.GemstoneMalignasaurToughHurtCooldown.get();
        GemstoneMalignasaurBreakToughHurtCooldownMultiple = COMMON.GemstoneMalignasaurBreakToughHurtCooldownMultiple.get();
        GemstoneMalignasaurBreakToughNeedBaseDamage = COMMON.GemstoneMalignasaurBreakToughNeedBaseDamage.get();
        GemstoneMalignasaurMeleeAttackCooldown = COMMON.GemstoneMalignasaurMeleeAttackCooldown.get();
        GemstoneMalignasaurJumpAttackLevel = COMMON.GemstoneMalignasaurJumpAttackLevel.get();
        GemstoneMalignasaurJumpAttackCooldown = COMMON.GemstoneMalignasaurJumpAttackCooldown.get();
        GemstoneMalignasaurSpellNormalLevel = COMMON.GemstoneMalignasaurSpellNormalLevel.get();
        GemstoneMalignasaurBreathCooldown = COMMON.GemstoneMalignasaurBreathCooldown.get();
        GemstoneMalignasaurControlCooldown = COMMON.GemstoneMalignasaurControlCooldown.get();
        GemstoneMalignasaurAngrySpellCooldown = COMMON.GemstoneMalignasaurAngrySpellCooldown.get();
        GemstoneMalignasaurFakeDamageCap = COMMON.GemstoneMalignasaurFakeDamageCap.get();
        GemstoneMalignasaurBreakFakeDamageCap = COMMON.GemstoneMalignasaurBreakFakeDamageCap.get();
        GemstoneMalignasaurBreakBlockCooldown = COMMON.GemstoneMalignasaurBreakBlockCooldown.get();
        GemstoneMalignasaurAttackPercentage = COMMON.GemstoneMalignasaurAttackPercentage.get();
        GemstoneMalignasaurMagicAttackPercentage = COMMON.GemstoneMalignasaurMagicAttackPercentage.get();
        GemstoneMalignasaurDamageCap = COMMON.GemstoneMalignasaurDamageCap.get();
        GemstoneMalignasaurDamageCooldownTick = COMMON.GemstoneMalignasaurDamageCooldownTick.get();
        //明路巨兽
        BrightLandBeastBossBarColor = COMMON.BrightLandBeastBossBarColor.get();
        BrightLandBeastBossBarNameColor = COMMON.BrightLandBeastBossBarNameColor.get();
        BrightLandBeastMaxHealth = COMMON.BrightLandBeastMaxHealth.get();
        BrightLandBeastMeleeDamage = COMMON.BrightLandBeastMeleeDamage.get();
        BrightLandBeastArmor = COMMON.BrightLandBeastArmor.get();
        BrightLandBeastMovementSpeed = COMMON.BrightLandBeastMovementSpeed.get();
        BrightLandBeastAttackKnockback = COMMON.BrightLandBeastAttackKnockback.get();
        BrightLandBeastKnockbackResistance = COMMON.BrightLandBeastKnockbackResistance.get();
        BrightLandBeastToughHurtCooldown = COMMON.BrightLandBeastToughHurtCooldown.get();
        BrightLandBeastBreakToughHurtCooldownMultiple = COMMON.BrightLandBeastBreakToughHurtCooldownMultiple.get();
        BrightLandBeastBreakToughNeedBaseDamage = COMMON.BrightLandBeastBreakToughNeedBaseDamage.get();
        BrightLandBeastMeleeAttackCooldown = COMMON.BrightLandBeastMeleeAttackCooldown.get();
        BrightLandBeastQuakeCooldown = COMMON.BrightLandBeastQuakeCooldown.get();
        BrightLandBeastQuakeBaseDamage = COMMON.BrightLandBeastQuakeBaseDamage.get();
        BrightLandBeastQuakeHealthMultipleDamage = COMMON.BrightLandBeastQuakeHealthMultipleDamage.get();
        BrightLandBeastBreakBlockCooldown = COMMON.BrightLandBeastBreakBlockCooldown.get();
        BrightLandBeastAttackPercentage = COMMON.BrightLandBeastAttackPercentage.get();
        BrightLandBeastMagicAttackPercentage = COMMON.BrightLandBeastMagicAttackPercentage.get();
        BrightLandBeastDamageCap = COMMON.BrightLandBeastDamageCap.get();
        BrightLandBeastDamageCooldownTick = COMMON.BrightLandBeastDamageCooldownTick.get();
        //蛇龙执战者
        SerponCombatExecuterBossBarColor = COMMON.SerponCombatExecuterBossBarColor.get();
        SerponCombatExecuterBossBarNameColor = COMMON.SerponCombatExecuterBossBarNameColor.get();
        SerponCombatExecuterMaxHealth = COMMON.SerponCombatExecuterMaxHealth.get();
        SerponCombatExecuterMeleeDamage = COMMON.SerponCombatExecuterMeleeDamage.get();
        SerponCombatExecuterMeleeDamageFury = COMMON.SerponCombatExecuterMeleeDamageFury.get();
        SerponCombatExecuterArmor = COMMON.SerponCombatExecuterArmor.get();
        SerponCombatExecuterArmorFury = COMMON.SerponCombatExecuterArmorFury.get();
        SerponCombatExecuterMovementSpeed = COMMON.SerponCombatExecuterMovementSpeed.get();
        SerponCombatExecuterFlyingSpeed = COMMON.SerponCombatExecuterFlyingSpeed.get();
        SerponCombatExecuterAttackKnockback = COMMON.SerponCombatExecuterAttackKnockback.get();
        SerponCombatExecuterKnockbackResistance = COMMON.SerponCombatExecuterKnockbackResistance.get();
        SerponCombatExecuterKnockbackResistanceFury = COMMON.SerponCombatExecuterKnockbackResistanceFury.get();
        SerponCombatExecuterDropEquipmentChance = COMMON.SerponCombatExecuterDropEquipmentChance.get();
        SerponCombatExecuterRoundTimeMin = COMMON.SerponCombatExecuterRoundTimeMin.get();
        SerponCombatExecuterRoundTimeMax = COMMON.SerponCombatExecuterRoundTimeMax.get();
        SerponCombatExecuterFlameSlashChance = COMMON.SerponCombatExecuterFlameSlashChance.get();
        SerponCombatExecuterWhirlwindSlashChance = COMMON.SerponCombatExecuterWhirlwindSlashChance.get();
        SerponCombatExecuterFireAbsorptionPercentage = COMMON.SerponCombatExecuterFireAbsorptionPercentage.get();
        SerponCombatExecuterSpellNormalLevel = COMMON.SerponCombatExecuterSpellNormalLevel.get();
        SerponCombatExecuterSpellCooldown = COMMON.SerponCombatExecuterSpellCooldown.get();
        SerponCombatExecuterAttackPercentage = COMMON.SerponCombatExecuterAttackPercentage.get();
        SerponCombatExecuterMagicAttackPercentage = COMMON.SerponCombatExecuterMagicAttackPercentage.get();
        SerponCombatExecuterDamageCap = COMMON.SerponCombatExecuterDamageCap.get();
        SerponCombatExecuterDamageCooldownTick = COMMON.SerponCombatExecuterDamageCooldownTick.get();
        //盘罗仙蔓
        CelestialCoilvineBossBarColor = COMMON.CelestialCoilvineBossBarColor.get();
        CelestialCoilvineBossBarNameColor = COMMON.CelestialCoilvineBossBarNameColor.get();
        CelestialCoilvineMaxHealth = COMMON.CelestialCoilvineMaxHealth.get();
        CelestialCoilvineMeleeDamage = COMMON.CelestialCoilvineMeleeDamage.get();
        CelestialCoilvineArmor = COMMON.CelestialCoilvineArmor.get();
        CelestialCoilvineMovementSpeed = COMMON.CelestialCoilvineMovementSpeed.get();
        CelestialCoilvineAttackKnockback = COMMON.CelestialCoilvineAttackKnockback.get();
        CelestialCoilvineKnockbackResistance = COMMON.CelestialCoilvineKnockbackResistance.get();
        CelestialCoilvineSpellNormalLevel = COMMON.CelestialCoilvineSpellNormalLevel.get();
        CelestialCoilvineAttackPercentage = COMMON.CelestialCoilvineAttackPercentage.get();
        CelestialCoilvineMagicAttackPercentage = COMMON.CelestialCoilvineMagicAttackPercentage.get();
        CelestialCoilvineDamageCap = COMMON.CelestialCoilvineDamageCap.get();
        CelestialCoilvineDamageCooldownTick = COMMON.CelestialCoilvineDamageCooldownTick.get();
        //二轮傀儡
        SecondRounderGolemBossBarColor = COMMON.SecondRounderGolemBossBarColor.get();
        SecondRounderGolemBossBarNameColor = COMMON.SecondRounderGolemBossBarNameColor.get();
        SecondRounderGolemMaxHealth = COMMON.SecondRounderGolemMaxHealth.get();
        SecondRounderGolemMeleeDamage = COMMON.SecondRounderGolemMeleeDamage.get();
        SecondRounderGolemMeleeDamageUseSword = COMMON.SecondRounderGolemMeleeDamageUseSword.get();
        SecondRounderGolemArmor = COMMON.SecondRounderGolemArmor.get();
        SecondRounderGolemMovementSpeed = COMMON.SecondRounderGolemMovementSpeed.get();
        SecondRounderGolemMovementSpeedUseSword = COMMON.SecondRounderGolemMovementSpeedUseSword.get();
        SecondRounderGolemAttackKnockback = COMMON.SecondRounderGolemAttackKnockback.get();
        SecondRounderGolemKnockbackResistance = COMMON.SecondRounderGolemKnockbackResistance.get();
        SecondRounderGolemArmorUseSword = COMMON.SecondRounderGolemArmorUseSword.get();
        SecondRounderGolemSpellNormalLevel = COMMON.SecondRounderGolemSpellNormalLevel.get();
        SecondRounderGolemBreathCooldown = COMMON.SecondRounderGolemBreathCooldown.get();
        SecondRounderGolemIntenseBombingCooldown = COMMON.SecondRounderGolemIntenseBombingCooldown.get();
        SecondRounderGolemFireAbsorptionPercentage = COMMON.SecondRounderGolemFireAbsorptionPercentage.get();
        SecondRounderGolemNotAttackedStartHealCooldown = COMMON.SecondRounderGolemNotAttackedStartHealCooldown.get();
        SecondRounderGolemNormalMerorToughnessNonMagical = COMMON.SecondRounderGolemNormalMerorToughnessNonMagical.get();
        SecondRounderGolemNormalMerorToughnessMagical = COMMON.SecondRounderGolemNormalMerorToughnessMagical.get();
        SecondRounderGolemPoisonFireCloakTime = COMMON.SecondRounderGolemPoisonFireCloakTime.get();
        SecondRounderGolemPoisonFireCloakFireTime = COMMON.SecondRounderGolemPoisonFireCloakFireTime.get();
        SecondRounderGolemPoisonFireCloakPotionLevel = COMMON.SecondRounderGolemPoisonFireCloakPotionLevel.get();
        SecondRounderGolemSagesCooldown = COMMON.SecondRounderGolemSagesCooldown.get();
        SecondRounderGolemBreakBlockCooldown = COMMON.SecondRounderGolemBreakBlockCooldown.get();
        SecondRounderGolemAttackPercentage = COMMON.SecondRounderGolemAttackPercentage.get();
        SecondRounderGolemSwordAttackPercentage = COMMON.SecondRounderGolemSwordAttackPercentage.get();
        SecondRounderGolemMagicAttackPercentage = COMMON.SecondRounderGolemMagicAttackPercentage.get();
        SecondRounderGolemDamageCap = COMMON.SecondRounderGolemDamageCap.get();
        SecondRounderGolemDamageCooldownTick = COMMON.SecondRounderGolemDamageCooldownTick.get();
    }

    public static void bakeClientConfig() {
    }

    @SubscribeEvent
    public static void onModConfigEvent(final ModConfigEvent.Loading configEvent) {
        if (configEvent.getConfig().getSpec() == OtherMainConfig.COMMON_SPEC) {
            bakeCommonConfig();
        } else if (configEvent.getConfig().getSpec() == OtherMainConfig.CLIENT_SPEC) {
            bakeClientConfig();
        }
    }

    public static class CommonConfig {
        public final ForgeConfigSpec.BooleanValue SpecialDay;

        public final ForgeConfigSpec.ConfigValue<List<String>> CarvedVillagerCustomNameWide;
        public final ForgeConfigSpec.ConfigValue<List<String>> CarvedVillagerCustomNameSlim;
        public final ForgeConfigSpec.BooleanValue SpawnEggCarvedVillagerHasArmor;
        public final ForgeConfigSpec.BooleanValue CarvedObligation;
        public final ForgeConfigSpec.BooleanValue CarvedActiveAttackCreeper;
        public final ForgeConfigSpec.ConfigValue<List<String>> CarvedGonnaActiveAttackList;
        public final ForgeConfigSpec.ConfigValue<List<String>> CarvedNeverGonnaActiveAttackList;
        public final ForgeConfigSpec.ConfigValue<List<String>> CarvedCanNotAttackList;
        public final ForgeConfigSpec.BooleanValue CarvedHoundRetire;
        public final ForgeConfigSpec.BooleanValue CarvedVillagerHelmetHeightAsVillager;

        public final ForgeConfigSpec.BooleanValue NewProfessionVillagerGiveCustomGift;

        //莫厄通缉
        public final ForgeConfigSpec.BooleanValue MerorHighEnergySpaceTeleport;
        public final ForgeConfigSpec.ConfigValue<List<String>> MerorHighEnergySpaceTeleportAboutDimension;
        public final ForgeConfigSpec.DoubleValue MerorWantedChancePerSecond;
        public final ForgeConfigSpec.BooleanValue AllCreaturesCanBeWantedByMeror;
        //邪笑之夜
        public final ForgeConfigSpec.IntValue WickedRidiculeNightBossBarColor;
        public final ForgeConfigSpec.IntValue WickedRidiculeNightBossBarNameColor;
        public final ForgeConfigSpec.ConfigValue<List<String>> WickedRidiculeNightAboutDimension;
        public final ForgeConfigSpec.BooleanValue WickedRidiculeNightAboutAllDimension;
        public final ForgeConfigSpec.DoubleValue WickedRidiculeNightChance;
        public final ForgeConfigSpec.BooleanValue WickedRidiculeNightBossBar;
        public final ForgeConfigSpec.BooleanValue WickedRidiculeNightScreenOverlay;
        public final ForgeConfigSpec.DoubleValue WickedRidiculeNightScreenOverlayLevel;
        public final ForgeConfigSpec.IntValue WickedRidiculeNightScreenOverlayColorChoose;
        public final ForgeConfigSpec.BooleanValue WickedRidiculeNightFog;
        public final ForgeConfigSpec.IntValue WickedRidiculeNightFogColorChoose;
        public final ForgeConfigSpec.BooleanValue WickedRidiculeNightBlockColor;
        public final ForgeConfigSpec.IntValue WickedRidiculeNightBlockColorChoose;
        public final ForgeConfigSpec.BooleanValue WickedRidiculeNightLiquidColor;
        public final ForgeConfigSpec.IntValue WickedRidiculeNightLiquidColorChoose;
        //泽林亲和
        public final ForgeConfigSpec.ConfigValue<List<String>> ZsieinAffinityAboutDimension;
        public final ForgeConfigSpec.BooleanValue ZsieinAffinityAboutAllDimension;
        public final ForgeConfigSpec.DoubleValue ZsieinAffinityMaxLevel;
        public final ForgeConfigSpec.DoubleValue ZsieinMildInfectedLevel;
        public final ForgeConfigSpec.DoubleValue ZsieinModerateInfectedLevel;
        public final ForgeConfigSpec.DoubleValue ZsieinSevereInfectedLevel;
        public final ForgeConfigSpec.DoubleValue ZsieinExtremelyInfectedLevel;

        public final ForgeConfigSpec.BooleanValue SecondRoundWorldSpecialScreen;
        public final ForgeConfigSpec.BooleanValue RandomSkinMobHasUnderTexture;
        public final ForgeConfigSpec.DoubleValue PurpleSandWeatherLightningChance;
        public final ForgeConfigSpec.BooleanValue PurpleSandEyeProtectionMode;
        public final ForgeConfigSpec.BooleanValue PurpleSandCustomColor;
        public final ForgeConfigSpec.IntValue PurpleSandCustomColorUse;
        public final ForgeConfigSpec.BooleanValue SomeEliteHasBossBarBaseInGameRule;
        public final ForgeConfigSpec.BooleanValue EliteBossBarOnlyCombat;
        public final ForgeConfigSpec.ConfigValue<List<String>> EliteCanHasBossBar;
        public final ForgeConfigSpec.BooleanValue MerorMonstrositySpecialGlow;
        public final ForgeConfigSpec.BooleanValue DefectorMustAttack;
        public final ForgeConfigSpec.BooleanValue SerponWeaknessInNonHotBiomes;
        public final ForgeConfigSpec.BooleanValue SerponCanWearAllArmor;
        public final ForgeConfigSpec.BooleanValue WonderlinCanWearAllArmor;
        public final ForgeConfigSpec.DoubleValue ResurrectCoffinSedimentGuardWeight;
        public final ForgeConfigSpec.DoubleValue ResurrectCoffinSedimentSoulStabilizerWeight;
        public final ForgeConfigSpec.DoubleValue ResurrectCoffinHeavyArmoredSedimentGuardWeight;
        public final ForgeConfigSpec.DoubleValue ResurrectCoffinSedimentGuardWeightAfterTenResurrections;
        public final ForgeConfigSpec.DoubleValue ResurrectCoffinSedimentSoulStabilizerWeightAfterTenResurrections;
        public final ForgeConfigSpec.DoubleValue ResurrectCoffinHeavyArmoredSedimentGuardWeightAfterTenResurrections;
        public final ForgeConfigSpec.BooleanValue RustBeetleFindIronAboutIsItemCraftedWithIron;
        public final ForgeConfigSpec.DoubleValue MerorMachineMerorToughness;
        public final ForgeConfigSpec.DoubleValue VirtualCaveCockroachMaxSize;
        public final ForgeConfigSpec.IntValue WonderlandLiCatMaxWild;
        public final ForgeConfigSpec.BooleanValue VirtualCaveCockroachCute;

        public final ForgeConfigSpec.BooleanValue SameTypeBossOnlyOne;
        public final ForgeConfigSpec.BooleanValue BossOnlyOne;
        public final ForgeConfigSpec.DoubleValue BossSpawnRestrictionRange;
        public final ForgeConfigSpec.BooleanValue SameTypeEliteOnlyOne;
        public final ForgeConfigSpec.BooleanValue EliteOnlyOne;
        public final ForgeConfigSpec.DoubleValue EliteSpawnRestrictionRange;
        public final ForgeConfigSpec.BooleanValue EliteAndBossOnlyOne;

        //探险者
        public final ForgeConfigSpec.BooleanValue RaidSpawnExplorer;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnExplorerCount;
        //行刑者
        public final ForgeConfigSpec.BooleanValue RaidSpawnExecutioner;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnExecutionerCount;
        //绘图者
        public final ForgeConfigSpec.BooleanValue RaidSpawnMapmaker;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnMapmakerCount;
        //背叛者
        public final ForgeConfigSpec.BooleanValue RaidSpawnDefector;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnDefectorCount;
        //执旗者
        public final ForgeConfigSpec.BooleanValue RaidSpawnBannerBearer;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnBannerBearerCount;
        //爆破者
        public final ForgeConfigSpec.BooleanValue RaidSpawnBlaster;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnBlasterCount;
        //吹号者
        public final ForgeConfigSpec.BooleanValue RaidSpawnTrumpeter;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnTrumpeterCount;
        //传送者
        public final ForgeConfigSpec.BooleanValue RaidSpawnTeleporter;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnTeleporterCount;
        //旋风者
        public final ForgeConfigSpec.BooleanValue RaidSpawnCycloner;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnCyclonerCount;
        //掷枪者
        public final ForgeConfigSpec.BooleanValue RaidSpawnJavelinThrower;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnJavelinThrowerCount;
        //养尸人
        public final ForgeConfigSpec.BooleanValue RaidSpawnZombieKeeper;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnZombieKeeperCount;
        //苦寒术士
        public final ForgeConfigSpec.BooleanValue RaidSpawnBitterColdSorcerer;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnBitterColdSorcererCount;
        //吐火者
        public final ForgeConfigSpec.BooleanValue RaidSpawnFireSpitter;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnFireSpitterCount;
        //野探客
        public final ForgeConfigSpec.BooleanValue RaidSpawnWildFinder;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnWildFinderCount;
        //潜海者
        public final ForgeConfigSpec.BooleanValue RaidSpawnSubmariner;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnSubmarinerCount;
        //灯术师
        public final ForgeConfigSpec.BooleanValue RaidSpawnLampWizard;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnLampWizardCount;
        //奴制监督者
        public final ForgeConfigSpec.BooleanValue RaidSpawnSlaverySupervisor;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnSlaverySupervisorCount;
        //火力倾泻者
        public final ForgeConfigSpec.BooleanValue RaidSpawnFirepowerPourer;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnFirepowerPourerCount;
        //巫神汉
        public final ForgeConfigSpec.BooleanValue RaidSpawnNecromancyWarlock;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnNecromancyWarlockCount;
        //引力师
        public final ForgeConfigSpec.BooleanValue RaidSpawnGaviler;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnGavilerCount;
        //斧头杀人狂
        public final ForgeConfigSpec.BooleanValue RaidSpawnAxCrazy;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnAxCrazyCount;
        //涉险者
        public final ForgeConfigSpec.BooleanValue RaidSpawnAdventurer;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnAdventurerCount;
        //鬼婆
        public final ForgeConfigSpec.BooleanValue RaidSpawnHag;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnHagCount;
        //女巫学者
        public final ForgeConfigSpec.BooleanValue RaidSpawnWitchScholar;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnWitchScholarCount;
        //紫沙女巫
        public final ForgeConfigSpec.BooleanValue RaidSpawnPurpleSandWitch;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnPurpleSandWitchCount;
        //灵奴
        public final ForgeConfigSpec.BooleanValue RaidSpawnSpirve;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnSpirveCount;
        //大巫婆
        public final ForgeConfigSpec.BooleanValue RaidSpawnBigWitch;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnBigWitchCount;
        //灾厄旗帜投影
        public final ForgeConfigSpec.BooleanValue RaidSpawnOminousBannerProjection;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnOminousBannerProjectionCount;
        //紫沙鬼婆
        public final ForgeConfigSpec.BooleanValue RaidSpawnPurpleSandHag;
        public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RaidSpawnPurpleSandHagCount;

        public final ForgeConfigSpec.BooleanValue BossBaseAttributeCanUseConfig;
        public final ForgeConfigSpec.BooleanValue RaidBossCanIncreaseSummonCountBasedOnPlayerCount;
        public final ForgeConfigSpec.DoubleValue RaidBossCanIncreaseSummonCountBasedOnPlayerCountFindReach;
        public final ForgeConfigSpec.IntValue RaidBossCanIncreaseSummonCountBasedOnPlayerCountMaxCount;
        public final ForgeConfigSpec.BooleanValue RaidBossCanIncreaseSummonCountBasedOnPlayerCountContainAllBoss;
        public final ForgeConfigSpec.ConfigValue<List<String>> RaidBossCanIncreaseSummonCountBasedOnPlayerCountContainBoss;
        public final ForgeConfigSpec.ConfigValue<List<String>> BossHasPercentageDamage;
        public final ForgeConfigSpec.ConfigValue<List<String>> BossHasDamageCap;
        public final ForgeConfigSpec.ConfigValue<List<String>> BossHasDamageCooldownTick;
        //沉降领主
        public final ForgeConfigSpec.IntValue SedimentLordBossBarColor;
        public final ForgeConfigSpec.IntValue SedimentLordBossBarNameColor;
        public final ForgeConfigSpec.DoubleValue SedimentLordMaxHealth;
        public final ForgeConfigSpec.DoubleValue SedimentLordMeleeDamage;
        public final ForgeConfigSpec.DoubleValue SedimentLordArmor;
        public final ForgeConfigSpec.DoubleValue SedimentLordMovementSpeed;
        public final ForgeConfigSpec.DoubleValue SedimentLordAttackKnockback;
        public final ForgeConfigSpec.DoubleValue SedimentLordKnockbackResistance;
        public final ForgeConfigSpec.DoubleValue SedimentLordLeadCooldown;
        public final ForgeConfigSpec.DoubleValue SedimentLordDropEquipmentChance;
        public final ForgeConfigSpec.DoubleValue SedimentLordAttackPercentage;
        public final ForgeConfigSpec.DoubleValue SedimentLordMagicAttackPercentage;
        public final ForgeConfigSpec.DoubleValue SedimentLordDamageCap;
        public final ForgeConfigSpec.DoubleValue SedimentLordDamageCooldownTick;
    //繁兴使者
        public final ForgeConfigSpec.IntValue ProsperousEnvoyBossBarColor;
        public final ForgeConfigSpec.IntValue ProsperousEnvoyBossBarNameColor;
        public final ForgeConfigSpec.DoubleValue ProsperousEnvoyMaxHealth;
        public final ForgeConfigSpec.DoubleValue ProsperousEnvoyMeleeDamage;
        public final ForgeConfigSpec.DoubleValue ProsperousEnvoyArmor;
        public final ForgeConfigSpec.DoubleValue ProsperousEnvoyMovementSpeed;
        public final ForgeConfigSpec.DoubleValue ProsperousEnvoyAttackKnockback;
        public final ForgeConfigSpec.DoubleValue ProsperousEnvoyKnockbackResistance;
        public final ForgeConfigSpec.DoubleValue ProsperousEnvoyToughHurtCooldown;
        public final ForgeConfigSpec.DoubleValue ProsperousEnvoyBreakToughHurtCooldownMultiple;
        public final ForgeConfigSpec.DoubleValue ProsperousEnvoyBreakToughNeedBaseDamage;
        public final ForgeConfigSpec.DoubleValue ProsperousEnvoyMeleeAttackCooldown;
        public final ForgeConfigSpec.DoubleValue ProsperousEnvoyProsperousTrampleChance;
        public final ForgeConfigSpec.DoubleValue ProsperousEnvoyProsperousRushTime;
        public final ForgeConfigSpec.DoubleValue ProsperousEnvoyProsperousRushCooldown;
        public final ForgeConfigSpec.DoubleValue ProsperousEnvoyBreakBlockCooldown;
        public final ForgeConfigSpec.DoubleValue ProsperousEnvoyAttackPercentage;
        public final ForgeConfigSpec.DoubleValue ProsperousEnvoyMagicAttackPercentage;
        public final ForgeConfigSpec.DoubleValue ProsperousEnvoyDamageCap;
        public final ForgeConfigSpec.DoubleValue ProsperousEnvoyDamageCooldownTick;
    //灾厄旗帜投影
        public final ForgeConfigSpec.IntValue OminousBannerProjectionBossBarColor;
        public final ForgeConfigSpec.IntValue OminousBannerProjectionBossBarNameColor;
        public final ForgeConfigSpec.DoubleValue OminousBannerProjectionMaxHealth;
        public final ForgeConfigSpec.DoubleValue OminousBannerProjectionMeleeDamage;
        public final ForgeConfigSpec.DoubleValue OminousBannerProjectionArmor;
        public final ForgeConfigSpec.DoubleValue OminousBannerProjectionMovementSpeed;
        public final ForgeConfigSpec.DoubleValue OminousBannerProjectionAttackKnockback;
        public final ForgeConfigSpec.DoubleValue OminousBannerProjectionKnockbackResistance;
        public final ForgeConfigSpec.DoubleValue OminousBannerProjectionRoundTimeMin;
        public final ForgeConfigSpec.DoubleValue OminousBannerProjectionRoundTimeMax;
        public final ForgeConfigSpec.IntValue OminousBannerProjectionMaxRound;
        public final ForgeConfigSpec.DoubleValue OminousBannerProjectionPointsBase;
        public final ForgeConfigSpec.DoubleValue OminousBannerProjectionPointsRoundGrowthMultiple;
        public final ForgeConfigSpec.BooleanValue OminousBannerProjectionAXCrazyCanAppear;
        public final ForgeConfigSpec.BooleanValue OminousBannerProjectionBiomeIllagerCanAppearInOtherBiome;
        public final ForgeConfigSpec.IntValue OminousBannerProjectionEliteCount;
        public final ForgeConfigSpec.DoubleValue OminousBannerProjectionAttackPercentage;
        public final ForgeConfigSpec.DoubleValue OminousBannerProjectionMagicAttackPercentage;
        public final ForgeConfigSpec.DoubleValue OminousBannerProjectionDamageCap;
        public final ForgeConfigSpec.DoubleValue OminousBannerProjectionDamageCooldownTick;
    //镇山大王
        public final ForgeConfigSpec.IntValue TyrantOfTheMountainRealmBossBarColor;
        public final ForgeConfigSpec.IntValue TyrantOfTheMountainRealmBossBarNameColor;
        public final ForgeConfigSpec.DoubleValue TyrantOfTheMountainRealmMaxHealth;
        public final ForgeConfigSpec.DoubleValue TyrantOfTheMountainRealmMeleeDamage;
        public final ForgeConfigSpec.DoubleValue TyrantOfTheMountainRealmArmor;
        public final ForgeConfigSpec.DoubleValue TyrantOfTheMountainRealmMovementSpeed;
        public final ForgeConfigSpec.DoubleValue TyrantOfTheMountainRealmAttackKnockback;
        public final ForgeConfigSpec.DoubleValue TyrantOfTheMountainRealmKnockbackResistance;
        public final ForgeConfigSpec.DoubleValue TyrantOfTheMountainRealmMeleeAttackCooldown;
        public final ForgeConfigSpec.IntValue TyrantOfTheMountainRealmSpellNormalLevel;
        public final ForgeConfigSpec.IntValue TyrantOfTheMountainRealmFlagSpearCount;
        public final ForgeConfigSpec.DoubleValue TyrantOfTheMountainRealmBreakBlockCooldown;
        public final ForgeConfigSpec.DoubleValue TyrantOfTheMountainRealmAttackPercentage;
        public final ForgeConfigSpec.DoubleValue TyrantOfTheMountainRealmMagicAttackPercentage;
        public final ForgeConfigSpec.DoubleValue TyrantOfTheMountainRealmDamageCap;
        public final ForgeConfigSpec.DoubleValue TyrantOfTheMountainRealmDamageCooldownTick;
    //蚀龙兽
        public final ForgeConfigSpec.IntValue CorrosiverBossBarColor;
        public final ForgeConfigSpec.IntValue CorrosiverBossBarNameColor;
        public final ForgeConfigSpec.DoubleValue CorrosiverMaxHealth;
        public final ForgeConfigSpec.DoubleValue CorrosiverMeleeDamage;
        public final ForgeConfigSpec.DoubleValue CorrosiverArmor;
        public final ForgeConfigSpec.DoubleValue CorrosiverMovementSpeed;
        public final ForgeConfigSpec.DoubleValue CorrosiverAttackKnockback;
        public final ForgeConfigSpec.DoubleValue CorrosiverKnockbackResistance;
        public final ForgeConfigSpec.DoubleValue CorrosiverToughHurtCooldown;
        public final ForgeConfigSpec.DoubleValue CorrosiverBreakToughHurtCooldownMultiple;
        public final ForgeConfigSpec.DoubleValue CorrosiverBreakToughNeedBaseDamage;
        public final ForgeConfigSpec.DoubleValue CorrosiverMeleeAttackCooldown;
        public final ForgeConfigSpec.DoubleValue CorrosiverSpellCooldown;
        public final ForgeConfigSpec.IntValue CorrosiverSpellNormalLevel;
        public final ForgeConfigSpec.DoubleValue CorrosiverColdAbsorptionPercentage;
        public final ForgeConfigSpec.DoubleValue CorrosiverBreakBlockCooldown;
        public final ForgeConfigSpec.DoubleValue CorrosiverAttackPercentage;
        public final ForgeConfigSpec.DoubleValue CorrosiverMagicAttackPercentage;
        public final ForgeConfigSpec.DoubleValue CorrosiverDamageCap;
        public final ForgeConfigSpec.DoubleValue CorrosiverDamageCooldownTick;
    //紫沙鬼婆
        public final ForgeConfigSpec.IntValue PurpleSandHagBossBarColor;
        public final ForgeConfigSpec.IntValue PurpleSandHagBossBarNameColor;
        public final ForgeConfigSpec.DoubleValue PurpleSandHagMaxHealth;
        public final ForgeConfigSpec.DoubleValue PurpleSandHagMeleeDamage;
        public final ForgeConfigSpec.DoubleValue PurpleSandHagArmor;
        public final ForgeConfigSpec.DoubleValue PurpleSandHagMovementSpeed;
        public final ForgeConfigSpec.DoubleValue PurpleSandHagAttackKnockback;
        public final ForgeConfigSpec.DoubleValue PurpleSandHagKnockbackResistance;
        public final ForgeConfigSpec.DoubleValue PurpleSandHagMainSpellCooldown;
        public final ForgeConfigSpec.DoubleValue PurpleSandHagOtherSpellCooldown;
        public final ForgeConfigSpec.IntValue PurpleSandHagSpellNormalLevel;
        public final ForgeConfigSpec.DoubleValue PurpleSandHagAttackPercentage;
        public final ForgeConfigSpec.DoubleValue PurpleSandHagMagicAttackPercentage;
        public final ForgeConfigSpec.DoubleValue PurpleSandHagDamageCap;
        public final ForgeConfigSpec.DoubleValue PurpleSandHagDamageCooldownTick;
    //宝石恶鳞
        public final ForgeConfigSpec.IntValue GemstoneMalignasaurBossBarColor;
        public final ForgeConfigSpec.IntValue GemstoneMalignasaurBossBarNameColor;
        public final ForgeConfigSpec.DoubleValue GemstoneMalignasaurMaxHealth;
        public final ForgeConfigSpec.DoubleValue GemstoneMalignasaurMeleeDamage;
        public final ForgeConfigSpec.DoubleValue GemstoneMalignasaurArmor;
        public final ForgeConfigSpec.DoubleValue GemstoneMalignasaurArmorGemstoneArmor;
        public final ForgeConfigSpec.DoubleValue GemstoneMalignasaurMovementSpeed;
        public final ForgeConfigSpec.DoubleValue GemstoneMalignasaurMovementSpeedGemstoneArmor;
        public final ForgeConfigSpec.DoubleValue GemstoneMalignasaurAttackKnockback;
        public final ForgeConfigSpec.DoubleValue GemstoneMalignasaurKnockbackResistance;
        public final ForgeConfigSpec.DoubleValue GemstoneMalignasaurToughHurtCooldown;
        public final ForgeConfigSpec.DoubleValue GemstoneMalignasaurBreakToughHurtCooldownMultiple;
        public final ForgeConfigSpec.DoubleValue GemstoneMalignasaurBreakToughNeedBaseDamage;
        public final ForgeConfigSpec.DoubleValue GemstoneMalignasaurMeleeAttackCooldown;
        public final ForgeConfigSpec.IntValue GemstoneMalignasaurJumpAttackLevel;
        public final ForgeConfigSpec.DoubleValue GemstoneMalignasaurJumpAttackCooldown;
        public final ForgeConfigSpec.IntValue GemstoneMalignasaurSpellNormalLevel;
        public final ForgeConfigSpec.DoubleValue GemstoneMalignasaurBreathCooldown;
        public final ForgeConfigSpec.DoubleValue GemstoneMalignasaurControlCooldown;
        public final ForgeConfigSpec.DoubleValue GemstoneMalignasaurAngrySpellCooldown;
        public final ForgeConfigSpec.DoubleValue GemstoneMalignasaurFakeDamageCap;
        public final ForgeConfigSpec.DoubleValue GemstoneMalignasaurBreakFakeDamageCap;
        public final ForgeConfigSpec.DoubleValue GemstoneMalignasaurBreakBlockCooldown;
        public final ForgeConfigSpec.DoubleValue GemstoneMalignasaurAttackPercentage;
        public final ForgeConfigSpec.DoubleValue GemstoneMalignasaurMagicAttackPercentage;
        public final ForgeConfigSpec.DoubleValue GemstoneMalignasaurDamageCap;
        public final ForgeConfigSpec.DoubleValue GemstoneMalignasaurDamageCooldownTick;
    //明路巨兽
        public final ForgeConfigSpec.IntValue BrightLandBeastBossBarColor;
        public final ForgeConfigSpec.IntValue BrightLandBeastBossBarNameColor;
        public final ForgeConfigSpec.DoubleValue BrightLandBeastMaxHealth;
        public final ForgeConfigSpec.DoubleValue BrightLandBeastMeleeDamage;
        public final ForgeConfigSpec.DoubleValue BrightLandBeastArmor;
        public final ForgeConfigSpec.DoubleValue BrightLandBeastMovementSpeed;
        public final ForgeConfigSpec.DoubleValue BrightLandBeastAttackKnockback;
        public final ForgeConfigSpec.DoubleValue BrightLandBeastKnockbackResistance;
        public final ForgeConfigSpec.DoubleValue BrightLandBeastToughHurtCooldown;
        public final ForgeConfigSpec.DoubleValue BrightLandBeastBreakToughHurtCooldownMultiple;
        public final ForgeConfigSpec.DoubleValue BrightLandBeastBreakToughNeedBaseDamage;
        public final ForgeConfigSpec.DoubleValue BrightLandBeastMeleeAttackCooldown;
        public final ForgeConfigSpec.DoubleValue BrightLandBeastQuakeCooldown;
        public final ForgeConfigSpec.DoubleValue BrightLandBeastQuakeBaseDamage;
        public final ForgeConfigSpec.DoubleValue BrightLandBeastQuakeHealthMultipleDamage;
        public final ForgeConfigSpec.DoubleValue BrightLandBeastBreakBlockCooldown;
        public final ForgeConfigSpec.DoubleValue BrightLandBeastAttackPercentage;
        public final ForgeConfigSpec.DoubleValue BrightLandBeastMagicAttackPercentage;
        public final ForgeConfigSpec.DoubleValue BrightLandBeastDamageCap;
        public final ForgeConfigSpec.DoubleValue BrightLandBeastDamageCooldownTick;
    //蛇龙执战者
        public final ForgeConfigSpec.IntValue SerponCombatExecuterBossBarColor;
        public final ForgeConfigSpec.IntValue SerponCombatExecuterBossBarNameColor;
        public final ForgeConfigSpec.DoubleValue SerponCombatExecuterMaxHealth;
        public final ForgeConfigSpec.DoubleValue SerponCombatExecuterMeleeDamage;
        public final ForgeConfigSpec.DoubleValue SerponCombatExecuterMeleeDamageFury;
        public final ForgeConfigSpec.DoubleValue SerponCombatExecuterArmor;
        public final ForgeConfigSpec.DoubleValue SerponCombatExecuterArmorFury;
        public final ForgeConfigSpec.DoubleValue SerponCombatExecuterMovementSpeed;
        public final ForgeConfigSpec.DoubleValue SerponCombatExecuterFlyingSpeed;
        public final ForgeConfigSpec.DoubleValue SerponCombatExecuterAttackKnockback;
        public final ForgeConfigSpec.DoubleValue SerponCombatExecuterKnockbackResistance;
        public final ForgeConfigSpec.DoubleValue SerponCombatExecuterKnockbackResistanceFury;
        public final ForgeConfigSpec.DoubleValue SerponCombatExecuterDropEquipmentChance;
        public final ForgeConfigSpec.DoubleValue SerponCombatExecuterRoundTimeMin;
        public final ForgeConfigSpec.DoubleValue SerponCombatExecuterRoundTimeMax;
        public final ForgeConfigSpec.DoubleValue SerponCombatExecuterFlameSlashChance;
        public final ForgeConfigSpec.DoubleValue SerponCombatExecuterWhirlwindSlashChance;
        public final ForgeConfigSpec.DoubleValue SerponCombatExecuterFireAbsorptionPercentage;
        public final ForgeConfigSpec.IntValue SerponCombatExecuterSpellNormalLevel;
        public final ForgeConfigSpec.DoubleValue SerponCombatExecuterSpellCooldown;
        public final ForgeConfigSpec.DoubleValue SerponCombatExecuterAttackPercentage;
        public final ForgeConfigSpec.DoubleValue SerponCombatExecuterMagicAttackPercentage;
        public final ForgeConfigSpec.DoubleValue SerponCombatExecuterDamageCap;
        public final ForgeConfigSpec.DoubleValue SerponCombatExecuterDamageCooldownTick;
    //盘罗仙蔓
        public final ForgeConfigSpec.IntValue CelestialCoilvineBossBarColor;
        public final ForgeConfigSpec.IntValue CelestialCoilvineBossBarNameColor;
        public final ForgeConfigSpec.DoubleValue CelestialCoilvineMaxHealth;
        public final ForgeConfigSpec.DoubleValue CelestialCoilvineMeleeDamage;
        public final ForgeConfigSpec.DoubleValue CelestialCoilvineArmor;
        public final ForgeConfigSpec.DoubleValue CelestialCoilvineMovementSpeed;
        public final ForgeConfigSpec.DoubleValue CelestialCoilvineAttackKnockback;
        public final ForgeConfigSpec.DoubleValue CelestialCoilvineKnockbackResistance;
        public final ForgeConfigSpec.IntValue CelestialCoilvineSpellNormalLevel;
        public final ForgeConfigSpec.DoubleValue CelestialCoilvineAttackPercentage;
        public final ForgeConfigSpec.DoubleValue CelestialCoilvineMagicAttackPercentage;
        public final ForgeConfigSpec.DoubleValue CelestialCoilvineDamageCap;
        public final ForgeConfigSpec.DoubleValue CelestialCoilvineDamageCooldownTick;
    //二轮傀儡
        public final ForgeConfigSpec.IntValue SecondRounderGolemBossBarColor;
        public final ForgeConfigSpec.IntValue SecondRounderGolemBossBarNameColor;
        public final ForgeConfigSpec.DoubleValue SecondRounderGolemMaxHealth;
        public final ForgeConfigSpec.DoubleValue SecondRounderGolemMeleeDamage;
        public final ForgeConfigSpec.DoubleValue SecondRounderGolemMeleeDamageUseSword;
        public final ForgeConfigSpec.DoubleValue SecondRounderGolemArmor;
        public final ForgeConfigSpec.DoubleValue SecondRounderGolemMovementSpeed;
        public final ForgeConfigSpec.DoubleValue SecondRounderGolemMovementSpeedUseSword;
        public final ForgeConfigSpec.DoubleValue SecondRounderGolemAttackKnockback;
        public final ForgeConfigSpec.DoubleValue SecondRounderGolemKnockbackResistance;
        public final ForgeConfigSpec.DoubleValue SecondRounderGolemArmorUseSword;
        public final ForgeConfigSpec.IntValue SecondRounderGolemSpellNormalLevel;
        public final ForgeConfigSpec.DoubleValue SecondRounderGolemBreathCooldown;
        public final ForgeConfigSpec.DoubleValue SecondRounderGolemIntenseBombingCooldown;
        public final ForgeConfigSpec.DoubleValue SecondRounderGolemFireAbsorptionPercentage;
        public final ForgeConfigSpec.DoubleValue SecondRounderGolemNotAttackedStartHealCooldown;
        public final ForgeConfigSpec.DoubleValue SecondRounderGolemNormalMerorToughnessNonMagical;
        public final ForgeConfigSpec.DoubleValue SecondRounderGolemNormalMerorToughnessMagical;
        public final ForgeConfigSpec.DoubleValue SecondRounderGolemPoisonFireCloakTime;
        public final ForgeConfigSpec.IntValue SecondRounderGolemPoisonFireCloakFireTime;
        public final ForgeConfigSpec.IntValue SecondRounderGolemPoisonFireCloakPotionLevel;
        public final ForgeConfigSpec.DoubleValue SecondRounderGolemSagesCooldown;
        public final ForgeConfigSpec.DoubleValue SecondRounderGolemBreakBlockCooldown;
        public final ForgeConfigSpec.DoubleValue SecondRounderGolemAttackPercentage;
        public final ForgeConfigSpec.DoubleValue SecondRounderGolemSwordAttackPercentage;
        public final ForgeConfigSpec.DoubleValue SecondRounderGolemMagicAttackPercentage;
        public final ForgeConfigSpec.DoubleValue SecondRounderGolemDamageCap;
        public final ForgeConfigSpec.DoubleValue SecondRounderGolemDamageCooldownTick;

        public CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.push(" ");
            builder.pop();
            builder.push("Others");
            SpecialDay = builder.comment("Special Day")
                    .define("特殊日期", true);
            builder.pop();

            builder.push("Carved Faction");
            CarvedVillagerCustomNameWide = builder.comment("Carved Villager Custom Name Wide")
                    .define("铜刻村民自定义名称宽体", Lists.newArrayList("Jerotes_"));
            CarvedVillagerCustomNameSlim = builder.comment("Carved Villager Custom Name Slim")
                    .define("铜刻村民自定义名称细体", Lists.newArrayList("Sentnes", "Maid"));
            SpawnEggCarvedVillagerHasArmor = builder.comment("Spawn Egg Carved Villager Has Armor")
                    .define("刷怪蛋铜刻村民拥有盔甲", false);
            CarvedObligation = builder.comment("Carved Obligation")
                    .define("铜刻佣兵团是否主动攻击僵尸类[Zombies]、袭击类生物[Raiders]以外[other than]的敌对生物", true);
            CarvedActiveAttackCreeper = builder.comment("Carved Active Attack Creeper")
                    .define("铜刻佣兵团主动攻击苦力怕[Creeper]", false);
            CarvedGonnaActiveAttackList = builder.comment("Carved Gonna Active Attack List")
                    .define("铜刻佣兵团 会主动攻击生物", Lists.newArrayList("minecraft:zombie"));
            CarvedNeverGonnaActiveAttackList = builder.comment("Carved Never Gonna Active Attack List")
                    .define("铜刻佣兵团 不会主动攻击生物", Lists.newArrayList("minecraft:villager", "minecraft:iron_golem", "minecraft:wandering_trader"));
            CarvedCanNotAttackList = builder.comment("Carved Can Not Attack List")
                    .define("铜刻佣兵团 无法攻击生物", Lists.newArrayList("minecraft:villager", "minecraft:wandering_trader"));
            CarvedHoundRetire = builder.comment("Carved Hound Retire")
                    .define("驯服后的铜刻猎犬不再主动协助村民、铜刻生物、信任玩家", false);
            CarvedVillagerHelmetHeightAsVillager = builder.comment("Carved Villager Helmet Height As Villager")
                    .define("铜刻村民的头盔类似村民一样", false);
            builder.pop();


            builder.push("Villager");
            NewProfessionVillagerGiveCustomGift = builder.comment("New Profession Villager Give Custom Gift")
                    .define("新职业村民给予自定义礼物（可能冲突）", false);
            builder.pop();

            //莫厄通缉
            builder.push("Meror Wanted");
            MerorHighEnergySpaceTeleport = builder.comment("Can Meror Locator be spawn at dimensions outside of the Second Round World")
                    .define("莫厄定位器能否生成于二轮世界外的维度", false);
            MerorHighEnergySpaceTeleportAboutDimension = builder.comment("Dimensions that can Meror Locator be spawn at")
                    .define("莫厄定位器能生成的维度", Lists.newArrayList("jerotesvillage:second_round_world"));
            MerorWantedChancePerSecond = builder.comment("Meror Wanted Chance Per Second")
                    .defineInRange("莫厄通缉每秒出现几率", 0.0005, 0.0, Double.MAX_VALUE);
            AllCreaturesCanBeWantedByMeror = builder.comment("All Creatures Can Be Wanted By Meror")
                    .define("所有生物可被莫厄通缉", true);
            builder.pop();

            builder.push("Wicked Ridicule Night");
            WickedRidiculeNightBossBarColor = builder.comment("Wicked Ridicule Night Boss Bar Color")
                    .defineInRange("邪笑之夜Boss血条颜色", 2, 0, 6);
            WickedRidiculeNightBossBarNameColor = builder.comment("Wicked Ridicule Night Boss Bar Name Color")
                    .defineInRange("邪笑之夜Boss血条名字颜色", 0x680200, 0, Integer.MAX_VALUE);
            WickedRidiculeNightAboutDimension = builder.comment("Wicked Ridicule Night About Dimension")
                    .define("邪笑之夜影响维度", Lists.newArrayList("jerotesvillage:second_round_world"));
            WickedRidiculeNightAboutAllDimension = builder.comment("Wicked Ridicule Night About All Dimension")
                    .define("邪笑之夜影响全部维度", false);
            WickedRidiculeNightChance = builder.comment("Wicked Ridicule Night Chance")
                    .defineInRange("邪笑之夜几率", 0.05, 0.0, Double.MAX_VALUE);
            WickedRidiculeNightBossBar = builder.comment("Wicked Ridicule Night BossBar")
                    .define("邪笑之夜Boss血条", true);
            WickedRidiculeNightScreenOverlay = builder.comment("Wicked Ridicule Night Screen Overlay")
                    .define("邪笑之夜屏幕覆盖", true);
            WickedRidiculeNightScreenOverlayLevel = builder.comment("Wicked Ridicule Night Screen Overlay Level")
                    .defineInRange("邪笑之夜屏幕覆盖程度", 0.5, 0.0, 1.0);
            WickedRidiculeNightScreenOverlayColorChoose = builder.comment("Wicked Ridicule Night Screen Overlay Color Choose")
                    .defineInRange("邪笑之夜屏幕覆盖颜色选择", 0xFF0000, 0, Integer.MAX_VALUE);
            WickedRidiculeNightFog = builder.comment("Wicked Ridicule Night Fog")
                    .define("邪笑之夜雾气", true);
            WickedRidiculeNightFogColorChoose = builder.comment("Wicked Ridicule Night Fog Color Choose")
                    .defineInRange("邪笑之夜雾气颜色选择", 0xFF0000, 0, Integer.MAX_VALUE);
            WickedRidiculeNightBlockColor = builder.comment("Wicked Ridicule Night Block Color")
                    .define("邪笑之夜方块颜色", false);
            WickedRidiculeNightBlockColorChoose = builder.comment("Wicked Ridicule Night Block Color Choose")
                    .defineInRange("邪笑之夜方块颜色选择", 0xFF0000, 0, Integer.MAX_VALUE);
            WickedRidiculeNightLiquidColor = builder.comment("Wicked Ridicule Night Liquid Color")
                    .define("邪笑之夜液体颜色", true);
            WickedRidiculeNightLiquidColorChoose = builder.comment("Wicked Ridicule Night Liquid Color Choose")
                    .defineInRange("邪笑之夜液体颜色选择", 0xFF0000, 0, Integer.MAX_VALUE);
            builder.pop();

            //泽林亲和
            builder.push("Zsiein Affinity");
            ZsieinAffinityAboutDimension = builder.comment("Zsiein Affinity About Dimension")
                    .define("泽林亲和度影响维度", Lists.newArrayList("jerotesvillage:second_round_world"));
            ZsieinAffinityAboutAllDimension = builder.comment("Zsiein Affinity About All Dimension")
                    .define("泽林亲和度影响全部维度", false);
            ZsieinAffinityMaxLevel = builder.comment("Zsiein Affinity Max Need Level")
                    .defineInRange("泽林亲和度最大等级", 1000, 0.0, Double.MAX_VALUE);
            ZsieinMildInfectedLevel = builder.comment("Zsiein Mild Infected Level")
                    .defineInRange("泽林极度感染需要等级", 900, 0.0, Double.MAX_VALUE);
            ZsieinModerateInfectedLevel = builder.comment("Zsiein Moderate Infected Level")
                    .defineInRange("泽林极度感染需要等级", 900, 0.0, Double.MAX_VALUE);
            ZsieinSevereInfectedLevel = builder.comment("Zsiein Severe Infected Level")
                    .defineInRange("泽林极度感染需要等级", 900, 0.0, Double.MAX_VALUE);
            ZsieinExtremelyInfectedLevel = builder.comment("Zsiein Extremely Infected Level")
                    .defineInRange("泽林极度感染需要等级", 900, 0.0, Double.MAX_VALUE);
            builder.pop();

            builder.push("Other");
            SecondRoundWorldSpecialScreen = builder.comment("Second Round World Special Screen")
                    .define("二轮世界特殊屏幕", true);
            RandomSkinMobHasUnderTexture = builder.comment("Random Skin Mob Has Under Texture")
                    .define("随机纹理生物拥有底层纹理", false);
            PurpleSandWeatherLightningChance = builder.comment("Purple Sand Weather Chance Per Second")
                    .defineInRange("紫色沙漠雷电每秒出现几率", 0.0085, 0.0, Double.MAX_VALUE);
            PurpleSandEyeProtectionMode = builder.comment("Purple Sand Eye Protection Mode")
                    .define("紫沙护眼模式", true);
            PurpleSandCustomColor = builder.comment("Purple Sand Custom Color")
                    .define("紫沙自定义颜色", true);
            PurpleSandCustomColorUse = builder.comment("Purple Sand Custom Color Use")
                    .defineInRange("紫沙自定义颜色选择", 0x703c9d, 0, Integer.MAX_VALUE);
            SomeEliteHasBossBarBaseInGameRule = builder.comment("Some Elite Has Boss Bar Base In Game Rule")
                    .define("基础游戏规则部分精英拥有血条", true);
            EliteBossBarOnlyCombat = builder.comment("Elite Boss Bar Only Combat")
                    .define("精英血条战斗开启", true);
            EliteCanHasBossBar = builder.comment("Elite Can Has Boss Bar")
                    .define("可以拥有血条的精英", Lists.newArrayList(
                            "jerotesvillage:primordier",
                            "jerotesvillage:duneshockler",

                            "jerotesvillage:big_witch",
                            "jerotesvillage:woodland_hear_guardian",
                            "jerotesvillage:meror_champion",
                            "jerotesvillage:meror_monstrosity"));
            MerorMonstrositySpecialGlow = builder.comment("Meror Monstrosity Special Glow")
                    .define("莫厄巨兽特殊发光", true);
            DefectorMustAttack = builder.comment("Defector Must Attack")
                    .define("背弃者强制迎战", false);
            SerponWeaknessInNonHotBiomes = builder.comment("Serpon Weakness In Non Hot Biomes")
                    .define("蛇龙人在非炎热群系虚弱", true);
            SerponCanWearAllArmor = builder.comment("Serpon Can Wear All Armor")
                    .define("蛇龙人可穿戴任意装备", false);
            WonderlinCanWearAllArmor = builder.comment("Wonderlin Can Wear All Armor")
                    .define("奇灵可穿戴任意装备", false);
            ResurrectCoffinSedimentGuardWeight = builder.comment("Resurrect Coffin Sediment Guard Weight")
                    .defineInRange("复魂尸棺沉降守卫权重", 90.0, 0.0, Double.MAX_VALUE);
            ResurrectCoffinSedimentSoulStabilizerWeight = builder.comment("Resurrect Coffin Sediment Soul Stabilizer Weight")
                    .defineInRange("复魂尸棺沉降定灵人权重", 5.0, 0.0, Double.MAX_VALUE);
            ResurrectCoffinHeavyArmoredSedimentGuardWeight = builder.comment("Resurrect Coffin Heavy Armored Sediment Guard Weight")
                    .defineInRange("复魂尸棺重装沉降守卫权重", 5.0, 0.0, Double.MAX_VALUE);
            ResurrectCoffinSedimentGuardWeightAfterTenResurrections = builder.comment("Resurrect Coffin Sediment Guard Weight After Ten Resurrections")
                    .defineInRange("十次复活后复魂尸棺沉降守卫权重", 40.0, 0.0, Double.MAX_VALUE);
            ResurrectCoffinSedimentSoulStabilizerWeightAfterTenResurrections = builder.comment("Resurrect Coffin Sediment Soul Stabilizer Weight After Ten Resurrections")
                    .defineInRange("十次复活后复魂尸棺沉降定灵人权重", 30.0, 0.0, Double.MAX_VALUE);
            ResurrectCoffinHeavyArmoredSedimentGuardWeightAfterTenResurrections = builder.comment("Resurrect Coffin Heavy Armored Sediment Guard Weight After Ten Resurrections")
                    .defineInRange("十次复活后复魂尸棺重装沉降守卫权重", 30.0, 0.0, Double.MAX_VALUE);
            RustBeetleFindIronAboutIsItemCraftedWithIron = builder.comment("Rust Beetle Find Iron About Is Item Crafted With Iron")
                    .define("腐蚀步甲寻找铁质物品与可使用铁类合成相关", false);
            MerorMachineMerorToughness = builder.comment("Meror Machine Meror Toughness")
                    .defineInRange("莫厄韧性", 3.0, 0.0, Double.MAX_VALUE);
            VirtualCaveCockroachMaxSize = builder.comment("Virtual Cave Cockroach Max Size")
                    .defineInRange("虚洞大蠊最大体型", 45.0, 0.0, Double.MAX_VALUE);
            WonderlandLiCatMaxWild = builder.comment("Wonderland Li Cat Max Wild")
                    .defineInRange("仙境狸猫最大野性", 10, 0, Integer.MAX_VALUE);
            VirtualCaveCockroachCute = builder.comment("Virtual Cave Cockroach Cute")
                    .define("虚洞大蠊可爱", false);

            SameTypeBossOnlyOne = builder.comment("Same Type Boss Only One")
                    .define("同类型Boss仅同时存在一个", true);
            BossOnlyOne = builder.comment("Boss Only One")
                    .define("Boss仅同时存在一个", false);
            BossSpawnRestrictionRange = builder.comment("Boss Spawn Restriction Range")
                    .defineInRange("Boss生成限制距离", 256.0, 0.0, Double.MAX_VALUE);
            SameTypeEliteOnlyOne = builder.comment("Same Type Elite Only One")
                    .define("同类型精英仅同时存在一个", true);
            EliteOnlyOne = builder.comment("Elite Only One")
                    .define("精英仅同时存在一个（×）", false);
            EliteSpawnRestrictionRange = builder.comment("Elite Spawn Restriction Range")
                    .defineInRange("精英生成限制距离", 128.0, 0.0, Double.MAX_VALUE);
            EliteAndBossOnlyOne = builder.comment("Elite And Boss Only One")
                    .define("精英和Boss仅同时存在一个（×）", false);
            builder.pop();

            builder.push("Raid");
            //探险者
            RaidSpawnExplorer = builder.comment("Raid Spawn Explorer")
                    .define("探险者袭击中生成", false);
            RaidSpawnExplorerCount = builder.comment("Raid Spawn Explorer Count")
                    .worldRestart()
                    .defineList("探险者袭击中生成数量",
                            Arrays.asList(0, 0, 0, 1, 1, 2, 2, 3), (i) -> i instanceof Integer);
            //行刑者
            RaidSpawnExecutioner = builder.comment("Raid Spawn Executioner")
                    .define("行刑者袭击中生成", false);
            RaidSpawnExecutionerCount = builder.comment("Raid Spawn Executioner Count")
                    .worldRestart()
                    .defineList("行刑者袭击中生成数量",
                            Arrays.asList(0, 0, 0, 0, 1, 1, 1, 1), (i) -> i instanceof Integer);
            //绘图者
            RaidSpawnMapmaker = builder.comment("Raid Spawn Mapmaker")
                    .define("绘图者袭击中生成", false);
            RaidSpawnMapmakerCount = builder.comment("Raid Spawn Mapmaker Count")
                    .worldRestart()
                    .defineList("绘图者袭击中生成数量",
                            Arrays.asList(0, 0, 0, 0, 0, 1, 1, 1), (i) -> i instanceof Integer);
            //背叛者
            RaidSpawnDefector = builder.comment("Raid Spawn Defector")
                    .define("背叛者袭击中生成", false);
            RaidSpawnDefectorCount = builder.comment("Raid Spawn Defector Count")
                    .worldRestart()
                    .defineList("背叛者袭击中生成数量",
                            Arrays.asList(0, 1, 1, 2, 2, 2, 2, 3), (i) -> i instanceof Integer);
            //执旗者
            RaidSpawnBannerBearer = builder.comment("Raid Spawn Banner Bearer")
                    .define("执旗者袭击中生成", false);
            RaidSpawnBannerBearerCount = builder.comment("Raid Spawn Banner Bearer Count")
                    .worldRestart()
                    .defineList("执旗者袭击中生成数量",
                            Arrays.asList(0, 0, 0, 0, 0, 1, 1, 1), (i) -> i instanceof Integer);
            //爆破者
            RaidSpawnBlaster = builder.comment("Raid Spawn Blaster")
                    .define("爆破者袭击中生成", false);
            RaidSpawnBlasterCount = builder.comment("Raid Spawn Blaster Count")
                    .worldRestart()
                    .defineList("爆破者袭击中生成数量",
                            Arrays.asList(0, 0, 0, 0, 1, 1, 1, 1), (i) -> i instanceof Integer);
            //吹号者
            RaidSpawnTrumpeter = builder.comment("Raid Spawn Trumpeter")
                    .define("吹号者袭击中生成", false);
            RaidSpawnTrumpeterCount = builder.comment("Raid Spawn Trumpeter Count")
                    .worldRestart()
                    .defineList("吹号者袭击中生成数量",
                            Arrays.asList(0, 0, 0, 0, 0, 1, 1, 1), (i) -> i instanceof Integer);
            //传送者
            RaidSpawnTeleporter = builder.comment("Raid Spawn Teleporter")
                    .define("传送者袭击中生成", false);
            RaidSpawnTeleporterCount = builder.comment("Raid Spawn Teleporter Count")
                    .worldRestart()
                    .defineList("传送者袭击中生成数量",
                            Arrays.asList(0, 0, 0, 0, 0, 0, 1, 1), (i) -> i instanceof Integer);
            //旋风者
            RaidSpawnCycloner = builder.comment("Raid Spawn Cycloner")
                    .define("旋风者袭击中生成", false);
            RaidSpawnCyclonerCount = builder.comment("Raid Spawn Cycloner Count")
                    .worldRestart()
                    .defineList("旋风者袭击中生成数量",
                            Arrays.asList(0, 0, 0, 0, 0, 1, 1, 1), (i) -> i instanceof Integer);
            //掷枪者
            RaidSpawnJavelinThrower = builder.comment("Raid Spawn Javelin Thrower")
                    .define("掷枪者袭击中生成", false);
            RaidSpawnJavelinThrowerCount = builder.comment("Raid Spawn Javelin Thrower Count")
                    .worldRestart()
                    .defineList("掷枪者袭击中生成数量",
                            Arrays.asList(0, 0, 0, 1, 1, 1, 2, 2), (i) -> i instanceof Integer);
            //养尸人
            RaidSpawnZombieKeeper = builder.comment("Raid Spawn Zombie Keeper")
                    .define("养尸人袭击中生成", false);
            RaidSpawnZombieKeeperCount = builder.comment("Raid Spawn Zombie Keeper Count")
                    .worldRestart()
                    .defineList("养尸人袭击中生成数量",
                            Arrays.asList(0, 0, 0, 0, 1, 1, 1, 1), (i) -> i instanceof Integer);
            //苦寒术士
            RaidSpawnBitterColdSorcerer = builder.comment("Raid Spawn Bitter Cold Sorcerer")
                    .define("苦寒术士袭击中生成", false);
            RaidSpawnBitterColdSorcererCount = builder.comment("Raid Spawn Bitter Cold Sorcerer Count")
                    .worldRestart()
                    .defineList("苦寒术士袭击中生成数量",
                            Arrays.asList(0, 0, 0, 0, 0, 1, 1, 1), (i) -> i instanceof Integer);
            //吐火者
            RaidSpawnFireSpitter = builder.comment("Raid Spawn Fire Spitter")
                    .define("吐火者袭击中生成", false);
            RaidSpawnFireSpitterCount = builder.comment("Raid Spawn Fire Spitter Count")
                    .worldRestart()
                    .defineList("吐火者袭击中生成数量",
                            Arrays.asList(0, 0, 0, 0, 0, 1, 1, 1), (i) -> i instanceof Integer);
            //野探客
            RaidSpawnWildFinder = builder.comment("Raid Spawn Wild Finder")
                    .define("野探客袭击中生成", false);
            RaidSpawnWildFinderCount = builder.comment("Raid Spawn Wild Finder Count")
                    .worldRestart()
                    .defineList("野探客袭击中生成数量",
                            Arrays.asList(0, 0, 0, 0, 0, 1, 1, 1), (i) -> i instanceof Integer);
            //潜海者
            RaidSpawnSubmariner = builder.comment("Raid Spawn Submariner")
                    .define("潜海者袭击中生成", false);
            RaidSpawnSubmarinerCount = builder.comment("Raid Spawn Submariner Count")
                    .worldRestart()
                    .defineList("潜海者袭击中生成数量",
                            Arrays.asList(0, 0, 0, 0, 1, 1, 1, 1), (i) -> i instanceof Integer);
            //灯术师
            RaidSpawnLampWizard = builder.comment("Raid Spawn Lamp Wizard")
                    .define("灯术师袭击中生成", false);
            RaidSpawnLampWizardCount = builder.comment("Raid Spawn Lamp Wizard Count")
                    .worldRestart()
                    .defineList("灯术师袭击中生成数量",
                            Arrays.asList(0, 0, 0, 0, 0, 1, 1, 1), (i) -> i instanceof Integer);
            //奴制监督者
            RaidSpawnSlaverySupervisor = builder.comment("Raid Spawn Slavery Supervisor")
                    .define("奴制监督者袭击中生成", false);
            RaidSpawnSlaverySupervisorCount = builder.comment("Raid Spawn Slavery Supervisor Count")
                    .worldRestart()
                    .defineList("奴制监督者袭击中生成数量",
                            Arrays.asList(0, 0, 0, 0, 0, 0, 0, 1), (i) -> i instanceof Integer);
            //火力倾泻者
            RaidSpawnFirepowerPourer = builder.comment("Raid Spawn Firepower Pourer")
                    .define("火力倾泻者袭击中生成", false);
            RaidSpawnFirepowerPourerCount = builder.comment("Raid Spawn Firepower Pourer Count")
                    .worldRestart()
                    .defineList("火力倾泻者袭击中生成数量",
                            Arrays.asList(0, 0, 0, 0, 0, 0, 0, 1), (i) -> i instanceof Integer);
            //巫神汉
            RaidSpawnNecromancyWarlock = builder.comment("Raid Spawn Necromancy Warlock")
                    .define("巫神汉袭击中生成", false);
            RaidSpawnNecromancyWarlockCount = builder.comment("Raid Spawn Necromancy Warlock Count")
                    .worldRestart()
                    .defineList("巫神汉袭击中生成数量",
                            Arrays.asList(0, 0, 0, 0, 0, 0, 0, 1), (i) -> i instanceof Integer);
            //引力师
            RaidSpawnGaviler = builder.comment("Raid Spawn Gaviler")
                    .define("引力师袭击中生成", false);
            RaidSpawnGavilerCount = builder.comment("Raid Spawn Gaviler Count")
                    .worldRestart()
                    .defineList("引力师袭击中生成数量",
                            Arrays.asList(0, 0, 0, 0, 0, 0, 0, 1), (i) -> i instanceof Integer);
            //斧头杀人狂
            RaidSpawnAxCrazy = builder.comment("Raid Spawn Ax-Crazy")
                    .define("斧头杀人狂袭击中生成", false);
            RaidSpawnAxCrazyCount = builder.comment("Raid Spawn Ax-Crazy Count")
                    .worldRestart()
                    .defineList("斧头杀人狂袭击中生成数量",
                            Arrays.asList(0, 0, 0, 0, 0, 0, 0, 1), (i) -> i instanceof Integer);
            //涉险者
            RaidSpawnAdventurer = builder.comment("Raid Spawn Adventurer")
                    .define("涉险者袭击中生成", false);
            RaidSpawnAdventurerCount = builder.comment("Raid Spawn Adventurer Count")
                    .worldRestart()
                    .defineList("涉险者袭击中生成数量",
                            Arrays.asList(0, 0, 0, 0, 0, 0, 0, 1), (i) -> i instanceof Integer);
            //鬼婆
            RaidSpawnHag = builder.comment("Raid Spawn Hag")
                    .define("鬼婆袭击中生成", false);
            RaidSpawnHagCount = builder.comment("Raid Spawn Hag Count")
                    .worldRestart()
                    .defineList("鬼婆袭击中生成数量",
                            Arrays.asList(0, 0, 0, 0, 0, 0, 1, 1), (i) -> i instanceof Integer);
            //女巫学者
            RaidSpawnWitchScholar = builder.comment("Raid Spawn Witch Scholar")
                    .define("女巫学者袭击中生成", false);
            RaidSpawnWitchScholarCount = builder.comment("Raid Spawn Witch Scholar Count")
                    .worldRestart()
                    .defineList("女巫学者袭击中生成数量",
                            Arrays.asList(0, 0, 0, 0, 1, 1, 1, 1), (i) -> i instanceof Integer);
            //紫沙女巫
            RaidSpawnPurpleSandWitch = builder.comment("Raid Spawn Purple Sand Witch")
                    .define("紫沙女巫袭击中生成", false);
            RaidSpawnPurpleSandWitchCount = builder.comment("Raid Spawn Purple Sand Witch Count")
                    .worldRestart()
                    .defineList("紫沙女巫袭击中生成数量",
                            Arrays.asList(0, 0, 0, 0, 0, 1, 1, 1), (i) -> i instanceof Integer);
            //灵奴
            RaidSpawnSpirve = builder.comment("Raid Spawn Spirve")
                    .define("灵奴袭击中生成", false);
            RaidSpawnSpirveCount = builder.comment("Raid Spawn Spirve Count")
                    .worldRestart()
                    .defineList("灵奴袭击中生成数量",
                            Arrays.asList(0, 1, 1, 2, 2, 2, 2, 3), (i) -> i instanceof Integer);
            //大巫婆
            RaidSpawnBigWitch = builder.comment("Raid Spawn Big Witch")
                    .define("大巫婆袭击中生成", false);
            RaidSpawnBigWitchCount = builder.comment("Raid Spawn Big Witch Count")
                    .worldRestart()
                    .defineList("大巫婆袭击中生成数量",
                            Arrays.asList(0, 0, 0, 0, 0, 0, 0, 1), (i) -> i instanceof Integer);
            //灾厄旗帜投影
            RaidSpawnOminousBannerProjection = builder.comment("Raid Spawn Ominous Banner Projection")
                    .define("灾厄旗帜投影袭击中生成", false);
            RaidSpawnOminousBannerProjectionCount = builder.comment("Raid Spawn Ominous Banner Projection Count")
                    .worldRestart()
                    .defineList("灾厄旗帜投影袭击中生成数量",
                            Arrays.asList(0, 0, 0, 0, 0, 0, 0, 1), (i) -> i instanceof Integer);
            //紫沙鬼婆
            RaidSpawnPurpleSandHag = builder.comment("Raid Spawn Purple Sand Hag")
                    .define("紫沙鬼婆袭击中生成", false);
            RaidSpawnPurpleSandHagCount = builder.comment("Raid Spawn Purple Sand Hag Count")
                    .worldRestart()
                    .defineList("紫沙鬼婆袭击中生成数量",
                            Arrays.asList(0, 0, 0, 0, 0, 0, 0, 1), (i) -> i instanceof Integer);
            builder.pop();

            builder.push("Attributes");
            builder.push("Boss");

            BossBaseAttributeCanUseConfig = builder.comment("Boss Base Attribute Can Use Config")
                    .define("Boss基础属性可以被配置影响", true);
            RaidBossCanIncreaseSummonCountBasedOnPlayerCount = builder.comment("Raid Boss Can Increase Summon Count Based On Player Count")
                    .define("袭击类boss可以依靠玩家数量提升生成数量", false);
            RaidBossCanIncreaseSummonCountBasedOnPlayerCountFindReach = builder.comment("Raid Boss Can Increase Summon Count Based On Player Count Find Reach")
                    .defineInRange("袭击类boss可以依靠玩家数量提升生成数量依照范围", 128.0, 0.0, Double.MAX_VALUE);
            RaidBossCanIncreaseSummonCountBasedOnPlayerCountMaxCount = builder.comment("Raid Boss Can Increase Summon Count Based On Player Count Max Count")
                    .defineInRange("袭击类boss可以依靠玩家数量提升生成数量最大数量", 6, 0, Integer.MAX_VALUE);
            RaidBossCanIncreaseSummonCountBasedOnPlayerCountContainAllBoss = builder.comment("Raid Boss Can Increase Summon Count Based On Player Count Contain All Boss")
                    .define("袭击类boss可以依靠玩家数量提升生成数量包含所有boss", true);
            RaidBossCanIncreaseSummonCountBasedOnPlayerCountContainBoss = builder.comment("Raid Boss Can Increase Summon Count Based On Player Count Contain Boss")
                    .define("袭击类boss可以依靠玩家数量提升生成数量包含boss", Lists.newArrayList("jerotesvillage:serpon_combat_executer", "jerotesvillage:ominous_banner_projection"));
            BossHasPercentageDamage = builder.comment("Boss Has Percentage Damage")
                    .define("Boss拥有百分比伤害", Lists.newArrayList("jerotesvillage:variant_zsiein", "jerotesvillage:second_rounder_golem"));
            BossHasDamageCap = builder.comment("Boss Has Damage Cap")
                    .define("Boss拥有限伤", Lists.newArrayList("jerotesvillage:variant_zsiein", "jerotesvillage:second_rounder_golem"));
            BossHasDamageCooldownTick = builder.comment("Boss Has Damage Cooldown Tick")
                    .define("Boss拥有无敌帧", Lists.newArrayList("jerotesvillage:variant_zsiein", "jerotesvillage:second_rounder_golem"));
            //沉降领主
            builder.push("Sediment Lord");
            SedimentLordBossBarColor = builder.comment("Sediment Lord Boss Bar Color")
                    .defineInRange("沉降领主Boss血条颜色", 1, 0, 6);
            SedimentLordBossBarNameColor = builder.comment("Sediment Lord Boss Bar Name Color")
                    .defineInRange("沉降领主Boss血条名字颜色", 0x111f42, 0, Integer.MAX_VALUE);
            SedimentLordMaxHealth = builder.comment("[Boss] Max Health : 170.0")
                    .defineInRange("Sediment Lord Max Health", 170.0, 0.0, Double.MAX_VALUE);
            SedimentLordMeleeDamage = builder.comment("[Boss] Melee Damage : 6.0")
                    .defineInRange("Sediment Lord Melee Damage", 6.0, 0.0, Double.MAX_VALUE);
            SedimentLordArmor = builder.comment("[Boss] Armor : 3.0")
                    .defineInRange("Sediment Lord Armor", 3.0, 0.0, Double.MAX_VALUE);
            SedimentLordMovementSpeed = builder.comment("[Boss] Movement Speed : 0.20")
                    .defineInRange("Sediment Lord Movement Speed", 0.20, 0.0, Double.MAX_VALUE);
            SedimentLordAttackKnockback = builder.comment("[Boss] Attack Knockback : 0.4")
                    .defineInRange("Sediment Lord Attack Knockback", 0.4, 0.0, Double.MAX_VALUE);
            SedimentLordKnockbackResistance = builder.comment("[Boss] Knockback Resistance : 0.5")
                    .defineInRange("Sediment Lord Knockback Resistance", 0.5, 0.0, Double.MAX_VALUE);
            SedimentLordLeadCooldown = builder.comment("[Boss] Lead Cooldown : 30.0")
                    .defineInRange("Sediment Lord Lead Cooldown", 30.0, 0.0, Double.MAX_VALUE);
            SedimentLordDropEquipmentChance = builder.comment("[Boss] Drop Equipment Chance : 0.0")
                    .defineInRange("Sediment Lord Drop Equipment Chance", 0.0, 0.0, Double.MAX_VALUE);
            SedimentLordAttackPercentage = builder.comment("[Boss] Attack Percentage : 5.0")
                    .defineInRange("Sediment Lord Attack Percentage", 5.0, 0.0, Double.MAX_VALUE);
            SedimentLordMagicAttackPercentage = builder.comment("[Boss] Magic Attack Percentage : 5.0")
                    .defineInRange("Sediment Lord Magic Attack Percentage", 5.0, 0.0, Double.MAX_VALUE);
            SedimentLordDamageCap = builder.comment("[Boss] Damage Cap : 22")
                    .defineInRange("Sediment Lord Damage Cap", 22, 0.0, Double.MAX_VALUE);
            SedimentLordDamageCooldownTick = builder.comment("[Boss] Damage Cooldown Tick : 20")
                    .defineInRange("Sediment Lord Damage Cooldown Tick", 20, 0.0, Double.MAX_VALUE);
            builder.pop();
            //繁兴使者
            builder.push("Prosperous Envoy");
            ProsperousEnvoyBossBarColor = builder.comment("Prosperous Envoy Boss Bar Color")
                    .defineInRange("繁兴使者Boss血条颜色", 3, 0, 6);
            ProsperousEnvoyBossBarNameColor = builder.comment("Prosperous Envoy Boss Bar Name Color")
                    .defineInRange("繁兴使者Boss血条名字颜色", 0x738726, 0, Integer.MAX_VALUE);
            ProsperousEnvoyMaxHealth = builder.comment("[Boss] Max Health : 225.0")
                    .defineInRange("Prosperous Envoy Max Health", 225.0, 0.0, Double.MAX_VALUE);
            ProsperousEnvoyMeleeDamage = builder.comment("[Boss] Melee Damage : 12.0")
                    .defineInRange("Prosperous Envoy Melee Damage", 12.0, 0.0, Double.MAX_VALUE);
            ProsperousEnvoyArmor = builder.comment("[Boss] Armor : 12.0")
                    .defineInRange("Prosperous Envoy Armor", 12.0, 0.0, Double.MAX_VALUE);
            ProsperousEnvoyMovementSpeed = builder.comment("[Boss] Movement Speed : 0.26")
                    .defineInRange("Prosperous Envoy Movement Speed", 0.26, 0.0, Double.MAX_VALUE);
            ProsperousEnvoyAttackKnockback = builder.comment("[Boss] Attack Knockback : 1.4")
                    .defineInRange("Prosperous Envoy Attack Knockback", 1.4, 0.0, Double.MAX_VALUE);
            ProsperousEnvoyKnockbackResistance = builder.comment("[Boss] Knockback Resistance : 1.0")
                    .defineInRange("Prosperous Envoy Knockback Resistance", 1.0, 0.0, Double.MAX_VALUE);
            ProsperousEnvoyToughHurtCooldown = builder.comment("[Boss] Tough Hurt Cooldown : 0.75")
                    .defineInRange("Prosperous Envoy Tough Hurt Cooldown", 0.75, 0.0, Double.MAX_VALUE);
            ProsperousEnvoyBreakToughHurtCooldownMultiple = builder.comment("[Boss] Break Tough Hurt Cooldown Multiple : 3")
                    .defineInRange("Prosperous Envoy Break Tough Hurt Cooldown Multiple", 3, 0, Double.MAX_VALUE);
            ProsperousEnvoyBreakToughNeedBaseDamage = builder.comment("[Boss] Break Tough Need Base Damage : 5.0")
                    .defineInRange("Prosperous Envoy Break Tough Need Base Damage", 5.0, 0.0, Double.MAX_VALUE);
            ProsperousEnvoyMeleeAttackCooldown = builder.comment("[Boss] Melee Attack Cooldown : 0.25")
                    .defineInRange("Prosperous Envoy Melee Attack Cooldown", 0.25, 0, Double.MAX_VALUE);
            ProsperousEnvoyProsperousTrampleChance = builder.comment("[Boss] Prosperous Trample Chance : 0.15")
                    .defineInRange("Prosperous Envoy Prosperous Trample Chance", 0.15, 0.0, 1.0);
            ProsperousEnvoyProsperousRushTime = builder.comment("[Boss] Prosperous Rush Time : 4.0")
                    .defineInRange("Prosperous Envoy Prosperous Rush Time", 4.0, 0.0, Double.MAX_VALUE);
            ProsperousEnvoyProsperousRushCooldown = builder.comment("[Boss] Prosperous Rush Cooldown : 12.0")
                    .defineInRange("Prosperous Envoy Prosperous Rush Cooldown", 12.0, 0.0, Double.MAX_VALUE);
            ProsperousEnvoyBreakBlockCooldown = builder.comment("[Boss] Break Block Cooldown : 2.5")
                    .defineInRange("Prosperous Envoy Break Block Cooldown", 2.5, 0.0, Double.MAX_VALUE);
            ProsperousEnvoyAttackPercentage = builder.comment("[Boss] Attack Percentage : 5.0")
                    .defineInRange("Prosperous Envoy Attack Percentage", 5.0, 0.0, Double.MAX_VALUE);
            ProsperousEnvoyMagicAttackPercentage = builder.comment("[Boss] Magic Attack Percentage : 5.0")
                    .defineInRange("Prosperous Envoy Magic Attack Percentage", 5.0, 0.0, Double.MAX_VALUE);
            ProsperousEnvoyDamageCap = builder.comment("[Boss] Damage Cap : 22")
                    .defineInRange("Prosperous Envoy Damage Cap", 22, 0.0, Double.MAX_VALUE);
            ProsperousEnvoyDamageCooldownTick = builder.comment("[Boss] Damage Cooldown Tick : 20")
                    .defineInRange("Prosperous Envoy Damage Cooldown Tick", 20, 0.0, Double.MAX_VALUE);
            builder.pop();
            //灾厄旗帜投影
            builder.push("Ominous Banner Projection");
            OminousBannerProjectionBossBarColor = builder.comment("Ominous Banner Projection Boss Bar Color")
                    .defineInRange("灾厄旗帜投影Boss血条颜色", 2, 0, 6);
            OminousBannerProjectionBossBarNameColor = builder.comment("Ominous Banner Projection Boss Bar Name Color")
                    .defineInRange("灾厄旗帜投影Boss血条名字颜色", 0x262626, 0, Integer.MAX_VALUE);
            OminousBannerProjectionMaxHealth = builder.comment("[Boss] Max Health : 200.0")
                    .defineInRange("Ominous Banner Projection Max Health", 200, 0.0, Double.MAX_VALUE);
            OminousBannerProjectionMeleeDamage = builder.comment("[Boss] Melee Damage : 2.0")
                    .defineInRange("Ominous Banner Projection Melee Damage", 2.0, 0.0, Double.MAX_VALUE);
            OminousBannerProjectionArmor = builder.comment("[Boss] Armor : 0.0")
                    .defineInRange("Ominous Banner Projection Armor", 0.0, 0.0, Double.MAX_VALUE);
            OminousBannerProjectionMovementSpeed = builder.comment("[Boss] Movement Speed : 0.0")
                    .defineInRange("Ominous Banner Projection Movement Speed", 0.0, 0.0, Double.MAX_VALUE);
            OminousBannerProjectionAttackKnockback = builder.comment("[Boss] Attack Knockback : 0.0")
                    .defineInRange("Ominous Banner Projection Attack Knockback", 0.0, 0.0, Double.MAX_VALUE);
            OminousBannerProjectionKnockbackResistance = builder.comment("[Boss] Knockback Resistance : 1.0")
                    .defineInRange("Ominous Banner Projection Knockback Resistance", 1.0, 0.0, Double.MAX_VALUE);
            OminousBannerProjectionRoundTimeMin = builder.comment("[Boss] Round Time Min : 60.0")
                    .defineInRange("Ominous Banner Projection Round Time Min", 60.0, 0.0, Double.MAX_VALUE);
            OminousBannerProjectionRoundTimeMax = builder.comment("[Boss] Round Time Max : 270.0")
                    .defineInRange("Ominous Banner Projection Round Time Max", 270.0, 0.0, Double.MAX_VALUE);
            OminousBannerProjectionMaxRound = builder.comment("[Boss] Max Round : 7")
                    .defineInRange("Ominous Banner Projection Max Round", 7, 0, Integer.MAX_VALUE);
            OminousBannerProjectionPointsBase = builder.comment("[Boss] Points Base : 20.0")
                    .defineInRange("Ominous Banner Projection Points Base", 20.0, 0.0, Double.MAX_VALUE);
            OminousBannerProjectionPointsRoundGrowthMultiple = builder.comment("[Boss] Points Round Growth Multiple : 1.5")
                    .defineInRange("Ominous Banner Projection Points Round Growth Multiple", 1.5, 0.0, Double.MAX_VALUE);
            OminousBannerProjectionAXCrazyCanAppear = builder.comment("[Boss] AX-Crazy Can Appear : false")
                    .define("Ominous Banner Projection AX-Crazy Can Appear", false);
            OminousBannerProjectionBiomeIllagerCanAppearInOtherBiome = builder.comment("[Boss] Biome Illager Can Appear In Other Biome : false")
                    .define("Ominous Banner Projection Biome Illager Can Appear In Other Biome", false);
            OminousBannerProjectionEliteCount = builder.comment("[Boss] Elite Count : 2")
                    .defineInRange("Ominous Banner Projection Elite Count", 2, 0, Integer.MAX_VALUE);
            OminousBannerProjectionAttackPercentage = builder.comment("[Boss] Attack Percentage : 5.0")
                    .defineInRange("Ominous Banner Projection Attack Percentage", 5.0, 0.0, Double.MAX_VALUE);
            OminousBannerProjectionMagicAttackPercentage = builder.comment("[Boss] Magic Attack Percentage : 5.0")
                    .defineInRange("Ominous Banner Projection Magic Attack Percentage", 5.0, 0.0, Double.MAX_VALUE);
            OminousBannerProjectionDamageCap = builder.comment("[Boss] Damage Cap : 20")
                    .defineInRange("Ominous Banner Projection Damage Cap", 20, 0.0, Double.MAX_VALUE);
            OminousBannerProjectionDamageCooldownTick = builder.comment("[Boss] Damage Cooldown Tick : 20")
                    .defineInRange("Ominous Banner Projection Damage Cooldown Tick", 20, 0.0, Double.MAX_VALUE);
            builder.pop();
            //镇山大王
            builder.push("Tyrant of The Mountain Realm");
            TyrantOfTheMountainRealmBossBarColor = builder.comment("Tyrant of The Mountain Realm Boss Bar Color")
                    .defineInRange("镇山大王Boss血条颜色", 2, 0, 6);
            TyrantOfTheMountainRealmBossBarNameColor = builder.comment("Tyrant of The Mountain Realm Boss Bar Name Color")
                    .defineInRange("镇山大王Boss血条名字颜色", 0xaf6b1c, 0, Integer.MAX_VALUE);
            TyrantOfTheMountainRealmMaxHealth = builder.comment("[Boss] Max Health : 360.0")
                    .defineInRange("Tyrant of The Mountain Realm Max Health", 360.0, 0.0, Double.MAX_VALUE);
            TyrantOfTheMountainRealmMeleeDamage = builder.comment("[Boss] Melee Damage : 12.0")
                    .defineInRange("Tyrant of The Mountain Realm Melee Damage", 12.0, 0.0, Double.MAX_VALUE);
            TyrantOfTheMountainRealmArmor = builder.comment("[Boss] Armor : 4.0")
                    .defineInRange("Tyrant of The Mountain Realm Armor", 4.0, 0.0, Double.MAX_VALUE);
            TyrantOfTheMountainRealmMovementSpeed = builder.comment("[Boss] Movement Speed : 0.37")
                    .defineInRange("Tyrant of The Mountain Realm Movement Speed", 0.37, 0.0, Double.MAX_VALUE);
            TyrantOfTheMountainRealmAttackKnockback = builder.comment("[Boss] Attack Knockback : 1.5")
                    .defineInRange("Tyrant of The Mountain Realm Attack Knockback", 1.5, 0.0, Double.MAX_VALUE);
            TyrantOfTheMountainRealmKnockbackResistance = builder.comment("[Boss] Knockback Resistance : 1.0")
                    .defineInRange("Tyrant of The Mountain Realm Knockback Resistance", 1.0, 0.0, Double.MAX_VALUE);
            TyrantOfTheMountainRealmMeleeAttackCooldown = builder.comment("[Boss] Melee Attack Cooldown : 0.5")
                    .defineInRange("Tyrant of The Mountain Realm Melee Attack Cooldown", 0.5, 0.0, Double.MAX_VALUE);
            TyrantOfTheMountainRealmSpellNormalLevel = builder.comment("[Boss] Spell Normal Level : 6")
                    .defineInRange("Tyrant of The Mountain Realm Spell Normal Level", 6, 0, Integer.MAX_VALUE);
            TyrantOfTheMountainRealmFlagSpearCount = builder.comment("[Boss] Flag Spear Count : 3")
                    .defineInRange("Tyrant of The Mountain Realm Flag Spear Count", 3, 0, Integer.MAX_VALUE);
            TyrantOfTheMountainRealmBreakBlockCooldown = builder.comment("[Boss] Break Block Cooldown : 1.5")
                    .defineInRange("Tyrant of The Mountain Realm Break Block Cooldown", 1.5, 0.0, Double.MAX_VALUE);
            TyrantOfTheMountainRealmAttackPercentage = builder.comment("[Boss] Attack Percentage : 10.0")
                    .defineInRange("Tyrant of The Mountain Realm Attack Percentage", 10.0, 0.0, Double.MAX_VALUE);
            TyrantOfTheMountainRealmMagicAttackPercentage = builder.comment("[Boss] Magic Attack Percentage : 10.0")
                    .defineInRange("Tyrant of The Mountain Realm Magic Attack Percentage", 10.0, 0.0, Double.MAX_VALUE);
            TyrantOfTheMountainRealmDamageCap = builder.comment("[Boss] Damage Cap : 22")
                    .defineInRange("Tyrant Of The Mountain Realm Damage Cap", 22, 0.0, Double.MAX_VALUE);
            TyrantOfTheMountainRealmDamageCooldownTick = builder.comment("[Boss] Damage Cooldown Tick : 20")
                    .defineInRange("Tyrant Of The Mountain Realm Damage Cooldown Tick", 20, 0.0, Double.MAX_VALUE);
            builder.pop();
            //蚀龙兽
            builder.push("Corrosiver");
            CorrosiverBossBarColor = builder.comment("Corrosiver Boss Bar Color")
                    .defineInRange("蚀龙兽Boss血条颜色", 6, 0, 6);
            CorrosiverBossBarNameColor = builder.comment("Corrosiver Boss Bar Name Color")
                    .defineInRange("蚀龙兽Boss血条名字颜色", 0x00624e, 0, Integer.MAX_VALUE);
            CorrosiverMaxHealth = builder.comment("[Boss] Max Health : 225.0")
                    .defineInRange("Corrosiver Max Health", 225.0, 0.0, Double.MAX_VALUE);
            CorrosiverMeleeDamage = builder.comment("[Boss] Melee Damage : 12.0")
                    .defineInRange("Corrosiver Melee Damage", 12.0, 0.0, Double.MAX_VALUE);
            CorrosiverArmor = builder.comment("[Boss] Armor : 5.0")
                    .defineInRange("Corrosiver Armor", 5.0, 0.0, Double.MAX_VALUE);
            CorrosiverMovementSpeed = builder.comment("[Boss] Movement Speed : 0.36")
                    .defineInRange("Corrosiver Movement Speed", 0.36, 0.0, Double.MAX_VALUE);
            CorrosiverAttackKnockback = builder.comment("[Boss] Attack Knockback : 0.6")
                    .defineInRange("Corrosiver Attack Knockback", 0.6, 0.0, Double.MAX_VALUE);
            CorrosiverKnockbackResistance = builder.comment("[Boss] Knockback Resistance : 1.0")
                    .defineInRange("Corrosiver Knockback Resistance", 1.0, 0.0, Double.MAX_VALUE);
            CorrosiverToughHurtCooldown = builder.comment("[Boss] Tough Hurt Cooldown : 2.0")
                    .defineInRange("Corrosiver Tough Hurt Cooldown", 2.0, 0.0, Double.MAX_VALUE);
            CorrosiverBreakToughHurtCooldownMultiple = builder.comment("[Boss] Break Tough Hurt Cooldown Multiple : 3")
                    .defineInRange("Corrosiver Break Tough Hurt Cooldown Multiple", 3, 0, Double.MAX_VALUE);
            CorrosiverBreakToughNeedBaseDamage = builder.comment("[Boss] Break Tough Need Base Damage : 6.0")
                    .defineInRange("Corrosiver Break Tough Need Base Damage", 6.0, 0.0, Double.MAX_VALUE);
            CorrosiverMeleeAttackCooldown = builder.comment("[Boss] Melee Attack Cooldown : 0.25")
                    .defineInRange("Corrosiver Melee Attack Cooldown", 0.25, 0.0, Double.MAX_VALUE);
            CorrosiverSpellCooldown = builder.comment("[Boss] Spell Cooldown : 12.0")
                    .defineInRange("Corrosiver Spell Cooldown", 12.0, 0.0, Double.MAX_VALUE);
            CorrosiverSpellNormalLevel = builder.comment("[Boss] Spell Normal Level : 5")
                    .defineInRange("Corrosiver Spell Normal Level", 5, 0, Integer.MAX_VALUE);
            CorrosiverColdAbsorptionPercentage = builder.comment("[Boss] Cold Absorption Percentage : 100.0")
                    .defineInRange("Corrosiver Cold Absorption Percentage", 100.0, 0.0, Double.MAX_VALUE);
            CorrosiverBreakBlockCooldown = builder.comment("[Boss] Break Block Cooldown : 2.5")
                    .defineInRange("Corrosiver Break Block Cooldown", 2.5, 0.0, Double.MAX_VALUE);
            CorrosiverAttackPercentage = builder.comment("[Boss] Attack Percentage : 8.0")
                    .defineInRange("Corrosiver Attack Percentage", 8.0, 0.0, Double.MAX_VALUE);
            CorrosiverMagicAttackPercentage = builder.comment("[Boss] Magic Attack Percentage : 8.0")
                    .defineInRange("Corrosiver Magic Attack Percentage", 8.0, 0.0, Double.MAX_VALUE);
            CorrosiverDamageCap = builder.comment("[Boss] Damage Cap : 18")
                    .defineInRange("Corrosiver Damage Cap", 18, 0.0, Double.MAX_VALUE);
            CorrosiverDamageCooldownTick = builder.comment("[Boss] Damage Cooldown Tick : 20")
                    .defineInRange("Corrosiver Damage Cooldown Tick", 20, 0.0, Double.MAX_VALUE);
            builder.pop();
            //紫沙鬼婆
            builder.push("Purple Sand Hag");
            PurpleSandHagBossBarColor = builder.comment("Purple Sand Hag Boss Bar Color")
                    .defineInRange("紫沙鬼婆Boss血条颜色", 5, 0, 6);
            PurpleSandHagBossBarNameColor = builder.comment("Purple Sand Hag Boss Bar Name Color")
                    .defineInRange("紫沙鬼婆Boss血条名字颜色", 0x1b003a, 0, Integer.MAX_VALUE);
            PurpleSandHagMaxHealth = builder.comment("[Boss] Max Health : 165.0")
                    .defineInRange("Purple Sand Hag Max Health", 165.0, 0.0, Double.MAX_VALUE);
            PurpleSandHagMeleeDamage = builder.comment("[Boss] Melee Damage : 13.0")
                    .defineInRange("Purple Sand Hag Melee Damage", 13.0, 0.0, Double.MAX_VALUE);
            PurpleSandHagArmor = builder.comment("[Boss] Armor : 15.0")
                    .defineInRange("Purple Sand Hag Armor", 15.0, 0.0, Double.MAX_VALUE);
            PurpleSandHagMovementSpeed = builder.comment("[Boss] Movement Speed : 0.4")
                    .defineInRange("Purple Sand Hag Movement Speed", 0.4, 0.0, Double.MAX_VALUE);
            PurpleSandHagAttackKnockback = builder.comment("[Boss] Attack Knockback : 0.0")
                    .defineInRange("Purple Sand Hag Attack Knockback", 0.0, 0.0, Double.MAX_VALUE);
            PurpleSandHagKnockbackResistance = builder.comment("[Boss] Knockback Resistance : 0.0")
                    .defineInRange("Purple Sand Hag Knockback Resistance", 0.0, 0.0, Double.MAX_VALUE);
            PurpleSandHagMainSpellCooldown = builder.comment("[Boss] Main Spell Cooldown : 6.0")
                    .defineInRange("Purple Sand Hag Main Spell Cooldown", 6.0, 0.0, Double.MAX_VALUE);
            PurpleSandHagOtherSpellCooldown = builder.comment("[Boss] Other Spell Cooldown : 6.0")
                    .defineInRange("Purple Sand Hag Other Spell Cooldown", 6.0, 0.0, Double.MAX_VALUE);
            PurpleSandHagSpellNormalLevel = builder.comment("[Boss] Spell Normal Level : 5")
                    .defineInRange("Purple Sand Hag Spell Normal Level", 5, 0, Integer.MAX_VALUE);
            PurpleSandHagAttackPercentage = builder.comment("[Boss] Attack Percentage : 5.0")
                    .defineInRange("Purple Sand Hag Attack Percentage", 5.0, 0.0, Double.MAX_VALUE);
            PurpleSandHagMagicAttackPercentage = builder.comment("[Boss] Magic Attack Percentage : 10.0")
                    .defineInRange("Purple Sand Hag Magic Attack Percentage", 10.0, 0.0, Double.MAX_VALUE);
            PurpleSandHagDamageCap = builder.comment("[Boss] Damage Cap : 18")
                    .defineInRange("Purple Sand Hag Damage Cap", 18, 0.0, Double.MAX_VALUE);
            PurpleSandHagDamageCooldownTick = builder.comment("[Boss] Damage Cooldown Tick : 20")
                    .defineInRange("Purple Sand Hag Damage Cooldown Tick", 20, 0.0, Double.MAX_VALUE);
            builder.pop();
            //宝石恶鳞
            builder.push("Gemstone Malignasaur");
            GemstoneMalignasaurBossBarColor = builder.comment("Gemstone Malignasaur Boss Bar Color")
                    .defineInRange("宝石恶鳞Boss血条颜色", 1, 0, 6);
            GemstoneMalignasaurBossBarNameColor = builder.comment("Gemstone Malignasaur Boss Bar Name Color")
                    .defineInRange("宝石恶鳞Boss血条名字颜色", 0x0000a8, 0, Integer.MAX_VALUE);
            GemstoneMalignasaurMaxHealth = builder.comment("[Boss] Max Health : 408.0")
                    .defineInRange("Gemstone Malignasaur Max Health", 408.0, 0.0, Double.MAX_VALUE);
            GemstoneMalignasaurMeleeDamage = builder.comment("[Boss] Melee Damage : 18.0")
                    .defineInRange("Gemstone Malignasaur Melee Damage", 18.0, 0.0, Double.MAX_VALUE);
            GemstoneMalignasaurArmor = builder.comment("[Boss] Armor : 4.0")
                    .defineInRange("Gemstone Malignasaur Armor", 4.0, 0.0, Double.MAX_VALUE);
            GemstoneMalignasaurArmorGemstoneArmor = builder.comment("[Boss] Armor Gemstone Armor : 20.0")
                    .defineInRange("Gemstone Malignasaur Armor Gemstone Armor", 20.0, 0.0, Double.MAX_VALUE);
            GemstoneMalignasaurMovementSpeed = builder.comment("[Boss] Movement Speed : 1.3")
                    .defineInRange("Gemstone Malignasaur Movement Speed", 1.3, 0.0, Double.MAX_VALUE);
            GemstoneMalignasaurMovementSpeedGemstoneArmor = builder.comment("[Boss] Movement Speed Gemstone Armor : 1.4")
                    .defineInRange("Gemstone Malignasaur Movement Speed Gemstone Armor", 1.4, 0.0, Double.MAX_VALUE);
            GemstoneMalignasaurAttackKnockback = builder.comment("[Boss] Attack Knockback : 2.8")
                    .defineInRange("Gemstone Malignasaur Attack Knockback", 2.8, 0.0, Double.MAX_VALUE);
            GemstoneMalignasaurKnockbackResistance = builder.comment("[Boss] Knockback Resistance : 1.0")
                    .defineInRange("Gemstone Malignasaur Knockback Resistance", 1.0, 0.0, Double.MAX_VALUE);
            GemstoneMalignasaurToughHurtCooldown = builder.comment("[Boss] Tough Hurt Cooldown : 1.0")
                    .defineInRange("Gemstone Malignasaur Tough Hurt Cooldown", 1.0, 0.0, Double.MAX_VALUE);
            GemstoneMalignasaurBreakToughHurtCooldownMultiple = builder.comment("[Boss] Break Tough Hurt Cooldown Multiple : 3")
                    .defineInRange("Gemstone Malignasaur Break Tough Hurt Cooldown Multiple", 3, 0.0, Double.MAX_VALUE);
            GemstoneMalignasaurBreakToughNeedBaseDamage = builder.comment("[Boss] Break Tough Need Base Damage : 6.0")
                    .defineInRange("Gemstone Malignasaur Break Tough Need Base Damage", 6.0, 0.0, Double.MAX_VALUE);
            GemstoneMalignasaurMeleeAttackCooldown = builder.comment("[Boss] Melee Attack Cooldown : 0.25")
                    .defineInRange("Bright Land Beast Melee Attack Cooldown", 0.25, 0.0, Double.MAX_VALUE);
            GemstoneMalignasaurJumpAttackLevel = builder.comment("[Boss] Jump Attack Level : 3")
                    .defineInRange("Gemstone Malignasaur Jump Attack Level", 3, 0, Integer.MAX_VALUE);
            GemstoneMalignasaurJumpAttackCooldown = builder.comment("[Boss] Jump Attack Cooldown : 18")
                    .defineInRange("Gemstone Malignasaur Jump Attack Cooldown", 18, 0.0, Double.MAX_VALUE);
            GemstoneMalignasaurSpellNormalLevel = builder.comment("[Boss] Spell Normal Level : 4")
                    .defineInRange("Gemstone Malignasaur Spell Normal Level", 4, 0, Integer.MAX_VALUE);
            GemstoneMalignasaurBreathCooldown = builder.comment("[Boss] Breath Cooldown : 30.0")
                    .defineInRange("Gemstone Malignasaur Breath Cooldown", 30.0, 0.0, Double.MAX_VALUE);
            GemstoneMalignasaurControlCooldown = builder.comment("[Boss] Control Cooldown : 60.0")
                    .defineInRange("Gemstone Malignasaur Control Cooldown", 60.0, 0.0, Double.MAX_VALUE);
            GemstoneMalignasaurAngrySpellCooldown = builder.comment("[Boss] Angry Spell Cooldown : 120.0")
                    .defineInRange("Gemstone Malignasaur Angry Spell Cooldown", 120.0, 0.0, Double.MAX_VALUE);
            GemstoneMalignasaurFakeDamageCap = builder.comment("[Boss] Fake Damage Cap : 16.0")
                    .defineInRange("Gemstone Malignasaur Fake Damage Cap", 16.0, 0.0, Double.MAX_VALUE);
            GemstoneMalignasaurBreakFakeDamageCap = builder.comment("[Boss] Break Fake Damage Cap : 1200.0")
                    .defineInRange("Gemstone Malignasaur Break Fake Damage Cap", 1200.0, 0.0, Double.MAX_VALUE);
            GemstoneMalignasaurBreakBlockCooldown = builder.comment("[Boss] Break Block Cooldown : 1.5")
                    .defineInRange("Gemstone Malignasaur Break Block Cooldown", 1.5, 0.0, Double.MAX_VALUE);
            GemstoneMalignasaurAttackPercentage = builder.comment("[Boss] Attack Percentage : 8.0")
                    .defineInRange("Gemstone Malignasaur Attack Percentage", 8.0, 0.0, Double.MAX_VALUE);
            GemstoneMalignasaurMagicAttackPercentage = builder.comment("[Boss] Magic Attack Percentage : 8.0")
                    .defineInRange("Gemstone Malignasaur Magic Attack Percentage", 8.0, 0.0, Double.MAX_VALUE);
            GemstoneMalignasaurDamageCap = builder.comment("[Boss] Damage Cap : 24")
                    .defineInRange("Gemstone Malignasaur Damage Cap", 24, 0.0, Double.MAX_VALUE);
            GemstoneMalignasaurDamageCooldownTick = builder.comment("[Boss] Damage Cooldown Tick : 20")
                    .defineInRange("Gemstone Malignasaur Damage Cooldown Tick", 20, 0.0, Double.MAX_VALUE);
            builder.pop();
            //明路巨兽
            builder.push("Bright Land Beast");
            BrightLandBeastBossBarColor = builder.comment("Bright Land Beast Boss Bar Color")
                    .defineInRange("明路巨兽Boss血条颜色", 2, 0, 6);
            BrightLandBeastBossBarNameColor = builder.comment("Bright Land Beast Boss Bar Name Color")
                    .defineInRange("明路巨兽Boss血条名字颜色", 0x0c3f04, 0, Integer.MAX_VALUE);
            BrightLandBeastMaxHealth = builder.comment("[Boss] Max Health : 650.0")
                    .defineInRange("Bright Land Beast Max Health", 650.0, 0.0, Double.MAX_VALUE);
            BrightLandBeastMeleeDamage = builder.comment("[Boss] Melee Damage : 40.0")
                    .defineInRange("Bright Land Beast Melee Damage", 40.0, 0.0, Double.MAX_VALUE);
            BrightLandBeastArmor = builder.comment("[Boss] Armor : 4.0")
                    .defineInRange("Bright Land Beast Armor", 4.0, 0.0, Double.MAX_VALUE);
            BrightLandBeastMovementSpeed = builder.comment("[Boss] Movement Speed : 0.32")
                    .defineInRange("Bright Land Beast Movement Speed", 0.32, 0.0, Double.MAX_VALUE);
            BrightLandBeastAttackKnockback = builder.comment("[Boss] Attack Knockback : 4.0")
                    .defineInRange("Bright Land Beast Attack Knockback", 4.0, 0.0, Double.MAX_VALUE);
            BrightLandBeastKnockbackResistance = builder.comment("[Boss] Knockback Resistance : 1.0")
                    .defineInRange("Bright Land Beast Knockback Resistance", 1.0, 0.0, Double.MAX_VALUE);
            BrightLandBeastToughHurtCooldown = builder.comment("[Boss] Tough Hurt Cooldown : 1.0")
                    .defineInRange("Bright Land Beast Tough Hurt Cooldown", 1.0, 0.0, Double.MAX_VALUE);
            BrightLandBeastBreakToughHurtCooldownMultiple = builder.comment("[Boss] Break Tough Hurt Cooldown Multiple : 3")
                    .defineInRange("Bright Land Beast Break Tough Hurt Cooldown Multiple", 3, 0, Double.MAX_VALUE);
            BrightLandBeastBreakToughNeedBaseDamage = builder.comment("[Boss] Break Tough Need Base Damage : 6.0")
                    .defineInRange("Bright Land Beast Break Tough Need Base Damage", 6.0, 0.0, Double.MAX_VALUE);
            BrightLandBeastMeleeAttackCooldown = builder.comment("[Boss] Melee Attack Cooldown : 0.5")
                    .defineInRange("Bright Land Beast Melee Attack Cooldown", 0.5, 0.0, Double.MAX_VALUE);
            BrightLandBeastQuakeCooldown = builder.comment("[Boss] Quake Cooldown : 32.0")
                    .defineInRange("Bright Land Beast Quake Cooldown", 32.0, 0.0, Double.MAX_VALUE);
            BrightLandBeastQuakeBaseDamage = builder.comment("[Boss] Quake Base Damage : 40.0")
                    .defineInRange("Bright Land Beast Quake Base Damage", 40.0, 0.0, Double.MAX_VALUE);
            BrightLandBeastQuakeHealthMultipleDamage = builder.comment("[Boss] Quake Health Multiple Damage : 0.6666667")
                    .defineInRange("Bright Land Beast Quake Health Multiple Damage", 1/15d, 0.0, Double.MAX_VALUE);
            BrightLandBeastBreakBlockCooldown = builder.comment("[Boss] Break Block Cooldown : 1")
                    .defineInRange("Bright Land Beast Break Block Cooldown", 1, 0.0, Double.MAX_VALUE);
            BrightLandBeastAttackPercentage = builder.comment("[Boss] Attack Percentage : 10.0")
                    .defineInRange("Bright Land Beast Attack Percentage", 10.0, 0.0, Double.MAX_VALUE);
            BrightLandBeastMagicAttackPercentage = builder.comment("[Boss] Magic Attack Percentage : 5.0")
                    .defineInRange("Bright Land Beast Magic Attack Percentage", 5.0, 0.0, Double.MAX_VALUE);
            BrightLandBeastDamageCap = builder.comment("[Boss] Damage Cap : 28")
                    .defineInRange("Bright Land Beast Damage Cap", 28, 0.0, Double.MAX_VALUE);
            BrightLandBeastDamageCooldownTick = builder.comment("[Boss] Damage Cooldown Tick : 20")
                    .defineInRange("Bright Land Beast Damage Cooldown Tick", 20, 0.0, Double.MAX_VALUE);
            builder.pop();
            //蛇龙执战者
            builder.push("Serpon Combat Executer");
            SerponCombatExecuterBossBarColor = builder.comment("Serpon Combat Executer Boss Bar Color")
                    .defineInRange("蛇龙执战者Boss血条颜色", 4, 0, 6);
            SerponCombatExecuterBossBarNameColor = builder.comment("Serpon Combat Executer Boss Bar Name Color")
                    .defineInRange("蛇龙执战者Boss血条名字颜色", 0x2c1700, 0, Integer.MAX_VALUE);
            SerponCombatExecuterMaxHealth = builder.comment("[Boss] Max Health : 270.0")
                    .defineInRange("Serpon Combat Executer Max Health", 270.0, 0.0, Double.MAX_VALUE);
            SerponCombatExecuterMeleeDamage = builder.comment("[Boss] Melee Damage : 8.0")
                    .defineInRange("Serpon Combat Executer Melee Damage", 8.0, 0.0, Double.MAX_VALUE);
            SerponCombatExecuterMeleeDamageFury = builder.comment("[Boss] Melee Damage Fury : 13.0")
                    .defineInRange("Serpon Combat Executer Melee Damage Fury", 12.0, 0.0, Double.MAX_VALUE);
            SerponCombatExecuterArmor = builder.comment("[Boss] Armor : 5.0")
                    .defineInRange("Serpon Combat Executer Armor", 5.0, 0.0, Double.MAX_VALUE);
            SerponCombatExecuterArmorFury = builder.comment("[Boss] Armor Fury : 18.0")
                    .defineInRange("Serpon Combat Executer Armor Fury", 18.0, 0.0, Double.MAX_VALUE);
            SerponCombatExecuterMovementSpeed = builder.comment("[Boss] Movement Speed : 0.40")
                    .defineInRange("Serpon Combat Executer Movement Speed", 0.40, 0.0, Double.MAX_VALUE);
            SerponCombatExecuterFlyingSpeed = builder.comment("[Boss] Flying Speed : 0.50")
                    .defineInRange("Serpon Combat Executer Flying Speed", 0.50, 0.0, Double.MAX_VALUE);
            SerponCombatExecuterAttackKnockback = builder.comment("[Boss] Attack Knockback : 0.5")
                    .defineInRange("Serpon Combat Executer Attack Knockback", 0.5, 0.0, Double.MAX_VALUE);
            SerponCombatExecuterKnockbackResistance = builder.comment("[Boss] Knockback Resistance : 0.8")
                    .defineInRange("Serpon Combat Executer Knockback Resistance", 0.8, 0.0, Double.MAX_VALUE);
            SerponCombatExecuterKnockbackResistanceFury = builder.comment("[Boss] Knockback Resistance Fury : 1.0")
                    .defineInRange("Serpon Combat Executer Knockback Resistance Fury", 1.0, 0.0, Double.MAX_VALUE);
            SerponCombatExecuterDropEquipmentChance = builder.comment("[Boss] Drop Equipment Chance : 0.0")
                    .defineInRange("Serpon Combat Executer Drop Equipment Chance", 0.0, 0.0, Double.MAX_VALUE);
            SerponCombatExecuterRoundTimeMin = builder.comment("[Boss] Round Time Min : 90.0")
                    .defineInRange("Serpon Combat Executer Round Time Min", 90.0, 0.0, Double.MAX_VALUE);
            SerponCombatExecuterRoundTimeMax = builder.comment("[Boss] Round Time Max : 420.0")
                    .defineInRange("Serpon Combat Executer Round Time Max", 420.0, 0.0, Double.MAX_VALUE);
            SerponCombatExecuterFlameSlashChance = builder.comment("[Boss] Flame Slash Chance : 0.1")
                    .defineInRange("Serpon Combat Executer Flame Slash Chance", 0.1, 0.0, 1.0);
            SerponCombatExecuterWhirlwindSlashChance = builder.comment("[Boss] Whirlwind Slash Chance : 0.1")
                    .defineInRange("Serpon Combat Executer Whirlwind Slash Chance", 0.1, 0.0, 1.0);
            SerponCombatExecuterFireAbsorptionPercentage = builder.comment("[Boss] Fire Absorption Percentage : 100.0")
                    .defineInRange("Serpon Combat Executer Fire Absorption Percentage", 100.0, 0.0, Double.MAX_VALUE);
            SerponCombatExecuterSpellNormalLevel = builder.comment("[Boss] Spell Normal Level : 6")
                    .defineInRange("Serpon Combat Executer Spell Level", 6, 0, Integer.MAX_VALUE);
            SerponCombatExecuterSpellCooldown = builder.comment("[Boss] Spell Cooldown : 12.0")
                    .defineInRange("Serpon Combat Executer Spell Cooldown", 12.0, 0.0, Double.MAX_VALUE);
            SerponCombatExecuterAttackPercentage = builder.comment("[Boss] Attack Percentage : 10.0")
                    .defineInRange("Serpon Combat Executer Attack Percentage", 10.0, 0.0, Double.MAX_VALUE);
            SerponCombatExecuterMagicAttackPercentage = builder.comment("[Boss] Magic Attack Percentage : 10.0")
                    .defineInRange("Serpon Combat Executer Magic Attack Percentage", 10.0, 0.0, Double.MAX_VALUE);
            SerponCombatExecuterDamageCap = builder.comment("[Boss] Damage Cap : 20")
                    .defineInRange("Serpon Combat Executer Damage Cap", 20, 0.0, Double.MAX_VALUE);
            SerponCombatExecuterDamageCooldownTick = builder.comment("[Boss] Damage Cooldown Tick : 20")
                    .defineInRange("Serpon Combat Executer Damage Cooldown Tick", 20, 0.0, Double.MAX_VALUE);
            builder.pop();
            //盘罗仙蔓
            builder.push("Celestial Coilvine");
            CelestialCoilvineBossBarColor = builder.comment("Celestial Coilvine Boss Bar Color")
                    .defineInRange("盘罗仙蔓Boss血条颜色", 3, 0, 6);
            CelestialCoilvineBossBarNameColor = builder.comment("Celestial Coilvine Boss Bar Name Color")
                    .defineInRange("盘罗仙蔓Boss血条名字颜色", 0x65ae26, 0, Integer.MAX_VALUE);
            CelestialCoilvineMaxHealth = builder.comment("[Boss] Max Health : 420.0")
                    .defineInRange("Celestial Coilvine Max Health", 420.0, 0.0, Double.MAX_VALUE);
            CelestialCoilvineMeleeDamage = builder.comment("[Boss] Melee Damage : 18.0")
                    .defineInRange("Celestial Coilvine Melee Damage", 18.0, 0.0, Double.MAX_VALUE);
            CelestialCoilvineArmor = builder.comment("[Boss] Armor : 15.0")
                    .defineInRange("Celestial Coilvine Armor", 15.0, 0.0, Double.MAX_VALUE);
            CelestialCoilvineMovementSpeed = builder.comment("[Boss] Movement Speed : 0.0")
                    .defineInRange("Celestial Coilvine Movement Speed", 0.0, 0.0, Double.MAX_VALUE);
            CelestialCoilvineAttackKnockback = builder.comment("[Boss] Attack Knockback : 2.0")
                    .defineInRange("Celestial Coilvine Attack Knockback", 2.0, 0.0, Double.MAX_VALUE);
            CelestialCoilvineKnockbackResistance = builder.comment("[Boss] Knockback Resistance : 1.0")
                    .defineInRange("Celestial Coilvine Knockback Resistance", 1.0, 0.0, Double.MAX_VALUE);
            CelestialCoilvineSpellNormalLevel = builder.comment("[Boss] Spell Normal Level : 6")
                    .defineInRange("Celestial Coilvine Spell Normal Level", 6, 0, Integer.MAX_VALUE);
            CelestialCoilvineAttackPercentage = builder.comment("[Boss] Sword Attack Percentage : 8.0")
                    .defineInRange("Celestial Coilvine Sword Attack Percentage", 8.0, 0.0, Double.MAX_VALUE);
            CelestialCoilvineMagicAttackPercentage = builder.comment("[Boss] Magic Attack Percentage : 12.0")
                    .defineInRange("Celestial Coilvine Magic Attack Percentage", 12.0, 0.0, Double.MAX_VALUE);
            CelestialCoilvineDamageCap = builder.comment("[Boss] Damage Cap : 25")
                    .defineInRange("Celestial Coilvine Damage Cap", 25, 0.0, Double.MAX_VALUE);
            CelestialCoilvineDamageCooldownTick = builder.comment("[Boss] Damage Cooldown Tick : 20")
                    .defineInRange("Celestial Coilvine Damage Cooldown Tick", 20, 0.0, Double.MAX_VALUE);
            builder.pop();
            //二轮傀儡
            builder.push("Second Rounder Golem");
            SecondRounderGolemBossBarColor = builder.comment("Second Rounder Golem Boss Bar Color")
                    .defineInRange("二轮傀儡Boss血条颜色", 0, 0, 6);
            SecondRounderGolemBossBarNameColor = builder.comment("Second Rounder Golem Boss Bar Name Color")
                    .defineInRange("二轮傀儡Boss血条名字颜色", 0x81150d, 0, Integer.MAX_VALUE);
            SecondRounderGolemMaxHealth = builder.comment("[Boss] Max Health : 436.0")
                    .defineInRange("Second Rounder Golem Max Health", 436.0, 0.0, Double.MAX_VALUE);
            SecondRounderGolemMeleeDamage = builder.comment("[Boss] Melee Damage : 36.0")
                    .defineInRange("Second Rounder Golem Melee Damage", 36.0, 0.0, Double.MAX_VALUE);
            SecondRounderGolemMeleeDamageUseSword = builder.comment("[Boss] Melee Damage Use Sword : 48.0")
                    .defineInRange("Second Rounder Golem Melee Damage Use Sword", 48.0, 0.0, Double.MAX_VALUE);
            SecondRounderGolemArmor = builder.comment("[Boss] Armor : 20.0")
                    .defineInRange("Second Rounder Golem Armor", 20.0, 0.0, Double.MAX_VALUE);
            SecondRounderGolemMovementSpeed = builder.comment("[Boss] Movement Speed : 0.30")
                    .defineInRange("Second Rounder Golem Movement Speed", 0.28, 0.0, Double.MAX_VALUE);
            SecondRounderGolemMovementSpeedUseSword = builder.comment("[Boss] Movement Speed Use Sword : 0.30")
                    .defineInRange("Second Rounder Golem Movement Speed Use Sword", 0.28, 0.0, Double.MAX_VALUE);
            SecondRounderGolemAttackKnockback = builder.comment("[Boss] Attack Knockback : 3.0")
                    .defineInRange("Second Rounder Golem Attack Knockback", 3.0, 0.0, Double.MAX_VALUE);
            SecondRounderGolemKnockbackResistance = builder.comment("[Boss] Knockback Resistance : 1.0")
                    .defineInRange("Second Rounder Golem Knockback Resistance", 1.0, 0.0, Double.MAX_VALUE);
            SecondRounderGolemArmorUseSword = builder.comment("[Boss] Armor Use Sword : 26.0")
                    .defineInRange("Second Rounder Golem Armor Use Sword", 26.0, 0.0, Double.MAX_VALUE);
            SecondRounderGolemSpellNormalLevel = builder.comment("[Boss] Spell Normal Level : 5")
                    .defineInRange("Second Rounder Golem Spell Normal Level", 5, 0, Integer.MAX_VALUE);
            SecondRounderGolemBreathCooldown = builder.comment("[Boss] Breath Cooldown : 24.0")
                    .defineInRange("Second Rounder Golem Breath Cool down", 24.0, 0.0, Double.MAX_VALUE);
            SecondRounderGolemIntenseBombingCooldown = builder.comment("[Boss] Intense Bombing Cooldown : 60.0")
                    .defineInRange("Second Rounder Golem Intense Bombing Cooldown", 60.0, 0.0, Double.MAX_VALUE);
            SecondRounderGolemFireAbsorptionPercentage = builder.comment("[Boss] Fire Absorption Percentage : 100.0")
                    .defineInRange("Second Rounder Golem Fire Absorption Percentage", 100.0, 0.0, Double.MAX_VALUE);
            SecondRounderGolemNotAttackedStartHealCooldown = builder.comment("[Boss] Not Attacked Start Heal Cooldown : 20.0")
                    .defineInRange("Second Rounder Golem Not Attacked Start Heal Cooldown", 20.0, 0.0, Double.MAX_VALUE);
            SecondRounderGolemNormalMerorToughnessNonMagical = builder.comment("[Boss] Normal Meror Toughness Non Magical : 4.0")
                    .defineInRange("Second Rounder Golem Normal Meror Toughness Non Magical", 4.0, 0.0, Double.MAX_VALUE);
            SecondRounderGolemNormalMerorToughnessMagical = builder.comment("[Boss] Normal Meror Toughness Magical : 6.0")
                    .defineInRange("Second Rounder Golem Normal Meror Toughness Magical", 6.0, 0.0, Double.MAX_VALUE);
            SecondRounderGolemPoisonFireCloakTime = builder.comment("[Boss] Poison Fire Cloak Time : 18")
                    .defineInRange("Second Rounder Golem Poison Fire Cloak Time", 18, 0.0, Double.MAX_VALUE);
            SecondRounderGolemPoisonFireCloakFireTime = builder.comment("[Boss] Poison Fire Cloak Fire Time : 12")
                    .defineInRange("Second Rounder Golem Poison Fire Cloak Fire Time", 12, 0, Integer.MAX_VALUE);
            SecondRounderGolemPoisonFireCloakPotionLevel = builder.comment("[Boss] Poison Fire Cloak Potion Level : 3")
                    .defineInRange("Second Rounder Golem Poison Fire Cloak Potion Level", 3, 0, Integer.MAX_VALUE);
            SecondRounderGolemSagesCooldown = builder.comment("[Boss] Sages Cooldown : 24")
                    .defineInRange("Second Rounder Golem Sages Cooldown", 24, 0.0, Double.MAX_VALUE);
            SecondRounderGolemBreakBlockCooldown = builder.comment("[Boss] Break Block Cooldown : 2")
                    .defineInRange("Second Rounder Golem Break Block Cooldown", 2, 0.0, Double.MAX_VALUE);
            SecondRounderGolemAttackPercentage = builder.comment("[Boss] Attack Percentage : 10.0")
                    .defineInRange("Second Rounder Golem Attack Percentage", 10.0, 0.0, Double.MAX_VALUE);
            SecondRounderGolemSwordAttackPercentage = builder.comment("[Boss] Sword Attack Percentage : 15.0")
                    .defineInRange("Second Rounder Golem Sword Attack Percentage", 15.0, 0.0, Double.MAX_VALUE);
            SecondRounderGolemMagicAttackPercentage = builder.comment("[Boss] Magic Attack Percentage : 12.0")
                    .defineInRange("Second Rounder Golem Magic Attack Percentage", 12.0, 0.0, Double.MAX_VALUE);
            SecondRounderGolemDamageCap = builder.comment("[Boss] Damage Cap : 26")
                    .defineInRange("Second Rounder Golem Damage Cap", 26, 0.0, Double.MAX_VALUE);
            SecondRounderGolemDamageCooldownTick = builder.comment("[Boss] Damage Cooldown Tick : 20")
                    .defineInRange("Second Rounder Golem Damage Cooldown Tick", 20, 0.0, Double.MAX_VALUE);
            builder.pop();

            //泽林变体
            builder.push("Variant Zsiein");
            builder.pop();
            builder.pop();
            builder.pop();
        }
    }

    public static class ClientConfig {
        public ClientConfig(ForgeConfigSpec.Builder builder) {
        }
    }
}