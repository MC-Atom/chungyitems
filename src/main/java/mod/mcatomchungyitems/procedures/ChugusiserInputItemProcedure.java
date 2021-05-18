package mod.mcatomchungyitems.procedures;

import net.minecraft.item.Item;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.container.Slot;
import net.minecraft.inventory.container.Container;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.command.ICommandSource;
import net.minecraft.command.CommandSource;

import mod.mcatomchungyitems.ChungyitemsModElements;
import mod.mcatomchungyitems.ChungyitemsMod;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.function.Supplier;
import java.util.Map;
import java.lang.*;

@ChungyitemsModElements.ModElement.Tag
public class ChugusiserInputItemProcedure extends ChungyitemsModElements.ModElement {
	public ChugusiserInputItemProcedure(ChungyitemsModElements instance) {
		super(instance, 7);
	}

	public static void addItem (ItemStack item, HashMap<Item,Integer> Items) {
		int itemCount = item.getCount();
		Item itemType = item.getItem();


		if (Items.get(itemType) != null) {
			Items.put(itemType, Items.get(itemType) + itemCount);
		} else {
			Items.put(itemType,itemCount);
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

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				ChungyitemsMod.LOGGER.warn("Failed to load dependency entity for procedure ChugusiserInputItem!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				ChungyitemsMod.LOGGER.warn("Failed to load dependency x for procedure ChugusiserInputItem!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				ChungyitemsMod.LOGGER.warn("Failed to load dependency y for procedure ChugusiserInputItem!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				ChungyitemsMod.LOGGER.warn("Failed to load dependency z for procedure ChugusiserInputItem!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				ChungyitemsMod.LOGGER.warn("Failed to load dependency world for procedure ChugusiserInputItem!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		double totalItems = 0;
		double itemsNeededToCraft = 0;
		if (world instanceof ServerWorld) {
			((World) world).getServer().getCommandManager().handleCommand(new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z),
					Vector2f.ZERO, (ServerWorld) world, 4, "", new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
					"say hi");
		}
		itemsNeededToCraft = (double) 160;

		HashMap<Item, Integer> Items = new HashMap<>();

		ItemStack guiSlot0 = getItemStack(0,entity);
		ItemStack guiSlot1 = getItemStack(1,entity);
		ItemStack guiSlot2 = getItemStack(2,entity);
		ItemStack guiSlot3 = getItemStack(3,entity);
		ItemStack guiSlot4 = getItemStack(4,entity);

		addItem(guiSlot0,Items);
		addItem(guiSlot1,Items);
		addItem(guiSlot2,Items);
		addItem(guiSlot3,Items);
		addItem(guiSlot4,Items);

		//ItemStack guiSlot0 = getItemStack((int) (0));
		//int guiAmountSlot0 = getAmount((int) (0));

		if (world instanceof ServerWorld) {
			((World) world).getServer().getCommandManager()
					.handleCommand(new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
							new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(), "say " + Items);

		}
	}
}
