package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.entity.Monster.Elite.FrostYetiEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class BitterColdBell extends Item {
	public BitterColdBell() {
			super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.BRUSH;
	}
	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 72000;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BELL_BLOCK, SoundSource.NEUTRAL, 20.0f, 1.0f / (level.getRandom().nextFloat() * 0.4f + 1.2f) + 1 * 0.5f);
		if (player.level() instanceof ServerLevel) {
			YetiAway(player);
		}
		return InteractionResultHolder.consume(itemStack);
	}


	public static void YetiAway(LivingEntity user) {
		List<FrostYetiEntity> list = user.level().getEntitiesOfClass(FrostYetiEntity.class, user.getBoundingBox().inflate(64.0, 32.0, 64.0));
		double d = Double.MAX_VALUE;
		for (FrostYetiEntity shock : list) {
			if (shock == null) continue;
			if ((user.distanceToSqr(shock)) > d) continue;
			if (shock.isAlliedTo(user)) continue;
			if (!(shock.getTarget() == user || shock.getTarget() == null || (user instanceof OwnableEntity ownable && shock.getTarget() == ownable.getOwner()) || !shock.isAggressive())) continue;
			if (shock.getLastHurtByMob() == user) continue;
			if (user instanceof Mob mob && mob instanceof OwnableEntity ownable && shock.getLastHurtByMob() == ownable.getOwner()) continue;
			if (shock.getRunawayTick() < 110) {
				shock.level().playSound(null, shock.getX(), shock.getY(), shock.getZ(), SoundEvents.BELL_RESONATE, SoundSource.NEUTRAL, 20.0f, 1.0f / (shock.level().getRandom().nextFloat() * 0.4f + 1.2f) + 1 * 0.5f);
			}
			if (!shock.level().isClientSide()) {
				shock.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120, 1));
				shock.setRunawayTick(120);
			}
			if (shock.onGround()) {
				Vec3 randomShake = new Vec3(shock.getRandom().nextFloat() - 0.5F, 0, shock.getRandom().nextFloat() - 0.5F).scale(0.1F);
				shock.setDeltaMovement(shock.getDeltaMovement().multiply(0.7F, 1, 0.7F).add(randomShake));
			}
			Vec3 vec = LandRandomPos.getPosAway(shock, 64, 24, user.position());
			if (vec != null) {
				shock.getNavigation().moveTo(vec.x, vec.y, vec.z, 1d);
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName_0().withStyle(ChatFormatting.GRAY));
		list.add(this.getDisplayName_1().withStyle(ChatFormatting.GRAY));
		list.add(this.getDisplayName_2().withStyle(ChatFormatting.GRAY));
		list.add(this.getDisplayName_3().withStyle(ChatFormatting.GRAY));
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
	public MutableComponent getDisplayName_3() {
		return Component.translatable(this.getDescriptionId() + ".desc_3");
	}
}
