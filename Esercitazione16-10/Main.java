public class Main {
    public static void main (String[] args){
        ArrayIndexedCollection<Integer> collect = new ArrayIndexedCollection<>(5);
        try {
            collect.insertAt(23, 0);
            collect.insertAt(32,1);
            collect.insertAt(26,1);
        }
        catch (NegativeIndexException | NullPointerException | IndexOutOfBoundsException e1){
            System.out.println(e1);
        }
        try {
            System.out.println(collect.get(2).toString());
        }
        catch (NegativeIndexException | IndexOutOfBoundsException e1) {
            System.out.println(e1);
        }
        try{
            System.out.println(collect.indexOf(26));
        }
        catch (NullPointerException e2){
            System.out.println();
        }
        System.out.println(collect.size());
    }
}
