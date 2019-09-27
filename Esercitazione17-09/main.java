import java.util.Scanner;

public class main {
    public static void main (String [] args){
        StringTable table1 = new StringTable();
        Scanner input = new Scanner(System.in);
        boolean exit= false;
        String cont;
        while (!exit){
            String key= input.next();
            int value= input.nextInt();

            try {
                table1.addName(key, value);
            }
            catch (DuplicateEntryException e){
                System.out.println(e.getMessage());
            }

            System.out.println("Vuoi inserire altri elementi?");
            cont= input.next();
            if (cont.equals("N") || cont.equals("n")){
                exit=true;
            }
        }
        System.out.print("La chiave col valore più basso è: ");
        System.out.println(table1.getLowest());
        System.out.print("Il numero di chiavi presente in tabella è: ");
        System.out.println(table1.infoSize());
        //System.out.println(table1.getVal("**STRINGA DA CECARE**"));
    }
}
