package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.event.WorldEvent;
import com.jerotes.jerotesvillage.network.JerotesVillageLevelData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public abstract class LevelMixin implements LevelAccessor {

    @Shadow
    public abstract long getDayTime();

    @Inject(method = "getCurrentDifficultyAt", at = @At(value = "HEAD"), cancellable = true)
    public void getCurrentDifficultyAt(BlockPos blockPos, CallbackInfoReturnable<DifficultyInstance> cir) {
        Level level = (Level)(Object)this;
        if (WorldEvent.ZsieinSevereInfectedAbove(level)) {
            long l = 3600000;
            float f = 1.0f;
            cir.setReturnValue(new DifficultyInstance(this.getDifficulty(), this.getDayTime(), l, f));
            cir.cancel();
        }
        if (JerotesVillageLevelData.get(level) != null && JerotesVillageLevelData.get(level).WickedRidiculeNight) {
            long l = 3600000;
            float f = 1.0f;
            cir.setReturnValue(new DifficultyInstance(this.getDifficulty(), this.getDayTime(), l, f));
            cir.cancel();
        }
    }
}