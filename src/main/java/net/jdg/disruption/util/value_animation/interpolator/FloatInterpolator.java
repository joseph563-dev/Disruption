package net.jdg.disruption.util.value_animation.interpolator;


import net.jdg.disruption.util.value_animation.Easing;

public class FloatInterpolator extends Interpolater<Float> {

    public static final FloatInterpolator linear = new FloatInterpolator();
    public static final FloatInterpolator sine_out = new FloatInterpolator(Easing.SINE_OUT);
    public static final FloatInterpolator sine_in = new FloatInterpolator(Easing.SINE_IN);
    public static final FloatInterpolator sine_in_out = new FloatInterpolator(Easing.SINE_IN_OUT);

    public FloatInterpolator(Easing easing) {
        super(easing);
    }

    public FloatInterpolator() {
        super();
    }

    @Override
    public Float withEasing(Easing easing, float delta, Float min, Float max) {
        return easing.ease(min < max ? delta : 1.0f - delta, Math.min(max, min), Math.max(max, min), 1.0f);
    }

    @Override
    public Float interpolate(float delta, Float min, Float max) {
        return easing.ease(min < max ? delta : 1.0f - delta, Math.min(max, min), Math.max(max, min), 1.0f);
    }
}
