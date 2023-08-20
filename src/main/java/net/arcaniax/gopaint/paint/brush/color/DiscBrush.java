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

public class DiscBrush extends ColorBrush {

    public DiscBrush() throws Exception {
        super(new BrushSettings[]{
                BrushSettings.SIZE,
                BrushSettings.AXIS
        });
    }


    @Override
    public String getName() {
        return "Disc Brush";
    }

    @Override
    public String[] getDescription() {
        return new String[]{"§7Paints blocks in the", "§7same selected axis", "§7from the block you clicked"};
    }

    @Override
    public String getSkin() {
        return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjFmMjgyNTBkMWU0MjBhNjUxMWIwMzk2NDg2OGZjYTJmNTYzN2UzYWJhNzlmNGExNjNmNGE4ZDYxM2JlIn19fQ==";
    }

    @Override
    public void paintRight(AbstractPlayerBrush playerBrush, Location clickedPosition, Player player, EditSession editSession) {
        int brushSize = playerBrush.getBrushSize();
        List<BlockType> brushMaterials = playerBrush.getBlocks();

        if (brushMaterials.isEmpty()) {
            return;
        }

        List<MutableVector3> blocks = Sphere.getBlocksInRadius(new MutableVector3(clickedPosition), brushSize, editSession);
        for (MutableVector3 blockLocation : blocks) {


            if (!((playerBrush.getAxis().equals("y") && blockLocation.getBlockY() == clickedPosition.getBlockY())
                    || (playerBrush.getAxis().equals("x") && blockLocation.getBlockX() == clickedPosition.getBlockX())
                    || (playerBrush.getAxis().equals("z") && blockLocation.getBlockZ() == clickedPosition.getBlockZ()))) {
                continue; // Skip blocks that don't match the axis condition
            }

            if (!canPlace(editSession, blockLocation, playerBrush, clickedPosition)) {
                continue;
            }

            Random random = new Random();
            int randomIndex = random.nextInt(brushMaterials.size());

            if (!isGmask(editSession, blockLocation.toBlockPoint())) {
                continue;
            }

            try {
                editSession.setBlock(
                        blockLocation.getBlockX(), blockLocation.getBlockY(), blockLocation.getBlockZ(),
                        brushMaterials.get(randomIndex).getDefaultState()
                );
            } catch (Exception ignored) {
                // Handle any exceptions that may occur during block placement
            }
        }
    }

}
