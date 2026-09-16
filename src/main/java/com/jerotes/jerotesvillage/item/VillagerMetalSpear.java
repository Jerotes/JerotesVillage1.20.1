package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.entity.Shoot.Arrow.BaseJavelinEntity;
import com.jerotes.jerotes.item.Tool.ItemToolBaseJavelin;
import com.jerotes.jerotes.item.Tool.ItemToolBaseSpear;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.ThrownJavelinVillagerMetalEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class VillagerMetalSpear extends ItemToolBaseSpear {
	public VillagerMetalSpear() {
		super(new Tier() {
				  public int getUses() {
					  return 450;
				  }

				  public float getSpeed() {
					  return 6f;
				  }

				  public float getAttackDamageBonus() {
					  return 3.5f;
				  }

				  public int getLevel() {
					  return 2;
				  }

				  public int getEnchantmentValue() {
					  return 17;
				  }

				  public Ingredient getRepairIngredient() {
					  return Ingredient.of(new ItemStack(JerotesVillageItems.VILLAGER_METAL_INGOT.get()));
				  }
			  }, new Properties().stacksTo(1).rarity(Rarity.COMMON), -1f, (1.0f / 0.77f) - 4f,
				0.77f, 1.0f, 0.75f, 5.0f, 14.0f, 10.0f, 5.1f, 15.0f, 4.6f);
	}
}


