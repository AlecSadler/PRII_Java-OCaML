public class Main {
    public static void main (String[] args){
        myBuffer<Integer> bf01 = new myBuffer(5);
        Integer data1[]= {3,5};                         // array con i dati da caricare sul buffer
        Integer data2[]= {6,9};
        Integer data3[]= {1,7};
        try {
            bf01.put(data1);
            bf01.put(data2);
        }
        catch (NullPointerException | BufferOverloadException e){
            System.out.println(e);
        }
        bf01.printBuffState();
        bf01.rewind();
        try {
            bf01.put(data3);
        }
        catch (NullPointerException | BufferOverloadException e){
            System.out.println(e);
        }
        bf01.printBuffState();
        bf01.clear();
        bf01.printBuffState();
    }
}
