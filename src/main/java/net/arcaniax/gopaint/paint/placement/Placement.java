package net.arcaniax.gopaint.paint.placement;

import com.sk89q.worldedit.EditSession;
import net.arcaniax.gopaint.paint.brush.player.AbstractPlayerBrush;
import net.arcaniax.gopaint.utils.vectors.MutableVector3;

import java.util.Random;

public abstract class Placement {

    /**
     * Place blocks using the defined strategy.
     *
     * @param editSession    The EditSession to perform block placement.
     * @param blockVector    The vector representing the block to be placed.
     * @param clickedVector  The vector representing the location clicked by the player.
     * @param random         A random number generator.
     * @param playerBrush    The player's brush settings.
     */
    public abstract void place(
            EditSession editSession,
            MutableVector3 blockVector,
            MutableVector3 clickedVector,
            Random random,
            AbstractPlayerBrush playerBrush
    );

    /**
     * Get the name of the placement strategy.
     *
     * @return The name of the placement strategy.
     */
    public abstract String getName();
}
