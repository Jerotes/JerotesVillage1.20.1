package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.entity.Shoot.Other.MerorFireEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotes.item.Tool.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.List;


public class MerorFireSpear extends ItemToolBaseSpear {
	public MerorFireSpear() {
		super(new Tier() {
				  public int getUses() {
					  return 18800;
				  }

				  public float getSpeed() {
					  return 18f;
				  }

				  public float getAttackDamageBonus() {
					  return 14f;
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
			  }, new Properties().stacksTo(1).durability(18800).fireResistant().rarity(Rarity.EPIC), -1f, (1.0f / 0.9f) - 4f,
				0.9f, 1.8f, 0.3f, 4.5f, 10.0f, 10.0f, 5.1f, 15.0f, 4.6f);
	}

	@Override
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int n) {
		super.releaseUsing(itemStack, level, livingEntity, n);
		level.playSound(null, livingEntity, SoundEvents.FIRE_EXTINGUISH, SoundSource.NEUTRAL, 1.0f, 1.0f);
		level.playSound(null, livingEntity, JerotesVillageSoundEvents.MEROR_TOOL_USE, SoundSource.NEUTRAL, 1.0f, 1.0f);
		if (!(livingEntity instanceof Player player2)) {
			return;
		}
		player2.awardStat(Stats.ITEM_USED.get(this));
	}

	public void onUseTickSpecial(Level level, LivingEntity livingEntity, ItemStack itemStack, int count) {
		if (level instanceof ServerLevel projectileLevel) {
			if (count % 30 == 0 && livingEntity instanceof Player player && livingEntity.isAlive()) {
				itemStack.hurtAndBreak(2, player, livingEntitys -> livingEntitys.broadcastBreakEvent(EquipmentSlot.MAINHAND));
			}
			if (!livingEntity.level().isClientSide) {
				livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 30, 0, false, false), livingEntity);
				if (livingEntity.getVehicle() != null && livingEntity.getVehicle() instanceof LivingEntity living) {
					living.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 30, 0, false, false), livingEntity);

				}
			}
			livingEntity.clearFire();
			if (livingEntity.getRemainingFireTicks() > 0) {
				livingEntity.setRemainingFireTicks(0);
			}
			if (livingEntity.getVehicle() != null && livingEntity.getVehicle() instanceof LivingEntity living) {
				living.clearFire();
				if (living.getRemainingFireTicks() > 0) {
					living.setRemainingFireTicks(0);
				}

			}
			if (livingEntity instanceof Mob mob && mob.getTarget() != null) {
				mob.getLookControl().setLookAt(mob.getTarget(), 360.0f, 360.0f);
				mob.lookAt(mob.getTarget(), 360.0f, 360.0f);
			}
			if (count % 3 == 0) {
				Projectile fire0 = new MerorFireEntity(JerotesVillageEntityType.MEROR_FIRE.get(), projectileLevel);
				Projectile fire1 = new MerorFireEntity(JerotesVillageEntityType.MEROR_FIRE.get(), projectileLevel);
				fire0.setOwner(livingEntity);
				fire1.setOwner(livingEntity);
				fire0.setPos(livingEntity.getX(), (livingEntity.getEyeY() - 0.2), livingEntity.getZ());
				fire1.setPos(livingEntity.getX(), (livingEntity.getEyeY() - 0.2), livingEntity.getZ());
				fire0.shootFromRotation(livingEntity, livingEntity.getXRot() + 3, livingEntity.getYRot(), 5.0f, 1.5f, 1.0f);
				fire1.shootFromRotation(livingEntity, livingEntity.getXRot() - 3, livingEntity.getYRot(), 5.0f, 1.5f, 1.0f);
				fire0.shoot(livingEntity.getLookAngle().x, livingEntity.getLookAngle().y, livingEntity.getLookAngle().z, 2, 0);
				projectileLevel.addFreshEntity(fire0);
				projectileLevel.addFreshEntity(fire1);
				if (!livingEntity.isSilent()) {
					level.playSound(null, livingEntity, SoundEvents.FIRE_AMBIENT, SoundSource.NEUTRAL, 1.0f, 1.0f);
					level.playSound(null, livingEntity, SoundEvents.FIRE_AMBIENT, SoundSource.NEUTRAL, 1.0f, 1.0f);
					level.playSound(null, livingEntity, SoundEvents.FIRE_AMBIENT, SoundSource.NEUTRAL, 1.0f, 1.0f);
					level.playSound(null, livingEntity, SoundEvents.FIRE_AMBIENT, SoundSource.NEUTRAL, 1.0f, 1.0f);
					level.playSound(null, livingEntity, SoundEvents.FIRE_AMBIENT, SoundSource.NEUTRAL, 1.0f, 1.0f);
					livingEntity.level().playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), JerotesVillageSoundEvents.MEROR_TOOL_USE, livingEntity.getSoundSource(), 10.0f, 1.0f);
				}
			}
		}
		super.onUseTickSpecial(level, livingEntity, itemStack, count);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName_0().withStyle(ChatFormatting.GRAY));
		list.add(this.getDisplayName_1().withStyle(ChatFormatting.GRAY));
		list.add(this.getDisplayName_2().withStyle(ChatFormatting.GRAY));
	}
	public MutableComponent getDisplayName_0() {
		return Component.translatable(this.getDescriptionId() + ".desc_0");
	}
	public MutableComponent getDisplayName_1() {
		return Component.translatable(this.getDescriptionId() + ".desc_1");
	}
	public MutableComponent getDisplayName_2() {
		return Component.translatable(this.getDescriptionId() + ".desc_2");
	}
}


