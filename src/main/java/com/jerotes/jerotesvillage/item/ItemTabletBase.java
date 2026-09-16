package com.jerotes.jerotesvillage.item;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class ItemTabletBase extends Item {
	private static final Map<EntityType<? extends Mob>, ItemTabletBase> BY_ID = Maps.newIdentityHashMap();

	private final MobEffect mobEffect;
	private final int time;
	private final int level;
	private final MobEffect mobEffect2;
	private final int time2;
	private final int level2;
	private final boolean hurt;
	private final int color;
	private final int color2;
	public ItemTabletBase(Properties properties, MobEffect mobEffect, int time, int level, MobEffect mobEffect2, int time2, int level2, boolean hurt, int color, int color2) {
		super(properties);
		this.mobEffect = mobEffect;
		this.time = time;
		this.level = level;
		this.mobEffect2 = mobEffect2;
		this.time2 = time2;
		this.level2 = level2;
		this.hurt = hurt;
		this.color = color;
		this.color2 = color2;
	}
	public static Iterable<ItemTabletBase> tablets() {
		return Iterables.unmodifiableIterable(BY_ID.values());
	}

	public int getColor() {
		return color;
	}

	public int getColor2() {
		return color2;
	}

	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 10;
	}
	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.EAT;
	}
	@Override
	public SoundEvent getDrinkingSound() {
		return SoundEvents.GENERIC_EAT;
	}
	@Override
	public SoundEvent getEatingSound() {
		return SoundEvents.GENERIC_EAT;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemstack = player.getItemInHand(interactionHand);
		if (interactionHand == InteractionHand.OFF_HAND && player.getMainHandItem().getItem() instanceof VillagerMetalSyringe) {
			return InteractionResultHolder.fail(itemstack);
		}
		return ItemUtils.startUsingInstantly(level, player, interactionHand);
    }

	public boolean useTablet(LivingEntity patient, LivingEntity operator) {
		if (!patient.isAlive())
			return false;
		if (!patient.level().isClientSide()) {
			MobEffectInstance mobEffectInstance = new MobEffectInstance(mobEffect, time, level, false, true);
			MobEffectInstance mobEffectInstance2 = new MobEffectInstance(mobEffect2, time2, level2, false, true);
			if (mobEffectInstance.getEffect().isInstantenous()) {
				mobEffectInstance.getEffect().applyInstantenousEffect(operator, operator, patient, mobEffectInstance.getAmplifier(), 1f);
			}
			else {
				patient.addEffect(mobEffectInstance, operator);
			}
			if (mobEffectInstance2.getEffect().isInstantenous()) {
				mobEffectInstance2.getEffect().applyInstantenousEffect(operator, operator, patient, mobEffectInstance2.getAmplifier(), 1f);
			}
			else {
				patient.addEffect(mobEffectInstance2, operator);
			}
			if (patient != operator && hurt) {
				DamageSource damageSource = AttackFind.findDamageType(operator, JerotesDamageTypes.SYRINGE, operator);
				AttackFind.attackBegin(operator, patient);
				AttackFind.attackAfterCustomDamage(operator, patient, damageSource, 0f, 0f, true, !AttackFind.FindCanNotAttack(operator, patient) ? 1f : 0f);
			}
		}
		return true;
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
		Player player;
		super.finishUsingItem(itemStack, level, livingEntity);
		if (livingEntity instanceof ServerPlayer) {
			player = (ServerPlayer)livingEntity;
			CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, itemStack);
			player.awardStat(Stats.ITEM_USED.get(this));
		}
		if (!level.isClientSide) {
			this.useTablet(livingEntity, livingEntity);
		}
		if (livingEntity instanceof Player && !((Player)livingEntity).getAbilities().instabuild) {
			itemStack.shrink(1);
		}
		return itemStack;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
		super.appendHoverText(itemStack, level, list, tooltipFlag);
	}
	public MutableComponent getDisplayName() {
		MutableComponent string = Component.translatable(mobEffect.getDisplayName().getString());
		MutableComponent string2 = Component.translatable(mobEffect2.getDisplayName().getString());
		return Component.translatable("item.jerotesvillage.tablet.desc", string, string2);
	}
}
