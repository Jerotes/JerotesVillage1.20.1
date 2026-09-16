package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Interface.ItemSpecialEffect;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;

public class OminousTorch extends StandingAndWallBlockItem implements ItemSpecialEffect {
	public OminousTorch() {
		super(JerotesVillageBlocks.OMINOUS_TORCH.get(), JerotesVillageBlocks.OMINOUS_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN);
	}

	@Override
	public void attackUse(Entity self, Entity attackTo) {
		attackTo.setSecondsOnFire(5);
	}
}
