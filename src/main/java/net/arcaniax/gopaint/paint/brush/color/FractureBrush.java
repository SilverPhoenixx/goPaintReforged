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

import com.sk89q.worldedit.EditSession;;
import com.sk89q.worldedit.world.block.BlockType;
import net.arcaniax.gopaint.paint.brush.ColorBrush;
import net.arcaniax.gopaint.paint.settings.BrushSettings;
import net.arcaniax.gopaint.paint.brush.player.AbstractPlayerBrush;
import net.arcaniax.gopaint.utils.math.Height;
import net.arcaniax.gopaint.utils.math.Sphere;
import net.arcaniax.gopaint.utils.vectors.MutableVector3;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class FractureBrush extends ColorBrush {

    public FractureBrush() throws Exception {
        super(new BrushSettings[]{
                BrushSettings.SIZE,
                BrushSettings.FRACTURE
        });
    }

    @Override
    public String getName() {
        return "Fracture Brush";
    }

    @Override
    public String[] getDescription() {
        return new String[]{"§7Places blocks in cracks/fisures"};
    }

    @Override
    public String getSkin() {
        return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjNkZjczZWVlNjIyNGM1YzVkOTQ4ZDJhMzQ1ZGUyNWYyMDhjYmQ5YWY3MTA4Y2UxZTFiNjFhNTg2ZGU5OGIyIn19fQ==";
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

        Random random = new Random();

        for (MutableVector3 blockLocation : blocks) {
            // Check if we can place a block at this location, if not, skip to the next location
            if (!canPlace(editSession, blockLocation, playerBrush, clickedVector)) {
                continue;
            }

            // Calculate the average height difference for the current block and its surroundings
            double blockHeightDiffFracture = Height.getAverageHeightDiffFracture(
                    blockLocation,
                    Height.getHeight(blockLocation, editSession),
                    1,
                    editSession
            );

            // Calculate the average height difference over a specified distance
            double fractureDistanceHeightDiff = Height.getAverageHeightDiffFracture(
                    blockLocation,
                    Height.getHeight(blockLocation.clone(), editSession),
                    playerBrush.getFractureDistance(),
                    editSession
            );

            // Check if the height differences meet a certain threshold (0.1)
            if (!(blockHeightDiffFracture >= 0.1 && fractureDistanceHeightDiff >= 0.1)) {
                continue;
            }

            playerBrush.getPlacement().place(editSession, blockLocation, clickedVector, random, playerBrush);
        }
    }
}
