package com.dephoegon.delrebase;

import com.dephoegon.delrebase.init.ModBlocks;
import com.dephoegon.delrebase.proxy.CommonProxy;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = DelReBase.MOD_ID, name = DelReBase.NAME, version = DelReBase.VERSION)
public class DelReBase
{
    public static final String MOD_ID = "delrebase";
    public static final String NAME = "rebase 1.12 test";
    public static final String VERSION = "0.0.1";

    @SidedProxy(clientSide = "com.dephoegon.delrebase.proxy.ClientProxy",
                serverSide = "com.dephoegon.delrebase.proxy.CommonProxy")
    public static CommonProxy proxy;

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        // Model registration now handled by ClientProxy ModelRegistryEvent
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        logger.info("TEST SAND BLOCK >> {}", ModBlocks.TEST_SAND.getRegistryName());
    }
}
