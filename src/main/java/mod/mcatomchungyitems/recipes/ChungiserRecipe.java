package mod.mcatomchungyitems.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mod.mcatomchungyitems.ChungyitemsMod;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static mod.mcatomchungyitems.ChungyitemsModElements.CHUNGISER_BLOCK_RECIPE;


public class ChungiserRecipe implements IRecipe <IInventory> {

    public static final Serialiser SERIALIZER = new Serialiser();
    private final Ingredient input;
    private final ItemStack output;
    private final Block block;
    private final ResourceLocation id;
    private final int count;
    private final int cookingTime;

    public ChungiserRecipe(Ingredient input, ItemStack output, Block block, ResourceLocation id, int count, int cookingTime) {
        this.input = input;
        this.output = output;
        this.block = block;
        this.id = id;
        this.count = count;
        this.cookingTime = cookingTime;

        System.out.println("lowoaded " + this.toString());
    }

    @Override
    public String toString() {
        //return super.toString();
        return "chungiser recipe [input = " + this.input + ", count = " + this.count + ", output = " + this.output + " , block = " + this.block.getRegistryName() + ", id = " + this.id + "]";
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return this.input.test(inv.getStackInSlot(0));
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return this.output.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.output;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType() {
        return CHUNGISER_BLOCK_RECIPE;
    }

    public int getCount() {
        return count;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public Ingredient getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public ArrayList<Ingredient> getMyIngredients() {
        ArrayList<Ingredient> output = new ArrayList<>();
        output.add(this.input);
        return output;
    }

    public boolean isValid(HashMap<Item, Integer> items, IRecipe<?> recipe, ArrayList<ItemStack> guiSlots) {

        Set<Item> itemsInChungusiser = items.keySet();

        Boolean validRecipe = true;

        if (this.input.test(guiSlots.get(0)) && recipe instanceof ChungiserRecipe) {
            for (Ingredient ingredient : ((ChungiserRecipe) recipe).getMyIngredients()) {

                Boolean isItemInList = false;
                Item itemThingy = null;
                for (ItemStack item : guiSlots) {
                    if(this.input.test(item)) {
                        isItemInList = true;
                        itemThingy = item.getItem();
                        break;
                    }
                }

                if (!isItemInList || items.get(itemThingy) == null || this.count > items.get(itemThingy)) {
                    validRecipe = false;
                    break;
                }
            }
            for (ItemStack item : guiSlots) {
                if (!this.input.test(item) ) {
                    validRecipe = false;
                    break;
                }
            }

        } else {
            validRecipe = false;
        }


        return validRecipe;
    }


    private static class Serialiser extends ForgeRegistryEntry <IRecipeSerializer<?>> implements IRecipeSerializer <ChungiserRecipe> {

        Serialiser () {
            this.setRegistryName(new ResourceLocation("chungyitems","chungiser_block_recipe"));

        }

        @Override
        public ChungiserRecipe read(ResourceLocation recipeId, JsonObject json) {

            final JsonElement inputElement;

            if (JSONUtils.isJsonArray(json, "input")) {
                inputElement = JSONUtils.getJsonArray(json,"input");
            } else {
                inputElement = JSONUtils.getJsonObject(json, "input");
            }

            final Ingredient input = Ingredient.deserialize(inputElement);

            final ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json,"output"));

            final int count = JSONUtils.getInt(json,"count");

            final int cookingTime = JSONUtils.getInt(json,"cookingtime");

            final ResourceLocation blockId = new ResourceLocation(JSONUtils.getString(json,"blockid"));
            final Block block = ForgeRegistries.BLOCKS.getValue(blockId);

            if (block == null || block == Blocks.AIR) {
                throw new IllegalStateException("The Block " + blockId + " does not exist!!!!!! >=[");
            }

            return new ChungiserRecipe(input,output,block,recipeId,count,cookingTime);
        }

        @Nullable
        @Override
        public ChungiserRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            // Reads a recipe from a packet buffer. This code is called on the client.
            System.out.println(buffer);
            final Ingredient input = Ingredient.read(buffer);
            final ItemStack output = buffer.readItemStack();
            final ResourceLocation blockId = buffer.readResourceLocation();
            final Block block = ForgeRegistries.BLOCKS.getValue(blockId);
            final int count = buffer.readInt();
            final int cookingTime = buffer.readInt();

            if (block == null) {

                throw new IllegalStateException("The block " + blockId + " does not exist.");
            }

            return new ChungiserRecipe(input, output, block, recipeId, count, cookingTime);
        }

        @Override
        public void write(PacketBuffer buffer, ChungiserRecipe recipe) {
            // Writes the recipe to a packet buffer. This is called on the server when a player
            // connects or when /reload is used.
            recipe.input.write(buffer);
            buffer.writeItemStack(recipe.output);
            buffer.writeResourceLocation(recipe.block.getRegistryName());
        }
    }
}
