package Visual.ProcessingVisual.CameraView;

import Utils.CallBack.SketchCallback;
import com.leapmotion.leap.*;
import processing.core.PImage;

public class CameraView extends SketchCallback {
    @Override
    public void render(Frame frame) {
        getSketch().background(100);

        getImage(frame);

        getSketch().text(frame.hands().count(), 50,300);
        getSketch().text(frame.fingers().count(), 50, 350);
    }

    public void getImage(Frame frame){
        int cameraOffset = 20;
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
            for (Hand hand : frame.hands()){
                getSketch().ellipse(-hand.palmPosition().getX() + 350,hand.palmPosition().getZ() + 100,10,10);
                // (float) (-0.5 * hand.palmPosition().getY()), (float) (-0.5* hand.palmPosition().getY())
            }
        }
    }
}
