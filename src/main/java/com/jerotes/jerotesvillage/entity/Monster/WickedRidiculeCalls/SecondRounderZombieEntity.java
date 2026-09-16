package com.jerotes.jerotesvillage.entity.Monster.WickedRidiculeCalls;

import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesHelpAlliesGoal;
import com.jerotes.jerotes.goal.JerotesHelpSameFactionGoal;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Humanoid.Meror.MerorEntity;
import com.jerotes.jerotesvillage.entity.Humanoid.SecondRounderEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.network.JerotesVillageLevelData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class SecondRounderZombieEntity extends Zombie implements JerotesEntity, SkinEntity, WickedRidiculeCallsEntity, FactionEntity {
	private static final EntityDataAccessor<Boolean> DATA_CONVERTING_ID = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> DATA_SKIN_A_ID = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> DATA_SKIN_B_ID = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> DATA_SKIN_C_ID = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> GIANT = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> MEROR_LOCATION = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.INT);
	//
	private static final EntityDataAccessor<Boolean> IS_FEMALE = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SKIN_COLOR = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> EYE_TYPE = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> EYE_COLOR = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> HAIR_TYPE = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> HAIR_COLOR = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ADD_TYPE_1 = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ADD_TYPE_2 = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ADD_TYPE_3 = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> JACKET_TYPE = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> JACKET_COLOR = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> PANTS_TYPE = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> PANTS_COLOR = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> GLOVES_TYPE = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> GLOVES_COLOR = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BAUBLE_TYPE_1 = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BAUBLE_TYPE_2 = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BAUBLE_TYPE_3 = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHOES_TYPE = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHOES_COLOR = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> HAT_TYPE = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> HAT_COLOR = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> OVERCOAT_TYPE = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> OVERCOAT_COLOR = SynchedEntityData.defineId(SecondRounderZombieEntity.class, EntityDataSerializers.INT);
	//
	public SecondRounderZombieEntity(EntityType<? extends SecondRounderZombieEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 10;
	}

	@Override
	public String getFirstFactionTypeName() {
		return "wicked_ridicule_calls";
	}

	@Override
	protected boolean canReplaceCurrentItem(ItemStack newItem, ItemStack oldItem) {
		return InventoryEntity.canReplaceCurrentItem(newItem, oldItem, true, false, false, false, false, false, true, true, true, true);
	}

	@Override
	public boolean wantsToPickUp(ItemStack itemStack) {
		if (itemStack.is(Items.BONE_MEAL)) {
			return false;
		}
		if (itemStack.is(Items.GLOW_INK_SAC)) {
			return false;
		}
		return super.wantsToPickUp(itemStack);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.26);
		builder = builder.add(Attributes.MAX_HEALTH, 35);
		builder = builder.add(Attributes.ARMOR, 2);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.FOLLOW_RANGE, 35);
		builder = builder.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, -1);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, new JerotesHelpSameFactionGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, 5, false, false, livingEntity -> EntityFactionFind.isHateFaction(this, livingEntity)));
		this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<SecondRounderEntity>(this, SecondRounderEntity.class, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<HumanEntity>(this, HumanEntity.class, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, false));
	}

	@Override
	public boolean removeWhenFarAway(double d) {
		return super.removeWhenFarAway(d) && !this.isConverting() && !this.isInitial() ;
	}
	@Override
	protected boolean convertsInWater() {
		return false;
	}
	public boolean isConverting() {
		return this.getEntityData().get(DATA_CONVERTING_ID);
	}
	@Override
	protected boolean isSunSensitive() {
		if (this.isInitial()) {
			return false;
		}
		return super.isSunSensitive();
	}
	@Override
	protected boolean shouldDespawnInPeaceful() {
		if (this.isInitial()) {
			return false;
		}
		return super.shouldDespawnInPeaceful();
	}
	@Override
	protected AABB makeBoundingBox() {
		if (this.isGiant()) {
			return AABB.ofSize(new Vec3(this.getX(), this.getY() + 1.7, this.getZ()), 1.05f, 3.4f, 1.05f);
		}
		return super.makeBoundingBox();
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		if (this.isGiant()) {
			return this.isBaby() ? 1.63f : 3.05f;
		}
		return this.isBaby() ? 1.0f : 1.9f;
	}
	private void finishConversion(ServerLevel serverLevel) {

		boolean blSkinA = this.isSkinA();
		boolean blSkinB = this.isSkinB();
		boolean blSkinC = this.isSkinC();
		boolean blGiant = this.isGiant();
		//
		boolean getFemale = this.IsFemale();
		int getMerorLocation = this.getMerorLocation();
		int getSkinType = this.SkinColor();
		int getSkinColor = this.SkinType();
		int getEyeType = this.EyeType();
		int getEyeColor = this.EyeColor();
		int getAddType_1 = this.AddType_1();
		int getAddType_2 = this.AddType_2();
		int getAddType_3 = this.AddType_3();
		int getHairType = this.HairType();
		int getHairColor = this.HairColor();
		int getJacketType = this.JacketType();
		int getJacketColor = this.JacketColor();
		int getPantsType = this.PantsType();
		int getPantsColor = this.PantsColor();
		int getGlovesType = this.GlovesType();
		int getGlovesColor = this.GlovesColor();
		int getBaubleType_1 = this.BaubleType_1();
		int getBaubleType_2 = this.BaubleType_2();
		int getBaubleType_3 = this.BaubleType_3();
		int getShoesType = this.ShoesType();
		int getShoesColor = this.ShoesColor();
		int getHatType = this.HatType();
		int getHatColor = this.HatColor();
		int getOvercoatType = this.OvercoatType();
		int getOvercoatColor = this.OvercoatColor();
		//
		Mob mob;
		EntityType mobEntityType = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(this.initialType));
		mob = this.convertTo(mobEntityType, true);
		if (mob != null) {
			mob.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(mob.blockPosition()), MobSpawnType.CONVERSION, null, null);
			if (mob instanceof SecondRounderEntity secondRounder) {
				secondRounder.newSpawn = 1;
				secondRounder.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
				//
				secondRounder.setFemale(getFemale);
				if (secondRounder instanceof MerorEntity merorEntity) {
					merorEntity.setMerorLocation(getMerorLocation);
				}
				secondRounder.setSkinType(getSkinType);
				secondRounder.setSkinColor(getSkinColor);
				secondRounder.setEyeType(getEyeType);
				secondRounder.setEyeColor(getEyeColor);
				secondRounder.setAddType_1(getAddType_1);
				secondRounder.setAddType_2(getAddType_2);
				secondRounder.setAddType_3(getAddType_3);
				secondRounder.setHairType(getHairType);
				secondRounder.setHairColor(getHairColor);
				secondRounder.setJacketType(getJacketType);
				secondRounder.setJacketColor(getJacketColor);
				secondRounder.setPantsType(getPantsType);
				secondRounder.setPantsColor(getPantsColor);
				secondRounder.setGlovesType(getGlovesType);
				secondRounder.setGlovesColor(getGlovesColor);
				secondRounder.setBaubleType_1(getBaubleType_1);
				secondRounder.setBaubleType_2(getBaubleType_2);
				secondRounder.setBaubleType_3(getBaubleType_3);
				secondRounder.setShoesType(getShoesType);
				secondRounder.setShoesColor(getShoesColor);
				secondRounder.setHatType(getHatType);
				secondRounder.setHatColor(getHatColor);
				secondRounder.setOvercoatType(getOvercoatType);
				secondRounder.setOvercoatColor(getOvercoatColor);
				//
				secondRounder.setGiant(blGiant);
			}
			if (mob instanceof SecondRounderSkeletonEntity secondRounderSkeleton) {
				secondRounderSkeleton.newSpawn = 1;
				mob.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 12000, 3));
				mob.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 12000, 3));
				mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 12000, 3));
				secondRounderSkeleton.setSkinA(blSkinA);
				secondRounderSkeleton.setSkinB(blSkinB);
				secondRounderSkeleton.setSkinC(blSkinC);
				secondRounderSkeleton.setGiant(blGiant);
			}
		}
		if (!this.isSilent()) {
			serverLevel.levelEvent(null, 1027, this.blockPosition(), 0);
		}
		net.minecraftforge.event.ForgeEventFactory.onLivingConvert(this, mob);
	}
	@Override
	public boolean killedEntity(ServerLevel serverLevel, LivingEntity livingEntity) {
		PlayerTeam teams = (PlayerTeam) this.getTeam();
		boolean bl = super.killedEntity(serverLevel, livingEntity);
		if (livingEntity instanceof SecondRounderEntity) {
			SecondRounderEntity secondRounder = (SecondRounderEntity)livingEntity;
			Boolean blSkinA = secondRounder.isSkinA();
			Boolean blSkinB = secondRounder.isSkinB();
			Boolean blSkinC = secondRounder.isSkinC();
			Boolean blGiant = secondRounder.isGiant();
			boolean getFemale = secondRounder.IsFemale();
			int getMerorLocation = 1;
			if (secondRounder instanceof MerorEntity merorEntity) {
				getMerorLocation = merorEntity.getMerorLocation();
			}
			int getSkinType = secondRounder.SkinColor();
			int getSkinColor = secondRounder.SkinType();
			int getEyeType = secondRounder.EyeType();
			int getEyeColor = secondRounder.EyeColor();
			int getAddType_1 = secondRounder.AddType_1();
			int getAddType_2 = secondRounder.AddType_2();
			int getAddType_3 = secondRounder.AddType_3();
			int getHairType = secondRounder.HairType();
			int getHairColor = secondRounder.HairColor();
			int getJacketType = secondRounder.JacketType();
			int getJacketColor = secondRounder.JacketColor();
			int getPantsType = secondRounder.PantsType();
			int getPantsColor = secondRounder.PantsColor();
			int getGlovesType = secondRounder.GlovesType();
			int getGlovesColor = secondRounder.GlovesColor();
			int getBaubleType_1 = secondRounder.BaubleType_1();
			int getBaubleType_2 = secondRounder.BaubleType_2();
			int getBaubleType_3 = secondRounder.BaubleType_3();
			int getShoesType = secondRounder.ShoesType();
			int getShoesColor = secondRounder.ShoesColor();
			int getHatType = secondRounder.HatType();
			int getHatColor = secondRounder.HatColor();
			int getOvercoatType = secondRounder.OvercoatType();
			int getOvercoatColor = secondRounder.OvercoatColor();
			String string = secondRounder.getType().toString();
			secondRounder.mobInventory().removeAllItems().forEach(this::spawnAtLocation);
			String cleanedEntityType = string.replaceFirst("^entity\\.", "").replaceFirst("\\.", ":");
			SecondRounderZombieEntity secondRounderZombie = secondRounder.convertTo(JerotesVillageEntityType.SECOND_ROUNDER_ZOMBIE.get(), true);
			if (secondRounderZombie != null) {
				if (teams != null) {
					serverLevel.getScoreboard().addPlayerToTeam(secondRounderZombie.getStringUUID(), teams);
				}
				secondRounderZombie.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(secondRounderZombie.blockPosition()), MobSpawnType.CONVERSION, null, null);
				secondRounderZombie.newSpawn = 1;
				secondRounderZombie.initial = true;
				secondRounderZombie.initialType = cleanedEntityType;
				//
				secondRounderZombie.setFemale(getFemale);
				secondRounderZombie.setMerorLocation(getMerorLocation);
				secondRounderZombie.setSkinType(getSkinType);
				secondRounderZombie.setSkinColor(getSkinColor);
				secondRounderZombie.setEyeType(getEyeType);
				secondRounderZombie.setEyeColor(getEyeColor);
				secondRounderZombie.setAddType_1(getAddType_1);
				secondRounderZombie.setAddType_2(getAddType_2);
				secondRounderZombie.setAddType_3(getAddType_3);
				secondRounderZombie.setHairType(getHairType);
				secondRounderZombie.setHairColor(getHairColor);
				secondRounderZombie.setJacketType(getJacketType);
				secondRounderZombie.setJacketColor(getJacketColor);
				secondRounderZombie.setPantsType(getPantsType);
				secondRounderZombie.setPantsColor(getPantsColor);
				secondRounderZombie.setGlovesType(getGlovesType);
				secondRounderZombie.setGlovesColor(getGlovesColor);
				secondRounderZombie.setBaubleType_1(getBaubleType_1);
				secondRounderZombie.setBaubleType_2(getBaubleType_2);
				secondRounderZombie.setBaubleType_3(getBaubleType_3);
				secondRounderZombie.setShoesType(getShoesType);
				secondRounderZombie.setShoesColor(getShoesColor);
				secondRounderZombie.setHatType(getHatType);
				secondRounderZombie.setHatColor(getHatColor);
				secondRounderZombie.setOvercoatType(getOvercoatType);
				secondRounderZombie.setOvercoatColor(getOvercoatColor);
				secondRounderZombie.setSkinA(blSkinA);
				secondRounderZombie.setSkinB(blSkinB);
				secondRounderZombie.setSkinC(blSkinC);
				secondRounderZombie.setGiant(blGiant);
				//
				if (!this.isSilent()) {
					serverLevel.levelEvent(null, 1026, this.blockPosition(), 0);
				}
				bl = false;
				net.minecraftforge.event.ForgeEventFactory.onLivingConvert(livingEntity, secondRounderZombie);
			}
		}
		return bl;
	}
	private int getConversionProgress() {
		int n = 1;
		if (this.random.nextFloat() < 0.01f) {
			int n2 = 0;
			BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
			for (int i = (int)this.getX() - 4; i < (int)this.getX() + 4 && n2 < 14; ++i) {
				for (int j = (int)this.getY() - 4; j < (int)this.getY() + 4 && n2 < 14; ++j) {
					for (int k = (int)this.getZ() - 4; k < (int)this.getZ() + 4 && n2 < 14; ++k) {
						BlockState blockState = this.level().getBlockState(mutableBlockPos.set(i, j, k));
						if (!blockState.is(Blocks.IRON_BARS) && !(blockState.getBlock() instanceof BedBlock)) continue;
						if (this.random.nextFloat() < 0.3f) {
							++n;
						}
						++n2;
					}
				}
			}
		}
		return n;
	}
	@Override
	public void addInWickedRidiculeNight(boolean bl) {
		if (!this.level().isClientSide()) {
			this.addEffect(new MobEffectInstance(JerotesVillageMobEffects.DEEP_BLESSING.get(), 5, 0), this);
			this.addEffect(new MobEffectInstance(JerotesVillageMobEffects.WICKED_RIDICULE_NIGHT.get(), 5, 0), this);

			this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 5, 0), this);
			this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 5, 0), this);
		}
	}

	public int getMerorLocation() {
		return this.getEntityData().get(MEROR_LOCATION);
	}
	public void setMerorLocation(int n) {
		this.getEntityData().set(MEROR_LOCATION, n);
	}
	public boolean isSkinA() {
		return this.getEntityData().get(DATA_SKIN_A_ID);
	}
	public boolean isSkinB() {
		return this.getEntityData().get(DATA_SKIN_B_ID);
	}
	public boolean isSkinC() {
		return this.getEntityData().get(DATA_SKIN_C_ID);
	}
	public boolean isGiant() {
		return this.getEntityData().get(GIANT);
	}
	public void setSkinA(boolean bl) {
		this.getEntityData().set(DATA_SKIN_A_ID, bl);
	}
	public void setSkinB(boolean bl) {
		this.getEntityData().set(DATA_SKIN_B_ID, bl);
	}
	public void setSkinC(boolean bl) {
		this.getEntityData().set(DATA_SKIN_C_ID, bl);
	}
	public void setGiant(boolean bl) {
		this.getEntityData().set(GIANT, bl);
		if (bl) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(70);
			this.setHealth(70.0f);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(7);
			setMaxUpStep(1.1f);
		}
		else{
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(35);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3);
			setMaxUpStep(0.6f);
		}
	}
	public int newSpawn;
	public boolean initial;
	public String initialType = "jerotesvillage:second_rounder_skeleton";
	private int zombieConversionTime;
	public boolean isInitial() {
		return this.initial;
	}
	public String getInitialType() {
		return this.initialType;
	}
	//性别
	@Override
	public boolean IsFemale() {
		return this.getEntityData().get(IS_FEMALE);
	}
	public void setFemale(boolean bl) {
		this.getEntityData().set(IS_FEMALE, bl);
	}
	//皮肤
	@Override
	public int SkinType() {
		return this.getEntityData().get(SKIN_TYPE);
	}
	public void setSkinType(int n) {
		this.getEntityData().set(SKIN_TYPE, n);
	}
	@Override
	public int SkinColor() {
		return this.getEntityData().get(SKIN_COLOR);
	}
	public void setSkinColor(int n) {
		this.getEntityData().set(SKIN_COLOR, n);
	}
	//眼睛
	@Override
	public int EyeType() {
		return this.getEntityData().get(EYE_TYPE);
	}
	public void setEyeType(int n) {
		this.getEntityData().set(EYE_TYPE, n);
	}
	@Override
	public int EyeColor() {
		return this.getEntityData().get(EYE_COLOR);
	}
	public void setEyeColor(int n) {
		this.getEntityData().set(EYE_COLOR, n);
	}
	//头发
	@Override
	public int HairType() {
		return this.getEntityData().get(HAIR_TYPE);
	}
	public void setHairType(int n) {
		this.getEntityData().set(HAIR_TYPE, n);
	}
	@Override
	public int HairColor() {
		return this.getEntityData().get(HAIR_COLOR);
	}
	public void setHairColor(int n) {
		this.getEntityData().set(HAIR_COLOR, n);
	}
	//附件-1
	@Override
	public int AddType_1() {
		return this.getEntityData().get(ADD_TYPE_1);
	}
	public void setAddType_1(int n) {
		this.getEntityData().set(ADD_TYPE_1, n);
	}
	//附件-2
	@Override
	public int AddType_2() {
		return this.getEntityData().get(ADD_TYPE_2);
	}
	public void setAddType_2(int n) {
		this.getEntityData().set(ADD_TYPE_2, n);
	}
	//附件-3
	@Override
	public int AddType_3() {
		return this.getEntityData().get(ADD_TYPE_3);
	}
	public void setAddType_3(int n) {
		this.getEntityData().set(ADD_TYPE_3, n);
	}
	//上衣
	@Override
	public int JacketType() {
		return this.getEntityData().get(JACKET_TYPE);
	}
	public void setJacketType(int n) {
		this.getEntityData().set(JACKET_TYPE, n);
	}
	@Override
	public int JacketColor() {
		return this.getEntityData().get(JACKET_COLOR);
	}
	public void setJacketColor(int n) {
		this.getEntityData().set(JACKET_COLOR, n);
	}
	//裤子
	@Override
	public int PantsType() {
		return this.getEntityData().get(PANTS_TYPE);
	}
	public void setPantsType(int n) {
		this.getEntityData().set(PANTS_TYPE, n);
	}
	@Override
	public int PantsColor() {
		return this.getEntityData().get(PANTS_COLOR);
	}
	public void setPantsColor(int n) {
		this.getEntityData().set(PANTS_COLOR, n);
	}
	//手套
	@Override
	public int GlovesType() {
		return this.getEntityData().get(GLOVES_TYPE);
	}
	public void setGlovesType(int n) {
		this.getEntityData().set(GLOVES_TYPE, n);
	}
	@Override
	public int GlovesColor() {
		return this.getEntityData().get(GLOVES_COLOR);
	}
	public void setGlovesColor(int n) {
		this.getEntityData().set(GLOVES_COLOR, n);
	}
	//饰品-1
	@Override
	public int BaubleType_1() {
		return this.getEntityData().get(BAUBLE_TYPE_1);
	}
	public void setBaubleType_1(int n) {
		this.getEntityData().set(BAUBLE_TYPE_1, n);
	}
	//饰品-2
	@Override
	public int BaubleType_2() {
		return this.getEntityData().get(BAUBLE_TYPE_2);
	}
	public void setBaubleType_2(int n) {
		this.getEntityData().set(BAUBLE_TYPE_2, n);
	}
	//饰品-3
	@Override
	public int BaubleType_3() {
		return this.getEntityData().get(BAUBLE_TYPE_3);
	}
	public void setBaubleType_3(int n) {
		this.getEntityData().set(BAUBLE_TYPE_3, n);
	}
	//鞋子
	@Override
	public int ShoesType() {
		return this.getEntityData().get(SHOES_TYPE);
	}
	public void setShoesType(int n) {
		this.getEntityData().set(SHOES_TYPE, n);
	}
	@Override
	public int ShoesColor() {
		return this.getEntityData().get(SHOES_COLOR);
	}
	public void setShoesColor(int n) {
		this.getEntityData().set(SHOES_COLOR, n);
	}
	//帽子
	@Override
	public int HatType() {
		return this.getEntityData().get(HAT_TYPE);
	}
	public void setHatType(int n) {
		this.getEntityData().set(HAT_TYPE, n);
	}
	@Override
	public int HatColor() {
		return this.getEntityData().get(HAT_COLOR);
	}
	public void setHatColor(int n) {
		this.getEntityData().set(HAT_COLOR, n);
	}
	//外套
	@Override
	public int OvercoatType() {
		return this.getEntityData().get(OVERCOAT_TYPE);
	}
	public void setOvercoatType(int n) {
		this.getEntityData().set(OVERCOAT_TYPE, n);
	}
	@Override
	public int OvercoatColor() {
		return this.getEntityData().get(OVERCOAT_COLOR);
	}
	public void setOvercoatColor(int n) {
		this.getEntityData().set(OVERCOAT_COLOR, n);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("MerorLocation", this.getMerorLocation());
		compoundTag.putInt("NewSpawn", this.newSpawn);
		compoundTag.putBoolean("Initial", this.initial);
		compoundTag.putString("InitialType", this.initialType);
		compoundTag.putInt("ConversionTime", this.isConverting() ? this.zombieConversionTime : -1);
		compoundTag.putBoolean("IsSkinA", this.isSkinA());
		compoundTag.putBoolean("IsSkinB", this.isSkinB());
		compoundTag.putBoolean("IsSkinC", this.isSkinC());
		compoundTag.putBoolean("IsGiant", this.isGiant());

		compoundTag.putBoolean("IsFemale", this.IsFemale());
		compoundTag.putInt("SkinType", this.SkinType());
		compoundTag.putInt("SkinColor", this.SkinColor());
		compoundTag.putInt("EyeType", this.EyeType());
		compoundTag.putInt("EyeColor", this.EyeColor());
		compoundTag.putInt("HairType", this.HairType());
		compoundTag.putInt("HairColor", this.HairColor());
		compoundTag.putInt("AddType_1", this.AddType_1());
		compoundTag.putInt("AddType_2", this.AddType_2());
		compoundTag.putInt("AddType_3", this.AddType_3());
		compoundTag.putInt("JacketType", this.JacketType());
		compoundTag.putInt("JacketColor", this.JacketColor());
		compoundTag.putInt("PantsType", this.PantsType());
		compoundTag.putInt("PantsColor", this.PantsColor());
		compoundTag.putInt("GlovesType", this.GlovesType());
		compoundTag.putInt("GlovesColor", this.GlovesColor());
		compoundTag.putInt("BaubleType_1", this.BaubleType_1());
		compoundTag.putInt("BaubleType_2", this.BaubleType_2());
		compoundTag.putInt("BaubleType_3", this.BaubleType_3());
		compoundTag.putInt("ShoesType", this.ShoesType());
		compoundTag.putInt("ShoesColor", this.ShoesColor());
		compoundTag.putInt("HatType", this.HatType());
		compoundTag.putInt("HatColor", this.HatColor());
		compoundTag.putInt("OvercoatType", this.OvercoatType());
		compoundTag.putInt("OvercoatColor", this.OvercoatColor());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setMerorLocation(compoundTag.getInt("MerorLocation"));
		this.newSpawn = compoundTag.getInt("NewSpawn");
		this.initial = compoundTag.getBoolean("Initial");
		this.initialType = compoundTag.getString("InitialType");
		if (compoundTag.contains("ConversionTime", 99) && compoundTag.getInt("ConversionTime") > -1) {
			this.zombieConversionTime = compoundTag.getInt("ConversionTime");
		}
		if (compoundTag.contains("IsSkinA")) {
			this.setSkinA(compoundTag.getBoolean("IsSkinA"));
		}
		if (compoundTag.contains("IsSkinB")) {
			this.setSkinB(compoundTag.getBoolean("IsSkinB"));
		}
		if (compoundTag.contains("IsSkinC")) {
			this.setSkinC(compoundTag.getBoolean("IsSkinC"));
		}
		if (compoundTag.contains("IsGiant")) {
			this.setGiant(compoundTag.getBoolean("IsGiant"));
		}


		if (compoundTag.contains("IsFemale")) {
			this.setFemale(compoundTag.getBoolean("IsFemale"));
		}
		if (compoundTag.contains("SkinType")) {
			this.setSkinType(compoundTag.getInt("SkinType"));
		}
		if (compoundTag.contains("SkinColor")) {
			this.setSkinColor(compoundTag.getInt("SkinColor"));
		}
		if (compoundTag.contains("EyeType")) {
			this.setEyeType(compoundTag.getInt("EyeType"));
		}
		if (compoundTag.contains("EyeColor")) {
			this.setEyeColor(compoundTag.getInt("EyeColor"));
		}
		if (compoundTag.contains("HairType")) {
			this.setHairType(compoundTag.getInt("HairType"));
		}
		if (compoundTag.contains("HairColor")) {
			this.setHairColor(compoundTag.getInt("HairColor"));
		}
		if (compoundTag.contains("AddType_1")) {
			this.setAddType_1(compoundTag.getInt("AddType_1"));
		}
		if (compoundTag.contains("AddType_2")) {
			this.setAddType_2(compoundTag.getInt("AddType_2"));
		}
		if (compoundTag.contains("AddType_3")) {
			this.setAddType_3(compoundTag.getInt("AddType_3"));
		}
		if (compoundTag.contains("JacketType")) {
			this.setJacketType(compoundTag.getInt("JacketType"));
		}
		if (compoundTag.contains("JacketColor")) {
			this.setJacketColor(compoundTag.getInt("JacketColor"));
		}
		if (compoundTag.contains("PantsType")) {
			this.setPantsType(compoundTag.getInt("PantsType"));
		}
		if (compoundTag.contains("PantsColor")) {
			this.setPantsColor(compoundTag.getInt("PantsColor"));
		}
		if (compoundTag.contains("GlovesType")) {
			this.setGlovesType(compoundTag.getInt("GlovesType"));
		}
		if (compoundTag.contains("GlovesColor")) {
			this.setGlovesColor(compoundTag.getInt("GlovesColor"));
		}
		if (compoundTag.contains("BaubleType_1")) {
			this.setBaubleType_1(compoundTag.getInt("BaubleType_1"));
		}
		if (compoundTag.contains("BaubleType_2")) {
			this.setBaubleType_2(compoundTag.getInt("BaubleType_2"));
		}
		if (compoundTag.contains("BaubleType_3")) {
			this.setBaubleType_3(compoundTag.getInt("BaubleType_3"));
		}
		if (compoundTag.contains("ShoesType")) {
			this.setShoesType(compoundTag.getInt("ShoesType"));
		}
		if (compoundTag.contains("ShoesColor")) {
			this.setShoesColor(compoundTag.getInt("ShoesColor"));
		}
		if (compoundTag.contains("HatType")) {
			this.setHatType(compoundTag.getInt("HatType"));
		}
		if (compoundTag.contains("HatColor")) {
			this.setHatColor(compoundTag.getInt("HatColor"));
		}
		if (compoundTag.contains("OvercoatType")) {
			this.setOvercoatType(compoundTag.getInt("OvercoatType"));
		}
		if (compoundTag.contains("OvercoatColor")) {
			this.setOvercoatColor(compoundTag.getInt("OvercoatColor"));
		}
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(MEROR_LOCATION, 1);
		this.getEntityData().define(DATA_CONVERTING_ID, false);
		this.getEntityData().define(DATA_SKIN_A_ID, false);
		this.getEntityData().define(DATA_SKIN_B_ID, false);
		this.getEntityData().define(DATA_SKIN_C_ID, false);
		this.getEntityData().define(GIANT, false);

		this.getEntityData().define(IS_FEMALE, false);
		this.getEntityData().define(SKIN_TYPE, 1);
		this.getEntityData().define(SKIN_COLOR, 1);
		this.getEntityData().define(EYE_TYPE, 1);
		this.getEntityData().define(EYE_COLOR, 1);
		this.getEntityData().define(HAIR_TYPE, 1);
		this.getEntityData().define(HAIR_COLOR, 1);
		this.getEntityData().define(ADD_TYPE_1, 0);
		this.getEntityData().define(ADD_TYPE_2, 0);
		this.getEntityData().define(ADD_TYPE_3, 0);
		this.getEntityData().define(JACKET_TYPE, 1);
		this.getEntityData().define(JACKET_COLOR, 1);
		this.getEntityData().define(PANTS_TYPE, 1);
		this.getEntityData().define(PANTS_COLOR, 1);
		this.getEntityData().define(GLOVES_TYPE, 0);
		this.getEntityData().define(GLOVES_COLOR, 1);
		this.getEntityData().define(BAUBLE_TYPE_1, 0);
		this.getEntityData().define(BAUBLE_TYPE_2, 0);
		this.getEntityData().define(BAUBLE_TYPE_3, 0);
		this.getEntityData().define(SHOES_TYPE, 1);
		this.getEntityData().define(SHOES_COLOR, 1);
		this.getEntityData().define(HAT_TYPE, 0);
		this.getEntityData().define(HAT_COLOR, 1);
		this.getEntityData().define(OVERCOAT_TYPE, 0);
		this.getEntityData().define(OVERCOAT_COLOR, 1);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (DATA_SKIN_A_ID.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		if (DATA_SKIN_B_ID.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		if (DATA_SKIN_C_ID.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		if (GIANT.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		if (IS_FEMALE.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}
	@Override
	public void refreshDimensions() {
		double d = this.getX();
		double d2 = this.getY();
		double d3 = this.getZ();
		super.refreshDimensions();
		this.setPos(d, d2, d3);
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (itemStack.is(Items.GOLDEN_APPLE)) {
			if (this.hasEffect(MobEffects.WEAKNESS)) {
				if (!player.getAbilities().instabuild) {
					itemStack.shrink(1);
				}
				if (!this.level().isClientSide) {
					int n = this.random.nextInt(2401) + 3600;
					this.zombieConversionTime = n;
					this.getEntityData().set(DATA_CONVERTING_ID, true);
					this.removeEffect(MobEffects.WEAKNESS);
					this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, n, Math.min(this.level().getDifficulty().getId() - 1, 0)));
					this.level().broadcastEntityEvent(this, (byte)16);
				}
				return InteractionResult.SUCCESS;
			}
			return InteractionResult.CONSUME;
		}
		return super.mobInteract(player, interactionHand);
	}


	@Override
	public void aiStep() {
		super.aiStep();
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
	}

	@Override
	public void tick() {
		if (!this.level().isClientSide && this.isAlive() && this.isConverting()) {
			int n = this.getConversionProgress();
			this.zombieConversionTime -= n;
			if (this.zombieConversionTime <= 0) {
				this.finishConversion((ServerLevel)this.level());
			}
		}
		super.tick();
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
		if (this.getSpawnType() != MobSpawnType.CONVERSION) {
			if (newSpawn == 0) {
				float f = 0.3f;
				if (JerotesVillageLevelData.get(this.level()) != null && JerotesVillageLevelData.get(this.level()).WickedRidiculeNight) {
					f = 0.8f;
				}
				float random = this.random.nextFloat();
				if (random <= f) {
					if (this.isGiant()) {
						this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(JerotesVillageItems.WILDERNESS_STICK.get()));
					} else {
						this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
					}
				}
				if (this.random.nextFloat() <= f) {
					if (this.isGiant()) {
						this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.WILDERNESS_GIANT_HELMET.get()));
					} else {
						this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
					}
				}
				if (this.random.nextFloat() <= f) {
					if (this.isGiant()) {
						this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.WILDERNESS_GIANT_CHESTPLATE.get()));
					} else {
						this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));
					}
				}
				if (this.random.nextFloat() <= f) {
					if (this.isGiant()) {
						this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.WILDERNESS_GIANT_LEGGINGS.get()));
					} else {
						this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));
					}
				}
				if (this.random.nextFloat() <= f) {
					if (this.isGiant()) {
						this.setItemSlot(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.WILDERNESS_GIANT_BOOTS.get()));
					} else {
						this.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.LEATHER_BOOTS));
					}
				}
			}
			else {
				super.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
			}
		}
		else {
			super.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
		}
	}

	@Override
	protected void hurtArmor(DamageSource damageSource, float damage) {
		if (JerotesGameRules.JEROTES_ARMOR_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_ARMOR_CAN_BREAK)) {
			if (damage >= 0.0F) {
				damage = damage / 4.0F;
				if (damage < 1.0F) {
					damage = 1.0F;
				}
				ItemStack head = this.getItemBySlot(EquipmentSlot.HEAD);
				ItemStack chest = this.getItemBySlot(EquipmentSlot.CHEST);
				ItemStack legs = this.getItemBySlot(EquipmentSlot.LEGS);
				ItemStack feet = this.getItemBySlot(EquipmentSlot.FEET);
				if ((!damageSource.is(DamageTypes.ON_FIRE) || !head.getItem().isFireResistant() && !damageSource.is(DamageTypeTags.BYPASSES_ARMOR))) {
					if (head.getItem() instanceof ArmorItem) {
						head.hurtAndBreak((int) damage, this, player -> player.broadcastBreakEvent(EquipmentSlot.HEAD));
					}
					if (chest.getItem() instanceof ArmorItem) {
						chest.hurtAndBreak((int) damage, this, player -> player.broadcastBreakEvent(EquipmentSlot.CHEST));
					}
					if (legs.getItem() instanceof ArmorItem) {
						legs.hurtAndBreak((int) damage, this, player -> player.broadcastBreakEvent(EquipmentSlot.LEGS));
					}
					if (feet.getItem() instanceof ArmorItem) {
						feet.hurtAndBreak((int) damage, this, player -> player.broadcastBreakEvent(EquipmentSlot.FEET));
					}
				}
			}
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		boolean bl = super.doHurtTarget(entity);
		if (bl) {
			if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
				ItemStack hand = this.getMainHandItem();
				hand.hurtAndBreak(1, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
			}
		}
		return bl;
	}

	@Override
	public boolean isAlliedTo(Entity entity) {
		if (entity == null) {
			return false;
		}
		if (entity == this) {
			return true;
		}
		if (super.isAlliedTo(entity)) {
			return true;
		}
		if (entity instanceof LivingEntity livingEntity && EntityFactionFind.isFaction(this, livingEntity)) {
			return this.getTeam() == null && entity.getTeam() == null;
		}
		return false;
	}

	@Override
	public boolean canAttack(LivingEntity livingEntity) {
		if (((this.getTeam() == null && livingEntity.getTeam() == null) || this.getTeam() == livingEntity.getTeam()) &&
				EntityFactionFind.isFaction(this, livingEntity)
		) {
			return false;
		}
		return super.canAttack(livingEntity);
	}

	@Override
	public void handleEntityEvent(byte by) {
		if (by == 16) {
			if (!this.isSilent()) {
				this.level().playLocalSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ZOMBIE_VILLAGER_CURE, this.getSoundSource(), 1.0f + this.random.nextFloat(), this.random.nextFloat() * 0.7f + 0.3f, false);
			}
			return;
		}
		else {
			super.handleEntityEvent(by);
		}
	}

	@Override
	protected ItemStack getSkull() {
		return new ItemStack(JerotesVillageItems.SECOND_ROUNDER_ZOMBIE_HEAD.get());
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		spawnGroupData = new ZombieGroupData(false, false);
		super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
		this.setCanBreakDoors(true);
		this.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(-1);
		RandomSource randomSource = serverLevelAccessor.getRandom();
		float f = difficultyInstance.getSpecialMultiplier();
		if (mobSpawnType != MobSpawnType.CONVERSION) {
			if (newSpawn == 0) {
				RandomSource randomSourceGiant = serverLevelAccessor.getRandom();
				if (randomSourceGiant.nextFloat() < 0.05f) {
					this.setGiant(true);
				}

				this.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
				this.setCanBreakDoors(this.supportsBreakDoorGoal() && randomSource.nextFloat() < f * 0.1f);

				if (serverLevelAccessor.getRandom().nextFloat() < 0.5f) {
					this.setFemale(true);
				}
				this.setSkinType(this.MaxSkinType());
				this.setSkinColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxSkinColor()));
				if (serverLevelAccessor.getRandom().nextFloat() < 0.03f) {
					this.setEyeType(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxEyeType()));
				}
				else {
					this.setEyeType(1);
				}
				this.setEyeColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxEyeColor()));
				if (serverLevelAccessor.getRandom().nextFloat() < 0.2f) {
					this.setAddType_1(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxAddType()));
					if (serverLevelAccessor.getRandom().nextFloat() < 0.3f) {
						this.setAddType_2(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxAddType()));
						if (this.AddType_1() == this.AddType_2()) {
							this.setAddType_2(0);
						}
						if (serverLevelAccessor.getRandom().nextFloat() < 0.4f) {
							this.setAddType_3(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxAddType()));
							if (this.AddType_2() == this.AddType_3()) {
								this.setAddType_3(0);
							}
						}
					}
				}
				this.setHairType(Main.randomReach(serverLevelAccessor.getRandom(), 0, this.MaxHairType()));
				this.setHairColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxHairColor()));
				this.setJacketType(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxJacketType()));
				this.setJacketColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxJacketColor()));
				this.setPantsType(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxPantsType()));
				this.setPantsColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxPantsColor()));
				this.setGlovesType(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxGlovesType()));
				this.setGlovesColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxGlovesColor()));
				if (serverLevelAccessor.getRandom().nextFloat() < 0.2f) {
					this.setBaubleType_1(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxBaubleType()));
					if (serverLevelAccessor.getRandom().nextFloat() < 0.3f) {
						this.setBaubleType_2(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxBaubleType()));
						if (this.BaubleType_1() == this.BaubleType_2()) {
							this.setBaubleType_2(0);
						}
						if (serverLevelAccessor.getRandom().nextFloat() < 0.4f) {
							this.setBaubleType_3(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxBaubleType()));
							if (this.BaubleType_2() == this.BaubleType_3()) {
								this.setBaubleType_3(0);
							}
						}
					}
				}
				this.setShoesType(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxShoesType()));
				this.setShoesColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxShoesColor()));
				if (serverLevelAccessor.getRandom().nextFloat() < 0.2f) {
					this.setHatType(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxHairType()));
				}
				this.setHatColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxHairColor()));
				this.setOvercoatType(0);
				this.setOvercoatColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, this.MaxOvercoatColor()));
				this.setBaby(false);
				this.populateDefaultEquipmentEnchantments(randomSource, difficultyInstance);
				newSpawn = 1;
			}
		}
		else {
			this.setCanPickUpLoot(false);
		}
		return spawnGroupData;
	}
	@Override
	protected void onOffspringSpawnedFromEgg(Player player, Mob child) {
		super.onOffspringSpawnedFromEgg(player, child);
		if (child instanceof SecondRounderZombieEntity secondRounderZombie && secondRounderZombie.level() instanceof ServerLevel serverLevelAccessor) {
			secondRounderZombie.setGiant(secondRounderZombie.isGiant());
			if (serverLevelAccessor.getRandom().nextFloat() < 0.5f) {
				secondRounderZombie.setFemale(true);
			}
			secondRounderZombie.setSkinType(secondRounderZombie.MaxSkinType());
			secondRounderZombie.setSkinColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, secondRounderZombie.MaxSkinColor()));
			if (serverLevelAccessor.getRandom().nextFloat() < 0.03f) {
				secondRounderZombie.setEyeType(Main.randomReach(serverLevelAccessor.getRandom(), 1, secondRounderZombie.MaxEyeType()));
			}
			else {
				secondRounderZombie.setEyeType(1);
			}
			secondRounderZombie.setEyeColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, secondRounderZombie.MaxEyeColor()));
			if (serverLevelAccessor.getRandom().nextFloat() < 0.2f) {
				secondRounderZombie.setAddType_1(Main.randomReach(serverLevelAccessor.getRandom(), 1, secondRounderZombie.MaxAddType()));
				if (serverLevelAccessor.getRandom().nextFloat() < 0.3f) {
					secondRounderZombie.setAddType_2(Main.randomReach(serverLevelAccessor.getRandom(), 1, secondRounderZombie.MaxAddType()));
					if (secondRounderZombie.AddType_1() == secondRounderZombie.AddType_2()) {
						secondRounderZombie.setAddType_2(0);
					}
					if (serverLevelAccessor.getRandom().nextFloat() < 0.4f) {
						secondRounderZombie.setAddType_3(Main.randomReach(serverLevelAccessor.getRandom(), 1, secondRounderZombie.MaxAddType()));
						if (secondRounderZombie.AddType_2() == secondRounderZombie.AddType_3()) {
							secondRounderZombie.setAddType_3(0);
						}
					}
				}
			}
			secondRounderZombie.setHairType(Main.randomReach(serverLevelAccessor.getRandom(), 0, secondRounderZombie.MaxHairType()));
			secondRounderZombie.setHairColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, secondRounderZombie.MaxHairColor()));
			secondRounderZombie.setJacketType(Main.randomReach(serverLevelAccessor.getRandom(), 1, secondRounderZombie.MaxJacketType()));
			secondRounderZombie.setJacketColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, secondRounderZombie.MaxJacketColor()));
			secondRounderZombie.setPantsType(Main.randomReach(serverLevelAccessor.getRandom(), 1, secondRounderZombie.MaxPantsType()));
			secondRounderZombie.setPantsColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, secondRounderZombie.MaxPantsColor()));
			secondRounderZombie.setGlovesType(Main.randomReach(serverLevelAccessor.getRandom(), 1, secondRounderZombie.MaxGlovesType()));
			secondRounderZombie.setGlovesColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, secondRounderZombie.MaxGlovesColor()));
			if (serverLevelAccessor.getRandom().nextFloat() < 0.2f) {
				secondRounderZombie.setBaubleType_1(Main.randomReach(serverLevelAccessor.getRandom(), 1, secondRounderZombie.MaxBaubleType()));
				if (serverLevelAccessor.getRandom().nextFloat() < 0.3f) {
					secondRounderZombie.setBaubleType_2(Main.randomReach(serverLevelAccessor.getRandom(), 1, secondRounderZombie.MaxBaubleType()));
					if (secondRounderZombie.BaubleType_1() == secondRounderZombie.BaubleType_2()) {
						secondRounderZombie.setBaubleType_2(0);
					}
					if (serverLevelAccessor.getRandom().nextFloat() < 0.4f) {
						secondRounderZombie.setBaubleType_3(Main.randomReach(serverLevelAccessor.getRandom(), 1, secondRounderZombie.MaxBaubleType()));
						if (secondRounderZombie.BaubleType_2() == secondRounderZombie.BaubleType_3()) {
							secondRounderZombie.setBaubleType_3(0);
						}
					}
				}
			}
			secondRounderZombie.setShoesType(Main.randomReach(serverLevelAccessor.getRandom(), 1, secondRounderZombie.MaxShoesType()));
			secondRounderZombie.setShoesColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, secondRounderZombie.MaxShoesColor()));
			if (serverLevelAccessor.getRandom().nextFloat() < 0.2f) {
				secondRounderZombie.setHatType(Main.randomReach(serverLevelAccessor.getRandom(), 1, secondRounderZombie.MaxHairType()));
			}
			secondRounderZombie.setHatColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, secondRounderZombie.MaxHairColor()));
			secondRounderZombie.setOvercoatType(0);
			secondRounderZombie.setOvercoatColor(Main.randomReach(serverLevelAccessor.getRandom(), 1, secondRounderZombie.MaxOvercoatColor()));
		}
	}

	public static class ZombieGroupData implements SpawnGroupData {
		public final boolean isBaby;
		public final boolean canSpawnJockey;

		public ZombieGroupData(boolean bl, boolean bl2) {
			this.isBaby = bl;
			this.canSpawnJockey = bl2;
		}
	}
}