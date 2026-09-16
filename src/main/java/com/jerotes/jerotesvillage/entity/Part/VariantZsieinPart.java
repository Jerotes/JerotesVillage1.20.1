package com.jerotes.jerotesvillage.entity.Part;

import com.jerotes.jerotes.entity.Part.BasePartEntity;
import com.jerotes.jerotesvillage.entity.Boss.Biome.OldVariantZsieinEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class VariantZsieinPart extends BasePartEntity {
	private OldVariantZsieinEntity variantZsiein;

	public VariantZsieinPart(EntityType<? extends VariantZsieinPart> t, Level world) {
		super(t, world);
	}

	public VariantZsieinPart(EntityType<? extends VariantZsieinPart> type, OldVariantZsieinEntity variantZsiein, float radius, float angleYaw, float offsetY, float sizeX, float sizeY, float damageMultiplier) {
		super(type, variantZsiein, radius, angleYaw, offsetY, sizeX, sizeY, damageMultiplier);
		this.variantZsiein = variantZsiein;
	}

	public VariantZsieinPart(OldVariantZsieinEntity variantZsiein, float radius, float angleYaw, float offsetY, float sizeX, float sizeY, float damageMultiplier) {
		super(JerotesVillageEntityType.VARIANT_ZSIEIN_PART.get(), variantZsiein, radius, angleYaw, offsetY, sizeX, sizeY, damageMultiplier);
		this.variantZsiein = variantZsiein;
	}

	@Override
	public boolean hurt(@NotNull DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		Entity parent = getParent();
		if (parent instanceof OldVariantZsieinEntity variantZsieins) {
			if (variantZsieins.getInvulnerableTick() < 30 + variantZsieins.strength())
				return false;
			if (damageSource.getEntity() == null)
				return false;
			if (!(damageSource.getEntity() instanceof LivingEntity livingEntity) || livingEntity.getHealth() <= 0) {
				return false;
			}
			double distanceDamage = this.distanceToSqr(livingEntity) / 20;
			float damages = (float) (amount / distanceDamage);
			if (damages > amount) {
				damages = amount;
			}
			float damagesTrue = damages / 2.5f;
			if (damagesTrue < 2f) {
				return false;
			}
			float damageMax = (36.5f - Math.min((this.variantZsiein.strength() / 3), 34.25f));
			if (damagesTrue > damageMax) {
				damagesTrue = damageMax;
			}
			boolean hurt = variantZsieins.hurt(damageSource, damagesTrue);
			if (hurt) {
				return variantZsieins.BossZsieinHurt(damageSource, damagesTrue);
			}
			return hurt;
		}
		return false;
	}

	@Override
	public void tick() {
		super.tick();
	}
}