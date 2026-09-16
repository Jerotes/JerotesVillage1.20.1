package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotesvillage.entity.Monster.Resurrecter.ResurrecterEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotesvillage.util.OtherEntityFactionFind;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;

public class ResurrecterAttackTargetGoal<T extends LivingEntity> extends TargetGoal {
    private static final int DEFAULT_RANDOM_INTERVAL = 10;
    protected final Class<T> targetType;
    protected final int randomInterval;
    @Nullable
    protected LivingEntity target;
    protected TargetingConditions targetConditions;

    public ResurrecterAttackTargetGoal(Mob mob, Class<T> class_, boolean bl) {
        this(mob, class_, 10, bl, false, null);
    }

    public ResurrecterAttackTargetGoal(Mob mob, Class<T> class_, boolean bl, Predicate<LivingEntity> predicate) {
        this(mob, class_, 10, bl, false, predicate);
    }

    public ResurrecterAttackTargetGoal(Mob mob, Class<T> class_, boolean bl, boolean bl2) {
        this(mob, class_, 10, bl, bl2, null);
    }

    public ResurrecterAttackTargetGoal(Mob mob, Class<T> class_, int n, boolean bl, boolean bl2, @Nullable Predicate<LivingEntity> predicate) {
        super(mob, bl, bl2);
        this.targetType = class_;
        this.randomInterval = NearestAttackableTargetGoal.reducedTickDelay(n);
        this.setFlags(EnumSet.of(Flag.TARGET));
        this.targetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(predicate);
    }

    @Override
    public boolean canUse() {
        if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
            return false;
        }
        if (!((ResurrecterEntity)this.mob).isAngryTick()) {
            return false;
        }
        this.findTarget();
        boolean helmet = this.target != null && (this.target.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.ANCIENT_SECOND_ROUNDER_NOBLE_HELMET.get() || this.target.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.RESURRECTER_HEAD.get());
        boolean chestplate = this.target != null && this.target.getItemBySlot(EquipmentSlot.CHEST).getItem() == JerotesVillageItems.ANCIENT_SECOND_ROUNDER_NOBLE_CHESTPLATE.get();
        boolean legs = this.target != null && this.target.getItemBySlot(EquipmentSlot.LEGS).getItem() == JerotesVillageItems.ANCIENT_SECOND_ROUNDER_NOBLE_LEGGINGS.get();
        boolean feet = this.target != null && this.target.getItemBySlot(EquipmentSlot.FEET).getItem() == JerotesVillageItems.ANCIENT_SECOND_ROUNDER_NOBLE_BOOTS.get();
        return this.target != null && !OtherEntityFactionFind.isResurrecter(this.target.getType()) && !(helmet || chestplate || legs || feet) && !(this.target instanceof Bat) && (this.target instanceof Mob || this.target instanceof Player);
    }

    protected AABB getTargetSearchArea(double d) {
        return this.mob.getBoundingBox().inflate(d, 4.0, d);
    }

    protected void findTarget() {
        this.target = this.targetType == Player.class || this.targetType == ServerPlayer.class ? this.mob.level().getNearestPlayer(this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ()) : this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(this.targetType, this.getTargetSearchArea(this.getFollowDistance()), livingEntity -> true), this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
    }

    @Override
    public void start() {
        this.mob.setTarget(this.target);
        super.start();
    }

    public void setTarget(@Nullable LivingEntity livingEntity) {
        this.target = livingEntity;
    }
}

