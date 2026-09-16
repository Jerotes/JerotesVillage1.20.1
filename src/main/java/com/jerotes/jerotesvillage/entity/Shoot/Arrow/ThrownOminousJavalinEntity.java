package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotes.entity.Shoot.Arrow.*;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ThrownOminousJavalinEntity extends BaseJavelinEntity {
	private static final ItemStack item = new ItemStack(JerotesVillageItems.OMINOUS_JAVELIN.get());
	private static final EntityType<ThrownOminousJavalinEntity> type = JerotesVillageEntityType.OMINOUS_JAVALIN.get();
	public ThrownOminousJavalinEntity(EntityType<? extends ThrownOminousJavalinEntity> entityType, Level level) {
		super(entityType, level, item, 9.0f);
	}
	public ThrownOminousJavalinEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
		super(level, livingEntity, itemStack, type, 9.0f);
	}

	@Override
	public boolean breakShield() {
		return true;
	}
	@Override
	public int basePierce() {
		return 1;
	}
}
