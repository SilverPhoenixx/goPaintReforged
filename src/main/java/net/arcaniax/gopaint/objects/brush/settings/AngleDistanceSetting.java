package net.arcaniax.gopaint.objects.brush.settings;

import net.arcaniax.gopaint.GoPaintPlugin;
import net.arcaniax.gopaint.objects.player.PlayerBrush;
import net.arcaniax.gopaint.utils.Items;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AngleDistanceSetting extends AbstractSetting {


    @Override
    public ItemStack getItem(PlayerBrush playerBrush) {
        return Items.create(
                        Material.DAYLIGHT_DETECTOR,
                        1,
                        "&6Angle Check Distance: &e" + playerBrush.getAngleDistance(),
                        "___&7Left click to increase___&7Right click to decrease"
        );
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
