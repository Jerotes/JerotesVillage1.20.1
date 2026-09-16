package com.jerotes.jerotesvillage.client.model;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.animation.DuneshocklerAnimation;
import com.jerotes.jerotesvillage.entity.Monster.Elite.DuneshocklerEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;


public class Modelduneshockler<T extends DuneshocklerEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "duneshockler"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart down_body;
	private final ModelPart left_front_leg;
	private final ModelPart left_front_leg_2;
	private final ModelPart left_front_leg_3;
	private final ModelPart right_front_leg;
	private final ModelPart right_front_leg_2;
	private final ModelPart right_front_leg_3;
	private final ModelPart left_hind_leg;
	private final ModelPart left_hind_leg_2;
	private final ModelPart left_hind_leg_3;
	private final ModelPart right_hind_leg;
	private final ModelPart right_hind_leg_2;
	private final ModelPart right_hind_leg_3;
	private final ModelPart middle_body;
	private final ModelPart up_body;
	private final ModelPart right_arm;
	private final ModelPart right_arm_2;
	private final ModelPart right_arm_3;
	private final ModelPart left_arm;
	private final ModelPart left_arm_2;
	private final ModelPart left_arm_3;
	private final ModelPart neck;
	private final ModelPart head;
	private final ModelPart head_up;
	private final ModelPart left_antenna;
	private final ModelPart left_antenna2;
	private final ModelPart left_antenna3;
	private final ModelPart left_antenna4;
	private final ModelPart right_antenna;
	private final ModelPart right_antenna2;
	private final ModelPart right_antenna3;
	private final ModelPart right_antenna4;
	private final ModelPart head_3;
	private final ModelPart jaw;

	public Modelduneshockler(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.down_body = this.body.getChild("down_body");
		this.right_front_leg = this.down_body.getChild("right_front_leg");
		this.right_front_leg_2 = this.right_front_leg.getChild("right_front_leg_2");
		this.right_front_leg_3 = this.right_front_leg_2.getChild("right_front_leg_3");
		this.left_front_leg = this.down_body.getChild("left_front_leg");
		this.left_front_leg_2 = this.left_front_leg.getChild("left_front_leg_2");
		this.left_front_leg_3 = this.left_front_leg_2.getChild("left_front_leg_3");
		this.right_hind_leg = this.down_body.getChild("right_hind_leg");
		this.right_hind_leg_2 = this.right_hind_leg.getChild("right_hind_leg_2");
		this.right_hind_leg_3 = this.right_hind_leg_2.getChild("right_hind_leg_3");
		this.left_hind_leg = this.down_body.getChild("left_hind_leg");
		this.left_hind_leg_2 = this.left_hind_leg.getChild("left_hind_leg_2");
		this.left_hind_leg_3 = this.left_hind_leg_2.getChild("left_hind_leg_3");
		this.middle_body = this.down_body.getChild("middle_body");
		this.up_body = this.middle_body.getChild("up_body");
		this.right_arm = this.up_body.getChild("right_arm");
		this.right_arm_2 = this.right_arm.getChild("right_arm_2");
		this.right_arm_3 = this.right_arm_2.getChild("right_arm_3");
		this.left_arm = this.up_body.getChild("left_arm");
		this.left_arm_2 = this.left_arm.getChild("left_arm_2");
		this.left_arm_3 = this.left_arm_2.getChild("left_arm_3");
		this.neck = this.up_body.getChild("neck");
		this.head = this.neck.getChild("head");
		this.head_up = this.head.getChild("head_up");
		this.left_antenna = this.head_up.getChild("left_antenna");
		this.left_antenna2 = this.left_antenna.getChild("left_antenna2");
		this.left_antenna3 = this.left_antenna2.getChild("left_antenna3");
		this.left_antenna4 = this.left_antenna3.getChild("left_antenna4");
		this.right_antenna = this.head_up.getChild("right_antenna");
		this.right_antenna2 = this.right_antenna.getChild("right_antenna2");
		this.right_antenna3 = this.right_antenna2.getChild("right_antenna3");
		this.right_antenna4 = this.right_antenna3.getChild("right_antenna4");
		this.head_3 = this.head_up.getChild("head_3");
		this.jaw = this.head.getChild("jaw");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -41.0F, 5.5F));

		PartDefinition down_body = body.addOrReplaceChild("down_body", CubeListBuilder.create(), PartPose.offset(0.0F, 30.0F, 22.0F));

		PartDefinition down_body_r1 = down_body.addOrReplaceChild("down_body_r1", CubeListBuilder.create().texOffs(20, 462).addBox(-2.0F, 8.5F, 10.0F, 4.0F, 20.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(80, 342).addBox(-11.0F, 8.5F, -8.0F, 22.0F, 9.0F, 18.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(0.0F, 3.1694F, -11.2927F, 1.4399F, 0.0F, 0.0F));

		PartDefinition down_body_r2 = down_body.addOrReplaceChild("down_body_r2", CubeListBuilder.create().texOffs(0, 340).addBox(-10.0F, -9.0F, -10.0F, 20.0F, 18.0F, 20.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(0.0F, 4.3694F, 10.7073F, 0.0F, -0.7854F, 0.0F));

		PartDefinition down_body_r3 = down_body.addOrReplaceChild("down_body_r3", CubeListBuilder.create().texOffs(22, 447).addBox(-3.0F, 9.5F, 10.0F, 6.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(10, 462).addBox(-2.0F, -2.5F, 10.0F, 4.0F, 17.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(80, 293).addBox(-11.0F, -7.5F, -8.0F, 22.0F, 25.0F, 18.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(0.0F, -4.6306F, -12.7927F, 0.9163F, 0.0F, 0.0F));

		PartDefinition down_body_r4 = down_body.addOrReplaceChild("down_body_r4", CubeListBuilder.create().texOffs(22, 447).addBox(-3.0F, -3.5F, 10.0F, 6.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 462).addBox(-2.0F, -2.5F, 10.0F, 4.0F, 24.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 293).addBox(-11.0F, -7.5F, -8.0F, 22.0F, 29.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -25.6306F, -23.2927F, 0.3491F, 0.0F, 0.0F));

		PartDefinition down_body_r5 = down_body.addOrReplaceChild("down_body_r5", CubeListBuilder.create().texOffs(22, 447).addBox(-3.0F, -2.5F, 8.0F, 6.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.2192F, -16.283F, 0.3491F, 0.0F, 0.0F));

		PartDefinition down_body_r6 = down_body.addOrReplaceChild("down_body_r6", CubeListBuilder.create().texOffs(160, 293).addBox(-2.0F, -14.5F, -9.0F, 5.0F, 29.0F, 18.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-11.0F, -19.3947F, -19.9589F, 0.3442F, 0.0594F, -0.1642F));

		PartDefinition down_body_r7 = down_body.addOrReplaceChild("down_body_r7", CubeListBuilder.create().texOffs(160, 293).mirror().addBox(-3.0F, -14.5F, -9.0F, 5.0F, 29.0F, 18.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(11.0F, -19.3947F, -19.9589F, 0.3442F, -0.0594F, 0.1642F));

		PartDefinition right_front_leg = down_body.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-14.2431F, -26.8132F, -25.0818F));

		PartDefinition right_front_leg_r1 = right_front_leg.addOrReplaceChild("right_front_leg_r1", CubeListBuilder.create().texOffs(486, 195).addBox(-3.5F, -14.5F, -3.0F, 7.0F, 29.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-1.0F, 10.5F, -1.5F, -0.1434F, 0.1234F, 0.225F));

		PartDefinition right_front_leg_2 = right_front_leg.addOrReplaceChild("right_front_leg_2", CubeListBuilder.create(), PartPose.offset(-2.7245F, 24.2305F, -2.6465F));

		PartDefinition right_front_leg_r2 = right_front_leg_2.addOrReplaceChild("right_front_leg_r2", CubeListBuilder.create().texOffs(444, 244).addBox(-4.5F, -14.5F, -4.0F, 8.0F, 29.0F, 7.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-3.2755F, -9.7305F, -9.8535F, 0.8678F, 0.0594F, -0.1642F));

		PartDefinition right_front_leg_3 = right_front_leg_2.addOrReplaceChild("right_front_leg_3", CubeListBuilder.create(), PartPose.offset(-9.9196F, -15.2448F, -18.356F));

		PartDefinition right_front_leg_r3 = right_front_leg_3.addOrReplaceChild("right_front_leg_r3", CubeListBuilder.create().texOffs(492, 336).addBox(-2.5F, 32.5F, -5.0F, 4.0F, 17.0F, 6.0F, new CubeDeformation(0.1F))
				.texOffs(478, 280).addBox(-4.5F, -14.5F, -5.0F, 8.0F, 47.0F, 9.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(2.6441F, 7.5143F, -3.4975F, -0.1309F, 0.0F, 0.0873F));

		PartDefinition left_front_leg = down_body.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(14.2431F, -26.8132F, -25.0818F));

		PartDefinition left_front_leg_r1 = left_front_leg.addOrReplaceChild("left_front_leg_r1", CubeListBuilder.create().texOffs(486, 195).mirror().addBox(-3.5F, -14.5F, -3.0F, 7.0F, 29.0F, 6.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(1.0F, 10.5F, -1.5F, -0.1434F, -0.1234F, -0.225F));

		PartDefinition left_front_leg_2 = left_front_leg.addOrReplaceChild("left_front_leg_2", CubeListBuilder.create(), PartPose.offset(2.7245F, 24.2305F, -2.6465F));

		PartDefinition left_front_leg_r2 = left_front_leg_2.addOrReplaceChild("left_front_leg_r2", CubeListBuilder.create().texOffs(444, 244).mirror().addBox(-3.5F, -14.5F, -4.0F, 8.0F, 29.0F, 7.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(3.2755F, -9.7305F, -9.8535F, 0.8678F, -0.0594F, 0.1642F));

		PartDefinition left_front_leg_3 = left_front_leg_2.addOrReplaceChild("left_front_leg_3", CubeListBuilder.create(), PartPose.offset(9.9196F, -15.2448F, -18.356F));

		PartDefinition left_front_leg_r3 = left_front_leg_3.addOrReplaceChild("left_front_leg_r3", CubeListBuilder.create().texOffs(492, 336).mirror().addBox(-1.5F, 32.5F, -5.0F, 4.0F, 17.0F, 6.0F, new CubeDeformation(0.1F)).mirror(false)
				.texOffs(478, 280).mirror().addBox(-3.5F, -14.5F, -5.0F, 8.0F, 47.0F, 9.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-2.6441F, 7.5143F, -3.4975F, -0.1309F, 0.0F, -0.0873F));

		PartDefinition right_hind_leg = down_body.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-14.2431F, -0.8132F, -0.9182F));

		PartDefinition right_hind_leg_r1 = right_hind_leg.addOrReplaceChild("right_hind_leg_r1", CubeListBuilder.create().texOffs(486, 195).addBox(-3.5F, -14.5F, -3.0F, 7.0F, 29.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-1.0F, 10.5F, 1.5F, 0.1434F, -0.1234F, 0.225F));

		PartDefinition right_hind_leg_2 = right_hind_leg.addOrReplaceChild("right_hind_leg_2", CubeListBuilder.create(), PartPose.offset(-2.7245F, 24.2305F, 2.6465F));

		PartDefinition right_hind_leg_r2 = right_hind_leg_2.addOrReplaceChild("right_hind_leg_r2", CubeListBuilder.create().texOffs(482, 230).addBox(-4.5F, -28.5F, -3.0F, 8.0F, 43.0F, 7.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-3.2755F, -15.7305F, 4.5535F, -0.2656F, 0.045F, -0.1687F));

		PartDefinition right_hind_leg_3 = right_hind_leg_2.addOrReplaceChild("right_hind_leg_3", CubeListBuilder.create(), PartPose.offset(-11.9196F, -40.2448F, 10.356F));

		PartDefinition right_hind_leg_r3 = right_hind_leg_3.addOrReplaceChild("right_hind_leg_r3", CubeListBuilder.create().texOffs(492, 336).addBox(-2.5F, 32.5F, -1.0F, 4.0F, 17.0F, 6.0F, new CubeDeformation(0.1F))
				.texOffs(478, 280).addBox(-4.5F, -14.5F, -4.0F, 8.0F, 47.0F, 9.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(2.6441F, 7.5143F, 3.4975F, 0.1309F, 0.0F, 0.0873F));

		PartDefinition left_hind_leg = down_body.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(14.2431F, -0.8132F, -0.9182F));

		PartDefinition left_hind_leg_r1 = left_hind_leg.addOrReplaceChild("left_hind_leg_r1", CubeListBuilder.create().texOffs(486, 195).mirror().addBox(-3.5F, -14.5F, -3.0F, 7.0F, 29.0F, 6.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(1.0F, 10.5F, 1.5F, 0.1434F, 0.1234F, -0.225F));

		PartDefinition left_hind_leg_2 = left_hind_leg.addOrReplaceChild("left_hind_leg_2", CubeListBuilder.create(), PartPose.offset(2.7245F, 24.2305F, 2.6465F));

		PartDefinition left_hind_leg_r2 = left_hind_leg_2.addOrReplaceChild("left_hind_leg_r2", CubeListBuilder.create().texOffs(482, 230).mirror().addBox(-3.5F, -28.5F, -3.0F, 8.0F, 43.0F, 7.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(3.2755F, -15.7305F, 4.5535F, -0.2656F, -0.045F, 0.1687F));

		PartDefinition left_hind_leg_3 = left_hind_leg_2.addOrReplaceChild("left_hind_leg_3", CubeListBuilder.create(), PartPose.offset(11.9196F, -40.2448F, 10.356F));

		PartDefinition left_hind_leg_r3 = left_hind_leg_3.addOrReplaceChild("left_hind_leg_r3", CubeListBuilder.create().texOffs(492, 336).mirror().addBox(-1.5F, 32.5F, -1.0F, 4.0F, 17.0F, 6.0F, new CubeDeformation(0.1F)).mirror(false)
				.texOffs(478, 280).mirror().addBox(-3.5F, -14.5F, -4.0F, 8.0F, 47.0F, 9.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-2.6441F, 7.5143F, 3.4975F, 0.1309F, 0.0F, -0.0873F));

		PartDefinition middle_body = down_body.addOrReplaceChild("middle_body", CubeListBuilder.create(), PartPose.offset(0.0F, -30.1324F, -23.1853F));

		PartDefinition middle_body_r1 = middle_body.addOrReplaceChild("middle_body_r1", CubeListBuilder.create().texOffs(160, 238).mirror().addBox(-2.5F, -10.5F, -9.0F, 5.0F, 37.0F, 18.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(10.5F, -24.7546F, 1.5916F, -0.1304F, -0.0114F, -0.0865F));

		PartDefinition middle_body_r2 = middle_body.addOrReplaceChild("middle_body_r2", CubeListBuilder.create().texOffs(160, 238).addBox(-2.5F, -10.5F, -9.0F, 5.0F, 37.0F, 18.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-10.5F, -24.7546F, 1.5916F, -0.1304F, 0.0114F, 0.0865F));

		PartDefinition middle_body_r3 = middle_body.addOrReplaceChild("middle_body_r3", CubeListBuilder.create().texOffs(22, 438).addBox(-3.0F, 9.5F, 10.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(22, 438).addBox(-3.0F, -1.5F, 10.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 424).addBox(-2.0F, -8.5F, 10.0F, 4.0F, 35.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(80, 238).addBox(-11.0F, -5.5F, -8.0F, 22.0F, 29.0F, 18.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(0.0F, -24.7982F, 3.5926F, -0.3054F, 0.0F, 0.0F));

		PartDefinition middle_body_r4 = middle_body.addOrReplaceChild("middle_body_r4", CubeListBuilder.create().texOffs(50, 381).addBox(10.0F, 0.5F, -15.0F, 0.0F, 35.0F, 25.0F, new CubeDeformation(0.0F))
				.texOffs(50, 381).addBox(-10.0F, 0.5F, -15.0F, 0.0F, 35.0F, 25.0F, new CubeDeformation(0.0F))
				.texOffs(0, 238).addBox(-11.0F, -10.5F, -8.0F, 22.0F, 37.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -24.7982F, 0.5926F, -0.1309F, 0.0F, 0.0F));

		PartDefinition up_body = middle_body.addOrReplaceChild("up_body", CubeListBuilder.create().texOffs(0, 378).addBox(-2.0F, -31.6351F, -4.1441F, 4.0F, 2.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -27.3112F, 1.3028F));

		PartDefinition up_body_r1 = up_body.addOrReplaceChild("up_body_r1", CubeListBuilder.create().texOffs(164, 149).addBox(-3.0F, -15.0F, -4.0F, 6.0F, 30.0F, 8.0F, new CubeDeformation(0.175F)), PartPose.offsetAndRotation(-16.2645F, -13.6922F, 2.9986F, 0.2208F, -0.2744F, -0.1884F));

		PartDefinition up_body_r2 = up_body.addOrReplaceChild("up_body_r2", CubeListBuilder.create().texOffs(96, 187).addBox(-6.5F, -16.0F, -10.0F, 18.0F, 31.0F, 20.0F, new CubeDeformation(0.275F)), PartPose.offsetAndRotation(-7.5F, -13.1946F, 0.1766F, 0.1828F, -0.3006F, -0.0547F));

		PartDefinition up_body_r3 = up_body.addOrReplaceChild("up_body_r3", CubeListBuilder.create().texOffs(164, 149).mirror().addBox(-3.0F, -15.0F, -4.0F, 6.0F, 30.0F, 8.0F, new CubeDeformation(0.15F)).mirror(false), PartPose.offsetAndRotation(16.2645F, -13.6922F, 2.9986F, 0.2208F, 0.2744F, 0.1884F));

		PartDefinition up_body_r4 = up_body.addOrReplaceChild("up_body_r4", CubeListBuilder.create().texOffs(96, 187).mirror().addBox(-11.5F, -16.0F, -10.0F, 18.0F, 31.0F, 20.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(7.5F, -13.1946F, 0.1766F, 0.1828F, 0.3006F, 0.0547F));

		PartDefinition up_body_r5 = up_body.addOrReplaceChild("up_body_r5", CubeListBuilder.create().texOffs(0, 141).mirror().addBox(-5.0F, -13.0F, -10.0F, 10.0F, 26.0F, 20.0F, new CubeDeformation(0.04F)).mirror(false), PartPose.offsetAndRotation(0.3F, -22.2332F, -6.8725F, 1.4123F, 0.0735F, 0.4305F));

		PartDefinition up_body_r6 = up_body.addOrReplaceChild("up_body_r6", CubeListBuilder.create().texOffs(0, 141).addBox(-5.0F, -13.0F, -10.0F, 10.0F, 26.0F, 20.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(-0.3F, -22.2332F, -6.8725F, 1.4123F, -0.0735F, -0.4305F));

		PartDefinition up_body_r7 = up_body.addOrReplaceChild("up_body_r7", CubeListBuilder.create().texOffs(60, 141).addBox(-5.0F, -13.0F, -10.0F, 10.0F, 26.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -22.2332F, -6.8725F, 1.3963F, 0.0F, 0.0F));

		PartDefinition up_body_r8 = up_body.addOrReplaceChild("up_body_r8", CubeListBuilder.create().texOffs(22, 425).addBox(-3.0F, 10.0F, 10.0F, 6.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(22, 410).addBox(-3.0F, -4.0F, 10.0F, 6.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 397).addBox(-2.0F, -10.0F, 10.0F, 4.0F, 20.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(22, 397).addBox(-3.0F, -15.0F, 10.0F, 6.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 187).addBox(-14.0F, -16.0F, -10.0F, 28.0F, 31.0F, 20.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -13.1946F, -1.3234F, 0.1745F, 0.0F, 0.0F));

		PartDefinition up_body_r9 = up_body.addOrReplaceChild("up_body_r9", CubeListBuilder.create().texOffs(50, 369).addBox(0.0F, -12.0F, -5.5F, 0.0F, 28.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.0F, -6.9111F, -13.9238F, 0.1739F, 0.0151F, -0.0859F));

		PartDefinition up_body_r10 = up_body.addOrReplaceChild("up_body_r10", CubeListBuilder.create().texOffs(50, 369).addBox(0.0F, -12.0F, -5.5F, 0.0F, 28.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, -6.9111F, -13.9238F, 0.1739F, -0.0151F, 0.0859F));

		PartDefinition right_arm = up_body.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-14.6816F, -22.482F, -4.1375F));

		PartDefinition right_arm_r1 = right_arm.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(428, 0).addBox(-5.0F, -8.0F, -5.5F, 10.0F, 16.0F, 11.0F, new CubeDeformation(0.75F)), PartPose.offsetAndRotation(-2.495F, 1.465F, 0.0F, 0.0F, 0.0F, 0.48F));

		PartDefinition right_arm_r2 = right_arm.addOrReplaceChild("right_arm_r2", CubeListBuilder.create().texOffs(428, 27).addBox(-4.0F, -15.5F, -5.5F, 8.0F, 36.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.3184F, 11.8055F, 0.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition right_arm_2 = right_arm.addOrReplaceChild("right_arm_2", CubeListBuilder.create(), PartPose.offset(-6.2688F, 26.4169F, -2.3344F));

		PartDefinition right_arm_r3 = right_arm_2.addOrReplaceChild("right_arm_r3", CubeListBuilder.create().texOffs(470, 0).addBox(-5.0F, -10.0F, -5.5F, 10.0F, 20.0F, 11.0F, new CubeDeformation(1.1F)), PartPose.offsetAndRotation(-2.4004F, 31.5739F, -9.6785F, 0.222F, -0.0716F, 0.0151F));

		PartDefinition right_arm_r4 = right_arm_2.addOrReplaceChild("right_arm_r4", CubeListBuilder.create().texOffs(457, 114).addBox(-1.0F, -13.5F, -2.0F, 2.0F, 27.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-1.4955F, 10.24F, -7.1531F, -0.3927F, 0.0F, 0.1745F));

		PartDefinition right_arm_r5 = right_arm_2.addOrReplaceChild("right_arm_r5", CubeListBuilder.create().texOffs(429, 74).addBox(-5.0F, -15.5F, -5.5F, 10.0F, 29.0F, 11.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-2.0495F, 16.3886F, -2.6656F, -0.3897F, -0.05F, 0.0535F));

		PartDefinition right_arm_r6 = right_arm_2.addOrReplaceChild("right_arm_r6", CubeListBuilder.create().texOffs(447, 114).addBox(-1.5F, -5.0F, -1.0F, 3.0F, 10.0F, 2.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(-13.4438F, -7.272F, 5.5534F, -0.3843F, -0.0829F, -0.0275F));

		PartDefinition right_arm_r7 = right_arm_2.addOrReplaceChild("right_arm_r7", CubeListBuilder.create().texOffs(429, 114).addBox(-7.0F, -21.5F, -1.5F, 7.0F, 28.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-2.019F, 15.4638F, -2.2861F, -0.3591F, -0.1624F, -0.2323F));

		PartDefinition right_arm_3 = right_arm_2.addOrReplaceChild("right_arm_3", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.7628F, 30.9334F, -4.8306F, 1.2217F, 0.0F, 0.0F));

		PartDefinition right_arm_r8 = right_arm_3.addOrReplaceChild("right_arm_r8", CubeListBuilder.create().texOffs(490, 101).addBox(-1.6544F, -10.7134F, 1.7844F, 4.0F, 21.0F, 7.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(-0.4985F, 51.9641F, -14.6033F, 0.3095F, -0.0727F, 0.0088F));

		PartDefinition right_arm_r9 = right_arm_3.addOrReplaceChild("right_arm_r9", CubeListBuilder.create().texOffs(484, 115).addBox(0.0F, -33.0F, -7.0F, 0.0F, 66.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6928F, 35.8858F, -3.47F, -0.0404F, -0.0652F, 0.0332F));

		PartDefinition right_arm_r10 = right_arm_3.addOrReplaceChild("right_arm_r10", CubeListBuilder.create().texOffs(486, 61).addBox(-2.7749F, -9.2191F, 2.8316F, 6.0F, 33.0F, 7.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(0.7133F, 18.032F, -18.3698F, -0.0404F, -0.0652F, 0.0332F));

		PartDefinition right_arm_r11 = right_arm_3.addOrReplaceChild("right_arm_r11", CubeListBuilder.create().texOffs(470, 31).addBox(-5.0F, -9.5F, -5.5F, 10.0F, 19.0F, 11.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.8964F, 7.4836F, -9.3433F, -0.45F, 0.5109F, -0.199F));

		PartDefinition left_arm = up_body.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(14.6816F, -22.482F, -4.1375F));

		PartDefinition left_arm_r1 = left_arm.addOrReplaceChild("left_arm_r1", CubeListBuilder.create().texOffs(428, 0).mirror().addBox(-5.0F, -8.0F, -5.5F, 10.0F, 16.0F, 11.0F, new CubeDeformation(0.75F)).mirror(false), PartPose.offsetAndRotation(2.495F, 1.465F, 0.0F, 0.0F, 0.0F, -0.48F));

		PartDefinition left_arm_r2 = left_arm.addOrReplaceChild("left_arm_r2", CubeListBuilder.create().texOffs(428, 27).mirror().addBox(-4.0F, -15.5F, -5.5F, 8.0F, 36.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.3184F, 11.8055F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition left_arm_2 = left_arm.addOrReplaceChild("left_arm_2", CubeListBuilder.create(), PartPose.offset(6.2688F, 26.4169F, -2.3344F));

		PartDefinition left_arm_r3 = left_arm_2.addOrReplaceChild("left_arm_r3", CubeListBuilder.create().texOffs(470, 0).mirror().addBox(-5.0F, -10.0F, -5.5F, 10.0F, 20.0F, 11.0F, new CubeDeformation(1.1F)).mirror(false), PartPose.offsetAndRotation(2.4004F, 31.5739F, -9.6785F, 0.222F, 0.0716F, -0.0151F));

		PartDefinition left_arm_r4 = left_arm_2.addOrReplaceChild("left_arm_r4", CubeListBuilder.create().texOffs(457, 114).mirror().addBox(-1.0F, -13.5F, -2.0F, 2.0F, 27.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(1.4956F, 10.24F, -7.1531F, -0.3927F, 0.0F, -0.1745F));

		PartDefinition left_arm_r5 = left_arm_2.addOrReplaceChild("left_arm_r5", CubeListBuilder.create().texOffs(429, 74).mirror().addBox(-5.0F, -15.5F, -5.5F, 10.0F, 29.0F, 11.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(2.0495F, 16.3886F, -2.6656F, -0.3897F, 0.05F, -0.0535F));

		PartDefinition left_arm_r6 = left_arm_2.addOrReplaceChild("left_arm_r6", CubeListBuilder.create().texOffs(447, 114).mirror().addBox(-1.5F, -5.0F, -1.0F, 3.0F, 10.0F, 2.0F, new CubeDeformation(0.15F)).mirror(false), PartPose.offsetAndRotation(13.4438F, -7.2719F, 5.5534F, -0.3843F, 0.0829F, 0.0275F));

		PartDefinition left_arm_r7 = left_arm_2.addOrReplaceChild("left_arm_r7", CubeListBuilder.create().texOffs(429, 114).mirror().addBox(0.0F, -21.5F, -1.5F, 7.0F, 28.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(2.019F, 15.4638F, -2.2861F, -0.3591F, 0.1624F, 0.2323F));

		PartDefinition left_arm_3 = left_arm_2.addOrReplaceChild("left_arm_3", CubeListBuilder.create(), PartPose.offsetAndRotation(3.7628F, 30.9334F, -4.8306F, 1.2217F, 0.0F, 0.0F));

		PartDefinition left_arm_r8 = left_arm_3.addOrReplaceChild("left_arm_r8", CubeListBuilder.create().texOffs(490, 101).mirror().addBox(-2.3456F, -10.7134F, 1.7844F, 4.0F, 21.0F, 7.0F, new CubeDeformation(-0.15F)).mirror(false), PartPose.offsetAndRotation(0.4985F, 51.9641F, -14.6033F, 0.3095F, 0.0727F, -0.0088F));

		PartDefinition left_arm_r9 = left_arm_3.addOrReplaceChild("left_arm_r9", CubeListBuilder.create().texOffs(484, 115).mirror().addBox(-0.1602F, -15.7258F, 8.6306F, 0.0F, 66.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(486, 61).mirror().addBox(-3.2251F, -9.2191F, 2.8316F, 6.0F, 33.0F, 7.0F, new CubeDeformation(-0.15F)).mirror(false), PartPose.offsetAndRotation(-0.7133F, 18.032F, -18.3698F, -0.0404F, 0.0652F, -0.0332F));

		PartDefinition left_arm_r10 = left_arm_3.addOrReplaceChild("left_arm_r10", CubeListBuilder.create().texOffs(470, 31).mirror().addBox(-5.0F, -9.5F, -5.5F, 10.0F, 19.0F, 11.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(-0.8963F, 7.4836F, -9.3433F, -0.45F, -0.5109F, 0.199F));

		PartDefinition neck = up_body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(120, 163).addBox(-6.0F, -6.5417F, -2.9167F, 12.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -15.0148F, -9.7009F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 59).addBox(-8.0091F, -0.3052F, -9.917F, 16.0F, 5.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 108).addBox(-8.0091F, 3.6948F, 0.083F, 16.0F, 5.0F, 6.0F, new CubeDeformation(0.05F))
				.texOffs(0, 97).addBox(-8.0091F, 4.6948F, -7.917F, 16.0F, 2.0F, 9.0F, new CubeDeformation(0.025F))
				.texOffs(60, 18).mirror().addBox(5.9909F, -9.3052F, -5.917F, 2.0F, 9.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(60, 18).addBox(-8.0091F, -9.3052F, -5.917F, 2.0F, 9.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(0, 119).addBox(-6.0091F, -12.3052F, -3.917F, 12.0F, 12.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0091F, -0.2365F, -8.9996F));

		PartDefinition head_up = head.addOrReplaceChild("head_up", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0638F, -4.2756F, -1.5492F, -0.6109F, 0.0F, 0.0F));

		PartDefinition right_eye_r1 = head_up.addOrReplaceChild("right_eye_r1", CubeListBuilder.create().texOffs(48, 59).addBox(-2.5F, -2.5F, -0.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-8.5582F, -0.9515F, -6.4151F, 0.0F, 0.7854F, 0.0F));

		PartDefinition right_eye_r2 = head_up.addOrReplaceChild("right_eye_r2", CubeListBuilder.create().texOffs(60, 59).addBox(-6.0F, -3.0F, -10.0F, 7.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(78, 54).mirror().addBox(8.0F, -3.0F, -1.0F, 2.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.073F, -1.4515F, -1.4654F, 0.0F, 0.7854F, 0.0F));

		PartDefinition left_eye_r1 = head_up.addOrReplaceChild("left_eye_r1", CubeListBuilder.create().texOffs(48, 65).mirror().addBox(-0.5F, -2.5F, -2.5F, 1.0F, 5.0F, 5.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(8.4123F, -0.9515F, -6.4151F, 0.0F, 0.7854F, 0.0F));

		PartDefinition head_r1 = head_up.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(90, 99).addBox(-1.0F, -8.0F, -3.5F, 2.0F, 13.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-15.5903F, 4.1552F, -2.8406F, 1.0036F, 0.7854F, 0.0F));

		PartDefinition head_r2 = head_up.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(90, 84).addBox(-1.0F, -5.0F, -2.5F, 2.0F, 10.0F, 5.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(-15.5903F, 4.1552F, -2.8406F, 0.48F, 0.7854F, 0.0F));

		PartDefinition head_r3 = head_up.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(90, 68).addBox(-8.0F, -2.0F, -5.5F, 2.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 36).addBox(-8.0F, -10.0F, -1.5F, 16.0F, 20.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.7905F, -1.4515F, -3.9403F, 0.0F, 0.7854F, 0.0F));

		PartDefinition head_r4 = head_up.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(90, 116).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 14.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(-10.326F, 8.5485F, -1.8189F, 0.3441F, 0.762F, 0.3038F));

		PartDefinition head_r5 = head_up.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(40, 16).addBox(-1.5F, -10.0F, -1.5F, 3.0F, 20.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.073F, -1.4515F, -10.6578F, 0.0F, 0.7854F, 0.0F));

		PartDefinition head_r6 = head_up.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(102, 101).mirror().addBox(-0.5F, -8.0F, -1.0F, 4.0F, 13.0F, 2.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(15.4444F, 4.1552F, -2.8406F, 0.7006F, 0.3897F, 1.1475F));

		PartDefinition head_r7 = head_up.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(104, 84).mirror().addBox(-2.5F, -5.0F, -1.0F, 5.0F, 10.0F, 2.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(15.4444F, 4.1552F, -2.8406F, 0.4326F, 0.678F, 0.6346F));

		PartDefinition head_r8 = head_up.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(102, 70).mirror().addBox(1.5F, -2.0F, 6.0F, 4.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(38, 23).mirror().addBox(-1.5F, -10.0F, -8.0F, 3.0F, 20.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.6445F, -1.4515F, -3.9403F, 0.0F, 0.7854F, 0.0F));

		PartDefinition head_r9 = head_up.addOrReplaceChild("head_r9", CubeListBuilder.create().texOffs(96, 116).mirror().addBox(-0.5F, -1.0F, -1.0F, 1.0F, 14.0F, 2.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(10.1801F, 8.5485F, -1.8189F, 0.3491F, 0.7854F, 0.0F));

		PartDefinition left_antenna = head_up.addOrReplaceChild("left_antenna", CubeListBuilder.create(), PartPose.offset(9.4412F, -4.868F, -6.0298F));

		PartDefinition left_antenna_r1 = left_antenna.addOrReplaceChild("left_antenna_r1", CubeListBuilder.create().texOffs(103, 9).mirror().addBox(-1.0F, -5.5F, -2.5F, 2.0F, 11.0F, 5.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.1719F, 0.7703F, 0.2444F));

		PartDefinition left_antenna2 = left_antenna.addOrReplaceChild("left_antenna2", CubeListBuilder.create(), PartPose.offset(0.1665F, -10.3557F, -1.1665F));

		PartDefinition left_antenna_r2 = left_antenna2.addOrReplaceChild("left_antenna_r2", CubeListBuilder.create().texOffs(99, 25).mirror().addBox(-1.0F, -4.0F, -1.5F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, -4.0F, 0.0F, 0.1719F, 0.7703F, 0.2444F));

		PartDefinition left_antenna3 = left_antenna2.addOrReplaceChild("left_antenna3", CubeListBuilder.create(), PartPose.offset(2.1665F, -8.3557F, -0.1665F));

		PartDefinition left_antenna_r3 = left_antenna3.addOrReplaceChild("left_antenna_r3", CubeListBuilder.create().texOffs(98, 0).mirror().addBox(-1.0F, -5.5F, -1.5F, 2.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -5.0F, -1.0F, 0.1719F, 0.7703F, 0.2444F));

		PartDefinition left_antenna4 = left_antenna3.addOrReplaceChild("left_antenna4", CubeListBuilder.create(), PartPose.offset(0.7602F, -10.2385F, -2.7602F));

		PartDefinition left_antenna_r4 = left_antenna4.addOrReplaceChild("left_antenna_r4", CubeListBuilder.create().texOffs(84, 0).mirror().addBox(-0.5F, -23.0F, -0.5F, 1.0F, 29.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, -6.0F, 0.0F, 0.1719F, 0.7703F, 0.2444F));

		PartDefinition right_antenna = head_up.addOrReplaceChild("right_antenna", CubeListBuilder.create(), PartPose.offset(-9.4194F, -4.9045F, -5.8621F));

		PartDefinition right_antenna_r1 = right_antenna.addOrReplaceChild("right_antenna_r1", CubeListBuilder.create().texOffs(88, 14).addBox(-2.5F, -5.5F, -1.0F, 5.0F, 11.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.1309F, 0.7854F, 0.0F));

		PartDefinition right_antenna2 = right_antenna.addOrReplaceChild("right_antenna2", CubeListBuilder.create(), PartPose.offset(-0.8768F, -10.4187F, 0.1232F));

		PartDefinition right_antenna_r2 = right_antenna2.addOrReplaceChild("right_antenna_r2", CubeListBuilder.create().texOffs(88, 27).addBox(-1.5F, -4.0F, -1.0F, 3.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, -1.0F, 0.1309F, 0.7854F, 0.0F));

		PartDefinition right_antenna3 = right_antenna2.addOrReplaceChild("right_antenna3", CubeListBuilder.create(), PartPose.offset(-0.8768F, -8.4187F, -0.8768F));

		PartDefinition right_antenna_r3 = right_antenna3.addOrReplaceChild("right_antenna_r3", CubeListBuilder.create().texOffs(88, 1).addBox(-1.5F, -5.5F, -1.0F, 3.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, -1.0F, 0.1309F, 0.7854F, 0.0F));

		PartDefinition right_antenna4 = right_antenna3.addOrReplaceChild("right_antenna4", CubeListBuilder.create(), PartPose.offset(-1.4119F, -10.3364F, -1.4119F));

		PartDefinition right_antenna_r4 = right_antenna4.addOrReplaceChild("right_antenna_r4", CubeListBuilder.create().texOffs(84, 0).addBox(-0.5F, -23.0F, -0.5F, 1.0F, 29.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, -1.0F, 0.1309F, 0.7854F, 0.0F));

		PartDefinition head_3 = head_up.addOrReplaceChild("head_3", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.073F, -0.4515F, -6.4151F, 0.2182F, 0.0F, 0.0F));

		PartDefinition right_teeth_r1 = head_3.addOrReplaceChild("right_teeth_r1", CubeListBuilder.create().texOffs(1, 82).addBox(-1.0F, -1.5F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0104F, 14.65F, 2.0678F, 0.1309F, 0.7854F, 0.0F));

		PartDefinition right_teeth_r2 = head_3.addOrReplaceChild("right_teeth_r2", CubeListBuilder.create().texOffs(7, 82).addBox(-3.5F, 13.0F, -3.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(7, 82).addBox(-1.5F, 13.0F, -3.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(33, 80).addBox(0.5F, 13.0F, -3.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(7, 85).mirror().addBox(2.5F, 13.0F, 2.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(7, 85).mirror().addBox(2.5F, 13.0F, 0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(33, 84).mirror().addBox(2.5F, 13.0F, -2.5F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 0).addBox(-6.5F, -13.0F, -3.5F, 10.0F, 26.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.25F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition left_teeth_r1 = head_3.addOrReplaceChild("left_teeth_r1", CubeListBuilder.create().texOffs(1, 86).mirror().addBox(-0.5F, -1.5F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.0104F, 14.65F, 2.0678F, 0.1298F, 0.7769F, 0.1841F));

		PartDefinition head_3_r1 = head_3.addOrReplaceChild("head_3_r1", CubeListBuilder.create().texOffs(30, 2).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.75F, -2.5355F, 0.5064F, 0.7307F, 0.3508F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(30, 80).addBox(-8.0F, -0.4118F, -14.4685F, 16.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(10, 94).addBox(-8.0F, -2.4118F, -14.2185F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(10, 94).mirror().addBox(6.0F, -2.4118F, -14.2185F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(6, 94).addBox(-5.0F, -2.4118F, -14.4685F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(6, 94).mirror().addBox(4.0F, -2.4118F, -14.4685F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 94).addBox(-1.0F, -2.4118F, -17.4685F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(6, 94).addBox(-3.0F, -2.4118F, -15.4685F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(6, 94).mirror().addBox(2.0F, -2.4118F, -15.4685F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(50, 97).addBox(-2.0F, 2.5882F, -14.4685F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(50, 104).addBox(-1.0F, 6.5882F, -14.4685F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(58, 104).addBox(-8.0F, 6.5882F, -13.4685F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(50, 97).addBox(-8.0F, 2.5882F, -13.4685F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(58, 104).mirror().addBox(6.0F, 6.5882F, -13.4685F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(50, 97).mirror().addBox(4.0F, 2.5882F, -13.4685F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.0091F, 6.1066F, 2.5515F));

		PartDefinition jaw_r1 = jaw.addOrReplaceChild("jaw_r1", CubeListBuilder.create().texOffs(66, 104).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(8.0F, 14.5882F, -12.4685F, -0.0869F, -0.0076F, -0.0869F));

		PartDefinition jaw_r2 = jaw.addOrReplaceChild("jaw_r2", CubeListBuilder.create().texOffs(66, 104).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-8.0F, 14.5882F, -12.4685F, -0.1304F, 0.0114F, 0.0865F));

		PartDefinition jaw_r3 = jaw.addOrReplaceChild("jaw_r3", CubeListBuilder.create().texOffs(0, 80).addBox(-8.0F, -1.5F, -8.0F, 11.0F, 3.0F, 11.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, 1.0882F, -7.4685F, 0.0F, -0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(t, f4, f5, f3);

		if (t.specialAction()) {
			this.animateWalk(DuneshocklerAnimation.WALK, f, f2, 1.5f, 2.0f);
		}
		else if (t.isAggressive()) {
			this.animateWalk(DuneshocklerAnimation.RUSH, f, f2, 1.0f, 2.0f);
		}
		else {
			this.animateWalk(DuneshocklerAnimation.WALK, f, f2, 1.5f, 2.0f);
		}
		this.animate(t.attack1AnimationState, DuneshocklerAnimation.ATTACKHAND1, f3);
		this.animate(t.attack2AnimationState, DuneshocklerAnimation.ATTACKHAND2, f3);
		this.animate(t.attack3AnimationState, DuneshocklerAnimation.ATTACKHAND3, f3);
		this.animate(t.attack4AnimationState, DuneshocklerAnimation.ATTACKFOOT, f3);
		this.animate(t.shoot1AnimationState, DuneshocklerAnimation.ATTACKMOUTH1, f3);
		this.animate(t.shoot2AnimationState, DuneshocklerAnimation.ATTACKMOUTH2, f3);
		this.animate(t.idleAnimationState, DuneshocklerAnimation.IDLE1, f3);
		this.animate(t.idleAnimationState, DuneshocklerAnimation.IDLE2, f3);
		this.animate(t.idleAnimationState, DuneshocklerAnimation.IDLEBASE, f3);
		this.animate(t.deadAnimationState, DuneshocklerAnimation.DEAD, f3);
	}

	private void applyHeadRotation(T t, float f, float f2, float f3) {
		f = Mth.clamp(f, -30.0f, 30.0f);
		f2 = Mth.clamp(f2, -25.0f, 25.0f);
		this.head.yRot = f * ((float) Math.PI / 180F);
		this.head.xRot = f2 * ((float) Math.PI / 180F);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}