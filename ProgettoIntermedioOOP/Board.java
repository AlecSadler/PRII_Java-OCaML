/* AF: f(< UserID, Categories[0...categories[size-1], Password >)->
        < myUserID,myCategories[0...myCategories.size-1],myPassword >
   IR: myUserID!=null && myPassword!=null && myCategories!=null &&
       forall 0<=i,j<myCategories.size -> myCategories[i].equals(myCategories[j])==false
 */

import java.util.*;

public class Board<T extends Data> implements DataBoard<T> {
    private String myUserId;
    private Vector<Category<T>> myCategories;
    private String myPassword;

    //costruttore
    public Board(String user, String password){
        myUserId = user;
        myPassword = password;
        myCategories = new Vector<>();
    }

    public String getMyId() {
        return myUserId;
    }

    private boolean authenticate(String password){
        return password.equals(myPassword);
    }
    // RETURNS: true se password.equals(myPassword), false altrimenti

    public Category getCategory(String cat) {
        for (int i=0;i<myCategories.size();i++){
            if (myCategories.get(i).getName().equals(cat)){
                return myCategories.get(i);
            }
        }
        throw new NullPointerException();
    }

    @Override
    public void createCategory(String category, String password) throws NullPointerException, WrongPasswordException, DuplicateCategoryException {
        if (category==null | password==null){
            throw new NullPointerException();
        }
        if (!authenticate(password)){
            throw new WrongPasswordException();
        }
        int i=0;
        while (i<myCategories.size()){
            if (myCategories.get(i).getName().equals(category)){
                throw new DuplicateCategoryException();
            }
            i++;
        }
        Category cat = new Category(category);
        this.myCategories.add(cat);
    }

    @Override
    public void removeCategory(String category, String password) throws NullPointerException, WrongPasswordException,CategoryNoExistsException {
        if (category==null | password==null){
            throw new NullPointerException();
        }
        if (!authenticate(password)){
            throw new WrongPasswordException();
        }
        for (int i=0;i<myCategories.size();i++){
            if (this.myCategories.get(i).getName().equals(category)){
                this.myCategories.remove(i);
                return;
            }
        }
        throw new CategoryNoExistsException();
    }

    @Override
    public void addFriend(String category, String password, String friend) throws NullPointerException, WrongPasswordException, CategoryNoExistsException {
        if (category==null | password==null | friend==null){
            throw new NullPointerException();
        }
        if (!authenticate(password)){
            throw new WrongPasswordException();
        }
        boolean foundCat=false;
        for (int i=0;i<myCategories.size();i++){
            if (this.myCategories.get(i).getName().equals(category)){
                foundCat=true;
                try {
                    this.myCategories.get(i).newFriend(friend);
                    return;
                } catch (DuplicateFriendException e){
                    System.out.println(e.getMessage() + "\n");
                }
            }
        }
        if (!foundCat) throw new CategoryNoExistsException();
    }

    @Override
    public void removeFriend(String category, String password, String friend) throws NullPointerException, WrongPasswordException {
        if (category==null | password==null | friend==null){
            throw new NullPointerException();
        }
        if (!authenticate(password)){
            throw new WrongPasswordException();
        }
        for (int i=0;i<myCategories.size();i++){
            if (this.myCategories.get(i).getName().equals(category)){
                this.myCategories.get(i).delFriend(friend);
            }
        }
    }

    @Override
    public boolean put (String password, T post, String category) throws NullPointerException, WrongPasswordException {
        if (category==null | password==null | post==null){
            throw new NullPointerException();
        }
        if (!authenticate(password)){
            throw new WrongPasswordException();
        }
        for (int i=0;i<myCategories.size();i++){
            if (this.myCategories.get(i).getName().equals(category)){
                try {
                    this.myCategories.get(i).addData(post);
                } catch (InvalidContentException e){
                    System.out.println(e.getMessage() + "\n");
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public T get(String password, T post) throws NullPointerException, WrongPasswordException, InvalidContentException {
        if (password==null | post==null){
            throw new NullPointerException();
        }
        if (!authenticate(password)){
            throw new WrongPasswordException();
        }
        for (int i =0;i<this.myCategories.size();i++){
            for (int j=0;j<this.myCategories.get(i).getPosts().size();j++){
                if (this.myCategories.get(i).getPosts().get(j).equals(post)){
                    try {
                        this.myCategories.get(i).getCont(j).Display();
                        return (T) this.myCategories.get(i).getCont(j);
                    } catch (NegativeIndexException | IndexOutOfBoundsException e){
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        throw new InvalidContentException();
    }

    @Override
    public T remove(String password, T post) throws NullPointerException, WrongPasswordException, InvalidContentException {
        if (password==null | post==null){
            throw new NullPointerException();
        }
        if (!authenticate(password)){
            throw new WrongPasswordException();
        }
        T tmp = null;
        for (int i=0;i<myCategories.size();i++){
            for (int j=0; j<myCategories.get(i).getPosts().size();j++){
                if (myCategories.get(i).getPosts().get(j).equals(post)){
                    try {
                        tmp = (T) myCategories.get(i).getCont(j).copy();
                    } catch (IndexOutOfBoundsException | NegativeIndexException e){
                        System.out.println(e.getMessage());
                    }
                    try {
                        myCategories.get(i).remData(myCategories.get(i).getCont(j));
                    } catch (NegativeIndexException | IndexOutOfBoundsException e){
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        if (tmp==null){
            throw new InvalidContentException();
        }
        return tmp;
    }

    @Override
    public List getDataCategory(String password, String category) throws NullPointerException, WrongPasswordException, CategoryNoExistsException {
        if (password==null | category==null){
            throw new NullPointerException();
        }
        if (!authenticate(password)){
            throw new WrongPasswordException();
        }
        List <T> catDetail = new ArrayList<>();
        for (int i=0;i<myCategories.size();i++) {
            if (this.myCategories.get(i).getName().equals(category)) {
                for (int j = 0; j < myCategories.get(i).getPosts().size(); j++) {
                    catDetail.add(myCategories.get(i).getPosts().get(j));
                }
                return catDetail;
            }
        }
        throw new CategoryNoExistsException();
    }

    @Override
    public Iterator<T> getIterator(String password) throws NullPointerException, WrongPasswordException {
        if (password==null){
            throw new NullPointerException();
        }
        if (!authenticate(password)){
            throw new WrongPasswordException();
        }
        Set<T> mydash = new TreeSet(new MyCompare<>());
        for (int i=0;i<myCategories.size();i++){
            for (int j=0;j<myCategories.get(i).getPosts().size();j++){
                mydash.add(myCategories.get(i).getPosts().get(j));
            }
        }
        Iterator dash_index = mydash.iterator();
        return dash_index;
    }

    @Override
    public void insertLike(String friend, T post) throws NullPointerException, UnknownUserException, InvalidContentException {
        if (friend==null | post==null) {
            throw new NullPointerException();
        }
        boolean found=false;
        Category aux=null;
        T tmp= null;
        for (int i=0;i<myCategories.size();i++){
            for (int j=0;j<myCategories.get(i).getPosts().size();j++){
                if (myCategories.get(i).getPosts().get(j).equals(post)){
                    found=true;
                    aux=myCategories.get(i);
                    tmp=myCategories.get(i).getPosts().get(j);

                }
            }
        }
        if (found==false){
            throw new InvalidContentException();
        }
        if (!aux.getFriends().contains(friend)){
            throw new UnknownUserException();
        }
        try {
            tmp.Addlike(friend);
        }
        catch (DoubleLikeException e){
            System.out.println(e.getMessage() + "\n");
        }
    }

    @Override
    public Iterator<T> getFriendIterator(String friend) throws NullPointerException, UnknownUserException {
        if (friend == null) {
            throw new NullPointerException();
        }
        Vector<Category<T>> tmp = new Vector<>();
        boolean foundFr=false;
        for (int i = 0; i < myCategories.size(); i++) {
            if (myCategories.get(i).getFriends().contains(friend)) {
                tmp.add(myCategories.get(i));
                foundFr=true;
            }
        }
        if (!foundFr) throw new UnknownUserException();
        Vector<T> aux = new Vector<>();
        for (int i = 0; i < tmp.size(); i++) {
            for (int j = 0; j < tmp.get(i).getPosts().size(); j++) {
                try {
                    aux.add(tmp.get(i).getCont(j));
                } catch (IndexOutOfBoundsException | NegativeIndexException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        Iterator vec_index = aux.iterator();
        return vec_index;
    }

        public void printCats(){
            System.out.println("CATEOGRIE PRESENTI IN BACHECA:");
            for (Category<T> c:myCategories) {
                System.out.println(c.getName());
            }
            System.out.println(" ");
        }

        public void printFriends(){
            System.out.println("STAMPA CATEGORIE-AMICI");
            for (Category<T> c: myCategories){
                System.out.println(c.getName());
                for (String f:c.getFriends()) {
                    System.out.println(f);
                }
            }
            System.out.println(" ");
        }
}

