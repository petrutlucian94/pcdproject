package gifgen.actors;

import gifgen.Config;
import gifgen.utils.CamelMessageUtils;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.camel.StreamCache;

import akka.actor.ActorRef;
import akka.camel.CamelMessage;
import akka.camel.javaapi.UntypedConsumerActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.UUID;


public class HttpConsumer extends UntypedConsumerActor {
    private ActorRef unarchiver;
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public HttpConsumer(ActorRef unarchiver) {
        this.unarchiver = unarchiver;
    }

    @Override
    public String getEndpointUri() {
        String urlTemplate = "jetty:http://%s:%d";
        return String.format(urlTemplate,
                             Config.listen_addr, Config.listen_port);
    }

    @Override
    public void onReceive(Object msg) throws IOException {
        CamelMessage message = (CamelMessage) msg;
        String uploadPath = getUploadPath();
        saveBody(message, uploadPath);
        message = CamelMessageUtils.setBody(message, uploadPath);
        unarchiver.forward(message, getContext());
    }

    private String getUploadPath(){
        String filename = UUID.randomUUID().toString();
        return Config.uploadDir + filename;
    }

    private void saveBody(CamelMessage message, String path) throws IOException{
        StreamCache bodyStream = message.getBodyAs(StreamCache.class,
                                                   getCamelContext());
        FileOutputStream fstream = new FileOutputStream(path);
        bodyStream.writeTo(fstream);
        fstream.close();
    }
}
