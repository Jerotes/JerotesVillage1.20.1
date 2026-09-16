package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.entity.Boss.Biome.SerponCombatExecuterEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Other.SerponSignalBulletEntity;
import com.jerotes.jerotesvillage.util.BossSpawn;
import com.jerotes.jerotesvillage.init.JerotesVillageBiomeTypeTags;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.util.SpawnRules;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class SerponSignalBullet extends Item {
	public SerponSignalBullet() {
			super(new Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant());
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

        if (player instanceof ServerPlayer serverPlayer && SpawnRules.BossSpawnFind(JerotesVillageEntityType.SERPON_COMBAT_EXECUTER.get(), player)) {
            if ((level.getBlockState(BlockPos.containing(context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ()))).getBlock() == JerotesVillageBlocks.SERPON_CONNECTION_POINT.get() &&
					level.getBiome(BlockPos.containing(context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ())).is(JerotesVillageBiomeTypeTags.SERPON_FIND)) {
				if (!BossSpawn.ChallengeMiddleBoss(serverPlayer)) {
					player.displayClientMessage(Component.translatable("message.jerotesvillage.challenge_middle_boss_fail").withStyle(ChatFormatting.RED), true);
					return InteractionResult.SUCCESS;
				}
                SerponSignalBulletEntity serponSignalBullet = new SerponSignalBulletEntity(JerotesVillageEntityType.SERPON_SIGNAL_BULLET.get(), level);
                serponSignalBullet.setPos(x + 0.5, y + 1, z + 0.5);
                serponSignalBullet.setOwner(player);
                level.addFreshEntity(serponSignalBullet);
                if (player.level() instanceof ServerLevel serverLevel) {
                    BlockPos blockPos = Main.findSpawnPositionNearFill(player, 32);
                    SerponCombatExecuterEntity boss = null;
                    if (blockPos != null) {
                        boss = JerotesVillageEntityType.SERPON_COMBAT_EXECUTER.get().spawn(serverLevel, blockPos, MobSpawnType.EVENT);
                        if (boss != null) {
                            boss.setDeltaMovement(0, 0, 0);
                            boss.setTrueCome(false);
                        }
                        if (!player.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }
                        level.playSound(null, x, y, z, SoundEvents.GENERIC_EXPLODE, SoundSource.NEUTRAL, 1.0f, 1.0f / (level.getRandom().nextFloat() * 0.4f + 1.2f) + 1 * 0.5f);
                        player.getCooldowns().addCooldown(this, 1800);
                        BossSpawn.SerponCombatExecuter(player);
                    }
                }
                return InteractionResult.SUCCESS;
            }
			else {
				player.displayClientMessage(Component.translatable("message.jerotesvillage.use_serpon_signal_bullet").withStyle(ChatFormatting.GOLD), true);
				return InteractionResult.SUCCESS;
			}
        }
        return super.useOn(context);
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
