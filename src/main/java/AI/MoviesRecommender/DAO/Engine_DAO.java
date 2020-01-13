package AI.MoviesRecommender.DAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import AI.MoviesRecommender.Model.EngineFilm;
import AI.MoviesRecommender.Model.EngineUser;
import AI.MoviesRecommender.Model.Similarity;
import AI.MoviesRecommender.Model.User;
import AI.MoviesRecommender.Recommender.Engine;

/**
 * Engine_DAO 
 * Odpowiada za funkcjonalności związane z rekomendacją
 * 
 * @author Marek Pałdyna
 */
@Repository
public class Engine_DAO {
    @Autowired
    public User_DAO user_DAO;

    @Autowired
    public Film_DAO film_DAO;

    @Autowired
    public Engine engine;

    public List<EngineUser> users = null; // userzy z obiliczonymi podobieństwami
    
    public List<EngineUser> getAllU(){
        return users;
    }
    
    /**
     * Zwraca usera o podanym id
     * 
     * @param Id - id usera do znalezienia
     * @return EngineUser - znaleziony user
     * @see EngineUser
     */
    public EngineUser getUserById(Long Id) {
        if (users !=null) {
            // System.out.println("GetUserById users != null");
            for (EngineUser engineUser : users) {
                if (engineUser.getID().equals(Id)){
                    
                    // System.out.println("Found");
                    return engineUser;
                }
                else{
                    // System.out.println(engineUser.getID());
                }
            }
        }
        // System.out.println("GetUserById users = null");
        return null;
    }
    /**
     * Tworzy i zwraca usera jeśli nie istnieje. W przeciwnym wypadku aktualizuje podanego usera
     * @param user - user do dodania 
     * @return EngineUser - stworzony lub zaaktualizowany user
     */
    public EngineUser createUser(User user){
        if (user.getPolubione() == null) {
            return null;
        }
        if (user.getPolubione().size() < 4) {
            return null;
        }
        if (getUserById(user.getID()) == null) {
            
            EngineUser u = new EngineUser(user);
            
            List<Similarity> sim = engine.getSimUsers(u.getID());// dodanie podobnych userów
            if(sim != null)
                Collections.sort(sim);
            u.setSimilarity(sim);
    
            List<EngineFilm> recoFilms = engine.getRecommendedFilmsList(u); // dodanie recomendowanych filmów
            if(recoFilms != null)
            Collections.sort(recoFilms);
            u.setSugFilms(recoFilms);
            if (users == null) {
                users = new ArrayList<>();
            }
            users.add(u);
            return u;
        }
        else{
            return updateUser(getUserById(user.getID()));
        }
    }


    /**
     * Aktualizuje usera o podanym ID
     * 
     * @param Id - id usera do zaktualizowania
     * @return EngineUser - zaaktualizowany user, null jeśli podane id należy do sztucznego usera
     */
    public EngineUser updateUser(Long Id) {
        EngineUser u = this.getUserById(Id);

        if (u != null) {
            if (u.getPolubione() == null) {
                u.setSimilarity(null);
                u.setSugFilms(null);
                return null;
            }
            if (u.getPolubione().size() < 4) {
                u.setSugFilms(null);
                u.setSimilarity(null);
                return null;
            }
            List<Similarity> sim = engine.getSimUsers(u.getID());
            Collections.sort(sim);// sortowanie
            u.setSimilarity(sim);
            List<EngineFilm> recoFilms = engine.getRecommendedFilmsList(u);// dodanie recomendowanych userów
            Collections.sort(recoFilms);
            u.setSugFilms(recoFilms);
            // System.out.println("u!=null");
        } else {
            User user = user_DAO.getUserByID(Id);
            if (user != null) {
                if (user.getNick() == null) {
                    return null;
                } else {
                    u = new EngineUser(user);
                    if (u.getPolubione() == null) {
                        u.setSugFilms(null);
                        u.setSimilarity(null);
                        return null;
                    }
                    if (u.getPolubione().size() < 4) {
                        u.setSugFilms(null);
                        u.setSimilarity(null);
                        return null;
                    }
                    createUser(user);
                }
            }
            // List<Similarity> sim = engine.getSimUsers(u.getID());
            // Collections.sort(sim);// sortowanie
            // u.setSimilarity(sim);
            // List<EngineFilm> recoFilms = engine.getRecommendedFilmsList(u);// dodanie recomendowanych userów
            // Collections.sort(recoFilms);
            // u.setSugFilms(recoFilms);
            // users.add(u);
            
            // System.out.println("u = " + user.getID());
        }
        // System.out.println("User "+ Id +" updated");
        return u;
    }

    /**
     * Aktualizuje podanego usera
     * 
     * @param u - user do zaktualizowania
     */
    public EngineUser updateUser(User u) {
        return updateUser(u.getID());
    }

    /**
     * Aktualizuje podanego usera
     * 
     * @param u - user do zaktualizowania
     */
    public EngineUser updateUser(EngineUser u) {
        if (u.getPolubione()==null) {
            return null;
        }
        if(u.getPolubione().size() < 4){
            return null;
        } 
        List<Similarity> sim = engine.getSimUsers(u.getID());// dodanie podobnych userów
        Collections.sort(sim);
        u.setSimilarity(sim);

        List<EngineFilm> recoFilms = engine.getRecommendedFilmsList(u); // dodanie recomendowanych filmów
        Collections.sort(recoFilms);
        u.setSugFilms(recoFilms);
        return u;
    }

}