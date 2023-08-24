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
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractSetting {

    /**
     * Get the ItemStack representation of this setting.
     *
     * @param playerBrush The PlayerBrush instance associated with the player.
     * @return The ItemStack representing this setting.
     */
    public abstract ItemStack getItem(PlayerBrush playerBrush);

    /**
     * Increase the setting value.
     *
     * @param playerBrush The PlayerBrush instance associated with the player.
     * @param isShifting  Indicates whether the player is holding the shift key.
     */
    public abstract void increase(PlayerBrush playerBrush, boolean isShifting);

    /**
     * Decrease the setting value.
     *
     * @param playerBrush The PlayerBrush instance associated with the player.
     * @param isShifting  Indicates whether the player is holding the shift key.
     */
    public abstract void decrease(PlayerBrush playerBrush, boolean isShifting);

    /**
     * Perform an interaction with the setting based on the ClickType.
     *
     * @param playerBrush The PlayerBrush instance associated with the player.
     * @param clickType   The ClickType that triggered the interaction.
     */
    public void interact(PlayerBrush playerBrush, ClickType clickType) {
        // Determine the click type and perform corresponding action
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
