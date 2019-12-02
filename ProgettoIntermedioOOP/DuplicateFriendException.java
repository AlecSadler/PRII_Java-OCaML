public class DuplicateFriendException extends Throwable {
    public DuplicateFriendException(){
        super("Hai già aggiunto questo utente tra gli amici!");
    }
}
