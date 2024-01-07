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
import net.arcaniax.gopaint.paint.brush.player.AbstractPlayerBrush;
import net.arcaniax.gopaint.paint.settings.BrushSettings;
import net.arcaniax.gopaint.utils.math.Surface;
import net.arcaniax.gopaint.utils.vectors.MutableVector3;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class BiomeBrush extends Brush {

    public BiomeBrush(final BrushSettings[] settings) throws Exception {
        super(settings);
    }

    /**
     * Update chunks in the world to refresh biome information.
     *
     * @param player  The player for getting the world information
     * @param chunks  A list of pairs representing chunk coordinates (X, Z).
     */
    public void update(Player player, List<Pair<Integer, Integer>> chunks) {
        World world = player.getWorld();

        // Loop through the provided chunks and refresh them
        chunks.forEach(chunk -> {
            int x = chunk.getLeft();
            int z = chunk.getRight();

            // Check if the chunk is loaded before refreshing it
            if (world.isChunkLoaded(x, z)) {
                world.refreshChunk(x, z);
            }
        });
    }

    /**
     * Check if a block can be placed at a given location.
     *
     * @param editSession    The EditSession to check block placement.
     * @param blockVector  The vector representing the block's location.
     * @param playerBrush    The player's brush settings.
     * @param clickedVector  The vector representing the location clicked by the player.
     * @return True if the block can be placed, otherwise false.
     */
    public boolean canPlace(EditSession editSession, MutableVector3 blockVector, AbstractPlayerBrush playerBrush,
                            MutableVector3 clickedVector) {
        BlockState block = editSession.getBlock(blockVector.getBlockX(), blockVector.getBlockY(),
                blockVector.getBlockZ()
        );

        if (playerBrush.isSurfaceModeEnabled()
                && !Surface.isOnSurface(blockVector.clone(), clickedVector, editSession)) {
            return false; // Skip blocks that don't meet surface mode condition
        }

        if(playerBrush.isMaskEnabled()) {
            if (block.getBlockType() != playerBrush.getMask()) return false;
        }

        return isGmask(editSession, blockVector); // Skip blocks that don't meet the mask condition
    }

}
