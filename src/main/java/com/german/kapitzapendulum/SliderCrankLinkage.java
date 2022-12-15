package com.german.kapitzapendulum;

import com.german.Displayable;
import com.german.physic.LinearIntegrated;
import com.german.physic.PhysicConstants;
import com.german.physic.TimeVarying;
import com.german.physic.Vector2D;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.gl2.GLUT;

/**
 * Кривошипно-поршневой механизм
 * <a href="http://tm.spbstu.ru/%D0%9A%D0%9F:_%D0%9A%D0%B8%D0%BD%D0%B5%D0%BC%D0%B0%D1%82%D0%B8%D0%BA%D0%B0_%D0%BA%D1%80%D0%B8%D0%B2%D0%BE%D1%88%D0%B8%D0%BF%D0%BD%D0%BE-%D1%88%D0%B0%D1%82%D1%83%D0%BD%D0%BD%D0%BE%D0%B3%D0%BE_%D0%BC%D0%B5%D1%85%D0%B0%D0%BD%D0%B8%D0%B7%D0%BC%D0%B0">кривошипно-поршневой механизм</a>
 */
public class SliderCrankLinkage implements Displayable, TimeVarying, LinearIntegrated {
    private static final double DEFAULT_CRANK_R = 0.05;

    private static final double DEFAULT_SLIDER_LENGTH = 0.25;

    private static final double MIN_CRANK_W = 0.0;
    private static final double MAX_CRANK_W = Math.PI * 500;
    private static final double CRANK_W_INCREMENT = Math.PI * 5;

    /**
     * радиус кривошипа
     * r
     */
    private final double crankR;
    /**
     * длина поршня
     * l
     */
    private final double sliderLength;
    /**
     * безразмерный параметр КШМ
     * λ = r/L
     * {@link this#crankR} / {@link this#sliderLength}
     */
    private final double lambda;

    private final Vector2D sliderLocation;
    private final Vector2D sliderVelocity;
    /**
     * угол поворота кривошипа
     */
    private double crankAngle;
    /**
     * угловая скорость кривошипа
     */
    private double crankW;

    public SliderCrankLinkage(double crankR, double sliderLength) {
        this.crankR = crankR;
        this.sliderLength = sliderLength;
        this.lambda = crankR / sliderLength;
        this.sliderLocation = new Vector2D();
        this.sliderVelocity = new Vector2D();
        this.crankW = 0.0;
    }

    public SliderCrankLinkage() {
        this(DEFAULT_CRANK_R, DEFAULT_SLIDER_LENGTH);
    }

    /**
     * Увеличить угловую скорость кривошипа
     *
     * @param sign знак инкремента
     */
    public void increaseCrankW(int sign) {
        this.crankW += sign * CRANK_W_INCREMENT;
        this.crankW = Math.max(MIN_CRANK_W, this.crankW);
        this.crankW = Math.min(MAX_CRANK_W, this.crankW);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        GLUT glut = new GLUT();

        gl.glPushMatrix();
        gl.glColor3d(1.0, 1.0, 1.0);
        gl.glTranslated(0.0, sliderLocation.getY(), 0.0);
        gl.glRotated(90.0, 1.0, 0.0, 0.0);
        glut.glutSolidCylinder(0.02, sliderLength, 10, 1);
        gl.glPopMatrix();
    }

    /**
     * линейная скорость поршня из угловой скорости и положения кривошипа:
     * ν = rω(sin(φ) + (λ/2)sin(2φ))
     */
    @Override
    public void vary() {
        crankAngle += crankW * PhysicConstants.TIME_INTEGRATOR;
        sliderVelocity.set(0.0, crankR * crankW * (Math.sin(crankAngle) + lambda / 2 * Math.sin(2 * crankAngle)));
        integrateLinear();
    }

    @Override
    public Vector2D getLocation() {
        return sliderLocation;
    }

    @Override
    public Vector2D getVelocity() {
        return sliderVelocity;
    }
}
