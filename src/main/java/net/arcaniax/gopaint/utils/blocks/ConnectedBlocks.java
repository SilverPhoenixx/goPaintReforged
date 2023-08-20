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
package net.arcaniax.gopaint.utils.blocks;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockType;
import net.arcaniax.gopaint.utils.vectors.MutableVector3;

import java.util.ArrayList;
import java.util.List;

public class ConnectedBlocks {

    public static List<MutableVector3> getConnectedBlocks(
            MutableVector3 location,
            List<MutableVector3> blocks,
            EditSession editSession
    ) {
        MutableVector3 startBlock = location.clone();

        BlockType checkingBlock =
                editSession.getBlock(startBlock.getBlockX(), startBlock.getBlockY(), startBlock.getBlockZ()).getBlockType();

        List<MutableVector3> connectCheckBlocks = new ArrayList<>();
        List<MutableVector3> hasBeenChecked = new ArrayList<>();
        List<MutableVector3> connected = new ArrayList<>();
        int x = 0;
        connectCheckBlocks.add(startBlock);
        connected.add(startBlock);
        while (!connectCheckBlocks.isEmpty() && x < 5000) {
            MutableVector3 b = connectCheckBlocks.get(0);

            for (MutableVector3 blockLocation : getBlocksAround(b)) {
                BlockState blockState = editSession.getBlock(blockLocation.getBlockX(), blockLocation.getBlockY(),
                        blockLocation.getBlockZ()
                );
                if ((!connected.contains(blockLocation))
                        && (!hasBeenChecked.contains(blockLocation))
                        && blocks.contains(blockLocation)
                        && checkingBlock == blockState.getBlockType()) {
                    connectCheckBlocks.add(blockLocation);
                    connected.add(blockLocation);
                    x++;
                }
            }
            hasBeenChecked.add(b);
            connectCheckBlocks.remove(b);
        }
        return connected;
    }

    private static List<MutableVector3> getBlocksAround(MutableVector3 b) {
        List<MutableVector3> blocks = new ArrayList<>();
        blocks.add(b.clone().addX(1));
        blocks.add(b.clone().subtractX(1));

        blocks.add(b.clone().addY(1));
        blocks.add(b.clone().subtractY(1));

        blocks.add(b.clone().addZ(1));
        blocks.add(b.clone().subtractZ(1));
        return blocks;
    }

}
