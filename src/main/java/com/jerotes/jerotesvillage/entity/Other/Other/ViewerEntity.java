package com.jerotes.jerotesvillage.entity.Other.Other;

import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Interface.PlayerSkinEntity;
import com.jerotes.jerotes.entity.Interface.SkinEntity;
import com.jerotes.jerotes.util.PlayerName;
import com.jerotes.jerotes.util.PlayerSkin;
import com.jerotes.jerotesvillage.goal.ViewerLookGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.util.ViewerNameManager;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.StringUtil;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewerEntity extends PathfinderMob implements JerotesEntity, SkinEntity, PlayerSkinEntity {
	public AnimationState armWideScaleAnimationState = new AnimationState();
	public AnimationState armSlimScaleAnimationState = new AnimationState();
	public AnimationState clap1AnimationState = new AnimationState();
	public AnimationState clap2AnimationState = new AnimationState();
	private static final EntityDataAccessor<String> SKIN_NAME = SynchedEntityData.defineId(ViewerEntity.class, EntityDataSerializers.STRING);
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(ViewerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(ViewerEntity.class, EntityDataSerializers.INT);

	public ViewerEntity(EntityType<? extends ViewerEntity> type, Level world) {
		super(type, world);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new ViewerLookGoal(this, Player.class, 256.0f, 10f, false));
	}

	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.VIEWER_DEATH;
	}

	@Override
	protected float getSoundVolume() {
		return 40.0f;
	}

	protected Vector3f getPassengerAttachmentPoint(Entity entity, EntityDimensions entityDimensions, float f) {
		return new Vector3f(0.0f, entityDimensions.height + 0.0625f * f, 0.0f);
	}

	protected float ridingOffset(Entity entity) {
		return -0.7f;
	}

	public double getMyRidingOffset() {
		return this.isBaby() ? 0.0D : -0.45D;
	}

	@Override
	public int getMaxHeadYRot() {
		return 360;
	}

	@Override
	public int getMaxHeadXRot() {
		return 360;
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0);
		builder = builder.add(Attributes.MAX_HEALTH, 1);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
		builder = builder.add(Attributes.FOLLOW_RANGE, 0);
		return builder;
	}

	@Override
	public void tick() {
		super.tick();
		boolean slim = PlayerSkin.getPlayerSkinType(this.getProfile()) == PlayerSkin.SkinType.SLIM;
		if (!slim) {
			this.armSlimScaleAnimationState.stop();
			this.armWideScaleAnimationState.startIfStopped(this.tickCount);
		} else {
			this.armWideScaleAnimationState.stop();
			this.armSlimScaleAnimationState.startIfStopped(this.tickCount);
		}
	}
	@Override
	public void aiStep() {
		super.aiStep();
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//
		//
		//清除动画
		if (!this.level().isClientSide()) {
			this.setAnimTick(Math.max(-1, this.getAnimTick() - 1));
		}
		if (this.getAnimTick() == 0) {
			if (!this.level().isClientSide()) {
				this.setAnimationState(0);
			}
		}
	}


	public void setSkinName(String string) {
		this.getEntityData().set(SKIN_NAME, string);
	}

	public String getSkinName() {
		return this.getEntityData().get(SKIN_NAME);
	}

	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
		if (!this.level().isClientSide() && component != null) {
			this.setSkinName(this.getDisplayName().getString());
		}
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
		if (Objects.equals(animation, "clap1")){
			return 1;
		}
		if (Objects.equals(animation, "clap2")){
			return 2;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.clap1AnimationState);
		list.add(this.clap2AnimationState);
		return list;
	}
	public void stopMostAnimation(AnimationState exception){
		for (AnimationState state : this.getAllAnimations()){
			if (state != exception) {
				state.stop();
			}
		}
	}
	public void stopAllAnimation(){
		for (AnimationState state : this.getAllAnimations()){
			state.stop();
		}
	}
	//
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putString("SkinName", this.getSkinName());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.setSkinName(compoundTag.getString("SkinName"));
		String skinName = compoundTag.getString("SkinName");
		if (!StringUtil.isNullOrEmpty(skinName)) {
			setUsername(skinName);
		}
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(SKIN_NAME, "Jerotes_");
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
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
						this.clap1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.clap1AnimationState);
						break;
					case 2:
						this.clap2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.clap2AnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}


	@Nullable
	private GameProfile profile;
	@Nullable
	private ResourceLocation skin;
	@Nullable
	private ResourceLocation cape;
	@Nullable
	private ResourceLocation elytra;
	private boolean skinAvailable;
	private boolean capeAvailable;
	private boolean elytraAvailable;

	@OnlyIn(Dist.CLIENT)
	public SkinManager.SkinTextureCallback getSkinCallback() {
		return (type, location, profileTexture) -> {
			switch (type) {
				case SKIN -> {
					skin = location;
					skinAvailable = true;
				}
				case CAPE -> {
					cape = location;
					capeAvailable = true;
				}
				case ELYTRA -> {
					elytra = location;
					elytraAvailable = true;
				}
			}
		};
	}
	@Nullable
	public GameProfile getProfile() {
		if (profile == null && hasUsername()) {
			profile = new GameProfile(null, getUsername().getSkinName());
			PlayerSkin.ProfileUpdater.updateProfile(this);
		}
		return profile;
	}
	public void setProfile(@Nullable GameProfile profile) {
		this.profile = profile;
	}
	public boolean hasUsername() {
		return !StringUtil.isNullOrEmpty(getEntityData().get(SKIN_NAME));
	}
	public PlayerName getUsername() {
		return new PlayerName(getEntityData().get(SKIN_NAME));
	}
	public void setUsername(String username) {
		PlayerName playerName = new PlayerName(username);
		setUsername(playerName);
	}
	public void setUsername(PlayerName name) {
		PlayerName oldName = hasUsername() ? getUsername(): null;
		getEntityData().set(SKIN_NAME, name.getCombinedNames());

		if (!Objects.equals(oldName, name)) {
			setProfile(null);
			getProfile();
		}
	}
	@OnlyIn(Dist.CLIENT)
	public boolean isTextureAvailable(MinecraftProfileTexture.Type type) {
		return skinAvailable;
	}
	@SuppressWarnings("ConstantConditions")
	@OnlyIn(Dist.CLIENT)
	public ResourceLocation getTexture(MinecraftProfileTexture.Type type) {
		if (type == MinecraftProfileTexture.Type.SKIN)
			return skin;
		if (type == MinecraftProfileTexture.Type.ELYTRA)
			return elytra;
		return cape;
	}
	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		String randomName = ViewerNameManager.getInstance().getRandomName();
		setUsername(randomName);
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}
}
