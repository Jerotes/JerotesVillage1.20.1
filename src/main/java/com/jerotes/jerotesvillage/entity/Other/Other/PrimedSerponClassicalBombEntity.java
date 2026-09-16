package com.jerotes.jerotesvillage.entity.Other.Other;

import com.jerotes.jerotes.entity.Interface.TameMobEntity;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotesvillage.block.BrightGrassland.BrightLandDrum;
import com.jerotes.jerotesvillage.entity.Shoot.ThrowingBall.BaseThrowingBallEntity;
import com.jerotes.jerotesvillage.entity.Shoot.ThrowingBall.GlassThrowingBallEntity;
import com.jerotes.jerotesvillage.init.*;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotesvillage.util.Other;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BellBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class PrimedSerponClassicalBombEntity extends PrimedTnt implements TraceableEntity {
	private static final EntityDataAccessor<Integer> DATA_FUSE_ID = SynchedEntityData.defineId(PrimedSerponClassicalBombEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<BlockState> DATA_BLOCK_STATE_ID = SynchedEntityData.defineId(PrimedSerponClassicalBombEntity.class, EntityDataSerializers.BLOCK_STATE);
	@Nullable
	private LivingEntity owner;

	public PrimedSerponClassicalBombEntity(EntityType<? extends PrimedSerponClassicalBombEntity> entityType, Level level) {
		super(entityType, level);
		this.blocksBuilding = true;
	}

	public PrimedSerponClassicalBombEntity(Level level, double d, double d2, double d3, @Nullable LivingEntity livingEntity) {
		this(JerotesVillageEntityType.SERPON_CLASSICAL_BOMB.get(), level);
		this.setPos(d, d2, d3);
		double d0 = level.random.nextDouble() * (double)((float)Math.PI * 2F);
		this.setDeltaMovement(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
		this.setFuse(240);
		this.xo = d;
		this.yo = d2;
		this.zo = d3;
		this.owner = livingEntity;
	}

	protected void defineSynchedData() {
		this.getEntityData().define(DATA_FUSE_ID, 240);
		this.getEntityData().define(DATA_BLOCK_STATE_ID, JerotesVillageBlocks.SERPON_CLASSICAL_BOMB.get().defaultBlockState());
	}

	protected MovementEmission getMovementEmission() {
		return MovementEmission.NONE;
	}

	public boolean isPickable() {
		return !this.isRemoved();
	}
	public float getBombLevel() {
		AABB aabb = AABB.ofSize(new Vec3(this.getX(), this.getY(), this.getZ()), 9, 9, 9);
		float count = 1f;
		//玻璃 大鼓
		for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
			BlockState blockState = this.level().getBlockState(blockPos);
			if (blockState.is(JerotesVillageBlocks.SERPON_ALLOY_BLOCK.get())) {
				count += 0.02f;
			}
		}
		return count;
	}

	public void tick() {
		this.shockTick += 1;
		//震慑 粒子
		int flashFrequency = (int) Mth.clamp(60 - ((this.tickCount / 240.0) * 58), 2, 60);
		if (this.shockTick >= flashFrequency) {
			this.shockTick = 0;
			this.shock();
			this.shockGlass(flashFrequency);
			this.explodeAbout();
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, this.getSoundSource(), 20.0f, (float) (0.8f + Math.random() * 0.4f));
			this.level().addParticle(JerotesVillageParticleTypes.SERPON_JAVELIN_DOWN.get(), this.getX(), this.getY(), this.getZ(), 0, 0.01, 0);
			this.level().addParticle(JerotesVillageParticleTypes.SERPON_JAVELIN.get(), this.getX(), this.getY(), this.getZ(), 0, 0.01, 0);
		}
		if (!this.isNoGravity()) {
			this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
		}
		this.move(MoverType.SELF, this.getDeltaMovement());
		this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
		if (this.onGround()) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
		}
		int i = this.getFuse() - 1;
		this.setFuse(i);
		if (i <= 0) {
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.LIGHTNING_BOLT_IMPACT, this.getSoundSource(), 40.0f, (float) (0.8f + Math.random() * 0.4f));
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, this.getSoundSource(), 40.0f, (float) (0.8f + Math.random() * 0.4f));
			this.explode();
			this.discard();
		} else {
			this.updateInWaterStateAndDoFluidPushing();
			if (this.level().isClientSide) {
				this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);
			}
		}
	}

	public boolean shock() {
		float scale = 0.8f + getBombLevel() * 0.2f;
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(64.0 * scale, 64.0 * scale, 64.0 * scale));
		double d = Double.MAX_VALUE;
		int maxProcess = (int) (32 + getBombLevel());
		int count = 0;
		for (LivingEntity shock : list) {
			if (shock == null) continue;
			if ((this.distanceToSqr(shock)) > d) continue;
			if (shock.getHealth() > 300 * scale) continue;
			//推动
			double knock;
			if (shock.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
				knock = shock.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
			} else {
				knock = 0.0;
			}
			double d2 = Math.max(0.3, 1.3 - knock);
			if ((Main.mobSizeSmall(shock) || Main.mobSizeMedium(shock) || Main.mobSizeLarge(shock)) && (this.distanceToSqr(shock)) <= 32) {
				shock.setOnGround(false);
				shock.setDeltaMovement(shock.getDeltaMovement().add(-(this.getX() - shock.getX()) * d2 / 3, -(this.getY() - shock.getY()) * d2 / 4, -(this.getZ() - shock.getZ()) * d2 / 3));
			}
			//驯服生物
			if (shock instanceof TamableAnimal shocks && shocks.isInSittingPose())  {
				if (!shocks.level().isClientSide) {
					shocks.setOrderedToSit(false);
				}
			}
			if (shock instanceof TameMobEntity shocks && shocks.isInSittingPose())  {
				if (!shock.level().isClientSide) {
					shocks.setOrderedToSit(false);
				}
			}
			//起床
			if (shock.isSleeping()) {
				shock.stopSleeping();
			}
			if (!shock.level().isClientSide) {
				shock.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 40, 0, false, false), this);
			}
			if (shock instanceof PathfinderMob pathfinderMob) {
				for (int i = 0; i < 10; ++i) {
					Vec3 vec = LandRandomPos.getPosAway(pathfinderMob, 64, 64, this.position());
					if (vec != null) {
						boolean blNow = true;
						if (pathfinderMob.getNavigation().getTargetPos() != null) {
							Vec3 now = new Vec3(pathfinderMob.getNavigation().getTargetPos().getX(), pathfinderMob.getNavigation().getTargetPos().getY(), pathfinderMob.getNavigation().getTargetPos().getZ());
							if (vec.distanceTo(this.position()) <= now.distanceTo(this.position())) {
								blNow = false;
							}
						}
						//当前与目标位置 要比 当前与被逃离位置 更远
						if (blNow
								//目标与被逃离位置 要比 当前与被逃离位置 更远
								&& (vec.distanceTo(this.position()) > pathfinderMob.distanceTo(this))
								//被逃离与目标位置 要比 当前与目标位置 更远
								&& (vec.distanceTo(this.position()) > vec.distanceTo(pathfinderMob.position()) || i < 2)) {
							pathfinderMob.getNavigation().moveTo(vec.x, vec.y, vec.z, 1.5d);
							count++;
							break;
						}
					}
				}
			}
			if (count >= maxProcess) {
				break;
			}
		}
		return true;
	}

	public boolean shockGlass(int n) {
		float scale = 0.8f + getBombLevel() * 0.2f;
		AABB aabb = AABB.ofSize(new Vec3(this.getX(), this.getY(), this.getZ()), 64 * scale, 64 * scale, 64 * scale);
		AABB aabb0 = AABB.ofSize(new Vec3(this.getX(), this.getY(), this.getZ()), 6 * scale, 6 * scale, 6 * scale);
		if (this.level() instanceof ServerLevel serverLevel) {
			//玻璃 大鼓
			for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
				BlockState blockState = serverLevel.getBlockState(blockPos);
				float block = blockState.getDestroySpeed(serverLevel, blockPos);
				//大鼓
				if (blockState.getBlock() instanceof BrightLandDrum) {
					float voice = (17 - n / 5f) * 0.1f + 0.1f;
					float voiceDistance = 10f;
					if (this.level().getBlockState(blockPos.below()).getBlock() == Blocks.SCAFFOLDING) {
						voiceDistance = 20f;
					}
					serverLevel.sendParticles(ParticleTypes.NOTE, blockPos.getX() + 0.5, blockPos.getY() + 1.2, blockPos.getZ() + 0.5, 0, voice, 0.0, 0.0, 0.0);
					this.level().playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), JerotesVillageSoundEvents.BRIGHT_LAND_DRUM, SoundSource.BLOCKS, voiceDistance, voice);
					this.level().gameEvent(null, GameEvent.BLOCK_CHANGE, blockPos);
				}
				//钟
				if (blockState.getBlock() instanceof BellBlock bellBlock) {
					bellBlock.attemptToRing(this, serverLevel, blockPos, null);
				}
				//玻璃
				Block blocks = blockState.getBlock();
				ResourceLocation id = BuiltInRegistries.BLOCK.getKey(blocks);
				if (id != null && id.getPath().contains("glass")) {
					if (!blockState.isAir() && block < 30f && block > 0f) {
						serverLevel.destroyBlock(blockPos, true, this);
						BaseThrowingBallEntity throwItem = new GlassThrowingBallEntity(this.level(), blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
						throwItem.setItem(new ItemStack(blocks.asItem()));
						throwItem.speedDamage = 0.3 * 15;
						throwItem.addMaxBackCount = 6;
						this.level().addFreshEntity(throwItem);
					}
				}
			}
			//下落实体
			for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aabb0.minX), Mth.floor(aabb0.minY), Mth.floor(aabb0.minZ), Mth.floor(aabb0.maxX), Mth.floor(aabb0.maxY), Mth.floor(aabb0.maxZ))) {
				BlockState blockState = serverLevel.getBlockState(blockPos);
				if (blockState.getBlock() != JerotesVillageBlocks.SERPON_CLASSICAL_BOMB.get()) {
					Other.spawnEarthquakeFallingBlockByPos(serverLevel, blockPos);
				}
			}
		}
		return true;
	}

	private void processExplosionArea(ServerLevel serverLevel, AABB aabb, float chance, int n) {
		for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
			BlockState blockState = serverLevel.getBlockState(blockPos);
			FluidState fluidState = serverLevel.getFluidState(blockPos);
			float blockStrength = blockState.getDestroySpeed(serverLevel, blockPos);

			if (!blockState.isAir() && fluidState.isEmpty() && this.random.nextFloat() > chance
					&& blockStrength < 10 + getBombLevel() * 4f && blockStrength > 0f
					&& blockState.getBlock() != JerotesVillageBlocks.BRIGHT_LAND_DRUM.get()
					&& blockState.getBlock() != Blocks.TNT
					&& blockState.getBlock() != JerotesVillageBlocks.SERPON_CLASSICAL_BOMB.get()) {

				if (n == 0) {
					if (this.random.nextFloat() > chance) {
						Main.spawnMinecraftFallingBlockByPos(serverLevel, blockPos);
					}
					else {
						Other.spawnEarthquakeFallingBlockByPos(serverLevel, blockPos);
					}
				}
				if (!blockState.is(BlockTags.REPLACEABLE_BY_TREES)
						&& !blockState.is(BlockTags.DIRT)
						&& !blockState.is(BlockTags.STONE_ORE_REPLACEABLES)
						&& !blockState.is(BlockTags.DEEPSLATE_ORE_REPLACEABLES)
						&& !blockState.is(BlockTags.SCULK_REPLACEABLE)
						&& !blockState.is(BlockTags.REPLACEABLE_BY_TREES)
						&& !blockState.is(BlockTags.REPLACEABLE)) {
					serverLevel.destroyBlock(blockPos, true, this);
				} else {
					serverLevel.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
				}
				if (!serverLevel.getBlockState(blockPos.below()).isAir()) {
					serverLevel.setBlock(blockPos, Blocks.FIRE.defaultBlockState(), 2);
				}
			}
			else {
				if (n == 0) {
					Other.spawnEarthquakeFallingBlockByPos(serverLevel, blockPos);
				}
			}
		}
	}

	public void explodeAbout() {
		this.level().addParticle(JerotesVillageParticleTypes.EARTHQUAKE.get(), this.getX(), this.getY() + 0.1, this.getZ(), 0, 0.05, 0);
		this.level().addParticle(JerotesVillageParticleTypes.EARTHQUAKE.get(), this.getX(), this.getY() + 0.1, this.getZ(), 0, 0.1, 0);

		Vec3 vec3 = this.position().add(0, this.getY(0.5) - this.getY(), 0);
		for (int i = 0; i < 240; i++) {
			double radius = 12.0;
			double angle = Math.random() * Math.PI * 2;
			double inclination = Math.random() * Math.PI;
			double offsetX = radius * Math.sin(inclination) * Math.cos(angle);
			double offsetY = radius * Math.cos(inclination);
			double offsetZ = radius * Math.sin(inclination) * Math.sin(angle);
			Vec3 particlePos = vec3.add(offsetX, offsetY, offsetZ);
			Vec3 direction = vec3.subtract(particlePos).normalize();
			double speed = 0.1 + getBombLevel() * 0.05f;
			Vec3 velocity = direction.scale(speed);
			this.level().addParticle(ParticleTypes.FLAME, particlePos.x, particlePos.y, particlePos.z, velocity.x, velocity.y, velocity.z);
		}

		// 爆炸核心闪光（短时间内剧烈亮度）
		for (int i = 0; i < 100; i++) {
			double dx = (random.nextDouble() - 0.5) * 6;
			double dy = (random.nextDouble() - 0.5) * 6;
			double dz = (random.nextDouble() - 0.5) * 6;
			this.level().addParticle(ParticleTypes.FLASH, this.getX() + dx, this.getY() + dy, this.getZ() + dz, 0, 0, 0);
		}

		// 冲击波环（横向扩散的烟雾）
		for (int i = 0; i < 200; i++) {
			double angle = Math.random() * Math.PI * 2;
			double distance = 4 + Math.random() * 6;
			double px = this.getX() + Math.cos(angle) * distance;
			double pz = this.getZ() + Math.sin(angle) * distance;
			this.level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, px, this.getY(), pz, 0, 0.05, 0);
		}

		// 上升的蘑菇云（烟雾和火焰）
		for (int i = 0; i < 300; i++) {
			double height = Math.random() * 20;
			double radius = Math.sqrt(height / 20) * 6;
			double angle = Math.random() * Math.PI * 2;
			double px = this.getX() + Math.cos(angle) * radius;
			double pz = this.getZ() + Math.sin(angle) * radius;
			this.level().addParticle(ParticleTypes.LARGE_SMOKE, px, this.getY() + height, pz, 0, 0.1, 0);
			if (random.nextDouble() < 0.3) {
				this.level().addParticle(ParticleTypes.FLAME, px, this.getY() + height, pz, 0, 0.15, 0);
			}
		}

		// 火焰与余烬（燃烧的碎片）
		for (int i = 0; i < 50; i++) {
			double dx = (random.nextDouble() - 0.5) * 10;
			double dy = random.nextDouble() * 6;
			double dz = (random.nextDouble() - 0.5) * 10;
			this.level().addParticle(ParticleTypes.FALLING_LAVA, this.getX() + dx, this.getY() + dy, this.getZ() + dz, 0, -0.05, 0);
		}

		// 地面扬尘（冲击波向外扩散）
		for (int i = 0; i < 100; i++) {
			double angle = Math.random() * Math.PI * 2;
			double distance = 8 + Math.random() * 10;
			double px = this.getX() + Math.cos(angle) * distance;
			double pz = this.getZ() + Math.sin(angle) * distance;
			this.level().addParticle(ParticleTypes.SMOKE, px, this.getY(), pz, 0, 0.01, 0);
		}
	}

	protected void explode() {
		float f = 10f + getBombLevel() * 2f;
		if (this.level() instanceof ServerLevel serverLevel) {
			float scale = 0.8f + getBombLevel() * 0.2f;
			//震荡方块
			AABB[] aabbs = {
					AABB.ofSize(new Vec3(this.getX(), this.getY(), this.getZ()), 24 * scale, 24 * scale, 24 * scale),
					AABB.ofSize(new Vec3(this.getX(), this.getY(), this.getZ()), 32 * scale, 32 * scale, 32 * scale),
					AABB.ofSize(new Vec3(this.getX(), this.getY(), this.getZ()), 40 * scale, 40 * scale, 40 * scale),
					AABB.ofSize(new Vec3(this.getX(), this.getY(), this.getZ()), 48 * scale, 48 * scale, 48 * scale)
			};
//			aabbs = new AABB[]{
//                    AABB.ofSize(new Vec3(this.getX(), this.getY(), this.getZ()), 64, 64, 64),
//                    AABB.ofSize(new Vec3(this.getX(), this.getY(), this.getZ()), 80, 80, 80),
//                    AABB.ofSize(new Vec3(this.getX(), this.getY(), this.getZ()), 96, 96, 96),
//                    AABB.ofSize(new Vec3(this.getX(), this.getY(), this.getZ()), 128, 128, 128)
//            };

//			for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aabbs[0].minX), Mth.floor(aabbs[0].minY), Mth.floor(aabbs[0].minZ), Mth.floor(aabbs[0].maxX), Mth.floor(aabbs[0].maxY), Mth.floor(aabbs[0].maxZ))) {
//				JerotesVillageEntity.spawnMinecraftFallingBlockByPos(serverLevel, blockPos);
//			}
			float[] chances = {0.25f, 0.35f, 0.55f, 0.85f};
			for (int i = 0; i < aabbs.length; i++) {
				processExplosionArea(serverLevel, aabbs[i], chances[i], i);
			}
		}
			//攻击
			double d = Double.MAX_VALUE;
			List<Entity> list = this.level().getEntitiesOfClass(Entity.class, this.getBoundingBox().inflate(48, 48, 48));
			for (Entity hurt : list) {
				if (hurt == null) continue;
				if (hurt == this) continue;
				if ((this.distanceToSqr(hurt)) > d) continue;
				float strength = (64 - this.distanceTo(hurt)) / 3 * (0.8f + this.getBombLevel() * 0.2f);
				hurt.setOnGround(false);
				if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt)) && !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
					hurt.setDeltaMovement(hurt.getDeltaMovement().add(0, (this.getY() < hurt.getY() ? -1 : 1) * strength / 20, 0));
				}
				if (hurt instanceof LivingEntity livingHurt) {
					//推动
					double knock;
					if (livingHurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
						knock = livingHurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
					} else {
						knock = 0.0;
					}
					double d2 = Math.max(0.3, 1.3 - knock);
					livingHurt.setDeltaMovement(livingHurt.getDeltaMovement().add(-(this.getX() - livingHurt.getX()) * d2 / 3, -(this.getY() - livingHurt.getY()) * d2 / 4, -(this.getZ() - livingHurt.getZ()) * d2 / 3));

					if (Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt)) {
						livingHurt.setDeltaMovement(livingHurt.getDeltaMovement().add(0, (this.getY() < livingHurt.getY() ? -1 : 1) * strength, 0));
					}
				}
				if (hurt instanceof ItemEntity itemEntity && itemEntity.getItem().getRarity() == Rarity.COMMON) {
					itemEntity.setDeltaMovement(0, strength / 6, 0);
				}
				if (!(hurt instanceof ItemEntity)){
					hurt.setDeltaMovement(0, strength / 5, 0);
				}
				if (!(hurt instanceof LivingEntity livingHurt)) continue;
				if (!livingHurt.level().isClientSide) {
					livingHurt.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 120, 0, false, false), this);
				}
				float damages = f;
				if (EntityFactionFind.isMachine(livingHurt) || EntityFactionFind.isConstruct(livingHurt) || !(livingHurt instanceof Player || livingHurt instanceof Mob)) {
					damages = f * 8;
				}
				livingHurt.hurt(damageSources().explosion(this, this.getOwner() == null ? this : this.getOwner()), strength * damages);
				if (livingHurt instanceof Player player) {
					Item item0 = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
					Item item1 = player.getOffhandItem().getItem();
					if (item0 instanceof ShieldItem) {
						player.getCooldowns().addCooldown(item0, (int) (200 + this.getBombLevel() * 40));
					}
					if (item1 instanceof ShieldItem) {
						player.getCooldowns().addCooldown(item1, (int) (200 + this.getBombLevel() * 40));
					}
				}
			}

		if (this.level() instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(JerotesVillageParticleTypes.EARTHQUAKE.get(), this.getX(), this.getY() + 0.1, this.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
		}
		if (!this.level().isClientSide) {
			this.level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), f, true, Level.ExplosionInteraction.TNT);
		}
	}

	private int shockTick;
	protected void addAdditionalSaveData(CompoundTag compoundTag) {
		compoundTag.putInt("ShockTick", this.shockTick);
		compoundTag.putShort("fuse", (short)this.getFuse());
		compoundTag.put("block_state", NbtUtils.writeBlockState(this.getBlockState()));
	}

	protected void readAdditionalSaveData(CompoundTag compoundTag) {
		this.shockTick = compoundTag.getInt("ShockTick");
		this.setFuse(compoundTag.getShort("fuse"));
		if (compoundTag.contains("block_state", 10)) {
			this.setBlockState(NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), compoundTag.getCompound("block_state")));
		}
	}

	@Nullable
	public LivingEntity getOwner() {
		return this.owner;
	}

	public void restoreFrom(Entity entity) {
		super.restoreFrom(entity);
		if (entity instanceof PrimedSerponClassicalBombEntity PrimedSerponClassicalBombEntity) {
			this.owner = PrimedSerponClassicalBombEntity.owner;
		}

	}

	protected float getEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 0.15F;
	}

	public void setFuse(int n) {
		this.entityData.set(DATA_FUSE_ID, n);
	}

	public int getFuse() {
		return this.entityData.get(DATA_FUSE_ID);
	}

	public void setBlockState(BlockState blockState) {
		this.entityData.set(DATA_BLOCK_STATE_ID, blockState);
	}

	public BlockState getBlockState() {
		return this.entityData.get(DATA_BLOCK_STATE_ID);
	}
}