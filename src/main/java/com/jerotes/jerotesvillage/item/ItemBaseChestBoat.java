package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.entity.Other.Boat.ABaseBoatType;
import com.jerotes.jerotesvillage.entity.Other.Boat.ABaseChestBoatEntity;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Predicate;

public class ItemBaseChestBoat extends Item {
	private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
	private final ABaseBoatType type;

	public ItemBaseChestBoat(Properties properties, ABaseBoatType type) {
		super(properties);
		this.type = type;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
	{
		ItemStack itemStack = player.getItemInHand(hand);
		HitResult rayTraceResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);

		if (rayTraceResult.getType() == HitResult.Type.MISS) {
			return InteractionResultHolder.pass(itemStack);
		}
		else {
			Vec3 view = player.getViewVector(1.0F);
			List<Entity> entityList = level.getEntities(player, player.getBoundingBox().expandTowards(view.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);
			if (!entityList.isEmpty()) {
				Vec3 eyePosition = player.getEyePosition(1.0F);
				for (Entity entity : entityList) {
					AABB aabb = entity.getBoundingBox().inflate(entity.getPickRadius());
					if (aabb.contains(eyePosition)) {
						return InteractionResultHolder.pass(itemStack);
					}
				}
			}

			if (rayTraceResult.getType() == HitResult.Type.BLOCK) {
				ABaseChestBoatEntity boat = new ABaseChestBoatEntity(level, rayTraceResult.getLocation().x, rayTraceResult.getLocation().y, rayTraceResult.getLocation().z);
				boat.setModBoatType(this.type);
				boat.setYRot(player.getYRot());
				if (!level.noCollision(boat, boat.getBoundingBox())) {
					return InteractionResultHolder.fail(itemStack);
				}
				else {
					if (!level.isClientSide) {
						level.addFreshEntity(boat);
						level.gameEvent(player, GameEvent.ENTITY_PLACE, rayTraceResult.getLocation());
						if (!player.getAbilities().instabuild) {
							itemStack.shrink(1);
						}
					}
					player.awardStat(Stats.ITEM_USED.get(this));
					return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
				}
			}
			else {
				return InteractionResultHolder.pass(itemStack);
			}
		}
	}
}