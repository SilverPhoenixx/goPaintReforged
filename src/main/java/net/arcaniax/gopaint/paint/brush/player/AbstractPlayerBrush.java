/*
 * goPaint is designed to simplify painting inside of Minecraft.
 * Copyright (C) Arcaniax-Development
 * Copyright (C) Arcaniax team and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package net.arcaniax.gopaint.paint.brush.player;

import com.sk89q.worldedit.world.biome.BiomeType;
import com.sk89q.worldedit.world.biome.BiomeTypes;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;
import net.arcaniax.gopaint.GoPaint;
import net.arcaniax.gopaint.paint.brush.ColorBrush;
import net.arcaniax.gopaint.paint.brush.Brush;
import net.arcaniax.gopaint.paint.placement.Placement;
import net.arcaniax.gopaint.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AbstractPlayerBrush {

    // Placement strategy for the brush
    Placement placement;

    // Boolean flags for various brush settings
    Boolean surfaceEnabled;
    Boolean maskEnabled;
    Boolean enabled;
    Boolean biomeBrush;

    // Integer settings for the brush
    int brushSize;
    int chance;
    int thickness;
    int fractureDistance;
    int angleDistance;
    int falloffStrength;
    int mixingStrength;
    double minAngleHeightDifference;

    // Axis setting for the brush
    String axis;

    // The current brush
    Brush brush;

    // BlockType mask for the brush
    BlockType mask;

    // Lists of BlockTypes and BiomeTypes
    List<BlockType> blockTypes;
    List<BiomeType> biomeTypes;

    /**
     * Constructor for the AbstractPlayerBrush class. Initializes settings based on defaults.
     */
    public AbstractPlayerBrush() {
        // Initialize settings with default values
        this.surfaceEnabled = GoPaint.getSettings().isSurfaceModeEnabledDefault();
        this.maskEnabled = GoPaint.getSettings().isMaskEnabledDefault();
        this.enabled = GoPaint.getSettings().isEnabledDefault();
        this.biomeBrush = GoPaint.getSettings().isBiomesEnabledDefault();
        this.chance = GoPaint.getSettings().getDefaultChance();
        this.thickness = GoPaint.getSettings().getDefaultThickness();
        this.fractureDistance = GoPaint.getSettings().getDefaultFractureDistance();
        this.angleDistance = GoPaint.getSettings().getDefaultAngleDistance();
        this.minAngleHeightDifference = GoPaint.getSettings().getDefaultAngleHeightDifference();
        this.falloffStrength = 50;
        this.mixingStrength = 50;
        this.axis = "y";
        this.brush = GoPaint.getBrushManager().cycleColor((ColorBrush) brush);
        this.brushSize = GoPaint.getSettings().getDefaultSize();
        this.blockTypes = new ArrayList<>();
        this.blockTypes.add(BlockTypes.STONE);
        this.biomeTypes = new ArrayList<>();
        this.biomeTypes.add(BiomeTypes.PLAINS);

        this.mask = BlockTypes.SPONGE;

        this.placement = GoPaint.getPlacementManager().cyclePlacement(placement);
    }

    /**
     * Get the current placement strategy for the brush.
     *
     * @return The placement strategy.
     */
    public Placement getPlacement() {
        return placement;
    }

    /**
     * Get the list of biome types associated with the brush.
     *
     * @return A list of biome types.
     */
    public List<BiomeType> getBiomeTypes() {
        return biomeTypes;
    }

    /**
     * Get the current brush.
     *
     * @return The current brush.
     */
    public Brush getBrush() {
        return brush;
    }

    /**
     * Set the current brush.
     *
     * @param b The brush to set.
     */
    public void setBrush(Brush b) {
        this.brush = b;
    }

    /**
     * Get the falloff strength setting for the brush.
     *
     * @return The falloff strength value.
     */
    public int getFalloffStrength() {
        return falloffStrength;
    }

    /**
     * Add to the falloff strength setting for the brush.
     *
     * @param addedFalloff The amount to add to the falloff strength.
     */
    public void addFalloffStrength(int addedFalloff) {
        this.falloffStrength += addedFalloff;
    }

    /**
     * Remove from the falloff strength setting for the brush.
     *
     * @param removedFalloff The amount to remove from the falloff strength.
     */
    public void removeFalloffStrength(int removedFalloff) {
        this.falloffStrength -= removedFalloff;
    }

    /**
     * Get the mixing strength setting for the brush.
     *
     * @return The mixing strength value.
     */
    public int getMixingStrength() {
        return mixingStrength;
    }

    /**
     * Change the mixing strength setting for the brush.
     *
     * @param change The amount to change the mixing strength by.
     */
    public void changeMixingStrength(int change) {
        this.mixingStrength += change;
    }

    /**
     * Change the minimum angle height difference setting for the brush.
     *
     * @param change The amount to change the minimum angle height difference by.
     */
    public void chanceMinAngleHeight(Double change) {
        this.minAngleHeightDifference += change;
    }

    /**
     * Set the minimum height difference setting for the brush.
     *
     * @param change The new value for the minimum angle height difference.
     */
    public void setMinHeightDifference(Double change) {
        this.minAngleHeightDifference = change;
    }

    /**
     * Get the minimum height difference setting for the brush.
     *
     * @return The minimum angle height difference.
     */
    public Double getMinHeightDifference() {
        return this.minAngleHeightDifference;
    }

    /**
     * Get the angle distance setting for the brush.
     *
     * @return The angle distance value.
     */
    public int getAngleDistance() {
        return this.angleDistance;
    }

    /**
     * Change the angle distance setting for the brush.
     *
     * @param change The amount to change the angle distance by.
     */
    public void changeAngleDistance(int change) {
        this.angleDistance += change;
    }

    /**
     * Get the fracture distance setting for the brush.
     *
     * @return The fracture distance value.
     */
    public int getFractureDistance() {
        return this.fractureDistance;
    }

    /**
     * Change the fracture distance setting for the brush.
     *
     * @param change The amount to change the fracture distance by.
     */
    public void changeFracture(int change) {
        this.fractureDistance += change;
    }

    /**
     * Get the mask setting for the brush.
     *
     * @return The BlockType mask.
     */
    public BlockType getMask() {
        return mask;
    }

    /**
     * Get the list of block types associated with the brush.
     *
     * @return A list of block types.
     */
    public List<BlockType> getBlocks() {
        return blockTypes;
    }

    /**
     * Check if the brush is set to biome mode.
     *
     * @return True if the brush is in biome mode, false otherwise.
     */
    public boolean isBiome() {
        return biomeBrush;
    }

    /**
     * Get the brush size setting.
     *
     * @return The brush size.
     */
    public int getBrushSize() {
        return brushSize;
    }

    /**
     * Check if the brush is enabled.
     *
     * @return True if the brush is enabled, false otherwise.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Change the place chance setting for the brush.
     *
     * @param change The amount to change the place chance by.
     */
    public void changeChance(int change) {
        this.chance += change;
    }

    /**
     * Get the place chance setting for the brush.
     *
     * @return The place chance value.
     */
    public int getChance() {
        return chance;
    }

    /**
     * Check if the mask is enabled for the brush.
     *
     * @return True if the mask is enabled, false otherwise.
     */
    public boolean isMaskEnabled() {
        return maskEnabled;
    }

    /**
     * Check if surface mode is enabled for the brush.
     *
     * @return True if surface mode is enabled, false otherwise.
     */
    public boolean isSurfaceModeEnabled() {
        return surfaceEnabled;
    }

    /**
     * Get the thickness setting for the brush.
     *
     * @return The thickness value.
     */
    public int getThickness() {
        return thickness;
    }

    /**
     * Change the thickness setting for the brush.
     *
     * @param change The amount to change the thickness by.
     */
    public void changeThickness(int change) {
        thickness += change;
    }

    /**
     * Get the axis setting for the brush.
     *
     * @return The axis value.
     */
    public String getAxis() {
        return axis;
    }

    /**
     * Change the axis setting for the brush.
     *
     * @param change The new value for the axis.
     */
    public void changeAxis(String change) {
        this.axis = change;
    }

    /**
     * Export the current brush settings to an ItemStack.
     *
     * @param itemStack The ItemStack to which the settings will be exported.
     * @return An ItemStack containing the brush settings.
     */
    public ItemStack export(ItemStack itemStack) {
        ItemBuilder itemBuilder = new ItemBuilder(itemStack)
                .setName("§3GoPaint §7>§b Exported Brush §7>§b " + this.brush.getName())
                .addGlow();

        // Add current settings
        itemBuilder
                .setList(
                        "Brush Size: " + this.brushSize,
                        "Place Chance: " + this.chance,
                        "Thickness: " + this.thickness,
                        "Axis: " + this.axis,
                        "FractureDistance: " + this.fractureDistance,
                        "AngleDistance: " + this.angleDistance,
                        "AngleHeightDistance: " + this.minAngleHeightDifference,
                        "Mixing: " + this.mixingStrength,
                        "Falloff: " + this.falloffStrength,
                        "Mask Enabled: " + this.maskEnabled,
                        "Surface Mode: " + this.surfaceEnabled,
                        "Biome Mode: " + this.biomeBrush,
                        "Placement: " + this.placement.getName(),
                        "Mask: " + getMaterialFromBlockType(this.mask).getKey()
                );

        // Add all blocks
        StringBuilder blocks = new StringBuilder();
        blocks.append("Blocks: ");
        if (blockTypes.isEmpty()) {
            blocks.append("None");
        } else {
            for (BlockType blockType : blockTypes) {
                blocks.append(" ").append(blockType.getId());
            }
        }
        itemBuilder.addList(blocks.toString());

        // Add all biomes
        StringBuilder biomes = new StringBuilder();
        biomes.append("Biomes: ");
        if (biomeTypes.isEmpty()) {
            biomes.append("None");
        } else {
            for (BiomeType biomeType : biomeTypes) {
                biomes.append(" ").append(biomeType.getId());
            }
        }
        itemBuilder.addList(biomes.toString());

        return itemBuilder.create();
    }

    /**
     * Get the BlockType associated with a Material.
     *
     * @param material The Material for which to retrieve the BlockType.
     * @return The BlockType corresponding to the given Material.
     */
    public BlockType getBlockTypeFromMaterial(Material material) {
        return BlockType.REGISTRY.get(material.getKey().toString());
    }

    /**
     * Get the Material associated with a BlockType.
     *
     * @param blockType The BlockType for which to retrieve the Material.
     * @return The Material corresponding to the given BlockType.
     */
    public Material getMaterialFromBlockType(BlockType blockType) {
        return Material.getMaterial(blockType.getId().replace(blockType.getNamespace() + ":", "").toUpperCase());
    }

}
