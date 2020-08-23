package org.hecate.engine;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Mesh {
    private int vbo;
    private int size;

    public Mesh()
    {
        vbo = glGenBuffers();
        size = 0;
    }

    public void addVertices(Vertex[] vertices)
    {
        size = vertices.length;
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBuffer(vertices), GL_STATIC_DRAW);
    }

    public void draw()
    {
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(0,3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
        glDrawArrays(GL_TRIANGLES,0,  size);
        glDisableVertexAttribArray(0);
    }


}
