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

import net.arcaniax.gopaint.paint.brush.settings.BrushSettings;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class BiomeBrush extends Brush {

    public BiomeBrush(final BrushSettings[] settings) throws Exception {
        super(settings);
    }

    public void update(Player player, List<Pair<Integer, Integer>> chunks) {
        World world = player.getWorld();

        chunks.forEach(chunk -> {
            int x = chunk.getLeft();
            int z = chunk.getRight();

            // Not a good option but it works
            if (world.isChunkLoaded(x, z)) {
                world.refreshChunk(x, z);
            }
        });
    }
    /*
    public void update(Player player, List<Pair<Integer, Integer>> chunks) {
        World world = player.getWorld();

        for(Pair<Integer, Integer> chunkCoords : chunks) {
            Chunk bukkitChunk = player.getWorld().getChunkAt(chunkCoords.getLeft(), chunkCoords.getRight());
            LevelChunk minecraftChunk =
                    (LevelChunk) ((CraftChunk) bukkitChunk).getHandle(ChunkStatus.BIOMES);

            ClientboundLevelChunkWithLightPacket packet = new ClientboundLevelChunkWithLightPacket(minecraftChunk,
                    minecraftChunk.getLevel().getLightEngine(), null, null);


            for(Player worldPlayer : player.getWorld().getPlayers())
            ((CraftPlayer) worldPlayer).getHandle().connection.send(packet);
        }
    }*/
}
