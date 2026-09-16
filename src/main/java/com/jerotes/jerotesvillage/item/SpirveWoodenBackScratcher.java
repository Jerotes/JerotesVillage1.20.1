package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.item.Tool.ItemToolBaseSword;
import com.jerotes.jerotes.util.Main;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class SpirveWoodenBackScratcher extends ItemToolBaseSword {
	public SpirveWoodenBackScratcher() {
		super(new Tier() {
			public int getUses() {
				return 32;
			}

			public float getSpeed() {
				return 2f;
			}

			public float getAttackDamageBonus() {
				return 3f;
			}

			public int getLevel() {
				return 0;
			}

			public int getEnchantmentValue() {
				return 15;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(ItemTags.PLANKS);
			}
		}, -1, 1f - 4f, new Properties());
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.TOOT_HORN;
	}
	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 72000;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
			return InteractionResultHolder.fail(itemStack);
		}
		if (Main.getTargetedEntity(player, 3, true, true) != null && Main.getTargetedEntity(player, 3, true, true) instanceof LivingEntity livingEntity2) {
			if (!level.isClientSide) {
				livingEntity2.removeEffect(JerotesMobEffects.PRURITUS.get());
			}
			if (level instanceof ServerLevel serverLevel) {
				for (int i = 0; i < 16; ++i) {
					serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, itemStack), livingEntity2.getRandomX(0.5), livingEntity2.getRandomY(), livingEntity2.getRandomZ(0.5), 0, 0.0, 0.0, 0.0, 0.0);
				}
			}
			if (livingEntity2 instanceof Mob mob) {
				mob.playAmbientSound();
			}
			if (livingEntity2 instanceof Player player1) {
				player1.playSound(SoundEvents.PLAYER_BREATH);
			}
		}
		else {
			if (!level.isClientSide) {
				player.removeEffect(JerotesMobEffects.PRURITUS.get());
			}
			if (level instanceof ServerLevel serverLevel) {
				for (int i = 0; i < 16; ++i) {
					serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, itemStack), player.getRandomX(0.5), player.getRandomY(), player.getRandomZ(0.5), 0, 0.0, 0.0, 0.0, 0.0);
				}
			}
			player.playSound(SoundEvents.PLAYER_BREATH);
		}
		if (!player.getAbilities().instabuild) {
			player.getCooldowns().addCooldown(this, 60);
		}
		return InteractionResultHolder.consume(itemStack);
	}
}
