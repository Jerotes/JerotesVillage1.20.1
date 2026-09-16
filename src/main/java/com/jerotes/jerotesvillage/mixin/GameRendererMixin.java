package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.item.BaseHagEye;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(method = "getNightVisionScale", at = @At("HEAD"), cancellable = true)
    private static void getNightVisionScale(LivingEntity living, float f, CallbackInfoReturnable<Float> cir) {
        if (living.hasEffect(JerotesVillageMobEffects.ZSIEIN_FOG.get())) {
            cir.setReturnValue(1.0f);
        }
        if (living.isUsingItem() && living.getUseItem().getItem() instanceof BaseHagEye) {
            cir.setReturnValue(1.0f);
        }
        if (living.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof BaseHagEye) {
            cir.setReturnValue(1.0f);
        }
        if (living.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.MEROR_STANDARD_HELMET.get() || living.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.MEROR_BOUNTY_HUNTER_HELMET.get()) {
            cir.setReturnValue(1.0f);
        }
    }
}