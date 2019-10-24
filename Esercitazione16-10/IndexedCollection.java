/* OVERVIEW: Collezione indicizzata di oggetti ti tipo generico, modificabile, di dimensione prestabilita.
   TYPICAL ELEMENT: < E[i],i > | 0 < i < dim && size = #E[i]!=null && size<=dim. */

public interface IndexedCollection<E> {

    // Inserisce l'elemento elem in posizione p, spostando l'elemento già presente in una posizione libera
    public void insertAt (E elem,int p) throws NegativeIndexException,NullPointerException,IndexOutOfBoundsException;
    /* REQUIRES: elem!=null, p>=0, p < dim.
       MODIFIES: this
       THROWS: NegativeIndexException se p<0, IndexOutOfBoundsException se p>=dim, NullPointerException se elem==null
       EFFECTS: Inserisce elem nella posizione p, spostando l'elemento eventualmente presente in una posizione libera
     */

    // Restituisce l'elemento in posizione p
    public E get(int p) throws IndexOutOfBoundsException, NegativeIndexException;
    /* REQUIRES: 0 <= p < dim
       THROWS: IndexOutOfBoundException se p>=dim, NegativeIndexException se p<0
       EFFECTS: restituisce l'elemento in posizione p della collezione
     */

    // Restituisce l'indice in cui è posizionato elem, se elem non è presente ritorna -1
    public int indexOf (E elem) throws NullPointerException;
    /* REQUIRES: elem!=null
       THROWS: NullPointerException se elem==null
       EFFECTS: ritorna l'indice in cui è posizionato elem, -1 se elem non è presente
     */

    // Restituisce il numero di elementi non null della collezione
    public int size();
    // EFFECTS: Restituisce il numero di elementi non null della collezione
}

