package com.jerotes.jerotesvillage.util;

import com.jerotes.jerotes.util.EntityFactionFind;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class OtherEntityFactionFind {
	//是否铜刻类型生物
	public static boolean isCarved(EntityType type) {
		return type.is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("jerotes:carved")));
	}
	//是否二轮世界原住民
	public static boolean isSecondRounder(EntityType type) {
		return type.is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("jerotes:second_rounder")));
	}
	//是否莫厄人
	public static boolean isMeror(EntityType type) {
		return type.is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("jerotes:meror")));
	}
	//是否荒野巨人
	public static boolean isWildernessGiant(EntityType type) {
		return type.is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("jerotes:wilderness_giant")));
	}
	//是否复魂尸
	public static boolean isResurrecter(EntityType type) {
		return type.is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("jerotes:resurrecter")));
	}
	//是否鬼婆
	public static boolean isHag(EntityType type) {
		return type.is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("jerotes:hag")));
	}
	//是否吉暴
	public static boolean isChibao(EntityType type) {
		return type.is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("jerotes:chibao")));
	}
	//是否蛇龙人
	public static boolean isSerpon(EntityType type) {
		return type.is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("jerotes:serpon")));
	}
	//是否蛇龙阵营
	public static boolean isSerponFaction(EntityType type) {
		return type.is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("jerotes:serpon_faction")));
	}
	//是否脍酷鱼人
	public static boolean isQoaiku(EntityType type) {
		return type.is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("jerotes:qoaiku")));
	}
	//是否奇灵
	public static boolean isWonderlin(EntityType type) {
		return type.is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("jerotes:wonderlin")));
	}
	//是否二轮世界机械
	public static boolean isSecondRounderMachine(EntityType type) {
		return type.is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("jerotes:second_rounder_machine")));
	}
	//是否莫厄机械
	public static boolean isMerorMachine(EntityType type) {
		return type.is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("jerotes:meror_machine")));
	}
	//是否泽林
	public static boolean isZsiein(EntityType type) {
		return type.is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("jerotes:zsiein")));
	}

	//铜刻佣兵团 Copper Carved Company
	public static boolean isFactionCopperCarvedCompany(LivingEntity livingEntity) {
		return EntityFactionFind.getTrueFaction(livingEntity).equals("copper_carved_company");
	}
	//邪笑呼唤 Wicked Ridicule Calls
	public static boolean isFactionWickedRidiculeCalls(LivingEntity livingEntity) {
		return EntityFactionFind.getTrueFaction(livingEntity).equals("wicked_ridicule_calls");
	}
	//末影渡客集体 Ender Stowaway Collective
	public static boolean isFactionEnderStowawayCollective(LivingEntity livingEntity) {
		return EntityFactionFind.getTrueFaction(livingEntity).equals("ender_stowaway_collective");
	}
	//紫沙姐妹会 Purple Sand Sisterhood
	public static boolean isFactionPurpleSandSisterhood(LivingEntity livingEntity) {
		return EntityFactionFind.getTrueFaction(livingEntity).equals("purple_sand_sisterhood");
	}
	//沉降复魂尸 Sediment Resurrecter
	public static boolean isFactionSedimentResurrecter(LivingEntity livingEntity) {
		return EntityFactionFind.getTrueFaction(livingEntity).equals("sediment_resurrecter");
	}
	//灾厄村民旗帜投影部队 Ominous Banner Raid Force
	public static boolean isFactionOminousBannerRaidForce(LivingEntity livingEntity) {
		return EntityFactionFind.getTrueFaction(livingEntity).equals("ominous_banner_raid_force");
	}
	//猪灵驻团 Piglin Resident Detachment
	public static boolean isFactionPiglinResidentDetachment(LivingEntity livingEntity) {
		return EntityFactionFind.getTrueFaction(livingEntity).equals("piglin_resident_detachment");
	}
	//莫厄机械 Meror Machine
	public static boolean isFactionMerorMachine(LivingEntity livingEntity) {
		return EntityFactionFind.getTrueFaction(livingEntity).equals("meror_machine");
	}
	//泽林 Zsiein
	public static boolean isFactionZsiein(LivingEntity livingEntity) {
		return EntityFactionFind.getTrueFaction(livingEntity).equals("zsiein");
	}
	//莫厄文明 Meror Civilization
	public static boolean isFactionMerorCivilization(LivingEntity livingEntity) {
		return EntityFactionFind.getTrueFaction(livingEntity).equals("meror_civilization");
	}
	//荒野巨人部落 Wilderness Giant Tribes
	public static boolean isFactionWildernessGiantTribes(LivingEntity livingEntity) {
		return EntityFactionFind.getTrueFaction(livingEntity).equals("wilderness_giant_tribes");
	}
	//古莫厄文明 Ancient Meror Civilization
	public static boolean isFactionAncientMerorCivilization(LivingEntity livingEntity) {
		return EntityFactionFind.getTrueFaction(livingEntity).equals("ancient_meror_civilization");
	}
	//吉暴军团 Chibao Legion
	public static boolean isFactionChibaoLegion(LivingEntity livingEntity) {
		return EntityFactionFind.getTrueFaction(livingEntity).equals("chibao_legion");
	}
	//脍酷联盟 Qoaiku Alliance
	public static boolean isFactionQoaikuAlliance(LivingEntity livingEntity) {
		return EntityFactionFind.getTrueFaction(livingEntity).equals("qoaiku_alliance");
	}
	//脍酷海盗团 Qoaiku Pirates
	public static boolean isFactionQoaikuPirates(LivingEntity livingEntity) {
		return EntityFactionFind.getTrueFaction(livingEntity).equals("qoaiku_pirates");
	}
	//植究人村庄 Botanize Village
	public static boolean isFactionBotanizeVillage(LivingEntity livingEntity) {
		return EntityFactionFind.getTrueFaction(livingEntity).equals("botanize_village");
	}
	//奇灵洞穴 Wonderlin Caves
	public static boolean isFactionWonderlinCaves(LivingEntity livingEntity) {
		return EntityFactionFind.getTrueFaction(livingEntity).equals("wonderlin_caves");
	}
	//蛇龙战斗组 Serpon Combat Team
	public static boolean isFactionSerponCombatTeam(LivingEntity livingEntity) {
		return EntityFactionFind.getTrueFaction(livingEntity).equals("serpon_combat_team");
	}
	//邪笑法团 Wicked Ridicule Conclave
	public static boolean isFactionWickedRidiculeConclave(LivingEntity livingEntity) {
		return EntityFactionFind.getTrueFaction(livingEntity).equals("wicked_ridicule_conclave");
	}
}