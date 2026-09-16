package com.jerotes.jerotesvillage.event;

import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Humanoid.Meror.MerorEntity;
import com.jerotes.jerotesvillage.entity.Neutral.MerorLocatorEntity;
import com.jerotes.jerotesvillage.init.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = JerotesVillage.MODID)
public class MerorWantedEvent {

	@SubscribeEvent
	public static void MerorDeath(LivingDeathEvent event) {
		LivingEntity victim = event.getEntity();
		Entity murderer = event.getSource().getEntity();
		if (victim == null || murderer == null)
			return;
		if (!(victim instanceof MerorEntity))
			return;
		if (Main.getJerotesPersistentData(murderer).getDouble("jerotesvillage_meror_wanted_order") < 48000) {
			if (murderer instanceof LivingEntity murdererLiv) {
				if (!murderer.level().isClientSide()) {
					Main.getJerotesPersistentData(murdererLiv).putDouble("jerotesvillage_meror_wanted_order", 48000);
				}
			}
		}
		if (murderer instanceof OwnableEntity ownable && ownable.getOwner() != null) {
			if (Main.getJerotesPersistentData(ownable.getOwner()).getDouble("jerotesvillage_meror_wanted_order") < 48000) {
				if (!ownable.getOwner().level().isClientSide()) {
					Main.getJerotesPersistentData(ownable.getOwner()).putDouble("jerotesvillage_meror_wanted_order", 48000);
				}
			}
		}
	}

	@SubscribeEvent
	public static void MerorEffect(LivingEvent.LivingTickEvent event) {
		LivingEntity murderer = event.getEntity();
		if (murderer == null)
			return;
		if (murderer.level().isClientSide)
			return;
		if (Main.getJerotesPersistentData(murderer).getDouble("jerotesvillage_meror_wanted_order") > 0) {
			//计时降低
			Main.getJerotesPersistentData(murderer).putDouble("jerotesvillage_meror_wanted_order", Main.getJerotesPersistentData(murderer).getDouble("jerotesvillage_meror_wanted_order") - 1);
			//没有buff
			if (!murderer.hasEffect(JerotesVillageMobEffects.MEROR_WANTED_ORDER.get())) {
				if (!murderer.level().isClientSide()) {
					murderer.addEffect(new MobEffectInstance(JerotesVillageMobEffects.MEROR_WANTED_ORDER.get(), (int) Main.getJerotesPersistentData(murderer).getDouble("jerotesvillage_meror_wanted_order"), 0));
				}
			}
			//有buff
			else {
				//如果buff时间大于计时
				if (murderer.getEffect(JerotesVillageMobEffects.MEROR_WANTED_ORDER.get()).getDuration() > Main.getJerotesPersistentData(murderer).getDouble("jerotesvillage_meror_wanted_order")) {
					Main.getJerotesPersistentData(murderer).putDouble("jerotesvillage_meror_wanted_order", murderer.getEffect(JerotesVillageMobEffects.MEROR_WANTED_ORDER.get()).getDuration());
				}
				//如果buff时间小于计时
				else {
					murderer.addEffect(new MobEffectInstance(JerotesVillageMobEffects.MEROR_WANTED_ORDER.get(), (int) Main.getJerotesPersistentData(murderer).getDouble("jerotesvillage_meror_wanted_order"), 0));
				}
			}
		}
		else if (murderer.hasEffect(JerotesVillageMobEffects.MEROR_WANTED_ORDER.get())) {
			Main.getJerotesPersistentData(murderer).putDouble("jerotesvillage_meror_wanted_order", Objects.requireNonNull(murderer.getEffect(JerotesVillageMobEffects.MEROR_WANTED_ORDER.get())).getDuration());
		}
	}

	@SubscribeEvent
	public static void MurdererDeath(LivingDeathEvent event) {
		LivingEntity murderer = event.getEntity();
		if (murderer == null)
			return;
		if (murderer.level().isClientSide)
			return;
		//死亡清除
		if (Main.getJerotesPersistentData(murderer).getDouble("jerotesvillage_meror_wanted_order") > 0) {
			Main.getJerotesPersistentData(murderer).putDouble("jerotesvillage_meror_wanted_order", 0);
		}
		//掉落物
		Entity executioner = event.getSource().getEntity();
		if (executioner == null)
			return;
		if (murderer == executioner)
			return;
		if (!(executioner instanceof Player executionerPlayer))
			return;
		if (murderer.hasEffect(JerotesVillageMobEffects.MEROR_WANTED_ORDER.get())) {
			int reward = executionerPlayer.getRandom().nextInt(100);
			ItemStack itemStack2;
			if (reward <= 3) {
				itemStack2 = new ItemStack(JerotesVillageItems.MEROR_TOKEN_E.get(), 2);
			}
			else if (reward <= 15) {
				itemStack2 = new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 10);
			}
			else if (reward <= 30) {
				itemStack2 = new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 4);
			}
			else if (reward <= 60) {
				itemStack2 = new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 2);
			}
			else{
				itemStack2 = new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 1);
			}
			if (!executionerPlayer.getInventory().add(itemStack2)) {
				executionerPlayer.drop(itemStack2, false);
			}
		}
	}

	@SubscribeEvent
	public static void MerorHunter(LivingEvent.LivingTickEvent event) {
		LivingEntity murderer = event.getEntity();
		if (murderer == null)
			return;
		if (Main.getJerotesPersistentData(murderer).getDouble("jerotesvillage_meror_wanted_order") > 0) {
			if (murderer.hasEffect(JerotesVillageMobEffects.MEROR_WANTED_ORDER.get()) && (murderer instanceof Player || OtherMainConfig.AllCreaturesCanBeWantedByMeror)) {
				boolean bl = OtherMainConfig.MerorHighEnergySpaceTeleport;
				if (bl || OtherMainConfig.MerorHighEnergySpaceTeleportAboutDimension.contains(murderer.level().dimension().location().toString())) {
					if (murderer.getRandom().nextFloat() < (OtherMainConfig.MerorWantedChancePerSecond / 20)) {
						if (murderer.level() instanceof ServerLevel _level) {
							BlockPos blockPosSpawn = murderer.getOnPos();
							MerorLocatorEntity locator = JerotesVillageEntityType.MEROR_LOCATOR.get().spawn(_level, new BlockPos(blockPosSpawn.getX(), blockPosSpawn.getY() + 4, blockPosSpawn.getZ()), MobSpawnType.EVENT);
							if (locator != null) {
								locator.setTarget(murderer);
							}
						}
					}
				}
			}
		}
	}
}

