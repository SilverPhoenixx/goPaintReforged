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
package net.arcaniax.gopaint.listeners;

import net.arcaniax.gopaint.GoPaint;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    /**
     * The GoPaint plugin instance.
     */
    public GoPaint plugin;

    /**
     * Constructor for PlayerQuitListener.
     *
     * @param plugin The GoPaint plugin instance.
     */
    public PlayerQuitListener(GoPaint plugin) {
        this.plugin = plugin;
    }

    /**
     * Handles the player quit event.
     *
     * @param event The PlayerQuitEvent object representing the event.
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent event) {
        // Remove the player's brush when they quit.
        GoPaint.getBrushManager().removePlayerBrush(event.getPlayer());
    }

}
