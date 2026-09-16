package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseTwoHandedHoe;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.MerorScytheShootEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class MerorScythe extends ItemToolBaseTwoHandedHoe {
	public MerorScythe() {
		super(new Tier() {
			public int getUses() {
				return 18800;
			}

			public float getSpeed() {
				return 18f;
			}

			public float getAttackDamageBonus() {
				return 15f;
			}

			public int getLevel() {
				return 5;
			}

			public int getEnchantmentValue() {
				return 28;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(JerotesVillageItems.REFINE_MEROR_METAL_INGOT.get()));
			}
		}, -1, 1.4f - 4f, new Properties().fireResistant().rarity(Rarity.EPIC));
	}

	@Override
	public boolean isMeleeWeapon() {
		return true;
	}

	@Override
	public boolean mineBlock(ItemStack itemstack, Level world, BlockState blockstate, BlockPos pos, LivingEntity entity) {
		if (((Player)entity).getCooldowns().isOnCooldown(this)) {
			double x = pos.getX();
			double y = pos.getY();
			double z = pos.getZ();
			double i = 0;
			double j = 0;
			i = -2;
			for (int index0 = 0; index0 < 5; index0++) {
				j = -2;
				for (int index1 = 0; index1 < 5; index1++) {
					if (i != 0 || j != 0) {
						if (entity.getXRot() > 40 || entity.getXRot() < -40) {
							if ((world.getBlockState(BlockPos.containing(x + i, y, z + j))).is(blockstate.getBlock())) {
								{
									BlockPos _pos = BlockPos.containing(x + i, y, z + j);
									Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(x, y, z), null);
									world.destroyBlock(_pos, false);
								}
							}
						} else if ((entity.getDirection()).getAxis() == Direction.Axis.Z) {
							if ((world.getBlockState(BlockPos.containing(x + i, y + j, z))).is(blockstate.getBlock())) {
								{
									BlockPos _pos = BlockPos.containing(x + i, y + j, z);
									Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(x, y, z), null);
									world.destroyBlock(_pos, false);
								}
							}
						} else if ((entity.getDirection()).getAxis() == Direction.Axis.X) {
							if ((world.getBlockState(BlockPos.containing(x, y + j, z + i))).is(blockstate.getBlock())) {
								{
									BlockPos _pos = BlockPos.containing(x, y + j, z + i);
									Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(x, y, z), null);
									world.destroyBlock(_pos, false);
								}
							}
						}
					}
					j = j + 1;
				}
				i = i + 1;
			}
		}
		return super.mineBlock(itemstack, world, blockstate, pos, entity);
	}

	@Override
	public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity2, LivingEntity user) {
		itemStack.hurtAndBreak(1, user, livingEntity -> livingEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND));

		if (user.level() instanceof ServerLevel serverLevel) {
			if (!user.isSilent()) {
				user.level().playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, user.getSoundSource(), 10.0f, 1.0f);
			}
			float reachs = AttackFind.reachAttackReach(user, itemStack, 4f, 2f, 3f, 0.5f);
			List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, user.getBoundingBox().inflate(reachs, reachs, reachs));
			for (LivingEntity hurt : list) {
				if (hurt == null || hurt.distanceTo(user) > reachs * 4 || hurt == livingEntity2 || hurt == user) continue;
				if (AttackFind.FindCanNotAttack(user, hurt)) continue;
				AttackFind.attackBegin(user, hurt);
				AttackFind.attackAfter(user, hurt, 1f, 0.5f, false, 0f);
			}
		}
		if (user instanceof Player player) {
			Main.sweepAttack(player);
		}
		else {
			double d = -Mth.sin(user.getYRot() * 0.017453292f);
			double d2 = Mth.cos(user.getYRot() * 0.017453292f);
			if (user.level() instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, user.getX() + d, user.getY(0.5), user.getZ() + d2, 0, d, 0.0, d2, 0.0);
			}
		}
		return true;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.BOW;
	}

	public MerorScytheShootEntity truesBy;

	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 72000;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (player.isShiftKeyDown()) {
			player.getCooldowns().addCooldown(this, 600);
			itemStack.hurtAndBreak(2, player, livingEntitys -> livingEntitys.broadcastBreakEvent(EquipmentSlot.MAINHAND));
			level.playSound(null, player.getX(), player.getY(), player.getZ(), JerotesVillageSoundEvents.MEROR_TOOL_USE, SoundSource.NEUTRAL, 1.5f, 0.4f / (level.getRandom().nextFloat() * 0.4f + 0.8f));
			return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
		}
		else{
			if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
				return InteractionResultHolder.fail(itemStack);
			}
			if (!level.isClientSide) {
				if (level instanceof ServerLevel projectileLevel) {
					itemStack.hurtAndBreak(2, player, livingEntitys -> livingEntitys.broadcastBreakEvent(EquipmentSlot.MAINHAND));
					MerorScytheShootEntity shoot = new MerorScytheShootEntity(projectileLevel, player, new ItemStack(Items.AIR));
					shoot.setPos(player.getX(), player.getEyeY() + 0.2, player.getZ());
					shoot.shootFromRotation(player, player.getXRot(), player.getYRot(), 5.0f, 3f, 1.0f);
					shoot.setOwner(player);
					shoot.setNoGravity(true);
					shoot.pickup = AbstractArrow.Pickup.DISALLOWED;
					projectileLevel.addFreshEntity(shoot);
					projectileLevel.playSound(null, player, JerotesVillageSoundEvents.MEROR_TOOL_USE, SoundSource.NEUTRAL, 1.0f, 1.0f);
					truesBy = shoot;
				}
			}
			player.startUsingItem(interactionHand);
			return InteractionResultHolder.consume(itemStack);
		}
    }

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName_0().withStyle(ChatFormatting.GRAY));
		list.add(this.getDisplayName_1().withStyle(ChatFormatting.GRAY));
		list.add(this.getDisplayName_2().withStyle(ChatFormatting.GRAY));
		list.add(this.getDisplayName_3().withStyle(ChatFormatting.GRAY));
	}

	public MutableComponent getDisplayName_0() {
		return Component.translatable(this.getDescriptionId() + ".desc_0");
	}
	public MutableComponent getDisplayName_1() {
		return Component.translatable(this.getDescriptionId() + ".desc_1");
	}
	public MutableComponent getDisplayName_2() {
		return Component.translatable(this.getDescriptionId() + ".desc_2");
	}
	public MutableComponent getDisplayName_3() {
		return Component.translatable(this.getDescriptionId() + ".desc_3");
	}
}
