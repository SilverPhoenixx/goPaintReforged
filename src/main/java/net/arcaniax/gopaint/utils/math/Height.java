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
     * Gets the highest solid block's Y-coordinate above or below the given location.
     *
     * @param blockLocation     The location to check.
     * @param editSession  The editing session to retrieve block information.
     * @return The Y-coordinate of the highest solid block.
     */
    public static int getHeight(MutableVector3 blockLocation, EditSession editSession) {
        MutableVector3 currentVector = blockLocation.clone();

        BlockState block = editSession.getBlock(currentVector.getBlockX(), currentVector.getBlockY(), currentVector.getBlockZ());

        // If the starting block is air, find the highest non-air block below it.
        if (block.getBlockType() == BlockTypes.AIR) {
            while (block.getBlockType() == BlockTypes.AIR) {
                currentVector.subtractY(1);

                // Check if we've reached the world's minimum Y-coordinate.
                if (currentVector.getBlockY() < editSession.getWorld().getMinY()) {
                    return editSession.getWorld().getMinY();
                }

                block = editSession.getBlock(currentVector.getBlockX(), currentVector.getBlockY(), currentVector.getBlockZ());
            }

            // Return the Y-coordinate one block above the highest non-air block.
            return currentVector.getBlockY() + 1;
        } else {
            // If the starting block is solid, find the highest non-solid block above it.
            while (block.getBlockType() != BlockTypes.AIR) {
                currentVector.addY(1);

                // Check if we've reached the world's maximum Y-coordinate.
                if (currentVector.getBlockY() > editSession.getWorld().getMaxY()) {
                    return editSession.getMaxY();
                }

                block = editSession.getBlock(currentVector.getBlockX(), currentVector.getBlockY(), currentVector.getBlockZ());
            }

            // Return the Y-coordinate of the highest non-solid block.
            return currentVector.getBlockY();
        }
    }

    /**
     * Calculate the average height difference among neighboring blocks in a given radius.
     *
     * @param blockVector  The center location for height comparison.
     * @param height       The reference height to compare against.
     * @param distance     The distance from the center to consider neighboring blocks.
     * @param editSession  The editing session to retrieve block information.
     * @return The average height difference normalized by distance.
     */
    public static double getAverageHeightDiffFracture(
            MutableVector3 blockVector, int height, int distance, EditSession editSession
    ) {
        MutableVector3 currentVector = blockVector.clone();

        double totalHeight = 0;

        // Calculate the height difference for neighboring blocks in eight directions.
        totalHeight += Math.abs(getHeight(currentVector.clone().add(distance, 0, -distance), editSession) - height);
        totalHeight += Math.abs(getHeight(currentVector.clone().add(distance, 0, distance), editSession) - height);
        totalHeight += Math.abs(getHeight(currentVector.clone().add(-distance, 0, distance), editSession) - height);
        totalHeight += Math.abs(getHeight(currentVector.clone().add(-distance, 0, -distance), editSession) - height);
        totalHeight += Math.abs(getHeight(currentVector.clone().add(0, 0, -distance), editSession) - height);
        totalHeight += Math.abs(getHeight(currentVector.clone().add(0, 0, distance), editSession) - height);
        totalHeight += Math.abs(getHeight(currentVector.clone().add(-distance, 0, 0), editSession) - height);
        totalHeight += Math.abs(getHeight(currentVector.clone().add(distance, 0, 0), editSession) - height);

        // Calculate the average height difference and normalize by the number of directions and distance.
        return (totalHeight / 8.0) / (double) distance;
    }

    /**
     * Calculate the average height difference between diagonally opposite blocks in a given radius.
     *
     * @param centerBlock  The center location for height comparison.
     * @param distance     The distance from the center to consider diagonally opposite blocks.
     * @param editSession  The editing session to retrieve block information.
     * @return The average height difference normalized by distance.
     */
    public static double getAverageHeightDiffAngle(MutableVector3 centerBlock, int distance, EditSession editSession) {
        MutableVector3 centerVector = centerBlock.clone();

        double maxHeightDiff = 0;
        double maxHeightDiff2 = 0;

        // Compare height differences between diagonally opposite blocks.
        double diff = Math.abs(getHeight(centerVector.clone().add(distance, 0, -distance), editSession) - getHeight(centerVector.clone().add(-distance, 0, distance), editSession));
        if (diff >= maxHeightDiff) {
            maxHeightDiff = diff;
            maxHeightDiff2 = maxHeightDiff;
        }

        diff = Math.abs(getHeight(centerVector.clone().add(distance, 0, distance), editSession) - getHeight(centerVector.clone().add(-distance, 0, -distance), editSession));
        if (diff > maxHeightDiff) {
            maxHeightDiff = diff;
            maxHeightDiff2 = maxHeightDiff;
        }

        diff = Math.abs(getHeight(centerVector.clone().add(distance, 0, 0), editSession) - getHeight(centerVector.clone().add(-distance, 0, 0), editSession));
        if (diff > maxHeightDiff) {
            maxHeightDiff = diff;
            maxHeightDiff2 = maxHeightDiff;
        }

        diff = Math.abs(getHeight(centerVector.clone().add(0, 0, -distance), editSession) - getHeight(centerVector.clone().add(0, 0, distance), editSession));
        if (diff > maxHeightDiff) {
            maxHeightDiff = diff;
            maxHeightDiff2 = maxHeightDiff;
        }

        // Calculate the average height difference between diagonally opposite blocks and normalize by distance.
        double height = (maxHeightDiff2 + maxHeightDiff) / 2.0;
        return height / (double) (distance * 2);
    }

    /**
     * Check if a location is within a specified thickness from the top of the solid block.
     *
     * @param currentBlock The location to check.
     * @param thickness    The maximum allowed thickness.
     * @param editSession  The editing session to retrieve block information.
     * @return True if the location is within the specified thickness from the top of the solid block.
     */
    public static boolean isOnTop(MutableVector3 currentBlock, int thickness, EditSession editSession) {
        MutableVector3 currentVector = currentBlock.clone();

        int height = getHeight(currentVector, editSession);
        return height - currentVector.getBlockY() <= thickness;
    }


}
