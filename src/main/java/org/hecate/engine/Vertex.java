package org.hecate.engine;

public class Vertex {
    private Vector3f pos;
    private Vector2f textCoord;
    public static final int SIZE = 5;

    public Vector2f getTextCoord() {
        return textCoord;
    }

    public void setTextCoord(Vector2f textCoord) {
        this.textCoord = textCoord;
    }


    public Vector3f getPos() {
        return pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public Vertex(Vector3f pos)
    {
        this(pos, new Vector2f(0,0));
    }

    public Vertex(Vector3f pos, Vector2f textCoord)
    {
        this.pos = pos;
        this.textCoord = textCoord;
    }
}
