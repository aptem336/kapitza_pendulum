package com.german.kapitzapendulum;

import com.german.GLSettings;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class KapitzaPendulumGLEventListener implements GLEventListener, MouseWheelListener {
    private KapitzaPendulum kapitzaPendulum;

    private static final String CONTROL_MESSAGE = "MouseWheel - speed";

    static {
        GLSettings.getMessages().add(CONTROL_MESSAGE);
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        kapitzaPendulum = new KapitzaPendulum();
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        kapitzaPendulum.vary();
        kapitzaPendulum.display(glAutoDrawable);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        kapitzaPendulum.increaseCrankR(-e.getWheelRotation());
    }
}
