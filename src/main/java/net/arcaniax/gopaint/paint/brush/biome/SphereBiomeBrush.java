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
package net.arcaniax.gopaint.paint.brush.biome;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.world.biome.BiomeType;
import net.arcaniax.gopaint.paint.brush.BiomeBrush;
import net.arcaniax.gopaint.paint.settings.BrushSettings;
import net.arcaniax.gopaint.paint.brush.player.AbstractPlayerBrush;
import net.arcaniax.gopaint.utils.math.Sphere;
import net.arcaniax.gopaint.utils.vectors.MutableVector3;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SphereBiomeBrush extends BiomeBrush {

    public SphereBiomeBrush() throws Exception {
        super(new BrushSettings[]{BrushSettings.SIZE});
    }

    @Override
    public void paintRight(AbstractPlayerBrush playerBrush, MutableVector3 clickedVector, Player player,
                           EditSession editSession) {
        // If there are no biome types to place, exit the method
        List<BiomeType> brushBiomes = playerBrush.getBiomeTypes();
        if (brushBiomes.isEmpty()) {
            return;
        }

        // Get a list of block locations within the specified radius around the clicked vector, including air blocks
        int size = playerBrush.getBrushSize();
        List<MutableVector3> blocks = Sphere.getBlocksInRadiusWithAir(clickedVector, size, editSession);

        // Create a list to track unique chunk coordinates
        List<Pair<Integer, Integer>> chunks = new ArrayList<>();

        Random random = new Random();

        for (MutableVector3 blockLocation : blocks) {

            // Check if we can place a block at this location, if not, skip to the next location
            if(!canPlace(editSession, blockLocation, playerBrush, clickedVector)) {
                continue;
            }

            // Generate a random index to choose a biome type from the available biome types
            int randomBlock = random.nextInt(brushBiomes.size());

            try {
                // Get the chunk coordinates for the current block
                int chunkX = blockLocation.getChunkX();
                int chunkZ = blockLocation.getChunkZ();
                Pair<Integer, Integer> chunkCoords = Pair.of(chunkX, chunkZ);

                // Add unique chunk coordinates to the list
                if (!chunks.contains(chunkCoords)) {
                    chunks.add(chunkCoords);
                }

                // Set the biome at the current block location to the randomly chosen biome type
                editSession.setBiome(
                        blockLocation.getBlockX(),
                        blockLocation.getBlockY(),
                        blockLocation.getBlockZ(),
                        brushBiomes.get(randomBlock)
                );

            } catch (Exception ignored) {
                // Handle any exceptions that may occur during biome placement
            }
        }

        // Commit the changes to the edit session
        editSession.commit();

        // Update the player with the affected chunks
        update(player, chunks);
    }


    @Override
    public String getName() {
        return "Biome Sphere Brush";
    }

    @Override
    public String[] getDescription() {
        return new String[]{"§7Regular Biome Sphere brush"};
    }

    @Override
    public String getSkin() {
        return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmU5OGY0ODU2MDE0N2MwYTJkNGVkYzE3ZjZkOTg1ZThlYjVkOTRiZDcyZmM2MDc0NGE1YThmMmQ5MDVhMTgifX19";
    }

}
