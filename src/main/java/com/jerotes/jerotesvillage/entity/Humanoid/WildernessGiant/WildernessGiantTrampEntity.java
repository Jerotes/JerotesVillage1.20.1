package com.jerotes.jerotesvillage.entity.Humanoid.WildernessGiant;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.jerotes.jerotes.goal.JerotesTradeWithPlayerGoal;
import com.jerotes.jerotes.init.JerotesItems;
import com.jerotes.jerotesvillage.event.AdvancementEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.BasicItemListing;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class WildernessGiantTrampEntity extends WildernessGiantEntity implements Merchant {

	public WildernessGiantTrampEntity(EntityType type, Level world) {
		super(type, world);
		xpReward = 20;
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
		this.maybeWearArmor(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.WILDERNESS_GIANT_HELMET.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.WILDERNESS_GIANT_CHESTPLATE.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.WILDERNESS_GIANT_LEGGINGS.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.WILDERNESS_GIANT_BOOTS.get()), randomSource);
	}

	private void maybeWearArmor(EquipmentSlot equipmentSlot, ItemStack itemStack, RandomSource randomSource) {
		if (randomSource.nextFloat() < 0.12f) {
			this.setItemSlot(equipmentSlot, itemStack);
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.32);
		builder = builder.add(Attributes.MAX_HEALTH, 85);
		builder = builder.add(Attributes.ARMOR, 2);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 10);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.5);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.75);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new JerotesTradeWithPlayerGoal(this));
	}


	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		MerchantOffers merchantOffers = this.getOffers();
		if (!merchantOffers.isEmpty()) {
			compoundTag.put("Offers", merchantOffers.createTag());
		}
		this.writeInventoryToTag(compoundTag);
		if (this.wanderTarget != null) {
			compoundTag.put("WanderTarget", NbtUtils.writeBlockPos(this.wanderTarget));
		}
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (compoundTag.contains("Offers", 10)) {
			this.offers = new MerchantOffers(compoundTag.getCompound("Offers"));
		}
		this.readInventoryFromTag(compoundTag);
		if (compoundTag.contains("WanderTarget")) {
			this.wanderTarget = NbtUtils.readBlockPos(compoundTag.getCompound("WanderTarget"));
		}
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (this.getTarget() == null && this.isAlive() && !this.isTrading() && !player.isShiftKeyDown()) {
			if (interactionHand == InteractionHand.MAIN_HAND) {
				player.awardStat(Stats.TALKED_TO_VILLAGER);
			}
			if (this.getOffers().isEmpty()) {
				return InteractionResult.sidedSuccess(this.level().isClientSide);
			}
			if (this.offers != null && !this.level().isClientSide && !this.offers.isEmpty()) {
				this.setTradingPlayer(player);
				this.openTradingScreen(player, this.getDisplayName(), 1);
			}
			return InteractionResult.sidedSuccess(this.level().isClientSide);
		}
		return super.mobInteract(player, interactionHand);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.random.nextInt(20 * 60 * 20) == 1) {
			for (MerchantOffer merchantOffer : this.getOffers()) {
				merchantOffer.resetUses();
			}
		}
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		boolean bl = super.hurt(damageSource, amount);
		if (bl) {
			this.stopTrading();
		}
		return bl;
	}

	@Override
	public ItemStack createSpawnWeapon(float weaponRandom) {
		if (weaponRandom > 0.8) {
			return new ItemStack(JerotesVillageItems.WILDERNESS_STICK.get());
		}
		if (weaponRandom > 0.6) {
			return new ItemStack(JerotesVillageItems.WILDERNESS_STAFF.get());
		}
		if (weaponRandom > 0.4) {
			return new ItemStack(JerotesVillageItems.WILDERNESS_THROWING_SPEAR_OF_JAVELIN.get());
		}
		if (weaponRandom > 0.2) {
			return new ItemStack(JerotesVillageItems.WILDERNESS_ARROW_THROWER.get());
		}
		return new ItemStack(JerotesVillageItems.WILDERNESS_BOOMERANG.get());
	}


	//商人

	@Nullable
	private Player tradingPlayer;
	@Nullable
	protected MerchantOffers offers;
	@Nullable
	private BlockPos wanderTarget;

	@Override
	public void setTradingPlayer(@Nullable Player player) {
		this.tradingPlayer = player;
	}

	@Nullable
	@Override
	public Player getTradingPlayer() {
		return this.tradingPlayer;
	}

	public boolean isTrading() {
		return this.tradingPlayer != null;
	}

	@Override
	public MerchantOffers getOffers() {
		if (this.offers == null) {
			this.offers = new MerchantOffers();
			this.updateTrades();
		}
		return this.offers;
	}

	@Override
	public void overrideOffers(@Nullable MerchantOffers merchantOffers) {
	}

	@Override
	public void overrideXp(int n) {
	}

	@Override
	public void notifyTrade(MerchantOffer merchantOffer) {
		merchantOffer.increaseUses();
		this.ambientSoundTime = -this.getAmbientSoundInterval();
		this.rewardTradeXp(merchantOffer);
		if (this.tradingPlayer instanceof ServerPlayer player) {
			AdvancementEvent.AdvancementGive(player, "jerotesvillage:prohibit_rip_of");
		}
	}

	protected void rewardTradeXp(MerchantOffer merchantOffer) {
		if (merchantOffer.shouldRewardExp()) {
			int n = 3 + this.random.nextInt(4);
			this.level().addFreshEntity(new ExperienceOrb(this.level(), this.getX(), this.getY() + 0.5, this.getZ(), n));
		}
	}

	@Override
	public boolean showProgressBar() {
		return false;
	}

	@Override
	public void notifyTradeUpdated(ItemStack itemStack) {
		if (!this.level().isClientSide && this.ambientSoundTime > -this.getAmbientSoundInterval() + 20) {
			this.ambientSoundTime = -this.getAmbientSoundInterval();
			this.playSound(this.getTradeUpdatedSound(!itemStack.isEmpty()), this.getSoundVolume(), this.getVoicePitch());
		}
	}

	@Override
	public int getVillagerXp() {
		return 0;
	}

	@Override
	public SoundEvent getNotifyTradeSound() {
		return SoundEvents.PLAYER_LEVELUP;
	}

	protected SoundEvent getTradeUpdatedSound(boolean bl) {
		return bl ? SoundEvents.PLAYER_LEVELUP : JerotesVillageSoundEvents.MEROR_TOOL_USE;
	}

	@Nullable
	@Override
	public Entity changeDimension(ServerLevel serverLevel) {
		this.stopTrading();
		return super.changeDimension(serverLevel);
	}

	protected void stopTrading() {
		this.setTradingPlayer(null);
	}

	@Override
	public void die(DamageSource damageSource) {
		super.die(damageSource);
		this.stopTrading();
	}

	@Override
	public SlotAccess getSlot(int n) {
		int n2 = n - 300;
		if (n2 >= 0 && n2 < this.getInventory().getContainerSize()) {
			return SlotAccess.forContainer(this.getInventory(), n2);
		}
		return super.getSlot(n);
	}

	protected void updateTrades() {
		VillagerTrades.ItemListing[] arritemListing = MEROR_MERCHANT.get(1);
		VillagerTrades.ItemListing[] arritemListing2 = MEROR_MERCHANT.get(2);
		if (arritemListing == null || arritemListing2 == null) {
			return;
		}
		MerchantOffers merchantOffers = this.getOffers();
		this.addOffersFromItemListings(merchantOffers, arritemListing, 8);
		this.addOffersFromItemListings(merchantOffers, arritemListing2, 8);
	}

	public static final Int2ObjectMap<VillagerTrades.ItemListing[]> MEROR_MERCHANT =
			new Int2ObjectOpenHashMap(ImmutableMap.of(
					//玩家卖出
					1, new VillagerTrades.ItemListing[]{
							//漫兽肉
							new BasicItemListing(new ItemStack(JerotesVillageItems.RAMBLER_MEAT.get(), 1),
									new ItemStack(JerotesVillageItems.RAMBLER_SKIN.get(), 2), 30, 5, 0.05f),
							//熟漫兽肉
							new BasicItemListing(new ItemStack(JerotesVillageItems.COOKED_RAMBLER_MEAT.get(), 1),
									new ItemStack(JerotesVillageItems.RAMBLER_SKIN.get(), 3), 30, 5, 0.05f),
							//云毛鹿肉
							new BasicItemListing(new ItemStack(JerotesVillageItems.CLOUD_FUR_VENISON.get(), 1),
									new ItemStack(JerotesVillageItems.RAMBLER_SKIN.get(), 3), 30, 5, 0.05f),
							//熟云毛鹿肉
							new BasicItemListing(new ItemStack(JerotesVillageItems.COOKED_CLOUD_FUR_VENISON.get(), 1),
									new ItemStack(JerotesVillageItems.RAMBLER_SKIN.get(), 4), 30, 5, 0.05f),
							//高强度丝线
							new BasicItemListing(new ItemStack(JerotesItems.HIGH_STRENGTH_STRING.get(), 1),
									new ItemStack(JerotesVillageItems.RAMBLER_SKIN.get(), 2), 30, 5, 0.05f)
					},
					//玩家买入
					2, new VillagerTrades.ItemListing[]{
							//荒野巨人套装
							new BasicItemListing(new ItemStack(JerotesVillageItems.RAMBLER_SKIN.get(), 2),
									new ItemStack(JerotesVillageItems.WILDERNESS_GIANT_HELMET.get(), 1), 30, 5, 0.05f),
							new BasicItemListing(new ItemStack(JerotesVillageItems.RAMBLER_SKIN.get(), 4),
									new ItemStack(JerotesVillageItems.WILDERNESS_GIANT_CHESTPLATE.get(), 1), 30, 5, 0.05f),
							new BasicItemListing(new ItemStack(JerotesVillageItems.RAMBLER_SKIN.get(), 4),
									new ItemStack(JerotesVillageItems.WILDERNESS_GIANT_LEGGINGS.get(), 1), 30, 5, 0.05f),
							new BasicItemListing(new ItemStack(JerotesVillageItems.RAMBLER_SKIN.get(), 2),
									new ItemStack(JerotesVillageItems.WILDERNESS_GIANT_BOOTS.get(), 1), 30, 5, 0.05f),
							new BasicItemListing(new ItemStack(JerotesVillageItems.RAMBLER_SKIN.get(), 6),
									new ItemStack(JerotesVillageItems.WILDERNESS_STICK.get(), 1), 30, 5, 0.05f),
							//褐毒蛞蝓
							new BasicItemListing(new ItemStack(JerotesVillageItems.RAMBLER_SKIN.get(), 4),
									new ItemStack(JerotesVillageItems.BROWN_VENOMOUS_SLUG.get(), 1), 30, 5, 0.05f),
							//漫兽皮革条
							new BasicItemListing(new ItemStack(JerotesVillageItems.RAMBLER_SKIN.get(), 2),
									new ItemStack(JerotesVillageItems.RAMBLER_LEATHER_STRIP.get(), 1), 30, 5, 0.05f),
							//捕获的褐毒蛞蝓
							new BasicItemListing(new ItemStack(JerotesVillageItems.RAMBLER_SKIN.get(), 6),
									new ItemStack(JerotesVillageItems.CAPTURED_BROWN_VENOMOUS_SLUG.get(), 1), 30, 5, 0.05f),
							//捕网
							new BasicItemListing(new ItemStack(JerotesVillageItems.RAMBLER_SKIN.get(), 3),
									new ItemStack(JerotesVillageItems.SPIRVE_INSECT_NET.get(), 1), 30, 5, 0.05f),
							//荒野木芯
							new BasicItemListing(new ItemStack(JerotesVillageItems.RAMBLER_SKIN.get(), 3),
									new ItemStack(JerotesVillageItems.WILDERNESS_WOOD_CORE.get(), 1), 30, 5, 0.05f)
							}
			));

	protected void addOffersFromItemListings(MerchantOffers merchantOffers, VillagerTrades.ItemListing[] arritemListing, int n) {
		ArrayList arrayList = Lists.newArrayList((Object[])arritemListing);
		int n2 = 0;
		while (n2 < n && !arrayList.isEmpty()) {
			MerchantOffer merchantOffer = ((VillagerTrades.ItemListing)arrayList.remove(this.random.nextInt(arrayList.size()))).getOffer(this, this.random);
			if (merchantOffer == null) continue;
			merchantOffers.add(merchantOffer);
			++n2;
		}
	}

	@Override
	public Vec3 getRopeHoldPosition(float f) {
		float f2 = Mth.lerp(f, this.yBodyRotO, this.yBodyRot) * 0.017453292f;
		Vec3 vec3 = new Vec3(0.0, this.getBoundingBox().getYsize() - 1.0, 0.2);
		return this.getPosition(f).add(vec3.yRot(-f2));
	}

	@Override
	public boolean isClientSide() {
		return this.level().isClientSide;
	}
}