package Visual.ProcessingVisual.CameraView;

import Utils.Converter.ByteConverter;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Image;
import processing.core.PApplet;
import processing.core.PImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class CameraImage {
    private Controller controller = new Controller();

    public CameraImage(){
        controller.setPolicy(Controller.PolicyFlag.POLICY_IMAGES);
    }

    public byte[] getByteArrayImage(Frame frame) throws IOException {
        if(frame == null || !frame.isValid()) {
            return null;
        }
        Image img = frame.images().get(0);
        PImage camera = new PImage(img.width(), img.height(), PApplet.RGB);
        camera.loadPixels();
        byte[] pxs = img.data();
        for (int i = 0; i < img.width() * img.height(); i++){
            int r = (pxs[i] & 0xFF) << 16; // to unsigned and bin shift
            int g = (pxs[i] & 0xFF) << 8; // to unsigned and bin shift
            int b = (pxs[i] & 0xFF); // to unsigned
            camera.pixels[i] = r | g | b; // Bitwise operation --> https://stackoverflow.com/questions/3312611/pipe-operator-in-java
        }
        camera.updatePixels();

        var ba = new ByteArrayOutputStream();
        var bImage = new BufferedImage(camera.getImage().getWidth(null), camera.getImage().getHeight(null), BufferedImage.TYPE_INT_RGB);
        bImage.createGraphics().drawImage(camera.getImage(), null, null);

        ImageIO.write(bImage, "jpeg", ba);
        return ba.toByteArray();
    }

    public byte[] getByteArrayImage() throws IOException {
        return getByteArrayImage(this.controller.frame());
    }

}
