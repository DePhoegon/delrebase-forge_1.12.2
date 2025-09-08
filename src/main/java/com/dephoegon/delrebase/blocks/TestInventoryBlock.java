package com.dephoegon.delrebase.blocks;

import com.dephoegon.delrebase.DelReBase;
import com.dephoegon.delrebase.blocks.tileentity.TestInventoryTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class TestInventoryBlock extends Block implements ITileEntityProvider {

    public TestInventoryBlock() {
        super(Material.ROCK);
        setTranslationKey("test_inventory_block");
        setRegistryName(DelReBase.MOD_ID, "test_inventory_block");
        setCreativeTab(CreativeTabs.MISC);
        setHardness(2.0f);
        setResistance(6.0f);
        setLightLevel(0.0f);
        setLightOpacity(15);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
                                  EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote && playerIn.getHeldItem(hand).isEmpty()) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof TestInventoryTileEntity) {
                playerIn.openGui(DelReBase.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
                return true;
            }
        }
        return false;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TestInventoryTileEntity) {
            TestInventoryTileEntity inventoryTile = (TestInventoryTileEntity) tileEntity;
            InventoryHelper.dropInventoryItems(worldIn, pos, inventoryTile);
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TestInventoryTileEntity();
    }
}
