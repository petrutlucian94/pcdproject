package gifgen.exception;


public class CommandExecutionException extends GifgenException {
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

    public CommandExecutionException(Exception e){
        super(e);
    }
}
