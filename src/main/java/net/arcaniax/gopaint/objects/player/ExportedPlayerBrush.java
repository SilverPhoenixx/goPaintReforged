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

import net.arcaniax.gopaint.GoPaintPlugin;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class ExportedPlayerBrush extends AbstractPlayerBrush {


    public ExportedPlayerBrush(String name, List<String> lore) {
        super();
        brush = GoPaintPlugin.getBrushManager().getColorBrush(name.replaceAll(" §b♦ ", ""));
        blocks = new ArrayList<>();
        for (String s : lore) {
            if (s.startsWith("§8Size: ")) {
                this.brushSize = Integer.parseInt(s.replaceAll("§8Size: ", ""));
            } else if (s.startsWith("§8Chance: ")) {
                this.chance = Integer.parseInt(s.replaceAll("§8Chance: ", "").replaceAll("%", ""));
            }
            if (s.startsWith("§8Thickness: ")) {
                this.thickness = Integer.parseInt(s.replaceAll("§8Thickness: ", ""));
            }
            if (s.startsWith("§8Axis: ")) {
                this.axis = s.replaceAll("§8Axis: ", "");
            }
            if (s.startsWith("§8FractureDistance: ")) {
                this.fractureDistance = Integer.parseInt(s.replaceAll("§8FractureDistance: ", ""));
            }
            if (s.startsWith("§8AngleDistance: ")) {
                this.angleDistance = Integer.parseInt(s.replaceAll("§8AngleDistance: ", ""));
            }
            if (s.startsWith("§8AngleHeightDifference: ")) {
                this.minAngleHeightDifference = Double.parseDouble(s.replaceAll("§8AngleHeightDifference: ", ""));
            }
            if (s.startsWith("§8Mixing: ")) {
                this.mixingStrength = Integer.parseInt(s.replaceAll("§8Mixing: ", ""));
            }
            if (s.startsWith("§8Falloff: ")) {
                this.falloffStrength = Integer.parseInt(s.replaceAll("§8Falloff: ", ""));
            }
            if(s.startsWith("§8Biome")) {
                this.biome = true;
            }
            if (s.startsWith("§8Blocks: ")) {
                s = s.replaceAll("§8Blocks: ", "");
                if (!s.equals("none")) {
                    for (String s2 : s.split(" ")) {
                        String[] type = s2.split("\\:");
                        Material mat = Material.getMaterial(type[0].toUpperCase());
                        this.blocks.add(mat);
                    }
                }
            }
            if (s.startsWith("§8Mask: ")) {
                s = s.replaceAll("§8Mask: ", "");
                String[] type = s.split("\\:");
                Material mat = Material.getMaterial(type[0].toUpperCase());
                this.mask = mat;
                this.maskEnabled = true;
            }
            if (s.startsWith("§8Surface Mode")) {
                this.surfaceEnabled = true;
            }
        }
    }
}
