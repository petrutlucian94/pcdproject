package gifgen.actors;

import gifgen.Config;
import gifgen.exception.ArchiverException;
import gifgen.utils.ArchiveExtractor;
import gifgen.utils.CamelMessageUtils;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.camel.CamelMessage;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Unarchiver extends UntypedActor {
    private ActorRef gifGenerator;
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public Unarchiver(ActorRef gifGenerator) {
        this.gifGenerator = gifGenerator;
    }

    @Override
    public void onReceive(Object msg) throws ArchiverException {
        CamelMessage message = (CamelMessage) msg;
        String uploadPath = message.body().toString();
        String imageDir = getImageDir(uploadPath);
        unarchive(uploadPath, imageDir);
        message = CamelMessageUtils.setBody(message, imageDir);
        gifGenerator.forward(message, getContext());
    }

    private String getImageDir(String uploadPath){
        String[] split = uploadPath.split("/");
        String fname = split[split.length - 1];
        return Config.imageDir + fname;
    }

    private void unarchive(String src, String dest) throws ArchiverException{
        log.debug("Unarchiving " + src);
        ArchiveExtractor extractor = new ArchiveExtractor(src);
        extractor.extract(dest);
    }
}
