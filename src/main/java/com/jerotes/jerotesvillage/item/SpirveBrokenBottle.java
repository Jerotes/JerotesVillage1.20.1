package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.List;

public class SpirveBrokenBottle extends ItemToolBaseSword {
	public SpirveBrokenBottle() {
		super(new Tier() {
			public int getUses() {
				return 32;
			}

			public float getSpeed() {
				return 2f;
			}

			public float getAttackDamageBonus() {
				return 5f;
			}

			public int getLevel() {
				return 0;
			}

			public int getEnchantmentValue() {
				return 15;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(Items.GLASS_BOTTLE));
			}
		}, -1, 1f - 4f, new Properties().rarity(Rarity.UNCOMMON));
	}

	@Override
	public void attackUse(Entity self, Entity attackTo) {
		if (!self.level().isClientSide && attackTo instanceof LivingEntity livingSelf) {
			LivingEntity entityBottle = livingSelf;
			if (Math.random() > 0.92 && self instanceof LivingEntity livingBottle) {
				entityBottle = livingBottle;
			}
			if (!entityBottle.level().isClientSide) {
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 60, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 60, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 60, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.JUMP, 60, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 60, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 60, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 60, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 60, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 60, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.HUNGER, 60, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 60, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 60, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.SATURATION, 60, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.GLOWING, 60, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 60, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.LUCK, 60, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.UNLUCK, 60, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 60, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, 60, 1));
				}
				if (Math.random() <= 0.02) {
					entityBottle.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 60, 1));
				}
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
	}

	public MutableComponent getDisplayName() {
		return Component.translatable(this.getDescriptionId() + ".desc");
	}
}
