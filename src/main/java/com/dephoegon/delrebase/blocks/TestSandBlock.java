package com.dephoegon.delrebase.blocks;

import com.dephoegon.delrebase.DelReBase;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class TestSandBlock extends Block {

    public TestSandBlock() {
        super(Material.SAND);

        // ===== ESSENTIAL REGISTRATION METHODS =====

        // 1. CRITICAL: Set the registry name - this MUST be unique and lowercase
        // Format: setRegistryName(modid, name) or setRegistryName("modid:name")
        this.setRegistryName(DelReBase.MOD_ID, "testsand");

        // 2. CRITICAL: Set the translation key (for localization)
        // This is what appears in lang files - should match registry name
        this.setTranslationKey("testsand");

        // 3. IMPORTANT: Set creative tab (where players find the block)
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);

        // ===== BLOCK PROPERTIES METHODS =====

        // Material properties
        this.setSoundType(SoundType.SAND);        // Sound when walking/breaking
        this.setHardness(0.5F);                   // Mining speed (0.0F = instant, -1.0F = unbreakable)
        this.setResistance(2.5F);                 // Explosion resistance

        // Additional common methods you might need:

        // ===== LIGHTING PROPERTIES (1.12.2 vs 1.20.1 COMPARISON) =====

        // LIGHT EMISSION (setLightLevel):
        // 1.12.2: this.setLightLevel(1.0F);              // Range: 0.0F to 1.0F (float)
        // 1.20.1: Uses integer 0-15 in block properties or LightEmission.of(15)
        //
        // Conversion: 1.12.2 float * 15 = 1.20.1 integer
        // Examples:
        // - 0.0F = no light (like stone)
        // - 0.5F = 7.5 light level (like redstone torch - 7 in 1.20.1)
        // - 0.9375F = 14.0625 light level (like torch - 14 in 1.20.1)
        // - 1.0F = 15 light level (like glowstone/sea lantern)

        // LIGHT BLOCKING (setLightOpacity):
        // 1.12.2: this.setLightOpacity(255);             // Range: 0-255 (int)
        // 1.20.1: Replaced with block shape system and propagatesSkylightDown property
        //
        // How it works in 1.12.2:
        // - 0 = completely transparent to light (like glass, air)
        // - 1-254 = partial light blocking (rare, used for special cases)
        // - 255 = completely opaque, blocks all light (like stone, dirt)
        //
        // 1.20.1 equivalent logic:
        // - Transparent blocks: propagatesSkylightDown = true, full collision shape = false
        // - Opaque blocks: propagatesSkylightDown = false, full collision shape = true
        // - The actual light blocking is determined by the block's VoxelShape

        // Examples for common block types:

        // For TRANSPARENT blocks (like glass):
        // this.setLightOpacity(0);                // 1.12.2: doesn't block light
        // 1.20.1 equivalent: Block.Properties.of().noOcclusion().isValidSpawn(Blocks::never)

        // For TRANSLUCENT blocks (like ice, slime):
        // this.setLightOpacity(3);                // 1.12.2: slightly reduces light
        // 1.20.1: Handled through custom light filtering in block state properties

        // For OPAQUE blocks (like stone, dirt) - DEFAULT:
        // this.setLightOpacity(255);              // 1.12.2: blocks all light (default for full blocks)
        // 1.20.1: Default behavior for full cube blocks

        // PRACTICAL EXAMPLES:

        // Glowing sand block (emits light like a torch):
        // this.setLightLevel(0.9375F);            // Emits light level 14 (like torch)

        // Transparent sand (like glass sand):
        // this.setLightOpacity(0);                // Doesn't block light at all

        // Semi-transparent sand (like tinted glass):
        // this.setLightOpacity(15);               // Reduces light slightly

        // Regular opaque sand (current behavior):
        // this.setLightOpacity(255);              // Blocks all light (default)

        // Harvest properties
        // this.setHarvestLevel("shovel", 0);     // Tool type and level required

        // Step properties
        // this.setSlipperiness(0.6F);            // Ice = 0.98F, normal = 0.6F

        // Tick properties
        // this.setTickRandomly(true);            // Enable random ticks for growth/decay
    }

    // ===== ADDITIONAL METHODS TO WATCH OUT FOR =====

    /* COMMON OVERRIDES YOU MIGHT NEED:

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false; // For transparent/translucent blocks
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false; // For non-full blocks (slabs, stairs, etc.)
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        // Called when block is placed
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        // Called when block is broken
    }

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        // Called for random ticks (if setTickRandomly(true))
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        // Called when player left-clicks block
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state,
                                  EntityPlayer playerIn, EnumHand hand,
                                  EnumFacing facing, float hitX, float hitY, float hitZ) {
        // Called when player right-clicks block
        return false; // Return true to prevent further processing
    }

    */
}
