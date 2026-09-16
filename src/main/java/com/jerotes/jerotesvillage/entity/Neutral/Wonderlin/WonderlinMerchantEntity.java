package com.jerotes.jerotesvillage.entity.Neutral.Wonderlin;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.BasicItemListing;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class WonderlinMerchantEntity extends BaseWonderlinEntity implements Merchant, RangedAttackMob {
	public WonderlinMerchantEntity(EntityType<? extends WonderlinMerchantEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 5;
	}

	@Override
	public boolean wantsToPickUp(ItemStack itemStack) {
		return false;
	}

	@Override
	protected boolean canReplaceCurrentItem(ItemStack itemStack, ItemStack itemStack2) {
		return false;
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.32);
		builder = builder.add(Attributes.MAX_HEALTH, 14);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 2);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new JerotesShiftKeyDownGoal(this));
		this.goalSelector.addGoal(1, new JerotesMainSpellAttackGoal(this, this.getSpellLevel(), 60, 240, 0.5f));
		this.goalSelector.addGoal(1, new JerotesAddSpellAttackGoal(this, this.getSpellLevel(), 180, 240, 0.5f));
		this.goalSelector.addGoal(1, new JerotesCombatIMagicAttackGoal<>(this, 0.5, 15.0f, this.meleeOrRangeDistance()));
		this.goalSelector.addGoal(1, new JerotesCombatIIMagicAttackGoal<>(this, 0.2, true, this.meleeOrRangeDistance()));
		this.goalSelector.addGoal(1, new JerotesCombatIIIMagicAttackGoal<>(this, 0.5, 15.0f, this.meleeOrRangeDistance()));
		this.goalSelector.addGoal(1, new JerotesCombatIVMagicAttackGoal<>(this, 0.5, 15.0f, this.meleeOrRangeDistance()));
		this.goalSelector.addGoal(1, new JerotesRangedBowAttackGoal<WonderlinMerchantEntity>(this, 0.5, 20, 15.0f));
		this.goalSelector.addGoal(1, new JerotesRangedCrossbowAttackGoal<WonderlinMerchantEntity>(this, 1.0, 15.0f));
		this.goalSelector.addGoal(1, new JerotesRangedThrowAttackGoal<>(this, 0.4, 40, 12.0F));
		this.goalSelector.addGoal(1, new JerotesRangedJavelinAttackGoal<>(this, 1.0, 60, 12.0F, this.meleeOrRangeDistance()));
		this.goalSelector.addGoal(1, new JerotesSpearUseGoal<>(this,  1.0, 1.0, 10.0f, 2.0f));
		this.goalSelector.addGoal(1, new JerotesPikeUseGoal(this,  1.2, true));

		this.goalSelector.addGoal(2, new WonderlinMeleeAttackGoal(WonderlinMerchantEntity.this));
		this.goalSelector.addGoal(2, new WonderlinAvoidGoal(this));
	}

	@Override
	public ItemStack createSpawnWeapon(float weaponRandom) {
		return new ItemStack(JerotesVillageItems.VINE_THROWING_BALL.get());
	}
	//协助所有同伴
	@Override
	public boolean helpAllSameFaction() {
		return false;
	}
	//被所有同伴协助
	@Override
	public boolean helpByAllSameFaction() {
		return false;
	}
	//同类协助
	@Override
	public boolean helpSameType() {
		return true;
	}
	//特例协助
	@Override
	public boolean canBeHelp(Entity entity) {
		return false;
	}

	public boolean canRush() {
		return true;
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
                this.startTrading(player);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
		}
		return super.mobInteract(player, interactionHand);
	}

	private void startTrading(Player player) {
		this.updateSpecialPrices(player);
		this.setTradingPlayer(player);
		this.openTradingScreen(player, this.getDisplayName(), 1);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.random.nextInt(20 * 60 * 20) == 1) {
			for (MerchantOffer merchantOffer : this.getOffers()) {
				merchantOffer.resetUses();
			}
		}
		if (this.isAggressive() && this.tickCount % 10 == 0) {
			if (!this.level().isClientSide) {
				this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 20, 0));
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

	private void updateSpecialPrices(Player player) {
		this.resetSpecialPrices();
		boolean helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.MOUNTAIN_REALM_HELMET.get() || player.getItemBySlot(EquipmentSlot.HEAD).getItem() == JerotesVillageItems.WONDERLIN_FAKE_EARS.get();
		boolean chestplate = player.getItemBySlot(EquipmentSlot.CHEST).getItem() == JerotesVillageItems.MOUNTAIN_REALM_CHESTPLATE.get();
		boolean legs = player.getItemBySlot(EquipmentSlot.LEGS).getItem() == JerotesVillageItems.MOUNTAIN_REALM_LEGGINGS.get();
		boolean feet = player.getItemBySlot(EquipmentSlot.FEET).getItem() == JerotesVillageItems.MOUNTAIN_REALM_BOOTS.get();
		if (helmet || chestplate || legs || feet) {
			for (MerchantOffer merchantOffer : this.getOffers()) {
				double d = 0.4;
				int n3 = (int)Math.floor(d * (double)merchantOffer.getBaseCostA().getCount());
				merchantOffer.addToSpecialPriceDiff(-Math.max(n3, 1));
			}
		}
	}
	private void resetSpecialPrices() {
		for (MerchantOffer merchantOffer : this.getOffers()) {
			merchantOffer.resetSpecialPriceDiff();
		}
	}

	@Override
	public void notifyTrade(MerchantOffer merchantOffer) {
		merchantOffer.increaseUses();
		this.ambientSoundTime = -this.getAmbientSoundInterval();
		this.rewardTradeXp(merchantOffer);
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
		return bl ? SoundEvents.PLAYER_LEVELUP : JerotesVillageSoundEvents.WONDERLIN_AMBIENT;
	}

	@Nullable
	@Override
	public Entity changeDimension(ServerLevel serverLevel) {
		this.stopTrading();
		return super.changeDimension(serverLevel);
	}

	protected void stopTrading() {
		this.setTradingPlayer(null);
		this.resetSpecialPrices();
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
		VillagerTrades.ItemListing[] arritemListing = WINDERLIN_MERCHANT.get(1);
		if (arritemListing == null) {
			return;
		}
		MerchantOffers merchantOffers = this.getOffers();
		this.addOffersFromItemListings(merchantOffers, arritemListing, 9);
	}

	public static final Int2ObjectMap<VillagerTrades.ItemListing[]> WINDERLIN_MERCHANT =
			new Int2ObjectOpenHashMap(ImmutableMap.of(
					//卖出
					1, new VillagerTrades.ItemListing[]{
							//木棍-仙境果
							new BasicItemListing(new ItemStack(Items.STICK, 64),
									new ItemStack(JerotesVillageItems.WONDERLAND_FRUIT.get(), 1), 30, 5, 0.05f),
							//苹果-仙境果
							new BasicItemListing(new ItemStack(Items.APPLE, 2),
									new ItemStack(JerotesVillageItems.WONDERLAND_FRUIT.get(), 1), 30, 5, 0.05f),
							//仙境果-仙酿
							new BasicItemListing(new ItemStack(JerotesVillageItems.WONDERLAND_FRUIT.get(), 3),
									new ItemStack(JerotesVillageItems.IMMORTAL_BREW.get(), 1), 30, 5, 0.05f),
							//仙境果-芒药绷带
							new BasicItemListing(new ItemStack(JerotesVillageItems.WONDERLAND_FRUIT.get(), 5),
									new ItemStack(JerotesVillageItems.AWN_HERB_BANDAGE.get(), 1), 30, 5, 0.05f),
							//仙境果-奇灵小画
							new BasicItemListing(new ItemStack(JerotesVillageItems.WONDERLAND_FRUIT.get(), 3),
									new ItemStack(JerotesVillageItems.WONDERLIN_PAINTING.get(), 1), 30, 5, 0.05f),
							//仙境果-奇灵滑翔伞
							new BasicItemListing(new ItemStack(JerotesVillageItems.WONDERLAND_FRUIT.get(), 32),
									new ItemStack(JerotesVillageItems.WONDERLIN_HANG_GLIDER.get(), 1), 30, 5, 0.05f),
							//仙境果-仙境树苗
							new BasicItemListing(new ItemStack(JerotesVillageItems.WONDERLAND_FRUIT.get(), 2),
									new ItemStack(JerotesVillageItems.WONDERLAND_SAPLING.get(), 1), 30, 5, 0.05f),
							//仙境果-藤蔓投团
							new BasicItemListing(new ItemStack(JerotesVillageItems.WONDERLAND_FRUIT.get(), 3),
									new ItemStack(JerotesVillageItems.VINE_THROWING_BALL.get(), 4), 30, 5, 0.05f),
							//仙境果-奇灵小包
							new BasicItemListing(new ItemStack(JerotesVillageItems.WONDERLAND_FRUIT.get(), 12),
									new ItemStack(JerotesVillageItems.WONDERLIN_SMALL_BAG.get(), 1), 30, 5, 0.05f),
							//仙境果-奇灵耳朵
							new BasicItemListing(new ItemStack(JerotesVillageItems.WONDERLAND_FRUIT.get(), 16),
									new ItemStack(JerotesVillageItems.WONDERLIN_FAKE_EARS.get(), 1), 30, 5, 0.05f),
							//仙境果-集箭之球
							new BasicItemListing(new ItemStack(JerotesVillageItems.WONDERLAND_FRUIT.get(), 32),
									new ItemStack(JerotesVillageItems.COLLECTING_ARROWS_BALL.get(), 1), 30, 5, 0.05f),
							//仙境果-可疑树枝
							new BasicItemListing(new ItemStack(JerotesVillageItems.WONDERLAND_FRUIT.get(), 32),
									new ItemStack(JerotesVillageItems.SUSPICIOUS_BRANCH.get(), 1), 30, 5, 0.05f),
							//仙境果-可疑树枝
							new BasicItemListing(new ItemStack(JerotesVillageItems.WONDERLAND_FRUIT.get(), 16),
									new ItemStack(JerotesVillageItems.RAMBLER_SKIN_WAND.get(), 1), 30, 5, 0.05f),
							//仙境果-镇山大王雕像
							new BasicItemListing(new ItemStack(JerotesVillageItems.WONDERLAND_FRUIT.get(), 64),
									new ItemStack(JerotesVillageItems.TYRANT_OF_THE_MOUNTAIN_REALM_STATUE.get(), 1), 30, 5, 0.05f),
							//仙境果-粉黏液球
							new BasicItemListing(new ItemStack(JerotesVillageItems.WONDERLAND_FRUIT.get(), 8),
									new ItemStack(JerotesVillageItems.PINK_SLIME_BALL.get(), 64), 30, 5, 0.05f)
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

