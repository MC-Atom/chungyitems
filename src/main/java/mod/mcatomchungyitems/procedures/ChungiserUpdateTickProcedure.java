package mod.mcatomchungyitems.procedures;

import mod.mcatomchungyitems.ChungyitemsMod;
import net.minecraft.item.ItemStack;

import mod.mcatomchungyitems.ChungyitemsModElements;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

@ChungyitemsModElements.ModElement.Tag
public class ChungiserUpdateTickProcedure extends ChungyitemsModElements.ModElement {
	public ChungiserUpdateTickProcedure(ChungyitemsModElements instance) {
		super(instance, 3);
	}
	public boolean isValidRecipe = false;

	public static void executeProcedure(World world){

		ItemStack itemInMeter = ItemStack.EMPTY;
		Map<ResourceLocation, IRecipe<?>> x = ChungyitemsMod.getRecipes(ChungyitemsModElements.CHUNGISER_BLOCK_RECIPE, world.getRecipeManager());

		System.out.println(x);

	}


}
