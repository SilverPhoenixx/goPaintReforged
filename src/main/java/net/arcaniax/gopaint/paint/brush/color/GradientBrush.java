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
    public void paintRight(AbstractPlayerBrush playerBrush, Location clickedPosition, Player player, EditSession editSession) {

        MutableVector3 mutableLocation = new MutableVector3(clickedPosition);

        int size = playerBrush.getBrushSize();
        int falloff = playerBrush.getFalloffStrength();
        int mixing = playerBrush.getMixingStrength();
        List<BlockType> pbBlocks = playerBrush.getBlocks();

        if (pbBlocks.isEmpty()) {
            return;
        }

        List<MutableVector3> blocks = Sphere.getBlocksInRadius(mutableLocation.clone(), size, editSession);
        double y = clickedPosition.getBlockY() - ((double) size / 2.0);
        Random r = new Random();

        for (MutableVector3 blockLocation : blocks) {
            double _y = (blockLocation.getBlockY() - y) / (double) size * pbBlocks.size();
            int blockIndex = (int) (_y + (r.nextDouble() * 2 - 1) * ((double) mixing / 100.0));
            blockIndex = Math.max(0, Math.min(blockIndex, pbBlocks.size() - 1));

            if(!canPlace(editSession, blockLocation, playerBrush, clickedPosition)) continue;

            double rate = (blockLocation.distance(mutableLocation) - ((double) size / 2.0) * ((100.0 - (double) falloff) / 100.0)) /
                    (((double) size / 2.0) - ((double) size / 2.0) * ((100.0 - (double) falloff) / 100.0));

            if ((r.nextDouble() <= rate)) {
                continue;
            }

            if (!isGmask(editSession, blockLocation.toBlockPoint())) {
                continue;
            }

            try {
                editSession.setBlock(
                        blockLocation.getBlockX(), blockLocation.getBlockY(), blockLocation.getBlockZ(),
                        pbBlocks.get(blockIndex).getDefaultState()
                );
            } catch (Exception ignored) {
                // Handle any exceptions that may occur during block placement
            }

        }
    }

}
