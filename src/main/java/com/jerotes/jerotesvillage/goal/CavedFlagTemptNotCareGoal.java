package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotes.entity.Interface.CarvedEntity;
import com.jerotes.jerotes.entity.Interface.TameMobEntity;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotesvillage.event.AdvancementEvent;
import com.jerotes.jerotesvillage.event.RelationshipEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.util.OtherEntityFactionFind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class CavedFlagTemptNotCareGoal extends Goal {
    private static final TargetingConditions TEMP_TARGETING = TargetingConditions.forNonCombat().range(32.0).ignoreLineOfSight();
    private final TargetingConditions targetingConditions;
    protected final PathfinderMob mob;
    private final double speedModifier;
    private double px;
    private double py;
    private double pz;
    private double pRotX;
    private double pRotY;
    @Nullable
    protected LivingEntity livingEntity;
    private boolean isRunning;
    private final ItemStack items = new ItemStack(JerotesVillageItems.CARVED_FLAG.get());
    private final boolean canScare;

    public CavedFlagTemptNotCareGoal(PathfinderMob pathfinderMob, double d, boolean bl) {
        this.mob = pathfinderMob;
        this.speedModifier = d;
        this.canScare = bl;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        this.targetingConditions = TEMP_TARGETING.copy().selector(this::shouldFollow);
    }

    @Override
    public boolean canUse() {
        if (this.mob instanceof TamableAnimal carvedHound && carvedHound.isInSittingPose()) {
            return false;
        }
        if (this.mob instanceof TameMobEntity carvedHound && carvedHound.isInSittingPose()) {
            return false;
        }
        if (this.mob instanceof OwnableEntity carvedHound && carvedHound.getOwner() != null) {
            return false;
        }
        List<LivingEntity> list = this.mob.level().getEntitiesOfClass(LivingEntity.class, this.mob.getBoundingBox().inflate(256.0, 256.0, 256.0));
        this.livingEntity = this.mob.level().getNearestEntity(list, this.targetingConditions, this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ());
        if (this.livingEntity != null && (this.mob.getTarget() == this.livingEntity || this.livingEntity instanceof Player player
                && RelationshipEvent.LessCopperCarvedCompanyRelationship(player, -500))
        ) {
            return false;
        }
        return this.livingEntity != null;
    }

    private boolean shouldFollow(LivingEntity livingEntity) {
        return (this.items.getItem() == livingEntity.getMainHandItem().getItem() || this.items.getItem() == livingEntity.getOffhandItem().getItem()) && livingEntity instanceof Player player && player.getCooldowns().isOnCooldown(items.getItem())
                || (OtherEntityFactionFind.isCarved(livingEntity.getType()) && livingEntity != this.mob &&
                (livingEntity.getMainHandItem().getItem() == JerotesVillageItems.CARVED_FLAG.get() || livingEntity.getOffhandItem().getItem() == JerotesVillageItems.CARVED_FLAG.get() || livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof BannerItem) &&
                !(this.mob.getMainHandItem().getItem() == JerotesVillageItems.CARVED_FLAG.get() || this.mob.getOffhandItem().getItem() == JerotesVillageItems.CARVED_FLAG.get() || this.mob.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof BannerItem));
    }

    @Override
    public boolean canContinueToUse() {
        if (this.livingEntity == null) {
            return false;
        }
        if (this.mob instanceof TamableAnimal carvedHound && carvedHound.isInSittingPose()) {
            return false;
        }
        if (this.mob instanceof TameMobEntity carvedHound && carvedHound.isInSittingPose()) {
            return false;
        }
        if (this.mob instanceof OwnableEntity carvedHound && carvedHound.getOwner() != null) {
            return false;
        }
        if (this.canScare()) {
            if (this.mob.distanceToSqr(this.livingEntity) < 256.0) {
                if (this.livingEntity.distanceToSqr(this.px, this.py, this.pz) > 0.010000000000000002) {
                    return false;
                }
                if (Math.abs((double)this.livingEntity.getXRot() - this.pRotX) > 120.0 || Math.abs((double)this.livingEntity.getYRot() - this.pRotY) > 120.0) {
                    return false;
                }
            } else {
                this.px = this.livingEntity.getX();
                this.py = this.livingEntity.getY();
                this.pz = this.livingEntity.getZ();
            }
            this.pRotX = this.livingEntity.getXRot();
            this.pRotY = this.livingEntity.getYRot();
        }
        return this.canUse();
    }

    protected boolean canScare() {
        return this.canScare;
    }

    @Override
    public void start() {
        this.px = this.livingEntity.getX();
        this.py = this.livingEntity.getY();
        this.pz = this.livingEntity.getZ();
        this.isRunning = true;
        if (livingEntity instanceof Player player && RelationshipEvent.MoreCopperCarvedCompanyRelationship(player, 500)) {
            if (player instanceof ServerPlayer serverPlayer) {
                AdvancementEvent.AdvancementGive(serverPlayer, "jerotesvillage:liberty_leading_the_villager");
            }
        }
    }

    @Override
    public void stop() {
        this.livingEntity = null;
        this.mob.getNavigation().stop();
        this.isRunning = false;
    }

    @Override
    public void tick() {
        if (this.livingEntity == null) {
            return;
        }
        this.mob.getLookControl().setLookAt(this.livingEntity, this.mob.getMaxHeadYRot() + 20, this.mob.getMaxHeadXRot());
        if (this.mob.distanceToSqr(this.livingEntity) < 18) {
            this.mob.getNavigation().stop();
        } else {
            this.mob.getNavigation().moveTo(this.livingEntity, this.speedModifier);
        }
        boolean bl = (this.mob instanceof CarvedEntity carved && carved.trusts(livingEntity.getUUID()));
        if (bl) {
            if (this.mob.level() instanceof ServerLevel projectileLevel) {
                projectileLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, this.mob.getX(), this.mob.getBoundingBox().maxY + 0.5, this.mob.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
            }
        }
    }

    public boolean isRunning() {
        return this.isRunning;
    }
}

