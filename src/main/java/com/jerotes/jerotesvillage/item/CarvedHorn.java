package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.event.RelationshipEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.util.OtherEntityFactionFind;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.PlayerTeam;

import javax.annotation.Nullable;
import java.util.List;

public class CarvedHorn extends Item {
	public CarvedHorn() {
			super(new Properties().stacksTo(1).rarity(Rarity.COMMON));
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
		player.startUsingItem(interactionHand);
		level.playSound(null, player.getX(), player.getY(), player.getZ(), JerotesVillageSoundEvents.CARVED_HORN_USE, SoundSource.NEUTRAL, 20.0f, 1.0f / (level.getRandom().nextFloat() * 0.4f + 1.2f) + 1 * 0.5f);
		PlayerTeam teams = (PlayerTeam) player.getTeam();
		List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(32.0, 32.0, 32.0));
		double d = Double.MAX_VALUE;
		for (LivingEntity livingEntity : list) {
			if (livingEntity == null) continue;
			if ((livingEntity.distanceToSqr(livingEntity)) > d) continue;

			//有队伍
			if (teams != null) {
				if (!player.isAlliedTo(livingEntity)) continue;
			}
			//常规
			else {
				if (!(OtherEntityFactionFind.isCarved(livingEntity.getType())) && !(livingEntity instanceof Player player1 && RelationshipEvent.MoreCopperCarvedCompanyRelationship(player1, 500))) continue;
			}
			if (livingEntity instanceof Mob mob && player == mob.getTarget()) continue;


			if (!level.isClientSide) {
				livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 240, 0));
				livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 240, 0));
				livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 240, 0));
				livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 240, 0));
			}
		}
		player.getCooldowns().addCooldown(this, 120);
		player.awardStat(Stats.ITEM_USED.get(this));
		return InteractionResultHolder.consume(itemStack);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
	}

	public MutableComponent getDisplayName() {
		return Component.translatable(this.getDescriptionId() + ".desc");
	}

}
