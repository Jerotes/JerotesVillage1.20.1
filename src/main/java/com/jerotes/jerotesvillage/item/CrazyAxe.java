package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseAxe;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageDamageTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class CrazyAxe extends ItemToolBaseAxe {
	public CrazyAxe() {
		super(new Tier() {
			public int getUses() {
				return 800;
			}

			public float getSpeed() {
				return 6f;
			}

			public float getAttackDamageBonus() {
				return 11.5f;
			}

			public int getLevel() {
				return 3;
			}

			public int getEnchantmentValue() {
				return 16;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(Items.IRON_INGOT));
			}
		}, -1, 0.9f - 4f, new Properties().rarity(Rarity.UNCOMMON));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (interactionHand == InteractionHand.OFF_HAND) {
			return InteractionResultHolder.fail(itemStack);
		}
		if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
			return InteractionResultHolder.fail(itemStack);
		}
		player.startUsingItem(interactionHand);
		return InteractionResultHolder.consume(itemStack);
	}

	@Override
	public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int count) {
		if (livingEntity.getTicksUsingItem() == count) {
			livingEntity.stopUsingItem();
			this.releaseUsing(itemStack, level, livingEntity, count);
		}
		//冲刺
		if (livingEntity.onGround()) {
			float f = livingEntity.getYRot();
			float f2 = livingEntity.getXRot();
			float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
			float f4 = -Mth.sin(f2 * 0.017453292f);
			float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
			float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
			float f7 = 0.14f;
			livingEntity.push(f3 *= f7 / f6 * 2, f4 *= f7 / f6 * 2, f5 *= f7 / f6 * 2);
		}
		if (count % 5 == 0) {
			if (level instanceof ServerLevel serverLevel) {
				if (!livingEntity.level().isClientSide) {
					livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10, 2), livingEntity);
				}
				livingEntity.swing(InteractionHand.MAIN_HAND, true);
				//音效
				if (!livingEntity.isSilent()) {
					livingEntity.level().playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.PLAYER_ATTACK_CRIT, livingEntity.getSoundSource(), 10.0f, 1.0f);
				}
				//方块破坏
				AABB aABB = livingEntity.getBoundingBox().inflate(1, 0.5, 1).move(0, 0.5, 0);

				boolean bl = serverLevel.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) || livingEntity instanceof Player;
				for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aABB.minX), Mth.floor(aABB.minY), Mth.floor(aABB.minZ), Mth.floor(aABB.maxX), Mth.floor(aABB.maxY), Mth.floor(aABB.maxZ))) {
					BlockState blockState = serverLevel.getBlockState(blockPos);
					float block = blockState.getDestroySpeed(serverLevel, blockPos);
					if (block > 5f) continue;
					if (block < 0f) continue;
					if (!blockState.is(BlockTags.MINEABLE_WITH_AXE) && block > 2f) continue;
					if (!Main.canSee(blockPos.getCenter(), livingEntity)) continue;
					bl = serverLevel.destroyBlock(blockPos, true, livingEntity) || bl;
				}

				//攻击
				float reachs = AttackFind.reachAttackReach(livingEntity, itemStack, 3f, 1.5f, 3f, 0.5f);
				List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(reachs, reachs, reachs));
				for (LivingEntity hurt : list) {
					if (hurt == null || hurt.distanceTo(livingEntity) > reachs * 4) continue;
					if (AttackFind.SameFactionAvoidDamage(livingEntity, hurt, false)) {
						if (hurt != livingEntity)
							if (!(hurt instanceof ArmorStand armorStand && armorStand.isMarker())) {
								if (!hurt.level().isClientSide) {
									hurt.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 120, 2), livingEntity);
									hurt.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 120, 0), livingEntity);
									hurt.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 120, 1), livingEntity);
								}
							}
						continue;
					}
					if (!hurt.hasLineOfSight(livingEntity)) continue;
					if (!Main.canSee(hurt, livingEntity)) continue;
					double healthOld = hurt.getHealth();
					DamageSource damageSource = AttackFind.findDamageType(livingEntity, JerotesVillageDamageTypes.AX_CRAZY_ATTACK, livingEntity);
					AttackFind.attackBegin(livingEntity, hurt);
					boolean bl2 = AttackFind.attackAfterCustomDamage(livingEntity, hurt, damageSource,1.0f, 1.5f, false, 0f);
					if (bl2) {
						double healthNew = hurt.getHealth();
						int base = 3;
						if ((float) (healthOld - healthNew)/base > 0) {
							livingEntity.heal((float) (healthOld - healthNew) / base);
						}
						for (int i = 0; i < (healthOld - healthNew)/6; ++i) {
							serverLevel.sendParticles(ParticleTypes.HEART, livingEntity.getRandomX(1f), livingEntity.getRandomY(), livingEntity.getRandomZ(1f), 0, 0, 0.0, 0, 0.0);
							serverLevel.sendParticles(ParticleTypes.ANGRY_VILLAGER, livingEntity.getRandomX(1f), livingEntity.getRandomY(), livingEntity.getRandomZ(1f), 0, 0, 0.0, 0, 0.0);
						}
						level.playSound(null, livingEntity, JerotesVillageSoundEvents.AX_CRAZY_ATTACK, SoundSource.NEUTRAL, 1.0f, 1.0f);

						if (!EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
							if (Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt)) {
								if (livingEntity.onGround()) {
									hurt.setOnGround(false);
								}
								float angleYaw = 90.0F * ((float) Math.PI / 180.0F);
								double x = livingEntity.getX() + 4.5f * Mth.cos((float) (livingEntity.getYRot() * (Math.PI / 180.0F) + angleYaw));
								double y = livingEntity.getY();
								double z = livingEntity.getZ() + 4.5f * Mth.sin((float) (livingEntity.getYRot() * (Math.PI / 180.0F) + angleYaw));
								if (hurt.isShiftKeyDown()) continue;
								hurt.teleportTo(x, y, z);
							}
						}
					}
				}
				//横扫粒子
				double d0 = -Mth.sin(livingEntity.getYRot() * ((float)Math.PI / 180F));
				double d1 = Mth.cos(livingEntity.getYRot() * ((float)Math.PI / 180F));
				serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, livingEntity.getX() + d0, livingEntity.getY(0.5f) , livingEntity.getZ() + d1, 0, d0, 0.0D, d1, 0.0D);
			}
		}
		super.onUseTick(level, livingEntity, itemStack, count);
	}

	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 240;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.CUSTOM;
	}

	@Override
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int n) {
		if (!(livingEntity instanceof Player player)) {
			return;
		}
		level.playSound(null, livingEntity, JerotesVillageSoundEvents.AX_CRAZY_AMBIENT, SoundSource.NEUTRAL, 1.0f, 1.0f);
		if (!player.getAbilities().instabuild) {
			player.getCooldowns().addCooldown(this, 360);
		}
		itemStack.hurtAndBreak(8, player, livingEntitys -> livingEntitys.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		player.awardStat(Stats.ITEM_USED.get(this));
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
	}
	public MutableComponent getDisplayName() {
		return Component.translatable(this.getDescriptionId() + ".desc");
	}
}
