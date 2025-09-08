package com.dephoegon.delrebase.blocks.gui;

import com.dephoegon.delrebase.aid.slots.itemSlot;
import com.dephoegon.delrebase.aid.slots.planSlots;
import com.dephoegon.delrebase.aid.slots.resultSlot;
import com.dephoegon.delrebase.blocks.tileentity.TestInventoryTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

import static com.dephoegon.delrebase.blocks.tileentity.TestInventoryTileEntity.INVENTORY_SIZE;

public class TestBlockEntityMenu extends Container {

    private final TestInventoryTileEntity tileEntity;
    private final World world;
    private int lastCraftProgress = -1;
    private int lastCraftTime = -1;

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = HOTBAR_SLOT_COUNT;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int TE_INVENTORY_SLOT_COUNT = INVENTORY_SIZE; // Number of slots in the tile entity

    public TestBlockEntityMenu(InventoryPlayer playerInventory, TestInventoryTileEntity tileEntity) {
        this.tileEntity = tileEntity;
        this.world = playerInventory.player.world;

        // Add tile entity slots (3 slots in a row)
        IItemHandler handler = tileEntity.getItemHandler();
        this.addSlotToContainer(new planSlots(handler, TestInventoryTileEntity.planSlot, 103, 18));
        this.addSlotToContainer(new itemSlot(handler, TestInventoryTileEntity.inputSlot, 57, 18));
        this.addSlotToContainer(new resultSlot(handler, TestInventoryTileEntity.outputSlot, 80, 60));

        // Add player inventory slots
        final int SLOT_SPACING = 18;
        final int HOTBAR_X_POS = 8;
        final int HOTBAR_Y_POS = 142;
        final int PLAYER_INVENTORY_X_POS = 8;
        final int PLAYER_INVENTORY_Y_POS = 84;

        // Add hotbar slots
        for (int x = 0; x < HOTBAR_SLOT_COUNT; x++) {
            addSlotToContainer(new Slot(playerInventory, x, HOTBAR_X_POS + SLOT_SPACING * x, HOTBAR_Y_POS));
        }

        // Add main player inventory
        for (int i = 0; i < PLAYER_INVENTORY_ROW_COUNT; ++i) {
            for (int l = 0; l < PLAYER_INVENTORY_COLUMN_COUNT; ++l) {
                this.addSlotToContainer(new Slot(playerInventory, l + i * 9 + 9, PLAYER_INVENTORY_X_POS + l * 18, PLAYER_INVENTORY_Y_POS + i * 18));
            }
        }
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer playerIn) { return tileEntity.isUsableByPlayer(playerIn); }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(@Nonnull EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot == null || !slot.getHasStack()) { return itemstack; }

        ItemStack sourceStack = slot.getStack();
        itemstack = sourceStack.copy();

        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            if (!mergeItemStack(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            if (!mergeItemStack(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("DelRebase (TestBlockEntity) - Invalid SlotIndex: "+index);
            return ItemStack.EMPTY;
        }
        if (sourceStack.isEmpty()) { slot.putStack(ItemStack.EMPTY); }
        else { slot.onSlotChanged(); }
        slot.onTake(playerIn, sourceStack);
        return itemstack;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        // Update craft data from tile entity and check for changes
        if (tileEntity != null && !world.isRemote) {
            int currentProgress = tileEntity.getCraftProgress();
            int currentTime = tileEntity.getCraftTime();

            // Only send updates if values have changed
            if (currentProgress != lastCraftProgress) {
                for (IContainerListener listener : this.listeners) {
                    listener.sendWindowProperty(this, 0, currentProgress);
                }
                lastCraftProgress = currentProgress;
            }

            if (currentTime != lastCraftTime) {
                for (IContainerListener listener : this.listeners) {
                    listener.sendWindowProperty(this, 1, currentTime);
                }
                lastCraftTime = currentTime;
            }
        }
    }

    @Override
    public void updateProgressBar(int id, int data) {
        // Receive updates from server on client side
        if (tileEntity != null) { tileEntity.setCraftData(id, data); }
    }

    // Getter methods for accessing craft data from GUI
    public int getCraftProgress() { return tileEntity != null ? tileEntity.getCraftProgress() : 0; }

    public int getCraftTime() { return tileEntity != null ? tileEntity.getCraftTime() : 0; }

    public boolean isCrafting() { return tileEntity.getCraftProgress() > 0; }
}
