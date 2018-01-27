package util;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.GL_FALSE;

public class ShaderUtils {
    private ShaderUtils(){}

    public static int load(String verPath,String fragPath){
        String vert = FileUtils.loadAsString(verPath);
        String frag = FileUtils.loadAsString(fragPath);
        return create(vert,frag);
    }

    public static int create(String vertex,String fragment){
        int program =   glCreateProgram();
        int vertID = glCreateShader(GL_VERTEX_SHADER);
        int fragID = glCreateShader(GL_FRAGMENT_SHADER);

        glShaderSource(vertID,vertex);
        glShaderSource(fragID,fragment);

        glCompileShader(vertID);
        if (glGetShaderi(vertID,GL_COMPILE_STATUS)==GL_FALSE){
            System.err.println("Failed to compile Vertex Shader");
            System.err.println( glGetShaderInfoLog(vertID,2048));
        }
        glCompileShader(fragID);

        if (glGetShaderi(fragID,GL_COMPILE_STATUS)==GL_FALSE){
            System.err.println("Failed to compile Fragment Shader");
            System.err.println( glGetShaderInfoLog(fragID,2048));
        }

        glAttachShader(program,vertID);
        glAttachShader(program,fragID);

        glLinkProgram(program);
        glValidateProgram(program);

        return program;

    }

}
