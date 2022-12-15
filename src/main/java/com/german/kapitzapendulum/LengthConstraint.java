package com.german.kapitzapendulum;

import com.german.physic.Constraint;
import com.german.physic.PhysicConstants;
import com.german.physic.Vector2D;

public class LengthConstraint implements Constraint {
    private final Vector2D location;
    private final Vector2D constrainedLocation;
    private final Vector2D velocity;
    private final Vector2D constrainedVelocity;
    private final double constrainedLength;

    public LengthConstraint(Vector2D location,
                            Vector2D constrainedLocation,
                            Vector2D velocity,
                            Vector2D constrainedVelocity,
                            double constrainedLength) {
        this.location = location;
        this.constrainedLocation = constrainedLocation;
        this.velocity = velocity;
        this.constrainedVelocity = constrainedVelocity;
        this.constrainedLength = constrainedLength;
    }

    @Override
    public void solve() {
        Vector2D constraintVector = Vector2D.sum(location, constrainedLocation.getInvert());
        Vector2D constraintNormal = constraintVector.getNormalized();
        double lengthViolation = constraintVector.len() - constrainedLength;
        double correctionScalar = lengthViolation / PhysicConstants.TIME_INTEGRATOR;
        correctionScalar -= Vector2D.dotProduct(constrainedVelocity, constraintNormal);
        correctionScalar += Vector2D.dotProduct(velocity, constraintNormal);
        constrainedVelocity.add(Vector2D.product(constraintNormal, correctionScalar));
    }
}
