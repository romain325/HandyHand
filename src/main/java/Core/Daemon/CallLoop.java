package Core.Daemon;

public class CallLoop implements Runnable{
    private long lastFrameId;
    private boolean isListening = true;
    private boolean killer = false;
    private final Runnable runnable;

    public CallLoop(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void run() {
        while (true){
            if(killer){
                break;
            }
            if(!isListening){
                continue;
            }

            runnable.run();

            /**
            // Maybe slow down with sleep ?

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            */

        }
    }

    public void end(){
        killer = true;
    }
}
