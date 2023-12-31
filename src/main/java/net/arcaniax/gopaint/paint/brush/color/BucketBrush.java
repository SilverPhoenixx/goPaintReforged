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
import net.arcaniax.gopaint.utils.blocks.ConnectedBlocks;
import net.arcaniax.gopaint.utils.math.Sphere;
import net.arcaniax.gopaint.utils.math.Surface;
import net.arcaniax.gopaint.utils.vectors.MutableVector3;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class BucketBrush extends ColorBrush {

    public BucketBrush() throws Exception {
        super(new BrushSettings[] {
            BrushSettings.SIZE
        });
    }

    @Override
    public String getName() {
        return "Bucket Brush";
    }

    @Override
    public String getSkin() {
        return  "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTAxOGI0NTc0OTM5Nzg4YTJhZDU1NTJiOTEyZDY3ODEwNjk4ODhjNTEyMzRhNGExM2VhZGI3ZDRjOTc5YzkzIn19fQ==";
    }

    @Override
    public String[] getDescription() {
        return new String[] {"§7Paints connected blocks", "§7with the same block type"};
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
        List<MutableVector3> blocks = Sphere.getBlocksInRadius(clickedVector, brushSize, editSession);

        // Find connected blocks within the edit session
        List<MutableVector3> connectedBlocks = ConnectedBlocks.getConnectedBlocks(clickedVector, blocks, editSession);
        MutableVector3 playerVector = new MutableVector3(player.getLocation());

        Random random = new Random();

        for (MutableVector3 blockLocation : connectedBlocks) {
            // Check if surface mode is enabled and the block is not on the surface, skip if true
            if(!canPlace(editSession, blockLocation, playerBrush, clickedVector)) {
                continue;
            }

            playerBrush.getPlacement().place(editSession, blockLocation, clickedVector, random, playerBrush);
        }
    }
}
