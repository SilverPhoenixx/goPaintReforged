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
