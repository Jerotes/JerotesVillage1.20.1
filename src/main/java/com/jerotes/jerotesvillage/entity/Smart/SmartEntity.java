package com.jerotes.jerotesvillage.entity.Smart;

import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.*;

public class SmartEntity extends PathfinderMob implements JerotesEntity {
	public SmartEntity(EntityType<? extends SmartEntity> type, Level world) {
		super(type, world);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
		builder = builder.add(Attributes.MAX_HEALTH, 1024);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
		builder = builder.add(Attributes.FOLLOW_RANGE, 128);
		return builder;
	}

	@Override
	protected void registerGoals() {
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 1.62f;
	}
	@Override
	public void setTarget(@Nullable LivingEntity livingEntity) {
		healthHistory.clear();
		if (!attackHistory.isEmpty()) {
			attackHistory.clear();
		}
		super.setTarget(livingEntity);
		resetKnow();
	}

	//
	public static final EntityDataAccessor<Boolean> SHOULD_REPORT = SynchedEntityData.defineId(SmartEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> SMART_LEVEL = SynchedEntityData.defineId(SmartEntity.class, EntityDataSerializers.INT);
	public float distanceToTarget = 8;            //与目标距离
	public boolean hasLineOfSightWithBlock = true;   //与目标是否没被方块格挡
	public boolean hasLineOfSightWithEntity = true;  //与目标是否没被实体格挡
	public boolean canMoveTo = true;                 //是否无法到达
	public boolean targetPositionDanger = false;      //目标位置危险
	public boolean thisPositionDanger = false;        //自身位置危险
	public float heightWithTarget = 0;            //与目标的高低差
	//
	public float targetHealthPercent = 1;         //目标生命值百分比
	public float targetAttributeLevel = 2000;        //目标属性水平（综合力量）
	public float targetHealthTrendShort = 0;      //目标生命值短期变化趋势
	public float targetHealthTrendLong = 0;       //目标生命值长期变化趋势
	public int targetPotionLevel = 0;             //目标药水水平
	public float targetSizeCompare = 1;           //目标体型与自身对比
	private Vec3 lastSelfPos = null;
	private final Map<UUID, Vec3> lastTargetPos = new HashMap<>();
	public float targetMoveState = 1;            //目标目前移动状态
	public Vec3 targetMoveDirection = Vec3.ZERO;          //目标目前移动方向
	public int targetAlliesNearby = 0;            //周围目标的同伴数量
	public ItemStack targetMainHandItem = ItemStack.EMPTY;      //目标手持物品
	public ItemStack targetUseItem = ItemStack.EMPTY;           //目标使用物品（如拉弓/喝药水）//记录目标攻击自己的历史
	private final Deque<DistanceRecord> targetAttackHistory = new ArrayDeque<>();
	//记录自己攻击目标的历史
	private final Deque<DistanceRecord> selfAttackHistory = new ArrayDeque<>();
	public float avgTargetAttackDistance = getAvgTargetAttackDistance();              //目标攻击自己的平均距离
	public float avgSelfAttackDistance = getAvgSelfAttackDistance();              //自己攻击目标的平均距离
	public float selfHealthPercent = 1;           //自身生命值百分比
	public float selfAttributeLevel = 2000;          //自身属性水平
	public float selfHealthTrendShort = 0;        //自身生命值短期变化趋势
	public float selfHealthTrendLong = 0;        //自身生命值长期变化趋势
	public int selfPotionLevel = calculatePotionLevel(this);              //自身药水水平
	public float selfMoveState = 1;              //自身目前移动状态
	public int selfUsableTypes = 1;               //自身能使用的类型（近战/远程/技能）
	public int selfAlliesNearby = 0;              //周围自身的同伴数量
	public boolean incomingProjectileDanger = false;  //未察觉的危险射弹靠近
	public Entity extraAttacker = null;              //多余的攻击来自谁
	public boolean needRest = false;                  //自身是否灵感枯竭需要休息
	public int selfIntelligenceLevel = getSmartLevel();         //自身智力等级
	public void resetKnow() {
		LivingEntity target = this.getTarget();
		long now = this.level().getGameTime();
		if (target != null) {
			//---------- 基础空间 ----------
			distanceToTarget = (float) this.distanceTo(target);
			hasLineOfSightWithBlock = this.hasLineOfSight(target);
			hasLineOfSightWithEntity = canSeeEntity(target); //上面写好的方法
			Path path = this.getNavigation().createPath(target, 0);
			this.canMoveTo = path != null && path.canReach();
			targetPositionDanger = isPositionDangerous(target.blockPosition());
			thisPositionDanger = isPositionDangerous(this.blockPosition());
			heightWithTarget = (float) (this.getY() - target.getY());

			//---------- 目标状态 ----------
			targetHealthPercent = target.getHealth() / target.getMaxHealth();
			targetAttributeLevel = (int) (
					(target.getAttribute(Attributes.MAX_HEALTH) != null ? target.getAttribute(Attributes.MAX_HEALTH).getValue() : 0)  *
							(target.getAttribute(Attributes.ATTACK_DAMAGE) != null ? target.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0)  *
							(target.getAttribute(Attributes.ARMOR) != null ? target.getAttribute(Attributes.ARMOR).getValue() + 20 : 20)
			);
			targetHealthTrendShort = calcHealthTrend(target, 40); //2秒趋势
			targetHealthTrendLong = calcHealthTrend(target, 200); //10秒趋势
			targetPotionLevel = calculatePotionLevel(target);
			targetSizeCompare = target.getBbWidth() * target.getBbWidth() * target.getBbHeight() /
					(this.getBbWidth() * this.getBbWidth() * this.getBbHeight());
			targetMoveState = detectMoveState(target);
			targetMoveDirection = target.getDeltaMovement();
			targetAlliesNearby = this.level().getEntitiesOfClass(
					target.getClass(),
					target.getBoundingBox().inflate(8),
					e -> e != target && e.isAlive()
			).size();
			targetMainHandItem = target.getMainHandItem();
			targetUseItem = target.isUsingItem() ? target.getUseItem() : ItemStack.EMPTY;
			avgTargetAttackDistance = getAvgTargetAttackDistance();
			avgSelfAttackDistance = getAvgSelfAttackDistance();

			//---------- 自身状态 ----------
			selfHealthPercent = this.getHealth() / this.getMaxHealth();
			selfAttributeLevel = (int) (
					(this.getAttribute(Attributes.MAX_HEALTH) != null ? this.getAttribute(Attributes.MAX_HEALTH).getValue() : 0)  *
							(this.getAttribute(Attributes.ATTACK_DAMAGE) != null ? this.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0)  *
							(this.getAttribute(Attributes.ARMOR) != null ? this.getAttribute(Attributes.ARMOR).getValue() + 20 : 20)
			);
			selfHealthTrendShort = calcHealthTrend(this, 40);
			selfHealthTrendLong = calcHealthTrend(this, 200);
			selfPotionLevel = calculatePotionLevel(this);
			selfMoveState = detectSelfMoveState();
			selfUsableTypes = evaluateSelfType();
			selfAlliesNearby = this.level().getEntitiesOfClass(
					this.getClass(),
					this.getBoundingBox().inflate(8),
					e -> e != this && e.isAlive()
			).size();

			//---------- 其他威胁 ----------
			incomingProjectileDanger = detectProjectileDanger();
			extraAttacker = findExtraAttacker();
			needRest = false;
			selfIntelligenceLevel = getSmartLevel();
		}
		else {
			distanceToTarget = 8;
			hasLineOfSightWithBlock = true;
			hasLineOfSightWithEntity = true;
			canMoveTo = true;
			targetPositionDanger = false;
			thisPositionDanger = false;
			heightWithTarget = 0;
			healthHistory.clear();

			targetHealthPercent = 1;
			targetAttributeLevel = 2000;
			targetHealthTrendShort = 0;
			targetHealthTrendLong = 0;
			targetPotionLevel = 0;
			targetSizeCompare = 1;
			targetMoveState = 1;
			targetMoveDirection = Vec3.ZERO;
			targetAlliesNearby = 0;
			targetMainHandItem = ItemStack.EMPTY;
			targetUseItem = ItemStack.EMPTY;
			avgTargetAttackDistance = getAvgTargetAttackDistance();
			avgSelfAttackDistance = getAvgSelfAttackDistance();

			selfHealthPercent = this.getHealth() / this.getMaxHealth();
			selfAttributeLevel = 2000;
			selfHealthTrendShort = 0;
			selfHealthTrendLong = 0;
			selfPotionLevel = calculatePotionLevel(this);
			selfMoveState = 1;
			selfUsableTypes = 1;
			selfAlliesNearby = 0;

			incomingProjectileDanger = false;
			extraAttacker = null;
			needRest = false;
			selfIntelligenceLevel = getSmartLevel();
		}
		healthHistory.entrySet().removeIf(e -> now - e.getValue().lastUpdateTick > 2000);
	}
	private static class DistanceRecord {
		final float distance;
		final long tick;
		DistanceRecord(float distance, long tick) {
			this.distance = distance;
			this.tick = tick;
		}
	}
	//prune历史，保持20秒窗口（400tick）
	private void pruneHistory(Deque<DistanceRecord> history) {
		long cutoff = this.level().getGameTime() - 400;
		while(!history.isEmpty() && history.getFirst().tick < cutoff) {
			history.removeFirst();
		}
	}
	//当目标攻击自己时调用
	public void recordTargetAttack(LivingEntity attacker) {
		float dist = (float) this.distanceTo(attacker);
		targetAttackHistory.addLast(new DistanceRecord(dist, this.level().getGameTime()));
		pruneHistory(targetAttackHistory);
	}
	//当自己攻击目标时调用
	public void recordSelfAttack(LivingEntity target) {
		float dist = (float) this.distanceTo(target);
		selfAttackHistory.addLast(new DistanceRecord(dist, this.level().getGameTime()));
		pruneHistory(selfAttackHistory);
	}
	//获取平均攻击距离
	private float averageDistance(Deque<DistanceRecord> history) {
		pruneHistory(history);
		if(history.isEmpty()) return -1f; //默认-1
		float sum = 0f;
		for(DistanceRecord r: history) sum += r.distance;
		return sum / history.size();
	}
	//获取双向平均攻击距离
	public float getAvgTargetAttackDistance() {
		return averageDistance(targetAttackHistory);
	}

	public float getAvgSelfAttackDistance() {
		return averageDistance(selfAttackHistory);
	}
	private int calculatePotionLevel(LivingEntity entity) {
		int level = 0;
		for (MobEffectInstance effect : entity.getActiveEffects()) {
			int amp = effect.getAmplifier() + 1; //药水等级从 0 开始，所以要 +1
			if (effect.getEffect().isBeneficial()) {
				level += amp;  //正面效果加
			} else {
				level -= amp;  //负面效果减
			}
		}
		return level;
	}
	private float detectMoveState(LivingEntity target) {
		UUID id = target.getUUID();
		Vec3 lastPos = lastTargetPos.getOrDefault(id, target.position());
		Vec3 currentPos = target.position();
		Vec3 selfPos = this.position();

		float lastDistance = (float) lastPos.distanceTo(this.lastSelfPos != null ? this.lastSelfPos : selfPos);
		float currentDistance = (float) currentPos.distanceTo(selfPos);

		//更新记录
		lastTargetPos.put(id, currentPos);
		lastSelfPos = selfPos;

		float diff = currentDistance - lastDistance;

		if (diff < -0.05f) return 1;      //靠近
		else if (diff > 0.05f) return 3;  //远离
		else return 2;                     //不动
	}
	private float detectSelfMoveState() {
		if (lastSelfPos == null) {
			lastSelfPos = this.position();
			return 2; //刚开始认为不动
		}
		float diff = (float) this.position().distanceTo(lastSelfPos);
		float state;
		if (diff < 0.01f) state = 2;       //不动
		else {
			//与目标比较方向，可根据 AI 需要再细化，这里简化：移动就算靠近
			state = 1;                       //移动中
		}
		lastSelfPos = this.position();
		return state;
	}
	private boolean canSeeEntity(Entity target) {
		Vec3 start = this.getEyePosition();
		Vec3 end = target.getEyePosition();
		AABB box = this.getBoundingBox().expandTowards(end.subtract(start)).inflate(1.0D);
		EntityHitResult hit = ProjectileUtil.getEntityHitResult(this, start, end, box,
				e -> e.isPickable() && !e.isSpectator() && e != this, start.distanceToSqr(end));
		return hit == null || hit.getEntity() == target;
	}
	private boolean isPositionDangerous(BlockPos pos) {
		Level level = this.level();
		return WalkNodeEvaluator.getBlockPathTypeStatic(level, pos.below().mutable()) == BlockPathTypes.DANGER_OTHER;
	}
	private final Map<UUID, HealthRecord> healthHistory = new HashMap<>();
	private static class HealthRecord {
		float lastHealth;
		long lastUpdateTick;

		HealthRecord(float lastHealth, long lastUpdateTick) {
			this.lastHealth = lastHealth;
			this.lastUpdateTick = lastUpdateTick;
		}
	}
	private float calcHealthTrend(LivingEntity entity, int tickWindow) {
		UUID id = entity.getUUID();
		long now = this.level().getGameTime();

		//获取已有记录
		HealthRecord record = healthHistory.get(id);

		if (record == null || now - record.lastUpdateTick > tickWindow) {
			//没有记录 或者 过期了 -> 重置
			healthHistory.put(id, new HealthRecord(entity.getHealth(), now));
			return 0F; //刚选中时趋势为 0
		}

		//有记录 -> 计算差值
		float diff = entity.getHealth() - record.lastHealth;

		//更新记录
		record.lastHealth = entity.getHealth();
		record.lastUpdateTick = now;

		return diff;
	}
	private int evaluateSelfType() {
		return 1;
	}
	private boolean detectProjectileDanger() {
		return !this.level().getEntitiesOfClass(Projectile.class,
				this.getBoundingBox().inflate(8),
				p -> p.isAlive() && p.getDeltaMovement().lengthSqr() > 0.01
		).isEmpty();
	}
	private Entity findExtraAttacker() {
		//简单示例：范围内最近的攻击者
		return this.level().getEntitiesOfClass(LivingEntity.class,
				this.getBoundingBox().inflate(12),
				e -> e != this && e.getLastHurtMob() == this
		).stream().findFirst().orElse(null);
	}
	private final Deque<AttackRecord> attackHistory = new ArrayDeque<>();
	private static class AttackRecord {
		final float distance;
		final long tick;
		AttackRecord(float distance, long tick) {
			this.distance = distance;
			this.tick = tick;
		}
	}
	private void pruneAttackHistory() {
		long cutoff = this.level().getGameTime() - 400; //20秒窗口（400tick）
		while (!attackHistory.isEmpty() && attackHistory.getFirst().tick < cutoff) {
			attackHistory.removeFirst();
		}
	}
	private void recordIncomingAttackFrom(LivingEntity attacker) {
		float dist = (float) this.distanceTo(attacker);
		attackHistory.addLast(new AttackRecord(dist, this.level().getGameTime()));
		pruneAttackHistory();
	}
	private float evaluateCombatType() {
		pruneAttackHistory();
		if (attackHistory.isEmpty()) return 1.0F; //没数据时默认近战
		float sum = 0f;
		for (AttackRecord r : attackHistory) sum += r.distance;
		float avgDist = sum / attackHistory.size();
		//0~3格=近战(1.0)，>=8格=远程(2.0)，中间线性
		if (avgDist <= 3.0F) return 1.0F;
		if (avgDist >= 8.0F) return 2.0F;
		return 1.0F + (avgDist - 3.0F) / (8.0F - 3.0F);
	}









	//
	public boolean isShouldReport() {
		return this.getEntityData().get(SHOULD_REPORT);
	}
	public void setShouldReport(boolean bl) {
		this.getEntityData().set(SHOULD_REPORT, bl);
	}
	public int getSmartLevel() {
		return this.getEntityData().get(SMART_LEVEL);
	}
	public void setSmartLevel(int n) {
		this.getEntityData().set(SMART_LEVEL, n);
	}
	//
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean("ShouldReport", this.isShouldReport());
		compoundTag.putInt("SmartLevel", this.getSmartLevel());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setShouldReport(compoundTag.getBoolean("ShouldReport"));
		this.setSmartLevel(compoundTag.getInt("SmartLevel"));
		resetKnow();
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(SHOULD_REPORT, this.getType() == JerotesVillageEntityType.SMART.get() || this.getType() == JerotesVillageEntityType.SMART_BULGE.get());
		this.getEntityData().define(SMART_LEVEL, 5);
	}

	protected Component getSay() {
		StringBuilder string = new StringBuilder();

		//没有敌人 / No target
		if (this.getTarget() == null) {
			string.append("没有敌人/NoTarget:true;\n");
		} else {
			//敌人类型 / Target type
			if (this.getTarget() instanceof Player player) {
				string.append("敌人是玩家/TargetIsPlayer:").append(player.getName().getString()).append(";\n");
			} else if (this.getTarget() instanceof Mob mob) {
				string.append("敌人是Mob/TargetIsMob:").append(mob.getName().getString()).append(";\n");
			} else {
				string.append("敌人是生物/TargetIsLivingEntity:").append(this.getTarget().getName().getString()).append(";\n");
			}

			//距离和状态 / Distance & status
			string.append("与敌人的距离/DistanceToTarget:").append(String.format("%.2f", distanceToTarget)).append(";\n");
			string.append("视线被方块阻挡/LineOfSightBlockedByBlocks:").append(!hasLineOfSightWithBlock).append(";\n");
			string.append("视线被实体阻挡/LineOfSightBlockedByEntities:").append(!hasLineOfSightWithEntity).append(";\n");
			string.append("是否可到达敌人/CanReachTarget:").append(canMoveTo).append(";\n");
			string.append("敌人位置危险/TargetPositionDanger:").append(targetPositionDanger).append(";\n");
			string.append("自身位置危险/SelfPositionDanger:").append(thisPositionDanger).append(";\n");
			string.append("与敌人的高度差(自己-敌人)/HeightDifference:").append(String.format("%.2f", heightWithTarget)).append(";\n");

			//其他重要信息 / Other important info
			string.append("敌人生命值百分比/TargetHPPercent:").append(String.format("%.2f", targetHealthPercent)).append(";\n");
			string.append("敌人属性水平/TargetAttributeLevel:").append(String.format("%.0f", targetAttributeLevel)).append(";\n");
			string.append("敌人短期血量趋势/TargetShortTermHPTrend:").append(String.format("%.2f", targetHealthTrendShort)).append(";\n");
			string.append("敌人长期血量趋势/TargetLongTermHPTrend:").append(String.format("%.2f", targetHealthTrendLong)).append(";\n");
			string.append("敌人药水效果等级/TargetPotionEffectLevel:").append(targetPotionLevel > 0 ? "+" + targetPotionLevel : targetPotionLevel).append(";\n");
			string.append("敌人体型与自身比例/TargetSizeCompare:").append(String.format("%.2f", targetSizeCompare)).append(";\n");
			string.append("敌人移动状态/TargetMoveState:").append(targetMoveState == 1 ? "靠近/approaching" : targetMoveState == 2 ? "站立/idle" : "远离/retreating").append(";\n");
			string.append("敌人移动方向/TargetMoveDirection:").append(targetMoveDirection).append(";\n");
			string.append("敌人附近同伴数量/TargetNearbyAllies:").append(targetAlliesNearby).append(";\n");
			string.append("敌人手持物品/TargetMainHandItem:").append(targetMainHandItem.getHoverName().getString()).append(";\n");
			string.append("敌人使用物品/TargetUsingItem:").append(targetUseItem.isEmpty() ? "none" : targetUseItem.getHoverName().getString()).append(";\n");
			string.append("目标攻击的平均距离/AvgTargetAttackDistance:").append(String.format("%.2f", avgTargetAttackDistance)).append(";\n");
			string.append("自己攻击的平均距离/AvgSelfAttackDistance:").append(String.format("%.2f", avgSelfAttackDistance)).append(";\n");
		}

		//最近战斗信息 / Recent combat info
		if (this.getLastHurtByMob() != null) {
			string.append("刚刚被攻击/IWasJustAttackedBy:").append(this.getLastHurtByMob().getName().getString()).append(";\n");
		}
		if (this.getLastHurtMob() != null) {
			string.append("刚刚攻击/IJustAttacked:").append(this.getLastHurtMob().getName().getString()).append(";\n");
		}

		//自身状态 / Self status
		string.append("自身生命值百分比/SelfHPPercent:").append(String.format("%.2f", selfHealthPercent)).append(";\n");
		string.append("自身属性水平/SelfAttributeLevel:").append(String.format("%.0f", selfAttributeLevel)).append(";\n");
		string.append("自身短期血量趋势/SelfShortTermHPTrend:").append(String.format("%.2f", selfHealthTrendShort)).append(";\n");
		string.append("自身长期血量趋势/SelfLongTermHPTrend:").append(String.format("%.2f", selfHealthTrendLong)).append(";\n");
		string.append("自身药水效果等级/SelfPotionEffectLevel:").append(selfPotionLevel > 0 ? "+" + selfPotionLevel : selfPotionLevel).append(";\n");
		string.append("自身移动状态/SelfMoveState:").append(selfMoveState == 1 ? "靠近/approaching" : selfMoveState == 2 ? "站立/idle" : "远离/retreating").append(";\n");
		string.append("自身可用攻击类型/SelfUsableAttackTypes:").append(selfUsableTypes).append(";\n");
		string.append("自身附近同伴数量/SelfNearbyAllies:").append(selfAlliesNearby).append(";\n");

		//其他威胁与状态 / Other threats & status
		string.append("未察觉的危险射弹/IncomingProjectileDanger:").append(incomingProjectileDanger).append(";\n");
		string.append("多余攻击来源/ExtraAttackers:").append(extraAttacker != null ? extraAttacker.getName().getString() : "none").append(";\n");
		string.append("是否需要休息/NeedRest:").append(needRest).append(";\n");
		string.append("自身智力等级/SelfIntelligenceLevel:").append(selfIntelligenceLevel).append(";\n");

		return Component.literal("来自/ReportFrom" + this.getName().getString() + ":\n" + string.toString())
				.withStyle(ChatFormatting.GREEN);
	}


	protected void logSay() {
		JerotesVillage.LOGGER.info("来自 {} 的报告:\n{}", this.getName().getString(), getSay());
	}

	@Override
	public void tick() {
		super.tick();
	}
	@Override
	public void aiStep() {
		super.aiStep();
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//
		//重要因素 计算
		if (this.tickCount % (11 - getSmartLevel() * 4) == 0) {
			resetKnow();
		}
		if (this.isShouldReport()) {
			//计算
			if (this.tickCount % (11 - getSmartLevel() * 4) == 0) {
				double x = this.getX();
				double y = this.getY();
				double z = this.getZ();
				AABB area = new AABB(x - 64, y - 64, z - 64, x + 64, y + 64, z + 64);
				List<ServerPlayer> nearbyPlayers = level().getEntitiesOfClass(ServerPlayer.class, area);
				if (!nearbyPlayers.isEmpty()) {
					logSay();
				}
//				if (level() instanceof ServerLevel serverLevel) {
//					serverLevel.getPlayers(EntitySelector.NO_SPECTATORS).forEach(serverPlayer -> serverPlayer.displayClientMessage
//							(this.getSay(), true));
//				}
			}
		}
	}

	@Override
	public boolean hurt(DamageSource damageSource, float f) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, f);
		}
		if (!this.level().isClientSide) {
			LivingEntity target = this.getTarget();
			if (target != null) {
				Entity attacker = damageSource.getEntity();
				if (attacker instanceof LivingEntity living && living == target) {
					recordIncomingAttackFrom(living);
				} else {
					Entity direct = damageSource.getDirectEntity();
					if (direct instanceof Projectile projectile) {
						Entity owner = projectile.getOwner();
						if (owner instanceof LivingEntity livingOwner && livingOwner == target) {
							recordIncomingAttackFrom(livingOwner);
						}
					}
				}
			}
		}
		if (this.getType() == JerotesVillageEntityType.SMART.get()) {
			if (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY))
				return super.hurt(damageSource, f);
			if (super.hurt(damageSource, f)) {
				this.setHealth(this.getMaxHealth());
			}
			return true;
		}
		return super.hurt(damageSource, f);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}

}
