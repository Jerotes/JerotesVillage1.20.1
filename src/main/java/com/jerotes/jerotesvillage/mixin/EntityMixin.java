package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Interface.GoastEntity;
import com.jerotes.jerotesvillage.util.OtherEntityFactionFind;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.entity.EntityInLevelCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow private EntityInLevelCallback levelCallback;

    @Inject(method = "isOnFire", at = @At("HEAD"), cancellable = true)
    private void isOnFire(CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof GoastEntity goastEntity && goastEntity.isGoast()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (((Entity)((Object)this)) instanceof LivingEntity livingEntity) {
            if (!(livingEntity instanceof Player) && !OtherEntityFactionFind.isZsiein(livingEntity.getType())) {
                if (Main.getJerotesPersistentData(livingEntity).getDouble("jerotesvillage_variant_zsiein_discard") >= 60000) {
                    DamageSource damageSource = AttackFind.findDamageType(livingEntity, DamageTypes.GENERIC_KILL, livingEntity);
                   livingEntity.hurt(livingEntity.damageSources().genericKill(), Float.MAX_VALUE);
                    livingEntity.hurt(damageSource, Float.MAX_VALUE);
                    levelCallback.onRemove(Entity.RemovalReason.CHANGED_DIMENSION);
                }
            }
        }
    }

//    @Inject(method = "setRemoved", at = @At("HEAD"))
//    public final void setRemoved(Entity.RemovalReason removalReason, CallbackInfo ci) {
//        if (((Entity)((Object)this)) instanceof VariantZsieinEntity variantZsiein && !(variantZsiein.isWantAway() && variantZsiein.getExaltedAway() <= -720) && variantZsiein.level() instanceof ServerLevel serverLevel && WorldEvent.ZsieinExtremelyInfectedAbove(serverLevel)) {
//            jerotes_$summon(variantZsiein, serverLevel);
//        }
//    }
//
//    @Unique
//    public void jerotes_$summon(VariantZsieinEntity variantZsiein, ServerLevel serverLevel) {
//        PlayerTeam teams = (PlayerTeam) variantZsiein.getTeam();
//        CompoundTag compoundTag = new CompoundTag();
//        VariantZsieinEntity variantZsiein1 = JerotesVillageEntityType.VARIANT_ZSIEIN.get().spawn(serverLevel, compoundTag, null, variantZsiein.getOnPos(), MobSpawnType.CONVERSION, true, true);
//        if (variantZsiein1 != null) {
//            if (teams != null) {
//                serverLevel.getScoreboard().addPlayerToTeam(variantZsiein1.getStringUUID(), teams);
//            }
//            variantZsiein1.setPos(variantZsiein.getX(), variantZsiein.getY(), variantZsiein.getZ());
//            variantZsiein1.moveToTrue(variantZsiein.getX(), variantZsiein.getY(), variantZsiein.getZ());
//            variantZsiein1.setUUID(variantZsiein.getUUID());
//            if (variantZsiein.getBossZsieinTarget() != null) {
//                variantZsiein1.setBossZsieinTarget(variantZsiein.getBossZsieinTarget());
//                if (!variantZsiein.getBossZsieinTarget().level().isClientSide()) {
//                    Main.getJerotesPersistentData(variantZsiein.getBossZsieinTarget()).putDouble("jerotesvillage_variant_zsiein_discard", Main.getJerotesPersistentData(variantZsiein.getTarget()).getDouble("jerotesvillage_variant_zsiein_discard") + 200);
//                }
//            }
//        }
//    }
    @Inject(method = "getTypeName", at = @At("HEAD"), cancellable = true)
    private void getTypeName(CallbackInfoReturnable<Component> cir) {
        if (this instanceof GoastEntity goastEntity && goastEntity.isGoast())
            cir.setReturnValue(Component.translatable("entity.jerotesvillage.goast"));

    }
}