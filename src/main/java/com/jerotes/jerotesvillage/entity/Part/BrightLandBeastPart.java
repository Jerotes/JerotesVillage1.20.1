package com.jerotes.jerotesvillage.entity.Part;

import com.jerotes.jerotesvillage.entity.Boss.Biome.BrightLandBeastEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;

import javax.annotation.Nullable;

public class BrightLandBeastPart extends PartEntity<BrightLandBeastEntity> {
	public final BrightLandBeastEntity parentMob;
	public final String name;
	private final EntityDimensions size;

	public BrightLandBeastPart(BrightLandBeastEntity parent, String p_31015_, float p_31016_, float p_31017_) {
		super(parent);
		this.size = EntityDimensions.scalable(p_31016_, p_31017_);
		this.refreshDimensions();
		this.parentMob = parent;
		this.name = p_31015_;
	}

	public InteractionResult interact(Player player, InteractionHand hand) {
		if (player.getVehicle() == this.getParent()) {
			return InteractionResult.PASS;
		}
		return this.getParent() == null ? InteractionResult.PASS : this.getParent().mobInteract(player, hand);
	}

	protected void defineSynchedData() {
	}
	protected void readAdditionalSaveData(CompoundTag compoundTag) {
	}
	protected void addAdditionalSaveData(CompoundTag compoundTag) {
	}

	public boolean isPickable() {
		return this.getParent() == null || this.getParent() != null && this.getParent().getControllingPassenger() == null;
	}

	@Nullable
	public ItemStack getPickResult() {
		return this.parentMob.getPickResult();
	}

	public boolean hurt(DamageSource damageSource, float p_31021_) {
		return !this.isInvulnerableTo(damageSource) && this.parentMob.hurtByPart(this, damageSource, p_31021_);
	}

	public boolean is(Entity p_31031_) {
		return this == p_31031_ || this.parentMob == p_31031_;
	}

	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		throw new UnsupportedOperationException();
	}

	public EntityDimensions getDimensions(Pose pose) {
		return this.size;
	}

	public boolean shouldBeSaved() {
		return false;
	}

	public void setPosCenteredY(Vec3 pos) {
		this.setPos(pos.x, pos.y - this.getBbHeight() * 0.5F, pos.z);
	}
	public Vec3 centeredPosition() {
		return this.position().add(0, this.getBbHeight() * 0.5F, 0);
	}
	public Vec3 centeredPosition(float partialTicks) {
		return this.getPosition(partialTicks).add(0, this.getBbHeight() * 0.5F, 0);
	}



	@Override
	public boolean canCollideWith(Entity entity) {
		return this.getParent().isAlive();
	}
	@Override
	public boolean canBeCollidedWith() {
		return this.getParent().isAlive();
	}
	@Override
	public boolean isPushedByFluid() {
		return false;
	}
	@Override
	public boolean isPushable() {
		return false;
	}
	@Override
	public void push(double d, double d2, double d3) {
		super.push(0, 0, 0);
	}
}