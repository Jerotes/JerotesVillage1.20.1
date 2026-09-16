package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Animal.SnowyApeEntity;
import com.jerotes.jerotesvillage.entity.Humanoid.ChibaoEntity;
import com.jerotes.jerotesvillage.entity.Humanoid.Qoaiku.QoaikuEntity;
import com.jerotes.jerotesvillage.entity.Monster.Elite.FrostYetiEntity;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.AdventurerEntity;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.ExplorerEntity;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.SlaverySupervisorEntity;
import com.jerotes.jerotesvillage.entity.Neutral.Wonderlin.WonderlinEntity;
import com.jerotes.jerotesvillage.event.WeatherEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {

    public HumanoidArmorLayerMixin(RenderLayerParent<T, M> renderLayerParent) {
        super(renderLayerParent);
    }

    @Inject(method = "renderArmorPiece*", at = @At("HEAD"), cancellable = true)
    public void renderArmorPiece(PoseStack poseStack, MultiBufferSource multiBufferSource, T t, EquipmentSlot equipmentSlot, int n, A a, CallbackInfo ci) {
        ItemStack itemstack = t.getItemBySlot(equipmentSlot);
        Item item = itemstack.getItem();
        if (t instanceof ExplorerEntity) {
            if (item == JerotesVillageItems.EXPLORER_HELMET.get()) {
                ci.cancel();
            }
            if (item == JerotesVillageItems.EXPLORER_CHESTPLATE.get()) {
                ci.cancel();
            }
            if (item == JerotesVillageItems.EXPLORER_LEGGINGS.get()) {
                ci.cancel();
            }
            if (item == JerotesVillageItems.EXPLORER_BOOTS.get()) {
                ci.cancel();
            }
        }
        if (t instanceof SlaverySupervisorEntity) {
            if (item == JerotesVillageItems.SLAVERY_SUPERVISOR_HELMET.get()) {
                ci.cancel();
            }
            if (item == JerotesVillageItems.SLAVERY_SUPERVISOR_CHESTPLATE.get()) {
                ci.cancel();
            }
            if (item == JerotesVillageItems.SLAVERY_SUPERVISOR_LEGGINGS.get()) {
                ci.cancel();
            }
            if (item == JerotesVillageItems.SLAVERY_SUPERVISOR_BOOTS.get()) {
                ci.cancel();
            }
        }
        if (t instanceof AdventurerEntity) {
            if (item == JerotesVillageItems.ADVENTURER_HELMET.get()) {
                ci.cancel();
            }
            if (item == JerotesVillageItems.ADVENTURER_CHESTPLATE.get()) {
                ci.cancel();
            }
            if (item == JerotesVillageItems.ADVENTURER_LEGGINGS.get()) {
                ci.cancel();
            }
            if (item == JerotesVillageItems.ADVENTURER_BOOTS.get()) {
                ci.cancel();
            }
        }
    }
    @Inject(method = "renderArmorPiece*", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;setPartVisibility(Lnet/minecraft/client/model/HumanoidModel;Lnet/minecraft/world/entity/EquipmentSlot;)V"))
    public void renderArmorPieceAdd(PoseStack poseStack, MultiBufferSource multiBufferSource, T t, EquipmentSlot equipmentSlot, int n, A a, CallbackInfo ci) {
        ItemStack itemstack = t.getItemBySlot(equipmentSlot);
        Item item = itemstack.getItem();
        if (t instanceof ChibaoEntity) {
            a.head.xScale *= 1.25f;
            a.head.yScale *= 1.1f;
            a.head.zScale *= 1.25f;
            a.body.xScale *= 1.95f;
            a.body.yScale *= 2f;
            a.body.zScale *= 1.8f;

            a.leftArm.xScale *= 1.7f;
            a.leftArm.yScale *= 2.15f;
            a.leftArm.zScale *= 1.8f;
            a.rightArm.xScale *= 1.7f;
            a.rightArm.yScale *= 2.15f;
            a.rightArm.zScale *= 1.8f;

            a.leftLeg.xScale *= 2f;
            a.leftLeg.yScale *= 1.05f;
            a.leftLeg.zScale *= 2f;
            a.rightLeg.xScale *= 2f;
            a.rightLeg.yScale *= 1.05f;
            a.rightLeg.zScale *= 2f;
        }
        if (t instanceof WonderlinEntity) {
            a.body.xScale *= 0.6f;
            a.body.yScale *= 0.6f;
            a.body.zScale *= 0.6f;

            a.leftArm.xScale *= 0.6f;
            a.leftArm.yScale *= 0.6f;
            a.leftArm.zScale *= 0.6f;
            a.rightArm.xScale *= 0.6f;
            a.rightArm.yScale *= 0.6f;
            a.rightArm.zScale *= 0.6f;

            a.leftLeg.xScale *= 0.6f;
            a.leftLeg.yScale *= 0.6f;
            a.leftLeg.zScale *= 0.6f;
            a.rightLeg.xScale *= 0.6f;
            a.rightLeg.yScale *= 0.6f;
            a.rightLeg.zScale *= 0.6f;
        }
        if (t instanceof SnowyApeEntity || t instanceof FrostYetiEntity) {
            a.body.xScale *= 1.25f;
            a.body.yScale *= 1.75f;
            a.body.zScale *= 1.4f;

            a.leftArm.xScale *= 0f;
            a.leftArm.yScale *= 0f;
            a.leftArm.zScale *= 0f;
            a.rightArm.xScale *= 0f;
            a.rightArm.yScale *= 0f;
            a.rightArm.zScale *= 0f;

            a.leftLeg.xScale *= 1.4f;
            a.leftLeg.yScale *= 1.75f;
            a.leftLeg.zScale *= 1.4f;
            a.rightLeg.xScale *= 1.4f;
            a.rightLeg.yScale *= 1.75f;
            a.rightLeg.zScale *= 1.4f;
        }
        if (t instanceof QoaikuEntity qoaikuEntity) {
            String string = ChatFormatting.stripFormatting(qoaikuEntity.getName().getString());
            if ((WeatherEvent.isNewYearsDay() && OtherMainConfig.SpecialDay) || "Old Lore".equals(string)) {
                a.body.zScale *= 1.5f;
                a.body.yScale *= 1.75f;
            }
        }
    }
    @Inject(method = "renderTrim*", at = @At("HEAD"), cancellable = true)
    public void renderTrim1(ArmorMaterial armorMaterial, PoseStack poseStack, MultiBufferSource multiBufferSource, int n, ArmorTrim armorTrim, A model, boolean bl, CallbackInfo ci) {
        if (specialArmor().contains(armorMaterial.getName())) {
            ci.cancel();
        }
    }

    public List<String> specialArmor() {
        List<String> list = new ArrayList<>();
        list.add("meror_metal_armor");
        list.add("meror_standard_armor");
        list.add("mountain_realm_armor");
        list.add("ancient_second_rounder_armor");
        list.add("magicka_scale_armor");
        list.add("blamer_robe");
        list.add("horned_armor");
        list.add("giant_monster_horned_armor");
        list.add("big_witch_armor");
        list.add("woodland_heart_guardian_mask");
        list.add("explorer_armor");
        list.add("adventurer_armor");
        return list;
    }
}