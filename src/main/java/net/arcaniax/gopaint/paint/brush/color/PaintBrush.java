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
import com.sk89q.worldedit.world.block.BlockTypes;
import net.arcaniax.gopaint.GoPaint;
import net.arcaniax.gopaint.paint.brush.ColorBrush;
import net.arcaniax.gopaint.paint.brush.settings.BrushSettings;
import net.arcaniax.gopaint.paint.player.AbstractPlayerBrush;
import net.arcaniax.gopaint.paint.player.PlayerBrush;
import net.arcaniax.gopaint.utils.math.Sphere;
import net.arcaniax.gopaint.utils.math.Surface;
import net.arcaniax.gopaint.utils.math.curve.BezierSpline;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PaintBrush extends ColorBrush {

    public PaintBrush() throws Exception {
        super(new BrushSettings[] {
                BrushSettings.SIZE,
                BrushSettings.FALLOFF_STRENGTH
        });
    }

    private static final HashMap<String, List<Location>> selectedPoints;

    @Override
    public void paintRight(AbstractPlayerBrush playerBrush, Location loc, Player p, EditSession session) {
        final String prefix = GoPaint.getSettings().getPrefix();
        if (!PaintBrush.selectedPoints.containsKey(p.getName())) {
            final List<Location> locs = new ArrayList<Location>();
            locs.add(loc);
            PaintBrush.selectedPoints.put(p.getName(), locs);
            p.sendMessage(prefix + " Paint brush point #1 set.");
        }
        else {
            if (!p.isSneaking()) {
                final List<Location> locs = PaintBrush.selectedPoints.get(p.getName());
                locs.add(loc);
                PaintBrush.selectedPoints.put(p.getName(), locs);
                p.sendMessage(prefix + " Paint brush point #" + locs.size() + " set.");
                return;
            }
            final List<Location> locs = PaintBrush.selectedPoints.get(p.getName());
            locs.add(loc);
            PaintBrush.selectedPoints.remove(p.getName());
            final PlayerBrush pb = GoPaint.getBrushManager().getPlayerBrush(p);
            final int size = pb.getBrushSize();
            final int falloff = pb.getFalloffStrength();
            final List<Material> pbBlocks = pb.getBlocks();
            if (pbBlocks.isEmpty()) {
                return;
            }
            final List<Block> blocks = Sphere.getBlocksInRadiusWithAir(locs.get(0), size);
            for (final Block b : blocks) {
                final Random r = new Random();
                final int random = r.nextInt(pbBlocks.size());
                final double rate = (b.getLocation().distance((Location)locs.get(0)) - size / 2.0 * ((100.0 - falloff) / 100.0)) / (size / 2.0 - size / 2.0 * ((100.0 - falloff) / 100.0));
                if (r.nextDouble() > rate) {
                    final LinkedList<Location> newCurve = new LinkedList<Location>();
                    int amount = 0;
                    for (final Location l : locs) {
                        if (amount == 0) {
                            newCurve.add(b.getLocation());
                        }
                        else {
                            newCurve.add(b.getLocation().clone().add(l.getX() - locs.get(0).getX(), l.getY() - locs.get(0).getY(), l.getZ() - locs.get(0).getZ()));
                        }
                        ++amount;
                    }
                    final BezierSpline bs = new BezierSpline(newCurve);
                    final double length = bs.getCurveLength();
                    for (int maxCount = (int)(length * 2.5) + 1, y = 0; y <= maxCount; ++y) {
                        final Location location = bs.getPoint(y / (double)maxCount * (locs.size() - 1)).getBlock().getLocation();
                        if (!location.getBlock().getType().equals(Material.AIR) && (!pb.isSurfaceModeEnabled() || Surface.isOnSurface(location, p.getLocation())) && (!pb.isMaskEnabled() || (location.getBlock().getType() == pb.getMask()))) {
                           session.setBlock(location.getBlockX(), location.getBlockY(), location.getBlockZ(), BlockTypes.get(pb.getBlocks().get(random).getKey().getKey()));
                        }
                    }
                }
            }
        }
    }


    @Override
    public String getName() {
        return "Paint Brush";
    }

    @Override
    public String[] getDescription() {
        return new String[] {"§7Paints strokes", "§7hold shift to end"};
    }

    @Override
    public String getSkin() {
        return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODBiM2E5ZGZhYmVmYmRkOTQ5YjIxN2JiZDRmYTlhNDg2YmQwYzNmMGNhYjBkMGI5ZGZhMjRjMzMyZGQzZTM0MiJ9fX0=";
    }

    static {
        selectedPoints = new HashMap<>();
    }

}
