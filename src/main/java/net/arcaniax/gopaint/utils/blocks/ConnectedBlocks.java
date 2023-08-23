package net.arcaniax.gopaint.utils.blocks;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.world.block.BlockType;
import net.arcaniax.gopaint.utils.vectors.MutableVector3;

import java.util.*;

public class ConnectedBlocks {

    public static List<MutableVector3> getConnectedBlocks(
            MutableVector3 startVector,
            List<MutableVector3> blocks,
            EditSession editSession
    ) {

        BlockType blockType = editSession.getBlockType(startVector.getBlockX(), startVector.getBlockY(),
                startVector.getBlockZ());

        Queue<MutableVector3> hasToBeChecked = new LinkedList<>();
        LinkedList<MutableVector3> hasBeenChecked = new LinkedList<>();
        LinkedList<MutableVector3> connectedBlocks = new LinkedList<>();

        hasToBeChecked.add(startVector);
        connectedBlocks.add(startVector);

        int currentStep = 0;
        while (!hasToBeChecked.isEmpty() && currentStep < 5000) {
            MutableVector3 currentVector = hasToBeChecked.poll();
            for (MutableVector3 blockVector : getBlocksAround(currentVector)) {
                BlockType currentType = editSession.getBlockType(
                        blockVector.getBlockX(),
                        blockVector.getBlockY(),
                        blockVector.getBlockZ());

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
