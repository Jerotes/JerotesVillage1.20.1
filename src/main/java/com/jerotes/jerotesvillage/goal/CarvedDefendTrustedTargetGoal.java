package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotes.entity.Interface.CarvedEntity;
import com.jerotes.jerotesvillage.event.RelationshipEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class CarvedDefendTrustedTargetGoal extends NearestAttackableTargetGoal<LivingEntity> {
    @Nullable
    private LivingEntity trustPlayerattack;
    private int timestamp;
    private static final Predicate<Entity> TRUSTED_TARGET_SELECTOR = entity -> {
        if (entity instanceof LivingEntity livingEntity) {
            return livingEntity.getLastHurtMob() != null && livingEntity.getLastHurtMobTimestamp() < livingEntity.tickCount + 600;
        }
        return false;
    };

    public CarvedDefendTrustedTargetGoal(Mob mob) {
        super(mob, LivingEntity.class, 10, false, false, TRUSTED_TARGET_SELECTOR::test);
    }

    public boolean thisPlayerIsFriend(Player player) {
        return RelationshipEvent.MoreCopperCarvedCompanyRelationship(player, 500);
    }

    @Override
    public boolean canUse() {
        if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
            return false;
        }
        if (this.mob.getTarget() != null) {
            return false;
        }
        if (!(this.mob instanceof CarvedEntity carved)) {
            return false;
        }
        if (this.mob instanceof OwnableEntity carvedHound && carvedHound.getOwner() != null
          //      && MainConfig.CarvedHoundRetire
        ) {
            return false;
        }
        List<Player> list = this.mob.level().getEntitiesOfClass(Player.class, this.mob.getBoundingBox().inflate(32.0, 32.0, 32.0));
        double d = Double.MAX_VALUE;
        for (Player trustPlayer : list) {
            if (trustPlayer == null) continue;
            if ((this.mob.distanceToSqr(trustPlayer)) > d) continue;
            if ((trustPlayer.getTeam() != null || this.mob.getTeam() != null) && !this.mob.isAlliedTo(trustPlayer)) continue;
            if (carved.trusts(trustPlayer.getUUID()) || thisPlayerIsFriend(trustPlayer) || this.mob.isAlliedTo(trustPlayer)) {
                if (trustPlayer.getLastHurtMob() != null && this.mob.canAttack(trustPlayer.getLastHurtMob()) && this.mob.canAttackType(trustPlayer.getLastHurtMob().getType())){
                    //如果自身为可驯服生物
                    if (this.mob instanceof OwnableEntity ownable && ownable.getOwner() != null){
                        //不攻击主人
                        if (trustPlayer.getLastHurtMob() == ownable.getOwner()) {
                            return false;
                        }
                        //不攻击同主人
                        if (trustPlayer.getLastHurtMob() instanceof OwnableEntity ownableHurt) {
                            if (ownableHurt.getOwner() == ownable.getOwner()){
                                return false;
                            }
                        }
                        //不攻击主人队友
                        if (trustPlayer.getLastHurtMob().isAlliedTo(ownable.getOwner())){
                            return false;
                        }
                    }
                    //不攻击信任者
                    if (carved.trusts(trustPlayer.getLastHurtMob().getUUID())) {
                        return false;
                    }
                    //不攻击信任者的驯服生物
                    if (trustPlayer.getLastHurtMob() instanceof OwnableEntity ownable && ownable.getOwner() != null) {
                        if (carved.trusts(ownable.getOwner().getUUID())) {
                            return false;
                        }
                    }
                    //不攻击队友及内讧
                    if (trustPlayer.getLastHurtMob().isAlliedTo(this.mob) || trustPlayer.getLastHurtMob().isAlliedTo(trustPlayer)) {
                        return false;
                    }

                    int n = trustPlayer.getLastHurtMobTimestamp();
                    if (n != this.timestamp) {
                        trustPlayerattack = trustPlayer.getLastHurtMob();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void start() {
        this.setTarget(trustPlayerattack);
        if (trustPlayerattack != null) {
            this.timestamp = this.trustPlayerattack.getLastHurtByMobTimestamp();
        }
        super.start();
    }
}

