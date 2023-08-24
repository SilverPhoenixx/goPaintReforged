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
package net.arcaniax.gopaint.paint.settings;

import net.arcaniax.gopaint.paint.brush.player.PlayerBrush;
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
        String newAxis = switch (playerBrush.getAxis()) {
            case "y" -> "z";
            case "z" -> "x";
            case "x" -> "y";
            default -> playerBrush.getAxis();
        };
        playerBrush.changeAxis(newAxis);
        playerBrush.updateInventory();
    }

    @Override
    public void decrease(final PlayerBrush playerBrush, final boolean isShifting) {
        String newAxis = switch (playerBrush.getAxis()) {
            case "x" -> "z";
            case "y" -> "x";
            case "z" -> "y";
            default -> playerBrush.getAxis();
        };
        playerBrush.changeAxis(newAxis);
        playerBrush.updateInventory();
    }

}
