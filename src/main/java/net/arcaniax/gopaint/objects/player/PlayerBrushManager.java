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

import net.arcaniax.gopaint.objects.brush.BiomeBrush;
import net.arcaniax.gopaint.objects.brush.ColorBrush;
import net.arcaniax.gopaint.objects.brush.biome.SphereBiomeBrush;
import net.arcaniax.gopaint.objects.brush.color.AngleBrush;
import net.arcaniax.gopaint.objects.brush.Brush;
import net.arcaniax.gopaint.objects.brush.color.BucketBrush;
import net.arcaniax.gopaint.objects.brush.color.DiscBrush;
import net.arcaniax.gopaint.objects.brush.color.FractureBrush;
import net.arcaniax.gopaint.objects.brush.color.GradientBrush;
import net.arcaniax.gopaint.objects.brush.color.OverlayBrush;
import net.arcaniax.gopaint.objects.brush.color.PaintBrush;
import net.arcaniax.gopaint.objects.brush.color.RecolorBrush;
import net.arcaniax.gopaint.objects.brush.color.SphereBrush;
import net.arcaniax.gopaint.objects.brush.color.SplatterBrush;
import net.arcaniax.gopaint.objects.brush.color.SprayBrush;
import org.bukkit.Color;
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
        colorBrushes.add(new RecolorBrush());
    }

    public void initializeBiomeBrushes() {
        biomeBrushes.add(new SphereBiomeBrush());
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

    public ArrayList<String> getColorBrushLore(ArrayList<String> lore, String name) {
        // &eSphere Brush___&8Spray Brush___&8Splatter Brush___&8Disc Brush___&8Bucket Brush___&8Angle Brush___&8Overlay Brush
        StringBuilder s = new StringBuilder();
        for (Brush b : colorBrushes) {
            if (b.getName().equalsIgnoreCase(name)) {
                lore.add("§e" + b.getName());
            } else {
                lore.add("§8" + b.getName());
            }
        }
        return lore;
    }

    public ArrayList<String>  getBiomeBrushLore(ArrayList<String> lore, String name) {
        // &eSphere Brush___&8Spray Brush___&8Splatter Brush___&8Disc Brush___&8Bucket Brush___&8Angle Brush___&8Overlay Brush
        StringBuilder s = new StringBuilder();
        for (Brush b : biomeBrushes) {
            if (b.getName().equalsIgnoreCase(name)) {
                lore.add("§e" + b.getName());
            } else {
                lore.add("§8" + b.getName());
            }
        }
        return lore;
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

    public List<BiomeBrush> getBiomeBrushes() {
        return biomeBrushes;
    }

    public void removePlayerBrush(Player p) {
        if (playerBrushes.containsKey(p.getName())) {
            playerBrushes.remove(playerBrushes.get(p.getName()));
        }
    }

    public Brush cycleColor(Brush b) {
        if (b == null) {
            return colorBrushes.get(0);
        }
        int next = colorBrushes.indexOf(b) + 1;
        if (next < colorBrushes.size()) {
            return colorBrushes.get(next);
        }
        return colorBrushes.get(0);
    }

    public Brush cycleBackColor(Brush b) {
        if (b == null) {
            return colorBrushes.get(0);
        }
        int back = colorBrushes.indexOf(b) - 1;
        if (back >= 0) {
            return colorBrushes.get(back);
        }
        return colorBrushes.get(colorBrushes.size() - 1);
    }


    public Brush cycleBiome(Brush b) {
        if (b == null) {
            return biomeBrushes.get(0);
        }
        int next = biomeBrushes.indexOf(b) + 1;
        if (next < biomeBrushes.size()) {
            return biomeBrushes.get(next);
        }
        return biomeBrushes.get(0);
    }

    public Brush cycleBackBiome(Brush b) {
        if (b == null) {
            return biomeBrushes.get(0);
        }
        int back = biomeBrushes.indexOf(b) - 1;
        if (back >= 0) {
            return biomeBrushes.get(back);
        }
        return biomeBrushes.get(biomeBrushes.size() - 1);
    }


}
