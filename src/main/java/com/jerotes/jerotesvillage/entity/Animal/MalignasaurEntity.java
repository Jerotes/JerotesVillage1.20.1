package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.control.GiantBodyRotationControl;
import com.jerotes.jerotes.control.GiantLookControl;
import com.jerotes.jerotes.entity.Interface.ArmorEntity;
import com.jerotes.jerotes.entity.Interface.ControlVehicleEntity;
import com.jerotes.jerotes.entity.Interface.StopLook;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.goal.JerotesTryFindWaterGoal;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.control.GemstoneMalignasaurMoveControl;
import com.jerotes.jerotesvillage.entity.Boss.Biome.GemstoneMalignasaurEntity;
import com.jerotes.jerotesvillage.entity.Part.MalignasaurPart;
import com.jerotes.jerotesvillage.goal.WaterSitWhenOrderedToGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.navigation.GemstoneMalignasaurPathNavigation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SaddleItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

public class MalignasaurEntity extends BaseTamableAnimalEntity implements NeutralMob, ControlVehicleEntity, StopLook, Saddleable, ArmorEntity, ContainerListener, HasCustomInventoryScreen {
	public AnimationState landAnimationState = new AnimationState();
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	public AnimationState attack3AnimationState = new AnimationState();
	public AnimationState headAttack1AnimationState = new AnimationState();
	public AnimationState headAttack2AnimationState = new AnimationState();
	public AnimationState headAttack3AnimationState = new AnimationState();
	public AnimationState tailAttack1AnimationState = new AnimationState();
	public AnimationState tailAttack2AnimationState = new AnimationState();
	public AnimationState tailAttack3AnimationState = new AnimationState();
	public AnimationState breathAnimationState = new AnimationState();
	public AnimationState whirlpoolAnimationState = new AnimationState();
	public AnimationState jumpAnimationState = new AnimationState();
	public AnimationState armorAnimationState = new AnimationState();
	public AnimationState controlAnimationState = new AnimationState();
	public AnimationState deterAnimationState = new AnimationState();
	public AnimationState bombAnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();
	public AnimationState sitAnimationState = new AnimationState();
	public AnimationState toSitAnimationState = new AnimationState();
	public AnimationState stopSitAnimationState = new AnimationState();
	public static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(MalignasaurEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(MalignasaurEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(MalignasaurEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_USE = SynchedEntityData.defineId(MalignasaurEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> JUMP_TICK = SynchedEntityData.defineId(MalignasaurEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> JUMP_USE_TICK = SynchedEntityData.defineId(MalignasaurEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> JUMP_SOUND = SynchedEntityData.defineId(MalignasaurEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> MANUALLY_CONTROL_COMBAT = SynchedEntityData.defineId(MalignasaurEntity.class, EntityDataSerializers.BOOLEAN);
	private static final Ingredient FOOD_ITEMS = Ingredient.of(
			ItemTags.create(new ResourceLocation(JerotesWarehouse.MODID, "animal_foods/meat_and_fish_foods"))
	);
	private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(MalignasaurEntity.class, EntityDataSerializers.BYTE);
	protected SimpleContainer inventory;
	private LazyOptional<?> itemHandler = null;
	public final MalignasaurPart head1;
	//public final MalignasaurPart head2;
	public final MalignasaurPart tail1;
	//public final MalignasaurPart tail2;
	//public final MalignasaurPart tail3;
	public final MalignasaurPart[] allParts;
	public SimpleContainer inventory() {
		return inventory;
	}

	public MalignasaurEntity(EntityType<? extends MalignasaurEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(3.1f);
		this.setPathfindingMalus(BlockPathTypes.LEAVES, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.FENCE, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.BLOCKED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_IRON_CLOSED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
		this.moveControl = new GemstoneMalignasaurMoveControl(this, 85, 85, 0.02f, 0.15f, false);
		this.lookControl = new GiantLookControl(this, 1);
		this.createInventory();
		head1 = new MalignasaurPart(this, "head1", 3.5f * 3/4f, 3.5f * 3/4f);
		//head2 = new MalignasaurPart(this, "head2", 3f * 3/4f, 3f * 3/4f);
		tail1 = new MalignasaurPart(this, "tail1", 4 * 3/4f, 4f * 3/4f);
		//tail2 = new MalignasaurPart(this, "tail2", 3.5f * 3/4f, 3.5f * 3/4f);
		//tail3 = new MalignasaurPart(this, "tail3", 3f * 3/4f, 3f * 3/4f);
		allParts = new MalignasaurPart[]{head1,
				//head2,
				tail1,
				//tail2,
				//tail3
				};
    }

	@Override
	public void remove(@NotNull RemovalReason reason) {
		super.remove(reason);
		if (allParts != null) {
			for (MalignasaurPart part : allParts) {
				part.remove(RemovalReason.KILLED);
			}
		}
	}

	@Override
	public boolean isWearingArmor() {
		return false;
	}
	@Override
	public boolean isWarBeastArmor() {
		return false;
	}
	@Override
	public boolean isGiantBeastArmor() {
		return false;
	}
	public int getInventorySize() {
		return 2;
	}

	protected void createInventory() {
		SimpleContainer simplecontainer = this.inventory;
		this.inventory = new SimpleContainer(this.getInventorySize());
		if (simplecontainer != null) {
			simplecontainer.removeListener(this);
			int i = Math.min(simplecontainer.getContainerSize(), this.inventory.getContainerSize());

			for(int j = 0; j < i; ++j) {
				ItemStack itemstack = simplecontainer.getItem(j);
				if (!itemstack.isEmpty()) {
					this.inventory.setItem(j, itemstack.copy());
				}
			}
		}

		this.inventory.addListener(this);
		this.updateContainerEquipment();
		this.itemHandler = LazyOptional.of(() -> {
			return new InvWrapper(this.inventory);
		});
	}

	protected void updateContainerEquipment() {
		if (this.level().isClientSide()) {
			return;
		}
		if (!this.level().isClientSide) {
			this.setFlag(4, !this.inventory.getItem(0).isEmpty());
		}
		this.setDropChance(EquipmentSlot.CHEST, 0.0f);
	}
	public void containerChanged(Container p_30548_) {
		boolean flag = this.isSaddled();
		this.updateContainerEquipment();
		if (this.tickCount > 20 && !flag && this.isSaddled()) {
			this.playSound(this.getSaddleSoundEvent(), 0.5F, 1.0F);
		}
	}
	public SoundEvent getSaddleSoundEvent() {
		return JerotesSoundEvents.USE_SADDLE;
	}
	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
		return capability == ForgeCapabilities.ITEM_HANDLER && this.itemHandler != null && this.isAlive() ? this.itemHandler.cast() : super.getCapability(capability, facing);
	}
	public void invalidateCaps() {
		super.invalidateCaps();
		if (this.itemHandler != null) {
			LazyOptional<?> oldHandler = this.itemHandler;
			this.itemHandler = null;
			oldHandler.invalidate();
		}
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new WaterSitWhenOrderedToGoal(this));
		this.goalSelector.addGoal(0, new JerotesTryFindWaterGoal(this));
		this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(this, 1.4, true));
		this.goalSelector.addGoal(4, new RandomSwimmingGoal(this,1.0,32));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<MalignasaurEntity>(this, false));
	}
	
	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MAX_HEALTH, 310);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 15);
		builder = builder.add(Attributes.ARMOR, 2);
		builder = builder.add(Attributes.MOVEMENT_SPEED, 1.20);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 2.5);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0);

		builder = builder.add(Attributes.FOLLOW_RANGE, 128);
		return builder;
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		if (this.isBaby())
			return super.getAmbientSound();
		return JerotesVillageSoundEvents.MALIGNASAUR_AMBIENT;
	}
	@Override
	public int getAmbientSoundInterval() {
		return 600;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.MALIGNASAUR_DEATH;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.MALIGNASAUR_HURT;
	}
	@Override
	protected SoundEvent getSwimSound() {
		return JerotesVillageSoundEvents.MALIGNASAUR_SWIM;
	}
	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		this.playSound(JerotesVillageSoundEvents.MALIGNASAUR_WALK, 0.15f, 1.0f);
	}
	@Override
	public MobType getMobType() {
		return MobType.WATER;
	}
	@Override
	public int getHeadRotSpeed() {
		return 5;
	}
	@Override
	public int getMaxHeadYRot() {
		return 15;
	}
	@Override
	public int getMaxHeadXRot() {
		return 15;
	}
	@Override
	public void setTame(boolean bl) {
		super.setTame(bl);
		if (!(this instanceof GemstoneMalignasaurEntity)) {
			if (bl) {
				this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(320.0);
				this.setHealth(300f);
				this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(16.0);
			} else {
				this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(310.0);
				this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(15.0);
			}
		}
	}
	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get())
			return false;
		return super.canDrownInFluidType(type);
	}
	@Override
	protected @NotNull BodyRotationControl createBodyControl() {
		return new GiantBodyRotationControl(this);
	}
	@Override
	protected PathNavigation createNavigation(Level level) {
		return new GemstoneMalignasaurPathNavigation(this, level);
	}
	@Override
	public void setBaby(boolean bl) {
		this.setAge(bl ? -288000 : 0);
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		float height = 1.35f;
		if (this.isBaby()) {
			return height / 2;
		}
		return height;
	}
	@Override
	public boolean isPushable() {
		return false;
	}
	@Override
	public boolean isPushedByFluid() {
		return false;
	}
	@Override
	public boolean canBeLeashed(Player player) {
		return this.isTame() && this.isBaby() && super.canBeLeashed(player);
	}
	@Nullable
	@Override
	public MalignasaurEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		UUID uUID;
		MalignasaurEntity malialosaurEntity = JerotesVillageEntityType.MALIGNASAUR.get().create(serverLevel);
		if (malialosaurEntity != null && (uUID = this.getOwnerUUID()) != null) {
			malialosaurEntity.setOwnerUUID(uUID);
			malialosaurEntity.setTame(true);
		}
		return malialosaurEntity;
	}
	@Override
	public boolean isFood(ItemStack itemStack) {
		return FOOD_ITEMS.test(itemStack);
	}
	@Override
	public boolean checkSpawnObstruction(LevelReader levelReader) {
		return levelReader.isUnobstructed(this);
	}
	protected int getMaxPassengers() {
		if (!this.isSaddled()) {
			return 1;
		}
		return 2;
	}
	@Override
	protected boolean canAddPassenger(Entity entity) {
		return this.getPassengers().size() < this.getMaxPassengers();
	}
	@Override
	public boolean stopLookTime() {
		return specialAction() || this.getAttackTick() > 0 && this.getAttackTick() < 15;
	}
	@Override
	public boolean isManuallyControlCombatJerotes() {
		return this.getEntityData().get(MANUALLY_CONTROL_COMBAT);
	}
	public boolean isTrueManuallyControlCombatJerotes() {
		return this.getControllingPassenger() instanceof Player player && canBeControlJerotes(player) && isManuallyControlCombatJerotes();
	}
	@Override
	public void setManuallyControlCombatJerotes(boolean bl) {
		this.getEntityData().set(MANUALLY_CONTROL_COMBAT, bl);
		if (this.getControllingPassenger() instanceof Player player) {
			if (bl) {
				player.displayClientMessage(Component.translatable("message.jerotes.change_control_combat_type_0"), true);
			}
			else {
				player.displayClientMessage(Component.translatable("message.jerotes.change_control_combat_type_1"), true);
			}
		}
	}
	@Override
	public float getManuallyControlCombatCameraChangeJerotes() {
		return 2.0f;
	}
	@Override
	public boolean canBeControlJerotes(Player player) {
		return this.isSaddled() && !this.isBaby() && this.getOwner() != null && (player == this.getOwner() || AttackFind.FindCanNotAttack(this.getOwner(), player));
	}
	@Override
	protected void updateControlFlags() {
		boolean flag = !(this.getControllingPassenger() instanceof Mob);
		boolean flag1 = !(this.getVehicle() instanceof Boat);
		boolean controlStopTarget = isTrueManuallyControlCombatJerotes();
		this.goalSelector.setControlFlag(Goal.Flag.MOVE, flag);
		this.goalSelector.setControlFlag(Goal.Flag.JUMP, flag && flag1);
		this.goalSelector.setControlFlag(Goal.Flag.LOOK, flag);
		this.goalSelector.setControlFlag(Goal.Flag.TARGET, !controlStopTarget);
	}
	@Override
	public void pressMainJerotes(Player player) {
		doHurtTargetHead(this);
	}
	@Override
	public void pressAddJerotes(Player player) {
		doHurtTargetTail(this);
	}
	@Override
	public boolean canPressMainJerotes() {
		int cooldown = this instanceof GemstoneMalignasaurEntity ? (int) (OtherMainConfig.GemstoneMalignasaurMeleeAttackCooldown * 20) : 5;
		return this.getAttackTick() >= -cooldown;
	}
	@Override
	public boolean canPressAddJerotes() {
		int cooldown = this instanceof GemstoneMalignasaurEntity ? (int) (OtherMainConfig.GemstoneMalignasaurMeleeAttackCooldown * 20) : 5;
		return this.getAttackTick() >= -cooldown;
	}

	public void setAttackTick(int n){
		this.getEntityData().set(ATTACK_TICK, n);
	}
	public int getAttackTick(){
		return this.getEntityData().get(ATTACK_TICK);
	}
	public int getAttackUse() {
		return this.getEntityData().get(ATTACK_USE);
	}
	public void setAttackUse(int n) {
		this.getEntityData().set(ATTACK_USE, n);
	}
	public void setJumpTick(int n){
		this.getEntityData().set(JUMP_TICK, n);
	}
	public int getJumpTick(){
		return this.getEntityData().get(JUMP_TICK);
	}
	public void setJumpUseTick(int n){
		this.getEntityData().set(JUMP_USE_TICK, n);
	}
	public int getJumpUseTick(){
		return this.getEntityData().get(JUMP_USE_TICK);
	}
	public void setJumpSound(boolean bl){
		this.getEntityData().set(JUMP_SOUND, bl);
	}
	public boolean isJumpSound(){
		return this.getEntityData().get(JUMP_SOUND);
	}
	public boolean specialAction() {
		return this.getJumpUseTick() > 0;
	}


	public double blockDestroyTick;
	private int sitTick = 0;
	protected void setFlag(int n, boolean n2) {
		byte b0 = (Byte)this.entityData.get(DATA_ID_FLAGS);
		if (n2) {
			this.entityData.set(DATA_ID_FLAGS, (byte)(b0 | n));
		} else {
			this.entityData.set(DATA_ID_FLAGS, (byte)(b0 & ~n));
		}
	}
	protected boolean getFlag(int n) {
		return ((Byte)this.entityData.get(DATA_ID_FLAGS) & n) != 0;
	}
	public ItemStack getArmor() {
		return this.getItemBySlot(EquipmentSlot.CHEST);
	}
	public boolean isArmor(ItemStack itemStack) {
		return false;
	}
	//动画
	public void setAnimTick(int n){
		this.getEntityData().set(ANIM_TICK, n);
	}
	public int getAnimTick(){
		return this.getEntityData().get(ANIM_TICK);
	}
	public void setAnimationState(String input) {
		this.setAnimationState(this.getAnimationState(input));
	}
	public void setAnimationState(int id) {
		this.entityData.set(ANIM_STATE, id);
	}

	public int getAnimationState(String animation) {
		if (Objects.equals(animation, "attack1")){
			return 1;
		}
		else if (Objects.equals(animation, "attack2")){
			return 2;
		}
		else if (Objects.equals(animation, "attack3")){
			return 3;
		}
		else if (Objects.equals(animation, "headAttack1")){
			return 4;
		}
		else if (Objects.equals(animation, "headAttack2")){
			return 5;
		}
		else if (Objects.equals(animation, "headAttack3")){
			return 6;
		}
		else if (Objects.equals(animation, "tailAttack1")){
			return 7;
		}
		else if (Objects.equals(animation, "tailAttack2")){
			return 8;
		}
		else if (Objects.equals(animation, "tailAttack3")){
			return 9;
		}
		else if (Objects.equals(animation, "breath")){
			return 10;
		}
		else if (Objects.equals(animation, "whirlpool")){
			return 11;
		}
		else if (Objects.equals(animation, "jump")){
			return 12;
		}
		else if (Objects.equals(animation, "armor")){
			return 13;
		}
		else if (Objects.equals(animation, "control")){
			return 14;
		}
		else if (Objects.equals(animation, "deter")){
			return 15;
		}
		else if (Objects.equals(animation, "bomb")){
			return 16;
		}
		else if (Objects.equals(animation, "dead")){
			return 17;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.attack1AnimationState);
		list.add(this.attack2AnimationState);
		list.add(this.attack3AnimationState);
		list.add(this.headAttack1AnimationState);
		list.add(this.headAttack2AnimationState);
		list.add(this.headAttack3AnimationState);
		list.add(this.tailAttack1AnimationState);
		list.add(this.tailAttack2AnimationState);
		list.add(this.tailAttack3AnimationState);
		list.add(this.breathAnimationState);
		list.add(this.whirlpoolAnimationState);
		list.add(this.jumpAnimationState);
		list.add(this.armorAnimationState);
		list.add(this.controlAnimationState);
		list.add(this.deterAnimationState);
		list.add(this.bombAnimationState);
		list.add(this.deadAnimationState);
		return list;
	}
	public void stopMostAnimation(AnimationState exception){
		for (AnimationState state : this.getAllAnimations()){
			if (state != exception && state != jumpAnimationState && state != armorAnimationState && state != controlAnimationState) {
				state.stop();
			}
		}
	}
	public void stopAllAnimation(){
		for (AnimationState state : this.getAllAnimations()){
			if (state != jumpAnimationState && state != armorAnimationState && state != controlAnimationState) {
				state.stop();
			}
		}
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("AttackTick", this.getAttackTick());
		compoundTag.putInt("AttackUse", this.getAttackUse());
		compoundTag.putDouble("BlockDestroyTick", this.blockDestroyTick);
		compoundTag.putInt("JumpTick", this.getJumpTick());
		compoundTag.putInt("JumpUseTick", this.getJumpUseTick());
		compoundTag.putBoolean("JumpSound", this.isJumpSound());
		compoundTag.putInt("SitTick", this.sitTick);
		if (!this.inventory.getItem(0).isEmpty()) {
			compoundTag.put("SaddleItem", this.inventory.getItem(0).save(new CompoundTag()));
		}
		if (!this.inventory.getItem(1).isEmpty()) {
			compoundTag.put("ArmorItem", this.inventory.getItem(1).save(new CompoundTag()));
		}
		compoundTag.putBoolean("IsManuallyControlCombatJerotes", this.isManuallyControlCombatJerotes());
		this.addPersistentAngerSaveData(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		ItemStack itemStack;
		super.readAdditionalSaveData(compoundTag);
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.setAttackTick(compoundTag.getInt("AttackTick"));
		this.setAttackUse(compoundTag.getInt("AttackUse"));
		this.blockDestroyTick = compoundTag.getDouble("BlockDestroyTick");
		this.setJumpTick(compoundTag.getInt("JumpTick"));
		this.setJumpUseTick(compoundTag.getInt("JumpUseTick"));
		this.setJumpSound(compoundTag.getBoolean("JumpSound"));
		this.sitTick = compoundTag.getInt("SitTick");
		if (compoundTag.contains("SaddleItem", 10)) {
			ItemStack itemstack = ItemStack.of(compoundTag.getCompound("SaddleItem"));
			if (itemstack.getItem() instanceof SaddleItem) {
				this.inventory.setItem(0, itemstack);
			}
		}
		if (compoundTag.contains("ArmorItem", 10) && !(itemStack = ItemStack.of(compoundTag.getCompound("ArmorItem"))).isEmpty() && this.isArmor(itemStack)) {
			this.inventory.setItem(1, itemStack);
		}
		this.setManuallyControlCombatJerotes(compoundTag.getBoolean("IsManuallyControlCombatJerotes"));
		this.readPersistentAngerSaveData(this.level(), compoundTag);
		this.updateContainerEquipment();
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ATTACK_TICK, 0);
		this.getEntityData().define(ATTACK_USE, 0);
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(JUMP_TICK, 0);
		this.getEntityData().define(JUMP_USE_TICK, 0);
		this.getEntityData().define(JUMP_SOUND, false);
		this.getEntityData().define(DATA_ID_FLAGS, (byte)0);
		this.getEntityData().define(MANUALLY_CONTROL_COMBAT, false);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (ANIM_STATE.equals(entityDataAccessor)) {
			if (this.level().isClientSide()) {
				switch (this.entityData.get(ANIM_STATE)){
					case 0:
						this.stopAllAnimation();
						break;
					case 1:
						this.attack1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack1AnimationState);
						break;
					case 2:
						this.attack2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack2AnimationState);
						break;
					case 3:
						this.attack3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack3AnimationState);
						break;
					case 4:
						this.headAttack1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.headAttack1AnimationState);
						break;
					case 5:
						this.headAttack2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.headAttack2AnimationState);
						break;
					case 6:
						this.headAttack3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.headAttack3AnimationState);
						break;
					case 7:
						this.tailAttack1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.tailAttack1AnimationState);
						break;
					case 8:
						this.tailAttack2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.tailAttack2AnimationState);
						break;
					case 9:
						this.tailAttack3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.tailAttack3AnimationState);
						break;
					case 10:
						this.breathAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.breathAnimationState);
						break;
					case 11:
						this.whirlpoolAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.whirlpoolAnimationState);
						break;
					case 12:
						this.jumpAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.jumpAnimationState);
						break;
					case 13:
						this.armorAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.armorAnimationState);
						break;
					case 14:
						this.controlAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.controlAnimationState);
						break;
					case 15:
						this.deterAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.deterAnimationState);
						break;
					case 16:
						this.bombAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.bombAnimationState);
						break;
					case 17:
						this.deadAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.deadAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	public boolean isLandNavigatorType = false;
	@Override
	public boolean isMultipartEntity() {
		return true;
	}
	@Override
	public PartEntity<?>[] getParts() {
		return allParts;
	}
	public MalignasaurPart[] getSubEntities() {
		return this.allParts;
	}
	@Override
	public void recreateFromPacket(ClientboundAddEntityPacket clientboundAddEntityPacket) {
		super.recreateFromPacket(clientboundAddEntityPacket);
		MalignasaurPart[] malignasaurParts = this.getSubEntities();
		for (int i = 0; i < malignasaurParts.length; ++i) {
			malignasaurParts[i].setId(i + 1 + clientboundAddEntityPacket.getId());
		}
	}
	private void tickMultipart() {
		Vec3[] avector3d = new Vec3[this.allParts.length];
		for (int j = 0; j < this.allParts.length; ++j) {
			avector3d[j] = new Vec3(this.allParts[j].getX(), this.allParts[j].getY(), this.allParts[j].getZ());
		}
		Vec3 center = this.position().add(0, this.getBbHeight() * 0.5F, 0);
		float scale = 1f;
		if (this instanceof GemstoneMalignasaurEntity) {
			scale = 4f/3f;
		}
		if (this.isBaby()) {
			scale *= 0.5f;
		}
		this.head1.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, 7F * 3/4f * scale),
				(this.getXRot() / 2) * (this.getWaterAnim() / 40f), this.yBodyRot).add(center));
//		this.head2.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, 3.5F * 3/4f * scale),
//				(this.getXRot() / 2) * (this.getWaterAnim() / 40f), this.yBodyRot).add(this.head1.centeredPosition()));
		this.tail1.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -9F * 3/4f * scale),
				(this.getXRot() / 2) * (this.getWaterAnim() / 40f), this.yBodyRot).add(center));
//		this.tail2.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -4F * 3/4f * scale),
//				(this.getXRot() / 2) * (this.getWaterAnim() / 40f), this.yBodyRot).add(this.tail1.centeredPosition()));
//		this.tail3.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -4F * 3/4f * scale),
//				(this.getXRot() / 2) * (this.getWaterAnim() / 40f), this.yBodyRot).add(this.tail2.centeredPosition()));
		for (int l = 0; l < this.allParts.length; ++l) {
			this.allParts[l].xo = avector3d[l].x;
			this.allParts[l].yo = avector3d[l].y;
			this.allParts[l].zo = avector3d[l].z;
			this.allParts[l].xOld = avector3d[l].x;
			this.allParts[l].yOld = avector3d[l].y;
			this.allParts[l].zOld = avector3d[l].z;
		}
	}
	private Vec3 rotateOffsetVec(Vec3 offset, float xRot, float yRot) {
		return offset.xRot(-xRot * ((float) Math.PI / 180F)).yRot(-yRot * ((float) Math.PI / 180F));
	}

	@Override
	public void tick() {
		tickMultipart();
		super.tick();
		if (this instanceof GemstoneMalignasaurEntity) {
			if (head1 != null && head1.scale != 0.5f * 4f / 3f && this.isBaby()) {
				if (allParts != null) {
					for (MalignasaurPart part : allParts) {
						part.scale = 0.5f * 4f / 3f;
						part.refreshDimensions();
					}
				}
			}

			if (head1 != null && head1.scale != 4f / 3f && !this.isBaby()) {
				if (allParts != null) {
					for (MalignasaurPart part : allParts) {
						part.scale = 4f / 3f;
						part.refreshDimensions();
					}
				}
			}
		}
		else {
			if (head1 != null && head1.scale != 0.5 && this.isBaby()) {
				if (allParts != null) {
					for (MalignasaurPart part : allParts) {
						part.scale = 0.5f;
						part.refreshDimensions();
					}
				}
			}
			if (head1 != null && head1.scale != 1 && !this.isBaby()) {
				if (allParts != null) {
					for (MalignasaurPart part : allParts) {
						part.scale = 1;
						part.refreshDimensions();
					}
				}
			}
		}
	}

	public void aiStep() {
		super.aiStep();
		//清除动画
		if (!this.level().isClientSide()) {
			this.setAnimTick(Math.max(-1, this.getAnimTick() - 1));
		}
		if (this.getAnimTick() == 0) {
			if (!this.level().isClientSide()) {
				this.setAnimationState(0);
			}
		}
		//停止部分动画
		if (this.getJumpUseTick() <= 0 && this.jumpAnimationState.isStarted()) {
			this.jumpAnimationState.stop();
		}
		//愤怒
		if (!this.level().isClientSide()) {
			this.updatePersistentAnger((ServerLevel) this.level(), true);
		}
		//注视
		if (this.specialAction() || this.getAttackTick() > 15){
			this.getNavigation().stop();
			if (this.getTarget() != null && !this.stopLookTime()) {
				this.getLookControl().setLookAt(this.getTarget(), 10.0f, 10.0f);
				this.lookAt(this.getTarget(), 10.0f, 10.0f);
			}
		}
		//水陆切换
		if (this.isInWater() && this.isLandNavigatorType) {
			this.navigation = new GemstoneMalignasaurPathNavigation(this, level());
			this.isLandNavigatorType = false;
		}
		if (!this.isInWater() && !this.isLandNavigatorType) {
			this.navigation = new AmphibiousPathNavigation(this, level());
			this.isLandNavigatorType = true;
		}
		//站立动画
		if (this.isAlive()) {
			this.landAnimationState.startIfStopped(this.tickCount);
		}
		//坐下
		if (!this.isInSittingPose()) {
			this.sitAnimationState.stop();
		}
		if (this.isInSittingPose() && sitTick <= 0){
			this.sitTick = 40;
		}
		if (!this.isInSittingPose() && sitTick > 0){
			this.stopSitAnimationState.start(this.tickCount);
			this.sitTick = 0;
			this.sitAnimationState.stop();
			this.toSitAnimationState.stop();
		}
		if (this.sitTick == 40){
			this.toSitAnimationState.start(this.tickCount);
		}
		if (this.sitTick == 30){
			this.toSitAnimationState.stop();
			this.sitAnimationState.start(this.tickCount);
		}
		if (this.sitTick > 5){
			this.sitTick -= 1;
		}
		//腾跃
		Main.WaterLeap(this, 0.25f);
		//近战攻击
		{
			int cooldown = this instanceof GemstoneMalignasaurEntity ? (int) (OtherMainConfig.GemstoneMalignasaurMeleeAttackCooldown * 20) : 5;
			if ((this.getAttackTick() == 10 && this.getAttackUse() != 3 || (this.getAttackTick() == 12 || this.getAttackTick() == 8) && this.getAttackUse() == 3) && this.isAlive()) {
				//头部攻击
				if (this.getAttackUse() == 1) {
					if (head1 != null) {
						MalignasaurPart[] headParts = new MalignasaurPart[]{head1};
						this.trueHurt(headParts);
					}
					else {
						MalignasaurPart[] headParts = new MalignasaurPart[]{};
						this.trueHurt(headParts);
					}
				}
				//跳跃攻击
				if (this.getAttackUse() == 4) {
					if (head1 != null) {
						MalignasaurPart[] headParts = new MalignasaurPart[]{head1};
						this.trueHurt(headParts);
					}
					else {
						MalignasaurPart[] headParts = new MalignasaurPart[]{};
						this.trueHurt(headParts);
					}
				}
				//尾部攻击
				if (this.getAttackUse() == 3) {
					if (tail1 != null) {
						MalignasaurPart[] tailParts = new MalignasaurPart[]{tail1};
						this.trueHurt(tailParts);
					}
					else {
						MalignasaurPart[] tailParts = new MalignasaurPart[]{};
						this.trueHurt(tailParts);
					}
				}
				//正常攻击
				if (this.getAttackUse() == 2) {
					MalignasaurPart[] headParts = new MalignasaurPart[]{};
					this.trueHurt(headParts);
				}
			}
			if (!this.level().isClientSide()) {
				this.setAttackTick(Math.max(-cooldown, this.getAttackTick() - 1));
			}
			//非主体攻击
			if (!this.isTrueManuallyControlCombatJerotes()) {
				if (this.getAttackTick() <= -cooldown && this.getTarget() != null && this.isAlive() && !this.specialAction()) {
					if (head1 != null && head1.getBoundingBox().inflate(0f).intersects(this.getTarget().getBoundingBox()) && Main.canSee(this.getTarget(), this)) {
						this.doHurtTargetHead(this.getTarget());
					}
//				else if (head2 != null && head2.getBoundingBox().inflate(0f).intersects(this.getTarget().getBoundingBox())) {
//					this.doHurtTargetHead(this.getTarget());
//				}
					else if (tail1 != null && tail1.getBoundingBox().inflate(0f).intersects(this.getTarget().getBoundingBox()) && !Main.canSee(this.getTarget(), this)) {
						this.doHurtTargetTail(this.getTarget());
					}
//				else if (tail2 != null && tail2.getBoundingBox().inflate(0f).intersects(this.getTarget().getBoundingBox())) {
//					this.doHurtTargetTail(this.getTarget());
//				}
//				else if (tail3 != null && tail3.getBoundingBox().inflate(0f).intersects(this.getTarget().getBoundingBox())) {
//					this.doHurtTargetTail(this.getTarget());
//				}
				}
			}
		}
//		//上升
//		{
//			if (this.isInWater() && this.getTarget() != null && this.isAlive()) {
//				Vec3 vec3 = this.getDeltaMovement();
//				if (((this.getTarget().getEyeY() > this.getEyeY() + 3 && this.getTarget().isInWater())) && this.canAttack(this.getTarget())) {
//					this.setDeltaMovement(this.getDeltaMovement().add(0.0, (0.3 - vec3.y) * 0.3, 0.0));
//					this.hasImpulse = true;
//				}
//			}
//		}
//		//控制
		if (this.getControllingPassenger() instanceof Player player && this.isVehicle()) {
			if (!this.isEyeInFluidType(ForgeMod.WATER_TYPE.get()) && this.isInWater() && this.horizontalCollision) {
				for (int n = 0; n < 18; ++n) {
					if (this.level() instanceof ServerLevel serverLevel) {
						serverLevel.sendParticles(ParticleTypes.BUBBLE, this.getRandomX(0.5), this.getY() + 0.5f, this.getRandomZ(0.5),
								0, (this.getRandom().nextFloat() - 0.5) * 0.2f, 0.2, (this.getRandom().nextFloat() - 0.5) * 0.2f, 0.5);
					}
				}
				this.setDeltaMovement(this.getDeltaMovement().add(0,0.25f,0));
			}
			if (!(this.xOld == this.getX() && this.zOld == this.getZ())) {
				if (this.isEyeInFluidType(ForgeMod.WATER_TYPE.get())) {
					double d = Math.abs(this.getX() - this.xOld);
					double d2 = Math.abs(this.getZ() - this.zOld);
					if (d >= 0.003 || d2 >= 0.003) {
						if (this.getDeltaMovement().y < 0.8f && this.getDeltaMovement().y > -0.8f) {
							float headXRot = player.getXRot();
							this.setDeltaMovement(this.getDeltaMovement().add(0.0, headXRot / 2 * -0.0035f, 0.0));
						}
					}
				}
			}
		}
		//
		//破坏方块
		{
			double blockDestroyTickUse = this instanceof GemstoneMalignasaurEntity ? OtherMainConfig.GemstoneMalignasaurBreakBlockCooldown * 20 : 60;
			if (this.blockDestroyTick > 0) {
				this.blockDestroyTick -= 1;
			}
			else if (this.horizontalCollision && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
				boolean canBlockDestroy = this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
				boolean blockDestroy = Main.BlockDestroy(this, 5f);
				if (blockDestroy) {
					this.blockDestroyTick = blockDestroyTickUse;
				}
				if (!this.level().isClientSide) {
					if (!canBlockDestroy && this.onGround()) {
						this.jumpFromGround();
					}
				}
			}
		}
		//技能
		{
			int cooldown = this instanceof GemstoneMalignasaurEntity ? (int) (OtherMainConfig.GemstoneMalignasaurJumpAttackCooldown * 20) : 30 * 20;
			//跃击
			{
				Vec3 vec3 = this.getDeltaMovement();
				if (!this.isTrueManuallyControlCombatJerotes()) {
					if (this.getTarget() != null && !this.onGround() && !this.getTarget().isInWater()) {
						if (spellNeed(4, 17, 1, true) && !this.isVehicle() && !this.onGround() && this.getJumpTick() >= cooldown) {
							double distanceXZ = this.distanceToSqr(this.getTarget().getX(), this.getY(), this.getTarget().getZ());
							if (distanceXZ < 300) {
								if (!this.level().isClientSide()) {
									this.setJumpUseTick(120);
									this.setJumpSound(true);
								}
								this.jumpToTarget(this.getTarget());
							}
						}
					}
				}
				if (this.getJumpUseTick() > 0) {
					//跃入水体
					if (!this.isInWater() && this.isJumpSound()) {
						if (this instanceof GemstoneMalignasaurEntity) {
							this.playSound(JerotesVillageSoundEvents.GEMSTONE_MALIGNASAUR_JUMP, 5.0f, 1.0f);
						}
						else {
							this.playSound(JerotesVillageSoundEvents.MALIGNASAUR_JUMP, 1.0f, 1.0f);
						}
						for (int i = 0; i < 20; ++i) {
							this.level().addParticle(ParticleTypes.BUBBLE, this.getRandomX(0.8), this.getRandomY(), this.getRandomZ(0.8), 0.0, 0.0, 0.0);
						}
						if (!this.level().isClientSide()) {
							this.setJumpSound(false);
						}
					}
//					//下落
//					if (this.getTarget() != null && (this.getTarget().getEyeY() < this.getEyeY()) && this instanceof GemstoneMalignasaurEntity gemstoneMalignasaur && gemstoneMalignasaur.isInRain() && !gemstoneMalignasaur.isInWater()) {
//						this.setDeltaMovement(this.getDeltaMovement().add(0.0, -(0.30000001192092896 - vec3.y) * 0.30000001192092896, 0.0));
//						this.hasImpulse = true;
//					}
					//攻击
					if (this.getTarget() != null && this.distanceTo(this.getTarget()) < 5f && this.getTarget().isAlive()) {
						if (!this.level().isClientSide()) {
							this.setAttackTick(6);
							this.setAttackUse(4);
							this.setJumpUseTick(0);
						}
					}
				}
				//未命中
				if (this.getJumpUseTick() == 1) {
					if (this instanceof GemstoneMalignasaurEntity) {
						if (!this.level().isClientSide()) {
							this.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 120, 3), this);
							this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120, 3), this);
							this.setJumpUseTick(0);
						}
					}
					else {
						if (!this.level().isClientSide()) {
							this.setJumpUseTick(0);
						}
					}
				}
			}
			if (!this.level().isClientSide()) {
				this.setJumpTick(Math.min(cooldown, this.getJumpTick() + 1));
				this.setJumpUseTick(Math.max(0, this.getJumpUseTick() - 1));
			}
		}
	}
	//法术前提
	public boolean spellNeed(float min, float max, int time, boolean bl) {
		return !this.specialAction() && this.isAlive() && !this.isNoAi() && this.getAttackTick() <= 0 && this.getTarget() != null && this.getTarget().isAlive()
				&& this.getTarget().distanceTo(this) > min && this.getTarget().distanceTo(this) < max
				&& this.getRandom().nextInt(time * 20) == 1;
	}

	public boolean jumpToTarget(LivingEntity target) {
		this.getLookControl().setLookAt(target, 360.0f, 360.0f);
		this.lookAt(target, 360.0f, 360.0f);

		final double smoothX = Mth.clamp(Math.abs(target.getX() - this.getX()), 0, 1);
		final double smoothZ = Mth.clamp(Math.abs(target.getZ() - this.getZ()), 0, 1);
		final double d0 = (target.getX() - this.getX()) * 0.3 * smoothX;
		final double d2 = (target.getZ() - this.getZ()) * 0.3 * smoothZ;
		final float up = 1.5f + this.getRandom().nextFloat() * 1.25f;
		this.setDeltaMovement(this.getDeltaMovement().add(
				d0 * 0.3,
				//(this instanceof GemstoneMalignasaurEntity gemstoneMalignasaur && gemstoneMalignasaur.isInRain() && !gemstoneMalignasaur.isInWater()) ? up/5 :
						up,
				d2 * 0.3));
		//this.getNavigation().moveTo(target, 1.0f);

		if (!this.level().isClientSide()) {
			this.setJumpTick(0);
			this.setAnimTick(30);
			this.setAnimationState("jump");
		}
		return true;
	}
	
	public boolean trueHurt(MalignasaurPart[] area) {
		if (!this.isSilent()) {
			if (this.getAttackUse() == 1) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.MALIGNASAUR_HEAD, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
			}
			else if (this.getAttackUse() == 2) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.MALIGNASAUR_ATTACK, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
			}
			else if (this.getAttackUse() == 3) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.MALIGNASAUR_TAIL, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
			}
			else if (this.getAttackUse() == 4) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.MALIGNASAUR_ATTACK, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
			}
		}
		int levels = 1;
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, getAttackBoundingBox().inflate(0.75, 0.75, 0.75));
		for (MalignasaurPart part : area) {
			List<LivingEntity> add = this.level().getEntitiesOfClass(LivingEntity.class, part.getBoundingBox().inflate(0.25, 0.25, 0.25));
			list.addAll(add);
		}
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			if (hurt instanceof MalignasaurEntity malignasaur && malignasaur.getTarget() != this && this.getTarget() != malignasaur)
				continue;
			if (hurt instanceof MalialosaurEntity malialosaurEntity && malialosaurEntity.getTarget() != this && this.getTarget() != malialosaurEntity)
				continue;
			//视线
//			if (!Main.canSee(hurt, this) && this.getAttackUse() == 1) continue;
//			if (Main.canSee(hurt, this) && this.getAttackUse() == 3) continue;
			double d = 0;
			if (hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
				d = hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
			}
			double d3 = Math.max(0.3, 1.3 - d);
			AttackFind.attackBegin(this, hurt);
			boolean bl2 = false;
			if (this.getAttackUse() == 1) {
				bl2 = AttackFind.attackAfter(this, hurt, 1f, 0.5f, false, 0f);
			}
			else if (this.getAttackUse() == 2) {
				bl2 = AttackFind.attackAfter(this, hurt, 1f, 1.5f, false, 0f);
			}
			else if (this.getAttackUse() == 3) {
				bl2 = AttackFind.attackAfter(this, hurt, 1f, 1.0f, false, 0f);
			}
			else if (this.getAttackUse() == 4) {
				bl2 = AttackFind.attackAfter(this, hurt, 0f, 0.5f, true, levels * 8);
			}
			if (bl2) {
				//流血
				if (this.getAttackUse() == 1) {
					if (!hurt.level().isClientSide) {
						hurt.addEffect(new MobEffectInstance(JerotesMobEffects.BLEEDING.get(), 60, 0, false, false), this);
					}
				}
				//扫尾
				if (this.getAttackUse() == 3) {
					if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt)) && !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
						hurt.setDeltaMovement(hurt.getDeltaMovement().add(-(this.getX() - hurt.getX()) * 0.2 * d3, -(this.getY() - hurt.getY()) * 0.2 * d3, -(this.getZ() - hurt.getZ()) * 0.2 * d3));
					}
					hurt.knockback(0.8, Mth.sin(this.getYRot() * 0.08f), -Mth.cos(this.getYRot() * 0.08f));
				}
				//跃击
				if (this.getJumpUseTick() > 0) {
					if (!hurt.level().isClientSide) {
						hurt.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 1), this);
						hurt.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1), this);
					}
					if (!this.level().isClientSide()) {
						this.setJumpUseTick(0);
					}
				}
			}
		}
		//震动
		if (this.getAttackUse() == 3 || this.getAttackUse() == 4) {
			List<LivingEntity> listShake = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(16.0, 16.0, 16.0));
			for (LivingEntity shake : listShake) {
				if (shake == null || shake == this || (this.distanceToSqr(shake)) > Double.MAX_VALUE)
					continue;
				if (!shake.level().isClientSide()) {
					shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 20, 0, false, false), this);
				}
			}
		}
		//横扫效果
		Main.sweepAttack(this);
		if (this.level() instanceof ServerLevel serverLevel && (this.getAttackUse() == 3 || this.getAttackUse() == 4)) {
			if (tail1 != null) {
				AABB aabb = AABB.ofSize(new Vec3(tail1.getX(), tail1.getY() - 1, tail1.getZ()), Main.mobWidth(tail1)/2 + 2, 0, Main.mobWidth(tail1)/2 + 2);
				for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
					Main.spawnFallingBlockByPos(serverLevel, blockPos);
				}
			}
		}
		return true;
	}

	public boolean doHurtTargetHead(LivingEntity entity) {
		if (this.getAttackTick() > -5 || this.specialAction()) {
			return false;
		}
		if (!this.level().isClientSide()) {
			this.setAttackTick(20);
			this.setAnimTick(20);
			this.setAttackUse(1);
			int attackRandom = this.getRandom().nextInt(30);
			if (attackRandom > 20) {
				this.setAnimationState("headAttack1");
			} else if (attackRandom < 10) {
				this.setAnimationState("headAttack2");
			} else {
				this.setAnimationState("headAttack3");
			}
		}
		return true;
	}
	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.isTrueManuallyControlCombatJerotes()) {
			return false;
		}
		if (this.getAttackTick() > -5 || this.specialAction()) {
			return false;
		}
		if (!this.level().isClientSide()) {
			this.setAttackTick(20);
			this.setAnimTick(20);
			this.setAttackUse(2);
			int attackRandom = this.getRandom().nextInt(30);
			if (attackRandom > 20) {
				this.setAnimationState("attack1");
			} else if (attackRandom < 10) {
				this.setAnimationState("attack2");
			} else {
				this.setAnimationState("attack3");
			}
		}
		return true;
	}
	public boolean doHurtTargetTail(LivingEntity entity) {
		if (this.getAttackTick() > -5 || this.specialAction()) {
			return false;
		}
		if (!this.level().isClientSide()) {
			this.setAttackTick(20);
			this.setAnimTick(20);
			this.setAttackUse(3);
			int attackRandom = this.getRandom().nextInt(30);
			if (attackRandom > 20) {
				this.setAnimationState("tailAttack1");
			} else if (attackRandom < 10) {
				this.setAnimationState("tailAttack2");
			} else {
				this.setAnimationState("tailAttack3");
			}
		}
		return true;
	}
	
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		return super.hurt(damageSource, amount);
	}
	public boolean hurtByPart(MalignasaurPart malignasaurPart, DamageSource damageSource, float f) {
		return this.hurt(damageSource, f);
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		Item item = itemStack.getItem();
		if (this.level().isClientSide) {
			boolean bl = this.isOwnedBy(player) || this.isTame() || this.isFood(itemStack) && !this.isTame() && !this.isAngry();
			boolean bl2 = this.isTame() && !player.isShiftKeyDown() && this.isBaby();
			if (!bl2) {
				return bl ? InteractionResult.CONSUME : InteractionResult.PASS;
			}
		}
		if (this.isTame()) {
			InteractionResult interactionResult;
			if (!player.isShiftKeyDown()) {
				if (this.isFood(itemStack)) {
					if (this.getHealth() < this.getMaxHealth()) {
						this.heal(8f);
						if (!player.getAbilities().instabuild) {
							itemStack.shrink(1);
						}
						this.gameEvent(GameEvent.EAT, this);
						return InteractionResult.SUCCESS;
					}
					return super.mobInteract(player, interactionHand);
				}
				//骑乘
				if (!this.isFood(itemStack) && this.isSaddled() && AttackFind.canRideAbout(this, this.getOwner(), this.getControllingPassenger(), player) && !player.isSecondaryUseActive() && !isOrderedToSit()) {
					if (!this.level().isClientSide()) {
						player.startRiding(this);
					}
					return InteractionResult.sidedSuccess(this.level().isClientSide);
				}
				this.openCustomInventoryScreen(player);
				return InteractionResult.sidedSuccess(this.level().isClientSide);
			}
			if ((interactionResult = super.mobInteract(player, interactionHand)).consumesAction() && !this.isBaby() || !this.isOwnedBy(player)) return interactionResult;
			if (!this.isVehicle() && player == this.getOwner()) {
				int pose = this.getChangeType() + 1;
				if (pose > 4) {
					pose = 1;
				}
				this.setChangeType(pose, player);
			}
			return InteractionResult.SUCCESS;
		}
		if (!this.isFood(itemStack) || this.isAngry())
			return super.mobInteract(player, interactionHand);
		if (!player.getAbilities().instabuild) {
			itemStack.shrink(1);
		}
		if (this.random.nextInt(16) == 1 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
			this.tame(player);
			this.navigation.stop();
			this.setTarget(null);
			setChangeType(1, player);
			((Level)this.level()).broadcastEntityEvent(this, (byte)7);
		} else {
			((Level)this.level()).broadcastEntityEvent(this, (byte)6);
		}
		return InteractionResult.SUCCESS;
	}

	@Nullable
	@Override
	public LivingEntity getControllingPassenger() {
		Entity entity;
		if (this.isSaddled() && (entity = this.getFirstPassenger()) instanceof Player player) {
			return player;
		}
		return super.getControllingPassenger();
	}



	@Override
	public boolean isSaddleable() {
		return this.isAlive() && !this.isBaby() && this.isTame();
	}

	@Override
	protected void dropEquipment() {
		super.dropEquipment();
		if (this.inventory != null) {
			for(int i = 0; i < this.inventory.getContainerSize(); ++i) {
				ItemStack itemstack = this.inventory.getItem(i);
				if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
					this.spawnAtLocation(itemstack);
				}
			}
		}
	}
	private SlotAccess createEquipmentSlotAccess(final int p_149503_, final Predicate<ItemStack> p_149504_) {
		return new SlotAccess() {
			public ItemStack get() {
				return MalignasaurEntity.this.inventory.getItem(p_149503_);
			}

			public boolean set(ItemStack p_149528_) {
				if (!p_149504_.test(p_149528_)) {
					return false;
				} else {
					MalignasaurEntity.this.inventory.setItem(p_149503_, p_149528_);
					MalignasaurEntity.this.updateContainerEquipment();
					return true;
				}
			}
		};
	}

	public SlotAccess getSlot(int p_149514_) {
		int i = p_149514_ - getAddNumber();
		if (i >= 0 && i < 2 && i < this.inventory.getContainerSize()) {
			if (i == 0) {
				return this.createEquipmentSlotAccess(i, (p_149516_) -> {
					return p_149516_.isEmpty() || p_149516_.getItem() instanceof SaddleItem;
				});
			}

			if (i == 1) {
				if (!this.canWearArmor()) {
					return SlotAccess.NULL;
				}

				return this.createEquipmentSlotAccess(i, (p_149516_) -> {
					return p_149516_.isEmpty() || this.isArmor(p_149516_);
				});
			}
		}

		int j = p_149514_ - 500 + 2;
		return j >= 2 && j < this.inventory.getContainerSize() ? SlotAccess.forContainer(this.inventory, j) : super.getSlot(p_149514_);
	}

	public boolean hasInventoryChanged(Container p_149512_) {
		return this.inventory != p_149512_;
	}

	@Override
	public boolean isSaddled() {
		return this.getFlag(4);
	}
	public boolean canWearArmor() {
		return false;
	}
	@Override
	public void equipSaddle(@Nullable SoundSource soundSource) {
		this.inventory.setItem(0, new ItemStack(Items.SADDLE));
	}
	public void openCustomInventoryScreen(Player player) {
		if (!this.level().isClientSide && (!this.isVehicle() || this.hasPassenger(player)) && this.isTame()) {
			if (player.containerMenu != player.inventoryMenu) {
				player.closeContainer();
			}

			if (player instanceof ServerPlayer serverPlayer) {
				Main.openSuchInventoryGui(serverPlayer, this);
			}
		}
	}

	@Override
	public Vec3 getDismountLocationForPassenger(LivingEntity livingEntity) {
		Direction direction = this.getMotionDirection();
		if (direction.getAxis() == Direction.Axis.Y) {
			return super.getDismountLocationForPassenger(livingEntity);
		}
		int[][] arrn = DismountHelper.offsetsForDirection(direction);
		BlockPos blockPos = this.blockPosition();
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		for (Pose pose : livingEntity.getDismountPoses()) {
			AABB aABB = livingEntity.getLocalBoundsForPose(pose);
			for (int[] arrn2 : arrn) {
				mutableBlockPos.set(blockPos.getX() + arrn2[0], blockPos.getY(), blockPos.getZ() + arrn2[1]);
				double d = this.level().getBlockFloorHeight(mutableBlockPos);
				if (!DismountHelper.isBlockFloorValid(d)) continue;
				Vec3 vec3 = Vec3.upFromBottomCenterOf(mutableBlockPos, d);
				if (!DismountHelper.canDismountTo(this.level(), livingEntity, aABB.move(vec3))) continue;
				livingEntity.setPose(pose);
				return vec3;
			}
		}
		return super.getDismountLocationForPassenger(livingEntity);
	}

	@Override
	protected void tickRidden(Player player, Vec3 vec3) {
		super.tickRidden(player, vec3);
		this.setRot(player.getYRot(), player.getXRot() * 0.5f);
		this.yBodyRot = this.yHeadRot = this.getYRot();
		this.yRotO = this.yHeadRot;
	}

	@Override
	protected Vec3 getRiddenInput(Player player, Vec3 vec3) {
		return new Vec3(0.0, 0.0, 1.0);
	}

	@Override
	protected float getRiddenSpeed(Player player) {
		return (float)(this.getAttributeValue(Attributes.MOVEMENT_SPEED));
	}

	@Override
	public Vec3 getLeashOffset() {
		return new Vec3(0.0, 0.6f * this.getEyeHeight(), this.getBbWidth() * 0.4f);
	}

	//
	protected void positionRider(Entity entity, MoveFunction moveFunction) {
		Vec3 vec3 = this.getPassengerRidingPosition(entity);
		moveFunction.accept(entity, vec3.x, vec3.y + getMyRidingOffset(this), vec3.z);
	}
	public float getMyRidingOffset(Entity entity) {
		return this.ridingOffset(entity) * this.getScale();
	}
	protected float ridingOffset(Entity entity) {
		float scale = 1f;
		if (this instanceof GemstoneMalignasaurEntity) {
			scale = 4f/3f;
		}
		if (!this.isSaddled()) {
			return -1.85F * scale;
		}
		return -1.55F * scale;
	}
	public Vec3 getPassengerRidingPosition(Entity entity) {
		return (new Vec3(this.getPassengerAttachmentPoint(entity, this.getDimensions(this.getPose()), this.getScale()).rotateY(-this.yBodyRot * ((float)Math.PI / 180F)))).add(this.position());
	}
	protected Vector3f getPassengerAttachmentPoint(Entity entity, EntityDimensions entityDimensions, float f) {
		int i = this.getPassengers().indexOf(entity);
		if (!this.isSaddled()) {
			return new Vector3f(0.0F, entityDimensions.height, 0.0F);
		}
		if (i == 0) {
			float f2 = 2.5f;
			float f3 = 0f;
			return new Vector3f(f3, entityDimensions.height, f2);
		}
		if (i == 1) {
			float f2 = 0.5f;
			float f3 = 0f;
			return new Vector3f(f3, entityDimensions.height, f2);
		}
		return new Vector3f(0.0F, entityDimensions.height, 0.0F);
	}
	@Override
	public void lookAt(Entity entity, float f, float f2) {
		if (!this.stopLookTime()) {
			super.lookAt(entity,f,f2);
		}
	}
	@Override
	public void setXRot(float f) {
		if (!this.stopLookTime()) {
			super.setXRot(f);
		}
	}
	@Override
	public void setYRot(float f) {
		if (!this.stopLookTime()) {
			super.setYRot(f);
		}
	}
	@Override
	public void setYBodyRot(float f) {
		if (!this.stopLookTime()) {
			super.setYBodyRot(f);
		}
	}
	public AABB getAttackBoundingBox() {
		Entity entity = this.getVehicle();
		AABB aabb;
		if (entity != null) {
			AABB aabb1 = entity.getBoundingBox();
			AABB aabb2 = this.getBoundingBox();
			aabb = new AABB(Math.min(aabb2.minX, aabb1.minX), aabb2.minY, Math.min(aabb2.minZ, aabb1.minZ), Math.max(aabb2.maxX, aabb1.maxX), aabb2.maxY, Math.max(aabb2.maxZ, aabb1.maxZ));
		} else {
			aabb = this.getBoundingBox();
		}
		AABB aabb1 = aabb.inflate(Math.sqrt((double)2.04F) - (double)0.6F, 0.0D, Math.sqrt((double)2.04F) - (double)0.6F);
		return aabb1.inflate(0.5d, 0.5d, 0.5d);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}
	//
	@Override
	public void travel(Vec3 dir) {
		Entity entity = this.getPassengers().isEmpty() ? null : (Entity) this.getPassengers().get(0);
		if (this.isVehicle() && this.getControllingPassenger() instanceof Player) {
			this.setYRot(entity.getYRot());
			this.yRotO = this.getYRot();
			this.setXRot(entity.getXRot() * 0.5F);
			this.setRot(this.getYRot(), this.getXRot());
			this.yBodyRot = entity.getYRot();
			this.yHeadRot = entity.getYRot();
			if (entity instanceof LivingEntity passenger) {
				if (this.isControlledByLocalInstance() && this.isInWater()) {
					this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.25f);
					float forward = passenger.zza;
					float strafe = passenger.xxa/2;
					this.moveRelative(this.getSpeed(), new Vec3(strafe, 0, forward));
					this.move(MoverType.SELF, this.getDeltaMovement());
					this.setDeltaMovement(this.getDeltaMovement().scale(0.7));
					this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
				}
				else {
					this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.15f);
					float forward = passenger.zza;
					float strafe = passenger.xxa/2;
					super.travel(new Vec3(strafe, 0, forward));
				}
			}
			double d1 = this.getX() - this.xo;
			double d0 = this.getZ() - this.zo;
			float f1 = (float) Math.sqrt(d1 * d1 + d0 * d0) * 4;
			if (f1 > 1.0F)
				f1 = 1.0F;
			this.walkAnimation.setSpeed(this.walkAnimation.speed() + (f1 - this.walkAnimation.speed()) * 0.4F);
			this.walkAnimation.position(this.walkAnimation.position() + this.walkAnimation.speed());
			this.calculateEntityAnimation(true);
			if (this.specialAction() && this.getJumpUseTick() <= 0) {
				this.setDeltaMovement(this.getDeltaMovement().multiply(0.05d, 1d, 0.05d));
			}
			return;
		}
		if (this.isControlledByLocalInstance() && this.isInWater()) {
			this.moveRelative(this.getSpeed(), dir);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.90));
		}
		else {
			super.travel(dir);
		}
		if (this.specialAction() && this.getJumpUseTick() <= 0) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(0.05d, 1d, 0.05d));
		}
	}

	@Nullable
	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	private int remainingPersistentAngerTime;
	private UUID persistentAngerTarget;

	@Override
	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	}

	@Override
	public void setRemainingPersistentAngerTime(int n) {
		this.remainingPersistentAngerTime = n;
	}

	@Override
	public int getRemainingPersistentAngerTime() {
		return this.remainingPersistentAngerTime;
	}

	@Override
	public void setPersistentAngerTarget(UUID uUID) {
		this.persistentAngerTarget = uUID;
	}

	@Override
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}
}