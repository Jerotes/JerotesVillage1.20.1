package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.entity.Boss.OminousBannerProjectionEntity;
import com.jerotes.jerotesvillage.util.BossSpawn;
import com.jerotes.jerotesvillage.init.JerotesVillageDimensions;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.util.SpawnRules;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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

public class OminousBannerRaidTagDeployer extends Item {
	public OminousBannerRaidTagDeployer() {
			super(new Properties().stacksTo(1).rarity(Rarity.RARE));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.BLOCK;
	}
	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 72000;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		player.startUsingItem(interactionHand);
		if (level.dimension() == JerotesVillageDimensions.SECOND_ROUND_WORLD && SpawnRules.BossSpawnFind(JerotesVillageEntityType.OMINOUS_BANNER_PROJECTION.get(), player)){
			if (level instanceof ServerLevel serverLevel) {
				BlockPos blockPos = Main.findSpawnPositionNearFillOnBlock(player, 6);
				OminousBannerProjectionEntity boss;
				if (blockPos != null) {
					boss = JerotesVillageEntityType.OMINOUS_BANNER_PROJECTION.get().spawn(serverLevel, blockPos, MobSpawnType.EVENT);
					if (boss != null) {
						boss.setDeltaMovement(0, 0, 0);
						player.getCooldowns().addCooldown(this, 1800);
						BossSpawn.OminousBannerProjection(player);
						level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.RAID_HORN.get(), SoundSource.NEUTRAL, 20.0f, 1.0f / (level.getRandom().nextFloat() * 0.4f + 1.2f) + 1 * 0.5f);
						if (!player.getAbilities().instabuild) {
							itemStack.shrink(1);
						}
					}
				}
			}
		}
		return InteractionResultHolder.consume(itemStack);
	}

	@Override
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int n) {
		if (!(livingEntity instanceof Player)) {
			return;
		}
		int n2 = this.getUseDuration(itemStack) - n;
		if (n2 < 10) {
			return;
		}
		Player player2 = (Player)livingEntity;
		player2.awardStat(Stats.ITEM_USED.get(this));
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
