package com.dephoegon.delrebase;

import com.dephoegon.delrebase.aid.proxy.commonProxy;
import com.dephoegon.delrebase.aid.util.ref;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.lang.ref.Reference;

@Mod(modid = ref.DEL_ID, name = ref.NAME, version = ref.VERSION)
public class DelReBase {
    @Mod.Instance
    public static DelReBase instance;
    private static Logger logger;

    @SidedProxy(clientSide = ref.CLIENT, serverSide = ref.COMMON)
    public static commonProxy proxy;

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }
    @EventHandler
    public void init(FMLInitializationEvent event) { }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) { }
}