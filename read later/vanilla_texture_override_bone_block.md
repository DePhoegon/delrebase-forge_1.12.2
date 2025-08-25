# Vanilla Block Texture Override Tutorial - Bone Block Example

## How to Override Vanilla Block Textures in Minecraft Forge 1.12.2

### Overview
You can override vanilla block textures by placing your custom textures in the minecraft assets folder within your mod's resources. This allows you to change how vanilla blocks look without modifying the base game files.

### Steps to Override Bone Block Texture:

1. **Create the minecraft assets folder structure:**
   ```
   src/main/resources/assets/minecraft/textures/blocks/
   ```

2. **Add your custom bone block textures:**
   - `bone_block_side.png` - for the side faces
   - `bone_block_top.png` - for the top and bottom faces

3. **Texture Requirements:**
   - 16x16 pixels for standard resolution
   - PNG format
   - Follow Minecraft's texture naming conventions exactly

4. **File Locations:**
   ```
   src/main/resources/assets/minecraft/textures/blocks/bone_block_side.png
   src/main/resources/assets/minecraft/textures/blocks/bone_block_top.png
   ```

### Important Notes:

- The texture files must have the exact same names as the vanilla textures
- Place them in `assets/minecraft/` NOT `assets/yourmodid/`
- The override will apply to ALL bone blocks in the game
- This method works for any vanilla block texture
- Resource packs can still override your mod's overrides

### Alternative Approach - Custom Bone Block:
If you want a separate block that looks like bone block but with custom textures:

1. Create a new block class extending Block
2. Use your own mod's assets folder: `assets/yourmodid/textures/blocks/`
3. Create blockstates, models, and item models pointing to your textures
4. Register the block normally

### Testing:
1. Build your mod
2. Launch Minecraft
3. Place a bone block in creative mode
4. Verify the texture has changed

### Troubleshooting:
- Ensure texture file names match exactly (case sensitive)
- Check that PNG files are valid 16x16 images
- Verify folder structure is correct
- Look for texture loading errors in the logs
