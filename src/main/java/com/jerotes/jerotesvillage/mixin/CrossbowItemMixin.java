package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.item.ShrapnelLauncher;
import com.jerotes.jerotes.item.Tool.*;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin extends ProjectileWeaponItem {

    public CrossbowItemMixin(Properties properties) {
        super(properties);
    }



    @Inject(method = "getShootingPower", at = @At(value = "HEAD"), cancellable = true)
    private static void getShootingPower(ItemStack itemStack, CallbackInfoReturnable<Float> cir) {
        if (itemStack.getItem() instanceof ItemToolBaseCrossbow itemToolBaseCrossBow) {
            cir.setReturnValue(itemToolBaseCrossBow.getShootingPower(itemStack));
            cir.cancel();
        }
    }

    @Inject(method = "getChargeDuration", at = @At(value = "HEAD"), cancellable = true)
    private static void getChargeDuration(ItemStack itemStack, CallbackInfoReturnable<Integer> cir) {
        if (itemStack.getItem() instanceof ItemToolBaseCrossbow itemToolBaseCrossBow) {
            cir.setReturnValue(itemToolBaseCrossBow.getChargeDurations(itemStack));
            cir.cancel();
        }
    }

    @Inject(method = "getStartSound", at = @At(value = "HEAD"), cancellable = true)
    private void getStartSound(int n, CallbackInfoReturnable<SoundEvent> cir) {
        if ((Object)this instanceof ShrapnelLauncher) {
            cir.setReturnValue(SoundEvents.TNT_PRIMED);
            cir.cancel();
        }
    }


}