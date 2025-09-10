package net.jdg.disruption.util.value_animation.interpolator;

import net.jdg.disruption.util.value_animation.Easing;
import org.joml.Vector2f;

public class Vector2fInterpolator extends Interpolater<Vector2f> {

    public static final Vector2fInterpolator linear = new Vector2fInterpolator();


    public Vector2fInterpolator(Easing easing) {
        super(easing);
    }
    public Vector2fInterpolator() {
        super();
    }

    @Override
    public Vector2f withEasing(Easing easing, float delta, Vector2f min, Vector2f max) {
        return new Vector2f(
                easing.ease(delta, min.x(), max.x(), 1),
                easing.ease(delta, min.y(), max.y(), 1)
        );
    }

    @Override
    public Vector2f interpolate(float delta, Vector2f min, Vector2f max) {
        return new Vector2f(
                    easing.ease(delta, min.x(), max.x(), 1),
                    easing.ease(delta, min.y(), max.y(), 1)
                );
    }
}
