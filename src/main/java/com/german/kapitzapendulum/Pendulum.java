package com.german.kapitzapendulum;

import com.german.Displayable;
import com.german.physic.*;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.gl2.GLUT;

public class Pendulum implements Displayable, TimeVarying, LinearIntegrated, Gravitated {
    private final Vector2D location;
    private final Vector2D velocity;
    public Pendulum() {
        this.location = new Vector2D();
        this.velocity = new Vector2D();
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        GLUT glut = new GLUT();

        gl.glPushMatrix();
        gl.glColor3d(1.0, 0.0, 0.0);
        gl.glTranslated(location.getX(), location.getY(), 0.0);
        glut.glutSolidSphere(0.05, 20, 20);
        gl.glPopMatrix();
    }

    @Override
    public Vector2D getLocation() {
        return location;
    }

    @Override
    public Vector2D getVelocity() {
        return velocity;
    }

    @Override
    public void vary() {
        integrateLinear();
    }
}
