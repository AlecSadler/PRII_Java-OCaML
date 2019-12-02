/* OVERVIEW: tipo di dato astratto che rappresenta una collezione di oggetti di tipo Data appartenenti ad una
             determinata categoria.
   TYPICAL ELEMENT: < friends[0...friends.size-1],name,posts[0...posts.size-1] >

   AF: f(< friends[0...friends.size-1],name,posts[0...posts.size-1] >) ->
       < friends[0...friends.size-1],name,posts[0...posts.size-1] >
   IR: friends!=null && name!=null && posts!=null
       forall 0<=i,j<friends.size -> friends[i].equals(friends[j])==false
       forall 0<=i,j<posts.size -> post[i].equals(post[j])==false
 */

import java.util.Vector;

public class Category<T extends Data> {
    private Vector<String> friends;
    private String name;
    private Vector<T> posts;

    public Category(String nome) throws NullPointerException {
        if (nome==null){
            throw new NullPointerException();
        }
        this.name = nome;
        this.friends = new Vector<>();
        this.posts = new Vector<>();
    }
    /* REQUIRES: nome!=null
       THROWS: NullPointerException se nome==null
       MODIFIES: this
       EFFECTS: this.string=nome, i vettori friends e posts vengono inizializzati
     */

    public void addData(T post) throws NullPointerException, InvalidContentException {
        if (post == null) {
            throw new NullPointerException();
        }
        if (posts.contains(post)) throw new InvalidContentException();
        this.posts.add(post);
    }
    /* REQUIRES: post!=null && posts.contains(post)==false
       THROWS: NullPointerException se post==null
               InvalidContentException se posts.contains(post)
       MODIFIES: this
       EFFECTS: this.posts.add(post), viene aggiunto l'oggetto post alla categoria
     */

    public void remData(T post) throws NullPointerException, InvalidContentException {
        if (post == null) {
            throw new NullPointerException();
        }
        if (this.posts.contains(post)) {
            this.posts.remove(post);
        } else {
            throw new InvalidContentException();
        }
    }
    /* REQUIRES: post!=null
       THROWS: NullPointerException se post==null
       MODIFIES: this
       EFFECTS: this.posts.remove(post), viene rimosso l'oggetto post alla categoria
     */

    public void newFriend(String friend) throws NullPointerException, DuplicateFriendException {
        if (friend == null) {
            throw new NullPointerException();
        }
        if (this.friends.contains(friend)) throw new DuplicateFriendException();
        this.friends.add(friend);
    }
    /* REQUIRES: friend!=null && this.friends.contains(friend)==false
       THROWS: NullPointerException se friend==null
               DuplicateFriendException se this.friends.contains(friend)
       MODIFIES: this
       EFFECTS: this.friends.add(friend), viene associato friend alla categoria
     */

    public void delFriend(String friend) throws NullPointerException {
        if (friend == null) {
            throw new NullPointerException();
        }
        if (this.friends.contains(friend)) {
            this.friends.remove(friend);
        }
    }
    /* REQUIRES: friend!=null
       THROWS: NullPointerException se friend==null
       MODIFIES: this
       EFFECTS: this.friends.remove(friend), viene rimosso friend dal vettore friends
     */

    public Vector<T> getPosts(){
        return this.posts;
    }

    public String getName() {
        return name;
    }

    public Vector<String> getFriends() {
        return friends;
    }

    public T getCont(int i) throws IndexOutOfBoundsException, NegativeIndexException{
        if (i>=this.posts.size()) throw new IndexOutOfBoundsException();
        if (i<0) throw new NegativeIndexException();
        return this.posts.get(i);
    }
    /* REQUIRES: i>=0 && i<this.posts.size
       THROWS: NegativeIndexException se i<0
               IndexOutOfBoundsException se i>=this.posts.size
       RETURNS: this.posts.get(i)
       EFFECTS: restituisce l'elemento T alla posizione i del vettore posts
     */
}
