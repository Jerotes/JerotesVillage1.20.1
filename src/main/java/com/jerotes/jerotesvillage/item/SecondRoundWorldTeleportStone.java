package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.init.JerotesVillageDimensions;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;

public class SecondRoundWorldTeleportStone extends Item implements Vanishable {
	private static final Logger LOGGER = LogUtils.getLogger();
	public static final String TAG_SECOND_ROUND_WORLD_TELEPORT_STONE_POS = "SecondRoundWorldTeleportStonePos";
	public static final String TAG_SECOND_ROUND_WORLD_TELEPORT_STONE_BINDING = "SecondRoundWorldTeleportStoneBinding";

	public SecondRoundWorldTeleportStone() {
		super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
	}

	public static boolean isBinding(ItemStack itemStack) {
		CompoundTag compoundtag = itemStack.getTag();
		return compoundtag != null && compoundtag.contains(TAG_SECOND_ROUND_WORLD_TELEPORT_STONE_BINDING);
	}

	public boolean isFoil(ItemStack itemStack) {
		return isBinding(itemStack) || super.isFoil(itemStack);
	}

	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 72000;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.BLOCK;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		//绑定
		if (player.isShiftKeyDown() && level.dimension() == JerotesVillageDimensions.SECOND_ROUND_WORLD) {
			CompoundTag compoundTags = itemStack.getTag();
			//如果是原位
			if (compoundTags != null && isBinding(itemStack)) {
				BlockPos blockPos = NbtUtils.readBlockPos(compoundTags.getCompound(TAG_SECOND_ROUND_WORLD_TELEPORT_STONE_POS));
				if (player.getOnPos().above().equals(blockPos)) {
					CompoundTag compoundtag = new CompoundTag();
					itemStack.setTag(compoundtag);
					level.playSound(null, player, JerotesSoundEvents.TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
					return InteractionResultHolder.success(itemStack);
				}
			}
			//如果不是原位
			this.addStoneTags(player.getOnPos().above(), itemStack.getOrCreateTag());
			level.playSound(null, player, JerotesSoundEvents.TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
			return InteractionResultHolder.success(itemStack);
		}
		//使用
		player.startUsingItem(interactionHand);
		return InteractionResultHolder.consume(itemStack);
	}

	@Override
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int n) {
		if (!(livingEntity instanceof Player player)) {
			return;
		}
		int n2 = this.getUseDuration(itemStack) - n;
		if (n2 < 120) {
			return;
		}
		//传送-未绑定
		if (!isBinding(itemStack)) {
			if (player.canChangeDimensions()) {
				if (((player.level().dimension()) == JerotesVillageDimensions.SECOND_ROUND_WORLD)) {
					if (player instanceof ServerPlayer serverPlayer && !serverPlayer.level().isClientSide()) {
						ResourceKey<Level> destinationType = Level.OVERWORLD;
						if (serverPlayer.level().dimension() == destinationType)
							return;
						ServerLevel serverLevel = serverPlayer.server.getLevel(destinationType);
						if (serverLevel != null) {
							serverPlayer.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.WIN_GAME, 0));
							double yHeight = serverPlayer.getY();
							BlockPos summonPos = new BlockPos((int) serverPlayer.getX() * 8, (int) yHeight, (int) serverPlayer.getZ() * 8);
							for (int find = 0; find < 400; ++find) {
								if (!(serverLevel.isEmptyBlock(summonPos) && serverLevel.isEmptyBlock(summonPos.above()))) {
									summonPos = new BlockPos((int) serverPlayer.getX() * 8, (int) yHeight + find, (int) serverPlayer.getZ() * 8);
								}
							}
							for (int find = 0; find < 400; ++find) {
								if (serverLevel.isEmptyBlock(summonPos) && serverLevel.isEmptyBlock(summonPos.below())) {
									summonPos = new BlockPos((int) serverPlayer.getX() * 8, (int) yHeight - find, (int) serverPlayer.getZ() * 8);
								}
							}
							serverPlayer.teleportTo(serverLevel, summonPos.getX(), summonPos.getY(), summonPos.getZ(), serverPlayer.getYRot(), serverPlayer.getXRot());
							serverPlayer.connection.send(new ClientboundPlayerAbilitiesPacket(serverPlayer.getAbilities()));
							for (MobEffectInstance _effectinstance : serverPlayer.getActiveEffects())
								serverPlayer.connection.send(new ClientboundUpdateMobEffectPacket(serverPlayer.getId(), _effectinstance));
							serverPlayer.connection.send(new ClientboundLevelEventPacket(1032, BlockPos.ZERO, 0, false));
						}
					}

					if (!level.isClientSide) {
						player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 240, 0));
					}
					if (level.isClientSide) {
						Minecraft.getInstance().gameRenderer.displayItemActivation(itemStack);
					}
					player.getCooldowns().addCooldown(this, 1200);
					return;
				} 
				else if (((player.level().dimension()) == Level.OVERWORLD)) {
					if (player instanceof ServerPlayer serverPlayer && !serverPlayer.level().isClientSide()) {
						ResourceKey<Level> destinationType = JerotesVillageDimensions.SECOND_ROUND_WORLD;
						if (serverPlayer.level().dimension() == destinationType)
							return;
						ServerLevel serverLevel = serverPlayer.server.getLevel(destinationType);
						if (serverLevel != null) {
							serverPlayer.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.WIN_GAME, 0));
							double yHeight = serverPlayer.getY();
							BlockPos summonPos = new BlockPos((int) serverPlayer.getX(), (int) yHeight, (int) serverPlayer.getZ());
							for (int find = 0; find < 400; ++find) {
								if (!(serverLevel.isEmptyBlock(summonPos) && serverLevel.isEmptyBlock(summonPos.above()))) {
									summonPos = new BlockPos((int) serverPlayer.getX(), (int) yHeight + find, (int) serverPlayer.getZ());
								}
							}
							for (int find = 0; find < 400; ++find) {
								if (serverLevel.isEmptyBlock(summonPos) && serverLevel.isEmptyBlock(summonPos.below())) {
									summonPos = new BlockPos((int) serverPlayer.getX(), (int) yHeight - find, (int) serverPlayer.getZ());
								}
							}
							serverPlayer.teleportTo(serverLevel, summonPos.getX(), summonPos.getY(), summonPos.getZ(), serverPlayer.getYRot(), serverPlayer.getXRot());
							serverPlayer.connection.send(new ClientboundPlayerAbilitiesPacket(serverPlayer.getAbilities()));
							for (MobEffectInstance _effectinstance : serverPlayer.getActiveEffects())
								serverPlayer.connection.send(new ClientboundUpdateMobEffectPacket(serverPlayer.getId(), _effectinstance));
							serverPlayer.connection.send(new ClientboundLevelEventPacket(1032, BlockPos.ZERO, 0, false));
						}
					}

					if (!level.isClientSide) {
						player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 240, 0));
					}
					if (level.isClientSide) {
						Minecraft.getInstance().gameRenderer.displayItemActivation(itemStack);
					}
					player.getCooldowns().addCooldown(this, 1200);
					return;
				}
			}
		}
		//传送-绑定
		else {
			CompoundTag compoundTag = itemStack.getTag();
			if (compoundTag != null && player.level().dimension() == JerotesVillageDimensions.SECOND_ROUND_WORLD && player.level() instanceof ServerLevel serverLevel) {
				if (player instanceof ServerPlayer serverPlayer) {
					BlockPos blockPos = NbtUtils.readBlockPos(compoundTag.getCompound(TAG_SECOND_ROUND_WORLD_TELEPORT_STONE_POS));
					serverPlayer.teleportTo(serverLevel, blockPos.getX(), blockPos.getY(), blockPos.getZ(), serverPlayer.getYRot(), serverPlayer.getXRot());
				}
				player.getCooldowns().addCooldown(this, 1200);
				return;
			}
		}
	}

	private void addStoneTags(BlockPos blockPos, CompoundTag compoundTag) {
		compoundTag.put(TAG_SECOND_ROUND_WORLD_TELEPORT_STONE_POS, NbtUtils.writeBlockPos(blockPos));
		compoundTag.putBoolean(TAG_SECOND_ROUND_WORLD_TELEPORT_STONE_BINDING, true);
	}

	private void removeStoneTags(CompoundTag compoundTag) {
		compoundTag.putBoolean(TAG_SECOND_ROUND_WORLD_TELEPORT_STONE_BINDING, false);
	}

	@Override
	public String getDescriptionId(ItemStack itemStack) {
		return isBinding(itemStack) ? "item.jerotesvillage.binding_second_round_world_teleport_stone" : super.getDescriptionId(itemStack);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		if (isBinding(itemStack)) {
			CompoundTag compoundTag = itemStack.getTag();
			if (compoundTag != null) {
				BlockPos blockPos = NbtUtils.readBlockPos(compoundTag.getCompound(TAG_SECOND_ROUND_WORLD_TELEPORT_STONE_POS));
				list.add(Component.literal(blockPos.getX() + " " + blockPos.getY() + " " + blockPos.getZ()).withStyle(ChatFormatting.GRAY));
				list.add(this.getDisplayName_2().withStyle(ChatFormatting.GRAY));
			}
		}
		else {
			list.add(this.getDisplayName_0().withStyle(ChatFormatting.GRAY));
			list.add(this.getDisplayName_1().withStyle(ChatFormatting.GRAY));
		}
	}
	public MutableComponent getDisplayName_0() {
		return Component.translatable(this.getDescriptionId() + ".desc_0");
	}
	public MutableComponent getDisplayName_1() {
		return Component.translatable(this.getDescriptionId() + ".desc_1");
	}
	public MutableComponent getDisplayName_2() {
		return Component.translatable(this.getDescriptionId() + ".desc_2");
	}
}
