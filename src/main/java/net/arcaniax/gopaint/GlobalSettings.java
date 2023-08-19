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
package net.arcaniax.gopaint;

import java.util.ArrayList;
import java.util.List;

public class GlobalSettings {

    private GoPaint goPaint;

    private String prefix;
    
    private int defaultSize;
    private int defaultChance;
    private int defaultThickness;
    private int defaultAngleDistance;
    private int defaultFractureDistance;
    private double defaultAngleHeightDifference;
    
    private int maxSize;
    private int maxAngleDistance;
    private int maxFractureDistance;
    private double maxAngleHeightDifference;
    private int maxThickness;

    private double minAngleHeightDifference;
    
    private List<String> disabledWorldNames;
    
    private boolean enabledByDefault;
    private boolean maskEnabled;
    private boolean boundingBoxEnabled;
    private boolean surfaceModeEnabled;
    private boolean biomesEnabled;

    public GlobalSettings(GoPaint goPaint) {
        this.goPaint = goPaint;
        
        // Plugin prefix
        this.prefix = "§bgoPaint > ";

        // Default settings
        this.defaultChance = 50;
        this.defaultThickness = 1;
        this.defaultAngleDistance = 2;
        this.defaultFractureDistance = 2;
        this.defaultSize = 10;
        this.defaultAngleHeightDifference = 40.0;

        // Max settings
        this.maxSize = 100;
        this.maxThickness = 5;
        this.maxFractureDistance = 5;
        this.maxAngleDistance = 5;
        this.maxAngleHeightDifference = 85.0;

        // Min settings
        this.minAngleHeightDifference = 10.0;

        // Lists settings
        this.disabledWorldNames = new ArrayList<>();

        // Boolean settings
        this.enabledByDefault = false;
        this.maskEnabled = true;
        this.surfaceModeEnabled = false;
        this.boundingBoxEnabled = false;
        this.biomesEnabled = false;
    }

    public void loadConfig() {
        // Plugin prefix
        this.prefix = this.goPaint.getConfig().getString("prefix");

        // Default settings
        this.defaultSize = this.goPaint.getConfig().getInt("size.default");
        this.defaultThickness = this.goPaint.getConfig().getInt("thickness.default");
        this.defaultAngleDistance = this.goPaint.getConfig().getInt("angleDistance.default");
        this.defaultFractureDistance = this.goPaint.getConfig().getInt("fractureDistance.default");

        int defaultChance = this.goPaint.getConfig().getInt("chance.default");
        if (defaultChance > 0 && defaultChance < 100 && defaultChance % 10 == 0) {
            this.defaultChance = defaultChance;
        }
        double defaultAngle = this.goPaint.getConfig().getDouble("angleHeightDifference.default");
        if (defaultAngle > 0 && defaultAngle < 90 && defaultAngle % 5 == 0) {
            this.defaultAngleHeightDifference = defaultAngle;
        }

        // Max settings
        maxSize = this.goPaint.getConfig().getInt("size.max");
        maxAngleDistance = this.goPaint.getConfig().getInt("angleDistance.max");
        maxFractureDistance = this.goPaint.getConfig().getInt("fractureDistance.max");
        maxThickness = this.goPaint.getConfig().getInt("thickness.max");

        double maxAngle = this.goPaint.getConfig().getDouble("angleHeightDifference.max");
        if (maxAngle > 0 && maxAngle < 90 && maxAngle % 5 == 0) {
            maxAngleHeightDifference = maxAngle;
        }

        // Min settings
        double minAngle = this.goPaint.getConfig().getDouble("angleHeightDifference.min");
        if (minAngle > 0 && minAngle < 90 && minAngle % 5 == 0) {
            minAngleHeightDifference = minAngle;
        }

        // Lists settings
        disabledWorldNames = this.goPaint.getConfig().getStringList("disabledWorlds");

        // Boolean settings
        enabledByDefault = this.goPaint.getConfig().getBoolean("toggles.enabledByDefault");
        maskEnabled = this.goPaint.getConfig().getBoolean("toggles.maskEnabled");
        boundingBoxEnabled = this.goPaint.getConfig().getBoolean("toggles.boundingBoxEnabled");
        surfaceModeEnabled = this.goPaint.getConfig().getBoolean("toggles.surfaceModeEnabled");
        biomesEnabled = this.goPaint.getConfig().getBoolean("toggles.biomesEnabled");
    }

    public int getDefaultSize() {
        return defaultSize;
    }

    public int getDefaultChance() {
        return defaultChance;
    }

    public int getDefaultThickness() {
        return defaultThickness;
    }

    public double getDefaultAngleHeightDifference() {
        return defaultAngleHeightDifference;
    }

    public int getDefaultAngleDistance() {
        return defaultAngleDistance;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getMaxThickness() {
        return maxThickness;
    }

    public double getMinAngleHeightDifference() {
        return minAngleHeightDifference;
    }

    public double getMaxAngleHeightDifference() {
        return maxAngleHeightDifference;
    }

    public int getMaxAngleDistance() {
        return maxAngleDistance;
    }

    public String getPrefix() {
        return prefix;
    }

    public List<String> getDisabledWorlds() {
        return disabledWorldNames;
    }

    public boolean isEnabledDefault() {
        return enabledByDefault;
    }

    public boolean isMaskEnabledDefault() {
        return maskEnabled;
    }

    public boolean isBiomesEnabledDefault() {
        return biomesEnabled;
    }

    public boolean isSurfaceModeEnabledDefault() {
        return surfaceModeEnabled;
    }

    public boolean isBoundingBoxEnabled() {
        return boundingBoxEnabled;
    }

    public int getDefaultFractureDistance() {
        return this.defaultFractureDistance;
    }

    public int getMaxFractureDistance() {
        return this.maxFractureDistance;
    }

}
