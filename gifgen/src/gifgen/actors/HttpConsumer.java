package gifgen.actors;

import gifgen.Config;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.camel.converter.stream.FileInputStreamCache;

import akka.actor.ActorRef;
import akka.camel.CamelMessage;
import akka.camel.javaapi.UntypedConsumerActor;
import akka.dispatch.Mapper;
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
		return "jetty:http://" + Config.listen_addr + ":" + Config.listen_port;
	}

	@Override
	public void onReceive(Object msg) {
		CamelMessage message = (CamelMessage) msg;
		String uploadPath = getUploadPath();
		log.warning(">>>>> Upload Path: " + uploadPath);
		saveBody(message, uploadPath);
		message = setBody(message, uploadPath);
		log.warning(">>>>Body updated");
		
		unarchiver.forward(message, getContext());
		log.warning(">>>>Message forwarder");
		// getSender().tell("File recieved", getSelf());
	}

	private String getUploadPath(){
		String filename = UUID.randomUUID().toString();
		return Config.uploadDir + filename;
	}

	private void saveBody(CamelMessage message, String path){
		FileInputStreamCache bodyStream = message.getBodyAs(FileInputStreamCache.class,
															getCamelContext());
		try {
			FileOutputStream fstream = new FileOutputStream(path);
			bodyStream.writeTo(fstream);
			fstream.close();
		} catch (IOException ex) {
			getSender().tell("Could not recieve file to "+ path +  " Error:"
					+ ex.getMessage(), getSelf());
			return;
		}
	}

	private CamelMessage setBody(CamelMessage message, String new_body){
		CamelMessage replacedMessage = message.mapBody(new Mapper<Object, String>() {
	        @Override
	        public String apply(Object body) {
	          return new_body;
	        }
	      });
		return replacedMessage;
	}
}
