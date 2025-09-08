package com.dephoegon.delrebase.blocks.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class TestInventoryTileEntity extends TileEntity implements IInventory, ITickable {
    public static final int INVENTORY_SIZE = 3;
    public static final int outputSlot = 1;
    public static final int inputSlot = 0;
    public static final int planSlot = 2;

    // Craft data - index 0: craft progress, index 1: craft time
    private final int[] craftData = new int[2];
    private int craftProgress = 0;
    private int craftTime = 52; // Default craft time

    private final ItemStackHandler itemHandler = new ItemStackHandler(INVENTORY_SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            TestInventoryTileEntity.this.markDirty();
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            // Allow any items for this test block
            return true;
        }
    };
    private final ItemStackHandler inputHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            TestInventoryTileEntity.this.markDirty();
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            // Allow any items for this test block
            return true;
        }
    };

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    // Craft data access methods
    public int getCraftProgress() {
        return craftProgress;
    }

    public void setCraftProgress(int progress) {
        this.craftProgress = progress;
        this.craftData[0] = progress;
        markDirty();
    }

    public int getCraftTime() {
        return craftTime;
    }

    public void setCraftTime(int time) {
        this.craftTime = time;
        this.craftData[1] = time;
        markDirty();
    }

    public int[] getCraftData() {
        craftData[0] = craftProgress;
        craftData[1] = craftTime;
        return craftData;
    }

    public void setCraftData(int index, int value) {
        if (index == 0) {
            setCraftProgress(value);
        } else if (index == 1) {
            setCraftTime(value);
        }
    }

    @Override
    public void update() {
        // Tile entity tick logic
        if (!world.isRemote) {
            // Server-side tick logic
            // Example craft progress logic
            if (canCraft()) {
                craftProgress++;
                if (craftProgress >= craftTime) {
                    // Crafting complete
                    completeCraft();
                    craftProgress = 0;
                }
            } else {
                craftProgress = 0;
            }
        }
    }

    private boolean canCraft() {
        // Add your crafting logic here
        // For example, check if input slots have valid items
        return !itemHandler.getStackInSlot(0).isEmpty() &&
               !itemHandler.getStackInSlot(1).isEmpty();
    }

    private void completeCraft() {
        // Add your craft completion logic here
        // For example, consume input items and produce output
        if (canCraft()) {
            // Example: consume one item from each input slot
            ItemStack input1 = itemHandler.extractItem(0, 1, false);
            ItemStack input2 = itemHandler.extractItem(1, 1, false);

            // Example: produce output (could be based on recipe system)
            ItemStack output = new ItemStack(net.minecraft.init.Items.DIAMOND, 1);
            itemHandler.insertItem(2, output, false);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("inventory", itemHandler.serializeNBT());
        compound.setInteger("craftProgress", craftProgress);
        compound.setInteger("craftTime", craftTime);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("inventory")) {
            itemHandler.deserializeNBT(compound.getCompoundTag("inventory"));
        }
        craftProgress = compound.getInteger("craftProgress");
        craftTime = compound.getInteger("craftTime");
    }

    // IInventory implementation for compatibility
    @Override
    public int getSizeInventory() {
        return INVENTORY_SIZE;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < INVENTORY_SIZE; i++) {
            if (!itemHandler.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return itemHandler.getStackInSlot(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return itemHandler.extractItem(index, count, false);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = itemHandler.getStackInSlot(index);
        itemHandler.setStackInSlot(index, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        itemHandler.setStackInSlot(index, stack);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return world.getTileEntity(pos) == this &&
               player.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64.0;
    }

    @Override
    public void openInventory(EntityPlayer player) {
        // Called when inventory is opened
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        // Called when inventory is closed
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return itemHandler.isItemValid(index, stack);
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {
        // Not used for this implementation
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < INVENTORY_SIZE; i++) {
            itemHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    @Override
    public String getName() {
        return "container.test_inventory_block";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString(getName());
    }
}
