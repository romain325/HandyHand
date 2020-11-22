package Core;

import com.leapmotion.leap.Controller;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        LmListener listener = new LmListener();
        Controller controller = new Controller();

        controller.addListener(listener);
        System.out.println("Press Enter to quit!");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        controller.removeListener(listener);

        Daemon D1= new Daemon("Démon 1");
        D1.start();
        D1.run();


    }
}
