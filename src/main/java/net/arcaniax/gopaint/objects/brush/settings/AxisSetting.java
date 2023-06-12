package net.arcaniax.gopaint.objects.brush.settings;

import net.arcaniax.gopaint.objects.player.PlayerBrush;
import net.arcaniax.gopaint.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AxisSetting extends AbstractSetting {

    @Override
    public ItemStack getItem(final PlayerBrush playerBrush) {
        return new ItemBuilder(Material.COMPASS)
                .setName( "§6Axis: §e" + playerBrush.getAxis())
                .setList( "", "§7Click to change")
                .create();
    }

    @Override
    public void increase(final PlayerBrush playerBrush, final boolean isShifting) {
        String newAxis = playerBrush.getAxis();
        switch (playerBrush.getAxis()) {
            case "y":
                newAxis = "z";
                break;
            case "z":
                newAxis = "x";
                break;
            case "x":
                newAxis = "y";
                break;
        }
        playerBrush.changeAxis(newAxis);
        playerBrush.updateInventory();
    }

    @Override
    public void decrease(final PlayerBrush playerBrush, final boolean isShifting) {
        String newAxis = playerBrush.getAxis();
        switch (playerBrush.getAxis()) {
            case "x":
                newAxis = "z";
                break;
            case "y":
                newAxis = "x";
                break;
            case "z":
                newAxis = "y";
                break;
        }
        playerBrush.changeAxis(newAxis);
        playerBrush.updateInventory();
    }

}
