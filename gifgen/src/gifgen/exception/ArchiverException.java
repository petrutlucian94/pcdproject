package gifgen.exception;

import java.io.IOException;

public class ArchiverException extends IOException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArchiverException(){
		super();
	}
	public ArchiverException(String mesaj){
		super(mesaj);
	}
	public ArchiverException(String mesaj, Throwable ex){
		super(mesaj,ex);
	}
	
	public ArchiverException(IOException e){
		super(e);
	}

}
