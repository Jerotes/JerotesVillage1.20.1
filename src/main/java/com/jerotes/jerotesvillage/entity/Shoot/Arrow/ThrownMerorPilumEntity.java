package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotes.entity.Interface.JerotesPlayerBaseEntity;
import com.jerotes.jerotes.entity.Shoot.Arrow.BaseJavelinEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ThrownMerorPilumEntity extends BaseJavelinEntity {
	@Nullable
	public List<Entity> teleportEntities = new ArrayList<>();
	private static final ItemStack item = new ItemStack(JerotesVillageItems.MEROR_PILUM.get());
	private static final EntityType<ThrownMerorPilumEntity> type = JerotesVillageEntityType.THROWN_MEROR_PILUM.get();
	public ThrownMerorPilumEntity(EntityType<? extends ThrownMerorPilumEntity> entityType, Level level) {
		super(entityType, level, item, 35f);
	}
	public ThrownMerorPilumEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
		super(level, livingEntity, itemStack, type, 35f);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level().isClientSide && !this.inGround) {
			this.level().addParticle(JerotesVillageParticleTypes.MEROR_CHARGING_BOW_ARROW_V.get(),
					this.getX(), this.getY(), this.getZ(),
					-this.getDeltaMovement().x * 0.1, -this.getDeltaMovement().y * 0.1, -this.getDeltaMovement().z * 0.1);
		}

		if (teleportEntities != null) {
			teleportEntities.removeIf(entity -> entity.distanceTo(this) > 3);
			teleportEntities.removeIf(entity -> entity instanceof Player player && player.isShiftKeyDown());
			teleportEntities.removeIf(entity -> entity instanceof JerotesPlayerBaseEntity player && entity instanceof LivingEntity livingEntity && livingEntity.isShiftKeyDown());
		}

		if (this.teleportEntities != null && !this.teleportEntities.isEmpty()) {
			for (Entity hurt : this.teleportEntities) {
				if (hurt == null) continue;
				if (hurt == this) continue;
				if (hurt == this.getOwner()) continue;
				if (this.getOwner() instanceof LivingEntity livingEntity && AttackFind.FindCanNotAttack(livingEntity, hurt)) continue;
				if (!EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
					hurt.teleportTo(this.getX(), this.getY() - hurt.getBbHeight()/2, this.getZ());
					if (hurt instanceof LivingEntity livingEntity) {
						livingEntity.stopUsingItem();
					}
					hurt.setDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y, this.getDeltaMovement().z);
				}
			}
		}
	}

	public void hitUse(Entity entity) {
        if (teleportEntities != null) {
            teleportEntities.add(entity);
        }
    }

	@Override
	public DamageSource getDamageSource(Entity entity) {
		DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_ARMOR_SHOOT, this, this);
		if (this.getOwner() != null) {
			damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_ARMOR_SHOOT, this, this.getOwner());
		}
		return damageSource;
	}


	@Override
	public boolean breakShield() {
		return true;
	}
	@Override
	public void rebound() {
	}
	@Override
	public int basePierce() {
		return 25;
	}

	protected float getWaterInertia() {
		return 0.99f;
	}
}
