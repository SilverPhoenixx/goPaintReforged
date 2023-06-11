package net.arcaniax.gopaint.objects.brush.settings;

import net.arcaniax.gopaint.GoPaintPlugin;
import net.arcaniax.gopaint.objects.player.PlayerBrush;
import net.arcaniax.gopaint.utils.Items;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class FractureSetting extends AbstractSetting {

    @Override
    public ItemStack getItem(final PlayerBrush playerBrush) {
        return Items.create(
                        Material.DAYLIGHT_DETECTOR,
                1,
                        "&6Fracture Check Distance: &e" + playerBrush.getFractureDistance(),
                        "___&7Left click to increase___&7Right click to decrease"
        );
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
