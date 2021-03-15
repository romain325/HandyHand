package Utils.CallBack;

import processing.opengl.PShader;

import java.util.HashMap;

/**
 * Class defining the callback for the display of the camera
 */
public abstract class ShaderCallback extends SketchCallback{
    private HashMap<String,PShader> shaders;

    /**
     * Method for adding a callback
     * @param key name of the shader
     * @param value shader
     */
    public void addShader(String key, PShader value){
        shaders.put(key, value);
    }

    /**
     * Method get a callback from his name
     * @param key name of the shader
     * @return the shader corresponding to the name
     */
    public PShader getShader(String key){
        return shaders.get(key);
    }

}
