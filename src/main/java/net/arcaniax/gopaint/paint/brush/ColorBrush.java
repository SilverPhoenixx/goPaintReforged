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
package net.arcaniax.gopaint.paint.brush;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockTypes;
import net.arcaniax.gopaint.paint.brush.settings.BrushSettings;
import net.arcaniax.gopaint.paint.player.AbstractPlayerBrush;
import net.arcaniax.gopaint.utils.math.Surface;
import net.arcaniax.gopaint.utils.vectors.MutableVector3;
import org.bukkit.Location;

public abstract class ColorBrush extends Brush {

    public ColorBrush(final BrushSettings[] settings) throws Exception {
        super(settings);
    }

    public boolean canPlace(EditSession editSession, MutableVector3 blockLocation, AbstractPlayerBrush playerBrush, Location clickedPosition) {
        BlockState block = editSession.getBlock(blockLocation.getBlockX(), blockLocation.getBlockY(),
                blockLocation.getBlockZ()
        );

        if (playerBrush.isSurfaceModeEnabled() && !Surface.isOnSurface(blockLocation.clone(),
                new MutableVector3(clickedPosition), editSession
        )) {
            return false; // Skip blocks that don't meet surface mode condition
        }

        if (playerBrush.isMaskEnabled() && block.getBlockType() != playerBrush.getMask()) {
            return false; // Skip blocks that don't meet the mask condition
        }
        return true;
    }

    public boolean canPlaceWithAir(EditSession editSession, MutableVector3 blockLocation, AbstractPlayerBrush playerBrush,
                             Location clickedPosition) {
        BlockState block = editSession.getBlock(blockLocation.getBlockX(), blockLocation.getBlockY(),
                blockLocation.getBlockZ()
        );

        if(block.isAir()) return false;

        if (playerBrush.isSurfaceModeEnabled() && !Surface.isOnSurface(blockLocation.clone(),
                new MutableVector3(clickedPosition), editSession
        )) {
            return false; // Skip blocks that don't meet surface mode condition
        }

        if (playerBrush.isMaskEnabled() && block.getBlockType() != playerBrush.getMask()) {
            return false; // Skip blocks that don't meet the mask condition
        }
        return true;
    }

}
