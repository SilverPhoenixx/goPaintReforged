/*
 * goPaint is designed to simplify painting inside of Minecraft.
 * Copyright (C) Arcaniax-Development
 * Copyright (C) Arcaniax team and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package net.arcaniax.gopaint.paint.brush.settings;

import net.arcaniax.gopaint.paint.player.PlayerBrush;
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
