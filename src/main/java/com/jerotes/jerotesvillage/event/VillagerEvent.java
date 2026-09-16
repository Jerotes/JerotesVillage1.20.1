package com.jerotes.jerotesvillage.event;

import com.jerotes.jerotes.entity.Mob.AddHandEntity;
import com.jerotes.jerotes.entity.Shoot.Arrow.ThrownAnestheticJavelinEntity;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.goal.JerotesPlayerTargetGoal;
import com.jerotes.jerotes.init.JerotesItems;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.spell.SpellList;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Animal.RottenDogEntity;
import com.jerotes.jerotesvillage.entity.Boss.OminousBannerProjectionEntity;
import com.jerotes.jerotesvillage.entity.Humanoid.CarvedVillagerEntity;
import com.jerotes.jerotesvillage.entity.Monster.BlamerEntity;
import com.jerotes.jerotesvillage.entity.Monster.Hag.BaseHagEntity;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.FirepowerPourerEntity;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.MeleeIllagerEntity;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.SpellIllagerEntity;
import com.jerotes.jerotesvillage.entity.Monster.Resurrecter.ResurrecterEntity;
import com.jerotes.jerotesvillage.entity.Monster.SpirveEntity;
import com.jerotes.jerotesvillage.entity.Monster.WickedRidiculeCalls.SecondRounderSkeletonEntity;
import com.jerotes.jerotesvillage.entity.Monster.WickedRidiculeCalls.SecondRounderZombieEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.CarvedVillagerSpectralArrowEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageVillagerProfessions;
import com.jerotes.jerotesvillage.util.OtherEntityFactionFind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = JerotesVillage.MODID)
public class VillagerEvent {

	@SubscribeEvent
	public static void VillagerGoal(EntityJoinLevelEvent event) {
		Entity entity = event.getEntity();
		if (!(entity.getType() == EntityType.VILLAGER || entity.getType() == EntityType.WANDERING_TRADER))
			return;
        if (entity instanceof Villager villager) {
			if (!(villager.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_CONSCRIPTION.get() && villager.getVillagerData().getLevel() > 0)) {
				VillagerAddGoal(villager);
			}
			else {
				Conscription(villager, villager.getVillagerData());
			}
        }
        if (entity instanceof WanderingTrader villager) {
			VillagerAddGoal(villager);
        }
	}

	@SubscribeEvent
	public static void VillagerProfession(LivingEvent.LivingTickEvent event) {
		LivingEntity entity = event.getEntity();
		if (entity == null || !entity.isAlive())
			return;
		if (!(entity instanceof Villager villager))
			return;
		if (Main.getJerotesPersistentData(villager).getDouble("jerotesvillage_villager_attack_cooldown") == 60 - (villager.getVillagerData().getLevel() * 5f)) {
			if (villager.getTarget() != null && villager.distanceTo(villager.getTarget()) < 18 && villager.getTarget().isAlive()
					&& !AttackFind.FindCanNotAttack(villager, villager.getTarget()) || villager.getLastHurtByMob() != null && villager.distanceTo(villager.getLastHurtByMob()) < 18 && villager.getLastHurtByMob().isAlive()
					&& !AttackFind.FindCanNotAttack(villager, villager.getLastHurtByMob())) {
				//猎人
				if (villager.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_HUNTER.get() && villager.getVillagerData().getLevel() > 0){
					AddHandEntity addHandEntity = Main.spawnAddHand(villager, (int) (60 - (villager.getVillagerData().getLevel() * 5f)), 100);
					if (addHandEntity != null) {
						addHandEntity.setItemInHand(InteractionHand.MAIN_HAND, Items.BOW.getDefaultInstance());
					}
				}
			}
		}

		if (Main.getJerotesPersistentData(villager).getDouble("jerotesvillage_villager_attack_cooldown") > 0) {
			Main.getJerotesPersistentData(villager).putDouble("jerotesvillage_villager_attack_cooldown", Main.getJerotesPersistentData(villager).getDouble("jerotesvillage_villager_attack_cooldown") - 1);
		}


		if (Main.getJerotesPersistentData(villager).getDouble("jerotesvillage_villager_attack_cooldown") <= 0) {
			//医师
			if (villager.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_PHYSICIAN.get() &&
					villager.getVillagerData().getLevel() > 0){
				int heals = 0;
				List<LivingEntity> list = villager.level().getEntitiesOfClass(LivingEntity.class, villager.getBoundingBox().inflate(8, 8, 8));
				for (LivingEntity livingEntityControl : list) {
					if (livingEntityControl == null) continue;
					if (heals > 5.0f * (villager.getVillagerData().getLevel() + 1)) continue;
					if ((villager.distanceToSqr(livingEntityControl)) > 16) continue;
					if ((livingEntityControl instanceof Player player && villager.getPlayerReputation(player) < 0) && !player.hasEffect(MobEffects.HERO_OF_THE_VILLAGE)) continue;
					if (!livingEntityControl.isAlliedTo(villager) && !(livingEntityControl instanceof Villager || OtherEntityFactionFind.isCarved(livingEntityControl.getType()) || livingEntityControl instanceof IronGolem || livingEntityControl instanceof WanderingTrader || livingEntityControl.hasEffect(MobEffects.HERO_OF_THE_VILLAGE))) continue;
					if (livingEntityControl.getHealth() >= livingEntityControl.getMaxHealth()) continue;
					if (livingEntityControl instanceof WanderingTrader) {
						livingEntityControl.playSound(SoundEvents.WANDERING_TRADER_DRINK_POTION, 1.0f, livingEntityControl.getVoicePitch());
					}
					else if (livingEntityControl instanceof CarvedVillagerEntity carvedVillagerEntity) {
						if (carvedVillagerEntity.isSkin()) {
							livingEntityControl.playSound(SoundEvents.PLAYER_ATTACK_NODAMAGE, 1.0f, livingEntityControl.getVoicePitch());
						}
						else if (carvedVillagerEntity.isEndermanRace()) {
							livingEntityControl.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0f, livingEntityControl.getVoicePitch());
						}
						else if (carvedVillagerEntity.isIllagerRace()) {
							livingEntityControl.playSound(SoundEvents.PILLAGER_CELEBRATE, 1.0f, livingEntityControl.getVoicePitch());
						}
						else if (carvedVillagerEntity.isPiglinRace()) {
							livingEntityControl.playSound(SoundEvents.PIGLIN_CELEBRATE, 1.0f, livingEntityControl.getVoicePitch());
						}
						else {
							livingEntityControl.playSound(SoundEvents.VILLAGER_YES, 1.0f, livingEntityControl.getVoicePitch());
						}
					}
					else if (livingEntityControl instanceof AbstractVillager abstractVillager) {
						if (abstractVillager instanceof VillagerDataHolder villagerDataHolder && villagerDataHolder.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_PIGLIN.get()) {
							livingEntityControl.playSound(SoundEvents.PIGLIN_CELEBRATE, 1.0f, livingEntityControl.getVoicePitch());
						}
						else if (abstractVillager instanceof VillagerDataHolder villagerDataHolder && villagerDataHolder.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_ILLAGER.get()) {
							livingEntityControl.playSound(SoundEvents.PILLAGER_CELEBRATE, 1.0f, livingEntityControl.getVoicePitch());
						}
						else if (abstractVillager instanceof VillagerDataHolder villagerDataHolder && villagerDataHolder.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_WITCH.get()) {
							livingEntityControl.playSound(SoundEvents.WITCH_CELEBRATE, 1.0f, livingEntityControl.getVoicePitch());
						}
						else {
							livingEntityControl.playSound(SoundEvents.VILLAGER_YES, 1.0f, livingEntityControl.getVoicePitch());
						}
					}
					else if (livingEntityControl instanceof IronGolem) {
						livingEntityControl.playSound(SoundEvents.IRON_GOLEM_REPAIR, 1.0f, livingEntityControl.getVoicePitch());
					}
					else {
						livingEntityControl.playSound(SoundEvents.SPLASH_POTION_BREAK, 1.0f, livingEntityControl.getVoicePitch());
					}
					livingEntityControl.heal(2.0f * (villager.getVillagerData().getLevel() + 1));
					if (villager.level() instanceof ServerLevel serverLevel) {
						serverLevel.sendParticles(ParticleTypes.HEART, livingEntityControl.getRandomX(0.5), livingEntityControl.getRandomY(), livingEntityControl.getRandomZ(0.5), 6, 0, 0, 0, 0);
						serverLevel.sendParticles(ParticleTypes.HEART, villager.getX() + villager.getRandom().nextGaussian() * 0.13, villager.getBoundingBox().maxY + 0.5 + villager.getRandom().nextGaussian() * 0.13, villager.getZ() + villager.getRandom().nextGaussian() * 0.13, 1, 0.0, 0.0, 0.0, 0.0);
					}
					heals += 1;
				}
				if (heals > 0) {
					villager.playSound(JerotesSoundEvents.ITEM_THROW, 1.0f, villager.getVoicePitch());
				}
				Main.getJerotesPersistentData(villager).putDouble("jerotesvillage_villager_attack_cooldown", 120);
			}
			//女巫
			if (villager.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_WITCH.get()
				&& villager.getVillagerData().getLevel() > 0){
				if (villager.getTarget() != null && villager.distanceTo(villager.getTarget()) < 6 && villager.getTarget().isAlive()
						&& !AttackFind.FindCanNotAttack(villager, villager.getTarget())) {
					Vec3 vec3 = villager.getTarget().getDeltaMovement();
					double d = villager.getTarget().getX() + vec3.x - villager.getX();
					double d2 = villager.getTarget().getEyeY() - 1.100000023841858 - villager.getY();
					double d3 = villager.getTarget().getZ() + vec3.z - villager.getZ();
					double d4 = Math.sqrt(d * d + d3 * d3);
					Potion potion;
					if (!villager.getTarget().hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
						potion = Potions.SLOWNESS;
						if (villager.getVillagerData().getLevel() > 2) {
							potion = Potions.STRONG_SLOWNESS;
						}
					}
					else {
						potion = Potions.WEAKNESS;
						if (villager.getVillagerData().getLevel() > 2) {
							potion = Potions.LONG_WEAKNESS;
						}
					}
					ThrownPotion thrownPotion = new ThrownPotion(villager.level(), villager);
					thrownPotion.setItem(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), potion));
					thrownPotion.setXRot(thrownPotion.getXRot() + 20.0f);
					thrownPotion.shoot(d, d2 + d4 * 0.2, d3, 0.75f, 8.0f);
					if (!villager.isSilent()) {
						villager.level().playSound(null, villager.getX(), villager.getY(), villager.getZ(), SoundEvents.WITCH_THROW, villager.getSoundSource(), 1.0f, 0.8f + villager.getRandom().nextFloat() * 0.4f);
					}
					villager.level().addFreshEntity(thrownPotion);
					Main.getJerotesPersistentData(villager).putDouble("jerotesvillage_villager_attack_cooldown", 60 - (villager.getVillagerData().getLevel() * 4f));
				}
				else if (villager.getLastHurtByMob() != null && villager.distanceTo(villager.getLastHurtByMob()) < 6 && villager.getLastHurtByMob().isAlive()
						&& !AttackFind.FindCanNotAttack(villager, villager.getLastHurtByMob())) {
					Vec3 vec3 = villager.getLastHurtByMob().getDeltaMovement();
					double d = villager.getLastHurtByMob().getX() + vec3.x - villager.getX();
					double d2 = villager.getLastHurtByMob().getEyeY() - 1.100000023841858 - villager.getY();
					double d3 = villager.getLastHurtByMob().getZ() + vec3.z - villager.getZ();
					double d4 = Math.sqrt(d * d + d3 * d3);
					Potion potion;
					if (!villager.getLastHurtByMob().hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
						potion = Potions.SLOWNESS;
						if (villager.getVillagerData().getLevel() > 2) {
							potion = Potions.STRONG_SLOWNESS;
						}
					}
					else {
						potion = Potions.WEAKNESS;
						if (villager.getVillagerData().getLevel() > 2) {
							potion = Potions.LONG_WEAKNESS;
						}
					}
					ThrownPotion thrownPotion = new ThrownPotion(villager.level(), villager);
					thrownPotion.setItem(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), potion));
					thrownPotion.setXRot(thrownPotion.getXRot() + 20.0f);
					thrownPotion.shoot(d, d2 + d4 * 0.2, d3, 0.75f, 8.0f);
					if (!villager.isSilent()) {
						villager.level().playSound(null, villager.getX(), villager.getY(), villager.getZ(), SoundEvents.WITCH_THROW, villager.getSoundSource(), 1.0f, 0.8f + villager.getRandom().nextFloat() * 0.4f);
					}
					villager.level().addFreshEntity(thrownPotion);
					Main.getJerotesPersistentData(villager).putDouble("jerotesvillage_villager_attack_cooldown", 60 - (villager.getVillagerData().getLevel() * 4f));
				}
			}
			//狱卒
			if (villager.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_JAILER.get() && villager.getVillagerData().getLevel() > 0){
				if (villager.getTarget() != null && villager.distanceTo(villager.getTarget()) < 18 && villager.getTarget().isAlive()
						&& !AttackFind.FindCanNotAttack(villager, villager.getTarget())) {
					villager.getLookControl().setLookAt(villager.getTarget(), 360.0f, 360.0f);
					villager.lookAt(villager.getTarget(), 360.0f, 360.0f);
					ThrownAnestheticJavelinEntity anestheticSpear = new ThrownAnestheticJavelinEntity(villager.level(), villager, new ItemStack(JerotesItems.ANESTHETIC_JAVELIN.get()));
					double d = villager.getTarget().getX() - villager.getX();
					double d2 = villager.getTarget().getY(0.3333333333333333) - anestheticSpear.getY();
					double d3 = villager.getTarget().getZ() - villager.getZ();
					double d4 = Math.sqrt(d * d + d3 * d3);
					anestheticSpear.isVillager = true;
					anestheticSpear.shoot(d, d2 + d4 * 0.20000000298023224, d3, 1.6f, 14 - villager.level().getDifficulty().getId() * 4);
					villager.playSound(SoundEvents.TRIDENT_THROW, 1.0f, 1.0f / (villager.getRandom().nextFloat() * 0.4f + 0.8f));
					villager.level().addFreshEntity(anestheticSpear);
					Main.getJerotesPersistentData(villager).putDouble("jerotesvillage_villager_attack_cooldown", 180 - (villager.getVillagerData().getLevel() * 10f));
				}
				else if (villager.getLastHurtByMob() != null && villager.distanceTo(villager.getLastHurtByMob()) < 18 && villager.getLastHurtByMob().isAlive()
						&& !AttackFind.FindCanNotAttack(villager, villager.getLastHurtByMob())) {
					villager.getLookControl().setLookAt(villager.getLastHurtByMob(), 360.0f, 360.0f);
					villager.lookAt(villager.getLastHurtByMob(), 360.0f, 360.0f);
					ThrownAnestheticJavelinEntity anestheticSpear = new ThrownAnestheticJavelinEntity(villager.level(), villager, new ItemStack(JerotesItems.ANESTHETIC_JAVELIN.get()));
					double d = villager.getLastHurtByMob().getX() - villager.getX();
					double d2 = villager.getLastHurtByMob().getY(0.3333333333333333) - anestheticSpear.getY();
					double d3 = villager.getLastHurtByMob().getZ() - villager.getZ();
					double d4 = Math.sqrt(d * d + d3 * d3);
					anestheticSpear.isVillager = true;
					anestheticSpear.shoot(d, d2 + d4 * 0.20000000298023224, d3, 1.6f, 14 - villager.level().getDifficulty().getId() * 4);
					villager.playSound(SoundEvents.TRIDENT_THROW, 1.0f, 1.0f / (villager.getRandom().nextFloat() * 0.4f + 0.8f));
					villager.level().addFreshEntity(anestheticSpear);
					Main.getJerotesPersistentData(villager).putDouble("jerotesvillage_villager_attack_cooldown", 180 - (villager.getVillagerData().getLevel() * 10f));
				}
			}
			//猎人
			if (villager.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_HUNTER.get() && villager.getVillagerData().getLevel() > 0){
				if (villager.getTarget() != null && villager.distanceTo(villager.getTarget()) < 18 && villager.getTarget().isAlive()
						&& !AttackFind.FindCanNotAttack(villager, villager.getTarget())) {
					villager.getLookControl().setLookAt(villager.getTarget(), 360.0f, 360.0f);
					villager.lookAt(villager.getTarget(), 360.0f, 360.0f);
					CarvedVillagerSpectralArrowEntity carvedVillagerSpectralArrow = new CarvedVillagerSpectralArrowEntity(villager.level(), villager, new ItemStack(JerotesVillageItems.CARVED_VILLAGER_SPECTRAL_ARROW.get()));
					double d = villager.getTarget().getX() - villager.getX();
					double d2 = villager.getTarget().getY(0.3333333333333333) - carvedVillagerSpectralArrow.getY();
					double d3 = villager.getTarget().getZ() - villager.getZ();
					double d4 = Math.sqrt(d * d + d3 * d3);
					carvedVillagerSpectralArrow.isVillager = true;
					carvedVillagerSpectralArrow.shoot(d, d2 + d4 * 0.20000000298023224, d3, 1.6f, 14 - villager.level().getDifficulty().getId() * 4);
					villager.playSound(SoundEvents.ARROW_SHOOT, 1.0f, 1.0f / (villager.getRandom().nextFloat() * 0.4f + 0.8f));
					villager.level().addFreshEntity(carvedVillagerSpectralArrow);
					Main.getJerotesPersistentData(villager).putDouble("jerotesvillage_villager_attack_cooldown", 60 - (villager.getVillagerData().getLevel() * 5f));
				}
				else if (villager.getLastHurtByMob() != null && villager.distanceTo(villager.getLastHurtByMob()) < 18 && villager.getLastHurtByMob().isAlive()
						&& !AttackFind.FindCanNotAttack(villager, villager.getLastHurtByMob())) {
					villager.getLookControl().setLookAt(villager.getLastHurtByMob(), 360.0f, 360.0f);
					villager.lookAt(villager.getLastHurtByMob(), 360.0f, 360.0f);
					CarvedVillagerSpectralArrowEntity carvedVillagerSpectralArrow = new CarvedVillagerSpectralArrowEntity(villager.level(), villager, new ItemStack(JerotesVillageItems.CARVED_VILLAGER_SPECTRAL_ARROW.get()));
					double d = villager.getLastHurtByMob().getX() - villager.getX();
					double d2 = villager.getLastHurtByMob().getY(0.3333333333333333) - carvedVillagerSpectralArrow.getY();
					double d3 = villager.getLastHurtByMob().getZ() - villager.getZ();
					double d4 = Math.sqrt(d * d + d3 * d3);
					carvedVillagerSpectralArrow.isVillager = true;
					carvedVillagerSpectralArrow.shoot(d, d2 + d4 * 0.20000000298023224, d3, 1.6f, 14 - villager.level().getDifficulty().getId() * 4);
					villager.playSound(SoundEvents.ARROW_SHOOT, 1.0f, 1.0f / (villager.getRandom().nextFloat() * 0.4f + 0.8f));
					villager.level().addFreshEntity(carvedVillagerSpectralArrow);
					Main.getJerotesPersistentData(villager).putDouble("jerotesvillage_villager_attack_cooldown", 60 - (villager.getVillagerData().getLevel() * 5f));
				}
			}
			//法师
			if (villager.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_MAGICIAN.get() &&
					villager.getVillagerData().getLevel() > 0){
				int spellLevel = 1;
				if (villager.getVillagerData().getLevel() >= 2) {
					spellLevel = 2;
				}
				if (villager.getVillagerData().getLevel() >= 4) {
					spellLevel = 3;
				}
				if (villager.getTarget() != null && villager.distanceTo(villager.getTarget()) < 18 && villager.getTarget().isAlive()
						&& !AttackFind.FindCanNotAttack(villager, villager.getTarget())) {
					villager.lookAt(villager.getTarget(), 360.0f, 360.0f);
					//法术列表-魔法飞弹
					SpellList.MagicMissile(spellLevel, villager, villager.getTarget()).spellUse();
					Main.getJerotesPersistentData(villager).putDouble("jerotesvillage_villager_attack_cooldown", 60 - (villager.getVillagerData().getLevel() * 5f));
				}
				else if (villager.getLastHurtByMob() != null && villager.distanceTo(villager.getLastHurtByMob()) < 18 && villager.getLastHurtByMob().isAlive()
						&& !AttackFind.FindCanNotAttack(villager, villager.getLastHurtByMob())) {
					villager.lookAt(villager.getLastHurtByMob(), 360.0f, 360.0f);
					//法术列表-魔法飞弹
					SpellList.MagicMissile(spellLevel, villager, villager.getLastHurtByMob()).spellUse();
					Main.getJerotesPersistentData(villager).putDouble("jerotesvillage_villager_attack_cooldown", 60 - (villager.getVillagerData().getLevel() * 5f));
				}
			}
		}
	}


	public static void VillagerAddGoal(AbstractVillager villager) {
		villager.goalSelector.addGoal(1, new AvoidEntityGoal<>(villager, MeleeIllagerEntity.class, 8.0F, 0.6D, 0.6D));
		villager.goalSelector.addGoal(1, new AvoidEntityGoal<>(villager, SpellIllagerEntity.class, 8.0F, 0.8D, 0.8D));
		villager.goalSelector.addGoal(1, new AvoidEntityGoal<>(villager, SpirveEntity.class, 8.0F, 0.8D, 0.8D));
		villager.goalSelector.addGoal(1, new AvoidEntityGoal<>(villager, ResurrecterEntity.class, 8.0F, 0.8D, 0.8D));
		villager.goalSelector.addGoal(1, new AvoidEntityGoal<>(villager, BlamerEntity.class, 8.0F, 0.8D, 0.8D));
		villager.goalSelector.addGoal(1, new AvoidEntityGoal<>(villager, SecondRounderZombieEntity.class, 8.0F, 0.8D, 0.8D));
		villager.goalSelector.addGoal(1, new AvoidEntityGoal<>(villager, SecondRounderSkeletonEntity.class, 8.0F, 0.8D, 0.8D));
		villager.goalSelector.addGoal(1, new AvoidEntityGoal<>(villager, RottenDogEntity.class, 8.0F, 0.8D, 0.8D));
		villager.goalSelector.addGoal(1, new AvoidEntityGoal<>(villager, BaseHagEntity.class, 8.0F, 0.8D, 0.8D));
		villager.goalSelector.addGoal(1, new AvoidEntityGoal<>(villager, FirepowerPourerEntity.class, 8.0F, 0.8D, 0.8D));
		villager.goalSelector.addGoal(1, new AvoidEntityGoal<>(villager, OminousBannerProjectionEntity.class, 8.0F, 0.8D, 0.8D));
	}
	public static void VillagerRemoveGoal(AbstractVillager villager) {
		villager.goalSelector.removeAllGoals(goal -> goal instanceof PanicGoal);
		villager.goalSelector.removeAllGoals(goal -> goal instanceof AvoidEntityGoal<?>);

		villager.goalSelector.removeGoal(new AvoidEntityGoal<>(villager, MeleeIllagerEntity.class, 8.0F, 0.6D, 0.6D));
		villager.goalSelector.removeGoal(new AvoidEntityGoal<>(villager, SpellIllagerEntity.class, 8.0F, 0.8D, 0.8D));
		villager.goalSelector.removeGoal(new AvoidEntityGoal<>(villager, SpirveEntity.class, 8.0F, 0.8D, 0.8D));
		villager.goalSelector.removeGoal(new AvoidEntityGoal<>(villager, ResurrecterEntity.class, 8.0F, 0.8D, 0.8D));
		villager.goalSelector.removeGoal(new AvoidEntityGoal<>(villager, BlamerEntity.class, 8.0F, 0.8D, 0.8D));
		villager.goalSelector.removeGoal(new AvoidEntityGoal<>(villager, SecondRounderZombieEntity.class, 8.0F, 0.8D, 0.8D));
		villager.goalSelector.removeGoal(new AvoidEntityGoal<>(villager, SecondRounderSkeletonEntity.class, 8.0F, 0.8D, 0.8D));
		villager.goalSelector.removeGoal(new AvoidEntityGoal<>(villager, RottenDogEntity.class, 8.0F, 0.8D, 0.8D));
		villager.goalSelector.removeGoal(new AvoidEntityGoal<>(villager, BaseHagEntity.class, 8.0F, 0.8D, 0.8D));
		villager.goalSelector.removeGoal(new AvoidEntityGoal<>(villager, FirepowerPourerEntity.class, 8.0F, 0.8D, 0.8D));
		villager.goalSelector.removeGoal(new AvoidEntityGoal<>(villager, OminousBannerProjectionEntity.class, 8.0F, 0.8D, 0.8D));
	}

	public static void Conscription(AbstractVillager villager, VillagerData villagerData) {
		villager.goalSelector.addGoal(0, new ConscriptionMeleeAttackGoal(villager));
		villager.goalSelector.addGoal(0, new ConscriptionMoveTowardsTargetGoal(villager));
		villager.targetSelector.addGoal(0, new ConscriptionHurtByTargetGoal(villager));
		villager.targetSelector.addGoal(1, new JerotesPlayerTargetGoal<Mob>(villager, Mob.class, false));
		VillagerRemoveGoal(villager);

		if (villager.getAttributeBaseValue(Attributes.ARMOR) < (4 + villagerData.getLevel() * 2)) {
			Objects.requireNonNull(villager.getAttribute(Attributes.ARMOR)).setBaseValue(4 + villagerData.getLevel() * 2);
		}
		if (villager.getAttributeBaseValue(Attributes.MAX_HEALTH) < (20 + villagerData.getLevel() * 4)) {
			Objects.requireNonNull(villager.getAttribute(Attributes.MAX_HEALTH)).setBaseValue((20 + villagerData.getLevel() * 4));
		}
	}

	public static class ConscriptionMeleeAttackGoal extends JerotesMeleeAttackGoal {
		private final AbstractVillager mob;
		public ConscriptionMeleeAttackGoal(AbstractVillager mob) {
			super(mob, 1.1, true);
			this.mob = mob;
		}
		@Override
		protected void checkAndPerformAttack(LivingEntity livingEntity) {
			if (this.canPerformAttack(livingEntity)) {
				this.resetAttackCooldown();
				this.mob.swing(InteractionHand.MAIN_HAND);
				doHurtTarget(this.mob, livingEntity);
			}
		}
		public boolean doHurtTarget(AbstractVillager villager, Entity entity) {
			if (villager instanceof Villager villager1) {
                AddHandEntity addHandEntity = Main.spawnAddHand(villager, 6, 100);
				float f = villager1.getVillagerData().getLevel() * 2;
				if (addHandEntity != null) {
					Objects.requireNonNull(addHandEntity.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(f + 1);
					addHandEntity.setItemInHand(InteractionHand.MAIN_HAND, JerotesVillageItems.VILLAGER_METAL_SWORD.get().getDefaultInstance());
				}
				AttackFind.attackBegin(villager, entity);
				boolean bl = AttackFind.attackAfter(villager1, entity, 0, 0.2f, true, f + 6);
				if (bl) {
					if (!villager1.isSilent()) {
						villager1.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0f, 1.0f);
					}
				}
				if (!entity.isAlive()) {
					villager.setTarget(null);
				}
				return bl;
			}
			return false;
		}
	}
	public static class ConscriptionMoveTowardsTargetGoal extends MoveTowardsTargetGoal {
		private final AbstractVillager mob;
		public ConscriptionMoveTowardsTargetGoal(AbstractVillager mob) {
			super(mob, 1.0, 32.0f);
			this.mob = mob;
		}
	}
	public static class ConscriptionHurtByTargetGoal extends HurtByTargetGoal {
		private final AbstractVillager mob;
		public ConscriptionHurtByTargetGoal(AbstractVillager mob) {
			super(mob);
			this.mob = mob;
		}

		public boolean canUse() {
			if (this.mob.getLastHurtByMob() != null && OtherEntityFactionFind.isCarved(this.mob.getLastHurtByMob().getType()) && this.mob.getTeam() == null && this.mob.getLastHurtByMob().getTeam() == null) {
				return false;
			}
			return super.canUse();
		}
	}
}

