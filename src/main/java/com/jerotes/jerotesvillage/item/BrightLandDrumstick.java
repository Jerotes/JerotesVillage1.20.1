package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.entity.Boss.Biome.BrightLandBeastEntity;
import com.jerotes.jerotesvillage.util.BossSpawn;
import com.jerotes.jerotesvillage.init.JerotesVillageBiomes;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.util.SpawnRules;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;
import java.util.List;

public class BrightLandDrumstick extends Item {
	public BrightLandDrumstick() {
		super(new Properties().stacksTo(1).rarity(Rarity.RARE));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.BLOCK;
	}
	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 20;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		player.startUsingItem(interactionHand);
		return InteractionResultHolder.consume(itemStack);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		BlockPos pos = context.getClickedPos();
		Player player = context.getPlayer();
		ItemStack itemstack = context.getItemInHand();
		Level level = context.getLevel();
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();


        if (player != null && (level.getBlockState(BlockPos.containing(x, y, z))).getBlock() == JerotesVillageBlocks.BRIGHT_LAND_DRUM.get()) {
            double distance = player.distanceToSqr(x, y, z);
            float voice = (float) distance * 0.1f + 0.1f;
			float voiceDistance = 10f;
			if (level.getBlockState(pos.below()).getBlock() == Blocks.SCAFFOLDING) {
				voiceDistance = 20f;
			}
			level.addParticle(ParticleTypes.NOTE, (double)x + 0.5, (double)y + 1.2, (double)z + 0.5, voice, 0.0, 0.0);

			if (player.level() instanceof ServerLevel serverLevel && !context.getLevel().getBlockState(pos).isAir()) {
				serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, context.getLevel().getBlockState(pos)), x + 0.5, y + 0.5, z + 0.5, 20, 0.3, 0, 0.3, 0);
            }
			if (level.getBiome(BlockPos.containing(player.getX(), player.getY(), player.getZ())).is(JerotesVillageBiomes.BRIGHT_GRASSLAND) && player.getRandom().nextInt(1, 20) == 1 && SpawnRules.BossSpawnFind(JerotesVillageEntityType.BRIGHT_LAND_BEAST.get(), player)) {
				if (!player.level().isClientSide) {
					player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 200, 0));
				}
				if (player.level() instanceof ServerLevel serverLevel) {
					BlockPos blockPos = Main.findSpawnPositionNearFillOnBlock(player, 48);
					BrightLandBeastEntity boss = null;
					if (blockPos != null) {
						boss = JerotesVillageEntityType.BRIGHT_LAND_BEAST.get().spawn(serverLevel, blockPos, MobSpawnType.EVENT);
						if (boss != null) {
							boss.setDeltaMovement(0, 0, 0);
							if (!boss.level().isClientSide) {
								boss.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 200, 1));
							}
							boss.start = 260;
						}
						level.playSound(null, x, y, z, JerotesVillageSoundEvents.BRIGHT_LAND_BEAST_AMBIENT, SoundSource.NEUTRAL, voiceDistance * 2, voice);
						player.getCooldowns().addCooldown(this, 1800);
						BossSpawn.BrightLandBeast(player);
						if (!player.getAbilities().instabuild) {
							itemstack.shrink(1);
						}
						player.level().destroyBlock(pos, false, player);
					}
				}
				return InteractionResult.SUCCESS;
            }
			else {
				level.playSound(null, x, y, z, JerotesVillageSoundEvents.BRIGHT_LAND_DRUM, SoundSource.BLOCKS, voiceDistance, voice);
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
				return InteractionResult.SUCCESS;
			}
        }
		return super.useOn(context);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
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
