package com.jerotes.jerotesvillage.item.Tool;

import com.jerotes.jerotes.entity.Interface.CatchEntity;
import com.jerotes.jerotes.entity.Interface.InventoryEntity;
import com.jerotes.jerotes.item.Interface.CanGetOffHand;
import com.jerotes.jerotes.item.ItemCatchBase;
import com.jerotes.jerotes.item.Interface.JerotesItemThrowUse;
import com.jerotes.jerotesvillage.entity.Shoot.ThrowingBall.BaseThrowingBallEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEnchantments;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;
import java.util.List;

public class ItemToolBaseThrowingBallGlove extends Item implements JerotesItemThrowUse, CanGetOffHand {
	private final float addSpeed;
	public ItemToolBaseThrowingBallGlove(Properties properties, float addSpeed) {
		super(properties);
		this.addSpeed = addSpeed;
	}

    public boolean getOffHandItem(LivingEntity livingEntity) {
        return livingEntity instanceof InventoryEntity inventoryEntity
                && inventoryEntity.canUseThrow()
                && livingEntity.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof ItemToolBaseThrowingBall;
    }


    public float getAddSpeed() {
        return addSpeed;
    }
    @Override
    public boolean isJerotesThrow(LivingEntity livingEntity) {
        return livingEntity.getMainHandItem().getItem() instanceof ItemToolBaseThrowingBallGlove itemToolBaseThrowingBallGlove && livingEntity.getOffhandItem().getItem() instanceof ItemToolBaseThrowingBall itemToolBaseThrowingBall;
    }
    @Override
    public boolean isJerotesThrowShrinkSelf() {
        return false;
    }
    @Override
    public boolean isJerotesThrowShrinkOther() {
        return true;
    }

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		if (interactionHand == InteractionHand.MAIN_HAND
                && (player.getOffhandItem().getItem() instanceof ItemToolBaseThrowingBall || player.getOffhandItem().getItem() instanceof ItemCatchBase)) {
            ItemStack itemStack = player.getItemInHand(interactionHand);
            ItemStack throwingBall = player.getOffhandItem();
			level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.NEUTRAL, 0.5f, 0.4f / (level.getRandom().nextFloat() * 0.4f + 0.8f));
			if (!(throwingBall.getItem() instanceof ItemCatchBase itemCatchBase)) {
                if (!level.isClientSide) {
                    Projectile throwItem = useJerotesThrowShoot(player, interactionHand);
                    throwItem.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, useJerotesThrowShootSpeed(player, interactionHand), 1.0f);
                    level.addFreshEntity(throwItem);
                }
            }
            //扔生物
            else {
                if (level instanceof ServerLevel serverLevel) {
                    EntityType<?> entityType = itemCatchBase.getEntityType();
                    Entity entity = entityType.spawn(serverLevel, throwingBall, null, player.getOnPos().above(), MobSpawnType.BUCKET, true, false);
                    if (entity instanceof CatchEntity catchEntity) {
                        catchEntity.loadFromCatchTag(throwingBall.getOrCreateTag());
                        catchEntity.setFromCatch(true);
                        entity.moveTo(player.getEyePosition());
                        entity.setXRot(player.getXRot());
                        entity.setYRot(player.getYRot());
                        entity.setYHeadRot(player.getYHeadRot());
                        entity.setYBodyRot(player.yBodyRot);
                        float f = player.getYRot();
                        float f2 = player.getXRot();
                        float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
                        float f4 = -Mth.sin(f2 * 0.017453292f);
                        float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
                        float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
                        float f7 = 1.5f;
                        entity.setOnGround(false);
                        entity.setDeltaMovement(entity.getDeltaMovement().add(f3 *= f7 / f6 * 2, f4 *= f7 / f6 * 2, f5 *= f7 / f6 * 2));
                    }
                    serverLevel.gameEvent(player, GameEvent.ENTITY_PLACE, player.getOnPos().above());
                }
            }
            player.awardStat(Stats.ITEM_USED.get(player.getOffhandItem().getItem()));
            if (!player.getAbilities().instabuild) {
                throwingBall.shrink(1);
            }
			itemStack.hurtAndBreak(1,  player, livingEntity -> livingEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
			return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
        }
		return super.use(level, player, interactionHand);
	}

    @Override
    public Projectile useJerotesThrowShoot(LivingEntity livingEntity, InteractionHand interactionHand) {
        if (livingEntity.getMainHandItem().getItem() instanceof ItemToolBaseThrowingBallGlove itemToolBaseThrowingBallGlove && livingEntity.getOffhandItem().getItem() instanceof ItemToolBaseThrowingBall itemToolBaseThrowingBall) {
            if (!livingEntity.level().isClientSide()) {
                BaseThrowingBallEntity throwItem = itemToolBaseThrowingBall.throwingBall(livingEntity.level(), livingEntity);
                throwItem.setItem(livingEntity.getOffhandItem());
                if (livingEntity.getAttribute(Attributes.MOVEMENT_SPEED) != null) {
                    float add = itemToolBaseThrowingBallGlove.getAddSpeed();
                    if (livingEntity.getAttribute(Attributes.MOVEMENT_SPEED) != null) {
                        throwItem.speedDamage = livingEntity.getAttributeValue(Attributes.MOVEMENT_SPEED) * 15 * add;
                        if (livingEntity.isSprinting()) {
                            throwItem.speedDamage = livingEntity.getAttributeValue(Attributes.MOVEMENT_SPEED) * 30 * add;
                        } else if (livingEntity.isShiftKeyDown()) {
                            throwItem.speedDamage = livingEntity.getAttributeValue(Attributes.MOVEMENT_SPEED) * 7.5 * add;
                        }
                    }
                }
                double deltaMoveX = livingEntity.getDeltaMovement().x();
                double deltaMoveY = livingEntity.getDeltaMovement().y();
                double deltaMoveZ = livingEntity.getDeltaMovement().z();
                if (livingEntity.getDeltaMovement().x() < 0) {
                    deltaMoveX = -livingEntity.getDeltaMovement().x();
                }
                if (livingEntity.getDeltaMovement().y() < 0) {
                    deltaMoveY = -livingEntity.getDeltaMovement().y();
                }
                if (livingEntity.getDeltaMovement().z() < 0) {
                    deltaMoveZ = -livingEntity.getDeltaMovement().z();
                }
                throwItem.deltaMoveDamage = deltaMoveX + deltaMoveY + deltaMoveZ;
                throwItem.addMaxBackCount = livingEntity.getMainHandItem().getEnchantmentLevel(JerotesVillageEnchantments.ELASTICITY.get()) + livingEntity.getOffhandItem().getEnchantmentLevel(JerotesVillageEnchantments.ELASTICITY.get());
                return throwItem;
            }
        }
        return null;
    }
    @Override
    public float useJerotesThrowShootSpeed(LivingEntity livingEntity, InteractionHand interactionHand) {
        if (livingEntity.getMainHandItem().getItem() instanceof ItemToolBaseThrowingBallGlove itemToolBaseThrowingBallGlove && livingEntity.getOffhandItem().getItem() instanceof ItemToolBaseThrowingBall itemToolBaseThrowingBall) {
            if (!livingEntity.level().isClientSide()) {
                BaseThrowingBallEntity throwItem = itemToolBaseThrowingBall.throwingBall(livingEntity.level(), livingEntity);
                throwItem.setItem(livingEntity.getOffhandItem());
                float add = itemToolBaseThrowingBallGlove.getAddSpeed();
                double attackofspeeds = 0;
                if (livingEntity.getAttribute(Attributes.MOVEMENT_SPEED) != null) {
                    attackofspeeds = livingEntity.getAttributeValue(Attributes.MOVEMENT_SPEED) * 5 * add;
                    throwItem.speedDamage = livingEntity.getAttributeValue(Attributes.MOVEMENT_SPEED) * 15 * add;
                    if (livingEntity.isSprinting()) {
                        attackofspeeds = livingEntity.getAttributeValue(Attributes.MOVEMENT_SPEED) * 10 * add;
                        throwItem.speedDamage = livingEntity.getAttributeValue(Attributes.MOVEMENT_SPEED) * 30 * add;
                    } else if (livingEntity.isShiftKeyDown()) {
                        attackofspeeds = livingEntity.getAttributeValue(Attributes.MOVEMENT_SPEED) * 2 * add;
                        throwItem.speedDamage = livingEntity.getAttributeValue(Attributes.MOVEMENT_SPEED) * 7.5 * add;
                    }
                }
                double deltaMoveX = livingEntity.getDeltaMovement().x();
                double deltaMoveY = livingEntity.getDeltaMovement().y();
                double deltaMoveZ = livingEntity.getDeltaMovement().z();
                if (livingEntity.getDeltaMovement().x() < 0) {
                    deltaMoveX = -livingEntity.getDeltaMovement().x();
                }
                if (livingEntity.getDeltaMovement().y() < 0) {
                    deltaMoveY = -livingEntity.getDeltaMovement().y();
                }
                if (livingEntity.getDeltaMovement().z() < 0) {
                    deltaMoveZ = -livingEntity.getDeltaMovement().z();
                }
                throwItem.deltaMoveDamage = deltaMoveX + deltaMoveY + deltaMoveZ;
                throwItem.addMaxBackCount = livingEntity.getMainHandItem().getEnchantmentLevel(JerotesVillageEnchantments.ELASTICITY.get()) + livingEntity.getOffhandItem().getEnchantmentLevel(JerotesVillageEnchantments.ELASTICITY.get());
                double deltaMove = livingEntity.getDeltaMovement().x() + livingEntity.getDeltaMovement().y() + livingEntity.getDeltaMovement().z();
                return (float) (0.6f + attackofspeeds + deltaMove);
            }
        }
        return 1.6f;
    }

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(Component.translatable("item.jerotesvillage.throwing_ball_glove", this.addSpeed).withStyle(ChatFormatting.YELLOW));
		super.appendHoverText(itemStack, level, list, tooltipFlag);
	}
}
