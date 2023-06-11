package net.arcaniax.gopaint.utils.gui;

import net.arcaniax.gopaint.GoPaintPlugin;
import net.arcaniax.gopaint.objects.brush.Brush;
import net.arcaniax.gopaint.objects.player.PlayerBrush;
import net.arcaniax.gopaint.utils.Items;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class BrushesInventory extends GoPaintInventory {

    @Override
    public Inventory createInventory(final PlayerBrush playerBrush) {
        this.inventory = Bukkit.createInventory(this, 27, "§1goPaint Brushes");
        // FILLER
        for (int x = 0; x < 27; x++) {
            this.inventory.setItem(
                    x,
                    Items.create(
                            Material.GRAY_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
        }
        int x = 0;
        for (Brush brush : GoPaintPlugin.getBrushManager().getColorBrushes()) {
            this.inventory.setItem(
                    x,
                    Items.createHead(
                            brush.getSkin(),
                            1,
                            "&6" + brush.getName(),
                            brush.getDescription()
                    )
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
