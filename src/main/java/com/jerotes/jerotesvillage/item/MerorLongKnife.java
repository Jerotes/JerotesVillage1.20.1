package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.item.Tool.ItemToolBaseTwoHandedSword;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Objects;

public class MerorLongKnife extends ItemToolBaseTwoHandedSword {
	public MerorLongKnife() {
		super(new Tier() {
			public int getUses() {
				return 18800;
			}

			public float getSpeed() {
				return 18f;
			}

			public float getAttackDamageBonus() {
				return 23.5f;
			}

			public int getLevel() {
				return 5;
			}

			public int getEnchantmentValue() {
				return 28;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(JerotesVillageItems.REFINE_MEROR_METAL_INGOT.get()));
			}
		}, -1, 1.6f - 4f, new Properties().fireResistant().rarity(Rarity.EPIC));
	}

	@Override
	public int getBlockReduction() {
		return 80;
	}

	@Override
	public void blockUse(Entity self, Entity attackBy, DamageSource damageSource) {
		if (attackBy instanceof LivingEntity living && attackBy != self) {
			if (self.level() instanceof ServerLevel serverLevel) {
				Vec3 vec3 = self.getEyePosition();
				Vec3 vec32 = attackBy.getEyePosition().subtract(vec3);
				Vec3 vec33 = vec32.normalize();
				for (int i = 1; i < Mth.floor(vec32.length()) * 6 + 1; ++i) {
					Vec3 vec34 = vec3.add(vec33.scale(i / 6f));
					serverLevel.sendParticles(JerotesVillageParticleTypes.MEROR.get(), vec34.x, vec34.y, vec34.z, 1, 0.0, 0.0, 0.0, 0.0);
				}
			}
			if (!attackBy.level().isClientSide) {
				living.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40, 0), self);
			}
		}
	}
//
//	@Override
//	public boolean onEntitySwing(ItemStack itemstack, LivingEntity livingEntity) {
//		spawnSwordEnergy(livingEntity);
//		return super.onEntitySwing(itemstack, livingEntity);
//	}
//
//	public static void spawnSwordEnergy(LivingEntity entity) {
//	}
//

	@Override
	public void attackUse(Entity self, Entity attackTo) {
		if (attackTo instanceof LivingEntity livingEntity) {
			if (!(livingEntity.hasEffect(JerotesMobEffects.BLEEDING.get()))) {
				if (!attackTo.level().isClientSide) {
					livingEntity.addEffect(new MobEffectInstance(JerotesMobEffects.BLEEDING.get(), 60, 0, false, false), self);
				}
			}
			else {
				int level = Objects.requireNonNull(livingEntity.getEffect(JerotesMobEffects.BLEEDING.get())).getAmplifier() + 1;
				int time = Objects.requireNonNull(livingEntity.getEffect(JerotesMobEffects.BLEEDING.get())).getDuration();
				int levelAdd = level;
				int timeAdd = time + 20;
				if (timeAdd > 120) {
					levelAdd = level + 1;
					timeAdd = 60;
				}
				if (levelAdd > 5) {
					levelAdd = 5;
				}

				if (!attackTo.level().isClientSide) {
					livingEntity.addEffect(new MobEffectInstance(JerotesMobEffects.BLEEDING.get(), timeAdd, levelAdd, false, false), self);
				}
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName_0().withStyle(ChatFormatting.GRAY));
		list.add(this.getDisplayName_1().withStyle(ChatFormatting.GRAY));
	}

	public MutableComponent getDisplayName_0() {
		return Component.translatable(this.getDescriptionId() + ".desc_0");
	}
	public MutableComponent getDisplayName_1() {
		return Component.translatable(this.getDescriptionId() + ".desc_1");
	}
}
