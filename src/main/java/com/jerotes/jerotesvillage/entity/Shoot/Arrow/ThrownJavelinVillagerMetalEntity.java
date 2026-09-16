package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotes.entity.Shoot.Arrow.BaseJavelinEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ThrownJavelinVillagerMetalEntity extends BaseJavelinEntity {
	private static final ItemStack item = new ItemStack(JerotesVillageItems.VILLAGER_METAL_JAVELIN.get());
	private static final EntityType<ThrownJavelinVillagerMetalEntity> type = JerotesVillageEntityType.THROWN_VILLAGER_METAL_JAVELIN.get();
	public ThrownJavelinVillagerMetalEntity(EntityType<? extends ThrownJavelinVillagerMetalEntity> entityType, Level level) {
		super(entityType, level, item, 7.0f);
	}
	public ThrownJavelinVillagerMetalEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
		super(level, livingEntity, itemStack, type, 7.0f);
	}

	@Override
	public void rebound() {
		this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.2, -0.01));
	}
	@Override
	public void reboundBlock() {
		this.setDeltaMovement(this.getDeltaMovement().multiply(-0.1, -0.2, -0.1));
	}
}
