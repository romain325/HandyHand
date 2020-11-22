package Visual.ProcessingVisual;

import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Image;
import com.leapmotion.leap.ImageList;
import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;

public class CameraView extends PApplet {

    @Override
    public void settings() {
        size(500,500);
    }

    public static void main(String args[]) {
        String[] processingArgs = {"cameraView"};
        CameraView cameraView = new CameraView();
        PApplet.runSketch(processingArgs, cameraView);
    }

    @Override
    public void draw() {
        background(64);
    }

    public void newImage(Frame frame){

        ImageList images = frame.images();
        for (Image img : images){
            PImage camera = createImage(img.width(), img.height(), RGB);
            camera.loadPixels();

            byte[] pxs = img.data();

            for (int i = 0; i < img.width() * img.height(); i++){
                int r = (pxs[i] & 0xFF) << 16; // to unsigned and bin shift
                int g = (pxs[i] & 0xFF) << 8; // to unsigned and bin shift
                int b = (pxs[i] & 0xFF); // to unsigned
                camera.pixels[i] = r | g | b; // Bitwise operation --> https://stackoverflow.com/questions/3312611/pipe-operator-in-java
                System.out.println(camera.pixels[i]);
            }

            camera.updatePixels();
            image(camera, 640 * img.id(), 0);
        }
    }
}
