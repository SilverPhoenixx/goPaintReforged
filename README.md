<p align="center">
    <img src="https://i.imgur.com/ulEwPm9.jpg">
</p>

---

## Initial Developer
* Arcaniax
* His plugin is supporting different versions from currently 1.13 - 1.19

## Refractored
* Refractored by _SilverPhoenix
* Added easy brush implementation api
* Added biome brushes
* Added more blocks you can use in brushes
* Added better readability and performance
* Currently no support for ExportedBrush
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
**BiomeBrushes** are shown when "Biome" is enabled
**ColorBrushes** are shown when "Biome" is disabled

If you want to create a own brush just extend from **"BiomeBrush"** or **"ColorBrush"**\
[in net.arcaniax.gopaint.paint.brush]

You can add only up to **9** settings for a brush.
If you try to add more than 9, the constructor throws an error. **(IllegalArgumentException)**

When you initialize your plugin add the brush to the PlayerBrushManager.\
[GoPaint.getBrushManager()]


**ColorBrush:** **GoPaint.getBrushManager().getColorBrushes().add(new ExampleBrush());
<br><br>
**BiomeBrush:** GoPaint.getBrushManager().getBiomesBrushes().add(new ExampleBrush());

## Example Class:

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
    



