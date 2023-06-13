<p align="center">
    <img src="https://i.imgur.com/ulEwPm9.jpg">
</p>

---

## Initial Developer
* Arcaniax
* His plugin is supporting different versions from currently 1.13 - 1.19

## Refractored
* Refractored by _SilverPhoenix
* This plugin supports only 1.19.2 version because im too lazy, lmao
* Added easy brush implementation api
* Added biome brushes
* Added more blocks you can use in brushes
* Added better readability and performance (because of polymorphism)
* Currently no support for ExportedBrush with biomes
---

goPaint is a plugin that's designed to simplify painting inside of Minecraft,
it has the essential features required for painting in Minecraft,
but with a revolutionary Surface Mode which lets you paint only the viewable blocks.

## Download Links
Only 1.19.2 is tested
* [1.19.2](https://workupload.com/file/Adng8tATfhX)

## Links of Arcaniaxs GoPaint

* [Download](https://www.spigotmc.org/resources/gopaint.27717/)
* [Development Builds](https://ci.athion.net/job/goPaint-1.14+/)
* [Discord](https://discord.gg/jpRVrjd)
* [Issues](https://github.com/Brennian/goPaint_1.14/issues)

## API

If you want to create a own brush just extend from **"BiomeBrush"** or **"ColorBrush"** [in net.arcaniax.gopaint.objects.brush]

BiomeBrushes are shown when "Biome" is enabled
ColorBrushes are shown when "Biome" is disabled

You can add only upto **9** settings for a brush.

When you initialize your plugin add the brush to the PlayerBrushManager

**ColorBrush:** GoPaintPlugin.getBrushManager().getColorBrushes().add(new ExampleBrush());
<br><br>
**BiomeBrush:** GoPaintPlugin.getBrushManager().getBiomeBrushes().add(new ExampleBrush());

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
        public void paintRight(AbstractPlayerBrush playerBrush, Location loc, Player p, EditSession session) {
        }
    }



