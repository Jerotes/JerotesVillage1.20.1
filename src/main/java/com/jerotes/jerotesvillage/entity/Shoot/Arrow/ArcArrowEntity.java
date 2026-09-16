package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotes.entity.Shoot.Arrow.BaseArrowEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ArcArrowEntity extends BaseArrowEntity {
    public static final ItemStack item = new ItemStack(JerotesVillageItems.ARC_ARROW.get());
    private static final EntityType<ArcArrowEntity> type = JerotesVillageEntityType.ARC_ARROW.get();

    public ArcArrowEntity(EntityType<? extends ArcArrowEntity> entityType, Level level) {
        super(entityType, level, item, 3.0f);
    }
    public ArcArrowEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
        super(level, livingEntity, itemStack, type, 3.0f);
    }
    public ArcArrowEntity(Level level, double d, double d2, double d3, ItemStack itemStack) {
        super(level, d, d2, d3, itemStack, type, 3.0f);
    }

    public void tick() {
        super.tick();

        if (!this.isRemoved()) {
            if (!this.inGround && !this.isNoPhysics()) {
                Vec3 vec3 = this.getDeltaMovement();
                double d5 = vec3.x;
                double d6 = vec3.y;
                double d1 = vec3.z;
                for(int i = 0; i < 4; ++i) {
                    this.level().addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX() + d5 * (double)i / 4.0, this.getY() + d6 * (double)i / 4.0, this.getZ() + d1 * (double)i / 4.0, -d5, -d6 + 0.2, -d1);
                }
            }
            else if (this.inGround) {
                for(int i = 0; i < 3; ++i) {
                    this.level().addParticle(ParticleTypes.ELECTRIC_SPARK, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0, 0, 0);
                }
            }
        }
    }

    @Override
    public DamageSource getDamageSource(Entity entity) {
        DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.LIGHTNING_SHOOT, this, this);
        if (this.getOwner() != null) {
            damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.LIGHTNING_SHOOT, this, this.getOwner());
        }
        return damageSource;
    }

    @Override
    public boolean canHurtEnderman() {
        return true;
    }

    @Override
    public void hitUse(Entity entity) {
        if (this.level() instanceof ServerLevel && this.level().isThundering()) {
            LightningBolt lightningBolt;
            BlockPos blockPos = entity.blockPosition();
            if (this.level().canSeeSky(blockPos) && (lightningBolt = EntityType.LIGHTNING_BOLT.create(this.level())) != null) {
                lightningBolt.moveTo(Vec3.atBottomCenterOf(blockPos));
                lightningBolt.setCause(this.getOwner() instanceof ServerPlayer serverPlayer ? serverPlayer : null);
                this.level().addFreshEntity(lightningBolt);
                soundEvent = SoundEvents.TRIDENT_THUNDER;
                soundLevel = 5.0f;
            }
        }
    }

}


