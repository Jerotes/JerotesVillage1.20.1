package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotes.entity.Shoot.Arrow.BaseJavelinEntity;
import com.jerotes.jerotesvillage.entity.Other.OtherSpell.BiteVinesEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ThrownMountainRealmFlagSpearEntity extends BaseJavelinEntity {
	private static final ItemStack item = new ItemStack(JerotesVillageItems.MOUNTAIN_REALM_FLAG_SPEAR_OF_JAVELIN.get());
	private static final EntityType<ThrownMountainRealmFlagSpearEntity> type = JerotesVillageEntityType.PROJECTILE_THROW_MOUNTAIN_REALM_FLAG_SPEAR.get();
	public ThrownMountainRealmFlagSpearEntity(EntityType<? extends ThrownMountainRealmFlagSpearEntity> entityType, Level level) {
		super(entityType, level, item, 15.0f);
	}
	public ThrownMountainRealmFlagSpearEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
		super(level, livingEntity, itemStack, type, 15.0f);
	}

	public int vinesLife = 0;
	public boolean vines = false;
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.vinesLife = compoundTag.getInt("VinesLife");
		this.vines = compoundTag.getBoolean("Vines");
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("VinesLife", this.vinesLife);
		compoundTag.putBoolean("Vines", this.vines);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.vines && this.vinesLife < 100) {
			this.vinesLife += 1;
		}
//		//全圈
//		int n = this.vinesLife;
//		float f2 = 4 + n + (float)n * (float)Math.PI * 2.0f / n;
//		this.createSpellEntity(this.getX() + (double) Mth.cos(f2) * (0.5 + n * 0.2), this.getZ() + (double)Mth.sin(f2) * (0.5 + n * 0.2), this.getY(), this.getY() + 1, f2);

		if (this.vinesLife == 10) {
			//第一圈
			int n;
			for (n = 0; n < 8; ++n) {
				float f2 = 4 + (float)n * (float)Math.PI * 2.0f / 8.0f;
				this.createVinesEntity(this.getX() + (double) Mth.cos(f2) * 1.5, this.getZ() + (double)Mth.sin(f2) * 1.5, this.getY(), this.getY() + 1, f2);
			}
		}
		if (this.vinesLife == 25) {
			//第二圈
			int n;
			for (n = 0; n < 8; ++n) {
				float f2 = 4 + (float)n * (float)Math.PI * 2.0f / 8.0f;
				this.createVinesEntity(this.getX() + (double)Mth.cos(f2) * 2.5, this.getZ() + (double)Mth.sin(f2) * 2.5, this.getY(), this.getY() + 1, f2);
			}
		}
		if (this.vinesLife == 40) {
			//第三圈
			int n;
			for (n = 0; n < 8; ++n) {
				float f2 = 4 + (float)n * (float)Math.PI * 2.0f / 8.0f;
				this.createVinesEntity(this.getX() + (double)Mth.cos(f2) * 3.5, this.getZ() + (double)Mth.sin(f2) * 3.5, this.getY(), this.getY() + 1, f2);
			}
		}
		if (this.vinesLife == 60) {
			byte by = this.entityData.get(ID_LOYALTY);
			if (by > 0) {
				//全圈
				int n;
				for (n = 0; n < 8; ++n) {
					float f2 = 4 + (float) n * (float) Math.PI * 2.0f / 8.0f;
					this.createVinesEntity(this.getX() + (double) Mth.cos(f2) * 1.5, this.getZ() + (double) Mth.sin(f2) * 1.5, this.getY(), this.getY() + 1, f2);
				}
				for (n = 0; n < 8; ++n) {
					float f2 = 4 + (float) n * (float) Math.PI * 2.0f / 8.0f;
					this.createVinesEntity(this.getX() + (double) Mth.cos(f2) * 2.5, this.getZ() + (double) Mth.sin(f2) * 2.5, this.getY(), this.getY() + 1, f2);
				}
				for (n = 0; n < 8; ++n) {
					float f2 = 4 + (float) n * (float) Math.PI * 2.0f / 8.0f;
					this.createVinesEntity(this.getX() + (double) Mth.cos(f2) * 3.5, this.getZ() + (double) Mth.sin(f2) * 3.5, this.getY(), this.getY() + 1, f2);
				}
			}
			else {
				//全圈
				int n;
				for (n = 0; n < 8; ++n) {
					float f2 = 4 + (float) n * (float) Math.PI * 2.0f / 8.0f;
					this.createVinesEntity(this.getX() + (double) Mth.cos(f2) * 1.5, this.getZ() + (double) Mth.sin(f2) * 1.5, this.getY(), this.getY() + 1, f2);
				}
				for (n = 0; n < 8; ++n) {
					float f2 = 4 + (float) n * (float) Math.PI * 2.0f / 8.0f;
					this.createVinesEntity(this.getX() + (double) Mth.cos(f2) * 2.5, this.getZ() + (double) Mth.sin(f2) * 2.5, this.getY(), this.getY() + 1, f2);
				}
				for (n = 0; n < 8; ++n) {
					float f2 = 4 + (float) n * (float) Math.PI * 2.0f / 8.0f;
					this.createVinesEntity(this.getX() + (double) Mth.cos(f2) * 3.5, this.getZ() + (double) Mth.sin(f2) * 3.5, this.getY(), this.getY() + 1, f2);
				}
			}
		}
	}

	private void createVinesEntity(double d, double d2, double d3, double d4, float f) {
		BlockPos blockPos = BlockPos.containing(d, d4, d2);
		boolean bl = false;
		double d5 = 0.0;
		do {
			BlockState blockState;
			VoxelShape voxelShape;
			BlockPos blockPos2 = blockPos.below();
			BlockState blockState2 = this.level().getBlockState(blockPos2);
			if (!blockState2.isFaceSturdy(this.level(), blockPos2, Direction.UP)) continue;
			if (!this.level().isEmptyBlock(blockPos) && !(voxelShape = (blockState = this.level().getBlockState(blockPos)).getCollisionShape(this.level(), blockPos)).isEmpty()) {
				d5 = voxelShape.max(Direction.Axis.Y);
			}
			bl = true;
			break;
		} while ((blockPos = blockPos.below()).getY() >= Mth.floor(d3) - 1);
		if (bl) {
			BiteVinesEntity biteVines = new BiteVinesEntity(JerotesVillageEntityType.BITE_VINES.get(), this.level());
			biteVines.setSpellLevelDamage(3);
			biteVines.setYRot(f * (180F / (float)Math.PI));
			biteVines.setPos(d, (double)blockPos.getY() + d5, d2);
			if (this.getOwner() != null && this.getOwner() instanceof LivingEntity livingEntity) {
				biteVines.setOwner(livingEntity);
			}
			this.level().addFreshEntity(biteVines);
			this.level().gameEvent(GameEvent.ENTITY_PLACE, new Vec3(d, (double)blockPos.getY() + d5, d2), GameEvent.Context.of(this));
		}
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (!this.vines) {
			byte by = this.entityData.get(ID_LOYALTY);
			if (by > 0) {
				this.vinesLife = 59;
			}
			this.vines = true;
		}
	}
}
