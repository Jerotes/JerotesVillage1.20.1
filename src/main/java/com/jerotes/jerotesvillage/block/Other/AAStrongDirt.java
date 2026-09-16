package com.jerotes.jerotesvillage.block.Other;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

public class AAStrongDirt extends Block {
    public AAStrongDirt() {
        super(Properties.of().mapColor(MapColor.DIRT).sound(SoundType.GRAVEL).strength(-1.0f, 3600000.0f));
    }
}