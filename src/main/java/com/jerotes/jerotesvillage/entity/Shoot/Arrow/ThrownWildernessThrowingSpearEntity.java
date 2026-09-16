package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotes.entity.Interface.AnesthetizedAttackEntity;
import com.jerotes.jerotes.entity.Shoot.Arrow.BaseJavelinEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ThrownWildernessThrowingSpearEntity extends BaseJavelinEntity implements AnesthetizedAttackEntity {
	private static final ItemStack item = new ItemStack(JerotesVillageItems.WILDERNESS_THROWING_SPEAR_OF_JAVELIN.get());
	private static final EntityType<ThrownWildernessThrowingSpearEntity> type = JerotesVillageEntityType.THROWN_WILDERNESS_THROWING_SPEAR.get();
	public ThrownWildernessThrowingSpearEntity(EntityType<? extends ThrownWildernessThrowingSpearEntity> entityType, Level level) {
		super(entityType, level, item, 14.0f);
	}
	public ThrownWildernessThrowingSpearEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
		super(level, livingEntity, itemStack, type, 14.0f);
	}

	@Override
	protected void doPostHurtEffects(LivingEntity livingEntity) {
		super.doPostHurtEffects(livingEntity);
		if (!livingEntity.level().isClientSide) {
			livingEntity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.WILDERNESS_GIANT_POISON.get(), 60, 1), this.getEffectSource());
		}
	}

	@Override
	public int getAnesthetized() {
		return 30;
	}
}
