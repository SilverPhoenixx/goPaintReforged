package net.arcaniax.gopaint.paint.placement;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.world.block.BlockType;
import net.arcaniax.gopaint.paint.brush.player.AbstractPlayerBrush;
import net.arcaniax.gopaint.utils.vectors.MutableVector3;

import java.util.List;
import java.util.Random;

public class GradientPlacement extends Placement {

    @Override
    public void place(
            final EditSession editSession,
            final MutableVector3 blockVector,
            final MutableVector3 clickedVector,
            final Random random,
            final AbstractPlayerBrush playerBrush
    ) {
        List<BlockType> brushBlocks = playerBrush.getBlocks();

        // Calculate the base Y coordinate for block placement
        double y = clickedVector.getBlockY() - ((double) playerBrush.getBrushSize() / 2.0);

        // Calculate the relative Y coordinate to determine the block index
        double _y = (blockVector.getBlockY() - y) / (double) playerBrush.getBrushSize() * brushBlocks.size();

        // Calculate a random offset for block selection based on mixing strength
        int randomBlock = (int) (_y + (random.nextDouble() * 2 - 1) * ((double) playerBrush.getMixingStrength() / 100.0));

        // Ensure the block index is within valid bounds
        randomBlock = Math.max(0, Math.min(randomBlock, brushBlocks.size() - 1));

        // Set the block at the current location to the randomly chosen block type
        try {
            editSession.setBlock(
                    blockVector.getBlockX(), blockVector.getBlockY(), blockVector.getBlockZ(),
                    brushBlocks.get(randomBlock).getDefaultState()
            );
        } catch (Exception ignored) {
            // Handle any exceptions that may occur during block placement
        }
    }

    @Override
    public String getName() {
        return "Gradient";
    }

}
