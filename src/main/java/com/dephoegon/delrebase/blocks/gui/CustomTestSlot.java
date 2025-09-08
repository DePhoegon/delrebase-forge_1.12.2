package com.dephoegon.delrebase.blocks.gui;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CustomTestSlot extends SlotItemHandler {

    private final int slotType; // 0 = input, 1 = fuel, 2 = output

    public CustomTestSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, int slotType) {
        super(itemHandler, index, xPosition, yPosition);
        this.slotType = slotType;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        // 1.12.2 equivalent of mayPlace
        switch (slotType) {
            case 0: // Input slot - only allow certain items
                return !stack.isEmpty() && stack.getItem() != net.minecraft.init.Items.DIAMOND;
            case 1: // Fuel slot - only allow fuel items
                return net.minecraft.item.ItemStack.areItemsEqual(stack, new ItemStack(net.minecraft.init.Items.COAL));
            case 2: // Output slot - no manual placement allowed
                return false;
            default:
                return super.isItemValid(stack);
        }
    }

    @Override
    public boolean isEnabled() {
        // Disable output slot when not crafting, or disable all slots under certain conditions
        if (slotType == 2) {
            // Could check if tile entity is crafting
            return true; // For now, always enabled
        }
        return true; // Input and fuel slots always enabled
    }

    @Override
    public boolean canTakeStack(net.minecraft.entity.player.EntityPlayer playerIn) {
        // Controls whether items can be removed from the slot
        if (slotType == 2) {
            // Output slot - only allow taking when craft is complete
            return this.getHasStack(); // Can take if there's something there
        }
        return true; // Input slots can always be emptied
    }
}
