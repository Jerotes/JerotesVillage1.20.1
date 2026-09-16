//1.20.4↑//
// package com.jerotes.jerotesvillage.network;
//
//import com.jerotes.jerotesvillage.block.BitterCold.TorchOfFirmIce;
//import com.jerotes.jerotesvillage.block.BitterCold.TorchOfFirmIceEntity;
//import com.jerotes.jerotesvillage.client.gui.TorchOfFirmIceScreen;
//import net.minecraft.core.BlockPos;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.entity.BlockEntity;
//import net.minecraftforge.event.network.CustomPayloadEvent;
//
//public class TorchOfFirmIcePacket {
//    public int elite;
//    public BlockPos blockPos;
//    public boolean open;
//    public int count;
//
//    public TorchOfFirmIcePacket(int id, BlockPos blockPos, boolean open, int count) {
//        this.elite = id;
//        this.blockPos = blockPos;
//        this.open = open;
//        this.count = count;
//    }
//
//    public static void encode(TorchOfFirmIcePacket packet, FriendlyByteBuf buffer) {
//        buffer.writeInt(packet.elite);
//        buffer.writeBlockPos(packet.blockPos);
//        buffer.writeBoolean(packet.open);
//        buffer.writeInt(packet.elite);
//    }
//
//    public static TorchOfFirmIcePacket decode(FriendlyByteBuf buffer) {
//        return new TorchOfFirmIcePacket(buffer.readInt(), buffer.readBlockPos(), buffer.readBoolean(), buffer.readInt());
//    }
//
//    public void consume(CustomPayloadEvent.Context context) {
//        context.enqueueWork(() -> {
//            ServerPlayer playerEntity = context.getSender();
//            if (playerEntity != null) {
//                Level level = playerEntity.level();
//                if (level.getBlockState(blockPos).getBlock() instanceof TorchOfFirmIce torchOfFirmIce) {
//                    //真正使用
//                    if (open) {
//                        if (level instanceof ServerLevel serverLevel && TorchOfFirmIceScreen.canSummonFindBlockCount(elite, serverLevel, blockPos, playerEntity)) {
//                            torchOfFirmIce.setOpen(playerEntity, level, level.getBlockState(blockPos), blockPos, true);
//                            BlockEntity blockentity = level.getBlockEntity(blockPos);
//                            if (blockentity instanceof TorchOfFirmIceEntity torchOfFirmIceEntity) {
//                                torchOfFirmIceEntity.shrink(count);
//                            }
//                            //生成生物
//                            TorchOfFirmIceScreen.changeEntity(elite, level, blockPos);
//                        }
//                    }
//                    //修改精英类型
//                    else {
//                       if (torchOfFirmIce.getElite(level.getBlockState(blockPos)) == elite) {
//                           torchOfFirmIce.setElite(playerEntity, level, level.getBlockState(blockPos), blockPos, 0);
//                       }
//                       else {
//                           torchOfFirmIce.setElite(playerEntity, level, level.getBlockState(blockPos), blockPos, elite);
//                       }
//                    }
//                }
//            }
//        });
//        context.setPacketHandled(true);
//    }
//}
//1.20.1//
package com.jerotes.jerotesvillage.network;

import com.jerotes.jerotesvillage.block.BitterCold.TorchOfFirmIce;
import com.jerotes.jerotesvillage.block.BitterCold.TorchOfFirmIceEntity;
import com.jerotes.jerotesvillage.client.gui.TorchOfFirmIceScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TorchOfFirmIcePacket {
    public int elite;
    public BlockPos blockPos;
    public boolean open;
    public int count;

    public TorchOfFirmIcePacket(int id, BlockPos blockPos, boolean open, int count) {
        this.elite = id;
        this.blockPos = blockPos;
        this.open = open;
        this.count = count;
    }

    public static void encode(TorchOfFirmIcePacket packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.elite);
        buffer.writeBlockPos(packet.blockPos);
        buffer.writeBoolean(packet.open);
        buffer.writeInt(packet.elite);
    }

    public static TorchOfFirmIcePacket decode(FriendlyByteBuf buffer) {
        return new TorchOfFirmIcePacket(buffer.readInt(), buffer.readBlockPos(), buffer.readBoolean(), buffer.readInt());
    }

    public static void consume(TorchOfFirmIcePacket message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer playerEntity = context.get().getSender();
            if (playerEntity != null) {
                Level level = playerEntity.level();
                if (level.getBlockState(message.blockPos).getBlock() instanceof TorchOfFirmIce torchOfFirmIce) {
                    //真正使用
                    if (message.open) {
                        if (level instanceof ServerLevel serverLevel && TorchOfFirmIceScreen.canSummonFindBlockCount(message.elite, serverLevel, message.blockPos, playerEntity)) {
                            torchOfFirmIce.setOpen(playerEntity, level, level.getBlockState(message.blockPos), message.blockPos, true);
                            BlockEntity blockentity = level.getBlockEntity(message.blockPos);
                            if (blockentity instanceof TorchOfFirmIceEntity torchOfFirmIceEntity) {
                                torchOfFirmIceEntity.shrink(message.count);
                            }
                            //生成生物
                            TorchOfFirmIceScreen.changeEntity(message.elite, level, message.blockPos);
                        }
                    }
                    //修改精英类型
                    else {
                        if (torchOfFirmIce.getElite(level.getBlockState(message.blockPos)) == message.elite) {
                            torchOfFirmIce.setElite(playerEntity, level, level.getBlockState(message.blockPos), message.blockPos, 0);
                        }
                        else {
                            torchOfFirmIce.setElite(playerEntity, level, level.getBlockState(message.blockPos), message.blockPos, message.elite);
                        }
                    }
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}