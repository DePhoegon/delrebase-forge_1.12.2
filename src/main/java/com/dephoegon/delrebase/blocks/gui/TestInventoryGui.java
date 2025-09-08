package com.dephoegon.delrebase.blocks.gui;

import com.dephoegon.delrebase.DelReBase;
import com.dephoegon.delrebase.blocks.tileentity.TestInventoryTileEntity;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import static com.dephoegon.delrebase.aid.slots.planSlots.isPlansSlotItem;
import static com.dephoegon.delrebase.init.ModItems.*;

public class TestInventoryGui extends GuiContainer {
    private static final ResourceLocation EMPTY_TEXTURE =
            new ResourceLocation(DelReBase.MOD_ID, "textures/gui/block_cutting_station_gui_empty.png");
    private static final ResourceLocation COMPOUND_TEXTURE =
            new ResourceLocation(DelReBase.MOD_ID, "textures/gui/block_cutting_station_gui_compound.png");
    private static final ResourceLocation PLANS_WALL_TEXTURE =
            new ResourceLocation(DelReBase.MOD_ID, "textures/gui/block_cutting_station_gui_wall.png");
    private static final ResourceLocation PLANS_SLAB_TEXTURE =
            new ResourceLocation(DelReBase.MOD_ID, "textures/gui/block_cutting_station_gui_slab.png");
    private static final ResourceLocation PLANS_STAIR_TEXTURE =
            new ResourceLocation(DelReBase.MOD_ID, "textures/gui/block_cutting_station_gui_stair.png");
    private static final ResourceLocation PLANS_FENCE_TEXTURE =
            new ResourceLocation(DelReBase.MOD_ID, "textures/gui/block_cutting_station_gui_fence.png");
    private static final ResourceLocation PLANS_FENCE_GATE_TEXTURE =
            new ResourceLocation(DelReBase.MOD_ID, "textures/gui/block_cutting_station_gui_fence_gate.png");
    private final TestInventoryTileEntity tileEntity;

    public TestInventoryGui(InventoryPlayer playerInventory, TestInventoryTileEntity tileEntity) {
        super(new TestBlockEntityMenu(playerInventory, tileEntity));
        this.tileEntity = tileEntity;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        Item item = tileEntity.getItemHandler().getStackInSlot(TestInventoryTileEntity.planSlot).getItem();
        ResourceLocation TEXTURE = null;
        if (isPlansSlotItem(item)) {
            if (item == ARMOR_COMPOUND) { TEXTURE = COMPOUND_TEXTURE; }
            if (item == WALL_PLANS) { TEXTURE = PLANS_WALL_TEXTURE; }
            if (item == FENCE_GATE_PLANS) { TEXTURE = PLANS_FENCE_GATE_TEXTURE; }
            if (item == FENCE_PLANS) { TEXTURE = PLANS_FENCE_TEXTURE; }
            if (item == SLAB_PLANS) { TEXTURE = PLANS_SLAB_TEXTURE; }
            if (item == STAIR_PLANS) { TEXTURE = PLANS_STAIR_TEXTURE; }
        }
        TEXTURE = TEXTURE != null ? TEXTURE : EMPTY_TEXTURE;
        int guiWidth = (this.width - this.xSize) / 2;
        // Match 1.20.1 coordinate calculation exactly
        int x = (this.width - this.xSize) / 2;  // Same as (width - imageWidth) / 2
        int y = (this.height - this.ySize) / 2; // Same as (height - imageHeight) / 2

        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize); // Same as imageWidth, imageHeight
        int progress = getScaledProgress();
        this.drawTexturedModalRect(x + 102, y + 41, 176, 0, 8, progress);
    }
    //round about way to access Container methods in 1.12.2
    private int getScaledProgress() {
        TestBlockEntityMenu container = (TestBlockEntityMenu) this.inventorySlots;
        int progress = container.getCraftProgress();
        int maxProgress = container.getCraftTime();
        int arrowSize = 26;
        return maxProgress != 0 ? progress * arrowSize / maxProgress : 0;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String tileName = "Test Inventory";
        this.fontRenderer.drawString(tileName,
            (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2), 6, 4210752);
        this.fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
    }
}
