import org.lwjgl.Version;

public class HelloWorld {

    private long window;

    public void run(){
        System.out.println("Version: "+ Version.getVersion());

        init();
        loop();
    }

    private void loop() {

    }

    private void init() {
    }
}
