package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.init.JerotesVillageBiomes;
import com.jerotes.jerotesvillage.init.JerotesVillageVillagerProfessions;
import com.jerotes.jerotes.item.Tool.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity {
    @Shadow public abstract void tick();

    protected MobMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "isSunBurnTick", at = @At("HEAD"), cancellable = true)
    private void isSunBurnTick(CallbackInfoReturnable<Boolean> cir) {
        if (this.level().getBiome(BlockPos.containing(this.getX(), this.getY(), this.getZ())).is(JerotesVillageBiomes.DAMAGED_RUINS)
                || (this.getType() == EntityType.ZOMBIE_VILLAGER && this instanceof VillagerDataHolder villagerDataHolder && villagerDataHolder.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_PIGLIN.get())) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}