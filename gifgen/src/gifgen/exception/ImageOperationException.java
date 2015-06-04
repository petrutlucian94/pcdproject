package gifgen.exception;


public class ImageOperationException extends GifgenException {
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

    public ImageOperationException(Exception e){
        super(e);
    }
}
