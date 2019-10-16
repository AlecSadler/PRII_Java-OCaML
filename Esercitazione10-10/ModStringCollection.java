public interface ModStringCollection {
    // OVERVIEW: collection di stringhe editabili (ModString)
    // TYPICAL ELEMENT: < str00 - occ00,.....str[size-1] - occ[size-1] >

    // restituisce il numero di occorrenze di str nella collezione

    public int occurrences (ModString str) throws NullPointerException;

    // RETURNS: il numero di occorrenze di str nella collection
    // THROWS: NUllPointerException se str==null



    // restituisce il numero di elementi presenti nella collezione

    public int size();

    // RETURNS: la cardinalità della collezione



    // inserise la stringa str nella collezione

    public void insert(ModString str) throws NullPointerException;

    // REQUIRES: str != null
    // MODIFIES: this
    // EFFECTS: inserisce str nella collezione in coda
    // THROWS: NullPointerException se str==null



    // rimuove al max n volte str dalla collezione, se n==0, le rimuove tutte.

    public int remove(ModString str, int n) throws NullPointerException, NegativeIndexException;

    // REQUIRES: str!=null, n>=0
    // MODIFIES: this
    // EFFECTS: rimuove al max n occorrenze di str dalla collezione, se n==0 le elimina tutte
    // RETURNS: il numero di elementi eliminati
    // THROWS: NullPointerException se str==null, NegativeIndexException se n<0



    // elimina i duplicati dalla collezione e restituisce un array con gli elementi della collezione

    public String[] getUnique();

    // MODIFIES: this
    // EFFECTS: rimuove i duplicati dalla colezione
    // RETURNS: un array contenente le stringhe della collezione
}
