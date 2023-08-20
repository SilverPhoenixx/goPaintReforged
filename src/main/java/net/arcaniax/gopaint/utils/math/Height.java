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

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockTypes;
import net.arcaniax.gopaint.utils.vectors.MutableVector3;

public class Height {

    /**
     * Gets the highest solid block
     *
     * @param location of checked position (Cloned)
     * @return highest solid position (y coordinate)
     */
    public static int getHeight(MutableVector3 location, EditSession editSession) {
        BlockState block = editSession.getBlock(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        if (block.getBlockType() == BlockTypes.AIR) {
            while (block.getBlockType() == BlockTypes.AIR) {
                location.subtractY(1);
                if (location.getBlockY() < editSession.getWorld().getMinY()) {
                    return editSession.getWorld().getMinY();
                }
                block = editSession.getBlock(location.getBlockX(), location.getBlockY(), location.getBlockZ());
            }
            return location.getBlockY() + 1;
        } else {
            while (block.getBlockType() != BlockTypes.AIR) {
                location.addY(1);
                if (location.getBlockY() > editSession.getWorld().getMaxY()) {
                    return editSession.getMaxY();
                }
                block = editSession.getBlock(location.getBlockX(), location.getBlockY(), location.getBlockZ());
            }
            return location.getBlockY();
        }
    }

    public static double getAverageHeightDiffFracture(
            MutableVector3 location, int height, int distance, EditSession editSession
    ) {
        double totalHeight = 0;
        totalHeight += Math.abs(getHeight(location.clone().add(distance, 0, -distance), editSession)) - height;
        totalHeight += Math.abs(getHeight(location.clone().add(distance, 0, distance), editSession)) - height;
        totalHeight += Math.abs(getHeight(location.clone().add(-distance, 0, distance), editSession)) - height;
        totalHeight += Math.abs(getHeight(location.clone().add(-distance, 0, -distance), editSession)) - height;
        totalHeight += Math.abs(getHeight(location.clone().add(0, 0, -distance), editSession)) - height;
        totalHeight += Math.abs(getHeight(location.clone().add(0, 0, distance), editSession)) - height;
        totalHeight += Math.abs(getHeight(location.clone().add(-distance, 0, 0), editSession)) - height;
        totalHeight += Math.abs(getHeight(location.clone().add(distance, 0, 0), editSession)) - height;
        return (totalHeight / (double) 8) / (double) distance;
    }

    public static double getAverageHeightDiffAngle(MutableVector3 location, int distance, EditSession editSession) {
        double maxHeightDiff = 0;
        double maxHeightDiff2 = 0;

        double diff = Math.abs(getHeight(location.clone().add(distance, 0, -distance), editSession) - getHeight(location
                .clone()
                .add(-distance, 0, distance), editSession));

        if (diff >= maxHeightDiff) {
            maxHeightDiff = diff;
            maxHeightDiff2 = maxHeightDiff;
        }

        diff = Math.abs(getHeight(location.clone().add(distance, 0, distance), editSession) - getHeight(location
                .clone()
                .add(-distance, 0, -distance), editSession));
        if (diff > maxHeightDiff) {
            maxHeightDiff = diff;
            maxHeightDiff2 = maxHeightDiff;
        }

        diff = Math.abs(getHeight(location.clone().add(distance, 0, 0), editSession) - getHeight(location
                .clone()
                .add(-distance, 0, 0), editSession));
        if (diff > maxHeightDiff) {
            maxHeightDiff = diff;
            maxHeightDiff2 = maxHeightDiff;
        }

        diff = Math.abs(getHeight(location.clone().add(0, 0, -distance), editSession) - getHeight(location
                .clone()
                .add(0, 0, distance), editSession));
        if (diff > maxHeightDiff) {
            maxHeightDiff = diff;
            maxHeightDiff2 = maxHeightDiff;
        }

        double height = (maxHeightDiff2 + maxHeightDiff) / 2.0;
        return height / (double) (distance * 2);
    }

    public static boolean isOnTop(MutableVector3 loc, int thickness, EditSession editSession) {
        int height = getHeight(loc.clone(), editSession);
        return height - loc.getBlockY() <= thickness;
    }

}
