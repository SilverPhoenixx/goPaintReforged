/*
 * goPaint is designed to simplify painting inside of Minecraft.
 * Copyright (C) Arcaniax-Development
 * Copyright (C) Arcaniax team and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package net.arcaniax.gopaint.paint.player;

import com.sk89q.worldedit.world.biome.BiomeType;
import net.arcaniax.gopaint.GoPaint;
import net.arcaniax.gopaint.paint.brush.ColorBrush;
import net.arcaniax.gopaint.paint.brush.color.AngleBrush;
import net.arcaniax.gopaint.paint.brush.Brush;
import net.arcaniax.gopaint.paint.brush.color.DiscBrush;
import net.arcaniax.gopaint.paint.brush.color.FractureBrush;
import net.arcaniax.gopaint.paint.brush.color.GradientBrush;
import net.arcaniax.gopaint.paint.brush.color.OverlayBrush;
import net.arcaniax.gopaint.paint.brush.color.SplatterBrush;
import net.arcaniax.gopaint.paint.brush.color.SprayBrush;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AbstractPlayerBrush {

    Boolean surfaceEnabled;
    Boolean maskEnabled;
    Boolean enabled;
    Boolean biomeBrush;

    int brushSize;
    int chance;
    int thickness;
    int fractureDistance;
    int angleDistance;
    int falloffStrength;
    int mixingStrength;
    double minAngleHeightDifference;

    String axis;

    Brush brush;
    Material mask;
    List<Material> blocks;
    List<BiomeType> biomeTypes;


    public AbstractPlayerBrush() {
        this.surfaceEnabled = GoPaint.getSettings().isSurfaceModeEnabledDefault();
        this.maskEnabled = GoPaint.getSettings().isMaskEnabledDefault();
        this.enabled = GoPaint.getSettings().isEnabledDefault();
        this.biomeBrush = GoPaint.getSettings().isBiomesEnabledDefault();
        this.chance = GoPaint.getSettings().getDefaultChance();
        this.thickness = GoPaint.getSettings().getDefaultThickness();
        this.fractureDistance = GoPaint.getSettings().getDefaultFractureDistance();
        this.angleDistance = GoPaint.getSettings().getDefaultAngleDistance();
        this.minAngleHeightDifference = GoPaint.getSettings().getDefaultAngleHeightDifference();
        this.falloffStrength = 50;
        this.mixingStrength = 50;
        this.axis = "y";
        this.brush = GoPaint.getBrushManager().cycleColor((ColorBrush) brush);
        this.brushSize = GoPaint.getSettings().getDefaultSize();
        this.blocks = new ArrayList<>();
        this.blocks.add(Material.STONE);
        this.biomeTypes = new ArrayList<>();

        mask = Material.SPONGE;
    }

    public List<BiomeType> getBiomeTypes() {
        return biomeTypes;
    }

    public Brush getBrush() {
        return brush;
    }

    public void setBrush(Brush b) {
        this.brush = b;
    }

    public int getFalloffStrength() {
        return falloffStrength;
    }
    public void addFalloffStrength(int addedFalloff) {
        this.falloffStrength += addedFalloff;
    }
    public void removeFalloffStrength(int removedFalloff) {
        this.falloffStrength -= removedFalloff;
    }

    public int getMixingStrength() {
        return mixingStrength;
    }

    public void changeMixingStrength(int change) {
        this.mixingStrength += change;
    }

    public void chanceMinAngleHeight(Double change) {
        this.minAngleHeightDifference += change;
    }

    public void setMinHeightDifference(Double change) {
        this.minAngleHeightDifference = change;
    }

    public Double getMinHeightDifference() {
        return this.minAngleHeightDifference;
    }

    public int getAngleDistance() {
        return this.angleDistance;
    }

    public void changeAngleDistance(int change) {
        this.angleDistance += change;
    }

    public int getFractureDistance() {
        return this.fractureDistance;
    }

    public void changeFracture(int change) {
        this.fractureDistance += change;
    }

    public Material getMask() {
        return mask;
    }

    public List<Material> getBlocks() {
        return blocks;
    }

    public boolean isBiome() {
        return biomeBrush;
    }

    public int getBrushSize() {
        return brushSize;
    }




    public boolean isEnabled() {
        return enabled;
    }


    public void changeChance(int change) {
        this.chance += change;
    }

    public int getChance() {
        return chance;
    }



    public boolean isMaskEnabled() {
        return maskEnabled;
    }


    public boolean isSurfaceModeEnabled() {
        return surfaceEnabled;
    }

    public int getThickness() {
        return thickness;
    }
    public void changeThickness(int change) {
        thickness += change;
    }


    public String getAxis() {
        return axis;
    }

    public void changeAxis(String change) {
        this.axis = change;
    }


    public ItemStack export(ItemStack i) {
        StringBuilder lore = new StringBuilder("___&8Size: " + brushSize);

        if (brush instanceof SplatterBrush || brush instanceof SprayBrush) {
            lore.append("___&8Chance: ").append(chance).append("%");
        } else if (brush instanceof OverlayBrush) {
            lore.append("___&8Thickness: ").append(thickness);
        } else if (brush instanceof DiscBrush) {
            lore.append("___&8Axis: ").append(axis);
        } else if (brush instanceof AngleBrush) {
            lore.append("___&8AngleDistance: ").append(this.angleDistance);
            lore.append("___&8AngleHeightDifference: ").append(this.minAngleHeightDifference);
        } else if (brush instanceof GradientBrush) {
            lore.append("___&8Mixing: ").append(this.mixingStrength);
            lore.append("___&8Falloff: ").append(this.falloffStrength);
        } else if (brush instanceof FractureBrush) {
            lore.append("___&8FractureDistance: ").append(this.fractureDistance);
        }

        lore.append("___&8Blocks:");
        if (blocks.isEmpty()) {
            lore.append(" none");
        } else {
            for (Material material : blocks) {
                lore.append(" ").append(material.toString().toLowerCase());
            }
        }

        if(isBiome()) {
            lore.append("___&8Biome Mode");
        }

        lore.append("___&8Biomes:");
        if (biomeTypes.isEmpty()) {
            lore.append(" none");
        } else {
            for (BiomeType biomeType : biomeTypes) {
                lore.append(" ").append(biomeType.getId());
            }
        }

        if (maskEnabled) {
            lore.append("___&8Mask: ").append(mask.toString());
        }
        if (surfaceEnabled) {
            lore.append("___&8Surface Mode");
        }

        if(biomeBrush) {
            lore.append("____&8Biome");
        }
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(" §b♦ " + brush.getName() + " §b♦ ");
        if (!lore.toString().equals("")) {
            String[] loreListArray = lore.toString().split("___");
            List<String> loreList = new ArrayList<String>();
            for (String s : loreListArray) {
                loreList.add(s.replace("&", "§"));
            }
            im.setLore(loreList);
        }
        im.addEnchant(Enchantment.ARROW_INFINITE, 10, true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        i.setItemMeta(im);

        return i;
    }

}
