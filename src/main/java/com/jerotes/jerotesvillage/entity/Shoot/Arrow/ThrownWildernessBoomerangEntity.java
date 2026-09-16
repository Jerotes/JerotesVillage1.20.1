package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotes.entity.Interface.AnesthetizedAttackEntity;
import com.jerotes.jerotes.entity.Shoot.Arrow.BaseJavelinEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ThrownWildernessBoomerangEntity extends BaseJavelinEntity implements AnesthetizedAttackEntity {
	private static final ItemStack item = new ItemStack(JerotesVillageItems.WILDERNESS_BOOMERANG.get());
	private static final EntityType<ThrownWildernessBoomerangEntity> type = JerotesVillageEntityType.THROWN_WILDERNESS_BOOMERANG.get();
	public ThrownWildernessBoomerangEntity(EntityType<? extends ThrownWildernessBoomerangEntity> entityType, Level level) {
		super(entityType, level, item, 6.0f);
	}
	public ThrownWildernessBoomerangEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
		super(level, livingEntity, itemStack, type, 6.0f);
	}

	@Override
	public void tick() {
		time += 1;
		if (this.time > 30 || this.inGroundTime > 0) {
			this.dealtDamage = true;
		}
		super.tick();
	}

	@Override
	public boolean customDealtDamage() {
		return true;
	}
	@Override
	public boolean mustLoyaltyOnlyToPlayer() {
		return true;
	}
	@Override
	public boolean mustLoyalty() {
		return true;
	}
	@Override
	public int getAnesthetized() {
		return 12;
	}
}
