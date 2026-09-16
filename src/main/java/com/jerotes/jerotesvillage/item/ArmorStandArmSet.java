package com.jerotes.jerotesvillage.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.NotNull;

public class ArmorStandArmSet extends Item implements Vanishable {
	public ArmorStandArmSet() {
		super(new Properties().stacksTo(1).rarity(Rarity.COMMON).durability(12));
	}

	@Override
	public boolean isValidRepairItem(@NotNull ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(Items.STICK) || super.isValidRepairItem(itemStack, itemStack2);
	}
}
