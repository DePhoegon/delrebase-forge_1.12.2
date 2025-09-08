package com.dephoegon.delrebase.aid.slots;

import com.dephoegon.delrebase.blocks.tileentity.TestInventoryTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class resultSlot extends SlotItemHandler {
    private final int slotType;
    public resultSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.slotType = index;
    }
    public boolean isItemValid(@Nonnull ItemStack ignoredStack) { return false; } // cannot place items in result slot
    public int getSlotStackLimit() { return 64; }
    public boolean isEnabled() { return slotType == TestInventoryTileEntity.planSlot; }
}
