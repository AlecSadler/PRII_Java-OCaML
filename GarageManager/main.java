import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        System.out.println("***GARAGE MANAGER***");
        Scanner input = new Scanner(System.in);
        System.out.println("Insert number of garage slots:");
        int garage_dim = Integer.parseInt(input.nextLine());
        boolean exit = false;
        garage g01 = new garage(garage_dim);
        int func;
        while (!exit) {
            System.out.println("Select an action:\n(1) ENTRY CAR\n(2) ENTRY VAN\n(3) ENTRY BIKE\n(4) SLOTS SITUATION\n(5) SEARCH VEICHLE BY PLATE\n(6) SEARCH VEICHLE BY OWNER\n(7) VEICHLE EXIT");
            func = Integer.parseInt(input.nextLine());
            switch (func) {
                case 1:
                    System.out.println("Insert car factory:");
                    String fact = input.nextLine();
                    System.out.println("Insert car model:");
                    String mod = input.nextLine();
                    System.out.println("Insert car plate:");
                    String plt = input.nextLine();
                    System.out.println("Insert car owner:");
                    String owr = input.nextLine();
                    System.out.println("insert tyoe of fuel - Gasoline,Diesel,Hybrid,Gas...");
                    String f = input.next();
                    System.out.println("insert car type - Coupe,Suv,Wagon,Berlina...");
                    String t = input.next();
                    g01.add_car(fact, mod, plt, owr, f, t);
                    input.nextLine();
                    break;
                case 2:
                    System.out.println("Insert van factory:");
                    fact = input.nextLine();
                    System.out.println("Insert van model:");
                    mod = input.nextLine();
                    System.out.println("Insert van plate:");
                    plt = input.nextLine();
                    System.out.println("Insert van owner:");
                    owr = input.nextLine();
                    System.out.println("Insert van height:");
                    float hgt = input.nextFloat();
                    System.out.println("insert van cargo liters capacity:");
                    float cap = input.nextFloat();
                    g01.add_van(fact, mod, plt, owr, hgt, cap);
                    input.nextLine();
                    break;
                case 3:
                    System.out.println("Insert bike factory:");
                    fact = input.nextLine();
                    System.out.println("Insert bike model:");
                    mod = input.nextLine();
                    System.out.println("Insert bike plate:");
                    plt = input.nextLine();
                    System.out.println("Insert bike owner:");
                    owr = input.nextLine();
                    System.out.println("Insert bike type: Naked,Sport,Offroad,Chopper...");
                    t = input.next();
                    System.out.println("Insert bike displacement in mc3:");
                    int dsp = input.nextInt();
                    g01.add_bike(fact, mod, plt, owr, t, dsp);
                    input.nextLine();
                    break;
                case 4:
                    g01.garage_state();
                    System.out.print("AvailabLe slots: ");
                    System.out.println(g01.free_slots());
                    break;
                case 5:
                    System.out.println("Insert veichle plate or a part of that:");
                    plt = input.nextLine();
                    g01.plate_src(plt);
                    break;
                case 6:
                    System.out.println("Insert owner name or a part of that:");
                    owr = input.nextLine();
                    g01.owner_src(owr);
                    break;
                case 7:
                    System.out.println("Insert exited veichle plate:");
                    plt = input.nextLine();
                    g01.exit_veichle(plt);
                    break;
            }
            System.out.println("Do you want to do a new operation? (1):YES/ (2):NOT");
            int cont = Integer.parseInt(input.nextLine());
            if (cont == 2) {
                exit = true;
            }
        }
    }
}