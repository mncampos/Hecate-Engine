package org.hecate.engine;


import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;

public class Camera {
    public static final Vector3f yAxis = new Vector3f(0,1,0);

    private Vector3f pos;
    private Vector3f forward;
    private Vector3f up;

    public static Vector3f getyAxis() {
        return yAxis;
    }

    public Vector3f getPos() {
        return pos;
    }

    public void input(long window)
    {
        float movAmt = (float)(100 * Time.getDelta());
        float rotAmt = (float)(10000 * Time.getDelta());

        if(glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
            System.out.println(Time.getDelta());
            move(getForward(), movAmt);
        }
        if(glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS)
            move(getForward(), -movAmt);
        if(glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS)
            move(getRight(), movAmt);
        if(glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS)
            move(getLeft(), movAmt);

        if(glfwGetKey(window, GLFW_KEY_UP) == GLFW_PRESS)
            rotateX(-rotAmt);
        if(glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_PRESS)
            rotateX(rotAmt);
        if(glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS)
            rotateY(-rotAmt);
        if(glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS)
            rotateY(rotAmt);

    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public Vector3f getForward() {
        return forward;
    }

    public void setForward(Vector3f forward) {
        this.forward = forward;
    }

    public Vector3f getUp() {
        return up;
    }

    public void setUp(Vector3f up) {
        this.up = up;
    }

    public void move(Vector3f dir, float amt)
    {
        pos = pos.add(dir.mult(amt));
    }

    public Camera()
    {
        this(new Vector3f(0,0,0), new Vector3f(0,0,1), new Vector3f(0,1,0));
    }



    public Vector3f getLeft()
    {
        Vector3f left = up.cross(forward);
        left.normalize();
        return left;
    }

    public Vector3f getRight()
    {
        Vector3f right = forward.cross(up);
        right.normalize();
        return right;
    }




    public void rotateY(float angle)
    {
        Vector3f Haxis = yAxis.cross(forward);
        Haxis.normalize();

        forward.rotate(angle, yAxis);
        forward.normalize();

        up = forward.cross(Haxis);
        up.normalize();

    }


    public void rotateX(float angle)
    {
        Vector3f Haxis = yAxis.cross(forward);
        Haxis.normalize();
        forward.rotate(angle, Haxis);
        forward.normalize();

        up = forward.cross(Haxis);
        up.normalize();
    }

    public Camera(Vector3f pos, Vector3f forward, Vector3f up)
    {
        this.pos = pos;
        this.forward = forward;
        this.up = up;
        up.normalize();
        forward.normalize();
    }
}
