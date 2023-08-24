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
import net.arcaniax.gopaint.paint.placement.GradientPlacement;
import net.arcaniax.gopaint.paint.placement.Placement;
import net.arcaniax.gopaint.paint.placement.RandomPlacement;
import net.arcaniax.gopaint.paint.placement.WithBlockDataPlacement;

import java.util.ArrayList;
import java.util.List;

public class PlacementManager {

    private GoPaint plugin;

    // Store different placement strategies in a list.
    private final List<Placement> placements;

    /**
     * Constructor for PlacementManager.
     *
     * @param plugin The GoPaint plugin instance.
     */
    public PlacementManager(GoPaint plugin) {
        this.plugin = plugin;

        this.placements = new ArrayList<>();

        // Initialize placement strategies.
        initializePlacement();
    }

    /**
     * Initialize placement strategies and add them to the 'placements' list.
     */
    public void initializePlacement() {
        try {
            placements.add(new RandomPlacement());
            placements.add(new GradientPlacement());
            placements.add(new WithBlockDataPlacement());
        } catch (Exception ignored) {
            // Handle any exceptions (ignored here).
        }
    }

    /**
     * Append lore information for placement strategies to an ArrayList based on a strategy name.
     *
     * @param lore The ArrayList to which lore information is appended.
     * @param name The name of the placement strategy.
     */
    public void appendPlacementLore(ArrayList<String> lore, String name) {
        for (Placement placement : placements) {
            if (placement.getName().equalsIgnoreCase(name)) {
                lore.add("§e" + placement.getName());
            } else {
                lore.add("§8" + placement.getName());
            }
        }
    }

    /**
     * Get a placement strategy based on its name.
     *
     * @param name The name of the placement strategy.
     * @return The Placement object with the specified name.
     */
    public Placement getPlacement(String name) {
        for (Placement placement : placements) {
            if (placement.getName().equals(name)) {
                return placement;
            }
        }
        // Return the first placement strategy if not found.
        return placements.get(0);
    }

    /**
     * Get the list of placement strategies.
     *
     * @return A list of Placement objects.
     */
    public List<Placement> getPlacements() {
        return placements;
    }

    /**
     * Cycle to the next placement strategy given the current placement strategy.
     *
     * @param placement The current Placement strategy.
     * @return The next Placement strategy in the list.
     */
    public Placement cyclePlacement(Placement placement) {
        if (placement == null) {
            return placements.get(0);
        }
        int next = placements.indexOf(placement) + 1;
        if (next < placements.size()) {
            return placements.get(next);
        }
        // Wrap around to the first strategy if at the end.
        return placements.get(0);
    }

    /**
     * Cycle to the previous placement strategy given the current placement strategy.
     *
     * @param placement The current Placement strategy.
     * @return The previous Placement strategy in the list.
     */
    public Placement cyclePlacementBackwards(Placement placement) {
        if (placement == null) {
            return placements.get(0);
        }
        int back = placements.indexOf(placement) - 1;
        if (back >= 0) {
            return placements.get(back);
        }
        // Wrap around to the last strategy if at the beginning.
        return placements.get(placements.size() - 1);
    }
}
