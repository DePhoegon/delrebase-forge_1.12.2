package com.dephoegon.delrebase.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class TestSandBlock extends Block {

    public TestSandBlock() {
        super(Material.SAND);
        this.setUnlocalizedName("testsand");
        this.setRegistryName("testsand");
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        this.setSoundType(SoundType.SAND);
        this.setHardness(0.5F);
        this.setResistance(2.5F);
    }
}
