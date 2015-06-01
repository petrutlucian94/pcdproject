package gifgen.utils;

import akka.camel.CamelMessage;
import akka.dispatch.Mapper;

public class CamelMessageUtils {
	public static CamelMessage setBody(CamelMessage message, String new_body){
		CamelMessage replacedMessage = message.mapBody(new Mapper<Object, String>() {
	        @Override
	        public String apply(Object body) {
	          return new_body;
	        }
	      });
		return replacedMessage;
	}

	public static String getBody(CamelMessage message){
		return message.body().toString();
	}
}
