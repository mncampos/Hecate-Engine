package org.hecate.engine;

public class Vector2f {
    private float x;
    private float y;

    public Vector2f(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public float lenght()
    {
        return (float)Math.sqrt(x * x+ y * y);
    }

    public float dot(Vector2f r)
    {
        return x * r.getX() + y * r.getY();
    }

    public Vector2f normalize()
    {
        float lenght = lenght();
        x /= lenght;
        y /= lenght;
        return this;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Vector2f rotate(float angle)
    {
        double rad = Math.toRadians(angle);
        double cos = Math.cos(rad);
        double sin = Math.cos(rad);

        return new Vector2f((float)(x * cos - y * sin), (float)(x * sin + y * cos));
    }

    public Vector2f add(Vector2f r)
    {
        return new Vector2f(x + r.getX(), y + r.getY());
    }

    public Vector2f add(float r)
    {
        return new Vector2f(x + r, y + r);
    }

    public Vector2f sub(Vector2f r)
    {
        return new Vector2f(x - r.getX(), y - r.getY());
    }

    public Vector2f sub(float r)
    {
        return new Vector2f(x - r, y - r);
    }

    public Vector2f mult(Vector2f r)
    {
        return new Vector2f(x * r.getX(), y * r.getY());
    }

    public Vector2f mult(float r)
    {
        return new Vector2f(x * r, y * r);
    }

    public Vector2f div(Vector2f r)
    {
        return new Vector2f(x / r.getX(), y / r.getY());
    }

    public Vector2f div(float r)
    {
        return new Vector2f(x / r, y / r);
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}

