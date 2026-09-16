package com.jerotes.jerotesvillage.entity.Part;

import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.FirepowerPourerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class FirepowerPourerChainsawPart extends FirepowerPourerPart {
	public final FirepowerPourerEntity parentMob;
	public final String name;
	private final EntityDimensions size;

	public FirepowerPourerChainsawPart(FirepowerPourerEntity parent, String p_31015_, float p_31016_, float p_31017_) {
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

	public void lookAt(Entity p_21392_, float p_21393_, float p_21394_) {
		double d0 = p_21392_.getX() - this.getX();
		double d2 = p_21392_.getZ() - this.getZ();
		double d1;
		if (p_21392_ instanceof LivingEntity livingentity) {
			d1 = livingentity.getEyeY() - this.getEyeY();
		} else {
			d1 = (p_21392_.getBoundingBox().minY + p_21392_.getBoundingBox().maxY) / 2.0 - this.getEyeY();
		}
		double d3 = Math.sqrt(d0 * d0 + d2 * d2);
		float f = (float)(Mth.atan2(d2, d0) * 57.2957763671875) - 90.0F;
		float f1 = (float)(-(Mth.atan2(d1, d3) * 57.2957763671875));
		this.setXRot(this.rotlerp(this.getXRot(), f1, p_21394_));
		this.setYRot(this.rotlerp(this.getYRot(), f, p_21393_));
	}

	private float rotlerp(float p_21377_, float p_21378_, float p_21379_) {
		float f = Mth.wrapDegrees(p_21378_ - p_21377_);
		if (f > p_21379_) {
			f = p_21379_;
		}
		if (f < -p_21379_) {
			f = -p_21379_;
		}
		return p_21377_ + f;
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

	public boolean hurt(DamageSource damageSource, float f) {
//		if (damageSource.getEntity() instanceof ServerPlayer serverPlayer)
//			serverPlayer.sendSystemMessage(Component.literal("chainsaw"));
		return !this.isInvulnerableTo(damageSource) && this.parentMob.hurtByChainsawPart(damageSource, f);
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
}