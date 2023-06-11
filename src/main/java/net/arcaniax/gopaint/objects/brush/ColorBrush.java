package net.arcaniax.gopaint.objects.brush;

import net.arcaniax.gopaint.objects.brush.Brush;
import net.arcaniax.gopaint.objects.brush.settings.BrushSettings;

public abstract class ColorBrush extends Brush {

    public ColorBrush(final BrushSettings[] settings) {
        super(settings);
    }

}
