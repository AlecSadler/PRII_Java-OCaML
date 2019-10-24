// AF: F(data[i],dim,size)-> <data[0],0......data[dim-1],dim-1>
// IR: 0 <= size < dim && data!=null && dim= data.length && for all 0<=i<dim -> size= #data[i] != null

public class ArrayIndexedCollection<E> implements IndexedCollection<E> {
    private E[] data;
    private int dim; // dimensione array
    private int size; // elementi non null

    // costruttore
    public ArrayIndexedCollection(int d){
        data = (E[])new Object[d];
        dim = d;
        size = 0;
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public void insertAt(E elem, int p) throws NegativeIndexException, NullPointerException, IndexOutOfBoundsException {
        if (elem==null){
            throw new NullPointerException();
        }
        if (p<0){
            throw new NegativeIndexException();
        }
        if (p>dim){
            throw new IndexOutOfBoundsException();
        }
        if (data[p]==null){
            data[p]=elem;
            size++;
        }
        else {
            E tmp = data[p];
            data[p] = elem;
            int i = 0;
            while (data[i] != null && i<dim) {
                i++;
            }
            if (i==dim) {
                throw new IndexOutOfBoundsException();
            }
            data[i] = tmp;
            size++;
        }
    }

    @Override
    public E get(int p) throws NegativeIndexException,IndexOutOfBoundsException{
        if (p<0){
            throw new NegativeIndexException();
        }
        if (p>dim){
            throw new IndexOutOfBoundsException();
        }
        return data[p];
    }

    @Override
    public int indexOf(E elem) throws NullPointerException {
        if (elem == null) {
            throw new NullPointerException();
        }
        int i=0;
        while (i < dim ){
            if (elem.equals(data[i])){
                return i;
            }
            i++;
        }
        return -1;
    }
}
