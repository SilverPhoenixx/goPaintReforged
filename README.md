<p align="center">
    <img src="https://i.imgur.com/ulEwPm9.jpg">
</p>

---

## Initial Developer
* Arcaniax \
original: https://github.com/Arcaniax-Development/goPaint_1.14 \
download: https://www.spigotmc.org/resources/gopaint.27717/

## Refractored

#### Overview
* Refractored by _SilverPhoenix
* Added biome brushes
* Added placement types
* Added more blocks you can use in brushes
* Added better readability and performance
* Added Export Brush for Biome and Color brushes\
#### Development
* Added Brush Manager for easily add new brushes
* Added Placement Manager for easily add placement methods
* Added Setting Enums
* Rewrote entire inventory behaivour

---

goPaint is a plugin that's designed to simplify painting inside of Minecraft,
it has the essential features required for painting in Minecraft,
but with a revolutionary Surface Mode which lets you paint only the viewable blocks.

## Download Links
Only 1.20.1 is tested

## Links of Arcaniaxs GoPaint

* [Download](https://www.spigotmc.org/resources/gopaint.27717/)
* [Development Builds](https://ci.athion.net/job/goPaint-1.14+/)
* [Discord](https://discord.gg/jpRVrjd)
* [Issues](https://github.com/Brennian/goPaint_1.14/issues)

## API


### Custom Brushes
If you want to create a own brush just extend from **"BiomeBrush"** or **"ColorBrush"**\
[in net.arcaniax.gopaint.paint.brush.ColorBrush or net.arcaniax.gopaint.paint.brush.BiomeBrush]

You can add only up to **7** settings for a brush.
If you try to add more than 7, the constructor throws an error. **(IllegalArgumentException)**

When you initialize your plugin add the brush to the PlayerBrushManager.\
[GoPaint.getBrushManager()]


**ColorBrush:** **GoPaint.getBrushManager().getColorBrushes().add(new ExampleBrush());
<br><br>
**BiomeBrush:** GoPaint.getBrushManager().getBiomesBrushes().add(new ExampleBrush());

Example Class:

    public class ExampleBrush extends ColorBrush {
        public ExampleBrush() {
            super(new BrushSettings[] {
                    BrushSettings.SIZE,
            });
        }

        @Override
        public String getName() {
            return "Example Brush";
        }

        @Override
        public String[] getDescription() {
            return new String[] {"§7Only works as example"};
        }
        /**
        * https://minecraft-heads.com/custom-heads/
        * Click on a head you want to use
        * Scroll below to "OTHER"
        * Copy "Value"
        @Override
        public String getSkin() {
            return "HEAD SKIN";
        }

        @Override
        public void paintRight(AbstractPlayerBrush playerBrush, MutableVector3 clickedVector, Player player,
        EditSession editSession) {
            ...
        }
    }

### Custom Placement
If you want to create a own placement just extend from **"Placement"**\
[in net.arcaniax.gopaint.paint.placement.Placement]

When you initialize your plugin add the placement to the PlacementManager.\
[GoPaint.getPlacementManager()]

### 
**Placement:** **GoPaint.getPlacementManager().getPlacements().add(new ExamplePlacement());

Example Class:

    public class ExamplePlacement extends Placement {

    @Override
    public void place(
            final EditSession editSession,
            final MutableVector3 blockVector,
            final MutableVector3 clickedVector,
            final Random random,
            final AbstractPlayerBrush playerBrush
    ) {
        List<BlockType> brushBlocks = playerBrush.getBlocks();

        int randomBlock = random.nextInt(brushBlocks.size());

        try {
            // Set the block to a randomly selected block type
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
        return "Example Name";
    }

}


### Utils

Items [in net.arcaniax.gopaint.utils]:
- ItemBuilder

Vector [in net.arcaniax.gopaint.utils.vectors]:
- MutableVector3

Math [in net.arcaniax.gopaint.utils.math]:
- Height
- Sphere
- Surface
- Curve

Blocks [in net.arcaniax.gopaint.utils.blocks]:
- ConnectedBlocks
    



