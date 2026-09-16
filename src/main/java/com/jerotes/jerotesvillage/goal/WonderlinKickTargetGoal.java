package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotesvillage.entity.Neutral.Wonderlin.BaseWonderlinEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotes.util.EntityAndItemFind;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;

public class WonderlinKickTargetGoal<T extends LivingEntity> extends WonderlinTargetGoal {
    private static final int DEFAULT_RANDOM_INTERVAL = 180;
    protected final Class<T> targetType;
    protected final int randomInterval;
    @Nullable
    protected LivingEntity target;
    protected TargetingConditions targetConditions;

    public WonderlinKickTargetGoal(BaseWonderlinEntity mob, Class<T> class_, boolean bl) {
        this(mob, class_, 180, bl, false, null);
    }

    public WonderlinKickTargetGoal(BaseWonderlinEntity mob, Class<T> class_, boolean bl, Predicate<LivingEntity> predicate) {
        this(mob, class_, 180, bl, false, predicate);
    }

    public WonderlinKickTargetGoal(BaseWonderlinEntity mob, Class<T> class_, boolean bl, boolean bl2) {
        this(mob, class_, 180, bl, bl2, null);
    }

    public WonderlinKickTargetGoal(BaseWonderlinEntity mob, Class<T> class_, int n, boolean bl, boolean bl2, @Nullable Predicate<LivingEntity> predicate) {
        super(mob, bl, bl2);
        this.targetType = class_;
        this.randomInterval = NearestAttackableTargetGoal.reducedTickDelay(n);
        this.setFlags(EnumSet.of(Flag.TARGET));
        this.targetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(predicate);
    }

    @Override
    public boolean canUse() {
        if (this.mob.getTarget() != null) {
            return false;
        }
        if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
            return false;
        }
        this.findTarget();
        boolean helmet = this.target != null &&
                (this.target.getItemBySlot(EquipmentSlot.HEAD).getEnchantmentLevel(Enchantments.THORNS) > 0 || this.target.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.MOUNTAIN_REALM_HELMET.get() || this.target.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.WONDERLIN_FAKE_EARS.get());
        boolean chestplate = this.target != null &&
                (this.target.getItemBySlot(EquipmentSlot.CHEST).getEnchantmentLevel(Enchantments.THORNS) > 0 || this.target.getItemBySlot(EquipmentSlot.CHEST).getItem() == JerotesVillageItems.MOUNTAIN_REALM_CHESTPLATE.get());
        boolean legs = this.target != null &&
                (this.target.getItemBySlot(EquipmentSlot.LEGS).getEnchantmentLevel(Enchantments.THORNS) > 0 || this.target.getItemBySlot(EquipmentSlot.LEGS).getItem() == JerotesVillageItems.MOUNTAIN_REALM_LEGGINGS.get());
        boolean feet = this.target != null &&
                (this.target.getItemBySlot(EquipmentSlot.FEET).getEnchantmentLevel(Enchantments.THORNS) > 0 || this.target.getItemBySlot(EquipmentSlot.FEET).getItem() == JerotesVillageItems.MOUNTAIN_REALM_BOOTS.get());
        return this.target != null && (this.target instanceof Mob || this.target instanceof Player) && !(helmet || chestplate || legs || feet);
    }

    @Override
    public boolean canContinueToUse() {
        if (this.mob.getTarget() != null) {
            return false;
        }
        return super.canContinueToUse();
    }


    protected AABB getNotAttackTargetSearchArea(double d) {
        return this.mob.getBoundingBox().inflate(d, 4.0, d);
    }

    protected void findTarget() {
        this.target = this.targetType == Player.class || this.targetType == ServerPlayer.class ? this.mob.level().getNearestPlayer(this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ()) : this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(this.targetType, this.getNotAttackTargetSearchArea(this.getFollowDistance()), livingEntity -> livingEntity.getHealth() > 1f && !Main.canSee(this.mob, livingEntity) && !livingEntity.hasEffect(MobEffects.UNLUCK) && !(livingEntity.getHealth() > this.mob.getHealth() * 10 || !(Main.mobSizeSmall(livingEntity) || Main.mobSizeMedium(livingEntity)) || EntityAndItemFind.isBoss(livingEntity.getType()))), this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
    }

    @Override
    public void start() {
        this.mob.setNotAttackTarget(this.target);
        super.start();
    }

    public void setNotAttackTarget(@Nullable LivingEntity livingEntity) {
        this.target = livingEntity;
    }
}

