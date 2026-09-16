package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.init.JerotesVillageVillagerProfessions;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerRenderer.class)
public abstract class VillagerRendererMixin extends MobRenderer<Villager, VillagerModel<Villager>> {
    public VillagerRendererMixin(EntityRendererProvider.Context context, VillagerModel<Villager> villagerVillagerModel, float f) {
        super(context, villagerVillagerModel, f);
    }

    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/npc/Villager;)Lnet/minecraft/resources/ResourceLocation;", at = @At("HEAD"), cancellable = true)
    public void getTextureLocation(Villager villager, CallbackInfoReturnable<ResourceLocation> callbackInfoReturnable) {
        ResourceLocation VILLAGER_BASE_SKIN = new ResourceLocation(JerotesVillage.MODID, "textures/entity/villager_no_head.png");
        if (villager.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_PIGLIN.get()) {
            callbackInfoReturnable.setReturnValue(VILLAGER_BASE_SKIN);
            callbackInfoReturnable.cancel();
        }
    }
}