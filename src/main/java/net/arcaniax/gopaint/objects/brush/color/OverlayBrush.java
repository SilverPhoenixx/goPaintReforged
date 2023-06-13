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
import net.arcaniax.gopaint.objects.brush.ColorBrush;
import net.arcaniax.gopaint.objects.brush.settings.BrushSettings;
import net.arcaniax.gopaint.utils.BlockPlace;
import net.arcaniax.gopaint.objects.player.AbstractPlayerBrush;
import net.arcaniax.gopaint.utils.math.Height;
import net.arcaniax.gopaint.utils.math.Sphere;
import net.arcaniax.gopaint.utils.math.Surface;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OverlayBrush extends ColorBrush {

    public OverlayBrush() {
        super(new BrushSettings[] {
                BrushSettings.SIZE,
                BrushSettings.THICKNESS
        });
    }

    @SuppressWarnings("deprecation")
    @Override
    public void paintRight(AbstractPlayerBrush playerBrush, Location loc, Player p, EditSession session) {
        int size = playerBrush.getBrushSize();
        List<Material> pbBlocks = playerBrush.getBlocks();
        if (pbBlocks.isEmpty()) return;
        List<Block> blocks = Sphere.getBlocksInRadius(loc, size);
        List<BlockPlace> placedBlocks = new ArrayList<BlockPlace>();
        for (Block b : blocks) {
            if ((!playerBrush.isSurfaceModeEnabled()) || Surface.isOnSurface(b.getLocation(), p.getLocation())) {
                if ((!playerBrush.isMaskEnabled()) || (b.getType().equals(playerBrush
                        .getMask()))) {
                    if (Height.isOnTop(p, b.getLocation(), playerBrush.getThickness())) {
                        Random r = new Random();
                        int random = r.nextInt(pbBlocks.size());
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

    @Override
    public String getName() {
        return "Overlay Brush";
    }

    @Override
    public String[] getDescription() {
        return new String[] {"§7Only paints blocks", "§7that have air above it"};
    }

    @Override
    public String getSkin() {
        return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGYzMWQ2Zjk2NTRmODc0ZWE5MDk3YWRlZWEwYzk2OTk2ZTc4ZTNmZDM3NTRmYmY5ZWJlOTYzYWRhZDliZTRjIn19fQ==";
    }
}
