package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.entity.Interface.JerotesChangeLivingEntity;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.entity.Humanoid.CarvedVillagerEntity;
import com.jerotes.jerotesvillage.event.RelationshipEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class CarvedHorseSlab extends Item {
	public CarvedHorseSlab() {
			super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
	}


	public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
		if (livingEntity instanceof AbstractVillager villager && ((villager instanceof CarvedVillagerEntity carvedVillager && (carvedVillager.trusts(player.getUUID()) || RelationshipEvent.MoreCopperCarvedCompanyRelationship(player, 500))) || (villager instanceof Villager && RelationshipEvent.MoreCopperCarvedCompanyRelationship(player, 500)))) {
			player.swing(InteractionHand.MAIN_HAND);
			if (villager.isPassenger()) {
				if (!villager.level().isClientSide) {
					villager.stopRiding();
				}
			}
			else {
				boolean bl = false;
				List<Mob> list = villager.level().getEntitiesOfClass(Mob.class, villager.getBoundingBox().inflate(8.0, 8.0, 8.0));
				double d = Double.MAX_VALUE;
				for (Mob ride : list) {
					if (ride == null) continue;
					if ((ride.distanceToSqr(ride)) > d) continue;
					if (!(ride instanceof Saddleable saddleable)) continue;
					if (ride.isAggressive() || ride.getTarget() != null)
						continue;
					if (!saddleable.isSaddled()) continue;
					if (ride instanceof JerotesChangeLivingEntity rideAbout && !rideAbout.canAddPassengerJerotes(villager))
						continue;
					if (!AttackFind.canRideAboutEnemy(ride, ride instanceof OwnableEntity ownable ? ownable.getOwner() : null, ride.getControllingPassenger(), villager))
						continue;
					if (ride instanceof OwnableEntity ownable && (ownable.getOwner() != null && ownable.getOwner().isAlive() && ownable.getOwner() != player && ownable.getOwner() != villager))
						continue;
					if (!villager.level().isClientSide()) {
						villager.startRiding(ride);
					}
					bl = true;
				}
				if (!bl) {
					if (player.level() instanceof ServerLevel) {
						player.sendSystemMessage(Component.translatable("message.jerotesvillage.carved_ride_not_find", livingEntity.getDisplayName()).withStyle(ChatFormatting.GOLD));
					}
				}
			}
			return InteractionResult.sidedSuccess(player.level().isClientSide());
		}
		return InteractionResult.PASS;
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
