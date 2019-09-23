import java.util.Scanner;

class cc {
    private float saldo;

    // costruttore
    public cc (float val){
        saldo=val;
    }

    // metodo versamento
    public void versamento (float amount){
        this.saldo = this.saldo+amount;
    }

    // metodo prelievo
    public void prelievo (float amount){
        this.saldo = this.saldo-amount;
    }

    // metodo che mi restituisce il saldo del conto
    public float tot (){
        return saldo;
    }
}

class main {
    public static void main(String[] args) {
        double init;
        Scanner in = new Scanner(System.in);
        init = in.nextFloat();
        // creo il conto
        cc salvo = new cc(init);
        // verso un importo
        salvo.versamento(10500.00);
        System.out.println(salvo.tot());
        // prelevo un importo
        salvo.prelievo(450.20);
        System.out.println(salvo.tot());
    }
}
