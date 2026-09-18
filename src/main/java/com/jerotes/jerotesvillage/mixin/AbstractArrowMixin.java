package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.entity.Humanoid.WildernessGiant.WildernessGiantEntity;
import com.jerotes.jerotesvillage.entity.Neutral.WildernessGiantSkeleton.WildernessGiantSkeletonEntity;
import com.jerotes.jerotesvillage.util.JerotesVillageChangeArrow;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin implements JerotesVillageChangeArrow {
	@Unique
	private static final EntityDataAccessor<Boolean> JEROTESVILLAGE$WILDERNESS_GIANT_ARROW =
			SynchedEntityData.defineId(AbstractArrow.class, EntityDataSerializers.BOOLEAN);

	@Override
	public boolean jerotes$isGiantArrow() {
		return ((AbstractArrow)(Object)this).getEntityData().get(JEROTESVILLAGE$WILDERNESS_GIANT_ARROW);
	}
	@Inject(method = "setOwner", at = @At("TAIL"))
	private void setOwner(Entity entity, CallbackInfo ci) {
		((AbstractArrow)(Object)this).getEntityData().set(JEROTESVILLAGE$WILDERNESS_GIANT_ARROW,
				entity instanceof WildernessGiantEntity || entity instanceof WildernessGiantSkeletonEntity);
	}
	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	private void addAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
		compoundTag.putBoolean("IsWildernessGiantArrow", ((AbstractArrow)(Object)this).getEntityData().get(JEROTESVILLAGE$WILDERNESS_GIANT_ARROW));
	}
	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	private void readAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
		((AbstractArrow)(Object)this).getEntityData().set(JEROTESVILLAGE$WILDERNESS_GIANT_ARROW, compoundTag.getBoolean("IsWildernessGiantArrow"));
	}
	@Inject(method = "defineSynchedData", at = @At("TAIL"))
	private void defineSynchedData(CallbackInfo ci) {
		((AbstractArrow)(Object)this).getEntityData().define(JEROTESVILLAGE$WILDERNESS_GIANT_ARROW, false);
	}

}
