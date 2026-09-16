package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.entity.Other.Other.BossShowEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class ItemBossDrop extends Item {
	private final Supplier<? extends EntityType<? extends BossShowEntity>> entityTypeSupplier;
	private final Supplier<SoundEvent> soundEventSupplier;

	public ItemBossDrop(Supplier<? extends EntityType<? extends BossShowEntity>> entityTypeSupplier,
						Supplier<SoundEvent> soundEventSupplier) {
		super(new Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC));
		this.entityTypeSupplier = entityTypeSupplier;
		this.soundEventSupplier = soundEventSupplier;
	}

	public Supplier<? extends EntityType<? extends BossShowEntity>> getBossEntity() {
		return entityTypeSupplier;
	}
	public EntityType<? extends BossShowEntity> getBossEntityType() {
		return entityTypeSupplier.get();
	}

	public SoundEvent getBossSoundEvent() {
		return soundEventSupplier.get();
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(this.getDisplayName().withStyle(ChatFormatting.LIGHT_PURPLE));
	}
	public MutableComponent getDisplayName() {
		return Component.translatable(this.getDescriptionId() + ".desc");
	}

	@Override
	public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
		return new ItemStack(this);
	}
}
