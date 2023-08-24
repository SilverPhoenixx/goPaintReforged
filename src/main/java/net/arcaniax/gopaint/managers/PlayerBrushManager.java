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
package net.arcaniax.gopaint.managers;

import net.arcaniax.gopaint.GoPaint;
import net.arcaniax.gopaint.paint.brush.BiomeBrush;
import net.arcaniax.gopaint.paint.brush.ColorBrush;
import net.arcaniax.gopaint.paint.brush.biome.SphereBiomeBrush;
import net.arcaniax.gopaint.paint.brush.color.AngleBrush;
import net.arcaniax.gopaint.paint.brush.Brush;
import net.arcaniax.gopaint.paint.brush.color.BucketBrush;
import net.arcaniax.gopaint.paint.brush.color.DiscBrush;
import net.arcaniax.gopaint.paint.brush.color.FractureBrush;
import net.arcaniax.gopaint.paint.brush.color.SphereBrush;
import net.arcaniax.gopaint.paint.brush.color.OverlayBrush;
import net.arcaniax.gopaint.paint.brush.color.PaintBrush;
import net.arcaniax.gopaint.paint.brush.color.SplatterBrush;
import net.arcaniax.gopaint.paint.brush.color.SprayBrush;
import net.arcaniax.gopaint.paint.brush.player.PlayerBrush;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerBrushManager {

    private GoPaint plugin;

    // Store player brushes in a map, color brushes in a list, and biome brushes in a list.
    private final HashMap<Player, PlayerBrush> playerBrushes;
    private final List<ColorBrush> colorBrushes;
    private final List<BiomeBrush> biomeBrushes;

    /**
     * Constructor for PlayerBrushManager.
     *
     * @param plugin The GoPaint plugin instance.
     */
    public PlayerBrushManager(GoPaint plugin) {
        this.plugin = plugin;

        // Initialize the data structures.
        playerBrushes = new HashMap<>();
        colorBrushes = new ArrayList<>();
        biomeBrushes = new ArrayList<>();

        // Initialize color and biome brushes.
        initializeBiomeBrushes();
        initializeColorBrushes();
    }

    /**
     * Initialize color brushes and add them to the 'colorBrushes' list.
     */
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
            colorBrushes.add(new PaintBrush());
        } catch (Exception ignored) {
            // Handle any exceptions (ignored here).
        }
    }

    /**
     * Initialize biome brushes and add them to the 'biomeBrushes' list.
     */
    public void initializeBiomeBrushes() {
        try {
            biomeBrushes.add(new SphereBiomeBrush());
        } catch (Exception ignored) {
            // Handle any exceptions (ignored here).
        }
    }

    /**
     * Get a player's brush based on the player object.
     *
     * @param p The Player object.
     * @return The PlayerBrush associated with the player.
     */
    public PlayerBrush getPlayerBrush(Player p) {
        if (playerBrushes.containsKey(p)) {
            return playerBrushes.get(p);
        } else {
            PlayerBrush pb = new PlayerBrush(p);
            playerBrushes.put(p, pb);
            return pb;
        }
    }

    /**
     * Append lore information for color brushes to an ArrayList based on a brush name.
     *
     * @param lore The ArrayList to which lore information is appended.
     * @param name The name of the brush.
     */
    public void appendColorBrushLore(ArrayList<String> lore, String name) {
        for (Brush b : colorBrushes) {
            if (b.getName().equalsIgnoreCase(name)) {
                lore.add("§e" + b.getName());
            } else {
                lore.add("§8" + b.getName());
            }
        }
    }

    /**
     * Append lore information for biome brushes to an ArrayList based on a brush name.
     *
     * @param lore The ArrayList to which lore information is appended.
     * @param name The name of the brush.
     */
    public void appendBiomesBrushLore(ArrayList<String> lore, String name) {
        for (Brush b : biomeBrushes) {
            if (b.getName().equalsIgnoreCase(name)) {
                lore.add("§e" + b.getName());
            } else {
                lore.add("§8" + b.getName());
            }
        }
    }

    /**
     * Get a color brush based on its name.
     *
     * @param name The name of the color brush.
     * @return The ColorBrush with the specified name.
     */
    public Brush getColorBrush(String name) {
        for (Brush b : colorBrushes) {
            if (b.getName().equalsIgnoreCase(name)) {
                return b;
            }
        }
        // Return the first color brush if not found.
        return colorBrushes.get(0);
    }

    /**
     * Get a biome brush based on its name.
     *
     * @param name The name of the biome brush.
     * @return The BiomeBrush with the specified name.
     */
    public Brush getBiomeBrush(String name) {
        for (Brush currentBrush : biomeBrushes) {
            if (currentBrush.getName().equalsIgnoreCase(name)) {
                return currentBrush;
            }
        }
        // Return the first biome brush if not found.
        return biomeBrushes.get(0);
    }

    /**
     * Get the list of color brushes.
     *
     * @return A list of ColorBrush objects.
     */
    public List<ColorBrush> getColorBrushes() {
        return colorBrushes;
    }

    /**
     * Get the list of biome brushes.
     *
     * @return A list of BiomeBrush objects.
     */
    public List<BiomeBrush> getBiomesBrushes() {
        return biomeBrushes;
    }

    /**
     * Remove a player's brush based on the player object.
     *
     * @param player The Player object whose brush should be removed.
     */
    public void removePlayerBrush(Player player) {
        playerBrushes.remove(player);
    }

    /**
     * Cycle to the next color brush given the current color brush.
     *
     * @param colorBrush The current ColorBrush.
     * @return The next ColorBrush in the list.
     */
    public Brush cycleColor(ColorBrush colorBrush) {
        if (colorBrush == null) {
            return colorBrushes.get(0);
        }
        int next = colorBrushes.indexOf(colorBrush) + 1;
        if (next < colorBrushes.size()) {
            return colorBrushes.get(next);
        }
        // Wrap around to the first brush if at the end.
        return colorBrushes.get(0);
    }

    /**
     * Cycle to the previous color brush given the current color brush.
     *
     * @param brush The current ColorBrush.
     * @return The previous ColorBrush in the list.
     */
    public Brush cycleBackColor(ColorBrush brush) {
        if (brush == null) {
            return colorBrushes.get(0);
        }
        int back = colorBrushes.indexOf(brush) - 1;
        if (back >= 0) {
            return colorBrushes.get(back);
        }
        // Wrap around to the last brush if at the beginning.
        return colorBrushes.get(colorBrushes.size() - 1);
    }

    /**
     * Cycle to the next biome brush given the current biome brush.
     *
     * @param biomeBrush The current BiomeBrush.
     * @return The next BiomeBrush in the list.
     */
    public Brush cycleBiomes(BiomeBrush biomeBrush) {
        if (biomeBrush == null) {
            return biomeBrushes.get(0);
        }
        int next = biomeBrushes.indexOf(biomeBrush) + 1;
        if (next < biomeBrushes.size()) {
            return biomeBrushes.get(next);
        }
        // Wrap around to the first biome brush if at the end.
        return biomeBrushes.get(0);
    }

    /**
     * Cycle to the previous biome brush given the current biome brush.
     *
     * @param biomeBrush The current BiomeBrush.
     * @return The previous BiomeBrush in the list.
     */
    public Brush cycleBackBiomes(BiomeBrush biomeBrush) {
        if (biomeBrush == null) {
            return biomeBrushes.get(0);
        }
        int back = biomeBrushes.indexOf(biomeBrush) - 1;
        if (back >= 0) {
            return biomeBrushes.get(back);
        }
        // Wrap around to the last biome brush if at the beginning.
        return biomeBrushes.get(biomeBrushes.size() - 1);
    }
}
