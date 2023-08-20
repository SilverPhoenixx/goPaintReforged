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

import java.util.ArrayList;
import java.util.List;

public class Sphere {
    /**
     * Create a list with blocks in a entire sphere and check if the block is not air
     * @param middlePoint of the sphere (Cloned)
     * @param radius of the sphere
     * @return list with solid blocks in the sphere (no air)
     */
    public static List<MutableVector3> getBlocksInRadius(MutableVector3 middlePoint, double radius, EditSession editSession) {
        List<MutableVector3> locations = new ArrayList<>();
        for (MutableVector3 currentLocation : getBlocksInRadiusWithAir(middlePoint, radius, editSession)) {
            BlockState blockState = editSession.getBlock(currentLocation.getBlockX(), currentLocation.getBlockY(),
                    currentLocation.getBlockZ());
            if (blockState.getBlockType() != BlockTypes.AIR) {
                locations.add(currentLocation);
            }
        }
        return locations;
    }


    /**
     * Create a list with blocks in a entire sphere
     * @param middlePoint of the sphere
     * @param radius of the sphere
     * @return list with blocks in the sphere (with air)
     */
    public static List<MutableVector3> getBlocksInRadiusWithAir(MutableVector3 middlePoint, double radius, EditSession editSession) {
        List<MutableVector3> locations = new ArrayList<>();
        MutableVector3 loc1 = middlePoint.clone().add(-radius / 2, -radius / 2, -radius / 2);
        MutableVector3 loc2 = middlePoint.clone().add(+radius / 2, +radius / 2, +radius / 2);
        for (double x = loc1.getX(); x <= loc2.getX(); x++) {
            for (double y = loc1.getY(); y <= loc2.getY(); y++) {
                for (double z = loc1.getZ(); z <= loc2.getZ(); z++) {
                    MutableVector3 loc = new MutableVector3(x, y, z);
                    if (loc.distance(middlePoint) < (radius / 2)) {
                        locations.add(loc);
                    }
                }
            }
        }
        return locations;
    }

}
