package gifgen.actors;
import gifgen.Config;
import gifgen.utils.ArchiveExtractor;
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
	public void onReceive(Object msg) {
		log.warning("Unarchiver got the message");
		CamelMessage message = (CamelMessage) msg;
		String uploadPath = message.body().toString();
		String[] split = uploadPath.split("/");
		String fname = split[split.length - 1];
		String imageDir = Config.imageDir + fname;
		log.warning(uploadPath);
		unarchive(uploadPath, imageDir);
		gifGenerator.forward(msg, getContext());
	}

	private void unarchive(String src, String dest){
		ArchiveExtractor extractor = new ArchiveExtractor(src);
		extractor.extract(dest);
	}
}