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
import net.arcaniax.gopaint.GoPaint;
import net.arcaniax.gopaint.paint.brush.ColorBrush;
import net.arcaniax.gopaint.paint.settings.BrushSettings;
import net.arcaniax.gopaint.paint.brush.player.AbstractPlayerBrush;
import net.arcaniax.gopaint.utils.math.Sphere;
import net.arcaniax.gopaint.utils.math.curve.BezierSpline;
import net.arcaniax.gopaint.utils.vectors.MutableVector3;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PaintBrush extends ColorBrush {

    public PaintBrush() throws Exception {
        super(new BrushSettings[]{
                BrushSettings.SIZE,
                BrushSettings.FALLOFF_STRENGTH
        });
    }

    private static final HashMap<String, List<MutableVector3>> SELECTED_POINTS;

    @Override
    public void paintRight(
            AbstractPlayerBrush playerBrush,
            MutableVector3 clickedVector,
            Player player,
            EditSession editSession
    ) {
        // Get the prefix for messages from settings
        String prefix = GoPaint.getSettings().getPrefix();
        String playerName = player.getName();

        if (!SELECTED_POINTS.containsKey(playerName) || !player.isSneaking()) {
            List<MutableVector3> locs = SELECTED_POINTS.getOrDefault(playerName, new LinkedList<>());
            locs.add(clickedVector);
            SELECTED_POINTS.put(playerName, locs);
            int pointNumber = locs.size();
            player.sendMessage(prefix + " Paint brush point #" + pointNumber + " set.");
            return;
        }

        List<MutableVector3> locs = SELECTED_POINTS.get(player.getName());
        SELECTED_POINTS.remove(player.getName());

        // Check if the brush has no blocks
        List<BlockType> brushBlocks = playerBrush.getBlocks();
        if (brushBlocks.isEmpty()) {
            return;
        }

        // Get the blocks in a spherical radius around the first selected point
        int brushSize = playerBrush.getBrushSize();
        List<MutableVector3> blocks = Sphere.getBlocksInRadiusWithAir(locs.get(0), brushSize, editSession);

        int falloff = playerBrush.getFalloffStrength();

        Random random = new Random();

        for (MutableVector3 blockLocation : blocks) {


            // Calculate the rate at which to place blocks based on distance and falloff
            double rate = (blockLocation.distance(locs.get(0)) - brushSize / 2.0 * ((100.0 - falloff) / 100.0)) /
                    (brushSize / 2.0 - brushSize / 2.0 * ((100.0 - falloff) / 100.0));

            // Check if a randomly generated value is less than the rate, if so, continue to the next block
            if (random.nextDouble() < rate) {
                continue;
            }

            LinkedList<MutableVector3> newCurve = new LinkedList<>();
            boolean start = true;
            for (MutableVector3 l : locs) {
                if (start) {
                    newCurve.add(blockLocation.clone());
                    start = false;
                    continue;
                }

                // Calculate the new location for the current point in the selection
                MutableVector3 newLocation = blockLocation.clone().add(
                        l.getX() - locs.get(0).getX(),
                        l.getY() - locs.get(0).getY(),
                        l.getZ() - locs.get(0).getZ()
                );

                newCurve.add(newLocation);
            }

            // Create a Bezier spline from the new curve
            BezierSpline bs = new BezierSpline(newCurve);
            double length = bs.getCurveLength();

            // Iterate over points on the spline and place blocks if conditions are met
            for (int maxCount = (int) (length * 2.5) + 1, y = 0; y <= maxCount; ++y) {
                MutableVector3 currentLocation = bs.getPoint(y / (double) maxCount * (locs.size() - 1));

                // Check if a block can be placed at the current location
                if (!canPlace(editSession, currentLocation, playerBrush, clickedVector)) {
                    continue;
                }

                playerBrush.getPlacement().place(editSession, currentLocation, clickedVector, random, playerBrush);
            }
        }
    }



    @Override
    public String getName() {
        return "Paint Brush";
    }

    @Override
    public String[] getDescription() {
        return new String[]{"§7Paints strokes", "§7hold shift to end"};
    }

    @Override
    public String getSkin() {
        return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODBiM2E5ZGZhYmVmYmRkOTQ5YjIxN2JiZDRmYTlhNDg2YmQwYzNmMGNhYjBkMGI5ZGZhMjRjMzMyZGQzZTM0MiJ9fX0=";
    }

    static {
        SELECTED_POINTS = new HashMap<>();
    }

}
