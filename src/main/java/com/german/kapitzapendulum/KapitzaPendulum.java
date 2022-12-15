package com.german.kapitzapendulum;

import com.german.Displayable;
import com.german.physic.TimeVarying;
import com.german.physic.Vector2D;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.gl2.GLUT;

public class KapitzaPendulum implements Displayable, TimeVarying {
    private static final double DEFAULT_CRANK_R = 0.09;
    private static final double DEFAULT_SLIDER_LENGTH = 0.45;
    private static final double DEFAULT_CONSTRAINED_LENGTH = 0.9;

    private final double constrainedLength;
    private final SliderCrankLinkage sliderCrankLinkage;
    private final Pendulum pendulum;
    private final LengthConstraint lengthConstraint;
    private final HeightConstraint heightConstraint;
    private final double toBottomCorrection;

    public KapitzaPendulum(double crankR, double sliderLength, double constrainedLength) {
        this.constrainedLength = constrainedLength;

        sliderCrankLinkage = new SliderCrankLinkage(crankR, sliderLength);
        pendulum = new Pendulum() {{
            double xy45 = Math.cos(Math.PI / 4) + constrainedLength;
            getLocation().set(xy45, xy45);
        }};
        lengthConstraint = new LengthConstraint(sliderCrankLinkage.getLocation(),
                pendulum.getLocation(),
                sliderCrankLinkage.getVelocity(),
                pendulum.getVelocity(),
                constrainedLength);
        heightConstraint = new HeightConstraint(sliderCrankLinkage.getLocation(),
                pendulum.getLocation(),
                sliderCrankLinkage.getVelocity(),
                pendulum.getVelocity(),
                0.0);
        toBottomCorrection = -1.0 - crankR + sliderLength;
    }

    public KapitzaPendulum() {
        this(DEFAULT_CRANK_R, DEFAULT_SLIDER_LENGTH, DEFAULT_CONSTRAINED_LENGTH);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        GLUT glut = new GLUT();

        gl.glPushMatrix();
        gl.glTranslated(0.0, toBottomCorrection, 0.0);
        sliderCrankLinkage.display(glAutoDrawable);
        pendulum.display(glAutoDrawable);
        Vector2D pendulumToSliderNormal = Vector2D.sum(pendulum.getLocation(), sliderCrankLinkage.getLocation().getInvert()).getNormalized();
        gl.glPushMatrix();
        gl.glColor3d(0.5, 0.5, 0.5);
        gl.glTranslated(sliderCrankLinkage.getLocation().getX(), sliderCrankLinkage.getLocation().getY(), 0.0);
        gl.glRotated(90, 1.0, 0.0, 0.0);
        gl.glRotated(Math.toDegrees(Math.acos(pendulumToSliderNormal.getX())) + 90, 0.0, 1.0, 0.0);
        glut.glutSolidCylinder(0.01, constrainedLength, 10, 1);
        gl.glPopMatrix();

        gl.glPopMatrix();
    }

    @Override
    public void vary() {
        sliderCrankLinkage.vary();
        pendulum.gravitate();
        lengthConstraint.solve();
        heightConstraint.solve();
        pendulum.vary();
    }

    /**
     * Увеличить угловую скорость кривошипа
     *
     * @param sign знак инкремента
     */
    public void increaseCrankR(int sign) {
        sliderCrankLinkage.increaseCrankW(sign);
    }
}
