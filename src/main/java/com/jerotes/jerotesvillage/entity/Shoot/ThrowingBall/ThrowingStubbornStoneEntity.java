package com.jerotes.jerotesvillage.entity.Shoot.ThrowingBall;

import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class ThrowingStubbornStoneEntity extends BaseThrowingBallEntity {
    public ThrowingStubbornStoneEntity(EntityType<? extends ThrowingStubbornStoneEntity> entityType, Level level) {
        super(entityType, level);
    }

    public ThrowingStubbornStoneEntity(Level level, LivingEntity livingEntity) {
        super(JerotesVillageEntityType.THROWING_STUBBORN_STONE.get(), livingEntity, level);
    }

    public ThrowingStubbornStoneEntity(Level level, double d, double d2, double d3) {
        super(JerotesVillageEntityType.THROWING_STUBBORN_STONE.get(), d, d2, d3, level);
    }

    @Override
    public float baseDamage() {
        return 2f;
    }
    @Override
    public float addDamage() {
        return 4f;
    }
    @Override
    public int maxBackCount() {
        return 0;
    }

    @Override
    protected Item getDefaultItem() {
        return JerotesVillageItems.THROWING_STUBBORN_STONE.get();
    }
}
