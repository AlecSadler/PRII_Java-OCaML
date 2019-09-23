// il programma calcola la potenza di 2 con un esponente a scelta, per poi cambiare la base.

import static java.lang.Math.*;

class power {
    private int exp;

    // costruttore di oggetti
    public power (int val){
        exp=val;
    }

    // metodo di calcolo potenza di 2
    public int calc(){
        return (int)Math.pow(2,exp);
    }

    // metodo di calcolo potenza con base passata come parametro
    public int base_change(int a){
        return (int)Math.pow(a,exp);
    }
}

class main{
    public static void main (String[] args){
        power potenza = new power(3);
        System.out.println(potenza.calc());
        System.out.println(potenza.base_change(4));
    }
}
