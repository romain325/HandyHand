package Visual.ProcessingVisual.CameraView;

import Utils.CallBack.ShaderCallback;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Image;
import com.leapmotion.leap.ImageList;
import com.leapmotion.leap.Vector;
import processing.core.PImage;
import processing.core.PShape;
import processing.opengl.PShader;

import java.awt.*;

import static java.lang.Math.floor;
import static processing.core.PConstants.ARGB;
import static processing.core.PConstants.RGB;
import static processing.opengl.PShapeOpenGL.NORMAL;

/**
 * Camera view normalized with shaders
 */
public class CameraViewNormalized extends ShaderCallback {

    /**
     * Get image with shaders correction
     * @param frame frmae object
     */
    public void getImageShader(Frame frame) {

        //FIRST YOU CREATE A TEXTURE OF THE CURRENT IMAGE
        ImageList images = frame.images();
        Image image = images.get(0);

        PImage leapCamera = getSketch().createImage(image.width(), image.height(), RGB);
        leapCamera.loadPixels();

        byte[] imageData = new byte[image.width() * image.height()];
        image.data(imageData);
        int r,g,b;
        for(int i = 0; i < image.width() * image.height(); i++){
            r = (imageData[i] & 0xFF) << 16; //convert to unsigned and shift into place
            g = (imageData[i] & 0xFF) << 8;
            b = imageData[i] & 0xFF;
            leapCamera.pixels[i] =  r | g | b;
        }
        leapCamera.updatePixels();

        //THEN YOU CREATE THE EQUIVALENT FROM THE DISTORSION
        float[] distortionData = image.distortion();
        PImage distortionHorizontal = getSketch().createImage(image.distortionWidth()/2, image.distortionHeight(), ARGB);
        PImage distortionVertical = getSketch().createImage(image.distortionWidth()/2, image.distortionHeight(), ARGB);
        distortionHorizontal.loadPixels();
        distortionVertical.loadPixels();
        for(int d = 0; d < image.distortionWidth() * image.distortionHeight(); d += 2){
            float dX = distortionData[d];
            float dY = distortionData[d + 1];
            distortionHorizontal.pixels[d/2] = encodeFloatRGBA(dX).getRGB();
            distortionVertical.pixels[d/2] = encodeFloatRGBA(dY).getRGB();
        }
        distortionHorizontal.updatePixels();
        distortionVertical.updatePixels();

        //COMBINE BOTH WITH SHADERS TO CREATE A UNIFIED RENDER
        //PShader dewarp = getSketch().loadShader(getClass().getResource("/dewarp.glpl").getPath(), getClass().getResource("/passthrough.glpl").getPath());
        PShader dewarp = getShader("dewarp");
        PShape quad = getSketch().createShape();
        quad.beginShape();
        quad.noStroke();
        quad.vertex(0, 0, 1, 0, 0);
        quad.vertex(0, 400, 1, 0, 1);
        quad.vertex(400, 400, 1, 1, 1);
        quad.vertex(400, 0, 1, 1, 0);
        quad.vertex(0, 0, 1, 0, 0);
        quad.fill(0,0,128);
        quad.texture(leapCamera);
        quad.endShape();

        getSketch().shader(dewarp);
        dewarp.set("vDistortion", distortionVertical);
        dewarp.set("hDistortion", distortionHorizontal);

        getSketch().textureMode(NORMAL);
        getSketch().shape(quad);

    }

    /**
     * Get the corrected image without the shaders
     * @param frame frame object
     */
    private void getImageGreedy(Frame frame){
        int targetWidth = 200;
        int targetHeight = 200;

        if(frame.isValid()){
            ImageList images = frame.images();
            for(Image image : images)
            {
                PImage camera = getSketch().createImage(targetWidth, targetHeight, RGB);
                camera.loadPixels();

                int[] brightness = {0,0,0}; //An array to hold the rgba color components

                //For each pixel in the target image...
                for(float y = 0; y < targetWidth; y++){
                    for(float x = 0; x < targetHeight; x++){
                        //Normalized slope for this pixel
                        Vector input = new Vector(x/targetWidth, y/targetHeight,0);
                        //Convert from normalized [0..1] to slope [-4..4]
                        input.setX((input.getX() - image.rayOffsetX()) / image.rayScaleX());
                        input.setY((input.getY() - image.rayOffsetY()) / image.rayScaleY());

                        //Look up the pixel coordinates in the raw image corresponding to the slope values
                        Vector pixel = image.warp(input);

                        //Check that the coordinates are valid (i.e. within the camera image)
                        if(pixel.getX() >= 0 && pixel.getX() < image.width() && pixel.getY() >= 0 && pixel.getY() < image.height()) {
                            int data_index = (int) (floor(pixel.getY()) * image.width() + floor(pixel.getX())); //xy to buffer index
                            brightness[0] = image.data()[data_index] & 0xff; //Look up brightness value
                            brightness[2] = brightness[1] = brightness[0]; //Set the color components equal (greyscale)
                        } else {
                            brightness[0] = 255; //Display invalid pixels as red
                            brightness[2] = brightness[1] = 0;
                        }

                        //Set the pixel of the display object, in this case PImage defined in Processing
                        camera.pixels[(int) floor(y * targetWidth + x)] =  getSketch().color(brightness[0], brightness[1], brightness[2]);
                    }
                }

                //Show the image
                camera.updatePixels();
                getSketch().image(camera, 640 * image.id(), 0);
            }
        }

    }

    /**
     * Transform float to RGBA Color
     * @param input color as float
     * @return RGBA Color
     */
    private Color encodeFloatRGBA(float input){
        float r,g,b,a,scaled;
        scaled = (float) ((input + 0.6)/2.3);
        r = scaled;
        g = scaled * 255;
        b = scaled * (255^2);
        a = scaled * (255^3);

        r = r - (float) floor(r);
        g = g - (float) floor(g);
        b = b - (float) floor(b);
        a = a - (float) floor(a);

        return new Color(r,g,b,a);
    }

    @Override
    public void render(Frame frame) {
        getSketch().background(100);

        getImageGreedy(frame);
    }
}
