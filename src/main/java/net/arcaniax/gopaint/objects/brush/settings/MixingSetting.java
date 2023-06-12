package net.arcaniax.gopaint.objects.brush.settings;

import net.arcaniax.gopaint.objects.player.PlayerBrush;
import net.arcaniax.gopaint.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MixingSetting extends AbstractSetting {

    @Override
    public ItemStack getItem(final PlayerBrush playerBrush) {
        return new ItemBuilder(Material.MAGMA_CREAM)
                .setName("§6Mixing Strength: §e" + playerBrush.getMixingStrength() + "%")
                .setList("", "§7Left click to increase", "§7Right click to decrease")
                .create();
    }

    public void decrease(PlayerBrush playerBrush, boolean isShifting) {
        if (playerBrush.getMixingStrength() >= 10) {
            playerBrush.changeMixingStrength(-10);
        }
        playerBrush.updateInventory();
    }

    public void increase(PlayerBrush playerBrush, boolean isShifting) {
        if (playerBrush.getMixingStrength() <= 90) {
            playerBrush.changeMixingStrength(10);
        }
        playerBrush.updateInventory();
    }
}
