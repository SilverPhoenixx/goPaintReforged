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
package net.arcaniax.gopaint.paint.brush.biome;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.world.biome.BiomeType;
import com.sk89q.worldedit.world.block.BlockState;
import net.arcaniax.gopaint.paint.brush.BiomeBrush;
import net.arcaniax.gopaint.paint.brush.settings.BrushSettings;
import net.arcaniax.gopaint.paint.player.AbstractPlayerBrush;
import net.arcaniax.gopaint.utils.math.Sphere;
import net.arcaniax.gopaint.utils.math.Surface;
import net.arcaniax.gopaint.utils.vectors.MutableVector3;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SphereBiomeBrush extends BiomeBrush {

    public SphereBiomeBrush() throws Exception {
        super(new BrushSettings[]{BrushSettings.SIZE});
    }

    @Override
    public void paintRight(AbstractPlayerBrush playerBrush, Location clickedLocation, Player player, EditSession editSession) {
        int size = playerBrush.getBrushSize();
        List<BiomeType> pbBlocks = playerBrush.getBiomeTypes();
        if (pbBlocks.isEmpty()) {
            return;
        }

        List<MutableVector3> blocks = Sphere.getBlocksInRadiusWithAir(new MutableVector3(
                clickedLocation.getX(),
                clickedLocation.getY(),
                clickedLocation.getZ()
        ), size, editSession);

        List<Pair<Integer, Integer>> chunks = new ArrayList<>();

        Location playerLocation = player.getLocation();
        for (MutableVector3 blockLocation : blocks) {
            if (playerBrush.isSurfaceModeEnabled()) {
                continue;
            }

            if (!Surface.isOnSurface(blockLocation.clone(), new MutableVector3(playerLocation), editSession)) {
                continue;
            }

            BlockState block = editSession.getBlock(
                    blockLocation.getBlockX(),
                    blockLocation.getBlockY(),
                    blockLocation.getBlockZ()
            );

            if (playerBrush.isMaskEnabled() && block.getBlockType() != playerBrush.getMask()) {
                continue;
            }

            Random r = new Random();
            int random = r.nextInt(pbBlocks.size());
            if (!isGmask(editSession, blockLocation.toBlockPoint())) {
                continue;
            }

            try {
                int chunkX = blockLocation.getChunkX();
                int chunkZ = blockLocation.getChunkZ();
                Pair<Integer, Integer> chunkCoords = Pair.of(chunkX, chunkZ);

                if (!chunks.contains(chunkCoords)) {
                    chunks.add(chunkCoords);
                }

                editSession.setBiome(
                        blockLocation.getBlockX(),
                        blockLocation.getBlockY(),
                        blockLocation.getBlockZ(),
                        pbBlocks.get(random)
                );


            } catch (Exception ignored) {
            }

        }

        editSession.commit();

        update(player, chunks);
    }

    @Override
    public String getName() {
        return "Biome Sphere Brush";
    }

    @Override
    public String[] getDescription() {
        return new String[]{"§7Regular Biome Sphere brush"};
    }

    @Override
    public String getSkin() {
        return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmU5OGY0ODU2MDE0N2MwYTJkNGVkYzE3ZjZkOTg1ZThlYjVkOTRiZDcyZmM2MDc0NGE1YThmMmQ5MDVhMTgifX19";
    }

}
