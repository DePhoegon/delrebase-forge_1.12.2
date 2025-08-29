package com.dephoegon.delrebase.aid.modExtensions;

import com.dephoegon.delrebase.DelReBase;
import com.dephoegon.delrebase.aid.interfaces.leafColoring;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings({"NullableProblems", "deprecation"})
public class leafBlock extends Block implements leafColoring {
    private final String colorType; // Default to biome coloring
    private static final float HARDNESS = 0.2F;
    private static final float RESISTANCE = 0.2F;

    public leafBlock(String name, CreativeTabs creativeTabs, String colorType, MapColor blockMapColorIn) {
        super(Material.LEAVES, blockMapColorIn);
        this.setSoundType(SoundType.PLANT);
        this.setHardness(HARDNESS);
        this.setResistance(RESISTANCE);
        this.setRegistryName(DelReBase.MOD_ID, name);
        this.setTranslationKey(name);
        this.setCreativeTab(creativeTabs);
        this.colorType = colorType;
    }

    public leafBlock(String name, CreativeTabs creativeTabs, String colorType) {
        super(Material.LEAVES);
        this.setSoundType(SoundType.PLANT);
        this.setHardness(HARDNESS);
        this.setResistance(RESISTANCE);
        this.setRegistryName(DelReBase.MOD_ID, name);
        this.setCreativeTab(creativeTabs);
        this.colorType = colorType;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) { return false; }
    public boolean isFullCube(IBlockState state) { return false; }

    @Override
    public String getColorType() { return this.colorType; }
}