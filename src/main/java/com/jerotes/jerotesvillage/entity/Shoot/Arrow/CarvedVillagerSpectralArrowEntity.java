package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotes.entity.Shoot.Arrow.BaseArrowEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class CarvedVillagerSpectralArrowEntity extends BaseArrowEntity {
    public static final ItemStack item = new ItemStack(JerotesVillageItems.CARVED_VILLAGER_SPECTRAL_ARROW.get());
    private static final EntityType<CarvedVillagerSpectralArrowEntity> type = JerotesVillageEntityType.CARVED_VILLAGER_SPECTRAL_ARROW.get();

    public CarvedVillagerSpectralArrowEntity(EntityType<? extends CarvedVillagerSpectralArrowEntity> entityType, Level level) {
        super(entityType, level, item, 2.0);
    }
    public CarvedVillagerSpectralArrowEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
        super(level, livingEntity, itemStack, type, 2.0);
    }
    public CarvedVillagerSpectralArrowEntity(Level level, double d, double d2, double d3, ItemStack itemStack) {
        super(level, d, d2, d3, itemStack, type, 2.0);
    }

    public boolean isVillager;
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.isVillager = compoundTag.getBoolean("IsVillager");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("IsVillager", this.isVillager);
    }
    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity entity2 = entityHitResult.getEntity();
        if (this.isVillager && this.getOwner() != null && this.getOwner() instanceof Mob mob && !(entity2 == mob.getTarget() || entity2 == mob.getLastHurtByMob() || entity2 == mob.getLastHurtMob())) {
            return;
        }
        super.onHitEntity(entityHitResult);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity livingEntity) {
        super.doPostHurtEffects(livingEntity);
        if (!livingEntity.level().isClientSide) {
            livingEntity.addEffect( new MobEffectInstance(MobEffects.GLOWING, 200, 0), this.getEffectSource());
            livingEntity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.CARVED_VILLAGER_ARROW.get(), 20, 0), this.getEffectSource());
        }
    }
}


