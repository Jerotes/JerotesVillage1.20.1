package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotesvillage.event.AdvancementEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;

import java.util.List;

public class VillagerMetalSyringe extends Item implements Vanishable {
	public VillagerMetalSyringe() {
		super(new Properties().stacksTo(1).rarity(Rarity.COMMON).durability(120));
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(JerotesVillageItems.VILLAGER_METAL_INGOT.get()) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.BOW;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		player.startUsingItem(interactionHand);
		return InteractionResultHolder.consume(itemStack);
	}

	public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
		if (itemStack.getItem() instanceof VillagerMetalSyringe syringe && !player.getCooldowns().isOnCooldown(syringe)) {
			if (player.getOffhandItem().getItem() instanceof ItemTabletBase tablet) {
				player.swing(InteractionHand.MAIN_HAND);
				player.getCooldowns().addCooldown(syringe, 60);
				tablet.useTablet(livingEntity, player);
				itemStack.hurtAndBreak(1, player, players -> players.broadcastBreakEvent(EquipmentSlot.MAINHAND));
				if (!player.getAbilities().instabuild) {
					player.getOffhandItem().shrink(1);
				}
				for (int i = 0; i < 15; ++i) {
					double d = livingEntity.getRandom().nextGaussian() * 0.02;
					double d2 = livingEntity.getRandom().nextGaussian() * 0.02;
					double d3 = livingEntity.getRandom().nextGaussian() * 0.02;
					livingEntity.level().addParticle(ParticleTypes.ENTITY_EFFECT, livingEntity.getRandomX(1.0), livingEntity.getRandomY() + 1.0, livingEntity.getRandomZ(1.0), d, d2, d3);
				}
				if (!player.isSilent()) {
					player.level().playSound(null, player, SoundEvents.BREWING_STAND_BREW, player.getSoundSource(), 1.0f, 1.0f);
				}
				if (EntityFactionFind.isHumanoid(livingEntity) && !livingEntity.isAlive() && player instanceof ServerPlayer serverPlayer) {
					AdvancementEvent.AdvancementGive(serverPlayer, "jerotesvillage:drug_allergy");
				}
				return InteractionResult.sidedSuccess(player.level().isClientSide);
			}

			if (player.getOffhandItem().getItem() instanceof PotionItem) {
				player.swing(InteractionHand.MAIN_HAND);
				player.getCooldowns().addCooldown(syringe, 60);
				if (!player.level().isClientSide()) {
					List<MobEffectInstance> list = PotionUtils.getMobEffects(player.getOffhandItem());

					boolean hurt = false;
					for (MobEffectInstance mobEffectInstance : list) {
						if (!mobEffectInstance.getEffect().isBeneficial()) {
							hurt = true;
						}
					}
					if (hurt) {
						DamageSource damageSource = AttackFind.findDamageType(player, JerotesDamageTypes.SYRINGE, player);
						AttackFind.attackBegin(player, livingEntity);
						AttackFind.attackAfterCustomDamage(player, livingEntity, damageSource, 0f, 0f, true, !AttackFind.FindCanNotAttack(player, livingEntity) ? 1f : 0f);
					}

					for (MobEffectInstance mobEffectInstance : list) {
						if (mobEffectInstance.getEffect().isInstantenous()) {
							mobEffectInstance.getEffect().applyInstantenousEffect(player, player, livingEntity, mobEffectInstance.getAmplifier(), 1.0);
							continue;
						}
						MobEffect mobEffect = new MobEffectInstance(mobEffectInstance).getEffect();
						int amplifier = new MobEffectInstance(mobEffectInstance).getAmplifier();
						int duration = new MobEffectInstance(mobEffectInstance).getDuration();
						boolean bl = new MobEffectInstance(mobEffectInstance).isAmbient();
						boolean bl2 = new MobEffectInstance(mobEffectInstance).isVisible();
						livingEntity.addEffect(new MobEffectInstance(mobEffect, duration, amplifier, bl, bl2), player);
					}
				}
				itemStack.hurtAndBreak(1, player, players -> players.broadcastBreakEvent(EquipmentSlot.MAINHAND));
				if (!player.getAbilities().instabuild) {
					ItemStack itemStack1 = Items.GLASS_BOTTLE.getDefaultInstance();
					player.setItemInHand(InteractionHand.OFF_HAND, itemStack1);
				}
				for (int i = 0; i < 15; ++i) {
					double d = livingEntity.getRandom().nextGaussian() * 0.02;
					double d2 = livingEntity.getRandom().nextGaussian() * 0.02;
					double d3 = livingEntity.getRandom().nextGaussian() * 0.02;
					livingEntity.level().addParticle(ParticleTypes.ENTITY_EFFECT, livingEntity.getRandomX(1.0), livingEntity.getRandomY() + 1.0, livingEntity.getRandomZ(1.0), d, d2, d3);
				}
				if (!player.isSilent()) {
					player.level().playSound(null, player, SoundEvents.BREWING_STAND_BREW, player.getSoundSource(), 1.0f, 1.0f);
				}
				if (EntityFactionFind.isHumanoid(livingEntity) && !livingEntity.isAlive() && player instanceof ServerPlayer serverPlayer) {
					AdvancementEvent.AdvancementGive(serverPlayer, "jerotesvillage:drug_allergy");
				}
				return InteractionResult.sidedSuccess(player.level().isClientSide);
			}
		}
		return InteractionResult.PASS;
	}

	@Override
	public int getEnchantmentValue() {
		return 17;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
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


