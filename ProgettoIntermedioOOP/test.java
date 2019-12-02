/* TEST SET PER ENTRAMBE LE IMPLEMENTAZIONI DI DATABOARD:
   PER PASSARE DA UN'IMPLEMENTAZIONE ALL'ALTRA, SOSTITUIRE IL TIPO "Board" CON "BoardII"
   NELLA CREAZIONE DELLA BACHECA (RIGA 12)
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class test {
    public static void main (String[] args) {
        BoardII salvo = new BoardII("salvo88", "220288"); // creo la mia bacheca (Board o BoardII)
        // UTENTI ISCRITTI: Laura, Marco, Giuseppe, Giulia
        System.out.println("PER I SEGUENTI TEST IPOTIZZO CHE CI SIANO 4 UTENTI ISCRITTI:\nLAURA, MARCO, GIUSEPPE, GIULIA\n");

        // CREAZIONE CATEGORIE
        try {
            salvo.createCategory("Rock", "220288");
            salvo.createCategory("House", "220288");
            salvo.createCategory("Classica","220288");
            System.out.println("INSERISCO UNA PASSWORD ERRATA");
            salvo.createCategory("Prova","34875");   // provo ad inserire password errata
        } catch (NullPointerException | WrongPasswordException | DuplicateCategoryException e) {
            System.out.println(e.getMessage() + "\n");
        }

        try {
            System.out.println("PASSO I PARAMETRI A NULL");
            salvo.createCategory(null,null); // passo i parametri null
        } catch (NullPointerException | WrongPasswordException | DuplicateCategoryException e) {
            System.out.println(e.toString() + "\n");
        }

        // AGGIUNGO GLI AMICI ALLE CATEGORIE
        try {
            salvo.addFriend("Rock", "220288", "Giulia");
            salvo.addFriend("House","220288","Marco");
            salvo.addFriend("Classica","220288","Laura");
            salvo.addFriend("House","220288","Laura");
            salvo.addFriend("Rock","220288","Giuseppe");
            System.out.println("AGGIUNGO NUOVAMENTE UN AMICO");
            salvo.addFriend("Classica","220288","Laura"); // provo ad aggiungere nuovamente Laura alla categoria Classica
        } catch (NullPointerException | WrongPasswordException | CategoryNoExistsException e) {
            System.out.println(e.getMessage() + "\n");
        }

        salvo.printFriends(); // stampa di prova

        Music post1 = null;
        Music post2 = null;
        Music post3 = null;
        Music post4 = null;

        // CREO DEI CONTENUTI DI TIPO MUSIC CHE ESTENDE DATA
        try {
            post1 = new Music("Vasco Rossi", "Sally", 197);
            post2 = new Music("David Guetta", "Wet", 254);
            post3 = new Music("ACDC", "Tnt", 182);
            post4 = new Music("Mozart", "Rondo", 329);
            System.out.println("PROVO A INSERIRE UNA DURATA NEGATIVA");
            Music post5 = new Music("aaa","bbb",-65); // inserisco una durata negativa
        } catch (NullPointerException | NegativeDurationException e){
            System.out.println(e.getMessage() + "\n");
        }

        // INSERISCO I CONTENUTI NELLE MIE CATEGORIE
        System.out.println("VALORI BOOLEAN DI RITORNO DEL METODO PUT");
        try {
            System.out.println(salvo.put("220288",post1, "Rock"));
            System.out.println(salvo.put("220288",post2, "House"));
            System.out.println(salvo.put("220288",post3,"Rock"));
            System.out.println(salvo.put("220288",post4,"Classica"));
        } catch (NullPointerException | WrongPasswordException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("");

        // PROVA DEL METODO GET SU UN CONTENUTO
        System.out.println("METODO GET");
        Music copia=null;
        try {
            copia= (Music) salvo.get("220288",post1);
            System.out.println(copia.Display() + "\n");
        } catch (NullPointerException | WrongPasswordException | InvalidContentException e) {
            System.out.println(e.getMessage());
        }

        // INSERIMENTO LIKE DA PARTE DI DUE UTENTI AMICI
        try {
            salvo.insertLike("Giulia",post1);
            salvo.insertLike("Laura",post4);
            salvo.insertLike("Giuseppe",post1);
            System.out.println("CERCO DI METTERE LIKE DUE VOLTE");
            salvo.insertLike("Giulia",post1);     // provo a mettere nuovamente un like sullo stesso post
        } catch (UnknownUserException | InvalidContentException e){
            System.out.println(e.getMessage());
        }
        // METTO LIKE A UN CONTENUTO DI CUI NON SEGUO LA CATEGORIA
        System.out.println("METTO LIKE A CONTENUTO DI CUI NON SONO AMICO");
        try {
            salvo.insertLike("Giulis",post2);
        } catch (UnknownUserException | InvalidContentException e){
            System.out.println(e.getMessage() + "\n");
        }

        // PROVA METODO GET FRIEND ITERATOR SU DUE AMICI
        System.out.println("GET ITERATOR");
        try {
            Iterator <Music> indexMarco = salvo.getFriendIterator("Marco");
            Iterator <Music> indexLaura = salvo.getFriendIterator("Laura");
            System.out.println("MARCO");
            while (indexMarco.hasNext()){
                System.out.println(indexMarco.next().Display());
            }
            System.out.println("LAURA");
            while (indexLaura.hasNext()){
                System.out.println(indexLaura.next().Display());
            }
            System.out.println("");
        } catch (NullPointerException | UnknownUserException e){
            System.out.println(e.getMessage());
        }


        // PROVA METODO GET CATEGORY
        System.out.println("GET CATEGORY ROCK");
        List<Music> myCat = new ArrayList();
        try {
            myCat=salvo.getDataCategory("220288","Rock");
        } catch (NullPointerException | WrongPasswordException | CategoryNoExistsException e){
            System.out.println(e.getMessage());
        }
        for (Music m:myCat){
            System.out.println(m.Display());    // stampa di prova
        }
        System.out.println("");

        // PROVA METODO GET ITERATOR
        System.out.println("GET ITERATOR");
        try {
            Iterator <Music> index1 = salvo.getIterator("220288");
            while (index1.hasNext()){
                System.out.println(index1.next().Display());
            }
        } catch (NullPointerException | WrongPasswordException e){
            System.out.println(e.getMessage());
        }
        System.out.println("");

        // RIMUOVO GIULIA DAGLI AMICI E PROVO A METTERE UN LIKE
        System.out.println("PROVO A METTERE UN LIKE DOPO AVER ELIMINATO UN AMICO");
        try {
            salvo.removeFriend("Rock","220288","Giulia");
        }
        catch (WrongPasswordException e){
            System.out.println(e.getMessage());
        }
        try {
            salvo.insertLike("Giulia",post1);
        } catch (UnknownUserException | InvalidContentException e){
            System.out.println(e.getMessage());
        }

        System.out.println("");
        // RIMOZIONE CONTENUTO E PROVA DEL METODO GET SUL CONTENUTO ELIMINATO
        System.out.println("REMOVE (PROVA RETURN)");
        try {
            System.out.println(salvo.remove("220288",post1).Display());
        } catch (WrongPasswordException | InvalidContentException e){
            System.out.println(e.getMessage());
        }
        System.out.println("");
        System.out.println("PROVO A FARE GET SUL POST ELIMINATO");
        try {
            System.out.println(salvo.get("220288",post1).Display());
        } catch (NullPointerException | WrongPasswordException | InvalidContentException e){
            System.out.println(e.getMessage());
        }
        System.out.println("");

        // PROVO A RIMUOVERE UNA CATEGORIA E A CHIAMARE GET SU UN POST APPARTENENTE ALLA CATEGORIA ELIMINATA
        System.out.println("RIMOZIONE CATEGORIA HOUSE\n");
        try {
            salvo.removeCategory("House","220288");
        } catch (NullPointerException | WrongPasswordException| CategoryNoExistsException e){
            System.out.println(e.getMessage());
        }
        System.out.println("FACCIO GET SU POST APPARTENENTE ALLA CATEGORIA APPENA ELIMINATA");
        try {
            System.out.println(salvo.get("220288",post2).Display());
        } catch (NullPointerException | WrongPasswordException | InvalidContentException e){
            System.out.println(e.getMessage());
        }
        System.out.println("");

        // PROVO A CREARE UNA CATEGORIA GIA' PRESENTE
        System.out.println("PROVO A INSERIRE UNA CATEGORIA UGUALE A UNA PRESENTE");
        try {
            salvo.createCategory("Classica","220288");
        } catch (NullPointerException | WrongPasswordException | DuplicateCategoryException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("");
        salvo.printCats();

        // INSERISCO NUOVAMENTE UN POST NELLA CATEGORIA IN CUI E' PRESENTE
        System.out.println("PROVA POST DOPPIO IN CATEGORIA ROCK");
        try {
            System.out.println("BOOLEAN DI RITORNO " + salvo.put("220288", post3, "Rock"));
        } catch (WrongPasswordException e){
            System.out.println(e.getMessage());
        }
    }
}