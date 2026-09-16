package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import com.jerotes.jerotes.entity.Shoot.Arrow.*;
import net.minecraft.world.level.Level;

public class MerorMetalArrowEntity extends BaseArrowEntity {
    public static final ItemStack item = new ItemStack(JerotesVillageItems.MEROR_METAL_ARROW.get());
    private static final EntityType<MerorMetalArrowEntity> type = JerotesVillageEntityType.MEROR_METAL_ARROW.get();

    public MerorMetalArrowEntity(EntityType<? extends MerorMetalArrowEntity> entityType, Level level) {
        super(entityType, level, item, 4.0);
    }
    public MerorMetalArrowEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
        super(level, livingEntity, itemStack, type, 4.0);
    }
    public MerorMetalArrowEntity(Level level, double d, double d2, double d3, ItemStack itemStack) {
        super(level, d, d2, d3, itemStack, type, 4.0);
    }

    @Override
    public int basePierce() {
        return 5;
    }
    @Override
    public boolean canNotBreak() {
        return true;
    }
    @Override
    protected float getWaterInertia() {
        return 0.99f;
    }
}


