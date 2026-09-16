package com.jerotes.jerotesvillage.event;

import com.jerotes.jerotes.entity.Interface.CarvedEntity;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Animal.CarvedHoundEntity;
import com.jerotes.jerotesvillage.entity.Animal.CarvedLlamaEntity;
import com.jerotes.jerotesvillage.entity.Boss.Biome.CelestialCoilvineEntity;
import com.jerotes.jerotesvillage.entity.Boss.Biome.GemstoneMalignasaurEntity;
import com.jerotes.jerotesvillage.entity.Boss.Biome.OldVariantZsieinEntity;
import com.jerotes.jerotesvillage.entity.Interface.GoastEntity;
import com.jerotes.jerotesvillage.entity.Monster.BlamerEntity;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.MeleeIllagerEntity;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.SpellIllagerEntity;
import com.jerotes.jerotesvillage.entity.Monster.PurpleSandCreeperEntity;
import com.jerotes.jerotesvillage.entity.Monster.Resurrecter.ResurrecterEntity;
import com.jerotes.jerotesvillage.entity.Monster.SpirveEntity;
import com.jerotes.jerotesvillage.entity.Monster.TanglerEntity;
import com.jerotes.jerotesvillage.entity.Monster.WickedRidiculeCalls.SecondRounderSkeletonEntity;
import com.jerotes.jerotesvillage.entity.Monster.WickedRidiculeCalls.SecondRounderZombieEntity;
import com.jerotes.jerotesvillage.entity.Other.Other.ViewerEntity;
import com.jerotes.jerotesvillage.entity.Smart.SmartEntity;
import com.jerotes.jerotesvillage.goal.*;
import com.jerotes.jerotesvillage.init.*;
import com.jerotes.jerotesvillage.util.OtherEntityAndItemFind;
import com.jerotes.jerotesvillage.util.OtherEntityFactionFind;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.frog.Tadpole;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = JerotesVillage.MODID)
public class EntityAboutEvent {
	//盘罗仙蔓 移花接木
	@SubscribeEvent
	public static void Hypallage(LivingAttackEvent event) {
		LivingEntity hurt = event.getEntity();
		Entity attacker = event.getSource().getEntity();
		if (attacker == null || !(hurt instanceof OwnableEntity ownable && ownable.getOwner() != null && ownable.getOwner() instanceof CelestialCoilvineEntity celestialCoilvine && !(attacker instanceof Mob mob && mob.getTarget() == celestialCoilvine)) || attacker == ownable.getOwner())
			return;
		if (celestialCoilvine.isAlive() && event.getAmount() <= celestialCoilvine.getHealth() && celestialCoilvine.distanceTo(hurt) < 1024f && !celestialCoilvine.isHide() && !celestialCoilvine.isSecondType() && celestialCoilvine.getRandom().nextFloat() > 0.75f) {
			DamageSource source = event.getSource();
			if (source.is(DamageTypeTags.IS_LIGHTNING) || source.is(DamageTypeTags.IS_FREEZING) || source.is(DamageTypeTags.IS_FIRE) || source.getDirectEntity() != null && source.getDirectEntity() instanceof AbstractArrow arrow && arrow.isOnFire()) {
				return;
			}
			if (AttackFind.isMeleeDamage(source) && attacker instanceof LivingEntity livingEntity) {
				ItemStack handItem = livingEntity.getMainHandItem();
				if (!AttackFind.MeleeDamageFromMainHandNotOffHand(livingEntity))
					handItem = livingEntity.getOffhandItem();
				if (handItem.getEnchantmentLevel(Enchantments.FIRE_ASPECT) > 0) {
					return;
				}
			}
			celestialCoilvine.hurt(event.getSource(), event.getAmount() / 6);
			event.setCanceled(true);
		}
	}
	@SubscribeEvent
	public static void SerponCrossbowTurret(PlayerInteractEvent event) {
		Player player = event.getEntity();
		if (player == null || player.getVehicle() == null || player.getVehicle().getType() != JerotesVillageEntityType.SERPON_CROSSBOW_TURRET.get())
			return;
		player.swing(InteractionHand.MAIN_HAND);
	}

	//发现
	@SubscribeEvent
	public static void HeadVisibility(LivingEvent.LivingVisibilityEvent event) {
		LivingEntity self = event.getEntity();
		Entity lookingEntity = event.getLookingEntity();
		if (lookingEntity == null || self == null)
			return;
		{
			ItemStack itemstack = self.getItemBySlot(EquipmentSlot.HEAD);
			//EntityType<?> entitytype = lookingEntity.getType();

			if (lookingEntity instanceof SecondRounderSkeletonEntity && (itemstack.is(JerotesVillageItems.SECOND_ROUNDER_SKELETON_SKULL.get()) || itemstack.is(Items.SKELETON_SKULL))
					|| lookingEntity instanceof SecondRounderZombieEntity && (itemstack.is(JerotesVillageItems.SECOND_ROUNDER_ZOMBIE_HEAD.get()) || itemstack.is(Items.ZOMBIE_HEAD))
					|| lookingEntity instanceof PurpleSandCreeperEntity && (itemstack.is(JerotesVillageItems.PURPLE_SAND_CREEPER_HEAD.get()) || itemstack.is(Items.CREEPER_HEAD))
					|| lookingEntity instanceof SpirveEntity && itemstack.is(JerotesVillageItems.SPIRVE_HEAD.get())
					|| lookingEntity instanceof ResurrecterEntity && itemstack.is(JerotesVillageItems.RESURRECTER_HEAD.get())
					|| lookingEntity instanceof BlamerEntity && itemstack.is(JerotesVillageItems.BLAMER_HEAD.get())
					|| lookingEntity instanceof TanglerEntity && itemstack.is(JerotesVillageItems.TANGLER_HEAD.get())
					|| lookingEntity instanceof LivingEntity livingEntity && OtherEntityFactionFind.isFactionPiglinResidentDetachment(livingEntity) && itemstack.is(Items.PIGLIN_HEAD)
			) {
				event.modifyVisibility(0.5);
			}
		}
	}
	@SubscribeEvent
	public static void OtherVisibility(LivingEvent.LivingVisibilityEvent event) {
		LivingEntity self = event.getEntity();
		Entity lookingEntity = event.getLookingEntity();
		if (lookingEntity == null || self == null)
			return;
		{
			if ((lookingEntity instanceof SpellIllagerEntity spellIllagerEntity && spellIllagerEntity.teleportCooldown > 0
			|| lookingEntity instanceof MeleeIllagerEntity meleeIllagerEntity && meleeIllagerEntity.teleportCooldown > 0) && self.getMainHandItem().is(JerotesVillageItems.OMINOUS_PROBE.get()))
			 {
				event.modifyVisibility(0.15);
			}
		}
	}


	//聪明
	@SubscribeEvent
	public static void Smart(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			LivingEntity hurt = event.getEntity();
			DamageSource damageSource = event.getSource();
			if (damageSource == null || hurt == null)
				return;
			if (hurt instanceof SmartEntity smartEntity && damageSource.getEntity() instanceof LivingEntity attacker) {
				smartEntity.recordTargetAttack(attacker);
			}
			if (damageSource.getEntity() instanceof SmartEntity smartEntity) {
				smartEntity.recordSelfAttack(hurt);
			}
		}
	}

	//观众欢呼
	@SubscribeEvent
	public static void ViewerCheer(LivingDeathEvent event) {
		LivingEntity loser = event.getEntity();
		Entity winner = event.getSource().getEntity();
		if (loser == null || winner == null)
			return;
		if (!(winner instanceof Player player))
			return;
		List<ViewerEntity> list = player.level().getEntitiesOfClass(ViewerEntity.class, player.getBoundingBox().inflate(256.0, 256.0, 256.0));
		double d = Double.MAX_VALUE;
		int n = Main.randomReach(player.getRandom(), 1, 2);
		for (ViewerEntity viewer : list) {
			if (viewer == null) continue;
			if (player.distanceToSqr(viewer) > d) continue;
			if (viewer == loser) continue;
			if (!viewer.isSilent()) {
				if (!viewer.level().isClientSide()) {
					viewer.setAnimTick(20);
					viewer.setAnimationState(n);
				}
				if ((double) viewer.getRandom().nextFloat() < 0.2) {
					viewer.playSound(JerotesVillageSoundEvents.VIEWER_CHEER_1, 30.0f, 1.0f);
				}
				else if ((double) viewer.getRandom().nextFloat() < 0.4) {
					viewer.playSound(JerotesVillageSoundEvents.VIEWER_CHEER_2, 30.0f, 1.0f);
				}
				else if ((double) viewer.getRandom().nextFloat() < 0.6) {
					viewer.playSound(JerotesVillageSoundEvents.VIEWER_CHEER_3, 30.0f, 1.0f);
				}
				else if ((double) viewer.getRandom().nextFloat() < 0.8) {
					viewer.playSound(JerotesVillageSoundEvents.VIEWER_CLAP_1, 30.0f, 1.0f);
				}
				else {
					viewer.playSound(JerotesVillageSoundEvents.VIEWER_CLAP_2, 30.0f, 1.0f);
				}
			}
		}
	}

	@SubscribeEvent
	public static void AddGoal(EntityJoinLevelEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof Animal animal) {
			animal.goalSelector.addGoal(2, new FollowFragranceGoal(animal, 1.2));
		}
		if (entity instanceof PathfinderMob mob) {
			//铜刻村民
			if (entity instanceof CarvedEntity) {
				if (entity instanceof CarvedHoundEntity) {
					mob.goalSelector.addGoal(6, new CavedFlagTemptNotCareGoal(mob, 1.2, false));
				} else if (entity instanceof CarvedLlamaEntity) {
					mob.goalSelector.addGoal(4, new CavedFlagTemptNotCareGoal(mob, 1.2, false));
				} else {
					mob.goalSelector.addGoal(3, new CavedFlagTemptNotCareGoal(mob, 1.2, false));
				}
				mob.goalSelector.addGoal(0, new VillagerMetalOpenDoorGoal(mob, true));
				mob.goalSelector.addGoal(0, new CavedFlagTemptGoal(mob, 1.2, false));
				mob.goalSelector.addGoal(1, new CarvedFindLodestoneGoal(mob));
				mob.targetSelector.addGoal(2, new CarvedDefendTrustedTargetGoal(mob));
				mob.targetSelector.addGoal(2, new CarvedDefendVillageTargetGoal(mob));
			}

			//村民金属门
			if (entity instanceof AbstractVillager) {
				mob.goalSelector.addGoal(0, new VillagerMetalOpenDoorGoal(mob, true));
			}

			//远离宝石恶鳞
			{
				if (mob instanceof Axolotl mobs && mob.getType() == EntityType.AXOLOTL) {
					mobs.goalSelector.addGoal(1, new AvoidEntityGoal<>(mobs, GemstoneMalignasaurEntity.class, 32.0f, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test));
				}
				if (mob instanceof Cod mobs && mob.getType() == EntityType.COD) {
					mobs.goalSelector.addGoal(1, new AvoidEntityGoal<>(mobs, GemstoneMalignasaurEntity.class, 32.0f, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test));
				}
				if (mob instanceof Salmon mobs && mob.getType() == EntityType.SALMON) {
					mobs.goalSelector.addGoal(1, new AvoidEntityGoal<>(mobs, GemstoneMalignasaurEntity.class, 32.0f, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test));
				}
				if (mob instanceof TropicalFish mobs && mob.getType() == EntityType.TROPICAL_FISH) {
					mobs.goalSelector.addGoal(1, new AvoidEntityGoal<>(mobs, GemstoneMalignasaurEntity.class, 32.0f, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test));
				}
				if (mob instanceof Squid mobs && mob.getType() == EntityType.SQUID) {
					mobs.goalSelector.addGoal(1, new AvoidEntityGoal<>(mobs, GemstoneMalignasaurEntity.class, 32.0f, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test));
				}
				if (mob instanceof Dolphin mobs && mob.getType() == EntityType.DOLPHIN) {
					mobs.goalSelector.addGoal(1, new AvoidEntityGoal<>(mobs, GemstoneMalignasaurEntity.class, 32.0f, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test));
				}
				if (mob instanceof Guardian mobs && mob.getType() == EntityType.GUARDIAN) {
					mobs.goalSelector.addGoal(1, new AvoidEntityGoal<>(mobs, GemstoneMalignasaurEntity.class, 32.0f, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test));
				}
				if (mob instanceof ElderGuardian mobs && mob.getType() == EntityType.ELDER_GUARDIAN) {
					mobs.goalSelector.addGoal(1, new AvoidEntityGoal<>(mobs, GemstoneMalignasaurEntity.class, 32.0f, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test));
				}
				if (mob instanceof Turtle mobs && mob.getType() == EntityType.TURTLE) {
					mobs.goalSelector.addGoal(1, new AvoidEntityGoal<>(mobs, GemstoneMalignasaurEntity.class, 32.0f, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test));
				}
				if (mob instanceof Pufferfish mobs && mob.getType() == EntityType.PUFFERFISH) {
					mobs.goalSelector.addGoal(1, new AvoidEntityGoal<>(mobs, GemstoneMalignasaurEntity.class, 32.0f, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test));
				}
				if (mob instanceof Drowned mobs && mob.getType() == EntityType.DROWNED) {
					mobs.goalSelector.addGoal(1, new AvoidEntityGoal<>(mobs, GemstoneMalignasaurEntity.class, 32.0f, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test));
				}
				if (mob instanceof Tadpole mobs && mob.getType() == EntityType.TADPOLE) {
					mobs.goalSelector.addGoal(1, new AvoidEntityGoal<>(mobs, GemstoneMalignasaurEntity.class, 32.0f, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test));
				}
			}
		}
	}

	//计时
	@SubscribeEvent
	public static void Tick(LivingEvent.LivingTickEvent event) {
		LivingEntity entity = event.getEntity();
		if (entity == null || !entity.isAlive())
			return;
		if (entity instanceof GoastEntity goastEntity && goastEntity.isGoast()) {
			entity.clearFire();
		}
		if (Main.getJerotesPersistentData(entity).getDouble("jerotesvillage_variant_zsiein_discard") > 0) {
			Main.getJerotesPersistentData(entity).putDouble("jerotesvillage_variant_zsiein_discard", Main.getJerotesPersistentData(entity).getDouble("jerotesvillage_variant_zsiein_discard") - 1);
		}
		if (Main.getJerotesPersistentData(entity).getDouble("jerotesvillage_bright_land_heart") > 0) {
			Main.getJerotesPersistentData(entity).putDouble("jerotesvillage_bright_land_heart", Main.getJerotesPersistentData(entity).getDouble("jerotesvillage_bright_land_heart") - 1);
		}
        //计时
		Main.persistentDataDoubleReduceToZero(entity, "jerotesvillage_mountain_realm_flag_spear", true);
	}

	//玩家
	@SubscribeEvent
	public static void Player(LivingEvent.LivingTickEvent event) {
		LivingEntity entity = event.getEntity();
		if (entity == null || !entity.isAlive() || !(entity instanceof Player player))
			return;
		glowShine(player, true);
		glowShine(player, false);

		boolean bl = player.level().getBlockState(player.getOnPos().above()).is(JerotesVillageBlocks.SECOND_ROUND_WORLD_PORTAL_BLOCK.get())
                || player.level().getBlockState(player.getOnPos().above()).is(JerotesVillageBlocks.PIGLIN_SECOND_ROUND_WORLD_PORTAL_BLOCK.get());
        for (Direction direction : Direction.values()) {
			BlockPos adjacentPos = player.getOnPos().above().relative(direction);
			if (player.level().getBlockState(adjacentPos).is(JerotesVillageBlocks.SECOND_ROUND_WORLD_PORTAL_BLOCK.get())
			|| player.level().getBlockState(adjacentPos).is(JerotesVillageBlocks.PIGLIN_SECOND_ROUND_WORLD_PORTAL_BLOCK.get())) {
				bl = true;
			}
		}
		if (bl) {
			Main.persistentDataDoubleAddTo(entity, "jerotesvillage_second_rounder_portal", 20);
		}
		else {
			Main.persistentDataDoubleReduceToZero(entity, "jerotesvillage_second_rounder_portal", true);
		}
	}
	
	public static void glowShine(Player player, boolean bl) {
		CompoundTag compoundTag = bl ? player.getShoulderEntityLeft() : player.getShoulderEntityRight();
		EntityType.byString(compoundTag.getString("id")).filter(entityType -> entityType == JerotesVillageEntityType.GLOW_SPARROW.get()).ifPresent(entityType -> {
			if (!player.level().isClientSide) {
				player.addEffect(new MobEffectInstance(JerotesVillageMobEffects.GLOW_SPARROW_BLESSING.get(), 25, 0, false, false), player);
			}
		});
	}

	//泽林变体
	@SubscribeEvent
	public static void onWorldTick(TickEvent.LevelTickEvent event) {
//		if (event.phase == TickEvent.Phase.END && !event.level.isClientSide) {
//			ServerLevel serverWorld = (ServerLevel) event.level;
//
//			serverWorld.getEntities(JerotesVillageEntityType.VARIANT_ZSIEIN.get(), entity -> true).forEach((OldVariantZsieinEntity variantZsiein) -> {
//				// 对每个实体进行操作
//				ChunkPos chunkPos = new ChunkPos(variantZsiein.blockPosition());
//
//				// 确保区块已加载
//				serverWorld.getChunkSource().getChunk(chunkPos.x, chunkPos.z, ChunkStatus.FULL, true);
//
//				// 为区块添加加载票
//				serverWorld.getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, chunkPos, 1, variantZsiein.getId());
//			});
//		}
	}
	@SubscribeEvent
	public static void VariantZsieinDiscard(LivingEvent.LivingTickEvent event) {
		LivingEntity entity = event.getEntity();
		if (entity == null)
			return;
		if (!(entity instanceof Player) && !OtherEntityFactionFind.isZsiein(entity.getType())) {
			if (Main.getJerotesPersistentData(entity).getDouble("jerotesvillage_variant_zsiein_discard") >= 6000 && !OtherEntityAndItemFind.isZsieinVariantImmune(entity)) {
				for (int i = 0; i < 60; ++i) {
					entity.level().addParticle(ParticleTypes.ASH, entity.getRandomX(1.5), entity.getRandomY(), entity.getRandomZ(1.5), 0.0, 0.0, 0.0);
				}
				DamageSource damageSources = AttackFind.findDamageType(entity, DamageTypes.GENERIC_KILL, entity);
				entity.hurt(entity.damageSources().genericKill(), Float.MAX_VALUE);
				entity.hurt(damageSources, Float.MAX_VALUE);
				entity.setRemoved(Entity.RemovalReason.CHANGED_DIMENSION);
				if (entity instanceof Mob mob) {
					mob.setNoAi(true);
					mob.setTarget(mob);
					mob.setLastHurtByMob(mob);
					mob.setAggressive(false);
					mob.getNavigation().stop();
					if (!mob.level().isClientSide) {
						mob.goalSelector.setControlFlag(Goal.Flag.MOVE, false);
						mob.goalSelector.setControlFlag(Goal.Flag.JUMP, false);
						mob.goalSelector.setControlFlag(Goal.Flag.LOOK, false);
					}
				}
			}
		}
	}
	@SubscribeEvent
	public static void VariantZsieinHurt(LivingAttackEvent event) {
		LivingEntity livingEntity = event.getEntity();
		Entity attackBy = event.getSource().getEntity();
		if (livingEntity == null || !livingEntity.isAlive())
			return;
		if (livingEntity instanceof OldVariantZsieinEntity variantZsiein) {
			if (variantZsiein.getInvulnerableTick() < 30 || !(attackBy instanceof LivingEntity))
				event.setCanceled(true);
		}
	}
	@SubscribeEvent
	public static void VariantZsieinHurt(LivingHurtEvent event) {
		LivingEntity livingEntity = event.getEntity();
		Entity attackBy = event.getSource().getEntity();
		if (livingEntity == null || !livingEntity.isAlive())
			return;
		if (livingEntity instanceof OldVariantZsieinEntity variantZsiein) {
			if (variantZsiein.getInvulnerableTick() < 30 || !(attackBy instanceof LivingEntity))
				event.setCanceled(true);
		}
	}
	@SubscribeEvent
	public static void VariantZsieinLook(LivingHealEvent event) {
		LivingEntity livingEntity = event.getEntity();
		if (!(livingEntity instanceof Mob mob))
			return;
		if (mob.getTarget() instanceof OldVariantZsieinEntity) {
			event.setCanceled(true);
		}
	}
	@SubscribeEvent
	public static void VariantZsieinAttack(LivingAttackEvent event) {
		LivingEntity livingEntity = event.getEntity();
		if (!(livingEntity instanceof OldVariantZsieinEntity))
			return;
		event.setCanceled(false);
	}

	@SubscribeEvent
	public static void Fall(LivingFallEvent event) {
		LivingEntity livingEntity = event.getEntity();
		if (livingEntity == null)
			return;
		boolean zsiein = OtherEntityFactionFind.isZsiein(livingEntity.getType());
		if (!zsiein && WorldEvent.ZsieinModerateInfectedAbove(livingEntity.level()) && !OtherEntityAndItemFind.isZsieinAboutImmune(livingEntity)) {
			event.setDistance(event.getDistance() * 2);
		}
	}

	@SubscribeEvent
	public static void ZsieinAboutHurt(LivingHurtEvent event) {
		LivingEntity livingEntity = event.getEntity();
		if (livingEntity == null || !livingEntity.isAlive())
			return;
		boolean zsiein = OtherEntityFactionFind.isZsiein(livingEntity.getType());
		if (!zsiein && WorldEvent.ZsieinModerateInfectedAbove(livingEntity.level()) && event.getSource().is(DamageTypeTags.IS_FALL) && !OtherEntityAndItemFind.isZsieinAboutImmune(livingEntity)) {
			event.setAmount(event.getAmount() * 10);
		}
		if (AttackFind.MagicResistance(event.getSource()) && WorldEvent.ZsieinExtremelyInfectedAbove(livingEntity.level())) {
			event.setAmount(0);
			event.setCanceled(true);
		}
	}
}