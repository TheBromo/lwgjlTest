package graphics;


import util.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import static org.lwjgl.opengl.GL11.*;

public class Texture {
    private int width, height;
    private int id;

    public Texture(String path) {
        id =load(path);
    }

    private int load(String path) {
        int[] pixels = null;
        try {
            BufferedImage image = ImageIO.read(new FileInputStream(path));
            width = image.getWidth();
            height = image.getHeight();
            pixels = new int[width * height];

            image.getRGB(0, 0, width, height, pixels, 0, width);

        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] data = new int[width * height];
        for (int x = 0; x < width * height; x++) {
            int a = (pixels[x] & 0xff000000) >> 24;
            int r = (pixels[x] & 0xff0000) >> 16;
            int g = (pixels[x] & 0xff00) >> 8;
            int b = (pixels[x] & 0xff);

            data[x] = a << 24 | b << 16 | g << 8 | r;
        }
        int tex = glGenTextures();
        glBindTexture(GL_TEXTURE_2D,tex);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA,width,height,0,GL_RGBA,GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data));
        glBindTexture(GL_TEXTURE_2D,0);
        return tex;
    }

    public void bind(){
        glBindTexture(GL_TEXTURE_2D,id);
    }

    public void unbind(){
        glBindTexture(GL_TEXTURE_2D,0);
    }

    public int getId() {
        return id;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
