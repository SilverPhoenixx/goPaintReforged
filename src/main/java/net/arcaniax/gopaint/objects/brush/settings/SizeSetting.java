package net.arcaniax.gopaint.objects.brush.settings;

import net.arcaniax.gopaint.GoPaintPlugin;
import net.arcaniax.gopaint.objects.player.PlayerBrush;
import net.arcaniax.gopaint.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SizeSetting extends AbstractSetting {

    @Override
    public ItemStack getItem(final PlayerBrush playerBrush) {
        return new ItemBuilder(Material.BROWN_MUSHROOM)
                .setName("§6Brush Size: §e" + playerBrush.getBrushSize())
                .setList("", "§7Left click to increase", "§7Right click to decrease", "§7Shift click to change by 10")
                .create();
    }

    @Override
    public void increase(final PlayerBrush playerBrush, final boolean isShifting) {
        if (isShifting) {
            if (playerBrush.getBrushSize() + 10 <= GoPaintPlugin.getSettings().getMaxSize()) {
                playerBrush.changeBrushSize(10);
            } else {
                playerBrush.setBrushSize(GoPaintPlugin.getSettings().getMaxSize());
            }
        } else {
            if (playerBrush.getBrushSize() < GoPaintPlugin.getSettings().getMaxSize()) {
                playerBrush.changeBrushSize(1);
            }
        }
        playerBrush.updateInventory();
    }

    @Override
    public void decrease(final PlayerBrush playerBrush, final boolean isShifting) {
        if (isShifting) {
            if (playerBrush.getBrushSize() - 10 >= 1) {
                playerBrush.changeBrushSize(-10);
            } else {
                playerBrush.setBrushSize(1);
            }
        } else {
            if (playerBrush.getBrushSize() > 1) {
                playerBrush.changeBrushSize(-1);
            }
        }
        playerBrush.updateInventory();
    }

}
