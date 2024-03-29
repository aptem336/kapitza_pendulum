package com.german;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

import javax.swing.*;

public class GLMain {

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame();
        mainFrame.setSize(GLSettings.getMainFrameSize());
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GLCanvas canvas = new GLCanvas();
        canvas.setSize(mainFrame.getSize());
        GLSettings.getGlEventListeners().forEach(canvas::addGLEventListener);
        GLSettings.getKeyListeners().forEach(canvas::addKeyListener);
        GLSettings.getMouseWheelListeners().forEach(canvas::addMouseWheelListener);
        GLSettings.getMouseMotionListeners().forEach(canvas::addMouseMotionListener);
        GLSettings.getMouseListeners().forEach(canvas::addMouseListener);
        mainFrame.add(canvas);

        Animator animator = new Animator(canvas);
        animator.start();
    }
}