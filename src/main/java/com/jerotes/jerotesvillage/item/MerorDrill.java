package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBasePickaxe;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class MerorDrill extends ItemToolBasePickaxe {
	public MerorDrill() {
		super(new Tier() {
			public int getUses() {
				return 18800;
			}

			public float getSpeed() {
				return 18f;
			}

			public float getAttackDamageBonus() {
				return 14f;
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
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.BOW;
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
						}
						else if ((entity.getDirection()).getAxis() == Direction.Axis.Z) {
							if ((world.getBlockState(BlockPos.containing(x + i, y + j, z))).is(blockstate.getBlock())) {
								{
									BlockPos _pos = BlockPos.containing(x + i, y + j, z);
									Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(x, y, z), null);
									world.destroyBlock(_pos, false);
								}
							}
						}
						else if ((entity.getDirection()).getAxis() == Direction.Axis.X) {
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
		if (livingEntity.getXRot() <= 20 && livingEntity.onGround()) {
			float f = livingEntity.getYRot();
			float f2 = livingEntity.getXRot();
			float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
			float f4 = -Mth.sin(f2 * 0.017453292f);
			float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
			float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
			float f7 = 0.1f;
			livingEntity.push(f3 *= f7 / f6 * 2, f4 *= f7 / f6 * 2, f5 *= f7 / f6 * 2);
		}
		if (livingEntity.level() instanceof ServerLevel serverLevel) {
			//方块破坏
			AABB aABB = livingEntity.getBoundingBox().inflate(1, 0.5, 1).move(0, 0.5, 0);
			if (livingEntity.getXRot() > 40) {
				aABB = livingEntity.getBoundingBox().inflate(1, 1, 1);
			}
			boolean bl = serverLevel.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) || livingEntity instanceof Player;
			for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aABB.minX), Mth.floor(aABB.minY), Mth.floor(aABB.minZ), Mth.floor(aABB.maxX), Mth.floor(aABB.maxY), Mth.floor(aABB.maxZ))) {
				BlockState blockState = serverLevel.getBlockState(blockPos);
				float block = blockState.getDestroySpeed(serverLevel, blockPos);
				if (block >= 20f || block < 0f) continue;
				if (!blockState.is(BlockTags.MINEABLE_WITH_PICKAXE) && block >= 5f) continue;
				bl = serverLevel.destroyBlock(blockPos, true, livingEntity) || bl;
			}
			//攻击
			if (!livingEntity.isSilent()) {
				livingEntity.level().playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), JerotesVillageSoundEvents.MEROR_TOOL_USE, livingEntity.getSoundSource(), 10.0f, 1.0f);
			}
			float reachs = AttackFind.reachAttackReach(livingEntity, itemStack, 3f, 1.5f, 3f, 0.5f);
			List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(reachs, reachs, reachs));
			for (LivingEntity hurt : list) {
				if (hurt == null || hurt.distanceTo(livingEntity) > reachs * 4) continue;
				if (AttackFind.FindCanNotAttack(livingEntity, hurt)) continue;
				if (!Main.canSee(hurt, livingEntity) && !(hurt.getBoundingBox().intersects(livingEntity.getBoundingBox()))) continue;
				if (!hurt.hasLineOfSight(livingEntity) && !(hurt.getBoundingBox().intersects(livingEntity.getBoundingBox()))) continue;
				AttackFind.attackBegin(livingEntity, hurt);
				boolean bl2 = AttackFind.attackAfter(livingEntity, hurt, 0.3f, 0.1f, false, 0f);
				if (bl2) {
					serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, hurt.getX(), hurt.getY(0.5), hurt.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
				}
			}
			double d3 = -Mth.sin(livingEntity.getYRot() * ((float)Math.PI / 180F));
			double d4 = Mth.cos(livingEntity.getYRot() * ((float)Math.PI / 180F));
			serverLevel.sendParticles(JerotesVillageParticleTypes.MEROR.get(),  livingEntity.getRandomX(0.5), livingEntity.getRandomY(), livingEntity.getRandomZ(0.5), 4, d3, 0.0, d4, 0.0);
		}
	}

	@Override
	public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity2, LivingEntity livingEntity3) {
		itemStack.hurtAndBreak(1, livingEntity3, livingEntity -> livingEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		return true;
	}

	@Override
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int n) {
		if (livingEntity instanceof Player player2) {
			player2.awardStat(Stats.ITEM_USED.get(this));
		}
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
