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
package net.arcaniax.gopaint.paint.player;

import com.sk89q.worldedit.world.biome.BiomeType;
import com.sk89q.worldedit.world.biome.BiomeTypes;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;
import net.arcaniax.gopaint.GoPaint;
import net.arcaniax.gopaint.paint.brush.ColorBrush;
import net.arcaniax.gopaint.paint.brush.Brush;
import net.arcaniax.gopaint.paint.brush.placement.Placement;
import net.arcaniax.gopaint.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AbstractPlayerBrush {

    Placement placement;

    Boolean surfaceEnabled;
    Boolean maskEnabled;
    Boolean enabled;
    Boolean biomeBrush;

    int brushSize;
    int chance;
    int thickness;
    int fractureDistance;
    int angleDistance;
    int falloffStrength;
    int mixingStrength;
    double minAngleHeightDifference;

    String axis;

    Brush brush;
    BlockType mask;
    List<BlockType> blockTypes;
    List<BiomeType> biomeTypes;


    public AbstractPlayerBrush() {
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

    public Placement getPlacement() {
        return placement;
    }

    public List<BiomeType> getBiomeTypes() {
        return biomeTypes;
    }

    public Brush getBrush() {
        return brush;
    }

    public void setBrush(Brush b) {
        this.brush = b;
    }

    public int getFalloffStrength() {
        return falloffStrength;
    }

    public void addFalloffStrength(int addedFalloff) {
        this.falloffStrength += addedFalloff;
    }

    public void removeFalloffStrength(int removedFalloff) {
        this.falloffStrength -= removedFalloff;
    }

    public int getMixingStrength() {
        return mixingStrength;
    }

    public void changeMixingStrength(int change) {
        this.mixingStrength += change;
    }

    public void chanceMinAngleHeight(Double change) {
        this.minAngleHeightDifference += change;
    }

    public void setMinHeightDifference(Double change) {
        this.minAngleHeightDifference = change;
    }

    public Double getMinHeightDifference() {
        return this.minAngleHeightDifference;
    }

    public int getAngleDistance() {
        return this.angleDistance;
    }

    public void changeAngleDistance(int change) {
        this.angleDistance += change;
    }

    public int getFractureDistance() {
        return this.fractureDistance;
    }

    public void changeFracture(int change) {
        this.fractureDistance += change;
    }

    public BlockType getMask() {
        return mask;
    }

    public List<BlockType> getBlocks() {
        return blockTypes;
    }

    public boolean isBiome() {
        return biomeBrush;
    }

    public int getBrushSize() {
        return brushSize;
    }


    public boolean isEnabled() {
        return enabled;
    }


    public void changeChance(int change) {
        this.chance += change;
    }

    public int getChance() {
        return chance;
    }


    public boolean isMaskEnabled() {
        return maskEnabled;
    }


    public boolean isSurfaceModeEnabled() {
        return surfaceEnabled;
    }

    public int getThickness() {
        return thickness;
    }

    public void changeThickness(int change) {
        thickness += change;
    }


    public String getAxis() {
        return axis;
    }

    public void changeAxis(String change) {
        this.axis = change;
    }


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

    public BlockType getBlockTypeFromMaterial(Material material) {
        return BlockType.REGISTRY.get(material.getKey().toString());
    }

    public Material getMaterialFromBlockType(BlockType blockType) {
        return Material.getMaterial(blockType.getId().replace(blockType.getNamespace() + ":", "").toUpperCase());
    }

}
