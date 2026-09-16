package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.init.JerotesVillageDimensions;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class ItemMagicMap extends Item {
	private final String string;

	public ItemMagicMap(Properties properties, String string) {
		super(properties);
		this.string = string;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.BLOCK;
	}

	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 72000;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		player.startUsingItem(interactionHand);
		//二轮世界判定
		if (player.level().dimension() == JerotesVillageDimensions.SECOND_ROUND_WORLD) {
			player.getCooldowns().addCooldown(this, 20);
			if (level.isClientSide) {
				return InteractionResultHolder.success(itemStack);
			}
			player.awardStat(Stats.ITEM_USED.get(this));
			BlockPos blockPos = player.getOnPos();

			if (level instanceof ServerLevel serverLevel) {
				ResourceKey<Structure> targetStructure = ResourceKey.create(
						Registries.STRUCTURE,
						new ResourceLocation(JerotesVillage.MODID, this.string)
				);
				Registry<Structure> structureRegistry = serverLevel.registryAccess().registryOrThrow(Registries.STRUCTURE);
				Optional<Holder.Reference<Structure>> structureHolder = structureRegistry.getHolder(targetStructure);
				HolderSet<Structure> holderSet = HolderSet.direct(structureHolder.get());
				BlockPos blockPos2 = serverLevel.findNearestMapStructure(
						TagKey.create(Registries.STRUCTURE, new ResourceLocation(JerotesVillage.MODID, string + "_map")), player.blockPosition(), 100, false);
				if (blockPos2!= null) {
					player.sendSystemMessage(Component.translatable("chat.coordinates", blockPos2.getX(), blockPos2.getY(), blockPos2.getZ()));
				}
			}

			for (ItemStack map : player.level().getServer().getLootData().getLootTable(new ResourceLocation("jerotesvillage:gameplay/jerotesvillage_magic_map/" + this.string + "_map")).getRandomItems(new LootParams.Builder((ServerLevel) player.level()).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockPos)).withParameter(LootContextParams.BLOCK_STATE, player.level().getBlockState(blockPos)).withOptionalParameter(LootContextParams.BLOCK_ENTITY, player.level().getBlockEntity(blockPos)).create(LootContextParamSets.EMPTY))) {
				if (!player.getInventory().add(map)) {
					player.drop(map, false);
				}
			}
			player.level().playSound(null, player, SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, player.getSoundSource(), 1.0f, 1.0f);
			if (!player.getAbilities().instabuild) {
				itemStack.shrink(1);
			}
		}
		return InteractionResultHolder.consume(itemStack);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(Component.translatable("item.jerotesvillage.magic_map.desc").withStyle(ChatFormatting.GRAY));
	}

}
