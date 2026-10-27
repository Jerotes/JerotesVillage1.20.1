package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.init.JerotesVillageDimensions;
import com.jerotes.jerotesvillage.util.StructureFilter;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureCheck;
import net.minecraft.world.level.levelgen.structure.StructureCheckResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StructureCheck.class)
public class StructureCheckMixin {

    @Final
    @Shadow
    private ResourceKey<Level> dimension;

    @Final
    @Shadow
    private Registry<Structure> structureConfigs;

    @Inject(method = "checkStart", at = @At("HEAD"), cancellable = true)
    private void jerotesvillage$filterStructureInSecondRoundWorld(ChunkPos chunkPos, Structure structure, boolean skipKnown,
                                                                  CallbackInfoReturnable<StructureCheckResult> cir) {
        if (!this.dimension.location().equals(JerotesVillageDimensions.SECOND_ROUND_WORLD.location())) {
            return;
        }
        if (StructureFilter.isAllowed(this.structureConfigs, structure)) {
            return;
        }
        cir.setReturnValue(StructureCheckResult.START_NOT_PRESENT);
    }
}
