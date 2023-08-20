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
import net.arcaniax.gopaint.paint.brush.settings.BrushSettings;
import net.arcaniax.gopaint.paint.player.AbstractPlayerBrush;
import net.arcaniax.gopaint.paint.player.PlayerBrush;
import net.arcaniax.gopaint.utils.math.Sphere;
import net.arcaniax.gopaint.utils.math.curve.BezierSpline;
import net.arcaniax.gopaint.utils.vectors.MutableVector3;
import org.bukkit.Location;
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

    private static final HashMap<String, List<MutableVector3>> selectedPoints;

    @Override
    public void paintRight(AbstractPlayerBrush playerBrush, Location clickedPosition, Player player, EditSession editSession) {
        final String prefix = GoPaint.getSettings().getPrefix();

        if (!PaintBrush.selectedPoints.containsKey(player.getName())) {
            final List<MutableVector3> locs = new LinkedList<>();
            locs.add(new MutableVector3(clickedPosition));
            PaintBrush.selectedPoints.put(player.getName(), locs);
            player.sendMessage(prefix + " Paint brush point #1 set.");
            return;
        }

        if (!player.isSneaking()) {
            final List<MutableVector3> locs = PaintBrush.selectedPoints.get(player.getName());
            locs.add(new MutableVector3(clickedPosition));
            PaintBrush.selectedPoints.put(player.getName(), locs);
            player.sendMessage(prefix + " Paint brush point #" + locs.size() + " set.");
            return;
        }

        final PlayerBrush pb = GoPaint.getBrushManager().getPlayerBrush(player);
        final List<BlockType> pbBlocks = pb.getBlocks();

        if (pbBlocks.isEmpty()) {
            return;
        }

        final List<MutableVector3> locs = PaintBrush.selectedPoints.get(player.getName());
        locs.add(new MutableVector3(clickedPosition));
        PaintBrush.selectedPoints.remove(player.getName());

        final int size = pb.getBrushSize();
        final int falloff = pb.getFalloffStrength();

        final List<MutableVector3> blocks = Sphere.getBlocksInRadiusWithAir(locs.get(0), size, editSession);
        for (final MutableVector3 blockLocation : blocks) {
            final Random r = new Random();
            final int random = r.nextInt(pbBlocks.size());
            final double rate = (blockLocation
                    .distance(locs.get(0)) - size / 2.0 * ((100.0 - falloff) / 100.0)) / (size / 2.0 - size / 2.0 * ((100.0 - falloff) / 100.0));
            if (r.nextDouble() < rate) {
                continue;
            }

            final LinkedList<MutableVector3> newCurve = new LinkedList<>();
            int amount = 0;
            for (final MutableVector3 l : locs) {
                if (amount == 0) {
                    newCurve.add(blockLocation.clone());
                    amount++;
                    continue;
                }

                MutableVector3 newLocation = blockLocation.clone().add(
                        l.getX() - locs.get(0).getX(),
                        l.getY() - locs.get(0).getY(),
                        l.getZ() - locs.get(0).getZ()
                );

                newCurve.add(newLocation);
                ++amount;
            }

            final BezierSpline bs = new BezierSpline(newCurve);
            final double length = bs.getCurveLength();
            for (int maxCount = (int) (length * 2.5) + 1, y = 0; y <= maxCount; ++y) {
                final MutableVector3 currentLocation = bs.getPoint(y / (double) maxCount * (locs.size() - 1));

                if (!canPlaceWithAir(editSession, currentLocation, playerBrush, clickedPosition)) {
                    continue;
                }

                editSession.setBlock(
                        currentLocation.getBlockX(),
                        currentLocation.getBlockY(),
                        currentLocation.getBlockZ(),
                        pb.getBlocks().get(random)
                );
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
        selectedPoints = new HashMap<>();
    }

}
