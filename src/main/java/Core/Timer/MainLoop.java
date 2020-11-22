package Core.Timer;

import java.util.Timer;

public abstract class MainLoop extends Thread {
    private int CALL_PER_S = 30;
    private final int ELAPSED_NANO = 33333333;
    private boolean isPaused;

    private long lastFrameTime;

    public void run(){
        long begin, end;
        while(!isPaused){
            begin = System.nanoTime();
            tick();
            end = System.nanoTime();
            try {
                Thread.sleep(0, (int) (ELAPSED_NANO - (begin-end)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract void tick();
}
