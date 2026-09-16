package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.*;
import com.jerotes.jerotes.util.Main;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.List;

public class GlaiveofSerpentDominater extends ItemToolBaseTwoHandedSword {
	public GlaiveofSerpentDominater() {
		super(new Tier() {
			public int getUses() {
				return 5000;
			}

			public float getSpeed() {
				return 12f;
			}

			public float getAttackDamageBonus() {
				return 18f;
			}

			public int getLevel() {
				return 4;
			}

			public int getEnchantmentValue() {
				return 28;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(Items.OBSIDIAN));
			}
		}, -1, 1.2f - 4f, new Properties().fireResistant().rarity(Rarity.EPIC));
	}

	@Override
	public void blockUse(Entity self, Entity attackBy, DamageSource damageSource) {
		if (self instanceof LivingEntity livingSelf && attackBy != self && self.isAlive() && Main.getJerotesPersistentData(livingSelf).getDouble("jerotesvillage_start_glaive_of_serpent_god_attack_cooldown") <= 0) {
			float f = livingSelf.getYRot();
			float f2 = livingSelf.getXRot();
			float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
			float f4 = -Mth.sin(f2 * 0.017453292f);
			float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
			float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
			float f7 = 4.0f;
			livingSelf.push(f3 * (f7 / f6), f4 * (f7 / f6), f5 * (f7 / f6));
			if (!livingSelf.level().isClientSide()) {
				Main.getJerotesPersistentData(livingSelf).putDouble("jerotesvillage_start_glaive_of_serpent_god_attack", 6);
				Main.getJerotesPersistentData(livingSelf).putDouble("jerotesvillage_start_glaive_of_serpent_god_attack_cooldown", 40);
			}
		}
	}

	@Override
	public int getBlockReduction() {
		return 75;
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
