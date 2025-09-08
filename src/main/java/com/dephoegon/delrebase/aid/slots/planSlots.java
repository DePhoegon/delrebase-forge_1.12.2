package com.dephoegon.delrebase.aid.slots;

import com.dephoegon.delrebase.blocks.tileentity.TestInventoryTileEntity;
import com.dephoegon.delrebase.items.PlansCompound;
import com.dephoegon.delrebase.items.PlansItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class planSlots extends SlotItemHandler {
    private final int slotType;
    public planSlots(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.slotType = index;
    }
    public static boolean isPlansSlotItem(Item item) { return item instanceof PlansItem || item instanceof PlansCompound; }
    public boolean isItemValid(@Nonnull ItemStack stack) { return isPlansSlotItem(stack.getItem()); }
    public int getSlotStackLimit() { return 64; }
    public boolean isEnabled() { return slotType == TestInventoryTileEntity.planSlot; }
}
