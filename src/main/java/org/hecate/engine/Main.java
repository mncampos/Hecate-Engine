package org.hecate.engine;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_SRGB;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main {

    //The screen size
    private final int Height = 768;
    private final int Width = 1280;
    public static final double FRAME_CAP = 5000.0;
    private Transform transform;
    private Camera camera;
    private Shader shader;
    private Texture texture;


    //The window handle
    private long window;

    public void run() {

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable


        // Create the window
        window = glfwCreateWindow(Width, Height, "Hecate3D", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Change the cursor mode
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);



       // Set a callback for when the cursor moves




        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
             });

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());



            // Center the window
            assert vidmode != null;
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically


        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);


        // Make the window visible
        glfwShowWindow(window);

    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(0.0f,0.2f,0.3f,0.0f);
        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        //glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_FRAMEBUFFER_SRGB);
        Mesh mesh = new Mesh(); //ResourceLoader.loadMesh("untitled.obj"); //
        texture = ResourceLoader.loadTexture("./res/textures/test.png");
        shader = new Shader();
        camera = new Camera();

        Vertex[] vertices = new Vertex[]{
                new Vertex(new Vector3f(-1,-1,0), new Vector2f(0,0)),
                new Vertex(new Vector3f(0,1,0), new Vector2f(0.5f,0)),
                new Vertex(new Vector3f(1,-1,0), new Vector2f(1.0f,0)),
                new Vertex(new Vector3f(0,-1,1), new Vector2f(0.0f,0.5f))
        };
        int[] indices = new int[] {
                3,1,0,
                2,1,3,
                0,1,2,
                0,2,3
        };

        mesh.addVertices(vertices, indices);




        shader.addVertexShader(ResourceLoader.loadShader("basicVertex.hvs"));
        shader.addFragmentShader(ResourceLoader.loadShader("basicFragment.hfrs"));
        shader.compileShader();
        shader.addUniform("transform");
        long lastTime = Time.getTime();
        double unprocessedTime = 0;
        final double frameTime = 1.0 / FRAME_CAP;
        int frames = 0;
        long frameCounter = 0;
        float temp = 0;
        Transform.setProjection(70f, Width, Height, 0.1f, 1000);
        Transform.setCamera(camera);
        transform = new Transform();



        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.

        while ( !glfwWindowShouldClose(window) ) {
            glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer

            Time.setDelta(frameTime);



            long startTime = Time.getTime();
            long passedTime = startTime - lastTime;
            lastTime = startTime;
            unprocessedTime += passedTime / (double)Time.SECOND;
            frameCounter += passedTime;

            unprocessedTime -= frameTime;
            transform.setTranslation((float)Math.sin(temp), 0, 5);
            //transform.setRotation(0,(float)Math.sin(temp) * 180, 0);
            //transform.setScale((float)Math.sin(temp),Math.abs((float)Math.sin(temp)),(float)Math.sin(temp) );






            camera.input(window);
            shader.bind();

            shader.setUniform("transform", transform.getProjectedTransformation());
            mesh.draw();

            if(frameCounter >= Time.SECOND)
            {
                System.out.println(frames);
                frames = 0;
                frameCounter = 0;
            }
            frames++;
            temp += 0.01;



            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();

        }


    }


    public static void main(String[] args) {
        new Main().run();
    }

}