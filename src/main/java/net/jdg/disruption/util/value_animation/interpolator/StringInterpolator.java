package net.jdg.disruption.util.value_animation.interpolator;


import net.jdg.disruption.util.value_animation.Easing;

public class StringInterpolator extends Interpolater<String> {
    private final String[] strings;
    private final IntInterpolator interpolator;

    public StringInterpolator(Easing easing, String... strings) {
        super(null);
        this.strings = strings;
        this.interpolator = new IntInterpolator(easing);
    }

    public StringInterpolator(String... strings) {
        super(null);
        this.strings = strings;
        this.interpolator = new IntInterpolator();
    }

    @Override
    public String withEasing(Easing easing, float delta, String unused, String unused2) {
        var i = interpolator.withEasing(easing, delta,0, strings.length - 1);
        return strings[i];
    }

    @Override
    public String interpolate(float delta, String unused, String unused2) {
        var i = interpolator.interpolate(delta,0, strings.length - 1);
        return strings[i];
    }
}
