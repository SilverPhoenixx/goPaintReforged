package net.arcaniax.gopaint.objects.brush.settings;

import net.arcaniax.gopaint.GoPaintPlugin;
import net.arcaniax.gopaint.objects.player.PlayerBrush;
import net.arcaniax.gopaint.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AngleDistanceSetting extends AbstractSetting {


    @Override
    public ItemStack getItem(PlayerBrush playerBrush) {
        return new ItemBuilder(Material.DAYLIGHT_DETECTOR)
                .setName("§6Angle Check Distance: §e" + playerBrush.getAngleDistance())
                .setList("", "§7Left click to increase", "§7Right click to decrease")
                .create();
    }

    public void increase(PlayerBrush playerBrush, boolean isShifting) {
        if (playerBrush.getAngleDistance() < GoPaintPlugin.getSettings().getMaxAngleDistance()) {
            playerBrush.changeAngleDistance(1);
        }
        playerBrush.updateInventory();
    }

    public void decrease(PlayerBrush playerBrush, boolean isShifting) {
        if (playerBrush.getAngleDistance() > 1) {
            playerBrush.changeAngleDistance(-1);
        }
        playerBrush.updateInventory();
    }
}
