package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.ChestedHorseModel;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.ChestedHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestedHorseRenderer.class)
public abstract class ChestedHorseRendererMixin<T extends AbstractChestedHorse> extends AbstractHorseRenderer<T, ChestedHorseModel<T>> {


    public ChestedHorseRendererMixin(EntityRendererProvider.Context context, ChestedHorseModel<T> chestedHorseModel, float f) {
        super(context, chestedHorseModel, f);
    }

    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/animal/horse/AbstractChestedHorse;)Lnet/minecraft/resources/ResourceLocation;", at = @At("HEAD"), cancellable = true)
    public void getTextureLocation(AbstractChestedHorse abstractChestedHorse, CallbackInfoReturnable<ResourceLocation> callbackInfoReturnable) {
        ResourceLocation JEROTES_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/jerotes_donkey.png");
        String string = ChatFormatting.stripFormatting(abstractChestedHorse.getName().getString());
        if ("Jerotes_".equals(string)) {
            callbackInfoReturnable.setReturnValue(JEROTES_LOCATION);
            callbackInfoReturnable.cancel();
        }
    }
}