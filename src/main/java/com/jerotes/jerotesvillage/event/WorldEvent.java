package com.jerotes.jerotesvillage.event;

import com.jerotes.jerotes.entity.Interface.WickedRidiculeCallsEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.network.JerotesVillageLevelData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = JerotesVillage.MODID)
public class WorldEvent {
    public static final UUID NIGHT_UUID = UUID.fromString("0DD1F1C7-A309-D2AF-5EB9-6EEB1D513047");
    public static final UUID ZSIEIN_UUID = UUID.fromString("be7a4304-3c4d-4311-9dd2-33e5c376502b");
    private final static JerotesBossEvent nightEvent = new JerotesBossEvent(Component.translatable("event.jerotesvillage.wicked_ridicule_night"), NIGHT_UUID,BossEvent.BossBarColor.RED, true, true);
    private final static JerotesBossEvent zsieinEvent = new JerotesBossEvent(Component.translatable("event.jerotesvillage.zsein"), ZSIEIN_UUID,BossEvent.BossBarColor.WHITE, true, true, true);
    public static boolean WickedRidiculeNightAbout(Level level) {
        return OtherMainConfig.WickedRidiculeNightAboutDimension.contains(level.dimension().location().toString()) || OtherMainConfig.WickedRidiculeNightAboutAllDimension;
    }
    @SubscribeEvent
    public static void WickedRidiculeNight(TickEvent.LevelTickEvent event) {
        Level level = event.level;
        //玩家位于二轮世界
        if (level.getDifficulty() != Difficulty.PEACEFUL && WickedRidiculeNightAbout(level) && !ZsieinModerateInfectedAbove(level)) {
            float times = level.getDayTime() % 24000;
            double chance = OtherMainConfig.WickedRidiculeNightChance;
            //判定夜晚
            if (level.isNight() && level.getDayTime() > 72000 && times == 15000 && level.getRandom().nextFloat() <= chance && JerotesVillageLevelData.get(level) != null && !JerotesVillageLevelData.get(level).WickedRidiculeNight) {
                WickedRidiculeNightTrueOrFalse(level, true);
                if (!level.isClientSide() && JerotesVillageLevelData.get(level) != null && JerotesVillageLevelData.get(level).WickedRidiculeNight) {
                    List<? extends Player> list = level.players();
                    list.removeIf(player -> player.level() != level);
                    for (Player player : list) {
                        player.addEffect(new MobEffectInstance(JerotesVillageMobEffects.WICKED_RIDICULE_NIGHT_START.get(), 20, 1));
                        player.sendSystemMessage(Component.translatable("message.jerotesvillage.wicked_ridicule_night_start").withStyle(ChatFormatting.DARK_RED));
                    }
                }
            }
            //如果开始
            if (JerotesVillageLevelData.get(level) != null && JerotesVillageLevelData.get(level).WickedRidiculeNight) {
                //level.setFor;
                //午夜
                if (times > 6000) {
                    nightEvent.setProgress(((24000 - times) / 10000) + 0.1f);
                }
                //黎明
                else {
                    nightEvent.setProgress((1000 - times) / 10000);
                }
                //日出
                if (times > 1000 && times < 6000) {
                    if (!level.isClientSide() && JerotesVillageLevelData.get(level) != null && JerotesVillageLevelData.get(level).WickedRidiculeNight && WickedRidiculeNightAbout(level)) {
                        List<? extends Player> list = level.players();
                        list.removeIf(player -> player.level() != level);
                        for (Player player : list) {
                            player.addEffect(new MobEffectInstance(JerotesVillageMobEffects.WICKED_RIDICULE_NIGHT_START.get(), 20, 1));
                            player.sendSystemMessage(Component.translatable("message.jerotesvillage.wicked_ridicule_night_stop").withStyle(ChatFormatting.DARK_RED));
                        }
                    }
                    WickedRidiculeNightTrueOrFalse(level, false);
                }
            }
        }
        //否则
        else {
            if (!level.isClientSide() && JerotesVillageLevelData.get(level) != null && JerotesVillageLevelData.get(level).WickedRidiculeNight) {
                List<? extends Player> list = level.players();
                list.removeIf(player -> player.level() != level);
                for (Player player : list) {
                    player.addEffect(new MobEffectInstance(JerotesVillageMobEffects.WICKED_RIDICULE_NIGHT_START.get(), 20, 1));
                    player.sendSystemMessage(Component.translatable("message.jerotesvillage.wicked_ridicule_night_stop").withStyle(ChatFormatting.DARK_RED));
                }
            }
            WickedRidiculeNightTrueOrFalse(level, false);
        }
    }
    @SubscribeEvent
    public static void PlayerNight(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        Level level = event.getEntity().level();
        if (JerotesVillageLevelData.get(level) == null) {
            return;
        }
        //玩家血条
        if (livingEntity instanceof Player player) {
            if (JerotesVillageLevelData.get(level) != null && JerotesVillageLevelData.get(level).WickedRidiculeNight) {
                if (!player.level().isClientSide()) {
                    player.addEffect(new MobEffectInstance(JerotesVillageMobEffects.WICKED_RIDICULE_NIGHT.get(), 5, 0, false, false));
                }
            }
            if (JerotesVillageLevelData.get(level) != null && JerotesVillageLevelData.get(level).WickedRidiculeNight && OtherMainConfig.WickedRidiculeNightBossBar) {
                if (player instanceof ServerPlayer serverPlayer) {
                    if (!nightEvent.getPlayers().contains(player)) {
                        nightEvent.addPlayer(serverPlayer);
                    }
                }
            } else {
                if (player instanceof ServerPlayer serverPlayer) {
                    if (nightEvent.getPlayers().contains(serverPlayer)) {
                        nightEvent.removePlayer(serverPlayer);
                    }
                }
            }
        }
        //邪笑呼唤阵营
        if (livingEntity instanceof WickedRidiculeCallsEntity wickedRidiculeCallsEntity) {
            if (JerotesVillageLevelData.get(level) != null && JerotesVillageLevelData.get(level).WickedRidiculeNight) {
                wickedRidiculeCallsEntity.addInWickedRidiculeNight(true);
            }
        }
    }
    public static void WickedRidiculeNightTrueOrFalse(Level level, boolean bl) {
        if (JerotesVillageLevelData.get(level) == null) {
            return;
        }
        JerotesVillageLevelData.get(level).setWickedRidiculeNight(bl);
    }
    public static void allChanged() {
        Minecraft.getInstance().levelRenderer.allChanged();
    }





    //潜伏感染以上
    public static boolean ZsieinLatentInfectedAbove(Level level) {
        if (level == null) {
            return false;
        }
        if (JerotesVillageLevelData.get(level) == null) {
            return false;
        }
        return OtherMainConfig.ZsieinAffinityAboutDimension.contains(level.dimension().location().toString()) || OtherMainConfig.ZsieinAffinityAboutAllDimension;
    }
    //轻度感染以上
    public static boolean ZsieinMildInfectedAbove(Level level) {
        if (level == null) {
            return false;
        }
        if (JerotesVillageLevelData.get(level) == null) {
            return false;
        }
        if (!ZsieinLatentInfectedAbove(level)) {
            return false;
        }
        return (JerotesVillageLevelData.get(level).ZsieinAffinity >= OtherMainConfig.ZsieinMildInfectedLevel || JerotesVillageLevelData.get(level).ZsieinAffinity >= OtherMainConfig.ZsieinModerateInfectedLevel || JerotesVillageLevelData.get(level).ZsieinAffinity >= OtherMainConfig.ZsieinSevereInfectedLevel || JerotesVillageLevelData.get(level).ZsieinAffinity >= OtherMainConfig.ZsieinExtremelyInfectedLevel);
    }
    //中度感染以上
    public static boolean ZsieinModerateInfectedAbove(Level level) {
        if (level == null) {
            return false;
        }
        if (JerotesVillageLevelData.get(level) == null) {
            return false;
        }
        if (!ZsieinLatentInfectedAbove(level)) {
            return false;
        }
        return (JerotesVillageLevelData.get(level).ZsieinAffinity >= OtherMainConfig.ZsieinModerateInfectedLevel || JerotesVillageLevelData.get(level).ZsieinAffinity >= OtherMainConfig.ZsieinSevereInfectedLevel || JerotesVillageLevelData.get(level).ZsieinAffinity >= OtherMainConfig.ZsieinExtremelyInfectedLevel);
    }
    //重度感染以上
    public static boolean ZsieinSevereInfectedAbove(Level level) {
        if (level == null) {
            return false;
        }
        if (JerotesVillageLevelData.get(level) == null) {
            return false;
        }
        if (!ZsieinLatentInfectedAbove(level)) {
            return false;
        }
        return (JerotesVillageLevelData.get(level).ZsieinAffinity >= OtherMainConfig.ZsieinSevereInfectedLevel || JerotesVillageLevelData.get(level).ZsieinAffinity >= OtherMainConfig.ZsieinExtremelyInfectedLevel);
    }
    //极度感染以上
    public static boolean ZsieinExtremelyInfectedAbove(Level level) {
        if (level == null) {
            return false;
        }
        if (JerotesVillageLevelData.get(level) == null) {
            return false;
        }
        if (!ZsieinLatentInfectedAbove(level)) {
            return false;
        }
        return (JerotesVillageLevelData.get(level).ZsieinAffinity >= OtherMainConfig.ZsieinExtremelyInfectedLevel);
    }
    //增加泽林亲融度
    public static void ZsieinAffinityAdd(Level level, double d) {
        if (level == null) {
            return;
        }
        if (JerotesVillageLevelData.get(level) == null) {
            return;
        }
        if (ZsieinLatentInfectedAbove(level)) {
            if (ZsieinAffinityGet(level) + d > OtherMainConfig.ZsieinAffinityMaxLevel) {
                JerotesVillageLevelData.get(level).setZsieinAffinity(OtherMainConfig.ZsieinAffinityMaxLevel);
            }
            else if (ZsieinAffinityGet(level) + d < 0) {
                JerotesVillageLevelData.get(level).setZsieinAffinity(0);
            }
            else {
                JerotesVillageLevelData.get(level).setZsieinAffinity(JerotesVillageLevelData.get(level).ZsieinAffinity + d);
            }
        }
    }
    //设置泽林亲融度
    public static void ZsieinAffinitySet(Level level, double d) {
        if (level == null) {
            return;
        }
        if (JerotesVillageLevelData.get(level) == null) {
            return;
        }
        if (ZsieinLatentInfectedAbove(level)) {
            if (d > OtherMainConfig.ZsieinAffinityMaxLevel) {
                JerotesVillageLevelData.get(level).setZsieinAffinity(OtherMainConfig.ZsieinAffinityMaxLevel);
            }
            else if (d < 0) {
                JerotesVillageLevelData.get(level).setZsieinAffinity(0);
            }
            else {
                JerotesVillageLevelData.get(level).setZsieinAffinity(d);
            }
        }
    }
    //获得泽林亲融度
    public static double ZsieinAffinityGet(Level level) {
        if (level == null) {
            return -1;
        }
        if (JerotesVillageLevelData.get(level) == null) {
            return 0;
        }
        return JerotesVillageLevelData.get(level).ZsieinAffinity;
    }
    @SubscribeEvent
    public static void PlayerZsieinFog(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        //玩家血条
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.hasEffect(JerotesVillageMobEffects.ZSIEIN_FOG.get())) {
                if (!zsieinEvent.getPlayers().contains(serverPlayer)) {
                    zsieinEvent.addPlayer(serverPlayer);
                }
            } else {
                if (zsieinEvent.getPlayers().contains(serverPlayer)) {
                    zsieinEvent.removePlayer(serverPlayer);
                }
            }
        }
    }
}
