package com.dephoegon.delrebase.proxy;

import com.dephoegon.delrebase.init.ModBlocks;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        // Register item models for client-side rendering
        registerItemModel(Item.getItemFromBlock(ModBlocks.TEST_SAND), 0, "testsand");
    }

    private static void registerItemModel(Item item, int meta, String name) {
        ModelLoader.setCustomModelResourceLocation(item, meta,
            new ModelResourceLocation("delrebase:" + name, "inventory"));
    }
}
