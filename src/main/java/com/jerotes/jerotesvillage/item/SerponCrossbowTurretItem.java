package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.entity.Other.Machine.SerponCrossbowTurretEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.ArrowPiercingEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;
import java.util.List;

public class SerponCrossbowTurretItem extends Item {
	public SerponCrossbowTurretItem() {
		super(new Properties().stacksTo(1).rarity(Rarity.COMMON).fireResistant());
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		if (context.getLevel() instanceof ServerLevel serverLevel) {
			int m = context.getItemInHand().getEnchantmentLevel(Enchantments.MULTISHOT);
			int q = context.getItemInHand().getEnchantmentLevel(Enchantments.QUICK_CHARGE);
			int p = context.getItemInHand().getEnchantmentLevel(Enchantments.PIERCING);
			SerponCrossbowTurretEntity serponCrossbowTurret = new SerponCrossbowTurretEntity(JerotesVillageEntityType.SERPON_CROSSBOW_TURRET.get(), serverLevel);
			serponCrossbowTurret.setNotSerpon(true);
			serponCrossbowTurret.setMultishotLevel(m);
			serponCrossbowTurret.setQuickChargeLevel(q);
			serponCrossbowTurret.setPiercingLevel(p);
			serponCrossbowTurret.moveTo((context.getClickedPos().getX() + 0.5), (context.getClickedPos().getY() + 1), (context.getClickedPos().getZ() + 0.5), context.getLevel().getRandom().nextFloat() * 360F, 0);
			serponCrossbowTurret.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(serponCrossbowTurret.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
			serverLevel.addFreshEntity(serponCrossbowTurret);
			serverLevel.gameEvent(null, GameEvent.ENTITY_PLACE, context.getClickedPos());
			if (context.getPlayer() != null) {
				serverLevel.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, context.getClickedPos());
				context.getPlayer().awardStat(Stats.ITEM_USED.get(this));
				if (!context.getPlayer().getAbilities().instabuild) {
					context.getItemInHand().shrink(1);
				}
			}
			else {
				serverLevel.gameEvent(null, GameEvent.ENTITY_PLACE, context.getClickedPos());
			}
		}
		return InteractionResult.SUCCESS;
	}

	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		if (enchantment == Enchantments.MULTISHOT || enchantment == Enchantments.QUICK_CHARGE || enchantment == Enchantments.PIERCING) {
			return true;
		}
		if (enchantment instanceof ArrowPiercingEnchantment) {
			return true;
		}
		return super.canApplyAtEnchantingTable(stack, enchantment);
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
