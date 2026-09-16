package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.item.Interface.ItemBaseGiantBeastArmor;
import com.jerotes.jerotes.item.Interface.ItemBeastArmor;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotesvillage.client.model.Modelgiant_monster;
import com.jerotes.jerotesvillage.client.model.Modelgiant_monster_armor;
import com.jerotes.jerotesvillage.entity.Animal.GiantMonsterEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;

public class GiantMonsterArmorLayer extends RenderLayer<GiantMonsterEntity, Modelgiant_monster<GiantMonsterEntity>> {
    private final Modelgiant_monster<GiantMonsterEntity> model;

    public GiantMonsterArmorLayer(RenderLayerParent<GiantMonsterEntity, Modelgiant_monster<GiantMonsterEntity>> renderLayerParent, EntityModelSet entityModelSet) {
        super(renderLayerParent);
        this.model = new Modelgiant_monster_armor(entityModelSet.bakeLayer(Modelgiant_monster_armor.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, GiantMonsterEntity giantMonster, float f, float f2, float f3, float f4, float f5, float f6) {
        if (EntityAndItemFind.isTrueInvisible(giantMonster)) {
            return;
        }
        float f7;
        float f8;
        float f9;
        ItemStack itemStack = giantMonster.getItemBySlot(EquipmentSlot.CHEST);
        if (!(itemStack.getItem() instanceof ItemBaseGiantBeastArmor itemGiantBeastArmor)) {
            return;
        }
        this.getParentModel().copyPropertiesTo(this.model);
        this.model.prepareMobModel(giantMonster, f, f2, f3);
        this.model.setupAnim(giantMonster, f, f2, f4, f5, f6);
        if (itemGiantBeastArmor instanceof DyeableLeatherItem) {
            int n2 = ((DyeableLeatherItem)itemGiantBeastArmor).getColor(itemStack);
            f9 = (float)(n2 >> 16 & 0xFF) / 255.0f;
            f7 = (float)(n2 >> 8 & 0xFF) / 255.0f;
            f8 = (float)(n2 & 0xFF) / 255.0f;
        }
        else {
            f9 = 1.0f;
            f7 = 1.0f;
            f8 = 1.0f;
        }
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(itemGiantBeastArmor.getTexture()));
        this.model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, f9, f7, f8, 1.0f);
        if (itemStack.getItem() instanceof ItemBeastArmor itemBeastArmor && itemBeastArmor.hasOverlay()) {
            VertexConsumer vertexConsumer2 = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(itemGiantBeastArmor.getTextureOverlay()));
            this.model.renderToBuffer(poseStack, vertexConsumer2, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
}

