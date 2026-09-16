package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.event.RelationshipEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.util.OtherEntityFactionFind;
import com.jerotes.jerotesvillage.inventory.CarvedFactionMenu;
import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class CarvedContacter extends Item implements Vanishable {
	private static final Logger LOGGER = LogUtils.getLogger();
	public static final String TAG_CARVED_LODESTONE_POS = "CarvedLodestonePos";
	public static final String TAG_CARVED_LODESTONE_DIMENSION = "CarvedLodestoneDimension";
	public static final String TAG_CARVED_LODESTONE_TRACKED = "CarvedLodestoneTracked";

	public CarvedContacter() {
		super(new Properties().stacksTo(1).rarity(Rarity.RARE));
	}

	public static boolean isLodestoneContacter(ItemStack itemStack) {
		CompoundTag compoundtag = itemStack.getTag();
		return compoundtag != null && (compoundtag.contains(TAG_CARVED_LODESTONE_DIMENSION) && compoundtag.contains(TAG_CARVED_LODESTONE_POS));
	}

	private static Optional<ResourceKey<Level>> getLodestoneDimension(CompoundTag compoundTag) {
		return Level.RESOURCE_KEY_CODEC.parse(NbtOps.INSTANCE, compoundTag.get(TAG_CARVED_LODESTONE_DIMENSION)).result();
	}

	public boolean isFoil(ItemStack itemStack) {
		return isLodestoneContacter(itemStack) || super.isFoil(itemStack);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		if (player.level() instanceof ServerLevel) {
			ItemStack itemStack = player.getItemInHand(hand);
			CompoundTag compoundTag = itemStack.getTag();
			//绑定
			if (compoundTag != null && isLodestoneContacter(itemStack) && itemStack.getItem() instanceof CarvedContacter) {
				BlockPos blockPos = NbtUtils.readBlockPos(compoundTag.getCompound(TAG_CARVED_LODESTONE_POS));
				Optional<ResourceKey<Level>> optional = getLodestoneDimension(compoundTag);
				if (optional.isPresent() && optional.get() == level.dimension() && optional.get() == player.level().dimension()) {
					if (!level.getBlockState(blockPos).is(JerotesVillageBlocks.CARVED_LODESTONE.get())) {
						level.playSound(null, player, SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS, 1.0F, 1.0F);
							ItemStack itemstack1 = new ItemStack(JerotesVillageItems.CARVED_CONTACTER.get(), 1);
							if (!player.getAbilities().instabuild) {
								itemStack.shrink(1);
							}
							if (!player.getInventory().add(itemstack1)) {
								player.drop(itemstack1, false);
							}
						player.sendSystemMessage(Component.translatable("message.jerotesvillage.carved_lodestone_not_find").withStyle(ChatFormatting.GOLD));
					}
				}
			}
			//传送
			if (player.isShiftKeyDown() && RelationshipEvent.MoreCopperCarvedCompanyRelationship(player, 500)) {
				List<Mob> list = player.level().getEntitiesOfClass(Mob.class, player.getBoundingBox().inflate(128.0, 128.0, 128.0));
				double d = Double.MAX_VALUE;
				for (Mob carved : list) {
					if (carved == null) continue;
					if ((carved.distanceToSqr(carved)) > d) continue;
					if (!(OtherEntityFactionFind.isCarved(carved.getType()))) continue;
					if (carved.getTarget() != null && carved.getTarget() == player) continue;
					teleportPos(carved, player, player.getItemInHand(hand));
					carved.getNavigation().stop();
					if (carved.getVehicle() instanceof LivingEntity livingEntity && livingEntity.getControllingPassenger() == carved) {
						teleportPos(livingEntity, player, player.getItemInHand(hand));
						if (livingEntity instanceof Mob mob)
							mob.getNavigation().stop();
					}
				}
			}
			//常规
			else {
				player.openMenu(new SimpleMenuProvider((n, inventory, player2) -> new CarvedFactionMenu(n, inventory), Component.translatable(this.getDescriptionId())));
			}
		}
		return super.use(level, player, hand);
	}

	public static void teleport(LivingEntity livingEntity, Player player, double x, double y, double z) {
		if (livingEntity.level() instanceof ServerLevel serverLevel) {
			BlockPos tpPos = Main.findSpawnPositionNearFillOnBlock(player.level(), new BlockPos((int) x, (int) y, (int) z), 3);
			livingEntity.moveTo(tpPos, livingEntity.getYRot(), livingEntity.getXRot());
			for (int i = 0; i < 20; ++i) {
				serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, livingEntity.getRandomX(1.5), livingEntity.getRandomY(), livingEntity.getRandomZ(1.5), 0, 0.0, 0.0, 0.0, 0.0);
			}
			if (!livingEntity.isSilent()) {
				serverLevel.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), JerotesSoundEvents.TELEPORT, livingEntity.getSoundSource(), 5.0f, 1.0F);
			}
		}
	}

	public static void teleportPos(LivingEntity livingEntity, Player player, ItemStack itemStack) {
		CompoundTag compoundTag = itemStack.getTag();
		if (itemStack.getItem() instanceof CarvedContacter) {
			if (isLodestoneContacter(itemStack)) {
				if (compoundTag != null) {
					Optional<ResourceKey<Level>> optional = getLodestoneDimension(compoundTag);
					if (optional.isPresent() && optional.get() == livingEntity.level().dimension() && optional.get() == player.level().dimension()) {
						BlockPos blockPos = NbtUtils.readBlockPos(compoundTag.getCompound(TAG_CARVED_LODESTONE_POS));
						teleport(livingEntity, player, blockPos.getX(), blockPos.getY(), blockPos.getZ());
					}
					else {
						player.sendSystemMessage(Component.translatable("message.jerotesvillage.carved_lodestone_dimension_not_find").withStyle(ChatFormatting.GOLD));
					}
					return;
				}
			}
			teleport(livingEntity, player, player.getX(), player.getY(), player.getZ());
		}
	}

	public InteractionResult useOn(UseOnContext useOnContext) {
		BlockPos blockpos = useOnContext.getClickedPos();
		Level level = useOnContext.getLevel();
		if (!level.getBlockState(blockpos).is(JerotesVillageBlocks.CARVED_LODESTONE.get())) {
			return super.useOn(useOnContext);
		} else {
			level.playSound(null, blockpos, SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS, 1.0F, 1.0F);
			Player player = useOnContext.getPlayer();
			ItemStack itemstack = useOnContext.getItemInHand();
			boolean flag = !player.getAbilities().instabuild && itemstack.getCount() == 1;
			if (flag) {
				this.addLodestoneTags(level.dimension(), blockpos, itemstack.getOrCreateTag());
			} else {
				ItemStack itemstack1 = new ItemStack(JerotesVillageItems.CARVED_CONTACTER.get(), 1);
				CompoundTag compoundtag = itemstack.hasTag() ? itemstack.getTag().copy() : new CompoundTag();
				itemstack1.setTag(compoundtag);
				if (!player.getAbilities().instabuild) {
					itemstack.shrink(1);
				}
				this.addLodestoneTags(level.dimension(), blockpos, compoundtag);
				if (!player.getInventory().add(itemstack1)) {
					player.drop(itemstack1, false);
				}
			}

			return InteractionResult.sidedSuccess(level.isClientSide);
		}
	}

	private void addLodestoneTags(ResourceKey<Level> resourceKey, BlockPos blockPos, CompoundTag compoundTag) {
		compoundTag.put(TAG_CARVED_LODESTONE_POS, NbtUtils.writeBlockPos(blockPos));
		Level.RESOURCE_KEY_CODEC.encodeStart(NbtOps.INSTANCE, resourceKey).resultOrPartial(LOGGER::error).ifPresent((p_) -> {
			compoundTag.put(TAG_CARVED_LODESTONE_DIMENSION, p_);
		});
		compoundTag.putBoolean(TAG_CARVED_LODESTONE_TRACKED, true);
	}

	@Override
	public String getDescriptionId(ItemStack itemStack) {
		return isLodestoneContacter(itemStack) ? "item.jerotesvillage.lodestone_carved_contacter" : super.getDescriptionId(itemStack);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName_0().withStyle(ChatFormatting.GRAY));
		list.add(this.getDisplayName_1().withStyle(ChatFormatting.GRAY));
		list.add(this.getDisplayName_2().withStyle(ChatFormatting.GRAY));
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
