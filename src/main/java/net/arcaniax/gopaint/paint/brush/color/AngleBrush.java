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
import net.arcaniax.gopaint.paint.settings.BrushSettings;
import net.arcaniax.gopaint.paint.brush.player.AbstractPlayerBrush;
import net.arcaniax.gopaint.utils.math.Height;
import net.arcaniax.gopaint.utils.math.Sphere;
import net.arcaniax.gopaint.utils.vectors.MutableVector3;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class AngleBrush extends ColorBrush {

    public AngleBrush() throws Exception {
        super(new BrushSettings[]{
                BrushSettings.SIZE,
                BrushSettings.ANGLE_DISTANCE,
                BrushSettings.ANGLE_HEIGHT
        });
    }

    @Override
    public String getName() {
        return "Angle Brush";
    }

    @Override
    public String[] getDescription() {
        return new String[]{"§7Only works on cliffs"};
    }

    @Override
    public String getSkin() {
        return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRlNDQ4ZjBkYmU3NmJiOGE4MzJjOGYzYjJhMDNkMzViZDRlMjc4NWZhNWU4Mjk4YzI2MTU1MDNmNDdmZmEyIn19fQ==";
    }

    @Override
    public void paintRight(AbstractPlayerBrush playerBrush, MutableVector3 clickedVector, Player player, EditSession editSession) {
        // If there are no blocks to place, exit the method
        List<BlockType> brushBlocks = playerBrush.getBlocks();
        if (brushBlocks.isEmpty()) {
            return;
        }

        // Get a list of block locations within the specified radius around the clicked vector
        int brushSize = playerBrush.getBrushSize();
        List<MutableVector3> affectedBlocks = Sphere.getBlocksInRadius(clickedVector, brushSize, editSession);

        Random random = new Random();

        for (MutableVector3 blockLocation : affectedBlocks) {
            // Skip if surface mode is enabled (canPlace method)
            if (!canPlace(editSession, blockLocation, playerBrush, clickedVector)) {
                continue;
            }

            // Calculate the block's average height difference with nearby blocks
            double blockHeightDifference = Height.getAverageHeightDiffAngle(blockLocation, 1, editSession);

            // Calculate the maximum allowed angle based on the brush configuration
            double maxAllowedAngle = Math.tan(Math.toRadians(playerBrush.getMinHeightDifference()));

            // Calculate the current angle of height difference for the block
            double currentAngle = Height.getAverageHeightDiffAngle(blockLocation, playerBrush.getAngleDistance(), editSession);

            // Skip if the angle condition is not met
            if (blockHeightDifference >= 0.1 && currentAngle >= maxAllowedAngle) {
                continue;
            }

            playerBrush.getPlacement().place(editSession, blockLocation, clickedVector, random, playerBrush);
        }
    }


}


