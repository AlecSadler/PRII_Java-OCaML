public interface ModString {
    // OVERVIEW: tipo delle stringhe editabili
    // TYPICAL ELEMENT: < char[00],.....char[size-1] >



    // restituisce il numero di caratteri della stringa

    public int size();

    // RETURNS: restituisce la dimensione in caratteri della stringa



    // inserisce il carattere c nella posizione n sovrascrivendo quello presente

    public void update(char c,int n) throws IllegalArgumentException, NegativeIndexException;

    // REQUIRES: 0 <= n < size()
    // MODIFIES: this
    // EFFECTS: sostituisce il carattere presente in posizione n con il carattere c
    // THROWS: IllegalArgumentException se n>=size(), NegativeIndexException se n<0



    // elimina il carattere in posizione n shiftando opportunamente i successivi

    public void remove(int n) throws IllegalArgumentException, NegativeIndexException;

    // REQUIRES: O <= n < size()
    // MODIFIES: this
    // EFFECTS: rimuove il carattere che si trova in posizione n preservando l'ordine
    // THROWS: IllegalArgumentException se n>=size(), NegativeIndexException



    // converte una ModString in una comune String

    public String toString();

    // EFFECTS: converte un oggetto di tipo ModString in uno di tipo String

    public boolean equals(ModString str);
}

