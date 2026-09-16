package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Interface.ItemSpecialEffect;
import com.jerotes.jerotes.item.Interface.JerotesItemThrowUse;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.VirtualCaveFishingNetEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nullable;
import java.util.List;

public class VirtualCaveFishingNet extends Item implements Equipable, ItemSpecialEffect, JerotesItemThrowUse {
	public VirtualCaveFishingNet() {
		super(new Properties().stacksTo(16).rarity(Rarity.COMMON));
	}

	@Override
	public void attackUse(Entity self, Entity attackTo) {
		if (attackTo instanceof LivingEntity livingEntity) {
			if (self instanceof Mob) {
				if ((livingEntity.level().getBlockState(BlockPos.containing(attackTo.getX(), attackTo.getY(), attackTo.getZ()))).getBlock() == Blocks.WATER) {
					if (!livingEntity.level().isClientSide) {
						livingEntity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.FISHING_NET_CONFINEMET.get(), 50, 2, false, false));
					}
				}
				else if ((livingEntity.level().getBlockState(BlockPos.containing(attackTo.getX(), attackTo.getY(), attackTo.getZ()))).is(Blocks.AIR)) {
					if (!livingEntity.level().isClientSide) {
						livingEntity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.FISHING_NET_CONFINEMET.get(), 50, 2, false, false));
					}
				}
			}
		}
	}

	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 72000;
	}

	@Override
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int n) {
		if (!(livingEntity instanceof Player)) {
			return;
		}
		Player player = (Player)livingEntity;
		int n2 = this.getUseDuration(itemStack) - n;
		if (n2 < 10) {
			return;
		}
		if (!level.isClientSide) {
			Projectile thrownItem = useJerotesThrowShoot(player, player.getUsedItemHand());
			if (thrownItem instanceof VirtualCaveFishingNetEntity virtualCaveFishingNetEntity) {
				virtualCaveFishingNetEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, useJerotesThrowShootSpeed(player, player.getUsedItemHand()), 1.0f);
				if (player.getAbilities().instabuild) {
					virtualCaveFishingNetEntity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
				}
				level.addFreshEntity(virtualCaveFishingNetEntity);
				level.playSound(null, virtualCaveFishingNetEntity, SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.PLAYERS, 1.0f, 1.0f);
			}
			if (!player.getAbilities().instabuild) {
				itemStack.shrink(1);
			}
		}
		player.awardStat(Stats.ITEM_USED.get(this));
	}

	@Override
	public Projectile useJerotesThrowShoot(LivingEntity livingEntity, InteractionHand interactionHand) {
		if (livingEntity.getItemInHand(interactionHand).getItem() instanceof VirtualCaveFishingNet virtualCaveFishingNet) {
			if (!livingEntity.level().isClientSide()) {
				VirtualCaveFishingNetEntity throwItem = new VirtualCaveFishingNetEntity(livingEntity.level(), livingEntity, livingEntity.getItemInHand(interactionHand));
				return throwItem;
			}
		}
		return null;
	}
	@Override
	public float useJerotesThrowShootSpeed(LivingEntity livingEntity, InteractionHand interactionHand) {
		if (livingEntity.getItemInHand(interactionHand).getItem() instanceof VirtualCaveFishingNet virtualCaveFishingNet) {
			if (!livingEntity.level().isClientSide()) {
				return 1.6f;
			}
		}
		return 1.6f;
	}


	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		player.startUsingItem(interactionHand);
		return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
	}

	@Override
	public boolean isJerotesThrow() {
		return true;
	}

	@Override
	public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity2, LivingEntity livingEntity3) {
		if (livingEntity3 instanceof Player) {
			if ((livingEntity2.level().getBlockState(BlockPos.containing(livingEntity2.getX(), livingEntity2.getY(), livingEntity2.getZ()))).getBlock() == Blocks.WATER) {
				livingEntity2.level().setBlock(BlockPos.containing(livingEntity2.getX(), livingEntity2.getY(), livingEntity2.getZ()), JerotesVillageBlocks.VIRTUAL_CAVE_FISHING_NET_BLOCK.get().defaultBlockState(), 3);
				if (!livingEntity2.level().isClientSide) {
					livingEntity2.addEffect(new MobEffectInstance(JerotesVillageMobEffects.FISHING_NET_CONFINEMET.get(), 50, 2, false, false));
				}
				if (!((Player) livingEntity3).getAbilities().instabuild) {
					itemStack.shrink(1);
				}
			}
			else if ((livingEntity2.level().getBlockState(BlockPos.containing(livingEntity2.getX(), livingEntity2.getY(), livingEntity2.getZ()))).is(Blocks.AIR)) {
				livingEntity2.level().setBlock(BlockPos.containing(livingEntity2.getX(), livingEntity2.getY(), livingEntity2.getZ()), JerotesVillageBlocks.VIRTUAL_CAVE_FISHING_NET_BLOCK.get().defaultBlockState(), 3);
				if (!livingEntity2.level().isClientSide) {
					livingEntity2.addEffect(new MobEffectInstance(JerotesVillageMobEffects.FISHING_NET_CONFINEMET.get(), 50, 2, false, false));
				}
				if (!((Player) livingEntity3).getAbilities().instabuild) {
					itemStack.shrink(1);
				}
			}
		}
		return true;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.SPEAR;
	}

	@Override
	public EquipmentSlot getEquipmentSlot() {
		return EquipmentSlot.HEAD;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName_0().withStyle(ChatFormatting.GRAY));
		list.add(this.getDisplayName_1().withStyle(ChatFormatting.GRAY));
	}
	public MutableComponent getDisplayName_0() {
		return Component.translatable(this.getDescriptionId() + ".desc_0");
	}
	public MutableComponent getDisplayName_1() {
		return Component.translatable(this.getDescriptionId() + ".desc_1");
	}

}

