package com.jerotes.jerotesvillage.item.Tool;

import com.jerotes.jerotes.entity.Interface.InventoryEntity;
import com.jerotes.jerotes.init.JerotesPotions;
import com.jerotes.jerotes.item.Interface.CanGetOffHand;
import com.jerotes.jerotes.item.Interface.JerotesItemThrowUse;
import com.jerotes.jerotesvillage.entity.Shoot.ThrowingBall.BaseThrowingBallEntity;
import com.jerotes.jerotesvillage.entity.Shoot.ThrowingBall.ThrowingStubbornStoneEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEnchantments;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class ItemToolBaseThrowingBall extends Item implements JerotesItemThrowUse, CanGetOffHand {
    private final int effectLevel;
    private final float baseDamage;
    private final float addDamage;

    public ItemToolBaseThrowingBall(Properties properties, int effectLevel, float baseDamage, float addDamage) {
        super(properties);
        this.effectLevel = effectLevel;
        this.baseDamage = baseDamage;
        this.addDamage = addDamage;
    }

    public boolean getOffHandItem(LivingEntity livingEntity) {
        return livingEntity instanceof InventoryEntity inventoryEntity
                && inventoryEntity.canUseThrow()
                && livingEntity.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof ItemToolBaseThrowingBallGlove;
    }

    public void addEffects(LivingEntity livingEntity) {
        if (!livingEntity.level().isClientSide) {
            livingEntity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.THROWING_STUBBORN_STONE.get(), 5, effectLevel - 1, false, false));
        }
    }

    @Override
    public boolean isJerotesThrow() {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (usePotion(level, player, interactionHand)) {
            return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
        }
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.NEUTRAL, 0.5f, 0.4f / (level.getRandom().nextFloat() * 0.4f + 0.8f));
        if (!level.isClientSide) {
            Projectile throwItem = useJerotesThrowShoot(player, interactionHand);
            throwItem.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, useJerotesThrowShootSpeed(player, interactionHand), 1.0f);
            level.addFreshEntity(throwItem);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    public boolean usePotion(Level level, LivingEntity livingEntity, InteractionHand interactionHand) {
        ItemStack main = livingEntity.getItemInHand(interactionHand);
        ItemStack off = livingEntity.getOffhandItem();
        if (main.getItem() instanceof ItemToolBaseThrowingBall && interactionHand == InteractionHand.MAIN_HAND) {
            if (off.getItem() instanceof PotionItem) {
                Potion offPotion = PotionUtils.getPotion(off);
                Potion mainPotion = PotionUtils.getPotion(main);
                if (!Objects.equals(offPotion, JerotesPotions.WASTE.get()) && !Objects.equals(offPotion, mainPotion)) {
                    CompoundTag offTag = off.getTag();
                    CompoundTag mainTag = main.getOrCreateTag();
                    if (offTag != null && offTag.contains("Potion", Tag.TAG_STRING)) {
                        mainTag.putString("Potion", offTag.getString("Potion"));
                    } else {
                        mainTag.putString("Potion", "minecraft:water");
                    }
                    if (offTag != null && offTag.contains("CustomPotionEffects", Tag.TAG_LIST)) {
                        mainTag.put("CustomPotionEffects", offTag.getList("CustomPotionEffects", Tag.TAG_COMPOUND).copy());
                    } else {
                        mainTag.remove("CustomPotionEffects");
                    }
                    if (offTag != null && offTag.contains("CustomPotionColor", Tag.TAG_INT)) {
                        mainTag.putInt("CustomPotionColor", offTag.getInt("CustomPotionColor"));
                    } else {
                        mainTag.remove("CustomPotionColor");
                    }
                    if (livingEntity instanceof Player player && !player.getAbilities().instabuild) {
                        PotionUtils.setPotion(off, JerotesPotions.WASTE.get());
                        CompoundTag offTag2 = off.getTag();
                        if (offTag2 != null) {
                            offTag2.remove("CustomPotionEffects");
                            offTag2.remove("CustomPotionColor");
                        }
                    }
                    level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(),
                            SoundEvents.BREWING_STAND_BREW, SoundSource.NEUTRAL,
                            0.5f, 0.4f / (level.getRandom().nextFloat() * 0.4f + 0.8f));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Projectile useJerotesThrowShoot(LivingEntity livingEntity, InteractionHand interactionHand) {
        if (livingEntity.getItemInHand(interactionHand).getItem() instanceof ItemToolBaseThrowingBall itemToolBaseThrowingBall) {
            if (!livingEntity.level().isClientSide()) {
                BaseThrowingBallEntity throwItem = itemToolBaseThrowingBall.throwingBall(livingEntity.level(), livingEntity);
                throwItem.setItem(livingEntity.getItemInHand(interactionHand));
                if (livingEntity.getAttribute(Attributes.MOVEMENT_SPEED) != null) {
                    throwItem.speedDamage = livingEntity.getAttributeValue(Attributes.MOVEMENT_SPEED) * 15;
                    if (livingEntity.isSprinting()) {
                        throwItem.speedDamage = livingEntity.getAttributeValue(Attributes.MOVEMENT_SPEED) * 30;
                    } else if (livingEntity.isShiftKeyDown()) {
                        throwItem.speedDamage = livingEntity.getAttributeValue(Attributes.MOVEMENT_SPEED) * 7.5;
                    }
                }
                throwItem.water = livingEntity.onGround() && !livingEntity.isInFluidType();
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
                throwItem.addMaxBackCount = livingEntity.getItemInHand(interactionHand).getEnchantmentLevel(JerotesVillageEnchantments.ELASTICITY.get());
                if (interactionHand == InteractionHand.MAIN_HAND && livingEntity.getOffhandItem().getItem() instanceof FireworkRocketItem) {
                    if (!livingEntity.isSilent()) {
                        livingEntity.level().playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.FIREWORK_ROCKET_SHOOT, SoundSource.NEUTRAL, 0.5f, 0.4f / (livingEntity.getRandom().nextFloat() * 0.4f + 0.8f));
                    }
                    throwItem.setItem(livingEntity.getOffhandItem());
                    if (livingEntity instanceof Player player) {
                        if (!player.getAbilities().instabuild) {
                            player.getOffhandItem().shrink(1);
                        }
                    }
                }
                else if (interactionHand == InteractionHand.OFF_HAND &&livingEntity.getMainHandItem().getItem() instanceof FireworkRocketItem) {
                    if (!livingEntity.isSilent()) {
                        livingEntity.level().playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.FIREWORK_ROCKET_SHOOT, SoundSource.NEUTRAL, 0.5f, 0.4f / (livingEntity.getRandom().nextFloat() * 0.4f + 0.8f));
                    }
                    throwItem.setItem(livingEntity.getMainHandItem());
                    if (livingEntity instanceof Player player) {
                        if (!player.getAbilities().instabuild) {
                            player.getMainHandItem().shrink(1);
                        }
                    }
                }
                return throwItem;
            }
        }
        return null;
    }

    @Override
    public float useJerotesThrowShootSpeed(LivingEntity livingEntity, InteractionHand interactionHand) {
        if (livingEntity.getItemInHand(interactionHand).getItem() instanceof ItemToolBaseThrowingBall itemToolBaseThrowingBall) {
            if (!livingEntity.level().isClientSide()) {
                BaseThrowingBallEntity throwItem = itemToolBaseThrowingBall.throwingBall(livingEntity.level(), livingEntity);
                throwItem.setItem(livingEntity.getItemInHand(interactionHand));
                double attackofspeeds = 0;
                if (livingEntity.getAttribute(Attributes.MOVEMENT_SPEED) != null) {
                    attackofspeeds = livingEntity.getAttributeValue(Attributes.MOVEMENT_SPEED) * 5;
                    throwItem.speedDamage = livingEntity.getAttributeValue(Attributes.MOVEMENT_SPEED) * 15;
                    if (livingEntity.isSprinting()) {
                        attackofspeeds = livingEntity.getAttributeValue(Attributes.MOVEMENT_SPEED) * 10;
                        throwItem.speedDamage = livingEntity.getAttributeValue(Attributes.MOVEMENT_SPEED) * 30;
                    } else if (livingEntity.isShiftKeyDown()) {
                        attackofspeeds = livingEntity.getAttributeValue(Attributes.MOVEMENT_SPEED) * 2;
                        throwItem.speedDamage = livingEntity.getAttributeValue(Attributes.MOVEMENT_SPEED) * 7.5;
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
                throwItem.addMaxBackCount = livingEntity.getItemInHand(interactionHand).getEnchantmentLevel(JerotesVillageEnchantments.ELASTICITY.get());
                double deltaMove = livingEntity.getDeltaMovement().x() + livingEntity.getDeltaMovement().y() + livingEntity.getDeltaMovement().z();
                return (float) (0.6f + attackofspeeds + deltaMove);
            }
        }
        return 1.6f;
    }

    public BaseThrowingBallEntity throwingBall(Level level, LivingEntity livingEntity) {
        return new ThrowingStubbornStoneEntity(level, livingEntity);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.jerotesvillage.throwing_ball", this.effectLevel, this.baseDamage, this.addDamage).withStyle(ChatFormatting.YELLOW));
        super.appendHoverText(itemStack, level, list, tooltipFlag);
    }
}