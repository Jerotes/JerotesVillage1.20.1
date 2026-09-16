package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelvillager_piglin_other;
import com.jerotes.jerotesvillage.client.model.Modelvillager_witch_other;
import com.jerotes.jerotesvillage.client.model.Modelzombie_villager_piglin_other;
import com.jerotes.jerotesvillage.client.model.Modelzombie_villager_witch_other;
import com.jerotes.jerotesvillage.init.JerotesVillageVillagerProfessions;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;

public class VillagerOtherBodyLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation PIGLIN_LOCATION = new ResourceLocation("minecraft:textures/entity/piglin/piglin.png");
    private static final ResourceLocation PIGLIN_BRUTE_LOCATION = new ResourceLocation("minecraft:textures/entity/piglin/piglin_brute.png");
    private static final ResourceLocation ZOMBIE_PIGLIN_LOCATION = new ResourceLocation("minecraft:textures/entity/piglin/zombified_piglin.png");
    private static final ResourceLocation WITCH_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/villager_witch.png");
    private static final ResourceLocation ZOMBIE_WITCH_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/zombie_villager_witch.png");
    private final EntityRendererProvider.Context context;

    public VillagerOtherBodyLayer(RenderLayerParent<T, M> renderLayerParent, EntityRendererProvider.Context context) {
        super(renderLayerParent);
        this.context = context;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        boolean bl;
        Minecraft minecraft = Minecraft.getInstance();
        boolean bl2 = bl = minecraft.shouldEntityAppearGlowing((Entity)t) && ((Entity)t).isInvisible();
        if (((Entity)t).isInvisible() && !bl) {
            return;
        }



        if (t instanceof Villager villager) {
            if (villager.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_PIGLIN.get()) {
                EntityModel<T> model = new Modelvillager_piglin_other<>(this.context.bakeLayer(Modelvillager_piglin_other.LAYER_LOCATION));
                this.getParentModel().copyPropertiesTo(model);
                model.prepareMobModel(t, f, f2, f3);
                model.setupAnim(t, f, f2, f4, f5, f6);
                VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(PIGLIN_LOCATION));
                if (villager.getVillagerData().getLevel() > 4) {
                    vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(PIGLIN_BRUTE_LOCATION));
                }
                ((Model)model).renderToBuffer(poseStack, vertexConsumer, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
            }
            if (villager.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_WITCH.get()) {
                EntityModel<T> model = new Modelvillager_witch_other<>(this.context.bakeLayer(Modelvillager_witch_other.LAYER_LOCATION));
                this.getParentModel().copyPropertiesTo(model);
                model.prepareMobModel(t, f, f2, f3);
                model.setupAnim(t, f, f2, f4, f5, f6);
                VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(WITCH_LOCATION));
                ((Model)model).renderToBuffer(poseStack, vertexConsumer, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
        if (t instanceof ZombieVillager villager) {
            if (villager.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_PIGLIN.get()) {
                EntityModel<T> model = new Modelzombie_villager_piglin_other(this.context.bakeLayer(Modelzombie_villager_piglin_other.LAYER_LOCATION));
                this.getParentModel().copyPropertiesTo(model);
                model.prepareMobModel(t, f, f2, f3);
                model.setupAnim(t, f, f2, f4, f5, f6);
                VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(ZOMBIE_PIGLIN_LOCATION));
                ((Model)model).renderToBuffer(poseStack, vertexConsumer, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
            }
            if (villager.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_WITCH.get()) {
                EntityModel<T> model = new Modelzombie_villager_witch_other(this.context.bakeLayer(Modelzombie_villager_witch_other.LAYER_LOCATION));
                this.getParentModel().copyPropertiesTo(model);
                model.prepareMobModel(t, f, f2, f3);
                model.setupAnim(t, f, f2, f4, f5, f6);
                VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(ZOMBIE_WITCH_LOCATION));
                ((Model)model).renderToBuffer(poseStack, vertexConsumer, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
    }
}

