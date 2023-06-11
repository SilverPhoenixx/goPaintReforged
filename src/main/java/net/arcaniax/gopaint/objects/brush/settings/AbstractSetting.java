package net.arcaniax.gopaint.objects.brush.settings;

import net.arcaniax.gopaint.objects.player.PlayerBrush;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractSetting {

    public abstract ItemStack getItem(PlayerBrush playerBrush);


    public abstract void increase(PlayerBrush playerBrush, boolean isShifting);
    public abstract void decrease(PlayerBrush playerBrush, boolean isShifting);

    public void interact(PlayerBrush playerBrush, ClickType clickType) {
        if (clickType.equals(ClickType.LEFT)) {
            increase(playerBrush, false);
        } else if (clickType.equals(ClickType.RIGHT)) {
            decrease(playerBrush, false);
        } else if (clickType.equals(ClickType.SHIFT_LEFT)) {
            increase(playerBrush, true);
        } else if (clickType.equals(ClickType.SHIFT_RIGHT)) {
            decrease(playerBrush, true);
        }
    }
}
