package AI.MoviesRecommender.Model;

import java.util.List;

/**
 * Klasa przetrzymująca dane o filmie.
 * 
 * @author Rafał Świąder
 */

 public class DataBase
 {
     //Listy użytkowników i filmów
     List<User> users; 
     List<Film> films;

     DataBase(List<User> users, List<Film> films)
     {
        this.users = users;
        this.films = films;
     }

     public User getUser(Long ID)
     {
         for(User i:this.users)
         {
            if(i.getID() == ID)
                return i;
         }
         return null;
     }

     public Film getFilm(Long ID)
     {
         for(Film i:this.films)
         {
            if(i.getId() == ID)
                return i;
         }
         return null;
     }
 }