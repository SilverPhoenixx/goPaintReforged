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

import net.arcaniax.gopaint.paint.brush.BiomeBrush;
import net.arcaniax.gopaint.paint.brush.ColorBrush;
import net.arcaniax.gopaint.paint.brush.biome.SphereBiomeBrush;
import net.arcaniax.gopaint.paint.brush.color.AngleBrush;
import net.arcaniax.gopaint.paint.brush.Brush;
import net.arcaniax.gopaint.paint.brush.color.BucketBrush;
import net.arcaniax.gopaint.paint.brush.color.DiscBrush;
import net.arcaniax.gopaint.paint.brush.color.FractureBrush;
import net.arcaniax.gopaint.paint.brush.color.GradientBrush;
import net.arcaniax.gopaint.paint.brush.color.OverlayBrush;
import net.arcaniax.gopaint.paint.brush.color.PaintBrush;
import net.arcaniax.gopaint.paint.brush.color.SphereBrush;
import net.arcaniax.gopaint.paint.brush.color.SplatterBrush;
import net.arcaniax.gopaint.paint.brush.color.SprayBrush;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerBrushManager {

    private final HashMap<Player, PlayerBrush> playerBrushes;
    private final List<ColorBrush> colorBrushes;
    private final List<BiomeBrush> biomeBrushes;

    public PlayerBrushManager() {
        playerBrushes = new HashMap<>();
        colorBrushes = new ArrayList<>();
        biomeBrushes = new ArrayList<>();

        initializeBiomeBrushes();
        initializeColorBrushes();
    }

    public void initializeColorBrushes() {
        try {
            colorBrushes.add(new SphereBrush());
            colorBrushes.add(new SprayBrush());
            colorBrushes.add(new SplatterBrush());
            colorBrushes.add(new DiscBrush());
            colorBrushes.add(new BucketBrush());
            colorBrushes.add(new AngleBrush());
            colorBrushes.add(new OverlayBrush());
            colorBrushes.add(new FractureBrush());
            colorBrushes.add(new GradientBrush());
            colorBrushes.add(new PaintBrush());
        } catch (Exception ignored) {

        }
    }

    public void initializeBiomeBrushes() {
        try {
            biomeBrushes.add(new SphereBiomeBrush());
        } catch (Exception ignored) {

        }
    }


    public PlayerBrush getPlayerBrush(Player p) {
        if (playerBrushes.containsKey(p)) {
            return playerBrushes.get(p);
        } else {
            PlayerBrush pb = new PlayerBrush(p);
            playerBrushes.put(p, pb);
            return pb;
        }
    }

    public void appendColorBrushLore(ArrayList<String> lore, String name) {
        for (Brush b : colorBrushes) {
            if (b.getName().equalsIgnoreCase(name)) {
                lore.add("§e" + b.getName());
            } else {
                lore.add("§8" + b.getName());
            }
        }
    }

    public void appendBiomesBrushLore(ArrayList<String> lore, String name) {
        for (Brush b : biomeBrushes) {
            if (b.getName().equalsIgnoreCase(name)) {
                lore.add("§e" + b.getName());
            } else {
                lore.add("§8" + b.getName());
            }
        }
    }

    public Brush getColorBrush(String name) {
        for (Brush b : colorBrushes) {
            if (b.getName().equalsIgnoreCase(name)) {
                return b;
            }
        }
        return colorBrushes.get(0);
    }

    public Brush getBiomeBrush(String name) {
        for (Brush b : biomeBrushes) {
            if (b.getName().equalsIgnoreCase(name)) {
                return b;
            }
        }
        return biomeBrushes.get(0);
    }

    public List<ColorBrush> getColorBrushes() {
        return colorBrushes;
    }

    public List<BiomeBrush> getBiomesBrushes() {
        return biomeBrushes;
    }

    public void removePlayerBrush(Player player) {
        playerBrushes.remove(player);
    }

    public Brush cycleColor(ColorBrush colorBrush) {
        if (colorBrush == null) {
            return colorBrushes.get(0);
        }
        int next = colorBrushes.indexOf(colorBrush) + 1;
        if (next < colorBrushes.size()) {
            return colorBrushes.get(next);
        }
        return colorBrushes.get(0);
    }

    public Brush cycleBackColor(ColorBrush brush) {
        if (brush == null) {
            return colorBrushes.get(0);
        }
        int back = colorBrushes.indexOf(brush) - 1;
        if (back >= 0) {
            return colorBrushes.get(back);
        }
        return colorBrushes.get(colorBrushes.size() - 1);
    }


    public Brush cycleBiomes(BiomeBrush biomeBrush) {
        if (biomeBrush == null) {
            return biomeBrushes.get(0);
        }
        int next = biomeBrushes.indexOf(biomeBrush) + 1;
        if (next < biomeBrushes.size()) {
            return biomeBrushes.get(next);
        }
        return biomeBrushes.get(0);
    }

    public Brush cycleBackBiomes(BiomeBrush biomeBrush) {
        if (biomeBrush == null) {
            return biomeBrushes.get(0);
        }
        int back = biomeBrushes.indexOf(biomeBrush) - 1;
        if (back >= 0) {
            return biomeBrushes.get(back);
        }
        return biomeBrushes.get(biomeBrushes.size() - 1);
    }


}
