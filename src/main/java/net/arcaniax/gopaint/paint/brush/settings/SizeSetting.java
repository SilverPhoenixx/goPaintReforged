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

import net.arcaniax.gopaint.GoPaint;
import net.arcaniax.gopaint.paint.player.PlayerBrush;
import net.arcaniax.gopaint.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SizeSetting extends AbstractSetting {

    @Override
    public ItemStack getItem(final PlayerBrush playerBrush) {
        return new ItemBuilder(Material.BROWN_MUSHROOM)
                .setName("§6Brush Size: §e" + playerBrush.getBrushSize())
                .setList("", "§7Left click to increase", "§7Right click to decrease", "§7Shift click to change by 10")
                .create();
    }

    @Override
    public void increase(final PlayerBrush playerBrush, final boolean isShifting) {
        if (isShifting) {
            if (playerBrush.getBrushSize() + 10 <= GoPaint.getSettings().getMaxSize()) {
                playerBrush.changeBrushSize(10);
            } else {
                playerBrush.setBrushSize(GoPaint.getSettings().getMaxSize());
            }
        } else {
            if (playerBrush.getBrushSize() < GoPaint.getSettings().getMaxSize()) {
                playerBrush.changeBrushSize(1);
            }
        }
        playerBrush.updateInventory();
    }

    @Override
    public void decrease(final PlayerBrush playerBrush, final boolean isShifting) {
        if (isShifting) {
            if (playerBrush.getBrushSize() - 10 >= 1) {
                playerBrush.changeBrushSize(-10);
            } else {
                playerBrush.setBrushSize(1);
            }
        } else {
            if (playerBrush.getBrushSize() > 1) {
                playerBrush.changeBrushSize(-1);
            }
        }
        playerBrush.updateInventory();
    }

}
