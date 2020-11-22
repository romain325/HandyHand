package Utils.CallBack;

import com.leapmotion.leap.Frame;
import processing.core.PApplet;

public abstract class FrameCallback {
    private PApplet sketch;
    public final void setUpSketch(PApplet sketch){
        this.sketch = sketch;
    }
    public final PApplet getSketch(){
        return sketch;
    }

    /**
     * Use the getSketch function to render on the sketch
     * @param frame current Frame
     */
    public abstract void render(Frame frame);

}
