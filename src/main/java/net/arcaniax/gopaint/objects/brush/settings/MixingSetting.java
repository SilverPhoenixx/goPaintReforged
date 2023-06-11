package net.arcaniax.gopaint.objects.brush.settings;

import net.arcaniax.gopaint.objects.player.PlayerBrush;
import net.arcaniax.gopaint.utils.Items;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MixingSetting extends AbstractSetting {

    @Override
    public ItemStack getItem(final PlayerBrush playerBrush) {
        return Items.create(
                Material.MAGMA_CREAM,
                1,
                "&6Mixing Strength: &e" + playerBrush.getMixingStrength() + "%",
                "___&7Left click to increase___&7Right click to decrease"
        );
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
