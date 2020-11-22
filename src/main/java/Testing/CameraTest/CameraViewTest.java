package Testing.CameraTest;

import Visual.ProcessingVisual.CameraView;

import java.io.IOException;

public class CameraViewTest {


    public void start() throws InterruptedException {
        String[] args = new String[] {""};
        CameraView cam = new CameraView();

        cam.affichage(args);

    }
}
