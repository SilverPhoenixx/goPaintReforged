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
package net.arcaniax.gopaint.managers;

import net.arcaniax.gopaint.GoPaint;

import java.util.ArrayList;
import java.util.List;

public class GlobalSettingsManager {

    private GoPaint goPaint;

    // Prefix for plugin messages.
    private String prefix;

    // Default values for various settings.
    private int defaultSize;
    private int defaultChance;
    private int defaultThickness;
    private int defaultAngleDistance;
    private int defaultFractureDistance;
    private double defaultAngleHeightDifference;

    // Maximum values for settings.
    private int maxSize;
    private int maxAngleDistance;
    private int maxFractureDistance;
    private double maxAngleHeightDifference;
    private int maxThickness;

    // Minimum angle height difference.
    private double minAngleHeightDifference;

    // List of disabled world names.
    private List<String> disabledWorldNames;

    // Boolean settings.
    private boolean enabledByDefault;
    private boolean maskEnabled;
    private boolean surfaceModeEnabled;
    private boolean biomesEnabled;

    /**
     * Constructor for GlobalSettingsManager.
     *
     * @param goPaint The GoPaint plugin instance.
     */
    public GlobalSettingsManager(GoPaint goPaint) {
        this.goPaint = goPaint;

        // Plugin prefix.
        this.prefix = "§bgoPaint >";

        // Default settings.
        this.defaultChance = 50;
        this.defaultThickness = 1;
        this.defaultAngleDistance = 2;
        this.defaultFractureDistance = 2;
        this.defaultSize = 10;
        this.defaultAngleHeightDifference = 40.0;

        // Max settings.
        this.maxSize = 100;
        this.maxThickness = 5;
        this.maxFractureDistance = 5;
        this.maxAngleDistance = 5;
        this.maxAngleHeightDifference = 85.0;

        // Min settings.
        this.minAngleHeightDifference = 10.0;

        // Lists settings.
        this.disabledWorldNames = new ArrayList<>();

        // Boolean settings.
        this.enabledByDefault = false;
        this.maskEnabled = true;
        this.surfaceModeEnabled = false;
        this.biomesEnabled = false;
    }

    /**
     * Load configuration settings from the plugin's configuration file.
     */
    public void loadConfig() {
        // Plugin prefix.
        this.prefix = this.goPaint.getConfig().getString("prefix");

        // Default settings.
        this.defaultSize = this.goPaint.getConfig().getInt("size.default");
        this.defaultThickness = this.goPaint.getConfig().getInt("thickness.default");
        this.defaultAngleDistance = this.goPaint.getConfig().getInt("angleDistance.default");
        this.defaultFractureDistance = this.goPaint.getConfig().getInt("fractureDistance.default");

        // Validate and set the default chance within a specific range.
        int defaultChance = this.goPaint.getConfig().getInt("chance.default");
        if (defaultChance > 0 && defaultChance < 100 && defaultChance % 10 == 0) {
            this.defaultChance = defaultChance;
        }

        // Validate and set the default angle height difference within a specific range.
        double defaultAngle = this.goPaint.getConfig().getDouble("angleHeightDifference.default");
        if (defaultAngle > 0 && defaultAngle < 90 && defaultAngle % 5 == 0) {
            this.defaultAngleHeightDifference = defaultAngle;
        }

        // Max settings.
        maxSize = this.goPaint.getConfig().getInt("size.max");
        maxAngleDistance = this.goPaint.getConfig().getInt("angleDistance.max");
        maxFractureDistance = this.goPaint.getConfig().getInt("fractureDistance.max");
        maxThickness = this.goPaint.getConfig().getInt("thickness.max");

        // Validate and set the max angle height difference within a specific range.
        double maxAngle = this.goPaint.getConfig().getDouble("angleHeightDifference.max");
        if (maxAngle > 0 && maxAngle < 90 && maxAngle % 5 == 0) {
            maxAngleHeightDifference = maxAngle;
        }

        // Min settings.
        double minAngle = this.goPaint.getConfig().getDouble("angleHeightDifference.min");
        if (minAngle > 0 && minAngle < 90 && minAngle % 5 == 0) {
            minAngleHeightDifference = minAngle;
        }

        // Lists settings.
        disabledWorldNames = this.goPaint.getConfig().getStringList("disabledWorlds");

        // Boolean settings.
        enabledByDefault = this.goPaint.getConfig().getBoolean("toggles.enabledByDefault");
        maskEnabled = this.goPaint.getConfig().getBoolean("toggles.maskEnabled");
        surfaceModeEnabled = this.goPaint.getConfig().getBoolean("toggles.surfaceModeEnabled");
        biomesEnabled = this.goPaint.getConfig().getBoolean("toggles.biomesEnabled");
    }

    /**
     * Get the default size value.
     *
     * @return The default size.
     */
    public int getDefaultSize() {
        return defaultSize;
    }

    /**
     * Get the default chance value.
     *
     * @return The default chance.
     */
    public int getDefaultChance() {
        return defaultChance;
    }

    /**
     * Get the default thickness value.
     *
     * @return The default thickness.
     */
    public int getDefaultThickness() {
        return defaultThickness;
    }

    /**
     * Get the default angle height difference value.
     *
     * @return The default angle height difference.
     */
    public double getDefaultAngleHeightDifference() {
        return defaultAngleHeightDifference;
    }

    /**
     * Get the default angle distance value.
     *
     * @return The default angle distance.
     */
    public int getDefaultAngleDistance() {
        return defaultAngleDistance;
    }

    /**
     * Get the maximum size value.
     *
     * @return The maximum size.
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * Get the maximum thickness value.
     *
     * @return The maximum thickness.
     */
    public int getMaxThickness() {
        return maxThickness;
    }

    /**
     * Get the minimum angle height difference value.
     *
     * @return The minimum angle height difference.
     */
    public double getMinAngleHeightDifference() {
        return minAngleHeightDifference;
    }

    /**
     * Get the maximum angle height difference value.
     *
     * @return The maximum angle height difference.
     */
    public double getMaxAngleHeightDifference() {
        return maxAngleHeightDifference;
    }

    /**
     * Get the maximum angle distance value.
     *
     * @return The maximum angle distance.
     */
    public int getMaxAngleDistance() {
        return maxAngleDistance;
    }

    /**
     * Get the plugin prefix.
     *
     * @return The plugin prefix.
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Get a list of disabled world names.
     *
     * @return A list of disabled world names.
     */
    public List<String> getDisabledWorlds() {
        return disabledWorldNames;
    }

    /**
     * Check if "enabled by default" is set to true.
     *
     * @return True if "enabled by default" is true, otherwise false.
     */
    public boolean isEnabledDefault() {
        return enabledByDefault;
    }

    /**
     * Check if mask is enabled by default.
     *
     * @return True if mask is enabled by default, otherwise false.
     */
    public boolean isMaskEnabledDefault() {
        return maskEnabled;
    }

    /**
     * Check if biomes are enabled by default.
     *
     * @return True if biomes are enabled by default, otherwise false.
     */
    public boolean isBiomesEnabledDefault() {
        return biomesEnabled;
    }

    /**
     * Check if surface mode is enabled by default.
     *
     * @return True if surface mode is enabled by default, otherwise false.
     */
    public boolean isSurfaceModeEnabledDefault() {
        return surfaceModeEnabled;
    }

    /**
     * Get the default fracture distance value.
     *
     * @return The default fracture distance.
     */
    public int getDefaultFractureDistance() {
        return this.defaultFractureDistance;
    }

    /**
     * Get the maximum fracture distance value.
     *
     * @return The maximum fracture distance.
     */
    public int getMaxFractureDistance() {
        return this.maxFractureDistance;
    }

}
