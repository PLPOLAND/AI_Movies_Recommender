package AI.MoviesRecommender.Controler;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AI.MoviesRecommender.Model.User;

/**
 * RestController
 */
@RequestMapping("/api")
@RestController
public class RESTController {

    @RequestMapping("/tmp")
    public User tmp(){
        return new User(1L,"Marek","Marek@paldyna.pl","Pałdyna","Kozak","xxx", new ArrayList<Long>(), new ArrayList<Long>());
    }
    
}