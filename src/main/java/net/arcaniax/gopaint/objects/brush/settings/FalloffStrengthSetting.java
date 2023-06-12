package net.arcaniax.gopaint.objects.brush.settings;

import net.arcaniax.gopaint.objects.player.PlayerBrush;
import net.arcaniax.gopaint.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class FalloffStrengthSetting extends AbstractSetting {

    @Override
    public ItemStack getItem(final PlayerBrush playerBrush) {
        return new ItemBuilder(Material.BLAZE_POWDER)
                .setName("§6Falloff Strength: §e" + playerBrush.getFalloffStrength() + "%")
                .setList("", "§7Left click to increase", "§7Right click to decrease")
                .create();
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
