# TODO: Block Variants Infrastructure Setup

## When You're Ready to Work - Complete Block Variants System

### Priority Order (Recommended Implementation Sequence):
1. **SLABS** (Easiest - good starting point)
2. **STAIRS** (Medium complexity)
3. **WALLS** (Complex blockstate handling)
4. **FENCES** (Connection logic)
5. **FENCE GATES** (Most complex - redstone + connections)

---

## CRITICAL MATERIAL VERIFICATION FOR 1.12.2

### ⚠️ DOUBLE CHECK MATERIALS BEFORE IMPLEMENTATION ⚠️

#### Available Materials in 1.12.2:
- [x] **Material.SAND** - ✅ EXISTS (current TestSand usage)
- [x] **Material.ROCK** - ✅ EXISTS (stone variants)
- [x] **Material.WOOD** - ✅ EXISTS (wood planks)
- [x] **Material.CLAY** - ✅ EXISTS (terracotta - called "clay" in 1.12.2)
- [x] **Material.GLASS** - ✅ EXISTS (glowstone usage)

#### Materials to VERIFY/INVESTIGATE:
- [ ] **Sandstone blocks**: Check if they use `Material.ROCK` or `Material.SAND`
- [ ] **Concrete blocks**: ❌ DO NOT EXIST in 1.12.2 (added in 1.12+)
- [ ] **Terracotta**: ✅ EXISTS but called "Hardened Clay" in 1.12.2

### Material Configuration by Block Type:

#### Sand-Like Materials (Current TestSand):
```java
super(Material.SAND);
this.setSoundType(SoundType.SAND);
this.setHardness(0.5F);
this.setResistance(2.5F);
// Affected by gravity - use for: sand variants, dust blocks
```

#### Stone/Sandstone Materials:
```java
super(Material.ROCK);
this.setSoundType(SoundType.STONE);
this.setHardness(0.8F); // Sandstone hardness
this.setResistance(4.0F); // Sandstone resistance
// Use for: stone, sandstone, cobblestone variants
```

#### Wood/Plank Materials:
```java
super(Material.WOOD);
this.setSoundType(SoundType.WOOD);
this.setHardness(2.0F);
this.setResistance(3.0F);
// Flammable - use for: plank variants, wood blocks
```

#### Terracotta/Clay Materials (1.12.2):
```java
super(Material.CLAY); // Called "clay" but is terracotta
this.setSoundType(SoundType.STONE);
this.setHardness(1.25F);
this.setResistance(21.0F);
// Use for: terracotta/hardened clay variants
```

#### Leaf Materials:
```java
super(Material.LEAVES);
this.setSoundType(SoundType.PLANT);
this.setHardness(0.2F);
this.setResistance(1.0F);
this.setLightOpacity(1); // Partial transparency
// Use for: leaf variants, plant decorations
```

#### Glowstone Materials:
```java
super(Material.GLASS);
this.setSoundType(SoundType.GLASS);
this.setHardness(0.3F);
this.setResistance(1.5F);
this.setLightLevel(1.0F); // CRITICAL: Emits light
// Use for: glowstone variants, light-emitting blocks
```

### 1.12.2 Color Variants Available:

#### Terracotta Colors (Hardened Clay in 1.12.2):
**All 16 dye colors exist as stained clay/terracotta:**
- **White Stained Clay** (`stained_hardened_clay:0`)
- **Orange Stained Clay** (`stained_hardened_clay:1`)
- **Magenta Stained Clay** (`stained_hardened_clay:2`)
- **Light Blue Stained Clay** (`stained_hardened_clay:3`)
- **Yellow Stained Clay** (`stained_hardened_clay:4`)
- **Lime Stained Clay** (`stained_hardened_clay:5`)
- **Pink Stained Clay** (`stained_hardened_clay:6`)
- **Gray Stained Clay** (`stained_hardened_clay:7`)
- **Light Gray Stained Clay** (`stained_hardened_clay:8`)
- **Cyan Stained Clay** (`stained_hardened_clay:9`)
- **Purple Stained Clay** (`stained_hardened_clay:10`)
- **Blue Stained Clay** (`stained_hardened_clay:11`)
- **Brown Stained Clay** (`stained_hardened_clay:12`)
- **Green Stained Clay** (`stained_hardened_clay:13`)
- **Red Stained Clay** (`stained_hardened_clay:14`)
- **Black Stained Clay** (`stained_hardened_clay:15`)

#### Dyes Available in 1.12.2:
**Complete 16-color dye set exists:**
- **Primary Dyes**: Red, Blue, Yellow, White (Bone Meal), Black (Ink Sac)
- **Secondary Dyes**: Orange, Magenta, Light Blue, Lime, Pink, Cyan, Purple, Green
- **Gray Variants**: Gray, Light Gray
- **Natural Sources**: Cocoa Beans (Brown), Lapis Lazuli (Blue)

#### Leaf Block Types in 1.12.2:
**Vanilla Leaf Varieties:**
- **Oak Leaves** (`leaves:0`) - Standard green
- **Spruce Leaves** (`leaves:1`) - Dark green
- **Birch Leaves** (`leaves:2`) - Light green
- **Jungle Leaves** (`leaves:3`) - Vibrant green
- **Acacia Leaves** (`leaves2:0`) - Orange-tinted
- **Dark Oak Leaves** (`leaves2:1`) - Very dark green

### Color Implementation Notes for Block Variants:

#### Creating Colored Terracotta Variants:
```java
// Example for creating colored terracotta block variants
public class ColoredTerracottaBlock extends Block {
    private final EnumDyeColor color;
    
    public ColoredTerracottaBlock(EnumDyeColor dyeColor) {
        super(Material.CLAY);
        this.color = dyeColor;
        this.setUnlocalizedName("colored_terracotta_" + dyeColor.getName());
        this.setRegistryName("colored_terracotta_" + dyeColor.getName());
        // Material properties same as vanilla stained clay
    }
}
```

#### Dye Color Integration:
```java
// Use EnumDyeColor for consistent color handling
for (EnumDyeColor color : EnumDyeColor.values()) {
    // Create variant for each of the 16 dye colors
    // color.getName() returns: "white", "orange", "magenta", etc.
    // color.getMetadata() returns: 0-15 for each color
}
```

#### Leaf Block Variants:
```java
// Example leaf block with transparency
public class CustomLeafBlock extends Block {
    public CustomLeafBlock() {
        super(Material.LEAVES);
        this.setSoundType(SoundType.PLANT);
        this.setHardness(0.2F);
        this.setResistance(1.0F);
        this.setLightOpacity(1); // Allows light to pass through partially
        this.setBlockUnbreakable(false);
        // Optional: Add decay mechanics like vanilla leaves
    }
}
```

### CRITICAL TEXTURE PLANNING FOR COLORED VARIANTS:

#### Terracotta Texture Requirements:
- **Base texture**: 16x16 PNG for each of 16 colors
- **Naming convention**: `terracotta_white.png`, `terracotta_orange.png`, etc.
- **Color matching**: Must match vanilla dye color values
- **Variants per color**: Each color needs stairs/slabs/walls/fences if implementing

#### Leaf Texture Considerations:
- **Transparency**: Leaf textures should use alpha channel
- **Seasonal variants**: Consider different leaf states (healthy, dying, etc.)
- **Biome tinting**: 1.12.2 supports biome-based leaf coloring
- **Overlay textures**: Some leaves use overlay textures for effects

#### Implementation Priority for Colored Variants:
1. **Start with single color** (test one terracotta color first)
2. **Verify color rendering** (ensure colors match vanilla)
3. **Test transparency** (especially for leaf variants)
4. **Scale to all 16 colors** (once single color works)
5. **Add block variants** (stairs/slabs/etc. for each color)

#### Memory Considerations for 16-Color Sets:
- **16 terracotta colors × 5 variants = 80 total blocks** per material type
- **Texture memory**: 16 × 64KB = ~1MB per variant type
- **Model files**: 16 × 15+ models = 240+ JSON files per variant type
- **Recommendation**: Implement incrementally, test performance

---

## CRITICAL SETUP TASKS

### 1. Base Infrastructure Setup
- [ ] Create `ModBlockVariants.java` registry class
- [ ] Set up base block reference system for variants
- [ ] Create shared texture/model helper utilities
- [ ] Test with TestSand block variants first

### 2. Required Java Classes to Create

#### For Each Variant Type:
- [ ] **TestSandSlab.java** (extends BlockSlab)
- [ ] **TestSandSlabDouble.java** (extends BlockSlab)
- [ ] **TestSandStairs.java** (extends BlockStairs)
- [ ] **TestSandWall.java** (extends BlockWall)
- [ ] **TestSandFence.java** (extends BlockFence)
- [ ] **TestSandFenceGate.java** (extends BlockFenceGate)

### 3. Model Files Required (Create These Folders/Files)

#### Slab Models:
- [ ] `models/block/testsand_slab.json` (bottom half)
- [ ] `models/block/testsand_slab_top.json` (top half)
- [ ] `models/block/testsand_slab_double.json` (full block)

#### Stair Models:
- [ ] `models/block/testsand_stairs.json` (straight)
- [ ] `models/block/testsand_stairs_inner.json` (inner corner)
- [ ] `models/block/testsand_stairs_outer.json` (outer corner)

#### Wall Models:
- [ ] `models/block/testsand_wall_post.json` (center post)
- [ ] `models/block/testsand_wall_side.json` (side connection)
- [ ] `models/block/testsand_wall_side_tall.json` (tall side)

#### Fence Models:
- [ ] `models/block/testsand_fence_post.json` (center post)
- [ ] `models/block/testsand_fence_side.json` (rail connection)
- [ ] `models/block/testsand_fence_inventory.json` (inventory display)

#### Fence Gate Models:
- [ ] `models/block/testsand_fence_gate_closed.json`
- [ ] `models/block/testsand_fence_gate_open.json`
- [ ] `models/block/testsand_fence_gate_wall_closed.json`
- [ ] `models/block/testsand_fence_gate_wall_open.json`

### 4. Blockstate Files (Complex JSON Files)

#### Simple Blockstates:
- [ ] `blockstates/testsand_slab.json` (half property)
- [ ] `blockstates/testsand_stairs.json` (facing + shape properties)

#### Complex Blockstates:
- [ ] `blockstates/testsand_wall.json` (north/south/east/west/up connections)
- [ ] `blockstates/testsand_fence.json` (north/south/east/west connections)
- [ ] `blockstates/testsand_fence_gate.json` (facing + open + in_wall properties)

### 5. Item Models for Variants
- [ ] `models/item/testsand_slab.json`
- [ ] `models/item/testsand_stairs.json`
- [ ] `models/item/testsand_wall.json`
- [ ] `models/item/testsand_fence.json`
- [ ] `models/item/testsand_fence_gate.json`

### 6. Localization Updates
Add to `en_us.lang`:
- [ ] `tile.testsand_slab.name=Test Sand Slab`
- [ ] `tile.testsand_stairs.name=Test Sand Stairs`
- [ ] `tile.testsand_wall.name=Test Sand Wall`
- [ ] `tile.testsand_fence.name=Test Sand Fence`
- [ ] `tile.testsand_fence_gate.name=Test Sand Fence Gate`

### 7. Recipe Integration
- [ ] Create crafting recipes for each variant
- [ ] Set up stonecutter recipes (if available in 1.12.2)
- [ ] Add to creative tabs appropriately

---

## IMPLEMENTATION NOTES FOR WHEN YOU'RE READY

### Start with Slabs (Easiest):
```java
// TestSandSlab.java structure you'll need:
public class TestSandSlab extends BlockSlab {
    public TestSandSlab() {
        super(Material.SAND);
        // Configure properties
    }
}

// TestSandSlabDouble.java:
public class TestSandSlabDouble extends BlockSlab {
    // Double slab implementation
}
```

### Key Configuration Points:
- **Material Consistency**: All variants should use `Material.SAND`
- **Sound Consistency**: All should use `SoundType.SAND`
- **Hardness/Resistance**: Keep consistent with base block
- **Creative Tab**: Decide where each variant appears

### Testing Checklist:
- [ ] Each variant places correctly
- [ ] Textures render without stretching
- [ ] Collision boxes work properly
- [ ] Connection logic works (fences/walls)
- [ ] Redstone interaction works (fence gates)
- [ ] All variants appear in creative menu
- [ ] Recipes work correctly

---

## REFERENCE FILES TO STUDY WHEN IMPLEMENTING

### Check These Vanilla Examples:
- `BlockStoneSlab` - for slab implementation
- `BlockStairs` - for stair mechanics
- `BlockWall` - for wall connection logic
- `BlockFence` - for fence connections
- `BlockFenceGate` - for gate mechanics

### Test Sand Block Location:
- Base block: `src/main/java/com/dephoegon/delrebase/blocks/TestSandBlock.java`
- Current texture placeholder: `src/main/resources/assets/delrebase/textures/blocks/testsand_texture_goes_here.txt`
- **REMEMBER**: Replace placeholder with actual `testsand.png` (16x16) before starting variants

---

## MEMORY AIDS FOR IMPLEMENTATION

### Common Pitfalls to Avoid:
- **UV Mapping**: Ensure all variants use the same base texture correctly
- **Registration Order**: Register blocks before items
- **Blockstate Complexity**: Wall and fence blockstates are very complex
- **Double Slab Logic**: Double slabs need special handling in 1.12.2

### Performance Considerations:
- **Model Count**: Each variant adds multiple model files
- **Blockstate Complexity**: Complex blockstates can impact loading times
- **Texture Reuse**: All variants should reuse the base testsand texture

---

## WHEN TO TACKLE THIS PROJECT

### Prerequisites (Do These First):
- [ ] Have actual testsand.png texture file ready
- [ ] Confirm base TestSandBlock works correctly
- [ ] Test mod builds and runs without errors
- [ ] Have clear mental energy for complex JSON editing

### Time Estimate:
- **Slabs**: 2-3 hours
- **Stairs**: 3-4 hours  
- **Walls**: 4-5 hours
- **Fences**: 3-4 hours
- **Fence Gates**: 4-6 hours
- **Total Project**: 16-22 hours (spread over multiple sessions)

---

## QUICK START COMMAND FOR WHEN READY
```bash
# Navigate to project directory
cd "D:\coding\minecraft_mods\delrebase-forge_1.12.2"

# Create the variant class files
# Start with: src/main/java/com/dephoegon/delrebase/blocks/variants/
# (Create this folder structure first)
```

**REMEMBER**: This is a significant project. Start small with slabs, test thoroughly, then move to the next variant type. Don't try to implement everything at once!
