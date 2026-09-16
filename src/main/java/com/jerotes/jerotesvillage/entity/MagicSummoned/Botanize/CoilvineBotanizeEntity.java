package com.jerotes.jerotesvillage.entity.MagicSummoned.Botanize;

import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.scores.PlayerTeam;


public class CoilvineBotanizeEntity extends BotanizeEntity implements JerotesEntity, RangedAttackMob, SpellUseEntity, NeutralMob {
	public CoilvineBotanizeEntity(EntityType<? extends CoilvineBotanizeEntity> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new CoilvineBotanizeRangedAttackGoal());
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
	}
	class CoilvineBotanizeRangedAttackGoal extends RangedAttackGoal {
		public CoilvineBotanizeRangedAttackGoal() {
			super(CoilvineBotanizeEntity.this, 1.0, 50, 20.0f);
		}

		@Override
		public void start() {
			super.start();
			CoilvineBotanizeEntity.this.setAggressive(true);
		}

		@Override
		public void stop() {
			super.stop();
			CoilvineBotanizeEntity.this.setAggressive(false);
		}
	}

	public void performRangedAttack(LivingEntity livingEntity, float f) {
		if (this.onGround() || Main.isInFluid(this)) {
			if (this.distanceTo(livingEntity) < 8 || this.distanceTo(livingEntity) > 24) {
				float f1 = this.getYRot();
				float f2 = this.getXRot();
				float f3 = -Mth.sin(f1 * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
				float f4 = -Mth.sin(f2 * 0.017453292f);
				float f5 = Mth.cos(f1 * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
				float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
				float f7 = 0.25f;
				if (this.distanceTo(livingEntity) < 8) {
					f7 = -0.3f;
				}
				this.setOnGround(false);
				this.setDeltaMovement(this.getDeltaMovement().add(f3 *= f7 / f6 * 2, 0.75, f5 *= f7 / f6 * 2));
			}
		}
		if (!this.level().isClientSide()) {
			this.setAnimTick(20);
			this.setAnimationState("shoot");
		}
		//法术列表-植究射弹
		OtherSpellList.BotanizeBullet(this.getSpellLevel(), this, livingEntity).spellUse();
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.28);
		builder = builder.add(Attributes.MAX_HEALTH, 14);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 2);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48.0);
		return builder;
	}

	public void cloneFriend() {
		if (this.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) this.getTeam();
			CoilvineBotanizeEntity botanize;
			if (serverLevel.getBlockState(this.getOnPos().below()).isAir()) {
				botanize = JerotesVillageEntityType.COILVINE_BOTANIZE.get().spawn(serverLevel, BlockPos.containing(this.getX(), this.getY(), this.getZ()), MobSpawnType.MOB_SUMMONED);
			}
			else {
				botanize = JerotesVillageEntityType.COILVINE_BOTANIZE.get().spawn(serverLevel, this.getOnPos().above(), MobSpawnType.MOB_SUMMONED);
			}
			if (botanize != null) {
				if (teams != null) {
					serverLevel.getScoreboard().addPlayerToTeam(botanize.getStringUUID(), teams);
				}
				for (int i = 0; i < 30; ++i) {
					serverLevel.sendParticles(ParticleTypes.CHERRY_LEAVES, this.getRandomX(1), this.getRandomY(), this.getRandomX(1), 0, 0.0, 0.0, 0.0, 0.0);
				}
				botanize.spellLevel = this.spellLevel;
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ()), GameEvent.Context.of(this));
			this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.AIR));
			this.playSound(JerotesVillageSoundEvents.BOTANIZE_GIFT, 1.0f, 1.0f);
		}
	}

	public int spellLevel = 1;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("SpellLevel", this.spellLevel);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.spellLevel = compoundTag.getInt("SpellLevel");
	}
}

