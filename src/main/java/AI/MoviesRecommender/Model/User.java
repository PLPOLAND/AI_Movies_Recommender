package AI.MoviesRecommender.Model;

import java.util.List;

/**
 * User
 * Klasa przechowująca dane usera. 
 * @author Marek Pałdyna
 */
public class User {

    Long ID;            // ID usera
    String imie;        //imie usera
    String email;       // email usera
    String nazwisko;    // nazwisko usera
    String nick;        // nick usera
    String pass;        // hasło usera

    List<Long> polubione;   //Przechowuje id filmów które zostały polubione
    List<Long> nielubione;  //Przechowuje id filmów które zostały ocenione negatywnie

    public Long getID() {
        return this.ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getImie() {
        return this.imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNazwisko() {
        return this.nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPass() {
        return this.pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public List<Long> getPolubione() {
        return this.polubione;
    }

    public void setPolubione(List<Long> polubione) {
        this.polubione = polubione;
    }

    public List<Long> getNielubione() {
        return this.nielubione;
    }

    public void setNielubione(List<Long> nielubione) {
        this.nielubione = nielubione;
    }

    public User(Long ID, String imie, String email, String nazwisko, String nick, String pass, List<Long> polubione, List<Long> nielubione) {
        this.ID = ID;
        this.imie = imie;
        this.email = email;
        this.nazwisko = nazwisko;
        this.nick = nick;
        this.pass = pass;
        this.polubione = polubione;
        this.nielubione = nielubione;
    }

}