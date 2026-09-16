package com.jerotes.jerotesvillage.event;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.item.ItemTabletBase;
import com.jerotes.jerotesvillage.item.PotionThrowingBall;
import com.jerotes.jerotesvillage.item.SerponWizardArmor;
import com.jerotes.jerotesvillage.util.PurpleSandTint;
import com.jerotes.jerotesvillage.util.SunSandTint;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = JerotesVillage.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ColorEvent {
    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        BlockColors blockColors = event.getBlockColors();

        //蛇龙巫师套装
        event.register((itemStack, n) -> n > 0 ? -1:
                        itemStack.getItem() instanceof SerponWizardArmor serponWizardArmor
                                ? (serponWizardArmor).getColor(itemStack)
                                : 0xFFFFFF,
                JerotesVillageItems.SERPON_WIZARD_HELMET.get(), JerotesVillageItems.SERPON_WIZARD_CHESTPLATE.get(), JerotesVillageItems.SERPON_WIZARD_LEGGINGS.get(), JerotesVillageItems.SERPON_WIZARD_BOOTS.get());

        //药水投团
        event.register((itemStack, n) -> n > 0 ? -1:
                        itemStack.getItem() instanceof PotionThrowingBall potionThrowingBall
                                ? PotionUtils.getColor(PotionUtils.getMobEffects(itemStack))
                                : 0xFFFFFF,
                JerotesVillageItems.POTION_THROWING_BALL.get());

        //强硬草方块
        event.register((itemStack, n) -> {
            BlockState blockState = ((BlockItem)itemStack.getItem()).getBlock().defaultBlockState();
            return blockColors.getColor(blockState, null, null, n);
        }, JerotesVillageBlocks.AASTRONG_GRASS_BLOCK.get());

        //二轮锚定核心
        event.register((itemStack, n) -> {
            BlockState blockState = ((BlockItem)itemStack.getItem()).getBlock().defaultBlockState();
            return blockColors.getColor(blockState, null, null, n);
        }, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get());

        //紫沙
        event.register((itemStack, n) -> !OtherMainConfig.PurpleSandEyeProtectionMode ? (!OtherMainConfig.PurpleSandCustomColor ? 0x703c9d : OtherMainConfig.PurpleSandCustomColorUse) :
                0xcaa4c7, JerotesVillageItems.PURPLE_SAND.get());

        //紫沙岩
        event.register((itemStack, n) -> !OtherMainConfig.PurpleSandEyeProtectionMode ? (!OtherMainConfig.PurpleSandCustomColor ? 0x703c9d : OtherMainConfig.PurpleSandCustomColorUse) :
                0xcaa4c7, JerotesVillageItems.PURPLE_SANDSTONE.get());

        //奇灵假耳
        event.register((itemStack, n) ->
                ((DyeableLeatherItem)((Object)itemStack.getItem())).getColor(itemStack), JerotesVillageItems.WONDERLIN_FAKE_EARS.get());

        //药片
        event.register((itemStack, n) -> n > 0 ? ((ItemTabletBase)((Object)itemStack.getItem())).getColor() :
                ((ItemTabletBase)((Object)itemStack.getItem())).getColor2(),
                JerotesVillageItems.THERAPEUTIC_TABLET.get(),
                JerotesVillageItems.FIRM_TABLET.get(),
                JerotesVillageItems.VIOLENT_TABLET.get(),
                JerotesVillageItems.DEXTEROUS_TABLET.get(),
                JerotesVillageItems.DEBILITATED_TABLET.get(),
                JerotesVillageItems.ABLEPSIA_TABLET.get(),
                JerotesVillageItems.ANESTHETIC_TABLET.get()
        );

        //紫黑死灌
        event.register((itemStack, n) -> n <= 0 ? -1 :
                        !OtherMainConfig.PurpleSandEyeProtectionMode ? 0x703c9d :
                                0xcc98c8,
                JerotesVillageItems.PURPLE_BLACK_DEAD_BUSH.get());
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        //强硬草方块
        event.register((blockState, blockAndTintGetter, blockPos, n) -> {
            if (blockAndTintGetter == null || blockPos == null) {
                return GrassColor.getDefaultColor();
            }
            return BiomeColors.getAverageGrassColor(blockAndTintGetter, blockPos);
        }, JerotesVillageBlocks.AASTRONG_GRASS_BLOCK.get());

        //二轮锚定核心
        event.register((blockState, blockAndTintGetter, blockPos, n) -> {
            if (blockAndTintGetter == null || blockPos == null) {
                return GrassColor.getDefaultColor();
            }
            return BiomeColors.getAverageGrassColor(blockAndTintGetter, blockPos);
        }, JerotesVillageBlocks.SECOND_ROUND_ANCHOR_CORE.get());

        //强硬草方块
        event.register((blockState, blockAndTintGetter, blockPos, n) -> {
            if (blockAndTintGetter == null || blockPos == null) {
                return GrassColor.getDefaultColor();
            }
            return BiomeColors.getAverageGrassColor(blockAndTintGetter, blockPos);
        }, JerotesVillageBlocks.AASTRONG_GRASS_BLOCK.get());
        //紫沙
        event.register((blockState, blockAndTintGetter, blockPos, n) ->
                        PurpleSandTint.getTint(blockPos),
                JerotesVillageBlocks.PURPLE_SAND.get());
        //紫沙岩
        event.register((blockState, blockAndTintGetter, blockPos, n) ->
                        (blockPos != null && blockAndTintGetter != null && blockAndTintGetter.getBlockState(blockPos.above()).getBlock() == JerotesVillageBlocks.PURPLE_SANDSTONE.get()) ? 0x9a68b5 : PurpleSandTint.getTint(blockPos),
                JerotesVillageBlocks.PURPLE_SANDSTONE.get());
        //日光沙
        event.register((blockState, blockAndTintGetter, blockPos, n) ->
                        SunSandTint.getTint(blockPos),
                JerotesVillageBlocks.SUN_SAND.get());

        //瓜茎
        event.register((blockState, blockAndTintGetter, blockPos, n) -> {
            int n2 = blockState.getValue(StemBlock.AGE);
            int n3 = n2 * 32;
            int n4 = 255 - n2 * 8;
            int n5 = n2 * 4;
            return n3 << 16 | n4 << 8 | n5;
        }, JerotesVillageBlocks.FLAVORLESS_MELON_STEM.get());
        //结果的瓜茎
        event.register((blockState, blockAndTintGetter, blockPos, n) -> 14731036, JerotesVillageBlocks.ATTACHED_FLAVORLESS_MELON_STEM.get());

        //紫黑死灌
        event.register((blockState, blockAndTintGetter, blockPos, n) -> PurpleSandTint.getTint(blockPos), JerotesVillageBlocks.PURPLE_BLACK_DEAD_BUSH.get(), JerotesVillageBlocks.POTTED_PURPLE_BLACK_DEAD_BUSH.get());
    }
}
