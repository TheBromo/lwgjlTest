import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.*;//NULL

import graphics.Shader;
import input.Input;
import maths.Vector3f;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import util.ShaderUtils;


public class Main implements Runnable {

    private Thread thread;
    public boolean running;

    private GLFWKeyCallback keyCallback;

    public long window;
    private int vao ;

    private float x=0;
    private float colorRed=1;
    private float blueColor=0;

    public static void main(String[] args) {
        Main game = new Main();
        game.start();
    }

    public void start() {
        running = true;
        thread = new Thread(this, "EndlessRunner");
        thread.start();
    }

    public void init() {

        if (!glfwInit()) {
            System.err.println("Init failed");
        }

        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);

        window = glfwCreateWindow(800, 600, "EndlessRunner", NULL, NULL);

        if (window == 0) {
            System.err.println("Could not create Window");
        }

        glfwSetKeyCallback(window, keyCallback = new Input());

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidMode.width() - 800 / 2), (vidMode.height() - 600 / 2));

        glfwMakeContextCurrent(window);

        glfwShowWindow(window);

        GL.createCapabilities();

        glClearColor(0.56f, 0.258f, 0.425f, 1.0f);


        glEnable(GL_DEPTH_TEST);
        Shader.loadAll();

        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        Shader shader = Shader.BASIC;

        shader.enable();
        shader.setUniform3f("col",new Vector3f(0.8f,0.2f,0.3f));
        System.out.println("GL: " + glGetString(GL_VERSION));


    }

    public void update() {


        glfwPollEvents();

        if (Input.keys[GLFW_KEY_SPACE]) {
            System.out.println("KeyPressed");
            x+=0.001f;

        }else if (Input.keys[GLFW_KEY_B]){
            colorRed = 0.25f;
            blueColor = 1;
        }else if (Input.keys[GLFW_KEY_R]){
            blueColor = 0.25f;
            colorRed =1;
        }
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glDrawArrays(GL_TRIANGLES,0,3);

        glfwSwapBuffers(window);

    }


    @Override
    public void run() {
        init();
        while (running) {
            update();
            render();

            if (glfwWindowShouldClose(window)) {
                running = false;
            }
        }
        glfwTerminate();
        keyCallback.free();
    }

}
