package maths;

import static java.lang.Math.*;
public class Matrix4f {

    public static final int SIZE = 4 * 4;
    public float[] matrix = new float[SIZE];

    public Matrix4f() {
    }

    public static Matrix4f indentity() {
        Matrix4f result = new Matrix4f();

        for (int x = 0; x < SIZE; x++) {
            result.matrix[x]=0;
        }
        result.matrix[0 + 0 * 4] = 1.0f;
        result.matrix[1 + 1 * 4] = 1.0f;
        result.matrix[2 + 2 * 4] = 1.0f;
        result.matrix[3 + 3 * 4] = 1.0f;

        return result;
    }

    public static Matrix4f translate(Vector3f vector) {
        Matrix4f returnMatrix = indentity();
        returnMatrix.matrix[0 + 3 * 4] = vector.x;
        returnMatrix.matrix[1 + 3 * 4] = vector.y;
        returnMatrix.matrix[2 + 3 * 4] = vector.z;

        return returnMatrix;
    }

    public static Matrix4f rotate(float angle) {
        Matrix4f returnMatrix = indentity();

        float r = (float) toRadians(angle);
        float cos = (float) cos(r);
        float sin = (float) sin(r);

        returnMatrix.matrix[0 + 0 * 4] = cos;
        returnMatrix.matrix[1 + 0 * 4] = sin;

        returnMatrix.matrix[0 + 1 * 4] = -sin;
        returnMatrix.matrix[1 + 1 * 4] = cos;

        return returnMatrix;
    }

    public Matrix4f multiply(Matrix4f matrix4f) {
        Matrix4f result = new Matrix4f();

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                float sum = 0.0f;
                for (int e = 0; e < 4; e++) {
                    sum += this.matrix[e + y * 4] * matrix4f.matrix[x + e * 4];
                }
                result.matrix[x + y * 4] = sum;

            }
        }
        return result;
    }

    public static Matrix4f orthographic(float left,float right,float bottom,float top,float near,float far){
        Matrix4f result = indentity();

        result.matrix[0+0*4]=2.0f/(right-left);

        result.matrix[1+1*4]=2.0f/(top-bottom);

        result.matrix[2+2*4]=2.0f/(near-far);

        result.matrix[0+3*4]=(left+right)/(left-right);
        result.matrix[1+3*4]=(bottom +top)/(bottom-top);
        result.matrix[2+3*4]=(near+far)/(near-far);

        return result;



    }
}
