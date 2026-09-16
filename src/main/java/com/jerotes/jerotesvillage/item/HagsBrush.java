package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import com.jerotes.jerotes.item.Tool.*;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class HagsBrush extends ItemToolBaseBrush {
	public HagsBrush() {
			super(new Properties().stacksTo(1).rarity(Rarity.EPIC), 2.0f, 1.0f);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		BlockPos pos = context.getClickedPos();
		Level level = context.getLevel();
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		if ((level.getBlockState(BlockPos.containing(context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ()))).getBlock() == JerotesVillageBlocks.DIRTY_HAGS_CAULDRON.get()) {
			if (level instanceof ServerLevel serverLevel) {
				serverLevel.setBlock(pos, JerotesVillageBlocks.NEW_HAGS_CAULDRON.get().defaultBlockState(), 2);
			}
			level.playSound(null, BlockPos.containing(x, y, z), SoundEvents.BRUSH_GENERIC, SoundSource.NEUTRAL, 1.0f, 1.0f / (level.getRandom().nextFloat() * 0.4f + 1.2f) + 1 * 0.5f);
			return InteractionResult.SUCCESS;
		}
		return super.useOn(context);
	}

	@Override
	public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity2, LivingEntity livingEntity3) {
//		if (livingEntity3 instanceof Player) {
//			CompoundTag compoundTag = Main.getJerotesPersistentData(livingEntity2);
//			livingEntity2.addAdditionalSaveData(compoundTag);
//			setAllHealthTagsToZero(compoundTag);
//			livingEntity2.readAdditionalSaveData(compoundTag);
//		}
		return true;
	}

//	public void setAllHealthTagsToZero(CompoundTag tag) {
//		for (String key : tag.getAllKeys()) {
//			Tag value = tag.get(key);
//			if (value instanceof CompoundTag) {
//				setAllHealthTagsToZero((CompoundTag) value);
//			} else if (key.toLowerCase().contains("Health") || key.toLowerCase().contains("health")) {
//				switch (Objects.requireNonNull(value).getId()) {
//					case Tag.TAG_FLOAT:
//						tag.putFloat(key, 0f);
//						break;
//					case Tag.TAG_INT:
//						tag.putInt(key, 0);
//						break;
//					case Tag.TAG_DOUBLE:
//						tag.putDouble(key, 0);
//						break;
//				}
//			}
//		}
//	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
	}

	public MutableComponent getDisplayName() {
		return Component.translatable(this.getDescriptionId() + ".desc");
	}
}
