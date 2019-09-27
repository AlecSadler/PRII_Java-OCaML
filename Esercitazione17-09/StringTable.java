public class StringTable {
    protected int size=0;
    protected String[] keys;
    protected int[] val;

    // costruttore
    public StringTable(){
        keys = new String[10];
        val = new int[10];
    }

    // controlla il numero degli elementi,se necessario aumenta le dimensioni dell'array
    private void check_resize(){
        if (size == keys.length-1){
            String[] keys2 = new String[keys.length+10];
            int[] val2 = new int[keys.length+10];
            for (int i=0;i<keys.length-1;i++) {
                keys2[i]=keys[i];
                val2[i]=val[i];
            }
            keys=keys2;
            val=val2;
        }
    }

    // aggiunge una coppia nome/valore all'array
    public void addName (String key,int value) throws DuplicateEntryException{
        if (size>0) {
            boolean trovato = false;
            for (int i=0;i<size;i++) {
                if (keys[i].equals(key)) {
                    trovato = true;
                }
            }
            if (trovato==true){
                throw new DuplicateEntryException();
            }
        }
        check_resize();
        size++;
        keys[size-1]=key;
        val[size-1]=value;
    }

    // data una stringa come parametro restituisce il valore associato
    public int getVal (String key){
        for (int i=0;i < keys.length-1;i++){
            if (keys[i].equals(key)){
                return val[i];
            }
        }
        return -1;
    }

    // ritorna la stringa con il valore associato minore
    public String getLowest(){
        int lowest= 0;
        for (int i=1;i<size;i++){
            if (val[i] < val[lowest]) {
                lowest = i;
            }
        }
        return keys[lowest];
    }

    // ritorna il numero di coppie inserite
    public int infoSize(){
        return size;
    }
}
