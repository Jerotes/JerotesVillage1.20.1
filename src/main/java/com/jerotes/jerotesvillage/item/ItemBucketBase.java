package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class ItemBucketBase extends MobBucketItem {
	public ItemBucketBase(Supplier<? extends EntityType<?>> fishTypeIn, Fluid fluid, Item.Properties builder) {
		super(fishTypeIn, () -> fluid, () -> SoundEvents.BUCKET_EMPTY_FISH, builder.stacksTo(1));
	}


	public InteractionResultHolder<ItemStack> use(Level p_40703_, Player p_40704_, InteractionHand p_40705_) {
		ItemStack itemstack = p_40704_.getItemInHand(p_40705_);
		if (itemstack.is(JerotesVillageItems.HARMONIOUS_SLIME_BUCKET.get())) {
			BlockHitResult blockhitresult = getPlayerPOVHitResult(p_40703_, p_40704_, ClipContext.Fluid.SOURCE_ONLY);
			InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(p_40704_, p_40703_, itemstack, blockhitresult);
			if (ret != null) return ret;
			if (blockhitresult.getType() == HitResult.Type.MISS) {
				return InteractionResultHolder.pass(itemstack);
			} else if (blockhitresult.getType() != HitResult.Type.BLOCK) {
				return InteractionResultHolder.pass(itemstack);
			} else {
				BlockPos blockpos = blockhitresult.getBlockPos();
				Direction direction = blockhitresult.getDirection();
				BlockPos blockpos1 = blockpos.relative(direction);
				if (p_40703_.mayInteract(p_40704_, blockpos) && p_40704_.mayUseItemAt(blockpos1, direction, itemstack)) {
					BlockState blockstate = p_40703_.getBlockState(blockpos);
					BlockPos blockpos2 = canBlockContainFluid(p_40703_, blockpos, blockstate) ? blockpos : blockpos1;
					this.checkExtraContent(p_40704_, p_40703_, itemstack, blockpos2);
					if (p_40704_ instanceof ServerPlayer) {
						CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)p_40704_, blockpos2, itemstack);
					}

					p_40704_.awardStat(Stats.ITEM_USED.get(this));
					return InteractionResultHolder.sidedSuccess(getEmptySuccessItem(itemstack, p_40704_), p_40703_.isClientSide());
				}
			}
		}
		return super.use(p_40703_, p_40704_, p_40705_);
	}


	@Override
	public void checkExtraContent(@Nullable Player player, Level level, ItemStack stack, BlockPos pos) {
		if (level instanceof ServerLevel) {
			this.spawnFish((ServerLevel)level, stack, pos);
			level.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
		}
	}

	private void spawnFish(ServerLevel serverLevel, ItemStack stack, BlockPos pos) {
		Entity entity = getFishType().spawn(serverLevel, stack, (Player)null, pos, MobSpawnType.BUCKET, true, false);
		if (entity instanceof Bucketable) {
			Bucketable bucketable = (Bucketable)entity;
			bucketable.loadFromBucketTag(stack.getOrCreateTag());
			bucketable.setFromBucket(true);
		}
	}


	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		if (getFishType() == JerotesVillageEntityType.CHROMASNAP_BASS.get()) {
			CompoundTag compoundtag = itemStack.getTag();
			if (compoundtag != null && compoundtag.contains("BaseColor") && compoundtag.contains("Color1") && compoundtag.contains("Color2") && compoundtag.contains("Color3") && compoundtag.contains("Color4")) {
				int baseColor = compoundtag.getInt("BaseColor");
				int color1 = compoundtag.getInt("Color1");
				int color2 = compoundtag.getInt("Color2");
				int color3 = compoundtag.getInt("Color3");
				int color4 = compoundtag.getInt("Color4");
				ChatFormatting[] achatformatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
				String color;
				MutableComponent mutablecomponent;
				if (baseColor == 1) {
					color = "color.jerotesvillage.chromasnap_bass_color_yellow";
					mutablecomponent = Component.translatable(color).withStyle(achatformatting).withStyle(ChatFormatting.YELLOW);
				}
				else if (baseColor == 2) {
					color = "color.jerotesvillage.chromasnap_bass_color_green";
					mutablecomponent = Component.translatable(color).withStyle(achatformatting).withStyle(ChatFormatting.GREEN);
				}
				else if (baseColor == 3) {
					color = "color.jerotesvillage.chromasnap_bass_color_blue";
					mutablecomponent = Component.translatable(color).withStyle(achatformatting).withStyle(ChatFormatting.BLUE);
				}
				else {
					color = "color.jerotesvillage.chromasnap_bass_color_silver";
					mutablecomponent = Component.translatable(color).withStyle(achatformatting).withStyle(ChatFormatting.WHITE);
				}
				mutablecomponent.withStyle(achatformatting);
				list.add(mutablecomponent);
				MutableComponent mutablecomponent1 = Component.translatable("color.jerotesvillage.chromasnap_bass_color_1", color1).withStyle(achatformatting)
						//1.20.4↑//
						//.withColor(color1)
						;
				list.add(mutablecomponent1);
				MutableComponent mutablecomponent2 = Component.translatable("color.jerotesvillage.chromasnap_bass_color_2", color2).withStyle(achatformatting)
						//1.20.4↑//
						//.withColor(color2)
						;
				list.add(mutablecomponent2);
				MutableComponent mutablecomponent3 = Component.translatable("color.jerotesvillage.chromasnap_bass_color_3", color3).withStyle(achatformatting)
						//1.20.4↑//
						//.withColor(color3)
						;
				list.add(mutablecomponent3);
				MutableComponent mutablecomponent4 = Component.translatable("color.jerotesvillage.chromasnap_bass_color_4", color4).withStyle(achatformatting)
						//1.20.4↑//
						//.withColor(color4)
						;
				list.add(mutablecomponent4);
			}
		}
		if (getFishType() == JerotesVillageEntityType.LAKE_LOACH.get()) {
			CompoundTag compoundtag = itemStack.getTag();
			if (compoundtag != null && compoundtag.contains("Color")) {
				int baseColor = compoundtag.getInt("Color");
				ChatFormatting[] achatformatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
				String color;
				MutableComponent mutablecomponent;
				if (baseColor == 1) {
					color = "color.jerotesvillage.lake_loach_color_brown";
					mutablecomponent = Component.translatable(color).withStyle(achatformatting).withStyle(ChatFormatting.GOLD);
				}
				else if (baseColor == 2) {
					color = "color.jerotesvillage.lake_loach_color_green";
					mutablecomponent = Component.translatable(color).withStyle(achatformatting).withStyle(ChatFormatting.GREEN);
				}
				else if (baseColor == 3) {
					color = "color.jerotesvillage.lake_loach_color_blue";
					mutablecomponent = Component.translatable(color).withStyle(achatformatting).withStyle(ChatFormatting.BLUE);
				}
				else {
					color = "color.jerotesvillage.lake_loach_color_yellow";
					mutablecomponent = Component.translatable(color).withStyle(achatformatting).withStyle(ChatFormatting.YELLOW);
				}
				list.add(mutablecomponent);
			}
		}
		if (getFishType() == JerotesVillageEntityType.JEWELROAMER.get()) {
			CompoundTag compoundtag = itemStack.getTag();
			if (compoundtag != null && compoundtag.contains("BaseColor") && compoundtag.contains("AddColor") && compoundtag.contains("FishType")) {
				int baseColor = compoundtag.getInt("BaseColor");
				int addColor = compoundtag.getInt("AddColor");
				int fishType = compoundtag.getInt("FishType");
				ChatFormatting[] achatformatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
				MutableComponent mutablecomponent = Component.translatable("color.jerotesvillage.jewelroamer_base_color", baseColor).withStyle(achatformatting)
						//1.20.4↑//
						//.withColor(baseColor)
						;
				list.add(mutablecomponent);
				MutableComponent mutablecomponent1 = Component.translatable("color.jerotesvillage.jewelroamer_add_color", addColor).withStyle(achatformatting)
						//1.20.4↑//
						//.withColor(addColor)
						;
				list.add(mutablecomponent1);
				MutableComponent mutablecomponent2 = Component.translatable("color.jerotesvillage.jewelroamer_fish_type_" + fishType);
				mutablecomponent2.withStyle(achatformatting);
				list.add(mutablecomponent2);
			}
		}
		if (getFishType() == JerotesVillageEntityType.GREYSCALE.get()) {
			CompoundTag compoundtag = itemStack.getTag();
			if (compoundtag != null && compoundtag.contains("Size")) {
				int size = compoundtag.getInt("Size");
				ChatFormatting[] achatformatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
				MutableComponent mutablecomponent = Component.translatable("entity.jerotesvillage.greyscale.size", size);
				mutablecomponent.withStyle(achatformatting);
				list.add(mutablecomponent);
			}
		}
		if (getFishType() == JerotesVillageEntityType.HARMONIOUS_SLIME.get()) {
			CompoundTag compoundtag = itemStack.getTag();
			if (compoundtag != null && compoundtag.contains("Size")) {
				int size = compoundtag.getInt("Size");
				ChatFormatting[] achatformatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
				MutableComponent mutablecomponent = Component.translatable("entity.jerotesvillage.harmonious_slime.size", size);
				mutablecomponent.withStyle(achatformatting);
				list.add(mutablecomponent);
			}
		}
	}
}
