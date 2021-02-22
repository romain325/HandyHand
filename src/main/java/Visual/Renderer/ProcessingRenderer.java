package Visual.Renderer;

import Utils.CallBack.ShaderCallback;
import Utils.CallBack.SketchCallback;
import com.leapmotion.leap.*;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.PShader;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class ProcessingRenderer extends PApplet {
    private final Controller controller;
    private int width = 500;
    private int height = 500;
    private SketchCallback callback;
    private  HashMap<String, String[]> glplFiles;

    public ProcessingRenderer(Controller controller, SketchCallback callback){
        this.controller = controller;
        this.callback = callback;
        this.callback.setUpSketch(this);
    }
    public ProcessingRenderer(Controller controller, SketchCallback callback, List<Controller.PolicyFlag> policyFlags){
        this(controller,callback);
        setPolicyFlags(policyFlags);
    }
    public ProcessingRenderer(Controller controller, SketchCallback callback, List<Controller.PolicyFlag> policyFlags, HashMap<String, String[]> glplFiles){
        this(controller,callback);
        setPolicyFlags(policyFlags);
        this.glplFiles = glplFiles;
    }
    public ProcessingRenderer(Controller controller, SketchCallback callback, int Width, int Height){
        this(controller,callback);
        this.width = Width;
        this.height = Height;
    }
    public ProcessingRenderer(Controller controller, SketchCallback callback, int Width, int Height, List<Controller.PolicyFlag> policyFlags){
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
        // Add P2D as renderer when using shaders BUT not working rn
        size(width,height);
    }

    public void show() {
        PApplet.runSketch(new String[]{"cameraView"}, this);
    }

    @Override
    public void draw() {
        callback.render(controller.frame());
    }

    @Override
    public void setup() {
        super.setup();
        if (glplFiles != null){
            for (String key: glplFiles.keySet()) {
                var values = glplFiles.get(key);
                if(values.length == 1){
                    ((ShaderCallback)callback).addShader(key, this.loadShader(values[0]));
                }else{
                    ((ShaderCallback)callback).addShader(key, this.loadShader(values[0],values[1]));
                }
            }
        }
    }
}
