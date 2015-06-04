package gifgen.actors;

import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import gifgen.Config;
import gifgen.exception.ImageOperationException;
import gifgen.utils.ImageUtils;
import akka.actor.UntypedActor;
import akka.camel.CamelMessage;

public class GifGenerator extends UntypedActor {
    @Override
    public void onReceive(Object msg) throws ImageOperationException, IOException {
        CamelMessage message = (CamelMessage) msg;
        String imageDir = message.body().toString();
        String gifPath = getGifPath(imageDir);
        List<String> imagePaths = ImageUtils.get_images(imageDir);

        resize_images(imagePaths);
        ImageUtils.generateGif(imagePaths, gifPath);

        getSender().tell(getGif(gifPath), getSelf());
    }

    private void resize_images(List<String> imagePaths) throws ImageOperationException{
        for (String imagePath: imagePaths){
            ImageUtils.resizeImage(imagePath,
                                   Config.gif_x_res,
                                   Config.gif_y_res);
        }
    }

    private String getGifPath(String imageDir){
        String[] split = imageDir.split("/");
        String fname = split[split.length - 1];
        return Config.gifDir + fname + ".gif";
    }

    private byte[] getGif(String gifPath) throws IOException{
        Path path = Paths.get(gifPath);
        return Files.readAllBytes(path);
    }
}
