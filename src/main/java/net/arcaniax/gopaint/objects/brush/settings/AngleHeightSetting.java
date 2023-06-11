package net.arcaniax.gopaint.objects.brush.settings;

import net.arcaniax.gopaint.GoPaintPlugin;
import net.arcaniax.gopaint.objects.player.PlayerBrush;
import net.arcaniax.gopaint.utils.Items;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AngleHeightSetting extends AbstractSetting {

    @Override
    public ItemStack getItem(final PlayerBrush playerBrush) {
        return Items.create(
                        Material.BLAZE_ROD,
                        1,
                        "&6Maximum Angle: &e" + playerBrush.getMinHeightDifference() + "°",
                        "___&7Left click to increase___&7Right click to decrease___&7Shift click to change by 15"
        );
    }

    public void increase(PlayerBrush playerBrush, boolean isShifting) {
        if (isShifting) {
            playerBrush.chanceMinAngleHeight(15.0);
        } else {
            playerBrush.chanceMinAngleHeight(5.0);
        }
        if (playerBrush.getMinHeightDifference() > GoPaintPlugin.getSettings().getMaxAngleHeightDifference()) {
            playerBrush.setMinHeightDifference(GoPaintPlugin.getSettings().getMaxAngleHeightDifference());
        }
        playerBrush.updateInventory();
    }

    public void decrease(PlayerBrush playerBrush, boolean isShifting) {
        if (isShifting) {
            playerBrush.chanceMinAngleHeight(-15.0);
        } else {
            playerBrush.chanceMinAngleHeight(-5.0);
        }
        if (playerBrush.getMinHeightDifference() < GoPaintPlugin.getSettings().getMinAngleHeightDifference()) {
            playerBrush.setMinHeightDifference(GoPaintPlugin.getSettings().getMinAngleHeightDifference());
        }
        playerBrush.updateInventory();
    }

}
