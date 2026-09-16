package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import com.jerotes.jerotes.entity.Shoot.Arrow.*;

public class VirtualCaveSpiderSetaeEntity extends BaseArrowEntity {
    public static final ItemStack item = new ItemStack(JerotesVillageItems.VIRTUAL_CAVE_SPIDER_SETAE.get());
    private static final EntityType<VirtualCaveSpiderSetaeEntity> type = JerotesVillageEntityType.VIRTUAL_CAVE_SPIDER_SETAE.get();

    public VirtualCaveSpiderSetaeEntity(EntityType<? extends VirtualCaveSpiderSetaeEntity> entityType, Level level) {
        super(entityType, level, item, 2.25f);
    }
    public VirtualCaveSpiderSetaeEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
        super(level, livingEntity, itemStack, type, 2.25f);
    }
    public VirtualCaveSpiderSetaeEntity(Level level, double d, double d2, double d3, ItemStack itemStack) {
        super(level, d, d2, d3, itemStack, type, 2.25f);
    }
    @Override
    public float specialDamage(Entity entity) {
        if (entity instanceof LivingEntity livingEntity && livingEntity.getMobType() == MobType.ARTHROPOD && livingEntity.getType() != JerotesVillageEntityType.VIRTUAL_CAVE_SPIDER.get()) {
            return 7f;
        }
        return 0f;
    }


    @Override
    protected void doPostHurtEffects(LivingEntity livingEntity) {
        super.doPostHurtEffects(livingEntity);
        if (!livingEntity.level().isClientSide) {
            livingEntity.addEffect(new MobEffectInstance(JerotesMobEffects.PRURITUS.get(), 80, 0), this.getEffectSource());
        }
    }
}


