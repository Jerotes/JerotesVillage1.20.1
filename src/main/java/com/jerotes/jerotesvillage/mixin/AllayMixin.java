package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotes.entity.Interface.CarvedEntity;
import com.jerotes.jerotesvillage.entity.Neutral.CarvedAllayEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Allay.class)
public abstract class AllayMixin extends PathfinderMob {
    @Shadow protected abstract void resetDuplicationCooldown();

    protected AllayMixin(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Unique
    public void jerotes_$resetDuplicationCooldowns() {
        resetDuplicationCooldown();
    }

    @Inject(method = "duplicateAllay", at = @At("HEAD"), cancellable = true)
    private void duplicateAllay(CallbackInfo ci) {
        if (this instanceof CarvedEntity carved) {
            CarvedAllayEntity allay = JerotesVillageEntityType.CARVED_ALLAY.get().create(this.level());
            if (allay != null) {
                allay.moveTo(this.position());
                allay.setPersistenceRequired();
                ((AllayMixin)(Object)allay).jerotes_$resetDuplicationCooldowns();
                this.resetDuplicationCooldown();
                if (this.getType() == JerotesVillageEntityType.CARVED_ALLAY.get()) {
                    allay.setVexRace(carved.isVexRace());
                    CompoundTag compoundTag = new CompoundTag();
                    allay.addAdditionalSaveData(compoundTag);
                    ListTag listTag = compoundTag.getList("Trusted", 11);
                    for (Tag tag : listTag) {
                        allay.addTrustedUUID(NbtUtils.loadUUID(tag));
                    }
                    allay.readAdditionalSaveData(compoundTag);
                }
                this.level().addFreshEntity(allay);
            }
            ci.cancel();
        }
    }
}