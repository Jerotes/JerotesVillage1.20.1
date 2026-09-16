package com.jerotes.jerotesvillage.event;

import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotesvillage.network.JerotesVillagePlayerData;
import com.jerotes.jerotesvillage.util.OtherEntityFactionFind;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = JerotesVillage.MODID)
public class RelationshipEvent {
	//铜刻佣兵团
	public static int GetCopperCarvedCompanyRelationship(Player player) {
		return player.getCapability(JerotesVillagePlayerData.CAPABILITY, null).orElse(new JerotesVillagePlayerData.PlayerVariables()).CopperCarvedCompanyRelationship;
	}
	public static boolean MoreCopperCarvedCompanyRelationship(Player player, int n) {
		return GetCopperCarvedCompanyRelationship(player) > n;
	}
	public static boolean LessCopperCarvedCompanyRelationship(Player player, int n) {
		return GetCopperCarvedCompanyRelationship(player) < n;
	}
	public static void AddCopperCarvedCompanyRelationship(Player player, int n) {
		int setInt = GetCopperCarvedCompanyRelationship(player) + n;
		SetCopperCarvedCompanyRelationship(player, setInt);
	}
	public static void SetCopperCarvedCompanyRelationship(Player player, int n) {
		player.getCapability(JerotesVillagePlayerData.CAPABILITY, null).ifPresent(capability -> {
			capability.setCopperCarvedCompanyRelationship(n);
		});
		if (!player.getCapability(JerotesVillagePlayerData.CAPABILITY, null).orElse(new JerotesVillagePlayerData.PlayerVariables()).HaveCopperCarvedCompanyRelationship) {
			player.getCapability(JerotesVillagePlayerData.CAPABILITY, null).ifPresent(capability -> {
				capability.setHaveCopperCarvedCompanyRelationship(true);
			});
		}
	}
	//阵营免伤
	@SubscribeEvent
	public static void FactionHurt(LivingAttackEvent event) {
		LivingEntity hurt = event.getEntity();
		Entity attacker = event.getSource().getEntity();
		if (!(attacker instanceof Mob mobAttacker) || !(hurt instanceof Mob mobHurt))
			return;
		if (mobAttacker.getTarget() != mobHurt && mobHurt.getTarget() != mobAttacker && (mobAttacker.getTeam() == null && mobHurt.getTeam() == null || mobAttacker.isAlliedTo(mobHurt))) {
			//铜刻类型生物不误伤信任者
			if (OtherEntityFactionFind.isCarved(mobAttacker.getType()) && mobAttacker instanceof CarvedEntity carvedEntity && carvedEntity.trusts(mobHurt.getUUID())) {
				event.setCanceled(true);
			}
			//铜刻类型生物不误伤信任者的宠物
			if (OtherEntityFactionFind.isCarved(mobAttacker.getType()) && mobAttacker instanceof CarvedEntity carvedEntity && mobHurt instanceof OwnableEntity ownable && ownable.getOwner() instanceof Player player && (RelationshipEvent.MoreCopperCarvedCompanyRelationship(player, 500) || carvedEntity.trusts(player.getUUID()))) {
				event.setCanceled(true);
			}
			//信任者不误伤铜刻类型生物
			if (OtherEntityFactionFind.isCarved(mobHurt.getType()) && mobHurt instanceof CarvedEntity carvedEntity && mobAttacker instanceof OwnableEntity ownable && ownable.getOwner() instanceof Player player && (RelationshipEvent.MoreCopperCarvedCompanyRelationship(player, 500) || carvedEntity.trusts(player.getUUID()))) {
				event.setCanceled(true);
			}
		}
	}


	@SubscribeEvent
	public static void RelationshipTick(LivingEvent.LivingTickEvent event) {
		LivingEntity entity = event.getEntity();
		if (entity == null || !entity.isAlive())
			return;
		if (entity instanceof Player player){
			float carved = ((float) (player.getCapability(JerotesVillagePlayerData.CAPABILITY, null).orElse(new JerotesVillagePlayerData.PlayerVariables())).CopperCarvedCompanyRelationship);
			if (carved > 1000) {
				SetCopperCarvedCompanyRelationship(player, 1000);
			}
			if (carved < -1000) {
				SetCopperCarvedCompanyRelationship(player, -1000);
			}
		}
	}

	@SubscribeEvent
	public static void RelationshipAbout(LivingDeathEvent event) {
		LivingEntity victim = event.getEntity();
		Entity murderer = event.getSource().getEntity();
		if (victim == null || murderer == null)
			return;
		if (!(murderer instanceof Player player))
			return;
		//铜刻佣兵团-袭击加入者
		if (EntityFactionFind.isRaider(victim)) {
			RelationshipEvent.AddCopperCarvedCompanyRelationship(player, +1);
		}
		//铜刻佣兵团-铁傀儡
		if (MoreCopperCarvedCompanyRelationship(player, -500) && victim.getType() == EntityType.IRON_GOLEM) {
			RelationshipEvent.AddCopperCarvedCompanyRelationship(player, -1);
		}
		//铜刻佣兵团-村民
		if (victim instanceof Villager villager) {
			if (villager.isBaby()) {
				RelationshipEvent.AddCopperCarvedCompanyRelationship(player, -10);
			} else {
				RelationshipEvent.AddCopperCarvedCompanyRelationship(player, -5);
			}
		}
	}
}

