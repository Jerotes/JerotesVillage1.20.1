package com.jerotes.jerotesvillage.entity.Interface;

import com.jerotes.jerotes.util.ParticlesUse;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

public interface MerorTransportEntity {
    default void transportSoon(Mob mob) {
        if (mob.level() instanceof ServerLevel serverLevel) {
            Vec3 vec3 = mob.getDeltaMovement();
            for (int i = 0; i < 5; ++i) {
                float f2 = (mob.getRandom().nextFloat() * 4.0f - 1.0f) * mob.getBbWidth() * 1f;
                float f3 = (mob.getRandom().nextFloat() * 4.0f - 1.0f) * mob.getBbWidth() * 1f;
                serverLevel.sendParticles(JerotesVillageParticleTypes.MEROR.get(), mob.getX() + (double) f2, (mob.getY() + mob.getEyeY()) + (double) f2, mob.getZ() + (double) f3, 0, vec3.x, vec3.y - 0.1, vec3.z, 0);
                serverLevel.sendParticles(JerotesVillageParticleTypes.MEROR.get(), mob.getRandomX(1.0), mob.getRandomY(), mob.getRandomZ(1.0), 0, 0.0, 0.0, 0.0, 0);
            }
        }
    }

    default void transport(Mob mob) {
        if (mob.level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 60; ++i) {
                serverLevel.sendParticles(JerotesVillageParticleTypes.MEROR.get(), mob.getRandomX(1.5), mob.getRandomY(), mob.getRandomZ(1.5), 0, 0.0, 0.0, 0.0, 0);
            }
        }
        if (!mob.isSilent()) {
            mob.level().playSound(null, mob.getX(), mob.getY(), mob.getZ(), JerotesVillageSoundEvents.MEROR_TELEPORT, mob.getSoundSource(), 5.0f, 1.0F);
        }
        if (mob.level() instanceof ServerLevel serverLevel) {
            ParticlesUse.summonParticle(serverLevel, mob, mob.getX(), mob.getY(), mob.getZ(),
                    0xff4f6b, 0xfdb9c4);
        }
        mob.remove(Entity.RemovalReason.CHANGED_DIMENSION);
    }
}

