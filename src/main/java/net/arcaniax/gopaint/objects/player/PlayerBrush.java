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
package net.arcaniax.gopaint.objects.player;

import com.sk89q.worldedit.world.biome.BiomeType;
import net.arcaniax.gopaint.GoPaintPlugin;
import net.arcaniax.gopaint.utils.gui.MenuInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerBrush extends AbstractPlayerBrush {

    private Player player;

    public PlayerBrush(Player player) {
        this.player = player;
    }

    public void updateInventory() {
        player.openInventory(new MenuInventory().createInventory(this));
    }

    public void addBlock(Material itemStack, int slot) {
        if (blocks.size() >= slot) {
            blocks.set(slot - 1, itemStack);
        } else {
            blocks.add(itemStack);
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
        brush = GoPaintPlugin.getBrushManager().cycleColor(brush);
        updateInventory();
    }

    public void cycleColorBrushBackwards() {
        brush = GoPaintPlugin.getBrushManager().cycleBackColor(brush);
        updateInventory();
    }

    public void cycleBiomeBrush() {
        brush = GoPaintPlugin.getBrushManager().cycleBiome(brush);
        updateInventory();
    }

    public void cycleBiomeBrushBackwards() {
        brush = GoPaintPlugin.getBrushManager().cycleBackBiome(brush);
        updateInventory();
    }

    public void changeBrushSize(int change) {
        this.brushSize += change;
    }
    public void setBrushSize(int size) {
        if (size <= GoPaintPlugin.getSettings().getMaxSize() && size > 0) {
            brushSize = size;
        } else if (size > GoPaintPlugin.getSettings().getMaxSize()) {
            brushSize = GoPaintPlugin.getSettings().getMaxSize();
        } else {
            brushSize = 1;
        }
        updateInventory();
    }

    public void setMask(Material material) {
        this.mask = material;
        updateInventory();
    }

    public void toggleEnabled() {
        enabled = !enabled;
        updateInventory();
    }

    public void toggleBiome() {
        biome = !biome;

        if(biome) {
            brush = GoPaintPlugin.getBrushManager().getBiomeBrushes().get(0);
        } else {
            brush = GoPaintPlugin.getBrushManager().getColorBrushes().get(0);
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
