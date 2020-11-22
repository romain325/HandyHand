package Core;


import com.leapmotion.leap.*;

import java.io.IOException;

public class Daemon extends Thread{
    private Thread thread;
    private String threadName;

    Daemon( String name) {
        threadName = name;
        System.out.println("Creating " +  threadName );
    }

    // Create runnable action for daemon
        public void run() {
            LmListener listener = new LmListener();
            Controller controller = new Controller();

            controller.addListener(listener);

            System.out.println("Press enter to quit.");
            while (true) {
                Frame frame= controller.frame();
                Hand hand =frame.hands().frontmost();
                String handName = hand.isLeft() ? "Left hand" : "Right hand";
                System.out.println(handName);
                Vector handCenter = hand.palmPosition();
                if(handCenter.getX()>0){
                    System.out.println("La main est à droite du leap motion");
                }else{
                    System.out.println("La main est à gauche du leap motion");
                }
                if(handCenter.getZ()>0){
                    System.out.println("La main est entre vous et le leap motion");
                }else{
                    System.out.println("La main est entre l'écran est le Leap motion");
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }
        }

    public void start(){
        System.out.println("Start " +  threadName );
        if (thread == null) {
            thread = new Thread (this, threadName);

            // Sets the Thread as "daemon"
            thread.setDaemon(true);
            thread.start();
        }
    }

}

