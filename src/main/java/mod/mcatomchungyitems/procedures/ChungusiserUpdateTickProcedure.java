package mod.mcatomchungyitems.procedures;

import mod.mcatomchungyitems.ChungyitemsMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.AirItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import mod.mcatomchungyitems.ChungyitemsModElements;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.*;
import java.util.function.Supplier;

@ChungyitemsModElements.ModElement.Tag
public class ChungusiserUpdateTickProcedure extends ChungyitemsModElements.ModElement {
	public ChungusiserUpdateTickProcedure(ChungyitemsModElements instance) {
		super(instance, 3);
	}
	public boolean isValidRecipe = false;

	public static void addItem (ItemStack item, Dictionary<Item,Integer> Items) {
		int itemCount = item.getCount();
		Item itemType = item.getItem();

		if (itemType.getDisplayName(item).toString() != "Air") {
			if (Items.get(itemType) != null) {
				Items.put(itemType, Items.get(itemType) + itemCount);
			} else {
				Items.put(itemType, itemCount);
			}
		}
	}

	public static ItemStack getItemStack(int sltid, Entity entity) {
		if (entity instanceof ServerPlayerEntity) {
			Container _current = ((ServerPlayerEntity) entity).openContainer;
			if (_current instanceof Supplier) {
				Object invobj = ((Supplier) _current).get();
				if (invobj instanceof Map) {
					return ((Slot) ((Map) invobj).get(sltid)).getStack();
				}
			}
		}
		return ItemStack.EMPTY;
	}

	public static int getAmount(int sltid, Entity entity) {
		if (entity instanceof ServerPlayerEntity) {
			Container _current = ((ServerPlayerEntity) entity).openContainer;
			if (_current instanceof Supplier) {
				Object invobj = ((Supplier) _current).get();
				if (invobj instanceof Map) {
					ItemStack stack = ((Slot) ((Map) invobj).get(sltid)).getStack();;
					if (stack != null)
						return stack.getCount();
				}
			}
		}
		return 0;
	}

	public static void executeProcedure(ServerWorld world){

		ItemStack itemInMeter = ItemStack.EMPTY;

		Collection x = ChungyitemsMod.getRecipes(ChungyitemsModElements.CHUNGISER_BLOCK_RECIPE, world.getRecipeManager()).values();
		System.out.println(x);

		//double itemsNeededToCraft = 160;

		Dictionary<Item, Integer> Items = new Hashtable<>();

		ItemStack guiSlot1 = getItemStack(1);
		ItemStack guiSlot2 = getItemStack(2);
		ItemStack guiSlot3 = getItemStack(3);
		ItemStack guiSlot4 = getItemStack(4);
		ItemStack guiSlot5 = getItemStack(5);

		addItem(guiSlot1,Items);
		addItem(guiSlot2,Items);
		addItem(guiSlot3,Items);
		addItem(guiSlot4,Items);
		addItem(guiSlot5,Items);

		Enumeration itemsInChungusiser = Items.keys();
		Boolean sameItems = false;

		while (itemsInChungusiser.hasMoreElements()) {
			System.out.println(itemsInChungusiser.nextElement());

		}

	}


}