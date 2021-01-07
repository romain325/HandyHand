package Api.ApiApp;

import Visual.ProcessingVisual.CameraView.CameraImage;
import com.leapmotion.leap.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping("/leap")
public class LeapInfosRESTController {
    private Controller controller = new Controller();

    @GetMapping("/state")
    public boolean getState(){
        return controller.isConnected();
    }

    @GetMapping("/view")
    public String getLeapView() {
        return Base64.getEncoder().encodeToString(new CameraImage().getByteArrayImage());
    }
}
