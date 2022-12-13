package com.german.physic;

public interface Gravitated {
    Vector2D getVelocity();

    default void gravitate() {
        getVelocity().add(Vector2D.product(PhysicConstants.G, PhysicConstants.TIME_INTEGRATOR));
    }
}
