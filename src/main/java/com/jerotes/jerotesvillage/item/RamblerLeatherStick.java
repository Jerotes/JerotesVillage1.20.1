package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotes.item.Tool.*;
import com.jerotes.jerotes.util.Main;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class RamblerLeatherStick extends ItemToolBaseHammer {
	public RamblerLeatherStick() {
		super(new Tier() {
			public int getUses() {
				return 250;
			}

			public float getSpeed() {
				return 4f;
			}

			public float getAttackDamageBonus() {
				return 4f;
			}

			public int getLevel() {
				return 2;
			}

			public int getEnchantmentValue() {
				return 12;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(JerotesVillageItems.RAMBLER_LEATHER.get()));
			}
		}, -1, 1.6f - 4f, new Properties());
	}

	@Override
	public void attackUse(Entity self, Entity attackTo) {
		if (attackTo instanceof LivingEntity livingEntity && livingEntity.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
			double d2 = livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
			double d3 = Math.max(0.0, 1.0 - d2);
			if (self.onGround() && attackTo.onGround() && (Main.mobSizeSmall(attackTo) || Main.mobSizeMedium(attackTo))) {
				livingEntity.setOnGround(false);
				livingEntity.push(0.0, 0.6 * d3, 0.0);
			}
			if (!attackTo.level().isClientSide && Math.random() < 0.28) {
				livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 60, 1));
				livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
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
