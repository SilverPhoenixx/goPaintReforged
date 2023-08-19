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
import net.arcaniax.gopaint.paint.player.AbstractPlayerBrush;
import net.arcaniax.gopaint.paint.player.ExportedPlayerBrush;
import net.arcaniax.gopaint.paint.player.PlayerBrush;
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

public class InteractListener implements Listener {

    public GoPaint plugin;

    public InteractListener(GoPaint plugin) {
        this.plugin = plugin;
    }

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

        AbstractPlayerBrush playerBrush = isExportedBrush(player);
        if (player.getInventory().getItemInMainHand().getType() == Material.FEATHER) {
            playerBrush = GoPaint.getBrushManager().getPlayerBrush(player);
        } else if (playerBrush == null) {
            return;
        }


        if (player.isSneaking() && (event.getAction().equals(Action.LEFT_CLICK_AIR) || event
                .getAction()
                .equals(Action.LEFT_CLICK_BLOCK))) {
            Player p = event.getPlayer();
            PlayerBrush pb = GoPaint.getBrushManager().getPlayerBrush(p);
            p.openInventory(new MenuInventory().createInventory(pb));
            return;
        }

        if (!playerBrush.isEnabled()) {
            player.sendMessage(GoPaint.getSettings().getPrefix() + "§cYour brush is disabled, left click to enable the brush.");
            return;
        }


        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_AIR)) {
            location = player.getTargetBlock(null, 250).getLocation().clone();
        } else {
            location = event.getClickedBlock().getLocation().clone();
        }

        if (location.getBlock().getType().equals(Material.AIR)) {
            return;
        }

        if ((!event.getPlayer().hasPermission("gopaint.world.bypass")) && (GoPaint.getSettings().getDisabledWorlds().contains(
                location.getWorld().getName()))) {
            return;
        }

        playerBrush.getBrush().interact(event, playerBrush, location);
    }

    public AbstractPlayerBrush isExportedBrush(Player player) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (!itemStack.hasItemMeta()) {
            return null;
        }
        if (!itemStack.getItemMeta().hasDisplayName()) {
            return null;
        }
        if (!itemStack.getItemMeta().getDisplayName().startsWith(" §b♦ ")) {
            return null;
        }
        if (!itemStack.getItemMeta().hasLore()) {
            return null;
        }

        return new ExportedPlayerBrush(itemStack);
    }

}
