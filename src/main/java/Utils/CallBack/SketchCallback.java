package Utils.CallBack;

import com.leapmotion.leap.Frame;
import processing.core.PApplet;

/**
 * Class defining a callback for the display of the camera
 */
public abstract class SketchCallback {
    private PApplet sketch;

    /**
     * Method to set up the sketch
     * @param sketch sketch to set up
     */
    public final void setUpSketch(PApplet sketch){
        this.sketch = sketch;
    }

    /**
     * Get the current Sketch
     * @return the current sketch
     */
    public final PApplet getSketch(){
        return sketch;
    }

    /**
     * Use the getSketch function to render on the sketch
     * @param frame current Frame
     */
    public abstract void render(Frame frame);

}
