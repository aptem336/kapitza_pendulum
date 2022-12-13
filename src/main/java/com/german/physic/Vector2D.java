package com.german.physic;

public class Vector2D {
    private double x;
    private double y;

    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static double dotProduct(Vector2D v1, Vector2D v2) {
        return v1.x * v2.x + v1.y * v2.y;
    }

    public static Vector2D sum(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.x + v2.x, v1.y + v2.y);
    }

    public static Vector2D product(Vector2D v, double value) {
        return new Vector2D(v.x * value, v.y * value);
    }

    public void add(Vector2D v) {
        this.x += v.x;
        this.y += v.y;
    }

    public void mul(double multiplier) {
        this.x *= multiplier;
        this.y *= multiplier;
    }

    public double squareLen() {
        return x * x + y * y;
    }

    public double len() {
        return Math.sqrt(squareLen());
    }

    public Vector2D getInvert() {
        return new Vector2D(-x, -y);
    }

    public Vector2D getNormalized() {
        double len = len();
        return new Vector2D(x / len, y / len);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }
}