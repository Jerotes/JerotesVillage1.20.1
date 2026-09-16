//1.20.4↑//
//package com.jerotes.jerotesvillage.network;
//
//import com.jerotes.jerotesvillage.JerotesVillage;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraftforge.network.NetworkDirection;
//import net.minecraftforge.network.PacketDistributor;
//import net.minecraftforge.network.ChannelBuilder;
//import net.minecraftforge.network.SimpleChannel;
//
//public class OtherPacketHandler {
//	public static final SimpleChannel NETWORK_WRAPPER = ChannelBuilder.named(
//					new ResourceLocation(JerotesVillage.MODID, "main"))
//			.serverAcceptedVersions(((status, i) -> true))
//			.clientAcceptedVersions(((status, i) -> true))
//			.networkProtocolVersion(1)
//			.simpleChannel();
//
//	public static void register() {
//		NETWORK_WRAPPER.messageBuilder(JerotesVillagePlayerData.PlayerVariablesSyncMessage.class, NetworkDirection.PLAY_TO_CLIENT)
//				.encoder(JerotesVillagePlayerData.PlayerVariablesSyncMessage::encode)
//				.decoder(JerotesVillagePlayerData.PlayerVariablesSyncMessage::decode)
//				.consumerMainThread(JerotesVillagePlayerData.PlayerVariablesSyncMessage::consume)
//				.add();
//		NETWORK_WRAPPER.messageBuilder(TorchOfFirmIcePacket.class, NetworkDirection.PLAY_TO_SERVER)
//				.encoder(TorchOfFirmIcePacket::encode)
//				.decoder(TorchOfFirmIcePacket::decode)
//				.consumerMainThread(TorchOfFirmIcePacket::consume)
//				.add();
//	}
//
//	public static void sendToServer(Object msg) {
//		NETWORK_WRAPPER.send(msg, PacketDistributor.SERVER.noArg());
//	}
//}


//1.20.1//
package com.jerotes.jerotesvillage.network;

import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class OtherPacketHandler {
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel NETWORK_WRAPPER = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(JerotesVillage.MODID, "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);

	public static void register() {
		int packetsRegistered = 0;
		NETWORK_WRAPPER.registerMessage(packetsRegistered++, JerotesVillagePlayerData.PlayerVariablesSyncMessage.class,
				JerotesVillagePlayerData.PlayerVariablesSyncMessage::encode,
				JerotesVillagePlayerData.PlayerVariablesSyncMessage::decode,
				JerotesVillagePlayerData.PlayerVariablesSyncMessage::consume,
				Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		NETWORK_WRAPPER.registerMessage(packetsRegistered++, TorchOfFirmIcePacket.class,
				TorchOfFirmIcePacket::encode,
				TorchOfFirmIcePacket::decode,
				TorchOfFirmIcePacket::consume,
				Optional.of(NetworkDirection.PLAY_TO_SERVER));
	}
	public static void sendToServer(Object msg) {
		NETWORK_WRAPPER.sendToServer(msg);
	}
}
