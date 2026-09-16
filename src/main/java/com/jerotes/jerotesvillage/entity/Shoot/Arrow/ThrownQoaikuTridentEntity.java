package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotes.entity.Shoot.Arrow.BaseJavelinEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ThrownQoaikuTridentEntity extends BaseJavelinEntity {
	private static final ItemStack item = new ItemStack(JerotesVillageItems.QOAIKU_TRIDENT.get());
	private static final EntityType<ThrownQoaikuTridentEntity> type = JerotesVillageEntityType.THROWN_QOAIKU_TRIDENT.get();
	public ThrownQoaikuTridentEntity(EntityType<? extends ThrownQoaikuTridentEntity> entityType, Level level) {
		super(entityType, level, item, 9.0f);
	}
	public ThrownQoaikuTridentEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
		super(level, livingEntity, itemStack, type, 9.0f);
	}

	@Override
	protected float getWaterInertia() {
		return 0.99f;
	}
}
