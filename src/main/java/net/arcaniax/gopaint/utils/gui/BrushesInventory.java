package net.arcaniax.gopaint.utils.gui;

import net.arcaniax.gopaint.GoPaintPlugin;
import net.arcaniax.gopaint.objects.brush.Brush;
import net.arcaniax.gopaint.objects.player.PlayerBrush;
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
        for (Brush brush : GoPaintPlugin.getBrushManager().getColorBrushes()) {

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
        PlayerBrush pb = GoPaintPlugin.getBrushManager().getPlayerBrush(player);
        boolean check = false;
            if (event.getCurrentItem().getType().equals(Material.getMaterial("SKULL_ITEM"))) {
                check = true;
            }

        if (check) {
            pb.setBrush(GoPaintPlugin
                    .getBrushManager()
                    .getColorBrush(event.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§6", "")));
            player.openInventory(new MenuInventory().createInventory(pb));
        }
    }

}
