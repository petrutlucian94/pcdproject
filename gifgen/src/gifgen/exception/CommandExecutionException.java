package gifgen.exception;

import java.io.IOException;

public class CommandExecutionException extends IOException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CommandExecutionException(){
		super();
	}
	public CommandExecutionException(String mesaj){
		super(mesaj);
	}
	public CommandExecutionException(String mesaj, Throwable ex){
		super(mesaj,ex);
	}
	
	public CommandExecutionException(IOException e){
		super(e);
	}
	
	public CommandExecutionException(Exception e){
		super(e);
	}

}
