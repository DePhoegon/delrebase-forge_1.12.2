package com.dephoegon.delrebase;

import com.dephoegon.delrebase.aid.generated.ModConstants;
import com.dephoegon.delrebase.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static com.dephoegon.delrebase.aid.generated.ModConstants.*;


@Mod(modid = DelReBase.MOD_ID, name = DelReBase.NAME, version = DelReBase.VERSION)
public class DelReBase
{
    public static final String MOD_ID = MOD_ID_FROM_GRADLE;
    public static final String NAME = NAME_FROM_GRADLE;
    public static final String VERSION = VERSION_FROM_GRADLE;

    @SidedProxy(clientSide = "com.dephoegon.delrebase.proxy.ClientProxy",
                serverSide = "com.dephoegon.delrebase.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance(MOD_ID)
    public static DelReBase instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
