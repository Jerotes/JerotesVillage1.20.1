package com.jerotes.jerotesvillage.block.DroughtFire;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

public class SerponConnectionPoint extends Block {
	public SerponConnectionPoint() {
		super(Properties.of().instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(5.0f, 6.0f));
	}
}
