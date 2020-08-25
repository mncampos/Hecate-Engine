package org.hecate.engine;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

    public static Vector2f GetCursorPos(long window, double  posX, double posY)
    {


        Vector2f mousePos = new Vector2f((float)posX, (float)posY);
        System.out.println(posX + " " + posY);
        return mousePos;

    }


}
