package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CarriedBlockLayer;
import net.minecraft.client.renderer.entity.layers.EnderEyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EnderStowawayRenderer extends MobRenderer<EnderMan, EndermanModel<EnderMan>> {
    private static final ResourceLocation ENDERMAN_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/ender_stowaway.png");
    private static final ResourceLocation ENDERMAN_ELITE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/ender_stowaway_elite.png");
    private final RandomSource random = RandomSource.create();

    public EnderStowawayRenderer(EntityRendererProvider.Context context) {
        super(context, new EndermanModel(context.bakeLayer(ModelLayers.ENDERMAN)), 0.5f);
        this.addLayer(new EnderEyesLayer<EnderMan>(this));
        this.addLayer(new CarriedBlockLayer(this, context.getBlockRenderDispatcher()));
    }

    @Override
    public void render(EnderMan enderMan, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        BlockState blockState = enderMan.getCarriedBlock();
        EndermanModel endermanModel = (EndermanModel)this.getModel();
        endermanModel.carrying = blockState != null;
        endermanModel.creepy = enderMan.isCreepy();
        super.render(enderMan, f, f2, poseStack, multiBufferSource, n);
    }

    @Override
    public Vec3 getRenderOffset(EnderMan enderMan, float f) {
        if (enderMan.isCreepy()) {
            double d = 0.02;
            return new Vec3(this.random.nextGaussian() * 0.02, 0.0, this.random.nextGaussian() * 0.02);
        }
        return super.getRenderOffset(enderMan, f);
    }

    @Override
    public ResourceLocation getTextureLocation(EnderMan enderMan) {
        String string = ChatFormatting.stripFormatting(enderMan.getName().getString());
        if ("Elite".equals(string)) {
            return ENDERMAN_ELITE_LOCATION;
        }
        return ENDERMAN_LOCATION;
    }
}

