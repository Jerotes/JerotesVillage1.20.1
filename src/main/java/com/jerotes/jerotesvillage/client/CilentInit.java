package com.jerotes.jerotesvillage.client;

import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.item.*;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Items;

public class CilentInit
{
    public static void clientInit() {
        JerotesVillageItems.REGISTRY.getEntries().forEach(item -> {
            ItemProperties.register(item.get(), new ResourceLocation("pull"), (itemStack, clientLevel, livingEntity, n) -> {
                if (livingEntity == null) {
                    return 0.0f;
                }
                if (livingEntity.getUseItem() != itemStack) {
                    return 0.0f;
                }
                return (float)(itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0f;
            });
            ItemProperties.register(item.get(), new ResourceLocation("jerotesvillage_pull_crossbow"), (itemStack, clientLevel, livingEntity, n) -> {
                if (livingEntity == null) {
                    return 0.0f;
                }
                if (CrossbowItem.isCharged(itemStack)) {
                    return 0.0f;
                }
                return (float)(itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / (float)CrossbowItem.getChargeDuration(itemStack);
            });
            ItemProperties.register(item.get(), new ResourceLocation("jerotesvillage_pull_firepower_pourer_crossbow"), (itemStack, clientLevel, livingEntity, n) -> {
                if (livingEntity == null) {
                    return 0.0f;
                }
                if (FirepowerPourerCrossbow.isCharged(itemStack)) {
                    return 0.0f;
                }
                return (float)(itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / (float)FirepowerPourerCrossbow.getChargeDuration(itemStack);
            });

            ItemProperties.register(item.get(), new ResourceLocation("brushing"), (itemStack, clientLevel, livingEntity, n) -> {
                if (livingEntity == null || livingEntity.getUseItem() != itemStack) {
                    return 0.0f;
                }
                return (float)(livingEntity.getUseItemRemainingTicks() % 10) / 10.0f;
            });
            ItemProperties.register(item.get(), new ResourceLocation("filled"), (itemStack, clientLevel, livingEntity, n) -> WonderlinSmallBag.getFullnessDisplay(itemStack));
            ItemProperties.register(item.get(), new ResourceLocation("pulling"), (itemStack, clientLevel, livingEntity, n) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack && (!(itemStack.getItem() instanceof CrossbowItem) || !CrossbowItem.isCharged(itemStack)) ? 1.0f : 0.0f);
            ItemProperties.register(item.get(), new ResourceLocation("throwing"), (itemStack, clientLevel, livingEntity, n) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0f : 0.0f);
            ItemProperties.register(item.get(), new ResourceLocation("charged"), (itemStack, clientLevel, livingEntity, n) -> CrossbowItem.isCharged(itemStack) ? 1.0f : 0.0f);
            ItemProperties.register(item.get(), new ResourceLocation("firework"), (itemStack, clientLevel, livingEntity, n) -> CrossbowItem.isCharged(itemStack) && CrossbowItem.containsChargedProjectile(itemStack, Items.FIREWORK_ROCKET) ? 1.0f : 0.0f);
            ItemProperties.register(item.get(), new ResourceLocation("blocking"), (itemStack, clientLevel, livingEntity, n) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0f : 0.0f);
            ItemProperties.register(item.get(), new ResourceLocation("cast"), (itemStack, clientLevel, livingEntity, n) -> {
                boolean bl;
                if (livingEntity == null) {
                    return 0.0f;
                }
                boolean bl2 = livingEntity.getMainHandItem() == itemStack;
                boolean bl3 = bl = livingEntity.getOffhandItem() == itemStack;
                if (livingEntity.getMainHandItem().getItem() instanceof FishingRodItem) {
                    bl = false;
                }
                return (bl2 || bl) && livingEntity instanceof Player && ((Player)livingEntity).fishing != null ? 1.0f : 0.0f;
            });
            ItemProperties.register(item.get(), new ResourceLocation("jerotesvillage_gemstone_throwing_knives"), (itemStack, clientLevel, livingEntity, n) -> {
                if (livingEntity == null) {
                    return 3.0f;
                }
                if (livingEntity.getUseItem() != itemStack) {
                    return 3.0f;
                }
                if ((float)(itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) < 5) {
                    return 0.0f;
                }
                if ((float)(itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) < 20) {
                    return 1.0f;
                }
                return (float)(itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0f + 1;
            });
            ItemProperties.register(item.get(), new ResourceLocation("jerotesvillage_count_12"), (itemStack, clientLevel, livingEntity, n) -> itemStack.getCount() >= 12 ? 1.0f : 0.0f);
            ItemProperties.register(item.get(), new ResourceLocation("jerotesvillage_count_48"), (itemStack, clientLevel, livingEntity, n) -> itemStack.getCount() >= 48 ? 1.0f : 0.0f);
            ItemProperties.register(item.get(), new ResourceLocation("jerotesvillage_fall_1_5"), (itemStack, clientLevel, livingEntity, n) -> livingEntity != null && livingEntity.fallDistance > 1.5 ? 1.0f : 0.0f);
            ItemProperties.register(item.get(), new ResourceLocation("jerotesvillage_shift"), (itemStack, clientLevel, livingEntity, n) -> livingEntity != null && livingEntity.isShiftKeyDown() ? 1.0f : 0.0f);
            ItemProperties.register(item.get(), new ResourceLocation("jerotesvillage_syringe_full"), (itemStack, clientLevel, livingEntity, n) -> livingEntity != null && livingEntity.getOffhandItem().getItem() instanceof ItemTabletBase && livingEntity.getMainHandItem().getItem() instanceof VillagerMetalSyringe ? 1.0f : 0.0f);
            ItemProperties.register(item.get(), new ResourceLocation("jerotesvillage_bright_golden_melon_hammer_use"), (itemStack, clientLevel, livingEntity, n) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem().getItem() instanceof BrightGoldenMelonHammer ? 1.0f : 0.0f);
            ItemProperties.register(item.get(), new ResourceLocation("jerotesvillage_offhand"), (itemStack, clientLevel, livingEntity, n) -> livingEntity != null && livingEntity.getOffhandItem() == itemStack ? 1.0f : 0.0f);
            ItemProperties.register(item.get(), new ResourceLocation("jerotesvillage_swing"), (itemStack, clientLevel, livingEntity, n) -> livingEntity != null && livingEntity.getMainHandItem() == itemStack && livingEntity.swinging ? 1.0f : 0.0f);
            ItemProperties.register(item.get(), new ResourceLocation("jerotesvillage_head"), (itemStack, clientLevel, livingEntity, n) -> livingEntity != null && livingEntity.getItemBySlot(EquipmentSlot.HEAD) == itemStack ? 1.0f : 0.0f);
            ItemProperties.register(item.get(), new ResourceLocation("jerotesvillage_glaive_of_serpent_god_cooldown"), (itemStack, clientLevel, livingEntity, n) -> livingEntity != null && Main.getJerotesPersistentData(livingEntity).getDouble("jerotesvillage_start_glaive_of_serpent_god_attack_cooldown") > 0 ? 1.0f : 0.0f);
            ItemProperties.register(item.get(), new ResourceLocation("jerotesvillage_meror_impact_pistol_fine"), (itemStack, clientLevel, livingEntity, n) -> livingEntity != null && (livingEntity.getMainHandItem() == itemStack || livingEntity.getOffhandItem() == itemStack) && !(livingEntity instanceof Player player && player.getCooldowns().isOnCooldown(itemStack.getItem())) ? 1.0f : 0.0f);
            ItemProperties.register(item.get(), new ResourceLocation("jerotesvillage_tick_count_4"), (itemStack, clientLevel, livingEntity, n) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ?
                    (livingEntity.tickCount % 4 == 0 ? 1.0f :  (livingEntity.tickCount % 4 == 1 ? 2.0f : (livingEntity.tickCount % 4 == 2 ? 3.0f : 4.0f))) : 0.0f);
            ItemProperties.register(item.get(), new ResourceLocation("jerotesvillage_tick_count_3"), (itemStack, clientLevel, livingEntity, n) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ?
                    (livingEntity.tickCount % 3 == 1 ? 1.0f : (livingEntity.tickCount % 3 == 2 ? 2.0f : 3.0f)) : 0.0f);
            ItemProperties.register(item.get(), new ResourceLocation("jerotesvillage_color"), (itemStack, clientLevel, livingEntity, n) -> itemStack.getItem() instanceof DyeableLeatherItem dyeableLeatherItem && dyeableLeatherItem.hasCustomColor(itemStack) ? 1.0f : 0.0f);
            ItemProperties.register(item.get(), new ResourceLocation("jerotesvillage_catch_queen"), (itemStack, clientLevel, livingEntity, n) -> ItemCatchBaseAdd.isQueen(itemStack) ? 1.0f : 0.0f);
            ItemProperties.register(item.get(), new ResourceLocation("jerotesvillage_catch_baby"), (itemStack, clientLevel, livingEntity, n) -> ItemCatchBaseAdd.isBaby(itemStack) ? 1.0f : 0.0f);
            ItemProperties.register(item.get(), new ResourceLocation("jerotesvillage_catch_pupa"), (itemStack, clientLevel, livingEntity, n) -> ItemCatchBaseAdd.isPupa(itemStack) ? 1.0f : 0.0f);
            ItemProperties.register(item.get(), new ResourceLocation("jerotesvillage_continuous_cut_leaf_strong_attack"), (itemStack, clientLevel, livingEntity, n) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getTicksUsingItem() > 20 ? 1.0f : 0.0f);
        });
    }
}
