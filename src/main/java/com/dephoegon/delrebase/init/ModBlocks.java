package com.dephoegon.delrebase.init;

import com.dephoegon.delrebase.aid.modExtensions.leafBlock;
import com.dephoegon.delrebase.blocks.TestSandBlock;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;

@Mod.EventBusSubscriber
public class ModBlocks {

    public static final Block TEST_SAND = new TestSandBlock();
    public static final Block TEST_LEAF = new leafBlock("testleaf", CreativeTabs.DECORATIONS, leafBlock.BIOME);


    private static final Block [] BLOCKS = {
            TEST_SAND,
            TEST_LEAF
    };
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        Arrays.stream(BLOCKS).forEach((block) -> event.getRegistry().register(block));
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        Arrays.stream(BLOCKS).forEach((block) -> event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName())));
    }
}
