package com.dephoegon.delrebase.proxy;

import com.dephoegon.delrebase.aid.interfaces.leafColoring;
import com.dephoegon.delrebase.init.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        // Register item models for client-side rendering
        registerItemModel(Item.getItemFromBlock(ModBlocks.TEST_SAND), 0, "testsand");
        registerItemModel(Item.getItemFromBlock(ModBlocks.TEST_LEAF), 0, "testleaf");
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerBlockColors(ColorHandlerEvent.Block event) {
        final BlockColors blockColors = event.getBlockColors();

        // Register the TestLeaf block to use foliage coloring (like oak leaves)
       //blockColors.registerBlockColorHandler(((leafBlock)ModBlocks.TEST_LEAF).getColorHandler(), ModBlocks.TEST_LEAF);
        leafColoring.getBlockColors(blockColors, ModBlocks.TEST_LEAF);

        // Examples of how to use other coloring methods:
        // For grass-based coloring: blockColors.registerBlockColorHandler(TestLeafBlock::getGrassColor, ModBlocks.TEST_LEAF);
        // For spruce-like coloring: blockColors.registerBlockColorHandler(TestLeafBlock::getSpruceColor, ModBlocks.TEST_LEAF);
        // For birch-like coloring: blockColors.registerBlockColorHandler(TestLeafBlock::getBirchColor, ModBlocks.TEST_LEAF);
        // For seasonal coloring: blockColors.registerBlockColorHandler(TestLeafBlock::getSeasonalColor, ModBlocks.TEST_LEAF);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerItemColors(ColorHandlerEvent.Item event) {
        // Register item coloring to match the block coloring
        event.getItemColors().registerItemColorHandler((stack, tintIndex) -> {
            return Minecraft.getMinecraft().getBlockColors().colorMultiplier(
                ((ModBlocks.TEST_LEAF.getDefaultState())),
                null, null, tintIndex);
        }, ModBlocks.TEST_LEAF);
    }

    private static void registerItemModel(Item item, int meta, String name) {
        ModelLoader.setCustomModelResourceLocation(item, meta,
            new ModelResourceLocation("delrebase:" + name, "inventory"));
    }
}
