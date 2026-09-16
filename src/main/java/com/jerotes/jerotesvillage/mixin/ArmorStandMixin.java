package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.item.ArmorStandArmSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorStand.class)
public abstract class ArmorStandMixin extends LivingEntity {
    protected ArmorStandMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "interactAt", at = @At("HEAD"), cancellable = true)
    private void interactAt(Player player, Vec3 vec3, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (itemStack.getItem() instanceof ArmorStandArmSet) {
            if ((Object) this instanceof ArmorStand armorStand) {
                if (!armorStand.isShowArms()) {
                    player.swing(interactionHand);
                    armorStand.setShowArms(true);
                    itemStack.hurtAndBreak(1, player, players -> players.broadcastBreakEvent(interactionHand));
                    if (!player.isSilent()) {
                        player.level().playSound(null, player, SoundEvents.ARMOR_STAND_PLACE, player.getSoundSource(), 1.0f, 1.0f);
                    }
                    cir.setReturnValue(InteractionResult.SUCCESS);
                    cir.cancel();
                }
            }
        }
    }
}