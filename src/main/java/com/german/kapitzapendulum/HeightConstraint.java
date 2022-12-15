package com.german.kapitzapendulum;

import com.german.physic.Constraint;
import com.german.physic.PhysicConstants;
import com.german.physic.Vector2D;

public class HeightConstraint implements Constraint {
    private final Vector2D location;
    private final Vector2D constrainedLocation;
    private final Vector2D velocity;
    private final Vector2D constrainedVelocity;
    private final double constrainedHeight;

    public HeightConstraint(Vector2D location,
                            Vector2D constrainedLocation,
                            Vector2D velocity,
                            Vector2D constrainedVelocity,
                            double constrainedHeight) {
        this.location = location;
        this.constrainedLocation = constrainedLocation;
        this.velocity = velocity;
        this.constrainedVelocity = constrainedVelocity;
        this.constrainedHeight = constrainedHeight;
    }

    @Override
    public void solve() {
        Vector2D constraintVector = Vector2D.sum(location, constrainedLocation.getInvert());
        Vector2D constraintNormal = new Vector2D(0.0, -1.0);
        double heightViolation = Vector2D.dotProduct(constraintVector, constraintNormal) - constrainedHeight;
        if (heightViolation < 0.0) {
            double correctionScalar = -Vector2D.dotProduct(constrainedVelocity, constraintNormal);
            correctionScalar += Vector2D.dotProduct(velocity, constraintNormal);
            correctionScalar /= 2;
            correctionScalar += heightViolation / PhysicConstants.TIME_INTEGRATOR;
            constrainedVelocity.add(Vector2D.product(constraintNormal, correctionScalar));
        }
    }
}
