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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSevent.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package net.arcaniax.gopaint.listeners;

import net.arcaniax.gopaint.GoPaint;
import net.arcaniax.gopaint.paint.brush.player.AbstractPlayerBrush;
import net.arcaniax.gopaint.paint.brush.player.ExportedPlayerBrush;
import net.arcaniax.gopaint.paint.brush.player.PlayerBrush;
import net.arcaniax.gopaint.inventories.MenuInventory;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class InteractListener implements Listener {

    /**
     * The GoPaint plugin instance.
     */
    public GoPaint plugin;

    /**
     * Constructor for InteractListener.
     *
     * @param plugin The GoPaint plugin instance.
     */
    public InteractListener(GoPaint plugin) {
        this.plugin = plugin;
    }

    /**
     * Handles player interaction events.
     *
     * @param event The PlayerInteractEvent object representing the event.
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Location location;

        if (event.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }

        if (!event.getPlayer().hasPermission("gopaint.use")) {
            return;
        }

        // Check if the player is holding an exported brush.
        AbstractPlayerBrush playerBrush = isExportedBrush(player);

        if (player.getInventory().getItemInMainHand().getType() == Material.FEATHER) {
            // If the player is holding a feather, get their active brush.
            playerBrush = GoPaint.getBrushManager().getPlayerBrush(player);
        } else if (playerBrush == null) {
            return;
        }

        if (player.isSneaking() && (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK))) {
            // Open the brush menu when the player sneaks and left-clicks.
            Player p = event.getPlayer();
            PlayerBrush pb = GoPaint.getBrushManager().getPlayerBrush(p);
            p.openInventory(new MenuInventory().createInventory(pb));
            return;
        }

        if (!playerBrush.isEnabled()) {
            player.sendMessage(GoPaint.getSettings().getPrefix() + "§cYour brush is disabled, left click to enable the brush.");
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_AIR) {
            // Get the target block location in the air.
            location = player.getTargetBlock(Set.of(Material.AIR, Material.WATER, Material.LAVA), 250).getLocation().clone();
        } else {
            // Get the clicked block location.
            location = event.getClickedBlock().getLocation().clone();
        }

        if (location.getBlock().getType().equals(Material.AIR)) {
            return;
        }

        if ((!event.getPlayer().hasPermission("gopaint.world.bypass")) && (GoPaint.getSettings().getDisabledWorlds().contains(location.getWorld().getName()))) {
            return;
        }

        // Perform the brush interaction.
        playerBrush.getBrush().interact(event, playerBrush, location);
    }

    /**
     * Checks if the player is holding an exported brush.
     *
     * @param player The player to check.
     * @return An AbstractPlayerBrush instance if the player is holding an exported brush, or null otherwise.
     */
    public AbstractPlayerBrush isExportedBrush(Player player) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (!itemStack.hasItemMeta()) {
            return null;
        }
        if (!itemStack.getItemMeta().hasDisplayName()) {
            return null;
        }
        if (!itemStack.getItemMeta().getDisplayName().startsWith("§3GoPaint §7>§b Exported Brush §7>§b ")) {
            return null;
        }
        if (!itemStack.getItemMeta().hasLore()) {
            return null;
        }

        return new ExportedPlayerBrush(itemStack);
    }
}
