package net.arcaniax.gopaint.objects.brush.settings;

import net.arcaniax.gopaint.GoPaintPlugin;
import net.arcaniax.gopaint.objects.player.PlayerBrush;
import net.arcaniax.gopaint.utils.Items;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ThicknessSetting extends AbstractSetting {

    @Override
    public ItemStack getItem(final PlayerBrush playerBrush) {
        return Items.create(
                        Material.BOOK,
                        1,
                        "&6Layer Thickness: &e" + playerBrush.getThickness(),
                        "___&7Left click to increase___&7Right click to decrease"
        );
    }

    public void increase(PlayerBrush playerBrush, boolean isShifting) {
        if (playerBrush.getThickness() < GoPaintPlugin.getSettings().getMaxThickness()) {
            playerBrush.changeThickness(1);
        }
        playerBrush.updateInventory();
    }

    public void decrease(PlayerBrush playerBrush, boolean isShifting) {
        if (playerBrush.getThickness() > 1) {
            playerBrush.changeThickness(-1);
        }
        playerBrush.updateInventory();
    }
}
