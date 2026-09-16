package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Interface.GoastEntity;
import net.minecraft.client.model.GoatModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.GoatRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.goat.Goat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GoatRenderer.class)
public abstract class GoatRendererMixin extends MobRenderer<Goat, GoatModel<Goat>> {

    public GoatRendererMixin(EntityRendererProvider.Context p_174304_, GoatModel<Goat> p_174305_, float p_174306_) {
        super(p_174304_, p_174305_, p_174306_);
    }

    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/animal/goat/Goat;)Lnet/minecraft/resources/ResourceLocation;", at = @At("HEAD"), cancellable = true)
    public void getTextureLocation(Goat goat, CallbackInfoReturnable<ResourceLocation> cir) {
        ResourceLocation GOAST_SKIN = new ResourceLocation(JerotesVillage.MODID, "textures/entity/goast.png");
        if (goat instanceof GoastEntity goastEntity && goastEntity.isGoast()) {
            cir.setReturnValue(GOAST_SKIN);
            cir.cancel();
        }
    }
}