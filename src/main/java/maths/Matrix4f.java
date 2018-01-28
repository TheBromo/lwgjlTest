package maths;

import util.BufferUtils;

import java.nio.FloatBuffer;

import static java.lang.Math.*;
public class Matrix4f {

    public static final int SIZE = 4 * 4;
    public float[] elements = new float[SIZE];

    public Matrix4f() {
    }

    public static Matrix4f indentity() {
        Matrix4f result = new Matrix4f();

        for (int x = 0; x < SIZE; x++) {
            result.elements[x]=0;
        }
        result.elements[0 + 0 * 4] = 1.0f;
        result.elements[1 + 1 * 4] = 1.0f;
        result.elements[2 + 2 * 4] = 1.0f;
        result.elements[3 + 3 * 4] = 1.0f;

        return result;
    }

    public static Matrix4f translate(Vector3f vector) {
        Matrix4f returnMatrix = indentity();
        returnMatrix.elements[0 + 3 * 4] = vector.x;
        returnMatrix.elements[1 + 3 * 4] = vector.y;
        returnMatrix.elements[2 + 3 * 4] = vector.z;

        return returnMatrix;
    }

    public static Matrix4f rotate(float angle) {
        Matrix4f returnMatrix = indentity();

        float r = (float) toRadians(angle);
        float cos = (float) cos(r);
        float sin = (float) sin(r);

        returnMatrix.elements[0 + 0 * 4] = cos;
        returnMatrix.elements[1 + 0 * 4] = sin;

        returnMatrix.elements[0 + 1 * 4] = -sin;
        returnMatrix.elements[1 + 1 * 4] = cos;

        return returnMatrix;
    }

    public Matrix4f multiply(Matrix4f matrix4f) {
        Matrix4f result = new Matrix4f();

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                float sum = 0.0f;
                for (int e = 0; e < 4; e++) {
                    sum += this.elements[e + y * 4] * matrix4f.elements[x + e * 4];
                }
                result.elements[x + y * 4] = sum;

            }
        }
        return result;
    }

    public static Matrix4f orthographic(float left,float right,float bottom,float top,float near,float far){
        Matrix4f result = indentity();

        result.elements[0+0*4]=2.0f/(right-left);

        result.elements[1+1*4]=2.0f/(top-bottom);

        result.elements[2+2*4]=2.0f/(near-far);

        result.elements[0+3*4]=(left+right)/(left-right);
        result.elements[1+3*4]=(bottom +top)/(bottom-top);
        result.elements[2+3*4]=(near+far)/(near-far);

        return result;
    }

    public FloatBuffer toFloatBuffer(){
        return BufferUtils.createFloatBuffer(elements);
    }
}
