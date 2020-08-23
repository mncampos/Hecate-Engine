package org.hecate.engine;

public class Vertex {
    private Vector3f pos;
    public static final int SIZE = 3;

    public Vector3f getPos() {
        return pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public Vertex(Vector3f pos)
    {
        this.pos = pos;
    }
}
