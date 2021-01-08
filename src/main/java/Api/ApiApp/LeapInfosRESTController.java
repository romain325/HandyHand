package Api.ApiApp;

import Visual.ProcessingVisual.CameraView.CameraImage;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Leap;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Base64;

@RestController
@RequestMapping("/leap")
public class LeapInfosRESTController {

    @GetMapping("/state")
    public boolean getState(){
        return new Controller().isConnected();
    }

    @GetMapping(value="/view")
    public void getLeapView(HttpServletResponse response) throws IOException, URISyntaxException {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        byte[] img = new CameraImage().getByteArrayImage();

        IOUtils.copy((img == null || img.length == 0) ? new ClassPathResource("img/defaultImage.jpg").getInputStream() : new ByteArrayInputStream(img), response.getOutputStream());
    }
}
