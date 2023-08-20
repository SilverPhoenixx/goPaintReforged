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
     * Checks if the block is on surface
     *
     * @param blockLocation  checked location (Cloned)
     * @param playerLocation player location (Cloned)
     * @return true when its on surface and false when its not the surface
     */
    public static boolean isOnSurface(MutableVector3 blockLocation, MutableVector3 playerLocation, EditSession editSession) {
        playerLocation.addY(1.5);
        double distanceX = playerLocation.getX() - blockLocation.getX();
        double distanceY = playerLocation.getY() - blockLocation.getY();
        double distanceZ = playerLocation.getZ() - blockLocation.getZ();

        // Change x coordinate
        if (distanceX > 1) {
            blockLocation.addX(1);
        } else if (distanceX > 0) {
            blockLocation.addX(0.5);
        }

        // Change y coordinate
        if (distanceY > 1) {
            blockLocation.addY(1);
        } else if (distanceY > 0) {
            blockLocation.addY(0.5);
        }

        // Change z coordinate
        if (distanceZ > 1) {
            blockLocation.addZ(1);
        } else if (distanceZ > 0) {
            blockLocation.addZ(0.5);
        }

        double distance = blockLocation.distance(playerLocation);
        for (int currentDistance = 1; currentDistance < distance; currentDistance++) {
            double moveX = distanceX * (currentDistance / distance);
            double moveY = distanceY * (currentDistance / distance);
            double moveZ = distanceZ * (currentDistance / distance);

            MutableVector3 checkLoc = new MutableVector3(blockLocation).add(moveX, moveY, moveZ);
            BlockState blockState = editSession.getBlock(checkLoc.getBlockX(), checkLoc.getBlockY(), checkLoc.getBlockZ());

            if (blockState.getBlockType() != BlockTypes.AIR) {
                return false;
            }
        }

        return true;
    }

}
