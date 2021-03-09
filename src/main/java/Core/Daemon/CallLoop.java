package Core.Daemon;

/**
 * A class to run other class that implements Runnable
 */
public class CallLoop implements Runnable{
    /**
     * The id of the last frame of the Leap Motion
     */
    private long lastFrameId;
    /**
     * If we want the class to continue to run
     */
    private boolean isListening = true;
    /**
     * If we want to kill the runnable
     */
    private boolean killer = false;
    /**
     * The instance of Runnable that is run
     */
    private final Runnable runnable;

    /**
     * The constructor of the class CallLoop
     * @param runnable The Runnable that we run in this class
     */
    public CallLoop(Runnable runnable) {
        this.runnable = runnable;
    }

    /**
     * The method that override the interface Runnable
     * Run an infinite loop that calls the method run of the Runnable instance in the class
     */
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


             // or with lastFrameId ?
            */

        }
    }

    /**
     * A method to end the infinite loop of the method run
     */
    public void end(){
        killer = true;
    }
}
