package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class IrishtailChickenEntity extends Chicken implements JerotesEntity {

	public IrishtailChickenEntity(EntityType<? extends IrishtailChickenEntity> type, Level world) {
		super(type, world);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
		builder = builder.add(Attributes.MAX_HEALTH, 4);
		builder = builder.add(Attributes.FOLLOW_RANGE, 20);
		return builder;
	}

	@Nullable
	@Override
	public IrishtailChickenEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		return JerotesVillageEntityType.IRISHTAIL_CHICKEN.get().create(serverLevel);
	}

	public static final String IRISHTAIL_CHICKEN = "IsIrishtailChicken";
	public static boolean isIrishtailChicken(ItemStack itemStack) {
		CompoundTag compoundtag = itemStack.hasTag() ? itemStack.getTag().copy() : new CompoundTag();
		return compoundtag.getBoolean(IRISHTAIL_CHICKEN);
	}

	public static void setIrishtailChicken(ItemStack itemStack) {
		CompoundTag compoundtag = itemStack.hasTag() ? itemStack.getTag().copy() : new CompoundTag();
		compoundtag.putBoolean(IRISHTAIL_CHICKEN,  true);
		itemStack.setTag(compoundtag);
	}

	@Nullable
	public ItemEntity spawnAtLocation(ItemStack itemStack, float f) {
		if (itemStack.isEmpty()) {
			return null;
		} else if (this.level().isClientSide) {
			return null;
		} else {
			if (itemStack.getItem() == Items.EGG) {
				setIrishtailChicken(itemStack);
				itemStack.setHoverName(Component.translatable("item.jerotesvillage.irishtail_chicken_egg"));

				//itemStack = new ItemStack(JerotesVillageItems.IRISHTAIL_CHICKEN_EGG.get());
			}
			ItemEntity itementity = new ItemEntity(this.level(), this.getX(), this.getY() + (double)f, this.getZ(), itemStack);
			itementity.setDefaultPickUpDelay();
			if (this.captureDrops() != null) {
				this.captureDrops().add(itementity);
			} else {
				this.level().addFreshEntity(itementity);
			}

			return itementity;
		}
	}
}




