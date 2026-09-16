package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotes.entity.Shoot.Arrow.BaseArrowEntity;
import com.jerotes.jerotesvillage.entity.Boss.Biome.SerponCombatExecuterEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SerponCrossbowTurretArrowEntity extends BaseArrowEntity {
	private static final ItemStack item = new ItemStack(JerotesVillageItems.SERPON_CROSSBOW_TURRET_ARROW.get());
	private static final EntityType<SerponCrossbowTurretArrowEntity> type = JerotesVillageEntityType.PROJECTILE_SERPON_CROSSBOW_TURRET_ARROW.get();

	public SerponCrossbowTurretArrowEntity(EntityType<? extends SerponCrossbowTurretArrowEntity> entityType, Level level) {
		super(entityType, level, item, 7.0);
	}
	public SerponCrossbowTurretArrowEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
		super(level, livingEntity, itemStack, type, 7.0);
	}
	public SerponCrossbowTurretArrowEntity(Level level, double d, double d2, double d3, ItemStack itemStack) {
		super(level, d, d2, d3, itemStack, type, 7.0);
	}

	@Override
	public int basePierce() {
		return 8;
	}
	@Override
	protected float getWaterInertia() {
		return 0.99f;
	}
	protected boolean canRebound() {
		return false;
	}

	@Override
	public float specialDamage(Entity entity) {
		if (entity instanceof SerponCombatExecuterEntity) {
			return 65f;
		}
		if (entity.isInvulnerableTo(entity.damageSources().inFire()) || entity.fireImmune() || entity.getType().fireImmune() || entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(MobEffects.FIRE_RESISTANCE)) {
			return 20f;
		}
		return 0f;
	}

	public int life = 0;
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.life = compoundTag.getInt("Life");
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("Life", this.life);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.life > 120) {
			return;
		}
		else {
			this.life += 1;
			if (this.level().isClientSide && !this.inGround) {
				this.level().addParticle(JerotesVillageParticleTypes.SERPON_SIROCCO.get(), this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
			}
		}
	}


	@Override
	public boolean isNoGravity() {
		return super.isNoGravity() || life <= 60 && !this.onGround();
	}
}
