package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotes.entity.Shoot.Arrow.BaseJavelinEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ThrownMerorMetalJavelinEntity extends BaseJavelinEntity {
	private static final ItemStack item = new ItemStack(JerotesVillageItems.MEROR_METAL_JAVELIN.get());
	private static final EntityType<ThrownMerorMetalJavelinEntity> type = JerotesVillageEntityType.THROWN_MEROR_METAL_JAVELIN.get();
	public ThrownMerorMetalJavelinEntity(EntityType<? extends ThrownMerorMetalJavelinEntity> entityType, Level level) {
		super(entityType, level, item, 15.5f);
	}
	public ThrownMerorMetalJavelinEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
		super(level, livingEntity, itemStack, type, 15.5f);
	}

	@Override
	public void rebound() {
	}
	@Override
	public int basePierce() {
		return 5;
	}
}
