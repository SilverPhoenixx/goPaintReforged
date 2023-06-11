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
package net.arcaniax.gopaint.command;

import net.arcaniax.gopaint.GoPaintPlugin;
import net.arcaniax.gopaint.objects.player.PlayerBrush;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Handler implements CommandExecutor {

    private GoPaintPlugin plugin;

    public Handler(GoPaintPlugin main) {
        plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gopaint") || cmd.getName().equalsIgnoreCase("gp")) {
            if (!(sender instanceof Player)) {
                return false;
            }



            Player player = (Player) sender;
            PlayerBrush playerBrush = GoPaintPlugin.getBrushManager().getPlayerBrush(player);
            String prefix = GoPaintPlugin.getSettings().getPrefix();

            if (!player.hasPermission("gopaint.use")) {
                player.sendMessage(prefix + "§cYou are lacking the permission gopaint.use");
                return true;
            }

            switch (args.length) {
                case 0: {
                    if (player.hasPermission("gopaint.admin")) {
                        player.sendMessage(prefix + "§c/gp size§7|§ctoggle§7|§cinfo");
                        return true;
                    }
                    player.sendMessage(prefix + "§c/gp size§7|§ctoggle§7|§cinfo§7|§creload");
                    return true;
                }
                case 1: {
                    switch (args[0].toLowerCase()) {
                        case "size": {
                            player.sendMessage(prefix + "§c/gp size [number]");
                            return true;
                        }
                        case "toggle": {
                            playerBrush.toggleEnabled();
                            String status = playerBrush.isEnabled() ? "§aEnabled" : "§cDisabled";
                            player.sendMessage(prefix + status + " brush");
                            return true;
                        }
                        case "r":
                        case "reload": {
                            if(player.hasPermission("gopaint.admin")) {
                                GoPaintPlugin.reload();
                                player.sendMessage(prefix + "§aReloaded");
                                return true;
                            }
                            return true;
                        }
                        case "i":
                        case "info": {
                            player.spigot().sendMessage(new ComponentBuilder("goPaint> ").color(ChatColor.AQUA)
                                    .append("Created by: ").color(ChatColor.GOLD)
                                    .append("Arcaniax").color(ChatColor.YELLOW).create());


                            player.spigot().sendMessage(new ComponentBuilder("goPaint> ").color(ChatColor.AQUA)
                                    .append("Links: ").color(ChatColor.GOLD)
                                    .append("Twitter").color(ChatColor.DARK_AQUA).color(ChatColor.UNDERLINE)
                                    .event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://twitter.com/Arcaniax")).append("     ")
                                    .append("Spigot").color(ChatColor.YELLOW).color(ChatColor.UNDERLINE)
                                    .event(new ClickEvent(
                                            ClickEvent.Action.OPEN_URL,
                                            "https://www.spigotmc.org/resources/authors/arcaniax.47444/"
                                    )).create());
                            return true;
                        }
                        default: {
                            sendHelpMessage(player, prefix);
                            return true;
                        }
                    }
                }
                case 2: {
                    switch (args[0].toLowerCase()) {
                        case "size":
                        case "s": {
                            try {
                                int sizeAmount = Integer.parseInt(args[1]);
                                playerBrush.setBrushSize(sizeAmount);
                                player.sendMessage(prefix + "§6Size set to: §e" + playerBrush.getBrushSize());
                                return true;
                            } catch (Exception e) {
                                player.sendMessage(prefix + "§c/gb size [number]");
                                return true;
                            }
                        }
                    }
                }
            }
            sendHelpMessage(player, prefix);
        }
        return false;
    }


    public void sendHelpMessage(Player player, String prefix) {
        if (player.hasPermission("gopaint.admin")) {
            player.sendMessage(prefix + "§c/gp size§7|§ctoggle§7|§cinfo§7|§creload");
            return;
        }
        player.sendMessage(prefix + "§c/gp size§7|§ctoggle§7|§cinfo");
    }
}
