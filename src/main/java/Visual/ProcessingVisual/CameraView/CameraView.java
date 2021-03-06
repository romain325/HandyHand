package Visual.ProcessingVisual.CameraView;

import Core.Position.HandPosition;
import Utils.CallBack.SketchCallback;
import com.leapmotion.leap.*;
import processing.core.PImage;

/**
 * View of the Leap Motion camera
 */
public class CameraView extends SketchCallback {
    HandPosition handPosition = new HandPosition();
    int cnt = 0;

    @Override
    public void render(Frame frame) {
        getSketch().background(100);

        getImage(frame);

        printPos(frame);
    }

    /**
     * Get current view of the Leap Motion
     * @param frame frame
     */
    public void getImage(Frame frame){
        if(frame == null || !frame.isValid())return;

        ImageList images = frame.images();
        for (Image img : images){
            PImage camera = getSketch().createImage(img.width(), img.height(), getSketch().RGB);
            camera.loadPixels();
            byte[] pxs = img.data();
            for (int i = 0; i < img.width() * img.height(); i++){
                int r = (pxs[i] & 0xFF) << 16; // to unsigned and bin shift
                int g = (pxs[i] & 0xFF) << 8; // to unsigned and bin shift
                int b = (pxs[i] & 0xFF); // to unsigned
                camera.pixels[i] = r | g | b; // Bitwise operation --> https://stackoverflow.com/questions/3312611/pipe-operator-in-java
            }
            camera.updatePixels();
            getSketch().image(camera, 640 * img.id(),0);
        }
    }

    /**
     * Add entities to identify hand & fingers
     * @param frame current frame
     */
    public void printPos(Frame frame){
        var values = handPosition.getHandsPosition(frame);
        StringBuilder pos = new StringBuilder();

        for (var val : values.keySet()) {
            if(val.equals("None")){
                continue;
            }
            for (var val2: values.get(val)) {
                pos.append(val2.getMessage()).append("\n\t");
            }
            getSketch().text(val + " Hand: " + pos.toString() , 50, 300 + 35 * cnt);
            cnt++;
            pos.setLength(0);
        }
        cnt = 0;
        getSketch().text("Nb Main: " + frame.hands().count(), 50,380);

        getSketch().line(250,0,250,250);
    }
}
