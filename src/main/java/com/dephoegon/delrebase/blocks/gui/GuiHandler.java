package com.dephoegon.delrebase.blocks.gui;

import com.dephoegon.delrebase.blocks.tileentity.TestInventoryTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    public static final int TEST_INVENTORY_GUI = 0;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity tileEntity = world.getTileEntity(pos);

        switch (ID) {
            case TEST_INVENTORY_GUI:
                if (tileEntity instanceof TestInventoryTileEntity) {
                    return new TestBlockEntityMenu(player.inventory, (TestInventoryTileEntity) tileEntity);
                }
                break;
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity tileEntity = world.getTileEntity(pos);

        switch (ID) {
            case TEST_INVENTORY_GUI:
                if (tileEntity instanceof TestInventoryTileEntity) {
                    return new TestInventoryGui(player.inventory, (TestInventoryTileEntity) tileEntity);
                }
                break;
        }
        return null;
    }
}
