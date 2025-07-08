# Test Sand Block Implementation Guide - NOT A TEXTURE OVERRIDE

## Important Distinction:
**The test sand block is a CUSTOM BLOCK, not a texture override.** It's a completely new block added to the game with its own registry name, properties, and texture.

## What We Created - Test Sand Block:

### 1. Custom Block Class (`TestSandBlock.java`)
- Extends `Block` class
- Uses `Material.SAND` for proper sand-like behavior
- Registry name: `delrebase:testsand`
- Localized name: "Test Sand Block"
- Found in Building Blocks creative tab
- Sand sound effects and appropriate hardness/resistance

### 2. Block Registration (`ModBlocks.java`)
- Event-driven registration system using `@Mod.EventBusSubscriber`
- Registers both block and its item form
- Uses Forge's registry events for proper initialization

### 3. Asset Files Required:
- **Blockstate**: `assets/delrebase/blockstates/testsand.json`
- **Block Model**: `assets/delrebase/models/block/testsand.json`
- **Item Model**: `assets/delrebase/models/item/testsand.json`
- **Texture**: `assets/delrebase/textures/blocks/testsand.png` (16x16 PNG)
- **Localization**: `assets/delrebase/lang/en_us.lang`

## Material Types and Properties:

### Sand-Like Materials (Current Test Sand Block):
```java
super(Material.SAND);
this.setSoundType(SoundType.SAND);
this.setHardness(0.5F);
this.setResistance(2.5F);
```
- **Use Cases**: Sand variants, dust blocks, loose materials
- **Behavior**: Affected by gravity, soft mining
- **Sound**: Sandy/gritty sounds

### Stone-Like Materials:
```java
super(Material.ROCK);
this.setSoundType(SoundType.STONE);
this.setHardness(1.5F);
this.setResistance(6.0F);
```
- **Use Cases**: Stone variants, ore blocks, hard building materials
- **Behavior**: Solid, durable, requires pickaxe
- **Sound**: Stone breaking/placing sounds
- **Alternative**: `Material.IRON` for metal blocks

### Wood-Like Materials:
```java
super(Material.WOOD);
this.setSoundType(SoundType.WOOD);
this.setHardness(2.0F);
this.setResistance(3.0F);
```
- **Use Cases**: Wood variants, planks, logs, organic building materials
- **Behavior**: Flammable, medium durability, axe effective
- **Sound**: Wood breaking/placing sounds
- **Fire**: Can be set on fire and burn

### Leaf-Like Materials:
```java
super(Material.LEAVES);
this.setSoundType(SoundType.PLANT);
this.setHardness(0.2F);
this.setResistance(1.0F);
```
- **Use Cases**: Decorative foliage, plant blocks, organic decorations
- **Behavior**: Very soft, shears effective, may decay
- **Sound**: Soft plant sounds
- **Special**: Often transparent/translucent

## Complete Block Creation Process:

### Step 1: Create Block Class
```java
public class YourBlock extends Block {
    public YourBlock() {
        super(Material.APPROPRIATE_MATERIAL);
        this.setUnlocalizedName("blockname");
        this.setRegistryName("blockname");
        this.setCreativeTab(CreativeTabs.APPROPRIATE_TAB);
        this.setSoundType(SoundType.APPROPRIATE_SOUND);
        this.setHardness(value);
        this.setResistance(value);
    }
}
```

### Step 2: Register in ModBlocks
```java
public static final Block YOUR_BLOCK = new YourBlock();

@SubscribeEvent
public static void registerBlocks(RegistryEvent.Register<Block> event) {
    event.getRegistry().registerAll(YOUR_BLOCK);
}

@SubscribeEvent
public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
    event.getRegistry().registerAll(
        new ItemBlock(YOUR_BLOCK).setRegistryName(YOUR_BLOCK.getRegistryName())
    );
}
```

### Step 3: Create Asset Files
1. Blockstate JSON (defines model variants)
2. Block model JSON (defines texture mapping)
3. Item model JSON (usually inherits from block model)
4. Texture PNG file (16x16 pixels)
5. Localization entry

## Block Variants Implementation Guide:

### STAIRS
1. **Class**: Extend `BlockStairs`
2. **Constructor**: Pass base block state
3. **Models**: Need 4 models (straight, inner_left, inner_right, outer_left, outer_right)
4. **Blockstate**: Complex with facing and shape variants
5. **Item**: `ItemBlock` registration

### SLABS
1. **Classes**: Create both half slab and double slab classes
2. **Extend**: `BlockSlab`
3. **Models**: bottom, top, double variants
4. **Blockstate**: half (upper/lower) variants
5. **Special**: Double slab uses different material/model

### WALLS
1. **Class**: Extend `BlockWall`
2. **Models**: post, side_low, side_tall variants
3. **Blockstate**: Very complex with north, south, east, west, up connections
4. **Collision**: Automatically handled by BlockWall

### FENCES
1. **Class**: Extend `BlockFence`
2. **Models**: post, side variants
3. **Blockstate**: north, south, east, west connection variants
4. **Connection**: Automatically connects to other fences

### FENCE GATES
1. **Class**: Extend `BlockFenceGate`
2. **Models**: closed, open, wall variants
3. **Blockstate**: facing, open, in_wall variants
4. **Interaction**: Right-click to open/close

## Texture Requirements for Variants:

### Base Block:
- `blockname.png` (16x16)

### Stairs:
- Uses base block texture, models handle the arrangement

### Slabs:
- Uses base block texture, models handle top/bottom positioning

### Walls:
- `blockname.png` for the wall texture
- Models arrange it for post and side connections

### Fences:
- `blockname.png` for fence texture
- Models create post and rail arrangements

## Model Complexity by Type:
1. **Basic Block**: 1 model file
2. **Stairs**: 5+ model files (straight, inner corners, outer corners)
3. **Slabs**: 3 model files (bottom, top, double)
4. **Walls**: 3+ model files (post, side_low, side_tall)
5. **Fences**: 2+ model files (post, side)

## Registration Pattern for Full Set:
```java
// In ModBlocks.java
public static final Block MATERIAL_BLOCK = new MaterialBlock();
public static final Block MATERIAL_STAIRS = new MaterialStairs();
public static final Block MATERIAL_SLAB_HALF = new MaterialSlabHalf();
public static final Block MATERIAL_SLAB_DOUBLE = new MaterialSlabDouble();
public static final Block MATERIAL_WALL = new MaterialWall();
public static final Block MATERIAL_FENCE = new MaterialFence();
public static final Block MATERIAL_FENCE_GATE = new MaterialFenceGate();
```

## Key Differences from Texture Override:
- **Custom blocks**: New registry entries with mod namespace
- **Texture override**: Replaces existing vanilla textures in minecraft namespace
- **Custom blocks**: Can have unique properties and behaviors
- **Texture override**: Same properties as vanilla block, just different appearance
- **Custom blocks**: Require full asset setup (models, blockstates, etc.)
- **Texture override**: Only requires texture replacement

## Testing the Test Sand Block:
1. Build the mod with `./gradlew build`
2. Launch in development environment
3. Open creative mode
4. Look in Building Blocks tab for "Test Sand Block"
5. Place and verify it behaves like sand (sound, break time, etc.)
6. Check console logs for registry confirmation
