package com.jerotes.jerotesvillage.client.model;

import com.google.common.collect.ImmutableList;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Animal.WonderlandGiantFrogTadpoleEntity;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class Modelwonderland_giant_frog_tadpole<T extends WonderlandGiantFrogTadpoleEntity> extends AgeableListModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JerotesVillage.MODID, "wonderland_giant_frog_tadpole"), "main");
	private final ModelPart root;
	private final ModelPart tail;

	public Modelwonderland_giant_frog_tadpole(ModelPart p_233443_) {
		super(true, 8.0F, 3.35F);
		this.root = p_233443_;
		this.tail = p_233443_.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition $$0 = new MeshDefinition();
		PartDefinition $$1 = $$0.getRoot();
		float $$2 = 0.0F;
		float $$3 = 22.0F;
		float $$4 = -3.0F;
		$$1.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 3.0F), PartPose.offset(0.0F, 22.0F, -3.0F));
		$$1.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 7.0F), PartPose.offset(0.0F, 22.0F, 0.0F));
		return LayerDefinition.create($$0, 16, 16);
	}

	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.root);
	}

	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.tail);
	}

	public void setupAnim(T p_233453_, float p_233454_, float p_233455_, float p_233456_, float p_233457_, float p_233458_) {
		float $$6 = p_233453_.isInWater() ? 1.0F : 1.5F;
		this.tail.yRot = -$$6 * 0.25F * Mth.sin(0.3F * p_233456_);
	}
}
