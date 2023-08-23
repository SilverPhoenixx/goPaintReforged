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
package net.arcaniax.gopaint.paint.brush.placement;

import java.util.ArrayList;
import java.util.List;

public class PlacementManager {

    private final List<Placement> placements;

    public PlacementManager() {
        this.placements = new ArrayList<>();

        initializePlacement();
    }

    public void initializePlacement() {
        try {
            placements.add(new RandomPlacement());
            placements.add(new GradientPlacement());
        } catch (Exception ignored) {

        }
    }
    public void appendPlacementLore(ArrayList<String> lore, String name) {
        for (Placement placement : placements) {
            if (placement.getName().equalsIgnoreCase(name)) {
                lore.add("§e" + placement.getName());
            } else {
                lore.add("§8" + placement.getName());
            }
        }
    }
    public Placement getPlacement(String name) {
        for (Placement placement : placements) {
            if (placement.getName().equals(name)) {
                return placement;
            }
        }
        return placements.get(0);
    }


    public List<Placement> getPlacements() {
        return placements;
    }

    public Placement cyclePlacement(Placement placement) {
        if (placement == null) {
            return placements.get(0);
        }
        int next = placements.indexOf(placement) + 1;
        if (next < placements.size()) {
            return placements.get(next);
        }
        return placements.get(0);
    }

    public Placement cyclePlacementBackwards(Placement placement) {
        if (placement == null) {
            return placements.get(0);
        }
        int back = placements.indexOf(placement) - 1;
        if (back >= 0) {
            return placements.get(back);
        }
        return placements.get(placements.size() - 1);
    }
}
