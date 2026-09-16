package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.entity.Animal.CarvedHoundEntity;
import com.jerotes.jerotesvillage.event.RelationshipEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.Main;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class CarvedDogWhistle extends Item {
	public CarvedDogWhistle() {
		super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
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
		return InteractionResultHolder.consume(itemStack);
	}

	@Override
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int n) {
		if (!(livingEntity instanceof Player player)) {
			return;
		}
		int n2 = this.getUseDuration(itemStack) - n;
		if (n2 < 20) {
			return;
		}
		if (level instanceof ServerLevel serverLevel) {
			BlockPos blockPos = Main.findSpawnPositionNearFillOnBlock(player, 8);
			CarvedHoundEntity hound;
			if (blockPos != null) {
				hound = JerotesVillageEntityType.CARVED_HOUND.get().spawn(serverLevel, blockPos, MobSpawnType.EVENT);
				if (hound != null) {
					hound.setDeltaMovement(0, 0, 0);
					player.getCooldowns().addCooldown(this, 120);
					for (int i = 0; i < 60; ++i) {
						serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, hound.getRandomX(1.5), hound.getRandomY(), hound.getRandomZ(1.5), 0, 0.0, 0.0, 0.0, 0.0);
					}
					if (!hound.isSilent()) {
						serverLevel.playSound(null, hound.getX(), hound.getY(), hound.getZ(), JerotesSoundEvents.TELEPORT, hound.getSoundSource(), 5.0f, 1.0F);
					}
					if (!player.getAbilities().instabuild) {
						itemStack.shrink(1);
					}
					RelationshipEvent.AddCopperCarvedCompanyRelationship(player, 5);
				}
			}
		}
		player.awardStat(Stats.ITEM_USED.get(this));
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
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
