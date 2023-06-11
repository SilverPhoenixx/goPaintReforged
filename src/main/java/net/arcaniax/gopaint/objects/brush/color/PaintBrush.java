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
package net.arcaniax.gopaint.objects.brush.color;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.Vector3;
import net.arcaniax.gopaint.GoPaintPlugin;
import net.arcaniax.gopaint.objects.brush.Brush;
import net.arcaniax.gopaint.objects.brush.ColorBrush;
import net.arcaniax.gopaint.objects.brush.settings.BrushSettings;
import net.arcaniax.gopaint.objects.player.AbstractPlayerBrush;
import net.arcaniax.gopaint.utils.BlockUtils;
import net.arcaniax.gopaint.utils.Sphere;
import net.arcaniax.gopaint.utils.Surface;
import net.arcaniax.gopaint.utils.curve.BezierSpline;
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

    public PaintBrush() {
        super(new BrushSettings[] {
                BrushSettings.SIZE,
                BrushSettings.FALLOFF_STRENGTH
        });
    }

    private static final HashMap<String, List<Location>> selectedPoints = new HashMap<>();

    @Override
    public void paintRight(AbstractPlayerBrush playerBrush, Location loc, Player p, EditSession session) {
        String prefix = GoPaintPlugin.getSettings().getPrefix();
        if (!selectedPoints.containsKey(p.getName())) {
            List<Location> locs = new ArrayList<>();
            locs.add(loc);
            selectedPoints.put(p.getName(), locs);
            p.sendMessage(prefix + " Paint brush point #1 set.");
        } else {

            if (!p.isSneaking()) {
                List<Location> locs = selectedPoints.get(p.getName());
                locs.add(loc);
                selectedPoints.put(p.getName(), locs);
                p.sendMessage(prefix + " Paint brush point #" + locs.size() + " set.");
                return;
            }
            List<Location> locs = selectedPoints.get(p.getName());
            locs.add(loc);
            selectedPoints.remove(p.getName());
            int size = playerBrush.getBrushSize();
            int falloff = playerBrush.getFalloffStrength();
            List<Material> pbBlocks = playerBrush.getBlocks();
            if (pbBlocks.isEmpty()) return;

            List<Block> blocks = Sphere.getBlocksInRadiusWithAir(locs.get(0), size);
            for (Block b : blocks) {
                Random r = new Random();
                int random = r.nextInt(pbBlocks.size());
                double rate = (b
                        .getLocation()
                        .distance(locs.get(0)) - ((double) size / 2.0) * ((100.0 - (double) falloff) / 100.0)) / (((double) size / 2.0) - ((double) size / 2.0) * ((100.0 - (double) falloff) / 100.0));
                if (!(r.nextDouble() <= rate)) {
                    LinkedList<Location> newCurve = new LinkedList<>();
                    int amount = 0;
                    for (Location l : locs) {
                        if (amount == 0) {
                            newCurve.add(b.getLocation());
                        } else {
                            newCurve.add(b.getLocation().clone().add(
                                    l.getX() - locs.get(0).getX(),
                                    l.getY() - locs.get(0).getY(),
                                    l.getZ() - locs.get(0).getZ()
                            ));
                        }
                        amount++;
                    }
                    BezierSpline bs = new BezierSpline(newCurve);
                    double length = bs.getCurveLength();
                    int maxCount = (int) (length * 2.5) + 1;
                    for (int y = 0; y <= maxCount; y++) {
                        Location l = bs.getPoint(((double) y / (double) maxCount) * (locs.size() - 1)).getBlock().getLocation();
                        Location location = new Location(l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ());
                        if (BlockUtils.isLoaded(location) && (!location
                                .getBlock()
                                .getType()
                                .equals(Material.AIR))) {
                            if ((!playerBrush.isSurfaceModeEnabled()) || Surface.isOnSurface(location, p.getLocation())) {
                                if ((!playerBrush.isMaskEnabled()) || (b.getType().equals(playerBrush
                                        .getMask()))) {
                                    Vector3 vector3 = Vector3.at(b.getX(), b.getY(), b.getZ());
                                    if (isGmask(session, vector3.toBlockPoint())) {
                                        try {
                                            session.setBlock(
                                                    b.getX(), b.getY(), b.getZ(),
                                                    BukkitAdapter.asBlockType(pbBlocks.get(random)).getDefaultState()
                                            );
                                        } catch (Exception ignored) {
                                        }
                                    }
                                }
                            }
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
    public String getDescription() {
        return "___&7Click to select______" + "&8Paints strokes___&8hold shift to end";
    }

    @Override
    public String getSkin() {
        return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODBiM2E5ZGZhYmVmYmRkOTQ5YjIxN2JiZDRmYTlhNDg2YmQwYzNmMGNhYjBkMGI5ZGZhMjRjMzMyZGQzZTM0MiJ9fX0=";
    }

}
