package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class MysterestPerfume extends Item {
	public MysterestPerfume() {
			super(new Properties().stacksTo(64).rarity(Rarity.COMMON));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.BRUSH;
	}

	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 40;
	}


	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		return ItemUtils.startUsingInstantly(level, player, interactionHand);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
		Player player;
		super.finishUsingItem(itemStack, level, livingEntity);
		if (!level.isClientSide) {
			livingEntity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.FRAGRANCE.get(), 6000, 0, false, true));
		}

		List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(8, 8, 8));
		double d = Double.MAX_VALUE;
		for (LivingEntity perfume : list) {
			if (perfume == null) continue;
			if ((livingEntity.distanceToSqr(perfume)) > d) continue;
			if (perfume.hasEffect(JerotesMobEffects.ANESTHETIZED.get())) {
				if (!level.isClientSide) {
					int an = perfume.getEffect(JerotesMobEffects.ANESTHETIZED.get()).getDuration();
					perfume.removeEffect(JerotesMobEffects.ANESTHETIZED.get());
					if (an > 18000) {
						perfume.addEffect(new MobEffectInstance(JerotesMobEffects.ANESTHETIZED.get(), an - 18000, 0, false, false));
					}
				}
			}
		}

		if (level instanceof ServerLevel _level) {
			_level.sendParticles(ParticleTypes.FALLING_NECTAR, livingEntity.getRandomX(0.5), livingEntity.getRandomY(), livingEntity.getRandomZ(0.5), 1, 0, 0, 0, 0);
		}
		Vec3 lookDir = livingEntity.getLookAngle().normalize();
		Vec3 entityPos = livingEntity.position().add(lookDir.scale(0.8));
		double radius = 1.5;
		int particles = 80;
		double heightOffset = livingEntity.getY(0.5) - livingEntity.getY();

		float yaw = livingEntity.getYRot();
		float pitch = livingEntity.getXRot();

		for (int i = 0; i < particles; i++) {
			double angle = Math.toRadians(-90 + (i * 180.0 / particles));
			double zOffset = radius * Math.cos(angle);
			double xOffset = radius * Math.sin(angle);
			double cosYaw = Math.cos(Math.toRadians(yaw));
			double sinYaw = Math.sin(Math.toRadians(yaw));
			double rotatedX = xOffset * cosYaw - zOffset * sinYaw;
			double rotatedZ = xOffset * sinYaw + zOffset * cosYaw;
			double finalYOffset = heightOffset + (Math.sin(Math.toRadians(-pitch)) * zOffset);
			Vec3 particlePos = entityPos.add(rotatedX, finalYOffset, rotatedZ);
			double speedX = rotatedX * 0.07 + (Math.random() * 0.02 - 0.01);
			double speedY = lookDir.y * 0.07 + (Math.random() * 0.02 - 0.01);
			double speedZ = rotatedZ * 0.07 + (Math.random() * 0.02 - 0.01);

			level.addParticle(JerotesVillageParticleTypes.MEROR_BULLET.get(),
					particlePos.x, particlePos.y, particlePos.z,
					speedX, speedY, speedZ);
		}
		if (livingEntity instanceof ServerPlayer) {
			player = (ServerPlayer)livingEntity;
			CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, itemStack);
			player.awardStat(Stats.ITEM_USED.get(this));
		}
		if (livingEntity instanceof Player && !((Player)livingEntity).getAbilities().instabuild) {
			itemStack.shrink(1);
		}
		return itemStack;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
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
