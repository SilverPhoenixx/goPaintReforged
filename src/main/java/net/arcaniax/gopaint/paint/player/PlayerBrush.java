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
import com.sk89q.worldedit.world.block.BlockType;
import net.arcaniax.gopaint.GoPaint;
import net.arcaniax.gopaint.inventories.MenuInventory;
import net.arcaniax.gopaint.paint.brush.BiomeBrush;
import net.arcaniax.gopaint.paint.brush.ColorBrush;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PlayerBrush extends AbstractPlayerBrush {

    private final Player player;

    public PlayerBrush(Player player) {
        this.player = player;
    }

    public void updateInventory() {
        player.openInventory(new MenuInventory().createInventory(this));
    }

    public void addBlock(Material material, int slot) {
        if (blocks.size() >= slot) {
            blocks.set(slot - 1, getBlockTypeFromMaterial(material));
        } else {
            blocks.add(getBlockTypeFromMaterial(material));
        }
        updateInventory();
    }

    public void removeBlock(int slot) {
        if (blocks.size() >= slot) {
            blocks.remove(slot - 1);
            updateInventory();
        }
    }

    public void addBiomes(BiomeType biomeType, int slot) {
        if (biomeTypes.size() >= slot) {
            biomeTypes.set(slot - 1, biomeType);
        } else {
            biomeTypes.add(biomeType);
        }
        updateInventory();
    }

    public void removeBiome(int slot) {
        if (biomeTypes.size() >= slot) {
            biomeTypes.remove(slot - 1);
            updateInventory();
        }
    }


    public void cycleColorBrush() {
        brush = GoPaint.getBrushManager().cycleColor((ColorBrush) brush);
        updateInventory();
    }

    public void cycleColorBrushBackwards() {
        brush = GoPaint.getBrushManager().cycleBackColor((ColorBrush) brush);
        updateInventory();
    }

    public void cycleBiomeBrush() {
        brush = GoPaint.getBrushManager().cycleBiomes((BiomeBrush) brush);
        updateInventory();
    }

    public void cycleBiomeBrushBackwards() {
        brush = GoPaint.getBrushManager().cycleBackBiomes((BiomeBrush) brush);
        updateInventory();
    }

    public void changeBrushSize(int change) {
        this.brushSize += change;
    }
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

    public void setMask(BlockType material) {
        this.mask = material;
        updateInventory();
    }

    public void toggleEnabled() {
        enabled = !enabled;
        updateInventory();
    }

    public void toggleBiome() {
        biomeBrush = !biomeBrush;

        if(biomeBrush) {
            brush = GoPaint.getBrushManager().getBiomesBrushes().get(0);
        } else {
            brush = GoPaint.getBrushManager().getColorBrushes().get(0);
        }

        updateInventory();
    }

    public void toggleMask() {
        maskEnabled = !maskEnabled;
        updateInventory();
    }

    public void toggleSurfaceMode() {
        surfaceEnabled = !surfaceEnabled;
        updateInventory();
    }
}
