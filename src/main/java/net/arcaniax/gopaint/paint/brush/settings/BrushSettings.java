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

public enum BrushSettings {

    CHANCE(new ChanceSetting()),
    ANGLE_DISTANCE(new AngleDistanceSetting()),
    ANGLE_HEIGHT(new AngleHeightSetting()),
    AXIS(new AxisSetting()),
    SIZE(new SizeSetting()),
    MIXING(new MixingSetting()),
    THICKNESS(new ThicknessSetting()),
    FALLOFF_STRENGTH(new FalloffStrengthSetting()),
    FRACTURE(new FractureSetting());


    private final AbstractSetting setting;

    BrushSettings(AbstractSetting setting) {
        this.setting = setting;
    }

    public AbstractSetting getSetting() {
        return setting;
    }
}
