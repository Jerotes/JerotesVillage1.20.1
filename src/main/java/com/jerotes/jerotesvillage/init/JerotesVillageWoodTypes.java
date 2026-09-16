package com.jerotes.jerotesvillage.init;

import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;

public class JerotesVillageWoodTypes {
	public static final WoodType BITTER_COLD = WoodType.register(new WoodType(new ResourceLocation(JerotesVillage.MODID, "bitter_cold").toString(), JerotesVillageBlockSetTypes.BITTER_COLD));
	public static final WoodType WONDERLAND = WoodType.register(new WoodType(new ResourceLocation(JerotesVillage.MODID, "wonderland").toString(), JerotesVillageBlockSetTypes.WONDERLAND));
	public static final WoodType BLACK_YELLOW = WoodType.register(new WoodType(new ResourceLocation(JerotesVillage.MODID, "black_yellow").toString(), JerotesVillageBlockSetTypes.BLACK_YELLOW));
	public static final WoodType BRIGHT_GLOW = WoodType.register(new WoodType(new ResourceLocation(JerotesVillage.MODID, "bright_glow").toString(), JerotesVillageBlockSetTypes.BRIGHT_GLOW));
	public static void init() {
	}
}
