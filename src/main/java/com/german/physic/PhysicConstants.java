package com.german.physic;

public interface PhysicConstants {
    double TIME_INTEGRATOR = 1.0 / 1000.0;
    Vector2D G = new Vector2D(0.0, -9.8 / TIME_INTEGRATOR);
}
