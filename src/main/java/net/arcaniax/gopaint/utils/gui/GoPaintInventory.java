package net.arcaniax.gopaint.utils.gui;

import net.arcaniax.gopaint.objects.player.PlayerBrush;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public abstract class GoPaintInventory implements InventoryHolder {

    protected Inventory inventory;

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public abstract Inventory createInventory(PlayerBrush playerBrush);
    public abstract void interactInventory(InventoryClickEvent event);
}
