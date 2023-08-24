package net.arcaniax.gopaint.utils.blocks;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.world.block.BlockType;
import net.arcaniax.gopaint.utils.vectors.MutableVector3;

import java.util.*;

public class ConnectedBlocks {

    /**
     * Finds connected blocks of the same type starting from a given block.
     *
     * @param startVector The starting block's vector.
     * @param blocks      The list of blocks to search within.
     * @param editSession The EditSession to query block types.
     * @return A list of connected blocks of the same type.
     */
    public static List<MutableVector3> getConnectedBlocks(
            MutableVector3 startVector,
            List<MutableVector3> blocks,
            EditSession editSession
    ) {
        // Get the block type of the starting block
        BlockType blockType = editSession.getBlockType(startVector.getBlockX(), startVector.getBlockY(),
                startVector.getBlockZ()
        );

        // Create queues to track blocks to be checked and those that have been checked
        Queue<MutableVector3> hasToBeChecked = new LinkedList<>();
        LinkedList<MutableVector3> hasBeenChecked = new LinkedList<>();
        LinkedList<MutableVector3> connectedBlocks = new LinkedList<>();

        // Add the starting block to the queues and initialize step counter
        hasToBeChecked.add(startVector);
        connectedBlocks.add(startVector);

        int currentStep = 0;
        while (!hasToBeChecked.isEmpty() && currentStep < 5000) {
            MutableVector3 currentVector = hasToBeChecked.poll();
            for (MutableVector3 blockVector : getBlocksAround(currentVector)) {
                BlockType currentType = editSession.getBlockType(
                        blockVector.getBlockX(),
                        blockVector.getBlockY(),
                        blockVector.getBlockZ()
                );

                // Check if the block should be added to the connectedBlocks list
                if (!connectedBlocks.contains(blockVector)
                        && !hasBeenChecked.contains(blockVector)
                        && blocks.contains(blockVector)
                        && blockType == currentType) {
                    hasToBeChecked.add(blockVector);
                    connectedBlocks.add(blockVector);
                    currentStep++;
                }
            }
            hasBeenChecked.add(currentVector);
            hasToBeChecked.remove(currentVector);
        }
        return connectedBlocks;
    }

    /**
     * Gets the blocks surrounding a given block.
     *
     * @param currentBlock The vector of the current block.
     * @return A list of vectors representing the blocks around the current block.
     */
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
