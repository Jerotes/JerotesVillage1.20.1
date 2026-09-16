package com.jerotes.jerotesvillage.block.PurpleDesert;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Animal.RottenDogEntity;
import com.jerotes.jerotesvillage.entity.Animal.WildernessWolfEntity;
import com.jerotes.jerotesvillage.entity.Monster.Hag.CohortHagEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageRecipeType;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.recipes.HagReplacementRecipe;
import com.jerotes.jerotesvillage.util.IngredientGet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Objects;

public class NewHagsCauldron extends Block implements WorldlyContainerHolder {
	public NewHagsCauldron() {
		super(Properties.of().sound(SoundType.METAL).strength(50f, 10000f).pushReaction(PushReaction.BLOCK).noOcclusion());
	}

	private static final VoxelShape INSIDE = AbstractCauldronBlock.box(2.0, 4.0, 2.0, 14.0, 16.0, 14.0);
	protected static final VoxelShape SHAPE = Shapes.join(Shapes.block(), Shapes.or(AbstractCauldronBlock.box(0.0, 0.0, 4.0, 16.0, 3.0, 12.0), AbstractCauldronBlock.box(4.0, 0.0, 0.0, 12.0, 3.0, 16.0), AbstractCauldronBlock.box(2.0, 0.0, 2.0, 14.0, 3.0, 14.0), INSIDE), BooleanOp.ONLY_FIRST);

	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return SHAPE;
	}

	@Override
	public VoxelShape getInteractionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return INSIDE;
	}
	@Override
	public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
		super.onPlace(blockstate, world, pos, oldState, moving);
		world.scheduleTick(pos, this, 1);
	}

	public boolean isPotion(ItemStack stack) {
		return Ingredient.of(new ItemStack(JerotesVillageItems.EVIL_POTION.get())).test(stack);
	}

	@Override
	public void randomTick(BlockState blockState, ServerLevel level, BlockPos pos, RandomSource randomSource) {
		super.randomTick(blockState, level, pos, randomSource);
		level.scheduleTick(pos, this, 1);
	}

	@Override
	public InteractionResult use(BlockState blockstate, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		super.use(blockstate, world, pos, player, hand, hit);
		if (world.isClientSide) {
			return InteractionResult.SUCCESS;
		}
        if (this.isPotion(player.getItemInHand(hand))) {
			return InteractionResult.PASS;
		}
		ItemStack hands;
		ItemStack handWant;
		ItemStack give;
		player.getItemInHand(hand);
        if (!this.isPotion(player.getItemInHand(hand))) {
			hands = player.getItemInHand(hand);
			if (!hands.is(Items.AIR)) {
				if (world instanceof ServerLevel serverLevel) {
					change(hands, serverLevel, pos);
					if (!player.getAbilities().instabuild) {
						hands.shrink(1);
					}
				}
				return InteractionResult.SUCCESS;
			}
		}

		return InteractionResult.CONSUME;
	}

	static void change(ItemStack itemStacks, ServerLevel serverLevel, BlockPos blockPos) {
		//爆炸
		serverLevel.explode(null,blockPos.getX()+0.5, blockPos.getY()+0.5, blockPos.getZ()+0.5, 4, Level.ExplosionInteraction.NONE);

		String id = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(itemStacks.getItem())).getNamespace();
		String name = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(itemStacks.getItem())).getPath();
		boolean bl = false;

		HagReplacementRecipe recipe = findMatchingRecipe(serverLevel, itemStacks);
		//优先战利品表寻找
		for (ItemStack itemStack : Objects.requireNonNull(serverLevel.getServer()).getLootData().getLootTable(
				new ResourceLocation(JerotesVillage.MODID, "gameplay/jerotesvillage_hag_replacement/"+ id + "_" + name)
		).getRandomItems(new LootParams.Builder(serverLevel).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockPos)).withParameter(LootContextParams.BLOCK_STATE, serverLevel.getBlockState(blockPos)).withOptionalParameter(LootContextParams.BLOCK_ENTITY, serverLevel.getBlockEntity(blockPos)).create(LootContextParamSets.EMPTY))) {
			if (!itemStack.isEmpty()) bl = true;
			if (itemStack.getItem() instanceof SpawnEggItem) {
				spawnEntityFromSpawnEgg(itemStack, serverLevel, blockPos);
			}
			//最后原物
			else {
				summonItem(itemStack, serverLevel, blockPos);
			}
		}

		if (!bl) {
			//其次合成表
			if (isChangeable(serverLevel, itemStacks) && recipe != null) {
				NonNullList<Ingredient> result = recipe.getResult();
				ItemStack itemStack = IngredientGet.getRandomItemFromIngredients(result);
				if (itemStack.getItem() instanceof SpawnEggItem) {
					spawnEntityFromSpawnEgg(itemStack, serverLevel, blockPos);
				}
				//最后原物
				else {
					summonItem(itemStack, serverLevel, blockPos);
				}
			}
			//最后原物
			else {
				summonItem(itemStacks.copyWithCount(1), serverLevel, blockPos);
			}
		}
	}

	private static void spawnEntityFromSpawnEgg(ItemStack itemStack, ServerLevel serverLevel, BlockPos blockPos) {
		//by Heoupo修改
		if (itemStack.getItem() instanceof SpawnEggItem spawnegg) {
			EntityType<?> entityType = spawnegg.getType(itemStack.getTag());
			if (entityType == JerotesVillageEntityType.COHORT_HAG.get()) {
				//嗯凑鬼婆集会
				for (int i = 0; i < 3; ++i) {
					CohortHagEntity witch = JerotesVillageEntityType.COHORT_HAG.get().spawn(serverLevel, blockPos, MobSpawnType.EVENT);
					if (witch != null) {
						witch.setDeltaMovement(0, 0, 0);
						serverLevel.addFreshEntity(witch);
					}
				}
			}
			else if (entityType == JerotesVillageEntityType.WILDERNESS_WOLF.get()) {
				//年糕汪汪队
				for (int i = 0; i < 3; ++i) {
					WildernessWolfEntity witch = JerotesVillageEntityType.WILDERNESS_WOLF.get().spawn(serverLevel, blockPos, MobSpawnType.EVENT);
					if (witch != null) {
						witch.setDeltaMovement(0, 0, 0);
						serverLevel.addFreshEntity(witch);
					}
				}
			}
			else if (entityType == JerotesVillageEntityType.ROTTEN_DOG.get()) {
				//黑皮汪汪队
				for (int i = 0; i < 3; ++i) {
					RottenDogEntity witch = JerotesVillageEntityType.ROTTEN_DOG.get().spawn(serverLevel, blockPos, MobSpawnType.EVENT);
					if (witch != null) {
						witch.setDeltaMovement(0, 0, 0);
						serverLevel.addFreshEntity(witch);
					}
				}
			}
			else {
				//生成刷怪蛋对应实体
				Entity entity = entityType.spawn(serverLevel, blockPos, MobSpawnType.EVENT);
				if (entity != null) {
					entity.setDeltaMovement(0, 0, 0);
					serverLevel.addFreshEntity(entity);
				}
			}
		}
	}


	private static boolean isChangeable(Level level, ItemStack items) {
		HagReplacementRecipe recipe = findMatchingRecipe(level, items);
		if (recipe != null) {
			return true;
		}
		return false;
	}
	@Nullable
	private static HagReplacementRecipe findMatchingRecipe(Level level, ItemStack base) {
		return level.getRecipeManager()
				.getAllRecipesFor(JerotesVillageRecipeType.HAG_REPLACEMENT.get())
				.stream()
				//1.20.4↑//
				//.map(RecipeHolder::value)
				.filter(recipe -> matchesIngredients(recipe.getBase(), base))
				.findFirst()
				.orElse(null);
	}
	private static boolean matchesIngredients(NonNullList<Ingredient> ingredients, ItemStack itemStack) {
		return ingredients.stream().anyMatch(ingredient -> ingredient.test(itemStack));
	}

	static void summonItem(ItemStack itemStack, ServerLevel serverLevel, BlockPos blockPos) {
		ItemEntity entityToSpawn0 = new ItemEntity(serverLevel, blockPos.getX()+0.5, blockPos.getY()+1.25, blockPos.getZ()+0.5, itemStack);
		entityToSpawn0.setPickUpDelay(0);
		//生命值
		CompoundTag compoundTag0 = new CompoundTag();
		entityToSpawn0.addAdditionalSaveData(compoundTag0);
		compoundTag0.putShort("Health", (short) 10000);
		entityToSpawn0.readAdditionalSaveData(compoundTag0);

		entityToSpawn0.setUnlimitedLifetime();
		serverLevel.addFreshEntity(entityToSpawn0);
		if (serverLevel.getRandom().nextInt(1, 10) == 10) {
			serverLevel.playSound(null, BlockPos.containing(blockPos.getX(), blockPos.getY(), blockPos.getZ()), JerotesVillageSoundEvents.PURPLE_SAND_HAG_LAUGH_1, SoundSource.NEUTRAL, 5, 1);
		}
	}

	@Override
	public WorldlyContainer getContainer(BlockState blockState, LevelAccessor levelAccessor, BlockPos blockPos) {
		return new InputContainer(blockState, levelAccessor, blockPos);
	}

	static class InputContainer
			extends SimpleContainer
			implements WorldlyContainer {
		private final BlockState state;
		private final LevelAccessor level;
		private final BlockPos pos;
		private boolean changed;

		public InputContainer(BlockState blockState, LevelAccessor levelAccessor, BlockPos blockPos) {
			super(1);
			this.state = blockState;
			this.level = levelAccessor;
			this.pos = blockPos;
		}

		@Override
		public int getMaxStackSize() {
			return 1;
		}

		@Override
		public int[] getSlotsForFace(Direction direction) {
			int[] arrn;
			//本来想上面才行
//			if (direction == Direction.UP) {
				int[] arrn2 = new int[1];
				arrn = arrn2;
				arrn2[0] = 0;
//			}
//			else {
//				arrn = new int[]{};
//			}
			return arrn;
		}

		@Override
		public boolean canPlaceItemThroughFace(int n, ItemStack itemStack, @Nullable Direction direction) {
			return
					!this.changed &&
							//direction == Direction.UP &&
							!itemStack.isEmpty() && !Ingredient.of(new ItemStack(JerotesVillageItems.EVIL_POTION.get())).test(itemStack);
		}

		@Override
		public boolean canTakeItemThroughFace(int n, ItemStack itemStack, Direction direction) {
			return false;
		}

		@Override
		public void setChanged() {
			ItemStack itemStack = this.getItem(0);
			if (!itemStack.isEmpty()) {
				this.changed = true;
				if (this.level instanceof ServerLevel serverLevel) {
					NewHagsCauldron.change(itemStack, serverLevel, this.pos);
				}
				this.removeItemNoUpdate(0);
			}
		}
	}
}
