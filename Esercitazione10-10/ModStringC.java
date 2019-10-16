public class ModStringC implements ModString {
    // AF: < element[i], element.length > when element[i] != null
    // IR: ogni carattere è diverso da null
    //     element != null
    //     0 <= dim < element.lenght
    //     dim = #{ i | element[i]!=null

    private final Character[] element;
    private int dim;

    // constructor

    public ModStringC(String str) throws NullPointerException{
        if (str==null) {
            throw new NullPointerException();
        }
        dim = str.length();
        element = new Character[dim];
        for (int i=0;i<dim;i++){
            element[i]=str.charAt(i);
        }
    }

    // REQUIRES: str!=null
    // THROWS: se str==null lancia una NullPointerException
    // MODIFIES: thia
    // EFFECTS: inizializza l'oggetto con le sue variabili d'istanza



    // restituisce il numero di caratteri della stringa

    public int size(){
        return dim;
    }

    // RETURNS: restituisce la dimensione della stringa



    // inserisce il carattere c in posizione n sovrascrivendo quello presente

    public void update(char c,int n) throws IllegalArgumentException, NegativeIndexException {
        if (n < 0) {
            throw new NegativeIndexException();
        }
        if (n >= dim) {
            throw new IllegalArgumentException();
        }
        element[n] = c;
    }

    // REQUIRES: 0 <= n < size()
    // MODIFIES: this
    // EFFECTS: sostituisce il carattere presente in posizione n con il carattere c
    // THROWS: IllegalArgumentException se n>=size(), NegativeIndexException se n<0



    // elimina il carattere in posizione n shiftando opportunamente i successivi

    public void remove(int n) throws IllegalArgumentException, NegativeIndexException {
        if (n < 0) {
            throw new NegativeIndexException();
        }
        if (n >= (size() - 1)) {
            throw new IllegalArgumentException();
        }
        for (int i = n+1; i < dim;i++) {
            element[i] = element[i-1];
        }
        dim--;
    }

    // REQUIRES: O <= n < size()
    // MODIFIES: this
    // EFFECTS: rimuove il carattere che si trova in posizione n
    // THROWS: IllegalArgumentException se n>=size(), NegativeIndexException



    // converte una ModString in una comune String

    public String toString(){
        String aux= "";
        for (int i=0;i<dim;i++){
            aux= aux + element[i];
        }
        return aux;
    }

    // EFFECTS: converte un oggetto ModString in un oggetto String



    public boolean equals(ModString str){
        return this.toString().equals(str.toString());
    }
}

