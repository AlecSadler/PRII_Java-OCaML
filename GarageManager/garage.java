import java.util.Vector;

public class garage {
    private Vector<veichle> slots;
    protected int sl;

    // constructor
    public garage (int dim){
        slots= new Vector<veichle>();
        sl=dim;
    }

    // add new van
    public void add_van (String fac, String mod, String plt, String onr, float hgh, float cap){
        if (slots.size() < sl){
            van newvan = new van (fac,mod,plt,onr,hgh,cap);
            slots.add(newvan);
        }
    }

    // add new car
    public void add_car (String fac, String mod, String plt, String onr, String f, String t){
        if (slots.size() < sl){
            car newcar = new car(fac,mod,plt,onr,f,t);
            slots.add(newcar);
        }
    }

    // add new bike
    public void add_bike (String fac, String mod, String plt, String onr, String t, int ds){
        if (slots.size() < sl){
            bike newbike = new bike (fac,mod,plt,onr,t,ds);
            slots.add(newbike);
        }
    }

    // print all veichles in garage
    public void garage_state(){
        int i;
        System.out.println("Veichles actually in the garage:\n");
        van a= new van("aa","aa","AA","A",1,1);
        car b= new car("bb","bb","1","B","B","C");
        bike c= new bike("cc","cc","1","C","C",'1');
        for (i=0;i<slots.size();i++) {
            if (slots.get(i).getClass() == (a.getClass())) {
                System.out.println("VAN: " + " " + slots.get(i).factory + " " + slots.get(i).model + " - " + slots.get(i).plate + " OWNER: " + slots.get(i).owner);
            }
            else if (slots.get(i).getClass() == (b.getClass())) {
                System.out.println("CAR: " + ((car)slots.get(i)).type + " " + slots.get(i).factory + " " + slots.get(i).model + " - " + slots.get(i).plate + " OWNER: " + slots.get(i).owner);
            }
            else{
                System.out.println("BIKE: " + ((bike)slots.get(i)).type + " " + slots.get(i).factory + " " + slots.get(i).model + " " + ((bike)slots.get(i)).displ + " - " + slots.get(i).plate + " OWNER: " + slots.get(i).owner);
            }
        }
    }

    // return available slots
    public int free_slots(){
        return sl - slots.size();
    }

    // print veichle in n-position
    public void position (int n){
        System.out.println(slots.get(n).factory + " " + slots.get(n).model + " " + slots.get(n).plate);
    }

    // search by plate
    public void plate_src (String pl){
        int i;
        for (i = 0; i < slots.size(); i++) {
            if (slots.get(i).plate.contains(pl)) {
                System.out.println(slots.get(i).factory + " " + slots.get(i).model + " " + slots.get(i).plate + " slot: " + i);
            }
        }
    }

    // search by owner
    public void owner_src (String owr){
        int i;
        for (i = 0; i < slots.size(); i++) {
            if (slots.get(i).owner.contains(owr)) {
                System.out.println(slots.get(i).factory + " " + slots.get(i).model + " " + slots.get(i).plate + " slot: " + i);
            }
        }
    }

    // veichle exit
    public void exit_veichle (String plt){
        int i;
        for (i=0;i<slots.size();i++){
            if (slots.get(i).plate.equals(plt)){
                slots.remove(i);
            }
        }
    }
}
