package com.jerotes.jerotesvillage.init;

import com.jerotes.jerotesvillage.config.OtherMainConfig;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;

import java.util.ArrayList;
import java.util.List;

public class JerotesVillageRaider {
    public static final List<Raid.RaiderType> NEW_RAID_MEMBERS = new ArrayList<>();

    public static void addRaiders() {
        //探险者
        if (OtherMainConfig.RaidSpawnExplorer) {
            addWaves("EXPLORER", JerotesVillageEntityType.EXPLORER.get(), OtherMainConfig.RaidSpawnExplorerCount);
        }
        //行刑者
        if (OtherMainConfig.RaidSpawnExecutioner) {
            addWaves("EXECUTIONER", JerotesVillageEntityType.EXECUTIONER.get(), OtherMainConfig.RaidSpawnExecutionerCount);
        }
        //绘图者
        if (OtherMainConfig.RaidSpawnMapmaker) {
            addWaves("MAPMAKER", JerotesVillageEntityType.MAPMAKER.get(), OtherMainConfig.RaidSpawnMapmakerCount);
        }
        //背叛者
        if (OtherMainConfig.RaidSpawnDefector) {
            addWaves("DEFECTOR", JerotesVillageEntityType.DEFECTOR.get(), OtherMainConfig.RaidSpawnDefectorCount);
        }
        //执旗者
        if (OtherMainConfig.RaidSpawnBannerBearer) {
            addWaves("BANNER_BEARER", JerotesVillageEntityType.BANNER_BEARER.get(), OtherMainConfig.RaidSpawnBannerBearerCount);
        }
        //爆破者
        if (OtherMainConfig.RaidSpawnBlaster) {
            addWaves("BLASTER", JerotesVillageEntityType.BLASTER.get(), OtherMainConfig.RaidSpawnBlasterCount);
        }
        //吹号者
        if (OtherMainConfig.RaidSpawnTrumpeter) {
            addWaves("TRUMPETER", JerotesVillageEntityType.TRUMPETER.get(), OtherMainConfig.RaidSpawnTrumpeterCount);
        }
        //传送者
        if (OtherMainConfig.RaidSpawnTeleporter) {
            addWaves("TELEPORTER", JerotesVillageEntityType.TELEPORTER.get(), OtherMainConfig.RaidSpawnTeleporterCount);
        }
        //旋风者
        if (OtherMainConfig.RaidSpawnCycloner) {
            addWaves("CYCLONER", JerotesVillageEntityType.CYCLONER.get(), OtherMainConfig.RaidSpawnCyclonerCount);
        }
        //掷枪者
        if (OtherMainConfig.RaidSpawnJavelinThrower) {
            addWaves("JAVELIN_THROWER", JerotesVillageEntityType.JAVELIN_THROWER.get(), OtherMainConfig.RaidSpawnJavelinThrowerCount);
        }
        //养尸人
        if (OtherMainConfig.RaidSpawnZombieKeeper) {
            addWaves("ZOMBIE_KEEPER", JerotesVillageEntityType.ZOMBIE_KEEPER.get(), OtherMainConfig.RaidSpawnZombieKeeperCount);
        }
        //苦寒术士
        if (OtherMainConfig.RaidSpawnBitterColdSorcerer) {
            addWaves("BITTER_COLD_SORCERER", JerotesVillageEntityType.BITTER_COLD_SORCERER.get(), OtherMainConfig.RaidSpawnBitterColdSorcererCount);
        }
        //吐火者
        if (OtherMainConfig.RaidSpawnFireSpitter) {
            addWaves("FIRE_SPITTER", JerotesVillageEntityType.FIRE_SPITTER.get(), OtherMainConfig.RaidSpawnFireSpitterCount);
        }
        //野探客
        if (OtherMainConfig.RaidSpawnWildFinder) {
            addWaves("WILD_FINDER", JerotesVillageEntityType.WILD_FINDER.get(), OtherMainConfig.RaidSpawnWildFinderCount);
        }
        //潜海者
        if (OtherMainConfig.RaidSpawnSubmariner) {
            addWaves("SUBMARINER", JerotesVillageEntityType.SUBMARINER.get(), OtherMainConfig.RaidSpawnSubmarinerCount);
        }
        //灯术师
        if (OtherMainConfig.RaidSpawnLampWizard) {
            addWaves("LAMP_WIZARD", JerotesVillageEntityType.LAMP_WIZARD.get(), OtherMainConfig.RaidSpawnLampWizardCount);
        }
        //奴制监督者
        if (OtherMainConfig.RaidSpawnSlaverySupervisor) {
            addWaves("SLAVERY_SUPERVISOR", JerotesVillageEntityType.SLAVERY_SUPERVISOR.get(), OtherMainConfig.RaidSpawnSlaverySupervisorCount);
        }
        //火力倾泻者
        if (OtherMainConfig.RaidSpawnFirepowerPourer) {
            addWaves("FIREPOWER_POURER", JerotesVillageEntityType.FIREPOWER_POURER.get(), OtherMainConfig.RaidSpawnFirepowerPourerCount);
        }
        //巫神汉
        if (OtherMainConfig.RaidSpawnNecromancyWarlock) {
            addWaves("NECROMANCY_WARLOCK", JerotesVillageEntityType.NECROMANCY_WARLOCK.get(), OtherMainConfig.RaidSpawnNecromancyWarlockCount);
        }
        //引力师
        if (OtherMainConfig.RaidSpawnGaviler) {
            addWaves("GAVILER", JerotesVillageEntityType.GAVILER.get(), OtherMainConfig.RaidSpawnGavilerCount);
        }
        //斧头杀人狂
        if (OtherMainConfig.RaidSpawnAxCrazy) {
            addWaves("AX_CRAZY", JerotesVillageEntityType.AX_CRAZY.get(), OtherMainConfig.RaidSpawnAxCrazyCount);
        }
        //涉险者
        if (OtherMainConfig.RaidSpawnAdventurer) {
            addWaves("ADVENTURER", JerotesVillageEntityType.ADVENTURER.get(), OtherMainConfig.RaidSpawnAdventurerCount);
        }
        //鬼婆
        if (OtherMainConfig.RaidSpawnHag) {
            addWaves("HAG", JerotesVillageEntityType.COHORT_HAG.get(), OtherMainConfig.RaidSpawnHagCount);
        }
        //女巫学者
        if (OtherMainConfig.RaidSpawnWitchScholar) {
            addWaves("WITCH_SCHOLAR", JerotesVillageEntityType.WITCH_SCHOLAR.get(), OtherMainConfig.RaidSpawnWitchScholarCount);
        }
        //紫沙女巫
        if (OtherMainConfig.RaidSpawnPurpleSandWitch) {
            addWaves("PURPLE_SAND_WITCH", JerotesVillageEntityType.PURPLE_SAND_WITCH.get(), OtherMainConfig.RaidSpawnPurpleSandWitchCount);
        }
        //灵奴
        if (OtherMainConfig.RaidSpawnSpirve) {
            addWaves("SPIRVE", JerotesVillageEntityType.SPIRVE.get(), OtherMainConfig.RaidSpawnSpirveCount);
        }
        //大巫婆
        if (OtherMainConfig.RaidSpawnBigWitch) {
            addWaves("BIG_WITCH", JerotesVillageEntityType.BIG_WITCH.get(), OtherMainConfig.RaidSpawnBigWitchCount);
        }
        //灾厄旗帜投影
        if (OtherMainConfig.RaidSpawnOminousBannerProjection) {
            addWaves("OMINOUS_BANNER_PROJECTION", JerotesVillageEntityType.OMINOUS_BANNER_PROJECTION.get(), OtherMainConfig.RaidSpawnOminousBannerProjectionCount);
        }
        //紫沙鬼婆
        if (OtherMainConfig.RaidSpawnPurpleSandHag) {
            addWaves("PURPLE_SAND_HAG", JerotesVillageEntityType.PURPLE_SAND_HAG.get(), OtherMainConfig.RaidSpawnPurpleSandHagCount);
        }
    }

    private static Raid.RaiderType addWaves(String name, EntityType<? extends Raider> type, List<? extends Integer> list) {
        Raid.RaiderType member = Raid.RaiderType.create(name, type, new int[]{list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5), list.get(6), list.get(7)});
        NEW_RAID_MEMBERS.add(member);
        return member;
    }
}

