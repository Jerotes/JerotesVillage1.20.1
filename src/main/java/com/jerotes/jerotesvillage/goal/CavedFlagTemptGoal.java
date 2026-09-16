package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotes.entity.Interface.CarvedEntity;
import com.jerotes.jerotes.entity.Interface.TameMobEntity;
import com.jerotes.jerotesvillage.event.AdvancementEvent;
import com.jerotes.jerotesvillage.event.RelationshipEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class CavedFlagTemptGoal extends Goal {
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
    protected Player player;
    private boolean isRunning;
    private final ItemStack items = new ItemStack(JerotesVillageItems.CARVED_FLAG.get());
    private final boolean canScare;

    public CavedFlagTemptGoal(PathfinderMob pathfinderMob, double d, boolean bl) {
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
        this.player = this.mob.level().getNearestPlayer(this.targetingConditions, this.mob);
        if (this.player != null && (this.mob.getTarget() == this.player || RelationshipEvent.LessCopperCarvedCompanyRelationship(this.player, -500))) {
            return false;
        }
        return this.player != null;
    }

    private boolean shouldFollow(LivingEntity livingEntity) {
        return (this.items.getItem() == livingEntity.getMainHandItem().getItem() || this.items.getItem() == livingEntity.getOffhandItem().getItem()) && !((Player)livingEntity).getCooldowns().isOnCooldown(items.getItem());
    }

    @Override
    public boolean canContinueToUse() {
        if (this.player == null) {
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
            if (this.mob.distanceToSqr(this.player) < 256.0) {
                if (this.player.distanceToSqr(this.px, this.py, this.pz) > 0.010000000000000002) {
                    return false;
                }
                if (Math.abs((double)this.player.getXRot() - this.pRotX) > 120.0 || Math.abs((double)this.player.getYRot() - this.pRotY) > 120.0) {
                    return false;
                }
            } else {
                this.px = this.player.getX();
                this.py = this.player.getY();
                this.pz = this.player.getZ();
            }
            this.pRotX = this.player.getXRot();
            this.pRotY = this.player.getYRot();
        }
        return this.canUse();
    }

    protected boolean canScare() {
        return this.canScare;
    }

    @Override
    public void start() {
        this.px = this.player.getX();
        this.py = this.player.getY();
        this.pz = this.player.getZ();
        this.isRunning = true;
        if (RelationshipEvent.MoreCopperCarvedCompanyRelationship(player, 500)) {
            if (this.player instanceof ServerPlayer serverPlayer) {
                AdvancementEvent.AdvancementGive(serverPlayer, "jerotesvillage:liberty_leading_the_villager");
            }
        }
    }

    @Override
    public void stop() {
        this.player = null;
        this.mob.getNavigation().stop();
        this.isRunning = false;
    }

    @Override
    public void tick() {
        if (this.player == null) {
            return;
        }
        this.mob.getLookControl().setLookAt(this.player, this.mob.getMaxHeadYRot() + 20, this.mob.getMaxHeadXRot());
        if (this.mob.distanceToSqr(this.player) < 6.25) {
            this.mob.getNavigation().stop();
        } else {
            this.mob.getNavigation().moveTo(this.player, this.speedModifier);
        }
        boolean bl = (this.mob instanceof CarvedEntity carved && carved.trusts(player.getUUID()));
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

