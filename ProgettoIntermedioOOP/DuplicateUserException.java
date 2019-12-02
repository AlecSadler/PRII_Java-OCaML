public class DuplicateUserException extends Throwable {
    public DuplicateUserException(){
        super("Utente già presente sulla piattaforma!");
    }
}
