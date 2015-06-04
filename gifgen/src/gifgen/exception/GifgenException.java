package gifgen.exception;


public class GifgenException extends Exception {
    private static final long serialVersionUID = 1L;

    public GifgenException(){
        super();
    }

    public GifgenException(String mesaj){
        super(mesaj);
    }

    public GifgenException(String mesaj, Throwable ex){
        super(mesaj,ex);
    }

    public GifgenException(Exception e){
        super(e);
    }
}
