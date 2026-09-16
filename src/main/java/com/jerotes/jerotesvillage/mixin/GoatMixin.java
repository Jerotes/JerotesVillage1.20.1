package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.entity.Interface.GoastEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageBiomes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Goat.class)
public abstract class GoatMixin extends Animal implements GoastEntity {
    @Unique
    private static final UniformInt TIME_BETWEEN_LONG_JUMPS = UniformInt.of(600, 1200);
    @Unique
    private static final UniformInt TIME_BETWEEN_RAMS = UniformInt.of(600, 6000);

    protected GoatMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    private static final EntityDataAccessor<Boolean> IS_GOAST = SynchedEntityData.defineId(GoatMixin.class, EntityDataSerializers.BOOLEAN);
    public boolean isGoast() {
        return this.getEntityData().get(IS_GOAST);
    }
    public void setGoast(boolean bl) {
        this.getEntityData().set(IS_GOAST, bl);
    }
    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void addAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        compoundTag.putBoolean("IsGoast", this.isGoast());
    }
    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        this.setGoast(compoundTag.getBoolean("IsGoast"));
    }
    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    protected void defineSynchedData(CallbackInfo ci) {
        this.getEntityData().define(IS_GOAST, false);
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    protected void aiStep(CallbackInfo ci) {
        if (isGoast() && this.deathTime == 5 && !this.isAlive()) {
            this.spawnAtLocation(Items.GHAST_TEAR);
        }
    }

    @Inject(method = "finalizeSpawn", at = @At("HEAD"))
    private void finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, SpawnGroupData p_21437_, CompoundTag p_21438_, CallbackInfoReturnable<SpawnGroupData> cir) {
        if (this.level().getBiome(BlockPos.containing(this.getX(), this.getY(), this.getZ())).is(JerotesVillageBiomes.DROUGHT_FIRE)
                && (this.getType() == EntityType.GOAT)) {
            this.setGoast(true);
        }
    }
    @Inject(method = "getBreedOffspring(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/AgeableMob;)Lnet/minecraft/world/entity/animal/goat/Goat;", at = @At("HEAD"), cancellable = true)
    private void getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob, CallbackInfoReturnable<Goat> cir) {
        if (this.isGoast() || ageableMob instanceof GoastEntity goastEntity && goastEntity.isGoast()) {
            Goat goatBaby = EntityType.GOAT.create(serverLevel);
            if (goatBaby != null) {
                boolean var10000;
                label22:
                {
                    label21:
                    {
                        goatBaby.getBrain().setMemory(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, TIME_BETWEEN_LONG_JUMPS.sample(serverLevel.getRandom()));
                        goatBaby.getBrain().setMemory(MemoryModuleType.RAM_COOLDOWN_TICKS, TIME_BETWEEN_RAMS.sample(serverLevel.getRandom()));
                        AgeableMob ageableMob1 = serverLevel.getRandom().nextBoolean() ? this : ageableMob;
                        if (ageableMob1 instanceof Goat goat) {
                            if (goat.isScreamingGoat()) {
                                break label21;
                            }
                        }

                        if (!(serverLevel.getRandom().nextDouble() < 0.02)) {
                            var10000 = false;
                            break label22;
                        }
                    }

                    var10000 = true;
                }

                boolean $$5 = var10000;
                goatBaby.setScreamingGoat($$5);
                if (goatBaby instanceof GoastEntity goastEntity) {
                    goastEntity.setGoast(true);
                }
            }

            cir.setReturnValue(goatBaby);
            cir.cancel();
        }
    }
}