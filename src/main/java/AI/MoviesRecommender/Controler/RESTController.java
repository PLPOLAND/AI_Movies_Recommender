package AI.MoviesRecommender.Controler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AI.MoviesRecommender.DAO.Film_DAO;
import AI.MoviesRecommender.DAO.User_DAO;
import AI.MoviesRecommender.Model.Film;
import AI.MoviesRecommender.Model.User;

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

    @RequestMapping("/tmp")
    public User tmp(){
        return new User(1L,"Marek","Marek@paldyna.pl","Pałdyna","Kozak","xxx", new ArrayList<Long>(), new ArrayList<Long>());
    }
    @RequestMapping("/allu")
    public List<User> getallusers(){
        return userDatabase.getDatabase();
    }
    @RequestMapping("/allf")
    public List<Film> getallfilms(){
        System.out.println(filmDatabase.getDatabase());
        return filmDatabase.getDatabase();
    }
}