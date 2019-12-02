/* OVERVIEW: tipo di dato astratto rappresentante un contenuto di qualsiasi tipo che estende Comparable
   TYPICAL ELEMENT: < header,content,likes,like_detail[0...like_detail.size-1] > con like_detail.size == likes

   AF: f(header,content,likes,like_detail[0...like_detail.size-1]) -> < header,content,likes >
   IR: header!=null && T!=null && 0 <= likes && likes!=null && like_detail!=null && like_detail.size==likes
       forall 0<=i,j< like_detail.size -> like_detail[i].equal(like_detail[j])==false
 */
import java.util.Vector;

public class Data implements Comparable<Data> {
    private String header;
    private String description;
    private Integer likes;
    private Vector<String> like_detail;

    // costruttore
    public Data (String head,String desc) throws NullPointerException {
        if (head==null | desc==null){
            throw new NullPointerException();
        }
        this.header = head;
        this.description = desc;
        this.likes = 0;
        this.like_detail = new Vector<>();
    }
    /* REQUIRES: tit!=null && cont!=null
       THROWS: NullPointerException se tit==null | cont==null
       MODIFIES: this
       EFFECTS: inizializza le variabili d'istanza per la creazione dell'oggetto,inoltre imposta la
                varibile likes=0 e il vettore like_details vuoto.
     */

    public int compareTo(Data obj) throws NullPointerException {
        if (obj == null){
            throw new NullPointerException();
        }
        if (obj.equals(this)) return 0;
        if (this.likes - obj.likes ==0) return 1;
        return this.likes - obj.likes;
    }


    public int getLikes() {
        return likes;
    }
    // RETURNS: il valore della variabile likes

    public String getHeader() {
        return header;
    }
    // RETURNS: il valore della variabile header

    public String getDescription() {
        return description;
    }
    // RETURNS: l'elemento T della variabile content

    public Vector<String> getLike_detail() {
        return like_detail;
    }
    // RETURNS: il vettore like_detail

    @Override
    public String toString() {
        return ("Intestazione: " +this.header + " - Contenuto: " + this.description +" - Likes: " + this.getLikes() );
    }

    public void Addlike (String user) throws DoubleLikeException,NullPointerException {
        if (user == null){
            throw new NullPointerException();
        }
        if (this.like_detail.contains(user)){
            throw new DoubleLikeException();
        }
        this.likes++;
        this.like_detail.add(user);
    }
    /* REQUIRES: user!=null && !(like_details.contains(user))
       THROWS: NUllPointerException se user==null
               DoubleLikeException se like_details.contains(user)
       MODIFIES: this
       EFFECTS: this.likes= this.likes+1 && this.like_details.add(user)
     */

    public String Display() {
        return this.toString();
    }
    // EFFECTS: ritorna this.toString

    public boolean equals(Data obj){
        if (this.description.equals(obj.description) && this.header.equals(obj.header)) return true;
        return false;
    }

    public Data copy(){
        Data clone= new Data(this.header,this.description);
        return clone;
    }
    // RETURNS: una copia dell'oggetto
}

