package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.config.StructureFilterConfig;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
    private void onCheckStart(ChunkPos chunkPos, Structure structure, boolean skipKnown,
                              CallbackInfoReturnable<StructureCheckResult> cir) {
        ResourceLocation currentDim = this.dimension.location();
        ResourceLocation structureId = this.structureConfigs.getKey(structure);
        if (structureId == null) return;
        String modId = structureId.getNamespace();
        boolean allowed = StructureFilterConfig.isStructureAllowed(currentDim, modId);
        if (!allowed) {
            cir.setReturnValue(StructureCheckResult.START_NOT_PRESENT);
        }
    }
}
