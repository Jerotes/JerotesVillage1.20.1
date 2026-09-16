package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotes.config.MainConfig;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import com.jerotes.jerotes.entity.Shoot.Arrow.*;

public class BrightBolaEntity extends BaseJavelinEntity implements ItemSupplier {
	private static final ItemStack item = new ItemStack(JerotesVillageItems.BRIGHT_BOLA.get());
	private static final EntityType<BrightBolaEntity> type = JerotesVillageEntityType.BRIGHT_BOLA.get();
	public BrightBolaEntity(EntityType<? extends BrightBolaEntity> entityType, Level level) {
		super(entityType, level, item, 1.0f);
	}
	public BrightBolaEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
		super(level, livingEntity, itemStack, type, 1.0f);
	}

	@Override
	protected void doPostHurtEffects(LivingEntity livingEntity) {
		super.doPostHurtEffects(livingEntity);
		if (this.getOwner() != null && this.getOwner() instanceof LivingEntity owner && livingEntity.getHealth() <= owner.getHealth() * 5 && (Main.mobSizeSmall(livingEntity) || Main.mobSizeMedium(livingEntity))) {
			Main.addEffectMax(livingEntity, owner, JerotesMobEffects.ENSLAVEMENT.get(), 80, 0, false, true, 320);
		}
	}

	@Override
	public boolean canLoyalty() {
		return false;
	}
	@Override
	protected SoundEvent getDefaultHitSoundEvent() {
		return SoundEvents.PLAYER_ATTACK_CRIT;
	}
	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return SoundEvents.PLAYER_ATTACK_CRIT;
	}
	public DamageSource getDamageSource(Entity entity) {
		return this.damageSources().thrown(this, this.getOwner() == null ? this : this.getOwner());
	}

	@Override
	public ItemStack getItem() {
		return new ItemStack(JerotesVillageItems.BRIGHT_BOLA.get());
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (!this.level().isClientSide && !this.isRemoved()) {
			if (this.level() instanceof ServerLevel _level) {
				if (!(hitResult instanceof EntityHitResult) && (this.getOwner() instanceof Player || MainConfig.MobUseThrowShrinkItem)) {
					ItemEntity entityToSpawn = new ItemEntity(_level, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z, new ItemStack(JerotesVillageItems.BRIGHT_BOLA.get()));
					entityToSpawn.setPickUpDelay(10);
					_level.addFreshEntity(entityToSpawn);
					this.discard();
				}
			}
		}
	}
}
