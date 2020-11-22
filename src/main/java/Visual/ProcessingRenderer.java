package Visual;

import Utils.CallBack.FrameCallback;
import com.leapmotion.leap.*;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Image;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.List;

public class ProcessingRenderer extends PApplet {
    private final Controller controller;
    private int width = 500;
    private int height = 500;
    private FrameCallback callback;

    public ProcessingRenderer(Controller controller, FrameCallback callback){
        this.controller = controller;
        this.callback = callback;
        this.callback.setUpSketch(this);
    }
    public ProcessingRenderer(Controller controller, FrameCallback callback, List<Controller.PolicyFlag> policyFlags){
        this(controller,callback);
        setPolicyFlags(policyFlags);
    }
    public ProcessingRenderer(Controller controller, FrameCallback callback, int Width, int Height){
        this(controller,callback);
        this.width = Width;
        this.height = Height;
    }
    public ProcessingRenderer(Controller controller, FrameCallback callback, int Width, int Height, List<Controller.PolicyFlag> policyFlags){
        this(controller,callback,Width,Height);
        setPolicyFlags(policyFlags);
    }

    private void setPolicyFlags(List<Controller.PolicyFlag> policyFlags){
        if(policyFlags != null){
            for (Controller.PolicyFlag val : policyFlags){
                controller.setPolicy(val);
            }
        }
    }

    @Override
    public void settings() {
        size(width,height);
    }

    public void show() {
        PApplet.runSketch(new String[]{"cameraView"}, this);
    }

    @Override
    public void draw() {
        callback.render(controller.frame());
    }

}
