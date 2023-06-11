package net.arcaniax.gopaint.objects.brush.settings;

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


    private AbstractSetting setting;

    private BrushSettings(AbstractSetting setting) {
        this.setting = setting;
    }

    public AbstractSetting getSetting() {
        return setting;
    }
}
