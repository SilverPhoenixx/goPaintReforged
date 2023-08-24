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

import net.arcaniax.gopaint.GoPaint;
import net.arcaniax.gopaint.paint.brush.player.PlayerBrush;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GoPaintCommand implements CommandExecutor {

    private GoPaint plugin;

    /**
     * Constructor for GoPaintCommand.
     *
     * @param plugin The GoPaint plugin instance.
     */
    public GoPaintCommand(GoPaint plugin) {
        this.plugin = plugin;
    }

    /**
     * Executes the /gp command.
     *
     * @param sender The command sender.
     * @param cmd    The command.
     * @param label  The command label.
     * @param args   The command arguments.
     * @return true if the command was executed successfully, false otherwise.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        PlayerBrush playerBrush = GoPaint.getBrushManager().getPlayerBrush(player);
        String prefix = GoPaint.getSettings().getPrefix();

        if (!player.hasPermission("gopaint.use")) {
            player.sendMessage(prefix + "§cYou are lacking the permission gopaint.use");
            return true;
        }


        switch (args.length) {
            case 0 -> sendHelpMessage(player, prefix);
            case 1 -> {
                switch (args[0].toLowerCase()) {
                    case "size" -> player.sendMessage(prefix + "§c/gp size [number]");
                    case "toggle" -> {
                        playerBrush.toggleEnabled();
                        String status = playerBrush.isEnabled() ? "§aEnabled" : "§cDisabled";
                        player.sendMessage(prefix + status + " brush");
                    }
                    case "r", "reload" -> {
                        if (player.hasPermission("gopaint.admin")) {
                            GoPaint.reload();
                            player.sendMessage(prefix + "§aReloaded");
                        }
                    }
                    case "i", "info" -> {
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
                    }
                    default -> sendHelpMessage(player, prefix);
                }
            }
            case 2 -> {
                {
                    switch (args[0].toLowerCase()) {
                        case "size", "s" -> {
                            try {
                                int sizeAmount = Integer.parseInt(args[1]);
                                playerBrush.setBrushSize(sizeAmount);
                                player.sendMessage(prefix + "§6Size set to: §e" + playerBrush.getBrushSize());

                            } catch (Exception e) {
                                player.sendMessage(prefix + "§c/gb size [number]");
                            }
                        }
                        default -> sendHelpMessage(player, prefix);
                    }
                }
            }
            default -> sendHelpMessage(player, prefix);
        }
        return false;
    }

    /**
     * Sends a help message to the player.
     *
     * @param player The player to send the help message to.
     * @param prefix The plugin prefix.
     */
    public void sendHelpMessage(Player player, String prefix) {
        if (!player.hasPermission("gopaint.admin")) {
            player.sendMessage(prefix + "§c/gp size§7|§ctoggle§7|§cinfo");
            return;
        }
        player.sendMessage(prefix + "§c/gp size§7|§ctoggle§7|§cinfo§7|§creload");
    }
}
