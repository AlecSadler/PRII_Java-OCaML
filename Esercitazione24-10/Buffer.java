/* OVERVIEW: tipo di dato buffer identificato da un vettore di elementi generici, di capacità fissa non negativa,
un limite di scrittura <= della capacità e un indice posizione che indica il punto in cui scriverei dati in arrivo.

TYPICAL ELEMENT: < Buffer[i],position > | 0 <= i < limit, 0 <= limit <= capacity, 0 <= position < limit
*/

public interface Buffer<T> {

    public void clear();
    /* EFFECTS: elems[i]=null for all 0 <= i < limit
       MODIFIES: this
     */

    public void rewind();
    /* EFFECTS: position = 0, in caso venissero caricati elementi, eventuali elementi presenti nelle prime posizioni
                saranno sovrascritti
       MODIFIES: this
     */

    public void put (T[] src) throws BufferOverloadException, NullPointerException;
    /* REQUIRES: src!=null, src.length <= limit-position
       THROWS: BufferOverloadException se src.length > limit-position, NullPointerException se src==null
       MODIFIES: this
       EFFECTS: position = position + src.length, elem[position]= src[0]...elem[position+src.lenght-1] = src[src.length-1]
     */

    public T[] get() throws NullPointerException;
    /* REQUIRES: elems!=null
       THROWS: NullPointerException se elems==null
       EFFECTS: crea un array a di dimensione = limit, a[0....limit-1], a[i]= elem[i]
     */

    public void printBuffState();
    // EFFECTS: stampa la situazione del buffer, dalla posizione 0 a limit-1, elementi null compresi
}
