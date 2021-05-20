package mod.mcatomchungyitems;

import mod.mcatomchungyitems.recipes.ChungiserRecipe;
import mod.mcatomchungyitems.recipes.ChungiserRecipeType;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.util.ResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.entity.EntityType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.block.Block;

import java.util.Map;
import java.util.function.Supplier;

import static mod.mcatomchungyitems.ChungyitemsModElements.CHUNGISER_BLOCK_RECIPE;

@Mod("chungyitems")
public class ChungyitemsMod {
	public static final Logger LOGGER = LogManager.getLogger(ChungyitemsMod.class);
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation("chungyitems", "chungyitems"),
			() -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	public ChungyitemsModElements elements;
	public ChungyitemsMod() {
		elements = new ChungyitemsModElements();
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientLoad);
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(IRecipeSerializer.class,this::registerRecipeSerializers);
		MinecraftForge.EVENT_BUS.register(new ChungyitemsModFMLBusEvents(this));
	}

	private void init(FMLCommonSetupEvent event) {
		elements.getElements().forEach(element -> element.init(event));
	}

	public void clientLoad(FMLClientSetupEvent event) {
		elements.getElements().forEach(element -> element.clientLoad(event));
	}

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(elements.getBlocks().stream().map(Supplier::get).toArray(Block[]::new));
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(elements.getItems().stream().map(Supplier::get).toArray(Item[]::new));
	}

	@SubscribeEvent
	public void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
		event.getRegistry().registerAll(elements.getEntities().stream().map(Supplier::get).toArray(EntityType[]::new));
	}

	@SubscribeEvent
	public void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
		event.getRegistry().registerAll(elements.getEnchantments().stream().map(Supplier::get).toArray(Enchantment[]::new));
	}

	@SubscribeEvent
	public void registerSounds(RegistryEvent.Register<net.minecraft.util.SoundEvent> event) {
		elements.registerSounds(event);
	}
	private static class ChungyitemsModFMLBusEvents {
		private final ChungyitemsMod parent;
		ChungyitemsModFMLBusEvents(ChungyitemsMod parent) {
			this.parent = parent;
		}

		@SubscribeEvent
		public void serverLoad(FMLServerStartingEvent event) {
			this.parent.elements.getElements().forEach(element -> element.serverLoad(event));
		}
	}
	private void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
		Registry.register(Registry.RECIPE_TYPE, new ResourceLocation("chungyitems:chungiser_block_recipe"),CHUNGISER_BLOCK_RECIPE);
		System.out.println("I registered " + CHUNGISER_BLOCK_RECIPE);
		event.getRegistry().register(ChungiserRecipe.SERIALIZER);
	}
	public static Map<ResourceLocation, IRecipe<?>> getRecipes (IRecipeType<?> recipeType, RecipeManager manager) {

		final Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipesMap = ObfuscationReflectionHelper.getPrivateValue(RecipeManager.class, manager, "field_199522_d");
		return recipesMap.get(recipeType);
	}
}