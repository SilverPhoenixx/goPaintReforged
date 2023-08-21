package net.arcaniax.gopaint.utils.blocks;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockType;
import net.arcaniax.gopaint.utils.vectors.MutableVector3;

import java.util.*;

public class ConnectedBlocks {

    /**
     * Function to find connected blocks from a given startPoint
     * @return list of the connected blocks
     */
    public static List<MutableVector3> getConnectedBlocks(
            MutableVector3 startPoint,
            List<MutableVector3> blocksInArea,
            EditSession editSession
    ) {
        // Set to store connected blocks
        // We use Set because they didnt store duplicates
        Set<MutableVector3> connected = new HashSet<>();

        Queue<MutableVector3> queue = new LinkedList<>();
        queue.add(startPoint.clone()); // Start from the provided startPoint

        // Get the block type to check against
        BlockType checkingBlock = getBlockType(startPoint, editSession);

        // Track the current step for limiting iterations
        int currentStep = 0;

        // Perform BFS traversal
        while (!queue.isEmpty() && currentStep < 5000) {
            MutableVector3 currentBlock = queue.poll(); // Get the next block to examine
            connected.add(currentBlock); // Mark it as connected

            // Explore blocks around the current block
            for (MutableVector3 aroundBlock : getBlocksAround(currentBlock)) {
                // Check if the block hasn't been connected, is in the specified area, and matches the checkingBlock type
                if (!connected.contains(aroundBlock) &&
                        blocksInArea.contains(aroundBlock) &&
                        checkingBlock == getBlockType(aroundBlock, editSession)) {
                    queue.add(aroundBlock); // Add it to the queue for further exploration
                }
            }

            currentStep++; // Increment the step count
        }

        // Convert the connected Set to a List for the final result
        return new ArrayList<>(connected);
    }

    // Function to get the block type at a given location
    private static BlockType getBlockType(MutableVector3 block, EditSession editSession) {
        BlockState blockState = editSession.getBlock(
                block.getBlockX(), block.getBlockY(), block.getBlockZ()
        );
        return blockState.getBlockType();
    }

    // Function to get the blocks around a given block
    private static List<MutableVector3> getBlocksAround(MutableVector3 currentBlock) {
        List<MutableVector3> blocks = new ArrayList<>();
        blocks.add(currentBlock.clone().addX(1));
        blocks.add(currentBlock.clone().subtractX(1));
        blocks.add(currentBlock.clone().addY(1));
        blocks.add(currentBlock.clone().subtractY(1));
        blocks.add(currentBlock.clone().addZ(1));
        blocks.add(currentBlock.clone().subtractZ(1));
        return blocks;
    }

}
