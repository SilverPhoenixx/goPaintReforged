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
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ExportedPlayerBrush extends AbstractPlayerBrush {

    public ExportedPlayerBrush(ItemStack itemStack) {
        this(itemStack.getItemMeta().getDisplayName(), itemStack.getItemMeta().getLore());
    }

    public ExportedPlayerBrush(String name, List<String> lore) {
        this.enabled = true;

        blockTypes = new ArrayList<>();
        biomeTypes = new ArrayList<>();
        for (String s : lore) {
            if (s.startsWith("Surface Mode: ")) {
                this.surfaceEnabled = Boolean.parseBoolean(s.replaceAll("Surface Mode: ", ""));
                continue;
            }
            if (s.startsWith("Mask Enabled: ")) {
                this.maskEnabled = Boolean.parseBoolean(s.replaceAll("Mask Enabled: ", ""));
                continue;
            }
            if (s.startsWith("Biome Mode: ")) {
                this.biomeBrush = Boolean.parseBoolean(s.replaceAll("Biome Mode: ", ""));
                continue;
            }
            if (s.startsWith("Brush Size: ")) {
                this.brushSize = Integer.parseInt(s.replaceAll("Brush Size: ", ""));
                continue;
            }
            if (s.startsWith("Place Chance: ")) {
                this.chance = Integer.parseInt(s.replaceAll("Place Chance: ", "").replaceAll("%", ""));
                continue;
            }
            if (s.startsWith("Thickness: ")) {
                this.thickness = Integer.parseInt(s.replaceAll("Thickness: ", ""));
                continue;
            }
            if (s.startsWith("Axis: ")) {
                this.axis = s.replaceAll("Axis: ", "");
                continue;
            }
            if (s.startsWith("FractureDistance: ")) {
                this.fractureDistance = Integer.parseInt(s.replaceAll("FractureDistance: ", ""));
                continue;
            }
            if (s.startsWith("AngleDistance: ")) {
                this.angleDistance = Integer.parseInt(s.replaceAll("AngleDistance: ", ""));
                continue;
            }
            if (s.startsWith("AngleHeightDifference: ")) {
                this.minAngleHeightDifference = Double.parseDouble(s.replaceAll("AngleHeightDifference: ", ""));
                continue;
            }
            if (s.startsWith("Mixing: ")) {
                this.mixingStrength = Integer.parseInt(s.replaceAll("Mixing: ", ""));
                continue;
            }

            if (s.startsWith("Falloff: ")) {
                this.falloffStrength = Integer.parseInt(s.replaceAll("Falloff: ", ""));
                continue;
            }

            if(s.startsWith("Biomes: ")) {
                s = s.replaceAll("Biomes: ", "");
                if (!s.equals("None")) {
                    for (String s2 : s.split(" ")) {
                        BiomeType biomeType = BiomeTypes.get(s2);
                        if(biomeType == null) continue;
                        this.biomeTypes.add(biomeType);
                    }
                }
            }
            if (s.startsWith("Blocks: ")) {
                s = s.replaceAll("Blocks: ", "");
                if (!s.equals("None")) {
                    for (String key : s.split(" ")) {
                        BlockType blockType = BlockTypes.get(key);
                        if(blockType == null) continue;
                        this.blockTypes.add(blockType);
                    }
                }
            }
            if (s.startsWith("Mask: ")) {
                s = s.replaceAll("Mask: ", "");
                BlockType blockType = BlockTypes.get(s);
                if(blockType == null) continue;
                this.mask = blockType;
            }

            if(biomeBrush) {
                brush = GoPaint.getBrushManager().getBiomeBrush(name.replaceAll("§3GoPaint §7>§b Exported Brush §7>§b ", ""));
            } else {
                brush = GoPaint.getBrushManager().getColorBrush(name.replaceAll("§3GoPaint §7>§b Exported Brush §7>§b ", ""));
            }
        }
    }
}
