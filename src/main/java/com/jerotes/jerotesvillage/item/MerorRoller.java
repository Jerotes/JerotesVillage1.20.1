package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotes.item.Tool.*;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class MerorRoller extends ItemToolBaseShovel {
	public MerorRoller() {
		super(new Tier() {
			public int getUses() {
				return 18800;
			}

			public float getSpeed() {
				return 18f;
			}

			public float getAttackDamageBonus() {
				return 12.5f;
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
		}, -1, 1.8f - 4f, new Properties().fireResistant().rarity(Rarity.EPIC));
	}

	@Override
	public boolean isMeleeWeapon() {
		return true;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.BLOCK;
	}

	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 72000;
	}

	@Override
	public boolean mineBlock(ItemStack itemstack, Level world, BlockState blockstate, BlockPos pos, LivingEntity entity) {
		if (((Player)entity).getCooldowns().isOnCooldown(this)){
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
			player.startUsingItem(interactionHand);
			return InteractionResultHolder.consume(itemStack);
		}
	}

	@Override
	public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int count) {
		if (count % 60 == 0 && livingEntity instanceof Player player) {
			itemStack.hurtAndBreak(2, player, livingEntitys -> livingEntitys.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		}
		if (livingEntity.level() instanceof ServerLevel serverLevel) {
			if (!livingEntity.isSilent()) {
				livingEntity.level().playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), JerotesVillageSoundEvents.MEROR_TOOL_USE, livingEntity.getSoundSource(), 10.0f, 1.0f);
			}
			List<ItemEntity> list = serverLevel.getEntitiesOfClass(ItemEntity.class, livingEntity.getBoundingBox().inflate(64, 64, 64));
			for (ItemEntity drop : list) {
				double d;
				Vec3 vec3;
				if (drop == null || drop.distanceTo(livingEntity) > Double.MAX_VALUE) continue;
				if ((d = (vec3 = new Vec3(livingEntity.getX() - drop.getX(), livingEntity.getY() + (double)livingEntity.getEyeHeight() / 2.0 - drop.getY(), livingEntity.getZ() - drop.getZ())).lengthSqr()) < 256.0) {
					double d2 = 1.0 - Math.sqrt(d) / 24.0;
					drop.setDeltaMovement(drop.getDeltaMovement().add(vec3.normalize().scale(d2 * d2)));
				}
			}
			List<ExperienceOrb> list2 = serverLevel.getEntitiesOfClass(ExperienceOrb.class, livingEntity.getBoundingBox().inflate(32, 32, 32));
			for (ExperienceOrb drop : list2) {
				double d;
				Vec3 vec3;
				if (drop == null || drop.distanceTo(livingEntity) > Double.MAX_VALUE) continue;
				if ((d = (vec3 = new Vec3(livingEntity.getX() - drop.getX(), livingEntity.getY() + (double)livingEntity.getEyeHeight() / 2.0 - drop.getY(), livingEntity.getZ() - drop.getZ())).lengthSqr()) < 256.0) {
					double d2 = 1.0 - Math.sqrt(d) / 24.0;
					drop.setDeltaMovement(drop.getDeltaMovement().add(vec3.normalize().scale(d2 * d2)));
				}
			}
			double d3 = -Mth.sin(livingEntity.getYRot() * ((float)Math.PI / 180F));
			double d4 = Mth.cos(livingEntity.getYRot() * ((float)Math.PI / 180F));
			serverLevel.sendParticles(JerotesVillageParticleTypes.MEROR.get(),  livingEntity.getRandomX(0.5), livingEntity.getRandomY(), livingEntity.getRandomZ(0.5), 4, d3, 0.0, d4, 0.0);
		}
	}

	@Override
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int n) {
		if (livingEntity instanceof Player player2) {
			player2.awardStat(Stats.ITEM_USED.get(this));
		}
	}

	@Override
	public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity2, LivingEntity livingEntity3) {
		itemStack.hurtAndBreak(1, livingEntity3, livingEntity -> livingEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		return true;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName_0().withStyle(ChatFormatting.GRAY));
		list.add(this.getDisplayName_1().withStyle(ChatFormatting.GRAY));
		list.add(this.getDisplayName_2().withStyle(ChatFormatting.GRAY));
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
}
