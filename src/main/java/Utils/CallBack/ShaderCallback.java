package Utils.CallBack;

import processing.opengl.PShader;

import java.util.HashMap;

public abstract class ShaderCallback extends SketchCallback{
    private HashMap<String,PShader> shaders;
    public void addShader(String key, PShader value){
        shaders.put(key, value);
    }
    public PShader getShader(String key){
        return shaders.get(key);
    }

}
