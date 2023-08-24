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
import com.sk89q.worldedit.world.block.BlockType;
import net.arcaniax.gopaint.GoPaint;
import net.arcaniax.gopaint.inventories.MenuInventory;
import net.arcaniax.gopaint.paint.brush.BiomeBrush;
import net.arcaniax.gopaint.paint.brush.ColorBrush;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PlayerBrush extends AbstractPlayerBrush {

    private final Player player;

    /**
     * Represents a PlayerBrush for a specific player.
     *
     * @param player The player associated with this brush.
     */
    public PlayerBrush(Player player) {
        this.player = player;
    }

    /**
     * Updates the player's inventory with a menu for this brush.
     */
    public void updateInventory() {
        player.openInventory(new MenuInventory().createInventory(this));
    }

    /**
     * Adds a block type to the brush at a specific slot.
     *
     * @param material The material of the block to add.
     * @param slot     The slot where the block type should be added.
     */
    public void addBlock(Material material, int slot) {
        if (blockTypes.size() >= slot) {
            blockTypes.set(slot - 1, getBlockTypeFromMaterial(material));
        } else {
            blockTypes.add(getBlockTypeFromMaterial(material));
        }
        updateInventory();
    }

    /**
     * Removes a block type from the brush at a specific slot.
     *
     * @param slot The slot from which the block type should be removed.
     */
    public void removeBlock(int slot) {
        if (blockTypes.size() >= slot) {
            blockTypes.remove(slot - 1);
            updateInventory();
        }
    }

    /**
     * Adds a biome type to the brush at a specific slot.
     *
     * @param biomeType The biome type to add.
     * @param slot      The slot where the biome type should be added.
     */
    public void addBiomes(BiomeType biomeType, int slot) {
        if (biomeTypes.size() >= slot) {
            biomeTypes.set(slot - 1, biomeType);
        } else {
            biomeTypes.add(biomeType);
        }
        updateInventory();
    }

    /**
     * Removes a biome type from the brush at a specific slot.
     *
     * @param slot The slot from which the biome type should be removed.
     */
    public void removeBiome(int slot) {
        if (biomeTypes.size() >= slot) {
            biomeTypes.remove(slot - 1);
            updateInventory();
        }
    }

    /**
     * Cycles the color brush to the next color.
     */
    public void cycleColorBrush() {
        brush = GoPaint.getBrushManager().cycleColor((ColorBrush) brush);
        updateInventory();
    }

    /**
     * Cycles the color brush to the previous color.
     */
    public void cycleColorBrushBackwards() {
        brush = GoPaint.getBrushManager().cycleBackColor((ColorBrush) brush);
        updateInventory();
    }

    /**
     * Cycles the biome brush to the next biome.
     */
    public void cycleBiomeBrush() {
        brush = GoPaint.getBrushManager().cycleBiomes((BiomeBrush) brush);
        updateInventory();
    }

    /**
     * Cycles the biome brush to the previous biome.
     */
    public void cycleBiomeBrushBackwards() {
        brush = GoPaint.getBrushManager().cycleBackBiomes((BiomeBrush) brush);
        updateInventory();
    }

    /**
     * Cycles the placement type.
     */
    public void cyclePlacement() {
        placement = GoPaint.getPlacementManager().cyclePlacement(placement);
        updateInventory();
    }

    /**
     * Cycles the placement type backwards.
     */
    public void cyclePlacementBackwards() {
        placement = GoPaint.getPlacementManager().cyclePlacementBackwards(placement);
        updateInventory();
    }

    /**
     * Changes the brush size by the specified amount.
     *
     * @param change The amount by which to change the brush size.
     */
    public void changeBrushSize(int change) {
        this.brushSize += change;
    }

    /**
     * Sets the brush size to the specified size, considering the maximum size allowed.
     *
     * @param size The size to set for the brush.
     */
    public void setBrushSize(int size) {
        if (size <= GoPaint.getSettings().getMaxSize() && size > 0) {
            brushSize = size;
        } else if (size > GoPaint.getSettings().getMaxSize()) {
            brushSize = GoPaint.getSettings().getMaxSize();
        } else {
            brushSize = 1;
        }
        updateInventory();
    }

    /**
     * Sets the mask for the brush.
     *
     * @param material The material to set as the mask.
     */
    public void setMask(BlockType material) {
        this.mask = material;
        updateInventory();
    }

    /**
     * Toggles the enabled state of the brush.
     */
    public void toggleEnabled() {
        enabled = !enabled;
        updateInventory();
    }

    /**
     * Toggles between biome and color brush modes.
     */
    public void toggleBiome() {
        biomeBrush = !biomeBrush;

        if (biomeBrush) {
            brush = GoPaint.getBrushManager().getBiomesBrushes().get(0);
        } else {
            brush = GoPaint.getBrushManager().getColorBrushes().get(0);
        }

        updateInventory();
    }

    /**
     * Toggles the mask enabled state of the brush.
     */
    public void toggleMask() {
        maskEnabled = !maskEnabled;
        updateInventory();
    }

    /**
     * Toggles the surface mode of the brush.
     */
    public void toggleSurfaceMode() {
        surfaceEnabled = !surfaceEnabled;
        updateInventory();
    }
}
