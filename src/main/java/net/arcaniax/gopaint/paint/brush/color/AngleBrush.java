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
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockType;
import net.arcaniax.gopaint.paint.brush.ColorBrush;
import net.arcaniax.gopaint.paint.brush.settings.BrushSettings;
import net.arcaniax.gopaint.paint.player.AbstractPlayerBrush;
import net.arcaniax.gopaint.utils.math.Height;
import net.arcaniax.gopaint.utils.math.Sphere;
import net.arcaniax.gopaint.utils.math.Surface;
import net.arcaniax.gopaint.utils.vectors.MutableVector3;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
    public void paintRight(AbstractPlayerBrush playerBrush, Location clickedPosition, Player player, EditSession editSession) {
        int brushSize = playerBrush.getBrushSize();
        List<BlockType> brushBlocks = playerBrush.getBlocks();

        if (brushBlocks.isEmpty()) {
            return;
        }

        List<MutableVector3> affectedBlocks = Sphere.getBlocksInRadius(new MutableVector3(clickedPosition), brushSize, editSession);
        for (MutableVector3 blockLocation : affectedBlocks) {
            // Skip if surface mode is enabled
            if(!canPlace(editSession, blockLocation, playerBrush, clickedPosition)) continue;

            double blockHeightDifference = Height.getAverageHeightDiffAngle(blockLocation, 1, editSession);
            double maxAllowedAngle = Math.tan(Math.toRadians(playerBrush.getMinHeightDifference()));
            double currentAngle = Height.getAverageHeightDiffAngle(blockLocation, playerBrush.getAngleDistance(), editSession);

            // Skip if the angle condition is not met
            if (blockHeightDifference >= 0.1 && currentAngle >= maxAllowedAngle) {
                continue;
            }

            // Randomly select a block type
            Random random = new Random();
            int randomIndex = random.nextInt(brushBlocks.size());

            // Check if it's allowed to replace the block (isGmask method)
            if (!isGmask(editSession, blockLocation.toBlockPoint())) {
                continue;
            }

            try {
                // Set the block to a randomly selected block type
                editSession.setBlock(
                        blockLocation.getBlockX(), blockLocation.getBlockY(), blockLocation.getBlockZ(),
                        brushBlocks.get(randomIndex).getDefaultState()
                );
            } catch (Exception ignored) {
                // Handle any exceptions that may occur during block placement
            }
        }
    }

}


