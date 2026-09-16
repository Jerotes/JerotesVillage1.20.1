package com.jerotes.jerotesvillage.entity.Shoot.Other;

import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class PurpleSandExplosiveAlchemyBombEntity extends ThrowableItemProjectile {
    public PurpleSandExplosiveAlchemyBombEntity(EntityType<? extends PurpleSandExplosiveAlchemyBombEntity> entityType, Level level) {
        super(entityType, level);
    }

    public PurpleSandExplosiveAlchemyBombEntity(Level level, LivingEntity livingEntity) {
        super(JerotesVillageEntityType.PURPLE_SAND_EXPLOSIVE_ALCHEMY_BOMB.get(), livingEntity, level);
    }

    public PurpleSandExplosiveAlchemyBombEntity(Level level, double d, double d2, double d3) {
        super(JerotesVillageEntityType.PURPLE_SAND_EXPLOSIVE_ALCHEMY_BOMB.get(), d, d2, d3, level);
    }

    public int boom = 0;
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.boom = compoundTag.getInt("Boom");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Boom", this.boom);
    }

    @Override
    public void handleEntityEvent(byte by) {
        if (by == 3) {
            ParticleOptions particleOptions = this.getParticle();
            for (int i = 0; i < 8; ++i) {
                this.level().addParticle(particleOptions, this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08);
            }
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide) {
            this.boom += 120;
            this.setDeltaMovement(new Vec3(hitResult.getLocation().x - this.getX(), hitResult.getLocation().y - this.getY(), hitResult.getLocation().z - this.getZ()));

        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.boom >= 120) {
            if (!this.level().isClientSide) {
                this.level().broadcastEntityEvent(this, (byte)3);
                boolean bl = this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
                this.level().explode(this, this.getX(), this.getY(), this.getZ(), 2.0f, bl, Level.ExplosionInteraction.MOB);
                this.discard();
            }
        }
        else {
            this.boom += 1;
        }
    }

    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.FLAME;
    }

    @Override
    protected Item getDefaultItem() {
        return JerotesVillageItems.PURPLE_SAND_EXPLOSIVE_ALCHEMY_BOMB.get();
    }

    private ParticleOptions getParticle() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? ParticleTypes.FLAME : new ItemParticleOption(ParticleTypes.ITEM, itemStack);
    }
}
