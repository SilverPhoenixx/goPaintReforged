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

import net.arcaniax.gopaint.GoPaintPlugin;
import net.arcaniax.gopaint.utils.gui.GoPaintInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {

    public GoPaintPlugin plugin;

    public InventoryListener(GoPaintPlugin main) {
        plugin = main;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void menuClick(InventoryClickEvent event) {
        try {
            if (!(event.getClickedInventory().getHolder() instanceof GoPaintInventory)) {
                return;
            }

            if (event.getView().getTopInventory() != event.getClickedInventory()) {
                if (event.getClick().isShiftClick() || event.getAction() == InventoryAction.COLLECT_TO_CURSOR) {
                    event.setCancelled(true);
                }
                return;
            }

            event.setCancelled(true);

            GoPaintInventory goPaintInventory = (GoPaintInventory) event.getClickedInventory().getHolder();
            goPaintInventory.interactInventory(event);

        } catch (NullPointerException e) {
            event.setCancelled(true);
        }
    }
}
