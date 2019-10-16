import java.util.Vector;

public class ModStringCollectionC implements ModStringCollection {
    // AF: < colect[i],occ[i] > | 0 <= i < collect.size
    // IR: dim == collect.size && collect != null && occ != null
    //     && for all 0 <= i < dim collect[i] != null && for all 0 <= i < dim occ[i] != null
    //     && for all 0 <= i,j < dim -> collect[i] != collect[j]

    private Vector <ModString> collect;
    private Vector <Integer> occ;
    private int dim;


    // contructor

    public ModStringCollectionC(){
        collect = new Vector<ModString>();
        occ = new Vector<Integer>();
        dim=0;
    }

    // EFFECTS: Inizializza i vettori della collezione



    // conta le occorrenze di str nella collection

    public int occurrences (ModString str) throws NullPointerException{
        int n=0;
        if (str==null){
            throw new NullPointerException();
        }
        for (int i=0; i< dim;i++){
            if ( collect.get(i).equals(str) ){
                n = occ.get(i);
            }
        }
        return n;
    }

    // RETURNS: il numero di occorrenze di str nella collection
    // THROWS: NUllPointerException se str==null



    // restituisce il numero di elementi presenti nella collection

    public int size(){
        int sum=0;
        for (int i=0;i<dim;i++){
            sum=sum+occ.get(i);
        }
        return sum;
    }

    // RETURNS: la cardinalità della collezione



    // inserisce la stringa str nella collection

    public void insert(ModString str) throws NullPointerException{
        if (str==null){
            throw new NullPointerException();
        }
        for (int i=0;i<dim;i++){
            if (collect.get(i).equals(str)){
                occ.setElementAt(occ.get(i)+1,i);
                return;
            }
        }
        collect.addElement(str);
        occ.add(1);
        dim++;
    }

    // REQUIRES: str != null
    // MODIFIES: this
    // EFFECTS: inserisce str nella collezione in coda
    // THROWS: NullPointerException se str==null



    // rimuove al max n occorrenze di str dalla collezione, se n==0 le elimina tutte

    public int remove(ModString str, int n) throws NullPointerException, NegativeIndexException{
        if (str==null){
            throw new NullPointerException();
        }
        if (n<0){
            throw new NegativeIndexException();
        }
        int i=0;
        int tmp;
        int del=0;
        boolean ok=false;
        while (i<dim && !ok){
            if (collect.get(i).equals(str)) {
                tmp = occ.get(i);
                if (tmp > n && n!=0) {
                    del = n;
                    occ.setElementAt(tmp - n, i);
                }
                else if (n==0 || tmp<=n) {
                    del = tmp;
                    collect.remove(i);
                    occ.remove(i);
                    dim--;
                }
                ok=true;
            }
            i++;
        }
        return del;
    }

    // REQUIRES: str!=null, n>=0
    // MODIFIES: this
    // EFFECTS: rimuove al max n occorrenze di str dalla collezione, se n==0 le elimina tutte
    // RETURNS: il numero di elementi eliminati
    // THROWS: NullPointerException se str==null, NegativeIndexException se n<0



    // restituisce gli elementi della collecion eliminando i duplicati

    public String[] getUnique(){
        String collection[]= new String[collect.size()];
        for (int i=0; i< dim;i++){
            collection[i]= (collect.get(i).toString());
        }
        return collection;
    }

    // MODIFIES: this
    // EFFECTS: rimuove i duplicati dalla colezione
    // RETURNS: un array contenente le stringhe della collezione
}
