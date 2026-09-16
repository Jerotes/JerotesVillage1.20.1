package com.jerotes.jerotesvillage.entity.Monster.Piglin;

import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.init.JerotesItems;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.item.Tool.ItemToolBaseCrossbow;
import com.jerotes.jerotes.spell.MagicType;
import com.jerotes.jerotes.spell.SpellTypeInterface;
import com.jerotes.jerotes.util.Main;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.EntityArmorInvWrapper;
import net.minecraftforge.items.wrapper.EntityHandsInvWrapper;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class PiglinResidentEntity extends Piglin implements SpellUseEntity, UseDaggerEntity, ShiftKeyDownEntity, NeutralMob, WizardEntity, JerotesPiglinEntity, UseThrowEntity, UseThrownJavelinEntity, InventoryCarrier, InventoryEntity, UseBowEntity, UseCrossbowEntity, UseShieldEntity, JerotesEntity, FactionEntity {
    private static final String TAG_PIGMAN = "Pigman";
    boolean isPigman;
    private static final EntityDataAccessor<Integer> COMBAT_STYLE = SynchedEntityData.defineId(PiglinResidentEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> USE_SELF_NOT_SPELL_LIST = SynchedEntityData.defineId(PiglinResidentEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SPELL_TICK = SynchedEntityData.defineId(PiglinResidentEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BOW_LEVEL = SynchedEntityData.defineId(PiglinResidentEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SHIELD_LEVEL = SynchedEntityData.defineId(PiglinResidentEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> CAN_CHANGE_INVENTORY = SynchedEntityData.defineId(PiglinResidentEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> CAN_CHANGE_MELEE_OR_RANGE = SynchedEntityData.defineId(PiglinResidentEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> NO_COMBAT_EMPTY_WEAPON = SynchedEntityData.defineId(PiglinResidentEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> NO_COMBAT_EMPTY_SHIELD = SynchedEntityData.defineId(PiglinResidentEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> CHANGE_INVENTORY_COOLDOWN_TICK = SynchedEntityData.defineId(PiglinResidentEntity.class, EntityDataSerializers.INT);
    public final SimpleContainer inventory = new SimpleContainer(inventoryCount());
    private LazyOptional<?> itemHandler = null;

    public PiglinResidentEntity(EntityType<? extends PiglinResidentEntity> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 10;
        setMaxUpStep(0.6f);
        this.setCanPickUpLoot(true);
        this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, 0.0f);
        this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
        this.itemHandler = LazyOptional.of(this::createCombinedHandler);
    }
    private IItemHandler createCombinedHandler() {
        IItemHandlerModifiable handsHandler = new EntityHandsInvWrapper(this);
        IItemHandlerModifiable armorHandler = new EntityArmorInvWrapper(this);
        IItemHandlerModifiable customHandler = new InvWrapper(this.inventory);
        return new CombinedInvWrapper(
                armorHandler,
                handsHandler,
                customHandler
        );
    }

    @Override
    public String getFirstFactionTypeName() {
        return "piglin_resident_detachment";
    }
    @Override
    public List<String> getFactionTypeUntilTame() {
        List<String> list = new ArrayList<>();
        list.add(getFirstFactionTypeName());
        list.add("piglin");
        return list;
    }

    @VisibleForDebug
    @Override
    public SimpleContainer getInventory() {
        return this.inventory;
    }

    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        return capability == ForgeCapabilities.ITEM_HANDLER && itemHandler != null &&
                this.isAlive() ? itemHandler.cast() : super.getCapability(capability, facing);
    }
    public void invalidateCaps() {
        super.invalidateCaps();
        if (this.itemHandler != null) {
            LazyOptional<?> oldHandler = this.itemHandler;
            this.itemHandler = null;
            oldHandler.invalidate();
        }
    }

    protected ItemStack addToInventory(ItemStack itemStack) {
        return this.inventory.addItem(itemStack);
    }

    protected boolean canAddToInventory(ItemStack itemStack) {
        return this.inventory.canAddItem(itemStack);
    }

    @Override
    public ItemStack equipItemIfPossible(ItemStack itemStack) {
        EquipmentSlot equipmentSlot = Mob.getEquipmentSlotForItem(itemStack);
        ItemStack itemStack2 = this.getItemBySlot(equipmentSlot);
        boolean bl = this.canReplaceCurrentItem(itemStack, itemStack2);
        if (equipmentSlot.isArmor() && !bl) {
            equipmentSlot = EquipmentSlot.MAINHAND;
            itemStack2 = this.getItemBySlot(equipmentSlot);
            bl = itemStack2.isEmpty();
        }
        if (bl && this.canHoldItem(itemStack)) {
            double d = this.getEquipmentDropChance(equipmentSlot);
            if (this.canAddToInventory(itemStack2)) {
                this.addToInventory(itemStack2);
            }
            else {
                this.spawnAtLocation(itemStack2);
            }

            if (equipmentSlot.isArmor() && itemStack.getCount() > 1) {
                ItemStack itemStack3 = itemStack.copyWithCount(1);
                this.setItemSlotAndDropWhenKilled(equipmentSlot, itemStack3);
                return itemStack3;
            }
            this.setItemSlotAndDropWhenKilled(equipmentSlot, itemStack);
            return itemStack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    protected boolean canReplaceCurrentItem(ItemStack newItem, ItemStack oldItem) {
        return InventoryEntity.canReplaceCurrentItem(this, newItem, oldItem);
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource damageSource, int n, boolean bl) {
        super.dropCustomDeathLoot(damageSource, n, bl);
        this.inventory.removeAllItems().forEach(this::spawnAtLocation);
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
        this.goalSelector.addGoal(1, new JerotesRangedBowAttackGoal<PiglinResidentEntity>(this, 0.5, 20, 15.0f));
        this.goalSelector.addGoal(1, new JerotesRangedThrowAttackGoal<>(this, 0.4, 40, 12.0F));
        this.goalSelector.addGoal(1, new JerotesRangedJavelinAttackGoal<>(this, 1.0, 60, 12.0F, this.meleeOrRangeDistance()));
        this.goalSelector.addGoal(1, new JerotesSpearUseGoal<>(this,  1.0, 1.0, 10.0f, 2.0f));
        this.goalSelector.addGoal(1, new JerotesPikeUseGoal(this,  1.2, true));
        this.goalSelector.addGoal(1, new JerotesRangedCrossbowAttackGoal<>(this, 1.0, 15.0f));
        this.goalSelector.addGoal(2, new JerotesMeleeAttackGoal(this, 1.1, true));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, HumanEntity.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, AbstractPiglin.class, 3.0f, 1.0f));
        this.targetSelector.addGoal(1, new JerotesHelpSameFactionGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
        this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<PiglinResidentEntity>(this, true));
    }
    @Override
    public void performRangedAttack(LivingEntity livingEntity, float f) {
        ItemStack handItem = this.getMainHandItem();
        if ((this.getMainHandItem().isEmpty() || InventoryEntity.isMeleeWeapon(this.getMainHandItem())) && !this.getOffhandItem().isEmpty()) {
            handItem = this.getOffhandItem();
        }
        if (InventoryEntity.isRangeJavelin(handItem)) {
            useTridentShoot(this, livingEntity);
        }
        if (InventoryEntity.isBow(handItem)) {
            useBowShoot(this, livingEntity, f, getBowLevel(), 20);
        }
        if (InventoryEntity.isThrow(handItem)) {
            useThrowShoot(this, livingEntity);
            if (!this.isSilent()) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesSoundEvents.ITEM_THROW, this.getSoundSource(), 0.5f, 0.4f / (this.level().getRandom().nextFloat() * 0.4f + 0.8f));
            }
        }
        if (InventoryEntity.isCrossbow(handItem)) {
            useCrossbowShoot(this, 3.15F);
        }
        if (JerotesGameRules.JEROTES_RANGE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_RANGE_CAN_BREAK)) {
            if (handItem.getItem() instanceof BowItem || handItem.getItem() instanceof TridentItem) {
                handItem.hurtAndBreak(1, this, player -> player.broadcastBreakEvent((this.getMainHandItem().isEmpty() || InventoryEntity.isMeleeWeapon(this.getMainHandItem())) && !this.getOffhandItem().isEmpty() ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND));
            }
        }
    }
    @Override
    public ItemStack getProjectile(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ProjectileWeaponItem projectileWeaponItem) {
            Predicate<ItemStack> predicate = projectileWeaponItem.getSupportedHeldProjectiles();
            ItemStack itemStack2 = ProjectileWeaponItem.getHeldProjectile(this, predicate);
            if (predicate.test(this.getMainHandItem())) {
                itemStack2 = this.getMainHandItem();
            }
            else if (predicate.test(this.getOffhandItem())) {
                itemStack2 = this.getOffhandItem();
            }
            else {
                for (int n = 0; n < this.inventoryCount(); ++n) {
                    ItemStack finds = this.mobInventory().getItem(n);
                    if (predicate.test(finds)) {
                        itemStack2 = finds;
                        break;
                    }
                }
            }
            return itemStack2.isEmpty() ? new ItemStack(Items.ARROW) : itemStack2;
        }
        return ItemStack.EMPTY;
    }
    @Override
    public void shootCrossbowProjectile(LivingEntity livingEntity, ItemStack itemStack, Projectile projectile, float f) {
        this.shootCrossbowProjectile(this, livingEntity, projectile, f, itemStack.getItem() instanceof ItemToolBaseCrossbow itemToolBaseCrossbow ? itemToolBaseCrossbow.getShootingPower(itemStack) : 3.15f);
    }
    @Override
    public void shootCrossbowProjectile(LivingEntity livingEntity, LivingEntity livingEntity2, Projectile projectile, float f, float f2) {
        double d0 = livingEntity2.getX() - livingEntity.getX();
        double d1 = livingEntity2.getZ() - livingEntity.getZ();
        double d2 = Math.sqrt(d0 * d0 + d1 * d1);
        double d3 = livingEntity2.getY(0.3333333333333333) - projectile.getY() + d2 * 0.1;
        Vector3f vector3f = this.getProjectileShotVector(livingEntity, new Vec3(d0, d3, d1), f);
        projectile.shoot((double)vector3f.x(), (double)vector3f.y(), (double)vector3f.z(), f2, Math.max(0, 20 - getBowLevel()) / 5f);
        livingEntity.playSound(SoundEvents.CROSSBOW_SHOOT, 1.0F, 1.0F / (livingEntity.getRandom().nextFloat() * 0.4F + 0.8F));
    }

    @Override
    public void onCrossbowAttackPerformed() {
        this.noActionTime = 0;
    }
    @Override
    public AbstractArrow getCustomArrow(ItemStack itemStack, float f) {
        return ProjectileUtil.getMobArrow(this, itemStack, f);
    }

    @Override
    public boolean canFireProjectileWeapon(ProjectileWeaponItem projectileWeaponItem) {
        return projectileWeaponItem instanceof BowItem || projectileWeaponItem instanceof CrossbowItem || super.canFireProjectileWeapon(projectileWeaponItem);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.34);
        builder = builder.add(Attributes.MAX_HEALTH, 24);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 6);
        builder = builder.add(Attributes.FOLLOW_RANGE, 24);
        return builder;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (this.isPigman) {
            return this.isAggressive() ? SoundEvents.ZOMBIFIED_PIGLIN_ANGRY : SoundEvents.ZOMBIFIED_PIGLIN_AMBIENT;
        }
        if (this.level().isClientSide) {
            return null;
        }
        return PiglinAi.getSoundForCurrentActivity(this).orElse(null);
    }
    @Override
    protected SoundEvent getDeathSound() {
        if (this.isPigman) {
            return SoundEvents.ZOMBIFIED_PIGLIN_DEATH;
        }
        return SoundEvents.PIGLIN_DEATH;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        if (this.isDamageSourceBlocked(damageSource)) {
            return SoundEvents.SHIELD_BLOCK;
        }
        if (damageSource.is(DamageTypeTags.BYPASSES_SHIELD) && this.isBlocking()) {
            return SoundEvents.SHIELD_BLOCK;
        }
        if (this.isPigman) {
            return SoundEvents.ZOMBIFIED_PIGLIN_HURT;
        }
        return SoundEvents.PIGLIN_HURT;
    }
    @Override
    protected boolean isImmuneToZombification() {
        if (this.isPigman) {
            return true;
        }
        return super.isImmuneToZombification();
    }
    @Override
    public boolean removeWhenFarAway(double d) {
        return false;
    }
    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }
    @Override
    protected void finishConversion(ServerLevel serverLevel) {
        SimpleContainer simpleContainer = this.mobInventory();
        for (int i = 0; i < simpleContainer.getMaxStackSize(); ++i) {
            this.spawnAtLocation(simpleContainer.getItem(i));
        }
       super.finishConversion(serverLevel);
    }
    public AABB getAttackBoundingBox() {
        Entity entity = this.getVehicle();
        AABB aabb;
        if (entity != null) {
            AABB aabb1 = entity.getBoundingBox();
            AABB aabb2 = this.getBoundingBox();
            aabb = new AABB(Math.min(aabb2.minX, aabb1.minX), aabb2.minY, Math.min(aabb2.minZ, aabb1.minZ), Math.max(aabb2.maxX, aabb1.maxX), aabb2.maxY, Math.max(aabb2.maxZ, aabb1.maxZ));
        } else {
            aabb = this.getBoundingBox();
        }
        AABB aabb1 = aabb.inflate(Math.sqrt((double)2.04F) - (double)0.6F, 0.0D, Math.sqrt((double)2.04F) - (double)0.6F);
        return aabb1.inflate(0.5d, 0.5d, 0.5d);
    }
    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
        return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
    }
    @Override
    public boolean canUseRangeJavelin() {
        return true;
    }
    @Override
    public MobType getMobType() {
        if (this.isPigman) {
            return MobType.UNDEAD;
        }
        return super.getMobType();
    }

    public int shieldCoolDown;
    public int shieldCanUse = 1;
//    @Override
//    public boolean isAdult() {
//        return true;
//    }
//    @Override
//    public boolean isBaby() {
//        return false;
//    }
    @Override
    public boolean shieldCanUse() {
        return this.shieldCanUse == 1 && this.shieldCoolDown <= 0;
    }
    @Override
    public void setCustomName(@Nullable Component component) {
        super.setCustomName(component);
        if (!this.isPigman && component != null && component.getString().equals(TAG_PIGMAN)) {
            this.isPigman = true;
        }
    }
    @Override
    public SimpleContainer mobInventory() {
        return inventory;
    }
    @Override
    public int inventoryCount() {
        return 8;
    }
    @Override
    public boolean isCanChangeInventory() {
        return this.getEntityData().get(CAN_CHANGE_INVENTORY);
    }
    @Override
    public void setCanChangeInventory(boolean bl) {
        this.getEntityData().set(CAN_CHANGE_INVENTORY, bl);
    }
    @Override
    public boolean isCanChangeMeleeOrRange() {
        return this.getEntityData().get(CAN_CHANGE_MELEE_OR_RANGE);
    }
    @Override
    public void setCanChangeMeleeOrRange(boolean bl) {
        this.getEntityData().set(CAN_CHANGE_MELEE_OR_RANGE, bl);
    }
    @Override
    public boolean NonCombatEmptyWeapon() {
        return this.isNoCombatEmptyWeapon();
    }
    @Override
    public boolean NonCombatEmptyShield() {
        return this.isNoCombatEmptyShield();
    }
    @Override
    public int changeInventoryCooldownTick() {
        return this.getChangeInventoryCooldownTick();
    }
    public boolean isNoCombatEmptyWeapon() {
        return this.getEntityData().get(NO_COMBAT_EMPTY_WEAPON);
    }
    public void setNoCombatEmptyWeapon(boolean bl) {
        this.getEntityData().set(NO_COMBAT_EMPTY_WEAPON, bl);
    }
    public boolean isNoCombatEmptyShield() {
        return this.getEntityData().get(NO_COMBAT_EMPTY_SHIELD);
    }
    public void setNoCombatEmptyShield(boolean bl) {
        this.getEntityData().set(NO_COMBAT_EMPTY_SHIELD, bl);
    }
    public int getChangeInventoryCooldownTick() {
        return this.getEntityData().get(CHANGE_INVENTORY_COOLDOWN_TICK);
    }
    public void setChangeInventoryCooldownTick(int n) {
        this.getEntityData().set(CHANGE_INVENTORY_COOLDOWN_TICK, n);
    }
    @Override
    public void SpellUseAfterAttack(String string, MagicType magicType, MagicType magicType2) {
        if (!this.level().isClientSide()) {
            this.setSpellTick(10);
        }
    }
    @Override
    public List<SpellTypeInterface> SelfMainSpellList() {
        return new ArrayList<>();
    }
    @Override
    public List<SpellTypeInterface> SelfAddSpellList() {
        return new ArrayList<>();
    }
    public int spellLevel = 1;
    @Override
    public int getSpellLevel() {
        return this.spellLevel;
    }
    public void setSpellTick(int n){
        this.getEntityData().set(SPELL_TICK, n);
    }
    public int getSpellTick(){
        return this.getEntityData().get(SPELL_TICK);
    }
    public void setBowLevel(int n){
        this.getEntityData().set(BOW_LEVEL, n);
    }
    public int getBowLevel(){
        return this.getEntityData().get(BOW_LEVEL);
    }
    public void setShieldLevel(int n){
        this.getEntityData().set(SHIELD_LEVEL, n);
    }
    public int getShieldLevel(){
        return this.getEntityData().get(SHIELD_LEVEL);
    }
    public int getCombatStyle() {
        return this.getEntityData().get(COMBAT_STYLE);
    }
    public void setCombatStyle(int n) {
        this.getEntityData().set(COMBAT_STYLE, n);
    }
    public boolean isUseSelfNotStringSpellList() {
        return this.getEntityData().get(USE_SELF_NOT_SPELL_LIST);
    }
    public void setUseSelfNotStringSpellList(boolean bl) {
        this.getEntityData().set(USE_SELF_NOT_SPELL_LIST, bl);
    }
    public boolean isMagicUseStyle() {
        return this.getSpellTick() > 0;
    }
    @Override
    public boolean isSpellHumanoid() {
        return true;
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        if (this.isPigman) {
            compoundTag.putBoolean(TAG_PIGMAN, true);
        }
        compoundTag.putInt("SpellLevel", this.spellLevel);
        compoundTag.putInt("SpellTick", this.getSpellTick());
        compoundTag.putInt("BowLevel", this.getBowLevel());
        compoundTag.putInt("ShieldLevel", this.getShieldLevel());
        compoundTag.putInt("CombatStyle", this.getCombatStyle());
        compoundTag.putBoolean("UseSelfNotStringSpellList", this.isUseSelfNotStringSpellList());
        compoundTag.putBoolean("IsNoCombatEmptyWeapon", this.isNoCombatEmptyWeapon());
        compoundTag.putBoolean("IsNoCombatEmptyShield", this.isNoCombatEmptyShield());
        compoundTag.putInt("ChangeInventoryCooldownTick", this.getChangeInventoryCooldownTick());
        compoundTag.putBoolean("IsCanChangeInventory", this.isCanChangeInventory());
        compoundTag.putBoolean("IsCanChangeMeleeOrRange", this.isCanChangeMeleeOrRange());
        compoundTag.putInt("ShieldCoolDown", this.shieldCoolDown);
        compoundTag.putInt("ShieldCanUse", this.shieldCanUse);
        this.addPersistentAngerSaveData(compoundTag);
        this.writeInventoryToTag(compoundTag);
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.contains(TAG_PIGMAN, 99)) {
            this.isPigman = compoundTag.getBoolean(TAG_PIGMAN);
        }
        this.spellLevel = compoundTag.getInt("SpellLevel");
        this.setSpellTick(compoundTag.getInt("SpellTick"));
        this.setBowLevel(compoundTag.getInt("BowLevel"));
        this.setShieldLevel(compoundTag.getInt("ShieldLevel"));
        this.setCombatStyle(compoundTag.getInt("CombatStyle"));
        this.setUseSelfNotStringSpellList(compoundTag.getBoolean("UseSelfNotStringSpellList"));
        this.setNoCombatEmptyWeapon(compoundTag.getBoolean("IsNoCombatEmptyWeapon"));
        this.setNoCombatEmptyShield(compoundTag.getBoolean("IsNoCombatEmptyShield"));
        this.setChangeInventoryCooldownTick(compoundTag.getInt("ChangeInventoryCooldownTick"));
        this.setCanChangeInventory(compoundTag.getBoolean("IsCanChangeInventory"));
        this.setCanChangeMeleeOrRange(compoundTag.getBoolean("IsCanChangeMeleeOrRange"));
        this.shieldCoolDown = compoundTag.getInt("ShieldCoolDown");
        this.shieldCanUse = compoundTag.getInt("ShieldCanUse");
        this.readPersistentAngerSaveData(this.level(), compoundTag);
        this.readInventoryFromTag(compoundTag);
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(SPELL_TICK, 0);
        this.getEntityData().define(BOW_LEVEL, 15);
        this.getEntityData().define(SHIELD_LEVEL, 1);
        this.getEntityData().define(USE_SELF_NOT_SPELL_LIST, true);
        this.getEntityData().define(COMBAT_STYLE, 1);
        this.getEntityData().define(CAN_CHANGE_INVENTORY, false);
        this.getEntityData().define(CAN_CHANGE_MELEE_OR_RANGE, false);
        this.getEntityData().define(NO_COMBAT_EMPTY_WEAPON, false);
        this.getEntityData().define(NO_COMBAT_EMPTY_SHIELD, false);
        this.getEntityData().define(CHANGE_INVENTORY_COOLDOWN_TICK, 50);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.random.nextInt	(900) == 1 && this.deathTime == 0) {
            this.heal(3.0f);
        }
        this.updateSwingTime();
        this.updateNoActionTime();
        if (!this.level().isClientSide) {
            this.updatePersistentAnger((ServerLevel)this.level(), true);
        }
        //停止战斗
        if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
            this.setTarget(null);
        }
        //
        //法术
        if (!this.level().isClientSide()) {
            this.setSpellTick(Math.max(0, this.getSpellTick() - 1));
        }
        //
        if (this.shieldCoolDown > 0){
            this.shieldCoolDown -= 1;
            this.shieldCanUse = 0;
        }
        if (this.shieldCoolDown <= 0){
            this.shieldCanUse = 1;
        }
        //摧毁骑乘物
        Main.destroyRides(this);
        //使用盾牌和双手武器
        useBlockingItem(this);
        //替换自己的物品
        changeInventory(this);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean bl = super.doHurtTarget(entity);
        if (bl) {
            if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
                ItemStack hand = this.getMainHandItem();
                hand.hurtAndBreak(1, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }
        return bl;
    }

    @Override
    protected void blockUsingShield(LivingEntity entityIn) {
        super.blockUsingShield(entityIn);
        if (entityIn instanceof BreakShieldEntity breakShield) this.disableShieldBreak(breakShield.getShieldBreakStrength());
        if (entityIn.getMainHandItem().canDisableShield(this.useItem, this, entityIn) || entityIn.canDisableShield()) this.disableShield();
        this.disableShieldTry();
    }

    @Override
    public boolean isDamageSourceBlocks(DamageSource damageSource) {
        Vec3 object;
        Entity entity = damageSource.getDirectEntity();
        boolean bl = entity instanceof AbstractArrow && ((AbstractArrow) (AbstractArrow) entity).getPierceLevel() > 0;
        if (!damageSource.is(DamageTypeTags.BYPASSES_SHIELD) && !bl && (object = damageSource.getSourcePosition()) != null) {
            Vec3 vec3 = this.calculateViewVector(0.0f, this.getYHeadRot());
            Vec3 vec32 = object.vectorTo(this.position());
            vec32 = new Vec3(vec32.x, 0.0, vec32.z).normalize();
            return vec32.dot(vec3) < 0.0;
        }
        return false;
    }

    @Override
    protected void hurtCurrentlyUsedShield(float f) {
        if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_SHIELD_CAN_BREAK)) {
            if (this.useItem.canPerformAction(ToolActions.SHIELD_BLOCK)) {
                if (f >= 3.0f) {
                    int n = 1 + Mth.floor(f);
                    InteractionHand interactionHand = this.getUsedItemHand();
                    this.useItem.hurtAndBreak(n, this, player -> player.broadcastBreakEvent(interactionHand));
                    if (this.useItem.isEmpty()) {
                        if (interactionHand == InteractionHand.MAIN_HAND) {
                            this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                        } else {
                            this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                        }
                        this.useItem = ItemStack.EMPTY;
                        this.playSound(SoundEvents.SHIELD_BREAK, 0.8f, 0.8f + this.level().random.nextFloat() * 0.4f);
                    }
                }
            }
        }
    }

    @Override
    public void disableShield() {
        if (this.getRandom().nextFloat() <= (1 - (this.getShieldLevel() - 1) * 0.2f)) {
            if (this.shieldCoolDown < 100) {
                this.shieldCoolDown = 100;
            }
            this.shieldCanUse = 0;
            if (this.getUseItem().getItem() instanceof ShieldItem) {
                this.stopUsingItem();
            }
        }
    }
    @Override
    public void disableShieldTry() {
        if (this.getRandom().nextFloat() <= (0.15 - (this.getShieldLevel() - 1) * 0.05f)) {
            if (this.shieldCoolDown < 100) {
                this.shieldCoolDown = 100;
            }
            this.shieldCanUse = 0;
            if (this.getUseItem().getItem() instanceof ShieldItem) {
                this.stopUsingItem();
            }
        }
    }
    @Override
    public void disableShieldBreak(int tick) {
        if (tick == 0) {
            return;
        }
        if (this.shieldCoolDown < tick) {
            this.shieldCoolDown = tick;
        }
        this.shieldCanUse = 0;
        if (this.getUseItem().getItem() instanceof ShieldItem) {
            this.stopUsingItem();
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        SpawnGroupData spawnGroupData2 = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
        RandomSource randomSource = serverLevelAccessor.getRandom();
        if (mobSpawnType != MobSpawnType.CONVERSION) {
            this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
            this.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
            float weaponRandom = this.random.nextFloat();
            this.setItemSlot(EquipmentSlot.MAINHAND, this.createSpawnWeapon(weaponRandom));
            this.populateDefaultEquipmentEnchantments(randomSource, difficultyInstance);
        }
        return spawnGroupData2;
    }

    private ItemStack createSpawnWeapon(float weaponRandom) {
        return new ItemStack(JerotesItems.GOLDEN_JAVELIN.get());
    }


    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private int remainingPersistentAngerTime;
    private UUID persistentAngerTarget;

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Override
    public void setRemainingPersistentAngerTime(int n) {
        this.remainingPersistentAngerTime = n;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    @Override
    public void setPersistentAngerTarget(UUID uUID) {
        this.persistentAngerTarget = uUID;
    }

    @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

}

