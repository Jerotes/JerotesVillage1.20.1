package com.jerotes.jerotesvillage.util;

import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class OtherParticlesUse {
    //盘罗仙蔓锁定
    public static void CelestialCoilvineLock(LivingEntity entity) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            if (entity.tickCount % 14 == 0) {
                for (int i = 0; i < 8; ++i) {
                    if (i == 0) {
                        Vec3 vec3 = entity.getPosition(0).add(2, 0, 2);
                        Vec3 vec31 = entity.getPosition(0).add(4, 2, 4);
                        Vec3 vec32 = vec31.subtract(vec3);
                        Vec3 vec33 = vec32.normalize();
                        Vec3 vec35 = entity.getPosition(0).add(3.75, 1.8, 3.75);
                        for (int i2 = 1; i2 < Mth.floor(vec32.length()) * 6 + 1; ++i2) {
                            Vec3 vec34 = vec3.add(vec33.scale(i2 / 6f));
                            serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_LOCK_VINE.get(),
                                    vec34.x, vec34.y, vec34.z, 0, 0.0, 0.0, 0.0, 0.0);
                        }
                        serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_LOCK.get(),
                                vec35.x, vec35.y, vec35.z, 0, 0.0, 0.0, 0.0, 0.0);
                    } else if (i == 1) {
                        Vec3 vec3 = entity.getPosition(0).add(2, 0, -2);
                        Vec3 vec31 = entity.getPosition(0).add(4, 2, -4);
                        Vec3 vec32 = vec31.subtract(vec3);
                        Vec3 vec33 = vec32.normalize();
                        Vec3 vec35 = entity.getPosition(0).add(3.75, 1.8, -3.75);
                        for (int i2 = 1; i2 < Mth.floor(vec32.length()) * 6 + 1; ++i2) {
                            Vec3 vec34 = vec3.add(vec33.scale(i2 / 6f));
                            serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_LOCK_VINE.get(),
                                    vec34.x, vec34.y, vec34.z, 0, 0.0, 0.0, 0.0, 0.0);
                        }
                        serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_LOCK.get(),
                                vec35.x, vec35.y, vec35.z, 0, 0.0, 0.0, 0.0, 0.0);
                    } else if (i == 2) {
                        Vec3 vec3 = entity.getPosition(0).add(-2, 0, 2);
                        Vec3 vec31 = entity.getPosition(0).add(-4, 2, 4);
                        Vec3 vec32 = vec31.subtract(vec3);
                        Vec3 vec33 = vec32.normalize();
                        Vec3 vec35 = entity.getPosition(0).add(-3.75, 1.8, 3.75);
                        for (int i2 = 1; i2 < Mth.floor(vec32.length()) * 6 + 1; ++i2) {
                            Vec3 vec34 = vec3.add(vec33.scale(i2 / 6f));
                            serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_LOCK_VINE.get(),
                                    vec34.x, vec34.y, vec34.z, 0, 0.0, 0.0, 0.0, 0.0);
                        }
                        serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_LOCK.get(),
                                vec35.x, vec35.y, vec35.z, 0, 0.0, 0.0, 0.0, 0.0);
                    } else if (i == 3) {
                        Vec3 vec3 = entity.getPosition(0).add(-2, 0, -2);
                        Vec3 vec31 = entity.getPosition(0).add(-4, 2, -4);
                        Vec3 vec32 = vec31.subtract(vec3);
                        Vec3 vec33 = vec32.normalize();
                        Vec3 vec35 = entity.getPosition(0).add(-3.75, 1.8, -3.75);
                        for (int i2 = 1; i2 < Mth.floor(vec32.length()) * 6 + 1; ++i2) {
                            Vec3 vec34 = vec3.add(vec33.scale(i2 / 6f));
                            serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_LOCK_VINE.get(),
                                    vec34.x, vec34.y, vec34.z, 0, 0.0, 0.0, 0.0, 0.0);
                        }
                        serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_LOCK.get(),
                                vec35.x, vec35.y, vec35.z, 0, 0.0, 0.0, 0.0, 0.0);
                    } else if (i == 4) {
                        Vec3 vec3 = entity.getPosition(0).add(2, 0, 2);
                        Vec3 vec31 = entity.getPosition(0).add(4, -2, 4);
                        Vec3 vec32 = vec31.subtract(vec3);
                        Vec3 vec33 = vec32.normalize();
                        Vec3 vec35 = entity.getPosition(0).add(3.75, -1.8, 3.75);
                        for (int i2 = 1; i2 < Mth.floor(vec32.length()) * 6 + 1; ++i2) {
                            Vec3 vec34 = vec3.add(vec33.scale(i2 / 6f));
                            serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_LOCK_VINE.get(),
                                    vec34.x, vec34.y, vec34.z, 0, 0.0, 0.0, 0.0, 0.0);
                        }
                        serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_LOCK.get(),
                                vec35.x, vec35.y, vec35.z, 0, 0.0, 0.0, 0.0, 0.0);
                    } else if (i == 5) {
                        Vec3 vec3 = entity.getPosition(0).add(2, 0, -2);
                        Vec3 vec31 = entity.getPosition(0).add(4, -2, -4);
                        Vec3 vec32 = vec31.subtract(vec3);
                        Vec3 vec33 = vec32.normalize();
                        Vec3 vec35 = entity.getPosition(0).add(3.75, -1.8, -3.75);
                        for (int i2 = 1; i2 < Mth.floor(vec32.length()) * 6 + 1; ++i2) {
                            Vec3 vec34 = vec3.add(vec33.scale(i2 / 6f));
                            serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_LOCK_VINE.get(),
                                    vec34.x, vec34.y, vec34.z, 0, 0.0, 0.0, 0.0, 0.0);
                        }
                        serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_LOCK.get(),
                                vec35.x, vec35.y, vec35.z, 0, 0.0, 0.0, 0.0, 0.0);
                    } else if (i == 6) {
                        Vec3 vec3 = entity.getPosition(0).add(-2, 0, 2);
                        Vec3 vec31 = entity.getPosition(0).add(-4, -2, 4);
                        Vec3 vec32 = vec31.subtract(vec3);
                        Vec3 vec33 = vec32.normalize();
                        Vec3 vec35 = entity.getPosition(0).add(-3.75, -1.8, 3.75);
                        for (int i2 = 1; i2 < Mth.floor(vec32.length()) * 6 + 1; ++i2) {
                            Vec3 vec34 = vec3.add(vec33.scale(i2 / 6f));
                            serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_LOCK_VINE.get(),
                                    vec34.x, vec34.y, vec34.z, 0, 0.0, 0.0, 0.0, 0.0);
                        }
                        serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_LOCK.get(),
                                vec35.x, vec35.y, vec35.z, 0, 0.0, 0.0, 0.0, 0.0);
                    } else {
                        Vec3 vec3 = entity.getPosition(0).add(-2, 0, -2);
                        Vec3 vec31 = entity.getPosition(0).add(-4, -2, -4);
                        Vec3 vec32 = vec31.subtract(vec3);
                        Vec3 vec33 = vec32.normalize();
                        Vec3 vec35 = entity.getPosition(0).add(-3.75, -1.8, -3.75);
                        for (int i2 = 1; i2 < Mth.floor(vec32.length()) * 6 + 1; ++i2) {
                            Vec3 vec34 = vec3.add(vec33.scale(i2 / 6f));
                            serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_LOCK_VINE.get(),
                                    vec34.x, vec34.y, vec34.z, 0, 0.0, 0.0, 0.0, 0.0);
                        }
                        serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_LOCK.get(),
                                vec35.x, vec35.y, vec35.z, 0, 0.0, 0.0, 0.0, 0.0);
                    }
                }
            }
        }
    }
    //水浪
    public static void WaterSpiral(LivingEntity livingEntity) {
        Level level = livingEntity.level();
        if (!(level instanceof ServerLevel serverLevel)) return;

        Vec3 position = livingEntity.position();
        int spiralArms = 8;
        int height = 35;
        int particlesPerLayer = 16;
        double radius = 6.0;

        for (int y = 0; y < height; y++) {
            double heightRatio = (double) y / height;
            double currentRadius = radius * (heightRatio * 0.5 + 0.25) * 2;

            for (int i = 0; i < spiralArms; i++) {
                double angle = Math.toRadians((i * 360.0 / spiralArms) + (y * 10));

                double x = position.x + Math.cos(angle) * currentRadius;
                double z = position.z + Math.sin(angle) * currentRadius;
                double yPos = position.y + y * 0.5;

                serverLevel.sendParticles(JerotesVillageParticleTypes.GEMSTONE_BUBBLE.get(), x, yPos, z, 2, 0.1, 0.1, 0.1, 0.05);
                serverLevel.sendParticles(ParticleTypes.SPLASH, x, yPos, z, 1, 0.1, 0.1, 0.1, 0.02);
            }
        }

        for (int y = 0; y < height; y++) {
            double yPos = position.y + y * 0.5;

            serverLevel.sendParticles(ParticleTypes.CLOUD, position.x, yPos, position.z, 3, 0.2, 0.2, 0.2, 0.1);
            serverLevel.sendParticles(JerotesVillageParticleTypes.GEMSTONE_BUBBLE.get(), position.x, yPos, position.z, 3, 0.2, 0.2, 0.2, 0.05);
        }

        for (int i = 0; i < particlesPerLayer; i++) {
            double angle = livingEntity.getRandom().nextDouble() * Math.PI * 2;
            double distance = livingEntity.getRandom().nextDouble() * 2.0;
            double x = position.x + Math.cos(angle) * distance;
            double z = position.z + Math.sin(angle) * distance;
            double y = position.y + height * 0.5;

            serverLevel.sendParticles(ParticleTypes.SPLASH, x, y, z, 5, 0.3, 0.3, 0.3, 0.1);
            serverLevel.sendParticles(ParticleTypes.FALLING_WATER, x, y + 1, z, 3, 0.2, 0.4, 0.2, 0.05);
        }
    }
    //魔法粒子
    public static void ParticleMagic(LivingEntity livingEntity){
        if (livingEntity.level() instanceof ServerLevel serverLevel) {
            Vec3 position = livingEntity.position().add(0, 0.1, 0);
            for (int i = 0; i < 36; i++) {
                double angle = (i / 36.0) * Math.PI * 2;
                double radius = 2.0 + livingEntity.getRandom().nextDouble() * 2.5;
                double x = position.x + Math.cos(angle) * radius;
                double y = position.y + 0.2 + (livingEntity.getRandom().nextDouble() * 0.3);
                double z = position.z + Math.sin(angle) * radius;
                serverLevel.sendParticles(ParticleTypes.WITCH, x, y, z, 2, 0, 0, 0, 0.02);
                serverLevel.sendParticles(ParticleTypes.ENCHANT, x, y, z, 2, 0, 0, 0, 0.05);
            }
            for (int i = 0; i < 60; i++) {
                double angle = (i / 60.0) * Math.PI * 2;
                double radius = 6 + livingEntity.getRandom().nextDouble() * 2;
                double x = position.x + Math.cos(angle) * radius;
                double y = position.y + livingEntity.getRandom().nextDouble() * 1.5;
                double z = position.z + Math.sin(angle) * radius;
                serverLevel.sendParticles(ParticleTypes.WITCH, x, y, z, 3, 0.1, 1.0, 0.1, 0.1);
            }
            for (int i = 0; i < 20; i++) {
                double x = position.x + (livingEntity.getRandom().nextDouble() - 0.5) * 4;
                double y = position.y + livingEntity.getRandom().nextDouble() * 1.5;
                double z = position.z + (livingEntity.getRandom().nextDouble() - 0.5) * 4;
                serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, x, y, z, 3, 0, 0, 0, 0.05);
            }
        }
    }

    //粒子
    public static void sendBallParticles(LivingEntity entity, SimpleParticleType simpleParticleType) {
        sendBallParticles(entity, simpleParticleType, false, 3.0f, 0.15f);
    }
    public static void sendBallParticles(LivingEntity entity, SimpleParticleType simpleParticleType, boolean bl, float radius, float speed) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            Vec3 playerPos = entity.position().add(0, entity.getY(0.5) - entity.getY(), 0);
            int particleCount = 40;

            for (int i = 0; i < particleCount; i++) {
                double angle = entity.getRandom().nextDouble() * Math.PI * 2;
                double inclination = entity.getRandom().nextDouble() * Math.PI;
                double offsetX = radius * Math.sin(inclination) * Math.cos(angle);
                double offsetY = radius * Math.cos(inclination);
                double offsetZ = radius * Math.sin(inclination) * Math.sin(angle);

                Vec3 particlePos;
                Vec3 velocity;

                if (bl) {
                    particlePos = playerPos;
                    Vec3 direction = new Vec3(offsetX, offsetY, offsetZ).normalize();
                    velocity = direction.scale(speed);
                } else {
                    particlePos = playerPos.add(offsetX, offsetY, offsetZ);
                    Vec3 direction = playerPos.subtract(particlePos).normalize();
                    velocity = direction.scale(speed);
                }

                serverLevel.sendParticles(simpleParticleType,
                        particlePos.x, particlePos.y, particlePos.z,
                        0, velocity.x, velocity.y, velocity.z, 1.0f);
            }
        }
    }
}

