package com.jerotes.jerotesvillage.block.VirtualCave;

import com.jerotes.jerotes.init.JerotesMobEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;

public class SliderBlock extends SlimeBlock {
	public SliderBlock() {
		super(Properties.of().mapColor(MapColor.COLOR_GREEN).friction(0.8f).sound(SoundType.SLIME_BLOCK).noOcclusion());
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable BlockGetter blockGetter, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
		super.appendHoverText(itemStack, blockGetter, list, tooltipFlag);
	}
	public boolean isSlimeBlock(BlockState state) {
		return true;
	}
	public boolean isStickyBlock(BlockState state) {
		return true;
	}

	public boolean canStickTo(BlockState state, BlockState other) {
		return !(state.isStickyBlock() && other.isStickyBlock());
	}
	public MutableComponent getDisplayName() {
		return Component.translatable(this.getDescriptionId() + ".desc");
	}

	protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 15, 16.0);

	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return SHAPE;
	}


	@Override
	public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
		if (!level.isClientSide()) {
			if (entity instanceof LivingEntity livingEntity && livingEntity.tickCount % 40 == 1) {
				livingEntity.addEffect(new MobEffectInstance(JerotesMobEffects.CORROSIVE.get(), 20, 0, false, false));
			}
		}
	}
}
