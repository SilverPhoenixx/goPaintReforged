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

public class SplatterBrush extends ColorBrush {

    public SplatterBrush() throws Exception {
        super(new BrushSettings[]{
                BrushSettings.SIZE,
                BrushSettings.FALLOFF_STRENGTH
        });
    }

    @Override
    public void paintRight(AbstractPlayerBrush playerBrush, MutableVector3 clickedVector, Player player, EditSession editSession) {
        // Check if there are no block types to paint.
        List<BlockType> brushBlocks = playerBrush.getBlocks();
        if (brushBlocks.isEmpty()) {
            return;
        }

        // Get a list of block positions within a spherical radius (brush size).
        int brushSize = playerBrush.getBrushSize();
        List<MutableVector3> blocks = Sphere.getBlocksInRadius(clickedVector, brushSize, editSession);

        int falloff = playerBrush.getFalloffStrength();
        Random random = new Random();

        for (MutableVector3 blockLocation : blocks) {

            // Check if this position is suitable for placing a block. If not, skip.
            if (!canPlace(editSession, blockLocation, playerBrush, clickedVector)) {
                continue;
            }

            if (!isGmask(editSession, blockLocation.toBlockPoint())) {
                continue;
            }

            // Calculate a rate based on the distance of the block from the clicked position and the falloff strength.
            double rate = (blockLocation.distance(clickedVector) - ((double) brushSize / 2.0) * ((100.0 - (double) falloff) / 100.0))
                    / (((double) brushSize / 2.0) - ((double) brushSize / 2.0) * ((100.0 - (double) falloff) / 100.0));

            // If a random number is less than or equal to the calculated rate, skip this block.
            if (random.nextDouble() <= rate) {
                continue;
            }

            playerBrush.getPlacement().place(editSession, blockLocation, clickedVector, random, playerBrush);
        }
    }


    @Override
    public String getName() {
        return "Splatter Brush";
    }

    @Override
    public String[] getDescription() {
        return new String[]{"§7More chance when closer", "§7to the clicked point", "§7and configurable chance"};
    }

    @Override
    public String getSkin() {
        return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzMzODI5MmUyZTY5ZjA5MDY5NGNlZjY3MmJiNzZmMWQ4Mzc1OGQxMjc0NGJiNmZmYzY4MzRmZGJjMWE5ODMifX19";
    }

}
