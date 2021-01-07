package Visual.ProcessingVisual.CameraView;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;

public class CameraImage {

    public byte[] getByteArrayImage(Frame frame){
        if(frame == null || !frame.isValid()) return null;
        return frame.images().get(0).data();
    }

    public byte[] getByteArrayImage(){
        return getByteArrayImage(new Controller().frame());
    }

}
