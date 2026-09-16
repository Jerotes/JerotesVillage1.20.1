package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import com.jerotes.jerotes.entity.Shoot.Arrow.*;
import net.minecraft.world.level.Level;

public class MerorChargingBowArrowIIIEntity extends BaseArrowEntity {
    public static final ItemStack item = new ItemStack(JerotesVillageItems.MEROR_METAL_ARROW.get());
    private static final EntityType<MerorChargingBowArrowIIIEntity> type = JerotesVillageEntityType.MEROR_CHARGING_BOW_ARROW_III.get();

    public MerorChargingBowArrowIIIEntity(EntityType<? extends MerorChargingBowArrowIIIEntity> entityType, Level level) {
        super(entityType, level, item, 8.0);
    }
    public MerorChargingBowArrowIIIEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
        super(level, livingEntity, itemStack, type, 8.0);
    }
    public MerorChargingBowArrowIIIEntity(Level level, double d, double d2, double d3, ItemStack itemStack) {
        super(level, d, d2, d3, itemStack, type, 8.0);
    }

    public int life = 0;
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.life = compoundTag.getInt("Life");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Life", this.life);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.life >= 800) {
            this.discard();
        }
        else {
            this.life += 1;
            if (this.level().isClientSide && !this.inGround) {
                this.level().addParticle(JerotesVillageParticleTypes.MEROR_CHARGING_BOW_ARROW_III.get(), this.getX(), this.getY(), this.getZ(),
                        -this.getDeltaMovement().x * 0.1, -this.getDeltaMovement().y * 0.1, -this.getDeltaMovement().z * 0.1);
            }
        }
    }

    @Override
    public int basePierce() {
        return 8;
    }

    @Override
    public boolean isNoGravity() {
        return super.isNoGravity() || life <= 80 && !this.onGround();
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    protected float getWaterInertia() {
        return 0.99f;
    }
}


