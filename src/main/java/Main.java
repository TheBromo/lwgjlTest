import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;//NULL

import java.nio.ByteBuffer;

import input.Input;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;


public class Main implements Runnable{

    private Thread thread;
    public boolean running;

    private GLFWKeyCallback keyCallback;

    public long window;

    public static void main(String[] args) {
        Main game = new Main();
        game.start();
    }

    public void start(){
        running=true;
        thread = new Thread(this,"EndlessRunner");
        thread.start();
    }

    public void init(){

        if (!glfwInit()){
            System.err.println("Init failed");
        }

        glfwWindowHint(GLFW_VISIBLE,GL_FALSE);

        window = glfwCreateWindow(800,600,"EndlessRunner",NULL,NULL);

        if (window==0){
            System.err.println("Could not create Window");
        }

        glfwSetKeyCallback(window,keyCallback= new Input());

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window,(vidMode.width()-800/2),(vidMode.height()-600/2));

        glfwMakeContextCurrent(window);

        glfwShowWindow(window);

        GL.createCapabilities();

        glClearColor(0.56f,0.258f,0.425f,1.0f);



        glEnable(GL_DEPTH_TEST);

        System.out.println("GL: "+glGetString(GL_VERSION));


    }

    public void update(){
        glfwPollEvents();

        if (Input.keys[GLFW_KEY_SPACE]){
            System.out.println("KeyPressed");
        }
    }

    public void render(){
        glfwSwapBuffers(window);
        glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);

    }


    @Override
    public void run(){
        init();
        while (running){
            update();
            render();

            if (glfwWindowShouldClose(window)){
                running=false;
            }
        }
        glfwTerminate();
        keyCallback.free();
    }

}