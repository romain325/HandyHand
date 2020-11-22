package Visual.ProcessingVisual;

import com.leapmotion.leap.*;
import processing.core.PApplet;
import processing.core.PImage;

public class CameraView extends PApplet {
    private Controller controller = new Controller();

    @Override
    public void settings() {
        size(500,500);
    }

    public void affichage(String[] args) {
        controller.setPolicy(Controller.PolicyFlag.POLICY_IMAGES);
        String[] processingArgs = {"cameraView"};
        PApplet.runSketch(processingArgs, this);
    }

    @Override
    public void draw() {
        Frame frame = controller.frame();
        background(100);
        newImage(frame);
        text(frame.hands().count(), 50,300);
        text(frame.fingers().count(), 50, 350);
    }

    public void newImage(Frame frame){
        if(frame == null || !frame.isValid())return;

        ImageList images = frame.images();
        System.out.println(images.count());
        for (Image img : images){
            PImage camera = createImage(img.width(), img.height(), RGB);
            camera.loadPixels();
            byte[] pxs = img.data();
            for (int i = 0; i < img.width() * img.height(); i++){
                int r = (pxs[i] & 0xFF) << 16; // to unsigned and bin shift
                int g = (pxs[i] & 0xFF) << 8; // to unsigned and bin shift
                int b = (pxs[i] & 0xFF); // to unsigned
                camera.pixels[i] = r | g | b; // Bitwise operation --> https://stackoverflow.com/questions/3312611/pipe-operator-in-java
            }
            camera.updatePixels();

            image(camera, 0,0);
        }
    }

}
