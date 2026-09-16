package com.jerotes.jerotesvillage.event;

import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Animal.RustleFoxEntity;
import com.jerotes.jerotesvillage.entity.Monster.BitterColdWindEntity;
import com.jerotes.jerotesvillage.init.*;
import com.jerotes.jerotesvillage.network.JerotesVillageLevelData;
import com.jerotes.jerotesvillage.util.OtherEntityAndItemFind;
import com.jerotes.jerotesvillage.util.OtherEntityFactionFind;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.village.VillageSiegeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;


@Mod.EventBusSubscriber
public class WeatherEvent {
	//村庄袭击
	@SubscribeEvent
	public static void SecondRoundVillageSiege(VillageSiegeEvent event) {
		Level level = event.getLevel();
		if (level.dimension() == JerotesVillageDimensions.SECOND_ROUND_WORLD) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void SecondRound(LivingEvent.LivingTickEvent event) {
		LivingEntity entity = event.getEntity();
		if (entity == null || !entity.isAlive())
			return;
		if (entity.getRandom().nextInt(1, 900) == 1) {
			if (entity.level().dimension() == JerotesVillageDimensions.SECOND_ROUND_WORLD && !WorldEvent.ZsieinModerateInfectedAbove(entity.level())) {
				entity.heal(1.0f);
			}
		}
	}

	@SubscribeEvent
	public static void BitterCold(LivingEvent.LivingTickEvent event) {
		LivingEntity entity = event.getEntity();
		if (entity == null || !entity.isAlive())
			return;
		if (event.getEntity().tickCount % 30 == 1) {
			if (entity.level().getBiome(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ())).is(JerotesVillageBiomes.BITTER_COLD)){
				if (!WorldEvent.ZsieinModerateInfectedAbove(entity.level())) {
					boolean type = entity.getType().is(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES);
					boolean armor = entity.getItemBySlot(EquipmentSlot.HEAD).is(ItemTags.FREEZE_IMMUNE_WEARABLES) || entity.getItemBySlot(EquipmentSlot.CHEST).is(ItemTags.FREEZE_IMMUNE_WEARABLES) || entity.getItemBySlot(EquipmentSlot.LEGS).is(ItemTags.FREEZE_IMMUNE_WEARABLES) || entity.getItemBySlot(EquipmentSlot.FEET).is(ItemTags.FREEZE_IMMUNE_WEARABLES);
					boolean knock = entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) >= 0.4;
					if (type || armor) {
						return;
					}
					if (entity.canFreeze() && (entity.level().isRaining() || entity.level().isThundering())) {
						entity.setIsInPowderSnow(true);
					}
					if (knock) {
						return;
					}
					if (!entity.level().isClientSide()) {
						entity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.BITTER_COLD.get(), 45, 0, false, false));
					}
				}

			}
		}
	}

	@SubscribeEvent
	public static void BitterCold(LivingDeathEvent event) {
		LivingEntity entity = event.getEntity();
		if (entity == null)
			return;
		if (!(entity instanceof Mob || entity instanceof Player))
			return;
		if (entity.getRandom().nextInt(1, 100) == 1) {
			if (entity.hasEffect(JerotesVillageMobEffects.BITTER_COLD.get()) && !WorldEvent.ZsieinModerateInfectedAbove(entity.level()) && !(entity instanceof BitterColdWindEntity) && entity.level() instanceof ServerLevel _level && !entity.level().isClientSide()) {
				BitterColdWindEntity bitterColdWind = JerotesVillageEntityType.BITTER_COLD_WIND.get().spawn(_level, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), MobSpawnType.MOB_SUMMONED);
				if (bitterColdWind != null) {
					entity.level().gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) bitterColdWind.getX(), (int) bitterColdWind.getY(), (int) bitterColdWind.getZ()), GameEvent.Context.of(bitterColdWind));
				}
			}
		}
	}


	@SubscribeEvent
	public static void PurpleDesert(TickEvent.PlayerTickEvent event) {
		LivingEntity player = event.player;
		if (player == null)
			return;
		if (event.player.tickCount % 40 == 1) {
			if (player.level().getBiome(BlockPos.containing(player.getX(), player.getY(), player.getZ())).is(JerotesVillageBiomes.PURPLE_DESERT)) {
			if (player.level() instanceof ServerLevel serverLevel) {
					if (!WorldEvent.ZsieinModerateInfectedAbove(player.level())) {
						boolean helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.SPIRVE_HELMET.get() || player.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.SPIRVE_HEAD.get();
						boolean chestplate = player.getItemBySlot(EquipmentSlot.CHEST).getItem() == JerotesVillageItems.SPIRVE_CHESTPLATE.get();
						boolean legs = player.getItemBySlot(EquipmentSlot.LEGS).getItem() == JerotesVillageItems.SPIRVE_LEGGINGS.get();
						boolean feet = player.getItemBySlot(EquipmentSlot.FEET).getItem() == JerotesVillageItems.SPIRVE_BOOTS.get();
						if (player.getRandom().nextFloat() < (OtherMainConfig.PurpleSandWeatherLightningChance * 2) && !(helmet || chestplate || legs || feet)) {
							BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(player, 16);
							for (int find = 0; find < 12; ++find) {
								if (!(serverLevel.canSeeSky(summonPos))) {
									summonPos = Main.findSpawnPositionNearFillOnBlock(player, 16);
								}
							}
							if ((serverLevel.canSeeSky(summonPos) && !serverLevel.isEmptyBlock(summonPos.below()))) {
								LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.NATURAL);
								if (lightningBolt != null) {
									lightningBolt.setVisualOnly(true);
									if (!(lightningBolt.level() instanceof ServerLevel)) {
										lightningBolt.level().setSkyFlashTime(2);
									} else {
										List<Entity> list1 = lightningBolt.level().getEntities(lightningBolt, new AABB(lightningBolt.getX() - 1.0D, lightningBolt.getY() - 1.0D, lightningBolt.getZ() - 1.0D, lightningBolt.getX() + 1.0D, lightningBolt.getY() + 2.0D + 1.0D, lightningBolt.getZ() + 1.0D), Entity::isAlive);
										for (Entity entity : list1) {
											if (!net.minecraftforge.event.ForgeEventFactory.onEntityStruckByLightning(entity, lightningBolt)) {
												entity.thunderHit((ServerLevel) lightningBolt.level(), lightningBolt);
												if (entity instanceof ServerPlayer serverPlayer) {
													AdvancementEvent.AdvancementGive(serverPlayer, "jerotesvillage:a_bolt_from_the_purple");
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}


	@SubscribeEvent
	public static void HarmoniousWonderland(LivingEvent.LivingTickEvent event) {
		LivingEntity entity = event.getEntity();
		if (entity == null)
			return;
		if (entity.getRandom().nextInt(100) == 1 && !(entity instanceof AbstractSchoolingFish) || entity.getRandom().nextInt(16000) == 1) {
			if (entity.level().getBiome(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ())).is(JerotesVillageBiomes.HARMONIOUS_WONDERLAND)) {
				if (!WorldEvent.ZsieinModerateInfectedAbove(entity.level())) {
					entity.heal(1.0f);
					if (entity.level() instanceof ServerLevel _level) {
						_level.sendParticles(ParticleTypes.CHERRY_LEAVES, entity.getRandomX(0.5), entity.getRandomY(), entity.getRandomZ(0.5), 6, 0, 0, 0, 0);
					}
				}
			}
		}
		if (event.getEntity().tickCount % 30 == 1) {
			if (entity instanceof Enemy && !(entity instanceof OwnableEntity ownable && !(ownable.getOwner() instanceof Enemy))) {
				if (entity.level().getBiome(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ())).is(JerotesVillageBiomes.HARMONIOUS_WONDERLAND)) {
					if (!WorldEvent.ZsieinModerateInfectedAbove(entity.level())) {
						if (!entity.level().isClientSide()) {
							entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 45, 0, false, false));
							entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 45, 0, false, false));
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void UnKnownDeep(LivingEvent.LivingTickEvent event) {
		LivingEntity entity = event.getEntity();
		if (entity == null)
			return;
		if (event.getEntity().tickCount % 30 == 1) {
			if (entity.level().getBiome(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ())).is(JerotesVillageBiomes.UNKNOWN_DEEP) && !WorldEvent.ZsieinModerateInfectedAbove(entity.level())) {
				int n = 0;
				if (JerotesVillageLevelData.get(entity.level()) != null && JerotesVillageLevelData.get(entity.level()).WickedRidiculeNight) {
					n = 2;
				}
				if (!entity.level().isClientSide()) {
					entity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.DEEP_BLESSING.get(), 45, n, false, false));
				}
			}
		}
	}

	@SubscribeEvent
	public static void VirtualCave(LivingDeathEvent event) {
		LivingEntity livingEntity = event.getEntity();
		Entity entity = event.getSource().getEntity();
		if (!(livingEntity instanceof Enemy enemy) || !(enemy instanceof Mob mob) || !(entity instanceof Player player) || !(mob.getTarget() == player))
			return;
		if (livingEntity.level().isClientSide()) {
			return;
		}
		if (mob.level().getBiome(BlockPos.containing(mob.getX(), mob.getY(), mob.getZ())).is(JerotesVillageBiomes.VIRTUAL_CAVE) && !WorldEvent.ZsieinModerateInfectedAbove(entity.level())) {
			int reward = player.getRandom().nextInt(100);
			ItemStack itemStack2 = new ItemStack(Items.AIR, 1);
			if (reward <= 2) {
				itemStack2 = new ItemStack(JerotesVillageItems.VIRTUAL_CAVE_EVIL_CRYSTAL.get(), 2);
			}
			else if (reward <= 7) {
				itemStack2 = new ItemStack(JerotesVillageItems.VIRTUAL_CAVE_EVIL_CRYSTAL.get(), 1);
			}
			if (reward <= 7) {
				ItemEntity itemEntity = mob.spawnAtLocation(itemStack2, 0.5f);
				if (itemEntity != null) {
					itemEntity.setPos(mob.getX(), mob.getEyeY(), mob.getZ());
				}
			}
		}
	}

	@SubscribeEvent
	public static void PlagueZone(LivingEvent.LivingTickEvent event) {
		LivingEntity entity = event.getEntity();
		if (entity == null)
			return;
		if (entity.tickCount % 60 == 0) {
			boolean zsiein = OtherEntityFactionFind.isZsiein(entity.getType());
			boolean iszsieinaboutimmune = OtherEntityAndItemFind.isZsieinAboutImmune(entity);
			boolean plagueZone = entity.level().getBiome(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ())).is(JerotesVillageBiomes.PLAGUE_ZONE);
			boolean mild = WorldEvent.ZsieinMildInfectedAbove(event.getEntity().level());
			boolean severe = WorldEvent.ZsieinSevereInfectedAbove(event.getEntity().level());
			boolean extremely = WorldEvent.ZsieinExtremelyInfectedAbove(event.getEntity().level());
			//泽林雾气
			if (!zsiein && plagueZone && mild || severe) {
				if (!entity.level().isClientSide()) {
					entity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.ZSIEIN_FOG.get(), 40, 0, false, false));
				}
			}
			//轻度感染
			if (mild && !zsiein) {
				//瘟疫之地液体腐蚀
				if ((Main.isInFluid(entity) || entity.isInWaterOrBubble() || entity.isInWaterOrRain()) && plagueZone && !iszsieinaboutimmune) {
					if (!entity.level().isClientSide()) {
						entity.addEffect(new MobEffectInstance(JerotesMobEffects.CORROSIVE.get(), 40, 0, false, false));
					}
				}
			}
			//重度感染
			if (severe && !zsiein) {
				//坠落
				if (!entity.noPhysics && entity.getDeltaMovement().y > -1d && !iszsieinaboutimmune) {
					entity.setDeltaMovement(entity.getDeltaMovement().x, -0.15d, entity.getDeltaMovement().z);
				}
				//夜视
				if (!entity.level().isClientSide()) {
					entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 25, 0, false, false));
				}
				//液体腐蚀
				if ((Main.isInFluid(entity) || entity.isInWaterOrBubble() || entity.isInWaterOrRain()) && !plagueZone && !iszsieinaboutimmune) {
					if (!entity.level().isClientSide()) {
						entity.addEffect(new MobEffectInstance(JerotesMobEffects.CORROSIVE.get(), 25, 0, false, false));
					}
				}
			}
			//极度感染
			if (extremely && !zsiein) {
				//失活
				if (entity.getMobType() == MobType.UNDEAD && !EntityAndItemFind.isBoss(entity.getType()) && !iszsieinaboutimmune) {
					if (!entity.level().isClientSide()) {
						entity.addEffect(new MobEffectInstance(JerotesMobEffects.PARALYZED.get(), 25, 0, false, false));
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void SomeDay(EntityJoinLevelEvent event) {
		Entity entity = event.getEntity();
		if (entity == null)
			return;
		if (isAprilFoolsDay() && OtherMainConfig.SpecialDay) {
			if (event.getLevel().dimension() == JerotesVillageDimensions.SECOND_ROUND_WORLD && entity instanceof Player player) {
				//你的伙伴
				if (event.getLevel() instanceof ServerLevel serverLevel) {
					List<RustleFoxEntity> foxList = serverLevel.getEntitiesOfClass(RustleFoxEntity.class, player.getBoundingBox().inflate(32, 32, 32));
					if (foxList.isEmpty()) {
						RustleFoxEntity fox = new RustleFoxEntity(JerotesVillageEntityType.RUSTLE_FOX.get(), serverLevel);
						fox.moveTo(player.getX(), player.getY(), player.getZ());
						fox.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
						fox.setCustomName(Component.literal("你的伙伴"));
						fox.tame(player);
						serverLevel.addFreshEntity(fox);
					}
				}
			}
		}
	}

	public static boolean isAprilFoolsDay() {
		LocalDate localDate = LocalDate.now();
		int n = localDate.get(ChronoField.DAY_OF_MONTH);
		int n2 = localDate.get(ChronoField.MONTH_OF_YEAR);
		return n2 == 4 && n == 1;
	}
	public static boolean isNewYearsDay() {
		LocalDate localDate = LocalDate.now();
		int n = localDate.get(ChronoField.DAY_OF_MONTH);
		int n2 = localDate.get(ChronoField.MONTH_OF_YEAR);
		return n2 == 1 && n == 1;
	}

}

