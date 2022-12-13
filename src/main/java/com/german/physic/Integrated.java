package com.german.physic;

public interface Integrated {
    Vector2D getLocation();

    Vector2D getVelocity();

    default void integrate() {
        getLocation().add(Vector2D.product(getVelocity(), PhysicConstants.TIME_INTEGRATOR));
    }
}
