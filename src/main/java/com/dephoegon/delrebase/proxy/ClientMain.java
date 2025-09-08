package com.dephoegon.delrebase.proxy;

import com.dephoegon.delrebase.aid.interfaces.leafColoring;
import com.dephoegon.delrebase.init.ModBlocks;
import com.dephoegon.delrebase.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientMain extends ServerMain {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        // Register item models for client-side rendering
        for (Block block : ModBlocks.TEST_BLOCKS) {
            if (block.getRegistryName() == null) { continue; }
            registerItemModel(Item.getItemFromBlock(block), 0, block.getRegistryName().getPath());
        }

        for (Item planItem : ModItems.PLAN_ITEMS) {
            if (planItem.getRegistryName() != null) { continue; }
            registerItemModel(planItem, 0, planItem.getRegistryName().getPath());
        }
    }

    @SubscribeEvent
    public static void registerBlockColors(ColorHandlerEvent.Block event) {
        final BlockColors blockColors = event.getBlockColors();

        leafColoring.getBlockColors(blockColors, ModBlocks.TEST_LEAF);
    }

    @SubscribeEvent
    public static void registerItemColors(ColorHandlerEvent.Item event) {
        event.getItemColors().registerItemColorHandler((stack, tintIndex) -> {
            return Minecraft.getMinecraft().getBlockColors().colorMultiplier(((ModBlocks.TEST_LEAF.getDefaultState())), null, null, tintIndex);
        }, ModBlocks.TEST_LEAF);
    }

    @SuppressWarnings("SameParameterValue")
    private static void registerItemModel(Item item, int meta, String name) {
        ModelLoader.setCustomModelResourceLocation(item, meta,
                new ModelResourceLocation("delrebase:" + name, "inventory"));
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        // Client-specific pre-initialization
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        // Client-specific initialization (register renderers, etc.)
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        // Client-specific post-initialization
    }
}
