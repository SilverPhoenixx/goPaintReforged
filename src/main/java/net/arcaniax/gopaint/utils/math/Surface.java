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

public class Surface {

    /**
     * Checks if the block is on the surface
     *
     * @param blockLocation  The location of the block being checked
     * @param playerLocation The location of the player
     * @param editSession    The editing session used to query block states
     * @return true when the block is on the surface, false otherwise
     */
    public static boolean isOnSurface(MutableVector3 blockLocation, MutableVector3 playerLocation, EditSession editSession) {
        // Clone the input vectors to avoid modifying the original vectors
        MutableVector3 blockVector = blockLocation.clone();
        MutableVector3 playerVector = playerLocation.clone();

        // Raise the player's position by 1.5 units
        playerVector.addY(1.5);

        // Calculate the distances between the player and the block in each dimension
        double distanceX = playerVector.getX() - blockVector.getX();
        double distanceY = playerVector.getY() - blockVector.getY();
        double distanceZ = playerVector.getZ() - blockVector.getZ();

        // Adjust the block's coordinates based on the distances
        // Change x coordinate
        if (distanceX > 1) {
            blockVector.addX(1);
        } else if (distanceX > 0) {
            blockVector.addX(0.5);
        }

        // Change y coordinate
        if (distanceY > 1) {
            blockVector.addY(1);
        } else if (distanceY > 0) {
            blockVector.addY(0.5);
        }

        // Change z coordinate
        if (distanceZ > 1) {
            blockVector.addZ(1);
        } else if (distanceZ > 0) {
            blockVector.addZ(0.5);
        }

        // Calculate the overall distance between the adjusted block and player positions
        double distance = blockVector.distance(playerVector);

        // Iterate through the points between the player and the block
        for (int currentDistance = 1; currentDistance < distance; currentDistance++) {
            // Calculate the movement in each dimension for this point
            double moveX = distanceX * (currentDistance / distance);
            double moveY = distanceY * (currentDistance / distance);
            double moveZ = distanceZ * (currentDistance / distance);

            // Calculate the location to check
            MutableVector3 checkLoc = new MutableVector3(blockVector).add(moveX, moveY, moveZ);

            // Get the block state at the checked location
            BlockState blockState = editSession.getBlock(checkLoc.getBlockX(), checkLoc.getBlockY(), checkLoc.getBlockZ());

            // If the block is not air (i.e., it's a solid block), return false
            if (blockState.getBlockType() != BlockTypes.AIR) {
                return false;
            }
        }

        // If no solid blocks were encountered, return true (the block is on the surface)
        return true;
    }
}
