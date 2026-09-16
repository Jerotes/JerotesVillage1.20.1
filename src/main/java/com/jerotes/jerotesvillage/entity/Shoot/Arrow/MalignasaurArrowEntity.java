package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import com.jerotes.jerotes.entity.Shoot.Arrow.*;

public class MalignasaurArrowEntity extends BaseArrowEntity {
    public static final ItemStack item = new ItemStack(JerotesVillageItems.MALIGNASAUR_ARROW.get());
    private static final EntityType<MalignasaurArrowEntity> type = JerotesVillageEntityType.MALIGNASAUR_ARROW.get();

    public MalignasaurArrowEntity(EntityType<? extends MalignasaurArrowEntity> entityType, Level level) {
        super(entityType, level, item, 2.5f);
    }
    public MalignasaurArrowEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
        super(level, livingEntity, itemStack, type, 2.5f);
    }
    public MalignasaurArrowEntity(Level level, double d, double d2, double d3, ItemStack itemStack) {
        super(level, d, d2, d3, itemStack, type, 2.5f);
    }

    @Override
    public float specialDamage(Entity entity) {
        if (entity instanceof LivingEntity livingEntity && livingEntity.getMobType() == MobType.WATER) {
            return 8f;
        }
        return 0f;
    }

    @Override
    protected float getWaterInertia() {
        return 1.3f;
    }
}


