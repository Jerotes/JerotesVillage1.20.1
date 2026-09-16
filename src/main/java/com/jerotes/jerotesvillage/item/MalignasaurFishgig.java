package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.entity.Shoot.Arrow.BaseJavelinEntity;
import com.jerotes.jerotes.item.Tool.ItemToolBaseJavelin;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.ThrownMalignasaurFishgigEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class MalignasaurFishgig extends ItemToolBaseJavelin {
	public MalignasaurFishgig() {
		super(new Properties().stacksTo(1).durability(780).rarity(Rarity.COMMON), 9f, 1.0f);
	}

	@Override
	public BaseJavelinEntity JerotesThrownJavelin(LivingEntity livingEntity, ItemStack itemStack) {
		return new ThrownMalignasaurFishgigEntity(livingEntity.level(), livingEntity, itemStack);
	}
	public float getThrowingDamage() {
		return 8.0f;
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(JerotesVillageItems.MALIGNASAUR_TEETH.get()) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
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

	@Override
	public int getEnchantmentValue() {
		return 15;
	}
}


