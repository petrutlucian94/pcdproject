package gifgen.exception;


public class ArchiverException extends GifgenException {
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

    public ArchiverException(Exception e){
        super(e);
    }
}
