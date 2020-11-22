package Core.Daemon;


import com.leapmotion.leap.*;

import java.io.IOException;
import java.util.List;

public class Daemon extends Thread{
    private final String threadName;

    public Daemon(String name, Runnable runner) {
        super(runner);
        threadName = name;
        System.out.println("Creating " +  threadName );
    }

/*
    // Create runnable action for daemon
    public void run() {
        IsRunning = true;
        System.out.println("Press enter to quit.");
        while (IsRunning) {
            Frame frame= controller.frame();
            Hand hand =frame.hands().frontmost();
            System.out.println(hand.isLeft() ? "Left hand" : "Right hand");
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
                System.out.println(e.getMessage());
            }
        }
    }*/


    @Override
    public void start(){
        System.out.println("Start " +  threadName);
        // Sets the Thread as "daemon"
        setDaemon(true);
        super.start();
    }

    public String getThreadName(){
        return threadName;
    }
}
