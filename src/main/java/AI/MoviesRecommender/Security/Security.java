package AI.MoviesRecommender.Security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import AI.MoviesRecommender.DAO.User_DAO;
import AI.MoviesRecommender.Model.User;

/**
 * Klasa odpowiedzialna za logowanie użytkownika, sprawdzanie czy już się
 * zalogował, oraz pobierania danych zalogowanego użytkownika.
 * 
 * @author Marek Pałdyna
 * @version 1.0
 */
@Repository
public class Security {
    @Autowired
    User_DAO database;

    HttpServletRequest request;

    /**
     * Konstruktor Inicjuje Klasę do działania
     * 
     * @param req - typu HttpServletRequest - request ze strony
     * @param dat - typu UsersDAO - instancja klasy do połączeń z bazą danych
     */
    public Security(HttpServletRequest req, User_DAO dat) {
        request = req;
        database = dat;
    }

    /**
     * Loguje użytkownika. Pobiera dane przesłane przez protokół POST Potrzebuje
     * conajmniej danej "nick"
     * 
     * @version 1.0
     * @return true jeśli logowanie się powiodło
     */
    public boolean login() {
        String nickname;
        String pass;

        nickname = request.getParameter("nick").toString();
        pass = request.getParameter("pass").toString();

        if (nickname == null || nickname.isEmpty()) {
            return false;
        }

        User resultUsers = database.getUserLoginData(nickname, pass);
        if (resultUsers==null) {
            return false;
        } else {
            String name = resultUsers.getNick();
            String nazwisko = resultUsers.getEmail();
            Long idU = resultUsers.getID();
            HttpSession session = request.getSession();
            session.setAttribute("imie", name); // dodawanie pola do sesji
            session.setAttribute("nazwisko", nazwisko);
            session.setAttribute("id", idU);
            session.setMaxInactiveInterval(60 * 60); // usuniecie pol sesji po 60 minutach

            return true;
        }

    }
    /**
     * Loguje użytkownika. z podanymi danymi w argumentach
     * conajmniej danej "nick"
     * @version 1.0
     * 
     * @param nickname - nick usera do zalogowania
     * @param nickname - hasło usera do zalogowania
     * 
     * @return true jeśli logowanie się powiodło
     */
    public boolean login(String nickname, String pass) {

        nickname = request.getParameter("nick").toString();
        pass = request.getParameter("pass").toString();

        if (nickname == null || nickname.isEmpty()) {
            return false;
        }

        User resultUsers = database.getUserLoginData(nickname, pass);
        if (resultUsers==null) {
            return false;
        } else {
            String name = resultUsers.getNick();
            String nazwisko = resultUsers.getEmail();
            Long idU = resultUsers.getID();
            HttpSession session = request.getSession();
            session.setAttribute("imie", name); // dodawanie pola do sesji
            session.setAttribute("nazwisko", nazwisko);
            session.setAttribute("id", idU);
            session.setMaxInactiveInterval(60 * 60); // usuniecie pol sesji po 60 minutach

            return true;
        }

    }

    /**
     * 
     * Funkcja sprawdzająca czy użytkownik jest zalogowany
     * 
     * 
     * @version 1.0
     * @return true - jeśli uzytkownik jest zalogowany
     */
    public boolean isLoged() {
        HttpSession session = request.getSession();
        if (session.getAttribute("imie") == null || session.getAttribute("nazwisko") == null
                || session.getAttribute("id") == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Funkcja zwracająca Imię użytkownika pobieraną z danych sesji
     * 
     * @version 1.0
     * @return Nazwe użytkownika
     */
    public String getUserName() {
        if (isLoged()) {
            HttpSession session = request.getSession();
            return session.getAttribute("imie").toString();
        } else
            return null;
    }

    /**
     * Funkcja zwracająca ID użytkonika pobierany z danych sesji
     * 
     * @version 1.0
     * @return Integer ID użytkownika
     */
    public Long getUserID() {
        if (isLoged()) {
            HttpSession session = request.getSession();
            return (Long) session.getAttribute("id");
        } else {
            return null;
        }
    }

    /**
     * Funckja zwracająca Nazwisko użytkownika z danych sesji *
     * 
     * @version 1.0
     * @return Nazwisko
     */
    public String getUserSurName() {
        if (isLoged()) {
            HttpSession session = request.getSession();
            return session.getAttribute("Nazwisko").toString();
        } else {
            return null;
        }
    }

    /**
     * Funkcja pobierająca dane użytkonika z bazy danych na podstawie ID (pobranego
     * z danych sesji)
     * 
     * @version 1.0
     * @return Dane zalogowanego użytkownika
     */
    public User getFullUserData() {
        if (isLoged()) {
            HttpSession session = request.getSession();
            User result = database.getUserByID((Long) session.getAttribute("id"));
            if (result == null) {
                return null;
            } else {
                return result;
            }
        } else {
            return null;
        }
    }

    /**
     * Funkcja usuwa dane sesji = wylogowanie użytkownika
     */
    public void logout() {
        if (isLoged()) {
            HttpSession session = request.getSession();

            session.removeAttribute("imie"); // usuwanie pola do sesji
            session.removeAttribute("nazwisko");
            session.removeAttribute("id");
            session.removeAttribute("uprawnienia");
        } else
            return;
    }

}