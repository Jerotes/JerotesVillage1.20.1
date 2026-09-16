package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotes.init.JerotesMobEffectTags;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Boss.Biome.SerponCombatExecuterEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

public class SerponHelpPlayerGoal extends NearestAttackableTargetGoal<LivingEntity> {
    @Nullable
    private LivingEntity attack;

    public SerponHelpPlayerGoal(Mob mob, Class<LivingEntity> class_, boolean bl, boolean bl2, Predicate<LivingEntity> predicate) {
        super(mob, class_, 10, bl, bl2, predicate);
    }

    @Override
    public boolean canUse() {
        if (this.randomInterval > 0 && mob.getRandom().nextInt(this.randomInterval) != 0) {
            return false;
        }
        if (mob.getTarget() != null) {
            return false;
        }
        //Boss战取消
		double area = OtherMainConfig.BossSpawnRestrictionRange;
        List<SerponCombatExecuterEntity> bossList = this.mob.level().getEntitiesOfClass(SerponCombatExecuterEntity.class, this.mob.getBoundingBox().inflate(area, area, area));
        bossList.removeIf(entity -> (entity.getTeam() != null || this.mob.getTeam() != null) && !this.mob.isAlliedTo(entity)) ;
        bossList.removeIf(entity -> entity.getTarget() != null) ;
        if (!bossList.isEmpty())
            return false;
        List<Player> list = mob.level().getEntitiesOfClass(Player.class, mob.getBoundingBox().inflate(32.0, 32.0, 32.0));
        double d = Double.MAX_VALUE;
        for (Player player : list) {
            if (player == null) continue;
            if ((mob.distanceToSqr(player)) > d) continue;
            if (player.getLastHurtMob() == null) continue;
            if (!AttackFind.SameFactionAvoidDamage(this.mob, player, false)) continue;
            //前提
            if (isCanAttacks(mob, mob, player)) continue;

            if (!isCanAttack(mob, player, player.getLastHurtMob())) continue;
            if ((player.getTeam() != null && mob.getTeam() != null) && !mob.isAlliedTo(player)) continue;
            if (AttackFind.FindCanNotAttack(this.mob, player.getLastHurtMob())) continue;
            if ((mob.canAttack(player.getLastHurtMob()) && mob.canAttackType(player.getLastHurtMob().getType()))) {
                //如果自身为可驯服生物
                if (mob instanceof OwnableEntity ownable && ownable.getOwner() != null){
                    //不攻击主人
                    if (player.getLastHurtMob() == ownable.getOwner()) {
                        continue;
                    }
                    //不攻击同主人
                    if (player.getLastHurtMob() instanceof OwnableEntity ownableHurt) {
                        if (ownableHurt.getOwner() == ownable.getOwner()){
                            continue;
                        }
                    }
                    //不攻击主人队友
                    if (Objects.requireNonNull(player.getLastHurtMob()).isAlliedTo(ownable.getOwner())){
                        continue;
                    }
                }
                //不攻击同阵营的驯服生物
                if (player.getLastHurtMob() instanceof OwnableEntity ownable && ownable.getOwner() != null) {
                    if (!isCanAttack(mob, player, ownable.getOwner())){
                        continue;
                    }
                }
                //不攻击队友及内讧
                if (player.getLastHurtMob().isAlliedTo(mob) || player.getLastHurtMob().isAlliedTo(player)) {
                    continue;
                }
                attack = player.getLastHurtMob();
                return true;
            }
        }
        return false;
    }

    @Override
    public void start() {
        this.setTarget(attack);
        super.start();
    }

    public boolean isCanAttacks(Mob mob, LivingEntity findMob, LivingEntity findMobTarget) {
        //蛇龙阵营
        if (EntityFactionFind.isFactionSerponCombatTeam(mob)) {
            AtomicBoolean canNot = new AtomicBoolean(false);
            findMobTarget.level().registryAccess().registryOrThrow(Registries.MOB_EFFECT).getTagOrEmpty(JerotesMobEffectTags.SERPON_FACTION).forEach(effect -> {
                if (findMobTarget.hasEffect(effect.get())) {
                    canNot.set(true);
                }
            });
            return !canNot.get();
        }
        return true;
    }

    public boolean isCanAttack(Mob mob, LivingEntity player, LivingEntity playerTarget) {
        //与队友为同伴
        return
                //额外判定
                isCanAttacks(mob, player, playerTarget)
                //目标与队友并非内讧
                && !(EntityFactionFind.isFaction(playerTarget, player))
                //目标并非自己同伴
                && !(EntityFactionFind.isFaction(mob, playerTarget));
    }
}

