package com.jerotes.jerotesvillage.block.HarmoniousWonderland;

import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Boss.Biome.CelestialCoilvineEntity;
import com.jerotes.jerotesvillage.event.AdvancementEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageBlockEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.util.SpawnRules;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class BotanizeAltarEntity extends BlockEntity {
	public static final String GIFT_ITEM = "jerotesvillage:gameplay/jerotesvillage_other/botanize_gift";
	private int tickCount;

	public BotanizeAltarEntity(BlockPos blockPos, BlockState blockState) {
		super(JerotesVillageBlockEntityType.BOTANIZE_ALTAR.get(), blockPos, blockState);
	}

	@Nullable
	public static <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
		return (levelIn, blockPos, blockStateIn, blockEntity) -> {
			BotanizeAltarEntity botanizeAltarEntity = (BotanizeAltarEntity) blockEntity;
			if (levelIn.isClientSide()) {
				botanizeAltarEntity.clientTick();
			} else {
				botanizeAltarEntity.serverTick();
			}
		};
	}

	public static boolean canOffer(ItemStack itemStack) {
		return itemStack.is(JerotesVillageItems.MOUNTAIN_REALM_NINE_RINGED_DAO.get())
				|| itemStack.is(JerotesVillageItems.TYRANT_OF_THE_MOUNTAIN_REALM_HELMET_PLUME.get())
				|| itemStack.is(JerotesVillageItems.MUSIC_DISC_TYRANT_OF_THE_MOUNTAIN_REALM.get())
				|| itemStack.is(ItemTags.FLOWERS);
	}

	private void clientTick() {
		Player player = Minecraft.getInstance().player;
		if (player == null || !(canOffer(player.getMainHandItem()) || canOffer(player.getOffhandItem()))) {
			return;
		}
		++this.tickCount;
		if (this.tickCount % 5 != 0) {
			return;
		}
		BlockPos blockPos = this.getBlockPos();
		this.level.addParticle(JerotesVillageParticleTypes.CELESTIAL_COILVINE_TARGET.get(),
				blockPos.getX() + 0.5D, blockPos.getY() + 1.2D, blockPos.getZ() + 0.5D,
				(this.level.random.nextFloat() / 5 - 0.1f) * 0.05f, (0.15f + this.level.random.nextFloat() / 5) * 0.05f, (this.level.random.nextFloat() / 5 - 0.1f) * 0.05f);
	}

	private void serverTick() {
		if (!(this.level instanceof ServerLevel serverLevel)) {
			return;
		}
		if (this.getSummonTick() > 0) {
			this.setSummonTick(this.getSummonTick() + 1);
			BlockPos blockPos = this.getBlockPos();
			for (int i = 0; i < 64; ++i) {
				serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, JerotesVillageBlocks.WONDERLAND_WOOD.get().defaultBlockState()),
						blockPos.getX() + 0.5D + (serverLevel.random.nextDouble() - 0.5D),
						blockPos.getY() + 0.5D * serverLevel.random.nextDouble(),
						blockPos.getZ() + 0.5D + (serverLevel.random.nextDouble() - 0.5D),
						0, 0.0D, 0.0D, 0.0D, 0.0D);
			}
		}
		if (this.getSummonTick() >= 60) {
			this.summonBoss(serverLevel);
		}
	}

	public void summonBoss(ServerLevel serverLevel) {
		BlockPos blockPos = Main.findSpawnPositionNearFillOnBlock(serverLevel, this.getBlockPos(), 4);
		if (blockPos == null || !SpawnRules.BossSpawnFind(JerotesVillageEntityType.CELESTIAL_COILVINE.get(), serverLevel, blockPos)) {
			this.setSummonTick(0);
			return;
		}
		CelestialCoilvineEntity boss = JerotesVillageEntityType.CELESTIAL_COILVINE.get().spawn(serverLevel, blockPos, MobSpawnType.EVENT);
		if (boss == null) {
			this.setSummonTick(0);
			return;
		}
		boss.yBodyRot = 0.0f;
		boss.yBodyRotO = 0.0f;
		if (!boss.level().isClientSide()) {
			boss.setAnimTick(20);
			boss.setBodyType(1);
			boss.setFirstHeal(true);
			boss.setAnimationState("start");
			boss.setHideUseTick(140);
			boss.setSpellTick(145);
		}
		boss.setDeltaMovement(0, 0, 0);
		serverLevel.playSound(null, blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D, JerotesVillageSoundEvents.CELESTIAL_COILVINE_AMBIENT, SoundSource.NEUTRAL, 20.0f, 1.0f);
		serverLevel.playSound(null, blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D,
				JerotesVillageSoundEvents.CELESTIAL_COILVINE_TELEPORT, SoundSource.NEUTRAL, 20.0f, 1.0f);
		this.setSummonTick(0);
	}

	public int getSummonTick() {
		return this.summonTick;
	}

	public void setSummonTick(int n) {
		this.summonTick = n;
	}

	public int summonTick = 0;

	@Override
	public void load(CompoundTag compoundTag) {
		super.load(compoundTag);
		this.summonTick = compoundTag.getInt("SummonTick");
	}

	@Override
	public void saveAdditional(CompoundTag compoundTag) {
		super.saveAdditional(compoundTag);
		compoundTag.putInt("SummonTick", this.summonTick);
	}

	public void offer(Player player, ItemStack itemStack) {
		if (!(this.level instanceof ServerLevel serverLevel)) {
			return;
		}
		ItemStack result;
		if (itemStack.is(JerotesVillageItems.MOUNTAIN_REALM_NINE_RINGED_DAO.get())) {
			result = new ItemStack(JerotesVillageItems.CONTINUOUS_CUT_LEAF.get());
		} else if (itemStack.is(JerotesVillageItems.TYRANT_OF_THE_MOUNTAIN_REALM_HELMET_PLUME.get())) {
			if (player instanceof ServerPlayer serverPlayer) {
				AdvancementEvent.AdvancementGive(serverPlayer, "jerotesvillage:plot");
			}
			result = new ItemStack(JerotesVillageItems.CELESTIAL_COILVINE_BROKEN_VINE.get());
		} else if (itemStack.is(JerotesVillageItems.MUSIC_DISC_TYRANT_OF_THE_MOUNTAIN_REALM.get())) {
			result = new ItemStack(JerotesVillageItems.MUSIC_DISC_CELESTIAL_COILVINE.get());
		} else {
			result = this.gift(serverLevel);
		}
		if (result.isEmpty()) {
			return;
		}
		BlockPos blockPos = this.getBlockPos().above();
		ItemEntity itemEntity = new ItemEntity(serverLevel, blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D, result);
		itemEntity.setDefaultPickUpDelay();
		serverLevel.addFreshEntity(itemEntity);
		serverLevel.playSound(null, this.getBlockPos(), JerotesVillageSoundEvents.BOTANIZE_GIFT, SoundSource.BLOCKS, 1.0f, 1.0f);
	}

	private ItemStack gift(ServerLevel serverLevel) {
		BlockPos blockPos = this.getBlockPos();
		for (ItemStack itemStack : serverLevel.getServer().getLootData().getLootTable(new ResourceLocation(GIFT_ITEM)).
				getRandomItems(new LootParams.Builder(serverLevel).
						withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockPos)).
						withParameter(LootContextParams.BLOCK_STATE, serverLevel.getBlockState(blockPos)).
						withOptionalParameter(LootContextParams.BLOCK_ENTITY, this).
						create(LootContextParamSets.EMPTY))) {
			return itemStack;
		}
		return ItemStack.EMPTY;
	}
}
