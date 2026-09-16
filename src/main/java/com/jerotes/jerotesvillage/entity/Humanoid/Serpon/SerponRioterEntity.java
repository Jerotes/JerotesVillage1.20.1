package com.jerotes.jerotesvillage.entity.Humanoid.Serpon;

import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.util.OtherEntityFactionFind;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;


public class SerponRioterEntity extends SerponEntity {

	public SerponRioterEntity(EntityType<? extends SerponRioterEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 25;
	}

	@Override
	public boolean wantsToPickUp(ItemStack itemStack) {
		if (itemStack.canEquip(EquipmentSlot.HEAD,this)) {
			return false;
		}
		if (itemStack.canEquip(EquipmentSlot.CHEST,this)) {
			return false;
		}
		if (itemStack.canEquip(EquipmentSlot.LEGS,this)) {
			return false;
		}
		if (itemStack.canEquip(EquipmentSlot.FEET,this)) {
			return false;
		}
		return super.wantsToPickUp(itemStack);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
		builder = builder.add(Attributes.FLYING_SPEED, 0.5);
		builder = builder.add(Attributes.MAX_HEALTH, 55);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.1);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 8);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		builder = builder.add(Attributes.ARMOR, 15);
		return builder;
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		this.level().broadcastEntityEvent(this, (byte) 4);
		boolean bl = super.doHurtTarget(entity);
		if (bl) {
			//群攻
			List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(1.0, 0.25, 1.0));
			if (!this.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
				for (LivingEntity hurt : list) {
					if (hurt == null) continue;
					if (AttackFind.FindCanNotAttack(this, hurt, entity)) continue;
					if (AttackFind.FindCanNotAttack(this, hurt)) continue;
					if (!this.hasLineOfSight(hurt)) continue;
					AttackFind.attackBegin(this, hurt);
					AttackFind.attackAfter(this, hurt, 0.5f, 0.5f, false, 0f);
				}
				//横扫效果
				Main.sweepAttack(this);
			}
		}
		return bl;
	}

	@Override
	public ItemStack createSpawnWeapon(float weaponRandom) {
		return new ItemStack(JerotesVillageItems.SERPON_AXE.get());
	}
}

