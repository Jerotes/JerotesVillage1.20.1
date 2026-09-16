package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import com.jerotes.jerotes.entity.Shoot.Arrow.*;

public class SerponDartEntity extends BaseArrowEntity {
    public static final ItemStack item = new ItemStack(JerotesVillageItems.SERPON_DART.get());
    private static final EntityType<SerponDartEntity> type = JerotesVillageEntityType.SERPON_DART.get();

    public SerponDartEntity(EntityType<? extends SerponDartEntity> entityType, Level level) {
        super(entityType, level, item, 4.0);
    }
    public SerponDartEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
        super(level, livingEntity, itemStack, type, 4.0);
    }
    public SerponDartEntity(Level level, double d, double d2, double d3, ItemStack itemStack) {
        super(level, d, d2, d3, itemStack, type, 4.0);
    }
}
