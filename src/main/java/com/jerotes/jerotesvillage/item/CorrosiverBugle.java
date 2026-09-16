package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.entity.Boss.Biome.CorrosiverEntity;
import com.jerotes.jerotesvillage.util.BossSpawn;
import com.jerotes.jerotesvillage.init.JerotesVillageBiomes;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.util.SpawnRules;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class CorrosiverBugle extends Item {
	public CorrosiverBugle() {
			super(new Properties().stacksTo(1).rarity(Rarity.RARE));
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
		if (level.getBiome(BlockPos.containing(player.getX(), player.getY(), player.getZ())).is(JerotesVillageBiomes.BITTER_COLD) && SpawnRules.BossSpawnFind(JerotesVillageEntityType.CORROSIVER.get(), player)){
			if (!player.level().isClientSide) {
				player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 200, 0));
			}
			if (level instanceof ServerLevel serverLevel) {
				BlockPos blockPos = Main.findSpawnPositionNearFillOnBlock(player, 24);
				CorrosiverEntity boss;
				if (blockPos != null) {
					boss = JerotesVillageEntityType.CORROSIVER.get().spawn(serverLevel, blockPos, MobSpawnType.EVENT);
					if (boss != null) {
						boss.setDeltaMovement(0, 0, 0);
						boss.start = 260;
						player.getCooldowns().addCooldown(this, 1800);
						BossSpawn.Corrosiver(player);
						level.playSound(null, player.getX(), player.getY(), player.getZ(), JerotesVillageSoundEvents.CORROSIVER_AMBIENT, SoundSource.NEUTRAL, 20.0f, 1.0f / (level.getRandom().nextFloat() * 0.4f + 1.2f) + 1 * 0.5f);
						if (!player.getAbilities().instabuild) {
							itemStack.shrink(1);
						}
					}
				}
			}
		}
		else{
			player.getCooldowns().addCooldown(this, 400);
			level.playSound(null, player.getX(), player.getY(), player.getZ(), JerotesVillageSoundEvents.GIANT_MONSTER_AMBIENT, SoundSource.NEUTRAL, 20.0f, 1.0f / (level.getRandom().nextFloat() * 0.4f + 1.2f) + 1 * 0.5f);
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
