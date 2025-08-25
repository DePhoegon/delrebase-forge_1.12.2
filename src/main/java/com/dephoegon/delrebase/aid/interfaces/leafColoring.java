package com.dephoegon.delrebase.aid.interfaces;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.minecraft.world.ColorizerFoliage.*;

public interface leafColoring {
    // Constants for color types
    String BIOME = "biome";
    String GRASS = "grass";
    String SPRUCE = "spruce";
    String BIRCH = "birch";
    String SEASONAL = "seasonal";

    // Methods that implementing classes must provide
    String getColorType();

    // Default method to validate color types
    default boolean isValidColorType(String type) {
        return BIOME.equals(type) || GRASS.equals(type) || SPRUCE.equals(type) || BIRCH.equals(type) || SEASONAL.equals(type);
    }

    // Default method to get available color types
    default String[] getAvailableColorTypes() { return new String[]{BIOME, GRASS, SPRUCE, BIRCH, SEASONAL}; }
    @SideOnly(Side.CLIENT)
    default IBlockColor getColorHandler() {
        switch (this.getColorType()) {
            case GRASS:
                return leafColoring::getGrassColor;
            case SPRUCE:
                return leafColoring::getSpruceColor;
            case BIRCH:
                return leafColoring::getBirchColor;
            case SEASONAL:
                return leafColoring::getSeasonalColor;
            case BIOME:
            default:
                return leafColoring::getFoliageColor;
        }
    }

    /**
     * Alternative method that returns the color directly for use in color handlers
     * Use this if you prefer lambda expressions in ClientProxy
     */
    @SideOnly(Side.CLIENT)
    default int getBlockColor(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex) {
        if (!(state.getBlock() instanceof leafColoring)) { return getFoliageColorBasic(); }
        switch (this.getColorType()) {
            case GRASS:
                return getGrassColor(state, world, pos, tintIndex);
            case SPRUCE:
                return getSpruceColor(state, world, pos, tintIndex);
            case BIRCH:
                return getBirchColor(state, world, pos, tintIndex);
            case SEASONAL:
                return getSeasonalColor(state, world, pos, tintIndex); // left as seasonal for potential future use, specific seasonal logic not implemented
            case BIOME:
            default:
                return getFoliageColor(state, world, pos, tintIndex);
        }
    }
    @SideOnly(Side.CLIENT)
    static void getBlockColors(BlockColors blockColors, Block block) {
        if (block instanceof leafColoring) {
            blockColors.registerBlockColorHandler(((leafColoring) block).getColorHandler(), block);
        }
    }

    // Biome-based coloring helper methods

    @SideOnly(Side.CLIENT)
    static int getFoliageColor(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex) {
        if (world != null && pos != null) { return BiomeColorHelper.getFoliageColorAtPos(world, pos); }
        return ColorizerFoliage.getFoliageColorBasic();
    }
    @SideOnly(Side.CLIENT)
    static int getGrassColor(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex) {
        if (world != null && pos != null) { return BiomeColorHelper.getGrassColorAtPos(world, pos); }
        return ColorizerGrass.getGrassColor(0.5D, 1.0D);
    }
    @SideOnly(Side.CLIENT)
    static int getSpruceColor(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex) { return getFoliageColorPine(); }
    @SideOnly(Side.CLIENT)
    static int getBirchColor(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex) { return getFoliageColorBirch(); }
    @SideOnly(Side.CLIENT)
    static int getSeasonalColor(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex) {
        if (world != null && pos != null) { return BiomeColorHelper.getFoliageColorAtPos(world, pos); }
        return ColorizerFoliage.getFoliageColorBasic();
    }
}
