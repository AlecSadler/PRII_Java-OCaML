public class veichle {
    protected String factory;
    protected String model;
    protected String plate;
    protected String owner;

    // constructor
    public veichle (String fac, String mod, String plt, String onr){
        factory=fac;
        model=mod;
        plate=plt;
        owner=onr;
    }
}

class van extends veichle{
    private float heigth;
    private float capacity;

    // constructor
    public van (String fac, String mod, String plt, String onr, float hgh, float cap){
        super(fac,mod,plt,onr);
        heigth=hgh;
        capacity=cap;
    }
}

class car extends veichle{
    protected String fuel;
    protected String type;

    // constructor
    public car (String fac, String mod, String plt, String onr, String f, String t){
        super(fac,mod,plt,onr);
        fuel=f;
        type=t;
    }
}

class bike extends veichle {
    protected String type;
    protected int displ;

    // constructor
    public bike(String fac, String mod, String plt, String onr, String t, int ds) {
        super(fac, mod, plt, onr);
        type = t;
        displ = ds;
    }
}
