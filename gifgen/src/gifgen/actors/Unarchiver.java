package gifgen.actors;
import java.io.IOException;

import gifgen.Config;
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
	public void onReceive(Object msg) throws IOException {
		log.warning("Unarchiver got the message");
		CamelMessage message = (CamelMessage) msg;
		String uploadPath = message.body().toString();
		String imageDir = getImageDir(uploadPath);
		log.warning(uploadPath);
		unarchive(uploadPath, imageDir);
		message = CamelMessageUtils.setBody(message, imageDir);
		gifGenerator.forward(message, getContext());
	}

	private String getImageDir(String uploadPath){
		String[] split = uploadPath.split("/");
		String fname = split[split.length - 1];
		String imageDir = Config.imageDir + fname;
		return imageDir;
	}
	
	private void unarchive(String src, String dest) throws IOException{
		ArchiveExtractor extractor = new ArchiveExtractor(src);
		extractor.extract(dest);
	}
}
