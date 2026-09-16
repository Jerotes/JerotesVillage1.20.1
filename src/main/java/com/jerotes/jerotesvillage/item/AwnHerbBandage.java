package com.jerotes.jerotesvillage.item;


import com.jerotes.jerotes.item.Tool.ItemToolBaseBandage;
import net.minecraft.world.item.Rarity;

public class AwnHerbBandage extends ItemToolBaseBandage {
	public AwnHerbBandage() {
		super(new Properties().stacksTo(64).rarity(Rarity.UNCOMMON));
	}

	@Override
	public float getHealth() {
		return 14.0f;
	}
}
