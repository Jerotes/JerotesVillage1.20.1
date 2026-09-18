package com.jerotes.jerotesvillage.mixin;

import net.minecraft.world.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import javax.annotation.Nullable;
import java.util.UUID;

@Mixin(Projectile.class)
public interface ProjectileMixin {
	@Accessor("ownerUUID")
	@Nullable
	UUID jerotes$getOwnerUUID();
}
