package com.dephoegon.delrebase.init;

import com.dephoegon.delrebase.DelReBase;
import com.dephoegon.delrebase.blocks.tileentity.TestInventoryTileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {

    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TestInventoryTileEntity.class,
            DelReBase.MOD_ID + ":test_inventory_tile_entity");
    }
}
