package com.dephoegon.delrebase.proxy;

import com.dephoegon.delrebase.DelReBase;
import com.dephoegon.delrebase.blocks.gui.GuiHandler;
import com.dephoegon.delrebase.init.ModTileEntities;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ServerMain {

    public void preInit(FMLPreInitializationEvent event) {
        // Register blocks and items
        ModTileEntities.registerTileEntities();
    }

    public void init(FMLInitializationEvent event) {
        // Register recipes, handlers, etc.
        NetworkRegistry.INSTANCE.registerGuiHandler(DelReBase.instance, new GuiHandler());
    }

    public void postInit(FMLPostInitializationEvent event) {
        // Post initialization tasks
    }
}