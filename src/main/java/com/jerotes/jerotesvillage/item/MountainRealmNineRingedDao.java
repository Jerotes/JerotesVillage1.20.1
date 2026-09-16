package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.item.Tool.ItemToolBaseTwoHandedSword;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.List;

public class MountainRealmNineRingedDao extends ItemToolBaseTwoHandedSword {
	public MountainRealmNineRingedDao() {
		super(new Tier() {
			public int getUses() {
				return 2000;
			}

			public float getSpeed() {
				return 10f;
			}

			public float getAttackDamageBonus() {
				return 17f;
			}

			public int getLevel() {
				return 4;
			}

			public int getEnchantmentValue() {
				return 22;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(Items.GOLD_INGOT));
			}
		}, -1, 1.1f - 4f, new Properties().fireResistant().rarity(Rarity.EPIC));
	}

	@Override
	public void attackUse(Entity self, Entity attackTo) {
		if (attackTo instanceof LivingEntity livingEntity && self instanceof LivingEntity livingSelf) {
			if (livingSelf.fallDistance > 0.05f && !livingSelf.isFallFlying()) {
				self.playSound(SoundEvents.BELL_RESONATE);
				if (!attackTo.level().isClientSide()) {
					livingEntity.addEffect(new MobEffectInstance(JerotesMobEffects.ABACK.get(), 60, 1), self);
					livingEntity.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 20, 0), self);
					livingSelf.addEffect(new MobEffectInstance(JerotesVillageMobEffects.RAMPANT.get(), 60, 0), self);
				}
			}
		}
	}

	public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
		return true;
	}

	@Override
	public int getBlockReduction() {
		return 75;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(this.getDisplayName_0().withStyle(ChatFormatting.GRAY));
	}
	public MutableComponent getDisplayName_0() {
		return Component.translatable(this.getDescriptionId() + ".desc_0");
	}
}
