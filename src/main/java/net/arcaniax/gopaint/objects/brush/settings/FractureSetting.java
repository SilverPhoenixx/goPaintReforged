package net.arcaniax.gopaint.objects.brush.settings;

import net.arcaniax.gopaint.GoPaintPlugin;
import net.arcaniax.gopaint.objects.player.PlayerBrush;
import net.arcaniax.gopaint.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class FractureSetting extends AbstractSetting {

    @Override
    public ItemStack getItem(final PlayerBrush playerBrush) {
        return new ItemBuilder(Material.DAYLIGHT_DETECTOR)
                .setName("§6Fracture Check Distance: §e" + playerBrush.getFractureDistance())
                .setList("", "§7Left click to increase", "§7Right click to decrease")
                .create();
    }

    public void increase(PlayerBrush playerBrush, boolean isShifting) {
        if (playerBrush.getFractureDistance() < GoPaintPlugin.getSettings().getMaxFractureDistance()) {
             playerBrush.changeFracture(1);
        }
        playerBrush.updateInventory();
    }

    public void decrease(PlayerBrush playerBrush, boolean isShifting) {
        if (playerBrush.getFractureDistance() > 1) {
            playerBrush.changeFracture(-1);
        }
        playerBrush.updateInventory();
    }
}
