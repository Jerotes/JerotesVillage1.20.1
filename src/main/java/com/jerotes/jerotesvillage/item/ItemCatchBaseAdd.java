package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.ItemCatchBase;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class ItemCatchBaseAdd extends ItemCatchBase {
	private final Supplier<? extends EntityType<?>> entityTypeSupplier;

	public ItemCatchBaseAdd(Supplier<? extends EntityType<?>> entitySupplier, Item.Properties properties) {
		super(entitySupplier, properties);
		this.entityTypeSupplier = entitySupplier;
	}

	@Override
	public String getDescriptionId(ItemStack itemStack) {
		return isQueen(itemStack) ? "item.jerotesvillage.captured_dust_moth_queen" : super.getDescriptionId(itemStack);
	}

	public static boolean isQueen(ItemStack itemStack) {
		CompoundTag compoundtag = itemStack.getTag();
		return compoundtag != null && (compoundtag.contains("IsQueen") && compoundtag.getBoolean("IsQueen"));
	}

	public static boolean isPupa(ItemStack itemStack) {
		CompoundTag compoundtag = itemStack.getTag();
		return compoundtag != null && (compoundtag.contains("IsPupa") && compoundtag.getBoolean("IsPupa"));
	}
	public static int getSize(ItemStack itemStack) {
		CompoundTag compoundtag = itemStack.getTag();
		if (compoundtag != null && compoundtag.contains("Size")) {
			return compoundtag.getInt("Size");
		}
		return 0;
	}

	public static void setQueen(ItemStack itemStack) {
		CompoundTag compoundtag = itemStack.hasTag() ? itemStack.getTag().copy() : new CompoundTag();
		compoundtag.putBoolean("IsQueen",  true);
		itemStack.setTag(compoundtag);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		if (getEntityType() == JerotesVillageEntityType.VIRTUAL_CAVE_COCKROACH.get()) {
			CompoundTag compoundtag = itemStack.getTag();
			if (compoundtag != null && compoundtag.contains("Size")) {
				ChatFormatting[] achatformatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
				MutableComponent mutablecomponent = Component.translatable("entity.jerotesvillage.virtual_cave_cockroach.size", getSize(itemStack));
				mutablecomponent.withStyle(achatformatting);
				list.add(mutablecomponent);
			}
		}
		if (getEntityType() == JerotesVillageEntityType.DOWNY_SPIDER.get()) {
			CompoundTag compoundtag = itemStack.getTag();
			if (compoundtag != null && compoundtag.contains("IsColorA") && compoundtag.contains("IsColorB")) {
				boolean isColorA = compoundtag.getBoolean("IsColorA");
				boolean isColorB = compoundtag.getBoolean("IsColorB");
				ChatFormatting[] achatformatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
				String color;
				MutableComponent mutablecomponent;
				if (isColorA && isColorB) {
					color = "color.jerotesvillage.downy_spider_color_purple";
					mutablecomponent = Component.translatable(color).withStyle(achatformatting).withStyle(ChatFormatting.DARK_PURPLE);
				}
				else if (isColorA) {
					color = "color.jerotesvillage.downy_spider_color_green";
					mutablecomponent = Component.translatable(color).withStyle(achatformatting).withStyle(ChatFormatting.GREEN);
				}
				else if (isColorB) {
					color = "color.jerotesvillage.downy_spider_color_blue";
					mutablecomponent = Component.translatable(color).withStyle(achatformatting).withStyle(ChatFormatting.BLUE);
				}
				else {
					color = "color.jerotesvillage.downy_spider_color_brown";
					mutablecomponent = Component.translatable(color).withStyle(achatformatting).withStyle(ChatFormatting.GOLD);
				}
				mutablecomponent.withStyle(achatformatting);
				list.add(mutablecomponent);
			}
		}
		if (getEntityType() == JerotesVillageEntityType.DUST_MOTH.get()) {
			CompoundTag compoundtag = itemStack.getTag();
			if (compoundtag != null && compoundtag.contains("IsColorA") && compoundtag.contains("IsColorB")) {
				boolean isColorA = compoundtag.getBoolean("IsColorA");
				boolean isColorB = compoundtag.getBoolean("IsColorB");
				ChatFormatting[] achatformatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
				String color;
				MutableComponent mutablecomponent;
				if (isColorA) {
					color = "color.jerotesvillage.dust_moth_color_brown";
					mutablecomponent = Component.translatable(color).withStyle(achatformatting).withStyle(ChatFormatting.GOLD);
				}
				else if (isColorB) {
					color = "color.jerotesvillage.dust_moth_color_pink";
					mutablecomponent = Component.translatable(color).withStyle(achatformatting).withStyle(ChatFormatting.LIGHT_PURPLE);
				}
				else {
					color = "color.jerotesvillage.dust_moth_color_white";
					mutablecomponent = Component.translatable(color).withStyle(achatformatting).withStyle(ChatFormatting.WHITE);
				}
				mutablecomponent.withStyle(achatformatting);
				list.add(mutablecomponent);
			}
		}
	}
}
