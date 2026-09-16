package com.jerotes.jerotesvillage.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;

public class JerotesVillageLevelData extends SavedData {
    private static final String NAME = "jerotesvillage_world_data";
    //邪笑之夜
    public boolean WickedRidiculeNight = false;
    //泽林亲和值
    public double ZsieinAffinity = 0;
    //蛇龙人离开
    public boolean SerponAway = false;

    public void setWickedRidiculeNight(boolean bl){
        this.WickedRidiculeNight = bl;
        this.setDirty();
    }
    public void setZsieinAffinity(double d){
        this.ZsieinAffinity = d;
        this.setDirty();
    }
    public void setSerponAway(boolean bl){
        this.SerponAway = bl;
        this.setDirty();
    }

    public JerotesVillageLevelData() {
        super();
    }


    public static JerotesVillageLevelData get(LevelAccessor world) {
        if (world instanceof ServerLevel level) {
            //1.20.4↑//
//            return level.getDataStorage()
//                    .computeIfAbsent(
//                            new Factory<>(
//                                    JerotesVillageLevelData::new,
//                                    JerotesVillageLevelData::load,
//                                    DataFixTypes.LEVEL)
//                            , NAME);
            //1.20.1//
            return level.getDataStorage()
             .computeIfAbsent(JerotesVillageLevelData::load, JerotesVillageLevelData::new, NAME);
        } else {
            return null;
        }
    }

    public static JerotesVillageLevelData load(CompoundTag compoundTag) {
        JerotesVillageLevelData jerotesVillageWorldData = new JerotesVillageLevelData();
        jerotesVillageWorldData.WickedRidiculeNight = compoundTag.getBoolean("WickedRidiculeNight");
        jerotesVillageWorldData.ZsieinAffinity = compoundTag.getDouble("ZsieinAffinity");
        jerotesVillageWorldData.SerponAway = compoundTag.getBoolean("SerponAway");
        return jerotesVillageWorldData;
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        compoundTag.putBoolean("WickedRidiculeNight", WickedRidiculeNight);
        compoundTag.putDouble("ZsieinAffinity", ZsieinAffinity);
        compoundTag.putBoolean("SerponAway", SerponAway);
        return compoundTag;
    }
}
