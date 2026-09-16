package com.jerotes.jerotesvillage.entity.Other.WonderlinPainting;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.decoration.PaintingVariants;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WonderlinPaintingEntity extends Painting {
	private static final EntityDataAccessor<Holder<PaintingVariant>> DATA_PAINTING_VARIANT_ID = SynchedEntityData.defineId(WonderlinPaintingEntity.class, EntityDataSerializers.PAINTING_VARIANT);
	private static final ResourceKey<PaintingVariant> DEFAULT_VARIANT = PaintingVariants.KEBAB;
	private static Holder<PaintingVariant> getDefaultVariant() {
		return BuiltInRegistries.PAINTING_VARIANT.getHolderOrThrow(DEFAULT_VARIANT);
	}

	public WonderlinPaintingEntity(EntityType<? extends Painting> p_31904_, Level p_31905_) {
		super(p_31904_, p_31905_);
	}

	public WonderlinPaintingEntity(EntityType<? extends Painting> p_31706_, Level p_31707_, BlockPos p_31708_) {
		this(p_31706_, p_31707_);
		this.pos = p_31708_;
	}

	public WonderlinPaintingEntity(Level p_218874_, BlockPos p_218875_) {
		this(JerotesVillageEntityType.WONDERLIN_PAINTING.get(), p_218874_, p_218875_);
	}

	public WonderlinPaintingEntity(Level level, BlockPos blockPos, Direction direction, Holder<PaintingVariant> holder) {
		this(JerotesVillageEntityType.WONDERLIN_PAINTING.get(), level);
		this.setVariant(holder);
		this.setDirection(direction);
		this.pos = blockPos;
	}

	protected void defineSynchedData() {
		this.entityData.define(DATA_PAINTING_VARIANT_ID, getDefaultVariant());
	}

	public void onSyncedDataUpdated(EntityDataAccessor<?> p_218896_) {
		if (DATA_PAINTING_VARIANT_ID.equals(p_218896_)) {
			this.recalculateBoundingBox();
		}
	}
	public void setVariant(Holder<PaintingVariant> p_218892_) {
		this.entityData.set(DATA_PAINTING_VARIANT_ID, p_218892_);
	}
	public Holder<PaintingVariant> getVariant() {
		return (Holder)this.entityData.get(DATA_PAINTING_VARIANT_ID);
	}

	public static Optional<WonderlinPaintingEntity> createModded(Level p_218888_, BlockPos p_218889_, Direction p_218890_) {
		WonderlinPaintingEntity painting = new WonderlinPaintingEntity(p_218888_, p_218889_);
		List<Holder<PaintingVariant>> list = new ArrayList<>();
		BuiltInRegistries.PAINTING_VARIANT.getTagOrEmpty(
				TagKey.create(ForgeRegistries.PAINTING_VARIANTS.getRegistryKey(), new ResourceLocation(JerotesVillage.MODID, "placeable"))

		).forEach(list::add);
		if (list.isEmpty()) {
			return Optional.empty();
		} else {
			painting.setDirection(p_218890_);
			list.removeIf((p_218886_) -> {
				painting.setVariant(p_218886_);
				return !painting.survives();
			});
			if (list.isEmpty()) {
				return Optional.empty();
			} else {
				int i = list.stream().mapToInt(WonderlinPaintingEntity::variantArea).max().orElse(0);
				list.removeIf((p_218883_) -> {
					return variantArea(p_218883_) < i;
				});
				Optional<Holder<PaintingVariant>> optional = Util.getRandomSafe(list, painting.random);
				if (optional.isEmpty()) {
					return Optional.empty();
				} else {
					painting.setVariant(optional.get());
					painting.setDirection(p_218890_);
					return Optional.of(painting);
				}
			}
		}
	}

	private static int variantArea(Holder<PaintingVariant> p_218899_) {
		return p_218899_.value().getWidth() * p_218899_.value().getHeight();
	}

	public void dropItem(@Nullable Entity p_31925_) {
		if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
			this.playSound(SoundEvents.PAINTING_BREAK, 1.0F, 1.0F);
			if (p_31925_ instanceof Player player) {
				if (player.getAbilities().instabuild) {
					return;
				}
			}

			this.spawnAtLocation(JerotesVillageItems.WONDERLIN_PAINTING.get());
		}
	}

	public ItemStack getPickResult() {
		return new ItemStack(JerotesVillageItems.WONDERLIN_PAINTING.get());
	}
}
