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
package net.arcaniax.gopaint.paint.brush.color;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.world.block.BlockType;
import net.arcaniax.gopaint.paint.brush.ColorBrush;
import net.arcaniax.gopaint.paint.brush.settings.BrushSettings;
import net.arcaniax.gopaint.paint.player.AbstractPlayerBrush;
import net.arcaniax.gopaint.utils.math.Sphere;
import net.arcaniax.gopaint.utils.vectors.MutableVector3;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class GradientBrush extends ColorBrush {

    public GradientBrush() throws Exception {
        super(new BrushSettings[]{
                BrushSettings.SIZE,
                BrushSettings.FALLOFF_STRENGTH,
                BrushSettings.MIXING
        });
    }

    @Override
    public String getName() {
        return "Gradient Brush";
    }

    @Override
    public String[] getDescription() {
        return new String[]{"§7Creates gradients"};
    }

    @Override
    public String getSkin() {
        return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjA2MmRhM2QzYjhmMWZkMzUzNDNjYzI3OWZiMGZlNWNmNGE1N2I1YWJjNDMxZmJiNzhhNzNiZjJhZjY3NGYifX19";
    }

    @Override
    public void paintRight(AbstractPlayerBrush playerBrush, MutableVector3 clickedVector, Player player, EditSession editSession) {
        // If there are no blocks to place, exit the method
        List<BlockType> brushBlocks = playerBrush.getBlocks();
        if (brushBlocks.isEmpty()) {
            return;
        }

        // Get a list of block locations within the specified radius
        int brushSize = playerBrush.getBrushSize();
        List<MutableVector3> blocks = Sphere.getBlocksInRadius(clickedVector, brushSize, editSession);

        int falloff = playerBrush.getFalloffStrength();
        int mixing = playerBrush.getMixingStrength();
        Random random = new Random();

        // Calculate the base Y coordinate for block placement
        double y = clickedVector.getBlockY() - ((double) brushSize / 2.0);

        for (MutableVector3 blockLocation : blocks) {
            // Check if we can place a block at this location, if not, skip to the next location
            if (!canPlace(editSession, blockLocation, playerBrush, clickedVector)) {
                continue;
            }

            if (!isGmask(editSession, blockLocation.toBlockPoint())) {
                continue;
            }

            // Calculate the relative Y coordinate to determine the block index
            double _y = (blockLocation.getBlockY() - y) / (double) brushSize * brushBlocks.size();

            // Calculate a random offset for block selection based on mixing strength
            int randomBlock = (int) (_y + (random.nextDouble() * 2 - 1) * ((double) mixing / 100.0));

            // Ensure the block index is within valid bounds
            randomBlock = Math.max(0, Math.min(randomBlock, brushBlocks.size() - 1));

            // Calculate the rate of block placement based on falloff strength and distance
            double rate = (blockLocation.distance(clickedVector) - ((double) brushSize / 2.0) * ((100.0 - (double) falloff) / 100.0)) /
                    (((double) brushSize / 2.0) - ((double) brushSize / 2.0) * ((100.0 - (double) falloff) / 100.0));

            // Check if the random value falls within the placement rate
            if ((random.nextDouble() <= rate)) {
                continue;
            }

            // Set the block at the current location to the randomly chosen block type
            try {
                editSession.setBlock(
                        blockLocation.getBlockX(), blockLocation.getBlockY(), blockLocation.getBlockZ(),
                        brushBlocks.get(randomBlock).getDefaultState()
                );
            } catch (Exception ignored) {
                // Handle any exceptions that may occur during block placement
            }
        }
    }


}
