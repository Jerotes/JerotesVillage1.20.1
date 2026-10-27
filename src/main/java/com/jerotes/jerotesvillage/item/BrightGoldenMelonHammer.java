package com.jerotes.jerotesvillage.item;

import com.google.common.collect.Multimap;
import com.jerotes.jerotes.item.Tool.ItemToolBaseHammer;
import com.jerotes.jerotes.util.AttackFind;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.List;

public class BrightGoldenMelonHammer extends ItemToolBaseHammer {
	private Multimap<Attribute, AttributeModifier> defaultModifiers;
	public BrightGoldenMelonHammer() {
		super(new Tier() {
			public int getUses() {
				return 240;
			}

			public float getSpeed() {
				return 12.0f;
			}

			public float getAttackDamageBonus() {
				return 12f;
			}

			public int getLevel() {
				return 0;
			}

			public int getEnchantmentValue() {
				return 22;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(Items.GOLD_INGOT));
			}
		}, -1, 1f - 4f, new Properties().rarity(Rarity.UNCOMMON));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.BOW;
	}

	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 72000;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1 || !(player.getMainHandItem().getItem() instanceof BrightGoldenMelonHammer && player.getOffhandItem().getItem() instanceof BrightGoldenMelonHammer)) {
			return InteractionResultHolder.fail(itemStack);
		}
		player.startUsingItem(interactionHand);
		return InteractionResultHolder.consume(itemStack);
	}

	@Override
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity user, int n) {
		int n1 = this.getUseDuration(itemStack) - n;
		if (n1 < 25) {
			return;
		}
		if (!(user.getMainHandItem().getItem() instanceof BrightGoldenMelonHammer && user.getOffhandItem().getItem() instanceof BrightGoldenMelonHammer)) {
			return;
		}
		if (level instanceof ServerLevel serverLevel) {
			if (!user.isSilent()) {
				user.level().playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.PLAYER_ATTACK_CRIT, user.getSoundSource(), 10.0f, 1.0f);
			}
			float reachs = AttackFind.reachAttackReach(user, itemStack, 4, 2, 3f, 0.5f);
			List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, user.getBoundingBox().inflate(reachs, reachs, reachs));
			for (LivingEntity hurt : list) {
				if (hurt == null || hurt.distanceTo(user) > reachs * 4) continue;
				if (user == hurt) continue;
				//if (AttackFind.FindCanNotAttack(user, hurt)) continue;
				if (!hurt.hasLineOfSight(user) && !(hurt.getBoundingBox().intersects(user.getBoundingBox()))) continue;
				AttackFind.attackBegin(user, hurt);
				AttackFind.attackAfter(user, hurt, 1f, 0.5f, false, 0f);
			}
			double d0 = -Mth.sin(user.getYRot() * ((float)Math.PI / 180F));
			double d1 = Mth.cos(user.getYRot() * ((float)Math.PI / 180F));
			for (int i = 0; i < 4; ++i) {
				serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, user.getX() + d0, user.getY(0.2D) + i * 0.35f , user.getZ() + d1, 0, d0, 0.0D, d1, 0.0D);
			}
		}
		user.getMainHandItem().hurtAndBreak(2, user, livingEntity -> livingEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		user.getOffhandItem().hurtAndBreak(2, user, livingEntity -> livingEntity.broadcastBreakEvent(EquipmentSlot.OFFHAND));
		if (user instanceof Player player) {
			player.awardStat(Stats.ITEM_USED.get(this));
		}
	}
	@Override
	public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
	}
	public MutableComponent getDisplayName() {
		return Component.translatable(this.getDescriptionId() + ".desc");
	}
}
