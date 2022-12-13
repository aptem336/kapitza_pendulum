package com.german.physic;

public interface LinearIntegrated {
    Vector2D getLocation();

    Vector2D getVelocity();

    default void integrateLinear() {
        getLocation().add(Vector2D.product(getVelocity(), PhysicConstants.TIME_INTEGRATOR));
    }
}
