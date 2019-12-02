public class DoubleLikeException extends Throwable {
    public DoubleLikeException(){
        super("Questo utente ha già lasciato un like a questo contenuto!");
    }
}
