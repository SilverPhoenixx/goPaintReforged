package net.arcaniax.gopaint.paint.brush.placement;

import com.sk89q.worldedit.EditSession;
import net.arcaniax.gopaint.paint.player.AbstractPlayerBrush;
import net.arcaniax.gopaint.utils.vectors.MutableVector3;

import java.util.Random;

public abstract class Placement {

    public abstract void place(
            EditSession editSession,
            MutableVector3 blockVector,
            MutableVector3 clickedVector,
            Random random,
            AbstractPlayerBrush playerBrush
    );

    public abstract String getName();
}
