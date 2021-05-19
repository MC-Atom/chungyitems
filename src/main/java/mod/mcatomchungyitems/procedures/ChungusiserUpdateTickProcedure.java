package mod.mcatomchungyitems.procedures;

import mod.mcatomchungyitems.ChungyitemsMod;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import mod.mcatomchungyitems.ChungyitemsModElements;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@ChungyitemsModElements.ModElement.Tag
public class ChungusiserUpdateTickProcedure extends ChungyitemsModElements.ModElement {
	public ChungusiserUpdateTickProcedure(ChungyitemsModElements instance) {
		super(instance, 3);
	}
	public boolean isValidRecipe = false;

	public static void addItem (ItemStack item, HashMap<Item,Integer> Items) {
		int itemCount = item.getCount();
		Item itemType = item.getItem();

		if (itemType != Blocks.AIR.asItem()) {
			Items.merge(itemType, itemCount, Integer::sum);
		}
	}

	public static ItemStack getItemStack(World world, BlockPos pos, int sltid) {
		TileEntity _ent = world.getTileEntity(pos);
		AtomicReference<ItemStack> _output = new AtomicReference<>(ItemStack.EMPTY);

		if (_ent != null) {
			_ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
				_output.set(capability.getStackInSlot(sltid));
			});
		}
		return _output.get();
	}

	public static void executeProcedure(double x, double y, double z, World world){

		ItemStack itemInMeter = ItemStack.EMPTY;

		Collection<IRecipe<?>> recipes = ChungyitemsMod.getRecipes(ChungyitemsModElements.CHUNGISER_BLOCK_RECIPE, world.getRecipeManager()).values();
		System.out.println(recipes);

		//double itemsNeededToCraft = 160;

		HashMap<Item, Integer> items = new HashMap<>();
		BlockPos blockPosition = new BlockPos((int) x, (int) y, (int) z);

		ItemStack guiSlot1 = getItemStack(world, blockPosition, 1);
		ItemStack guiSlot2 = getItemStack(world, blockPosition, 2);
		ItemStack guiSlot3 = getItemStack(world, blockPosition, 3);
		ItemStack guiSlot4 = getItemStack(world, blockPosition, 4);
		ItemStack guiSlot5 = getItemStack(world, blockPosition, 5);

		addItem(guiSlot1,items);
		addItem(guiSlot2,items);
		addItem(guiSlot3,items);
		addItem(guiSlot4,items);
		addItem(guiSlot5,items);

		Set<Item> itemsInChungusiser = items.keySet();


		System.out.println("recipes = " + recipes);
		System.out.println("itemsInChungusiser = " + itemsInChungusiser);

		for (IRecipe<?> recipe : recipes) {
			Boolean validRecipe = true;

			if (recipe.getIngredients().contains(itemsInChungusiser.toArray()[0])) {
				for thing in getIngreence {
					if (recipe.contains(item)) {
						if (recipe.count <= items.get(item)){
							then check the opposite direction
						}
					}
				}
				for (Item item : itemsInChungusiser) {

				}
			} else {
				validRecipe = false;
			}
		}

	}


}