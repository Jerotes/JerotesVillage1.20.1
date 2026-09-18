package com.jerotes.jerotesvillage.spell;

import com.jerotes.jerotes.entity.Interface.EntityTrueBasePart;
import com.jerotes.jerotes.entity.Other.FallingBlock.JerotesEarthrendBlock;
import com.jerotes.jerotes.entity.Other.SpellCloud.RainSpellCloudEntity;
import com.jerotes.jerotes.entity.Other.SpellCloud.SpellCloudEntity;
import com.jerotes.jerotes.forge.JerotesStopSpellEvent;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.spell.SpellType;
import com.jerotes.jerotes.util.*;
import com.jerotes.jerotesvillage.entity.Animal.*;
import com.jerotes.jerotesvillage.entity.Boss.Biome.CelestialCoilvineEntity;
import com.jerotes.jerotesvillage.entity.Boss.SedimentLordEntity;
import com.jerotes.jerotesvillage.entity.Boss.TyrantOfTheMountainRealmEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Botanize.CoilvineBotanizeEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Bulge.BulgeEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Bulge.SeawaveEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Darisorder.DarisorderEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.DroughtBlaze.DroughtBlazeEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.DroughtBlaze.DroughtWildfireEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.FlyingFireball.FlyingFireballEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.IronTeeth.IronTeethEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.SecondRounderAnimatedArmor.SecondRounderAnimatedArmorEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.VinePodBomb.VinePodBombEntity;
import com.jerotes.jerotesvillage.entity.Monster.BitterColdWindEntity;
import com.jerotes.jerotesvillage.entity.Monster.Elite.FrostYetiEntity;
import com.jerotes.jerotesvillage.entity.Monster.Hag.CovenHagEntity;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.DefectorEntity;
import com.jerotes.jerotesvillage.entity.Monster.Resurrecter.ResurrecterEntity;
import com.jerotes.jerotesvillage.entity.Monster.Resurrecter.SedimentGuardEntity;
import com.jerotes.jerotesvillage.entity.Monster.SpirveEntity;
import com.jerotes.jerotesvillage.entity.Neutral.MerorMachine.MerorCubeEntity;
import com.jerotes.jerotesvillage.entity.Neutral.Wonderlin.BaseWonderlinEntity;
import com.jerotes.jerotesvillage.entity.Neutral.Wonderlin.WonderlinWarriorEntity;
import com.jerotes.jerotesvillage.entity.Other.BreathAreaSpell.BloodyScreamAreaEntity;
import com.jerotes.jerotesvillage.entity.Other.BreathAreaSpell.CorrosiverBreathAreaEntity;
import com.jerotes.jerotesvillage.entity.Other.BreathAreaSpell.OceanCurrentBreathAreaEntity;
import com.jerotes.jerotesvillage.entity.Other.BreathAreaSpell.VirosaansWindOfSlaughterEntity;
import com.jerotes.jerotesvillage.entity.Other.MagicAboutMob.*;
import com.jerotes.jerotesvillage.entity.Other.OtherSpell.*;
import com.jerotes.jerotesvillage.entity.Other.ShootTargetSpell.SerpenperthMeteorEntity;
import com.jerotes.jerotesvillage.entity.Other.ShootTargetSpell.TakeRootEntity;
import com.jerotes.jerotesvillage.entity.Other.ShootTargetSpell.VirosaansForceBurstBallEntity;
import com.jerotes.jerotesvillage.entity.Part.MalignasaurPart;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicThrow.VineboltEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.ThrownSerponJavelinEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.Breath.*;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.Cloud.CorrosiveSnowCloudEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.Cloud.UncleanBloodRainEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicBeam.ElectroflashEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicMissile.ArcaneLightSpotEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicMissile.SedimentMissileEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicMissile.VertigoDelugeEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicMissile.WiltMissileEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicShoot.*;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicSpray.BitterColdFrostbiteEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicSpray.OminousFlamesEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicSpray.SerponFlamesEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicThrow.*;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.Ray.*;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.Target.DeadCenterEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.Target.FloatingForceEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.Target.GravityForceEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.Target.IndomitableWillEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Other.IntenseBombingEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Other.MerorBombEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Other.ZsieinSonicEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageBiomes;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.util.OtherEntityFactionFind;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraftforge.common.MinecraftForge;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OtherSpellFind {
	//蚀龙兽吐息$法术
	public static boolean CorrosiverBreath(LivingEntity caster, Entity target, int spellLevelDamage, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, int spellLevelOtherEffectTime, int spellLevelOtherEffectLevel, float spellLevelAccuracy, int count, float distance, boolean isPlayer, int subcategory) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			if (subcategory == 0) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				//目标位置
				Vec3 look = caster.getLookAngle().normalize();
				Vec3 targetPos = caster.position()
						.add(0, caster.getBbHeight() * 0.25 + 2 / 16f, 0)
						.add(look.scale(2.25));

				CorrosiverBreathAreaEntity cloud = new CorrosiverBreathAreaEntity(serverLevel, caster);
				cloud.setSpellLevelDamage(spellLevelDamage);
				cloud.setPos(targetPos.x, targetPos.y, targetPos.z);
				cloud.setYRot(caster.getYRot());
				cloud.setXRot(caster.getXRot());
				cloud.setOwner(caster);
				cloud.setTarget(caster);
				serverLevel.addFreshEntity(cloud);
				serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
			}
			if (subcategory == 1) {
				CorrosiverBreathEntity breath;
				for (int i = 0; i < count; ++i) {
					if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
						mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
						mob.lookAt(target, 360.0f, 360.0f);
					}
					breath = new CorrosiverBreathEntity(spellLevelDamage, spellLevelMainEffectTime, spellLevelMainEffectLevel, spellLevelOtherEffectTime, spellLevelOtherEffectLevel, serverLevel, caster, caster.getLookAngle().x, caster.getLookAngle().y, caster.getLookAngle().z);
					breath.setPos(caster.getX(), caster.getY(0.7), caster.getZ());
					breath.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1f, spellLevelAccuracy);
					breath.setOwner(caster);
					serverLevel.addFreshEntity(breath);
				}
			}
		}
		return true;
	}
	//洋流吐息$法术
	public static boolean OceanCurrentBreath(LivingEntity caster, Entity target, Entity where, int spellLevelDamage, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, float spellLevelAccuracy, int count, float distance, boolean isPlayer, int subcategory) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			if (subcategory == 0) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				//目标位置
				Vec3 look = caster.getLookAngle().normalize();
				Vec3 targetPos = caster.position()
						.add(0, caster.getBbHeight() * 0.25 + 2 / 16f, 0)
						.add(look.scale(2.25));

				OceanCurrentBreathAreaEntity cloud = new OceanCurrentBreathAreaEntity(serverLevel, caster);
				cloud.setSpellLevelDamage(spellLevelDamage);
				cloud.setPos(targetPos.x, targetPos.y, targetPos.z);
				cloud.setYRot(caster.getYRot());
				cloud.setXRot(caster.getXRot());
				cloud.setOwner(caster);
				cloud.setTarget(caster);
				serverLevel.addFreshEntity(cloud);
				serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
			}
			if (subcategory == 1) {
				OceanCurrentBreathEntity breath;
				for (int i = 0; i < count; ++i) {
					if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
						mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
						mob.lookAt(target, 360.0f, 360.0f);
					}
					if (!isPlayer && target != caster && where instanceof MalignasaurPart basePartEntity) {
						if (target != null) {
							basePartEntity.lookAt(target, 360.0f, 360.0f);
						}
					}
					breath = new OceanCurrentBreathEntity(spellLevelDamage, spellLevelMainEffectTime, spellLevelMainEffectLevel, serverLevel, caster, where.getLookAngle().x, where.getLookAngle().y, where.getLookAngle().z);
					breath.setPos(where.getX(), where.getY(0.7), where.getZ());
					if (where instanceof MalignasaurPart basePartEntity) {
						breath.setPos(where.getX(), where.getY(0.3), where.getZ());
					}
					breath.shootFromRotation(caster, where.getXRot(), (where.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1f, spellLevelAccuracy);
					breath.setOwner(caster);
					serverLevel.addFreshEntity(breath);
				}
			}
		}
		return true;
	}
	//血腥尖啸$法术
	public static boolean BloodyScream(LivingEntity caster, Entity target, int spellLevelDamage, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, float spellLevelAccuracy, int count, float distance, boolean isPlayer, int subcategory) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			if (subcategory == 0) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				//目标位置
				Vec3 look = caster.getLookAngle().normalize();
				Vec3 targetPos = caster.position()
						.add(0, caster.getBbHeight() * 0.25 + 2 / 16f, 0)
						.add(look.scale(2.25));

				BloodyScreamAreaEntity cloud = new BloodyScreamAreaEntity(serverLevel, caster);
				cloud.setSpellLevelDamage(spellLevelDamage);
				cloud.setPos(targetPos.x, targetPos.y, targetPos.z);
				cloud.setYRot(caster.getYRot());
				cloud.setXRot(caster.getXRot());
				cloud.setOwner(caster);
				cloud.setTarget(caster);
				serverLevel.addFreshEntity(cloud);
				serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
			}
			if (subcategory == 1) {
				BloodyScreamEntity breath;
				for (int i = 0; i < count; ++i) {
					if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
						mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
						mob.lookAt(target, 360.0f, 360.0f);
					}
					breath = new BloodyScreamEntity(spellLevelDamage, spellLevelMainEffectTime, spellLevelMainEffectLevel, serverLevel, caster, caster.getLookAngle().x, caster.getLookAngle().y, caster.getLookAngle().z);
					breath.setPos(caster.getX(), caster.getY(0.7), caster.getZ());
					breath.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1f, spellLevelAccuracy);
					breath.setOwner(caster);
					serverLevel.addFreshEntity(breath);
				}
			}
		}
		return true;
	}
	//维落萨恩的杀戮之风$法术
	public static boolean VirosaansWindOfSlaughter(LivingEntity caster, Entity target, int spellLevelDamage, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
				mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
				mob.lookAt(target, 360.0f, 360.0f);
			}
			//目标位置
			Vec3 look = caster.getLookAngle().normalize();
			Vec3 targetPos = caster.position()
					.add(0, caster.getBbHeight() * 0.25 + 2 / 16f, 0)
					.add(look.scale(2.25));

			VirosaansWindOfSlaughterEntity cloud = new VirosaansWindOfSlaughterEntity(serverLevel, caster);
			cloud.setSpellLevelDamage(spellLevelDamage);
			cloud.setPos(targetPos.x, targetPos.y, targetPos.z);
			cloud.setYRot(caster.getYRot());
			cloud.setXRot(caster.getXRot());
			cloud.setOwner(caster);
			cloud.setTarget(caster);
			serverLevel.addFreshEntity(cloud);
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
			if (!caster.isSilent()) {
				caster.level().playSound(null, caster.getX(), caster.getY(), caster.getZ(), JerotesSoundEvents.WIND_SHORT,
						caster.getSoundSource(), 1.0f, 1.0F);
			}
		}
		return true;
	}
	//喷吐雷液$法术
	public static boolean LightningSpit(LivingEntity caster, Entity target, float type, int spellLevelDamage, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, int spellLevelOtherEffectTime, int spellLevelOtherEffectLevel, float spellLevelAccuracy, int count, float distance, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
				LightningSpitEntity breath;
				for (int i = 0; i < count; ++i) {
					if (type == 1) {
						breath = new LightningSpitEntity(spellLevelDamage, spellLevelMainEffectTime, spellLevelMainEffectLevel, spellLevelOtherEffectTime, spellLevelOtherEffectLevel, serverLevel, caster, caster.getLookAngle().x, caster.getLookAngle().y, caster.getLookAngle().z);
						breath.setPos(caster.getX(), caster.getY(0.7), caster.getZ());
						breath.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() + 20) % 360, 0f, 1f, spellLevelAccuracy);
					} else if (type == 2) {
						breath = new LightningSpitEntity(spellLevelDamage, spellLevelMainEffectTime, spellLevelMainEffectLevel, spellLevelOtherEffectTime, spellLevelOtherEffectLevel, serverLevel, caster, caster.getLookAngle().x, caster.getLookAngle().y, caster.getLookAngle().z);
						breath.setPos(caster.getX(), caster.getY(0.7), caster.getZ());
						breath.shootFromRotation(caster, caster.getXRot(), caster.getYRot(), 0f, 1f, spellLevelAccuracy);
					} else if (type == 3) {
						breath = new LightningSpitEntity(spellLevelDamage, spellLevelMainEffectTime, spellLevelMainEffectLevel, spellLevelOtherEffectTime, spellLevelOtherEffectLevel, serverLevel, caster, caster.getLookAngle().x, caster.getLookAngle().y, caster.getLookAngle().z);
						breath.setPos(caster.getX(), caster.getY(0.7), caster.getZ());
						breath.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - 20) % 360, 0f, 1f, spellLevelAccuracy);
					} else {
						breath = new LightningSpitEntity(spellLevelDamage, spellLevelMainEffectTime, spellLevelMainEffectLevel, spellLevelOtherEffectTime, spellLevelOtherEffectLevel, serverLevel, caster, caster.getLookAngle().x, caster.getLookAngle().y, caster.getLookAngle().z);
						breath.setPos(caster.getX(), caster.getY(0.7), caster.getZ());
						breath.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1f, spellLevelAccuracy);
					}
					breath.setOwner(caster);
					serverLevel.addFreshEntity(breath);
				}

		}
		return true;
	}
	//云蒸雾集$法术
	public static boolean ToxicMistVolley(LivingEntity caster, Entity target, int spellLevelDamage, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, float spellLevelAccuracy, int count, float distance, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			ToxicMistVolleyEntity breath;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				breath = new ToxicMistVolleyEntity(spellLevelDamage, spellLevelMainEffectTime, spellLevelMainEffectLevel, serverLevel, caster, caster.getLookAngle().x, caster.getLookAngle().y, caster.getLookAngle().z);
				breath.setPos(caster.getX(), caster.getY(0.7), caster.getZ());
				breath.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1f, spellLevelAccuracy);
				breath.setOwner(caster);
				serverLevel.addFreshEntity(breath);
			}
		}
		return true;
	}
	//离弦之箭$法术
	public static boolean Vinebolt(LivingEntity caster, Entity target, int spellLevelDamage, float spellLevelSpeed, float spellLevelAccuracy, int count, float distance, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			VineboltEntity vinebolt;
			for (int i = 0; i < count - 1; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				vinebolt = new VineboltEntity(spellLevelDamage, serverLevel, caster);
				vinebolt.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0, spellLevelSpeed, count);
				vinebolt.setOwner(caster);
				serverLevel.addFreshEntity(vinebolt);
			}
			if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
				mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
				mob.lookAt(target, 360.0f, 360.0f);
			}
			vinebolt = new VineboltEntity(spellLevelDamage, serverLevel, caster);
			vinebolt.shootFromRotation(caster, caster.getXRot(), caster.getYRot(), 0, spellLevelSpeed, count);
			vinebolt.setOwner(caster);
			serverLevel.addFreshEntity(vinebolt);
		}
		return true;
	}
	//漩涡$法术
	public static boolean Whirlpool(LivingEntity caster, Entity target, Entity where,  int spellLevelDamage, int spellLevelMoveDistance, float spellLevelAccuracy, int count, float distance, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			WhirlpoolEntity spell;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				if (!isPlayer && target != caster && where instanceof MalignasaurPart basePartEntity) {
                    if (target != null) {
                        basePartEntity.lookAt(target, 360.0f, 360.0f);
                    }
                }
				spell = new WhirlpoolEntity(spellLevelDamage, spellLevelMoveDistance, serverLevel, caster, where.getLookAngle().x, where.getLookAngle().y, where.getLookAngle().z);
				spell.setPos(where.getX(), where.getY(0.7), where.getZ());
				spell.shootFromRotation(caster, where.getXRot(), (where.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1f, spellLevelAccuracy);
				spell.setOwner(caster);
				serverLevel.addFreshEntity(spell);
			}
		}
		return true;
	}
	//莫厄炸弹
	public static boolean MerorBomb(LivingEntity caster, Entity target, float spellLevelAccuracy, int count, float distance, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			MerorBombEntity merorBomb;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				merorBomb = new MerorBombEntity(serverLevel, caster);
				merorBomb.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0.0f, 1, spellLevelAccuracy);
				merorBomb.setOwner(caster);
				String string = ChatFormatting.stripFormatting(caster.getName().getString());
				if ("Redstone Monstrosity".equals(string) || "红石巨兽".equals(string)) {
					merorBomb.setOwnerType(2);
				}
				else {
					merorBomb.setOwnerType(1);
				}
				serverLevel.addFreshEntity(merorBomb);
			}
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.MEROR_BOMB_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//苦寒冰锥$法术
	public static boolean BitterColdIceSpike(LivingEntity caster, Entity target, int spellLevelDamage, int spellLevelFreezeTime, float spellLevelSpeed, float spellLevelAccuracy, int count, float distance, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			BitterColdIceSpikeEntity iceSpike;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				iceSpike = new BitterColdIceSpikeEntity(spellLevelDamage, spellLevelFreezeTime, serverLevel, caster);
				iceSpike.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0.0f, spellLevelSpeed, spellLevelAccuracy);
				iceSpike.setOwner(caster);
				serverLevel.addFreshEntity(iceSpike);
			}
		}
		return true;
	}
	//腐蚀冰锥$法术
	public static boolean CorrosiveIceSpike(LivingEntity caster, Entity target, int spellLevelDamage, int spellLevelFreezeTime, float spellLevelSpeed, float spellLevelAccuracy, int count, float distance, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			CorrosiveIceSpikeEntity iceSpike;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				iceSpike = new CorrosiveIceSpikeEntity(spellLevelDamage, spellLevelFreezeTime, serverLevel, caster);
				iceSpike.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0.0f, spellLevelSpeed, spellLevelAccuracy);
				iceSpike.setOwner(caster);
				serverLevel.addFreshEntity(iceSpike);
			}
		}
		return true;
	}
	//苦寒霜噬术$法术
	public static boolean BitterColdFrostbite(LivingEntity caster, Entity target, int spellLevelDamage, int spellLevelFreezeTime, float spellLevelAccuracy, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			BitterColdFrostbiteEntity spell;
			if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
				mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
				mob.lookAt(target, 360.0f, 360.0f);
			}
			double d2 = caster.getLookAngle().x;
			double d3 = caster.getLookAngle().y;
			double d4 = caster.getLookAngle().z;
			spell = new BitterColdFrostbiteEntity(spellLevelDamage, spellLevelFreezeTime, serverLevel, caster, d2, d3, d4);
			spell.setPos(caster.getX(), caster.getY(0.7) - spell.getBbHeight()/2, caster.getZ());
			spell.shootFromRotation(caster, caster.getXRot(), caster.getYRot(), 0f, 1f, spellLevelAccuracy);
			spell.setOwner(caster);
			serverLevel.addFreshEntity(spell);
		}
		return true;
	}
	//弹力光球$法术
	public static boolean ElasticLightBall(LivingEntity caster, Entity target, int spellLevelDamage, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, float spellLevelSpeed, float spellLevelAccuracy, int count, float distance, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			ElasticLightBallEntity elasticLightBall;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				elasticLightBall = new ElasticLightBallEntity(spellLevelDamage, spellLevelMainEffectTime, spellLevelMainEffectLevel, serverLevel, caster);
				elasticLightBall.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0.0f, spellLevelSpeed, spellLevelAccuracy);
				elasticLightBall.setOwner(caster);
				serverLevel.addFreshEntity(elasticLightBall);
			}
		}
		return true;
	}
	//投鞭断流$法术
	public static boolean WhipTheTide(LivingEntity caster, Entity target, int spellLevelDamage, float spellLevelSpeed, float spellLevelAccuracy, int count, float distance, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			WhipTheTideEntity whipTheTide;
			for (int i = 0; i < count - 1; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				whipTheTide = new WhipTheTideEntity(spellLevelDamage, serverLevel, caster);
				whipTheTide.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0.0f, spellLevelSpeed, count);
				whipTheTide.setOwner(caster);
				serverLevel.addFreshEntity(whipTheTide);
			}
			if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
				mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
				mob.lookAt(target, 360.0f, 360.0f);
			}
			whipTheTide = new WhipTheTideEntity(spellLevelDamage, serverLevel, caster);
			whipTheTide.shootFromRotation(caster, caster.getXRot(), caster.getYRot(), 0.0f, spellLevelSpeed, spellLevelAccuracy);
			whipTheTide.setOwner(caster);
			serverLevel.addFreshEntity(whipTheTide);
		}
		return true;
	}
	//冰岩投掷$法术
	public static boolean IceRockThrow(LivingEntity caster, Entity target, int spellLevelDamage, int spellLevelFreezeTime, float spellLevelSpeed, float spellLevelAccuracy, int count, float distance, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			IceRockThrowEntity iceRock;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				iceRock = new IceRockThrowEntity(spellLevelDamage, spellLevelFreezeTime, serverLevel, caster);
				iceRock.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0.0f, spellLevelSpeed, spellLevelAccuracy);
				iceRock.setOwner(caster);
				serverLevel.addFreshEntity(iceRock);
			}
		}
		return true;
	}
	//泌火油弹$法术
	public static boolean SecreteFireOilBomb(LivingEntity caster, Entity target, int spellLevelDamage, int spellLevelFireTime, float spellLevelExplode, float spellLevelAccuracy, int count, float distance, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			SecreteFireOilBombEntity spell;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				spell = new SecreteFireOilBombEntity(spellLevelDamage, spellLevelFireTime, spellLevelExplode, serverLevel, caster, caster.getLookAngle().x, caster.getLookAngle().y, caster.getLookAngle().z);
				spell.setPos(caster.getX(), caster.getY(0.7) - spell.getBbHeight()/2, caster.getZ());
				spell.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1f, spellLevelAccuracy);
				spell.setOwner(caster);
				serverLevel.addFreshEntity(spell);
			}
		}
		return true;
	}
	//蛇龙火球$法术
	public static boolean SerponFireball(LivingEntity caster, Entity target, int spellLevelDamage, int spellLevelFireTime, float spellLevelExplode, float spellLevelAccuracy, int count, float distance, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			SerponFireballEntity spell;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				spell = new SerponFireballEntity(spellLevelDamage, spellLevelFireTime, spellLevelExplode, serverLevel, caster, caster.getLookAngle().x, caster.getLookAngle().y, caster.getLookAngle().z);
				spell.setPos(caster.getX(), caster.getY(0.7) - spell.getBbHeight()/2, caster.getZ());
				spell.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1f, spellLevelAccuracy);
				spell.setOwner(caster);
				serverLevel.addFreshEntity(spell);
			}
		}
		return true;
	}
	//蛇龙火舌术$法术
	public static boolean SerponFlames(LivingEntity caster, Entity target, int spellLevelDamage, int spellLevelFireTime, float spellLevelAccuracy, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			SerponFlamesEntity spell;
			if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
				mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
				mob.lookAt(target, 360.0f, 360.0f);
			}
			double d2 = caster.getLookAngle().x;
			double d3 = caster.getLookAngle().y;
			double d4 = caster.getLookAngle().z;
			spell = new SerponFlamesEntity(spellLevelDamage, spellLevelFireTime, serverLevel, caster, d2, d3, d4);
			spell.setPos(caster.getX(), caster.getY(0.7) - spell.getBbHeight()/2, caster.getZ());
			spell.shootFromRotation(caster, caster.getXRot(), caster.getYRot(), 0f, 1f, spellLevelAccuracy);
			spell.setOwner(caster);
			serverLevel.addFreshEntity(spell);
		}
		return true;
	}
	//不祥火舌术$法术
	public static boolean OminousFlames(LivingEntity caster, Entity target, int spellLevelDamage, int spellLevelFireTime, float spellLevelAccuracy, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			OminousFlamesEntity spell;
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				double d2 = caster.getLookAngle().x;
				double d3 = caster.getLookAngle().y;
				double d4 = caster.getLookAngle().z;
				spell = new OminousFlamesEntity(spellLevelDamage, spellLevelFireTime, serverLevel, caster, d2, d3, d4);
				spell.setPos(caster.getX(), caster.getY(0.7) - spell.getBbHeight()/2, caster.getZ());
				spell.shootFromRotation(caster, caster.getXRot(), caster.getYRot(), 0f, 1f, spellLevelAccuracy);
				spell.setOwner(caster);
				serverLevel.addFreshEntity(spell);
		}
		return true;
	}
	//浓烟飞弹$法术
	public static boolean SmokeMissile(LivingEntity caster, Entity target, int spellLevelDamage, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, int spellLevelOtherEffectTime, int spellLevelOtherEffectLevel, int spellLevelHeight, float spellLevelAccuracy, int count, float distance, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			SmokeMissileEntity spell;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				spell = new SmokeMissileEntity(spellLevelDamage, spellLevelMainEffectTime, spellLevelMainEffectLevel, spellLevelOtherEffectTime, spellLevelOtherEffectLevel, spellLevelHeight, serverLevel, caster, caster.getLookAngle().x, caster.getLookAngle().y, caster.getLookAngle().z);
				spell.setPos(caster.getX(), caster.getY(0.7) - spell.getBbHeight()/2, caster.getZ());
				spell.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1f, spellLevelAccuracy);
				spell.setOwner(caster);
				serverLevel.addFreshEntity(spell);
			}
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.SMOKE_MISSILE_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//眼盘射线$法术
	public static boolean EyeDiscShoot(LivingEntity caster, Entity target, int spellLevelDamage, float spellLevelAccuracy, int count, float distance, int tickCount, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			EyeDiscShootEntity spell;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				spell = new EyeDiscShootEntity(spellLevelDamage, serverLevel, caster, caster.getLookAngle().x, caster.getLookAngle().y, caster.getLookAngle().z);
				spell.setPos(caster.getX(), caster.getEyeY(), caster.getZ());
				spell.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1f, spellLevelAccuracy);
				spell.setOwner(caster);
				serverLevel.addFreshEntity(spell);
			}
		}
		return true;
	}
	//捕食齿花喷吐$法术
	public static boolean PredationToothedFlowerShoot(LivingEntity caster, Entity target, int spellLevelDamage, float spellLevelAccuracy, int count, float distance, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PredationToothedFlowerShootEntity spell;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				spell = new PredationToothedFlowerShootEntity(spellLevelDamage, serverLevel, caster, caster.getLookAngle().x, caster.getLookAngle().y, caster.getLookAngle().z);
				spell.setPos(caster.getX(), caster.getY(0.7) - spell.getBbHeight()/2, caster.getZ());
				spell.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1f, spellLevelAccuracy);
				spell.setOwner(caster);
				serverLevel.addFreshEntity(spell);
			}
		}
		return true;
	}
	//蠕虫雷暴$法术
	public static boolean WormThunderstorm(LivingEntity caster, Entity target, int spellLevelDamage, float spellLevelAccuracy, int count, float distance, boolean isPlayer, int spellSubcategory) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			WormThunderstormEntity spell;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				spell = new WormThunderstormEntity(spellLevelDamage, spellSubcategory, serverLevel, caster, caster.getLookAngle().x, caster.getLookAngle().y, caster.getLookAngle().z);
				spell.setPos(caster.getX(), caster.getY(0.7) - spell.getBbHeight()/2, caster.getZ());
				spell.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1f, spellLevelAccuracy);
				spell.setOwner(caster);
				serverLevel.addFreshEntity(spell);
			}
		}
		return true;
	}
	//烈性轰炸
	public static boolean IntenseBombing(LivingEntity caster, int spellLevelDamage, int spellLevelFireTime, float spellLevelExplode) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			IntenseBombingEntity fire0 = new IntenseBombingEntity(spellLevelDamage, spellLevelFireTime, spellLevelExplode, serverLevel, caster, 0, 0, 0);
			IntenseBombingEntity fire1 = new IntenseBombingEntity(spellLevelDamage, spellLevelFireTime, spellLevelExplode, serverLevel, caster, 0, 0, 0);
			IntenseBombingEntity fire2 = new IntenseBombingEntity(spellLevelDamage, spellLevelFireTime, spellLevelExplode, serverLevel, caster, 0, 0, 0);
			IntenseBombingEntity fire3 = new IntenseBombingEntity(spellLevelDamage, spellLevelFireTime, spellLevelExplode, serverLevel, caster, 0, 0, 0);
			IntenseBombingEntity fire4 = new IntenseBombingEntity(spellLevelDamage, spellLevelFireTime, spellLevelExplode, serverLevel, caster, 0, 0, 0);
			IntenseBombingEntity fire5 = new IntenseBombingEntity(spellLevelDamage, spellLevelFireTime, spellLevelExplode, serverLevel, caster, 0, 0, 0);
			fire0.shoot(1, 0, 0, 1, 0);
			fire1.shoot(-1, 0, 0, 1, 0);
			fire2.shoot(0, 0, 1, 1, 0);
			fire3.shoot(0, 0, -1, 1, 0);
			fire4.shoot(0, 1, 0, 1, 0);
			fire5.shoot(0, -1, 0, 1, 0);
			fire0.setPos(fire0.getX(), fire0.getY(), fire0.getZ());
			fire1.setPos(fire1.getX(), fire0.getY(), fire1.getZ());
			fire2.setPos(fire2.getX(), fire0.getY(), fire2.getZ());
			fire3.setPos(fire3.getX(), fire0.getY(), fire3.getZ());
			fire4.setPos(fire4.getX(), fire0.getY(), fire4.getZ());
			fire5.setPos(fire5.getX(), fire0.getY(), fire5.getZ());
			serverLevel.addFreshEntity(fire0);
			serverLevel.addFreshEntity(fire1);
			serverLevel.addFreshEntity(fire2);
			serverLevel.addFreshEntity(fire3);
			serverLevel.addFreshEntity(fire4);
			serverLevel.addFreshEntity(fire5);
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.INTENSE_BOMBING_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//缴械牵扯
	public static boolean DisarmDrag(LivingEntity caster, Entity target, int spellLevelDamage, float spellLevelAccuracy, int count, float distance, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			SlaverySupervisorChainEntity spell;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				spell = new SlaverySupervisorChainEntity(spellLevelDamage, serverLevel, caster, caster.getLookAngle().x, caster.getLookAngle().y, caster.getLookAngle().z);
				spell.setPos(caster.getX(), caster.getY(0.7) - spell.getBbHeight()/2, caster.getZ());
				spell.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1f, spellLevelAccuracy);
				spell.setOwner(caster);
				serverLevel.addFreshEntity(spell);
			}
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.DISARM_DRAG_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//推动力场$法术
	public static boolean PushForce(LivingEntity caster, Entity target, int spellLevelDamage, float spellLevelXZPush, float spellLevelXZPushBase, float spellLevelYPush, float spellLevelYPushBase, float spellLevelAccuracy, int count, float distance, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PushForceEntity spell;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				double d2 = caster.getLookAngle().x;
				double d3 = caster.getLookAngle().y;
				double d4 = caster.getLookAngle().z;
				spell = new PushForceEntity(spellLevelDamage, spellLevelXZPush, spellLevelXZPushBase, spellLevelYPush, spellLevelYPushBase, serverLevel, caster, d2, d3, d4);
				spell.setPos(caster.getX(), caster.getY(0.7) - spell.getBbHeight()/2, caster.getZ());
				spell.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1f, spellLevelAccuracy);
				spell.setOwner(caster);
				serverLevel.addFreshEntity(spell);
			}
		}
		return true;
	}
	//忐忑不定$法术
	public static boolean UneasyUncertainty(LivingEntity caster, Entity target, int spellLevelDamage, float spellLevelAccuracy, int count, float distance, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			UneasyUncertaintyEntity spell;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				double d2 = caster.getLookAngle().x;
				double d3 = caster.getLookAngle().y;
				double d4 = caster.getLookAngle().z;
				spell = new UneasyUncertaintyEntity(spellLevelDamage, serverLevel, caster, d2, d3, d4);
				spell.setPos(caster.getX(), caster.getY(0.75), caster.getZ());
				spell.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1f, spellLevelAccuracy);
				spell.setOwner(caster);
				serverLevel.addFreshEntity(spell);
			}
		}
		return true;
	}
	//波涛射弹$法术
	public static boolean BulgeBullet(LivingEntity caster, Entity target, int spellLevelDamage, float spellLevelAccuracy, int count, float distance, int tickCount, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			BulgeBulletEntity spell;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				double d2 = caster.getLookAngle().x;
				double d3 = caster.getLookAngle().y;
				double d4 = caster.getLookAngle().z;
				spell = new BulgeBulletEntity(spellLevelDamage, serverLevel, caster, d2, d3, d4);
				spell.setPos(caster.getX(), caster.getY(0.7) - spell.getBbHeight()/2, caster.getZ());
				spell.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1f, spellLevelAccuracy);
				spell.setOwner(caster);
				serverLevel.addFreshEntity(spell);
			}
		}
		return true;
	}
	//植究射弹$法术
	public static boolean BotanizeBullet(LivingEntity caster, Entity target, int spellLevelDamage, float spellLevelAccuracy, int count, float distance, int tickCount, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			BotanizeBulletEntity spell;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				double d2 = caster.getLookAngle().x;
				double d3 = caster.getLookAngle().y;
				double d4 = caster.getLookAngle().z;
				spell = new BotanizeBulletEntity(spellLevelDamage, serverLevel, caster, d2, d3, d4);
				spell.setPos(caster.getX(), caster.getY(0.7) - spell.getBbHeight()/2, caster.getZ());
				spell.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1f, spellLevelAccuracy);
				spell.setOwner(caster);
				serverLevel.addFreshEntity(spell);
			}
		}
		return true;
	}
	//旱火火球$法术
	public static boolean DroughtFireball(LivingEntity caster, Entity target, int spellLevelDamage, int spellLevelFireTime, float spellLevelAccuracy, int count, float distance, int tickCount, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			DroughtFireballEntity spell;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				double d2 = caster.getLookAngle().x;
				double d3 = caster.getLookAngle().y;
				double d4 = caster.getLookAngle().z;
				spell = new DroughtFireballEntity(spellLevelDamage, spellLevelFireTime, serverLevel, caster, d2, d3, d4);
				spell.setPos(caster.getX(), caster.getY(0.7) - spell.getBbHeight()/2, caster.getZ());
				spell.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1f, spellLevelAccuracy);
				spell.setOwner(caster);
				serverLevel.addFreshEntity(spell);
			}
		}
		return true;
	}
	//遗迹风弹$法术
	public static boolean RuinsWindCharge(LivingEntity caster, Entity target, int spellLevelDamage, float spellLevelAccuracy, int count, float distance, int tickCount, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			RuinsWindChargeEntity spell;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				double d2 = caster.getLookAngle().x;
				double d3 = caster.getLookAngle().y;
				double d4 = caster.getLookAngle().z;
				spell = new RuinsWindChargeEntity(spellLevelDamage, serverLevel, caster, d2, d3, d4);
				spell.setPos(caster.getX(), caster.getY(0.7) - spell.getBbHeight()/2, caster.getZ());
				spell.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1f, spellLevelAccuracy);
				spell.setOwner(caster);
				serverLevel.addFreshEntity(spell);
			}
		}
		return true;
	}
	public static boolean SnapBolt(LivingEntity caster, Entity target, int spellLevelDamage, float spellLevelAccuracy, int count, float distance, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			SnapBoltEntity spell;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				spell = new SnapBoltEntity(spellLevelDamage, serverLevel, caster, caster.getLookAngle().x, caster.getLookAngle().y, caster.getLookAngle().z);
				spell.setPos(caster.getX(), caster.getY(0.7), caster.getZ());
				spell.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1, spellLevelAccuracy);
				spell.setOwner(caster);
				serverLevel.addFreshEntity(spell);

				Vec3 pos = spell.position();
				for (int i2 = 0; i2 < 40; i2++) {
					double dx = (caster.getRandom().nextDouble() - 0.5) * 2.0;
					double dy = (caster.getRandom().nextDouble() - 0.5) * 2.0;
					double dz = (caster.getRandom().nextDouble() - 0.5) * 2.0;
					serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_TARGET.get(),
							pos.x, pos.y, pos.z,
							1, dx, dy, dz, 0.05);
				}
			}
		}
		return true;
	}
	//光芒爆弹$法术
	public static boolean RadiantBomb(LivingEntity caster, Entity target, int spellLevelDamage, float spellLevelExplode, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, float spellLevelAccuracy, int count, float distance, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			RadiantBombEntity spell;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				spell = new RadiantBombEntity(spellLevelDamage, spellLevelExplode, spellLevelMainEffectTime, spellLevelMainEffectLevel, serverLevel, caster, caster.getLookAngle().x, caster.getLookAngle().y, caster.getLookAngle().z);
				spell.setPos(caster.getX(), caster.getY(0.7), caster.getZ());
				spell.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1f, spellLevelAccuracy);
				spell.setOwner(caster);
				serverLevel.addFreshEntity(spell);

                Vec3 pos = spell.position();
                for (int i2 = 0; i2 < 40; i2++) {
                    double dx = (caster.getRandom().nextDouble() - 0.5) * 2.0;
                    double dy = (caster.getRandom().nextDouble() - 0.5) * 2.0;
                    double dz = (caster.getRandom().nextDouble() - 0.5) * 2.0;
                    serverLevel.sendParticles(JerotesVillageParticleTypes.RADIANT_BOMB.get(),
                            pos.x, pos.y, pos.z,
                            1, dx, dy, dz, 0.05);
                }
			}
		}
		return true;
	}
	//沉降飞弹$法术
	public static boolean SedimentMissile(LivingEntity caster, Entity target, int spellLevelDamage, float spellLevelAccuracy, int count, float distance, int tickCount, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			SedimentMissileEntity spell;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				double d2 = caster.getLookAngle().x;
				double d3 = caster.getLookAngle().y;
				double d4 = caster.getLookAngle().z;
				spell = new SedimentMissileEntity(spellLevelDamage, serverLevel, caster, d2, d3, d4);
				spell.setPos(caster.getX(), caster.getY(0.7) - spell.getBbHeight()/2, caster.getZ());
				spell.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1f, spellLevelAccuracy);
				spell.setOwner(caster);
				if (target != null && target != caster) {
					spell.setTarget(target);
				}
				serverLevel.addFreshEntity(spell);
			}
		}
		return true;
	}
	//眼花缭乱$法术
	public static boolean VertigoDeluge(LivingEntity caster, Entity target, int spellLevelDamage, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, float spellLevelAccuracy, int count, float distance, int tickCount, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			VertigoDelugeEntity spell;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				double d2 = caster.getLookAngle().x;
				double d3 = caster.getLookAngle().y;
				double d4 = caster.getLookAngle().z;
				spell = new VertigoDelugeEntity(spellLevelDamage, spellLevelMainEffectTime, spellLevelMainEffectLevel, serverLevel, caster, d2, d3, d4);
				spell.setPos(caster.getX(), caster.getY(0.75) - spell.getBbHeight()/2, caster.getZ());
				spell.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1f, spellLevelAccuracy);
				spell.setOwner(caster);
				if (target != null && target != caster) {
					spell.setTarget(target);
				}
				serverLevel.addFreshEntity(spell);
			}
		}
		return true;
	}
	//奥术光点$法术
	public static boolean ArcaneLightSpot(LivingEntity caster, Entity target, int spellLevelDamage, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, float spellLevelAccuracy, int count, float distance, int tickCount, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			ArcaneLightSpotEntity spell;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				double d2 = caster.getLookAngle().x;
				double d3 = caster.getLookAngle().y;
				double d4 = caster.getLookAngle().z;
				spell = new ArcaneLightSpotEntity(spellLevelDamage, spellLevelMainEffectTime, spellLevelMainEffectLevel, serverLevel, caster, d2, d3, d4);
				spell.setPos(caster.getX(), caster.getY(0.7) - spell.getBbHeight()/2, caster.getZ());
				spell.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1f, spellLevelAccuracy);
				spell.setOwner(caster);
				if (target != null && target != caster) {
					spell.setTarget(target);
				}
				serverLevel.addFreshEntity(spell);
			}
		}
		return true;
	}
	//摧枯拉朽$法术
	public static boolean WiltMissile(LivingEntity caster, Entity target, int spellLevelDamage, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, float spellLevelAccuracy, int count, float distance, int tickCount, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			WiltMissileEntity spell;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				double d2 = caster.getLookAngle().x;
				double d3 = caster.getLookAngle().y;
				double d4 = caster.getLookAngle().z;
				spell = new WiltMissileEntity(spellLevelDamage, spellLevelMainEffectTime, spellLevelMainEffectLevel, serverLevel, caster, d2, d3, d4);
				spell.setPos(caster.getX(), caster.getY(0.7) - spell.getBbHeight()/2, caster.getZ());
				spell.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1f, spellLevelAccuracy);
				spell.setOwner(caster);
				if (target != null && target != caster) {
					spell.setTarget(target);
				}
				serverLevel.addFreshEntity(spell);
			}
		}
		return true;
	}
	//蛇龙热风术$法术
	public static boolean SerponSirocco(LivingEntity caster, Entity target, float knockbackDistance, float knockbackStrength, int spellLevelDamage, float spellLevelAccuracy, int count, float distance, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			List<LivingEntity> list = caster.level().getEntitiesOfClass(LivingEntity.class, caster.getBoundingBox().inflate(knockbackDistance, knockbackDistance, 3));
			for (LivingEntity attack : list) {
				if (AttackFind.FindCanNotAttack(caster, attack)) continue;
				attack.knockback(knockbackStrength, Mth.sin(caster.getYRot() * 0.0175f), -Mth.cos(caster.getYRot() * 0.0175f));
				double d = 0.0;
				if (attack.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
					d = Math.max(attack.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0);
				}
				double d2 = Math.max(0, 1 - d) + 0.3;
				attack.setDeltaMovement(attack.getDeltaMovement().add(-(caster.getX() - attack.getX()) * 0.5 * (d2 + 0.3), -(caster.getY() - attack.getY()) * 0.5 * (d2 + 0.35), -(caster.getZ() - attack.getZ()) * 0.5 * (d2 + 0.3)));
				if (!caster.isSilent()) {
					caster.playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK, 10.0f, 1.0f);
				}
			}
			SerponSiroccoEntity spell;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				double d2 = caster.getLookAngle().x;
				double d3 = caster.getLookAngle().y;
				double d4 = caster.getLookAngle().z;
				spell = new SerponSiroccoEntity(spellLevelDamage, serverLevel, caster, d2, d3, d4);
				spell.setPos(caster.getX(), caster.getY(0.7) - spell.getBbHeight()/2.5, caster.getZ());
				spell.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 0.45f, spellLevelAccuracy);
				spell.setOwner(caster);
				serverLevel.addFreshEntity(spell);
			}
		}
		return true;
	}
	//泽林声波
	public static boolean ZsieinSonic(LivingEntity caster, Entity target, float strength, float spellLevelAccuracy, int count, float distance, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			ZsieinSonicEntity spell;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				double d2 = caster.getLookAngle().x;
				double d3 = caster.getLookAngle().y;
				double d4 = caster.getLookAngle().z;
				spell = new ZsieinSonicEntity(strength, serverLevel, caster, d2, d3, d4);
				spell.setPos(caster.getX(), caster.getY(0.7) - spell.getBbHeight()/2, caster.getZ());
				spell.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1f, spellLevelAccuracy);
				spell.setOwner(caster);
				serverLevel.addFreshEntity(spell);
			}
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.ZSIEIN_SONIC_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//窒息控制$法术
	public static boolean AsphyxiaControl(LivingEntity caster, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, int spellLevelDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, caster.getBoundingBox().inflate(spellLevelDistance, spellLevelDistance, spellLevelDistance));
			for (LivingEntity livingEntityControl : list) {
				if (livingEntityControl == null) continue;
				if ((caster.distanceTo(livingEntityControl)) > spellLevelDistance * 2) continue;
				if (AttackFind.FindCanNotAttack(caster, livingEntityControl)) continue;
				if (livingEntityControl.hasEffect(MobEffects.WATER_BREATHING) || livingEntityControl.hasEffect(MobEffects.CONDUIT_POWER) || livingEntityControl.getMobType() == MobType.WATER)
					continue;
				if (!livingEntityControl.level().isClientSide) {
					livingEntityControl.addEffect(new MobEffectInstance(JerotesVillageMobEffects.MALIGNASAUR_ASPHYXIA.get(), spellLevelMainEffectTime * 20, spellLevelMainEffectLevel - 1), caster);
				}
				double d2 = livingEntityControl.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
				double d3 = Math.max(0, 1.0 - d2);
				if (caster.isInWater()) {
					for (int i = 0; i < 5; ++i) {
						serverLevel.sendParticles(JerotesVillageParticleTypes.GEMSTONE_BUBBLE.get(), livingEntityControl.getRandomX(0.8), livingEntityControl.getRandomY(), livingEntityControl.getRandomZ(0.8), 0, 0.0, 0.0, 0.0, 0.0);
					}
				}
				livingEntityControl.setDeltaMovement(livingEntityControl.getDeltaMovement().add(-(caster.getX() - livingEntityControl.getX()) * 0.1 * d3, -(caster.getY() - livingEntityControl.getY()) * 0.02 * d3, -(caster.getZ() - livingEntityControl.getZ()) * 0.1 * d3));
				livingEntityControl.knockback(0.5, Mth.sin(caster.getYRot() * 0.04f), -Mth.cos(caster.getYRot() * 0.04f));
			}
		}
		return true;
	}
	//海龙威慑$法术
	public static boolean OceanDragonDeterrence(LivingEntity caster, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, int spellLevelOtherEffectTime, int spellLevelOtherEffectLevel, int spellLevelDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, caster.getBoundingBox().inflate(spellLevelDistance, spellLevelDistance, spellLevelDistance));
			for (LivingEntity livingEntityDeter : list) {
				if (livingEntityDeter == null) continue;
				if ((caster.distanceTo(livingEntityDeter)) > spellLevelDistance * 2) continue;
				if (AttackFind.FindCanNotAttack(caster, livingEntityDeter)) continue;
				if (!livingEntityDeter.level().isClientSide) {
					if (!livingEntityDeter.hasEffect(MobEffects.WATER_BREATHING) && !livingEntityDeter.hasEffect(MobEffects.CONDUIT_POWER)) {
						livingEntityDeter.addEffect(new MobEffectInstance(JerotesVillageMobEffects.OCEAN_DRAGON_DETERRENCE.get(), spellLevelMainEffectTime * 20, spellLevelMainEffectLevel - 1), caster);
						livingEntityDeter.addEffect(new MobEffectInstance(MobEffects.DARKNESS, spellLevelOtherEffectTime * 20, spellLevelOtherEffectLevel - 1), caster);
					}
				}
			}
		}
		return true;
	}
	//震地踢击
	public static boolean EarthquakeKick(LivingEntity caster, int maxDistance, int pushDistance, int pushAngle, int pushTick, boolean breakBlock) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			if (caster instanceof Mob mob && mob.getTarget() != null) {
				mob.getLookControl().setLookAt(mob.getTarget(), 360.0f, 360.0f);
			}
			AABB aabb = AABB.ofSize(new Vec3(caster.getX(), caster.getY() - 1, caster.getZ()), maxDistance, maxDistance, maxDistance);
			for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
				BlockPos abovePos = new BlockPos(blockPos).above();
				BlockState block = serverLevel.getBlockState(blockPos);
				BlockState blockAbove = serverLevel.getBlockState(abovePos);

				if (!block.isAir() && block.isRedstoneConductor(serverLevel, blockPos) && !block.hasBlockEntity() && !blockAbove.blocksMotion()) {
					JerotesEarthrendBlock fallingBlock = new JerotesEarthrendBlock(serverLevel, blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, block, 10);
					fallingBlock.setOwner(caster);
					fallingBlock.setCanBreakBlock(breakBlock);
					if (fallingBlock.distanceTo(caster) <= maxDistance/2f) {
						fallingBlock.push(0,
								0.2+Math.max(0, (fallingBlock.distanceTo(caster) / (maxDistance - maxDistance/6f))),
								0);
					}
					else {//大于8
						fallingBlock.push(0,
								0.2 + Math.max(0, (fallingBlock.distanceTo(caster) / (maxDistance - maxDistance/6f)) - (maxDistance - maxDistance/2f)/6f),
								0);
					}
					if (Main.canSeeAngle(caster, fallingBlock.getPosition(0.5f), pushAngle) && fallingBlock.distanceTo(caster) < pushDistance) {
						fallingBlock.setPushTick(pushTick);
					}
					serverLevel.addFreshEntity(fallingBlock);
				}
			}
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.EARTHQUAKE_KICK_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//迅电流光$法术
	public static boolean Electroflash(LivingEntity caster, int spellLevelDamage) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			ElectroflashEntity baseBeamEntity = new ElectroflashEntity(JerotesVillageEntityType.ELECTROFLASH.get(), serverLevel, spellLevelDamage);
			baseBeamEntity.setPos(caster.getX(), caster.getY(0.75), caster.getZ());
			baseBeamEntity.setLightLockX((float) caster.getX());
			baseBeamEntity.setLightLockY((float) (caster.getY(0.75)));
			baseBeamEntity.setLightLockZ((float) caster.getZ());
			baseBeamEntity.setOwner(caster);
			serverLevel.addFreshEntity(baseBeamEntity);
		}
		return true;
	}
	//宝石海浪$法术
	public static boolean GemstoneWaves(LivingEntity caster, int spellLevelDamage, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, int spellLevelDistance, float spellLevelAngleReach, int spellLevelCount) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			caster.clearFire();
			int count = spellLevelCount;
			for (int i = 0; i < spellLevelCount * 20; ++i) {
				float f = (caster.getRandom().nextFloat() - 0.5f) * spellLevelDistance;
				float f2 = (caster.getRandom().nextFloat() - 0.5f) * spellLevelDistance;
				float f3 = (caster.getRandom().nextFloat() - 0.5f) * spellLevelDistance;
				for (int i2 = 0; i2 < 6; ++i2) {
					f = (caster.getRandom().nextFloat() - 0.5f) * spellLevelDistance;
					f2 = (caster.getRandom().nextFloat() - 0.5f) * spellLevelDistance;
					f3 = (caster.getRandom().nextFloat() - 0.5f) * spellLevelDistance;
					BlockState blockState = serverLevel.getBlockState(new BlockPos((int) (caster.getX() + f), (int) (caster.getEyeY() + f2 / 2f), (int) (caster.getZ(0.8) + f3)));
					FluidState fluidState = serverLevel.getFluidState(new BlockPos((int) (caster.getX() + f), (int) (caster.getEyeY() + f2 / 2f), (int) (caster.getZ(0.8) + f3)));
					if (Main.hasLineOfSightPos(new Vec3(caster.getX() + f, caster.getEyeY() + f2 / 2f, caster.getZ(0.8) + f3), caster)) {
						if (blockState.isAir() || !fluidState.isEmpty()) {
							if (Main.canSeeAngle(caster, new Vec3(caster.getX() + f, caster.getEyeY() + f2 / 2f, caster.getZ(0.8) + f3), spellLevelAngleReach)) {
								break;
							}
						}
					}
				}
				BlockState blockState = serverLevel.getBlockState(new BlockPos((int) (caster.getX() + f), (int) (caster.getEyeY() + f2 / 2f), (int) (caster.getZ(0.8) + f3)));
				FluidState fluidState = serverLevel.getFluidState(new BlockPos((int) (caster.getX() + f), (int) (caster.getEyeY() + f2 / 2f), (int) (caster.getZ(0.8) + f3)));
				if (Main.hasLineOfSightPos(new Vec3(caster.getX() + f, caster.getEyeY() + f2 / 2f, caster.getZ(0.8) + f3), caster)) {
					if (blockState.isAir() || !fluidState.isEmpty()) {
						if (Main.canSeeAngle(caster, new Vec3(caster.getX() + f, caster.getEyeY() + f2 / 2f, caster.getZ(0.8) + f3), spellLevelAngleReach)) {
							if (caster.getRandom().nextFloat() > 0.5f) {
								serverLevel.sendParticles(JerotesVillageParticleTypes.GEMSTONE_BUBBLE.get(), caster.getX() + f, caster.getEyeY() + f2 / 2f, caster.getZ(0.8) + f3, 0, f, f2, f3, 0.25f);
							} else if (caster.getRandom().nextFloat() < 0.25f) {
								serverLevel.sendParticles(ParticleTypes.BUBBLE_POP, caster.getX() + f, caster.getEyeY() + f2 / 2f, caster.getZ(0.8) + f3, 0, f, f2, f3, 0.25f);
							} else {
								serverLevel.sendParticles(ParticleTypes.FISHING, caster.getX() + f, caster.getEyeY() + f2 / 2f, caster.getZ(0.8) + f3, 0, f, f2, f3, 0.25f);
							}
						}
					}
				}
			}
			List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, caster.getBoundingBox().inflate(spellLevelDistance, spellLevelDistance / 2f, spellLevelDistance));
			Collections.shuffle(list, new java.util.Random());
			for (LivingEntity livingEntityControl : list) {
				if (livingEntityControl == null) continue;
				if (count <= 0) break;
				if ((caster.distanceTo(livingEntityControl)) > spellLevelDistance * 2) continue;
				if (!Main.canSeeAngle(caster, livingEntityControl.getPosition(0.5f), spellLevelAngleReach)) continue;
				if (AttackFind.FindCanNotAttack(caster, livingEntityControl)) continue;
				if (!Main.hasLineOfSightEntity(livingEntityControl, caster)) continue;
				DamageSource damageSource = AttackFind.findDamageType(caster, JerotesDamageTypes.DROWN_MAGIC, caster);
				JerotesStopSpellEvent event = new JerotesStopSpellEvent(livingEntityControl, null, null, caster, 1, spellLevelDamage);
				MinecraftForge.EVENT_BUS.post(event);
				if (event.isCanceled()) {
					count -= 1;
					continue;
				}
				else {
					boolean bl = livingEntityControl.hurt(damageSource,
							Main.rollDice(3 + (spellLevelDamage - 3), 6, caster.getRandom()) * (livingEntityControl.isSensitiveToWater() ? 2f : 1f)
					);
					if (bl) {
						if (!livingEntityControl.level().isClientSide) {
							livingEntityControl.addEffect(new MobEffectInstance(MobEffects.CONFUSION, spellLevelMainEffectTime * 20, spellLevelMainEffectLevel - 1), caster);
						}
						livingEntityControl.clearFire();
						if (caster.isInWater()) {
							for (int i = 0; i < 5; ++i) {
								serverLevel.sendParticles(JerotesVillageParticleTypes.GEMSTONE_BUBBLE.get(), livingEntityControl.getRandomX(0.8), livingEntityControl.getRandomY(), livingEntityControl.getRandomZ(0.8), 0, 0.0, 0.0, 0.0, 0.0);
								serverLevel.sendParticles(JerotesVillageParticleTypes.GEMSTONE_BUBBLE_LAND.get(), livingEntityControl.getRandomX(0.8), livingEntityControl.getRandomY(), livingEntityControl.getRandomZ(0.8), 0, 0.0, 0.0, 0.0, 0.0);
							}
						}
						double d2 = livingEntityControl.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
						double d3 = Math.max(0, 1.0 - d2);
						livingEntityControl.push(-(caster.getX() - livingEntityControl.getX()) * 0.1 * d3, -(caster.getY() - livingEntityControl.getY()) * 0.02 * d3, -(caster.getZ() - livingEntityControl.getZ()) * 0.1 * d3);
					}
				}
				count -= 1;
			}
		}
		return true;
	}
	//不屈意志$法术
	public static boolean IndomitableWill(LivingEntity caster, int spellLevelDamage, int spellLevelDistance, int spellLevelCount) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			int count = spellLevelCount;
			if (!caster.level().isClientSide()) {
				caster.getActiveEffects().removeIf(mobEffectInstance -> !mobEffectInstance.getEffect().isBeneficial());
			}
			caster.heal(
					Main.rollDice(4 + (spellLevelDamage - 6), 6, caster.getRandom())
			);
			//目标
			if (caster instanceof Mob mob && mob.getTarget() != null && count > 0 && !(caster.distanceTo(mob.getTarget()) > spellLevelDistance * 2) && !AttackFind.FindCanNotAttack(caster, mob.getTarget())) {
				IndomitableWillEntity spell = new IndomitableWillEntity(spellLevelDamage, serverLevel, caster, 0, 0, 0);
				spell.setPos(mob.getTarget().getX(), mob.getTarget().getY(), mob.getTarget().getZ());
				spell.setOwner(caster);
				spell.setTarget(mob.getTarget());
				serverLevel.addFreshEntity(spell);
				count -= 1;
			}
			//其他
			List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, caster.getBoundingBox().inflate(spellLevelDistance, spellLevelDistance, spellLevelDistance));
			Collections.shuffle(list, new java.util.Random());
			list.removeIf(livingEntity -> caster instanceof Mob mob && mob.getTarget() != null && livingEntity == mob.getTarget());
			for (LivingEntity livingEntity : list) {
				if (livingEntity == null) continue;
				if (count <= 0) break;
				if (livingEntity instanceof TraceableEntity && !(livingEntity instanceof Mob mob && mob.getTarget() == caster)) continue;
				if ((caster.distanceTo(livingEntity)) > spellLevelDistance * 2) continue;
				if (AttackFind.FindCanNotAttack(caster, livingEntity)) continue;
				if (!(livingEntity instanceof Mob mob && mob.getTarget() == caster ||
						livingEntity instanceof Player ||
						livingEntity.getTeam() != null && caster.getTeam() != null && livingEntity.getTeam() != caster.getTeam() ||
						//livingEntity.getTeam() == null && caster.getTeam() == null && !livingEntity.isAlliedTo(caster) ||
						AttackFind.getOwnerTrue(livingEntity) instanceof Mob mob2 && mob2.getTarget() == caster ||
						AttackFind.getOwnerTrue(livingEntity) instanceof Player)) continue;
				if (!caster.hasLineOfSight(livingEntity)) continue;
				if (caster instanceof TyrantOfTheMountainRealmEntity tyrantOfTheMountainRealm && caster.level().getBiome(caster.getOnPos()).is(JerotesVillageBiomes.HARMONIOUS_WONDERLAND)) {
					if (livingEntity instanceof Animal animal && animal.getTarget() != tyrantOfTheMountainRealm && tyrantOfTheMountainRealm.getTarget() != animal)
						continue;
				}
				boolean bl2 = OtherEntityFactionFind.isWonderlin(livingEntity.getType()) && OtherEntityFactionFind.isWonderlin(caster.getType()) && caster.getTeam() == null && livingEntity.getTeam() == null;
				if (bl2) continue;
				IndomitableWillEntity spell = new IndomitableWillEntity(spellLevelDamage, serverLevel, caster, 0, 0, 0);
				spell.setPos(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
				spell.setOwner(caster);
				spell.setTarget(livingEntity);
				serverLevel.addFreshEntity(spell);
				count -= 1;
			}
		}
		return true;
	}
	//漂浮力场$法术
	public static boolean FloatingForce(LivingEntity caster, int spellLevelDamage, int spellLevelDistance, int spellLevelCount) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			int count = spellLevelCount;
			//目标
			if (caster instanceof Mob mob && mob.getTarget() != null && count > 0 && !(caster.distanceTo(mob.getTarget()) > spellLevelDistance * 2) && !AttackFind.FindCanNotAttack(caster, mob.getTarget())) {
				FloatingForceEntity spell = new FloatingForceEntity(spellLevelDamage, serverLevel, caster, 0, 0, 0);
				spell.setPos(mob.getTarget().getX(), mob.getTarget().getY(), mob.getTarget().getZ());
				spell.setOwner(caster);
				spell.setTarget(mob.getTarget());
				serverLevel.addFreshEntity(spell);
				count -= 1;
			}
			//其他
			List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, caster.getBoundingBox().inflate(spellLevelDistance, spellLevelDistance, spellLevelDistance));
			Collections.shuffle(list, new java.util.Random());
			list.removeIf(livingEntity -> caster instanceof Mob mob && mob.getTarget() != null && livingEntity == mob.getTarget());
			for (LivingEntity livingEntity : list) {
				if (livingEntity == null) continue;
				if (count <= 0) break;
				if (AttackFind.FindCanNotAttack(caster, livingEntity)) continue;
//				boolean bl2 = (livingEntity instanceof Mob mob && mob.getTarget() == caster || caster instanceof Mob mob1 && mob1.getTarget() == livingEntity)
//						|| (livingEntity instanceof Player && caster instanceof Enemy)
//						|| (livingEntity.getTeam() != null && caster.getTeam() != null && !livingEntity.isAlliedTo(caster))
//						|| (caster instanceof Player player && Main.getTargetedEntity(player, spellLevelDistance) == livingEntity)
//						|| caster instanceof Mob mob && mob.getTarget() != null && (livingEntity.isAlliedTo( mob.getTarget()) && !livingEntity.isAlliedTo(caster));
//				boolean bl3 = caster.getTeam() == null && livingEntity.getTeam() == null && EntityFactionFind.isRaider(caster) && (livingEntity instanceof AbstractVillager || livingEntity instanceof IronGolem);
//				if (!bl2 && !bl3) continue;
				FloatingForceEntity spell = new FloatingForceEntity(spellLevelDamage, serverLevel, caster, 0, 0, 0);
				spell.setPos(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
				spell.setOwner(caster);
				spell.setTarget(livingEntity);
				serverLevel.addFreshEntity(spell);
				count -= 1;
			}
		}
		return true;
	}
	//地吸力场$法术
	public static boolean GravityForce(LivingEntity caster, int spellLevelDamage, int spellLevelDistance, int spellLevelCount) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			int count = spellLevelCount;
			//目标
			if (caster instanceof Mob mob && mob.getTarget() != null && count > 0 && !(caster.distanceTo(mob.getTarget()) > spellLevelDistance * 2) && !AttackFind.FindCanNotAttack(caster, mob.getTarget())) {
				GravityForceEntity spell = new GravityForceEntity(spellLevelDamage, serverLevel, caster, 0, 0, 0);
				spell.setPos(mob.getTarget().getX(), mob.getTarget().getY(), mob.getTarget().getZ());
				spell.setOwner(caster);
				spell.setTarget(mob.getTarget());
				serverLevel.addFreshEntity(spell);
				count -= 1;
			}
			//其他
			List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, caster.getBoundingBox().inflate(spellLevelDistance, spellLevelDistance, spellLevelDistance));
			Collections.shuffle(list, new java.util.Random());
			list.removeIf(livingEntity -> caster instanceof Mob mob && mob.getTarget() != null && livingEntity == mob.getTarget());
			for (LivingEntity livingEntity : list) {
				if (livingEntity == null) continue;
				if (count <= 0) break;
				if (AttackFind.FindCanNotAttack(caster, livingEntity)) continue;
//				boolean bl2 = (livingEntity instanceof Mob mob && mob.getTarget() == caster || caster instanceof Mob mob1 && mob1.getTarget() == livingEntity)
//						|| (livingEntity instanceof Player && caster instanceof Enemy)
//						|| (livingEntity.getTeam() != null && caster.getTeam() != null && !livingEntity.isAlliedTo(caster))
//						|| (caster instanceof Player player && Main.getTargetedEntity(player, spellLevelDistance) == livingEntity)
//						|| caster instanceof Mob mob && mob.getTarget() != null && (livingEntity.isAlliedTo( mob.getTarget()) && !livingEntity.isAlliedTo(caster));
//				boolean bl3 = caster.getTeam() == null && livingEntity.getTeam() == null && EntityFactionFind.isRaider(caster) && (livingEntity instanceof AbstractVillager || livingEntity instanceof IronGolem);
//				if (!bl2 && !bl3) continue;
				GravityForceEntity spell = new GravityForceEntity(spellLevelDamage, serverLevel, caster, 0, 0, 0);
				spell.setPos(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
				spell.setOwner(caster);
				spell.setTarget(livingEntity);
				serverLevel.addFreshEntity(spell);
			}
		}
		return true;
	}
	//地震
	public static boolean EarthQuake(LivingEntity caster, float spellLevelDamage, int spellLevelDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			AABB aABB0 = AABB.ofSize(new Vec3(caster.getX(), caster.getY() + 3, caster.getZ()), 24, 8, 24);
			for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aABB0.minX), Mth.floor(aABB0.minY), Mth.floor(aABB0.minZ), Mth.floor(aABB0.maxX), Mth.floor(aABB0.maxY), Mth.floor(aABB0.maxZ))) {
				BlockState blockState = serverLevel.getBlockState(blockPos);
				float block = blockState.getDestroySpeed(serverLevel, blockPos);
				if (block >= 20f || block < 0f) continue;
				Main.spawnFallingBlockByPosChance(serverLevel, blockPos, 0.25f);
			}
			//破坏
			if (serverLevel.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
				//摧毁
				for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aABB0.minX), Mth.floor(aABB0.minY), Mth.floor(aABB0.minZ), Mth.floor(aABB0.maxX), Mth.floor(aABB0.maxY), Mth.floor(aABB0.maxZ))) {
					BlockState blockState = serverLevel.getBlockState(blockPos);
					float block = blockState.getDestroySpeed(serverLevel, blockPos);
					if (block >= 20f || block < 0f) continue;
					if (blockState.is(Blocks.AIR)) continue;
					if (!blockState.is(BlockTags.REPLACEABLE_BY_TREES) && !blockState.is(BlockTags.DIRT)) {
						serverLevel.destroyBlock(blockPos, true, caster);
					}
					else {
						serverLevel.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
					}
				}
				AABB aABB1 = new AABB(caster.getX() + 14, caster.getY() + 3 + 4, caster.getZ() + 10, caster.getX() + 10, caster.getY() + 3 - 4, caster.getZ() - 10);
				for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aABB1.minX), Mth.floor(aABB1.minY), Mth.floor(aABB1.minZ), Mth.floor(aABB1.maxX), Mth.floor(aABB1.maxY), Mth.floor(aABB1.maxZ))) {
					BlockState blockState = serverLevel.getBlockState(blockPos);
					float block = blockState.getDestroySpeed(serverLevel, blockPos);
					if (block >= 20f || block < 0f) continue;
					if (blockState.is(Blocks.AIR)) continue;
					if (!blockState.is(BlockTags.REPLACEABLE_BY_TREES) && !blockState.is(BlockTags.DIRT)) {
						serverLevel.destroyBlock(blockPos, true, caster);
					}
					else {
						serverLevel.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
					}
				}
				AABB aABB2 = new AABB(caster.getX() + 10, caster.getY() + 3 + 4, caster.getZ() + 14, caster.getX() - 10, caster.getY() + 3 - 4, caster.getZ() + 10);
				for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aABB2.minX), Mth.floor(aABB2.minY), Mth.floor(aABB2.minZ), Mth.floor(aABB2.maxX), Mth.floor(aABB2.maxY), Mth.floor(aABB2.maxZ))) {
					BlockState blockState = serverLevel.getBlockState(blockPos);
					float block = blockState.getDestroySpeed(serverLevel, blockPos);
					if (block >= 20f || block < 0f) continue;
					if (blockState.is(Blocks.AIR)) continue;
					if (!blockState.is(BlockTags.REPLACEABLE_BY_TREES) && !blockState.is(BlockTags.DIRT)) {
						serverLevel.destroyBlock(blockPos, true, caster);
					}
					else {
						serverLevel.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
					}
				}
				AABB aABB3 = new AABB(caster.getX() - 14, caster.getY() + 3 + 4, caster.getZ() + 10, caster.getX() - 10, caster.getY() + 3 - 4, caster.getZ() - 10);
				for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aABB3.minX), Mth.floor(aABB3.minY), Mth.floor(aABB3.minZ), Mth.floor(aABB3.maxX), Mth.floor(aABB3.maxY), Mth.floor(aABB3.maxZ))) {
					BlockState blockState = serverLevel.getBlockState(blockPos);
					float block = blockState.getDestroySpeed(serverLevel, blockPos);
					if (block >= 20f || block < 0f) continue;
					if (blockState.is(Blocks.AIR)) continue;
					if (!blockState.is(BlockTags.REPLACEABLE_BY_TREES) && !blockState.is(BlockTags.DIRT)) {
						serverLevel.destroyBlock(blockPos, true, caster);
					}
					else {
						serverLevel.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
					}
				}
				AABB aABB4 = new AABB(caster.getX() + 10, caster.getY() + 3 + 4, caster.getZ() - 14, caster.getX() - 10, caster.getY() + 3 - 4, caster.getZ() - 10);
				for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aABB4.minX), Mth.floor(aABB4.minY), Mth.floor(aABB4.minZ), Mth.floor(aABB4.maxX), Mth.floor(aABB4.maxY), Mth.floor(aABB4.maxZ))) {
					BlockState blockState = serverLevel.getBlockState(blockPos);
					float block = blockState.getDestroySpeed(serverLevel, blockPos);
					if (block >= 20f || block < 0f) continue;
					if (blockState.is(Blocks.AIR)) continue;
					if (!blockState.is(BlockTags.REPLACEABLE_BY_TREES) && !blockState.is(BlockTags.DIRT)) {
						serverLevel.destroyBlock(blockPos, true, caster);
					}
					else {
						serverLevel.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
					}
				}
//				//替换
//				AABB aABB5 = AABB.ofSize(new Vec3(caster.getX(), caster.getY() - 5, caster.getZ()), 24, 1, 24);
//				for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aABB5.minX), Mth.floor(aABB5.minY), Mth.floor(aABB5.minZ), Mth.floor(aABB5.maxX), Mth.floor(aABB5.maxY), Mth.floor(aABB5.maxZ))) {
//					BlockState blockState = serverLevel.getBlockState(blockPos);
//					if (!blockState.is(Blocks.GRASS_BLOCK)) continue;
//					serverLevel.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(caster, Blocks.DIRT_PATH.defaultBlockState()));
//				}
			}
			List<LivingEntity> listShake = serverLevel.getEntitiesOfClass(LivingEntity.class, caster.getBoundingBox().inflate(32.0, 32.0, 32.0));
			for (LivingEntity shake : listShake) {
				if (shake == null || shake == caster || (caster.distanceToSqr(shake)) > Double.MAX_VALUE)
					continue;
				if (!shake.level().isClientSide()) {
					shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 20, 0, false, false), caster);
				}
			}
			//攻击
			List<Entity> list = serverLevel.getEntitiesOfClass(Entity.class, caster.getBoundingBox().inflate(spellLevelDistance, spellLevelDistance, spellLevelDistance));
			for (Entity hurt : list) {
				if (hurt == null) continue;
				if (AttackFind.FindCanNotAttack(caster, hurt)) continue;
				if (caster instanceof WonderlandGiantFrogEntity mob && hurt instanceof WonderlandGiantFrogEntity wonderlandGiantFrogEntity && wonderlandGiantFrogEntity.getTarget() != mob && mob.getTarget() != wonderlandGiantFrogEntity)
					continue;
				if (caster instanceof WonderlandGiantFrogEntity mob && hurt instanceof WonderlandGiantFrogTadpoleEntity wonderlandGiantFrogTadpoleEntity && wonderlandGiantFrogTadpoleEntity.getTarget() != mob && mob.getTarget() != wonderlandGiantFrogTadpoleEntity)
					continue;
				if (caster instanceof ArchnosedHornbeastEntity mob && hurt instanceof ArchnosedHornbeastEntity archnosedHornbeast && archnosedHornbeast.getTarget() != mob && mob.getTarget() != archnosedHornbeast)
					continue;
				if ((caster.distanceTo(hurt)) > spellLevelDistance * 8) continue;
				float strength = (spellLevelDistance - caster.distanceTo(hurt)) / 3;
				if (Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt)) {
					if (hurt instanceof LivingEntity livingHurt) {
						livingHurt.setOnGround(false);
						livingHurt.setDeltaMovement(livingHurt.getDeltaMovement().add(0, strength, 0));
						livingHurt.knockback(2, Mth.sin(caster.getYRot() * 0.04f), -Mth.cos(caster.getYRot() * 0.04f));
					}
					if (hurt instanceof ItemEntity itemEntity && itemEntity.getItem().getRarity() == Rarity.COMMON) {
						itemEntity.setDeltaMovement(0, strength / 6, 0);
					}
					if (!(hurt instanceof ItemEntity)){
						hurt.setDeltaMovement(0, strength / 5, 0);
					}
				}
				if (!(hurt instanceof LivingEntity livingHurt)) continue;
				AttackFind.attackBegin(caster, hurt);
				boolean bl = AttackFind.attackAfter(caster, hurt, 0f, 0f, true, spellLevelDamage);
				if (bl) {
					if (!livingHurt.level().isClientSide()) {
						livingHurt.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 60, 0, false, false), caster);
					}
					if (livingHurt.level() instanceof ServerLevel _level) {
						_level.sendParticles(JerotesVillageParticleTypes.BRIGHT_LAND_BEAST_HURT.get(), livingHurt.getX(), livingHurt.getY() + 0.1, livingHurt.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
					}
					if (livingHurt instanceof Player player) {
						Item item0 = player.getMainHandItem().getItem();
						Item item1 = player.getOffhandItem().getItem();
						if (item0 instanceof ShieldItem) {
							player.getCooldowns().addCooldown(item0, 1200);
						}
						if (item1 instanceof ShieldItem) {
							player.getCooldowns().addCooldown(item1, 1200);
						}
					}
					if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt)) && !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
						hurt.setDeltaMovement(hurt.getDeltaMovement().add(0f, (7 - caster.distanceTo(hurt)) / 4, 0f));
					}
				}
			}


			for (int i = 0; i < 120; i++) {
				double angle = (Math.PI * 2 * i) / 120;
				double radius = 12;
				double offsetX = Math.cos(angle) * radius;
				double offsetZ = Math.sin(angle) * radius;

				// 生成烟雾粒子
				serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, caster.getX() + offsetX, caster.getY(), caster.getZ() + offsetZ, 0, offsetX * 0.002, 0.02, offsetZ * 0.002, 1.0);
				serverLevel.sendParticles(ParticleTypes.FLASH, caster.getX() + offsetX, caster.getY() -0.1f, caster.getZ() + offsetZ, 0, offsetX * 0.002, 0.02, offsetZ * 0.002, 1.0);
			}

			serverLevel.sendParticles(JerotesVillageParticleTypes.EARTHQUAKE.get(), caster.getX(), caster.getY()+0.1, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.EARTHQUAKE_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//战斗指示
	public static boolean CombatInstruction(LivingEntity caster, LivingEntity target, int spellLevelDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			List<Mob> list = serverLevel.getEntitiesOfClass(Mob.class, caster.getBoundingBox().inflate(spellLevelDistance, spellLevelDistance, spellLevelDistance));
			for (Mob mobFind : list) {
				if (mobFind == null) continue;
				if ((caster.distanceTo(mobFind)) > spellLevelDistance * 2) continue;
				if (!AttackFind.SameFactionAvoidDamage(caster, mobFind, false)) continue;
				if (caster instanceof Mob mobCaster) {
					if (mobFind == target) continue;
					if (mobCaster == mobFind.getTarget()) continue;
					if (target == null) continue;
					if (mobCaster.canAttack(target) && mobCaster.canAttackType(target.getType()) && !AttackFind.FindCanNotAttack(mobCaster, target)) {
						if (mobFind.canAttack(target) && mobFind.canAttackType(target.getType()) && !AttackFind.FindCanNotAttack(mobFind, target)) {
							mobFind.setTarget(target);
						}
					}
				}
				else if (caster instanceof Player player) {
					if (player == target) continue;
					if (player == mobFind.getTarget()) continue;
					if (target == null) continue;
					if (player.canAttack(target) && player.canAttackType(target.getType()) && !AttackFind.FindCanNotAttack(player, target)) {
						if (mobFind.canAttack(target) && mobFind.canAttackType(target.getType()) && !AttackFind.FindCanNotAttack(mobFind, target)) {
							mobFind.setTarget(target);
						}
					}
				}
			}
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.COMBAT_INSTRUCTION_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//奇灵冲撞
	public static boolean WonderlinRush(LivingEntity caster, int spellLevelDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			List<BaseWonderlinEntity> list = serverLevel.getEntitiesOfClass(BaseWonderlinEntity.class, caster.getBoundingBox().inflate(spellLevelDistance, spellLevelDistance, spellLevelDistance));
			for (BaseWonderlinEntity wonderlin : list) {
				if (wonderlin == null) continue;
				if (!wonderlin.canRush()) continue;
				if ((caster.distanceTo(wonderlin)) > spellLevelDistance * 2) continue;
				if (!AttackFind.SameFactionAvoidDamage(caster, wonderlin, false)) continue;
				if (caster instanceof Mob mobCaster) {
					if (wonderlin == mobCaster.getTarget()) continue;
				}
				if (caster == wonderlin.getTarget()) continue;
				wonderlin.setXRot(caster.getXRot());
				wonderlin.setYRot(caster.getYRot());
				wonderlin.setYHeadRot(caster.getYHeadRot());
				wonderlin.setYBodyRot(caster.yBodyRot);
				float f = caster.getYRot();
				float f2 = caster.getXRot();
				float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
				float f4 = -Mth.sin(f2 * 0.017453292f);
				float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
				float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
				float f7 = 1.5f;
				wonderlin.setOnGround(false);
				wonderlin.setDeltaMovement(wonderlin.getDeltaMovement().add(f3 *= f7 / f6 * 2, 0.7f, f5 *= f7 / f6 * 2));
				wonderlin.setWonderlinRush(true);
			}
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.WONDERLIN_RUSH_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//地图推断
	public static boolean MapInference(LivingEntity caster, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, int spellLevelOtherEffectTime, int spellLevelOtherEffectLevel, int spellLevelDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, caster.getBoundingBox().inflate(spellLevelDistance, spellLevelDistance, spellLevelDistance));
			for (LivingEntity livingEntityEffect : list) {
				if (livingEntityEffect == null) continue;
				if (livingEntityEffect == caster) continue;
				if ((caster.distanceTo(livingEntityEffect)) > spellLevelDistance * 2) continue;
				if (!AttackFind.SameFactionAvoidDamage(caster, livingEntityEffect, false)) continue;
				if (caster instanceof Mob mob && livingEntityEffect == mob.getTarget()) continue;
				if (livingEntityEffect instanceof Mob mob && caster == mob.getTarget()) continue;
				if (!livingEntityEffect.level().isClientSide) {
					livingEntityEffect.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, spellLevelMainEffectTime * 20, spellLevelMainEffectLevel - 1), caster);
					livingEntityEffect.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, spellLevelOtherEffectTime * 20, spellLevelOtherEffectLevel - 1), caster);
				}
			}
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.MAP_INFERENCE_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//不祥号角
	public static boolean OminousHorn(LivingEntity caster, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, int spellLevelOtherEffectTime, int spellLevelOtherEffectLevel, int spellLevelDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, caster.getBoundingBox().inflate(spellLevelDistance, spellLevelDistance, spellLevelDistance));
			for (LivingEntity livingEntityEffect : list) {
				if (livingEntityEffect == null) continue;
				if ((caster.distanceTo(livingEntityEffect)) > spellLevelDistance * 2) continue;
				if (!AttackFind.SameFactionAvoidDamage(caster, livingEntityEffect, false)) continue;
				if (caster instanceof Mob mob && livingEntityEffect == mob.getTarget()) continue;
				if (livingEntityEffect instanceof Mob mob && caster == mob.getTarget()) continue;
				if (!livingEntityEffect.level().isClientSide) {
					livingEntityEffect.addEffect(new MobEffectInstance(MobEffects.REGENERATION, spellLevelMainEffectTime * 20, spellLevelMainEffectLevel - 1), caster);
					livingEntityEffect.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, spellLevelOtherEffectTime * 20, spellLevelOtherEffectLevel - 1), caster);
				}
			}
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.OMINOUS_HORN_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//传送石传送
	public static boolean TeleportStoneTeleport(LivingEntity caster, Entity target, Entity findUse, float spellLevelDistance, boolean exchange, boolean near) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			if (!target.isShiftKeyDown()) {
				//位置交换
				if (exchange) {
					double x = target.getX();
					double y = target.getY();
					double z = target.getZ();
					target.teleportTo(caster.getX(), caster.getY(), caster.getZ());
					caster.teleportTo(x, y, z);
				}
				else {
					BlockPos summonPos;
					//靠近
					if (near) {
						summonPos = Main.findSpawnPositionNearFillOnBlock(caster, (int) (spellLevelDistance / 2));
						for (int find = 0; find < 16; ++find) {
							if ((summonPos.distToCenterSqr(findUse.position()) > target.distanceToSqr(findUse))) {
								summonPos = Main.findSpawnPositionNearFillOnBlock(caster, (int) (spellLevelDistance / 2));
							}
							else {
								break;
							}
						}
					}
					//远离
					else {
						summonPos = Main.findSpawnPositionNearFillOnBlock(caster, (int) (spellLevelDistance * 2));
						for (int find = 0; find < 16; ++find) {
							if ((summonPos.distToCenterSqr(findUse.position()) < target.distanceToSqr(findUse))) {
								summonPos = Main.findSpawnPositionNearFillOnBlock(caster, (int) (spellLevelDistance * 2));
							}
							else {
								break;
							}
						}
					}
					target.teleportTo(summonPos.getX(), summonPos.getY(), summonPos.getZ());
				}
			}
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.TELEPORT_STONE_TELEPORT_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//缩地成寸
	public static boolean VineTeleport(LivingEntity caster, Entity target, Entity findUse, LivingEntity targetPos, float spellLevelDistance, boolean near) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			BlockPos summonPos;
			//靠近
			if (near) {
				summonPos = Main.findSpawnPositionNearFillOnBlock(targetPos, (int) (spellLevelDistance / 2));
				for (int find = 0; find < 16; ++find) {
					if ((summonPos.distToCenterSqr(findUse.position()) > target.distanceToSqr(findUse))) {
						summonPos = Main.findSpawnPositionNearFillOnBlock(caster, (int) (spellLevelDistance / 2));
					}
					else {
						break;
					}
				}
			}
			//远离
			else {
				summonPos = Main.findSpawnPositionNearFillOnBlock(targetPos, (int) (spellLevelDistance));
				for (int find = 0; find < 16; ++find) {
					if ((summonPos.distToCenterSqr(findUse.position()) < target.distanceToSqr(findUse))) {
						summonPos = Main.findSpawnPositionNearFillOnBlock(caster, (int) (spellLevelDistance));
					}
					else {
						break;
					}
				}
			}
			//踪迹
			Vec3 vec3 = caster.getEyePosition();
			Vec3 vec32 = new Vec3(summonPos.getX(), summonPos.getY() + 1, summonPos.getZ()).subtract(vec3);
			Vec3 vec33 = vec32.normalize();
			for (int i = 1; i < Mth.floor(vec32.length()) * 6 + 1; ++i) {
				Vec3 vec34 = vec3.add(vec33.scale(i / 6f));
				serverLevel.sendParticles(ParticleTypes.CHERRY_LEAVES, vec34.x, vec34.y, vec34.z, 1, 0.0, 0.0, 0.0, 0.0);
			}
			//切换方块
			BlockState blockStateOld = serverLevel.getBlockState(caster.getOnPos());
			BlockState blockStateNew = serverLevel.getBlockState(new BlockPos(summonPos.getX(), summonPos.getY(), summonPos.getZ()).below());
			float blockOld = blockStateOld.getDestroySpeed(serverLevel, caster.getOnPos());
			float blockNew = blockStateNew.getDestroySpeed(serverLevel, new BlockPos(summonPos.getX(), summonPos.getY(), summonPos.getZ()).below());
			if (blockOld < 10 && blockOld >= 0f && blockNew < 10 && blockNew >= 0f && !blockStateOld.isAir() && !blockStateNew.isAir()) {
				serverLevel.setBlock(new BlockPos(summonPos.getX(), summonPos.getY(), summonPos.getZ()).below(), blockStateOld, 3);
				serverLevel.setBlock(caster.getOnPos(), blockStateNew, 3);
			}
			target.teleportTo(summonPos.getX(), summonPos.getY(), summonPos.getZ());
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.VINE_TELEPORT_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//旋风裹身
	public static boolean WhirlwindShroud(LivingEntity caster, Entity target, Entity findUse, LivingEntity targetPos, float spellLevelDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			BlockPos summonPos;
			//靠近
			summonPos = Main.findSpawnPositionNearFillOnBlock(targetPos, (int) (spellLevelDistance / 2));
			for (int find = 0; find < 16; ++find) {
				if ((summonPos.distToCenterSqr(findUse.position()) > target.distanceToSqr(findUse))) {
					summonPos = Main.findSpawnPositionNearFillOnBlock(caster, (int) (spellLevelDistance / 2));
				}
				else {
					break;
				}
			}
			//踪迹
			Vec3 vec3 = caster.getEyePosition();
			Vec3 vec32 = new Vec3(summonPos.getX(), summonPos.getY() + 1, summonPos.getZ()).subtract(vec3);
			Vec3 vec33 = vec32.normalize();
			for (int i = 1; i < Mth.floor(vec32.length()) * 6 + 1; ++i) {
				Vec3 vec34 = vec3.add(vec33.scale(i / 6f));
				serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, vec34.x, vec34.y, vec34.z, 1, 0.0, 0.0, 0.0, 0.0);
			}
			//切换方块
			BlockState blockStateOld = serverLevel.getBlockState(caster.getOnPos());
			BlockState blockStateNew = serverLevel.getBlockState(new BlockPos(summonPos.getX(), summonPos.getY(), summonPos.getZ()).below());
			float blockOld = blockStateOld.getDestroySpeed(serverLevel, caster.getOnPos());
			float blockNew = blockStateNew.getDestroySpeed(serverLevel, new BlockPos(summonPos.getX(), summonPos.getY(), summonPos.getZ()).below());
			if (blockOld < 10 && blockOld >= 0f && blockNew < 10 && blockNew >= 0f && !blockStateOld.isAir() && !blockStateNew.isAir()) {
				serverLevel.setBlock(new BlockPos(summonPos.getX(), summonPos.getY(), summonPos.getZ()).below(), blockStateOld, 3);
				serverLevel.setBlock(caster.getOnPos(), blockStateNew, 3);
			}
			target.teleportTo(summonPos.getX(), summonPos.getY(), summonPos.getZ());
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.WHIRLWIND_SHROUD_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//腐蚀雪云$法术
	public static boolean CorrosiveSnowCloud(LivingEntity caster, LivingEntity target, int spellLevelDuration, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, int count, float summonHeight) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			for (int i = 0; i < count; ++i) {
				Vec3 targetPos = caster.getPosition(0);
				if (target != null) {
					targetPos = new Vec3(target.getX() + 0.5D, (double)(target.getY() + summonHeight), target.getZ() + 0.5D);
				}
				if (caster instanceof Player && (target == null || target == caster)) {
					Vec3 startPos = caster.getEyePosition(1.0f);
					Vec3 viewVector = caster.getViewVector(1.0f);
					Vec3 endPos = startPos.add(viewVector.scale(36));

					BlockHitResult hitResult = serverLevel.clip(new ClipContext(
							startPos, endPos,
							ClipContext.Block.OUTLINE,
							ClipContext.Fluid.NONE,
							caster
					));
					targetPos = Main.adjustPositionForSolidHit(hitResult, startPos, viewVector, 36);
				}
				//目标位置
				RainSpellCloudEntity cloud = new CorrosiveSnowCloudEntity(spellLevelMainEffectTime, spellLevelMainEffectLevel, serverLevel, targetPos.x, targetPos.y, targetPos.z);
				cloud.setOwner(caster);
				cloud.spellLevelDamage = spellLevelMainEffectLevel;
				cloud.setParticle(JerotesVillageParticleTypes.CORROSIVER_BREATH_FOG.get());
				cloud.setRainParticle(ParticleTypes.SNOWFLAKE);
				cloud.setRadius(1f);
				cloud.setDuration(20 * spellLevelDuration);
				cloud.setRadiusPerTick((2.0f - cloud.getRadius()) / (float)cloud.getDuration());
				cloud.setSpellUsefulHeight(16);
				cloud.addEffect(new MobEffectInstance(JerotesMobEffects.CORROSIVE.get(), 20 * spellLevelMainEffectTime, spellLevelMainEffectLevel-1));
				serverLevel.addFreshEntity(cloud);
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	//不洁血雨$法术
	public static boolean UncleanBloodRain(LivingEntity caster, LivingEntity target, int spellLevelDuration, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, int count, float summonHeight) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			for (int i = 0; i < count; ++i) {
				Vec3 targetPos = caster.getPosition(0);
				if (target != null) {
					targetPos = new Vec3(target.getX() + 0.5D, (double)(target.getY() + summonHeight), target.getZ() + 0.5D);
				}
				if (caster instanceof Player && (target == null || target == caster)) {
					Vec3 startPos = caster.getEyePosition(1.0f);
					Vec3 viewVector = caster.getViewVector(1.0f);
					Vec3 endPos = startPos.add(viewVector.scale(36));

					BlockHitResult hitResult = serverLevel.clip(new ClipContext(
							startPos, endPos,
							ClipContext.Block.OUTLINE,
							ClipContext.Fluid.NONE,
							caster
					));
					targetPos = Main.adjustPositionForSolidHit(hitResult, startPos, viewVector, 36);
				}
				//目标位置
				RainSpellCloudEntity cloud = new UncleanBloodRainEntity(spellLevelMainEffectTime, spellLevelMainEffectLevel, serverLevel, targetPos.x, targetPos.y, targetPos.z);
				cloud.setOwner(caster);
				cloud.spellLevelDamage = spellLevelMainEffectLevel;
				cloud.setParticle(JerotesVillageParticleTypes.UNCLEAN_BLOOD_RAIN_FOG.get());
				cloud.setRainParticle(JerotesVillageParticleTypes.UNCLEAN_BLOOD_RAIN.get());
				cloud.setRadius(1.5f);
				cloud.setDuration(20 * spellLevelDuration);
				cloud.setRadiusPerTick((3f - cloud.getRadius()) / (float)cloud.getDuration());
				cloud.setSpellUsefulHeight(24);
				cloud.addEffect(new MobEffectInstance(JerotesVillageMobEffects.UNCLEAN_BODY.get(), 20 * spellLevelMainEffectTime, spellLevelMainEffectLevel-1));
				serverLevel.addFreshEntity(cloud);
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	//不洁血雾$法术
	public static boolean UncleanBloodFog(LivingEntity caster, LivingEntity target, int spellLevelDuration, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, int spellLevelOffEffectTime, int spellLevelOffEffectLevel, int count) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			for (int i = 0; i < count; ++i) {
				Vec3 targetPos = caster.getPosition(0);
				if (target != null) {
					targetPos = new Vec3(target.getX() + 0.5D, target.getY() + i * 0.5f, target.getZ() + 0.5D);
				}
				if (caster instanceof Player && (target == null || target == caster)) {
					Vec3 startPos = caster.getEyePosition(1.0f);
					Vec3 viewVector = caster.getViewVector(1.0f);
					Vec3 endPos = startPos.add(viewVector.scale(36));

					BlockHitResult hitResult = serverLevel.clip(new ClipContext(
							startPos, endPos,
							ClipContext.Block.OUTLINE,
							ClipContext.Fluid.NONE,
							caster
					));
					targetPos = Main.adjustPositionForSolidHit(hitResult, startPos, viewVector, 36).add(0, i * 0.5f, 0);
				}
				//目标位置
				SpellCloudEntity cloud = new SpellCloudEntity(serverLevel, targetPos.x, targetPos.y, targetPos.z);
				cloud.setOwner(caster);
				cloud.spellLevelDamage = spellLevelMainEffectLevel;
				cloud.setParticle(JerotesVillageParticleTypes.UNCLEAN_BLOOD_FOG.get());
				cloud.setParticleScaleMultiple(0.5f);
				cloud.setRadius(1.5f);
				cloud.setDuration(20 * spellLevelDuration);
				cloud.setRadiusPerTick((3f - cloud.getRadius()) / (float)cloud.getDuration());
				cloud.addEffect(new MobEffectInstance(JerotesVillageMobEffects.UNCLEAN_BODY.get(), 20 * spellLevelMainEffectTime, spellLevelMainEffectLevel-1));
				cloud.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * spellLevelOffEffectTime, spellLevelOffEffectLevel-1));
				serverLevel.addFreshEntity(cloud);
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	//目眩神驰$法术
	public static boolean DazzlingLightFog(LivingEntity caster, LivingEntity target, int spellLevelDuration, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, int spellLevelOffEffectTime, int spellLevelOffEffectLevel, int count) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			for (int i = 0; i < count; ++i) {
				Vec3 targetPos = caster.getPosition(0);
				if (target != null) {
					targetPos = new Vec3(target.getX() + 0.5D, target.getY() + i * 0.5f, target.getZ() + 0.5D);
				}
				if (caster instanceof Player && (target == null || target == caster)) {
					Vec3 startPos = caster.getEyePosition(1.0f);
					Vec3 viewVector = caster.getViewVector(1.0f);
					Vec3 endPos = startPos.add(viewVector.scale(36));

					BlockHitResult hitResult = serverLevel.clip(new ClipContext(
							startPos, endPos,
							ClipContext.Block.OUTLINE,
							ClipContext.Fluid.NONE,
							caster
					));
					targetPos = Main.adjustPositionForSolidHit(hitResult, startPos, viewVector, 36).add(0, i * 0.5f, 0);
				}
				//目标位置
				SpellCloudEntity cloud = new SpellCloudEntity(serverLevel, targetPos.x, targetPos.y, targetPos.z);
				cloud.setOwner(caster);
				cloud.spellLevelDamage = spellLevelMainEffectLevel;
				cloud.setParticle(JerotesVillageParticleTypes.DAZZLING_LIGHT_FOG.get());
				cloud.setParticleScaleMultiple(0.5f);
				cloud.setRadius(1.5f);
				cloud.setDuration(20 * spellLevelDuration);
				cloud.setRadiusPerTick((3f - cloud.getRadius()) / (float)cloud.getDuration());
				cloud.addEffect(new MobEffectInstance(JerotesVillageMobEffects.DAZZLING_LIGHT.get(), 5, 0));
				cloud.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20 * spellLevelMainEffectTime, 0));
				cloud.addEffect(new MobEffectInstance(MobEffects.GLOWING, 20 * spellLevelOffEffectTime, 0));
				serverLevel.addFreshEntity(cloud);
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	//神汉低语$法术
	public static boolean WarlockWhisper(LivingEntity caster, int spellLevelUseHealthMultiplier, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, int spellLevelOtherEffectTime, int spellLevelOtherEffectLevel, int spellLevelAwayX, int spellLevelAwayY, double spellLevelAwaySpeedMultiple, int spellLevelDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, caster.getBoundingBox().inflate(spellLevelDistance, spellLevelDistance, spellLevelDistance));
			for (LivingEntity livingEntityControl : list) {
				if (livingEntityControl == null) continue;
				if ((caster.distanceTo(livingEntityControl)) > spellLevelDistance * 2) continue;
				if (AttackFind.FindCanNotAttack(caster, livingEntityControl)) continue;
				if (!Main.canSee(livingEntityControl, caster) || !caster.hasLineOfSight(livingEntityControl)) continue;
				if (!livingEntityControl.level().isClientSide) {
					livingEntityControl.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 20 * spellLevelMainEffectTime, spellLevelMainEffectLevel - 1), caster);
					livingEntityControl.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20 * spellLevelOtherEffectTime * 6, spellLevelOtherEffectLevel - 1), caster);
				}
				if (livingEntityControl instanceof PathfinderMob mob && mob.getHealth() < caster.getHealth() * spellLevelUseHealthMultiplier) {
					if (mob.onGround()) {
						Vec3 randomShake = new Vec3(mob.getRandom().nextFloat() - 0.5F, 0, mob.getRandom().nextFloat() - 0.5F).scale(0.1F);
						mob.setDeltaMovement(mob.getDeltaMovement().multiply(0.7F, 1, 0.7F).add(randomShake));
					}
					Vec3 vec = LandRandomPos.getPosAway(mob, spellLevelAwayX, spellLevelAwayY, caster.position());
					if (vec != null) {
						mob.getNavigation().moveTo(vec.x, vec.y, vec.z, spellLevelAwaySpeedMultiple);
					}
				}
			}
		}
		return true;
	}
	//雪怪恐吓$法术
	public static boolean YetiDeter(LivingEntity caster, LivingEntity target, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, int spellLevelSelfEffectLevel, int spellLevelDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, caster.getBoundingBox().inflate(spellLevelDistance, spellLevelDistance, spellLevelDistance));
			for (LivingEntity livingEntityControl : list) {
				if (livingEntityControl == null) continue;
				if (livingEntityControl instanceof FrostYetiEntity) continue;
				if (livingEntityControl instanceof SnowyApeEntity) continue;
				if (livingEntityControl instanceof GiantMonsterEntity giantMonsterEntity && giantMonsterEntity.isBitterColdBell()) continue;
				if (AttackFind.FindCanNotAttack(caster, livingEntityControl)) continue;
				if (!Main.canSee(livingEntityControl, caster) || !caster.hasLineOfSight(livingEntityControl)) continue;
				if (livingEntityControl.getHealth() >= caster.getHealth()) continue;
				if (!livingEntityControl.level().isClientSide) {
					livingEntityControl.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 20 * spellLevelMainEffectTime, spellLevelMainEffectLevel - 1), caster);
					livingEntityControl.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20 * spellLevelMainEffectTime * 6, spellLevelMainEffectLevel - 1), caster);
					livingEntityControl.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * spellLevelMainEffectTime * 3, spellLevelMainEffectLevel - 1), caster);
				}
			}
			if (target != null && target != caster) {
				boolean bl2 = target.getItemBySlot(EquipmentSlot.HEAD).is(ItemTags.FREEZE_IMMUNE_WEARABLES) || target.getItemBySlot(EquipmentSlot.CHEST).is(ItemTags.FREEZE_IMMUNE_WEARABLES) || target.getItemBySlot(EquipmentSlot.LEGS).is(ItemTags.FREEZE_IMMUNE_WEARABLES) || target.getItemBySlot(EquipmentSlot.FEET).is(ItemTags.FREEZE_IMMUNE_WEARABLES);
				if (!(target.getHealth() >= caster.getHealth() || bl2)) {
					if (!target.level().isClientSide) {
						target.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 20 * spellLevelMainEffectTime, spellLevelMainEffectLevel - 1), caster);
						target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20 * spellLevelMainEffectTime * 6, spellLevelMainEffectLevel - 1), caster);
						target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * spellLevelMainEffectTime * 3, spellLevelMainEffectLevel - 1), caster);
					}
				}
			}
			if (!caster.level().isClientSide()) {
				caster.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * spellLevelMainEffectTime, spellLevelSelfEffectLevel - 1), caster);
			}
		}
		return true;
	}
	//驱鬼禳灾$法术
	public static boolean UndeadBanishing(LivingEntity caster, int spellLevelDamage, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, int spellLevelDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, caster.getBoundingBox().inflate(spellLevelDistance, spellLevelDistance, spellLevelDistance));
			for (LivingEntity livingEntityControl : list) {
				if (livingEntityControl == null) continue;
				if (AttackFind.FindCanNotAttack(caster, livingEntityControl)) continue;
				if (livingEntityControl.getMobType() != MobType.UNDEAD) continue;
				DamageSource damageSource = AttackFind.findDamageType(caster, JerotesDamageTypes.RADIANT, caster);

				JerotesStopSpellEvent event = new JerotesStopSpellEvent(livingEntityControl, null, null, caster, 1, spellLevelDamage);
				MinecraftForge.EVENT_BUS.post(event);
				if (event.isCanceled()) {
					continue;
				}
				else {
					livingEntityControl.hurt(damageSource, Main.rollDice(spellLevelDamage, 6, caster.getRandom()));
					if (!livingEntityControl.level().isClientSide()) {
						livingEntityControl.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20 * spellLevelMainEffectTime * 6, spellLevelMainEffectLevel - 1), caster);
					}
					if ((Main.mobSizeSmall(livingEntityControl) || Main.mobSizeMedium(livingEntityControl) || Main.mobSizeLarge(livingEntityControl))
							&& !EntityAndItemFind.isNoSpecialKnockback(livingEntityControl.getType())) {
						double d = 0.0;
						if (livingEntityControl.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
							d = Math.max(livingEntityControl.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0);
						}
						double d3 = Math.max(0, 1 - d) + 0.2;
						livingEntityControl.setOnGround(false);
						livingEntityControl.setDeltaMovement(livingEntityControl.getDeltaMovement().add(
								-(caster.getX() - livingEntityControl.getX()) * 1 * d3,
								-(caster.getY() - livingEntityControl.getY()) * 1 * d3,
								-(caster.getZ() - livingEntityControl.getZ()) * 1 * d3));
					}
					for (int i = 0; i < 32; ++i) {
						serverLevel.sendParticles(ParticleTypes.ENCHANTED_HIT,
								livingEntityControl.getRandomX(1.25),
								livingEntityControl.getRandomY(),
								livingEntityControl.getRandomZ(1.25), 0,
								caster.getRandom().nextFloat() / 5 - 0.1f,
								caster.getRandom().nextFloat() / 5 - 0.1f,
								caster.getRandom().nextFloat() / 5 - 0.1f,
								0.5);
					}
					serverLevel.sendParticles(JerotesVillageParticleTypes.UNDEAD_BANISHING.get(), livingEntityControl.getX(), livingEntityControl.getY() + 0.1f, livingEntityControl.getZ(), 0, 0, 0, 0, 0);
				}
			}
			serverLevel.sendParticles(JerotesVillageParticleTypes.UNDEAD_BANISHING.get(), caster.getX(), caster.getY() + 0.1f, caster.getZ(), 0, 0, 0, 0, 0);
		}
		return true;
	}
	//蛇龙刺枪之雨
	public static boolean RainofSerponPike(LivingEntity caster, LivingEntity target, int countMin, int countMax, int summonDistance, int summonHeight) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			int count = countMin;
			if (countMin < countMax) {
				count = Main.randomReach(caster.getRandom(), countMin, countMax);
			}
			if (target == null) {
				return false;
			}
			for (int i = 0; i < count; ++i) {
				//目标位置
				BlockPos summonPos = new BlockPos(Main.findSpawnPositionNearFill(target, summonDistance).getX(), Main.findSpawnPositionNearFill(target, summonDistance).getY() + summonHeight, Main.findSpawnPositionNearFill(target, summonDistance).getZ()) ;
				ThrownSerponJavelinEntity pike = JerotesVillageEntityType.THROWN_SERPON_JAVELIN.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
				if (pike != null) {
					serverLevel.sendParticles(JerotesVillageParticleTypes.SERPON_JAVELIN.get(), pike.getX(), pike.getY(1.5), pike.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
					serverLevel.sendParticles(JerotesVillageParticleTypes.SERPON_JAVELIN_DOWN.get(), pike.getX(), pike.getY(1.5), pike.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
					pike.setOwner(caster);
					pike.isSummon = true;
					pike.setBaseDamage(12);
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.RAIN_OF_SERPON_JAVELIN_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}

	//瑟潘珀斯陨石术$法术
	public static boolean SerpenperthMeteor(LivingEntity caster, LivingEntity target, int spellLevelDamage, float spellLevelExplode, int countMin, int countMax, int summonDistance, float summonHeight, int spellSubcategory) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			int count = countMin;
			if (countMin < countMax) {
				count = Main.randomReach(caster.getRandom(), countMin, countMax);
			}

			Vec3 targetPos = caster.getPosition(0);
			if (target != null) {
				targetPos = new Vec3(target.getX() + 0.5D, target.getY(), target.getZ() + 0.5D);
			}
			if (caster instanceof Player && (target == null || target == caster)) {
				Vec3 startPos = caster.getEyePosition(1.0f);
				Vec3 viewVector = caster.getViewVector(1.0f);
				Vec3 endPos = startPos.add(viewVector.scale(45));

				BlockHitResult hitResult = serverLevel.clip(new ClipContext(
						startPos, endPos,
						ClipContext.Block.OUTLINE,
						ClipContext.Fluid.NONE,
						caster
				));
				targetPos = Main.adjustPositionForSolidHit(hitResult, startPos, viewVector, 45);
			}
			for (int i = 0; i < count; ++i) {
				if (spellSubcategory == 1) {
					for (int i2 = 0; i2 < 2; ++i2) {
						//目标位置
						summonDistance = 4;
						BlockPos summonPos = Main.findSpawnPositionNearFill(caster.level(), BlockPos.containing(targetPos), (int) (summonDistance));
						OldSerpenperthMeteorEntity meteor = new OldSerpenperthMeteorEntity(spellLevelDamage, spellLevelExplode, serverLevel, caster);
						meteor.setPos((double) summonPos.getX() + 0.5D, (double) (summonPos.getY() + 1 + summonHeight), (double) summonPos.getZ() + 0.5D);
						meteor.setDeltaMovement(meteor.getDeltaMovement().add((double) (caster.getRandom().nextInt(2 * summonDistance) - summonDistance) / 10f, 0f, (double) (caster.getRandom().nextInt(2 * summonDistance) - summonDistance) / 10f));
						serverLevel.sendParticles(JerotesVillageParticleTypes.SERPON_JAVELIN.get(), meteor.getX(), meteor.getY(1.5), meteor.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
						serverLevel.sendParticles(JerotesVillageParticleTypes.SERPON_JAVELIN_DOWN.get(), meteor.getX(), meteor.getY(1.5), meteor.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
						meteor.setOwner(caster);
						serverLevel.addFreshEntity(meteor);
					}
				}
				else {
					BlockPos shootPos = Main.findSpawnPositionNearFill(caster.level(), BlockPos.containing(targetPos), (int) (summonDistance/3f));
					Vec3 shootPosVec3 = new Vec3(shootPos.getX() + 0.5f, shootPos.getY() + summonHeight, shootPos.getZ() + 0.5f);
					BlockPos targetPosNew = Main.findSpawnPositionNearFill(caster.level(), BlockPos.containing(targetPos), summonDistance);
					SerpenperthMeteorEntity cloud = new SerpenperthMeteorEntity(serverLevel, caster);
					cloud.setStartPos(shootPosVec3);
					cloud.setSpellLevelDamage(spellLevelDamage);
					cloud.setPos(targetPosNew.getX() + 0.5f, targetPos.y() + 2/16f, targetPosNew.getZ() + 0.5f);
					cloud.setOwner(caster);
					cloud.setOrder(i);
					serverLevel.addFreshEntity(cloud);
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	//莫厄机关
	public static boolean MerorTrap(LivingEntity caster, LivingEntity target, int countMin, int countMax, int summonDistance, int summonDistanceTarget) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			int count = countMin;
			if (countMin < countMax) {
				count = Main.randomReach(caster.getRandom(), countMin, countMax);
			}
			if (target == null) {
				return false;
			}
			for (int i = 0; i < count/2; ++i) {
				//自身位置
				BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(caster, summonDistance);
				MerorTrapEntity trap = JerotesVillageEntityType.MEROR_TRAP.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
				//目标位置
				BlockPos summonPosTarget = Main.findSpawnPositionNearFillOnBlock(target, summonDistanceTarget);
				MerorTrapEntity trapTarget = JerotesVillageEntityType.MEROR_TRAP.get().spawn(serverLevel, BlockPos.containing(summonPosTarget.getX(), summonPosTarget.getY(), summonPosTarget.getZ()), MobSpawnType.MOB_SUMMONED);
				if (trap != null) {
					trap.setOwner(caster);
				}
				if (trapTarget != null) {
					trapTarget.setOwner(caster);
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.MEROR_TRAP_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//莫厄地雷
	public static boolean MerorMines(LivingEntity caster, LivingEntity target, int countMin, int countMax, int summonDistance, int summonDistanceTarget) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			int count = countMin;
			if (countMin < countMax) {
				count = Main.randomReach(caster.getRandom(), countMin, countMax);
			}
			if (target == null) {
				return false;
			}
			for (int i = 0; i < count/2; ++i) {
				//自身位置
				BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(caster, summonDistance);
				MerorBombEntity trap = JerotesVillageEntityType.MEROR_BOMB.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
				//目标位置
				BlockPos summonPosTarget = Main.findSpawnPositionNearFillOnBlock(target, summonDistanceTarget);
				MerorBombEntity trapTarget = JerotesVillageEntityType.MEROR_BOMB.get().spawn(serverLevel, BlockPos.containing(summonPosTarget.getX(), summonPosTarget.getY(), summonPosTarget.getZ()), MobSpawnType.MOB_SUMMONED);
				String string = ChatFormatting.stripFormatting(caster.getName().getString());
				if (trap != null) {
					if ("Redstone Monstrosity".equals(string) || "红石巨兽".equals(string)) {
						trap.setOwnerType(2);
					}
					else {
						trap.setOwnerType(1);
					}
					trap.setOwner(caster);
					trap.setMine(true);
					trap.setDeltaMovement(0d, 0d, 0d);
					trap.setNoGravity(true);
				}
				if (trapTarget != null) {
					if ("Redstone Monstrosity".equals(string) || "红石巨兽".equals(string)) {
						trapTarget.setOwnerType(2);
					}
					else {
						trapTarget.setOwnerType(1);
					}
					trapTarget.setOwner(caster);
					trap.setMine(true);
					trapTarget.setDeltaMovement(0d, 0d, 0d);
					trapTarget.setNoGravity(true);
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.MEROR_MINES_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//不祥齿轮
	public static boolean OminousGear(LivingEntity caster, LivingEntity target, int spellLevelDamage, float spellLevelDistance, int targetCount) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			int attackCount = 1;
			if (target != null && target != caster && target.distanceTo(caster) <= spellLevelDistance) {
				if (!target.isShiftKeyDown()) {
					BlockPos summonPos = target.getOnPos().above();
					OminousGearEntity gear = JerotesVillageEntityType.OMINOUS_GEAR.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
					if (gear != null) {
						gear.setOwner(caster);
						gear.setSpellLevelDamage(spellLevelDamage);
					}
				}
			}
			else {
				attackCount = 0;
			}
			List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, caster.getBoundingBox().inflate(spellLevelDistance, spellLevelDistance, spellLevelDistance));
			Collections.shuffle(list, new java.util.Random());
			list.sort(Comparator.comparingDouble(caster::distanceToSqr));
			for (LivingEntity find : list) {
				if (find == null) continue;
				if (find == caster) continue;
				if (find == target) continue;
				if (attackCount >= targetCount) break;
				if ((caster.distanceTo(find)) > spellLevelDistance * 2) continue;
				if (find instanceof EntityTrueBasePart) continue;
				if (AttackFind.FindCanNotAttack(caster, find)) continue;
				if (find.isShiftKeyDown()) continue;
				attackCount += 1;
				BlockPos summonPos = find.getOnPos().above();
				OminousGearEntity gear = JerotesVillageEntityType.OMINOUS_GEAR.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
				if (gear != null) {
					gear.setOwner(caster);
					gear.setSpellLevelDamage(spellLevelDamage);
				}
			}
			if (attackCount < targetCount) {
				for (int j = attackCount; j < targetCount; ++j) {
					BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(caster, (int) spellLevelDistance);
					OminousGearEntity gear = JerotesVillageEntityType.OMINOUS_GEAR.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
					if (gear != null) {
						gear.setOwner(caster);
						gear.setSpellLevelDamage(spellLevelDamage);
					}
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.OMINOUS_GEAR_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//邪祟召唤$法术
	public static boolean EvilSummoning(LivingEntity caster, LivingEntity target, int spellLevel, int countMin, int countMax, int summonDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			int count = countMin;
			if (countMin < countMax) {
				count = Main.randomReach(caster.getRandom(), countMin, countMax);
			}
			for (int i = 0; i < count; ++i) {
				//自身位置
				BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(target, summonDistance);
				UncleanTentacleEntity uncleanTentacle = JerotesVillageEntityType.UNCLEAN_TENTACLE.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
				//目标位置
				if (uncleanTentacle != null) {
					Main.conjureAbout(caster, uncleanTentacle);
					uncleanTentacle.setOwner(caster);
					uncleanTentacle.setSpellLevelDamage(spellLevel);
					if (teams != null) {
						serverLevel.getScoreboard().addPlayerToTeam(uncleanTentacle.getStringUUID(), teams);
					}
					if (target != null && target != caster) {
						uncleanTentacle.setTarget(target);
					}
					ParticlesUse.summonParticle(serverLevel, uncleanTentacle, uncleanTentacle.getX(), uncleanTentacle.getY(), uncleanTentacle.getZ(),
							0xb8003c, 0xff0055);
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	//枯木逢春$法术
	public static boolean SpringRejuvenation(LivingEntity caster, int spelllevel, int count, int summonDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			for (int n = 0; n < count; ++n) {
				float f2 = 4 + (float) n * (float) Math.PI * 2.0f / count;
				SpringRejuvenation(caster, caster, spelllevel, caster.getX() + (double) Mth.cos(f2) * summonDistance, caster.getZ() + (double) Mth.sin(f2) * summonDistance, caster.getY(), caster.getY() + 1, f2);
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	static void SpringRejuvenation(LivingEntity caster, LivingEntity target, int spelllevel, double d, double d2, double d3, double d4, float f) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();

			BlockPos blockPos = BlockPos.containing(d, d4, d2);
			boolean bl = false;
			double d5 = 0.0;
			do {
				BlockState blockState;
				VoxelShape voxelShape;
				BlockPos blockPos2 = blockPos.below();
				BlockState blockState2 = caster.level().getBlockState(blockPos2);
				if (!blockState2.isFaceSturdy(caster.level(), blockPos2, Direction.UP)) continue;
				if (!caster.level().isEmptyBlock(blockPos) && !(voxelShape = (blockState = caster.level().getBlockState(blockPos)).getCollisionShape(caster.level(), blockPos)).isEmpty()) {
					d5 = voxelShape.max(Direction.Axis.Y);
				}
				bl = true;
				break;
			} while ((blockPos = blockPos.below()).getY() >= Mth.floor(d3) - 1);
			if (bl) {
				CelestialCoilvineFlowerEntity celestialCoilvineFlowerEntity = new CelestialCoilvineFlowerEntity(caster.level(), d, (double) blockPos.getY() + d5, d2, f, caster);
				celestialCoilvineFlowerEntity.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(celestialCoilvineFlowerEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
				celestialCoilvineFlowerEntity.setOwner(caster);
				celestialCoilvineFlowerEntity.setSpellLevelDamage(spelllevel);
				Main.conjureAbout(caster, celestialCoilvineFlowerEntity);
				if (teams != null) {
					serverLevel.getScoreboard().addPlayerToTeam(celestialCoilvineFlowerEntity.getStringUUID(), teams);
				}
				ParticlesUse.summonParticle(serverLevel, celestialCoilvineFlowerEntity, celestialCoilvineFlowerEntity.getX(), celestialCoilvineFlowerEntity.getY(), celestialCoilvineFlowerEntity.getZ(),
						0x390300, 0xffb0c7);

				Vec3 vec3 = caster.getEyePosition();
				Vec3 vec32 = celestialCoilvineFlowerEntity.getEyePosition().subtract(vec3);
				Vec3 vec33 = vec32.normalize();
				for (int i = 1; i < Mth.floor(vec32.length()) * 6 + 1; ++i) {
					Vec3 vec34 = vec3.add(vec33.scale(i / 6f));
					serverLevel.sendParticles(ParticleTypes.CHERRY_LEAVES, vec34.x, vec34.y, vec34.z, 1, 0.0, 0.0, 0.0, 0.0);
				}

				caster.level().addFreshEntity(celestialCoilvineFlowerEntity);
				caster.level().gameEvent(GameEvent.ENTITY_PLACE, new Vec3(d, (double) blockPos.getY() + d5, d2), GameEvent.Context.of(caster));
			}
		}
	}
	//喝茅成剑$法术
	public static boolean CogonSword(LivingEntity caster, LivingEntity target, int spellLevel, int countMin, int countMax, int summonDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			int count = countMin;
			if (countMin < countMax) {
				count = Main.randomReach(caster.getRandom(), countMin, countMax);
			}

			//自身位置
			BlockPos summonPos;
			float facing;
			for (int i = 0; i < 4; ++i) {
				if (i == 0) {
					summonPos = target.getOnPos().above().relative(Direction.EAST);
					facing = 90f;
				}
				else if (i == 1) {
					summonPos = target.getOnPos().above().relative(Direction.SOUTH);
					facing = 180f;
				}
				else if (i == 2) {
					summonPos = target.getOnPos().above().relative(Direction.WEST);
					facing = 270f;
				}
				else {
					summonPos = target.getOnPos().above().relative(Direction.NORTH);
					facing = 0f;
				}
				CogonSwordEntity cogonSword = JerotesVillageEntityType.COGON_SWORD.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
				if (cogonSword != null) {
					Main.conjureAbout(caster, cogonSword);
					cogonSword.setOwner(caster);
					cogonSword.setSpellLevelDamage(spellLevel);
					if (teams != null) {
						serverLevel.getScoreboard().addPlayerToTeam(cogonSword.getStringUUID(), teams);
					}
					if (target != null && target != caster) {
						cogonSword.setTarget(target);
					}
//					ParticlesUse.summonParticle(serverLevel, cogonSword, cogonSword.getX(), cogonSword.getY(), cogonSword.getZ(),
//							0x2f4219, 0x8fe132);
					cogonSword.setYRot(facing);
					cogonSword.setYHeadRot(facing);
					cogonSword.setYBodyRot(facing);
					cogonSword.moveTo(summonPos, 0, facing);
				}
			}
			for (int i = 0; i < count - 4; ++i) {
				//目标位置
				BlockPos summonPosSelf = Main.findSpawnPositionNearFillOnBlock(caster, summonDistance);
				CogonSwordEntity cogonSword = JerotesVillageEntityType.COGON_SWORD.get().spawn(serverLevel, BlockPos.containing(summonPosSelf.getX(), summonPosSelf.getY(), summonPosSelf.getZ()), MobSpawnType.MOB_SUMMONED);
				if (cogonSword != null) {
					Main.conjureAbout(caster, cogonSword);
					cogonSword.setOwner(caster);
					cogonSword.setSpellLevelDamage(spellLevel);
					if (teams != null) {
						serverLevel.getScoreboard().addPlayerToTeam(cogonSword.getStringUUID(), teams);
					}
					if (target != null && target != caster) {
						cogonSword.setTarget(target);
					}
//					ParticlesUse.summonParticle(serverLevel, cogonSword, cogonSword.getX(), cogonSword.getY(), cogonSword.getZ(),
//							0x2f4219, 0x8fe132);
				}
			}
			serverLevel.sendParticles(JerotesVillageParticleTypes.COGON_SWORD.get(), caster.getX(), caster.getY() + 0.1f, caster.getZ(), 0, 0, 0, 0, 0);
			serverLevel.sendParticles(JerotesVillageParticleTypes.COGON_SWORD.get(), caster.getX(), caster.getY() + 0.1f, caster.getZ(), 0, 0, 0, 0, 0);
			serverLevel.sendParticles(JerotesVillageParticleTypes.COGON_SWORD.get(), caster.getX(), caster.getY() + 0.1f, caster.getZ(), 0, 0, 0, 0, 0);
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	//盘根错节$法术
	public static boolean ImprisonVine(LivingEntity caster, LivingEntity target, int spellLevel, int countMin, int countMax) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			int count = countMin;
			if (countMin < countMax) {
				count = Main.randomReach(caster.getRandom(), countMin, countMax);
			}
			if (target != null) {
				if (target != caster && caster instanceof Mob mob) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				for (int n = 0; n < count; ++n) {
					float f2 = (float) n * (float) Math.PI * 2.0f / count + caster.getYRot() * (Mth.PI / 180f);
					ImprisonVine(caster, target, spellLevel,
							caster.getX() + (double) Mth.cos(f2) * caster.distanceTo(target),
							caster.getZ() + (double) Mth.sin(f2) * caster.distanceTo(target),
							caster.getY(), caster.getY() + 1, f2);
				}
				int countParticles = (int) (count * 6 * caster.distanceTo(target));
				for (int i = 0; i < countParticles; i++) {
					for (int i2 = 0; i2 < 3; i2++) {
						double angle = (Math.PI * 2 * i) / countParticles;
						double radius = caster.distanceTo(target);
						double offsetX = Math.cos(angle) * radius;
						double offsetZ = Math.sin(angle) * radius;
						serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_TARGET.get(),
								caster.getX() + offsetX,
								caster.getY() + 0.1f,
								caster.getZ() + offsetZ, 0, 0, 0, 0, 0);
						serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_TARGET.get(),
								caster.getX() + offsetX,
								caster.getY() + 1.75f,
								caster.getZ() + offsetZ, 0, 0, 0, 0, 0);
						serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_TARGET.get(),
								caster.getX() + offsetX / 2,
								caster.getY() + 0.1f,
								caster.getZ() + offsetZ / 2, 0, 0, 0, 0, 0);
						serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_TARGET.get(),
								caster.getX() + offsetX / 2,
								caster.getY() + 1.75f,
								caster.getZ() + offsetZ / 2, 0, 0, 0, 0, 0);
					}
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	static void ImprisonVine(LivingEntity caster, LivingEntity target, int spellLevel, double d, double d2, double d3, double d4, float f) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();

			BlockPos blockPos = BlockPos.containing(d, d4, d2);
			boolean bl = false;
			double d5 = 0.0;
			do {
				BlockState blockState;
				VoxelShape voxelShape;
				BlockPos blockPos2 = blockPos.below();
				BlockState blockState2 = caster.level().getBlockState(blockPos2);
				if (!blockState2.isFaceSturdy(caster.level(), blockPos2, Direction.UP)) continue;
				if (!caster.level().isEmptyBlock(blockPos) && !(voxelShape = (blockState = caster.level().getBlockState(blockPos)).getCollisionShape(caster.level(), blockPos)).isEmpty()) {
					d5 = voxelShape.max(Direction.Axis.Y);
				}
				bl = true;
				break;
			} while ((blockPos = blockPos.below()).getY() >= Mth.floor(d3) - 1);
			if (bl) {
				ImprisonVineEntity imprisonVineEntity = new ImprisonVineEntity(caster.level(), d,
						(double) blockPos.getY() + d5,
						d2, f, caster);
				imprisonVineEntity.setSpellLevelDamage(spellLevel);
				Main.conjureAbout(caster, imprisonVineEntity);
				imprisonVineEntity.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(imprisonVineEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
				imprisonVineEntity.setOwner(caster);
				if (teams != null) {
					serverLevel.getScoreboard().addPlayerToTeam(imprisonVineEntity.getStringUUID(), teams);
				}
				if (target != null && target != caster) {
					imprisonVineEntity.setTarget(target);
				}
//				ParticlesUse.summonParticle(serverLevel, imprisonVineEntity, imprisonVineEntity.getX(), imprisonVineEntity.getY(), imprisonVineEntity.getZ(),
//						0x304a13, 0x65ae26);
				caster.level().addFreshEntity(imprisonVineEntity);
				caster.level().gameEvent(GameEvent.ENTITY_PLACE, new Vec3(d, (double) blockPos.getY() + d5, d2), GameEvent.Context.of(caster));
			}
		}
	}
	//荆棘之刺$法术
	public static boolean ThornSpike(LivingEntity caster, LivingEntity target, int spellLevel, int count, float summonDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			boolean sameEntity = caster == target;
			double baseY = sameEntity ? caster.getY() : Math.min(target.getY(), caster.getY());
			double maxY = sameEntity ? caster.getY() + 1.0 : Math.max(target.getY(), caster.getY()) + 1.0;
			float yRotRad = sameEntity ?
					(caster.getYRot() + 90) * ((float)Math.PI / 180F) :
					(float)Mth.atan2(target.getZ() - caster.getZ(), target.getX() - caster.getX());
			for (int i = 0; i < count; ++i) {
				double offset = summonDistance * (i + 1);
				double x = caster.getX() + (double) Mth.cos(yRotRad) * offset;
				double z = caster.getZ() + (double) Mth.sin(yRotRad) * offset;
				ThornSpike(caster, target, spellLevel, x, z, baseY, maxY, yRotRad);
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	static void ThornSpike(LivingEntity caster, LivingEntity target, int spellLevel, double d, double d2, double d3, double d4, float f) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();

			BlockPos blockPos = BlockPos.containing(d, d4, d2);
			boolean bl = false;
			double d5 = 0.0;
			do {
				BlockState blockState;
				VoxelShape voxelShape;
				BlockPos blockPos2 = blockPos.below();
				BlockState blockState2 = caster.level().getBlockState(blockPos2);
				if (!blockState2.isFaceSturdy(caster.level(), blockPos2, Direction.UP)) continue;
				if (!caster.level().isEmptyBlock(blockPos) && !(voxelShape = (blockState = caster.level().getBlockState(blockPos)).getCollisionShape(caster.level(), blockPos)).isEmpty()) {
					d5 = voxelShape.max(Direction.Axis.Y);
				}
				bl = true;
				break;
			} while ((blockPos = blockPos.below()).getY() >= Mth.floor(d3) - 1);
			if (bl) {
				ThornSpikeEntity thornSpikeEntity = new ThornSpikeEntity(caster.level(), d, (double) blockPos.getY() + d5, d2, f, caster);
				thornSpikeEntity.setSpellLevelDamage(spellLevel);
				Main.conjureAbout(caster, thornSpikeEntity);
                thornSpikeEntity.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(thornSpikeEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
                thornSpikeEntity.setOwner(caster);
                if (teams != null) {
                    serverLevel.getScoreboard().addPlayerToTeam(thornSpikeEntity.getStringUUID(), teams);
                }
				if (target != null && target != caster) {
					thornSpikeEntity.setTarget(target);
				}
//				ParticlesUse.summonParticle(serverLevel, thornSpikeEntity, thornSpikeEntity.getX(), thornSpikeEntity.getY(), thornSpikeEntity.getZ(),
//						0x6d5119, 0x9a9226);
                caster.level().addFreshEntity(thornSpikeEntity);
				caster.level().gameEvent(GameEvent.ENTITY_PLACE, new Vec3(d, (double) blockPos.getY() + d5, d2), GameEvent.Context.of(caster));
			}
		}
	}
	//八面威风$法术
	public static boolean VineheartBomb(LivingEntity caster, LivingEntity target, int spellLevel) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			int count = 9;
			int nulls = Main.randomReach(caster.getRandom(), 0, 8);
			if (target != null) {
				if (target != caster && caster instanceof Mob mob) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				for (int n = 0; n < count; ++n) {
					if (nulls == n) continue;
					float f2 = (float) n * (float) Math.PI * 2.0f / count + caster.getYRot() * (Mth.PI / 180f);
					VineheartBomb(caster, target,
							target.getX() + (double) Mth.cos(f2) * (Main.mobWidth(target) + 1.5f),
							target.getZ() + (double) Mth.sin(f2) * (Main.mobWidth(target) + 1.5f),
							target.getY(), target.getY() + 1, f2, spellLevel);
				}
				int countParticles = (int) (count * 6 * caster.distanceTo(target));
				for (int i = 0; i < countParticles; i++) {
					for (int i2 = 0; i2 < 3; i2++) {
						double angle = (Math.PI * 2 * i) / countParticles;
						double radius = Main.mobWidth(target) + 1.5f;
						double offsetX = Math.cos(angle) * 1.5f * radius;
						double offsetZ = Math.sin(angle) * 1.5f * radius;
						serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_TARGET.get(),
								target.getX() + offsetX,
								target.getY() + 0.1f,
								target.getZ() + offsetZ, 0, 0, 0, 0, 0);
						serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_TARGET.get(),
								target.getX() + offsetX,
								target.getY() + 1.75f,
								target.getZ() + offsetZ, 0, 0, 0, 0, 0);
						serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_TARGET.get(),
								target.getX() + offsetX / 2,
								target.getY() + 0.1f,
								target.getZ() + offsetZ / 2, 0, 0, 0, 0, 0);
						serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_TARGET.get(),
								target.getX() + offsetX / 2,
								target.getY() + 1.75f,
								target.getZ() + offsetZ / 2, 0, 0, 0, 0, 0);
					}
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	static void VineheartBomb(LivingEntity caster, LivingEntity target, double d, double d2, double d3, double d4, float f, int spellLevel) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();

			BlockPos blockPos = BlockPos.containing(d, d4, d2);
			boolean bl = false;
			double d5 = 0.0;
			do {
				BlockState blockState;
				VoxelShape voxelShape;
				BlockPos blockPos2 = blockPos.below();
				BlockState blockState2 = caster.level().getBlockState(blockPos2);
				if (!blockState2.isFaceSturdy(caster.level(), blockPos2, Direction.UP)) continue;
				if (!caster.level().isEmptyBlock(blockPos) && !(voxelShape = (blockState = caster.level().getBlockState(blockPos)).getCollisionShape(caster.level(), blockPos)).isEmpty()) {
					d5 = voxelShape.max(Direction.Axis.Y);
				}
				bl = true;
				break;
			} while ((blockPos = blockPos.below()).getY() >= Mth.floor(d3) - 1);
			if (bl) {
				VineheartBombEntity vineheartBombEntity = new VineheartBombEntity(caster.level(), d,
						(double) blockPos.getY() + d5,
						d2, f, caster);
				vineheartBombEntity.setSpellLevelDamage(spellLevel);
				vineheartBombEntity.setHealth(Math.max(30, (spellLevel - 2) * 50 + 30));
				Main.conjureAbout(caster, vineheartBombEntity);
				vineheartBombEntity.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(vineheartBombEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
				vineheartBombEntity.setOwner(caster);
				if (teams != null) {
					serverLevel.getScoreboard().addPlayerToTeam(vineheartBombEntity.getStringUUID(), teams);
				}
				if (target != null && target != caster) {
					vineheartBombEntity.setTarget(target);
				}
//				ParticlesUse.summonParticle(serverLevel, vineheartBombEntity, vineheartBombEntity.getX(), vineheartBombEntity.getY(), vineheartBombEntity.getZ(),
//						0x304a13, 0x529a00);
				caster.level().addFreshEntity(vineheartBombEntity);
				caster.level().gameEvent(GameEvent.ENTITY_PLACE, new Vec3(d, (double) blockPos.getY() + d5, d2), GameEvent.Context.of(caster));
			}
		}
	}
	//苦寒祭坛$法术
	public static boolean BitterColdAltar(LivingEntity caster, int spellLevel, int countMin, int countMax, int summonDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			int count = countMin;
			if (countMin < countMax) {
				count = caster.getRandom().nextInt(countMin, countMax);
			}
			for (int i = 0; i < count; ++i) {
				BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(caster, summonDistance);
				if (caster instanceof Player) {
					Vec3 startPos = caster.getEyePosition(1.0f);
					Vec3 viewVector = caster.getViewVector(1.0f);
					Vec3 endPos = startPos.add(viewVector.scale(summonDistance));

					BlockHitResult hitResult = serverLevel.clip(new ClipContext(
							startPos, endPos,
							ClipContext.Block.OUTLINE,
							ClipContext.Fluid.NONE,
							caster
					));
					Vec3 targetPos = Main.adjustPositionForSolidHit(hitResult, startPos, viewVector, summonDistance);
					summonPos = Main.findSafePosition(serverLevel, targetPos);
				}
				BitterColdAltarEntity bitterColdAltar = JerotesVillageEntityType.BITTER_COLD_ALTAR.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
				if (bitterColdAltar != null) {
					bitterColdAltar.setOwner(caster);
					bitterColdAltar.setSpellLevelDamage(spellLevel);
					Main.conjureAbout(caster, bitterColdAltar);
					if (teams != null) {
						serverLevel.getScoreboard().addPlayerToTeam(bitterColdAltar.getStringUUID(), teams);
					}
					if (caster instanceof Mob mob && mob.getTarget() != null) {
						bitterColdAltar.setTarget(mob.getTarget());
					}
//					ParticlesUse.summonParticle(serverLevel, bitterColdAltar, bitterColdAltar.getX(), bitterColdAltar.getY(), bitterColdAltar.getZ(),
//							0x006bcd, 0x8bc6fb);
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	//召唤铁齿$法术
	public static boolean ConjureIronTeeth(LivingEntity caster, Entity target, int countMin, int countMax, int summonDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			int count = countMin;
			if (countMin < countMax) {
				count = caster.getRandom().nextInt(countMin, countMax);
			}
			for (int i = 0; i < count; ++i) {
				BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(caster, summonDistance);
				Vec3 targetPos;
				if (target != null) {
					targetPos = new Vec3(target.getX() + 0.5D, target.getY(), target.getZ() + 0.5D);
					summonPos = Main.findSafePosition(serverLevel, targetPos);
				}
				if (caster instanceof Player) {
					Vec3 startPos = caster.getEyePosition(1.0f);
					Vec3 viewVector = caster.getViewVector(1.0f);
					Vec3 endPos = startPos.add(viewVector.scale(18));

					BlockHitResult hitResult = serverLevel.clip(new ClipContext(
							startPos, endPos,
							ClipContext.Block.OUTLINE,
							ClipContext.Fluid.NONE,
							caster
					));
					targetPos = Main.adjustPositionForSolidHit(hitResult, startPos, viewVector, 18);
					summonPos = Main.findSafePosition(serverLevel, targetPos);
				}
				IronTeethEntity ironTeethEntity = JerotesVillageEntityType.IRON_TEETH.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
				if (ironTeethEntity != null) {
					ironTeethEntity.setTame(true);
					ironTeethEntity.setOwnerUUID(caster.getUUID());
					if (caster instanceof Player player) {
						ironTeethEntity.setChangeType(2, player);
					}
					else {
						ironTeethEntity.setChangeType(2);
						if (caster instanceof Mob mob && mob.getTarget() != null) {
							ironTeethEntity.setTarget(mob);
						}
					}
					if (caster instanceof Mob mob && mob.getTarget() != null) {
						ironTeethEntity.setTarget(mob.getTarget());
					}
					ParticlesUse.summonParticle(serverLevel, ironTeethEntity, ironTeethEntity.getX(), ironTeethEntity.getY(), ironTeethEntity.getZ(),
							0x727272, 0xdadada);
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	//召唤飞行火团$法术
	public static boolean ConjureFlyingFireball(LivingEntity caster, LivingEntity target, float spellLevelExplode, int countMin, int countMax, int summonDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			int count = countMin;
			if (countMin < countMax) {
				count = caster.getRandom().nextInt(countMin, countMax);
			}
			for (int i = 0; i < count; ++i) {
				BlockPos summonPos = caster.getOnPos().above().above();
				FlyingFireballEntity flyingFireballEntity = JerotesVillageEntityType.FLYING_FIREBALL.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
				if (flyingFireballEntity != null) {
					Main.conjureAbout(caster, flyingFireballEntity);
					flyingFireballEntity.setTame(true);
					flyingFireballEntity.setOwnerUUID(caster.getUUID());
					SpellType.startConcentration(caster, flyingFireballEntity);
					if (caster instanceof Player player) {
						flyingFireballEntity.setChangeType(2, player);
					}
					else {
						flyingFireballEntity.setChangeType(2);
					}
					if (target != null && target != caster) {
						flyingFireballEntity.setTarget(target);
						flyingFireballEntity.setHasExplode(true);
						flyingFireballEntity.setExplodeLevel(spellLevelExplode);
					}
					if (teams != null) {
						serverLevel.getScoreboard().addPlayerToTeam(flyingFireballEntity.getStringUUID(), teams);
					}
					if (caster instanceof Mob mob && mob.getTarget() != null) {
						flyingFireballEntity.setTarget(mob.getTarget());
					}
					if (target != null && target != caster) {
						flyingFireballEntity.setTarget(target);
					}
					ParticlesUse.summonParticle(serverLevel, flyingFireballEntity, flyingFireballEntity.getX(), flyingFireballEntity.getY(), flyingFireballEntity.getZ(),
							0xa32800, 0xeac900);
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	//荒野驱使
	public static boolean WildernessDriven(LivingEntity caster, int countMin, int countMax, int summonDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			int count = countMin;
			if (countMin < countMax) {
				count = caster.getRandom().nextInt(countMin, countMax);
			}
			for (int i = 0; i < count; ++i) {
				BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(caster, summonDistance);
				WildernessWolfEntity wildernessWolf = JerotesVillageEntityType.WILDERNESS_WOLF.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
				if (wildernessWolf != null) {
					Main.conjureAbout(caster, wildernessWolf);
					wildernessWolf.setTame(true);
					wildernessWolf.setOwnerUUID(caster.getUUID());
					wildernessWolf.setOrderedToSit(false);
					wildernessWolf.setInSittingPose(false);
					if (EntityFactionFind.isRaider(caster)) {
						wildernessWolf.setIllagerFaction(true);
					}
					if (teams != null) {
						serverLevel.getScoreboard().addPlayerToTeam(wildernessWolf.getStringUUID(), teams);
					}
					if (caster instanceof Mob mob && mob.getTarget() != null) {
						wildernessWolf.setTarget(mob.getTarget());
					}
					ParticlesUse.summonParticle(serverLevel, wildernessWolf, wildernessWolf.getX(), wildernessWolf.getY(), wildernessWolf.getZ(),
							0x423a30, 0x625d52);
			}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.WILDERNESS_DRIVEN_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//赎罪抵抗
	public static boolean AtonementResistance(LivingEntity caster) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			BlockPos summonPos = caster.getOnPos().above();
			DefectorEntity defector = JerotesVillageEntityType.DEFECTOR.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
			if (defector != null) {
				Main.conjureAbout(caster, defector, false);
				if (teams != null) {
					serverLevel.getScoreboard().addPlayerToTeam(defector.getStringUUID(), teams);
				}
				if (caster instanceof Mob mob && mob.getTarget() != null) {
					defector.setTarget(mob.getTarget());
				}
				ParticlesUse.summonParticle(serverLevel, defector, defector.getX(), defector.getY(), defector.getZ(),
						0x525858, 0x909696);
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.ATONEMENT_RESISTANCE_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//召唤莫厄立方
	public static boolean ConjureMerorCube(LivingEntity caster, int countMin, int countMax, int summonDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			int count = countMin;
			if (countMin < countMax) {
				count = Main.randomReach(caster.getRandom(), countMin, countMax);
			}
			for (int i = 0; i < count; ++i) {
				BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(caster, summonDistance);
				MerorCubeEntity merorCube = JerotesVillageEntityType.MEROR_CUBE.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);

				if (merorCube != null) {
					Main.conjureAbout(caster, merorCube, false);
					String stringCaster = ChatFormatting.stripFormatting(caster.getName().getString());
					if ("Redstone Monstrosity".equals(stringCaster)) {
						merorCube.setCustomName(Component.literal("Redstone Cube"));
					}
					if ("红石巨兽".equals(stringCaster)) {
						merorCube.setCustomName(Component.literal("红石怪"));
					}
					merorCube.setTame(true);
					merorCube.setOwnerUUID(caster.getUUID());
					merorCube.setOwnerId(caster.getId());
					merorCube.setOrderedToSit(false);
					if (teams != null) {
						serverLevel.getScoreboard().addPlayerToTeam(merorCube.getStringUUID(), teams);
					}
					if (caster instanceof Mob mob && mob.getTarget() != null) {
						merorCube.setTarget(mob.getTarget());
					}
					if ("Redstone Monstrosity".equals(stringCaster)) {
						ParticlesUse.summonParticle(serverLevel, merorCube, merorCube.getX(), merorCube.getY(), merorCube.getZ(),
								0x710e06, 0xb6210a);
					}
					else if ("红石巨兽".equals(stringCaster)) {
						ParticlesUse.summonParticle(serverLevel, merorCube, merorCube.getX(), merorCube.getY(), merorCube.getZ(),
								0x710e06, 0xb6210a);
					}
					else {
						ParticlesUse.summonParticle(serverLevel, merorCube, merorCube.getX(), merorCube.getY(), merorCube.getZ(),
								0xdd717b, 0xe592a3);
					}
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.CONJURE_MEROR_CUBE_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//撒豆成兵$法术
	public static boolean ConjureCoilvineBotanizes(LivingEntity caster, int countMin, int countMax, int summonDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			for (int i = 0; i < 70; i++) {
				double angle = (Math.PI * 2 * i) / 70;
				double offsetXAdd = Math.cos(angle);
				double offsetZAdd = Math.sin(angle);
				// 生成烟雾粒子
				serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, caster.getX(), caster.getEyeY(), caster.getZ(), 0, offsetXAdd * 1.4f, 0.08, offsetZAdd * 1.4f, 1.0);
				serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, caster.getX(), caster.getEyeY(), caster.getZ(), 0, offsetXAdd * 1.0f, 0.06, offsetZAdd * 1.0f, 1.0);
				serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, caster.getX(), caster.getEyeY(), caster.getZ(), 0, offsetXAdd * 0.7f, 0.04, offsetZAdd * 0.7f, 1.0);
			}
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			int count = countMin;
			if (countMin < countMax) {
				count = Main.randomReach(caster.getRandom(), countMin, countMax);
			}
			for (int i = 0; i < count; ++i) {
				BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(caster, summonDistance);
				CoilvineBotanizeEntity coilvineBotanize = JerotesVillageEntityType.COILVINE_BOTANIZE.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
				if (coilvineBotanize != null) {
					Main.conjureAbout(caster, coilvineBotanize);
					coilvineBotanize.setMaxLifeTick(20 * 30);
					coilvineBotanize.setNotPersistent(true);
					coilvineBotanize.setLifeTick(0);
					coilvineBotanize.setTame(true);
					coilvineBotanize.setOwnerUUID(caster.getUUID());
					coilvineBotanize.setOrderedToSit(false);
					if (teams != null) {
						serverLevel.getScoreboard().addPlayerToTeam(coilvineBotanize.getStringUUID(), teams);
					}
					if (caster instanceof Mob mob && mob.getTarget() != null) {
						coilvineBotanize.setTarget(mob.getTarget());
					}
					ParticlesUse.summonParticle(serverLevel, coilvineBotanize, coilvineBotanize.getX(), coilvineBotanize.getY(), coilvineBotanize.getZ(),
							0x3c6a19, 0xe4c369);
					serverLevel.sendParticles(ParticleTypes.CHERRY_LEAVES, coilvineBotanize.getRandomX(0.5), coilvineBotanize.getRandomY(), coilvineBotanize.getRandomZ(0.5), 20, 0, 0, 0, 0);
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	//召唤波涛人$法术
	public static boolean ConjureBulges(LivingEntity caster, int countMin, int countMax, int summonDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			int count = countMin;
			if (countMin < countMax) {
				count = Main.randomReach(caster.getRandom(), countMin, countMax);
			}
			for (int i = 0; i < count; ++i) {
				BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(caster, summonDistance);
				BulgeEntity bulgeEntity = JerotesVillageEntityType.BULGE.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
				if (bulgeEntity != null) {
					Main.conjureAbout(caster, bulgeEntity);
					bulgeEntity.setMaxLifeTick(20 * 30);
					bulgeEntity.setNotPersistent(true);
					bulgeEntity.setLifeTick(0);
					bulgeEntity.setTame(true);
					bulgeEntity.setOwnerUUID(caster.getUUID());
					bulgeEntity.setOrderedToSit(false);
					if (teams != null) {
						serverLevel.getScoreboard().addPlayerToTeam(bulgeEntity.getStringUUID(), teams);
					}
					if (caster instanceof Mob mob && mob.getTarget() != null) {
						bulgeEntity.setTarget(mob.getTarget());
					}
					ParticlesUse.summonParticle(serverLevel, bulgeEntity, bulgeEntity.getX(), bulgeEntity.getY(), bulgeEntity.getZ(),
							0x0062d0, 0x81d1ff);
					for (int n = 0; n < 20; ++n) {
						serverLevel.sendParticles(JerotesVillageParticleTypes.GEMSTONE_BUBBLE.get(), bulgeEntity.getRandomX(0.5), bulgeEntity.getRandomY(), bulgeEntity.getRandomZ(0.5), 0, 0, 0, 0, 0.125f);
					}
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	//召唤骇浪$法术
	public static boolean ConjureSeawave(LivingEntity caster, int countMin, int countMax, int summonDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			int count = countMin;
			if (countMin < countMax) {
				count = caster.getRandom().nextInt(countMin, countMax);
			}
			for (int i = 0; i < count; ++i) {
				BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(caster, summonDistance);
				if (caster instanceof Player) {
					Vec3 startPos = caster.getEyePosition(1.0f);
					Vec3 viewVector = caster.getViewVector(1.0f);
					Vec3 endPos = startPos.add(viewVector.scale(summonDistance));

					BlockHitResult hitResult = serverLevel.clip(new ClipContext(
							startPos, endPos,
							ClipContext.Block.OUTLINE,
							ClipContext.Fluid.NONE,
							caster
					));
					Vec3 targetPos = Main.adjustPositionForSolidHit(hitResult, startPos, viewVector, summonDistance);
					summonPos = Main.findSafePosition(serverLevel, targetPos);
				}
				SeawaveEntity seawaveEntity = JerotesVillageEntityType.SEAWAVE.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
				if (seawaveEntity != null) {
					Main.conjureAbout(caster, seawaveEntity);
					seawaveEntity.setMaxLifeTick(20 * 60 * 10);
					seawaveEntity.setNotPersistent(true);
					seawaveEntity.setLifeTick(0);
					seawaveEntity.setTame(true);
					seawaveEntity.setOwnerUUID(caster.getUUID());
					SpellType.startConcentration(caster, seawaveEntity);
					if (caster instanceof Player player) {
						seawaveEntity.setChangeType(2, player);
					}
					else {
						seawaveEntity.setChangeType(2);
						if (caster instanceof Mob mob && mob.getTarget() != null) {
							seawaveEntity.setTarget(mob);
						}
					}
					if (caster instanceof Mob mob && mob.getTarget() != null) {
						seawaveEntity.setTarget(mob.getTarget());
					}
					if (teams != null) {
						serverLevel.getScoreboard().addPlayerToTeam(seawaveEntity.getStringUUID(), teams);
					}
					ParticlesUse.summonParticle(serverLevel, seawaveEntity, seawaveEntity.getX(), seawaveEntity.getY(), seawaveEntity.getZ(),
							0x033177, 0x4ac8ff);
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	//召唤旱火烈焰人$法术
	public static boolean ConjureDroughtBlazes(LivingEntity caster, int countMin, int countMax, int summonDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			int count = countMin;
			if (countMin < countMax) {
				count = Main.randomReach(caster.getRandom(), countMin, countMax);
			}
			for (int i = 0; i < count; ++i) {
				BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(caster, summonDistance);
				DroughtBlazeEntity droughtBlazeEntity = JerotesVillageEntityType.DROUGHT_BLAZE.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
				if (droughtBlazeEntity != null) {
					Main.conjureAbout(caster, droughtBlazeEntity);
					droughtBlazeEntity.setMaxLifeTick(20 * 30);
					droughtBlazeEntity.setNotPersistent(true);
					droughtBlazeEntity.setLifeTick(0);
					droughtBlazeEntity.setTame(true);
					droughtBlazeEntity.setOwnerUUID(caster.getUUID());
					droughtBlazeEntity.setOrderedToSit(false);
					if (teams != null) {
						serverLevel.getScoreboard().addPlayerToTeam(droughtBlazeEntity.getStringUUID(), teams);
					}
					if (caster instanceof Mob mob && mob.getTarget() != null) {
						droughtBlazeEntity.setTarget(mob.getTarget());
					}
					ParticlesUse.summonParticle(serverLevel, droughtBlazeEntity, droughtBlazeEntity.getX(), droughtBlazeEntity.getY(), droughtBlazeEntity.getZ(),
							0x850c00, 0xffa117);
					for (int n = 0; n < 20; ++n) {
						serverLevel.sendParticles(ParticleTypes.LAVA, droughtBlazeEntity.getRandomX(0.5), droughtBlazeEntity.getRandomY(), droughtBlazeEntity.getRandomZ(0.5), 0, 0, 0, 0, 0.125f);
					}
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	//召唤旱火野火$法术
	public static boolean ConjureDroughtWildfire(LivingEntity caster, int countMin, int countMax, int summonDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			int count = countMin;
			if (countMin < countMax) {
				count = caster.getRandom().nextInt(countMin, countMax);
			}
			for (int i = 0; i < count; ++i) {
				BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(caster, summonDistance);
				if (caster instanceof Player) {
					Vec3 startPos = caster.getEyePosition(1.0f);
					Vec3 viewVector = caster.getViewVector(1.0f);
					Vec3 endPos = startPos.add(viewVector.scale(summonDistance));

					BlockHitResult hitResult = serverLevel.clip(new ClipContext(
							startPos, endPos,
							ClipContext.Block.OUTLINE,
							ClipContext.Fluid.NONE,
							caster
					));
					Vec3 targetPos = Main.adjustPositionForSolidHit(hitResult, startPos, viewVector, summonDistance);
					summonPos = Main.findSafePosition(serverLevel, targetPos);
				}
				DroughtWildfireEntity droughtWildfireEntity = JerotesVillageEntityType.DROUGHT_WILDFIRE.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
				if (droughtWildfireEntity != null) {
					Main.conjureAbout(caster, droughtWildfireEntity);
					droughtWildfireEntity.setMaxLifeTick(20 * 60 * 10);
					droughtWildfireEntity.setNotPersistent(true);
					droughtWildfireEntity.setLifeTick(0);
					droughtWildfireEntity.setTame(true);
					droughtWildfireEntity.setOwnerUUID(caster.getUUID());
					SpellType.startConcentration(caster, droughtWildfireEntity);
					if (caster instanceof Player player) {
						droughtWildfireEntity.setChangeType(2, player);
					}
					else {
						droughtWildfireEntity.setChangeType(2);
						if (caster instanceof Mob mob && mob.getTarget() != null) {
							droughtWildfireEntity.setTarget(mob);
						}
					}
					if (caster instanceof Mob mob && mob.getTarget() != null) {
						droughtWildfireEntity.setTarget(mob.getTarget());
					}
					if (teams != null) {
						serverLevel.getScoreboard().addPlayerToTeam(droughtWildfireEntity.getStringUUID(), teams);
					}
					ParticlesUse.summonParticle(serverLevel, droughtWildfireEntity, droughtWildfireEntity.getX(), droughtWildfireEntity.getY(), droughtWildfireEntity.getZ(),
							0x681908, 0xd24200);
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	//呼唤暗乱$法术
	public static boolean CallTheDarisorder(LivingEntity caster, int countMin, int countMax, int summonDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			int count = countMin;
			if (countMin < countMax) {
				count = caster.getRandom().nextInt(countMin, countMax);
			}
			for (int i = 0; i < count; ++i) {
				BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(caster, summonDistance);
				if (caster instanceof Player) {
					Vec3 startPos = caster.getEyePosition(1.0f);
					Vec3 viewVector = caster.getViewVector(1.0f);
					Vec3 endPos = startPos.add(viewVector.scale(summonDistance));

					BlockHitResult hitResult = serverLevel.clip(new ClipContext(
							startPos, endPos,
							ClipContext.Block.OUTLINE,
							ClipContext.Fluid.NONE,
							caster
					));
					Vec3 targetPos = Main.adjustPositionForSolidHit(hitResult, startPos, viewVector, summonDistance);
					summonPos = Main.findSafePosition(serverLevel, targetPos);
				}
				DarisorderEntity darisorderEntity = JerotesVillageEntityType.DARISORDER.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
				if (darisorderEntity != null) {
					Main.conjureAbout(caster, darisorderEntity);
					darisorderEntity.setMaxLifeTick(20 * 30);
					darisorderEntity.setNotPersistent(true);
					darisorderEntity.setLifeTick(0);
					darisorderEntity.setTame(true);
					darisorderEntity.setOwnerUUID(caster.getUUID());
					SpellType.startConcentration(caster, darisorderEntity);
					if (caster instanceof Player player) {
						darisorderEntity.setChangeType(2, player);
					}
					else {
						darisorderEntity.setChangeType(2);
						if (caster instanceof Mob mob && mob.getTarget() != null) {
							darisorderEntity.setTarget(mob);
						}
					}
					if (caster instanceof Mob mob && mob.getTarget() != null) {
						darisorderEntity.setTarget(mob.getTarget());
					}
					if (teams != null) {
						serverLevel.getScoreboard().addPlayerToTeam(darisorderEntity.getStringUUID(), teams);
					}
					ParticlesUse.summonParticle(serverLevel, darisorderEntity, darisorderEntity.getX(), darisorderEntity.getY(), darisorderEntity.getZ(),
							0x11100f, 0x252423);
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	//天女散花$法术
	public static boolean VinePodBomb(LivingEntity caster, int countMin, int countMax, int spellLevelDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			int count = countMin;
			if (countMin < countMax) {
				count = Main.randomReach(caster.getRandom(), countMin, countMax);
			}
			List<VinePodBombEntity> list = serverLevel.getEntitiesOfClass(VinePodBombEntity.class, caster.getBoundingBox().inflate(spellLevelDistance, spellLevelDistance, spellLevelDistance));
			for (VinePodBombEntity vinePodBombEntity : list) {
				if (vinePodBombEntity == null) continue;
				if (vinePodBombEntity == caster) continue;
				if (!AttackFind.SameFactionAvoidDamage(caster, vinePodBombEntity, false)) continue;
				if ((caster.distanceTo(vinePodBombEntity)) > spellLevelDistance) continue;
				vinePodBombEntity.setDeltaMovement(vinePodBombEntity.getDeltaMovement().add(
						0,
						1.25F,
						0
				));
				vinePodBombEntity.setBomb(true);
			}
			for (int i = 0; i < count; ++i) {
				BlockPos summonPos = caster.getOnPos().above().above();
				VinePodBombEntity vinePodBombEntity = JerotesVillageEntityType.VINE_POD_BOMB.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
				if (vinePodBombEntity != null) {
					Main.conjureAbout(caster, vinePodBombEntity);
					vinePodBombEntity.setMaxLifeTick(20 * 30);
					vinePodBombEntity.setNotPersistent(true);
					vinePodBombEntity.setLifeTick(0);
					vinePodBombEntity.setTame(true);
					vinePodBombEntity.setOwnerUUID(caster.getUUID());
					vinePodBombEntity.setOrderedToSit(false);
					vinePodBombEntity.moveTo(caster.getEyePosition().add(0, 2f, 0));
					vinePodBombEntity.setDeltaMovement(
							(vinePodBombEntity.getRandom().nextFloat() - 0.5f) * 1.2f,
							1.5F,
							(vinePodBombEntity.getRandom().nextFloat() - 0.5f) * 1.2f
					);
					vinePodBombEntity.setBomb(true);
					if (teams != null) {
						serverLevel.getScoreboard().addPlayerToTeam(vinePodBombEntity.getStringUUID(), teams);
					}
					if (caster instanceof Mob mob && mob.getTarget() != null) {
						vinePodBombEntity.setTarget(mob.getTarget());
					}
//					ParticlesUse.summonParticle(serverLevel, vinePodBombEntity, vinePodBombEntity.getX(), vinePodBombEntity.getY(), vinePodBombEntity.getZ(),
//							0x304a13, 0x529a00);
					serverLevel.sendParticles(ParticleTypes.CHERRY_LEAVES, vinePodBombEntity.getRandomX(0.5), vinePodBombEntity.getRandomY(), vinePodBombEntity.getRandomZ(0.5), 20, 0, 0, 0, 0);
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	//沉降号令
	public static boolean SedimentOrder(LivingEntity caster, LivingEntity target, int countMin, int countMax, int summonDistance, int spellLevelDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			if (target != null) {
				Vec3 vec3 = caster.getEyePosition();
				Vec3 vec32 = target.getEyePosition().subtract(vec3);
				Vec3 vec33 = vec32.normalize();
				for (int i = 1; i < Mth.floor(vec32.length()) * 6 + 1; ++i) {
					Vec3 vec34 = vec3.add(vec33.scale(i / 6f));
					serverLevel.sendParticles(ParticleTypes.PORTAL, vec34.x, vec34.y, vec34.z, 1, 0.0, 0.0, 0.0, 0.0);
				}
			}
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			int count = countMin;
			if (countMin < countMax) {
				count = Main.randomReach(caster.getRandom(), countMin, countMax);
			}
			for (int i = 0; i < count; ++i) {
				BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(caster, summonDistance);
				SedimentGuardEntity sedimentGuard = JerotesVillageEntityType.SEDIMENT_GUARD.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
				if (sedimentGuard != null) {
					Main.conjureAbout(caster, sedimentGuard, false);
					if (teams != null) {
						serverLevel.getScoreboard().addPlayerToTeam(sedimentGuard.getStringUUID(), teams);
					}
					if (target != null && target != caster) {
						sedimentGuard.setTarget(target);
					}
					ParticlesUse.summonParticle(serverLevel, sedimentGuard, sedimentGuard.getX(), sedimentGuard.getY(), sedimentGuard.getZ(),
							0x0e273f, 0x436585);
					serverLevel.sendParticles(ParticleTypes.PORTAL, sedimentGuard.getRandomX(0.5), sedimentGuard.getRandomY(), sedimentGuard.getRandomZ(0.5), 20, 0, 0, 0, 0);
				}
			}
			List<ResurrecterEntity> list = serverLevel.getEntitiesOfClass(ResurrecterEntity.class, caster.getBoundingBox().inflate(spellLevelDistance, spellLevelDistance, spellLevelDistance));
			for (ResurrecterEntity resurrecterControl : list) {
				if (resurrecterControl == null) continue;
				if (resurrecterControl == caster) continue;
				if (resurrecterControl == target) continue;
				if (resurrecterControl instanceof SedimentLordEntity) continue;
				if (!AttackFind.SameFactionAvoidDamage(caster, resurrecterControl, false)) continue;
				if ((caster.distanceTo(resurrecterControl)) > spellLevelDistance * 2) continue;
				if ((resurrecterControl.getTeam() != null || caster.getTeam() != null) && !caster.isAlliedTo(resurrecterControl)) continue;
				if (target != null && target != caster) {
					resurrecterControl.setTarget(target);
				}
				resurrecterControl.angryTick = 3600;
			}
			if (target != null && target != caster) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.TARGET.get(), target.getX(), target.getY() + 0.1, target.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.SEDIMENT_ORDER_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//奇灵召唤
	public static boolean WonderlinCall(LivingEntity caster, int countMin, int countMax, int summonDistance, int spellLevelDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			int count = countMin;
			if (countMin < countMax) {
				count = Main.randomReach(caster.getRandom(), countMin, countMax);
			}
			for (int i = 0; i < count; ++i) {
				BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(caster, summonDistance);
				WonderlinWarriorEntity wonderlinWarrior = JerotesVillageEntityType.WONDERLIN_WARRIOR.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
				if (wonderlinWarrior != null) {
					Main.conjureAbout(caster, wonderlinWarrior, false);
					if (teams != null) {
						serverLevel.getScoreboard().addPlayerToTeam(wonderlinWarrior.getStringUUID(), teams);
					}
					if (caster instanceof Mob mob && mob.getTarget() != null) {
						wonderlinWarrior.setTarget(mob.getTarget());
					}
					ParticlesUse.summonParticle(serverLevel, wonderlinWarrior, wonderlinWarrior.getX(), wonderlinWarrior.getY(), wonderlinWarrior.getZ(),
							0x905243, 0xc28b7a);
					serverLevel.sendParticles(ParticleTypes.CHERRY_LEAVES, wonderlinWarrior.getRandomX(0.5), wonderlinWarrior.getRandomY(), wonderlinWarrior.getRandomZ(0.5), 20, 0, 0, 0, 0);
				}
			}
			List<BaseWonderlinEntity> list = serverLevel.getEntitiesOfClass(BaseWonderlinEntity.class, caster.getBoundingBox().inflate(spellLevelDistance, spellLevelDistance, spellLevelDistance));
			for (BaseWonderlinEntity wonderlin : list) {
				if (wonderlin == null) continue;
				if (wonderlin == caster) continue;
				if (wonderlin instanceof TyrantOfTheMountainRealmEntity) continue;
				if ((caster.distanceTo(wonderlin)) > spellLevelDistance * 2) continue;
				if ((wonderlin.getTeam() != null || caster.getTeam() != null) && !caster.isAlliedTo(wonderlin)) continue;
				wonderlin.getNavigation().moveTo(caster, 1.2f);
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.WONDERLIN_CALL_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//苦寒唤风者
	public static boolean BitterColdWindcaller(LivingEntity caster, LivingEntity target, int countMin, int countMax, int summonDistance, int spellLevelDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			int count = countMin;
			if (countMin < countMax) {
				count = Main.randomReach(caster.getRandom(), countMin, countMax);
			}
			for (int i = 0; i < count; ++i) {
				BlockPos summonPos = Main.findSpawnPositionNearFill(caster, summonDistance);
				BitterColdWindEntity bitterColdWind = JerotesVillageEntityType.BITTER_COLD_WIND.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
				if (bitterColdWind != null) {
					Main.conjureAbout(caster, bitterColdWind, false);
					if (teams != null) {
						serverLevel.getScoreboard().addPlayerToTeam(bitterColdWind.getStringUUID(), teams);
					}
					if (target != null && target != caster) {
						bitterColdWind.setTarget(target);
					}
					ParticlesUse.summonParticle(serverLevel, bitterColdWind, bitterColdWind.getX(), bitterColdWind.getY(), bitterColdWind.getZ(),
							0x68d5ff, 0xffffff);
					serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, bitterColdWind.getRandomX(0.5), bitterColdWind.getRandomY(), bitterColdWind.getRandomZ(0.5), 20, 0, 0, 0, 0);
				}
			}
			List<BitterColdWindEntity> list = serverLevel.getEntitiesOfClass(BitterColdWindEntity.class, caster.getBoundingBox().inflate(spellLevelDistance, spellLevelDistance, spellLevelDistance));
			for (BitterColdWindEntity bitterColdWind : list) {
				if (bitterColdWind == null) continue;
				if (bitterColdWind == caster) continue;
				if (bitterColdWind == target) continue;
				if ((caster.distanceTo(bitterColdWind)) > spellLevelDistance * 2) continue;
				if ((bitterColdWind.getTeam() != null || caster.getTeam() != null) && !caster.isAlliedTo(bitterColdWind)) continue;
				if (target != null && target != caster) {
					bitterColdWind.setTarget(target);
				}
			}
			if (target != null && target != caster) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.TARGET.get(), target.getX(), target.getY() + 0.1, target.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.BITTER_COLD_WINDCALLER_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//鬼婆集会
	public static boolean HagCoven(LivingEntity caster, int summonDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(caster, summonDistance);
			CovenHagEntity hag1 = JerotesVillageEntityType.COVEN_HAG_ONE.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
			summonPos = Main.findSpawnPositionNearFillOnBlock(caster, summonDistance);
			CovenHagEntity hag2 = JerotesVillageEntityType.COVEN_HAG_TWO.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
			if (hag1 != null) {
				Main.conjureAbout(caster, hag1, false);
				if (teams != null) {
					serverLevel.getScoreboard().addPlayerToTeam(hag1.getStringUUID(), teams);
				}
				if (caster instanceof Mob mob && mob.getTarget() != null) {
					hag1.setTarget(mob.getTarget());
				}
				ParticlesUse.summonParticle(serverLevel, hag1, hag1.getX(), hag1.getY(), hag1.getZ(),
						0x374200, 0x7b853d);
				serverLevel.sendParticles(ParticleTypes.WITCH, hag1.getRandomX(0.5), hag1.getRandomY(), hag1.getRandomZ(0.5), 20, 0, 0, 0, 0);
			}
			if (hag2 != null) {
				Main.conjureAbout(caster, hag2, false);
				if (teams != null) {
					serverLevel.getScoreboard().addPlayerToTeam(hag2.getStringUUID(), teams);
				}
				if (caster instanceof Mob mob && mob.getTarget() != null) {
					hag2.setTarget(mob.getTarget());
				}
				ParticlesUse.summonParticle(serverLevel, hag2, hag2.getX(), hag2.getY(), hag2.getZ(),
						0x374200, 0x7b853d);
				serverLevel.sendParticles(ParticleTypes.WITCH, hag2.getRandomX(0.5), hag2.getRandomY(), hag2.getRandomZ(0.5), 20, 0, 0, 0, 0);
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.HAG_COVENS_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//紫沙幻影$法术
	public static boolean PurpleSandPhantom(LivingEntity caster, Entity target, int countMin, int countMax, int summonDistance, int spellLevelDamage) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			int count = countMin;
			if (countMin < countMax) {
				count = Main.randomReach(caster.getRandom(), countMin, countMax);
			}
			for (int i = 0; i < count; ++i) {
				BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(caster, summonDistance);
				PurpleSandPhantomEntity purpleSandPhantom = JerotesVillageEntityType.PURPLE_SAND_PHANTOM.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
				if (purpleSandPhantom != null) {
					Main.conjureAbout(caster, purpleSandPhantom);
					purpleSandPhantom.setOwner(caster);
					purpleSandPhantom.setSpellLevelDamage(spellLevelDamage);
					if (target instanceof LivingEntity livingEntity) {
						purpleSandPhantom.setTarget(livingEntity);
					}
					purpleSandPhantom.lookAt(target, 360.0f, 360.0f);
					if (target instanceof Mob mob) {
						mob.setTarget(purpleSandPhantom);
					}
					if (teams != null) {
						serverLevel.getScoreboard().addPlayerToTeam(purpleSandPhantom.getStringUUID(), teams);
					}
					ParticlesUse.summonParticle(serverLevel, purpleSandPhantom, purpleSandPhantom.getX(), purpleSandPhantom.getY(), purpleSandPhantom.getZ(),
							0x4e275a, 0xb19cb7);
					serverLevel.sendParticles(ParticleTypes.WITCH, purpleSandPhantom.getRandomX(0.5), purpleSandPhantom.getRandomY(), purpleSandPhantom.getRandomZ(0.5), 20, 0, 0, 0, 0);
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	//二轮横扫
	public static boolean SecondRounderSweep(LivingEntity caster, double spellLevelDamage, int count) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			for (int i = 0; i < count; ++i) {
				SecondRounderGolemSwordEntity secondRounderGolemSword = JerotesVillageEntityType.SECOND_ROUNDER_GOLEM_SWORD.get().spawn(serverLevel, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), MobSpawnType.MOB_SUMMONED);
				if (secondRounderGolemSword != null) {
					Main.conjureAbout(caster, secondRounderGolemSword, false);
					if (teams != null) {
						serverLevel.getScoreboard().addPlayerToTeam(secondRounderGolemSword.getStringUUID(), teams);
					}
					if (caster instanceof Mob mob && mob.getTarget() != null) {
						secondRounderGolemSword.setTarget(mob.getTarget());
					}
					ParticlesUse.summonParticle(serverLevel, secondRounderGolemSword, secondRounderGolemSword.getX(), secondRounderGolemSword.getY(), secondRounderGolemSword.getZ(),
							0xc33d96, 0xf25dbf);
					secondRounderGolemSword.setOwner(caster);
					secondRounderGolemSword.setXRot(caster.getXRot());
					secondRounderGolemSword.setYRot(caster.getYRot());
					secondRounderGolemSword.setYBodyRot(caster.getYRot());
					secondRounderGolemSword.setYHeadRot(caster.getYRot());
					secondRounderGolemSword.attackDamage = spellLevelDamage;
					serverLevel.sendParticles(ParticleTypes.WITCH, secondRounderGolemSword.getRandomX(0.5), secondRounderGolemSword.getRandomY(), secondRounderGolemSword.getRandomZ(0.5), 20, 0, 0, 0, 0);
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.SECOND_ROUNDER_SWEEP_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//I-贤者
	public static boolean I_Sages(LivingEntity caster, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, int spellLevelOtherEffectTime, int spellLevelOtherEffectLevel, int spellLevelDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, caster.getBoundingBox().inflate(spellLevelDistance, spellLevelDistance, spellLevelDistance));
			for (LivingEntity livingEntityEffect : list) {
				if (livingEntityEffect == null) continue;
				if (livingEntityEffect == caster) continue;
				if ((caster.distanceTo(livingEntityEffect)) > spellLevelDistance * 2) continue;
				if (!AttackFind.SameFactionAvoidDamage(caster, livingEntityEffect, false)) continue;
				if (caster instanceof Mob mob && livingEntityEffect == mob.getTarget()) continue;
				if (livingEntityEffect instanceof Mob mob && caster == mob.getTarget()) continue;
				if (!livingEntityEffect.level().isClientSide) {
					caster.getActiveEffects().removeIf(mobEffectInstance -> !mobEffectInstance.getEffect().isBeneficial());
					livingEntityEffect.getActiveEffects().removeIf(mobEffectInstance -> !mobEffectInstance.getEffect().isBeneficial());
					livingEntityEffect.addEffect(new MobEffectInstance(MobEffects.REGENERATION, spellLevelMainEffectTime * 20, spellLevelMainEffectLevel - 1), caster);
					livingEntityEffect.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, spellLevelOtherEffectTime * 20, spellLevelOtherEffectLevel - 1), caster);
					livingEntityEffect.heal(livingEntityEffect.getMaxHealth()/8);
					serverLevel.sendParticles(ParticleTypes.HEART, livingEntityEffect.getRandomX(0.2), livingEntityEffect.getRandomY() + 2, livingEntityEffect.getRandomZ(0.2), 4, 0.0, 0.0, 0.0, 0.0);
				}
			}
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.I_SAGES_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//召唤灵奴
	public static boolean ConjureSpirve(LivingEntity caster, int summonDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(caster, summonDistance);
			SpirveEntity spirve1 = JerotesVillageEntityType.SPIRVE.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
			summonPos = Main.findSpawnPositionNearFillOnBlock(caster, summonDistance);
			SpirveEntity spirve2 = JerotesVillageEntityType.SPIRVE.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
			summonPos = Main.findSpawnPositionNearFillOnBlock(caster, summonDistance);
			SpirveEntity spirve3 = JerotesVillageEntityType.SPIRVE.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
			if (spirve1 != null) {
				Main.conjureAbout(caster, spirve1, false);
				if (teams != null) {
					serverLevel.getScoreboard().addPlayerToTeam(spirve1.getStringUUID(), teams);
				}
				if (caster instanceof Mob mob && mob.getTarget() != null) {
					spirve1.setTarget(mob.getTarget());
				}
				ParticlesUse.summonParticle(serverLevel, spirve1, spirve1.getX(), spirve1.getY(), spirve1.getZ(),
						0x2e2e2e, 0x605c57);
				serverLevel.sendParticles(ParticleTypes.WITCH, spirve1.getRandomX(0.5), spirve1.getRandomY(), spirve1.getRandomZ(0.5), 20, 0, 0, 0, 0);
			}
			if (spirve2 != null) {
				Main.conjureAbout(caster, spirve2, false);
				if (teams != null) {
					serverLevel.getScoreboard().addPlayerToTeam(spirve2.getStringUUID(), teams);
				}
				if (caster instanceof Mob mob && mob.getTarget() != null) {
					spirve2.setTarget(mob.getTarget());
				}
				ParticlesUse.summonParticle(serverLevel, spirve2, spirve2.getX(), spirve2.getY(), spirve2.getZ(),
						0x2e2e2e, 0x605c57);
				serverLevel.sendParticles(ParticleTypes.WITCH, spirve2.getRandomX(0.5), spirve2.getRandomY(), spirve2.getRandomZ(0.5), 20, 0, 0, 0, 0);
			}
			if (spirve3 != null) {
				Main.conjureAbout(caster, spirve3, false);
				if (teams != null) {
					serverLevel.getScoreboard().addPlayerToTeam(spirve3.getStringUUID(), teams);
				}
				if (caster instanceof Mob mob && mob.getTarget() != null) {
					spirve3.setTarget(mob.getTarget());
				}
				ParticlesUse.summonParticle(serverLevel, spirve3, spirve3.getX(), spirve3.getY(), spirve3.getZ(),
						0x2e2e2e, 0x605c57);
				serverLevel.sendParticles(ParticleTypes.WITCH, spirve3.getRandomX(0.5), spirve3.getRandomY(), spirve3.getRandomZ(0.5), 20, 0, 0, 0, 0);
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.CONJURE_SPIRVE_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//二轮活化盔甲$法术
	public static boolean SecondRounderAnimatedArmor(LivingEntity caster, LivingEntity target) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			if (target instanceof ArmorStand armorStand) {
				PlayerTeam teams = (PlayerTeam) caster.getTeam();
				SecondRounderAnimatedArmorEntity secondRounderAnimatedArmor = JerotesVillageEntityType.SECOND_ROUNDER_ANIMATED_ARMOR.get().spawn(serverLevel, BlockPos.containing(armorStand.getX(), armorStand.getY(), armorStand.getZ()), MobSpawnType.CONVERSION);
				if (secondRounderAnimatedArmor != null) {
					Main.conjureAbout(caster, secondRounderAnimatedArmor);
					secondRounderAnimatedArmor.setTame(true);
					secondRounderAnimatedArmor.setOwnerUUID(caster.getUUID());
					secondRounderAnimatedArmor.setOwnerId(caster.getId());
					secondRounderAnimatedArmor.copyPosition(armorStand);
					if (caster instanceof Player player) {
						secondRounderAnimatedArmor.setChangeType(2, player);
					}
					else {
						secondRounderAnimatedArmor.setChangeType(2);
						if (caster instanceof Mob mob && mob.getTarget() != null) {
							secondRounderAnimatedArmor.setTarget(mob);
						}
					}
					if (teams != null) {
						serverLevel.getScoreboard().addPlayerToTeam(secondRounderAnimatedArmor.getStringUUID(), teams);
					}
					ParticlesUse.summonParticle(serverLevel, secondRounderAnimatedArmor, secondRounderAnimatedArmor.getX(), secondRounderAnimatedArmor.getY(), secondRounderAnimatedArmor.getZ(),
							0x73707c, 0xbebcc8);
					EquipmentSlot[] equipmentSlots = EquipmentSlot.values();
                    for (EquipmentSlot equipmentslot : equipmentSlots) {
						ItemStack itemstack = armorStand.getItemBySlot(equipmentslot);
						secondRounderAnimatedArmor.setItemSlot(equipmentslot, itemstack.copyAndClear());
                    }
					armorStand.discard();
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	//恶鳞炸弹
	public static boolean MalignasaurBomb(LivingEntity caster, int countMin, int countMax, int summonDistance, int spellLevelDamage, float spellLevelExplode) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			int count = countMin;
			if (countMin < countMax) {
				count = Main.randomReach(caster.getRandom(), countMin, countMax);
			}
			for (int i = 0; i < count; ++i) {
				BlockPos summonPos = Main.findSpawnPositionNearFill(caster, summonDistance);
				MalignasaurBombEntity bomb = new MalignasaurBombEntity(spellLevelDamage, spellLevelExplode, serverLevel, caster, 0, 0, 0);
				bomb.setPos(summonPos.getCenter().subtract(0, 4, 0));
				bomb.setOwner(caster);
				bomb.xPower = 0;
				bomb.yPower = 0;
				bomb.zPower = 0;
				serverLevel.addFreshEntity(bomb);
				serverLevel.sendParticles(JerotesVillageParticleTypes.GEMSTONE_BUBBLE.get(), bomb.getRandomX(0.5), bomb.getRandomY(), bomb.getRandomZ(0.5), 20, 0, 0, 0, 0);
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.GEMSTONE_SCALE_ARMOR_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
	//错乱放电$法术
	public static boolean ChaoticDischarge(LivingEntity caster, LivingEntity target, int spellLevelDamage, float spellLevelDistance, int targetCount) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			int attackCount = 1;
			if (target != null && target != caster && target.distanceTo(caster) <= spellLevelDistance) {
				if (!target.isShiftKeyDown()) {
					ChaoticDischargeEntity cloud = new ChaoticDischargeEntity(serverLevel, caster);
					cloud.setSpellLevelDamage(spellLevelDamage);
					cloud.setPos(target.getX(), target.getY() + 2 / 16f, target.getZ());
					cloud.setOwnerUUID(caster.getUUID());
					serverLevel.addFreshEntity(cloud);
				}
			}
			else {
				attackCount = 0;
			}
			List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, caster.getBoundingBox().inflate(spellLevelDistance, spellLevelDistance, spellLevelDistance));
			Collections.shuffle(list, new java.util.Random());
			list.sort(Comparator.comparingDouble(caster::distanceToSqr));
			for (LivingEntity find : list) {
				if (find == null) continue;
				if (find == caster) continue;
				if (find == target) continue;
				if (attackCount >= targetCount) break;
				if ((caster.distanceTo(find)) > spellLevelDistance * 2) continue;
				if (find instanceof EntityTrueBasePart) continue;
				if (AttackFind.FindCanNotAttack(caster, find)) continue;
				if (find.isShiftKeyDown()) continue;
				attackCount += 1;
				ChaoticDischargeEntity cloud = new ChaoticDischargeEntity(serverLevel, caster);
				cloud.setSpellLevelDamage(spellLevelDamage);
				cloud.setPos(find.getX(), find.getY() + 2 / 16f, find.getZ());
				cloud.setOwnerUUID(caster.getUUID());
				serverLevel.addFreshEntity(cloud);
			}

			if (attackCount < targetCount) {
				for (int j = attackCount; j < targetCount; ++j) {
					BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(caster.level(), caster.getOnPos(), (int) spellLevelDistance);
					ChaoticDischargeEntity cloud = new ChaoticDischargeEntity(serverLevel, caster);
					cloud.setSpellLevelDamage(spellLevelDamage);
					cloud.setPos((double) summonPos.getX() + 0.5D, summonPos.getY() + 2 / 16f, (double) summonPos.getZ() + 0.5D);
					cloud.setOwnerUUID(caster.getUUID());
					serverLevel.addFreshEntity(cloud);
					attackCount += 1;
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	//目光如炬$法术
	public static boolean TorchGaze(LivingEntity caster, LivingEntity target, int spellLevelDamage, float spellLevelDistance, int targetCount) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			int attackCount = 1;
			if (target != null && target != caster && target.distanceTo(caster) <= spellLevelDistance) {
				if (!target.isShiftKeyDown()) {
					TorchGazeEntity cloud = new TorchGazeEntity(serverLevel, caster);
					cloud.setSpellLevelDamage(spellLevelDamage);
					cloud.setPos(target.getX(), target.getY() + 2 / 16f, target.getZ());
					cloud.setOwnerUUID(caster.getUUID());
					serverLevel.addFreshEntity(cloud);
				}
			}
			else {
				attackCount = 0;
			}
			List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, caster.getBoundingBox().inflate(spellLevelDistance, spellLevelDistance, spellLevelDistance));
			Collections.shuffle(list, new java.util.Random());
			list.sort(Comparator.comparingDouble(caster::distanceToSqr));
			for (LivingEntity find : list) {
				if (find == null) continue;
				if (find == caster) continue;
				if (find == target) continue;
				if (attackCount >= targetCount) break;
				if ((caster.distanceTo(find)) > spellLevelDistance * 2) continue;
				if (find instanceof EntityTrueBasePart) continue;
				if (AttackFind.FindCanNotAttack(caster, find)) continue;
				if (find.isShiftKeyDown()) continue;
				attackCount += 1;
				TorchGazeEntity cloud = new TorchGazeEntity(serverLevel, caster);
				cloud.setSpellLevelDamage(spellLevelDamage);
				cloud.setPos(find.getX(), find.getY() + 2 / 16f, find.getZ());
				cloud.setOwnerUUID(caster.getUUID());
				serverLevel.addFreshEntity(cloud);
			}

			if (attackCount < targetCount) {
				for (int j = attackCount; j < targetCount; ++j) {
					BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(caster.level(), caster.getOnPos(), (int) spellLevelDistance / 4);
					TorchGazeEntity cloud = new TorchGazeEntity(serverLevel, caster);
					cloud.setSpellLevelDamage(spellLevelDamage);
					cloud.setPos((double) summonPos.getX() + 0.5D, summonPos.getY() + 2 / 16f, (double) summonPos.getZ() + 0.5D);
					cloud.setOwnerUUID(caster.getUUID());
					serverLevel.addFreshEntity(cloud);
					attackCount += 1;
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	//土崩瓦解$法术
	public static boolean CollapseZone(LivingEntity caster, LivingEntity target, int spellLevelDamage, float spellLevelDistance, int targetCount) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			int attackCount = 1;
			if (target != null && target != caster && target.distanceTo(caster) <= spellLevelDistance) {
				if (!target.isShiftKeyDown()) {
					CollapseZoneEntity zone = new CollapseZoneEntity(serverLevel, caster);
					zone.setSpellLevelDamage(spellLevelDamage);
					zone.setPos(target.getX(), target.getY() + 2 / 16f, target.getZ());
					zone.setOwnerUUID(caster.getUUID());
					serverLevel.addFreshEntity(zone);
				}
			}
			else {
				attackCount = 0;
			}
			LivingEntity lastTarget = target;
			List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, caster.getBoundingBox().inflate(spellLevelDistance, spellLevelDistance, spellLevelDistance));
			Collections.shuffle(list, new java.util.Random());
			list.sort(Comparator.comparingDouble(caster::distanceToSqr));
			for (LivingEntity find : list) {
				if (find == null) continue;
				if (find == caster) continue;
				if (find == target) continue;
				if (attackCount >= targetCount) break;
				if ((caster.distanceTo(find)) > spellLevelDistance * 2) continue;
				if (find instanceof EntityTrueBasePart) continue;
				if (AttackFind.FindCanNotAttack(caster, find)) continue;
				if (find.isShiftKeyDown()) continue;
				if (find.distanceTo(lastTarget) < 6) continue;
				lastTarget = find;
				attackCount += 1;
				CollapseZoneEntity zone = new CollapseZoneEntity(serverLevel, caster);
				zone.setSpellLevelDamage(spellLevelDamage);
				zone.setPos(find.getX(), find.getY() + 2 / 16f, find.getZ());
				zone.setOwnerUUID(caster.getUUID());
				serverLevel.addFreshEntity(zone);
			}

			if (attackCount < targetCount) {
				for (int j = attackCount; j < targetCount; ++j) {
					BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(caster.level(), caster.getOnPos(), (int) spellLevelDistance);
					CollapseZoneEntity zone = new CollapseZoneEntity(serverLevel, caster);
					zone.setSpellLevelDamage(spellLevelDamage);
					zone.setPos((double) summonPos.getX() + 0.5D, summonPos.getY() + 2 / 16f, (double) summonPos.getZ() + 0.5D);
					zone.setOwnerUUID(caster.getUUID());
					serverLevel.addFreshEntity(zone);
					attackCount += 1;
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	//维落萨恩的魔能冲爆$法术
	public static boolean VirosaansManaSurge(LivingEntity caster, Entity target, int spellLevelDamage, float spellLevelAccuracy, int count, float distance, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			VirosaansManaSurgeEntity spell;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				double d2 = caster.getLookAngle().x;
				double d3 = caster.getLookAngle().y;
				double d4 = caster.getLookAngle().z;
				spell = new VirosaansManaSurgeEntity(spellLevelDamage, serverLevel, caster, d2, d3, d4);
				spell.setPos(caster.getX(), caster.getY(0.7) - spell.getBbHeight()/2, caster.getZ());
				spell.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1.5f, spellLevelAccuracy);
				spell.setOwner(caster);
				serverLevel.addFreshEntity(spell);
                if (!caster.isSilent()) {
                    caster.level().playSound(null, caster.getX(), caster.getY(), caster.getZ(), SoundEvents.GENERIC_EXPLODE,
                            caster.getSoundSource(), 1.0f, 1.0F);
                }
			}
		}
		return true;
	}
	//维落萨恩的力场球爆$法术
	public static boolean VirosaansForceBurstBall(LivingEntity caster, LivingEntity target, int spellLevelDamage) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			Vec3 targetPos = caster.getPosition(0);
			if (target != null) {
				targetPos = new Vec3(target.getX() + 0.5D, target.getY(), target.getZ() + 0.5D);
			}
			if (caster instanceof Player && (target == null || target == caster)) {
				Vec3 startPos = caster.getEyePosition(1.0f);
				Vec3 viewVector = caster.getViewVector(1.0f);
				Vec3 endPos = startPos.add(viewVector.scale(45));

				BlockHitResult hitResult = serverLevel.clip(new ClipContext(
						startPos, endPos,
						ClipContext.Block.OUTLINE,
						ClipContext.Fluid.NONE,
						caster
				));
				targetPos = Main.adjustPositionForSolidHit(hitResult, startPos, viewVector, 45);
			}
			//目标位置
			VirosaansForceBurstBallEntity cloud = new VirosaansForceBurstBallEntity(serverLevel, caster);
			cloud.setStartPos(new Vec3(caster.getX(), caster.getY(0.7), caster.getZ()));
			cloud.setSpellLevelDamage(spellLevelDamage);
			cloud.setPos(targetPos.x, targetPos.y + 2/16f, targetPos.z);
			cloud.setOwner(caster);
			serverLevel.addFreshEntity(cloud);
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	//落地生根$法术
	public static boolean TakeRoot(LivingEntity caster, LivingEntity target, int spellLevelDamage) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			Vec3 targetPos = caster.getPosition(0);
			if (target != null) {
				targetPos = new Vec3(target.getX() + 0.5D, target.getY(), target.getZ() + 0.5D);
			}
			if (caster instanceof Player && (target == null || target == caster)) {
				Vec3 startPos = caster.getEyePosition(1.0f);
				Vec3 viewVector = caster.getViewVector(1.0f);
				Vec3 endPos = startPos.add(viewVector.scale(45));

				BlockHitResult hitResult = serverLevel.clip(new ClipContext(
						startPos, endPos,
						ClipContext.Block.OUTLINE,
						ClipContext.Fluid.NONE,
						caster
				));
				targetPos = Main.adjustPositionForSolidHit(hitResult, startPos, viewVector, 45);
			}
			//目标位置
			TakeRootEntity cloud = new TakeRootEntity(serverLevel, caster);
			if (caster instanceof CelestialCoilvineEntity) {
				float angleYaw = (90.0F) * ((float) Math.PI / 180.0F);
				double x = caster.getX() + 6 * Mth.cos((float) (caster.getYHeadRot() * (Math.PI / 180.0F) + angleYaw));
				double z = caster.getZ() + 6 * Mth.sin((float) (caster.getYHeadRot() * (Math.PI / 180.0F) + angleYaw));
				cloud.setStartPos(new Vec3(x, caster.getY(0.6), z));
			}
			else {
				cloud.setStartPos(new Vec3(caster.getX(), caster.getY(0.7), caster.getZ()));
			}
			cloud.setSpellLevelDamage(spellLevelDamage);
			cloud.setPos(targetPos.x, targetPos.y + 2/16f, targetPos.z);
			cloud.setOwner(caster);
			serverLevel.addFreshEntity(cloud);
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	public static boolean DeadCenter(LivingEntity caster, LivingEntity target, int spellLevelDamage, float spellLevelDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			if (target != null && target != caster && target.isAlive() && caster.distanceTo(target) <= spellLevelDistance
					&& !AttackFind.FindCanNotAttack(caster, target)) {
				DeadCenterEntity spell = new DeadCenterEntity(spellLevelDamage, serverLevel, caster, 0, 0, 0);
				spell.setPos(target.getX(), target.getY() + 2 / 16f, target.getZ());
				spell.setOwner(caster);
				spell.setTarget(target);
				spell.setSize(Main.mobWidth(target));
				spell.setTargetHeight(Main.mobHeight(target));
				serverLevel.addFreshEntity(spell);
				serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
			}
		}
		return true;
	}
}