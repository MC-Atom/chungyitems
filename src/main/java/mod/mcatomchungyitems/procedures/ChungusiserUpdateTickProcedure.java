package mod.mcatomchungyitems.procedures;

import mod.mcatomchungyitems.ChungyitemsMod;
import mod.mcatomchungyitems.recipes.ChungiserRecipe;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import mod.mcatomchungyitems.ChungyitemsModElements;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
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
}