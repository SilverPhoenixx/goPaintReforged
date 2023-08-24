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

import com.sk89q.worldedit.world.biome.BiomeType;
import com.sk89q.worldedit.world.biome.BiomeTypes;
import net.arcaniax.gopaint.GoPaint;
import net.arcaniax.gopaint.paint.brush.player.PlayerBrush;
import net.arcaniax.gopaint.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class BiomesInventory extends GoPaintInventory {

    private int page;

    public BiomesInventory(int page) {
        this.page = page;
    }

    public BiomesInventory() {
        this(1);
    }

    @Override
    public Inventory createInventory(final PlayerBrush playerBrush) {

        List<BiomeType> biomeHolder = BiomeTypes.values().stream().toList();
        List<BiomeType> biomeTypes = new ArrayList<>(biomeHolder);
        biomeTypes.sort(Comparator.comparing(BiomeType::getId));

        int start = (page - 1) * 45;

        if (start >= biomeTypes.size()) {
            page--;
            return createInventory(playerBrush);
        }

        int end = page * 45;

        if (end > biomeTypes.size()) {
            end = biomeTypes.size();
        }

        List<BiomeType> listedBiomes = biomeTypes.stream().toList().subList(start, end);
        this.inventory = Bukkit.createInventory(this, 54, "§1goPaint Biomes | " + page);


        int position = 0;

        for (BiomeType biomeType : listedBiomes) {
            this.inventory.setItem(position, new ItemBuilder(Material.PAPER).setName(biomeType.getId()).create());
            position++;
        }

        if (page > 1) {
            this.inventory.setItem(
                    45,
                    new ItemBuilder(Material.PLAYER_HEAD)
                            .setName("Previous Page")
                            .setCustomHead(
                                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==")
                            .create()
            );
        }

        if ((page * 49) <= biomeTypes.size()) {
            this.inventory.setItem(53, new ItemBuilder(Material.PLAYER_HEAD)
                    .setName("Next Page")
                    .setCustomHead(
                            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19")
                    .create());

        }

        this.inventory.setItem(49, new ItemBuilder(Material.PLAYER_HEAD)
                .setName("Back to Menu")
                .setCustomHead(
                        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzQzNzM0NmQ4YmRhNzhkNTI1ZDE5ZjU0MGE5NWU0ZTc5ZGFlZGE3OTVjYmM1YTEzMjU2MjM2MzEyY2YifX19")
                .create());


        return this.inventory;
    }

    @Override
    public void interactInventory(final InventoryClickEvent event) {
        if (event.getCurrentItem() == null) {
            return;
        }
        if (event.getCurrentItem().getItemMeta() == null) {
            return;
        }


        Player player = (Player) event.getWhoClicked();
        PlayerBrush playerBrush = GoPaint.getBrushManager().getPlayerBrush(player);

        if (event.getSlot() >= 0 && event.getSlot() <= 44) {

            String itemDisplayName = event.getCurrentItem().getItemMeta().getDisplayName();

            playerBrush.addBiomes(
                    BiomeTypes.get(itemDisplayName),
                    8
            );
        } else if (event.getSlot() == 49) {
            player.openInventory(new MenuInventory().createInventory(playerBrush));
        } else if (event.getSlot() == 45) {
            player.openInventory(new BiomesInventory(page - 1).createInventory(playerBrush));
        } else if (event.getSlot() == 53) {
            player.openInventory(new BiomesInventory(page + 1).createInventory(playerBrush));
        }
    }

}
