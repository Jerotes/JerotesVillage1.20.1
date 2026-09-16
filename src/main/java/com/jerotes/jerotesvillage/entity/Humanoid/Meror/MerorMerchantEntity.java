package com.jerotes.jerotesvillage.entity.Humanoid.Meror;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.jerotes.jerotes.goal.JerotesTradeWithPlayerGoal;
import com.jerotes.jerotes.init.JerotesItems;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.event.AdvancementEvent;
import com.jerotes.jerotesvillage.goal.MerorPanicGoal;
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
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
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

public class MerorMerchantEntity extends MerorEntity implements Merchant {
	public MerorMerchantEntity(EntityType type, Level world) {
		super(type, world);
		this.xpReward = 20;
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
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.37);
		builder = builder.add(Attributes.MAX_HEALTH, 30);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
		this.goalSelector.addGoal(0, new MerorPanicGoal(this, 1.6));
		this.goalSelector.addGoal(1, new JerotesTradeWithPlayerGoal(this));
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

	private int addEffect = 1;
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("AddEffect", this.addEffect);
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
		this.addEffect = compoundTag.getInt("AddEffect");
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
		if (this.getTarget() == null && this.isAlive() && !this.isTrading() && !player.hasEffect(JerotesVillageMobEffects.MEROR_WANTED_ORDER.get()) && !player.isShiftKeyDown()) {
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
		if (this.getTarget() != null && addEffect == 1){
			addEffect = 0;
			if (!this.level().isClientSide) {
				this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 12000, 2));
				this.addEffect(new MobEffectInstance(JerotesVillageMobEffects.MEROR_STIMULIN.get(), 12000, 0));
			}
		}
		if (this.getTarget() == null && addEffect == 0){
			addEffect = 1;
		}
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if ((this.getTarget() == null || !this.getTarget().isAlive()) && !this.level().isClientSide) {
			if (!this.hasEffect(JerotesVillageMobEffects.MEROR_STIMULIN.get())) {
				this.addEffect(new MobEffectInstance(JerotesVillageMobEffects.MEROR_STIMULIN.get(), 12000, 0));
			}
			if (!this.hasEffect(MobEffects.DAMAGE_RESISTANCE)) {
				this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 12000, 2));
			}
		}
		if (AttackFind.isWaterDamage(damageSource) && !this.hasEffect(MobEffects.WATER_BREATHING)) {
			if (!this.level().isClientSide) {
				this.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 12000, 0));
			}
		}
		if (AttackFind.isFireDamage(damageSource) && !this.hasEffect(MobEffects.FIRE_RESISTANCE)) {
			if (!this.level().isClientSide) {
				this.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 12000, 0));
			}
		}
		boolean bl = super.hurt(damageSource, amount);
		if (bl) {
			this.stopTrading();
		}
		return bl;
	}

	@Override
	public ItemStack createSpawnWeapon(float weaponRandom) {
		return new ItemStack(JerotesVillageItems.MEROR_METAL_SWORD.get());
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
		VillagerTrades.ItemListing[] arritemListing3 = MEROR_MERCHANT.get(3);
		if (arritemListing == null || arritemListing2 == null || arritemListing3 == null) {
			return;
		}
		MerchantOffers merchantOffers = this.getOffers();
		this.addOffersFromItemListings(merchantOffers, arritemListing, 8);
		this.addOffersFromItemListings(merchantOffers, arritemListing2, 8);
		this.addOffersFromItemListings(merchantOffers, arritemListing3, 4);
	}

	public static final Int2ObjectMap<VillagerTrades.ItemListing[]> MEROR_MERCHANT =
			new Int2ObjectOpenHashMap(ImmutableMap.of(
					//卖出
					1, new VillagerTrades.ItemListing[]{
							//绿宝石
							new BasicItemListing(new ItemStack(Items.EMERALD, 1),
									new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 3), 30, 5, 0.05f),
							//滑行者黏液
							new BasicItemListing(new ItemStack(JerotesVillageItems.SLIDER_MUCUS.get(), 5),
									new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 1), 30, 5, 0.05f),
							//恶怨魂
							new BasicItemListing(new ItemStack(JerotesVillageItems.BLAMER_SOUL.get(), 5),
									new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 1), 30, 5, 0.05f),
							//虚洞水晶
							new BasicItemListing(new ItemStack(JerotesVillageItems.VIRTUAL_CAVE_EVIL_CRYSTAL.get(), 3),
									new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 1), 30, 5, 0.05f),
							//巨怪角
							new BasicItemListing(new ItemStack(JerotesVillageItems.GIANT_MONSTER_HORN.get(), 3),
									new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 1), 30, 5, 0.05f),
							//寒霜雪怪毛发
							new BasicItemListing(new ItemStack(JerotesVillageItems.FROST_YETI_HAIR.get(), 1),
									new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 3), 30, 5, 0.05f),
							//闪电蠕虫甲壳
							new BasicItemListing(new ItemStack(JerotesVillageItems.LIGHTNING_WORM_CHITIN.get(), 1),
									new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 3), 30, 5, 0.05f),
							//泌火恶怪粘胶
							new BasicItemListing(new ItemStack(JerotesVillageItems.FIRE_SECRETOR_ADHESIVE.get(), 1),
									new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 3), 30, 5, 0.05f),
							//海洋宝石
							new BasicItemListing(new ItemStack(JerotesVillageItems.OCEAN_GEMSTONE.get(),  1),
									new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 1), 30, 5, 0.05f),
							//鳞熊毛
							new BasicItemListing(new ItemStack(JerotesVillageItems.SCALY_BEAR_HAIR.get(), 3),
									new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 1), 30, 5, 0.05f),
							//渊鱼
							new BasicItemListing(new ItemStack(JerotesVillageItems.ABYSSALER.get(), 6),
									new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 1), 30, 5, 0.05f),
							//日光蟹
							new BasicItemListing(new ItemStack(JerotesVillageItems.SUN_CRAB.get(), 10),
									new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 1), 30, 5, 0.05f),
							//宝石恶鳞牙齿
							new BasicItemListing(new ItemStack(JerotesVillageItems.MALIGNASAUR_TEETH.get(), 1),
									new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 1), 30, 5, 0.05f),
							//鳞熊鳞片
							new BasicItemListing(new ItemStack(JerotesVillageItems.SCALY_BEAR_SCALE.get(), 3),
									new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 1), 30, 5, 0.05f),
							//漫兽皮革
							new BasicItemListing(new ItemStack(JerotesVillageItems.RAMBLER_LEATHER.get(), 2),
									new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 1), 30, 5, 0.05f),
							//迷林香料
							new BasicItemListing(new ItemStack(JerotesVillageItems.MYSTEREST_PERFUME.get(), 6),
									new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 8), 30, 5, 0.05f),
							//遗迹石砖
							new BasicItemListing(new ItemStack(JerotesVillageItems.RUIN_STONE_BRICKS.get(), 32),
									new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 1), 30, 5, 0.05f),
							//褐毒蛞蝓
							new BasicItemListing(new ItemStack(JerotesVillageItems.BROWN_VENOMOUS_SLUG.get(), 5),
									new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 1), 30, 5, 0.05f),
							//探险者记录
							new BasicItemListing(new ItemStack(JerotesVillageItems.explorer_tracking_record.get(), 3),
									new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 1), 30, 5, 0.05f),
							//附魔金苹果
							new BasicItemListing(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 1),
									new ItemStack(JerotesVillageItems.MEROR_TOKEN_E.get(), 2), 30, 5, 0.05f),
							//金苹果
							new BasicItemListing(new ItemStack(Items.GOLDEN_APPLE, 1),
									new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 4), 30, 5, 0.05f),
							//下界合金锭
							new BasicItemListing(new ItemStack(Items.NETHERITE_INGOT, 1),
									new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 12), 30, 5, 0.05f),
							//高强度丝线
							new BasicItemListing(new ItemStack(JerotesItems.HIGH_STRENGTH_STRING.get(), 3),
									new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 1), 30, 5, 0.05f)},
					//买入
					2, new VillagerTrades.ItemListing[]{
							//钻石
							new BasicItemListing(new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 6),
									new ItemStack(Items.DIAMOND, 1), 30, 5, 0.05f),
							//二轮地图
							new BasicItemListing(new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 1),
									new ItemStack(JerotesVillageItems.SECOND_ROUNDER_MAP.get(), 1), 30, 5, 0.05f),
							//莫厄金属锭
							new BasicItemListing(new ItemStack(JerotesVillageItems.MEROR_TOKEN_E.get(), 3),
									new ItemStack(JerotesVillageItems.MEROR_METAL_INGOT.get(), 1), 30, 5, 0.05f),
							//莫厄金属标枪
							new BasicItemListing(new ItemStack(JerotesVillageItems.MEROR_TOKEN_E.get(), 5),
									new ItemStack(JerotesVillageItems.MEROR_METAL_JAVELIN.get(), 1), 30, 5, 0.05f),
							//莫厄金属剑
							new BasicItemListing(new ItemStack(JerotesVillageItems.MEROR_TOKEN_E.get(), 8),
									new ItemStack(JerotesVillageItems.MEROR_METAL_SWORD.get(), 1), 30, 5, 0.05f),
							//莫厄金属箭
							new BasicItemListing(new ItemStack(JerotesVillageItems.MEROR_TOKEN_E.get(), 5),
									new ItemStack(JerotesVillageItems.MEROR_METAL_ARROW.get(), 16), 30, 5, 0.05f),
							//莫厄金属弓
							new BasicItemListing(new ItemStack(JerotesVillageItems.MEROR_TOKEN_E.get(), 5),
									new ItemStack(JerotesVillageItems.MEROR_METAL_BOW.get(), 1), 30, 5, 0.05f),
							//莫厄金属矛
							new BasicItemListing(new ItemStack(JerotesVillageItems.MEROR_TOKEN_E.get(), 5),
									new ItemStack(JerotesVillageItems.MEROR_METAL_SPEAR.get(), 1), 30, 5, 0.05f),
							//末影珍珠
							new BasicItemListing(new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 1),
									new ItemStack(Items.ENDER_PEARL, 1), 30, 5, 0.05f),
							//莫厄投影台
							new BasicItemListing(new ItemStack(JerotesVillageItems.MEROR_TOKEN_E.get(), 4),
									new ItemStack(JerotesVillageItems.MEROR_PROJECTION_TABLE.get(), 1), 30, 5, 0.05f),
							//金苹果
							new BasicItemListing(new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 6),
									new ItemStack(Items.GOLDEN_APPLE, 1), 30, 5, 0.05f),
							//拴绳
							new BasicItemListing(new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 1),
									new ItemStack(Items.LEAD, 5), 30, 5, 0.05f),
							//附魔金苹果
							new BasicItemListing(new ItemStack(JerotesVillageItems.MEROR_TOKEN_E.get(), 3),
									new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 1), 30, 5, 0.05f),
							//圣水
							new BasicItemListing(new ItemStack(JerotesVillageItems.MEROR_TOKEN_E.get(), 2),
									new ItemStack(JerotesVillageItems.MEROR_STIMULIN.get(), 1), 30, 5, 0.05f),
							//清洁机器
							new BasicItemListing(new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 4),
									new ItemStack(JerotesVillageItems.CLEANING_ROBOT_ITEM.get(), 1), 30, 5, 0.05f),
							//下界合金锭
							new BasicItemListing(new ItemStack(JerotesVillageItems.MEROR_TOKEN_E.get(), 1),
									new ItemStack(Items.NETHERITE_INGOT, 1), 30, 5, 0.05f),
							//绿宝石
							new BasicItemListing(new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 4),
									new ItemStack(Items.EMERALD, 1), 30, 5, 0.05f),
							//莫厄能源体
							new BasicItemListing(new ItemStack(JerotesVillageItems.MEROR_TOKEN_R.get(), 2),
									new ItemStack(JerotesVillageItems.MEROR_ENERGY_REGIME.get(), 1), 30, 5, 0.05f),
							//莫厄金属箭
							new BasicItemListing(new ItemStack(JerotesVillageItems.MEROR_TOKEN_E.get(), 8),
									new ItemStack(JerotesVillageItems.MEROR_BULLET.get(), 16), 30, 5, 0.05f),
							//精炼莫厄金属锭
							new BasicItemListing(new ItemStack(JerotesVillageItems.MEROR_TOKEN_R.get(), 1),
									new ItemStack(JerotesVillageItems.REFINE_MEROR_METAL_INGOT.get(), 1), 30, 5, 0.05f)},
					//货币转换
					3, new VillagerTrades.ItemListing[]{
							new BasicItemListing(new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 16),
									new ItemStack(JerotesVillageItems.MEROR_TOKEN_E.get(), 1), 320, 5, 0.05f),

							new BasicItemListing(new ItemStack(JerotesVillageItems.MEROR_TOKEN_E.get(), 16),
									new ItemStack(JerotesVillageItems.MEROR_TOKEN_R.get(), 1), 320, 5, 0.05f),

							new BasicItemListing(new ItemStack(JerotesVillageItems.MEROR_TOKEN_E.get(), 1),
									new ItemStack(JerotesVillageItems.MEROR_TOKEN_M.get(), 16), 320, 5, 0.05f),

							new BasicItemListing(new ItemStack(JerotesVillageItems.MEROR_TOKEN_R.get(), 1),
									new ItemStack(JerotesVillageItems.MEROR_TOKEN_E.get(), 16), 320, 5, 0.05f)}
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
