package net.arcaniax.gopaint.objects.brush.settings;

import net.arcaniax.gopaint.objects.player.PlayerBrush;
import net.arcaniax.gopaint.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ChanceSetting extends AbstractSetting {


    @Override
    public ItemStack getItem(PlayerBrush playerBrush) {
        return new ItemBuilder(Material.GOLD_NUGGET)
                .setName("§6Place chance: §e" + playerBrush.getChance() + "%")
                .setList("", "§7Left click to increase", "§7Right click to decrease")
                .create();
    }

    public void increase(PlayerBrush playerBrush, boolean isShifting) {
        if (playerBrush.getChance() < 90) {
            playerBrush.changeChance(10);
        }
        playerBrush.updateInventory();
    }

    public void decrease(PlayerBrush playerBrush, boolean isShifting) {
        if (playerBrush.getChance() > 10) {
            playerBrush.changeChance(10);
        }
        playerBrush.updateInventory();
    }


}
