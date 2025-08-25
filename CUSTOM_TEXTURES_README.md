# Custom Textures Guide for Minecraft 1.12.2 Forge Mods

## Overview
This guide covers everything you need to know about creating, implementing, and using custom textures in Minecraft 1.12.2 Forge mods. It includes technical specifications, file structure requirements, and best practices.

## Table of Contents
1. [Texture Specifications](#texture-specifications)
2. [File Structure](#file-structure)
3. [Entity Texture Details](#entity-texture-details)
4. [1.12.2 Model Implementation](#112-model-implementation)
5. [Datapack Porting Guide](#datapack-porting-guide)
6. [Texture Types](#texture-types)
7. [Implementation Steps](#implementation-steps)
8. [JSON Model Files](#json-model-files)
9. [Blockstate Files](#blockstate-files)
10. [Animation Support](#animation-support)
11. [Troubleshooting](#troubleshooting)
12. [Best Practices](#best-practices)

## Texture Specifications

### Basic Requirements
- **Format**: PNG only
- **Base Resolution**: 16x16 pixels (blocks/items), variable for entities
- **Color Depth**: 32-bit RGBA (supports transparency)
- **File Naming**: Lowercase letters, numbers, underscores only
- **No Spaces**: Use underscores instead of spaces

### Resolution Constraints by Type

#### Block Textures
- **16x16**: Standard resolution, best performance
- **32x32**: 2x resolution, requires resource pack or OptiFine
- **64x64**: 4x resolution, requires resource pack or OptiFine
- **128x128**: 8x resolution, may cause performance issues
- **256x256+**: Possible but NOT recommended for blocks
- **Maximum**: Technically unlimited, but 512x512+ will cause severe lag

#### Item Textures
- **16x16**: Standard resolution, matches vanilla items
- **32x32**: 2x resolution, requires resource pack
- **64x64**: 4x resolution, noticeable in inventory
- **128x128**: 8x resolution, may appear too detailed for items
- **Maximum**: Same as blocks, but items benefit less from high resolution

#### Entity Textures (DETAILED FOR CHOCOBOTS)
- **64x32**: Standard mob size (pig, cow, sheep)
- **64x64**: Medium mobs (horse, llama, wolf)
- **128x64**: Large mobs (horse variants, some modded creatures)
- **128x128**: Extra large mobs (dragons, large custom entities like chocobots)
- **256x128**: Massive entities (ender dragon size)
- **512x256**: Extremely large entities (massive bosses)
- **1024x512**: Maximum practical size for entities
- **Custom sizes**: Any width/height combination is possible

### Supported Resolutions
- **16x16**: Standard Minecraft resolution
- **32x32**: 2x resolution (requires resource pack)
- **64x64**: 4x resolution (requires resource pack)
- **128x128**: 8x resolution (requires resource pack)
- **256x256**: 16x resolution (heavy performance impact)
- **512x512**: 32x resolution (severe performance impact)
- **Higher**: Possible but may impact performance drastically

### Memory Usage by Resolution
- **16x16**: ~1KB per texture
- **32x32**: ~4KB per texture
- **64x64**: ~16KB per texture
- **128x128**: ~64KB per texture (your chocobot size)
- **256x256**: ~256KB per texture
- **512x512**: ~1MB per texture
- **1024x1024**: ~4MB per texture

### Transparency Support
- **Alpha Channel**: Fully supported
- **Translucency**: Partial transparency works
- **Cutout**: Binary transparency (0% or 100%)
- **Blend**: Smooth transparency gradients

## File Structure

### Custom Mod Textures
```
src/main/resources/assets/[modid]/textures/
├── blocks/           # Block textures (16x16 standard)
│   ├── blockname.png
│   ├── blockname_top.png
│   └── blockname_side.png
├── items/            # Item textures (16x16 standard)
│   ├── itemname.png
│   └── toolname.png
├── entity/           # Entity textures (variable sizes)
│   ├── chocobot/     # Entity-specific folder
│   │   ├── chocobot_blue.png      # 128x128 for your chocobots
│   │   ├── chocobot_red.png       # 128x128
│   │   └── chocobot_yellow.png    # 128x128
│   ├── simpleentity.png           # 64x32 for simple mobs
│   └── largeentity.png            # 128x64 for medium-large mobs
├── gui/              # GUI textures (256x256 typical)
│   └── guiname.png
└── effects/          # Particle textures (8x8 to 32x32)
    └── effectname.png
```

### Vanilla Texture Overrides
```
src/main/resources/assets/minecraft/textures/
├── blocks/           # Override vanilla block textures (16x16)
│   ├── stone.png
│   └── bone_block_side.png
├── items/            # Override vanilla item textures (16x16)
│   ├── diamond.png
│   └── iron_sword.png
└── entity/           # Override vanilla entity textures (variable)
    ├── cow/cow.png              # 64x32 (vanilla cow size)
    ├── horse/horse_*.png        # 64x64 (vanilla horse size)
    └── dragon/dragon.png        # 256x256 (ender dragon size)
```

## Entity Texture Details (For Chocobots and Custom Mobs)

### Entity Texture Specifications

#### Standard Entity Sizes
- **Small Mobs** (chicken, rabbit): 32x32 or 64x32
- **Medium Mobs** (pig, cow, sheep): 64x32
- **Large Mobs** (horse, llama): 64x64
- **Boss Mobs** (wither): 64x64 to 128x128
- **Massive Entities** (ender dragon): 256x256
- **Custom Large Entities** (chocobots): 128x128 recommended

#### Chocobot-Specific Considerations
- **Resolution**: 128x128 is excellent for detailed custom mobs
- **UV Mapping**: Must match your chocobot model's UV layout
- **Performance**: 128x128 is acceptable for a few entities, avoid for common spawns
- **Variants**: Each color variant needs separate texture file
- **Animation**: Can include animated parts (eyes, accessories)

### UV Mapping for 128x128 Entities

#### Typical 128x128 UV Layout
```
┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐
│Head │Head │Head │Head │     │     │     │     │ 0-16
│Front│Right│Back │Left │     │     │     │     │
├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤
│Head │Head │Body │Body │Body │Body │     │     │ 16-32
│Top  │Bot  │Front│Right│Back │Left │     │     │
├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤
│Body │Body │Leg1 │Leg1 │Leg1 │Leg1 │Leg2 │Leg2 │ 32-48
│Top  │Bot  │Front│Right│Back │Left │Front│Right│
├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤
│Leg2 │Leg2 │Leg3 │Leg3 │Leg3 │Leg3 │Leg4 │Leg4 │ 48-64
│Back │Left │Front│Right│Back │Left │Front│Right│
├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤
│Leg4 │Leg4 │Wing1│Wing1│Wing1│Wing1│Wing2│Wing2│ 64-80
│Back │Left │Front│Right│Back │Left │Front│Right│
├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤
│Wing2│Wing2│Tail │Tail │Tail │Tail │     │     │ 80-96
│Back │Left │Front│Right│Back │Left │     │     │
├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤
│Tail │Tail │Acc. │Acc. │Acc. │Acc. │     │     │ 96-112
│Top  │Bot  │Part1│Part2│Part3│Part4│     │     │
├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤
│     │     │     │     │     │     │     │     │ 112-128
│     │     │     │     │     │     │     │     │
└─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘
0    16    32    48    64    80    96   112  128
```

### Entity Model Integration

#### Registering Entity Textures
```java
// In your entity class
public class EntityChocobot extends EntityCreature {
    private static final ResourceLocation TEXTURE_BLUE = 
        new ResourceLocation("delrebase:textures/entity/chocobot/chocobot_blue.png");
    private static final ResourceLocation TEXTURE_RED = 
        new ResourceLocation("delrebase:textures/entity/chocobot/chocobot_red.png");
    
    @Override
    public ResourceLocation getEntityTexture() {
        switch(this.getChocobotType()) {
            case BLUE: return TEXTURE_BLUE;
            case RED: return TEXTURE_RED;
            default: return TEXTURE_BLUE;
        }
    }
}
```

#### Render Class Setup
```java
// In your render class
public class RenderChocobot extends RenderLiving<EntityChocobot> {
    public RenderChocobot(RenderManager manager) {
        super(manager, new ModelChocobot(), 0.5F);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityChocobot entity) {
        return entity.getEntityTexture();
    }
}
```

## 1.12.2 Model Implementation Requirements

### Essential Components for Custom Entities

#### 1. Entity Class Structure
```java
// Base entity class extending EntityCreature or EntityAnimal
public class EntityChocobot extends EntityAnimal {
    // Data parameter for variant types
    private static final DataParameter<Integer> VARIANT = 
        EntityDataManager.createKey(EntityChocobot.class, DataSerializers.VARINT);
    
    public EntityChocobot(World worldIn) {
        super(worldIn);
        this.setSize(1.4F, 1.6F); // Width, Height
        // Set AI tasks, attributes, etc.
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(VARIANT, 0);
    }
    
    // Texture selection based on variant
    public ResourceLocation getTexture() {
        int variant = this.dataManager.get(VARIANT);
        switch(variant) {
            case 0: return new ResourceLocation("delrebase:textures/entity/chocobot/chocobot_blue.png");
            case 1: return new ResourceLocation("delrebase:textures/entity/chocobot/chocobot_red.png");
            case 2: return new ResourceLocation("delrebase:textures/entity/chocobot/chocobot_yellow.png");
            default: return new ResourceLocation("delrebase:textures/entity/chocobot/chocobot_blue.png");
        }
    }
}
```

#### 2. Model Class (128x128 UV Layout)
```java
// Custom model class extending ModelBase
public class ModelChocobot extends ModelBase {
    // Model parts - each part maps to UV coordinates
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer neck;
    public ModelRenderer leg1, leg2, leg3, leg4;
    public ModelRenderer wing1, wing2;
    public ModelRenderer tail;
    
    public ModelChocobot() {
        this.textureWidth = 128;  // CRITICAL: Must match texture width
        this.textureHeight = 128; // CRITICAL: Must match texture height
        
        // Head (maps to UV coordinates 0,0 to 32,16)
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-4F, -8F, -4F, 8, 8, 8, 0F);
        this.head.setRotationPoint(0F, 6F, -8F);
        
        // Body (maps to UV coordinates 32,16 to 80,32)
        this.body = new ModelRenderer(this, 32, 16);
        this.body.addBox(-6F, -10F, -7F, 12, 18, 10, 0F);
        this.body.setRotationPoint(0F, 11F, 2F);
        
        // Legs (each leg maps to specific UV coordinates)
        this.leg1 = new ModelRenderer(this, 0, 32);
        this.leg1.addBox(-2F, 0F, -2F, 4, 12, 4, 0F);
        this.leg1.setRotationPoint(-4F, 12F, 7F);
        
        // Wings (for chocobot-specific features)
        this.wing1 = new ModelRenderer(this, 64, 64);
        this.wing1.addBox(0F, 0F, 0F, 1, 8, 12, 0F);
        this.wing1.setRotationPoint(6F, 1F, -4F);
        
        // Continue for all body parts...
    }
    
    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, 
                      float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, 
                              netHeadYaw, headPitch, scale, entityIn);
        
        this.head.render(scale);
        this.body.render(scale);
        this.leg1.render(scale);
        this.leg2.render(scale);
        this.leg3.render(scale);
        this.leg4.render(scale);
        this.wing1.render(scale);
        this.wing2.render(scale);
        this.tail.render(scale);
    }
    
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks,
                                 float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        // Animation logic - rotate parts based on movement
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        
        // Walking animation for legs
        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        
        // Wing flapping animation
        this.wing1.rotateAngleZ = MathHelper.cos(ageInTicks * 0.3F) * 0.3F;
        this.wing2.rotateAngleZ = -MathHelper.cos(ageInTicks * 0.3F) * 0.3F;
    }
}
```

#### 3. Render Class
```java
public class RenderChocobot extends RenderLiving<EntityChocobot> {
    
    public RenderChocobot(RenderManager manager) {
        super(manager, new ModelChocobot(), 0.7F); // Shadow size
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityChocobot entity) {
        return entity.getTexture(); // Uses the variant-based texture selection
    }
    
    @Override
    protected void preRenderCallback(EntityChocobot entitylivingbaseIn, float partialTickTime) {
        // Scale the entity if needed
        GlStateManager.scale(1.0F, 1.0F, 1.0F);
    }
}
```

## Datapack Porting Guide (128x128 Models)

### What Datapacks CANNOT Do in 1.12.2
- **Add new entities**: Entities require Java code, not possible with datapacks alone
- **Custom models**: 3D models must be coded in Java ModelBase classes
- **Texture binding**: Render classes must be implemented in Java
- **Animation logic**: All animations require Java code

### What You Need for Full Port

#### From Datapack to 1.12.2 Forge Mod:
1. **Java Programming Required**: No way around this for entities
2. **Model Conversion**: Convert datapack model to ModelBase Java class
3. **Texture Adaptation**: May need UV remapping for 1.12.2 format
4. **Animation Reimplementation**: Rewrite all animations in Java
5. **Behavior Programming**: Entity AI, breeding, taming all in Java

#### Detailed Conversion Process:

##### Step 1: Extract Datapack Assets
```bash
# From your datapack, extract:
- Model JSON files (for UV reference)
- 128x128 texture files
- Animation data (if any)
- Entity behavior data
```

##### Step 2: Convert Model Format
```java
// Datapack uses JSON models, 1.12.2 uses Java ModelBase
// You must manually convert each model part:

// Datapack JSON (example):
// "head": {"from": [0, 0, 0], "to": [8, 8, 8], "uv": [0, 0]}

// Becomes Java ModelBase:
this.head = new ModelRenderer(this, 0, 0); // UV coordinates
this.head.addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // Size and position
```

##### Step 3: UV Mapping Verification
```java
// CRITICAL: Ensure UV coordinates match your 128x128 texture
// Each ModelRenderer constructor takes (model, textureOffsetX, textureOffsetY)
// These MUST align with your texture layout

// For 128x128 texture, you have more UV space:
this.textureWidth = 128;  // Must match texture width exactly
this.textureHeight = 128; // Must match texture height exactly

// Example UV layout verification:
// If texture has head at pixels (0,0) to (32,16):
this.head = new ModelRenderer(this, 0, 0);
this.head.addBox(-4F, -8F, -4F, 8, 8, 8, 0F);
```

##### Step 4: Animation Conversion
```java
// Datapack animations are JSON-based, 1.12.2 requires Java code
// Convert each animation keyframe to rotation calculations:

@Override
public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks,
                             float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    // Walking cycle - convert from datapack keyframes
    float walkCycle = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    this.leg1.rotateAngleX = walkCycle;
    this.leg3.rotateAngleX = walkCycle;
    this.leg2.rotateAngleX = -walkCycle;
    this.leg4.rotateAngleX = -walkCycle;
    
    // Idle animations - breathing, wing movement
    float idleCycle = MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
    this.body.rotateAngleX = idleCycle;
    
    // Wing flapping
    this.wing1.rotateAngleZ = MathHelper.cos(ageInTicks * 0.3F) * 0.3F;
    this.wing2.rotateAngleZ = -this.wing1.rotateAngleZ;
}
```

### Required Development Environment

#### Essential Tools:
1. **Java 8 JDK**: Required for 1.12.2 development
2. **IntelliJ IDEA or Eclipse**: IDE with Minecraft development plugins
3. **Minecraft Development Kit (MDK)**: Forge MDK for 1.12.2
4. **Gradle 4.10**: Specific version for 1.12.2 compatibility
5. **MCP mappings**: For obfuscated Minecraft code access

#### Development Setup:
```bash
# Download Forge MDK 1.12.2-14.23.5.2768
# Extract to your project directory
# Import into IDE as Gradle project
# Run './gradlew setupDecompWorkspace' (first time only)
# Run './gradlew build' to compile
```

### Performance Considerations for 128x128

#### Memory Impact:
- **128x128 texture**: ~64KB per variant
- **Multiple variants**: 3 variants = ~192KB total
- **Loaded entities**: Memory usage scales with spawned entities
- **Render distance**: High-res textures more noticeable at distance

#### Optimization Requirements:
```java
// Implement LOD (Level of Detail) system for distant entities
@Override
protected void preRenderCallback(EntityChocobot entity, float partialTickTime) {
    // Scale down distant entities to reduce texture detail impact
    double distance = this.renderManager.renderViewEntity.getDistance(entity);
    if (distance > 50.0D) {
        GlStateManager.scale(0.8F, 0.8F, 0.8F);
    }
}

// Limit entity spawn rates to prevent performance issues
public static void limitSpawnRates() {
    // In your entity spawn configuration:
    // Use lower spawn weights for high-detail entities
    // Consider despawning mechanics for distant entities
}
```

### Testing and Validation

#### Required Tests:
1. **Texture Loading**: Verify all variants load correctly
2. **UV Mapping**: Ensure no texture stretching or misalignment
3. **Animation Smoothness**: Check all movement animations
4. **Performance**: Monitor FPS with multiple entities spawned
5. **Memory Usage**: Check for memory leaks with F3 debug screen

#### Debug Commands:
```java
// Add debug commands for testing
public class ChocobotDebugCommand extends CommandBase {
    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (args[0].equals("spawn")) {
            // Spawn test chocobot with specific variant
            EntityChocobot chocobot = new EntityChocobot(sender.getEntityWorld());
            chocobot.setVariant(Integer.parseInt(args[1]));
            // Set position and spawn
        }
    }
}
```

## Troubleshooting

### Common Issues

#### Texture Not Loading
- **Check file path**: Ensure correct directory structure
- **Verify naming**: No spaces, capitals, or special characters
- **JSON syntax**: Validate JSON files for syntax errors
- **Registry name**: Must match texture path

#### Purple/Black Texture
- **Missing texture file**: Texture file doesn't exist at specified path
- **Wrong format**: File must be PNG
- **Corrupted file**: Re-save the PNG file

#### Texture Appears Stretched
- **Wrong resolution**: Check if texture is 16x16 pixels
- **UV mapping**: Model UV coordinates may be incorrect
- **Resource pack**: High-res textures need matching resource pack

### Debug Steps
1. **Check console logs**: Look for texture loading errors
2. **Verify file paths**: Ensure all paths match exactly
3. **Test with simple texture**: Use solid color PNG for testing
4. **Validate JSON**: Use JSON validator tools
5. **Compare with working examples**: Check vanilla block implementations

## Best Practices

### Texture Creation
- **Consistent style**: Match Minecraft's pixel art aesthetic
- **Proper contrast**: Ensure readability at small sizes
- **Color palette**: Use colors that fit Minecraft's palette
- **Pixel-perfect**: Avoid anti-aliasing for crisp pixels

### File Organization
- **Descriptive names**: Use clear, descriptive filenames
- **Group by type**: Organize textures by blocks/items/entities
- **Version control**: Keep backup copies of texture files
- **Source files**: Save working files (.psd, .xcf) separately

### Performance
- **Optimize file size**: Use PNG optimization tools
- **Limit animations**: Animated textures use more memory
- **Standard resolution**: Stick to 16x16 for best performance
- **Batch operations**: Process multiple textures efficiently

### Compatibility
- **Test with resource packs**: Ensure compatibility
- **Document requirements**: Note any special texture needs
- **Fallback textures**: Provide default textures for missing files
- **Cross-platform**: Test on different operating systems

## Texture Tools

### Recommended Software
- **Free**: GIMP, Paint.NET, Piskel (for pixel art)
- **Paid**: Photoshop, Aseprite (excellent for pixel art)
- **Online**: Pixlr, Canva (basic editing)

### Useful Utilities
- **MCreator**: Visual mod creation with texture support
- **Blockbench**: 3D model and texture editor for Minecraft
- **PNGGauntlet**: PNG optimization tool
- **JSON validators**: Online JSON syntax checkers

## Version-Specific Notes for 1.12.2

### Key Differences from Later Versions
- **No data generation**: Manual JSON file creation required
- **Legacy format**: Some JSON structures differ from 1.13+
- **Resource location**: Uses `ResourceLocation` class
- **Model loading**: Uses `ModelLoader` for item model registration

### Gradle 4.10 Considerations
- **Build path**: Ensure textures are in correct source directory
- **Resource processing**: Textures copied during build process
- **IDE integration**: Configure IDE to recognize resource files

This guide should provide everything needed to successfully implement custom textures in your Minecraft 1.12.2 Forge mod. Remember to test thoroughly and keep backups of your texture files!
