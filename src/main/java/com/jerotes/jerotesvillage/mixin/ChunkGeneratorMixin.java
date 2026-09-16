package com.jerotes.jerotesvillage.mixin;

import net.minecraft.world.level.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ChunkGenerator.class, priority = 900)
public abstract class ChunkGeneratorMixin {
}