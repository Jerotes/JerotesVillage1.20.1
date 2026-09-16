package com.jerotes.jerotesvillage.block.Other;

import com.jerotes.jerotesvillage.init.JerotesVillageBlockEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageRecipeType;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.recipes.TabletPressingRecipe;
import com.jerotes.jerotesvillage.inventory.TabletPressMachineMenu;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
//1.20.4↑
//import net.minecraft.world.item.crafting.RecipeHolder;
//import net.minecraft.world.inventory.RecipeCraftingHolder;
//1.20.1//
import net.minecraft.world.item.crafting.Recipe;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Iterator;

//1.20.4↑
//implements RecipeCraftingHolder
public class TabletPressMachineEntity extends BaseContainerBlockEntity implements WorldlyContainer, StackedContentsCompatible {
    private static final int INGREDIENT_SLOT = 3;
    private static final int[] SLOTS_FOR_UP = new int[]{3};
    private static final int[] SLOTS_FOR_DOWN = new int[]{0, 1, 2};
    private static final int[] SLOTS_FOR_SIDES = new int[]{0, 1, 2};
    private NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed;
    private final RecipeManager.CachedCheck<Container, ? extends TabletPressingRecipe> quickCheck;
    int makeTime;
    private boolean[] lastPotionCount;
    private Item ingredient;
    protected final ContainerData dataAccess = new ContainerData() {
        public int get(int n) {
            if (n == 0) {
                return TabletPressMachineEntity.this.makeTime;
            }
            return 0;
        }

        public void set(int n, int n2) {
            if (n == 0) {
                TabletPressMachineEntity.this.makeTime = n2;
            }
        }

        public int getCount() {
            return 2;
        }
    };

    public TabletPressMachineEntity(BlockPos blockPos, BlockState blockState) {
        super(JerotesVillageBlockEntityType.TABLET_PRESS_MACHINE.get(), blockPos, blockState);
        this.recipesUsed = new Object2IntOpenHashMap();
        this.quickCheck = RecipeManager.createCheck(JerotesVillageRecipeType.TABLET_PRESSING.get());
    }

    protected Component getDefaultName() {
        return Component.translatable("block.jerotesvillage.villager_block_physician");
    }

    public int getContainerSize() {
        return this.items.size();
    }

    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, TabletPressMachineEntity tabletPressMachine) {

        boolean flag = isMakeable(level, tabletPressMachine.items);
        boolean flag1 = tabletPressMachine.makeTime > 0;
        ItemStack itemstack1 = tabletPressMachine.items.get(3);
        if (flag1) {
            --tabletPressMachine.makeTime;
            boolean flag2 = tabletPressMachine.makeTime == 0;
            //1.20.4↑//
            //RecipeHolder recipeholder;
            //1.20.1//
            Recipe recipeholder;
            if (flag2) {
                recipeholder = tabletPressMachine.quickCheck.getRecipeFor(tabletPressMachine, level).orElse(null);
            } else {
                recipeholder = null;
            }
            if (flag2 && flag) {
                tabletPressMachine.setRecipeUsed(recipeholder);
                doMake(level, blockPos, tabletPressMachine.items);
                setChanged(level, blockPos, blockState);
            } else if (!flag || !itemstack1.is(tabletPressMachine.ingredient)) {
                tabletPressMachine.makeTime = 0;
                setChanged(level, blockPos, blockState);
            }
        }
        else if (flag) {
            tabletPressMachine.makeTime = 600;
            tabletPressMachine.ingredient = itemstack1.getItem();
            setChanged(level, blockPos, blockState);
        }

        boolean[] aboolean = tabletPressMachine.getPotionBits();
        if (!Arrays.equals(aboolean, tabletPressMachine.lastPotionCount)) {
            tabletPressMachine.lastPotionCount = aboolean;
            BlockState blockstate = blockState;
            if (!(blockState.getBlock() instanceof TabletPressMachine)) {
                return;
            }
        }
    }

    private boolean[] getPotionBits() {
        boolean[] aboolean = new boolean[3];

        for(int i = 0; i < 3; ++i) {
            if (!this.items.get(i).isEmpty()) {
                aboolean[i] = true;
            }
        }

        return aboolean;
    }

    @Nullable
    private static TabletPressingRecipe findMatchingRecipe(Level level, ItemStack base, ItemStack addition) {
        return level.getRecipeManager()
                .getAllRecipesFor(JerotesVillageRecipeType.TABLET_PRESSING.get())
                .stream()
                //1.20.4↑//
                //.map(RecipeHolder::value)
                .filter(recipe -> matchesIngredients(recipe.getBase(), base) && matchesIngredients(recipe.getAddition(), addition))
                .findFirst()
                .orElse(null);
    }
    private static boolean matchesIngredients(NonNullList<Ingredient> ingredients, ItemStack itemStack) {
        return ingredients.stream().anyMatch(ingredient -> ingredient.test(itemStack));
    }

    private static boolean isMakeable(Level level, NonNullList<ItemStack> items) {
        ItemStack ingredientStack = items.get(INGREDIENT_SLOT);
        if (ingredientStack.isEmpty()) return false;

        for (int i = 0; i < 3; ++i) {
            ItemStack baseStack = items.get(i);
            if (!baseStack.isEmpty()) {
                TabletPressingRecipe recipe = findMatchingRecipe(level, baseStack, ingredientStack);
                if (recipe != null) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void doMake(Level level, BlockPos blockPos, NonNullList<ItemStack> items) {
        ItemStack ingredientStack = items.get(INGREDIENT_SLOT);

        for (int i = 0; i < 3; ++i) {
            ItemStack baseStack = items.get(i);
            if (!baseStack.isEmpty()) {
                TabletPressingRecipe recipe = findMatchingRecipe(level, baseStack, ingredientStack);
                if (recipe != null) {
                    ItemStack result = recipe.getResult().copy();

                    baseStack.shrink(1);
                    if (baseStack.isEmpty()) {
                        items.set(i, result);
                    } else {
                        ItemEntity drop;
                        if (level.getBlockState(blockPos).getValue(TabletPressMachine.FACING) == Direction.NORTH) {
                            drop = new ItemEntity(level, blockPos.getX()+0.5, blockPos.getY()+0.5, blockPos.getZ()-0.5, result);
                        }
                        else if (level.getBlockState(blockPos).getValue(TabletPressMachine.FACING) == Direction.SOUTH) {
                            drop = new ItemEntity(level, blockPos.getX()+0.5, blockPos.getY()+0.5, blockPos.getZ()+1.5, result);
                        }
                        else if (level.getBlockState(blockPos).getValue(TabletPressMachine.FACING) == Direction.WEST) {
                            drop = new ItemEntity(level, blockPos.getX()-0.5, blockPos.getY()+0.5, blockPos.getZ()+0.5, result);
                        }
                        else {
                            drop = new ItemEntity(level, blockPos.getX()+1.5, blockPos.getY()+0.5, blockPos.getZ()+0.5, result);
                        }
                        if (level.getBlockState(blockPos).getValue(TabletPressMachine.FACING) == Direction.NORTH) {
                            drop.setPosRaw(blockPos.getX()+0.5, blockPos.getY()+0.5, blockPos.getZ()-0.5);
                        }
                        else if (level.getBlockState(blockPos).getValue(TabletPressMachine.FACING) == Direction.SOUTH) {
                            drop.setPosRaw(blockPos.getX()+0.5, blockPos.getY()+0.5, blockPos.getZ()+1.5);
                        }
                        else if (level.getBlockState(blockPos).getValue(TabletPressMachine.FACING) == Direction.WEST) {
                            drop.setPosRaw(blockPos.getX()-0.5, blockPos.getY()+0.5, blockPos.getZ()+0.5);
                        }
                        else {
                            drop.setPosRaw(blockPos.getX()+1.5, blockPos.getY()+0.5, blockPos.getZ()+0.5);
                        }
                        drop.setDeltaMovement(0,0,0);
                        level.addFreshEntity(drop);
                    }

                    ingredientStack.shrink(1);
                    items.set(INGREDIENT_SLOT, ingredientStack.isEmpty() ? ItemStack.EMPTY : ingredientStack);
                }

                level.playSound(null, blockPos, JerotesVillageSoundEvents.TABLET_PRESS_MACHINE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }
    }


    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compoundTag, this.items);
        this.makeTime = compoundTag.getShort("MakeTime");

        CompoundTag compoundtag = compoundTag.getCompound("RecipesUsed");
        Iterator var3 = compoundtag.getAllKeys().iterator();
        while(var3.hasNext()) {
            String s = (String)var3.next();
            this.recipesUsed.put(new ResourceLocation(s), compoundtag.getInt(s));
        }
    }

    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        compoundTag.putShort("MakeTime", (short)this.makeTime);
        ContainerHelper.saveAllItems(compoundTag, this.items);
        CompoundTag compoundtag = new CompoundTag();
        this.recipesUsed.forEach((p_187449_, p_187450_) -> {
            compoundtag.putInt(p_187449_.toString(), p_187450_);
        });
        compoundTag.put("RecipesUsed", compoundtag);
    }

    public ItemStack getItem(int n) {
        return n >= 0 && n < this.items.size() ? this.items.get(n) : ItemStack.EMPTY;
    }

    public ItemStack removeItem(int n, int n2) {
        return ContainerHelper.removeItem(this.items, n, n2);
    }

    public ItemStack removeItemNoUpdate(int n) {
        return ContainerHelper.takeItem(this.items, n);
    }

    public void setItem(int n, ItemStack itemStack) {
        if (n >= 0 && n < this.items.size()) {
            this.items.set(n, itemStack);
        }
    }

    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    public boolean canPlaceItem(int n, ItemStack itemStack) {
        return true;
    }

    public int[] getSlotsForFace(Direction direction) {
        if (direction == Direction.UP) {
            return SLOTS_FOR_UP;
        } else {
            return direction == Direction.DOWN ? SLOTS_FOR_DOWN : SLOTS_FOR_SIDES;
        }
    }

    public boolean canPlaceItemThroughFace(int n, ItemStack itemStack, @Nullable Direction direction) {
        return this.canPlaceItem(n, itemStack);
    }

    public boolean canTakeItemThroughFace(int n, ItemStack itemStack, Direction direction) {
        return n < 3;
    }

    public void clearContent() {
        this.items.clear();
    }

    protected AbstractContainerMenu createMenu(int n, Inventory inventory) {
        return new TabletPressMachineMenu(n, inventory, this, this.dataAccess);
    }

    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
        if (capability == net.minecraftforge.common.capabilities.ForgeCapabilities.ITEM_HANDLER && facing != null && !this.remove) {
            return switch (facing) {
                case UP -> handlers[0].cast();
                case DOWN -> handlers[1].cast();
                default -> handlers[2].cast();
            };
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        for (int x = 0; x < handlers.length; x++)
            handlers[x].invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.handlers = net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
    }

    //1.20.4↑//
    //@Override
    //public void setRecipeUsed(RecipeHolder<?> recipeHolder) {
    //1.20.1//
    public void setRecipeUsed(Recipe<?> recipeHolder) {
        if (recipeHolder != null) {
            //1.20.4↑//
            //ResourceLocation resourcelocation = recipeHolder.id();
            //1.20.1//
            ResourceLocation resourcelocation = recipeHolder.getId();
            this.recipesUsed.addTo(resourcelocation, 1);
        }
    }

    @Nullable
    //1.20.4↑//
    //public RecipeHolder<?> getRecipeUsed() {
    //1.20.1//
    public Recipe<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void fillStackedContents(StackedContents stackedContents) {
        Iterator var2 = this.items.iterator();
        while(var2.hasNext()) {
            ItemStack itemstack = (ItemStack)var2.next();
            stackedContents.accountStack(itemstack);
        }
    }
}
