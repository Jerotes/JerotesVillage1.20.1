package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.entity.Neutral.CarvedAllayEntity;
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

public class CarvedAmethystFlute extends Item {
	public CarvedAmethystFlute() {
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
	public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemstack, int count) {
		if (livingEntity.getRandom().nextFloat() > 0.8) {
			float sound = 1.0f / (level.getRandom().nextFloat() * 0.4f + 1.2f) + 1 * 0.5f;
			level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.NEUTRAL, 20.0f, sound);
			for (int i = 0; i < 3; ++i) {
				level.addParticle(ParticleTypes.NOTE, livingEntity.getRandomX(1.5), livingEntity.getRandomY(), livingEntity.getRandomZ(1.5), sound, 0.0, 0.0);
			}
		}
		super.onUseTick(level, livingEntity, itemstack, count);
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
			CarvedAllayEntity allay;
			if (blockPos != null) {
				allay = JerotesVillageEntityType.CARVED_ALLAY.get().spawn(serverLevel, blockPos, MobSpawnType.EVENT);
				if (allay != null) {
					//长笛招出来的必须是铜刻悦灵，finalizeSpawn 那 5% 的恼鬼形态是给佣兵团用的
					allay.setVexRace(false);
					allay.setDeltaMovement(0, 0, 0);
					player.getCooldowns().addCooldown(this, 120);
					for (int i = 0; i < 60; ++i) {
						serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, allay.getRandomX(1.5), allay.getRandomY(), allay.getRandomZ(1.5), 0, 0.0, 0.0, 0.0, 0.0);
					}
					if (!allay.isSilent()) {
						serverLevel.playSound(null, allay.getX(), allay.getY(), allay.getZ(), JerotesSoundEvents.TELEPORT, allay.getSoundSource(), 5.0f, 1.0F);
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
