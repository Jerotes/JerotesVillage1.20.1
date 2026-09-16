package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotes.client.model.Modelspecial_action;
import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.item.Interface.ItemTwoHanded;
import com.jerotes.jerotes.item.Interface.JerotesItemThrownJavelinUse;
import com.jerotes.jerotes.item.Tool.ItemToolBaseDagger;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotes.client.animation.HumanoidAnimation;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Humanoid.CarvedVillagerEntity;
import com.jerotes.jerotesvillage.entity.Humanoid.Qoaiku.QoaikuEntity;
import com.jerotes.jerotesvillage.entity.Humanoid.SecondRounderEntity;
import com.jerotes.jerotesvillage.entity.Humanoid.Serpon.SerponEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.SecondRounderAnimatedArmor.SecondRounderAnimatedArmorEntity;
import com.jerotes.jerotesvillage.entity.Monster.Resurrecter.ResurrecterEntity;
import com.jerotes.jerotesvillage.entity.Monster.SpirveEntity;
import com.jerotes.jerotesvillage.entity.Other.Other.ViewerEntity;
import com.jerotes.jerotes.entity.Interface.SkinEntity;
import com.jerotes.jerotesvillage.event.WeatherEvent;
import com.jerotes.jerotesvillage.item.SerponBlowpipe;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeMod;

public class Modelhumanoid<T extends LivingEntity> extends Modelspecial_action<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "humanoid"), "main");

	public Modelhumanoid(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("ear", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("cloak", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition jacket = partdefinition.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition left_sleeve = partdefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition right_sleeve = partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create().texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition left_pants = partdefinition.addOrReplaceChild("left_pants", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

		PartDefinition right_pants = partdefinition.addOrReplaceChild("right_pants", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T t, float limbSwing, float limbSwingAmount, float ageInTicks, float netbipedHeadYaw, float bipedHeadPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		String string = ChatFormatting.stripFormatting(t.getName().getString());
		super.setupAnim(t, limbSwing, limbSwingAmount, ageInTicks, netbipedHeadYaw, bipedHeadPitch);
		ItemStack itemStack = t.getMainHandItem();
		ItemStack itemStacks = t.getOffhandItem();
        boolean bl3 = t.getFallFlyingTicks() > 4;
		boolean bl4 = t.isVisuallySwimming();
		ModelPart mainHand;
		ModelPart offHand;
		if (!(t instanceof Mob mob && mob.isLeftHanded())) {
			mainHand = rightArm;
			offHand = leftArm;
		}
		else {
			mainHand = leftArm;
			offHand = rightArm;
		}
		this.head.yRot = netbipedHeadYaw * ((float) Math.PI / 180F);
		this.head.xRot = bl3 ? -0.7853982f : (this.swimAmount > 0.0f ? (bl4 ? this.rotlerpRad(this.swimAmount, this.head.xRot, -0.7853982f) : this.rotlerpRad(this.swimAmount, this.head.xRot, bipedHeadPitch * ((float) Math.PI / 180F))) : bipedHeadPitch * ((float) Math.PI / 180F));

		//部件大小位置调整
		if (t instanceof SkinEntity skinEntity) {
			if (skinEntity instanceof SpirveEntity entity) {
				this.animate(entity.armWideScaleAnimationState, HumanoidAnimation.MALE, ageInTicks);
				this.animate(entity.armSlimScaleAnimationState, HumanoidAnimation.FEMALE, ageInTicks);
			}
			if (skinEntity instanceof SecondRounderEntity entity) {
				this.animate(entity.armWideScaleAnimationState, HumanoidAnimation.MALE, ageInTicks);
				this.animate(entity.armSlimScaleAnimationState, HumanoidAnimation.FEMALE, ageInTicks);
			}
			if (skinEntity instanceof ViewerEntity entity) {
				this.animate(entity.armWideScaleAnimationState, HumanoidAnimation.MALE, ageInTicks);
				this.animate(entity.armSlimScaleAnimationState, HumanoidAnimation.FEMALE, ageInTicks);
			}
		}
		if (t instanceof ViewerEntity viewerEntity) {
			this.animate(viewerEntity.clap1AnimationState, HumanoidAnimation.CLAP1, ageInTicks);
		}
		if (t instanceof CarvedVillagerEntity entity) {
			if (entity.isEndermanRace() && !entity.isSkin()) {
				this.rightArm.xScale = 0.5f;
				this.leftArm.xScale = 0.5f;

				this.rightArm.yScale = 2.5f;
				this.leftArm.yScale = 2.5f;
				this.rightArm.zScale = 0.5f;
				this.leftArm.zScale = 0.5f;

				this.rightLeg.xScale = 0.5f;
				this.leftLeg.xScale = 0.5f;
				this.rightLeg.yScale = 2.5f;
				this.leftLeg.yScale = 2.5f;
				this.rightLeg.zScale = 0.5f;
				this.leftLeg.zScale = 0.5f;

				this.rightArm.x = -4.5f;
				this.leftArm.x = 4.5f;
				this.head.y = -13f;
				if (entity.isAggressive() && !entity.isBaby()) {
					this.head.y -= 5f;
				}
				if (entity.isBaby()) {
					this.head.y += 5f;
				}
				this.body.y = -14f;
				this.rightArm.y = -9f;
				this.leftArm.y = -9f;
				this.rightArm.z = 0f;
				this.leftArm.z = 0f;
				this.rightLeg.y = -5f;
				this.leftLeg.y = -5f;

				this.body.xRot = 0.0f;
				this.body.z = -0.0f;

				this.rightLeg.xRot -= 0.0f;
				this.leftLeg.xRot -= 0.0f;
				this.rightArm.xRot *= 0.5f;
				this.leftArm.xRot *= 0.5f;
				this.rightLeg.xRot *= 0.5f;
				this.leftLeg.xRot *= 0.5f;
				if (this.rightArm.xRot > 0.4f) {
					this.rightArm.xRot = 0.4f;
				}
				if (this.leftArm.xRot > 0.4f) {
					this.leftArm.xRot = 0.4f;
				}
				if (this.rightArm.xRot < -0.4f) {
					this.rightArm.xRot = -0.4f;
				}
				if (this.leftArm.xRot < -0.4f) {
					this.leftArm.xRot = -0.4f;
				}
				if (this.rightLeg.xRot > 0.4f) {
					this.rightLeg.xRot = 0.4f;
				}
				if (this.leftLeg.xRot > 0.4f) {
					this.leftLeg.xRot = 0.4f;
				}
				if (this.rightLeg.xRot < -0.4f) {
					this.rightLeg.xRot = -0.4f;
				}
				if (this.leftLeg.xRot < -0.4f) {
					this.leftLeg.xRot = -0.4f;
				}
			}
			else{
				this.animate(entity.armWideScaleAnimationState, HumanoidAnimation.MALE, ageInTicks);
				this.animate(entity.armSlimScaleAnimationState, HumanoidAnimation.FEMALE, ageInTicks);
				this.rightLeg.xScale = 1f;
				this.leftLeg.xScale = 1f;
				this.rightLeg.yScale = 1f;
				this.leftLeg.yScale = 1f;
				this.rightLeg.zScale = 1f;
				this.leftLeg.zScale = 1f;
			}
		}
		if (t instanceof SerponEntity) {
			this.rightLeg.xRot = 0.05f;
			this.leftLeg.xRot = 0.05f;
			this.rightLeg.yRot = 0f;
			this.leftLeg.yRot = 0f;
			this.rightLeg.zRot = 0f;
			this.leftLeg.zRot = 0f;
			this.rightLeg.y = 8.5f;
			this.leftLeg.y = 8.5f;
			this.rightLeg.zScale = 0.95f;
			this.leftLeg.zScale = 0.95f;
			this.rightLeg.x = 1f * Mth.cos(limbSwing * 0.6662f) * limbSwingAmount - 2;
			this.leftLeg.x = 1f * Mth.cos(limbSwing * 0.6662f) * limbSwingAmount + 2;
		}
		if (t instanceof QoaikuEntity entity) {
			if (!(WeatherEvent.isNewYearsDay() && OtherMainConfig.SpecialDay) && !"Old Lore".equals(string)) {
				this.head.xScale = 1.1f;
				this.head.yScale = 1.1f;
				this.head.zScale = 1.1f;
				this.hat.xScale = 1.125f;
				this.hat.yScale = 1.125f;
				this.hat.zScale = 1.125f;
				this.rightArm.x += 0.4f;
				this.leftArm.x -= 0.4f;
				this.rightArm.xScale = 0.8f;
				this.leftArm.xScale = 0.8f;
				this.rightArm.zScale = 0.8f;
				this.leftArm.zScale = 0.8f;
				this.rightLeg.xScale = 0.8f;
				this.leftLeg.xScale = 0.8f;
				this.rightLeg.zScale = 0.8f;
				this.leftLeg.zScale = 0.8f;
			}
			else {
				this.head.z -= 4;
				this.head.xRot -= 40 * ((float) Math.PI / 180F);
				this.head.yRot = 0;
			}
			if (entity.isEyeInFluidType(ForgeMod.WATER_TYPE.get())) {
				if (!t.isUsingItem()) {
					this.rightArm.xRot = Mth.cos(limbSwing * 0.6662f + 3.1415927f) * 0.8f * limbSwingAmount - 0.3f;
					this.leftArm.xRot = Mth.cos(limbSwing * 0.6662f + 3.1415927f) * 0.8f * limbSwingAmount - 0.3f;
					this.rightArm.zRot = Mth.cos(limbSwing * 0.6662f + 3.1415927f) * 1.0f * limbSwingAmount + 0.7f;
					this.leftArm.zRot = Mth.cos(limbSwing * 0.6662f) * 1.0f * limbSwingAmount - 0.7f;
				}
				this.rightLeg.xRot = 0.15f - Mth.cos(limbSwing * 0.6662f) * limbSwingAmount * 0.5f;
				this.leftLeg.xRot = 0.15f - Mth.cos(limbSwing * 0.6662f + 3.1415927f) * limbSwingAmount * 0.5f;
			}
		}
		if (t instanceof ResurrecterEntity) {
			this.body.xRot = super.body.xRot + 0.2f;
			this.leftArm.zRot = super.leftArm.zRot - 0.2f;
			this.rightArm.zRot = super.rightArm.zRot + 0.2f;
			this.body.y = 1f;
			this.body.z = -2f;
			this.head.y = 1.2f;
			this.head.z = -2;
			this.leftArm.y = 2.5f;
			this.leftArm.z = -2;
			this.rightArm.y = 2.5f;
			this.rightArm.z = -2;
		}

		//姿势
		if (t instanceof Mob mob) {
			//潜行
			if (mob.isShiftKeyDown()) {
				//通用
				if (!(mob instanceof SerponEntity)) {
					this.head.y = 4.2f;
					this.body.y = 3.2f;
					this.leftArm.y = 5.2f;
					this.rightArm.y = 5.2f;
					this.body.xRot = 0.5f;
					this.rightLeg.z = 4.0f;
					this.leftLeg.z = 4.0f;
					this.rightLeg.y = 12.2f;
					this.leftLeg.y = 12.2f;
				}
				this.rightArm.xRot += 0.4f;
				this.leftArm.xRot += 0.4f;
				//
				if (mob instanceof CarvedVillagerEntity carvedVillager && carvedVillager.isEndermanRace()) {
					this.head.y = -13f + 4.2f;
					if (mob.isAggressive() && !mob.isBaby()) {
						this.head.y -= 5f;
					}
					if (mob.isBaby()) {
						this.head.y += 5f;
					}
					this.body.y = -14f + 3.2f;
					this.rightArm.y = -9f + 3.2f;
					this.leftArm.y = -9f + 3.2f;

					this.rightLeg.y = -5f + 0.2f;
					this.leftLeg.y = -5f + 0.2f;
				}
			}
			ItemStack handItem = t.getMainHandItem();
			if ((t.getMainHandItem().isEmpty() || InventoryEntity.isMeleeWeapon(t.getMainHandItem())) && !t.getOffhandItem().isEmpty()) {
				handItem = t.getOffhandItem();
			}
			//弓
			if (mob.isAggressive() && mob instanceof UseBowEntity) {
				if (itemStack.getItem() instanceof BowItem) {
					if (mainHand == rightArm) {
						this.rightArm.yRot = -0.1f + this.head.yRot;
						this.leftArm.yRot = 0.1f + this.head.yRot + 0.4f;
					} else {
						this.rightArm.yRot = -0.1f + this.head.yRot - 0.4f;
						this.leftArm.yRot = 0.1f + this.head.yRot;
					}
					this.rightArm.xRot = -1.5707964f + this.head.xRot;
					this.leftArm.xRot = -1.5707964f + this.head.xRot;
				}
				else if (itemStacks.getItem() instanceof BowItem && handItem != t.getMainHandItem() && t.getUsedItemHand() == InteractionHand.OFF_HAND) {
					if (mainHand == leftArm) {
						this.rightArm.yRot = -0.1f + this.head.yRot;
						this.leftArm.yRot = 0.1f + this.head.yRot + 0.4f;
					} else {
						this.rightArm.yRot = -0.1f + this.head.yRot - 0.4f;
						this.leftArm.yRot = 0.1f + this.head.yRot;
					}
					this.rightArm.xRot = -1.5707964f + this.head.xRot;
					this.leftArm.xRot = -1.5707964f + this.head.xRot;
				}
            }
			//弩
			if (mob.isAggressive() && mob instanceof UseCrossbowEntity crossbow) {
				if (itemStack.getItem() instanceof CrossbowItem) {
					if (crossbow.isChargingCrossbow()) {
						AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, t, !mob.isLeftHanded());
					} else {
						AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, !mob.isLeftHanded());
					}
				}
				else if (itemStacks.getItem() instanceof CrossbowItem && handItem != t.getMainHandItem()) {
					if (crossbow.isChargingCrossbow()) {
						AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, t, mob.isLeftHanded());
					} else {
						AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, mob.isLeftHanded());
					}
				}
			}
			//标枪
			if (mob.isAggressive() && mob instanceof UseThrownJavelinEntity) {
				if (InventoryEntity.isRangeJavelin(itemStack) || itemStack.getItem() instanceof TridentItem) {
					if (mob.isUsingItem() && mob.getUseItem().getItem() instanceof JerotesItemThrownJavelinUse jerotesItemThrownJavelinUse && jerotesItemThrownJavelinUse.isJerotesThrownJavelin()) {
						jerotesItemThrownJavelinUse.JerotesNormalThrownJavelinAnim(mainHand, offHand, head, true);
					}
					//三叉戟
					else if (mob.isUsingItem() && mob.getUseItem().getItem() instanceof TridentItem) {
						mainHand.xRot = mainHand.xRot * 0.5f - 3.1415927f;
						mainHand.yRot = 0.0f;
					}
				}
				else if ((InventoryEntity.isRangeJavelin(itemStacks) || itemStacks.getItem() instanceof TridentItem) && handItem != t.getMainHandItem()) {
					if (mob.isUsingItem() && mob.getUseItem().getItem() instanceof JerotesItemThrownJavelinUse jerotesItemThrownJavelinUse && jerotesItemThrownJavelinUse.isJerotesThrownJavelin()) {
						jerotesItemThrownJavelinUse.JerotesNormalThrownJavelinAnim(offHand, mainHand, head, true);
					}
					//三叉戟
					else if (mob.isUsingItem() && mob.getUseItem().getItem() instanceof TridentItem) {
						offHand.xRot = offHand.xRot * 0.5f - 3.1415927f;
						offHand.yRot = 0.0f;
					}
				}
			}
			//盾
			if (mob.isAggressive()) {
				if (mob instanceof UseShieldEntity useShield && useShield.shieldCanUse() && useShield.notBowCrossbow(mob, InteractionHand.MAIN_HAND) && mob.getOffhandItem().getItem() instanceof ShieldItem && mob.isUsingItem() && mob.getUseItem().getItem() instanceof ShieldItem) {
					this.poseBlockingArm(offHand, false);
				}
				//主手盾牌
				else if (mob instanceof UseShieldEntity useShield && useShield.shieldCanUse() && useShield.notBowCrossbow(mob, InteractionHand.OFF_HAND) && mob.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ShieldItem && mob.isUsingItem() && mob.getUseItem().getItem() instanceof ShieldItem) {
					this.poseBlockingArm(mainHand, false);
				}
				//主手双手武器
				else if (mob instanceof UseShieldEntity && mob.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ItemTwoHanded itemTwoHanded && itemTwoHanded.canBlock() && mob.getOffhandItem().isEmpty() && mob.isUsingItem() && mob.getUseItem().getItem() instanceof ItemTwoHanded && mob.attackAnim <= 0.0F) {
					this.poseBlockingArm(mainHand, false);
				}
				//副手双手武器
				else if (mob instanceof UseShieldEntity && mob.getOffhandItem().getItem() instanceof ItemTwoHanded itemTwoHanded && itemTwoHanded.canBlock() && mob.getItemInHand(InteractionHand.MAIN_HAND).isEmpty() && mob.isUsingItem() && mob.getUseItem().getItem() instanceof ItemTwoHanded && mob.attackAnim <= 0.0F) {
					this.poseBlockingArm(offHand, false);
				}
//				//主手矛
//				else if (t instanceof InventoryEntity inventoryEntity && InventoryEntity.isSpear(inventoryEntity, t.getItemInHand(InteractionHand.MAIN_HAND))) {
//					this.poseBlockingArm(mainHand, false);
//				}
//				//副手矛
//				else if (t instanceof InventoryEntity inventoryEntity && InventoryEntity.isSpear(inventoryEntity, t.getItemInHand(InteractionHand.OFF_HAND))) {
//					this.poseBlockingArm(offHand, false);
//				}
//				//主手矛
//				else if (InventoryEntity.isSpear(t.getItemInHand(InteractionHand.MAIN_HAND))) {
//					this.poseBlockingArm(mainHand, false);
//				}
//				//副手矛
//				else if (InventoryEntity.isSpear(t.getItemInHand(InteractionHand.OFF_HAND))) {
//					this.poseBlockingArm(offHand, false);
//				}
			}
			//蛇龙人特殊
			if (mob instanceof SerponEntity serpon) {
				if (serpon.isAggressive() && itemStack.getItem() instanceof SwordItem && !(itemStack.getItem() instanceof ItemTwoHanded) && !(itemStack.getItem() instanceof ItemToolBaseDagger) && this.attackTime <= 0.0f) {
					this.holdWeaponHigh(t);
				}
				if (itemStack.getItem() instanceof JerotesItemThrownJavelinUse jerotesItemThrownJavelinUse && jerotesItemThrownJavelinUse.isJerotesThrownJavelin() && !(serpon.getUseItem() == itemStack) && itemStacks.isEmpty()) {
					this.poseBlockingArm(mainHand, false);
					this.poseBlockingArm(offHand, false);
				}
				if (serpon.isAggressive() && itemStack.getItem() instanceof SerponBlowpipe) {
					leftArm.xRot = Mth.clamp(this.head.xRot, -1.2f, 1.2f) - 1.4835298f;
					leftArm.yRot = this.head.yRot + 0.5235988f;
					rightArm.xRot = Mth.clamp(this.head.xRot, -1.2f, 1.2f) - 1.4835298f;
					rightArm.yRot = this.head.yRot - 0.5235988f;
				}
			}
			//施法
			if (mob instanceof SpellUseEntity spellUseEntity && spellUseEntity.isSpellHumanoid()) {
				if (spellUseEntity.isMagicUseStyle()) {
					if (spellUseEntity.isSpellHumanoidTwoHanded()) {
						this.rightArm.z = 0.0f;
						this.rightArm.x = -5.0f;
						this.rightArm.xRot = Mth.cos(ageInTicks * 0.6662f) * 0.25f;
						this.rightArm.zRot = 2.3561945f;
						this.rightArm.yRot = 0.0f;
						this.leftArm.z = 0.0f;
						this.leftArm.x = 5.0f;
						this.leftArm.xRot = Mth.cos(ageInTicks * 0.6662f) * 0.25f;
						this.leftArm.zRot = -2.3561945f;
						this.leftArm.yRot = 0.0f;
					}
					else {
						if (mainHand == rightArm) {
							this.rightArm.z = 0.0f;
							this.rightArm.x = -5.0f;
							this.rightArm.xRot = Mth.cos(ageInTicks * 0.6662f) * 0.25f;
							this.rightArm.zRot = 2.3561945f;
							this.rightArm.yRot = 0.0f;
						} else {
							this.leftArm.z = 0.0f;
							this.leftArm.x = 5.0f;
							this.leftArm.xRot = Mth.cos(ageInTicks * 0.6662f) * 0.25f;
							this.leftArm.zRot = -2.3561945f;
							this.leftArm.yRot = 0.0f;
						}
					}
				}
			}
			specialAnim(t, this, limbSwing, limbSwingAmount, ageInTicks, netbipedHeadYaw, bipedHeadPitch);
			//活化盔甲特殊
			if (mob instanceof SecondRounderAnimatedArmorEntity animatedArmor) {
				if (animatedArmor.isInSittingPose() && !animatedArmor.isWander()) {
					this.head.xRot = 0.0f;
					this.body.xRot = 0.0f;
					this.rightArm.xRot = 0.0f;
					this.leftArm.xRot = 0.0f;
					this.rightLeg.xRot = 0.0f;
					this.leftLeg.xRot = 0.0f;

					this.head.yRot = 0.0f;
					this.body.yRot = 0.0f;
					this.rightArm.yRot = 0.0f;
					this.leftArm.yRot = 0.0f;
					this.rightLeg.yRot = 0.0f;
					this.leftLeg.yRot = 0.0f;

					this.head.zRot = 0.0f;
					this.body.zRot = 0.0f;
					this.rightArm.zRot = 0.0f;
					this.leftArm.zRot = 0.0f;
					this.rightLeg.zRot = 0.0f;
					this.leftLeg.zRot = 0.0f;
				}
			}
		}

		this.hat.copyFrom(this.head);
		this.jacket.copyFrom(this.body);
		this.leftPants.copyFrom(this.leftLeg);
		this.rightPants.copyFrom(this.rightLeg);
		this.leftSleeve.copyFrom(this.leftArm);
		this.rightSleeve.copyFrom(this.rightArm);
	}

	public boolean shouldTaczIdle() {
		return false;
	}

	@Override
	protected void setupAttackAnimation(T t, float f) {
		if (t instanceof SerponEntity serpon && serpon.getOffhandItem().getItem() instanceof SwordItem) {
			if (this.attackTime > 0.0f && ((Mob) t).isAggressive()) {
				AnimationUtils.swingWeaponDown(this.rightArm, this.leftArm, ((Mob) t), this.attackTime, f);
				return;
			}
		}
		super.setupAttackAnimation(t, f);
	}
	private void holdWeaponHigh(T t) {
		if (((Mob)t).isLeftHanded()) {
			this.leftArm.xRot = -1.8f;
		} else {
			this.rightArm.xRot = -1.8f;
		}
	}
	public void poseBlockingArm(ModelPart modelPart, boolean bl) {
		if (modelPart == leftArm) {
			modelPart.xRot = modelPart.xRot * 0.5f - 0.9424779f + Mth.clamp(this.head.xRot, -1.3962634f, 0.43633232f);
			modelPart.yRot = (bl ? -30.0f : 30.0f) * 0.017453292f + Mth.clamp(this.head.yRot, -0.5235988f, 0.5235988f);
		}
		else {
			modelPart.xRot = modelPart.xRot * 0.5f - 0.9424779f + Mth.clamp(this.head.xRot, -1.3962634f, 0.43633232f);
			modelPart.yRot = (bl ? 30.0f : -30.0f) * 0.017453292f + Mth.clamp(this.head.yRot, -0.5235988f, 0.5235988f);
		}
	}
}