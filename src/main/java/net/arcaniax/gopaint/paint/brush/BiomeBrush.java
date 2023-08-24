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

import net.arcaniax.gopaint.paint.settings.BrushSettings;
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
}
