package gifgen;

import gifgen.actors.*;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Launcher {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// Config customConf = ConfigFactory.parseFile(new File("config/app.conf"));
		ActorSystem system = ActorSystem.create("gifgen");
		final ActorRef gifGenerator = system.actorOf(Props.create(GifGenerator.class));
		final ActorRef unarchiver = system.actorOf(Props.create(Unarchiver.class, gifGenerator));
		final ActorRef httpConsumer = system.actorOf(Props.create(HttpConsumer.class, unarchiver));
	}
}
