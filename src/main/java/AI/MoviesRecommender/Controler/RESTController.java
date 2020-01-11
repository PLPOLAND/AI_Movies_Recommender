package AI.MoviesRecommender.Controler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import AI.MoviesRecommender.DAO.Film_DAO;
import AI.MoviesRecommender.DAO.User_DAO;
import AI.MoviesRecommender.Model.Film;
import AI.MoviesRecommender.Model.User;
import AI.MoviesRecommender.Recommender.Engine;
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


    @RequestMapping("/register")
    public boolean register(HttpServletRequest request, @RequestParam("nick") String nick, @RequestParam("name") String name,
                            @RequestParam("lastname") String lastname, @RequestParam("pass") String password, @RequestParam("email") String email)
    {
        User u = new User();
        u.setID(userDatabase.getNextID());
        u.setNick(nick);
        u.setImie(name);
        u.setNazwisko(lastname);
        u.setPass(password);
        u.setEmail(email);

        /*boolean acomplished =*/userDatabase.save(u);
        userDatabase.getDatabase().add(u);

        return true;
    }

    @RequestMapping("/recommended")
    public List<Film> recommended(HttpServletRequest request) {
        Security security = new Security(request, userDatabase);
        User user = security.getFullUserData();
        Engine engine = new Engine(userDatabase.getDatabase(), filmDatabase.getDatabase());
        
        List<Long> filmsId = engine.getRecommendedFilms(user,engine.getSimilarUsers(user.getID()));
        List<Film> films = filmDatabase.getFilmsFromIDs(filmsId);//To ma być zwracane
        

        return films;
    }

    @RequestMapping("/login")
    public boolean login(HttpServletRequest request) {
        Security security = new Security(request, userDatabase);

        if (security.login()) {
            return true;
        } else {
            return false;
        }
    }

    @RequestMapping("/allu")
    public List<User> getallusers(){
        return userDatabase.getDatabase();
    }
    
    @RequestMapping("/allf")
    public List<Film> getallfilms(){
        //System.out.println(filmDatabase.getDatabase());
        return filmDatabase.getDatabase();
    }

    @RequestMapping("/getUnliked")
    public List<Long> getUnlikedForUser(HttpServletRequest request) {
        Security security = new Security(request, userDatabase);
        return security.getFullUserData().getNielubione();
    }
    @RequestMapping("/getLiked")
    public List<Long> getLikedForUser(HttpServletRequest request) {
        Security security = new Security(request, userDatabase);
        return security.getFullUserData().getPolubione();
    }

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
    
    @RequestMapping("/likeFilm")
    public boolean likeFilm(@RequestParam("idF") Long idF, HttpServletRequest request){
        Security security = new Security(request, userDatabase);
        User user = security.getFullUserData();
        if (user.getPolubione().contains(idF) && !user.getNielubione().contains(idF)) {
            userDatabase.delLikeFilm(user, idF);
            return false; // Zwróć informację o tym że film nie istnieje w polubionych
        }
        else if(!user.getPolubione().contains(idF) && user.getNielubione().contains(idF)){
            userDatabase.likeFilm(user, idF);
            userDatabase.delUnLikeFilm(user,idF);
        }
        else{
            userDatabase.likeFilm(user, idF);
        }
        return true; //Zwróć informację o tym że film istnieje w polubionych
    }

    @RequestMapping("unLikeFilm")
    public boolean unLikeFilm(@RequestParam("idF") Long idF, HttpServletRequest request) {
        Security security = new Security(request, userDatabase);
        User user = security.getFullUserData();

        if (user.getNielubione().contains(idF) && !user.getPolubione().contains(idF)) {
            userDatabase.delUnLikeFilm(user, idF);
            return false;// Zwróć informację o tym że film nie istnieje w niepolubionych
        } 
        else if(!user.getNielubione().contains(idF) && user.getPolubione().contains(idF)){
            userDatabase.delLikeFilm(user, idF);
            userDatabase.UnLikeFilm(user, idF);
        }
        else {
            userDatabase.UnLikeFilm(user, idF);
        }
        return true;// Zwróć informację o tym że film istnieje w niepolubionych
    }
   
}