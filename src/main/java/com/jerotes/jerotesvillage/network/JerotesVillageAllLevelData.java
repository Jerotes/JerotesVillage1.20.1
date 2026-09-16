package com.jerotes.jerotesvillage.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.Objects;

public class JerotesVillageAllLevelData extends SavedData {
    private static final String NAME = "jerotesvillage_all_world_data";

    public JerotesVillageAllLevelData() {
        super();
    }


    public static JerotesVillageAllLevelData get(LevelAccessor world) {
        if (world instanceof ServerLevelAccessor serverLevelAcc) {
            //1.20.4↑//
//            return Objects.requireNonNull(serverLevelAcc.getLevel().getServer().getLevel(Level.OVERWORLD))
//                    .getDataStorage().computeIfAbsent(new Factory<>(JerotesVillageAllLevelData::new, JerotesVillageAllLevelData::load, DataFixTypes.LEVEL), NAME);
            //1.20.1//
            return Objects.requireNonNull(serverLevelAcc.getLevel().getServer().getLevel(Level.OVERWORLD))
             .getDataStorage().computeIfAbsent(JerotesVillageAllLevelData::load, JerotesVillageAllLevelData::new, NAME);
        } else {
            return null;
        }
    }

    //重置
    public static void resetInstance() {

    }

    public static JerotesVillageAllLevelData load(CompoundTag compoundTag) {
        JerotesVillageAllLevelData jerotesVillageWorldData = new JerotesVillageAllLevelData();

        return jerotesVillageWorldData;
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {

        return compoundTag;
    }
}
