/* AF: f(< UserID, Categories[0...categories[size-1], Password >)->
        < myUserID,myCategories[0...myCategories.size-1],myPassword >
   IR: myUserID!=null && myPassword!=null && myCategories!=null &&
       forall 0<=i,j<myCategories.size -> myCategories[i].equals(myCategories[j])==false
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class BoardII<T extends Data> implements DataBoard<T>  {
    String userID;
    String myPass;
    List<CategoryII<T>> myCategories;

    public BoardII (String usr,String pwd){
        userID=usr;
        myPass=pwd;
        myCategories = new ArrayList<>();
    }

    private boolean authentication(String password){
        return password.equals(myPass);
    }
    // RETURNS: true se password.equals(myPassword), false altrimenti

    @Override
    public String getMyId() {
        return userID;
    }

    @Override
    public void createCategory(String category, String password) throws NullPointerException, WrongPasswordException, DuplicateCategoryException {
        if (category==null | password==null) throw new NullPointerException();
        if (!authentication(password)) throw new WrongPasswordException();
        for (CategoryII<T> c:myCategories) {
            if (c.getName().equals(category)){
                throw new DuplicateCategoryException();
            }
        }
        CategoryII<T> nuova= new CategoryII<>(category);
        myCategories.add(nuova);
    }

    @Override
    public void removeCategory(String category, String password) throws NullPointerException, WrongPasswordException, CategoryNoExistsException {
        if (category==null | password==null) throw new NullPointerException();
        if (!authentication(password)) throw new WrongPasswordException();
        for (CategoryII<T> c:myCategories) {
            if (c.getName().equals(category)){
                myCategories.remove(c);
                return;
            }
        }
        throw new CategoryNoExistsException();
    }

    @Override
    public void addFriend(String category, String password, String friend) throws NullPointerException, WrongPasswordException, CategoryNoExistsException {
        if (category==null | password==null | friend==null) throw new NullPointerException();
        if (!authentication(password)) throw new WrongPasswordException();
        for (CategoryII<T> c:myCategories) {
            if (c.getName().equals(category)) {
                try {
                    c.newFriend(friend);
                } catch (DuplicateFriendException e){
                    System.out.println(e.getMessage() + "\n");
                }
                return;
            }
        }
        throw new CategoryNoExistsException();
    }

    @Override
    public void removeFriend(String category, String password, String friend) throws NullPointerException, WrongPasswordException {
        if (category==null | password==null | friend==null) throw new NullPointerException();
        if (!authentication(password)) throw new WrongPasswordException();
        for (CategoryII<T> c:myCategories) {
            if (c.getName().equals(category)) {
                c.delFriend(friend);
                return;
            }
        }
    }

    @Override
    public boolean put(String password, T post, String category) throws NullPointerException, WrongPasswordException {
        if (category==null | password==null | post==null) throw new NullPointerException();
        if (!authentication(password)) throw new WrongPasswordException();
        for (CategoryII<T> c:myCategories) {
            if (c.getName().equals(category)) {
                try {
                    c.addData(post);
                } catch (InvalidContentException e){
                    System.out.println(e.getMessage() + "\n");
                    return false;
                }
                return true;
            }
        }
        return false; // operazione fallita perchè non ho trovato la categoria
    }

    @Override
    public T get(String password, T post) throws NullPointerException, WrongPasswordException, InvalidContentException {
        if (password==null | post==null) throw new NullPointerException();
        if (!authentication(password)) throw new WrongPasswordException();
        for (CategoryII<T> c:myCategories) {
            if (c.getPosts().contains(post)){
                return (T) post.copy();
            }
        }
        throw new InvalidContentException();
    }

    @Override
    public T remove(String password, T post) throws NullPointerException, WrongPasswordException, InvalidContentException {
        if (password==null | post==null) throw new NullPointerException();
        if (!authentication(password)) throw new WrongPasswordException();
        T tmp=null;
        for (CategoryII<T> c:myCategories) {
            if (c.getPosts().contains(post)){
                tmp=(T) post.copy();
                c.remData(post);
                return tmp;
            }
        }
        throw new InvalidContentException();
    }

    @Override
    public List<T> getDataCategory(String password, String category) throws NullPointerException, WrongPasswordException, CategoryNoExistsException {
        if (password == null | category == null) throw new NullPointerException();
        if (!authentication(password)) throw new WrongPasswordException();
        CategoryII<T> aux = null;
        for (CategoryII<T> c : myCategories) {
            if (c.getName().equals(category)) {
                aux = c;
            }
        }
        if (aux == null) throw new CategoryNoExistsException();
        List<T> catList = new ArrayList<>();
        for (T post:aux.getPosts()) {
            catList.add(post);
        }
        return catList;
    }

    @Override
    public Iterator<T> getIterator(String password) throws NullPointerException, WrongPasswordException {
        if (password == null) throw new NullPointerException();
        if (!authentication(password)) throw new WrongPasswordException();
        TreeSet<T> databoardSet = new TreeSet<>(new MyCompare<>());
        for (CategoryII<T> c:myCategories) {
            for (T post:c.getPosts()) {
                databoardSet.add(post);
            }
        }
        Iterator<T> index= databoardSet.iterator();
        return index;
    }

    @Override
    public void insertLike(String friend, T post) throws NullPointerException, UnknownUserException, InvalidContentException {
        if (friend==null | post==null) throw new NullPointerException();
        boolean check=false;
        for (CategoryII<T> c:myCategories){
            if (c.getPosts().contains(post)) {
                check=true;
                if (c.getFriends().contains(friend)) {
                    try {
                        post.Addlike(friend);
                    } catch (DoubleLikeException e) {
                        System.out.println(e.getMessage() + "\n");
                    }
                    return;
                }
            }
        }
        if (!check) throw new InvalidContentException();
        throw new UnknownUserException();
    }

    @Override
    public Iterator<T> getFriendIterator(String friend) throws NullPointerException, UnknownUserException {
        if (friend==null) throw new NullPointerException();
        boolean foundFr=false;
        List<T> friendCont = new ArrayList<>();
        for (CategoryII<T> c:myCategories) {
            if (c.getFriends().contains(friend)){
                foundFr=true;
                for (T post:c.getPosts()) {
                    friendCont.add(post);
                }
            }
        }
        if (!foundFr) throw new UnknownUserException();
        Iterator<T> index = friendCont.iterator();
        return index;
    }

    public void printCats(){
        System.out.println("CATEOGRIE PRESENTI IN BACHECA:");
        for (CategoryII<T> c:myCategories) {
            System.out.println(c.getName());
        }
        System.out.println(" ");
    }

    public void printFriends(){
        System.out.println("STAMPA CATEGORIE-AMICI");
        for (CategoryII<T> c: myCategories){
            System.out.println(c.getName());
            for (String f:c.getFriends()) {
                System.out.println(f);
            }
        }
        System.out.println(" ");
    }
}
