package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.entity.Boss.Biome.SerponCombatExecuterEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.gameevent.GameEvent;

public class SerponCombatPortal extends Item {
	public SerponCombatPortal() {
		super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		if (context.getLevel() instanceof ServerLevel serverLevel) {
			SerponCombatExecuterEntity serponCombatExecuter = new SerponCombatExecuterEntity(JerotesVillageEntityType.SERPON_COMBAT_EXECUTER.get(), serverLevel);
			serponCombatExecuter.moveTo((context.getClickedPos().getX() + 0.5), (context.getClickedPos().getY() + 1), (context.getClickedPos().getZ() + 0.5), context.getLevel().getRandom().nextFloat() * 360F, 0);
			serponCombatExecuter.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(serponCombatExecuter.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
			serponCombatExecuter.setTrueCome(false);
			serverLevel.addFreshEntity(serponCombatExecuter);
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
}