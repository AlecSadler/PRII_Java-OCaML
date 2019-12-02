public class DuplicateCategoryException extends Throwable {
    public DuplicateCategoryException(){
        super("Categoria già presente in bacheca!");
    }
}
