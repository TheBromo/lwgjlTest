
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class HelloWorld {

    private long window;

    public void run(){
        System.out.println("Version: "+ Version.getVersion());

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        //set's up an  error callback.
        GLFWErrorCallback.createPrint(System.err).set();

        //init glfw, most functions wont work without it.
        if (!glfwInit())throw new IllegalStateException("Unable to init GLFW");

        //Configure GLFW.
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE,GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE,GLFW_TRUE);

        //creates the widow.
        window = glfwCreateWindow(300,300,"Hello World",NULL,NULL);
        if (window == NULL)throw  new RuntimeException("Failed to create GLFW window");

        //setup a key callback, It Will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window,(window, key,scancode,action,mods)->{
           if (key==GLFW_KEY_ESCAPE&&action==GLFW_RELEASE){
               glfwSetWindowShouldClose(window,true); // Will detect this in the rendering loop
           }
        });

        try(MemoryStack stack = stackPush()){
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            //Get the window size passed zo glfwCreateWindow
            glfwGetWindowSize(window,pWidth,pHeight);

            //get resolution of the primary monitor
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            //centre window
            glfwSetWindowPos(window,(vidMode.width()-pWidth.get(0)/2),(vidMode.height()-pHeight.get(0)/2));
        }//the stack frame is popped up automatically

        //Make the OpenGL context current
        glfwMakeContextCurrent(window);
        //Enable VSync
        glfwSwapInterval(1);

        //make window visible
        glfwShowWindow(window);

    }

    private void loop() {
        /*
        This line is critical for LWJGL's interoperation with GLFW's
        OpenGL context, or any context that is managed externally.
        LWJGL detects the context that is current in the current thread,
        creates the GLCapabilities instance and makes the OpenGL
        bindings available for use.
        */

        GL.createCapabilities();

        glClearColor(1.0f,0.0f,0.0f,0.0f);

        while (!glfwWindowShouldClose(window)){
            glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);

            glfwSwapBuffers(window);//swap the color buffers
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }
}
