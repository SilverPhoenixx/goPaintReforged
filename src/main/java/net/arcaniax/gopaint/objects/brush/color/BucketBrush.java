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
import com.sk89q.worldedit.world.block.BlockState;
import net.arcaniax.gopaint.objects.brush.ColorBrush;
import net.arcaniax.gopaint.objects.brush.settings.BrushSettings;
import net.arcaniax.gopaint.objects.player.AbstractPlayerBrush;
import net.arcaniax.gopaint.utils.blocks.ConnectedBlocks;
import net.arcaniax.gopaint.utils.math.Sphere;
import net.arcaniax.gopaint.utils.math.Surface;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class BucketBrush extends ColorBrush {

    public BucketBrush() {
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
    public void paintRight(AbstractPlayerBrush playerBrush, Location loc, Player p, EditSession session) {
        int size = playerBrush.getBrushSize();
        List<Material> pbBlocks = playerBrush.getBlocks();

        if(pbBlocks.isEmpty()) return;

        List<Block> blocks = Sphere.getBlocksInRadius(loc, size);
        List<Block> connectedBlocks = ConnectedBlocks.getConnectedBlocks(loc, blocks);
        for (Block b : connectedBlocks) {
            if ((!playerBrush.isSurfaceModeEnabled()) || Surface.isOnSurface(b.getLocation(), p.getLocation())) {
                if ((!playerBrush.isMaskEnabled()) || (b.getType().equals(playerBrush
                        .getMask()))) {
                    Random r = new Random();
                    int random = r.nextInt(pbBlocks.size());

                    Vector3 vector3 = Vector3.at(b.getX(), b.getY(), b.getZ());
                    if (isGmask(session, vector3.toBlockPoint())) {
                        try {
                            session.setBlock(
                                    b.getX(), b.getY(), b.getZ(),
                                    BukkitAdapter.asBlockType(pbBlocks.get(random)).getDefaultState().withProperties(BlockState.get(b.getState().getBlockData().getAsString()))
                            );
                        } catch (Exception ignored) {
                        }
                    }
                }
            }

        }
    }
}
