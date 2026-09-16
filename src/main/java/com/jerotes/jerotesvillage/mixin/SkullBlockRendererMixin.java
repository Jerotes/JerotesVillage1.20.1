package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.block.BaseBlock.ABaseBlock;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkullBlockRenderer.class)
public abstract class SkullBlockRendererMixin {
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void injectNewSkullType(CallbackInfo ci) {
        SkullBlockRenderer.SKIN_BY_TYPE.put(ABaseBlock.Types.SECOND_ROUNDER_ZOMBIE, new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_zombie.png"));
        SkullBlockRenderer.SKIN_BY_TYPE.put(ABaseBlock.Types.SECOND_ROUNDER_SKELETON, new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_skeleton_0.png"));
        SkullBlockRenderer.SKIN_BY_TYPE.put(ABaseBlock.Types.RESURRECTER, new ResourceLocation(JerotesVillage.MODID, "textures/entity/resurrecter.png"));
        SkullBlockRenderer.SKIN_BY_TYPE.put(ABaseBlock.Types.PURPLE_SAND_CREEPER, new ResourceLocation(JerotesVillage.MODID, "textures/entity/purple_sand_creeper.png"));
        SkullBlockRenderer.SKIN_BY_TYPE.put(ABaseBlock.Types.SPIRVE, new ResourceLocation(JerotesVillage.MODID, "textures/entity/spirve.png"));
        SkullBlockRenderer.SKIN_BY_TYPE.put(ABaseBlock.Types.TANGLER, new ResourceLocation(JerotesVillage.MODID, "textures/entity/tangler_head.png"));
        SkullBlockRenderer.SKIN_BY_TYPE.put(ABaseBlock.Types.BLAMER, new ResourceLocation(JerotesVillage.MODID, "textures/entity/blamer.png"));
    }
}