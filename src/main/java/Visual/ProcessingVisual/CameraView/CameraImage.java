package Visual.ProcessingVisual.CameraView;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Image;
import processing.core.PApplet;
import processing.core.PImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Leap Camera Image
 */
public class CameraImage {
    private Controller controller = new Controller();

    public CameraImage(){
        controller.setPolicy(Controller.PolicyFlag.POLICY_IMAGES);
    }

    /**
     * Get byte array from frame
     * @param frame frame
     * @return corrected byte array of the image
     * @throws IOException Error while ccovnerting
     */
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

    /**
     * Get image of the byte array from the current frame
     * @return current frame
     * @throws IOException Error while ccovnerting
     */
    public byte[] getByteArrayImage() throws IOException {
        return getByteArrayImage(this.controller.frame());
    }

}
