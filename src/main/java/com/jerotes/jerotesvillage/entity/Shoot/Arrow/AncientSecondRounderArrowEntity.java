package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import com.jerotes.jerotes.entity.Shoot.Arrow.*;
import net.minecraft.world.level.Level;

public class AncientSecondRounderArrowEntity extends BaseArrowEntity {
    public static final ItemStack item = new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_ARROW.get());
    private static final EntityType<AncientSecondRounderArrowEntity> type = JerotesVillageEntityType.ANCIENT_SECOND_ROUNDER_ARROW.get();

    public AncientSecondRounderArrowEntity(EntityType<? extends AncientSecondRounderArrowEntity> entityType, Level level) {
        super(entityType, level, item, 2.5);
    }
    public AncientSecondRounderArrowEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
        super(level, livingEntity, itemStack, type, 2.5);
    }
    public AncientSecondRounderArrowEntity(Level level, double d, double d2, double d3, ItemStack itemStack) {
        super(level, d, d2, d3, itemStack, type, 2.5);
    }
}


