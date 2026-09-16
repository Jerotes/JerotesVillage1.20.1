package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class WonderlinSmallBag extends Item {
	private static final String TAG_ITEMS = "Items";
	public static final int MAX_WEIGHT = 64;
	private static final int BUNDLE_IN_BUNDLE_WEIGHT = 4;
	private static final int BAR_COLOR = Mth.color(0.4f, 1.0f, 0.4f);

	public WonderlinSmallBag(Item.Properties properties) {
		super(properties);
	}

	public static float getFullnessDisplay(ItemStack itemStack) {
		return (float)WonderlinSmallBag.getContentWeight(itemStack) / 64.0f;
	}

	@Override
	public boolean overrideStackedOnOther(ItemStack itemStack, Slot slot, ClickAction clickAction, Player player) {
		if (clickAction != ClickAction.SECONDARY) {
			return false;
		}
		ItemStack itemStack3 = slot.getItem();
		if (itemStack3.isEmpty()) {
			this.playRemoveOneSound(player);
			WonderlinSmallBag.removeOne(itemStack).ifPresent(itemStack2 -> WonderlinSmallBag.add(itemStack, slot.safeInsert((ItemStack)itemStack2)));
		} else if (itemStack3.getItem().canFitInsideContainerItems()) {
			int n = (64 - WonderlinSmallBag.getContentWeight(itemStack)) / WonderlinSmallBag.getWeight(itemStack3);
			int n2 = WonderlinSmallBag.add(itemStack, slot.safeTake(itemStack3.getCount(), n, player));
			if (n2 > 0) {
				this.playInsertSound(player);
			}
		}
		return true;
	}

	@Override
	public boolean overrideOtherStackedOnMe(ItemStack itemStack2, ItemStack itemStack3, Slot slot, ClickAction clickAction, Player player, SlotAccess slotAccess) {
		if (clickAction != ClickAction.SECONDARY || !slot.allowModification(player)) {
			return false;
		}
		if (itemStack3.isEmpty()) {
			WonderlinSmallBag.removeOne(itemStack2).ifPresent(itemStack -> {
				this.playRemoveOneSound(player);
				slotAccess.set((ItemStack)itemStack);
			});
		} else {
			int n = WonderlinSmallBag.add(itemStack2, itemStack3);
			if (n > 0) {
				this.playInsertSound(player);
				itemStack3.shrink(n);
			}
		}
		return true;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (WonderlinSmallBag.dropContents(itemStack, player)) {
			this.playDropContentsSound(player);
			player.awardStat(Stats.ITEM_USED.get(this));
			return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
		}
		return InteractionResultHolder.fail(itemStack);
	}

	@Override
	public boolean isBarVisible(ItemStack itemStack) {
		return WonderlinSmallBag.getContentWeight(itemStack) > 0;
	}

	@Override
	public int getBarWidth(ItemStack itemStack) {
		return Math.min(1 + 12 * WonderlinSmallBag.getContentWeight(itemStack) / 64, 13);
	}

	@Override
	public int getBarColor(ItemStack itemStack) {
		return BAR_COLOR;
	}

	private static int add(ItemStack itemStack, ItemStack itemStack2) {
		if (itemStack2.isEmpty() || !itemStack2.getItem().canFitInsideContainerItems()) {
			return 0;
		}
		CompoundTag compoundTag = itemStack.getOrCreateTag();
		if (!compoundTag.contains(TAG_ITEMS)) {
			compoundTag.put(TAG_ITEMS, new ListTag());
		}
		int n = WonderlinSmallBag.getContentWeight(itemStack);
		int n2 = WonderlinSmallBag.getWeight(itemStack2);
		int n3 = Math.min(itemStack2.getCount(), (64 - n) / n2);
		if (n3 == 0) {
			return 0;
		}
		ListTag listTag = compoundTag.getList(TAG_ITEMS, 10);
		Optional<CompoundTag> optional = WonderlinSmallBag.getMatchingItem(itemStack2, listTag);
		if (optional.isPresent()) {
			CompoundTag compoundTag2 = optional.get();
			ItemStack itemStack3 = ItemStack.of(compoundTag2);
			itemStack3.grow(n3);
			itemStack3.save(compoundTag2);
			listTag.remove(compoundTag2);
			listTag.add(0, compoundTag2);
		} else {
			ItemStack itemStack4 = itemStack2.copyWithCount(n3);
			CompoundTag compoundTag3 = new CompoundTag();
			itemStack4.save(compoundTag3);
			listTag.add(0, compoundTag3);
		}
		return n3;
	}

	private static Optional<CompoundTag> getMatchingItem(ItemStack itemStack, ListTag listTag) {
		if (itemStack.is(JerotesVillageItems.WONDERLIN_SMALL_BAG.get())) {
			return Optional.empty();
		}
		if (itemStack.is(Items.BUNDLE)) {
			return Optional.empty();
		}
		return listTag.stream().filter(CompoundTag.class::isInstance).map(CompoundTag.class::cast).filter(compoundTag -> ItemStack.isSameItemSameTags(ItemStack.of(compoundTag), itemStack)).findFirst();
	}

	private static int getWeight(ItemStack itemStack) {
		CompoundTag compoundTag;
		if (itemStack.is(JerotesVillageItems.WONDERLIN_SMALL_BAG.get())) {
			return 4 + WonderlinSmallBag.getContentWeight(itemStack);
		}
		if (itemStack.is(Items.BUNDLE)) {
			return 4 + WonderlinSmallBag.getContentWeight(itemStack);
		}
		if ((itemStack.is(Items.BEEHIVE) || itemStack.is(Items.BEE_NEST)) && itemStack.hasTag() && (compoundTag = BlockItem.getBlockEntityData(itemStack)) != null && !compoundTag.getList("Bees", 10).isEmpty()) {
			return 64;
		}
		return 64 / itemStack.getMaxStackSize();
	}

	private static int getContentWeight(ItemStack itemStack2) {
		return WonderlinSmallBag.getContents(itemStack2).mapToInt(itemStack -> WonderlinSmallBag.getWeight(itemStack) * itemStack.getCount()).sum();
	}

	private static Optional<ItemStack> removeOne(ItemStack itemStack) {
		CompoundTag compoundTag = itemStack.getOrCreateTag();
		if (!compoundTag.contains(TAG_ITEMS)) {
			return Optional.empty();
		}
		ListTag listTag = compoundTag.getList(TAG_ITEMS, 10);
		if (listTag.isEmpty()) {
			return Optional.empty();
		}
		boolean bl = false;
		CompoundTag compoundTag2 = listTag.getCompound(0);
		ItemStack itemStack2 = ItemStack.of(compoundTag2);
		listTag.remove(0);
		if (listTag.isEmpty()) {
			itemStack.removeTagKey(TAG_ITEMS);
		}
		return Optional.of(itemStack2);
	}

	private static boolean dropContents(ItemStack itemStack, Player player) {
		CompoundTag compoundTag = itemStack.getOrCreateTag();
		if (!compoundTag.contains(TAG_ITEMS)) {
			return false;
		}
		if (player instanceof ServerPlayer) {
			ListTag listTag = compoundTag.getList(TAG_ITEMS, 10);
			for (int i = 0; i < listTag.size(); ++i) {
				CompoundTag compoundTag2 = listTag.getCompound(i);
				ItemStack itemStack2 = ItemStack.of(compoundTag2);
				player.drop(itemStack2, true);
			}
		}
		itemStack.removeTagKey(TAG_ITEMS);
		return true;
	}

	private static Stream<ItemStack> getContents(ItemStack itemStack) {
		CompoundTag compoundTag = itemStack.getTag();
		if (compoundTag == null) {
			return Stream.empty();
		}
		ListTag listTag = compoundTag.getList(TAG_ITEMS, 10);
		return listTag.stream().map(CompoundTag.class::cast).map(ItemStack::of);
	}

	@Override
	public Optional<TooltipComponent> getTooltipImage(ItemStack itemStack) {
		NonNullList<ItemStack> nonNullList = NonNullList.create();
		WonderlinSmallBag.getContents(itemStack).forEach(nonNullList::add);
		return Optional.of(new BundleTooltip(nonNullList, WonderlinSmallBag.getContentWeight(itemStack)));
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(Component.translatable("item.jerotesvillage.wonderlin_small_bag.fullness", WonderlinSmallBag.getContentWeight(itemStack), 64).withStyle(ChatFormatting.GRAY));
	}

	@Override
	public void onDestroyed(ItemEntity itemEntity) {
		ItemUtils.onContainerDestroyed(itemEntity, WonderlinSmallBag.getContents(itemEntity.getItem()));
	}

	private void playRemoveOneSound(Entity entity) {
		entity.playSound(SoundEvents.GRASS_BREAK, 0.8f, 0.8f + entity.level().getRandom().nextFloat() * 0.4f);
	}

	private void playInsertSound(Entity entity) {
		entity.playSound(SoundEvents.GRASS_PLACE, 0.8f, 0.8f + entity.level().getRandom().nextFloat() * 0.4f);
	}

	private void playDropContentsSound(Entity entity) {
		entity.playSound(SoundEvents.GRASS_FALL, 0.8f, 0.8f + entity.level().getRandom().nextFloat() * 0.4f);
	}
}