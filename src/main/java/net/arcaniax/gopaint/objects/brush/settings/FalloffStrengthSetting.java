package net.arcaniax.gopaint.objects.brush.settings;

import net.arcaniax.gopaint.objects.player.PlayerBrush;
import net.arcaniax.gopaint.utils.Items;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class FalloffStrengthSetting extends AbstractSetting {

    @Override
    public ItemStack getItem(final PlayerBrush playerBrush) {
        return Items.create(
                        Material.BLAZE_POWDER,
                        1,
                        "&6Falloff Strength: &e" + playerBrush.getFalloffStrength() + "%",
                        "___&7Left click to increase___&7Right click to decrease"
        );
    }

    public void increase(PlayerBrush playerBrush, boolean isShifting) {
        if (playerBrush.getFalloffStrength() <= 90) {
            playerBrush.addFalloffStrength(10);
        }
        playerBrush.updateInventory();
    }

    public void decrease(PlayerBrush playerBrush, boolean isShifting) {
        if (playerBrush.getFalloffStrength() >= 10) {
            playerBrush.removeFalloffStrength(10);
        }
        playerBrush.updateInventory();
    }
}
