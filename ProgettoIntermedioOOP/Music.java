/* OVERVIEW: tipo di dato astratto che estende Data,rappresentante una canzone.
   TYPICAL ELEMENT: < header,content,likes,like_detail[0...like_detail.size-1,length_seconds >

   AF: f(header,content,likes,like_detail[0...like_detail.size-1], length_seconds) -> < header,content,likes,length_seconds >
   IR: header!=null && T!=null && 0 <= likes && likes!=null && like_detail!=null && like_detail.size==likes
       length_seconds!=null && lenght_seconds > 0
 */

public class Music extends Data {
    private Integer length_seconds;

    // costruttore
    public Music (String hd,String desc,int lgh) throws NegativeDurationException {
        super(hd,desc);
        if (lgh<=0) throw new NegativeDurationException();
        this.length_seconds= lgh;
    }

    public String toString(){
        return (super.toString() + " - Durata(sec): " + this.length_seconds);
    }

    public String Display(){
        return this.toString();
    }

    public boolean equals(Music m) {
        if (super.getDescription().equals(m.getDescription()) && super.getHeader().equals(m.getHeader()) && this.length_seconds==m.length_seconds){
            return true;
        }
        return false;
    }

    public Music copy() {
        Music clone = null;
        try {
            clone = new Music(super.getHeader(), super.getDescription(), this.length_seconds);

        } catch (NegativeDurationException e) {
            System.out.println(e.getMessage());
        }
        return clone;
    }
    // RETURNS: una copia dell'oggetto
}

