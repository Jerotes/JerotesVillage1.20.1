package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.event.WorldEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NaturalSpawner.class)
public abstract class NaturalSpawnerMixin {


    @Inject(method = "spawnForChunk", at = @At(value = "HEAD"), cancellable = true)
    private static void spawnForChunk(ServerLevel serverLevel, LevelChunk p_47031_, NaturalSpawner.SpawnState p_47032_, boolean p_47033_, boolean p_47034_, boolean p_47035_, CallbackInfo ci) {
        if (WorldEvent.ZsieinSevereInfectedAbove(serverLevel)) {
            ci.cancel();
        }
    }
}