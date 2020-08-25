package org.hecate.engine;

import static org.lwjgl.opengl.GL11.*;

public class RenderUtils {

    public static void setTextures(boolean enabled)
    {
        if(enabled)
            glEnable(GL_TEXTURE_2D);
        else glDisable(GL_TEXTURE_2D);
    }

    public static void unbindTextures()
    {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public static void setClearColor(Vector3f color)
    {
        glClearColor(color.getX(), color.getY(), color.getZ(), 1.0f);
    }

}
