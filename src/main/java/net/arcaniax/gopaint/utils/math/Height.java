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
package net.arcaniax.gopaint.utils.math;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Height {

    /**
     * Gets the highest solid block
     * @param location of checked position
     * @return highest solid position (y coordinate)
     */
    public static int getHeight(Location location) {
        if (location.getBlock().getType().equals(Material.AIR)) {
            while (location.getBlock().getType().equals(Material.AIR)) {
                location.add(0, -1, 0);
                if (location.getBlockY() < location.getWorld().getMinHeight()) {
                    return location.getWorld().getMinHeight();
                }
            }
            return location.getBlockY() + 1;
        } else {
            while (!(location.getBlock().getType().equals(Material.AIR))) {
                location.add(0, 1, 0);
                if (location.getBlockY() > location.getWorld().getMaxHeight()) {
                    return location.getWorld().getMaxHeight();
                }
            }
            return location.getBlockY();
        }
    }

    public static double getAverageHeightDiffFracture(Location location, int height, int distance) {
        double totalHeight = 0;
        totalHeight += Math.abs(getHeight(location.clone().add(distance, 0, -distance))) - height;
        totalHeight += Math.abs(getHeight(location.clone().add(distance, 0, distance))) - height;
        totalHeight += Math.abs(getHeight(location.clone().add(-distance, 0, distance))) - height;
        totalHeight += Math.abs(getHeight(location.clone().add(-distance, 0, -distance))) - height;
        totalHeight += Math.abs(getHeight(location.clone().add(0, 0, -distance))) - height;
        totalHeight += Math.abs(getHeight(location.clone().add(0, 0, distance))) - height;
        totalHeight += Math.abs(getHeight(location.clone().add(-distance, 0, 0))) - height;
        totalHeight += Math.abs(getHeight(location.clone().add(distance, 0, 0))) - height;
        return (totalHeight / (double) 8) / (double) distance;
    }

    public static double getAverageHeightDiffAngle(Location location, int distance) {
        double maxHeightDiff = 0;
        double maxHeightDiff2 = 0;
        double diff = Math
                .abs(getHeight(location.clone().add(distance, 0, -distance)) - getHeight(location.clone().add(-distance, 0, distance)));
        if (diff >= maxHeightDiff) {
            maxHeightDiff = diff;
            maxHeightDiff2 = maxHeightDiff;
        }
        diff = Math
                .abs(getHeight(location.clone().add(distance, 0, distance)) - getHeight(location.clone().add(-distance, 0, -distance)));
        if (diff > maxHeightDiff) {
            maxHeightDiff = diff;
            maxHeightDiff2 = maxHeightDiff;
        }
        diff = Math
                .abs(getHeight(location.clone().add(distance, 0, 0)) - getHeight(location.clone().add(-distance, 0, 0)));
        if (diff > maxHeightDiff) {
            maxHeightDiff = diff;
            maxHeightDiff2 = maxHeightDiff;
        }
        diff = Math
                .abs(getHeight(location.clone().add(0, 0, -distance)) - getHeight(location.clone().add(0, 0, distance)));
        if (diff > maxHeightDiff) {
            maxHeightDiff = diff;
            maxHeightDiff2 = maxHeightDiff;
        }

        double height = (maxHeightDiff2 + maxHeightDiff) / 2.0;
        return height / (double) (distance * 2);
    }

    public static boolean isOnTop(Location loc, int thickness) {
        int height = getHeight(loc.clone());
        return height - loc.getBlockY() <= thickness;
    }

}
