package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.util.BossSpawn;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.util.SpawnRules;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class EvilPotion extends Item {
	public EvilPotion() {
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
		return InteractionResultHolder.consume(itemStack);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		BlockPos pos = context.getClickedPos();
		Player entity = context.getPlayer();
		ItemStack itemstack = context.getItemInHand();
		Level level = context.getLevel();
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

        if (entity != null && SpawnRules.BossSpawnFind(JerotesVillageEntityType.PURPLE_SAND_HAG.get(), entity)) {
            if ((level.getBlockState(BlockPos.containing(context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ()))).getBlock() == JerotesVillageBlocks.HAGS_CAULDRON.get()) {
                entity.getCooldowns().addCooldown(this, 1800);
                if (level instanceof ServerLevel serverLevel) {
                    BossSpawn.PurpleSandHag(entity);
                }
                if (!entity.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.setBlock(pos, JerotesVillageBlocks.UNSTABLE_HAGS_CAULDRON.get().defaultBlockState(), 2);
                }
                level.playSound(null, BlockPos.containing(x, y, z), SoundEvents.POINTED_DRIPSTONE_DRIP_WATER_INTO_CAULDRON, SoundSource.NEUTRAL, 1.0f, 1.0f / (level.getRandom().nextFloat() * 0.4f + 1.2f) + 1 * 0.5f);
                return InteractionResult.SUCCESS;
            }
			else if ((level.getBlockState(BlockPos.containing(context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ()))).getBlock() == JerotesVillageBlocks.NEW_HAGS_CAULDRON.get()) {
                entity.getCooldowns().addCooldown(this, 1800);
                if (level instanceof ServerLevel serverLevel) {
                    BossSpawn.PurpleSandHag(entity);
                }
                if (!entity.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.setBlock(pos, JerotesVillageBlocks.UNSTABLE_HAGS_CAULDRON.get().defaultBlockState(), 2);
                }
                level.playSound(null, BlockPos.containing(x, y, z), SoundEvents.POINTED_DRIPSTONE_DRIP_WATER_INTO_CAULDRON, SoundSource.NEUTRAL, 1.0f, 1.0f / (level.getRandom().nextFloat() * 0.4f + 1.2f) + 1 * 0.5f);
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
