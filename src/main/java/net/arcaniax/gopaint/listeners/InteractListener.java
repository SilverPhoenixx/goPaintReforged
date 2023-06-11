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
import net.arcaniax.gopaint.objects.player.AbstractPlayerBrush;
import net.arcaniax.gopaint.objects.player.ExportedPlayerBrush;
import net.arcaniax.gopaint.objects.player.PlayerBrush;
import net.arcaniax.gopaint.utils.gui.MenuInventory;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class InteractListener implements Listener {

    public GoPaintPlugin plugin;

    public InteractListener(GoPaintPlugin main) {
        plugin = main;
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Location location;

        if (GoPaintPlugin.nmsManager.isAtLeastVersion(1, 9, 0)) {
            if (e.getHand() == EquipmentSlot.OFF_HAND) {
                return;
            }
        }
        if (!e.getPlayer().hasPermission("gopaint.use")) {
            return;
        }


        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_AIR)) {
            location = player.getTargetBlock(null, 250).getLocation().clone();
        } else {
            location = e.getClickedBlock().getLocation().clone();
        }

        if (location.getBlock().getType().equals(Material.AIR)) {
            return;
        }


        if ((!e.getPlayer().hasPermission("gopaint.world.bypass")) && (GoPaintPlugin
                .getSettings()
                .getDisabledWorlds()
                .contains(location.getWorld().getName()))) {
            return;
        }


        AbstractPlayerBrush playerBrush;
        if (e.getPlayer().getItemInHand().hasItemMeta() &&
                e.getPlayer().getItemInHand().getItemMeta().hasDisplayName() &&
                e.getPlayer()
                        .getItemInHand()
                        .getItemMeta()
                        .getDisplayName()
                        .startsWith(" §b♦ ") && e.getPlayer().getItemInHand().getItemMeta().hasLore()) {
            playerBrush = new ExportedPlayerBrush(player.getItemInHand()
                    .getItemMeta()
                    .getDisplayName(), e.getPlayer().getItemInHand().getItemMeta().getLore());
        } else if(player.getInventory().getItemInMainHand().getType() == Material.FEATHER) {
           playerBrush = GoPaintPlugin.getBrushManager().getPlayerBrush(player);
        } else {
            return;
        }

        if(player.isSneaking() && (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK))) {
            Player p = e.getPlayer();
            PlayerBrush pb = GoPaintPlugin.getBrushManager().getPlayerBrush(p);
            p.openInventory(new MenuInventory().createInventory(pb));
            return;
        }

        if(!playerBrush.isEnabled()) {
            player.sendMessage(GoPaintPlugin
                    .getSettings()
                    .getPrefix() + "§cYour brush is disabled, left click to enable the brush.");
            return;
        }

        playerBrush.getBrush().interact(e, playerBrush, location);
        }
}
