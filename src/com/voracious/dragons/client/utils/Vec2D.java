package com.voracious.dragons.client.utils;

public class Vec2D {
    public double x, y;

    public Vec2D() {
        x = y = 0;
    }

    public Vec2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec2D(Vec2D v) {
        this.x = v.x;
        this.y = v.y;
    }

    public void add(Vec2D b) {
        this.x += b.x;
        this.y += b.y;
    }

    public void sub(Vec2D b) {
        this.x -= b.x;
        this.y -= b.y;
    }

    public void mult(double c) {
        this.x *= c;
        this.y *= c;
    }

    public void div(double c) {
        this.x /= c;
        this.y /= c;
    }

    public double dot(Vec2D b) {
        return this.x * b.x + this.y * b.y;
    }
}
