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
package net.arcaniax.gopaint.inventories;

import net.arcaniax.gopaint.paint.brush.player.PlayerBrush;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public abstract class GoPaintInventory implements InventoryHolder {

    /**
     * The inventory associated with this GoPaintInventory.
     */
    protected Inventory inventory;

    /**
     * Get the inventory associated with this GoPaintInventory.
     *
     * @return The inventory.
     */
    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    /**
     * Create a custom inventory for a specific player brush.
     *
     * @param playerBrush The PlayerBrush associated with the inventory.
     * @return The created custom inventory.
     */
    public abstract Inventory createInventory(PlayerBrush playerBrush);

    /**
     * Handle interactions with the custom inventory.
     *
     * @param event The InventoryClickEvent representing the interaction event.
     */
    public abstract void interactInventory(InventoryClickEvent event);
}
