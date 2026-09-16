package com.jerotes.jerotesvillage.block.DamagedRuins;

import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class MerorMetalOres {
	public static class MerorMetalOre extends DropExperienceBlock {
		public MerorMetalOre() {
			//1.20.4↑//
//			super(UniformInt.of(5, 15), Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(30.0f, 1200.0f));
			//1.20.1//
			super(Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(30.0f, 1200.0f), UniformInt.of(5, 15));
		}
	}

	public static class DeepslateMerorMetalOre extends DropExperienceBlock {
		public DeepslateMerorMetalOre() {
			//1.20.4↑//
//			super(UniformInt.of(5, 15), Properties.ofLegacyCopy(JerotesVillageBlocks.MEROR_METAL_ORE.get()).mapColor(MapColor.DEEPSLATE).strength(35.0f, 1200.0f).sound(SoundType.DEEPSLATE));
			//1.20.1//
			super(Properties.copy(JerotesVillageBlocks.MEROR_METAL_ORE.get()).mapColor(MapColor.DEEPSLATE).strength(35.0f, 1200.0f).sound(SoundType.DEEPSLATE), UniformInt.of(5, 15));
		}
	}
}
