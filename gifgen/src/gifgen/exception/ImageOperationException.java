package gifgen.exception;

import java.io.IOException;

public class ImageOperationException extends IOException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ImageOperationException(){
		super();
	}
	public ImageOperationException(String mesaj){
		super(mesaj);
	}
	public ImageOperationException(String mesaj, Throwable ex){
		super(mesaj,ex);
	}
	
	public ImageOperationException(IOException e){
		super(e);
	}
	
	public ImageOperationException(Exception e){
		super(e);
	}

}
