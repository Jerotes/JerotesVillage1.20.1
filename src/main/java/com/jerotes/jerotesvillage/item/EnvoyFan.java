package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesParticleTypes;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Neutral.Wonderlin.BaseWonderlinEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

import javax.annotation.Nullable;
import java.util.List;

public class EnvoyFan extends Item implements Vanishable {
	public EnvoyFan() {
		super(new Properties().stacksTo(1).rarity(Rarity.EPIC).durability(64));
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(JerotesVillageItems.RAMBLER_LEATHER.get()) || itemStack2.is(JerotesVillageItems.RAMBLER_SKIN.get()) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
			return InteractionResultHolder.fail(itemStack);
		}
		if (level instanceof ServerLevel serverLevel) {
			float reach = (3 + ((player.getAttribute(ForgeMod.ENTITY_REACH.get()) != null) ? (float) Math.max(0, player.getAttributeValue(ForgeMod.ENTITY_REACH.get()) - 3) : 0));
			List<Projectile> listArrow = serverLevel.getEntitiesOfClass(Projectile.class, player.getBoundingBox().inflate(reach * 4, reach * 4, reach * 4));
			for (Projectile arrow : listArrow) {
				if (arrow == null || arrow.distanceTo(player) > reach * 16) continue;
				if (!Main.canSee(arrow, player)) continue;
				if (!Main.hasLineOfSightEntity(arrow, player)) continue;
				if (!EntityAndItemFind.isNoSpecialKnockback(arrow.getType())) {
					if (Main.mobSizeSmall(arrow) || Main.mobSizeMedium(arrow)) {
						pushEntityAway(arrow, player,2.8f);
					}
					else {
						pushEntityAway(arrow, player,0.5f);
					}
				}
			}

			List<BaseWonderlinEntity> listWonderlin = serverLevel.getEntitiesOfClass(BaseWonderlinEntity.class, player.getBoundingBox().inflate(reach * 4, reach * 4, reach * 4));
			for (BaseWonderlinEntity wonderlin : listWonderlin) {
				if (wonderlin == null || wonderlin.distanceTo(player) > reach * 16) continue;
				if (!Main.canSee(wonderlin, player)) continue;
				if (!Main.hasLineOfSightEntity(wonderlin, player)) continue;
				if (!wonderlin.isWonderlinRush()) continue;
				if (!EntityAndItemFind.isNoSpecialKnockback(wonderlin.getType())) {
					if (Main.mobSizeSmall(wonderlin) || Main.mobSizeMedium(wonderlin)) {
						pushEntityAway(wonderlin, player,1.6f);
					}
					else {
						pushEntityAway(wonderlin, player,0.4f);
					}
				}
			}

			List<AreaEffectCloud> listCloud = serverLevel.getEntitiesOfClass(AreaEffectCloud.class, player.getBoundingBox().inflate(reach * 4, reach * 4, reach * 4));
			for (AreaEffectCloud cloud : listCloud) {
				if (cloud == null || cloud.distanceTo(player) > reach * 16) continue;
				if (!Main.canSee(player, cloud)) continue;
				if (!Main.hasLineOfSightEntity(cloud, player)) continue;
				cloud.setRadius(Math.max(cloud.getRadiusOnUse() - 2.5f, 0));
				cloud.setRadiusPerTick(Math.max(cloud.getRadiusOnUse() - 0.25f, 0));
			}


			List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(reach * 3, reach * 3, reach * 3));
			for (LivingEntity hurt : list) {
				if (hurt == null || hurt.distanceTo(player) > reach * 12) continue;
				if (!Main.canSee(hurt, player) && !(hurt.getBoundingBox().intersects(player.getBoundingBox()))) continue;
				if (!hurt.hasLineOfSight(player) && !(hurt.getBoundingBox().intersects(player.getBoundingBox()))) continue;
				if (player == hurt) continue;
				//if (AttackFind.FindCanNotAttack(player, hurt)) continue;
				if (hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
					double d2 = hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null ? hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) : 0;
					double d3 = Math.max(0.25f, 1.25 - d2);
					if (!EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
						if (Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt)) {
							pushEntityAway(hurt, player,d3 + 1.8f);
						}
						else {
							pushEntityAway(hurt, player,d3 + 0.25f);
						}
					}
				}
				AttackFind.attackBegin(player, hurt);
				boolean bl = AttackFind.attackAfter(player, hurt, 0f, 4f, true, 1f);
				if (bl) {
					//麻醉
					if (!hurt.level().isClientSide) {
						float anesthetizedTicks = 0;
						double anesthetizedAdds = 120 * 20;
						serverLevel.sendParticles(JerotesParticleTypes.ANESTHETIZED_VIII.get(), hurt.getRandomX(0.5), hurt.getRandomY(), hurt.getRandomZ(0.5), 3, 0, 0, 0, 0);
						if (hurt.hasEffect(JerotesMobEffects.ANESTHETIZED.get())) {
							anesthetizedTicks = hurt.getEffect(JerotesMobEffects.ANESTHETIZED.get()).getDuration();
						}
						if (!hurt.level().isClientSide && !EntityFactionFind.isMachine(hurt) && !EntityFactionFind.isConstruct(hurt)) {
							hurt.addEffect(new MobEffectInstance(JerotesMobEffects.ANESTHETIZED.get(), (int) (anesthetizedAdds + anesthetizedTicks), 0, false, false));
						}
					}
				}
			}
			double d0 = (-Mth.sin(player.getYRot() * ((float)Math.PI / 180F)));
			double d1 = Mth.cos(player.getYRot() * ((float)Math.PI / 180F));
			serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, player.getX() + d0, player.getY(0.5) , player.getZ() + d1, 0, d0, 0.0D, d1, 0.0D);
		}
		Vec3 lookDir = player.getLookAngle().normalize();
		Vec3 entityPos = player.position().add(lookDir.scale(0.8));
		double radius = 0.5;
		int particles = 80;
		double heightOffset = player.getY(0.5) - player.getY();
		float yaw = player.getYRot();
		float pitch = player.getXRot();
		for (int i = 0; i < particles; i++) {
			double angle = Math.toRadians(-90 + (i * 180.0 / particles));
			double zOffset = radius * Math.cos(angle);
			double xOffset = radius * Math.sin(angle);
			double cosYaw = Math.cos(Math.toRadians(yaw));
			double sinYaw = Math.sin(Math.toRadians(yaw));
			double rotatedX = xOffset * cosYaw - zOffset * sinYaw;
			double rotatedZ = xOffset * sinYaw + zOffset * cosYaw;
			double finalYOffset = heightOffset + (Math.sin(Math.toRadians(-pitch)) * zOffset);
			Vec3 particlePos = entityPos.add(rotatedX, finalYOffset, rotatedZ);
			double randomSpread = 0.2;
			double speedX = lookDir.x * 0.1 + (Math.random() * randomSpread - randomSpread / 2);
			double speedY = lookDir.y * 0.1 + (Math.random() * randomSpread - randomSpread / 2);
			double speedZ = lookDir.z * 0.1 + (Math.random() * randomSpread - randomSpread / 2);
			level.addParticle(JerotesParticleTypes.ANESTHETIZED_VIII.get(), particlePos.x, particlePos.y, particlePos.z, speedX, speedY, speedZ);
		}
		itemStack.hurtAndBreak(1, player, players -> players.broadcastBreakEvent(players.getUsedItemHand()));
		if (!player.isSilent()) {
			player.level().playSound(null, player.getX(), player.getY(), player.getZ(), JerotesSoundEvents.WIND_SHORT, player.getSoundSource(), 1.0f, 1.0f);
		}
		if (!player.getAbilities().instabuild) {
			player.getCooldowns().addCooldown(this, 120);
		}
		player.awardStat(Stats.ITEM_USED.get(this));
		player.startUsingItem(interactionHand);
		return InteractionResultHolder.consume(itemStack);

	}
	public static void pullEntityTowards(Entity target, LivingEntity source, double speed) {
		if (target == null || source == null) return;
		double dx = source.getX() - target.getX();
		double dy = source.getY() - target.getY();
		double dz = source.getZ() - target.getZ();

		double length = Math.sqrt(dx * dx + dy * dy + dz * dz);
		if (length > 1e-8) {
			dx /= length;
			dy /= length;
			dz /= length;
		} else {
			dx = 0;
			dy = 0;
			dz = 0;
		}
		target.setOnGround(false);
		target.push(dx * speed, dy * speed, dz * speed);
	}
	public static void pushEntityAway(Entity target, LivingEntity source, double speed) {
		if (target == null || source == null) return;

		double dx = target.getX() - source.getX();
		double dy = target.getY() - source.getY();
		double dz = target.getZ() - source.getZ();

		double length = Math.sqrt(dx * dx + dy * dy + dz * dz);
		if (length > 1e-8) {
			dx /= length;
			dy /= length;
			dz /= length;
		} else {
			dx = 0;
			dy = 0;
			dz = 0;
		}

		target.setOnGround(false);
		target.push(dx * speed, dy * speed, dz * speed);
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.BRUSH;
	}

	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 72000;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
	}
	public MutableComponent getDisplayName() {
		return Component.translatable(this.getDescriptionId() + ".desc");
	}

}
