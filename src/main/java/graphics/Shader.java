package graphics;

import maths.Vector3f;
import util.ShaderUtils;
import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private int ID;

    public static Shader BASIC;

    private Shader(String vertex,String fragment){
        ID= ShaderUtils.load(vertex,fragment);
    }

    public static void loadAll(){
        BASIC = new Shader("shaders/shader.vert", "shaders/shader.frag");
    }

    public int getUniform(String name){
        return glGetUniformLocation(ID,name);
    }
    public void setUniform3f(String name, Vector3f vector){
        glUniform3f(getUniform(name),vector.x,vector.y,vector.z);
    }

    public void enable(){
        glUseProgram(ID);
    }

    public void disable(){
        glUseProgram(0);
    }

}