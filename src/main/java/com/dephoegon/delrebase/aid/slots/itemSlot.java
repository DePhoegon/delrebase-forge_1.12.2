package com.dephoegon.delrebase.aid.slots;

import com.dephoegon.delrebase.blocks.tileentity.TestInventoryTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class itemSlot extends SlotItemHandler {
    private final int slotType; // 1 = item slot
    public itemSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.slotType = index;
    }
    public boolean isItemValid(@Nonnull ItemStack stack) { return !planSlots.isPlansSlotItem(stack.getItem()); }
    public boolean isEnabled() { return slotType == TestInventoryTileEntity.inputSlot; }
}
