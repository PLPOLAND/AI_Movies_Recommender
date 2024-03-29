package AI.MoviesRecommender.Controler;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import AI.MoviesRecommender.DAO.Engine_DAO;
import AI.MoviesRecommender.DAO.Film_DAO;
import AI.MoviesRecommender.DAO.User_DAO;
import AI.MoviesRecommender.Model.EngineFilm;
import AI.MoviesRecommender.Model.EngineUser;
import AI.MoviesRecommender.Model.Film;
import AI.MoviesRecommender.Model.User;
import AI.MoviesRecommender.Security.Security;

/**
 * RestController
 */
@RequestMapping("/api")
@RestController
public class RESTController {
    @Autowired
    User_DAO userDatabase;
    
    @Autowired
    Film_DAO filmDatabase;
    
    @Autowired
    Engine_DAO engineDatabase;

    /**
     * Rejestracja 
     */
    @RequestMapping("/register")
    public boolean register(HttpServletRequest request, @RequestParam("nick") String nick, @RequestParam("name") String name,
                            @RequestParam("lastname") String lastname, @RequestParam("pass") String password, @RequestParam("email") String email)
    {
        if (userDatabase.contains(nick)) {//jeśli baza zawiera już usera o takim nicku zwróć informację że nie można stworzyć takiego usera
            return false;
        }
        User u = new User();
        u.setID(userDatabase.getNextID());
        u.setNick(nick);
        u.setImie(name);
        u.setNazwisko(lastname);
        u.setPass(password);
        u.setEmail(email);
        
        userDatabase.save(u);
        userDatabase.getDatabase().add(u);
        Security security = new Security(request, userDatabase);

        if (security.login(u.getNick(), u.getPass())) {
            return true;
        } else {
            return false;
        }

        // return true;
    }
    /**
     * Pobieranie recomendowanych 
     */
    @RequestMapping("/recommended")
    public List<EngineFilm> recommended(HttpServletRequest request) {
        Security security = new Security(request, userDatabase);
        User user = security.getFullUserData();
        if (user.getPolubione() == null) {
            return null;
        }
        if (user.getPolubione().size() < 4) {
            return null;
        }
        EngineUser u = engineDatabase.getUserById(user.getID());
        List<EngineFilm> sugFilms = new ArrayList<>();
        for (EngineFilm engineFilm : u.getSugFilms()) {
            if (engineFilm.getRecomendation()>=50f) {
                sugFilms.add(engineFilm);
            }
        }
        return sugFilms;
    }

    /**
     * Logowanie usera
     */
    @RequestMapping("/login")
    public boolean login(HttpServletRequest request) {
        Security security = new Security(request, userDatabase);

        if (security.login()) {
            // engineDatabase.init(userDatabase,filmDatabase);
            engineDatabase.createUser(security.getFullUserData());
            return true;
        } else {
            return false;
        }
    }
    /**
     * Pobieranie listy userów
     */
    @RequestMapping("/allu")
    public List<User> getallusers(){
        return userDatabase.getDatabase();
    }
    
    /**
     * Pobieranie listy filmów
     */
    @RequestMapping("/allf")
    public List<Film> getallfilms(){
        //System.out.println(filmDatabase.getDatabase());
        return filmDatabase.getDatabase();
    }
    /**
     * Pobieranie listy nielubionych filmów dla zalogowanego usera
     */
    @RequestMapping("/getUnliked")
    public List<Long> getUnlikedForUser(HttpServletRequest request) {
        Security security = new Security(request, userDatabase);
        return security.getFullUserData().getNielubione();
    }
    /**
     * Pobieranie listy polubionych filmów dla zalogowanego usera
     * @param request
     * @return
     */
    @RequestMapping("/getLiked")
    public List<Long> getLikedForUser(HttpServletRequest request) {
        Security security = new Security(request, userDatabase);
        return security.getFullUserData().getPolubione();
    }
    /**
     * Tworzenie nowego filmu
     */
    @RequestMapping("/createFilm")
    public boolean createFilm(@RequestParam("tytul") String tytul, @RequestParam("zdjecie") String zdjecie, @RequestParam("gatunek") String gatunek, @RequestParam("rok") int rok) {
        if (filmDatabase.contains(tytul)) {
            return false;//Poinformuj o niepowodzeniu
        } else {
            Film film = new Film();
            film.setID(filmDatabase.getNextID());
            film.setZdjecie(zdjecie);
            film.setRokProdukcji(rok);
            film.setGatunek(gatunek);
            film.setTytul(tytul);
            filmDatabase.save(film);
            return true;//Poinformuj o powodzeniu
        }
    }
    /**
     * Dodanie filmu do listy lubionych dla zalogowanego usera 
     */
    @RequestMapping("/likeFilm") 
    public boolean likeFilm(@RequestParam("idF") Long idF, HttpServletRequest request){
        Security security = new Security(request, userDatabase);
        User user = security.getFullUserData();
        if (user.getPolubione() != null) {
            if (user.getPolubione().contains(idF)) {
                userDatabase.delLikeFilm(user, idF);
                if (user.getNielubione()!= null){
                    if (user.getNielubione().contains(idF)){
                        userDatabase.delUnLikeFilm(user,idF);
                    }
                }
                engineDatabase.updateUser(user);//zaatualizuj proponowane usera
                return false;
            }
            else{
                userDatabase.likeFilm(user, idF);
            }
        }
        else{
            user.setPolubione(new ArrayList<>());
            userDatabase.likeFilm(user, idF);
        }
        if (user.getNielubione()!= null){
            if (user.getNielubione().contains(idF)){
                userDatabase.delUnLikeFilm(user,idF);
            }
        }
        engineDatabase.updateUser(user);//zaktualizuj proponowane usera
        return true; //Zwróć informację o tym że film istnieje w polubionych
    }

    /**
     * Dodanie filmu do listy nielubionych dla zalogowanego usera
     */
    @RequestMapping("unLikeFilm") 
    public boolean unLikeFilm(@RequestParam("idF") Long idF, HttpServletRequest request) {
        Security security = new Security(request, userDatabase);
        User user = security.getFullUserData();
        
        if (user.getNielubione()!=null) {
            if (user.getNielubione().contains(idF)) {
                userDatabase.delUnLikeFilm(user, idF);
                if (user.getPolubione() != null) {
                    if (user.getPolubione().contains(idF)) {
                        userDatabase.delLikeFilm(user, idF);
                    }
                }
                engineDatabase.updateUser(user);//aktualizuj proponowane usera
                return false;
            }else{
                userDatabase.UnLikeFilm(user, idF);
            }
        }
        else{
            user.setNielubione(new ArrayList<>());
            userDatabase.UnLikeFilm(user, idF); 
        }
        if (user.getPolubione()!=null){
            if (user.getPolubione().contains(idF)) {
                userDatabase.delLikeFilm(user, idF);
                engineDatabase.updateUser(user);
                return false;
            }
        }
        engineDatabase.updateUser(user);// aktualizuj proponowane usera
        return true;// Zwróć informację o tym że film istnieje w niepolubionych
    }
    
}