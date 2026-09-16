package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotes.entity.Interface.CarvedEntity;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Animal.CarvedHoundEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.npc.Villager;

import javax.annotation.Nullable;
import java.util.List;

public class HelpVillagerGoal extends NearestAttackableTargetGoal<LivingEntity> {
    @Nullable
    private LivingEntity attack;

    public HelpVillagerGoal(Mob mob) {
        super(mob, LivingEntity.class, 10, false, false, livingEntity -> livingEntity instanceof LivingEntity);
    }

    @Override
    public boolean canUse() {
        if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
            return false;
        }
        if (!(this.mob instanceof CarvedEntity self)) {
            return false;
        }
        if (this.mob.getTarget() != null) {
            return false;
        }
        if (this.mob instanceof CarvedHoundEntity carvedHound && carvedHound.getOwner() != null && OtherMainConfig.CarvedHoundRetire) {
            return false;
        }
        List<Mob> list = this.mob.level().getEntitiesOfClass(Mob.class, this.mob.getBoundingBox().inflate(32.0, 32.0, 32.0));
        double d = Double.MAX_VALUE;
        for (Mob carved : list) {
            if (carved == null) continue;
            if ((this.mob.distanceToSqr(carved)) > d) continue;
            if ((carved.getTeam() != null || this.mob.getTeam() != null) && !this.mob.isAlliedTo(carved)) continue;
            if ((carved instanceof Villager villager)) {
                if (villager.getLastHurtByMob() != null && this.mob.canAttack(villager.getLastHurtByMob()) && this.mob.canAttackType(villager.getLastHurtByMob().getType())) {
                    //如果自身为可驯服生物
                    if (this.mob instanceof OwnableEntity ownable && ownable.getOwner() != null){
                        //不攻击主人
                        if (villager.getLastHurtByMob() == ownable.getOwner()) {
                            return false;
                        }
                        //不攻击同主人
                        if (villager.getLastHurtByMob() instanceof OwnableEntity ownableHurt) {
                            if (ownableHurt.getOwner() == ownable.getOwner()){
                                return false;
                            }
                            if (ownableHurt.getOwner() == ownable.getOwner()){
                                return false;
                            }
                        }
                        //不攻击主人队友
                        if (villager.getLastHurtByMob().isAlliedTo(ownable.getOwner())){
                            return false;
                        }
                    }
                    //不攻击信任者
                    if (self.trusts(villager.getLastHurtByMob().getUUID())) {
                        return false;
                    }
                    //不攻击信任者的驯服生物
                    if (villager.getLastHurtByMob() instanceof OwnableEntity ownable && ownable.getOwner() != null) {
                        if (self.trusts(ownable.getOwner().getUUID())){
                            return false;
                        }
                    }
                    //不攻击队友及内讧
                    if (villager.getLastHurtByMob().isAlliedTo(villager) || villager.getLastHurtByMob().isAlliedTo(this.mob)) {
                        return false;
                    }
                    attack = villager.getLastHurtByMob();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void start() {
        this.setTarget(attack);
        super.start();
    }
}

