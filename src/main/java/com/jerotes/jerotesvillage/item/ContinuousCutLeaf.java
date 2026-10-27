package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.item.Interface.ItemSpecialAttack;
import com.jerotes.jerotes.item.Tool.ItemToolBaseTwoHandedSword;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.List;
import java.util.function.Consumer;

public class ContinuousCutLeaf extends ItemToolBaseTwoHandedSword implements ItemSpecialAttack {
	public ContinuousCutLeaf() {
		super(new Tier() {
			public int getUses() {
				return 5000;
			}

			public float getSpeed() {
				return 12f;
			}

			public float getAttackDamageBonus() {
				return 15f;
			}

			public int getLevel() {
				return 4;
			}

			public int getEnchantmentValue() {
				return 28;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(ItemTags.LEAVES);
			}
		}, -1, 2.5f - 4f, new Properties().fireResistant().rarity(Rarity.EPIC));
	}
	public void jerotesSpecialAttackClient(LivingEntity livingEntity) {
		if (livingEntity instanceof Player player) {
			player.resetAttackStrengthTicker();
		}
	}
	public static final String ATTACK_TYPE = "AttackType";
	public int getAttackType(ItemStack itemStack) {
		CompoundTag compoundtag = itemStack.hasTag() ? itemStack.getTag().copy() : new CompoundTag();
		return compoundtag.getInt(ATTACK_TYPE);
	}
	public static void setAttackType(ItemStack itemStack, int n) {
		CompoundTag compoundtag = itemStack.hasTag() ? itemStack.getTag().copy() : new CompoundTag();
		compoundtag.putInt(ATTACK_TYPE,  n);
		itemStack.setTag(compoundtag);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		player.startUsingItem(interactionHand);
		return InteractionResultHolder.consume(itemStack);
	}

	@Override
	public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int count) {
		if (livingEntity.getTicksUsingItem() == count) {
			livingEntity.stopUsingItem();
			this.releaseUsing(itemStack, level, livingEntity, count);
		}
		if (livingEntity.getTicksUsingItem() == 20) {
			attack(livingEntity.level(), livingEntity, livingEntity.getUsedItemHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND, true);
			if (livingEntity instanceof Player player) {
				player.getCooldowns().addCooldown(this, 40);
			}
		}
		if (livingEntity.getTicksUsingItem() == 30) {
			if (livingEntity instanceof Player player) {
				itemStack.hurtAndBreak(1, player, player2 -> player2.broadcastBreakEvent(livingEntity.getUsedItemHand()));
			}
			else if (JerotesGameRules.JEROTES_MAGIC_CAN_BREAK != null && livingEntity.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MAGIC_CAN_BREAK)) {
				itemStack.hurtAndBreak(1, livingEntity, player2 -> player2.broadcastBreakEvent(EquipmentSlot.MAINHAND));
			}
		}
		super.onUseTick(level, livingEntity, itemStack, count);
	}

	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 60;
	}

	public void jerotesSpecialAttack(LivingEntity livingEntity, EquipmentSlot equipmentSlot) {
		attack(livingEntity.level(), livingEntity, equipmentSlot, false);
	}
	public void attack(Level level, LivingEntity livingEntity, EquipmentSlot equipmentSlot, boolean bl) {

		if (livingEntity.level() instanceof ServerLevel serverLevel) {
			if (!livingEntity.isSilent()) {
				livingEntity.level().playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(),
						JerotesVillageSoundEvents.CONTINUOUS_CUT_LEAF_ATTACK, livingEntity.getSoundSource(), 1.0f, 1.0f);
			}
			float f = livingEntity.getRandom().nextFloat();
			//攻击
			float reachs = AttackFind.reachAttackReach(livingEntity, livingEntity.getItemBySlot(equipmentSlot), bl ? 5 : 4, bl ? 5 : 4, 3.0F, 0.5F);
			List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(reachs, reachs, reachs));
			for (LivingEntity hurt : list) {
				if (hurt == null || hurt.distanceTo(livingEntity) > reachs * 4) continue;
				if (!hurt.isAlive()) continue;
				if (livingEntity == hurt) continue;
				//if (AttackFind.FindCanNotAttack(livingEntity, hurt)) continue;
				if (!Main.canSee(hurt, livingEntity) && !(hurt.getBoundingBox().intersects(livingEntity.getBoundingBox()))) continue;
				if (!hurt.hasLineOfSight(livingEntity) && !(hurt.getBoundingBox().intersects(livingEntity.getBoundingBox()))) continue;
				double healthOld = hurt.getHealth();
				DamageSource damageSource = AttackFind.findDamageType(livingEntity, JerotesDamageTypes.BYPASSES_COOLDOWN_MELEE, livingEntity);
				AttackFind.attackBegin(livingEntity, hurt);
				float f2 = f/2 - (hurt.distanceTo(livingEntity) / 16f);
				int n = (int) (f2 * 10);
				boolean bl2 = AttackFind.attackAfterCustomDamage(livingEntity, hurt, damageSource, bl ? 2f : 1.15f + n/10f, bl ? 2f : 1f, false, 0f);
				if (bl2) {
					if (bl) {
						double healthNew = hurt.getHealth();
						if ((float) (healthOld - healthNew)/3 > 0) {
							livingEntity.heal((float) (healthOld - healthNew) / 5);
						}
						if (!EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
							if (Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt)) {
								double d = 0.0;
								if (hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
									d = Math.max(livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0);
								}
								double d3 = Math.max(0, 1 - d) + 0.2f;
								if (livingEntity.onGround()) {
									float base = (0.15f + 1 / livingEntity.distanceTo(hurt) / 3f) * 0.25f;
									hurt.setOnGround(false);
									hurt.setDeltaMovement(hurt.getDeltaMovement().add(
											-(livingEntity.getX() - hurt.getX()) * d3 * base,
											-(livingEntity.getY() - hurt.getY()) * d3 * base + 0.125f * d3 * base,
											-(livingEntity.getZ() - hurt.getZ()) * d3 * base
									));
								}
							}
						}
					}
					if (!hurt.level().isClientSide) {
						hurt.addEffect(new MobEffectInstance(JerotesMobEffects.BLEEDING.get(), 60, 0, false, false), livingEntity);
					}
					serverLevel.sendParticles(JerotesVillageParticleTypes.CONTINUOUS_CUT_LEAF_SWEEP.get(), hurt.getX(), hurt.getY(0.5), hurt.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
				}
			}

			Vec3 lookDir = livingEntity.getLookAngle().normalize();
			Vec3 entityPos = livingEntity.position().add(lookDir.scale(0.35));
			double radius = 1.325;
			int particles = 80;
			double heightOffset = livingEntity.getY(0.8) - livingEntity.getY();

			float yaw = livingEntity.getYRot();
			float pitch = livingEntity.getXRot();
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
				double speedX = rotatedX * 0.07 + (Math.random() * 0.02 - 0.01);
				double speedY = lookDir.y * 0.07 + (Math.random() * 0.02 - 0.01);
				double speedZ = rotatedZ * 0.07 + (Math.random() * 0.02 - 0.01);

				if (bl) {
					serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_LEAF.get(),
							particlePos.x, particlePos.y, particlePos.z,
							0, speedX, speedY, speedZ, 1);
					serverLevel.sendParticles(JerotesVillageParticleTypes.CELESTIAL_COILVINE_CHANGE.get(),
							particlePos.x, particlePos.y, particlePos.z,
							0, speedX, speedY, speedZ, 1);
				}
			}
		}

		if (livingEntity.getItemBySlot(equipmentSlot).getItem() instanceof ContinuousCutLeaf continuousCutLeaf &&
				continuousCutLeaf.getAttackType(livingEntity.getItemBySlot(equipmentSlot)) == 0 && !livingEntity.isUsingItem() &&
				(livingEntity instanceof JerotesEntity && JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null &&
				livingEntity.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK) || livingEntity instanceof Player)) {
			if (livingEntity.getItemBySlot(equipmentSlot).isDamageableItem()) {
				livingEntity.getItemBySlot(equipmentSlot).hurtAndBreak(1, livingEntity,
						e -> livingEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
			}
		}

	}

	public boolean canAttackBlock(BlockState p_43291_, Level p_43292_, BlockPos p_43293_, Player p_43294_) {
		return false;
	}
	@Override
	public int getBlockReduction() {
		return 75;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
	}
	public MutableComponent getDisplayName() {
		return Component.translatable(this.getDescriptionId() + ".desc");
	}



	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			private void applyItemArmTransform(PoseStack poseStack, HumanoidArm arm, float equipProcess) {
				int i = arm == HumanoidArm.RIGHT ? 1 : -1;
				poseStack.translate((double)((float)i * 0.56F), (double)(-0.52F + equipProcess * -0.6F), (double)-0.72F);
			}
			//第一人称
			@Override
			public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm,
												   ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
				int dir = arm == HumanoidArm.RIGHT ? 1 : -1;
				if (player.isUsingItem() && player.getUseItem() == itemInHand) {
					this.applyItemArmTransform(poseStack, arm, 0);
					float fs = 0.5f - Math.min(40, player.getTicksUsingItem()) / 40f;
					poseStack.mulPose(Axis.YP.rotationDegrees(fs < 0 ? 30 * fs : -30 * fs));
					return true;
				}
				else if (swingProcess > 0.0F) {
					if (getAttackType(itemInHand) == 0) {
						setAttackType(itemInHand, Main.randomReach(player.getRandom(), 1, 6));
					}
					if (swingProcess >= 0.98) {
						setAttackType(itemInHand, 0);
					}
					this.applyItemArmTransform(poseStack, arm, 0);
					firstPersonAttack(swingProcess, poseStack, dir, arm, getAttackType(itemInHand));
					return true;
				}
				this.applyItemArmTransform(poseStack, arm, equipProcess);
				return true;
			}
		});
	}

	public static void firstPersonAttack(float f, PoseStack poseStack, int n, HumanoidArm humanoidArm, int type) {
		if (type == 1) {
			firstPersonAttacks(f, poseStack, n, humanoidArm, 1);
		}
		if (type == 2) {
			firstPersonAttacks(f, poseStack, n, humanoidArm, -1.08f);
		}
		if (type == 3) {
			firstPersonAttacks(f, poseStack, n, humanoidArm, -0.77f);
		}
		if (type == 4) {
			firstPersonAttacks(f, poseStack, n, humanoidArm,  0.65f);
		}
		if (type == 5) {
			firstPersonAttacks(f, poseStack, n, humanoidArm, -0.97f);
		}
		if (type == 6) {
			firstPersonAttacks(f, poseStack, n, humanoidArm,  0.85f);
		}
		//JerotesVillage.LOGGER.info("First Person Attack's f: " + f);
	}


	public static void firstPersonAttacks(float f, PoseStack poseStack, int n, HumanoidArm humanoidArm, float base) {
		//准备
		if (f > 0 && f <= 0.1) {
			float fs = f / 0.1f;
			poseStack.mulPose(Axis.ZP.rotationDegrees(90 * fs * base));
			poseStack.mulPose(Axis.XP.rotationDegrees(20 * fs));
			poseStack.translate(0, 0.4 * fs, -0.65 * fs);
		}
		else if (f > 0.1 && f <= 0.15) {
			poseStack.mulPose(Axis.ZP.rotationDegrees(90 * base));
			poseStack.mulPose(Axis.XP.rotationDegrees(20));
			poseStack.translate(0, 0.4, -0.65);
		}
		//攻击
		else if (f > 0.15 && f <= 0.3) {
			float fs = (f - 0.15f) / 0.15f;
			poseStack.mulPose(Axis.ZP.rotationDegrees(90 * base));
			poseStack.mulPose(Axis.XP.rotationDegrees(20 - (140 * fs)));
			poseStack.translate(0, 0.4 + (-0.1 * base * fs), -0.65 - 0.5 * fs);
		}
		else if (f > 0.3 && f <= 0.8) {
			float fs = 1 - (f - 0.3f) / 0.5f;
			poseStack.mulPose(Axis.ZP.rotationDegrees(90 * base));
			poseStack.mulPose(Axis.XP.rotationDegrees(-120 - (20 * fs)));
			poseStack.translate(0, 0.4 + (-0.1 * base), -1.15);
		}
		//收回
		else if (f > 0.8) {
			float fs = 1 - (f - 0.8f) / 0.2f;
			poseStack.mulPose(Axis.ZP.rotationDegrees(90 * fs * base));
			poseStack.mulPose(Axis.XP.rotationDegrees(-140 * fs));
			poseStack.translate(0, (0.4 + (-0.1 * base)) * fs, -1.15 * fs);
		}
	}

	public static float progress(float f, float f2, float f3) {
		return Mth.clamp(Mth.inverseLerp(f, f2, f3), 0.0f, 1.0f);
	}

	@Override
	public int swingTimes() {
		float f = 10;
		return (int)f;
	}
}
