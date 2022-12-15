package com.german.kapitzapendulum;

import com.german.Displayable;
import com.german.physic.TimeVarying;
import com.jogamp.opengl.GLAutoDrawable;

public class KapitzaPendulum implements Displayable, TimeVarying {
    private static final double DEFAULT_CRANK_R = 0.05;
    private static final double DEFAULT_SLIDER_LENGTH = 0.25;
    private static final double DEFAULT_CONSTRAINED_LENGTH = 0.5;

    private final SliderCrankLinkage sliderCrankLinkage;
    private final Pendulum pendulum;
    private final LengthConstraint lengthConstraint;
    private final HeightConstraint heightConstraint;

    public KapitzaPendulum(double crankR, double sliderLength, double constrainedLength) {
        sliderCrankLinkage = new SliderCrankLinkage(crankR, sliderLength);
        pendulum = new Pendulum() {{
            double xy45 = Math.cos(Math.PI / 4) * sliderLength / 2 + constrainedLength;
            getLocation().set(xy45, xy45);
        }};
        lengthConstraint = new LengthConstraint(sliderCrankLinkage.getLocation(),
                pendulum.getLocation(),
                sliderCrankLinkage.getVelocity(),
                pendulum.getVelocity(),
                sliderLength / 2 + constrainedLength);
        heightConstraint = new HeightConstraint(sliderCrankLinkage.getLocation(),
                pendulum.getLocation(),
                sliderCrankLinkage.getVelocity(),
                pendulum.getVelocity(),
                0.0);
    }

    public KapitzaPendulum() {
        this(DEFAULT_CRANK_R, DEFAULT_SLIDER_LENGTH, DEFAULT_CONSTRAINED_LENGTH);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        sliderCrankLinkage.display(glAutoDrawable);
        pendulum.display(glAutoDrawable);
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
