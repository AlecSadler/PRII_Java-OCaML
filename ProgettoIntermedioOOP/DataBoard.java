/* OVERVIEW: Collezione di oggetti che estendono il tipo astratto Data
   TYPICAL ELEMENT: < UserID, Categories[0...categories[size-1], Password >
 */

import java.util.Iterator;
import java.util.List;

public interface DataBoard<T extends Data> {

    public String getMyId();
    // RETURNS: this.myUserId
    // EFFECTS: Restituisce l'userID del creatore della DataBoard

    public void createCategory (String category,String password) throws NullPointerException, WrongPasswordException, DuplicateCategoryException;
    /* REQUIRES: category!=null && password!=null && password.equal(myPassword)==true &&
                 forall 0 <= i < myCategories.size -> ! (myCategories.get(i).name.equal(category))
       THROWS: NullPointerException se category==null | password==null
               WrongPasswordException se  password.equal(myPassword)==false
               DuplicateCategoryException se forall 0 <= i < myCategories.size -> ! (myCategories.get(i).name.equal(category))== false
       MODIFIES: this
       EFFECTS: viene aggiunta alla DataBoard la categoria denominata come passato tramite parametro.
    */

    public void removeCategory (String category,String password) throws NullPointerException, WrongPasswordException, CategoryNoExistsException;
    /* REQUIRES: category!=null && password!=null && password.equal(myPassword)==true
                 exists 0<=i<myCategories.size -> myCategories.get(i).getNaame.equals(category)
       THROWS:  NullPointerException se category==null | password==null
                WrongPasswordException se  password.equal(myPassword)==false
                CategoryNoExistsException se !(exists 0<=i<myCategories.size -> myCategories.get(i).getNaame.equals(category))
       MODIFIES: this
       EFFECTS: viene eliminata dalla DataBoard se presente la categoria denominata come passato tramite parametro.
     */

    public void addFriend (String category,String password,String friend) throws NullPointerException, WrongPasswordException, CategoryNoExistsException;
    /* REQUIRES: category!=null && password!=null && friend!=null && password.equal(myPassword)==true &&
                 exists 0 <= i < myCategories.size -> myCategories.get(i).name.equals(category)
       THROWS:   NullPointerException se category==null | password==null | friend==null
                 WrongPasswordException se  password.equal(myPassword)==false
                 CategoryNoExistsException se !(exists 0 <= i < myCategories.size -> myCategories.get(i).name.equals(category))
       MODIFIES: this
       EFFECTS:  Aggiunge un amico alla categoria denominata come pasato nel parametro
    */

    public void removeFriend (String category,String password,String friend) throws NullPointerException, WrongPasswordException;
    /* REQUIRES: category!=null && password!=null && friend!=null && password.equal(myPassword)==true
       THROWS:   NullPointerException se category==null | password==null | friend==null
                 WrongPasswordException se  password.equal(myPassword)==false
       MODIFIES: this
       EFFECTS:  Elimina un amico alla categoria denominata come pasato nel parametro
     */

    public boolean put (String password,T post, String category) throws NullPointerException, WrongPasswordException;
    /* REQUIRES: category!=null && password!=null && post!=null && password.equal(myPassword)==true &&
       THROWS:   NullPointerException se category==null | password==null | post==null
                 WrongPasswordException se  password.equal(myPassword)==false
       MODIFIES: this
       RETURNS: true se l'operazione è andata a buon fine
                false se !(exists 0 <= i < myCategories.size -> myCategories.get(i).name.equals(category))
       EFFECTS: aggiunge un dato nella categoria denominata come specificato dal parametro category
     */

    public T get (String password,T post) throws NullPointerException, WrongPasswordException, InvalidContentException;
    /* REQUIRES: password!=null && password.equal(myPassword)==true && post!=null &&
                 exists 0<=i<myCategories.size -> myCategories.get(i).posts.contains(post)
       THROWs:   NullPointerException se password==null | post==null
                 WrongPasswordException se  password.equal(myPassword)==false
                 InvalidDataException se !( exists 0<=i<myCategories.size -> myCategories.get(i).posts.contains(post) )
       RETURNS:  una copia del dato post di tipo T che estende Data
       EFFECTS:  restituisce una copia del dato post passato come parametro
     */

    public T remove (String password,T post) throws NullPointerException, WrongPasswordException, InvalidContentException;
    /* REQUIRES: password!=null && post!=null && password.equal(myPassword)==true &&
                 exists 0<=i<myCategories.size -> myCategories.get(i).posts.contains(post)
       THROWS:   NullPointerException se password==null | post==null
                 WrongPasswordException se  password.equal(myPassword)==false
                 InvalidDataException se  !( exists 0<=i<myCategories.size -> myCategories.get(i).posts.contains(post) )
       MODIFIES: this
       RETURNS:  una copia dell'oggetto eliminato.
       EFFECTS:  elimina dalla DataBoard il dato passato come parametro.
     */

    public List<T> getDataCategory (String password, String category) throws NullPointerException, WrongPasswordException, CategoryNoExistsException;
    /*  REQUIRES: category!=null && password!=null && password.equal(myPassword)==true &&
                  exists 0 <= i < myCategories.size -> myCategories.get(i).name.equals(category)
        THROWS:   NullPointerException se category==null | password==null
                  WrongPasswordException se  password.equal(myPassword)==false
                  CategoryNoExistsException se !(exists 0 <= i < myCategories.size -> myCategories.get(i).name.equals(category))
        RETURNS:  Una lista di tutti elementi T che estendono data appartenenti alla categoria di nome category(parametro)
        EFFECTS:  restituisce una lista di tutti gli elementi della categoria denominata come il parametro category
     */

    public Iterator<T> getIterator (String password) throws NullPointerException, WrongPasswordException;
    /* REQUIRES: password!=null && password.equals(myPassword)==true
       THROWS:   NullPointerException se password==null
                 WrongPasswordException se password.equals(myPassword)==false
       RETURNS:  un iteratore senza remove su tutti gli elementi di tutte le categorie presenti in bacheca
                 ordinati in ordine DECRESCENTE in base al numero di likes.
       EFFECTS:  restituisce un iteratore senza remove su tutti gli elementi di tutte le categorie presenti in bacheca
                 ordinati in ordine DECRESCENTE in base al numero di likes.
     */

    public void insertLike (String friend,T post) throws NullPointerException, UnknownUserException, InvalidContentException;
    /* REQUIRES: friend!=null && post!=null && post deve esistere in almento una caegoria &&
                 la categoria in cui è presente il post deve avere come amico il parametro friend.
       THROWS:   NullPointerException se friend==null | post==null
                 UnknownUserException se friend non fa parte degli amici della categoria a cui appartiene post
                 InvalidDataException se l'elemento post non è presente nella bacheca
       MODIFIES: this
       EFFECTS:  associa un like al post passato come parametro da parte dell'amico con userId uguale al parametro friend.
     */

    public Iterator<T> getFriendIterator (String friend) throws NullPointerException, UnknownUserException;
    /* REQUIRES: friend!=null &&
                 exists 0<=i<myCategories.size && exists 0<=j<myCategories.get(i).getFriends.size ->
                 myCategories.get(i).getFriends().get(j).equals(friend)
       THROWS:   NullPointerException se friend==null
                 UnknownUserException se !(exists 0<=i<myCategories.size && exists 0<=j<myCategories.get(i).getFriends.size ->
                 myCategories.get(i).getFriends().get(j).equals(friend))
       RETURNS:  un iteratore senza remove su tutti gli elementi condivisi con l'utente friend passato come parametro
       EFFECTS:  restituisce un iteratore senza remove su tutti gli elementi condivisi con l'utente friend passato come parametro
     */

    public void printCats();
    // EFFECTS: Stampa l'elenco dei nomi delle categorie presenti in bacheca

    public void printFriends();
    // EFFECTS: Stampa l'elenco degli amici per ogni categoria
}
