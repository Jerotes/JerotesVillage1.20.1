package com.jerotes.jerotesvillage.network;

import com.jerotes.jerotes.network.JerotesPlayerData;
import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
//1.20.4↑//
//import net.minecraftforge.event.network.CustomPayloadEvent;
//import net.minecraftforge.network.PacketDistributor;
//1.20.1//
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkDirection;
import java.util.function.Supplier;


@Mod.EventBusSubscriber(modid = JerotesVillage.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class JerotesVillagePlayerData {
	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
	}
	@SubscribeEvent
	public static void init(RegisterCapabilitiesEvent event) {
		event.register(PlayerVariables.class);
	}

	@Mod.EventBusSubscriber
	public static class EventBusVariableHandlers {
		@SubscribeEvent
		public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
			if (!event.getEntity().level().isClientSide())
				(event.getEntity().getCapability(CAPABILITY, null).orElse(new PlayerVariables())).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
			if (!event.getEntity().level().isClientSide())
				(event.getEntity().getCapability(CAPABILITY, null).orElse(new PlayerVariables())).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
			if (!event.getEntity().level().isClientSide())
				(event.getEntity().getCapability(CAPABILITY, null).orElse(new PlayerVariables())).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void clonePlayer(PlayerEvent.Clone event) {
			event.getOriginal().revive();
			PlayerVariables original = (event.getOriginal().getCapability(CAPABILITY, null).orElse(new PlayerVariables()));
			PlayerVariables clone = (event.getEntity().getCapability(CAPABILITY, null).orElse(new PlayerVariables()));
			//铜刻佣兵团
			clone.CopperCarvedCompanyRelationship = original.CopperCarvedCompanyRelationship;
			clone.HaveCopperCarvedCompanyRelationship = original.HaveCopperCarvedCompanyRelationship;
//			if (!event.isWasDeath()) {
//			}
		}
	}

	public static final Capability<PlayerVariables> CAPABILITY = CapabilityManager.get(new CapabilityToken<PlayerVariables>() {
	});

	@Mod.EventBusSubscriber
	private static class PlayerVariablesProvider implements ICapabilitySerializable<Tag> {
		@SubscribeEvent
		public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof Player)
				event.addCapability(new ResourceLocation(JerotesVillage.MODID, "player_variables"), new PlayerVariablesProvider());
		}

		private final PlayerVariables playerVariables = new PlayerVariables();
		private final LazyOptional<PlayerVariables> instance = LazyOptional.of(() -> playerVariables);

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			return cap == CAPABILITY ? instance.cast() : LazyOptional.empty();
		}

		@Override
		public Tag serializeNBT() {
			return playerVariables.writeNBT();
		}

		@Override
		public void deserializeNBT(Tag nbt) {
			playerVariables.readNBT(nbt);
		}
	}

	public static class PlayerVariables {
		//铜刻佣兵团
		public int CopperCarvedCompanyRelationship = 0;
		public boolean HaveCopperCarvedCompanyRelationship = false;

		public void setHaveCopperCarvedCompanyRelationship(boolean bl){
			this.HaveCopperCarvedCompanyRelationship = bl;
		}
		public void setCopperCarvedCompanyRelationship(int n){
			this.CopperCarvedCompanyRelationship = n;
		}

		public void syncPlayerVariables(Entity entity) {
			if (!entity.level().isClientSide() && entity instanceof ServerPlayer serverPlayer) {
				if (this.writeNBT() != null) {
					//1.20.4↑//
//					OtherPacketHandler.NETWORK_WRAPPER.send(
//							new PlayerVariablesSyncMessage(this.writeNBT()), PacketDistributor.PLAYER.with(serverPlayer));
					//1.20.1//
					OtherPacketHandler.NETWORK_WRAPPER.sendTo(
							new JerotesVillagePlayerData.PlayerVariablesSyncMessage(this.writeNBT()), serverPlayer.connection.connection,
							NetworkDirection.PLAY_TO_CLIENT);
				}
			}
		}


		public CompoundTag writeNBT() {
			CompoundTag nbt = new CompoundTag();
			//铜刻佣兵团
			nbt.putInt("JerotesVillageCopperCarvedCompanyRelationship", CopperCarvedCompanyRelationship);
			nbt.putBoolean("JerotesVillageHaveCopperCarvedCompanyRelationship", HaveCopperCarvedCompanyRelationship);
			return nbt;
		}

		public void readNBT(Tag tag) {
			CompoundTag nbt = (CompoundTag) tag;
			//铜刻佣兵团
			CopperCarvedCompanyRelationship = nbt.getInt("JerotesVillageCopperCarvedCompanyRelationship");
			HaveCopperCarvedCompanyRelationship = nbt.getBoolean("JerotesVillageHaveCopperCarvedCompanyRelationship");
		}
	}

	//1.20.4↑//
//	public static class PlayerVariablesSyncMessage {
//		private final CompoundTag data;
//		public PlayerVariablesSyncMessage(CompoundTag nbt) {
//			this.data = nbt;
//		}
//		public static void encode(PlayerVariablesSyncMessage message, FriendlyByteBuf buffer) {
//			buffer.writeNbt(message.data);
//		}
//		public static PlayerVariablesSyncMessage decode(FriendlyByteBuf buffer) {
//			return new PlayerVariablesSyncMessage(buffer.readNbt());
//		}
//		public static void consume(PlayerVariablesSyncMessage message, CustomPayloadEvent.Context context) {
//			context.enqueueWork(() -> {
//				// 客户端处理逻辑
//				Minecraft minecraft = Minecraft.getInstance();
//				if (minecraft.player != null) {
//					minecraft.player.getCapability(CAPABILITY).ifPresent(cap -> {
//						cap.readNBT(message.data);
//					});
//				}
//			});
//			context.setPacketHandled(true);
//		}
//	}
	//1.20.1//
	public static class PlayerVariablesSyncMessage {
		private final CompoundTag data;
		public PlayerVariablesSyncMessage(CompoundTag nbt) {
			this.data = nbt;
		}
		public static void encode(PlayerVariablesSyncMessage message, FriendlyByteBuf buffer) {
			buffer.writeNbt(message.data);
		}
		public static PlayerVariablesSyncMessage decode(FriendlyByteBuf buffer) {
			return new PlayerVariablesSyncMessage(buffer.readNbt());
		}
		public static void consume(PlayerVariablesSyncMessage message, Supplier<NetworkEvent.Context> context) {
			context.get().enqueueWork(() -> {
				Minecraft minecraft = Minecraft.getInstance();
				if (minecraft.player != null) {
					minecraft.player.getCapability(CAPABILITY).ifPresent(cap -> {
						cap.readNBT(message.data);
					});
				}
			});
			context.get().setPacketHandled(true);
		}
	}
}
