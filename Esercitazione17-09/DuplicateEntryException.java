public class DuplicateEntryException extends Exception {
    public DuplicateEntryException(){
        super("Chiave già presente!");
    }
}
