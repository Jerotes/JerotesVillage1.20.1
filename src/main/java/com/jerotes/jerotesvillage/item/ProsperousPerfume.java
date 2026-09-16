package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Animal.RamblerEntity;
import com.jerotes.jerotesvillage.entity.Boss.ProsperousEnvoyEntity;
import com.jerotes.jerotesvillage.util.BossSpawn;
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

public class ProsperousPerfume extends Item {
	public ProsperousPerfume() {
			super(new Properties().stacksTo(1).rarity(Rarity.RARE));
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
		ItemStack itemStack = player.getItemInHand(interactionHand);
		player.startUsingItem(interactionHand);
		double area = OtherMainConfig.BossSpawnRestrictionRange / 8;
		List<RamblerEntity> listRambler = player.level().getEntitiesOfClass(RamblerEntity.class, player.getBoundingBox().inflate(area, area, area));
		if (listRambler.size() > 12 && SpawnRules.BossSpawnFind(JerotesVillageEntityType.PROSPEROUS_ENVOY.get(), player)){
			if (!player.level().isClientSide) {
				player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 200, 0));
			}
			if (level instanceof ServerLevel serverLevel) {
				BlockPos blockPos = Main.findSpawnPositionNearFillOnBlock(player, 24);
				ProsperousEnvoyEntity boss;
				if (blockPos != null) {
					boss = JerotesVillageEntityType.PROSPEROUS_ENVOY.get().spawn(serverLevel, blockPos, MobSpawnType.EVENT);
					if (boss != null) {
						boss.setDeltaMovement(0, 0, 0);
						if (!boss.level().isClientSide) {
							boss.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 200, 1));
						}
						boss.start = 260;
						player.getCooldowns().addCooldown(this, 1800);
						BossSpawn.ProsperousEnvoy(player);
						level.playSound(null, player.getX(), player.getY(), player.getZ(), JerotesVillageSoundEvents.PROSPEROUS_ENVOY_AMBIENT, SoundSource.NEUTRAL, 20.0f, 1.0f / (level.getRandom().nextFloat() * 0.4f + 1.2f) + 1 * 0.5f);
						if (!player.getAbilities().instabuild) {
							itemStack.shrink(1);
						}
						//漫兽
						for (RamblerEntity rambler : listRambler) {
							if (rambler == null) continue;
							if (!rambler.level().isClientSide) {
								boss.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 6000, 0));
								boss.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 6000, 0));
								boss.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 1));
							}
						}
					}
				}
			}
		}
		else{
			player.getCooldowns().addCooldown(this, 400);
		}
		return InteractionResultHolder.consume(itemStack);
	}

	@Override
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int n) {
		if (!(livingEntity instanceof Player)) {
			return;
		}
		int n2 = this.getUseDuration(itemStack) - n;
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
