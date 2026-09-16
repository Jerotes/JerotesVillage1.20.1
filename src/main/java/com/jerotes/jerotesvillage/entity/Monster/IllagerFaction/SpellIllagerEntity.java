package com.jerotes.jerotesvillage.entity.Monster.IllagerFaction;

import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.goal.JerotesHelpAlliesGoal;
import com.jerotes.jerotes.goal.JerotesHelpSameFactionGoal;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.goal.SerponOpenDoorGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.EntityArmorInvWrapper;
import net.minecraftforge.items.wrapper.EntityHandsInvWrapper;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SpellIllagerEntity extends SpellcasterIllager implements OminouseBannerRaidForceEntity, WizardEntity, InventoryCarrier, InventoryEntity, JerotesEntity, FactionEntity, SpellUseEntity {
    private static final EntityDataAccessor<Integer> COMBAT_STYLE = SynchedEntityData.defineId(SpellIllagerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> USE_SELF_NOT_SPELL_LIST = SynchedEntityData.defineId(SpellIllagerEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(SpellIllagerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(SpellIllagerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SPELL_TICK = SynchedEntityData.defineId(SpellIllagerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SPELL_ONE_TICK = SynchedEntityData.defineId(SpellIllagerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SPELL_TWO_TICK = SynchedEntityData.defineId(SpellIllagerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SPELL_THREE_TICK = SynchedEntityData.defineId(SpellIllagerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> CAN_CHANGE_INVENTORY = SynchedEntityData.defineId(SpellIllagerEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> CAN_CHANGE_MELEE_OR_RANGE = SynchedEntityData.defineId(SpellIllagerEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> NO_COMBAT_EMPTY_WEAPON = SynchedEntityData.defineId(SpellIllagerEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> NO_COMBAT_EMPTY_SHIELD = SynchedEntityData.defineId(SpellIllagerEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> CHANGE_INVENTORY_COOLDOWN_TICK = SynchedEntityData.defineId(SpellIllagerEntity.class, EntityDataSerializers.INT);
    public final SimpleContainer inventory = new SimpleContainer(inventoryCount());
    private LazyOptional<?> itemHandler = null;

    public SpellIllagerEntity(EntityType<? extends SpellIllagerEntity> entityType, Level level) {
        super(entityType, level);
        xpReward = 10;
        setMaxUpStep(0.6f);
        this.applyOpenDoorsAbility();
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
        return "ominous_banner_raid_force";
    }
    @Override
    public List<String> getFactionTypeUntilTame() {
        List<String> list = new ArrayList<>();
        list.add(getFirstFactionTypeName());
        list.add("raider");
        list.add("illager");
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

    private void applyOpenDoorsAbility() {
        if (GoalUtils.hasGroundPathNavigation(this)) {
            ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
        if (!(this instanceof GavilerEntity)) {
            this.goalSelector.addGoal(0, new OpenDoorGoal(this, true));
        }
        else {
            this.goalSelector.addGoal(0, new SerponOpenDoorGoal(this, true));
        }
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, AbstractIllager.class, 3.0f, 1.0f));
        this.targetSelector.addGoal(1, new JerotesHelpSameFactionGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
        this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return null;
    }
    @Override
    protected SoundEvent getCastingSoundEvent() {
        return null;
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
    public void applyRaidBuffs(int n, boolean bl) {
    }
    public boolean isCastingSpell() {
        return super.isCastingSpell() || this.getSpellTick() > 0;
    }
    @Override
    public IllagerArmPose getArmPose() {
        if (this.getSpellTick() > 0 || this.isCastingSpell()) {
            return IllagerArmPose.SPELLCASTING;
        }
        if (this.isCelebrating()) {
            return IllagerArmPose.CELEBRATING;
        }
        return IllagerArmPose.CROSSED;
    }
    public IllagerSpell customSpell() {
        return IllagerSpell.NONE;
    }

    public void setSpellTick(int n){
        this.getEntityData().set(SPELL_TICK, n);
    }
    public int getSpellTick(){
        return this.getEntityData().get(SPELL_TICK);
    }
    public void setSpellOneTick(int n){
        this.getEntityData().set(SPELL_ONE_TICK, n);
    }
    public int getSpellOneTick(){
        return this.getEntityData().get(SPELL_ONE_TICK);
    }
    public void setSpellTwoTick(int n){
        this.getEntityData().set(SPELL_TWO_TICK, n);
    }
    public int getSpellTwoTick(){
        return this.getEntityData().get(SPELL_TWO_TICK);
    }
    public void setSpellThreeTick(int n){
        this.getEntityData().set(SPELL_THREE_TICK, n);
    }
    public int getSpellThreeTick(){
        return this.getEntityData().get(SPELL_THREE_TICK);
    }
    //动画
    public void setAnimTick(int n){
        this.getEntityData().set(ANIM_TICK, n);
    }
    public int getAnimTick(){
        return this.getEntityData().get(ANIM_TICK);
    }
    public void setAnimationState(String input) {
        this.setAnimationState(this.getAnimationState(input));
    }
    public void setAnimationState(int id) {
        this.entityData.set(ANIM_STATE, id);
    }
    public int getAnimationState(String animation) {
        return 0;
    }
    public List<AnimationState> getAllAnimations(){
        List<AnimationState> list = new ArrayList<>();
        return list;
    }
    public void stopMostAnimation(AnimationState exception){
        for (AnimationState state : this.getAllAnimations()){
            if (state != exception) {
                state.stop();
            }
        }
    }
    public void stopAllAnimation(){
        for (AnimationState state : this.getAllAnimations()){
            state.stop();
        }
    }
    //
    public int spellLevel = 1;
    public int teleportCooldown;
    @Override
    public int getSpellLevel() {
        return this.spellLevel;
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
    public boolean canUseMeleeWeapon() {
        return false;
    }
    @Override
    public boolean canUseShield() {
        return false;
    }
    @Override
    public boolean canUseCrossbow() {
        return false;
    }
    @Override
    public boolean canUseThrow() {
        return false;
    }
    @Override
    public boolean canUseRangeJavelin() {
        return false;
    }
    @Override
    public boolean canUseBow() {
        return false;
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
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("IsNoCombatEmptyWeapon", this.isNoCombatEmptyWeapon());
        compoundTag.putBoolean("IsNoCombatEmptyShield", this.isNoCombatEmptyShield());
        compoundTag.putInt("ChangeInventoryCooldownTick", this.getChangeInventoryCooldownTick());
        compoundTag.putBoolean("IsCanChangeInventory", this.isCanChangeInventory());
        compoundTag.putBoolean("IsCanChangeMeleeOrRange", this.isCanChangeMeleeOrRange());
        compoundTag.putInt("CombatStyle", this.getCombatStyle());
        compoundTag.putBoolean("UseSelfNotStringSpellList", this.isUseSelfNotStringSpellList());
        compoundTag.putInt("SpellLevel", this.spellLevel);
        compoundTag.putInt("AnimTick", this.getAnimTick());
        compoundTag.putInt("SpellTick", this.getSpellTick());
        compoundTag.putInt("SpellOneTick", this.getSpellOneTick());
        compoundTag.putInt("SpellTwoTick", this.getSpellTwoTick());
        compoundTag.putInt("SpellThreeTick", this.getSpellThreeTick());
        this.writeInventoryToTag(compoundTag);
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setNoCombatEmptyWeapon(compoundTag.getBoolean("IsNoCombatEmptyWeapon"));
        this.setNoCombatEmptyShield(compoundTag.getBoolean("IsNoCombatEmptyShield"));
        this.setChangeInventoryCooldownTick(compoundTag.getInt("ChangeInventoryCooldownTick"));
        this.setCanChangeInventory(compoundTag.getBoolean("IsCanChangeInventory"));
        this.setCanChangeMeleeOrRange(compoundTag.getBoolean("IsCanChangeMeleeOrRange"));
        this.setCombatStyle(compoundTag.getInt("CombatStyle"));
        this.setUseSelfNotStringSpellList(compoundTag.getBoolean("UseSelfNotStringSpellList"));
        this.spellLevel = compoundTag.getInt("SpellLevel");
        this.setAnimTick(compoundTag.getInt("AnimTick"));
        this.setSpellTick(compoundTag.getInt("SpellTick"));
        this.setSpellOneTick(compoundTag.getInt("SpellOneTick"));
        this.setSpellTwoTick(compoundTag.getInt("SpellTwoTick"));
        this.setSpellThreeTick(compoundTag.getInt("SpellThreeTick"));
        this.readInventoryFromTag(compoundTag);
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(USE_SELF_NOT_SPELL_LIST, true);
        this.getEntityData().define(COMBAT_STYLE, 1);
        this.getEntityData().define(ANIM_STATE, 0);
        this.getEntityData().define(ANIM_TICK, 0);
        this.getEntityData().define(SPELL_TICK, 0);
        this.getEntityData().define(SPELL_ONE_TICK, 0);
        this.getEntityData().define(SPELL_TWO_TICK, 0);
        this.getEntityData().define(SPELL_THREE_TICK, 0);
        this.getEntityData().define(CAN_CHANGE_INVENTORY, false);
        this.getEntityData().define(CAN_CHANGE_MELEE_OR_RANGE, false);
        this.getEntityData().define(NO_COMBAT_EMPTY_WEAPON, false);
        this.getEntityData().define(NO_COMBAT_EMPTY_SHIELD, false);
        this.getEntityData().define(CHANGE_INVENTORY_COOLDOWN_TICK, 50);
    }
    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (ANIM_STATE.equals(entityDataAccessor)) {
            if (this.level().isClientSide()) {
                switch (this.entityData.get(ANIM_STATE)){
                    case 0:
                        this.stopAllAnimation();
                        break;
                }
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.updateSwingTime();
        this.updateNoActionTime();
        if (this.random.nextInt	(900) == 1 && this.deathTime == 0) {
            this.heal(3.0f);
        }
        if (teleportCooldown > 0) {
            teleportCooldown--;
        }
        //停止战斗
        if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))){
            this.setTarget(null);
        }
        //
        if (this.getSpellTick() > 0){
            if (!this.level().isClientSide()) {
                this.setSpellTick(this.getSpellTick() - 1);
            }
            this.setIsCastingSpell(customSpell());
        }
        else {
            this.setIsCastingSpell(IllagerSpell.NONE);
        }
        if (!this.level().isClientSide()) {
            this.setSpellOneTick(Math.max(0, this.getSpellOneTick() - 1));
            this.setSpellTwoTick(Math.max(0, this.getSpellTwoTick() - 1));
            this.setSpellThreeTick(Math.max(0, this.getSpellThreeTick() - 1));
        }
        //清除动画
        if (!this.level().isClientSide()) {
            this.setAnimTick(Math.max(-1, this.getAnimTick() - 1));
        }
        if (this.getAnimTick() == 0) {
            if (!this.level().isClientSide()) {
                this.setAnimationState(0);
            }
        }
        //摧毁骑乘物
        Main.destroyRides(this);
        //替换自己的物品
        changeInventory(this);
    }

    @Override
    protected void hurtArmor(DamageSource damageSource, float damage) {
        if (JerotesGameRules.JEROTES_ARMOR_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_ARMOR_CAN_BREAK)) {
            if (damage >= 0.0F) {
                damage = damage / 4.0F;
                if (damage < 1.0F) {
                    damage = 1.0F;
                }
                ItemStack head = this.getItemBySlot(EquipmentSlot.HEAD);
                ItemStack chest = this.getItemBySlot(EquipmentSlot.CHEST);
                ItemStack legs = this.getItemBySlot(EquipmentSlot.LEGS);
                ItemStack feet = this.getItemBySlot(EquipmentSlot.FEET);
                if ((!damageSource.is(DamageTypes.ON_FIRE) || !head.getItem().isFireResistant() && !damageSource.is(DamageTypeTags.BYPASSES_ARMOR))) {
                    if (head.getItem() instanceof ArmorItem) {
                        head.hurtAndBreak((int) damage, this, player -> player.broadcastBreakEvent(EquipmentSlot.HEAD));
                    }
                    if (chest.getItem() instanceof ArmorItem) {
                        chest.hurtAndBreak((int) damage, this, player -> player.broadcastBreakEvent(EquipmentSlot.CHEST));
                    }
                    if (legs.getItem() instanceof ArmorItem) {
                        legs.hurtAndBreak((int) damage, this, player -> player.broadcastBreakEvent(EquipmentSlot.LEGS));
                    }
                    if (feet.getItem() instanceof ArmorItem) {
                        feet.hurtAndBreak((int) damage, this, player -> player.broadcastBreakEvent(EquipmentSlot.FEET));
                    }
                }
            }
        }
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        if (entity == null) {
            return false;
        }
        if (entity == this) {
            return true;
        }
        if (super.isAlliedTo(entity)) {
            return true;
        }
        if (entity instanceof LivingEntity livingEntity && EntityFactionFind.isFaction(this, livingEntity)) {
            return this.getTeam() == null && entity.getTeam() == null;
        }
        return false;
    }

    @Override
    public boolean canAttack(LivingEntity livingEntity) {
        if (teleportCooldown > 400 && livingEntity.getMainHandItem().is(JerotesVillageItems.OMINOUS_PROBE.get()))
            return false;
        if (((this.getTeam() == null && livingEntity.getTeam() == null) || this.getTeam() == livingEntity.getTeam()) &&
                EntityFactionFind.isFaction(this, livingEntity)
        ) {
            return false;
        }
        return super.canAttack(livingEntity);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        SpawnGroupData spawnGroupData2 = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
        RandomSource randomSource = serverLevelAccessor.getRandom();
        if (mobSpawnType != MobSpawnType.CONVERSION) {
            this.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
            float weaponRandom = this.random.nextFloat();
            float offhandRandom = this.random.nextFloat();
            this.setItemSlot(EquipmentSlot.MAINHAND, this.createSpawnWeapon(weaponRandom));
            this.setItemSlot(EquipmentSlot.OFFHAND, this.createSpawnOffhand(offhandRandom));
            this.populateDefaultEquipmentEnchantments(randomSource, difficultyInstance);
        }
        return spawnGroupData2;
    }

    public ItemStack createSpawnWeapon(float weaponRandom) {
        return new ItemStack(Items.AIR);
    }

    public ItemStack createSpawnOffhand(float offhandRandom) {
        return new ItemStack(Items.AIR);
    }
}

