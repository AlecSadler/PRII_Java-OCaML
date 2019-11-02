/* AF: f(elems<V>,capacity,position,limit) = < elems[i],capacity,position,limit >
   IR: elems != null && 0 <= position < limit && 0 <= limit <= capacity && capacity >=0
 */

import java.util.Vector;

public class myBuffer<T> implements Buffer<T> {

    private Vector<T> elems;
    private int capacity;
    private int position;
    private int limit;

    public myBuffer (int c) {
        elems =  new Vector<T>();
        for (int i=0;i<c;i++){
            elems.add(null);
        }
        capacity= c;
        limit=c;
        position= 0;

    }

    @Override
    public void rewind() {
        position=0;
    }

    @Override
    public void clear() {
        position=0;
        for (int i=0;i<limit;i++){
            elems.set(i,null);
        }
    }

    @Override
    public void put(T[] src) throws BufferOverloadException, NullPointerException {
        if (src == null) {
            throw new NullPointerException();
        }
        if (src.length > (limit - position)) {
            throw new BufferOverloadException();
        }
        for (int i = 0; i < src.length; i++) {
            elems.set(position,src[i]);
            position++;
        }
    }

    @Override
    public T[] get() {
         T arrBuffer[]=  (T[]) new Object[limit];
         for (int i=0;i<limit;i++){
             arrBuffer[i]= elems.get(i);
         }
         return arrBuffer;
    }

    @Override
    public void printBuffState() {
        T aux[]= (T[]) new Object[limit];
        try {
            aux = this.get();
        }
        catch (NullPointerException e){
            System.out.println(e);
        }
        for (int i=0; i<aux.length;i++){
            System.out.print(aux[i]+" ");
        }
        System.out.println();
    }
}
