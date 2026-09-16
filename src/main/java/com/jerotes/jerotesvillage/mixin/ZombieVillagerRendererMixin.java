package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.init.JerotesVillageVillagerProfessions;
import net.minecraft.client.model.ZombieVillagerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.ZombieVillagerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.ZombieVillager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ZombieVillagerRenderer.class)
public abstract class ZombieVillagerRendererMixin extends HumanoidMobRenderer<ZombieVillager, ZombieVillagerModel<ZombieVillager>> {
    public ZombieVillagerRendererMixin(EntityRendererProvider.Context context, ZombieVillagerModel<ZombieVillager> zombieVillagerZombieVillagerModel, float f) {
        super(context, zombieVillagerZombieVillagerModel, f);
    }

    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/monster/ZombieVillager;)Lnet/minecraft/resources/ResourceLocation;", at = @At("HEAD"), cancellable = true)
    public void getTextureLocation(ZombieVillager villager, CallbackInfoReturnable<ResourceLocation> callbackInfoReturnable) {
        ResourceLocation VILLAGER_BASE_SKIN = new ResourceLocation(JerotesVillage.MODID, "textures/entity/villager_no_head.png");
        if (villager.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_PIGLIN.get()) {
            callbackInfoReturnable.setReturnValue(VILLAGER_BASE_SKIN);
            callbackInfoReturnable.cancel();
        }
    }
}