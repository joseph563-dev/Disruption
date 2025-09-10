package net.jdg.disruption.util.value_animation.interpolator;

import net.jdg.disruption.util.value_animation.Easing;
import org.joml.Vector3f;

public class Vector3fInterpolator extends Interpolater<Vector3f> {

    public static final Vector3fInterpolator linear = new Vector3fInterpolator();

    public Vector3fInterpolator(Easing easing) {
        super(easing);
    }

    public Vector3fInterpolator() {
        super();
    }

    @Override
    public Vector3f withEasing(Easing easing, float delta, Vector3f min, Vector3f max) {

        return new Vector3f(
                easing.ease(delta, min.x(), max.x(), 1),
                easing.ease(delta, min.y(), max.y(), 1),
                easing.ease(delta, min.z(), max.z(), 1)
        );
    }

    @Override
    public Vector3f interpolate(float delta, Vector3f min, Vector3f max) {
        return new Vector3f(
                    easing.ease(delta, min.x(), max.x(), 1),
                    easing.ease(delta, min.y(), max.y(), 1),
                    easing.ease(delta, min.z(), max.z(), 1)
                );
    }
}
