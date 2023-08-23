package net.arcaniax.gopaint.paint.brush.placement;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockType;
import net.arcaniax.gopaint.paint.player.AbstractPlayerBrush;
import net.arcaniax.gopaint.utils.vectors.MutableVector3;

import java.util.List;
import java.util.Random;

public class WithBlockDataPlacement extends Placement {

    @Override
    public void place(
            final EditSession editSession,
            final MutableVector3 blockVector,
            final MutableVector3 clickedVector,
            final Random random,
            final AbstractPlayerBrush playerBrush
    ) {
        BlockState blockState = editSession.getBlock(blockVector.getBlockX(), blockVector.getBlockY(), blockVector.getBlockZ());
        List<BlockType> brushBlocks = playerBrush.getBlocks();

        int randomBlock = random.nextInt(brushBlocks.size());

        try {
            // Set the block to a randomly selected block type
            editSession.setBlock(
                    blockVector.getBlockX(), blockVector.getBlockY(), blockVector.getBlockZ(),
                    brushBlocks.get(randomBlock).getDefaultState().withProperties(blockState)
            );
        } catch (Exception ignored) {
            // Handle any exceptions that may occur during block placement
        }
    }


    @Override
    public String getName() {
        return "With BlockData";
    }

}
