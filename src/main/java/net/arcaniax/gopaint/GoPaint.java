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

import io.papermc.lib.PaperLib;
import net.arcaniax.gopaint.command.GoPaintCommand;
import net.arcaniax.gopaint.listeners.PlayerQuitListener;
import net.arcaniax.gopaint.listeners.InteractListener;
import net.arcaniax.gopaint.listeners.InventoryListener;
import net.arcaniax.gopaint.managers.GlobalSettingsManager;
import net.arcaniax.gopaint.managers.PlacementManager;
import net.arcaniax.gopaint.managers.PlayerBrushManager;
import net.arcaniax.gopaint.utils.blocks.DisabledBlocks;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.serverlib.ServerLib;

public class GoPaint extends JavaPlugin {

    /**
     * Instance of the plugin
     */
    private static GoPaint INSTANCE;

    /**
     * BSTATS Identifier for statistics
     */
    private static final int BSTATS_ID = 10557;
    /**
     * Manager of player brushes
     */
    private static PlayerBrushManager PLAYER_BRUSH_MANAGER;

    /**
     * Manager of placement methods
     */
    private static PlacementManager PLACEMENT_MANAGER;

    /**
     * Global settings (Load from config file)
     */
    private static GlobalSettingsManager GLOBAL_SETTINGS_MANAGER;

    /**
     * Called when the plugin is enabled.
     */
    public void onEnable() {
        INSTANCE = this;
        //Test 4
        this.saveDefaultConfig();

        load();
        registerListener();
        registerCommands();

        DisabledBlocks.addBlocks();

        // Check if we are in a safe environment
        ServerLib.checkUnsafeForks();
        ServerLib.isJavaSixteen();
        PaperLib.suggestPaper(this);

        Metrics metrics = new Metrics(this, BSTATS_ID);

        metrics.addCustomChart(new SimplePie(
                "worldeditImplementation",
                () -> Bukkit.getPluginManager().getPlugin("FastAsyncWorldEdit") != null ? "FastAsyncWorldEdit" : "WorldEdit"
        ));

    }

    /**
     * Register all available listener
     */
    public void registerListener() {
        PluginManager pm = getServer().getPluginManager();

        Listener connectListener = new PlayerQuitListener(INSTANCE);
        Listener interactListener = new InteractListener(INSTANCE);
        Listener inventoryListener = new InventoryListener(INSTANCE);

        pm.registerEvents(connectListener, this);
        pm.registerEvents(interactListener, this);
        pm.registerEvents(inventoryListener, this);
    }

    /**
     * Register all available commands
     */
    public void registerCommands() {
        CommandExecutor goPaintCommand = new GoPaintCommand(INSTANCE);

        getCommand("gopaint").setExecutor(goPaintCommand);
    }

    /**
     * Reloads the plugin configuration.
     */
    public static void reload() {
        INSTANCE.reloadConfig();
        load();
    }

    private static void load() {
        PLAYER_BRUSH_MANAGER = new PlayerBrushManager(INSTANCE);
        PLACEMENT_MANAGER = new PlacementManager(INSTANCE);

        GLOBAL_SETTINGS_MANAGER = new GlobalSettingsManager(INSTANCE);
        GLOBAL_SETTINGS_MANAGER.loadConfig();
    }

    /**
     * Get the global settings manager.
     *
     * @return The global settings manager.
     */
    public static GlobalSettingsManager getSettings() {
        return GLOBAL_SETTINGS_MANAGER;
    }

    /**
     * Get the player brush manager.
     *
     * @return The player brush manager.
     */
    public static PlayerBrushManager getBrushManager() {
        return PLAYER_BRUSH_MANAGER;
    }

    /**
     * Get the placement manager.
     *
     * @return The placement manager.
     */
    public static PlacementManager getPlacementManager() {
        return PLACEMENT_MANAGER;
    }

    /**
     * Get the instance of the plugin.
     *
     * @return The plugin instance.
     */
    public static GoPaint getInstance() {
        return INSTANCE;
    }
}
