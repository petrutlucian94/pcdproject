package gifgen.actors;

import akka.actor.UntypedActor;

public class GifGenerator extends UntypedActor {
	@Override
	public void onReceive(Object message) {
		getSender().tell("Test message", getSelf());
	}
}
