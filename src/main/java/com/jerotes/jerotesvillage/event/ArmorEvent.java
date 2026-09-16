package com.jerotes.jerotesvillage.event;

import com.jerotes.jerotes.entity.Interface.EliteEntity;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.spell.SpellList;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Boss.TyrantOfTheMountainRealmEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.item.BaseHagEye;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Optional;


@Mod.EventBusSubscriber
public class ArmorEvent {

	//大巫婆之帽
	@SubscribeEvent
	public static void BigWitchsHat(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			LivingEntity livingEntity = event.getEntity();
			DamageSource damageSource = event.getSource();
			if (damageSource == null || livingEntity == null)
				return;
			if (!livingEntity.level().isClientSide()) {
				if (damageSource.is(DamageTypeTags.WITCH_RESISTANT_TO)) {
					if (livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.BIG_WITCHS_HAT.get()) {
						SpellList.MagicAbsorption(3, livingEntity, livingEntity).spellUse();
						float newAmount = event.getAmount() - event.getAmount() * 0.3f;
						if (Float.isNaN(newAmount) || Float.isInfinite(newAmount)) {
							newAmount = 0f;
						}
						event.setAmount(newAmount);
					}
				}
			}
		}
	}

	//鬼婆之眼
	@SubscribeEvent
	public static void HagEye(LivingEvent.LivingTickEvent event) {
		LivingEntity livingEntity = event.getEntity();
		if (livingEntity == null)
			return;
		if (livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof BaseHagEye) {
			if (!livingEntity.level().isClientSide()) {
				livingEntity.addEffect(new MobEffectInstance((MobEffects.NIGHT_VISION), 255, 0, false, false), livingEntity);
			}
		}
	}

	//魔鳞套装
	@SubscribeEvent
	public static void MagickaScaleTick(LivingEvent.LivingTickEvent event) {
		LivingEntity livingEntity = event.getEntity();
		if (livingEntity == null)
			return;
		boolean helmet = livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.MAGICKA_SCALE_HELMET.get();
		boolean chestplate = livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == JerotesVillageItems.MAGICKA_SCALE_CHESTPLATE.get();
		boolean leggings = livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == JerotesVillageItems.MAGICKA_SCALE_LEGGINGS.get();
		boolean boots = livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() == JerotesVillageItems.MAGICKA_SCALE_BOOTS.get();
		if (!livingEntity.level().isClientSide()) {
			if (helmet && chestplate && leggings && boots &&
					livingEntity.getHealth() <= livingEntity.getMaxHealth() / 2) {
				livingEntity.addEffect(new MobEffectInstance((MobEffects.MOVEMENT_SPEED), 25, 1, false, false), livingEntity);
			}
		}
	}
	@SubscribeEvent
	public static void MagickaScaleAttack(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			LivingEntity livingEntity = event.getEntity();
			DamageSource damageSource = event.getSource();
			if (damageSource == null || livingEntity == null)
				return;
			boolean helmet = livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.MAGICKA_SCALE_HELMET.get();
			boolean chestplate = livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == JerotesVillageItems.MAGICKA_SCALE_CHESTPLATE.get();
			boolean leggings = livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == JerotesVillageItems.MAGICKA_SCALE_LEGGINGS.get();
			boolean boots = livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() == JerotesVillageItems.MAGICKA_SCALE_BOOTS.get();
			if (!livingEntity.level().isClientSide()) {
				if (damageSource.is(DamageTypes.CRAMMING)
						|| damageSource.is(DamageTypes.IN_WALL)
						|| damageSource.is(DamageTypes.THORNS)) {
					if (helmet && chestplate && leggings && boots) {
						event.setAmount(0);
						event.setCanceled(true);
					}
				}
			}
		}
	}
	@SubscribeEvent
	public static void MagickaScaleHurt(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			LivingEntity livingEntity = event.getEntity();
			DamageSource damageSource = event.getSource();
			if (damageSource == null || livingEntity == null)
				return;
			boolean helmet = livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.MAGICKA_SCALE_HELMET.get();
			boolean chestplate = livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == JerotesVillageItems.MAGICKA_SCALE_CHESTPLATE.get();
			boolean leggings = livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == JerotesVillageItems.MAGICKA_SCALE_LEGGINGS.get();
			boolean boots = livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() == JerotesVillageItems.MAGICKA_SCALE_BOOTS.get();
			if (!livingEntity.level().isClientSide()) {
			if (AttackFind.isFireDamage(damageSource) || AttackFind.isLightningDamage(damageSource) || AttackFind.isColdDamage(damageSource) || AttackFind.isWaterDamage(damageSource)) {
					float damages = 1;
						if (helmet) {
							damages -= 0.23f;
						}
						if (chestplate) {
							damages -= 0.23f;
						}
						if (leggings) {
							damages -= 0.23f;
						}
						if (boots) {
							damages -= 0.23f;
						}
					event.setAmount(event.getAmount() * damages);
				}
				if (AttackFind.MagicResistance(damageSource)) {
					if (helmet && chestplate && leggings && boots) {
						float newAmount = event.getAmount() - event.getAmount() * (0.25f + livingEntity.getHealth() <= livingEntity.getMaxHealth() / 2 ? 0.15f : 0f);
						if (Float.isNaN(newAmount) || Float.isInfinite(newAmount)) {
							newAmount = 0f;
						}
						event.setAmount(newAmount);
					}
				}
			}
		}
	}

	//元素纹饰
	@SubscribeEvent
	public static void ElementHurt(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			LivingEntity livingEntity = event.getEntity();
			DamageSource damageSource = event.getSource();
			if (damageSource == null || livingEntity == null)
				return;
			if (!livingEntity.level().isClientSide()) {
				float damages = 1;
				Optional<ArmorTrim> head = ArmorTrim.getTrim(livingEntity.level().registryAccess(), livingEntity.getItemBySlot(EquipmentSlot.HEAD)
						//1.20.4↑//
						//, true
				);
				if (head.isPresent()) {
					ArmorTrim armorTrim = head.get();
					//火
					if (armorTrim.material().is(livingEntity.level().registryAccess().registryOrThrow(Registries.TRIM_MATERIAL).getHolder(ResourceKey.create(Registries.TRIM_MATERIAL, new ResourceLocation(JerotesVillage.MODID, "fire_secretor_adhesive"))).get().key())) {
						if (AttackFind.isFireDamage(damageSource)) {
							damages -= 0.08f;
						}
					}
					//冰
					if (armorTrim.material().is(livingEntity.level().registryAccess().registryOrThrow(Registries.TRIM_MATERIAL).getHolder(ResourceKey.create(Registries.TRIM_MATERIAL, new ResourceLocation(JerotesVillage.MODID, "frost_yeti_hair"))).get().key())) {
						if (AttackFind.isColdDamage(damageSource)) {
							damages -= 0.08f;
						}
					}
					//雷
					if (armorTrim.material().is(livingEntity.level().registryAccess().registryOrThrow(Registries.TRIM_MATERIAL).getHolder(ResourceKey.create(Registries.TRIM_MATERIAL, new ResourceLocation(JerotesVillage.MODID, "lightning_worm_chitin"))).get().key())) {
						if (AttackFind.isLightningDamage(damageSource)) {
							damages -= 0.08f;
						}
					}
					//水
					if (armorTrim.material().is(livingEntity.level().registryAccess().registryOrThrow(Registries.TRIM_MATERIAL).getHolder(ResourceKey.create(Registries.TRIM_MATERIAL, new ResourceLocation(JerotesVillage.MODID, "malignasaur_teeth"))).get().key())) {
						if (AttackFind.isWaterDamage(damageSource)) {
							damages -= 0.08f;
						}
					}
				}
				Optional<ArmorTrim> chestplate = ArmorTrim.getTrim(livingEntity.level().registryAccess(), livingEntity.getItemBySlot(EquipmentSlot.CHEST)
						//1.20.4↑//
						//, true
				);
				if (chestplate.isPresent()) {
					ArmorTrim armorTrim = chestplate.get();
					//火
					if (armorTrim.material().is(livingEntity.level().registryAccess().registryOrThrow(Registries.TRIM_MATERIAL).getHolder(ResourceKey.create(Registries.TRIM_MATERIAL, new ResourceLocation(JerotesVillage.MODID, "fire_secretor_adhesive"))).get().key())) {
						if (AttackFind.isFireDamage(damageSource)) {
							damages -= 0.08f;
						}
					}
					//冰
					if (armorTrim.material().is(livingEntity.level().registryAccess().registryOrThrow(Registries.TRIM_MATERIAL).getHolder(ResourceKey.create(Registries.TRIM_MATERIAL, new ResourceLocation(JerotesVillage.MODID, "frost_yeti_hair"))).get().key())) {
						if (AttackFind.isColdDamage(damageSource)) {
							damages -= 0.08f;
						}
					}
					//雷
					if (armorTrim.material().is(livingEntity.level().registryAccess().registryOrThrow(Registries.TRIM_MATERIAL).getHolder(ResourceKey.create(Registries.TRIM_MATERIAL, new ResourceLocation(JerotesVillage.MODID, "lightning_worm_chitin"))).get().key())) {
						if (AttackFind.isLightningDamage(damageSource)) {
							damages -= 0.08f;
						}
					}
					//水
					if (armorTrim.material().is(livingEntity.level().registryAccess().registryOrThrow(Registries.TRIM_MATERIAL).getHolder(ResourceKey.create(Registries.TRIM_MATERIAL, new ResourceLocation(JerotesVillage.MODID, "malignasaur_teeth"))).get().key())) {
						if (AttackFind.isWaterDamage(damageSource)) {
							damages -= 0.08f;
						}
					}
				}
				Optional<ArmorTrim> leggings = ArmorTrim.getTrim(livingEntity.level().registryAccess(), livingEntity.getItemBySlot(EquipmentSlot.LEGS)
						//1.20.4↑//
						//, true
				);
				if (leggings.isPresent()) {
					ArmorTrim armorTrim = leggings.get();
					//火
					if (armorTrim.material().is(livingEntity.level().registryAccess().registryOrThrow(Registries.TRIM_MATERIAL).getHolder(ResourceKey.create(Registries.TRIM_MATERIAL, new ResourceLocation(JerotesVillage.MODID, "fire_secretor_adhesive"))).get().key())) {
						if (AttackFind.isFireDamage(damageSource)) {
							damages -= 0.08f;
						}
					}
					//冰
					if (armorTrim.material().is(livingEntity.level().registryAccess().registryOrThrow(Registries.TRIM_MATERIAL).getHolder(ResourceKey.create(Registries.TRIM_MATERIAL, new ResourceLocation(JerotesVillage.MODID, "frost_yeti_hair"))).get().key())) {
						if (AttackFind.isColdDamage(damageSource)) {
							damages -= 0.08f;
						}
					}
					//雷
					if (armorTrim.material().is(livingEntity.level().registryAccess().registryOrThrow(Registries.TRIM_MATERIAL).getHolder(ResourceKey.create(Registries.TRIM_MATERIAL, new ResourceLocation(JerotesVillage.MODID, "lightning_worm_chitin"))).get().key())) {
						if (AttackFind.isLightningDamage(damageSource)) {
							damages -= 0.08f;
						}
					}
					//水
					if (armorTrim.material().is(livingEntity.level().registryAccess().registryOrThrow(Registries.TRIM_MATERIAL).getHolder(ResourceKey.create(Registries.TRIM_MATERIAL, new ResourceLocation(JerotesVillage.MODID, "malignasaur_teeth"))).get().key())) {
						if (AttackFind.isWaterDamage(damageSource)) {
							damages -= 0.08f;
						}
					}
				}
				Optional<ArmorTrim> boots = ArmorTrim.getTrim(livingEntity.level().registryAccess(), livingEntity.getItemBySlot(EquipmentSlot.FEET)
						//1.20.4↑//
						//, true
				);
				if (boots.isPresent()) {
					ArmorTrim armorTrim = boots.get();
					//火
					if (armorTrim.material().is(livingEntity.level().registryAccess().registryOrThrow(Registries.TRIM_MATERIAL).getHolder(ResourceKey.create(Registries.TRIM_MATERIAL, new ResourceLocation(JerotesVillage.MODID, "fire_secretor_adhesive"))).get().key())) {
						if (AttackFind.isFireDamage(damageSource)) {
							damages -= 0.08f;
						}
					}
					//冰
					if (armorTrim.material().is(livingEntity.level().registryAccess().registryOrThrow(Registries.TRIM_MATERIAL).getHolder(ResourceKey.create(Registries.TRIM_MATERIAL, new ResourceLocation(JerotesVillage.MODID, "frost_yeti_hair"))).get().key())) {
						if (AttackFind.isColdDamage(damageSource)) {
							damages -= 0.08f;
						}
					}
					//雷
					if (armorTrim.material().is(livingEntity.level().registryAccess().registryOrThrow(Registries.TRIM_MATERIAL).getHolder(ResourceKey.create(Registries.TRIM_MATERIAL, new ResourceLocation(JerotesVillage.MODID, "lightning_worm_chitin"))).get().key())) {
						if (AttackFind.isLightningDamage(damageSource)) {
							damages -= 0.08f;
						}
					}
					//水
					if (armorTrim.material().is(livingEntity.level().registryAccess().registryOrThrow(Registries.TRIM_MATERIAL).getHolder(ResourceKey.create(Registries.TRIM_MATERIAL, new ResourceLocation(JerotesVillage.MODID, "malignasaur_teeth"))).get().key())) {
						if (AttackFind.isWaterDamage(damageSource)) {
							damages -= 0.08f;
						}
					}
				}
				event.setAmount(event.getAmount() * damages);
			}
		}
	}

	//蛇龙巫师套装
	@SubscribeEvent
	public static void SerponWizardHurt(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			LivingEntity livingEntity = event.getEntity();
			DamageSource damageSource = event.getSource();
			if (damageSource == null || livingEntity == null)
				return;
			boolean helmet = livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.SERPON_WIZARD_HELMET.get();
			boolean chestplate = livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == JerotesVillageItems.SERPON_WIZARD_CHESTPLATE.get();
			boolean leggings = livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == JerotesVillageItems.SERPON_WIZARD_LEGGINGS.get();
			boolean boots = livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() == JerotesVillageItems.SERPON_WIZARD_BOOTS.get();
			if (!livingEntity.level().isClientSide()) {
				if (AttackFind.MagicResistance(damageSource)) {
					float damages = 1;
					if (helmet) {
						damages -= 0.15f;
					}
					if (chestplate) {
						damages -= 0.15f;
					}
					if (leggings) {
						damages -= 0.15f;
					}
					if (boots) {
						damages -= 0.15f;
					}
					event.setAmount(event.getAmount() * damages);
				}
			}
		}
	}
	@SubscribeEvent
	public static void SerponWizardAttack(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			LivingEntity livingEntity = event.getEntity();
			DamageSource damageSource = event.getSource();
			if (!(damageSource.getEntity() instanceof LivingEntity living) || livingEntity == null)
				return;
			boolean helmet = living.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.SERPON_WIZARD_HELMET.get();
			boolean chestplate = living.getItemBySlot(EquipmentSlot.CHEST).getItem() == JerotesVillageItems.SERPON_WIZARD_CHESTPLATE.get();
			boolean leggings = living.getItemBySlot(EquipmentSlot.LEGS).getItem() == JerotesVillageItems.SERPON_WIZARD_LEGGINGS.get();
			boolean boots = living.getItemBySlot(EquipmentSlot.FEET).getItem() == JerotesVillageItems.SERPON_WIZARD_BOOTS.get();
			if (!living.level().isClientSide()) {
				if (AttackFind.isFireDamage(damageSource)) {
					float damages = 1;
					if (helmet) {
						damages += 0.25f;
					}
					if (chestplate) {
						damages += 0.25f;
					}
					if (leggings) {
						damages += 0.25f;
					}
					if (boots) {
						damages += 0.25f;
					}
					event.setAmount(event.getAmount() * damages);
				}
			}
		}
	}

	//火焰使徒套装
	@SubscribeEvent
	public static void FireApostleHurt(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			LivingEntity livingEntity = event.getEntity();
			DamageSource damageSource = event.getSource();
			if (damageSource == null || livingEntity == null)
				return;
			boolean helmet = livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.FIRE_APOSTLE_HELMET.get();
			boolean chestplate = livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == JerotesVillageItems.FIRE_APOSTLE_CHESTPLATE.get();
			boolean leggings = livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == JerotesVillageItems.FIRE_APOSTLE_LEGGINGS.get();
			boolean boots = livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() == JerotesVillageItems.FIRE_APOSTLE_BOOTS.get();
			if (!livingEntity.level().isClientSide()) {
				if (AttackFind.isFireDamage(damageSource)) {
					float damages = 1;
					if (helmet) {
						damages -= 0.20f;
					}
					if (chestplate) {
						damages -= 0.20f;
					}
					if (leggings) {
						damages -= 0.20f;
					}
					if (boots) {
						damages -= 0.20f;
					}
					event.setAmount(event.getAmount() * damages);
				}
			}
		}
	}
	@SubscribeEvent
	public static void FireApostleAttack(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			LivingEntity livingEntity = event.getEntity();
			DamageSource damageSource = event.getSource();
			if (!(damageSource.getEntity() instanceof LivingEntity living) || livingEntity == null)
				return;
			boolean helmet = living.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.FIRE_APOSTLE_HELMET.get();
			boolean chestplate = living.getItemBySlot(EquipmentSlot.CHEST).getItem() == JerotesVillageItems.FIRE_APOSTLE_CHESTPLATE.get();
			boolean leggings = living.getItemBySlot(EquipmentSlot.LEGS).getItem() == JerotesVillageItems.FIRE_APOSTLE_LEGGINGS.get();
			boolean boots = living.getItemBySlot(EquipmentSlot.FEET).getItem() == JerotesVillageItems.FIRE_APOSTLE_BOOTS.get();
			if (!living.level().isClientSide()) {
				if (AttackFind.isFireDamage(damageSource)) {
					float damages = 1;
					if (helmet) {
						damages += 0.3f;
					}
					if (chestplate) {
						damages += 0.3f;
					}
					if (leggings) {
						damages += 0.3f;
					}
					if (boots) {
						damages += 0.3f;
					}
					event.setAmount(event.getAmount() * damages);
				}
			}
		}
	}

	//莫厄金属套装
	@SubscribeEvent
	public static void MerorMetalHurt(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			LivingEntity livingEntity = event.getEntity();
			DamageSource damageSource = event.getSource();
			if (damageSource == null || livingEntity == null)
				return;
			boolean helmet = livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.MEROR_METAL_HELMET.get()
					|| livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.MEROR_STANDARD_HELMET.get();
			boolean chestplate = livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == JerotesVillageItems.MEROR_METAL_CHESTPLATE.get()
					|| livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == JerotesVillageItems.MEROR_STANDARD_CHESTPLATE.get();
			boolean warBeast = livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == JerotesVillageItems.MEROR_METAL_WAR_BEAST_ARMOR.get();
			boolean giantBeast = livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == JerotesVillageItems.MEROR_METAL_GIANT_BEAST_ARMOR.get();
			boolean leggings = livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == JerotesVillageItems.MEROR_METAL_LEGGINGS.get()
					|| livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == JerotesVillageItems.MEROR_STANDARD_LEGGINGS.get();
			boolean boots = livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() == JerotesVillageItems.MEROR_METAL_BOOTS.get()
					|| livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() == JerotesVillageItems.MEROR_STANDARD_BOOTS.get();
			if (!livingEntity.level().isClientSide()) {
				if (!EntityAndItemFind.isMerorAttack(damageSource) && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
					float damages = 1;
					if (helmet) {
						damages -= 0.12f;
					}
					if (chestplate) {
						damages -= 0.12f;
					}
					if (warBeast) {
						damages -= 0.36f;
					}
					if (giantBeast) {
						damages -= 0.36f;
					}
					if (leggings) {
						damages -= 0.12f;
					}
					if (boots) {
						damages -= 0.12f;
					}
					event.setAmount(event.getAmount() * damages);
				}
			}
		}
	}

	//蛇龙强者套装
	@SubscribeEvent
	public static void SerponPowerhouseHurt(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			LivingEntity livingEntity = event.getEntity();
			DamageSource damageSource = event.getSource();
			if (damageSource == null || livingEntity == null)
				return;
			if (!livingEntity.hasEffect(JerotesVillageMobEffects.SERPON_CREDENTIAL.get()))
				return;
			if (!(damageSource.getEntity() instanceof LivingEntity))
				return;
			boolean helmet = livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.SERPON_POWERHOUSE_HELMET.get();
			boolean chestplate = livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == JerotesVillageItems.SERPON_POWERHOUSE_CHESTPLATE.get();
			boolean leggings = livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == JerotesVillageItems.SERPON_POWERHOUSE_LEGGINGS.get();
			boolean boots = livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() == JerotesVillageItems.SERPON_POWERHOUSE_BOOTS.get();
			if (!livingEntity.level().isClientSide()) {
				float damages = 1;
				if (helmet) {
					damages -= 0.10f;
				}
				if (chestplate) {
					damages -= 0.10f;
				}
				if (leggings) {
					damages -= 0.10f;
				}
				if (boots) {
					damages -= 0.10f;
				}
				if (helmet && chestplate && leggings && boots) {
					if (!livingEntity.level().isClientSide()) {
						livingEntity.addEffect(new MobEffectInstance((JerotesVillageMobEffects.ABUNDANT_COURAGE.get()), (int) (event.getAmount() * 20), Math.min(4, (int) (event.getAmount() / 8))), livingEntity);
					}
				}
				event.setAmount(event.getAmount() * damages);
			}
		}
	}

	//巨怪套装
	@SubscribeEvent
	public static void GiantMonsterHurt(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			LivingEntity livingEntity = event.getEntity();
			DamageSource damageSource = event.getSource();
			if (damageSource == null || livingEntity == null)
				return;
			boolean helmet = livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.GIANT_MONSTER_HELMET.get()
					|| livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.GIANT_MONSTER_HORNED_HELMET.get();
			boolean chestplate = livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == JerotesVillageItems.GIANT_MONSTER_CHESTPLATE.get();
			boolean leggings = livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == JerotesVillageItems.GIANT_MONSTER_LEGGINGS.get();
			boolean boots = livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() == JerotesVillageItems.GIANT_MONSTER_BOOTS.get();
			if (!livingEntity.level().isClientSide()) {
				if (AttackFind.isColdDamage(damageSource)) {
					float damages = 1;
					if (helmet) {
						damages -= 0.16f;
					}
					if (chestplate) {
						damages -= 0.16f;
					}
					if (leggings) {
						damages -= 0.16f;
					}
					if (boots) {
						damages -= 0.16f;
					}
					event.setAmount(event.getAmount() * damages);
				}
			}
		}
	}

	//恶怨长袍
	@SubscribeEvent
	public static void BlamerRobeTick(LivingEvent.LivingTickEvent event) {
		LivingEntity livingEntity = event.getEntity();
		if (livingEntity == null)
			return;
		if (livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == JerotesVillageItems.BLAMER_ROBE.get()) {
			if (!livingEntity.level().isClientSide()) {
				livingEntity.addEffect(new MobEffectInstance((JerotesMobEffects.CLOAKING.get()), 25, 0, false, false), livingEntity);
			}
			if (Main.getJerotesPersistentData(livingEntity).get("jerotes_cloaking_shift_key_down") != null
					&& !Main.getJerotesPersistentData(livingEntity).getBoolean("jerotes_cloaking_shift_key_down")
					&& livingEntity.isShiftKeyDown()) {
				for (int i = 0; i < 7; ++i) {
					double d = livingEntity.getRandom().nextGaussian() * 0.02;
					double d2 = livingEntity.getRandom().nextGaussian() * 0.02;
					double d3 = livingEntity.getRandom().nextGaussian() * 0.02;
					livingEntity.level().addParticle(JerotesVillageParticleTypes.BLAMER_SOUL.get(), livingEntity.getRandomX(1.0), livingEntity.getRandomY() + 0.5, livingEntity.getRandomZ(1.0), d, d2, d3);
				}
			}
		}
	}

	//奴制监督者套装
	@SubscribeEvent
	public static void SlaverySupervisorDeath(LivingDeathEvent event) {
		LivingEntity livingEntity = event.getEntity();
		DamageSource damageSource = event.getSource();
		Entity entity = event.getSource().getEntity();
		if (entity instanceof Player player && livingEntity != null && damageSource != null) {
			if (livingEntity.level().isClientSide()) {
				return;
			}
			if (livingEntity instanceof AbstractIllager || livingEntity instanceof AbstractVillager) {
				boolean helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.SLAVERY_SUPERVISOR_HELMET.get();
				boolean chestplate = player.getItemBySlot(EquipmentSlot.CHEST).getItem() == JerotesVillageItems.SLAVERY_SUPERVISOR_CHESTPLATE.get();
				boolean leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem() == JerotesVillageItems.SLAVERY_SUPERVISOR_LEGGINGS.get();
				boolean boots = player.getItemBySlot(EquipmentSlot.FEET).getItem() == JerotesVillageItems.SLAVERY_SUPERVISOR_BOOTS.get();
				if (!(helmet || chestplate || leggings || boots)) return;
				if (livingEntity.isBaby()) return;

				int chance = 100;
				if (helmet) {
					chance -= 18;
				}
				if (chestplate) {
					chance -= 18;
				}
				if (leggings) {
					chance -= 18;
				}
				if (boots) {
					chance -= 18;
				}

				//boss和精英
				if (EntityAndItemFind.isBoss(livingEntity.getType())) {
					chance -= 16;
				}
				else if (livingEntity instanceof EliteEntity) {
					chance -= 10;
				}
				//村民等级
				if (livingEntity instanceof VillagerDataHolder villagerDataHolder) {
					chance -= villagerDataHolder.getVillagerData().getLevel() * 2;
				}

				//boss和精英
				int count = EntityAndItemFind.isBoss(livingEntity.getType()) ? 10 : livingEntity instanceof EliteEntity eliteEntity ? 3 : 1;
				//村民等级
				if (livingEntity instanceof VillagerDataHolder villagerDataHolder) {
					count += villagerDataHolder.getVillagerData().getLevel();
				}
				//最低4
				int chance2 = Math.max(4, chance);
				int reward = player.getRandom().nextInt(chance2);
				ItemStack itemStack2 = new ItemStack(Items.AIR, 1);
				if (reward <= 4) {
					itemStack2 = new ItemStack(Items.EMERALD, 2 * count);
				}
				else if (reward <= 12) {
					itemStack2 = new ItemStack(Items.EMERALD, count);
				}

				if (reward <= 12) {
					ItemEntity itemEntity = livingEntity.spawnAtLocation(itemStack2, 1);
					if (itemEntity != null) {
						itemEntity.setPos(livingEntity.getX(), livingEntity.getEyeY(), livingEntity.getZ());
					}
				}
			}
		}
	}

	//Buff
	@SubscribeEvent
	public static void BuffAboutTick(LivingEvent.LivingTickEvent event) {
		LivingEntity livingEntity = event.getEntity();
		if (livingEntity == null)
			return;
		if (livingEntity.tickCount % 20 == 0) {
			//莫厄赏金猎人套装
			if (livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.MEROR_BOUNTY_HUNTER_HELMET.get()) {
				if (!livingEntity.level().isClientSide()) {
					livingEntity.addEffect(new MobEffectInstance((MobEffects.NIGHT_VISION), 245, 0, false, false), livingEntity);
					livingEntity.removeEffect(MobEffects.POISON);
					livingEntity.removeEffect(JerotesMobEffects.DEADLY_POISON.get());
					livingEntity.removeEffect(JerotesVillageMobEffects.WILDERNESS_GIANT_POISON.get());
				}
			}
			//镇山套装
			{
				boolean helmet = livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.MOUNTAIN_REALM_HELMET.get();
				boolean chestplate = livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == JerotesVillageItems.MOUNTAIN_REALM_CHESTPLATE.get();
				boolean leggings = livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == JerotesVillageItems.MOUNTAIN_REALM_LEGGINGS.get();
				boolean boots = livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() == JerotesVillageItems.MOUNTAIN_REALM_BOOTS.get();
				if (helmet && chestplate && leggings && boots) {
					if (!livingEntity.level().isClientSide()) {
						livingEntity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
						livingEntity.removeEffect(MobEffects.WEAKNESS);
					}
				}
			}
		}
	}
	@SubscribeEvent
	public static void BuffAboutAddEffect(MobEffectEvent event) {
		LivingEntity livingEntity = event.getEntity();
		MobEffectInstance mobEffectInstance = event.getEffectInstance();
		if (livingEntity == null)
			return;
		if (mobEffectInstance == null)
			return;
		//莫厄赏金猎人套装
		if (livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.MEROR_BOUNTY_HUNTER_HELMET.get()) {
			if (mobEffectInstance.getEffect() == JerotesMobEffects.DEADLY_POISON.get() ||
					mobEffectInstance.getEffect() == MobEffects.POISON ||
					mobEffectInstance.getEffect() == JerotesVillageMobEffects.WILDERNESS_GIANT_POISON.get())
				event.setResult(Event.Result.DENY);
		}
		//镇山套装
		{
			boolean helmet = livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.MOUNTAIN_REALM_HELMET.get();
			boolean chestplate = livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == JerotesVillageItems.MOUNTAIN_REALM_CHESTPLATE.get();
			boolean leggings = livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == JerotesVillageItems.MOUNTAIN_REALM_LEGGINGS.get();
			boolean boots = livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() == JerotesVillageItems.MOUNTAIN_REALM_BOOTS.get();
			if (helmet && chestplate && leggings && boots) {
				if (mobEffectInstance.getEffect() == MobEffects.MOVEMENT_SLOWDOWN ||
						mobEffectInstance.getEffect() == MobEffects.WEAKNESS)
					event.setResult(Event.Result.DENY);
			}
		}
	}

	//莫厄制式套装
	@SubscribeEvent
	public static void MerorStandardTick(LivingEvent.LivingTickEvent event) {
		LivingEntity livingEntity = event.getEntity();
		if (livingEntity == null)
			return;
		boolean helmet = livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.MEROR_STANDARD_HELMET.get();
		boolean chestplate = livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == JerotesVillageItems.MEROR_STANDARD_CHESTPLATE.get();
		boolean leggings = livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == JerotesVillageItems.MEROR_STANDARD_LEGGINGS.get();
		boolean boots = livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() == JerotesVillageItems.MEROR_STANDARD_BOOTS.get();
		if (!livingEntity.level().isClientSide()) {
			if (helmet) {
				livingEntity.addEffect(new MobEffectInstance((MobEffects.NIGHT_VISION), 245, 0, false, false), livingEntity);
				livingEntity.addEffect(new MobEffectInstance((JerotesMobEffects.TRUESIGHT.get()), 245, 0, false, false), livingEntity);
				if (livingEntity.isInWater()) {
					livingEntity.addEffect(new MobEffectInstance((MobEffects.WATER_BREATHING), 245, 0, false, false), livingEntity);
				}
//				List<Mob> list = livingEntity.level().getEntitiesOfClass(Mob.class, livingEntity.getBoundingBox().inflate(64.0, 64.0, 64.0));
//				double d = Double.MAX_VALUE;
//				for (Mob livingEntityControl : list) {
//					if (livingEntityControl == null) continue;
//					if ((livingEntity.distanceToSqr(livingEntityControl)) > d) continue;
//					if (livingEntityControl == livingEntity || (livingEntityControl.getTarget() != livingEntity && livingEntityControl.getLastHurtMob() != livingEntity && livingEntity.getLastHurtByMob() != livingEntityControl))
//						continue;
//					if (!livingEntityControl.isInvisible())
//						continue;
//					if (livingEntityControl.getItemBySlot(EquipmentSlot.LEGS).getItem() == JerotesVillageItems.MEROR_STANDARD_LEGGINGS.get())
//						continue;
//					if (!livingEntityControl.level().isClientSide()) {
//						livingEntityControl.addEffect(new MobEffectInstance((MobEffects.GLOWING), 20, 0, false, false));
//					}
//				}
			}
			if (chestplate) {
				livingEntity.addEffect(new MobEffectInstance((MobEffects.DAMAGE_BOOST), 65, 0, false, false), livingEntity);
			}
			if (leggings) {
				if (livingEntity.isShiftKeyDown()){
					livingEntity.addEffect(new MobEffectInstance((MobEffects.INVISIBILITY), 65, 0, false, false), livingEntity);
				}
				if (livingEntity.isSprinting()){
					livingEntity.addEffect(new MobEffectInstance((MobEffects.MOVEMENT_SPEED), 25, 1, false, false), livingEntity);
					livingEntity.addEffect(new MobEffectInstance((MobEffects.JUMP), 25, 1, false, false), livingEntity);
				}
			}
		}
	}
	@SubscribeEvent
	public static void MerorStandardHurt(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			DamageSource damageSource = event.getSource();
			LivingEntity livingEntity = event.getEntity();
			Entity attackBy = event.getSource().getEntity();
			if (damageSource == null || livingEntity == null)
				return;
			boolean helmet = livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.MEROR_STANDARD_HELMET.get();
			boolean chestplate = livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == JerotesVillageItems.MEROR_STANDARD_CHESTPLATE.get();
			boolean leggings = livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == JerotesVillageItems.MEROR_STANDARD_LEGGINGS.get();
			boolean boots = livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() == JerotesVillageItems.MEROR_STANDARD_BOOTS.get();
			if (!livingEntity.level().isClientSide()) {
				float amout = event.getAmount();
				if (helmet) {
					if (damageSource.is(DamageTypes.IN_WALL)){
						amout -= 8;
					}
				}
				if (chestplate) {
					if (!damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
						amout -= 4;
					}
					if (AttackFind.MagicResistance(damageSource) && attackBy instanceof LivingEntity livingAttackBy){
						livingAttackBy.addEffect(new MobEffectInstance((MobEffects.MOVEMENT_SLOWDOWN), 240, 1, false, false), livingEntity);
					}
				}
				if (boots) {
					if (damageSource.is(DamageTypeTags.IS_FALL)){
						event.setAmount(0);
						event.setCanceled(true);
					}
				}
				if (helmet && chestplate && leggings && boots) {
					amout -= event.getAmount() * 0.30f;
				}
				if (amout < 0 && event.getAmount() >= 0) {
					amout = 0;
				}
				event.setAmount(amout);
			}
		}
	}
	@SubscribeEvent
	public static void MerorStandardTaczHurt(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			DamageSource damageSource = event.getSource();
			LivingEntity livingEntity = event.getEntity();
			Entity attackBy = event.getSource().getEntity();
			if (damageSource == null || livingEntity == null)
				return;
			boolean helmet = livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.MEROR_STANDARD_HELMET.get();
			boolean chestplate = livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == JerotesVillageItems.MEROR_STANDARD_CHESTPLATE.get();
			boolean leggings = livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == JerotesVillageItems.MEROR_STANDARD_LEGGINGS.get();
			boolean boots = livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() == JerotesVillageItems.MEROR_STANDARD_BOOTS.get();
			if (ModList.get().isLoaded("tacz")) {
				Entity directEntity = damageSource.getDirectEntity();
				if (directEntity != null) {
					try {
						Class<?> kineticBulletClass = Class.forName("com.tacz.entity.EntityKineticBullet");
						if (kineticBulletClass.isInstance(directEntity)) {
							float amout = event.getAmount();
							//每件降低1点伤害 以及百分之20的伤害
							float damages = 1;
							if (helmet) {
								amout -= 1;
								damages -= 0.20f;
							}
							if (chestplate) {
								amout -= 1;
								damages -= 0.20f;
							}
							if (leggings) {
								amout -= 1;
								damages -= 0.20f;
							}
							if (boots) {
								amout -= 1;
								damages -= 0.20f;
							}
							if (amout < 0 && event.getAmount() >= 0) {
								amout = 0;
							}
							event.setAmount(amout * damages);
						}
					} catch (ClassNotFoundException e) {
					}
				}
			}
		}
	}
	@SubscribeEvent
	public static void MerorStandardTaczAttack(LivingAttackEvent event) {
		if (event == null || event.getEntity() == null) {
			return;
		}
		DamageSource damageSource = event.getSource();
		LivingEntity livingEntity = event.getEntity();
		if (damageSource == null || livingEntity == null) {
			return;
		}
		boolean helmet = livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.MEROR_STANDARD_HELMET.get();
		boolean chestplate = livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == JerotesVillageItems.MEROR_STANDARD_CHESTPLATE.get();
		boolean leggings = livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == JerotesVillageItems.MEROR_STANDARD_LEGGINGS.get();
		boolean boots = livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() == JerotesVillageItems.MEROR_STANDARD_BOOTS.get();
		if ((helmet || chestplate || leggings || boots) && ModList.get().isLoaded("tacz")) {
			Entity directEntity = damageSource.getDirectEntity();
			if (directEntity != null) {
				try {
					Class<?> kineticBulletClass = Class.forName("com.tacz.entity.EntityKineticBullet");
					if (kineticBulletClass.isInstance(directEntity)) {
						float amount = event.getAmount();
						if (helmet) {
							amount -= 1;
						}
						if (chestplate) {
							amount -= 1;
						}
						if (leggings) {
							amount -= 1;
						}
						if (boots) {
							amount -= 1;
						}
						if (amount <= 0) {
							event.setCanceled(true);
						}
					}
				} catch (ClassNotFoundException e) {
				}
			}
		}
	}
	@SubscribeEvent
	public static void MerorStandardAttack(LivingAttackEvent event) {
		if (event != null && event.getEntity() != null) {
			DamageSource damageSource = event.getSource();
			LivingEntity livingEntity = event.getEntity();
			Entity attackBy = event.getSource().getEntity();
			if (damageSource == null || livingEntity == null)
				return;
			boolean helmet = livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.MEROR_STANDARD_HELMET.get();
			boolean chestplate = livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == JerotesVillageItems.MEROR_STANDARD_CHESTPLATE.get();
			boolean leggings = livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == JerotesVillageItems.MEROR_STANDARD_LEGGINGS.get();
			boolean boots = livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() == JerotesVillageItems.MEROR_STANDARD_BOOTS.get();
			if (!livingEntity.level().isClientSide()) {
				float amout = event.getAmount();
				if (helmet) {
					if (damageSource.is(DamageTypes.IN_WALL)){
						if (amout <= 8 || chestplate && amout <= 12) {
							event.setCanceled(true);
						}
					}
				}
				if (chestplate) {
					if (!damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
						if (amout <= 4) {
							event.setCanceled(true);
						}
					}
				}
				if (boots) {
					if (damageSource.is(DamageTypeTags.IS_FALL)){
						event.setCanceled(true);
					}
				}
			}
		}
	}


	//镇山套装
	@SubscribeEvent
	public static void MountainRealmTick(LivingEvent.LivingTickEvent event) {
		LivingEntity livingEntity = event.getEntity();
		if (livingEntity == null)
			return;
		if (livingEntity instanceof Player player && player.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.MOUNTAIN_REALM_HELMET.get()) {
			if (Main.getTargetedEntity(player, 32) != null &&
					Main.getTargetedEntity(player, 32) instanceof LivingEntity livingTarget &&
					!AttackFind.FindCanNotAttack(player, livingTarget)) {
				if (!livingEntity.level().isClientSide()) {
					livingTarget.addEffect(new MobEffectInstance((JerotesMobEffects.ABACK.get()), 5, 0), player);
				}
			}
		}
		if (livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == JerotesVillageItems.MOUNTAIN_REALM_LEGGINGS.get()) {
			if (livingEntity.isSprinting() && livingEntity.tickCount % 5 == 0) {
				List<LivingEntity> list = livingEntity.level().getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(6.0, 6.0, 6.0));
				double d = Double.MAX_VALUE;
				for (LivingEntity livingEntityControl : list) {
					if (livingEntityControl == null) continue;
					if (livingEntityControl == livingEntity) continue;
					if (AttackFind.FindCanNotAttack(livingEntity, livingEntityControl)) continue;
					if ((livingEntity.distanceToSqr(livingEntityControl)) > d) continue;
					if (livingEntityControl.getItemBySlot(EquipmentSlot.LEGS).getItem() == JerotesVillageItems.MOUNTAIN_REALM_LEGGINGS.get())
						continue;
					if (!livingEntityControl.level().isClientSide()) {
						livingEntityControl.addEffect(new MobEffectInstance((MobEffects.WEAKNESS), 60, 1), livingEntity);
					}
				}
			}
		}
	}
	@SubscribeEvent
	public static void MountainRealmAttackTo(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			DamageSource damageSource = event.getSource();
			LivingEntity livingEntity = event.getEntity();
			Entity attackBy = event.getSource().getEntity();
			if (damageSource == null || livingEntity == null || !(attackBy instanceof LivingEntity attacker))
				return;
			boolean chestplate = attacker.getItemBySlot(EquipmentSlot.CHEST).getItem() == JerotesVillageItems.MOUNTAIN_REALM_CHESTPLATE.get() && !(attacker instanceof TyrantOfTheMountainRealmEntity);
			if (chestplate && AttackFind.isMeleeDamage(damageSource)) {
				double d;
				if (livingEntity.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
					d = livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
				}
				else {
					d = 0.0;
				}
				double d2 = Math.max(0, 1 - d) + 0.3f;
				if (!EntityAndItemFind.isNoSpecialKnockback(livingEntity.getType()) &&
						(Main.mobSizeSmall(livingEntity) || Main.mobSizeMedium(livingEntity) || Main.mobSizeLarge(livingEntity))) {
					livingEntity.setOnGround(false);
					livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(
							-((attacker.getX() - livingEntity.getX()) * d2 / 3) * (0.7 + 0.5f / 3),
							-(attacker.getY() - livingEntity.getY()) * 1.5 * d2 * (0.7 + 0.5f / 3),
							-((attacker.getZ() - livingEntity.getZ()) * d2 / 3) * (0.7 + 0.5f / 3)
					));
				}
			}
		}
	}
}

