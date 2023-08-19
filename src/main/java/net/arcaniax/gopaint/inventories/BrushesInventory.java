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

import net.arcaniax.gopaint.GoPaint;
import net.arcaniax.gopaint.paint.brush.Brush;
import net.arcaniax.gopaint.paint.player.PlayerBrush;
import net.arcaniax.gopaint.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Arrays;

public class BrushesInventory extends GoPaintInventory {

    @Override
    public Inventory createInventory(final PlayerBrush playerBrush) {
        this.inventory = Bukkit.createInventory(this, 27, "§1goPaint Brushes");

        // FILLER
        for (int x = 0; x < 27; x++) {
            this.inventory.setItem(
                    x,
                    new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                            .setName("§7")
                            .create()
            );
        }
        int x = 0;


        ArrayList<String> mainBrushLore = new ArrayList<>();
        mainBrushLore.add("");
        mainBrushLore.add("§7Click to select");
        mainBrushLore.add("");
        mainBrushLore.add("");
        for (Brush brush : GoPaint.getBrushManager().getColorBrushes()) {

            ArrayList<String> brushLore = new ArrayList<>();
            brushLore.addAll(mainBrushLore);
            brushLore.addAll(Arrays.stream(brush.getDescription()).toList());

            this.inventory.setItem(
                    x,
                    new ItemBuilder(Material.PLAYER_HEAD)
                            .setName("§6" + brush.getName())
                            .setList(brushLore)
                            .setCustomHead(brush.getSkin())
                            .create()
            );
            x++;
        }
        return this.inventory;
    }

    @Override
    public void interactInventory(final InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        PlayerBrush pb = GoPaint.getBrushManager().getPlayerBrush(player);

        if(event.getCurrentItem() == null) return;
        if(event.getCurrentItem().getItemMeta() == null) return;

        boolean check = event.getCurrentItem().getType().equals(Material.getMaterial("SKULL_ITEM"));

        if (check) {
            pb.setBrush(GoPaint
                    .getBrushManager()
                    .getColorBrush(event.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§6", "")));
            player.openInventory(new MenuInventory().createInventory(pb));
        }
    }

}
