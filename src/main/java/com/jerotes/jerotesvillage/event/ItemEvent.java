package com.jerotes.jerotesvillage.event;

import com.jerotes.jerotes.entity.Interface.FireAbsorptionEntity;
import com.jerotes.jerotes.forge.JerotesMerorDamageEvent;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.item.Tool.ItemToolBaseBandage;
import com.jerotes.jerotes.item.Tool.ItemToolBaseDagger;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicShoot.RuinsWindChargeEntity;
import com.jerotes.jerotesvillage.init.*;
import com.jerotes.jerotesvillage.item.*;
import com.jerotes.jerotesvillage.item.Tool.ItemToolBaseThrowingBall;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = JerotesVillage.MODID)
public class ItemEvent {
	@SubscribeEvent
	public static void addWeaponEffect(LivingAttackEvent event) {
		LivingEntity entity = event.getEntity();
		Entity attackBy = event.getSource().getEntity();
		float amount = event.getAmount();
		if (entity == null || !entity.isAlive())
			return;
		if (attackBy != null) {
			ItemStack handItem = null;
			ItemStack otherHandItem = null;
			if (attackBy instanceof LivingEntity living) {
				handItem = living.getMainHandItem();
				otherHandItem = living.getOffhandItem();
				if (!AttackFind.MeleeDamageFromMainHandNotOffHand(living)) {
					handItem = living.getOffhandItem();
					otherHandItem = living.getMainHandItem();
				}
			}
			if (attackBy instanceof LivingEntity living && living != entity && (event.getSource().getDirectEntity() == living || event.getSource().getDirectEntity() == null) && AttackFind.isMeleeDamage(event.getSource())) {
				if (living.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
					float baseDamage = (float) living.getAttributeValue(Attributes.ATTACK_DAMAGE);
					if (baseDamage <= 0)
						return;
					//苦寒之锤
					if (handItem.getItem() == JerotesVillageItems.BITTER_COLD_HAMMER.get() && !event.getSource().is(JerotesVillageDamageTypes.BITTER_COLD_FALL_ATTACK)) {
						//旧风爆实现
//						living.level().explode(living, living.getX(), living.getY() - 1, living.getZ(), 5.0f, false,
//								Level.ExplosionInteraction.MOB);
//						living.level().explode(living, null, RuinsWindChargeEntity.EXPLOSION_DAMAGE_CALCULATOR,
//								living.getX(), living.getY() - 0.1, living.getZ(), (float) (3.0 + RandomSource.create().nextDouble()), false,
//								Level.ExplosionInteraction.BLOW,
//								JerotesVillageParticleTypes.GUST.get(),
//								ParticleTypes.GUST_EMITTER,
//								JerotesSoundEvents.WIND_BURST);
						if (living.fallDistance > 1.5f && !living.isFallFlying()) {
							//粒子音效
							if (living.level() instanceof ServerLevel serverLevel) {
								if (entity.onGround()) {
									Vec3 vec3 = entity.getOnPos().getCenter().add(0.0, 0.5, 0.0);
									int n = (int) (50.0f * living.fallDistance);
									BlockState blockState = living.level().getBlockState(living.getOnPos());
									serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockState), vec3.x, vec3.y, vec3.z, n, 0.3, 0.3, 0.3, 0.15);
									serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, JerotesVillageBlocks.ICE_ROCK.get().defaultBlockState()), vec3.x, vec3.y, vec3.z, n, 0.3, 0.3, 0.3, 0.15);
									SoundEvent soundEvent = living.fallDistance > 5.0f ? JerotesVillageSoundEvents.MACE_SMASH_GROUND_HEAVY : JerotesVillageSoundEvents.MACE_SMASH_GROUND;
									if (!living.isSilent()) {
										serverLevel.playSound(null, living.getX(), living.getY(), living.getZ(), soundEvent, SoundSource.NEUTRAL, 1.0f, 1.0f);
									}
								} else {
									if (!living.isSilent()) {
										serverLevel.playSound(null, living.getX(), living.getY(), living.getZ(), JerotesVillageSoundEvents.MACE_SMASH_AIR, SoundSource.NEUTRAL, 1.0f, 1.0f);
									}
								}
								BlockPos blockPos = entity.getOnPos();
								Vec3 vec33 = blockPos.getCenter().add(0.0, 0.5, 0.0);
								serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, serverLevel.getBlockState(blockPos)), vec33.x, vec33.y, vec33.z, 32, 0.3, 0.3, 0.3, 0.15);
								serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, vec33.x, entity.getRandomY(), vec33.z, 32, 0.3, 0.3, 0.3, 0.15);

							}
							//击退
							List<LivingEntity> list = living.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(2.5, 2.5, 2.5));
							for (LivingEntity livingEntity : list) {
								if (livingEntity == null) continue;
								if (AttackFind.FindCanNotAttack(living, livingEntity, entity)) continue;
								if (AttackFind.FindCanNotAttack(living, livingEntity)) continue;
								Vec3 vec3 = livingEntity.position().subtract(entity.position());
								double d = (2.5 - vec3.length()) * 0.6 * (1.0 - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
								if (!Main.mobSizeGiant(livingEntity)) {
									d += 0.5f;
								}
								Vec3 vec32 = vec3.normalize().scale(d);
								if (d > 0.0) {
									livingEntity.push(vec32.x, 0.6, vec32.z);
									if (living.level() instanceof ServerLevel serverLevel) {
										BlockPos blockPos = livingEntity.getOnPos();
										Vec3 vec33 = blockPos.getCenter().add(0.0, 0.5, 0.0);
										int n = (int) (100.0 * d);
										serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, serverLevel.getBlockState(blockPos)), vec33.x, vec33.y, vec33.z, n, 0.3, 0.3, 0.3, 0.15);
										serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, JerotesVillageBlocks.ICE_ROCK.get().defaultBlockState()), vec3.x, vec3.y, vec3.z, n, 0.3, 0.3, 0.3, 0.15);
										serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, vec33.x, entity.getRandomY(), vec33.z, 16, 0.3, 0.3, 0.3, 0.15);
									}
								}
							}
							//粒子
							for (int j = 0; j < 5; j++) {
								for (int i = 0; i < 24; i++) {
									double angle = (Math.PI * 2 * i) / 24;
									double radius = Main.mobWidth(entity) * 1.25f;
									double offsetX = Math.cos(angle) * radius;
									double offsetZ = Math.sin(angle) * radius;
									entity.level().addParticle(ParticleTypes.SNOWFLAKE, entity.getX() + offsetX, entity.getY(0) + j * 0.25, entity.getZ() + offsetZ, 0, 0.12, 0);
									double angle2 = (Math.PI * 2 * i) / 24;
									double radius2 = Main.mobWidth(entity) * 2.75f;
									double offsetX2 = Math.cos(angle2) * radius2;
									double offsetZ2 = Math.sin(angle2) * radius2;
									entity.level().addParticle(ParticleTypes.SNOWFLAKE, entity.getX() + offsetX2, entity.getY(0) + j * 0.15, entity.getZ() + offsetZ2, 0, 0.12, 0);
								}
							}

							//伤害
							DamageSource damageSource = AttackFind.findDamageType(living, JerotesVillageDamageTypes.BITTER_COLD_FALL_ATTACK, living);
							float damage =
									(float) (Math.min(6, 2 * living.fallDistance) +
											Math.min(8, living.fallDistance) + (1 + 0.5 * 0) * living.fallDistance);
							//额外致密Ⅴ
							damage += 2.5f * living.fallDistance;
							boolean attacks = amount > living.getAttributeValue(Attributes.ATTACK_DAMAGE);
							boolean crit = living instanceof Player player && attacks && player.fallDistance > 0.0f && !player.onGround() && !player.onClimbable() && !player.isInWater() && !player.hasEffect(MobEffects.BLINDNESS) && !player.isPassenger() && entity instanceof LivingEntity && !player.isSprinting();
							if (crit) {
								damage *= 1.5f;
							}
							boolean bl = entity.hurt(damageSource, amount + damage);
							if (bl) {
								entity.knockback(0.5, Mth.sin(living.getYRot() * ((float) Math.PI / 180F)), (-Mth.cos(living.getYRot() * ((float) Math.PI / 180F))));
								living.setLastHurtMob(entity);
							}
							living.setDeltaMovement(living.getDeltaMovement().with(Direction.Axis.Y, 0.01));
							living.resetFallDistance();
							//风爆
							living.setOnGround(false);
							living.setDeltaMovement(living.getDeltaMovement().add(0, 1f, 0));
							RuinsWindChargeEntity.explode(living, living.position());
						}
					}

					//火焰使徒剑
					if (handItem.getItem() == JerotesVillageItems.FIRE_APOSTLE_SWORD.get() && !event.getSource().is(JerotesDamageTypes.FIRE_MELEE) && !event.getSource().is(DamageTypes.MOB_ATTACK_NO_AGGRO)) {
						DamageSource damageSource = AttackFind.findDamageType(living, JerotesDamageTypes.FIRE_MELEE, living);
						if (!(entity instanceof FireAbsorptionEntity) &&
								!(entity.isInvulnerableTo(damageSource)) &&
								!entity.fireImmune() &&
								!(entity.hasEffect(JerotesMobEffects.FIRE_ABSORPTION.get())) &&
								!(entity.hasEffect(MobEffects.FIRE_RESISTANCE))) {
							float fireDamage = amount * 1.5f;
							if (entity.hurt(damageSource, fireDamage)) {
								event.setCanceled(true);
							}
							entity.setSecondsOnFire(12);
						}
					}
					//莫厄匕首
					if (handItem.getItem() instanceof MerorDagger) {
						if (living.isShiftKeyDown()) {
							float damage = 1.5f;
							//偷袭
							if (entity instanceof Mob mob && !mob.isAggressive() && mob.getTarget() == null) {
								damage *= 1.25f;
							}
							//背刺
							if (!Main.canSee(living, entity)) {
								damage *= 1.25f;
							}
							//非落地
							if (!living.onGround()) {
								damage *= 0.5f;
							}
							//副手匕首
							if (otherHandItem.getItem() instanceof ItemToolBaseDagger) {
								damage *= 1.15f;
							}
							//体型
							if (Main.mobSizeSmall(entity)) {
								damage *= 1.15f;
							}
							if (Main.mobSizeGiant(entity)) {
								damage *= 0.75f;
							}
							//满血
							if (entity.getHealth() >= entity.getMaxHealth()) {
								damage *= 1.25f;
							}
							//残血
							if (entity.getHealth() <= entity.getMaxHealth() / 5) {
								damage *= 1.25f;
							}
							//类人
							if (EntityFactionFind.isHumanoid(entity)) {
								damage *= 1.15f;
							}
							//隐身
							if (living.isInvisible()) {
								damage *= 1.25f;
							}
							//无视力
							if (EntityAndItemFind.targetBlindnessTrue(entity)) {
								damage *= 1.15f;
							}
							if (entity.getHealth() >= entity.getMaxHealth() && handItem.getItem() == JerotesVillageItems.MEROR_DAGGER.get() && !event.getSource().is(DamageTypes.MOB_ATTACK_NO_AGGRO)) {
								event.setCanceled(true);
								DamageSource damageSource = AttackFind.findDamageType(living, DamageTypes.MOB_ATTACK_NO_AGGRO, living);
								AttackFind.attackBegin(living, entity);
								float damageCount = DaggerCount(amount * damage, amount);
								if (Float.isNaN(damageCount) || Float.isInfinite(damageCount)) {
									damageCount = 0f;
								}
								AttackFind.attackAfterCustomDamage(living, entity, damageSource, 0f, 1f, true, damageCount);
								if (otherHandItem.getItem() instanceof ItemToolBaseDagger itemToolBaseDagger) {
									itemToolBaseDagger.attackEffectUse(living, entity, otherHandItem, damageCount > 0);
								}
							}
						}

					}
				}
			}
		}
	}

	public static float DaggerCount(float fs, float baseDamage) {
		final float baseMult = fs / baseDamage;
		final float softCap = 100f;
		final float hardCap = 300f;
		final float maxOutput = 500f;

		if (baseDamage > softCap) {
			float decay = 0.7f - 0.2f * (baseDamage / hardCap);
			float scaled = softCap * baseMult;

			float bonus = (float) (Math.log(baseDamage + 1) / Math.log(softCap)) * scaled * decay;
			return Math.min(Math.max(bonus, scaled), maxOutput + baseDamage * 0.2f);
		}
		return Math.min(baseDamage * baseMult, maxOutput);
	}

	@SubscribeEvent
	public static void addWeaponDamage(LivingDamageEvent event) {
		LivingEntity entity = event.getEntity();
		Entity attackBy = event.getSource().getEntity();
		float amount = event.getAmount();
		if (entity == null || !entity.isAlive())
			return;
		if (attackBy != null) {
			ItemStack handItem = null;
			ItemStack otherHandItem = null;
			if (attackBy instanceof LivingEntity living) {
				handItem = living.getMainHandItem();
				otherHandItem = living.getOffhandItem();
				if (!AttackFind.MeleeDamageFromMainHandNotOffHand(living)) {
					handItem = living.getOffhandItem();
					otherHandItem = living.getMainHandItem();
				}
			}
			if (attackBy instanceof LivingEntity living) {
				if (((attackBy instanceof Mob || attackBy instanceof Player)) && event.getSource().getDirectEntity() == living && AttackFind.isMeleeDamage(event.getSource())) {
					if (living.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
						float baseDamage = (float) living.getAttributeValue(Attributes.ATTACK_DAMAGE);
						if (baseDamage <= 0)
							return;
						//恶鳞鱼叉/恶鳞饵叉
						if (handItem.getItem() == JerotesVillageItems.MALIGNASAUR_FISHGIG.get() || handItem.getItem() == JerotesVillageItems.MALIGNASAUR_BAIT_FISHGIG.get()) {
							if (entity.getMobType() == MobType.WATER) {
								float newAmount = amount + amount / baseDamage * 5;
								if (Float.isNaN(newAmount) || Float.isInfinite(newAmount)) {
									newAmount = 0f;
								}
								event.setAmount(newAmount);
							}
						}
						//莫厄火矛
						if (handItem.getItem() == JerotesVillageItems.MEROR_FIRE_SPEAR.get()) {
							if (entity.isOnFire()) {
								float newAmount = amount + amount / baseDamage * 8;
								if (Float.isNaN(newAmount) || Float.isInfinite(newAmount)) {
									newAmount = 0f;
								}
								event.setAmount(newAmount);
							}
							if (!entity.isOnFire()) {
								entity.setSecondsOnFire(24);
							}
						}
						//明路巨兽角
						if (handItem.getItem() == JerotesVillageItems.BRIGHT_LAND_BEAST_HORN.get()) {
							float hurtBrightLandBeastHorn = (Main.mobWidth(entity) * 4 * Main.mobHeight(entity));
							if (hurtBrightLandBeastHorn > baseDamage * 10) {
								hurtBrightLandBeastHorn = (baseDamage * 10);
							}
							float newAmount;
							if (amount / 2 * hurtBrightLandBeastHorn < 2) {
								newAmount = amount + 2;
							} else {
								newAmount = amount + amount / baseDamage * hurtBrightLandBeastHorn;
							}
							if (Float.isNaN(newAmount) || Float.isInfinite(newAmount)) {
								newAmount = 0f;
							}
							event.setAmount(newAmount);
						}
						//幸运棒槌
						if (handItem.getItem() == JerotesVillageItems.LUCKY_CLUB.get()) {
							double luck = Main.luck(living) * 3;
							if (Main.luck(living) > 0) luck += 7;
							if (Main.luck(living) > 1) luck += 5;
							if (Main.luck(living) > 2) luck += 4;
							float addDamage = (float) (Main.randomReach(living.getRandom(), 1, (int) (20 + luck)) + luck / 2) / 2;
							for (int i = 0; i < addDamage; ++i) {
								living.level().addParticle(ParticleTypes.CHERRY_LEAVES,
										living.getRandomX(0.5f), living.getRandomY(), living.getRandomZ(0.5f), 0.0, 0.0, 0.0);
							}
							float newAmount = amount + (amount / baseDamage) * addDamage;
							if (Float.isNaN(newAmount) || Float.isInfinite(newAmount)) {
								newAmount = 0f;
							}
							event.setAmount(newAmount);
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void addWeaponEffectSelf(LivingEvent.LivingTickEvent event) {
		LivingEntity entity = event.getEntity();
		if (entity == null || !entity.isAlive())
			return;
		//莫厄匕首
		if (entity.getOffhandItem().getItem() == JerotesVillageItems.MEROR_DAGGER.get()) {
			if (!entity.level().isClientSide) {
				entity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.MEROR_DAGGER.get(), 25, 0, false, false));
			}
		}
		//投团
		if (entity.getMainHandItem().getItem() instanceof ItemToolBaseThrowingBall itemToolBaseThrowingBall) {
			itemToolBaseThrowingBall.addEffects(entity);
		}
		if (entity.getOffhandItem().getItem() instanceof ItemToolBaseThrowingBall itemToolBaseThrowingBall) {
			itemToolBaseThrowingBall.addEffects(entity);
		}

		if (entity.getMainHandItem().getItem() instanceof StubbornStoneCane stubbornStoneCane) {
			stubbornStoneCane.addEffects(entity);
		}
		if (entity.getOffhandItem().getItem() instanceof StubbornStoneCane stubbornStoneCane) {
			stubbornStoneCane.addEffects(entity);
		}

		if (entity.getMainHandItem().getItem() instanceof ItemBaseGiveEffect itemBaseGiveEffect) {
			if (!entity.level().isClientSide()) {
				entity.addEffect(new MobEffectInstance(itemBaseGiveEffect.mobEffect, itemBaseGiveEffect.time, itemBaseGiveEffect.level, false, false));
			}
		}
		if (entity.getOffhandItem().getItem() instanceof ItemBaseGiveEffect itemBaseGiveEffect) {
			if (!entity.level().isClientSide()) {
				entity.addEffect(new MobEffectInstance(itemBaseGiveEffect.mobEffect, itemBaseGiveEffect.time, itemBaseGiveEffect.level, false, false));
			}
		}
		if (entity.getMainHandItem().getItem() instanceof SerponCredential) {
			if (!entity.level().isClientSide) {
				if (!(entity.hasEffect(JerotesVillageMobEffects.SERPON_CREDENTIAL.get()) && entity.getEffect(JerotesVillageMobEffects.SERPON_CREDENTIAL.get()).getAmplifier() > 0)) {
					entity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.SERPON_CREDENTIAL.get(), 365, 0, false, false));
				}
			}
		}
		if (entity.getOffhandItem().getItem() instanceof SerponCredential) {
			if (!entity.level().isClientSide) {
				if (!(entity.hasEffect(JerotesVillageMobEffects.SERPON_CREDENTIAL.get()) && entity.getEffect(JerotesVillageMobEffects.SERPON_CREDENTIAL.get()).getAmplifier() > 0)) {
					entity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.SERPON_CREDENTIAL.get(), 365, 0, false, false));
				}
			}
		}

		//鬼婆之眼
		if (entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof HagEye) {
			if (entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof HagEye) {
				if (!entity.level().isClientSide()) {
					entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 5, 0, false, false), entity);
				}
				if (entity.hasEffect(MobEffects.BLINDNESS) || entity.hasEffect(MobEffects.DARKNESS)) {
					if (!entity.level().isClientSide()) {
						entity.removeEffect(MobEffects.BLINDNESS);
						entity.removeEffect(MobEffects.DARKNESS);
					}
				}
			}
		}
		if (ModList.get().isLoaded("curios")) {
			if (hasCurio(entity, JerotesVillageItems.HAG_EYE.get())) {
				if (!entity.level().isClientSide()) {
					entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 255, 0, false, false), entity);
				}
				if (entity.hasEffect(MobEffects.BLINDNESS) || entity.hasEffect(MobEffects.DARKNESS)) {
					if (!entity.level().isClientSide()) {
						entity.removeEffect(MobEffects.BLINDNESS);
						entity.removeEffect(MobEffects.DARKNESS);
					}
				}
			}
		}
		//紫沙鬼婆之眼
		if (entity.getMainHandItem().getItem() instanceof PurpleSandHagEye ||
				entity.getOffhandItem().getItem() instanceof PurpleSandHagEye ||
				entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof PurpleSandHagEye) {
			if (entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof PurpleSandHagEye) {
				if (!entity.level().isClientSide()) {
					entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 5, 0, false, false), entity);
				}
				if (entity.hasEffect(MobEffects.BLINDNESS) || entity.hasEffect(MobEffects.DARKNESS)) {
					if (!entity.level().isClientSide) {
						entity.removeEffect(MobEffects.BLINDNESS);
						entity.removeEffect(MobEffects.DARKNESS);
					}
				}
			}
			if (entity.isShiftKeyDown()) {
				if (!entity.level().isClientSide()) {
					entity.addEffect(new MobEffectInstance(JerotesMobEffects.TRUESIGHT.get(), 25, 1, false, false));
				}
			}
		}
		if (ModList.get().isLoaded("curios")) {
			if (hasCurio(entity, JerotesVillageItems.PURPLE_SAND_HAG_EYE.get())) {
				if (!entity.level().isClientSide()) {
					entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 255, 0, false, false), entity);
				}
				if (entity.hasEffect(MobEffects.BLINDNESS) || entity.hasEffect(MobEffects.DARKNESS)) {
					if (!entity.level().isClientSide()) {
						entity.removeEffect(MobEffects.BLINDNESS);
						entity.removeEffect(MobEffects.DARKNESS);
					}
				}
				if (entity.isShiftKeyDown()) {
					if (!entity.level().isClientSide()) {
						entity.addEffect(new MobEffectInstance(JerotesMobEffects.TRUESIGHT.get(), 25, 1, false, false));
					}
				}
			}
		}
		//二轮世界传送石
		if (entity instanceof Player player && player.getUseItem().getItem() instanceof SecondRoundWorldTeleportStone) {
			for (int i = 0; i < 32; ++i) {
				player.level().addParticle(ParticleTypes.PORTAL, player.getX(), player.getY() + player.getRandom().nextDouble() * 2.0, player.getZ(), player.getRandom().nextGaussian(), 0.0, player.getRandom().nextGaussian());
			}
			if (!player.level().isClientSide && player.getTicksUsingItem() > 20) {
				player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, player.getTicksUsingItem(), 0));
				player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, player.getTicksUsingItem(), 0));
				player.addEffect(new MobEffectInstance(MobEffects.HUNGER, player.getTicksUsingItem(), 0));
				player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, player.getTicksUsingItem(), 0));
			}
			float use = (float) player.getTicksUsingItem() / 120;
			if (use == 1) {
				if (!player.isSilent()) {
					player.playSound(JerotesSoundEvents.TELEPORT, 1.0F, 1.0F);
				}
			}
		}
		//绷带
		if (entity.getUseItem().getItem() instanceof ItemToolBaseBandage itemToolBaseBandage) {
			entity.setDeltaMovement(entity.getDeltaMovement().multiply(0.25d, 1d, 0.25d));
			float use = (float) entity.getTicksUsingItem() / itemToolBaseBandage.getUseTime();
			if (use == 1) {
				if (!entity.isSilent()) {
					entity.playSound(JerotesSoundEvents.USE_BANDAGE, 1.0F, 1.0F);
				}
			}
		}
	}

	public static ItemStack findCurio(LivingEntity livingEntity, Item item) {
		ItemStack foundStack = ItemStack.EMPTY;
		if (livingEntity != null) {
			if (ModList.get().isLoaded("curios")) {
				Optional<SlotResult> slotResult = CuriosApi.getCuriosInventory(livingEntity).map(inv -> inv.findFirstCurio(item))
						.orElse(Optional.empty());
				if (slotResult.isPresent()) {
					foundStack = slotResult.get().stack();
				}
			}
		}

		return foundStack;
	}

	public static boolean hasCurio(LivingEntity livingEntity, Item item) {
		return !findCurio(livingEntity, item).isEmpty();
	}

	//受伤
	@SubscribeEvent
	public static void hurts(LivingHurtEvent event) {
		LivingEntity entity = event.getEntity();
		//二轮世界传送石
		if (entity instanceof Player player && player.getUseItem().getItem() instanceof SecondRoundWorldTeleportStone stone) {
			player.stopUsingItem();
			player.getCooldowns().addCooldown(stone, 120);
			float newAmount = event.getAmount() * 4;
			if (Float.isNaN(newAmount) || Float.isInfinite(newAmount)) {
				newAmount = 0f;
			}
			event.setAmount(newAmount);
		}
	}


	//Tick
	@SubscribeEvent
	public static void ItemTickAbout(LivingEvent.LivingTickEvent event) {
		LivingEntity user = event.getEntity();
		if (user == null)
			return;
		//莫厄刺剑
		if (Main.getJerotesPersistentData(user).getDouble("jerotesvillage_start_meror_rapier_attack") > 0) {
			Main.getJerotesPersistentData(user).putDouble("jerotesvillage_start_meror_rapier_attack", Main.getJerotesPersistentData(user).getDouble("jerotesvillage_start_meror_rapier_attack") - 1);
			if ((Main.getJerotesPersistentData(user).getDouble("jerotesvillage_start_meror_rapier_attack") + 1) % 2 == 0) {
				user.swing(InteractionHand.MAIN_HAND, true);
				if (user.level() instanceof ServerLevel serverLevel) {
					if (!user.isSilent()) {
						user.level().playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, user.getSoundSource(), 10.0f, 1.0f);
					}
					List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, user.getBoundingBox().inflate(2, 1.6, 2));
					for (LivingEntity hurt : list) {
						if (hurt == null || hurt.distanceTo(user) > 16) continue;
						if (AttackFind.FindCanNotAttack(user, hurt)) continue;
						AttackFind.attackBegin(user, hurt);
						AttackFind.attackAfter(user, hurt, 1f, 0.5f, false, 0f);
					}
					double d = -Mth.sin(user.getYRot() * ((float) Math.PI / 180F));
					double d2 = Mth.cos(user.getYRot() * ((float) Math.PI / 180F));
					serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, user.getX() + d, user.getY(0.5), user.getZ() + d2, 0, d, 0.0, d2, 0.0);
					serverLevel.sendParticles(JerotesVillageParticleTypes.MEROR.get(), user.getRandomX(0.5), user.getRandomY(), user.getRandomZ(0.5), 5, d, 0.0, d2, 0.0);
				}
			}
		}
		//蛇神之刃
		if (Main.getJerotesPersistentData(user).getDouble("jerotesvillage_start_glaive_of_serpent_god_attack_cooldown") > 0) {
			Main.getJerotesPersistentData(user).putDouble("jerotesvillage_start_glaive_of_serpent_god_attack_cooldown", Main.getJerotesPersistentData(user).getDouble("jerotesvillage_start_glaive_of_serpent_god_attack_cooldown") - 1);
		}
		if (Main.getJerotesPersistentData(user).getDouble("jerotesvillage_start_glaive_of_serpent_god_attack") > 0) {
			Main.getJerotesPersistentData(user).putDouble("jerotesvillage_start_glaive_of_serpent_god_attack", Main.getJerotesPersistentData(user).getDouble("jerotesvillage_start_glaive_of_serpent_god_attack") - 1);
			if ((Main.getJerotesPersistentData(user).getDouble("jerotesvillage_start_glaive_of_serpent_god_attack") + 1) % 2 == 0 && user.getMainHandItem().getItem() instanceof GlaiveofSerpentDominater) {
				user.swing(InteractionHand.MAIN_HAND, true);
				if (user.level() instanceof ServerLevel serverLevel) {
					if (!user.isSilent()) {
						user.level().playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, user.getSoundSource(), 10.0f, 1.0f);
					}
					List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, user.getBoundingBox().inflate(2, 1.6, 2));
					for (LivingEntity hurt : list) {
						if (hurt == null || hurt.distanceTo(user) > 16) continue;
						if (AttackFind.FindCanNotAttack(user, hurt)) continue;
						AttackFind.attackBegin(user, hurt);
						DamageSource damageSource = AttackFind.findDamageType(user, JerotesDamageTypes.BYPASSES_ARMOR_MELEE, user);
						AttackFind.attackAfterCustomDamage(user, hurt, damageSource, 1f, 0.5f, false, 0f);
					}
					double d = -Mth.sin(user.getYRot() * ((float) Math.PI / 180F));
					double d2 = Mth.cos(user.getYRot() * ((float) Math.PI / 180F));
					serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, user.getX() + d, user.getY(0.5), user.getZ() + d2, 0, d, 0.0, d2, 0.0);
					for (int i = 0; i < 8; ++i) {
						serverLevel.sendParticles(ParticleTypes.FLAME, user.getRandomX(0.3), user.getRandomY(), user.getRandomZ(0.3), 2, d, 0.0, d2, 0.0);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void ItemTickAboutDeath(LivingDeathEvent event) {
		LivingEntity entity = event.getEntity();
		if (entity == null)
			return;
		//莫厄刺剑
		if (Main.getJerotesPersistentData(entity).getDouble("jerotesvillage_start_meror_rapier_attack") > 0) {
			Main.getJerotesPersistentData(entity).putDouble("jerotesvillage_start_meror_rapier_attack", 0);
		}
		//蛇神之刃
		if (Main.getJerotesPersistentData(entity).getDouble("jerotesvillage_start_glaive_of_serpent_god_attack") > 0) {
			Main.getJerotesPersistentData(entity).putDouble("jerotesvillage_start_glaive_of_serpent_god_attack", 0);
		}
		if (Main.getJerotesPersistentData(entity).getDouble("jerotesvillage_start_glaive_of_serpent_god_attack_cooldown") > 0) {
			Main.getJerotesPersistentData(entity).putDouble("jerotesvillage_start_glaive_of_serpent_god_attack_cooldown", 0);
		}
	}


	@SubscribeEvent
	public static void CanNotUseItem(PlayerInteractEvent.RightClickItem event) {
		Player player = event.getEntity();
		ItemStack itemStack = event.getItemStack();
		if (player == null)
			return;
		if (player.getMainHandItem().getItem() instanceof VillagerMetalSyringe) {
			if (itemStack.getItem() instanceof ItemTabletBase) {
				event.setCanceled(true);
			}
			else if (itemStack.getItem() instanceof PotionItem) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void TaczAboutMerorDamage(JerotesMerorDamageEvent event) {
		DamageSource damageSource = event.getDamageSource();
		if (damageSource == null)
			return;

		if (ModList.get().isLoaded("tacz")) {
			try {
				Class<?> entityKineticBulletClass = Class.forName("com.tacz.guns.entity.EntityKineticBullet");
				Class<?> modernKineticGunItemClass = Class.forName("com.tacz.guns.item.ModernKineticGunItem");

				if (damageSource.getDirectEntity() != null) {
					if (entityKineticBulletClass.isInstance(damageSource.getDirectEntity())) {
						Object bullet = damageSource.getDirectEntity();
						Method getAmmoIdMethod = entityKineticBulletClass.getMethod("getAmmoId");
						ResourceLocation ammoId = (ResourceLocation) getAmmoIdMethod.invoke(bullet);

						if (ammoId.toString().equals("merorsenergeticgun:meror_energy_bottles")) {
							event.setMerorDamage(true);
						}
					}
				}

				if (AttackFind.isMeleeDamage(damageSource) && damageSource.getEntity() instanceof LivingEntity livingEntity) {
					ItemStack handItem = livingEntity.getMainHandItem();
					ItemStack otherHandItem = livingEntity.getOffhandItem();
					if (!AttackFind.MeleeDamageFromMainHandNotOffHand(livingEntity)) {
						handItem = livingEntity.getOffhandItem();
						otherHandItem = livingEntity.getOffhandItem();
					}
					if (modernKineticGunItemClass.isInstance(handItem.getItem())) {
						Object gunItem = handItem.getItem();
						Method getGunIdMethod = modernKineticGunItemClass.getMethod("getGunId", ItemStack.class);
						ResourceLocation gunId = (ResourceLocation) getGunIdMethod.invoke(gunItem, handItem);

						String string = gunId.toString();
						if (string.equals("merorsenergeticgun:crimson_cunpowder_storm")
								|| string.equals("merorsenergeticgun:meror_guardian_pistol_iv")
								|| string.equals("merorsenergeticgun:star_breaker_assault_rifle")) {
							event.setMerorDamage(true);
						}
					}
				}
			} catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
					 InvocationTargetException e) {
				//
			}
		}
	}

	@SubscribeEvent
	public static void TaczAboutDamage(LivingAttackEvent event) {
		LivingEntity entity = event.getEntity();
		float amount = event.getAmount();
		if (entity == null || !entity.isAlive())
			return;
		if (ModList.get().isLoaded("tacz")) {
			try {
				Class<?> entityKineticBulletClass = Class.forName("com.tacz.guns.entity.EntityKineticBullet");
				if (event.getSource().getDirectEntity() != null) {
					if (entityKineticBulletClass.isInstance(event.getSource().getDirectEntity())) {
						if (entity.getUseItem().is(JerotesVillageItems.MEROR_RIOT_SHIELD.get()) && Main.canSee(event.getSource().getDirectEntity(), entity)) {
							event.setCanceled(true);
						}
					}
				}
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
	}
}