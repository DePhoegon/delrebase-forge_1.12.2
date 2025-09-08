package com.dephoegon.delrebase.blocks.recipes;

import com.dephoegon.delrebase.DelReBase;
import com.google.gson.JsonObject;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;

import javax.annotation.Nonnull;

public class TestInventoryRecipe implements IRecipe {

    private final ResourceLocation id;
    private final ItemStack output;
    private final ItemStack input;
    private final ItemStack plan;

    public TestInventoryRecipe(ResourceLocation id, ItemStack output, ItemStack input, ItemStack plan) {
        this.id = id;
        this.output = output;
        this.input = input;
        this.plan = plan;
    }

    public ItemStack getOutput() { return output.copy(); }
    public ItemStack getInput() { return input; }
    public ItemStack getPlan() { return plan; }

    public boolean matches(ItemStack inputSlot, ItemStack planSlot) {
        if (getInput().isEmpty() || getPlan().isEmpty()) {
            return false;
        }

        boolean inputMatch = ItemStack.areItemsEqual(getInput(), inputSlot) &&
                           inputSlot.getCount() >= getInput().getCount();
        boolean planMatch = ItemStack.areItemsEqual(getPlan(), planSlot) &&
                          planSlot.getCount() >= getPlan().getCount();

        return inputMatch && planMatch;
    }

    @Override
    public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World worldIn) {
        // This recipe type doesn't use standard crafting
        return false;
    }

    @Override
    @Nonnull
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
        return getOutput();
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    @Nonnull
    public ItemStack getRecipeOutput() {
        return output;
    }

    @Override
    @Nonnull
    public ResourceLocation getRegistryName() {
        return id;
    }

    @Override
    public IRecipe setRegistryName(ResourceLocation name) {
        // Registry name is set in constructor
        return this;
    }

    @Override
    public Class<IRecipe> getRegistryType() {
        return IRecipe.class;
    }

    public static class Factory implements IRecipeFactory {
        @Override
        public IRecipe parse(JsonContext context, JsonObject json) {
            ResourceLocation id = new ResourceLocation(DelReBase.MOD_ID, "test_inventory_recipe");

            JsonObject outputObj = JsonUtils.getJsonObject(json, "output");
            JsonObject inputObj = JsonUtils.getJsonObject(json, "input");
            JsonObject planObj = JsonUtils.getJsonObject(json, "plan");

            ItemStack output = parseItemStack(outputObj);
            ItemStack input = parseItemStack(inputObj);
            ItemStack plan = parseItemStack(planObj);

            return new TestInventoryRecipe(id, output, input, plan);
        }

        private ItemStack parseItemStack(JsonObject json) {
            String item = JsonUtils.getString(json, "item");
            int count = JsonUtils.getInt(json, "count", 1);
            int data = JsonUtils.getInt(json, "data", 0);

            // This is a simplified parser - in practice you'd want more robust parsing
            return new ItemStack(net.minecraft.item.Item.getByNameOrId(item), count, data);
        }
    }
}
