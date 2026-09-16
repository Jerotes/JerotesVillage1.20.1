package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.entity.Other.Machine.SerponChariotEntity;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;
import java.util.List;

public class SerponChariotItem extends Item {
	public SerponChariotItem() {
		super(new Properties().stacksTo(1).rarity(Rarity.COMMON).fireResistant());
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		if (context.getLevel() instanceof ServerLevel serverLevel) {
			SerponChariotEntity serponChariot = new SerponChariotEntity(JerotesVillageEntityType.SERPON_CHARIOT.get(), serverLevel);
			serponChariot.setNotSerpon(true);
			serponChariot.moveTo((context.getClickedPos().getX() + 0.5), (context.getClickedPos().getY() + 1), (context.getClickedPos().getZ() + 0.5), context.getLevel().getRandom().nextFloat() * 360F, 0);
			serponChariot.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(serponChariot.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
			serverLevel.addFreshEntity(serponChariot);
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

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
	}
	public MutableComponent getDisplayName() {
		return Component.translatable(this.getDescriptionId() + ".desc");
	}

}
